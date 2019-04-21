package modelo.alumno;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.ConectarDB;
import modelo.ResourceManager;
import modelo.curso.CursoBD;
import modelo.curso.CursoMD;
import modelo.notas.NotasBD;
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
    String nsqlMatri = "";

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

    public void agregarMatricula(int idAlmn, int idCurso) {
        String nsql = "\nINSERT INTO public.\"AlumnoCurso\"(\n"
                + "id_alumno, id_curso)\n"
                + "VALUES (" + idAlmn + ", " + idCurso + ");";
        nsqlMatri = nsqlMatri + nsql;
        System.out.println("Matricula: " + nsqlMatri);
    }

    public void guardarAlmnCurso() {
        System.out.println("-------------");
        System.out.println("Matricula completa: " + nsqlMatri);
        nsqlMatri = "";
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
                + "\"public\".\"AlumnoCurso2\".id_alumno,\n"
                + "\"public\".\"AlumnoCurso2\".almn_curso_asistencia,\n"
                + "\"public\".\"AlumnoCurso2\".almn_curso_estado,\n"
                + "\"public\".\"AlumnoCurso2\".almn_curso_num_faltas,\n"
                //+ "\"public\".\"AlumnoCurso2\".almn_curso_activo,\n"
                + "\"public\".\"Personas\".persona_identificacion,\n"
                + "\"public\".\"Personas\".persona_primer_apellido,\n"
                + "\"public\".\"Personas\".persona_segundo_apellido,\n"
                + "\"public\".\"Personas\".persona_primer_nombre,\n"
                + "\"public\".\"Personas\".persona_segundo_nombre,\n"
                + "\"public\".\"AlumnoCurso2\".id_almn_curso\n"
                + "FROM\n"
                + "\"public\".\"AlumnoCurso2\"\n"
                + "INNER JOIN \"public\".\"Cursos\" ON \"public\".\"AlumnoCurso2\".id_curso = \"public\".\"Cursos\".id_curso\n"
                + "INNER JOIN \"public\".\"PeriodoLectivo\" ON \"public\".\"Cursos\".id_prd_lectivo = \"public\".\"PeriodoLectivo\".id_prd_lectivo\n"
                + "INNER JOIN \"public\".\"Jornadas\" ON \"public\".\"Cursos\".id_jornada = \"public\".\"Jornadas\".id_jornada\n"
                + "INNER JOIN \"public\".\"Materias\" ON \"public\".\"Cursos\".id_materia = \"public\".\"Materias\".id_materia\n"
                + "INNER JOIN \"public\".\"Alumnos\" ON \"public\".\"AlumnoCurso2\".id_alumno = \"public\".\"Alumnos\".id_alumno\n"
                + "INNER JOIN \"public\".\"Personas\" ON \"public\".\"Alumnos\".id_persona = \"public\".\"Personas\".id_persona\n"
                + "WHERE\n"
                + "\"public\".\"Cursos\".id_docente = " + idDocente + " AND\n"
                + "\"public\".\"PeriodoLectivo\".prd_lectivo_nombre = '" + nombrePeriodo + "' AND\n"
                + "\"public\".\"Cursos\".curso_ciclo = " + ciclo + " AND\n"
                + "\"public\".\"Cursos\".curso_paralelo = '" + paralelo + "' AND\n"
                + "\"public\".\"Jornadas\".nombre_jornada = '" + nombreJornada + "' AND\n"
                + "\"public\".\"Materias\".materia_nombre = '" + nombreMateria + "'\n"
                + "ORDER BY\n"
                + "\"public\".\"Personas\".persona_primer_apellido ASC";

        System.out.println(SELECT);

        List<AlumnoCursoBD> lista = new ArrayList();

        ResultSet rs = ResourceManager.Query(SELECT);
        try {

            while (rs.next()) {
                AlumnoCursoBD alumnoCurso = new AlumnoCursoBD();

                alumnoCurso.setId(rs.getInt("id_almn_curso"));
                alumnoCurso.setAsistencia(rs.getString("almn_curso_asistencia"));
                alumnoCurso.setEstado(rs.getString("almn_curso_estado"));
                alumnoCurso.setNumFalta(rs.getInt("almn_curso_num_faltas"));

                AlumnoMD alumno = new AlumnoMD();
                alumno.setId_Alumno(rs.getInt("id_alumno"));
                alumno.setIdentificacion(rs.getString("persona_identificacion"));
                alumno.setPrimerApellido(rs.getString("persona_primer_apellido"));
                alumno.setSegundoApellido(rs.getString("persona_segundo_apellido"));
                alumno.setPrimerNombre(rs.getString("persona_primer_nombre"));
                alumno.setSegundoNombre(rs.getString("persona_segundo_nombre"));

                alumnoCurso.setAlumno(alumno);

                List<NotasBD> notas = NotasBD.selectWhere(alumnoCurso);

                alumnoCurso.setNotas(notas);

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
