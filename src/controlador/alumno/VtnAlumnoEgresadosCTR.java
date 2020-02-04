package controlador.alumno;

import controlador.principal.VtnPrincipalCTR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.alumno.Egresado;
import utils.ToExcel;

/**
 *
 * @author gus
 */
public class VtnAlumnoEgresadosCTR extends AVtnAlumnoEgresadoCTR implements IAlumnoEgresadoVTNCTR {

    private static final String[] TITULO = {
        "Carrera", "Periodo",
        "Cédula", "Alumno",
        "Fecha egreso", "Graduado"
    };

    public VtnAlumnoEgresadosCTR(VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
    }

    public void iniciar() {
        iniciarVtn(TITULO, this);
        cargarDatos();
        iniciarBuscador();
        iniciarAcciones();
        iniciarClicks();
        vtn.setTitle("Alumnos Egresados");
        ctrPrin.agregarVtn(vtn);
        vtn.getBtnNotasAlumno().setVisible(true);
        vtn.getBtnNotasPeriodo().setVisible(true);
        vtnCargada = true;
    }

    private void iniciarClicks() {
        vtn.getBtnRepPeriodo().addActionListener(e -> clickReportePorPeriodo());
        vtn.getBtnNotasAlumno().addActionListener(e -> clickReporteNotasAlumno());
        vtn.getBtnNotasPeriodo().addActionListener(e -> clickReporteNotasPeriodo());
    }

    private void cargarDatos() {
        todosEgresados = EBD.getAllEgresados();
        egresados = todosEgresados;
        llenarTbl(egresados);
    }

