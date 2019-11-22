package modelo.alumno;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.ConectarDB;
import modelo.materia.MateriaMD;
import modelo.persona.AlumnoMD;

/**
 *
 * @author Johnny
 */
public class MallaAlumnoBD extends MallaAlumnoMD {

    private final ConectarDB conecta;
    private String sql = "";

    public MallaAlumnoBD(ConectarDB conecta) {
        this.conecta = conecta;
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
        PreparedStatement ps = conecta.getPS(nsql);
        if (conecta.nosql(ps) == null) {
            System.out.println("Se guardao correctamente la nota");
        }
    }

    public boolean actualizarNota(int idMalla, double nota1, double nota2, double nota3, int numMatri, String estado) {
        String nsql = "UPDATE public.\"MallaAlumno\"\n"
                + "SET  malla_almn_nota1=" + nota1 + ", malla_almn_nota2=" + nota2 + ", "
                + "malla_almn_nota3=" + nota3 + ", malla_almn_estado='" + estado + "', "
                + "malla_almn_num_matricula = " + numMatri + " \n"
                + "WHERE id_malla_alumno=" + idMalla + ";";

        PreparedStatement ps = conecta.getPS(nsql);
        if (conecta.nosql(ps) == null) {
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
        String sqlc = "SELECT id_malla_alumno, id_materia, malla_almn_estado \n"
                + "FROM public.\"MallaAlumno\" "
                + "WHERE id_almn_carrera = " + idAlumnoCarrera + " AND id_materia = " + idMateria + ";";
        MallaAlumnoMD mll = new MallaAlumnoMD();
        PreparedStatement ps = conecta.getPS(sqlc);
        ResultSet rs = conecta.sql(ps);
        try {
            if (rs != null) {
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
                ps.getConnection().close();
                return mll;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No se pudieron buscar estado materia");
            System.out.println(e.getMessage());
            return null;
        }
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
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            if (rs != null) {
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
                ps.getConnection().close();
                return mallas;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No se pudieron cargar mallas");
            System.out.println(e.getMessage());
            return null;
        }
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
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            if (rs != null) {
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
                ps.getConnection().close();
                return mallas;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No se pudieron cargar mallas");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String getSql() {
        return sql;
    }
}
