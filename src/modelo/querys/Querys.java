/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.querys;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.ConnDBPool;

/**
 *
 * @author MrRainx
 */
public class Querys {

    private final ConnDBPool pool;
    private Connection conn;

    {
        pool = new ConnDBPool();
    }

    public Querys() {
    }

    public List<Object[]> selectReporteEstado(int idPeriodo, String estado) {

        String SELECT = "SELECT\n"
                + "	(\n"
                + "	SELECT\n"
                + "		lpv.lugar_nombre \n"
                + "	FROM\n"
                + "		PUBLIC.\"Lugares\" lp,\n"
                + "		PUBLIC.\"Lugares\" lc,\n"
                + "		PUBLIC.\"Lugares\" lpr,\n"
                + "		PUBLIC.\"Lugares\" lpv \n"
                + "	WHERE\n"
                + "		lp.id_lugar = \"Personas\".id_lugar_residencia \n"
                + "		AND lc.id_lugar = lp.id_lugar \n"
                + "		AND lpr.id_lugar = lc.id_lugar_referencia \n"
                + "		AND lpv.id_lugar = lpr.id_lugar_referencia \n"
                + "		AND lpv.lugar_nivel = 2 \n"
                + "	) AS \"PROVINCIA\",\n"
                + "	'INSTITUTO TECNOLOGICO DEL AZUAY' AS \"NOMBRE INSTITUTO\",\n"
                + "	\"Carreras\".carrera_codigo AS \"CODIGO CARRERA\",\n"
                + "	\"Carreras\".carrera_nombre AS \"CARRERA\",\n"
                + "	\"Personas\".persona_primer_apellido || ' ' || \"Personas\".persona_segundo_apellido || ' ' || \"Personas\".persona_primer_nombre || ' ' || \"Personas\".persona_segundo_nombre AS \"APELLIDOS Y NOMBRES\",\n"
                + "	\"Personas\".persona_identificacion AS \"NRO. DE IDENTIFICACIÓN\",\n"
                + "	\"Carreras\".carrera_modalidad AS \"MODALIDAD DE ESTUDIOS\",\n"
                + "	\"Cursos\".curso_nombre AS \"CURSO\",\n"
                + "	\"Materias\".materia_nombre AS \"ASIGNATURA\",\n"
                + "	per_doc.persona_primer_apellido || ' ' || per_doc.persona_primer_nombre AS \"DOCENTE\" \n"
                + "FROM\n"
                + "	\"AlumnoCurso\"\n"
                + "	INNER JOIN \"Cursos\" ON \"AlumnoCurso\".id_curso = \"Cursos\".id_curso\n"
                + "	INNER JOIN \"Materias\" ON \"Cursos\".id_materia = \"Materias\".id_materia\n"
                + "	INNER JOIN \"Alumnos\" ON \"AlumnoCurso\".id_alumno = \"Alumnos\".id_alumno\n"
                + "	INNER JOIN \"Personas\" ON \"Alumnos\".id_persona = \"Personas\".id_persona\n"
                + "	INNER JOIN \"PeriodoLectivo\" ON \"Cursos\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo\n"
                + "	INNER JOIN \"Carreras\" ON \"PeriodoLectivo\".id_carrera = \"Carreras\".id_carrera\n"
                + "	INNER JOIN \"Docentes\" ON \"Docentes\".id_docente = \"Cursos\".id_docente\n"
                + "	INNER JOIN \"Personas\" per_doc ON \"Docentes\".id_persona = per_doc.id_persona \n"
                + "WHERE\n"
                + "	\"AlumnoCurso\".almn_curso_asistencia ILIKE'%" + estado + "%' \n"
                + "	AND \"Cursos\".id_prd_lectivo = " + idPeriodo + " \n"
                + "ORDER BY\n"
                + "	\"Personas\".persona_primer_apellido,\n"
                + "	\"Personas\".persona_segundo_apellido";

        List<Object[]> lista = new ArrayList<>();
        conn = pool.getConnection();
        ResultSet rs = pool.ejecutarQuery(SELECT, conn, null);
        try {
            while (rs.next()) {

                Object[] vector = new Object[]{
                    lista.size() + 1,
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8),
                    rs.getString(9),
                    rs.getString(10)
                };
                lista.add(vector);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            pool.closeStmt().close(rs).close(conn);
        }
        return lista;
    }

}
