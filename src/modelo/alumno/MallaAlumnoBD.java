package modelo.alumno;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.ConectarDB;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import modelo.persona.AlumnoMD;

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

    public ArrayList<MallaAlumnoMD> cargarMallasTbl() {
        String sql = "SELECT id_malla_alumno, ma.id_materia, ma.id_almn_carrera, malla_almn_ciclo, \n"
                + "malla_almn_num_matricula, malla_almn_nota1, malla_almn_nota2, malla_almn_nota3, \n"
                + "malla_almn_estado, persona_primer_nombre, persona_primer_apellido,\n"
                + "materia_nombre\n"
                + "FROM public.\"MallaAlumno\" ma, public.\"AlumnosCarrera\" ac, public.\"Alumnos\" a,\n"
                + "public.\"Personas\" p, public.\"Materias\" m\n"
                + "WHERE ac.id_almn_carrera = ma.id_almn_carrera AND\n"
                + "a.id_alumno = ac.id_alumno AND \n"
                + "p.id_persona = a.id_persona AND\n"
                + "m.id_materia = ma.id_materia;";
        return consultaMallasTbl(sql);
    }

    public ArrayList<MallaAlumnoMD> cargarMallasPorEstudiante(int idAlumno) {
        String sql = "SELECT id_malla_alumno, ma.id_materia, ma.id_almn_carrera, malla_almn_ciclo, \n"
                + "malla_almn_num_matricula, malla_almn_nota1, malla_almn_nota2, malla_almn_nota3, \n"
                + "malla_almn_estado, persona_primer_nombre, persona_primer_apellido,\n"
                + "materia_nombre\n"
                + "FROM public.\"MallaAlumno\" ma, public.\"AlumnosCarrera\" ac, public.\"Alumnos\" a,\n"
                + "public.\"Personas\" p, public.\"Materias\" m\n"
                + "WHERE ac.id_almn_carrera = ma.id_almn_carrera AND\n"
                + "a.id_alumno = ac.id_alumno AND \n"
                + "p.id_persona = a.id_persona AND\n"
                + "m.id_materia = ma.id_materia AND\n"
                + "ac.id_almn_carrera = " + idAlumno + ";";
        //return consultaMallasPorAlumno(sql, idAlumno);
        //System.out.println(sql);
        return consultaMallasTbl(sql);
    }

    public ArrayList<MallaAlumnoMD> cargarMallaAlumnoPorEstado(int idAlumno, String estado) {
        String sql = "SELECT id_malla_alumno, ma.id_materia, ma.id_almn_carrera, malla_almn_ciclo, \n"
                + "malla_almn_num_matricula, malla_almn_nota1, malla_almn_nota2, malla_almn_nota3, \n"
                + "malla_almn_estado, persona_primer_nombre, persona_primer_apellido,\n"
                + "materia_nombre\n"
                + "FROM public.\"MallaAlumno\" ma, public.\"AlumnosCarrera\" ac, public.\"Alumnos\" a,\n"
                + "public.\"Personas\" p, public.\"Materias\" m\n"
                + "WHERE ac.id_almn_carrera = ma.id_almn_carrera AND\n"
                + "a.id_alumno = ac.id_alumno AND \n"
                + "p.id_persona = a.id_persona AND\n"
                + "m.id_materia = ma.id_materia AND\n"
                + "ac.id_almn_carrera = "+idAlumno+"\n"
                + "AND malla_almn_estado = '" + estado.charAt(0) + "';";
        //System.out.println(sql);
        return consultaMallasTbl(sql);
    }

    public ArrayList<MallaAlumnoMD> buscarMateriasAlumnoPorEstadoYCiclo(int idAlumnoCarrera, String estado, int ciclo) {
        String sql = "SELECT id_malla_alumno, id_materia \n"
                + "FROM public.\"MallaAlumno\" "
                + "WHERE id_almn_carrera = " + idAlumnoCarrera + " AND malla_almn_estado = '" + estado.charAt(0) + "' "
                + "AND malla_almn_ciclo = " + ciclo + ";";
        ArrayList<MallaAlumnoMD> mallas = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                AlumnoCarreraMD a = new AlumnoCarreraMD();
                while (rs.next()) {
                    MallaAlumnoMD mll = new MallaAlumnoMD();
                    a.setId(idAlumnoCarrera);
                    mll.setId(rs.getInt("id_malla_alumno"));
                    MateriaMD m = mat.buscarMateriaPorReferencia(rs.getInt("id_materia"));
                    mll.setMateria(m);
                    mll.setAlumnoCarrera(a);

                    mallas.add(mll);
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

    public MallaAlumnoMD buscarMateriaEstado(int idAlumnoCarrera, int idMateria) {
        String sql = "SELECT id_malla_alumno, id_materia, malla_almn_estado \n"
                + "FROM public.\"MallaAlumno\" "
                + "WHERE id_almn_carrera = " + idAlumnoCarrera + " AND id_materia = " + idMateria + ";";
        //System.out.println(sql);
        MallaAlumnoMD mll = new MallaAlumnoMD();
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                AlumnoCarreraMD a = new AlumnoCarreraMD();
                while (rs.next()) {

                    a.setId(idAlumnoCarrera);
                    mll.setId(rs.getInt("id_malla_alumno"));
                    MateriaMD m = mat.buscarMateriaPorReferencia(rs.getInt("id_materia"));
                    mll.setEstado(rs.getString("malla_almn_estado"));
                    mll.setMateria(m);
                    mll.setAlumnoCarrera(a);

                }
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
    
    //Aqui unicamente buscamos las materias que tiene un alumno, y su ciclo 
    public ArrayList<MallaAlumnoMD> buscarMateriasAlumnoPorEstado(int idAlumnoCarrera, String estado) {
        String sql = "SELECT id_malla_alumno, ma.id_materia, ma.id_almn_carrera, malla_almn_ciclo, \n"
                + "malla_almn_num_matricula, \n"
                + "materia_nombre\n"
                + "FROM public.\"MallaAlumno\" ma, public.\"AlumnosCarrera\" ac, public.\"Materias\" m\n"
                + "WHERE ac.id_almn_carrera = ma.id_almn_carrera AND\n"
                + "m.id_materia = ma.id_materia AND\n"
                + "ac.id_almn_carrera = "+idAlumnoCarrera+"\n"
                + "AND malla_almn_estado = '" + estado.charAt(0) + "';";
        ArrayList<MallaAlumnoMD> mallas = new ArrayList();
        ResultSet rs = conecta.sql(sql);
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

                    mallas.add(mll);
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

    public ArrayList<MallaAlumnoMD> cargarMallaAlumnoPorEstadoYCiclo(int idAlumno, String estado, int ciclo) {
        String sql = "SELECT id_malla_alumno, id_materia, id_almn_carrera, malla_almn_ciclo, \n"
                + "malla_almn_num_matricula, malla_almn_estado\n"
                + "FROM public.\"MallaAlumno\" "
                + "WHERE id_almn_carrera = " + idAlumno + " AND malla_almn_estado = '" + estado.charAt(0) + "' "
                + "AND malla_almn_ciclo = " + ciclo + ";";
        //System.out.println(sql);
        return consultaMallasPorReferencia(sql, idAlumno);
    }

    public ArrayList<MallaAlumnoMD> buscarMallaAlumno(String aguja) {
        String sql = "SELECT id_malla_alumno, ma.id_materia, ma.id_almn_carrera, malla_almn_ciclo, \n"
                + "malla_almn_num_matricula, malla_almn_nota1, malla_almn_nota2, malla_almn_nota3, \n"
                + "malla_almn_estado, persona_primer_nombre, persona_primer_apellido,\n"
                + "materia_nombre\n"
                + "FROM public.\"MallaAlumno\" ma, public.\"AlumnosCarrera\" ac, public.\"Alumnos\" a,\n"
                + "public.\"Personas\" p, public.\"Materias\" m\n"
                + "WHERE ac.id_almn_carrera = ma.id_almn_carrera AND\n"
                + "a.id_alumno = ac.id_alumno AND \n"
                + "p.id_persona = a.id_persona AND\n"
                + "m.id_materia = ma.id_materia AND (\n"
                + "	persona_primer_nombre || ' ' || persona_primer_apellido ILIKE '%" + aguja + "%'\n"
                + "	OR persona_identificacion ILIKE '%" + aguja + "%'\n"
                + ");";
        return consultaMallasTbl(sql);
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

    private ArrayList<MallaAlumnoMD> consultaMallasPorReferencia(String sql, int idAlumno) {
        ArrayList<MallaAlumnoMD> mallas = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                AlumnoCarreraMD a = alm.buscarAlumnoCarrera(idAlumno);
                while (rs.next()) {
                    MallaAlumnoMD mll = new MallaAlumnoMD();
                    mll.setId(rs.getInt("id_malla_alumno"));
                    MateriaMD m = mat.buscarMateriaPorReferencia(rs.getInt("id_materia"));
                    mll.setMateria(m);
                    mll.setAlumnoCarrera(a);
                    mll.setMallaCiclo(rs.getInt("malla_almn_ciclo"));
                    mll.setMallaNumMatricula(rs.getInt("malla_almn_num_matricula"));
                    mll.setEstado(rs.getString("malla_almn_estado"));

                    mallas.add(mll);
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

    private ArrayList<MallaAlumnoMD> consultaMallasTbl(String sql) {
        ArrayList<MallaAlumnoMD> mallas = new ArrayList();
        ResultSet rs = conecta.sql(sql);
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
                    AlumnoMD al = new AlumnoMD();
                    al.setPrimerApellido(rs.getString("persona_primer_apellido"));
                    al.setPrimerNombre(rs.getString("persona_primer_nombre"));
                    a.setAlumno(al);
                    mll.setAlumnoCarrera(a);
                    mll.setMallaCiclo(rs.getInt("malla_almn_ciclo"));
                    mll.setMallaNumMatricula(rs.getInt("malla_almn_num_matricula"));
                    mll.setNota1(rs.getDouble("malla_almn_nota1"));
                    mll.setNota2(rs.getDouble("malla_almn_nota2"));
                    mll.setNota2(rs.getDouble("malla_almn_nota2"));
                    mll.setEstado(rs.getString("malla_almn_estado"));

                    mallas.add(mll);
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
            MateriaMD m = mat.buscarMateriaPorReferencia(rs.getInt("id_materia"));
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
            MateriaMD m = mat.buscarMateriaPorReferencia(rs.getInt("id_materia"));
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
