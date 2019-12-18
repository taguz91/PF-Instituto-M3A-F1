package modelo.asistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.alumno.AlumnoCursoMD;
import modelo.curso.CursoMD;
import modelo.materia.MateriaMD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.AlumnoMD;
import utils.CONBD;
import utils.M;

/**
 *
 * @author gus
 */
public class NEWAsistenciaBD extends CONBD {

    private static NEWAsistenciaBD ABD;

    public static NEWAsistenciaBD single() {
        if (ABD == null) {
            ABD = new NEWAsistenciaBD();
        }
        return ABD;
    }

    public List<AsistenciaMD> getAlumnosCursoFicha(
            int idCurso,
            String fecha
    ) {
        String sql = "SELECT\n"
                + "    id_asistencia,\n"
                + "    persona_primer_apellido,\n"
                + "    persona_segundo_apellido,\n"
                + "    persona_primer_nombre,\n"
                + "    persona_segundo_nombre,\n"
                + "    numero_faltas\n"
                + "    FROM public.\"Asistencia\" aa\n"
                + "    JOIN public.\"AlumnoCurso\" ac\n"
                + "    ON ac.id_almn_curso = aa.id_almn_curso\n"
                + "    JOIN public.\"Alumnos\" a\n"
                + "    ON ac.id_alumno = a.id_alumno\n"
                + "    JOIN public.\"Personas\" p\n"
                + "    ON p.id_persona = a.id_persona\n"
                + "    WHERE ac.id_curso = " + idCurso + "\n"
                + "    AND  to_char(fecha_asistencia,'DD/MM/YYYY') ILIKE '%" + fecha + "%'\n"
                + "    ORDER BY persona_primer_apellido, "
                + "    persona_segundo_apellido,"
                + "    persona_primer_nombre, "
                + "    persona_segundo_nombre;";
        List<AsistenciaMD> as = new ArrayList<>();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AsistenciaMD a = new AsistenciaMD();
                a.setId(rs.getInt(1));
                a.setNumeroFaltas(rs.getInt(6));
                AlumnoCursoMD ac = new AlumnoCursoMD();
                AlumnoMD al = new AlumnoMD();
                ac.setAlumno(al);
                a.setAlumnoCurso(ac);
                al.setPrimerApellido(rs.getString(2));
                al.setSegundoApellido(rs.getString(3));
                al.setPrimerNombre(rs.getString(4));
                al.setSegundoNombre(rs.getString(5));
                as.add(a);
            }
        } catch (SQLException e) {
            M.errorMsg("Error al consultar asistencia. " + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return as;
    }

    public boolean iniciarAsistenciaCursoFecha(
            int idCurso,
            String fecha
    ) {
        String sql = "INSERT INTO public.\"Asistencia\"(\n"
                + "    id_almn_curso,\n"
                + "    fecha_asistencia,\n"
                + "    numero_faltas )\n"
                + "    SELECT id_almn_curso,\n"
                + "    TO_DATE('" + fecha + "', 'DD/MM/YYYY'), "
                + "    0 "
                + "    FROM public.\"AlumnoCurso\" "
                + "    WHERE id_curso = " + idCurso + " "
                + "    AND id_almn_curso NOT IN ("
                + "     SELECT id_almn_curso "
                + "     FROM public.\"Asistencia\" "
                + "     WHERE id_curso = " + idCurso + " "
                + "     AND fecha_asistencia = "
                + "     TO_DATE('" + fecha + "', 'DD/MM/YYYY') "
                + "   );";
        return CON.executeNoSQL(sql);
    }

    public boolean actualizarFalta(int idAsistencia, int numFalta) {
        String sql = "UPDATE public.\"Asistencia\" "
                + "    SET numero_faltas = " + numFalta + " "
                + "    WHERE id_asistencia = " + idAsistencia + ";";
        return CON.executeNoSQL(sql);
    }

    public AsistenciaSesionMD getInfoSesion(int idCurso) {
        String sql = "SELECT prd_lectivo_fecha_inicio,\n"
                + "prd_lectivo_fecha_fin, (\n"
                + "  SELECT MIN(dia_sesion)\n"
                + "  FROM public.\"SesionClase\"\n"
                + "  WHERE id_curso = cr.id_curso\n"
                + ") dia_inicia, (\n"
                + "  SELECT  MAX(dia_sesion)\n"
                + "  FROM public.\"SesionClase\"\n"
                + "  WHERE id_curso = cr.id_curso\n"
                + ")AS dia_fin\n"
                + "\n"
                + "FROM public.\"PeriodoLectivo\" plr\n"
                + "JOIN public.\"Cursos\" cr\n"
                + "ON cr.id_prd_lectivo = plr.id_prd_lectivo\n"
                + "WHERE id_curso = " + idCurso + ";";
        AsistenciaSesionMD as = new AsistenciaSesionMD();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                as.setPrdFechaInicio(rs.getDate(1).toLocalDate());
                as.setPrdFechaFin(rs.getDate(2).toLocalDate());
                as.setDiaInicio(rs.getInt(3));
                as.setDiaFin(rs.getInt(4));
            }
        } catch (SQLException e) {
            M.errorMsg("Error al consultar informacion de curso. " + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return as;
    }

    public List<AsistenciaHoras> getDiasCurso(int idCurso) {
        List<AsistenciaHoras> ahs = new ArrayList<>();
        String sql = "SELECT\n"
                + "dia_sesion,\n"
                + "COUNT(dia_sesion) AS num_horas\n"
                + "FROM public.\"SesionClase\"\n"
                + "WHERE id_curso = " + idCurso + "\n"
                + "GROUP BY dia_sesion";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AsistenciaHoras ah = new AsistenciaHoras();
                ah.setDia(rs.getInt(1));
                ah.setHoras(rs.getInt(2));

                ahs.add(ah);
            }
        } catch (SQLException e) {
            M.errorMsg("Error al consultar las horas de un curso. " + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return ahs;
    }

    public List<PeriodoLectivoMD> getPeriodosDocente(String docente) {
        String sql = "SELECT\n"
                + "    pl.id_prd_lectivo,\n"
                + "    prd_lectivo_nombre\n"
                + "    FROM public.\"Cursos\" c\n"
                + "    JOIN public.\"Materias\" m\n"
                + "    ON m.id_materia = c.id_materia\n"
                + "    JOIN public.\"Docentes\" d\n"
                + "    ON d.id_docente = c.id_docente\n"
                + "    JOIN public.\"Personas\" p\n"
                + "    ON p.id_persona = d.id_persona\n"
                + "    JOIN public.\"PeriodoLectivo\" pl\n"
                + "    ON pl.id_prd_lectivo = c.id_prd_lectivo\n"
                + "    WHERE persona_identificacion = '" + docente + "'\n"
                + "    GROUP BY\n"
                + "    pl.id_prd_lectivo,\n"
                + "    prd_lectivo_nombre,\n"
                + "    prd_lectivo_fecha_fin\n"
                + "    ORDER BY prd_lectivo_fecha_fin DESC;";
        List<PeriodoLectivoMD> pls = new ArrayList<>();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PeriodoLectivoMD pl = new PeriodoLectivoMD();
                pl.setID(rs.getInt(1));
                pl.setNombre(rs.getString(2));
                pls.add(pl);
            }
        } catch (SQLException e) {
            M.errorMsg("Error al consultar periodos de docente. " + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return pls;
    }

    public List<CursoMD> getCursosPeriodoDocente(int idPeriodo, String docente) {
        String sql = "SELECT\n"
                + "    c.id_curso,\n"
                + "    materia_nombre,\n"
                + "    curso_nombre \n"
                + "    FROM public.\"SesionClase\" sc\n"
                + "    JOIN public.\"Cursos\" c\n"
                + "    ON sc.id_curso = c.id_curso\n"
                + "    JOIN public.\"Materias\" m\n"
                + "    ON m.id_materia = c.id_materia\n"
                + "    JOIN public.\"Docentes\" d\n"
                + "    ON d.id_docente = c.id_docente\n"
                + "    JOIN public.\"Personas\" p\n"
                + "    ON p.id_persona = d.id_persona\n"
                + "    WHERE persona_identificacion = '" + docente + "' "
                + "    AND c.id_prd_lectivo = " + idPeriodo + "\n"
                + "    GROUP BY\n"
                + "    c.id_curso,\n"
                + "    materia_nombre,\n"
                + "    curso_nombre\n"
                + "    ORDER BY materia_nombre;";
        List<CursoMD> cs = new ArrayList<>();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CursoMD c = new CursoMD();
                c.setId(rs.getInt(1));
                MateriaMD m = new MateriaMD();
                m.setNombre(rs.getString(2));
                c.setNombre(rs.getString(3));
                c.setMateria(m);
                cs.add(c);
            }
        } catch (SQLException e) {
            M.errorMsg("Error al consultar los cursos de un docente. " + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return cs;
    }

}
