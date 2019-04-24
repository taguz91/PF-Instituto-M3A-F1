package controlador.notas;

import controlador.Libraries.Middlewares;
import java.util.HashMap;
import java.util.Map;

import vista.notas.VtnNotasAlumnoCurso;

public class ReportesCTR {

    private final VtnNotasAlumnoCurso vista;

    private final int idDocente;

    public ReportesCTR(VtnNotasAlumnoCurso vista, int idDocente) {
        this.vista = vista;
        this.idDocente = idDocente;
    }

    public void generarReporteCompleto() {

        String nombrePeriodo = vista.getCmbPeriodoLectivo().getSelectedItem().toString();
        String ciclo = vista.getCmbCiclo().getSelectedItem().toString();
        String materia = vista.getCmbAsignatura().getSelectedItem().toString();

        String path = "./src/vista/notas/reportesPresencial/ReporteCompletoPresencial.jasper";

        Map parametros = new HashMap();

        parametros.put("id_docente", idDocente);
        parametros.put("prd_lectivo_nombre", String.valueOf(nombrePeriodo));
        parametros.put("curso_nombre", ciclo);
        parametros.put("materia_nombre", materia);

        Middlewares.generarReporte(path, "Reporte Completo", parametros);

    }

    public void generarReporteMenos70() {
        String nombrePeriodo = vista.getCmbPeriodoLectivo().getSelectedItem().toString();
        String ciclo = vista.getCmbCiclo().getSelectedItem().toString();
        String materia = vista.getCmbAsignatura().getSelectedItem().toString();

        String path = "./src/vista/notas/reportesPresencial/ReporteMenor70_Presencial.jasper";

        Map parametros = new HashMap();

        parametros.put("id_docente", idDocente);
        parametros.put("prd_lectivo_nombre", String.valueOf(nombrePeriodo));
        parametros.put("curso_nombre", ciclo);
        parametros.put("materia_nombre", materia);

        Middlewares.generarReporte(path, "Reporte Menor 70", parametros);
    }

    public void generarReporteEntre70_80() {

        String nombrePeriodo = vista.getCmbPeriodoLectivo().getSelectedItem().toString();
        String ciclo = vista.getCmbCiclo().getSelectedItem().toString();
        String materia = vista.getCmbAsignatura().getSelectedItem().toString();

        String path = "./src/vista/notas/reportesPresencial/ReporteEntre70y80_Presencial.jasper";

        Map parametros = new HashMap();

        parametros.put("id_docente", idDocente);
        parametros.put("prd_lectivo_nombre", String.valueOf(nombrePeriodo));
        parametros.put("curso_nombre", ciclo);
        parametros.put("materia_nombre", materia);

        Middlewares.generarReporte(path, "Reporte Entre 70 y 80", parametros);

    }

    public void generarReporteEntre80_90() {

        String nombrePeriodo = vista.getCmbPeriodoLectivo().getSelectedItem().toString();
        String ciclo = vista.getCmbCiclo().getSelectedItem().toString();
        String materia = vista.getCmbAsignatura().getSelectedItem().toString();

        String path = "./src/vista/notas/reportesPresencial/ReporteNotasEntre80y90.jasper";

        Map parametros = new HashMap();

        parametros.put("id_docente", idDocente);
        parametros.put("prd_lectivo_nombre", String.valueOf(nombrePeriodo));
        parametros.put("curso_ciclo", new Integer(ciclo));
        parametros.put("materia_nombre", materia);

        Middlewares.generarReporte(path, "Reporte Entre 80 y 90", parametros);

    }

    public void generarReporteEntre90_100() {

        String nombrePeriodo = vista.getCmbPeriodoLectivo().getSelectedItem().toString();
        String ciclo = vista.getCmbCiclo().getSelectedItem().toString();
        String materia = vista.getCmbAsignatura().getSelectedItem().toString();

        String path = "./src/vista/notas/reportesPresencial/ReporteNotasEntre90y100.jasper";

        Map parametros = new HashMap();

        parametros.put("id_docente", idDocente);
        parametros.put("prd_lectivo_nombre", String.valueOf(nombrePeriodo));
        parametros.put("curso_ciclo", new Integer(ciclo));
        parametros.put("materia_nombre", materia);

        Middlewares.generarReporte(path, "Reporte Entre 90 y 100", parametros);

    }
    
    public void ReportePrueba2(){
         String nombrePeriodo = vista.getCmbPeriodoLectivo().getSelectedItem().toString();
        String ciclo = vista.getCmbCiclo().getSelectedItem().toString();
        String materia = vista.getCmbAsignatura().getSelectedItem().toString();

        String path = "./src/vista/notas/reportesPresencial/Prueba2.jasper";

        Map parametros = new HashMap();

        parametros.put("id_docente", idDocente);
        parametros.put("prd_lectivo_nombre", String.valueOf(nombrePeriodo));
        parametros.put("curso_nombre", ciclo);
        parametros.put("materia_nombre", materia);

        Middlewares.generarReporte(path, "Reporte Prueba 2", parametros);

    }
    


}
