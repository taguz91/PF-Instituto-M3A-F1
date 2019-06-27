
package controlador.asistenciaAlumnos;

import controlador.Libraries.Middlewares;
import java.util.HashMap;
import java.util.Map;
import vista.asistenciaAlumnos.FrmAsistencia;
import vista.notas.VtnNotas;

/**
 *
 * @author Yani
 */
public class reporteAsistenciaCTR {
    
    private final FrmAsistencia vista;

    private final int idDocente;

    public reporteAsistenciaCTR(FrmAsistencia vista, int idDocente) {
        this.vista = vista;
        this.idDocente = idDocente;
    }
    
    
     public void generarReporteAsistencia() {
        String nombrePeriodo = vista.getCmbPeriodoLectivoAsis().getSelectedItem().toString();
        String ciclo = vista.getCmbCicloAsis().getSelectedItem().toString();
        String materia = vista.getCmbAsignaturaAsis().getSelectedItem().toString();

        String path = "/vista/asistenciaAlumnos/reporteAsistencia/reporteAsistencia.jasper";

        Map parametros = new HashMap();

        parametros.put("id_docente", idDocente);
        parametros.put("prd_lectivo_nombre", String.valueOf(nombrePeriodo));
        parametros.put("curso_nombre", ciclo);
        parametros.put("materia_nombre", materia);
         System.out.println(parametros);
        Middlewares.generarReporte(getClass().getResource(path), "Reporte Asistencia", parametros);
    }
}
