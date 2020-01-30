package modelo.alumno;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.materia.MateriaMD;
import modelo.persona.AlumnoMD;
import utils.CONBD;
import utils.M;

/**
 *
 * @author Johnny
 */
public class MallaAlumnoBD extends CONBD {

    private String sql = "";

    private static MallaAlumnoBD MABD;

    public static MallaAlumnoBD single() {
        if (MABD == null) {
            MABD = new MallaAlumnoBD();
        }
        return MABD;
    }

    public void ingresarNota(int idMalla, int numMatri, double nota) {
        String estado = "R";
        String nsql = "";
        if (nota >= 70) {
            estado = "C";
        }
        switch (numMatri) {
            case 1:
                nsql = "UPDATE public.\"MallaAlumno\"\n"
                        + "SET malla_almn_nota1=" + nota + ", malla_almn_estado='" + estado + "'\n"
                        + "WHERE id_malla_alumno=" + idMalla + ";";
                break;
            case 2:
                nsql = "UPDATE public.\"MallaAlumno\"\n"
                        + "SET malla_almn_nota2=" + nota + ", malla_almn_estado='" + estado + "'\n"
                        + "WHERE id_malla_alumno=" + idMalla + ";";
                break;
            case 3:
                nsql = "UPDATE public.\"MallaAlumno\"\n"
                        + "SET malla_almn_nota3=" + nota + ", malla_almn_estado='" + estado + "'\n"
                        + "WHERE id_malla_alumno=" + idMalla + ";";
                break;
            default:
                break;
        }
        CON.executeNoSQL(nsql);
    }

