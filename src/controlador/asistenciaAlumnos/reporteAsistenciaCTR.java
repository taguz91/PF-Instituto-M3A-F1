package controlador.asistenciaAlumnos;

import controlador.Libraries.Middlewares;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import modelo.CONS;
import vista.asistenciaAlumnos.FrmAsistencia;

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

        Middlewares.generarReporte(getClass().getResource(path), "Reporte Asistencia", parametros);
    }

    public void generarReporteAsistenciaUBE() {
        String nombrePeriodo = vista.getCmbPeriodoLectivoAsis().getSelectedItem().toString();
        String ciclo = vista.getCmbCicloAsis().getSelectedItem().toString();
        String materia = vista.getCmbAsignaturaAsis().getSelectedItem().toString();

        String path = "/vista/asistenciaAlumnos/reporteAsistencia/reporteAsistenciaUBE.jasper";

        Map parametros = new HashMap();

        parametros.put("id_docente", idDocente);
        parametros.put("prd_lectivo_nombre", String.valueOf(nombrePeriodo));
        parametros.put("curso_nombre", ciclo);
        parametros.put("materia_nombre", materia);

        Middlewares.generarReporte(getClass().getResource(path), "Reporte Asistencia UBE", parametros);
    }
    
        public void generarReporteAsistenciaPorDia() {
        String nombrePeriodo = vista.getCmbPeriodoLectivoAsis().getSelectedItem().toString();
        String ciclo = vista.getCmbCicloAsis().getSelectedItem().toString();
        String materia = vista.getCmbAsignaturaAsis().getSelectedItem().toString();
        int fecha = CONS.getDia(vista.getCmbDiaClase().getSelectedItem().toString().split(" | ")[2]);

        String path = "/vista/asistenciaAlumnos/reporteAsistencia/reporteAsistenciaPorDia.jasper";

        Map parametros = new HashMap();

        parametros.put("id_docente", idDocente);
        parametros.put("prd_lectivo_nombre", String.valueOf(nombrePeriodo));
        parametros.put("curso_nombre", ciclo);
        parametros.put("materia_nombre", materia);
        parametros.put("fecha_asistencia", fecha);

        Middlewares.generarReporte(getClass().getResource(path), "Reporte Asistencia por Día", parametros);
    }
    

}
