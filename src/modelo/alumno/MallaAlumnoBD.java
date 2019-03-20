package modelo.alumno;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.ConectarDB;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;

/**
 *
 * @author Johnny
 */
public class MallaAlumnoBD extends MallaAlumnoMD {

    private final ConectarDB conecta;
    private final MateriaBD mat;
    private final AlumnoCarreraBD alm;

    public MallaAlumnoBD(ConectarDB conecta) {
        this.conecta = conecta;
        this.mat = new MateriaBD(conecta);
        this.alm = new AlumnoCarreraBD(conecta);
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
        if (conecta.nosql(nsql) == null) {
            System.out.println("Se guardao correctamente la nota");
        }
    }

    public void actualizarNumMatricula(int idAlumno, int idCarrera, int idMateria) {
        String nsql = "UPDATE public.\"MallaAlumno\"\n"
                + "SET malla_almn_num_matricula = ( SELECT malla_almn_num_matricula + 1\n"
                + "	FROM public.\"MallaAlumno\"\n"
                + "	WHERE id_almn_carrera = (\n"
                + "		SELECT id_almn_carrera\n"
                + "		FROM public.\"AlumnosCarrera\"\n"
                + "		WHERE id_alumno = " + idAlumno + " AND id_carrera = " + idCarrera + ") \n"
                + "	AND id_materia = " + idMateria + ")\n"
                + "WHERE id_malla_alumno=( SELECT id_malla_alumno\n"
                + "	FROM public.\"MallaAlumno\"\n"
                + "	WHERE id_almn_carrera = (\n"
                + "		SELECT id_almn_carrera\n"
                + "		FROM public.\"AlumnosCarrera\"\n"
                + "		WHERE id_alumno = " + idAlumno + " AND id_carrera = " + idCarrera + ") \n"
                + "	AND id_materia = " + idMateria + ");";
        if (conecta.nosql(nsql) == null) {
            System.out.println("Se actualizo la malla");
        }
    }

    public void actualizarEstadoMallaAlmn(int idAlumno, int idCarrera, int idMateria) {
        String nsql = "UPDATE public.\"MallaAlumno\"\n"
                + "SET malla_almn_estado = 'M'\n"
                + "WHERE id_malla_alumno=( SELECT id_malla_alumno\n"
                + "	FROM public.\"MallaAlumno\"\n"
                + "	WHERE id_almn_carrera = (\n"
                + "		SELECT id_almn_carrera\n"
                + "		FROM public.\"AlumnosCarrera\"\n"
                + "		WHERE id_alumno = " + idAlumno + " AND id_carrera = " + idCarrera + ") \n"
                + "	AND id_materia = " + idMateria + ");";
        if (conecta.nosql(nsql) == null) {
            System.out.println("Se actualizo la malla");
        }
    }

    public void iniciarMalla(int idMateria, int idAlumno, int ciclo) {
        //Este inser deberia cambiar
        String nsql = "INSERT INTO public.\"MallaAlumno\"(\n"
                + "	id_malla_alumno, id_materia, id_almn_carrera, malla_almn_ciclo)\n"
                + "	VALUES ('" + idMateria + "-" + idAlumno + "' ," + idMateria + ", " + idAlumno + ", " + ciclo + ");";

        if (conecta.nosql(nsql) == null) {
            //System.out.println("Se guarda malla de un estidiante");
        }
    }

    public ArrayList<MallaAlumnoMD> cargarMallas() {
        String sql = "SELECT id_malla_alumno, id_materia, id_almn_carrera, malla_almn_ciclo, \n"
                + "malla_almn_num_matricula, malla_almn_nota1, malla_almn_nota2, \n"
                + "malla_almn_nota3, malla_almn_estado\n"
                + "FROM public.\"MallaAlumno\";";
        return consultaMallas(sql);
    }

    public ArrayList<MallaAlumnoMD> cargarMallasPorEstudiante(int idAlumno) {
        String sql = "SELECT id_malla_alumno, id_materia, id_almn_carrera, malla_almn_ciclo, \n"
                + "malla_almn_num_matricula, malla_almn_nota1, malla_almn_nota2, \n"
                + "malla_almn_nota3, malla_almn_estado\n"
                + "	FROM public.\"MallaAlumno\" "
                + "WHERE id_almn_carrera = " + idAlumno + ";";
        return consultaMallasPorAlumno(sql, idAlumno);
    }

    public ArrayList<MallaAlumnoMD> cargarMallaAlumnoPorEstado(int idAlumno, String estado) {
        String sql = "SELECT id_malla_alumno, id_materia, id_almn_carrera, malla_almn_ciclo, \n"
                + "malla_almn_num_matricula, malla_almn_nota1, malla_almn_nota2, \n"
                + "malla_almn_nota3, malla_almn_estado\n"
                + "FROM public.\"MallaAlumno\" "
                + "WHERE id_almn_carrera = " + idAlumno + " AND malla_almn_estado = '" + estado.charAt(0) + "';";
        //System.out.println(sql);
        return consultaMallasPorAlumno(sql, idAlumno);
    }

