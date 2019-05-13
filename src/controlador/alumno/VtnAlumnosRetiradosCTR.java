package controlador.alumno;

import controlador.principal.DVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.alumno.AlumnoCursoRetiradoBD;
import modelo.alumno.AlumnoCursoRetiradoMD;
import modelo.estilo.TblEstilo;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.validaciones.Validar;
import vista.alumno.VtnAlumnosRetirados;

/**
 *
 * @author Johnny
 */
public class VtnAlumnosRetiradosCTR extends DVtnCTR {

    private final VtnAlumnosRetirados vtnAR;

    private final AlumnoCursoRetiradoBD acrb;
    private ArrayList<AlumnoCursoRetiradoMD> almsCursosRetirados;
    private final PeriodoLectivoBD prd;
    private ArrayList<PeriodoLectivoMD> periodos;
    private int posPrd;
    private DefaultTableModel mdTblACR;

    public VtnAlumnosRetiradosCTR(VtnPrincipalCTR ctrPrin,
            VtnAlumnosRetirados vtnAR) {
        super(ctrPrin);
        this.vtnAR = vtnAR;
        this.acrb = new AlumnoCursoRetiradoBD(ctrPrin.getConecta());
        this.prd = new PeriodoLectivoBD(ctrPrin.getConecta());
    }

    public void iniciar() {
        iniciarDependencias();
        iniciarTblACR();
        iniciarBuscador();
        iniciarAcciones();
        formatoTbl();
        cargarAnulados();
        ctrPrin.agregarVtn(vtnAR);
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
        vtnAR.getTblAlumnosClase().setModel(mdTblACR);
    }

    private void iniciarAcciones() {
        vtnAR.getCmbPrdLectivos().addActionListener(e -> clickPrd());
        vtnAR.getCbxEliminados().addActionListener(e -> clickCbxEliminados());
    }

    private void clickCbxEliminados() {
        vtnAR.getTxtBuscar().setText("");
        vtnAR.getBtnEliminar().setEnabled(false);
        cargarCmbACR();
    }

    private void cargarEliminados() {
        almsCursosRetirados = acrb.cargarRetiradosEliminados();
        llenarTblAlmRetirado(almsCursosRetirados);
    }

    private void cargarAnulados() {
        almsCursosRetirados = acrb.cargarRetirados();
        llenarTblAlmRetirado(almsCursosRetirados);
    }

    private void iniciarBuscador() {
        vtnAR.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    if (vtnAR.getCbxEliminados().isSelected()) {
                        buscarACRE(vtnAR.getTxtBuscar().getText().trim());
                    } else {
                        buscarACR(vtnAR.getTxtBuscar().getText().trim());
                    }
                } else if (vtnAR.getTxtBuscar().getText().length() == 0) {
                    mdTblACR.setRowCount(0);
                }

            }

        });
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
            if (vtnAR.getCbxEliminados().isSelected()) {
                almsCursosRetirados = acrb.cargarRetiradosPorPrdEliminados(periodos.get(posPrd - 1).getId_PerioLectivo());
                llenarTblAlmRetirado(almsCursosRetirados);
            } else {
                almsCursosRetirados = acrb.cargarRetiradosPorPrd(periodos.get(posPrd - 1).getId_PerioLectivo());
                llenarTblAlmRetirado(almsCursosRetirados);
            }
        }
    }

    private void iniciarTblACR() {
        String[] t = {"Periodo", "Alumno", "Materia", "Fecha", "Observacion"};
        String[][] d = {};
        mdTblACR = TblEstilo.modelTblSinEditar(d, t);
    }

    private void cargarCmbACR() {
        periodos = prd.cargarPrdParaCmbVtn();
        llenarCmbPrd(periodos);
    }

    private void llenarTblAlmRetirado(ArrayList<AlumnoCursoRetiradoMD> almns) {
        vtnAR.getTblAlumnosClase().setModel(mdTblACR);
        mdTblACR.setRowCount(0);
        if (almns != null) {
            almns.forEach(a -> {
                Object[] v = {a.getAlumnoCurso().getCurso().getPeriodo().getNombre_PerLectivo(),
                    a.getAlumnoCurso().getAlumno().getNombreCorto(),
                    a.getAlumnoCurso().getCurso().getMateria().getNombre(),
                    a.getFecha(), a.getObservacion()};
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
