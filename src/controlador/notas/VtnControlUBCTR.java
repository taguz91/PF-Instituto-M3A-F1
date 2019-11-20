package controlador.notas;

import controlador.Libraries.Effects;
import controlador.Libraries.Validaciones;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.lang.Thread.sleep;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import utils.CONS;
import modelo.curso.CursoBD;
import modelo.curso.CursoMD;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import modelo.usuario.RolBD;
import modelo.usuario.UsuarioBD;
import vista.notas.VtnControlUB;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Alejandro
 */
public class VtnControlUBCTR {

    private final VtnPrincipal desktop;
    private static VtnControlUB vista;
    private UsuarioBD usuario;
    private static RolBD rolSeleccionado;

    //LISTA
    private static Map<String, DocenteMD> listaDocentes;
    private static List<PeriodoLectivoMD> listaPeriodos;
    private static List<MateriaMD> listaMaterias;

    private String itemCombo;

    //VARIABLES DE BUSQUEDA
    protected static int idDocente;
    protected static int idPeriodoLectivo = -1;
    protected static int idCurso = -1;

    private final PeriodoLectivoBD periodoBD;
    private final CursoBD cursoBD;
    private final MateriaBD materiaBD;
    private final DocenteBD docenteBD;

    {
        periodoBD = new PeriodoLectivoBD();
        cursoBD = new CursoBD();
        materiaBD = new MateriaBD();
        docenteBD = new DocenteBD();
    }

    public VtnControlUBCTR(VtnPrincipal desktop, VtnControlUB vista, UsuarioBD usuario, RolBD rolSeleccionado) {
        this.desktop = desktop;
        this.vista = vista;
        this.usuario = usuario;
        this.rolSeleccionado = rolSeleccionado;
    }
// <editor-fold defaultstate="collapsed" desc="INITS">   

    public void Init() {
        if (rolSeleccionado.getNombre().toLowerCase().contains("docente")) {
            listaDocentes = docenteBD.selectAll(usuario.getUsername());
        } else {
            listaDocentes = docenteBD.selectAll();
        }

        Effects.addInDesktopPane(vista, desktop.getDpnlPrincipal());

        activarForm(false);
        cargarComboDocente();
        cargarComboPeriodos();
        setLblCarrera();
        cargarComboCiclo();
        cargarComboMaterias();
        InitEventos();
        activarForm(true);
        InitPermisos();
    }

