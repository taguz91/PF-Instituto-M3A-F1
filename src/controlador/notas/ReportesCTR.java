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
        String paralelo = vista.getCmbParalelo().getSelectedItem().toString();
        String nombreJornada = vista.getCmbJornada().getSelectedItem().toString();
        String materia = vista.getCmbAsignatura().getSelectedItem().toString();
        String QUERY = "SELECT\n"
                + "	\"Alumnos\".id_alumno,\n"
                + "	p_alu.persona_identificacion,\n"
                + "	p_alu.persona_primer_apellido || ' ' ||p_alu.persona_segundo_apellido as \"APELLIDOS\",\n"
                + "	p_alu.persona_primer_nombre || ' ' || p_alu.persona_segundo_nombre AS \"NOMBRES\",\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nt_1_parcial, 1) AS \"APORTE 1\",\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_interciclo, 1) AS \"INTERCICLO\",\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nt_2_parcial, 1) AS \"APORTE 2\",\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_final, 1) AS \"EXAMEN FINAL\",\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_supletorio, 1) AS \"SUPLETORIO\",\n"
                + "	\"AlumnoCurso\".almn_curso_asistencia,\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nota_final, 0) AS \"NOTA FINAL\",\n"
                + "	\"AlumnoCurso\".almn_curso_estado,\n"
                + "	\"AlumnoCurso\".almn_curso_num_faltas,\n"
                + "	\"Materias\".materia_nombre, \n"
                + "	\"Jornadas\".nombre_jornada,\n"
                + "	\"Materias\".materia_ciclo,\n"
                + "	\"Cursos\".curso_paralelo,\n"
                + "	\"Carreras\".carrera_nombre,\n"
                + "	\"PeriodoLectivo\".prd_lectivo_nombre,\n"
                + "	\"Personas\".persona_primer_nombre || ' ' ||\"Personas\".persona_segundo_nombre as \"NOMBRE_PROF\",\n"
                + "	\"Personas\".persona_primer_apellido || ' ' ||\"Personas\".persona_segundo_apellido as \"APELLIDO_PROF\",\n"
                + "	(\"AlumnoCurso\".almn_curso_num_faltas * 100)/\"Materias\".materia_total_horas as \"% Faltas\",\n"
                + "  per_coor.persona_primer_apellido || ' ' || per_coor.persona_segundo_apellido as \"APELLIDOS_COORDINADOR\",\n"
                + "  per_coor.persona_primer_nombre || ' ' ||per_coor.persona_segundo_nombre AS \"NOMBRES_COORDINADOR\"\n"
                + "	\n"
                + "FROM\n"
                + "	\"AlumnoCurso\"\n"
                + "	INNER JOIN \"Cursos\" ON \"AlumnoCurso\".id_curso = \"Cursos\".id_curso\n"
                + "	INNER JOIN \"Jornadas\" ON \"Cursos\".id_jornada = \"Jornadas\".id_jornada\n"
                + "	INNER JOIN \"Materias\" ON \"Cursos\".id_materia = \"Materias\".id_materia\n"
                + "	INNER JOIN \"PeriodoLectivo\" ON \"Cursos\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo\n"
                + "	INNER JOIN \"Alumnos\" ON \"AlumnoCurso\".id_alumno = \"Alumnos\".id_alumno\n"
                + "	INNER JOIN \"Personas\" p_alu ON \"Alumnos\".id_persona = p_alu.id_persona\n"
                + "	INNER JOIN \"Docentes\" ON \"public\".\"Cursos\".id_docente = \"public\".\"Docentes\".id_docente\n"
                + "	INNER JOIN \"Carreras\" ON \"public\".\"Carreras\".id_carrera = \"public\".\"Materias\".id_carrera\n"
                + "	INNER JOIN \"Personas\" ON \"public\".\"Personas\".id_persona= \"public\".\"Docentes\".id_persona\n"
                + "  INNER JOIN \"Docentes\" doc_coor ON doc_coor.id_docente = \"public\".\"Carreras\".id_docente_coordinador\n"
                + "  INNER JOIN \"Personas\" per_coor ON per_coor.id_persona = doc_coor.id_persona"
                + "	WHERE\n"
                + "	\"PeriodoLectivo\".prd_lectivo_estado = TRUE \n"
                + "	AND \"Cursos\".id_docente = " + idDocente + "\n"
                + "	AND \"PeriodoLectivo\".prd_lectivo_nombre = '" + nombrePeriodo + "' \n"
                + "	AND \"Cursos\".curso_ciclo = " + ciclo + " \n"
                + "	AND \"Cursos\".curso_paralelo = '" + paralelo + "' \n"
                + "	AND \"Jornadas\".nombre_jornada = '" + nombreJornada + "' \n"
                + "ORDER BY\n"
                + "	p_alu.persona_primer_apellido ASC;";

        String path = "./src/vista/notas/Reportes/ReporteCompleto.jasper";

        Map<String, Object> parametros = new HashMap();

        parametros.put("id_docente", idDocente);
        parametros.put("prd_lectivo_nombre", String.valueOf(nombrePeriodo));
        parametros.put("nombre_jornada", String.valueOf(nombreJornada));
        parametros.put("curso_ciclo", new Integer(ciclo));
        parametros.put("curso_paralelo", String.valueOf(paralelo));

        Middlewares.generarReporte(path, "Reporte Completo", parametros);

    }

    public void generarReporteMenos70() {
        String nombrePeriodo = vista.getCmbPeriodoLectivo().getSelectedItem().toString();
        String ciclo = vista.getCmbCiclo().getSelectedItem().toString();
        String paralelo = vista.getCmbParalelo().getSelectedItem().toString();
        String nombreJornada = vista.getCmbJornada().getSelectedItem().toString();

        String QUERY = "SELECT\n"
                + "	\"Alumnos\".id_alumno,\n"
                + "	p_alu.persona_identificacion,\n"
                + "	p_alu.persona_primer_apellido || ' ' ||p_alu.persona_segundo_apellido as \"APELLIDOS\",\n"
                + "	p_alu.persona_primer_nombre || ' ' || p_alu.persona_segundo_nombre AS \"NOMBRES\",\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nt_1_parcial, 1) AS \"APORTE 1\",\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_interciclo, 1) AS \"INTERCICLO\",\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nt_2_parcial, 1) AS \"APORTE 2\",\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_final, 1) AS \"EXAMEN FINAL\",\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_supletorio, 1) AS \"SUPLETORIO\",\n"
                + "	\"AlumnoCurso\".almn_curso_asistencia,\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nota_final, 0) AS \"NOTA FINAL\",\n"
                + "	\"AlumnoCurso\".almn_curso_estado,\n"
                + "	\"AlumnoCurso\".almn_curso_num_faltas,\n"
                + "	\"Materias\".materia_nombre, \n"
                + "	\"Jornadas\".nombre_jornada,\n"
                + "	\"Materias\".materia_ciclo,\n"
                + "	\"Cursos\".curso_paralelo,\n"
                + "	\"Carreras\".carrera_nombre,\n"
                + "	\"PeriodoLectivo\".prd_lectivo_nombre,\n"
                + "	\"Personas\".persona_primer_nombre || ' ' ||\"Personas\".persona_segundo_nombre as \"NOMBRE_PROF\",\n"
                + "	\"Personas\".persona_primer_apellido || ' ' ||\"Personas\".persona_segundo_apellido as \"APELLIDO_PROF\",\n"
                + "	(\"AlumnoCurso\".almn_curso_num_faltas * \"Materias\".materia_total_horas)/100 as \"% Faltas\",\n"
                + "  per_coor.persona_primer_apellido || ' ' || per_coor.persona_segundo_apellido as \"APELLIDOS_COORDINADOR\",\n"
                + "  per_coor.persona_primer_nombre || ' ' ||per_coor.persona_segundo_nombre AS \"NOMBRES_COORDINADOR\"\n"
                + "	\n"
                + "FROM\n"
                + "	\"AlumnoCurso\"\n"
                + "	INNER JOIN \"Cursos\" ON \"AlumnoCurso\".id_curso = \"Cursos\".id_curso\n"
                + "	INNER JOIN \"Jornadas\" ON \"Cursos\".id_jornada = \"Jornadas\".id_jornada\n"
                + "	INNER JOIN \"Materias\" ON \"Cursos\".id_materia = \"Materias\".id_materia\n"
                + "	INNER JOIN \"PeriodoLectivo\" ON \"Cursos\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo\n"
                + "	INNER JOIN \"Alumnos\" ON \"AlumnoCurso\".id_alumno = \"Alumnos\".id_alumno\n"
                + "	INNER JOIN \"Personas\" p_alu ON \"Alumnos\".id_persona = p_alu.id_persona\n"
                + "	INNER JOIN \"Docentes\" ON \"public\".\"Cursos\".id_docente = \"public\".\"Docentes\".id_docente\n"
                + "	INNER JOIN \"Carreras\" ON \"public\".\"Carreras\".id_carrera = \"public\".\"Materias\".id_carrera\n"
                + "	INNER JOIN \"Personas\" ON \"public\".\"Personas\".id_persona= \"public\".\"Docentes\".id_persona\n"
                + "  INNER JOIN \"Docentes\" doc_coor ON doc_coor.id_docente = \"public\".\"Carreras\".id_docente_coordinador\n"
                + "  INNER JOIN \"Personas\" per_coor ON per_coor.id_persona = doc_coor.id_persona\n"
                + "	\n"
                + "\n"
                + "	WHERE\n"
                + "	\"PeriodoLectivo\".prd_lectivo_estado = TRUE \n"
                + "	AND \"Cursos\".id_docente = " + idDocente + "\n"
                + "	AND \"PeriodoLectivo\".prd_lectivo_nombre = '" + nombrePeriodo + "' \n"
                + "	AND \"Cursos\".curso_ciclo = " + ciclo + " \n"
                + "	AND \"Cursos\".curso_paralelo = '" + paralelo + "' \n"
                + "	AND \"Jornadas\".nombre_jornada = '" + nombreJornada + "' \n"
                + "	AND \"AlumnoCurso\".almn_curso_nota_final < 70\n"
                + "ORDER BY\n"
                + "	p_alu.persona_primer_apellido ASC;";
        System.out.println(QUERY);

        String path = "./src/vista/notas/Reportes/ReporteNotasMenor70.jasper";

        Middlewares.generarReporteDefault(path, QUERY, "Reporte Menor de 70");
    }

    public void generarReporteEntre70_80() {

        String nombrePeriodo = vista.getCmbPeriodoLectivo().getSelectedItem().toString();
        String ciclo = vista.getCmbCiclo().getSelectedItem().toString();
        String paralelo = vista.getCmbParalelo().getSelectedItem().toString();
        String nombreJornada = vista.getCmbJornada().getSelectedItem().toString();

        String QUERY = "SELECT\n"
                + "	\"Alumnos\".id_alumno,\n"
                + "	p_alu.persona_identificacion,\n"
                + "	p_alu.persona_primer_apellido || ' ' ||p_alu.persona_segundo_apellido as \"APELLIDOS\",\n"
                + "	p_alu.persona_primer_nombre || ' ' || p_alu.persona_segundo_nombre AS \"NOMBRES\",\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nt_1_parcial, 1) AS \"APORTE 1\",\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_interciclo, 1) AS \"INTERCICLO\",\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nt_2_parcial, 1) AS \"APORTE 2\",\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_final, 1) AS \"EXAMEN FINAL\",\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_supletorio, 1) AS \"SUPLETORIO\",\n"
                + "	\"AlumnoCurso\".almn_curso_asistencia,\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nota_final, 0) AS \"NOTA FINAL\",\n"
                + "	\"AlumnoCurso\".almn_curso_estado,\n"
                + "	\"AlumnoCurso\".almn_curso_num_faltas,\n"
                + "	\"Materias\".materia_nombre, \n"
                + "	\"Jornadas\".nombre_jornada,\n"
                + "	\"Materias\".materia_ciclo,\n"
                + "	\"Cursos\".curso_paralelo,\n"
                + "	\"Carreras\".carrera_nombre,\n"
                + "	\"PeriodoLectivo\".prd_lectivo_nombre,\n"
                + "	\"Personas\".persona_primer_nombre || ' ' ||\"Personas\".persona_segundo_nombre as \"NOMBRE_PROF\",\n"
                + "	\"Personas\".persona_primer_apellido || ' ' ||\"Personas\".persona_segundo_apellido as \"APELLIDO_PROF\",\n"
                + "	(\"AlumnoCurso\".almn_curso_num_faltas * \"Materias\".materia_total_horas)/100 as \"% Faltas\",\n"
                + "  per_coor.persona_primer_apellido || ' ' || per_coor.persona_segundo_apellido as \"APELLIDOS_COORDINADOR\",\n"
                + "  per_coor.persona_primer_nombre || ' ' ||per_coor.persona_segundo_nombre AS \"NOMBRES_COORDINADOR\"\n"
                + "	\n"
                + "FROM\n"
                + "	\"AlumnoCurso\"\n"
                + "	INNER JOIN \"Cursos\" ON \"AlumnoCurso\".id_curso = \"Cursos\".id_curso\n"
                + "	INNER JOIN \"Jornadas\" ON \"Cursos\".id_jornada = \"Jornadas\".id_jornada\n"
                + "	INNER JOIN \"Materias\" ON \"Cursos\".id_materia = \"Materias\".id_materia\n"
                + "	INNER JOIN \"PeriodoLectivo\" ON \"Cursos\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo\n"
                + "	INNER JOIN \"Alumnos\" ON \"AlumnoCurso\".id_alumno = \"Alumnos\".id_alumno\n"
                + "	INNER JOIN \"Personas\" p_alu ON \"Alumnos\".id_persona = p_alu.id_persona\n"
                + "	INNER JOIN \"Docentes\" ON \"public\".\"Cursos\".id_docente = \"public\".\"Docentes\".id_docente\n"
                + "	INNER JOIN \"Carreras\" ON \"public\".\"Carreras\".id_carrera = \"public\".\"Materias\".id_carrera\n"
                + "	INNER JOIN \"Personas\" ON \"public\".\"Personas\".id_persona= \"public\".\"Docentes\".id_persona\n"
                + "  INNER JOIN \"Docentes\" doc_coor ON doc_coor.id_docente = \"public\".\"Carreras\".id_docente_coordinador\n"
                + "  INNER JOIN \"Personas\" per_coor ON per_coor.id_persona = doc_coor.id_persona\n"
                + "	\n"
                + "\n"
                + "	WHERE\n"
                + "	\"PeriodoLectivo\".prd_lectivo_estado = TRUE \n"
                + "	AND \"Cursos\".id_docente = " + idDocente + "\n"
                + "	AND \"PeriodoLectivo\".prd_lectivo_nombre = '" + nombrePeriodo + "' \n"
                + "	AND \"Cursos\".curso_ciclo = " + ciclo + " \n"
                + "	AND \"Cursos\".curso_paralelo = '" + paralelo + "' \n"
                + "	AND \"Jornadas\".nombre_jornada = '" + nombreJornada + "' \n"
                + "	AND \"AlumnoCurso\".almn_curso_nota_final >=70 \n"
                + "	AND \"AlumnoCurso\".almn_curso_nota_final <81 \n"
                + "ORDER BY\n"
                + "	p_alu.persona_primer_apellido ASC;";

        System.out.println(QUERY);
        String path = "./src/vista/notas/Reportes/ReporteNotasEntre70y80.jasper";

        Middlewares.generarReporteDefault(path, QUERY, "Reporte Entre 70 y 80");
    }

    public void generarReporteEntre80_90() {

        String nombrePeriodo = vista.getCmbPeriodoLectivo().getSelectedItem().toString();
        String ciclo = vista.getCmbCiclo().getSelectedItem().toString();
        String paralelo = vista.getCmbParalelo().getSelectedItem().toString();
        String nombreJornada = vista.getCmbJornada().getSelectedItem().toString();

        String QUERY = "SELECT\n"
                + "	\"Alumnos\".id_alumno,\n"
                + "	p_alu.persona_identificacion,\n"
                + "	p_alu.persona_primer_apellido || ' ' ||p_alu.persona_segundo_apellido as \"APELLIDOS\",\n"
                + "	p_alu.persona_primer_nombre || ' ' || p_alu.persona_segundo_nombre AS \"NOMBRES\",\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nt_1_parcial, 1) AS \"APORTE 1\",\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_interciclo, 1) AS \"INTERCICLO\",\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nt_2_parcial, 1) AS \"APORTE 2\",\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_final, 1) AS \"EXAMEN FINAL\",\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_supletorio, 1) AS \"SUPLETORIO\",\n"
                + "	\"AlumnoCurso\".almn_curso_asistencia,\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nota_final, 0) AS \"NOTA FINAL\",\n"
                + "	\"AlumnoCurso\".almn_curso_estado,\n"
                + "	\"AlumnoCurso\".almn_curso_num_faltas,\n"
                + "	\"Materias\".materia_nombre, \n"
                + "	\"Jornadas\".nombre_jornada,\n"
                + "	\"Materias\".materia_ciclo,\n"
                + "	\"Cursos\".curso_paralelo,\n"
                + "	\"Carreras\".carrera_nombre,\n"
                + "	\"PeriodoLectivo\".prd_lectivo_nombre,\n"
                + "	\"Personas\".persona_primer_nombre || ' ' ||\"Personas\".persona_segundo_nombre as \"NOMBRE_PROF\",\n"
                + "	\"Personas\".persona_primer_apellido || ' ' ||\"Personas\".persona_segundo_apellido as \"APELLIDO_PROF\",\n"
                + "	(\"AlumnoCurso\".almn_curso_num_faltas * \"Materias\".materia_total_horas)/100 as \"% Faltas\",\n"
                + "  per_coor.persona_primer_apellido || ' ' || per_coor.persona_segundo_apellido as \"APELLIDOS_COORDINADOR\",\n"
                + "  per_coor.persona_primer_nombre || ' ' ||per_coor.persona_segundo_nombre AS \"NOMBRES_COORDINADOR\"\n"
                + "	\n"
                + "FROM\n"
                + "	\"AlumnoCurso\"\n"
                + "	INNER JOIN \"Cursos\" ON \"AlumnoCurso\".id_curso = \"Cursos\".id_curso\n"
                + "	INNER JOIN \"Jornadas\" ON \"Cursos\".id_jornada = \"Jornadas\".id_jornada\n"
                + "	INNER JOIN \"Materias\" ON \"Cursos\".id_materia = \"Materias\".id_materia\n"
                + "	INNER JOIN \"PeriodoLectivo\" ON \"Cursos\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo\n"
                + "	INNER JOIN \"Alumnos\" ON \"AlumnoCurso\".id_alumno = \"Alumnos\".id_alumno\n"
                + "	INNER JOIN \"Personas\" p_alu ON \"Alumnos\".id_persona = p_alu.id_persona\n"
                + "	INNER JOIN \"Docentes\" ON \"public\".\"Cursos\".id_docente = \"public\".\"Docentes\".id_docente\n"
                + "	INNER JOIN \"Carreras\" ON \"public\".\"Carreras\".id_carrera = \"public\".\"Materias\".id_carrera\n"
                + "	INNER JOIN \"Personas\" ON \"public\".\"Personas\".id_persona= \"public\".\"Docentes\".id_persona\n"
                + "  INNER JOIN \"Docentes\" doc_coor ON doc_coor.id_docente = \"public\".\"Carreras\".id_docente_coordinador\n"
                + "  INNER JOIN \"Personas\" per_coor ON per_coor.id_persona = doc_coor.id_persona\n"
                + "	\n"
                + "\n"
                + "	WHERE\n"
                + "	\"PeriodoLectivo\".prd_lectivo_estado = TRUE \n"
                + "	AND \"Cursos\".id_docente = " + idDocente + "\n"
                + "	AND \"PeriodoLectivo\".prd_lectivo_nombre = '" + nombrePeriodo + "' \n"
                + "	AND \"Cursos\".curso_ciclo = " + ciclo + " \n"
                + "	AND \"Cursos\".curso_paralelo = '" + paralelo + "' \n"
                + "	AND \"Jornadas\".nombre_jornada = '" + nombreJornada + "' \n"
                + "	AND \"AlumnoCurso\".almn_curso_nota_final >=80 \n"
                + "	AND \"AlumnoCurso\".almn_curso_nota_final <91 \n"
                + "ORDER BY\n"
                + "	p_alu.persona_primer_apellido ASC;";

        System.out.println(QUERY);
        String path = "./src/vista/notas/Reportes/ReporteNotasEntre80y90.jasper";

        Middlewares.generarReporteDefault(path, QUERY, "Reporte Entre 80 y 90");
    }

    public void generarReporteEntre90_100() {

        String nombrePeriodo = vista.getCmbPeriodoLectivo().getSelectedItem().toString();
        String ciclo = vista.getCmbCiclo().getSelectedItem().toString();
        String paralelo = vista.getCmbParalelo().getSelectedItem().toString();
        String nombreJornada = vista.getCmbJornada().getSelectedItem().toString();

        String QUERY = "SELECT\n"
                + "	\"Alumnos\".id_alumno,\n"
                + "	p_alu.persona_identificacion,\n"
                + "	p_alu.persona_primer_apellido || ' ' ||p_alu.persona_segundo_apellido as \"APELLIDOS\",\n"
                + "	p_alu.persona_primer_nombre || ' ' || p_alu.persona_segundo_nombre AS \"NOMBRES\",\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nt_1_parcial, 1) AS \"APORTE 1\",\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_interciclo, 1) AS \"INTERCICLO\",\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nt_2_parcial, 1) AS \"APORTE 2\",\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_final, 1) AS \"EXAMEN FINAL\",\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_supletorio, 1) AS \"SUPLETORIO\",\n"
                + "	\"AlumnoCurso\".almn_curso_asistencia,\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nota_final, 0) AS \"NOTA FINAL\",\n"
                + "	\"AlumnoCurso\".almn_curso_estado,\n"
                + "	\"AlumnoCurso\".almn_curso_num_faltas,\n"
                + "	\"Materias\".materia_nombre, \n"
                + "	\"Jornadas\".nombre_jornada,\n"
                + "	\"Materias\".materia_ciclo,\n"
                + "	\"Cursos\".curso_paralelo,\n"
                + "	\"Carreras\".carrera_nombre,\n"
                + "	\"PeriodoLectivo\".prd_lectivo_nombre,\n"
                + "	\"Personas\".persona_primer_nombre || ' ' ||\"Personas\".persona_segundo_nombre as \"NOMBRE_PROF\",\n"
                + "	\"Personas\".persona_primer_apellido || ' ' ||\"Personas\".persona_segundo_apellido as \"APELLIDO_PROF\",\n"
                + "	(\"AlumnoCurso\".almn_curso_num_faltas * \"Materias\".materia_total_horas)/100 as \"% Faltas\",\n"
                + "  per_coor.persona_primer_apellido || ' ' || per_coor.persona_segundo_apellido as \"APELLIDOS_COORDINADOR\",\n"
                + "  per_coor.persona_primer_nombre || ' ' ||per_coor.persona_segundo_nombre AS \"NOMBRES_COORDINADOR\"\n"
                + "	\n"
                + "FROM\n"
                + "	\"AlumnoCurso\"\n"
                + "	INNER JOIN \"Cursos\" ON \"AlumnoCurso\".id_curso = \"Cursos\".id_curso\n"
                + "	INNER JOIN \"Jornadas\" ON \"Cursos\".id_jornada = \"Jornadas\".id_jornada\n"
                + "	INNER JOIN \"Materias\" ON \"Cursos\".id_materia = \"Materias\".id_materia\n"
                + "	INNER JOIN \"PeriodoLectivo\" ON \"Cursos\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo\n"
                + "	INNER JOIN \"Alumnos\" ON \"AlumnoCurso\".id_alumno = \"Alumnos\".id_alumno\n"
                + "	INNER JOIN \"Personas\" p_alu ON \"Alumnos\".id_persona = p_alu.id_persona\n"
                + "	INNER JOIN \"Docentes\" ON \"public\".\"Cursos\".id_docente = \"public\".\"Docentes\".id_docente\n"
                + "	INNER JOIN \"Carreras\" ON \"public\".\"Carreras\".id_carrera = \"public\".\"Materias\".id_carrera\n"
                + "	INNER JOIN \"Personas\" ON \"public\".\"Personas\".id_persona= \"public\".\"Docentes\".id_persona\n"
                + "  INNER JOIN \"Docentes\" doc_coor ON doc_coor.id_docente = \"public\".\"Carreras\".id_docente_coordinador\n"
                + "  INNER JOIN \"Personas\" per_coor ON per_coor.id_persona = doc_coor.id_persona\n"
                + "	\n"
                + "\n"
                + "	WHERE\n"
                + "	\"PeriodoLectivo\".prd_lectivo_estado = TRUE \n"
                + "	AND \"Cursos\".id_docente = " + idDocente + "\n"
                + "	AND \"PeriodoLectivo\".prd_lectivo_nombre = '" + nombrePeriodo + "' \n"
                + "	AND \"Cursos\".curso_ciclo = " + ciclo + " \n"
                + "	AND \"Cursos\".curso_paralelo = '" + paralelo + "' \n"
                + "	AND \"Jornadas\".nombre_jornada = '" + nombreJornada + "' \n"
                + "	AND \"AlumnoCurso\".almn_curso_nota_final >=90 \n"
                + "	AND \"AlumnoCurso\".almn_curso_nota_final <101\n"
                + "ORDER BY\n"
                + "	p_alu.persona_primer_apellido ASC;";

        System.out.println(QUERY);
        String path = "./src/vista/notas/Reportes/ReporteNotasEntre90y100.jasper";

        Middlewares.generarReporteDefault(path, QUERY, "Reporte Entre 90 y 100");

    }

    /*
    {
        String QUERY = "SELECT\n"
                + "	\"Alumnos\".id_alumno,\n"
                + "	p_alu.persona_identificacion,\n"
                + "	p_alu.persona_primer_apellido || ' ' ||p_alu.persona_segundo_apellido as \"APELLIDOS\",\n"
                + "	p_alu.persona_primer_nombre || ' ' || p_alu.persona_segundo_nombre AS \"NOMBRES\",\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nt_1_parcial, 1) AS \"APORTE 1\",\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_interciclo, 1) AS \"INTERCICLO\",\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nt_2_parcial, 1) AS \"APORTE 2\",\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_final, 1) AS \"EXAMEN FINAL\",\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nt_examen_supletorio, 1) AS \"SUPLETORIO\",\n"
                + "	\"AlumnoCurso\".almn_curso_asistencia,\n"
                + "	ROUND(\"AlumnoCurso\".almn_curso_nota_final, 0) AS \"NOTA FINAL\",\n"
                + "	\"AlumnoCurso\".almn_curso_estado,\n"
                + "	\"AlumnoCurso\".almn_curso_num_faltas,\n"
                + "	\"Materias\".materia_nombre, \n"
                + "	\"Jornadas\".nombre_jornada,\n"
                + "	\"Materias\".materia_ciclo,\n"
                + "	\"Cursos\".curso_paralelo,\n"
                + "	\"Carreras\".carrera_nombre,\n"
                + "	\"PeriodoLectivo\".prd_lectivo_nombre,\n"
                + "	\"Personas\".persona_primer_nombre || ' ' ||\"Personas\".persona_segundo_nombre as \"NOMBRE_PROF\",\n"
                + "	\"Personas\".persona_primer_apellido || ' ' ||\"Personas\".persona_segundo_apellido as \"APELLIDO_PROF\",\n"
                + "	(\"AlumnoCurso\".almn_curso_num_faltas * 100)/\"Materias\".materia_total_horas as \"% Faltas\",\n"
                + "  per_coor.persona_primer_apellido || ' ' || per_coor.persona_segundo_apellido as \"APELLIDOS_COORDINADOR\",\n"
                + "  per_coor.persona_primer_nombre || ' ' ||per_coor.persona_segundo_nombre AS \"NOMBRES_COORDINADOR\"\n"
                + "	\n"
                + "FROM\n"
                + "	\"AlumnoCurso\"\n"
                + "	INNER JOIN \"Cursos\" ON \"AlumnoCurso\".id_curso = \"Cursos\".id_curso\n"
                + "	INNER JOIN \"Jornadas\" ON \"Cursos\".id_jornada = \"Jornadas\".id_jornada\n"
                + "	INNER JOIN \"Materias\" ON \"Cursos\".id_materia = \"Materias\".id_materia\n"
                + "	INNER JOIN \"PeriodoLectivo\" ON \"Cursos\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo\n"
                + "	INNER JOIN \"Alumnos\" ON \"AlumnoCurso\".id_alumno = \"Alumnos\".id_alumno\n"
                + "	INNER JOIN \"Personas\" p_alu ON \"Alumnos\".id_persona = p_alu.id_persona\n"
                + "	INNER JOIN \"Docentes\" ON \"public\".\"Cursos\".id_docente = \"public\".\"Docentes\".id_docente\n"
                + "	INNER JOIN \"Carreras\" ON \"public\".\"Carreras\".id_carrera = \"public\".\"Materias\".id_carrera\n"
                + "	INNER JOIN \"Personas\" ON \"public\".\"Personas\".id_persona= \"public\".\"Docentes\".id_persona\n"
                + "  INNER JOIN \"Docentes\" doc_coor ON doc_coor.id_docente = \"public\".\"Carreras\".id_docente_coordinador\n"
                + "  INNER JOIN \"Personas\" per_coor ON per_coor.id_persona = doc_coor.id_persona"
                + "	WHERE\n"
                + "	\"PeriodoLectivo\".prd_lectivo_estado = TRUE \n"
                + "	AND \"Cursos\".id_docente = " + idDocente + "\n"
                + "	AND \"PeriodoLectivo\".prd_lectivo_nombre = '" + nombrePeriodo + "' \n"
                + "	AND \"Cursos\".curso_ciclo = " + ciclo + " \n"
                + "	AND \"Cursos\".curso_paralelo = '" + paralelo + "' \n"
                + "	AND \"Jornadas\".nombre_jornada = '" + nombreJornada + "' \n"
                + "ORDER BY\n"
                + "	p_alu.persona_primer_apellido ASC;";

        String QUERY_PROMEDIO = "SELECT\n"
                + "ROUND(Avg(\"public\".\"AlumnoCurso\".almn_curso_nota_final),0) AS \"PROMEDIO CURSO\"\n"
                + "FROM\n"
                + "\"public\".\"AlumnoCurso\"\n"
                + "INNER JOIN \"public\".\"Cursos\" ON \"public\".\"AlumnoCurso\".id_curso = \"public\".\"Cursos\".id_curso AND ''= ''\n"
                + "INNER JOIN \"public\".\"PeriodoLectivo\" ON \"public\".\"Cursos\".id_prd_lectivo = \"public\".\"PeriodoLectivo\".id_prd_lectivo\n"
                + "INNER JOIN \"public\".\"Jornadas\" ON \"public\".\"Cursos\".id_jornada = \"public\".\"Jornadas\".id_jornada\n"
                + "WHERE\n"
                + "\"public\".\"Cursos\".id_docente = " + idDocente + " AND\n"
                + "\"public\".\"PeriodoLectivo\".prd_lectivo_nombre = '" + nombrePeriodo + "' AND\n"
                + "\"public\".\"Cursos\".curso_ciclo = " + ciclo + " AND\n"
                + "\"public\".\"Cursos\".curso_paralelo = '" + paralelo + "' AND\n"
                + "\"Jornadas\".nombre_jornada = '" + nombreJornada + "'";
    }
     */
}