    public boolean actualizarNota(
            int idMalla,
            double nota1,
            double nota2,
            double nota3,
            int numMatri,
            String estado
    ) {
        String nsql = "UPDATE public.\"MallaAlumno\"\n"
                + "SET  malla_almn_nota1=" + nota1 + ", malla_almn_nota2=" + nota2 + ", "
                + "malla_almn_nota3=" + nota3 + ", malla_almn_estado='" + estado + "', "
                + "malla_almn_num_matricula = " + numMatri + " \n"
                + "WHERE id_malla_alumno=" + idMalla + ";";
        if (CON.executeNoSQL(nsql)) {
            JOptionPane.showMessageDialog(null, "Se actualizo la malla correctamente.");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo actualizar malla, \n"
                    + "compruebe su conexion a internet.");
            return false;
        }
    }

    public ArrayList<MallaAlumnoMD> cargarMallasTbl() {
        sql = "SELECT id_malla_alumno, ma.id_materia, ma.id_almn_carrera, malla_almn_ciclo, \n"
                + "malla_almn_num_matricula, malla_almn_nota1, malla_almn_nota2, malla_almn_nota3, \n"
                + "malla_almn_estado, persona_primer_nombre, persona_segundo_nombre, "
                + "persona_segundo_apellido, persona_primer_apellido,\n"
                + "materia_nombre, persona_identificacion\n"
                + "FROM public.\"MallaAlumno\" ma, public.\"AlumnosCarrera\" ac, public.\"Alumnos\" a,\n"
                + "public.\"Personas\" p, public.\"Materias\" m, public.\"Carreras\" c\n"
                + "WHERE ac.id_almn_carrera = ma.id_almn_carrera AND\n"
                + "a.id_alumno = ac.id_alumno AND \n"
                + "c.id_carrera = ac.id_carrera AND \n"
                + "p.id_persona = a.id_persona AND\n"
                + "m.id_materia = ma.id_materia AND carrera_activo = true \n"
                + "ORDER BY persona_primer_apellido, persona_segundo_apellido, malla_almn_ciclo;";
        return consultaMallasTbl(sql);
    }

    public ArrayList<MallaAlumnoMD> cargarMallasPorEstudiante(int idAlumnoCarrera) {
        sql = "SELECT id_malla_alumno, ma.id_materia, ma.id_almn_carrera, malla_almn_ciclo, \n"
                + "malla_almn_num_matricula, malla_almn_nota1, malla_almn_nota2, malla_almn_nota3, \n"
                + "malla_almn_estado, persona_primer_nombre, persona_segundo_nombre, "
                + "persona_segundo_apellido, persona_primer_apellido,\n"
                + "materia_nombre, persona_identificacion\n"
                + "FROM public.\"MallaAlumno\" ma, public.\"AlumnosCarrera\" ac, public.\"Alumnos\" a,\n"
                + "public.\"Personas\" p, public.\"Materias\" m\n"
                + "WHERE ac.id_almn_carrera = ma.id_almn_carrera AND\n"
                + "a.id_alumno = ac.id_alumno AND \n"
                + "p.id_persona = a.id_persona AND\n"
                + "m.id_materia = ma.id_materia AND\n"
                + "ac.id_almn_carrera = " + idAlumnoCarrera + " \n"
                + "ORDER BY malla_almn_ciclo;";
        return consultaMallasTbl(sql);
    }

    public ArrayList<MallaAlumnoMD> cargarMallaAlumnoPorEstado(int idAlumno, String estado) {
        sql = "SELECT id_malla_alumno, ma.id_materia, ma.id_almn_carrera, malla_almn_ciclo, \n"
                + "malla_almn_num_matricula, malla_almn_nota1, malla_almn_nota2, malla_almn_nota3, \n"
                + "malla_almn_estado, persona_primer_nombre, persona_segundo_nombre, "
                + "persona_segundo_apellido, persona_primer_apellido,\n"
                + "materia_nombre, persona_identificacion\n"
                + "FROM public.\"MallaAlumno\" ma, public.\"AlumnosCarrera\" ac, public.\"Alumnos\" a,\n"
                + "public.\"Personas\" p, public.\"Materias\" m\n"
                + "WHERE ac.id_almn_carrera = ma.id_almn_carrera AND\n"
                + "a.id_alumno = ac.id_alumno AND \n"
                + "p.id_persona = a.id_persona AND\n"
                + "m.id_materia = ma.id_materia AND\n"
                + "ac.id_almn_carrera = " + idAlumno + "\n"
                + "AND malla_almn_estado = '" + estado.charAt(0) + "' \n"
                + "ORDER BY malla_almn_ciclo;";
        return consultaMallasTbl(sql);
    }

    public ArrayList<MallaAlumnoMD> cargarMallaAlumnoPorCiclo(int idAlumno, int ciclo) {
        sql = "SELECT id_malla_alumno, ma.id_materia, ma.id_almn_carrera, malla_almn_ciclo, \n"
                + "malla_almn_num_matricula, malla_almn_nota1, malla_almn_nota2, malla_almn_nota3, \n"
                + "malla_almn_estado, persona_primer_nombre, persona_segundo_nombre, "
                + "persona_segundo_apellido, persona_primer_apellido,\n"
                + "materia_nombre, persona_identificacion\n"
                + "FROM public.\"MallaAlumno\" ma, public.\"AlumnosCarrera\" ac, public.\"Alumnos\" a,\n"
                + "public.\"Personas\" p, public.\"Materias\" m\n"
                + "WHERE ac.id_almn_carrera = ma.id_almn_carrera AND\n"
                + "a.id_alumno = ac.id_alumno AND \n"
                + "p.id_persona = a.id_persona AND\n"
                + "m.id_materia = ma.id_materia AND\n"
                + "ac.id_almn_carrera = " + idAlumno + " AND\n"
                + "malla_almn_ciclo =" + ciclo + " \n"
                + "ORDER BY malla_almn_ciclo;";
        return consultaMallasTbl(sql);
    }

    /**
     * Cargamos la malla de un alumno por estado y ciclo
     *
     * @param idAlumno
     * @param ciclo
     * @param estado
     * @return
     */
    public ArrayList<MallaAlumnoMD> cargarMallaAlumnoPorEstadoCiclo(int idAlumno, int ciclo, String estado) {
        sql = "SELECT id_malla_alumno, ma.id_materia, ma.id_almn_carrera, malla_almn_ciclo, \n"
                + "malla_almn_num_matricula, malla_almn_nota1, malla_almn_nota2, malla_almn_nota3, \n"
                + "malla_almn_estado, persona_primer_nombre, persona_segundo_nombre, "
                + "persona_segundo_apellido, persona_primer_apellido,\n"
                + "materia_nombre, persona_identificacion\n"
                + "FROM public.\"MallaAlumno\" ma, public.\"AlumnosCarrera\" ac, public.\"Alumnos\" a,\n"
                + "public.\"Personas\" p, public.\"Materias\" m\n"
                + "WHERE ac.id_almn_carrera = ma.id_almn_carrera AND\n"
                + "a.id_alumno = ac.id_alumno AND \n"
                + "p.id_persona = a.id_persona AND\n"
                + "m.id_materia = ma.id_materia AND\n"
                + "ac.id_almn_carrera = " + idAlumno + "\n"
                + "AND malla_almn_estado = '" + estado.charAt(0) + "' AND \n"
                + "malla_almn_ciclo =" + ciclo + " \n"
                + "ORDER BY malla_almn_ciclo;";
        return consultaMallasTbl(sql);
    }

    public ArrayList<MallaAlumnoMD> cargarMallaPorCarrera(int idCarrera) {
        sql = "SELECT id_malla_alumno, ma.id_materia, ma.id_almn_carrera, malla_almn_ciclo, \n"
                + "malla_almn_num_matricula, malla_almn_nota1, malla_almn_nota2, malla_almn_nota3, \n"
                + "malla_almn_estado, persona_primer_nombre, persona_segundo_nombre, "
                + "persona_segundo_apellido, persona_primer_apellido,\n"
                + "materia_nombre, persona_identificacion\n"
                + "FROM public.\"MallaAlumno\" ma, public.\"AlumnosCarrera\" ac, public.\"Alumnos\" a,\n"
                + "public.\"Personas\" p, public.\"Materias\" m\n"
                + "WHERE ac.id_almn_carrera = ma.id_almn_carrera AND\n"
                + "a.id_alumno = ac.id_alumno AND \n"
                + "p.id_persona = a.id_persona AND\n"
                + "m.id_materia = ma.id_materia AND\n"
                + "ac.id_carrera = " + idCarrera + " \n"
                + "ORDER BY persona_primer_apellido, persona_segundo_apellido, malla_almn_ciclo;";
        return consultaMallasTbl(sql);
    }

    /**
     * Buscamos la malla de alumnos por carrera y ciclo
     *
     * @param idCarrera
     * @param ciclo
     * @return
     */
    public ArrayList<MallaAlumnoMD> cargarMallaPorCarreraCiclo(int idCarrera, int ciclo) {
        sql = "SELECT id_malla_alumno, ma.id_materia, ma.id_almn_carrera, malla_almn_ciclo, \n"
                + "malla_almn_num_matricula, malla_almn_nota1, malla_almn_nota2, malla_almn_nota3, \n"
                + "malla_almn_estado, persona_primer_nombre, persona_segundo_nombre, "
                + "persona_segundo_apellido, persona_primer_apellido,\n"
                + "materia_nombre, persona_identificacion\n"
                + "FROM public.\"MallaAlumno\" ma, public.\"AlumnosCarrera\" ac, public.\"Alumnos\" a,\n"
                + "public.\"Personas\" p, public.\"Materias\" m\n"
                + "WHERE ac.id_almn_carrera = ma.id_almn_carrera AND\n"
                + "a.id_alumno = ac.id_alumno AND \n"
                + "p.id_persona = a.id_persona AND\n"
                + "m.id_materia = ma.id_materia AND\n"
                + "ac.id_carrera = " + idCarrera + " AND\n"
                + "malla_almn_ciclo = " + ciclo + " \n"
                + "ORDER BY persona_primer_apellido, persona_segundo_apellido;";
        return consultaMallasTbl(sql);
    }

    /**
     * Cargamos la mallad e una carrera por estado.
     *
     * @param idCarrera
     * @param estado
     * @return
     */
    public ArrayList<MallaAlumnoMD> cargarMallaPorCarreraEstado(int idCarrera, String estado) {
        sql = "SELECT id_malla_alumno, ma.id_materia, ma.id_almn_carrera, malla_almn_ciclo, \n"
                + "malla_almn_num_matricula, malla_almn_nota1, malla_almn_nota2, malla_almn_nota3, \n"
                + "malla_almn_estado, persona_primer_nombre, persona_segundo_nombre, "
                + "persona_segundo_apellido, persona_primer_apellido,\n"
                + "materia_nombre, persona_identificacion\n"
                + "FROM public.\"MallaAlumno\" ma, public.\"AlumnosCarrera\" ac, public.\"Alumnos\" a,\n"
                + "public.\"Personas\" p, public.\"Materias\" m\n"
                + "WHERE ac.id_almn_carrera = ma.id_almn_carrera AND\n"
                + "a.id_alumno = ac.id_alumno AND \n"
                + "p.id_persona = a.id_persona AND\n"
                + "m.id_materia = ma.id_materia AND\n"
                + "ac.id_carrera = " + idCarrera + " AND\n"
                + "malla_almn_estado = '" + estado.charAt(0) + "'" + " \n"
                + "ORDER BY persona_primer_apellido, persona_segundo_apellido, malla_almn_ciclo;";
        return consultaMallasTbl(sql);
    }

    public ArrayList<MallaAlumnoMD> cargarMallaPorCarreraCicloEstado(int idCarrera, int ciclo, String estado) {
        sql = "SELECT id_malla_alumno, ma.id_materia, ma.id_almn_carrera, malla_almn_ciclo, \n"
                + "malla_almn_num_matricula, malla_almn_nota1, malla_almn_nota2, malla_almn_nota3, \n"
                + "malla_almn_estado, persona_primer_nombre, persona_segundo_nombre, "
                + "persona_segundo_apellido, persona_primer_apellido,\n"
                + "materia_nombre, persona_identificacion\n"
                + "FROM public.\"MallaAlumno\" ma, public.\"AlumnosCarrera\" ac, public.\"Alumnos\" a,\n"
                + "public.\"Personas\" p, public.\"Materias\" m\n"
                + "WHERE ac.id_almn_carrera = ma.id_almn_carrera AND\n"
                + "a.id_alumno = ac.id_alumno AND \n"
                + "p.id_persona = a.id_persona AND\n"
                + "m.id_materia = ma.id_materia AND\n"
                + "ac.id_carrera = " + idCarrera + " AND\n"
                + "malla_almn_ciclo = " + ciclo + " AND\n"
                + "malla_almn_estado = '" + estado.charAt(0) + "'" + " \n"
                + "ORDER BY persona_primer_apellido, persona_segundo_apellido;";
        return consultaMallasTbl(sql);
    }

    public MallaAlumnoMD buscarMateriaEstado(int idAlumnoCarrera, int idMateria) {
        String sqlc = "SELECT id_malla_alumno, "
                + "id_materia, "
                + "malla_almn_estado "
                + "FROM public.\"MallaAlumno\" "
                + "WHERE id_almn_carrera = " + idAlumnoCarrera + " "
                + "AND id_materia = " + idMateria + ";";
        MallaAlumnoMD mll = new MallaAlumnoMD();
        PreparedStatement ps = CON.getPSPOOL(sqlc);
        try {
            ResultSet rs = ps.executeQuery();
            AlumnoCarreraMD a = new AlumnoCarreraMD();
            while (rs.next()) {
                a.setId(idAlumnoCarrera);
                mll.setId(rs.getInt("id_malla_alumno"));
                MateriaMD m = new MateriaMD();
                m.setId(rs.getInt("id_materia"));
                mll.setEstado(rs.getString("malla_almn_estado"));
                mll.setMateria(m);
                mll.setAlumnoCarrera(a);
            }
        } catch (SQLException e) {
            M.errorMsg("Error al buscar materia estado. " + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return mll;
    }

    /**
     * Buscamos las materias de un alumno filtradas por estado.
     *
     * @param idAlumnoCarrera
     * @param estado
     * @return
     */
    public ArrayList<MallaAlumnoMD> buscarMateriasAlumnoPorEstado(int idAlumnoCarrera, String estado) {
        sql = "SELECT id_malla_alumno, ma.id_materia, ma.id_almn_carrera, malla_almn_ciclo, \n"
                + "malla_almn_num_matricula, \n"
                + "materia_nombre, malla_almn_estado \n"
                + "FROM public.\"MallaAlumno\" ma, public.\"AlumnosCarrera\" ac, public.\"Materias\" m\n"
                + "WHERE ac.id_almn_carrera = ma.id_almn_carrera AND\n"
                + "m.id_materia = ma.id_materia AND\n"
                + "ac.id_almn_carrera = " + idAlumnoCarrera + "\n"
                + "AND malla_almn_estado = '" + estado.charAt(0) + "';";
        return consultarMallaParaEstado();
    }

    public ArrayList<MallaAlumnoMD> buscarMallaAlumnoParaEstado(int idAlumnoCarrera) {
        sql = "SELECT "
                + "id_malla_alumno, "
                + "ma.id_materia, "
                + "ma.id_almn_carrera, "
                + "malla_almn_ciclo, "
                + "malla_almn_num_matricula, "
                + "materia_nombre, "
                + "malla_almn_estado "
                + "FROM public.\"MallaAlumno\" ma, "
                + "public.\"AlumnosCarrera\" ac, "
                + "public.\"Materias\" m "
                + "WHERE ac.id_almn_carrera = ma.id_almn_carrera "
                + "AND m.id_materia = ma.id_materia "
                + "AND ac.id_almn_carrera = " + idAlumnoCarrera + " "
                + "ORDER BY malla_almn_ciclo;";
        return consultarMallaParaEstado();
    }

    private ArrayList<MallaAlumnoMD> consultarMallaParaEstado() {
        ArrayList<MallaAlumnoMD> mallas = new ArrayList();
        PreparedStatement ps = CON.getPSPOOL(sql);

        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MallaAlumnoMD mll = new MallaAlumnoMD();
                mll.setId(rs.getInt("id_malla_alumno"));
                MateriaMD m = new MateriaMD();
                m.setId(rs.getInt("id_materia"));
                m.setNombre(rs.getString("materia_nombre"));
                mll.setMateria(m);
                AlumnoCarreraMD a = new AlumnoCarreraMD();
                mll.setAlumnoCarrera(a);
                mll.setMallaCiclo(rs.getInt("malla_almn_ciclo"));
                mll.setMallaNumMatricula(rs.getInt("malla_almn_num_matricula"));
                mll.setEstado(rs.getString("malla_almn_estado"));

                mallas.add(mll);
            }
        } catch (SQLException e) {
            M.errorMsg("No se pudieron cargar mallas. " + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return mallas;
    }

    public ArrayList<MallaAlumnoMD> buscarMallaAlumno(String aguja) {
        sql = "SELECT id_malla_alumno, ma.id_materia, ma.id_almn_carrera, malla_almn_ciclo, \n"
                + "malla_almn_num_matricula, malla_almn_nota1, malla_almn_nota2, malla_almn_nota3, \n"
                + "malla_almn_estado, persona_primer_nombre, persona_segundo_nombre, "
                + "persona_segundo_apellido, persona_primer_apellido,\n"
                + "materia_nombre, persona_identificacion\n"
                + "FROM public.\"MallaAlumno\" ma, public.\"AlumnosCarrera\" ac, public.\"Alumnos\" a,\n"
                + "public.\"Personas\" p, public.\"Materias\" m, public.\"Carreras\" c \n"
                + "WHERE ac.id_almn_carrera = ma.id_almn_carrera AND\n"
                + "a.id_alumno = ac.id_alumno AND \n"
                + "c.id_carrera = ac.id_carrera AND \n"
                + "p.id_persona = a.id_persona AND \n"
                + "m.id_materia = ma.id_materia AND (\n"
                + "	persona_primer_nombre || ' ' || persona_primer_apellido ILIKE '%" + aguja + "%' \n"
                + "	OR persona_identificacion ILIKE '%" + aguja + "%' \n"
                + ") AND persona_activa = true;";
        return consultaMallasTbl(sql);
    }

    private ArrayList<MallaAlumnoMD> consultaMallasTbl(String sql) {
        ArrayList<MallaAlumnoMD> mallas = new ArrayList();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MallaAlumnoMD mll = new MallaAlumnoMD();
                mll.setId(rs.getInt("id_malla_alumno"));
                MateriaMD m = new MateriaMD();
                m.setId(rs.getInt("id_materia"));
                m.setNombre(rs.getString("materia_nombre"));
                mll.setMateria(m);
                AlumnoCarreraMD a = new AlumnoCarreraMD();
                a.setId(rs.getInt("id_almn_carrera"));
                AlumnoMD al = new AlumnoMD();
                al.setIdentificacion(rs.getString("persona_identificacion"));
                al.setPrimerApellido(rs.getString("persona_primer_apellido"));
                al.setPrimerNombre(rs.getString("persona_primer_nombre"));
                al.setSegundoApellido(rs.getString("persona_segundo_apellido"));
                al.setSegundoNombre(rs.getString("persona_segundo_nombre"));
                a.setAlumno(al);
                mll.setAlumnoCarrera(a);
                mll.setMallaCiclo(rs.getInt("malla_almn_ciclo"));
                mll.setMallaNumMatricula(rs.getInt("malla_almn_num_matricula"));
                mll.setNota1(rs.getDouble("malla_almn_nota1"));
                mll.setNota2(rs.getDouble("malla_almn_nota2"));
                mll.setNota3(rs.getDouble("malla_almn_nota3"));
                mll.setEstado(rs.getString("malla_almn_estado"));

                mallas.add(mll);
            }
        } catch (SQLException e) {
            M.errorMsg("No se pudieron cargar mallas. " + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return mallas;
    }

    public String getSql() {
        return sql;
    }

    public List<List<String>> getPorCarrera(int idCarrera) {
        String sqlrep = "SELECT\n"
                + "persona_identificacion,\n"
                + "persona_primer_nombre || ' ' ||\n"
                + "persona_segundo_nombre || ' ' ||\n"
                + "persona_segundo_apellido || ' ' ||\n"
                + "persona_primer_apellido AS \"alumno\",\n"
                + "materia_nombre,\n"
                + "malla_almn_estado,\n"
                + "malla_almn_ciclo,\n"
                + "malla_almn_num_matricula,\n"
                + "malla_almn_nota1,\n"
                + "malla_almn_nota2,\n"
                + "malla_almn_nota3\n"
                + "\n"
                + "FROM public.\"MallaAlumno\" ma,\n"
                + "public.\"AlumnosCarrera\" ac,\n"
                + "public.\"Alumnos\" a,\n"
                + "public.\"Personas\" p,\n"
                + "public.\"Materias\" m\n"
                + "WHERE ac.id_almn_carrera = ma.id_almn_carrera AND\n"
                + "a.id_alumno = ac.id_alumno AND\n"
                + "p.id_persona = a.id_persona AND\n"
                + "m.id_materia = ma.id_materia AND\n"
                + "ac.id_carrera = " + idCarrera + "\n"
                + "ORDER BY\n"
                + "persona_primer_apellido,\n"
                + "persona_segundo_apellido,\n"
                + "malla_almn_ciclo;";
        return getParaReporte(sqlrep);
    }

    public List<List<String>> getPorAlumnoCarrera(int idAlmCarrera) {
        String sqlrep = "SELECT\n"
                + "persona_identificacion,\n"
                + "persona_primer_nombre || ' ' ||\n"
                + "persona_segundo_nombre || ' ' ||\n"
                + "persona_segundo_apellido || ' ' ||\n"
                + "persona_primer_apellido AS \"alumno\",\n"
                + "materia_nombre,\n"
                + "malla_almn_estado,\n"
                + "malla_almn_ciclo,\n"
                + "malla_almn_num_matricula,\n"
                + "malla_almn_nota1,\n"
                + "malla_almn_nota2,\n"
                + "malla_almn_nota3\n"
                + "\n"
                + "FROM public.\"MallaAlumno\" ma,\n"
                + "public.\"AlumnosCarrera\" ac,\n"
                + "public.\"Alumnos\" a,\n"
                + "public.\"Personas\" p,\n"
                + "public.\"Materias\" m\n"
                + "WHERE ac.id_almn_carrera = ma.id_almn_carrera AND\n"
                + "a.id_alumno = ac.id_alumno AND\n"
                + "p.id_persona = a.id_persona AND\n"
                + "m.id_materia = ma.id_materia AND\n"
                + "ac.id_almn_carrera = " + idAlmCarrera + "\n"
                + "ORDER BY\n"
                + "persona_primer_apellido,\n"
                + "persona_segundo_apellido,\n"
                + "malla_almn_ciclo;";
        return getParaReporte(sqlrep);
    }

    private List<List<String>> getParaReporte(String sqlrep) {
        List<List<String>> lista = new ArrayList();

        PreparedStatement ps = CON.getPSPOOL(sqlrep);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                List<String> datos = new ArrayList();
                datos.add(rs.getString(1));
                datos.add(rs.getString(2));
                datos.add(rs.getString(3));
                datos.add(rs.getString(4));

                datos.add(rs.getInt(5) + "");
                datos.add(rs.getInt(6) + "");
                datos.add(rs.getDouble(7) + "");
                datos.add(rs.getDouble(8) + "");
                datos.add(rs.getDouble(9) + "");

                lista.add(datos);
            }
        } catch (SQLException e) {
            M.errorMsg(
                    "No consultamos la malla del alumno por carrera."
                    + e.getMessage()
            );
        } finally {
            CON.cerrarCONPS(ps);
        }
        return lista;
    }

}