    private void InitEventos() {

        vista.getCmbDocente().addActionListener(e -> cargarComboPeriodos());
        vista.getCmbPeriodoLectivo().addActionListener(e -> {
            cargarComboCiclo();
        });
        vista.getCmbPeriodoLectivo().addItemListener(e -> setLblCarrera());

        vista.getCmbCiclo().addActionListener(e -> {
            cargarComboMaterias();
        });

        vista.getBtnReportesUB().addActionListener(e -> btnReportesUB(e));
        vista.getBtnBuscar().addActionListener(e -> btnBuscar());
        vista.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    String texto = vista.getTxtBuscar().getText();
                    if (texto.length() >= 10) {
                        btnBuscar();
                    }
                }
            }
        });

        vista.getTxtBuscar().addKeyListener(Validaciones.validarNumeros());
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Cargado de Combos">
    private void cargarComboDocente() {
        listaDocentes.entrySet().forEach((entry) -> {
            String key = entry.getKey();
            DocenteMD value = entry.getValue();

            vista.getCmbDocente().addItem(key);

        });

    }

    private void cargarComboPeriodos() {
        vista.getCmbPeriodoLectivo().removeAllItems();
        vista.getLblCarrera().setText("");

        listaPeriodos = periodoBD.selectPeriodoWhere(getIdDocente());
        listaPeriodos
                .stream()
                .forEach(obj -> {
                    vista.getCmbPeriodoLectivo().addItem(obj.getNombre());
                });
    }

    private void setLblCarrera() {

        vista.getLblCarrera().setText(listaPeriodos
                .stream()
                .filter(item -> item.getID() == getIdPeriodoLectivo())
                .map(c -> c.getCarrera().getNombre())
                .findFirst()
                .orElse("")
        );

    }

    private void cargarComboCiclo() {
        try {
            vista.getCmbCiclo().removeAllItems();

            cursoBD.selectCicloWhere(getIdDocente(), getIdPeriodoLectivo())
                    .stream()
                    .forEach(obj -> {
                        vista.getCmbCiclo().addItem(obj + "");
                    });
        } catch (NullPointerException e) {
        }
    }

    private void cargarComboMaterias() {
        try {
            vista.getCmbAsignatura().removeAllItems();

            CursoMD curso = new CursoMD();
            DocenteMD docente = new DocenteMD();
            docente.setIdDocente(getIdDocente());
            curso.setDocente(docente);
            PeriodoLectivoMD periodo = new PeriodoLectivoMD();
            periodo.setID(getIdPeriodoLectivo());
            curso.setPeriodo(periodo);
            curso.setNombre(vista.getCmbCiclo().getSelectedItem().toString());

            listaMaterias = materiaBD.selectWhere(curso);

            listaMaterias.stream()
                    .forEach(obj -> {
                        vista.getCmbAsignatura().addItem(obj.getNombre());
                    });

            validarCombos();
        } catch (NullPointerException e) {
            vista.getCmbAsignatura().removeAllItems();
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Metodos de Apoyo">
    private int getIdDocente() {
        return listaDocentes.entrySet().stream()
                .filter((entry) -> (entry.getKey().equals(vista.getCmbDocente().getSelectedItem().toString())))
                .map(c -> c.getValue().getIdDocente()).findAny().get();
    }

    private void validarCombos() {

        if (vista.getCmbAsignatura().getItemCount() > 0) {
            vista.getBtnReportesUB().setEnabled(true);
        } else {
            vista.getBtnReportesUB().setEnabled(false);
        }
    }

//    private static int getIdPeriodoLectivo() {
//        try {
//            String periodo = vista.getCmbPeriodoLectivo().getSelectedItem().toString();
//
//            listaPeriodos
//                    .stream()
//                    .filter(item -> item.getNombre_PerLectivo().equals(periodo))
//                    .collect(Collectors.toList())
//                    .forEach(obj -> {
//                        idPeriodoLectivo = obj.getId_PerioLectivo();
//                    });
//
//        } catch (NullPointerException e) {
//        }
//        return idPeriodoLectivo;
//    }
    private int getIdPeriodoLectivo() {
        try {
            String periodo = vista.getCmbPeriodoLectivo().getSelectedItem().toString();
            return listaPeriodos
                    .stream()
                    .filter(item -> item.getNombre().equals(periodo))
                    .map(c -> c.getID())
                    .findAny()
                    .orElse(-1);
        } catch (NullPointerException e) {
        }
        return -1;
    }

    private static void activarForm(boolean estado) {

        if (rolSeleccionado.getNombre().toLowerCase().contains("docente")) {
            vista.getTxtBuscar().setVisible(false);
            vista.getBtnBuscar().setVisible(false);
            vista.getCmbDocente().setEnabled(false);
        } else {
            vista.getTxtBuscar().setEnabled(estado);
            vista.getBtnBuscar().setEnabled(estado);
            vista.getCmbDocente().setEnabled(estado);
        }

        vista.getCmbPeriodoLectivo().setEnabled(estado);
        vista.getCmbCiclo().setEnabled(estado);
        vista.getCmbAsignatura().setEnabled(estado);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="EVENTOS">
    private void btnBuscar() {
        activarForm(false);

        vista.getCmbDocente()
                .setSelectedItem(listaDocentes
                        .entrySet()
                        .stream()
                        .filter(entry -> entry.getValue().getIdentificacion().equals(vista.getTxtBuscar().getText()))
                        .map(c -> c.getKey())
                        .findFirst()
                        .orElse("")
                );

        activarForm(true);
    }

    private void btnReportesUB(ActionEvent e) {
        System.out.println("Estamos en el evento");

        new Thread(() -> {
            int b = JOptionPane.showOptionDialog(vista,
                    "Reporte de Estado Estudiantil\n"
                    + "¿Elegir el tipo de Reporte?", "REPORTE UB",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{"Estado de Alumnos", "Rendimiento Interciclo",
                        "Rendimiento Fin de Ciclo Menores a 70  "}, "Cancelar");

            Effects.setLoadCursor(vista);

            Reportes_ubCRT reportes = new Reportes_ubCRT(vista, getIdDocente());

            switch (b) {
                case 0:

                    desktop.getLblEstado().setText("CARGANDO REPORTE....");
                    reportes.generarReporteEstadoAlumnos();
                    desktop.getLblEstado().setText("COMPLETADO");
                    break;

                case 1:

                    desktop.getLblEstado().setText("CARGANDO REPORTE....");
                    reportes.generarReporteRendimientoInterciclo();
                    desktop.getLblEstado().setText("COMPLETADO");
                    break;

                case 2:

                    desktop.getLblEstado().setText("CARGANDO REPORTE....");
                    reportes.generarReporteRendimientoCiclo();
                    desktop.getLblEstado().setText("COMPLETADO");
                    break;

                default:
                    break;
            }

            try {
                sleep(5000);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
            desktop.getLblEstado().setText("");
            Effects.setDefaultCursor(vista);
        }).start();

    }

    // </editor-fold>

    private void InitPermisos() {
        vista.getBtnReportesUB().getAccessibleContext().setAccessibleName("Notas-Rendimiento-Academico-Reporte UB");
        CONS.activarBtns(vista.getBtnReportesUB());
    }
}
