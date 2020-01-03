package modelo.alumno;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.carrera.CarreraMD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.AlumnoMD;
import utils.CONBD;
import utils.M;

/**
 *
 * @author gus
 */
public class AlumnoMatriculaBD extends CONBD {

    private static AlumnoMatriculaBD AMBD;

    public static AlumnoMatriculaBD single() {
        if (AMBD == null) {
            AMBD = new AlumnoMatriculaBD();
        }
        return AMBD;
    }

    private final String BASESQL = "SELECT\n"
            + "persona_identificacion,\n"
            + "persona_primer_nombre,\n"
            + "persona_segundo_nombre,\n"
            + "persona_primer_apellido,\n"
            + "persona_segundo_apellido,\n"
            + "persona_correo,\n"
            + "persona_celular,\n"
            + "persona_telefono,\n"
            + "carrera_nombre,\n"
            + "carrera_codigo,\n"
            + "prd_lectivo_nombre,\n"
            + "	STRING_AGG(\n"
            + "		c.curso_nombre || '  # ' || ac.almn_curso_num_matricula || ':  ' || materia_nombre, E'\\n'\n"
            + "	) Materias\n"
            + "FROM public.\"Carreras\" cr, public.\"Cursos\" c,\n"
            + "public.\"Alumnos\" a, public.\"Personas\" p,\n"
            + "public.\"Materias\" m, public.\"AlumnoCurso\" ac,\n"
            + "public.\"PeriodoLectivo\" pl\n"
            + "WHERE p.id_persona = a.id_persona\n"
            + "AND a.id_alumno = ac.id_alumno\n"
            + "AND ac.id_curso = c.id_curso\n"
            + "AND m.id_materia = c.id_materia\n"
            + "AND pl.id_prd_lectivo = c.id_prd_lectivo\n"
            + "AND cr.id_carrera = pl.id_carrera\n"
            + "AND ac.almn_curso_activo = true\n";

    private final String FINSQL = "\nGROUP BY persona_identificacion,\n"
            + "persona_primer_nombre,\n"
            + "persona_segundo_nombre,\n"
            + "persona_primer_apellido,\n"
            + "persona_segundo_apellido,\n"
            + "persona_correo,\n"
            + "persona_celular,\n"
            + "persona_telefono,\n"
            + "carrera_nombre,\n"
            + "carrera_codigo,\n"
            + "prd_lectivo_nombre\n"
            + "ORDER BY persona_primer_apellido, persona_segundo_apellido;";

    public ArrayList<AlumnoMatriculaMD> getTodos() {
        String sql = BASESQL + FINSQL;
        return obtenerParaTbl(sql);
    }

    public ArrayList<AlumnoMatriculaMD> getPorPeriodo(int idPeriodo) {
        String sql = BASESQL
                + " AND c.id_prd_lectivo = " + idPeriodo
                + FINSQL;
        return obtenerParaTbl(sql);
    }

    public ArrayList<AlumnoMatriculaMD> buscarPor(String aguja) {
        String sql = BASESQL
                + "AND ("
                + "persona_identificacion ILIKE '%" + aguja + "%' OR "
                + "persona_primer_nombre || persona_primer_apellido ILIKE '%" + aguja + "%' OR "
                + "persona_segundo_nombre || persona_primer_apellido ILIKE '%" + aguja + "%' OR "
                + "carrera_nombre ILIKE '%" + aguja + "%' "
                + ")"
                + FINSQL;
        return obtenerParaTbl(sql);
    }