    public ArrayList<MallaAlumnoMD> cargarMallaAlumnoPorEstadoYCiclo(int idAlumno, String estado, int ciclo) {
        String sql = "SELECT id_malla_alumno, id_materia, id_almn_carrera, malla_almn_ciclo, \n"
                + "malla_almn_num_matricula, malla_almn_nota1, malla_almn_nota2, \n"
                + "malla_almn_nota3, malla_almn_estado\n"
                + "FROM public.\"MallaAlumno\" "
                + "WHERE id_almn_carrera = " + idAlumno + " AND malla_almn_estado = '" + estado.charAt(0) + "' "
                + "AND malla_almn_ciclo = " + ciclo + ";";
        //System.out.println(sql);
        return consultaMallasPorAlumno(sql, idAlumno);
    }

    public ArrayList<MallaAlumnoMD> buscarMallaAlumno(String aguja) {
        String sql = "SELECT id_malla_alumno, id_materia, ma.id_almn_carrera, malla_almn_ciclo, malla_almn_num_matricula, \n"
                + "malla_almn_nota1, malla_almn_nota2, malla_almn_nota3, malla_almn_estado, malla_alm_observacion\n"
                + "FROM public.\"MallaAlumno\" AS ma, public.\"AlumnosCarrera\" AS ac, \n"
                + "public.\"Alumnos\" a, public.\"Personas\" p\n"
                + "WHERE ma.id_almn_carrera = ac.id_almn_carrera \n"
                + "AND ac.id_alumno = a.id_alumno \n"
                + "AND a.id_persona = p.id_persona \n"
                + "AND (persona_identificacion ILIKE '%" + aguja + "%'\n"
                + "	OR persona_primer_nombre ILIKE '%" + aguja + "%' \n"
                + "	OR persona_primer_apellido ILIKE '%" + aguja + "%'\n"
                + "	OR persona_segundo_nombre ILIKE '%" + aguja + "%'\n"
                + "	OR persona_segundo_apellido ILIKE '%" + aguja + "%');";
        return consultaMallas(sql);
    }

    public MallaAlumnoMD buscarMallaAlumno(int idMallaAlmn) {
        MallaAlumnoMD m = new MallaAlumnoMD();
        String sql = "";
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    m = obtenerMallaAlumno(rs);
                }
                return m;
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    private ArrayList<MallaAlumnoMD> consultaMallasPorAlumno(String sql, int idAlumno) {
        ArrayList<MallaAlumnoMD> mallas = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                AlumnoCarreraMD a = alm.buscarAlumnoCarrera(idAlumno);
                while (rs.next()) {
                    MallaAlumnoMD m = obtenerMallaAlumnoPorAlumno(rs, a);
                    if (m != null) {
                        mallas.add(m);
                    }
                }
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

    private ArrayList<MallaAlumnoMD> consultaMallas(String sql) {
        ArrayList<MallaAlumnoMD> mallas = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    MallaAlumnoMD m = obtenerMallaAlumno(rs);
                    if (m != null) {
                        mallas.add(m);
                    }
                }
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

    private MallaAlumnoMD obtenerMallaAlumno(ResultSet rs) {
        MallaAlumnoMD mll = new MallaAlumnoMD();
        try {
            mll.setId(rs.getInt("id_malla_alumno"));
            MateriaMD m = mat.buscarMateria(rs.getInt("id_materia"));
            mll.setMateria(m);
            AlumnoCarreraMD a = alm.buscarAlumnoCarrera(rs.getInt("id_almn_carrera"));
            mll.setAlumnoCarrera(a);
            mll.setMallaCiclo(rs.getInt("malla_almn_ciclo"));
            mll.setMallaNumMatricula(rs.getInt("malla_almn_num_matricula"));
            mll.setNota1(rs.getDouble("malla_almn_nota1"));
            mll.setNota2(rs.getDouble("malla_almn_nota2"));
            mll.setNota2(rs.getDouble("malla_almn_nota2"));
            mll.setEstado(rs.getString("malla_almn_estado"));
            return mll;
        } catch (SQLException e) {
            System.out.println("No se pudo obtener la malla");
            System.out.println(e.getMessage());
            return null;
        }
    }

    private MallaAlumnoMD obtenerMallaAlumnoPorAlumno(ResultSet rs, AlumnoCarreraMD almn) {
        MallaAlumnoMD mll = new MallaAlumnoMD();
        try {
            mll.setId(rs.getInt("id_malla_alumno"));
            MateriaMD m = mat.buscarMateria(rs.getInt("id_materia"));
            mll.setMateria(m);
            mll.setAlumnoCarrera(almn);
            mll.setMallaCiclo(rs.getInt("malla_almn_ciclo"));
            mll.setMallaNumMatricula(rs.getInt("malla_almn_num_matricula"));
            mll.setNota1(rs.getDouble("malla_almn_nota1"));
            mll.setNota2(rs.getDouble("malla_almn_nota2"));
            mll.setNota2(rs.getDouble("malla_almn_nota2"));
            mll.setEstado(rs.getString("malla_almn_estado"));
            return mll;
        } catch (SQLException e) {
            System.out.println("No se pudo obtener la malla");
            System.out.println(e.getMessage());
            return null;
        }
    }
}
