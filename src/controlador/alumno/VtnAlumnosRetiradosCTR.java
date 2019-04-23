package controlador.alumno;

import controlador.principal.DependenciasVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.alumno.AlumnoCursoBD;
import modelo.alumno.AlumnoCursoMD;
import modelo.alumno.AlumnoCursoRetiradoBD;
import modelo.alumno.AlumnoCursoRetiradoMD;
import modelo.estilo.TblEstilo;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.validaciones.Validar;
import vista.alumno.VtnAlumnosRetirados;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnAlumnosRetiradosCTR extends DependenciasVtnCTR {

    private final VtnAlumnosRetirados vtnAR;

    private final AlumnoCursoRetiradoBD acrb;
    private ArrayList<AlumnoCursoRetiradoMD> almsCursosRetirados;
    private final AlumnoCursoBD acb;
    private ArrayList<AlumnoCursoMD> almnCursos;
    private final PeriodoLectivoBD prd;
    private ArrayList<PeriodoLectivoMD> periodos;
    private int posPrd;
    private DefaultTableModel mdTblAC, mdTblACR;

    public VtnAlumnosRetiradosCTR(ConectarDB conecta, VtnPrincipal vtnPrin, VtnPrincipalCTR ctrPrin,
            VtnAlumnosRetirados vtnAR) {
        super(conecta, vtnPrin, ctrPrin);
        this.vtnAR = vtnAR;
        this.acb = new AlumnoCursoBD(conecta);
        this.acrb = new AlumnoCursoRetiradoBD(conecta);
        this.prd = new PeriodoLectivoBD(conecta);
    }

    public void iniciar() {
        vtnPrin.getDpnlPrincipal().add(vtnAR);
        vtnAR.show();

        iniciarDependencias();
        iniciarTblAC();
        iniciarTblACR();
        iniciarBuscador();
        iniciarAcciones();
        formatoTbl();
    }

    private void clickRetirar() {
        posFila = vtnAR.getTblAlumnosClase().getSelectedRow();
        if (posFila >= 0) {
            retirarAlumno();
        } else {
            JOptionPane.showMessageDialog(vtnPrin, "Seleccione una fila primero.");
        }
    }

    private void retirarAlumno() {
        String observacion = JOptionPane.showInputDialog(
                "Ingrese la razon de porque se retira \n"
                + almnCursos.get(posFila).getAlumno().getNombreCorto() + " de: \n"
                + almnCursos.get(posFila).getCurso().getId_materia().getNombre());
        System.out.println("Esta es mi observacion.");
        if (Validar.esLetras(observacion)) {
            acrb.setAlumnoCurso(almnCursos.get(posFila));
            acrb.setObservacion(observacion);

            acrb.guardar();
        } else {
            JOptionPane.showMessageDialog(vtnPrin, "Unicamente puede ingresar letras.");
            retirarAlumno();
        }

    }

    /**
     * Se incia filtros y estados de botones, y lbls
     */
    private void iniciarDependencias() {
        //Llenamos el combo 
        periodos = prd.cargarPrdParaCmbFrm();
        llenarCmbPrd(periodos);
        vtnAR.getBtnEliminar().setEnabled(false);
        vtnAR.getCbxEliminados().setVisible(false);
        vtnAR.getLblResultados().setText("0 Resultados obtenidos.");
    }

    private void formatoTbl() {
        TblEstilo.formatoTbl(vtnAR.getTblAlumnosClase());
        vtnAR.getTblAlumnosClase().setModel(mdTblAC);
    }

    private void iniciarAcciones() {
        vtnAR.getCmbPrdLectivos().addActionListener(e -> clickPrd());
        vtnAR.getCbxRetirados().addActionListener(e -> clickCbxRetirados());
        vtnAR.getCbxEliminados().addActionListener(e -> clickCbxEliminados());
        vtnAR.getBtnRetirar().addActionListener(e -> clickRetirar());
    }

    private void clickCbxRetirados() {
        if (!vtnAR.getCbxRetirados().isSelected() && !vtnAR.getCbxEliminados().isSelected()) {
            vtnAR.getTblAlumnosClase().setModel(mdTblAC);
            cargarCmbAC();
            mdTblAC.setRowCount(0);
            vtnAR.getBtnEliminar().setEnabled(false);
        } else {
            vtnAR.getCbxEliminados().setSelected(false);
            vtnAR.getTxtBuscar().setText("");
            vtnAR.getBtnEliminar().setEnabled(true);
            cargarCmbACR();
        }
    }

    private void clickCbxEliminados() {

        if (!vtnAR.getCbxRetirados().isSelected() && !vtnAR.getCbxEliminados().isSelected()) {
            vtnAR.getTblAlumnosClase().setModel(mdTblAC);
            cargarCmbAC();
            mdTblAC.setRowCount(0);
        } else {
            vtnAR.getCbxRetirados().setSelected(false);
            vtnAR.getTxtBuscar().setText("");
            vtnAR.getBtnEliminar().setEnabled(false);
            cargarCmbACR();
        }
    }

    private void cargarEliminados() {
        almsCursosRetirados = acrb.cargarRetiradosEliminados();
        llenarTblAlmRetirado(almsCursosRetirados);
    }

    private void cargarRetirados() {
        almsCursosRetirados = acrb.cargarRetirados();
        llenarTblAlmRetirado(almsCursosRetirados);
    }

    private void iniciarBuscador() {
        vtnAR.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    if (vtnAR.getCbxRetirados().isSelected()) {
                        buscarACR(vtnAR.getTxtBuscar().getText().trim());
                    } else if (vtnAR.getCbxEliminados().isSelected()) {
                        buscarACRE(vtnAR.getTxtBuscar().getText().trim());
                    } else {
                        buscarAC(vtnAR.getTxtBuscar().getText().trim());
                    }
                } else if (vtnAR.getTxtBuscar().getText().length() == 0) {
                    mdTblACR.setRowCount(0);
                    mdTblAC.setRowCount(0);
                }

            }

        });
    }

    private void buscarAC(String b) {
        if (Validar.esLetrasYNumeros(b)) {
            almnCursos = acb.buscarClasesAlumnoCurso(b);
            llenarTblAlmClase(almnCursos);
        }
    }

    private void buscarACR(String b) {
        if (Validar.esLetrasYNumeros(b)) {
            almsCursosRetirados = acrb.buscarRetirados(b);
            llenarTblAlmRetirado(almsCursosRetirados);
        }
    }

    /**
     * Buscamos los alumnos cursos que se eliminaron.
     */
    private void buscarACRE(String b) {
        if (Validar.esLetrasYNumeros(b)) {
            almsCursosRetirados = acrb.buscarRetiradosEliminados(b);
            llenarTblAlmRetirado(almsCursosRetirados);
        }
    }

    private void clickPrd() {
        posPrd = vtnAR.getCmbPrdLectivos().getSelectedIndex();
        if (posPrd > 0) {
            if (vtnAR.getCbxRetirados().isSelected()) {
                almsCursosRetirados = acrb.cargarRetiradosPorPrd(periodos.get(posPrd - 1).getId_PerioLectivo());
                llenarTblAlmRetirado(almsCursosRetirados);
            } else if (vtnAR.getCbxEliminados().isSelected()) {
                almsCursosRetirados = acrb.cargarRetiradosPorPrdEliminados(periodos.get(posPrd - 1).getId_PerioLectivo());
                llenarTblAlmRetirado(almsCursosRetirados);
            } else {
                almnCursos = acb.cargarClasesAlumnoCursoPorPrd(periodos.get(posPrd - 1).getId_PerioLectivo());
                llenarTblAlmClase(almnCursos);
            }
        } else {
            mdTblAC.setRowCount(0);
            mdTblACR.setRowCount(0);
            if (vtnAR.getCbxRetirados().isSelected()) {
                cargarRetirados();
            } else if (vtnAR.getCbxEliminados().isSelected()) {
                cargarEliminados();
            }
        }
    }

    private void iniciarTblAC() {
        String[] t = {"Periodo", "Alumno", "Materia", "Estado"};
        String[][] d = {};
        mdTblAC = TblEstilo.modelTblSinEditar(d, t);
    }

    private void iniciarTblACR() {
        String[] t = {"Periodo", "Alumno", "Materia", "Fecha", "Observacion"};
        String[][] d = {};
        mdTblACR = TblEstilo.modelTblSinEditar(d, t);
    }

    private void cargarCmbAC() {
        periodos = prd.cargarPrdParaCmbFrm();
        llenarCmbPrd(periodos);
    }

    private void cargarCmbACR() {
        periodos = prd.cargarPrdParaCmbVtn();
        llenarCmbPrd(periodos);
    }

    private void llenarTblAlmClase(ArrayList<AlumnoCursoMD> almns) {
        vtnAR.getTblAlumnosClase().setModel(mdTblAC);
        mdTblAC.setRowCount(0);
        if (almns != null) {
            almns.forEach(a -> {
                Object[] v = {a.getCurso().getId_prd_lectivo().getNombre_PerLectivo(),
                    a.getAlumno().getNombreCorto(),
                    a.getCurso().getId_materia().getNombre(),
                    a.getEstado()};
                mdTblAC.addRow(v);
            });
            vtnAR.getLblResultados().setText(almns.size() + " Resultados obtendios.");
        } else {
            mdTblAC.setRowCount(0);
        }

    }

    private void llenarTblAlmRetirado(ArrayList<AlumnoCursoRetiradoMD> almns) {
        vtnAR.getTblAlumnosClase().setModel(mdTblACR);
        mdTblACR.setRowCount(0);
        if (almns != null) {
            almns.forEach(a -> {
                Object[] v = {a.getAlumnoCurso().getCurso().getId_prd_lectivo().getNombre_PerLectivo(),
                    a.getAlumnoCurso().getAlumno().getNombreCorto(),
                    a.getAlumnoCurso().getCurso().getId_materia().getNombre(),
                    a.getFecha(), a.getObservacion()};
                System.out.println("leeemos");
                mdTblACR.addRow(v);
            });
            vtnAR.getLblResultados().setText(almns.size() + " Resultados obtendios.");
        } else {
            mdTblACR.setRowCount(0);
        }
    }

    private void llenarCmbPrd(ArrayList<PeriodoLectivoMD> periodos) {
        vtnAR.getCmbPrdLectivos().removeAllItems();
        vtnAR.getCmbPrdLectivos().addItem("Seleccione");
        if (periodos != null) {
            periodos.forEach(p -> {
                vtnAR.getCmbPrdLectivos().addItem(p.getNombre_PerLectivo());
            });
            vtnAR.getCmbPrdLectivos().setSelectedIndex(0);
        }
    }

}
