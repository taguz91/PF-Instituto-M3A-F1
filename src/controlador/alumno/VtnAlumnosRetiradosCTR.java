package controlador.alumno;

import controlador.principal.DVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
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

    /**
     *
     * @param ctrPrin
     * @param vtnAR
     */
    public VtnAlumnosRetiradosCTR(VtnPrincipalCTR ctrPrin,
            VtnAlumnosRetirados vtnAR) {
        super(ctrPrin);
        this.vtnAR = vtnAR;
        this.acrb = new AlumnoCursoRetiradoBD(ctrPrin.getConecta());
        this.prd = new PeriodoLectivoBD(ctrPrin.getConecta());
    }

    /**
     * Cargamos todas las dependencias de esta ventana
     */
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

    /**
     * Le damos el formato de esta tabla
     */
    private void formatoTbl() {
        TblEstilo.formatoTbl(vtnAR.getTblAlumnosClase());
        vtnAR.getTblAlumnosClase().setModel(mdTbl);
    }

    /**
     * Iniciamos todas las acciones de la ventana
     */
    private void iniciarAcciones() {
        vtnAR.getCmbPrdLectivos().addActionListener(e -> clickPrd());
        vtnAR.getCbxEliminados().addActionListener(e -> clickCbxEliminados());
    }

    /**
     * Al hacer click en eliminamos Se borra lo que este escrito en el txt
     * buscar Se desactiva el boton eliminar
     */
    private void clickCbxEliminados() {
        vtnAR.getTxtBuscar().setText("");
        vtnAR.getBtnEliminar().setEnabled(false);
        cargarCmbACR();
    }

    /**
     * Cargamos todos los alumnos retirados que eliminamos
     */
    private void cargarEliminados() {
        almsCursosRetirados = acrb.cargarRetiradosEliminados();
        llenarTblAlmRetirado(almsCursosRetirados);
    }

    /**
     * Cargamos todos los alumnos que anularon una matricula
     */
    private void cargarAnulados() {
        almsCursosRetirados = acrb.cargarRetirados();
        llenarTblAlmRetirado(almsCursosRetirados);
    }

    /**
     * Iniciamos el buscador de esta ventana
     */
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
                    mdTbl.setRowCount(0);
                }

            }

        });
    }

    /**
     * Buscamos los alumnos retirados
     *
     * @param b
     */
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

    /**
     * Al hacer click en un periodo se cargan solo los alumnos de ese periodo
     */
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

    /**
     * Iniciamos la tabla con el formato de alumnos con matriculas anuladas
     */
    private void iniciarTblACR() {
        String[] t = {"Periodo", "Alumno", "Materia", "Fecha", "Observacion"};
        String[][] d = {};
        mdTbl = TblEstilo.modelTblSinEditar(d, t);
    }

    /**
     * Cargamos el combo de periodos con todos los periodos
     */
    private void cargarCmbACR() {
        periodos = prd.cargarPrdParaCmbVtn();
        llenarCmbPrd(periodos);
    }

    /**
     * Llenamos la tabla con toda la informacion indicada
     *
     * @param almns
     */
    private void llenarTblAlmRetirado(ArrayList<AlumnoCursoRetiradoMD> almns) {
        vtnAR.getTblAlumnosClase().setModel(mdTbl);
        mdTbl.setRowCount(0);
        if (almns != null) {
            almns.forEach(a -> {
                Object[] v = {a.getAlumnoCurso().getCurso().getPeriodo().getNombre_PerLectivo(),
                    a.getAlumnoCurso().getAlumno().getNombreCorto(),
                    a.getAlumnoCurso().getCurso().getMateria().getNombre(),
                    a.getFecha(), a.getObservacion()};
                mdTbl.addRow(v);
            });
            vtnAR.getLblResultados().setText(almns.size() + " Resultados obtendios.");
        } else {
            mdTbl.setRowCount(0);
        }
    }

    /**
     * LLenamos el combo de periodos
     *
     * @param periodos
     */
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
