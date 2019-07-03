package controlador.vistaReportes;

import controlador.Libraries.Middlewares;
import java.util.HashMap;
import java.util.Map;
import vista.vistaReportes.VtnEstadosR;


/**
 *
 * @author Yani
 */
public class ReporteEstadoAlumCTR {
    private final VtnEstadosR vista;

    private final int idDocente;

    public ReporteEstadoAlumCTR(VtnEstadosR vista, int idDocente) {
        this.vista = vista;
        this.idDocente = idDocente;
    }
    
    public void generarReporteEstadoAlumno(){
        String nombrePeriodo = vista.getCmbPeriodos().getSelectedItem().toString();
        String estado = vista.getCmbEstado().getSelectedItem().toString();

        String path = "/vista/vistaReportes/ReporteEstadoAlum/ReporteEstadoAlumno.jasper";

        Map parametros = new HashMap();

        parametros.put("id_docente", idDocente);
        parametros.put("prd_lectivo_nombre", String.valueOf(nombrePeriodo));
        parametros.put("almn_curso_asistencia", estado);

        Middlewares.generarReporte(getClass().getResource(path), "Reporte Estado Alumno", parametros);
    }
}
