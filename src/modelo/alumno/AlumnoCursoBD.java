package modelo.alumno;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.ConectarDB;
import modelo.ResourceManager;
import modelo.curso.CursoBD;
import modelo.curso.CursoMD;
import modelo.persona.AlumnoBD;

/**
 *
 * @author Johnny
 */
public class AlumnoCursoBD extends AlumnoCursoMD {

    private final ConectarDB conecta;
    private final AlumnoBD alm;
    private final CursoBD cur;

    public AlumnoCursoBD(ConectarDB conecta) {
        this.conecta = conecta;
        this.alm = new AlumnoBD(conecta);
        this.cur = new CursoBD(conecta);
    }

    public void ingresarAlmnCurso(int idAlmn, int idCurso) {
        String nsql = "INSERT INTO public.\"AlumnoCurso\"(\n"
                + "id_alumno, id_curso)\n"
                + "VALUES (" + idAlmn + ", " + idCurso + ");";
        if (conecta.nosql(nsql) == null) {
            System.out.println("Se ingresao correctamente el alumno en el curso");
        }
    }

    public ArrayList<AlumnoCursoMD> cargarAlumnosCursos() {
        String sql = "SELECT "
                + "id_almn_curso,\n"
                + "id_alumno, \n"
                + "id_curso,\n"
                + "almn_curso_nt_1_parcial,\n"
                + "almn_curso_nt_examen_interciclo,\n"
                + "almn_curso_nt_2_parcial,\n"
                + "almn_curso_nt_examen_final,\n"
                + "almn_curso_nt_examen_supletorio,\n"
                + "almn_curso_asistencia,\n"
                + "almn_curso_nota_final,\n"
                + "almn_curso_estado,\n"
                + "almn_curso_num_faltas\n"
                + "FROM "
                + "public.\"AlumnoCurso\";";
        return consultarAlmnCursos(sql);
    }

    public ArrayList<AlumnoCursoMD> cargarAlumnosCursosPorAlumno(int idAlumno) {
        String sql = "SELECT id_almn_curso, id_alumno, id_curso, almn_curso_nt_1_parcial,\n"
                + "almn_curso_nt_examen_interciclo, almn_curso_nt_2_parcial,\n"
                + "almn_curso_nt_examen_final, almn_curso_nt_examen_supletorio,\n"
                + "almn_curso_asistencia, almn_curso_nota_final, almn_curso_estado,\n"
                + "almn_curso_num_faltas\n"
                + "FROM public.\"AlumnoCurso\" "
                + "WHERE id_alumno = " + idAlumno + ";";
        return consultarAlmnCursos(sql);
    }

    public ArrayList<AlumnoCursoMD> cargarAlumnosCursosPorCurso(int idCurso) {
        String sql = "SELECT id_almn_curso, id_alumno, id_curso, almn_curso_nt_1_parcial,\n"
                + "almn_curso_nt_examen_interciclo, almn_curso_nt_2_parcial,\n"
                + "almn_curso_nt_examen_final, almn_curso_nt_examen_supletorio,\n"
                + "almn_curso_asistencia, almn_curso_nota_final, almn_curso_estado,\n"
                + "almn_curso_num_faltas\n"
                + "FROM public.\"AlumnoCurso\" "
                + "WHERE id_curso = " + idCurso + ";";
        return consultarAlmnCursos(sql);
    }

