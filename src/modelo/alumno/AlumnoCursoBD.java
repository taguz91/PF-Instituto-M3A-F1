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
import modelo.persona.AlumnoMD;

/**
 *
 * @author Johnny
 */
public class AlumnoCursoBD extends AlumnoCursoMD {

    private ConectarDB conecta;
    private AlumnoBD alm;
    private CursoBD cur;

    public AlumnoCursoBD(ConectarDB conecta) {
        this.conecta = conecta;
        this.alm = new AlumnoBD(conecta);
        this.cur = new CursoBD(conecta);
    }

    public AlumnoCursoBD() {
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

    public ArrayList<AlumnoCursoMD> cargarAlumnosCursosPorCursoTbl(String curso, int idPrd) {
        String sql = "SELECT DISTINCT c.curso_nombre,  \n"
                + "persona_primer_nombre, persona_segundo_nombre, persona_primer_apellido, \n"
                + "persona_segundo_apellido, persona_identificacion\n"
                + "FROM public.\"AlumnoCurso\" ac, public.\"Alumnos\" a, public.\"Personas\" p, \n"
                + "public.\"Cursos\" c\n"
                + "WHERE a.id_alumno = ac.id_alumno AND \n"
                + "p.id_persona = a.id_persona AND\n"
                + "c.curso_nombre = '" + curso + "' AND\n"
                + "c.id_prd_lectivo = " + idPrd + "  AND\n"
                + "ac.id_curso = c.id_curso "
                + "ORDER BY persona_primer_apellido;";
        return consultarAlmnCursosParaTblSimple(sql);
    }

    public ArrayList<AlumnoCursoMD> cargarAlumnosCursosPorPrdTbl(int idPrd) {
        String sql = "SELECT DISTINCT c.curso_nombre,  \n"
                + "persona_primer_nombre, persona_segundo_nombre, persona_primer_apellido, \n"
                + "persona_segundo_apellido, persona_identificacion\n"
                + "FROM public.\"AlumnoCurso\" ac, public.\"Alumnos\" a, public.\"Personas\" p, \n"
                + "public.\"Cursos\" c\n"
                + "WHERE a.id_alumno = ac.id_alumno AND \n"
                + "p.id_persona = a.id_persona AND\n"
                + "c.id_prd_lectivo = " + idPrd + "  AND\n"
                + "ac.id_curso = c.id_curso "
                + "ORDER BY persona_primer_apellido;";
        return consultarAlmnCursosParaTblSimple(sql);
    }

    public ArrayList<AlumnoCursoMD> cargarAlumnosCursosTbl() {
        String sql = "SELECT DISTINCT curso_nombre, \n"
                + "persona_primer_nombre, persona_primer_apellido,\n"
                + "persona_identificacion\n"
                + "FROM public.\"AlumnoCurso\" ac , public.\"Alumnos\" a, \n"
                + "public.\"Cursos\" c, public.\"Personas\" p, "
                + "public.\"Materias\" m, public.\"Carreras\" cr \n"
                + "WHERE ac.id_alumno = a.id_alumno AND\n"
                + "p.id_persona = a.id_persona AND\n"
                + "ac.id_curso = c.id_curso AND \n"
                + "m.id_materia = c.id_materia AND \n"
                + "cr.id_carrera = m.id_carrera "
                + "AND persona_activa = true \n"
                + "AND alumno_activo = true AND carrera_activo = true;";
        return consultarAlmnCursosParaTblSimple(sql);
    }

    public ArrayList<AlumnoCursoMD> buscarAlumnosCursosTbl(String aguja) {
        String sql = "SELECT DISTINCT curso_nombre, \n"
                + "persona_primer_nombre, persona_primer_apellido,\n"
                + "persona_identificacion\n"
                + "FROM public.\"AlumnoCurso\" ac , public.\"Alumnos\" a, \n"
                + "public.\"Cursos\" c, public.\"Personas\" p, public.\"Materias\" m, "
                + "public.\"Carreras\" cr \n"
                + "WHERE ac.id_alumno = a.id_alumno AND \n"
                + "m.id_materia = c.id_materia AND \n"
                + "cr.id_carrera = m.id_carrera AND\n"
                + "p.id_persona = a.id_persona AND\n"
                + "ac.id_curso = c.id_curso AND\n"
                + "(curso_nombre ILIKE '%" + aguja + "%' OR \n"
                + "persona_primer_nombre || ' ' || persona_primer_apellido ILIKE '%" + aguja + "%'\n"
                + "OR persona_identificacion ILIKE '%" + aguja + "%') "
                + "AND persona_activa = true AND alumno_activo = true "
                + "AND carrera_activo = true;";
        return consultarAlmnCursosParaTblSimple(sql);
    }

    private ArrayList<AlumnoCursoMD> consultarAlmnCursosParaTblSimple(String sql) {
        ArrayList<AlumnoCursoMD> almns = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    AlumnoCursoMD a = new AlumnoCursoMD();
                    CursoMD cu = new CursoMD();
                    cu.setCurso_nombre(rs.getString("curso_nombre"));
                    AlumnoMD al = new AlumnoMD();
                    al.setPrimerNombre(rs.getString("persona_primer_nombre"));
                    al.setPrimerApellido(rs.getString("persona_primer_apellido"));
                    al.setIdentificacion(rs.getString("persona_identificacion"));
                    a.setAlumno(al);
                    a.setCurso(cu);

                    almns.add(a);
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

    public static List<AlumnoCursoBD> selectWhere(String paralelo, int ciclo, String nombreJornada, String nombreMateria, int idDocente, String nombrePeriodo) {

        String SELECT = "SELECT\n"
                + "\"public\".\"ViewAlumnoCurso\".id_almn_curso,\n"
                + "\"public\".\"ViewAlumnoCurso\".id_alumno,\n"
                + "\"public\".\"ViewAlumnoCurso\".id_curso,\n"
                + "\"public\".\"ViewAlumnoCurso\".almn_curso_nt_1_parcial,\n"
                + "\"public\".\"ViewAlumnoCurso\".almn_curso_nt_examen_interciclo,\n"
                + "\"public\".\"ViewAlumnoCurso\".almn_curso_nt_2_parcial,\n"
                + "\"public\".\"ViewAlumnoCurso\".almn_curso_nt_examen_final,\n"
                + "\"public\".\"ViewAlumnoCurso\".almn_curso_nt_examen_supletorio,\n"
                + "\"public\".\"ViewAlumnoCurso\".almn_curso_asistencia,\n"
                + "\"public\".\"ViewAlumnoCurso\".almn_curso_nota_final,\n"
                + "\"public\".\"ViewAlumnoCurso\".almn_curso_estado,\n"
                + "\"public\".\"ViewAlumnoCurso\".almn_curso_num_faltas,\n"
                + "\"public\".\"ViewAlumnoCurso\".persona_identificacion,\n"
                + "\"public\".\"ViewAlumnoCurso\".persona_primer_apellido,\n"
                + "\"public\".\"ViewAlumnoCurso\".persona_segundo_apellido,\n"
                + "\"public\".\"ViewAlumnoCurso\".persona_primer_nombre,\n"
                + "\"public\".\"ViewAlumnoCurso\".persona_segundo_nombre,\n"
                + "\"public\".\"ViewAlumnoCurso\".id_persona,\n"
                + "\"public\".\"ViewAlumnoCurso\".alumno_codigo,"
                + "(\"public\".\"ViewAlumnoCurso\".almn_curso_num_faltas * 100)/\"Materias\".materia_total_horas\n"
                + "FROM\n"
                + "\"public\".\"ViewAlumnoCurso\"\n"
                + "INNER JOIN \"Cursos\" ON \"ViewAlumnoCurso\".id_curso = \"Cursos\".id_curso\n"
                + "INNER JOIN \"Jornadas\" ON \"Cursos\".id_jornada = \"Jornadas\".id_jornada\n"
                + "INNER JOIN \"Materias\" ON \"Cursos\".id_materia = \"Materias\".id_materia\n"
                + "INNER JOIN \"PeriodoLectivo\" ON \"Cursos\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo\n"
                + "INNER JOIN \"Alumnos\" ON \"ViewAlumnoCurso\".id_alumno = \"Alumnos\".id_alumno\n"
                + "INNER JOIN \"Personas\" ON \"Alumnos\".id_persona = \"Personas\".id_persona\n"
                + "WHERE"
                + "\"public\".\"PeriodoLectivo\".prd_lectivo_estado IS TRUE AND\n"
                + "\"public\".\"Cursos\".id_docente = " + idDocente + " AND\n"
                + "\"public\".\"PeriodoLectivo\".prd_lectivo_nombre = '" + nombrePeriodo + "' AND\n"
                + "\"public\".\"Cursos\".curso_ciclo = " + ciclo + " AND\n"
                + "\"public\".\"Cursos\".curso_paralelo = '" + paralelo + "' AND\n"
                + "\"public\".\"Jornadas\".nombre_jornada = '" + nombreJornada + "'\n"
                + "ORDER BY\n"
                + "\"public\".\"Personas\".persona_primer_apellido ASC";
        System.out.println(SELECT);

        return selectFromView(SELECT);

    }

    private static List<AlumnoCursoBD> selectFromView(String QUERY) {
        ResourceManager.Statements("REFRESH MATERIALIZED VIEW \"ViewAlumnoCurso\" \n");
        List<AlumnoCursoBD> lista = new ArrayList();
        ResultSet rs = ResourceManager.Query(QUERY);
        try {

            while (rs.next()) {
                AlumnoCursoBD alumnoCurso = new AlumnoCursoBD();

                alumnoCurso.setId(rs.getInt("id_almn_curso"));
                AlumnoMD alumno = new AlumnoMD();
                alumno.setId_Alumno(rs.getInt("id_alumno"));
                alumno.setIdentificacion(rs.getString("persona_identificacion"));
                alumno.setPrimerApellido(rs.getString("persona_primer_apellido"));
                alumno.setSegundoApellido(rs.getString("persona_segundo_apellido"));
                alumno.setPrimerNombre(rs.getString("persona_primer_nombre"));
                alumno.setSegundoNombre(rs.getString("persona_segundo_nombre"));
                alumno.setIdPersona(rs.getInt("id_persona"));
                alumno.setId_Alumno(rs.getInt("alumno_codigo"));

                alumnoCurso.setAlumno(alumno);

                CursoMD curso = new CursoMD();
                curso.setId_curso(rs.getInt("id_curso"));
                alumnoCurso.setCurso(curso);

                alumnoCurso.setNota1Parcial(rs.getDouble("almn_curso_nt_1_parcial"));
                alumnoCurso.setNotaExamenInter(rs.getDouble("almn_curso_nt_examen_interciclo"));
                alumnoCurso.setNota2Parcial(rs.getDouble("almn_curso_nt_2_parcial"));
                alumnoCurso.setNotaExamenFinal(rs.getDouble("almn_curso_nt_examen_final"));
                alumnoCurso.setNotaExamenSupletorio(rs.getDouble("almn_curso_nt_examen_final"));
                alumnoCurso.setAsistencia(rs.getString("almn_curso_asistencia"));
                alumnoCurso.setNotaFinal(rs.getDouble("almn_curso_nota_final"));
                alumnoCurso.setEstado(rs.getString("almn_curso_estado"));
                alumnoCurso.setNumFalta(rs.getInt("almn_curso_num_faltas"));

                lista.add(alumnoCurso);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lista;
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
