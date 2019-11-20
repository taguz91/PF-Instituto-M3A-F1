package controlador.carrera;

import controlador.principal.DVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import utils.CONS;
import modelo.accesos.AccesosMD;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.estilo.TblEstilo;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.validaciones.TxtVBuscador;
import modelo.validaciones.Validar;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import vista.carrera.FrmCarrera;
import vista.carrera.VtnCarrera;

/**
 *
 * @author Johnny
 */
public class VtnCarreraCTR extends DVtnCTR {

    private final VtnCarrera vtnCarrera;
    private final PeriodoLectivoBD prd;

    private final CarreraBD car;
    private ArrayList<CarreraMD> carreras;
    private ArrayList<PeriodoLectivoMD> periodos;

    public VtnCarreraCTR(VtnCarrera vtnCarrera, VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
        this.vtnCarrera = vtnCarrera;
        this.car = new CarreraBD(ctrPrin.getConecta());
        this.prd = new PeriodoLectivoBD(ctrPrin.getConecta());
    }

    /**
     * Iniciamos todas las dependencias de este formulario
     */
    public void iniciar() {
        vtnCarrera.getBtnReporteAlumnoCarrera().setEnabled(false);
        String titutlo[] = {"id", "Codigo", "Nombre", "Fecha Inicio", "Modalidad", "Semanas", "Coordinador"};
        String datos[][] = {};
        mdTbl = TblEstilo.modelTblSinEditar(datos, titutlo);
        vtnCarrera.getTblMaterias().setModel(mdTbl);
        TblEstilo.formatoTbl(vtnCarrera.getTblMaterias());
        TblEstilo.ocualtarID(vtnCarrera.getTblMaterias());
        TblEstilo.columnaMedida(vtnCarrera.getTblMaterias(), 1, 50);
        TblEstilo.columnaMedida(vtnCarrera.getTblMaterias(), 3, 90);
        TblEstilo.columnaMedida(vtnCarrera.getTblMaterias(), 4, 90);
        TblEstilo.columnaMedida(vtnCarrera.getTblMaterias(), 5, 80);

        cargarCarreras();
        //Le damos accion al btn editar
        vtnCarrera.getBtnIngresar().addActionListener(e -> abrirFrmCarrera());
        vtnCarrera.getBtnEditar().addActionListener(e -> editarCarrera());
        vtnCarrera.getBtnEliminar().addActionListener(e -> eliminarCarrera());
        vtnCarrera.getTblMaterias().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                validarBotonesReportes();
            }
        });
        vtnCarrera.getBtnReporteAlumnoCarrera().addActionListener(e -> llamaReporteAlumnoCarrera());
        vtnCarrera.getBtnReporteDocente().addActionListener(e -> botonDocentes());
        vtnCarrera.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String b = vtnCarrera.getTxtBuscar().getText().trim();
                if (e.getKeyCode() == 10) {
                    buscar(b);
                } else if (b.length() == 0) {
                    cargarCarreras();
                }
            }
        });
        vtnCarrera.getBtnBuscar().addActionListener(e -> buscar(vtnCarrera.getTxtBuscar().getText().trim()));
        vtnCarrera.getTxtBuscar().addKeyListener(new TxtVBuscador(vtnCarrera.getTxtBuscar(),
                vtnCarrera.getBtnBuscar()));

        ctrPrin.agregarVtn(vtnCarrera);
        InitPermisos();
    }

    /**
     * Buscador de esta ventana le asignamos todos sus metodos
     *
     * @param b
     */
    private void buscar(String b) {
        if (Validar.esLetrasYNumeros(b)) {
            carreras = car.buscarCarrera(b);
            llenarTbl(carreras);
        } else {
            JOptionPane.showMessageDialog(ctrPrin.getVtnPrin(), "No debe ingresar caracteres especiales.");
        }
    }

    /**
     * Al dar click en editar se abre el formulario de carrera con todos los
     * datos de esta carrera.
     */
    private void editarCarrera() {
        int fila = vtnCarrera.getTblMaterias().getSelectedRow();
        if (fila >= 0) {
            FrmCarrera frmCarrera = new FrmCarrera();
            ctrPrin.eventoInternal(frmCarrera);
            FrmCarreraCTR ctrFrmCarrera = new FrmCarreraCTR(frmCarrera, ctrPrin);
            ctrFrmCarrera.iniciar();
            ctrFrmCarrera.editar(carreras.get(fila));
            ctrPrin.cerradoJIF();
            vtnCarrera.dispose();
        } else {
            JOptionPane.showMessageDialog(ctrPrin.getVtnPrin(), "Debe seleccionar una carrera primero.");
        }
    }

    /**
     * Eliminamos esta carrera , siempre preguntando un mensaje de confirmacion
     */
    private void eliminarCarrera() {
        int fila = vtnCarrera.getTblMaterias().getSelectedRow();
        if (fila >= 0) {
            int r = JOptionPane.showConfirmDialog(ctrPrin.getVtnPrin(), "Seguro que quiere eliminar \n"
                    + vtnCarrera.getTblMaterias().getValueAt(fila, 2).toString() + "\n"
                    + "No se podran recuperar los datos despues.");
            if (r == JOptionPane.OK_OPTION) {
                car.eliminarCarrera(carreras.get(fila).getId());
                cargarCarreras();
            }
        }
    }

    /**
     * Abrimos el formulario de la carrera
     */
    private void abrirFrmCarrera() {
        ctrPrin.abrirFrmCarrera();
        vtnCarrera.dispose();
        ctrPrin.cerradoJIF();
    }

    /**
     * Consultamos todas las carreras, y las cargamos en la tabla
     */
    public void cargarCarreras() {
        carreras = car.cargarCarreras();
        llenarTbl(carreras);
    }

    /**
     * LLenamos todos los datos de las carreras
     *
     * @param carreras
     */
    public void llenarTbl(ArrayList<CarreraMD> carreras) {
        mdTbl.setRowCount(0);
        if (carreras != null) {
            carreras.forEach((c) -> {
                if (c.getCoordinador().getPrimerNombre() == null) {
                    Object valoresSD[] = {c.getId(), c.getCodigo(), c.getNombre(),
                        c.getFechaInicio(), c.getModalidad(), c.getNumSemanas(), "SIN COORDINADOR "};
                    mdTbl.addRow(valoresSD);
                } else {
                    Object valoresCD[] = {c.getId(), c.getCodigo(), c.getNombre(),
                        c.getFechaInicio(), c.getModalidad(), c.getNumSemanas(),
                        c.getCoordinador().getPrimerApellido()
                        + " " + c.getCoordinador().getSegundoApellido() + " "
                        + c.getCoordinador().getPrimerNombre() + " "
                        + c.getCoordinador().getSegundoNombre()};
                    mdTbl.addRow(valoresCD);
                }
            });
            vtnCarrera.getLblResultados().setText(carreras.size() + " Resutados obtendidos.");
        } else {
            vtnCarrera.getLblResultados().setText("0 Resutados obtendidos.");
        }

    }

    /**
     * Llamamos al reporte de listado de alumnos por carrera
     */
    public void llamaReporteAlumnoCarrera() {
        posFila = vtnCarrera.getTblMaterias().getSelectedRow();
        JasperReport jr;
        String path = "/vista/reportes/repAlumnosCarrera.jasper";
        if (posFila >= 0) {
            try {
                Map parametro = new HashMap();
                parametro.put("alumnoCarrera", carreras.get(posFila).getId());
                jr = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
                ctrPrin.getConecta().mostrarReporte(jr, parametro, "Reporte de alumnos por Carrera");
            } catch (JRException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex);
            }
        } else {
            JOptionPane.showMessageDialog(vtnCarrera, "Seleccione una carrera.");
        }

    }

    /**
     * Docentes de esta carrera por periodo lectivo
     */
    public void botonDocentes() {
        int s = JOptionPane.showOptionDialog(vtnCarrera,
                "Reporte de Docentes por periodo LEctivo\n"
                + "¿Elegir el tipo de Reporte?", "REPORTE DOCENTES",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{"Elegir Periodo", "Cancelar"}, "Cancelar");
        switch (s) {
            case 0:
                seleccionarPeriodo();
                break;
            default:
                break;
        }
    }

    /**
     * Seleecionamos un periodo para poder llamar al reporte.
     */
    public void seleccionarPeriodo() {
        periodos = prd.cargarPeriodos();
        ArrayList<String> nmPrd = new ArrayList();
        nmPrd.add("Seleccione");
        periodos.forEach(p -> {
            nmPrd.add(p.getNombre());
        });
        Object np = JOptionPane.showInputDialog(ctrPrin.getVtnPrin(),
                "Lista de periodos lectivos", "Periodos lectivos",
                JOptionPane.QUESTION_MESSAGE, null,
                nmPrd.toArray(), "Seleccione");
        System.out.println("Selecciono " + np);
        //Se es null significa que no selecciono nada
        if (np == null) {
            botonDocentes();
        } else if (np.equals("Seleccione")) {
            JOptionPane.showMessageDialog(ctrPrin.getVtnPrin(), "Debe seleccionar un periodo lectivo.");
            seleccionarPeriodo();
        } else {
            JasperReport jr;
            String path = "/vista/reportes/repDocentesPrdLectivo.jasper";
            try {
                Map parametro = new HashMap();

                parametro.put("idPeriodo", np);
                System.out.println(parametro);
                jr = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
                ctrPrin.getConecta().mostrarReporte(jr, parametro, "Reporte de Materias del Docente por Periodos Lectivos");

            } catch (JRException ex) {
                JOptionPane.showMessageDialog(null, "error" + ex);
            }
        }
    }

    private void InitPermisos() {
        vtnCarrera.getBtnEliminar().getAccessibleContext().setAccessibleName("Carreras-Eliminar");
        vtnCarrera.getBtnEditar().getAccessibleContext().setAccessibleName("Carreras-Editar");
        vtnCarrera.getBtnIngresar().getAccessibleContext().setAccessibleName("Carreras-Ingresar");
        vtnCarrera.getBtnReporteAlumnoCarrera().getAccessibleContext().setAccessibleName("Carreras-Reporte-Alumno");
        vtnCarrera.getBtnReporteDocente().getAccessibleContext().setAccessibleName("Carreras-Reporte-Docente");
        
        CONS.activarBtns(vtnCarrera.getBtnEliminar(), vtnCarrera.getBtnEditar(), vtnCarrera.getBtnIngresar(), 
                vtnCarrera.getBtnReporteAlumnoCarrera(), vtnCarrera.getBtnReporteDocente());
    }

    public void validarBotonesReportes() {
        int selecTabl = vtnCarrera.getTblMaterias().getSelectedRow();
        if (selecTabl >= 0) {
            vtnCarrera.getBtnReporteAlumnoCarrera().setEnabled(true);
        } else {
            vtnCarrera.getBtnReporteAlumnoCarrera().setEnabled(false);
        }
    }
}