    private void iniciarBuscador() {
        vtn.getBtnBuscar().addActionListener(e -> buscar(
                vtn.getTxtBuscar().getText().trim()
        ));
        vtn.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                buscar(vtn.getTxtBuscar().getText().trim());
            }
        });
    }

    private void buscar(String aguja) {
        egresados = new ArrayList();
        todosEgresados.forEach(e -> {
            if (e.getAlmnCarrera().getAlumno()
                    .getNombreCompleto().toLowerCase()
                    .contains(aguja.toLowerCase())) {
                egresados.add(e);
            }
        });
        llenarTbl(egresados);
    }

    @Override
    public void llenarTbl(List<Egresado> egresados) {
        mdTbl.setRowCount(0);
        if (egresados != null) {
            egresados.forEach(r -> {
                Object[] valores = {
                    r.getAlmnCarrera().getCarrera().getCodigo(),
                    r.getPeriodo().getNombre(),
                    r.getAlmnCarrera().getAlumno().getIdentificacion(),
                    r.getAlmnCarrera().getAlumno().getPrimerApellido()
                    + " " + r.getAlmnCarrera().getAlumno().getSegundoApellido()
                    + " " + r.getAlmnCarrera().getAlumno().getPrimerNombre()
                    + " " + r.getAlmnCarrera().getAlumno().getSegundoNombre(),
                    r.getFechaEgreso().toString(),
                    r.isGraduado() ? "Si" : "No"
                };
                mdTbl.addRow(valores);
            });
            vtn.getLblResultados().setText(egresados.size() + " Resultados obtenidos.");
        }
    }

    private void clickReportePorPeriodo() {
        int posPeriodo = vtn.getCmbPeriodo().getSelectedIndex();
        if (posPeriodo > 0) {
            String nombre = vtn.getCmbPeriodo().getSelectedItem()
                    .toString().replace(" ", "")
                    .replace("/", "-") + "  Egresados";

            List<List<String>> alumnos = EBD.getReportesEgresadosExcel(
                    periodos.get(posPeriodo - 1).getID() + ""
            );
            List<String> cols = new ArrayList<>();
            cols.add("CÓDIGO DEL IST");
            cols.add("NOMBRE DEL INSTITUTO");
            cols.add("PROVINCIA");
            cols.add("CÓDIGO DE LA CARRERA");
            cols.add("CARRERA");
            cols.add("MODALIDAD DE ESTUDIOS");
            cols.add("TIPO DE IDENTIFICACIÓN");
            cols.add("NRO. DE IDENTIFICACIÓN");
            cols.add("APELLIDOS Y NOMBRES");
            cols.add("NACIONALIDAD");
            cols.add("TRABAJO DE TITULACIÓN FINALIZADO S/N");
            ToExcel excel = new ToExcel();
            excel.exportarExcel(
                    cols,
                    alumnos,
                    nombre
            );
        } else {
            JOptionPane.showMessageDialog(vtn, "No selecciono un periodo lectivo para el reporte.");
        }
    }

    private void clickReporteNotasPeriodo() {
        int posPeriodo = vtn.getCmbPeriodo().getSelectedIndex();
        if (posPeriodo > 0) {
            int s = JOptionPane.showOptionDialog(vtn,
                    "Reporte de notas por periodo\n"
                    + "¿Elegir el tipo de carrera?", "Notas Finales",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{"Tradicional", "Dual",
                        "Cancelar"}, "Tradicional");
            switch (s) {
                case 0:
                    reporteNotasPeriodo(posPeriodo);
                    break;
                case 1:
                    reporteNotasPeriodoDual(posPeriodo);
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(vtn, "No selecciono un periodo lectivo para el reporte.");
        }
    }

    private void clickReporteNotasAlumno() {
        int posEgresado = vtn.getTblEgresados().getSelectedRow();
        if (posEgresado >= 0) {
            int s = JOptionPane.showOptionDialog(vtn,
                    "Reporte de notas por alumno\n"
                    + "¿Elegir el tipo de carrera?", "Notas Finales",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{"Tradicional", "Dual",
                        "Cancelar"}, "Tradicional");
            switch (s) {
                case 0:
                    reporteNotasAlumno(posEgresado);
                    break;
                case 1:
                    reporteNotasAlumnoDual(posEgresado);
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(vtn, "No selecciono un alumno.");
        }
    }

    private void reporteNotasPeriodoDual(int posPeriodo) {

        String nombre = vtn.getCmbPeriodo().getSelectedItem()
                .toString().replace(" ", "")
                .replace("/", "-")
                + " Notas Egresados"
                + periodos.get(posPeriodo - 1).getID();

        List<List<String>> alumnos = EBD.getNotasPromedioPorPeriodoDual(
                periodos.get(posPeriodo - 1).getID()
        );
        List<String> cols = new ArrayList<>();
        cols.add("IDENTIFICACIÓN");
        cols.add("PRIMER NOMBRE");
        cols.add("SEGUNDO NOMBRE");
        cols.add("PRIMER APELLIDO");
        cols.add("SEGUNDO APELLIDO");

        cols.add("FASE TEORICA");
        cols.add("PTI");
        cols.add("FASE PRACTICA");
        ToExcel excel = new ToExcel();
        excel.exportarExcel(
                cols,
                alumnos,
                nombre
        );
    }

    private void reporteNotasPeriodo(int posPeriodo) {

        String nombre = vtn.getCmbPeriodo().getSelectedItem()
                .toString().replace(" ", "")
                .replace("/", "-")
                + " Notas Egresados"
                + periodos.get(posPeriodo - 1).getID();

        List<List<String>> alumnos = EBD.getNotasPromedioPorPeriodo(
                periodos.get(posPeriodo - 1).getID()
        );
        List<String> cols = new ArrayList<>();
        cols.add("IDENTIFICACIÓN");
        cols.add("PRIMER NOMBRE");
        cols.add("SEGUNDO NOMBRE");
        cols.add("PRIMER APELLIDO");
        cols.add("SEGUNDO APELLIDO");

        cols.add("PROMEDIO FINAL");
        ToExcel excel = new ToExcel();
        excel.exportarExcel(
                cols,
                alumnos,
                nombre
        );
    }

    private void reporteNotasAlumnoDual(int posEgresado) {
        String nombre = vtn.getCmbPeriodo().getSelectedItem()
                .toString().replace(" ", "")
                .replace("/", "-") + " Notas Alumno Egresados" + egresados.get(posEgresado).getId();

        List<List<String>> alumnos = EBD.getNotasPromedioPorEstudianteDual(
                egresados.get(posEgresado).getId()
        );
        List<String> cols = new ArrayList<>();
        cols.add("IDENTIFICACIÓN");
        cols.add("PRIMER NOMBRE");
        cols.add("SEGUNDO NOMBRE");
        cols.add("PRIMER APELLIDO");
        cols.add("SEGUNDO APELLIDO");

        cols.add("CICLO");
        cols.add("# MATERIAS");
        cols.add("FASE TEORICA");
        cols.add("FASE PRACTICA");
        cols.add("PTI");
        ToExcel excel = new ToExcel();
        excel.exportarExcel(
                cols,
                alumnos,
                nombre
        );

    }

    private void reporteNotasAlumno(int posEgresado) {
        String nombre = vtn.getCmbPeriodo().getSelectedItem()
                .toString().replace(" ", "")
                .replace("/", "-") + " Notas Alumno Egresados" + egresados.get(posEgresado).getId();

        List<List<String>> alumnos = EBD.getNotasPromedioPorEstudiante(
                egresados.get(posEgresado).getId()
        );
        List<String> cols = new ArrayList<>();
        cols.add("IDENTIFICACIÓN");
        cols.add("PRIMER NOMBRE");
        cols.add("SEGUNDO NOMBRE");
        cols.add("PRIMER APELLIDO");
        cols.add("SEGUNDO APELLIDO");

        cols.add("CICLO");
        cols.add("# MATERIAS");
        cols.add("PROMEDIO FINAL");
        ToExcel excel = new ToExcel();
        excel.exportarExcel(
                cols,
                alumnos,
                nombre
        );
    }

}
