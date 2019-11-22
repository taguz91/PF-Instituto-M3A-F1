package controlador.alumno;

import controlador.principal.VtnPrincipalCTR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import modelo.alumno.Egresado;

/**
 *
 * @author gus
 */
public class VtnAlumnoGraduadoCTR extends AVtnAlumnoEgresadoCTR implements IAlumnoEgresadoVTNCTR {

    private static final String[] TITULO = {
        "Carrera", "Periodo",
        "Cédula", "Alumno",
        "Fecha Graduacion"
    };

    private final JDEgresarAlumnoCTR ECTR;

    public VtnAlumnoGraduadoCTR(VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
        this.ECTR = new JDEgresarAlumnoCTR(ctrPrin);
    }

    public void iniciar() {
        iniciarVtn(TITULO, this);
        cargarDatos();
        iniciarBuscador();
        iniciarAcciones();
        vtn.setTitle("Alumnos Graduados");
        ctrPrin.agregarVtn(vtn);
        vtnCargada = true;
    }

    private void cargarDatos() {
        todosEgresados = EBD.getAllGraduados();
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
                    r.getFechaGraduacion().toString()
                };
                mdTbl.addRow(valores);
            });
            vtn.getLblResultados().setText(egresados.size() + " Resultados obtenidos.");
        }
    }

}