    public ArrayList<AlumnoMatriculaMD> obtenerParaTbl(String sql) {
        ArrayList<AlumnoMatriculaMD> almnsMatri = new ArrayList();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AlumnoMatriculaMD am = new AlumnoMatriculaMD();
                AlumnoMD a = new AlumnoMD();
                CarreraMD c = new CarreraMD();
                PeriodoLectivoMD p = new PeriodoLectivoMD();

                a.setIdentificacion(rs.getString("persona_identificacion"));
                a.setPrimerNombre(rs.getString("persona_primer_nombre"));
                a.setSegundoNombre(rs.getString("persona_segundo_nombre"));
                a.setPrimerApellido(rs.getString("persona_primer_apellido"));
                a.setSegundoApellido(rs.getString("persona_segundo_apellido"));
                a.setCorreo(rs.getString("persona_correo"));
                a.setCelular(rs.getString("persona_celular"));
                a.setTelefono(rs.getString("persona_telefono"));

                c.setNombre(rs.getString("carrera_nombre"));
                c.setCodigo(rs.getString("carrera_codigo"));

                p.setNombre(rs.getString("prd_lectivo_nombre"));
                p.setCarrera(c);

                am.setAlumno(a);
                am.setPeriodo(p);
                am.setCursos(rs.getString(12));

                almnsMatri.add(am);
            }

        } catch (SQLException e) {
            M.errorMsg("No logramos consultar " + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return almnsMatri;
    }

    public List<List<String>> getNumeroAlumnos(String idPrds) {
        String sql = "SELECT\n"
                + "carrera_codigo,\n"
                + "prd_lectivo_nombre, (\n"
                + "  SELECT COUNT(*)\n"
                + "  FROM public.\"Matricula\" m\n"
                + "  JOIN public.\"Alumnos\" a ON\n"
                + "  a.id_alumno = m.id_alumno\n"
                + "  JOIN public.\"Personas\" p ON\n"
                + "  a.id_persona = p.id_persona\n"
                + "  WHERE persona_activa = true AND\n"
                + "  persona_sexo ILIKE '%H%' AND\n"
                + "  m.id_prd_lectivo = pl.id_prd_lectivo\n"
                + ") AS hombres, (\n"
                + "  SELECT COUNT(*)\n"
                + "  FROM public.\"Matricula\" m\n"
                + "  JOIN public.\"Alumnos\" a ON\n"
                + "  a.id_alumno = m.id_alumno\n"
                + "  JOIN public.\"Personas\" p ON\n"
                + "  a.id_persona = p.id_persona\n"
                + "  WHERE persona_activa = true AND\n"
                + "  persona_sexo ILIKE '%M%' AND\n"
                + "  m.id_prd_lectivo = pl.id_prd_lectivo\n"
                + ") AS mujeres, (\n"
                + "  SELECT COUNT(*)\n"
                + "  FROM public.\"Matricula\" m\n"
                + "  WHERE  m.id_prd_lectivo = pl.id_prd_lectivo\n"
                + ") AS total\n"
                + "FROM public.\"Carreras\" c\n"
                + "JOIN public.\"PeriodoLectivo\" pl ON\n"
                + "pl.id_carrera = c.id_carrera\n"
                + "WHERE pl.id_prd_lectivo IN (\n"
                + idPrds + " ) GROUP BY\n"
                + "pl.id_prd_lectivo,\n"
                + "carrera_codigo,\n"
                + "prd_lectivo_nombre\n"
                + "ORDER BY\n"
                + "prd_lectivo_fecha_inicio DESC;";

        List<List<String>> alms = new ArrayList<>();

        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                List<String> datos = new ArrayList();
                datos.add(rs.getString(1));
                datos.add(rs.getString(2));
                datos.add(rs.getInt(3) + "");
                datos.add(rs.getInt(4) + "");
                datos.add(rs.getInt(5) + "");
                alms.add(datos);
            }
        } catch (SQLException e) {
            M.errorMsg(
                    "No consultamos el numero de alumnos "
                    + "por periodos para el reporte. " + e.getMessage()
            );
        }
        return alms;
    }

    public List<List<String>> getNumeroAlumnosPorJornada(String idPrds) {
        String sql = "SELECT\n"
                + "carrera_codigo,\n"
                + "prd_lectivo_nombre, (\n"
                + "  SELECT\n"
                + "  COUNT(DISTINCT curso_nombre)\n"
                + "  FROM public.\"Cursos\" cr\n"
                + "  WHERE\n"
                + "  cr.id_prd_lectivo = pl.id_prd_lectivo AND\n"
                + "  cr.id_jornada = 1\n"
                + ") AS num_matutina, (\n"
                + "  SELECT\n"
                + "  count(DISTINCT id_alumno)\n"
                + "  FROM public.\"AlumnoCurso\" ac\n"
                + "  WHERE id_curso IN (\n"
                + "    SELECT id_curso\n"
                + "    FROM public.\"Cursos\" cr\n"
                + "    WHERE\n"
                + "    cr.id_prd_lectivo = pl.id_prd_lectivo AND\n"
                + "    cr.id_jornada = 1\n"
                + "  )\n"
                + ") AS alum_matu,(\n"
                + "  SELECT\n"
                + "  COUNT(DISTINCT curso_nombre)\n"
                + "  FROM public.\"Cursos\" cr\n"
                + "  WHERE\n"
                + "  cr.id_prd_lectivo = pl.id_prd_lectivo AND\n"
                + "  cr.id_jornada = 2\n"
                + ") AS num_vespertina, (\n"
                + "  SELECT\n"
                + "  count(DISTINCT id_alumno)\n"
                + "  FROM public.\"AlumnoCurso\" ac\n"
                + "  WHERE id_curso IN (\n"
                + "    SELECT id_curso\n"
                + "    FROM public.\"Cursos\" cr\n"
                + "    WHERE\n"
                + "    cr.id_prd_lectivo = pl.id_prd_lectivo AND\n"
                + "    cr.id_jornada = 2\n"
                + "  )\n"
                + ") AS alum_vesp, (\n"
                + "  SELECT\n"
                + "  COUNT(DISTINCT curso_nombre)\n"
                + "  FROM public.\"Cursos\" cr\n"
                + "  WHERE\n"
                + "  cr.id_prd_lectivo = pl.id_prd_lectivo AND\n"
                + "  cr.id_jornada = 3\n"
                + ") AS num_nocturna, (\n"
                + "  SELECT\n"
                + "  count(DISTINCT id_alumno)\n"
                + "  FROM public.\"AlumnoCurso\" ac\n"
                + "  WHERE id_curso IN (\n"
                + "    SELECT id_curso\n"
                + "    FROM public.\"Cursos\" cr\n"
                + "    WHERE\n"
                + "    cr.id_prd_lectivo = pl.id_prd_lectivo AND\n"
                + "    cr.id_jornada = 3\n"
                + "  )\n"
                + ") AS alum_noct\n"
                + "FROM public.\"Carreras\" c\n"
                + "JOIN public.\"PeriodoLectivo\" pl ON\n"
                + "pl.id_carrera = c.id_carrera\n"
                + "WHERE pl.id_prd_lectivo IN (\n"
                + "\n"
                + ") GROUP BY\n"
                + "pl.id_prd_lectivo,\n"
                + "carrera_codigo,\n"
                + "prd_lectivo_nombre\n"
                + "ORDER BY\n"
                + "prd_lectivo_fecha_inicio DESC;";

        List<List<String>> alms = new ArrayList<>();

        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                List<String> datos = new ArrayList();
                datos.add(rs.getString(1));
                datos.add(rs.getString(2));
                datos.add(rs.getInt(3) + "");
                datos.add(rs.getInt(4) + "");
                datos.add(rs.getInt(5) + "");
                datos.add(rs.getInt(6) + "");
                datos.add(rs.getInt(7) + "");
                datos.add(rs.getInt(8) + "");
                alms.add(datos);
            }
        } catch (SQLException e) {
            M.errorMsg(
                    "No consultamos el numero de alumnos "
                    + "por periodos para el reporte. " + e.getMessage()
            );
        }
        return alms;
    }

}
