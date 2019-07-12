package modelo.alumno;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.ConectarDB;
import modelo.carrera.CarreraMD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.AlumnoMD;

/**
 *
 * @author gus
 */
public class AlumnoMatriculaBD {

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

    private final ConectarDB conecta;

    public AlumnoMatriculaBD(ConectarDB conecta) {
        this.conecta = conecta;
    }

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
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        if (rs != null) {
            try {
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

                    p.setNombre_PerLectivo(rs.getString("prd_lectivo_nombre"));
                    p.setCarrera(c);

                    am.setAlumno(a);
                    am.setPeriodo(p);
                    am.setCursos(rs.getString(12));
                    
                    almnsMatri.add(am);
                }
                ps.getConnection().close();
                return almnsMatri;
            } catch (SQLException e) {
                System.out.println("No logramos consultar " + e.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }

}