    public AlumnoCursoMD buscarAlumnoCurso(int idAlmnCurso) {
        AlumnoCursoMD almn = new AlumnoCursoMD();
        String sql = "SELECT id_almn_curso, id_alumno, id_curso, almn_curso_nt_1_parcial,\n"
                + "almn_curso_nt_examen_interciclo, almn_curso_nt_2_parcial,\n"
                + "almn_curso_nt_examen_final, almn_curso_nt_examen_supletorio,\n"
                + "almn_curso_asistencia, almn_curso_nota_final, almn_curso_estado,\n"
                + "almn_curso_num_faltas\n"
                + "FROM public.\"AlumnoCurso\" "
                + "WHERE id_almn_curso = " + idAlmnCurso + ";";
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    almn = obtenerAlmCurso(rs);
                }
                return almn;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No se pudo buscar alumno curso");
            System.out.println(e.getMessage());
            return null;
        }
    }

    private ArrayList<AlumnoCursoMD> consultarAlmnCursos(String sql) {
        ArrayList<AlumnoCursoMD> almns = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    almns.add(obtenerAlmCurso(rs));
                }
                return almns;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No se pudieron consultar los cursos");
            return null;
        }
    }

    private AlumnoCursoMD obtenerAlmCurso(ResultSet rs) {
        try {
            AlumnoCursoMD a = new AlumnoCursoMD();
            a.setId(rs.getInt("id_almn_curso"));
            a.setAlumno(alm.buscarAlumnoParaReferencia(rs.getInt("id_alumno")));
            a.setCurso(cur.buscarCurso(rs.getInt("id_curso")));
            a.setNota1Parcial(rs.getDouble("almn_curso_nt_1_parcial"));
            a.setNotaExamenInter(rs.getDouble("almn_curso_nt_examen_interciclo"));
            a.setNota2Parcial(rs.getDouble("almn_curso_nt_2_parcial"));
            a.setNotaExamenFinal(rs.getDouble("almn_curso_nt_examen_final"));
            a.setNotaExamenSupletorio(rs.getDouble("almn_curso_nt_examen_supletorio"));
            a.setAsistencia(rs.getString("almn_curso_asistencia"));
            a.setNotaFinal(rs.getDouble("almn_curso_nota_final"));
            a.setEstado(rs.getString("almn_curso_estado"));
            a.setNumFalta(rs.getInt("almn_curso_num_faltas"));
            return a;
        } catch (SQLException e) {
            System.out.println("No pudimos obtener alumnocurso");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<AlumnoCursoBD> selectWhere(String paralelo, int ciclo, String nombreJornada, String nombreMateria, int idDocente, String nombrePeriodo) {

        String SELECT = "SELECT\n"
                + "\"AlumnoCurso\".id_almn_curso,\n"
                + "\"AlumnoCurso\".id_alumno,\n"
                + "\"AlumnoCurso\".id_curso,\n"
                + "\"AlumnoCurso\".almn_curso_nt_1_parcial,\n"
                + "\"AlumnoCurso\".almn_curso_nt_examen_interciclo,\n"
                + "\"AlumnoCurso\".almn_curso_nt_2_parcial,\n"
                + "\"AlumnoCurso\".almn_curso_nt_examen_final,\n"
                + "\"AlumnoCurso\".almn_curso_nt_examen_supletorio,\n"
                + "\"AlumnoCurso\".almn_curso_asistencia,\n"
                + "\"AlumnoCurso\".almn_curso_nota_final,\n"
                + "\"AlumnoCurso\".almn_curso_estado,\n"
                + "\"AlumnoCurso\".almn_curso_num_faltas\n"
                + "FROM\n"
                + "\"AlumnoCurso\"\n"
                + "INNER JOIN \"Cursos\" ON \"AlumnoCurso\".id_curso = \"Cursos\".id_curso\n"
                + "INNER JOIN \"Jornadas\" ON \"Cursos\".id_jornada = \"Jornadas\".id_jornada\n"
                + "INNER JOIN \"Materias\" ON \"Cursos\".id_materia = \"Materias\".id_materia\n"
                + "INNER JOIN \"PeriodoLectivo\" ON \"Cursos\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo\n"
                + "INNER JOIN \"Alumnos\" ON \"AlumnoCurso\".id_alumno = \"Alumnos\".id_alumno\n"
                + "INNER JOIN \"Personas\" ON \"Alumnos\".id_persona = \"Personas\".id_persona\n"
                + "WHERE\n"
                + "\"PeriodoLectivo\".prd_lectivo_estado = FALSE AND\n"
                + "\"Cursos\".id_docente = " + idDocente + " AND\n"
                + "\"PeriodoLectivo\".prd_lectivo_nombre = '" + nombrePeriodo + "' AND\n"
                + "\"Cursos\".curso_ciclo = " + ciclo + " AND\n"
                + "\"Cursos\".curso_paralelo = '" + paralelo + "' AND\n"
                + "\"Jornadas\".nombre_jornada = '" + nombreJornada + "'\n"
                + "ORDER BY\n"
                + "\"Personas\".persona_primer_apellido ASC;";

        return selectSimple(SELECT);

    }

    private List<AlumnoCursoBD> selectSimple(String QUERY) {
        List<AlumnoCursoBD> almns = new ArrayList();
        ResultSet rs = ResourceManager.Query(QUERY);
        try {

            while (rs.next()) {
                AlumnoCursoBD a = new AlumnoCursoBD(conecta);
                a.setId(rs.getInt("id_almn_curso"));

                a.setAlumno(alm.buscarAlumnoParaReferencia(rs.getInt("id_alumno")));
                CursoMD curso = new CursoMD();
                curso.setId_curso(rs.getInt("id_curso"));
                a.setCurso(curso);
                a.setNota1Parcial(rs.getDouble("almn_curso_nt_1_parcial"));
                a.setNotaExamenInter(rs.getDouble("almn_curso_nt_examen_interciclo"));
                a.setNota2Parcial(rs.getDouble("almn_curso_nt_2_parcial"));
                a.setNotaExamenFinal(rs.getDouble("almn_curso_nt_examen_final"));
                a.setNotaExamenSupletorio(rs.getDouble("almn_curso_nt_examen_supletorio"));
                a.setAsistencia(rs.getString("almn_curso_asistencia"));
                a.setNotaFinal(rs.getDouble("almn_curso_nota_final"));
                a.setEstado(rs.getString("almn_curso_estado"));
                a.setNumFalta(rs.getInt("almn_curso_num_faltas"));
                almns.add(a);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return almns;
    }

    public boolean editar() {
        String UPDATE = "UPDATE \"AlumnoCurso\" \n"
                + "SET \n"
                + "almn_curso_nt_1_parcial = " + getNota1Parcial() + ", \n"
                + "almn_curso_nt_examen_interciclo = " + getNotaExamenInter() + ", \n"
                + "almn_curso_nt_2_parcial = " + getNota2Parcial() + ", \n"
                + "almn_curso_nt_examen_final = " + getNotaExamenFinal() + ", \n"
                + "almn_curso_nt_examen_supletorio = " + getNotaExamenSupletorio() + ", \n"
                + "almn_curso_asistencia = '" + getAsistencia() + "', \n"
                + "almn_curso_nota_final = " + getNotaFinal() + ", \n"
                + "almn_curso_estado = '" + getEstado() + "',\n"
                + "almn_curso_num_faltas = " + getNumFalta() + "\n"
                + "WHERE \n"
                + "id_almn_curso = " + getId() + ";";

        System.out.println(UPDATE);

        return ResourceManager.Statement(UPDATE) == null;

    }

}
