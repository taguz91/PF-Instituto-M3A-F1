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
                + "		lp.id_lugar = \"public\".\"Personas\".id_lugar_residencia \n"
                + "		AND lc.id_lugar = lp.id_lugar \n"
                + "		AND lpr.id_lugar = lc.id_lugar_referencia \n"
                + "		AND lpv.id_lugar = lpr.id_lugar_referencia \n"
                + "		AND lpv.lugar_nivel = 2 \n"
                + "	) AS \"PROVINCIA\",\n"
                + "	'INSTITUTO TECNOLOGICO DEL AZUAY' AS \"NOMBRE INSTITUTO\",\n"
                + "	\"public\".\"Carreras\".carrera_codigo AS \"CODIGO CARRERA\",\n"
                + "	\"public\".\"Carreras\".carrera_nombre AS \"CARRERA\",\n"
                + "	\"public\".\"Personas\".persona_primer_apellido || ' ' || \"public\".\"Personas\".persona_segundo_apellido || ' ' || \"public\".\"Personas\".persona_primer_nombre || ' ' || \"public\".\"Personas\".persona_segundo_nombre AS \"APELLIDOS Y NOMBRES\",\n"
                + "	\"public\".\"Personas\".persona_identificacion AS \"NRO. DE IDENTIFICACIÓN\",\n"
                + "	\"Carreras\".carrera_modalidad AS \"MODALIDAD DE ESTUDIOS\",\n"
                + "	\"public\".\"Cursos\".curso_nombre AS \"CURSO\",\n"
                + "	\"public\".\"Materias\".materia_nombre AS \"ASIGNATURA\",\n"
                + "	per_doc.persona_primer_apellido || ' ' || per_doc.persona_primer_nombre AS \"DOCENTE\" \n"
                + "FROM\n"
                + "	\"public\".\"AlumnoCurso\"\n"
                + "	INNER JOIN \"public\".\"Cursos\" ON \"public\".\"AlumnoCurso\".id_curso = \"public\".\"Cursos\".id_curso\n"
                + "	INNER JOIN \"public\".\"Materias\" ON \"public\".\"Cursos\".id_materia = \"public\".\"Materias\".id_materia\n"
                + "	INNER JOIN \"public\".\"Alumnos\" ON \"public\".\"AlumnoCurso\".id_alumno = \"public\".\"Alumnos\".id_alumno\n"
                + "	INNER JOIN \"public\".\"Personas\" ON \"public\".\"Alumnos\".id_persona = \"public\".\"Personas\".id_persona\n"
                + "	INNER JOIN \"public\".\"PeriodoLectivo\" ON \"public\".\"Cursos\".id_prd_lectivo = \"public\".\"PeriodoLectivo\".id_prd_lectivo\n"
                + "	INNER JOIN \"public\".\"Carreras\" ON \"public\".\"PeriodoLectivo\".id_carrera = \"public\".\"Carreras\".id_carrera\n"
                + "	INNER JOIN \"public\".\"Docentes\" ON \"public\".\"Docentes\".id_docente = \"public\".\"Cursos\".id_docente\n"
                + "	INNER JOIN \"public\".\"Personas\" per_doc ON \"public\".\"Docentes\".id_persona = per_doc.id_persona \n"
                + "WHERE\n"
                + "	\"public\".\"AlumnoCurso\".almn_curso_asistencia ILIKE'%" + estado + "%' \n"
                + "	AND \"public\".\"Cursos\".id_prd_lectivo = " + idPeriodo + " \n"
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
