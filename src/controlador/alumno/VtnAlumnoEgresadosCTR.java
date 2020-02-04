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
        vtnCargada = true;
    }

    private void iniciarClicks() {
        vtn.getBtnRepPeriodo().addActionListener(e -> clickReportePorPeriodo());
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

}
