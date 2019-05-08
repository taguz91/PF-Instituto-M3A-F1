package modelo.alumno;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.ConectarDB;
import modelo.ResourceManager;
import modelo.curso.CursoBD;
import modelo.curso.CursoMD;
import modelo.materia.MateriaMD;
import modelo.notas.NotasBD;
import modelo.periodolectivo.PeriodoLectivoMD;
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
    private String nsqlMatri = "", nsqlMatriUpdate = "";

    public AlumnoCursoBD(ConectarDB conecta) {
        this.conecta = conecta;
        this.alm = new AlumnoBD(conecta);
        this.cur = new CursoBD(conecta);
    }

    public AlumnoCursoBD() {
    }

    /**
     * La sentencia que se va a enviar se le manda un string vacio.
     */
    public void borrarMatricula() {
        nsqlMatri = "";
    }

    public void agregarMatricula(int idAlmn, int idCurso) {
        String nsql = "\nINSERT INTO public.\"AlumnoCurso\"(\n"
                + "id_alumno, id_curso)\n"
                + "VALUES (" + idAlmn + ", " + idCurso + ");";
        nsqlMatri = nsqlMatri + nsql;
    }

    public boolean guardarAlmnCurso() {
        if (conecta.nosql(nsqlMatri) == null) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "No pudimos matricular al alumno, revise \n"
                    + "su conexion a internet.");
            return false;
        }
    }

    public void agregarUpdate(int idAlmnCurso, int idCurso) {
        String nsql = "UPDATE public.\"AlumnoCurso\"\n"
                + "	SET id_curso=" + idCurso + "\n"
                + "	WHERE id_almn_curso =" + idAlmnCurso + "; \n";
        nsqlMatriUpdate = nsqlMatriUpdate + nsql;
    }

    public boolean actualizarMatricula() {
        System.out.println(nsqlMatriUpdate);
        if (conecta.nosql(nsqlMatriUpdate) == null) {
            JOptionPane.showMessageDialog(null, "Editamos correctamente la matricula.");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "No pudimos editar la matricula, revise \n"
                    + "su conexion a internet.");
            return false;
        }
//        return true;
    }

    public void borrarActualizarMatricula() {
        nsqlMatriUpdate = "";
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

    /**
     * Buscamos los cursos en los que esta matriculado un alumno, en un ciclo.
     *
     * @param idAlm
     * @param idPrd
     * @return
     */
    public ArrayList<AlumnoCursoMD> buscarCursosAlmPeriodo(int idAlm, int idPrd) {
        String sql = "SELECT id_almn_curso, c.id_curso, \n"
                + "c.id_materia, materia_nombre, curso_nombre, "
                + "curso_ciclo\n"
                + "FROM public.\"AlumnoCurso\" ac, public.\"Cursos\" c, \n"
                + "public.\"Materias\" m \n"
                + "	WHERE c.id_curso = ac.id_curso\n"
                + "	AND m.id_materia = c.id_materia\n"
                + "	AND id_alumno = " + idAlm + " AND id_prd_lectivo = " + idPrd + " "
                + "     AND almn_curso_estado <> 'RETIRADO';";
        ArrayList<AlumnoCursoMD> cursos = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    AlumnoCursoMD ac = new AlumnoCursoMD();
                    ac.setId(rs.getInt("id_almn_curso"));
                    CursoMD c = new CursoMD();
                    c.setId(rs.getInt("id_curso"));
                    MateriaMD m = new MateriaMD();
                    m.setId(rs.getInt("id_materia"));
                    m.setNombre(rs.getString("materia_nombre"));
                    c.setMateria(m);
                    c.setNombre(rs.getString("curso_nombre"));
                    c.setCiclo(rs.getInt("curso_ciclo"));
                    ac.setCurso(c);

                    cursos.add(ac);
                }
                return cursos;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No pudimos consultar cursos por alumno y periodo    ");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<AlumnoCursoMD> buscarClasesAlumnoCurso(String aguja) {
        String sql = "SELECT ac.id_almn_curso, almn_curso_estado, \n"
                + "ac.id_curso, materia_nombre, persona_identificacion, \n"
                + "persona_primer_nombre, persona_primer_apellido, prd_lectivo_nombre\n"
                + "FROM public.\"AlumnoCurso\" ac, public.\"Cursos\" c, \n"
                + "public.\"Materias\" m, public.\"Alumnos\" a, \n"
                + "public.\"Personas\" p, public.\"PeriodoLectivo\" pl \n"
                + "WHERE c.id_curso = ac.id_curso AND \n"
                + "m.id_materia = c.id_materia AND \n"
                + "a.id_alumno = ac.id_alumno AND \n"
                + "pl.id_prd_lectivo = c.id_prd_lectivo AND \n"
                + "p.id_persona = a.id_persona AND(\n"
                + "	persona_identificacion ILIKE '%" + aguja + "%' OR \n"
                + "	persona_primer_nombre || ' ' || persona_segundo_nombre \n"
                + "	|| ' ' || persona_primer_apellido || ' ' || persona_segundo_apellido \n"
                + "	ILIKE '%" + aguja + "%'  \n"
                + "OR persona_primer_nombre || ' ' || persona_primer_apellido ILIKE '%" + aguja + "%') \n"
                + "AND prd_lectivo_estado = true;";
        return consultaParaTblRetirados(sql);
    }

    public ArrayList<AlumnoCursoMD> cargarClasesAlumnoCursoPorPrd(int idPrd) {
        String sql = "SELECT ac.id_almn_curso, almn_curso_estado, \n"
                + "ac.id_curso, materia_nombre, persona_identificacion, \n"
                + "persona_primer_nombre, persona_primer_apellido, prd_lectivo_nombre \n"
                + "FROM public.\"AlumnoCurso\" ac, public.\"Cursos\" c, \n"
                + "public.\"Materias\" m, public.\"Alumnos\" a, \n"
                + "public.\"Personas\" p, public.\"PeriodoLectivo\" pl\n"
                + "WHERE c.id_curso = ac.id_curso AND \n"
                + "m.id_materia = c.id_materia AND \n"
                + "a.id_alumno = ac.id_alumno AND \n"
                + "pl.id_prd_lectivo = c.id_prd_lectivo AND \n"
                + "p.id_persona = a.id_persona AND\n"
                + "c.id_prd_lectivo = " + idPrd + " \n"
                + "AND prd_lectivo_estado = true;";
        return consultaParaTblRetirados(sql);
    }

    private ArrayList<AlumnoCursoMD> consultaParaTblRetirados(String sql) {
        ArrayList<AlumnoCursoMD> almns = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    AlumnoCursoMD ac = new AlumnoCursoMD();
                    CursoMD c = new CursoMD();
                    c.setId(rs.getInt("id_curso"));
                    ac.setId(rs.getInt("id_almn_curso"));
                    ac.setEstado(rs.getString("almn_curso_estado"));
                    AlumnoMD a = new AlumnoMD();
                    a.setPrimerNombre(rs.getString("persona_primer_nombre"));
                    a.setPrimerApellido(rs.getString("persona_primer_apellido"));
                    a.setIdentificacion(rs.getString("persona_identificacion"));

                    MateriaMD m = new MateriaMD();
                    m.setNombre(rs.getString("materia_nombre"));

                    PeriodoLectivoMD p = new PeriodoLectivoMD();
                    p.setNombre_PerLectivo(rs.getString("prd_lectivo_nombre"));
                    c.setPeriodo(p);

                    c.setMateria(m);
                    ac.setAlumno(a);
                    ac.setCurso(c);

                    almns.add(ac);
                }
                return almns;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No se pudieron consultar las clases de un alumno " + e.getMessage());
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
    
        public ArrayList<AlumnoCursoMD> cargarAlumnosCursosPorCicloTbl(int ciclo, int idPrd) {
        String sql = "SELECT DISTINCT c.curso_nombre,  \n"
                + "persona_primer_nombre, persona_segundo_nombre, persona_primer_apellido, \n"
                + "persona_segundo_apellido, persona_identificacion\n"
                + "FROM public.\"AlumnoCurso\" ac, public.\"Alumnos\" a, public.\"Personas\" p, \n"
                + "public.\"Cursos\" c\n"
                + "WHERE a.id_alumno = ac.id_alumno AND \n"
                + "p.id_persona = a.id_persona AND\n"
                + "c.curso_ciclo = " + ciclo + " AND\n"
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
                    cu.setNombre(rs.getString("curso_nombre"));
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

    public static List<AlumnoCursoBD> selectWhere(String cursoNombre, String nombreMateria, int idDocente, int idPeriodo) {

        String SELECT = "SELECT\n"
                + "\"public\".\"AlumnoCurso\".id_alumno,\n"
                + "\"public\".\"AlumnoCurso\".almn_curso_asistencia,\n"
                + "\"public\".\"AlumnoCurso\".almn_curso_estado,\n"
                + "\"public\".\"AlumnoCurso\".almn_curso_num_faltas,\n"
                + "\"public\".\"AlumnoCurso\".almn_curso_activo,\n"
                + "\"public\".\"Personas\".persona_identificacion,\n"
                + "\"public\".\"Personas\".persona_primer_apellido,\n"
                + "\"public\".\"Personas\".persona_segundo_apellido,\n"
                + "\"public\".\"Personas\".persona_primer_nombre,\n"
                + "\"public\".\"Personas\".persona_segundo_nombre,\n"
                + "\"public\".\"AlumnoCurso\".almn_curso_nota_final,\n"
                + "\"public\".\"AlumnoCurso\".id_almn_curso,\n"
                + "\"public\".\"AlumnoCurso\".id_curso\n"
                + "FROM\n"
                + "\"public\".\"AlumnoCurso\"\n"
                + "INNER JOIN \"public\".\"Cursos\" ON \"public\".\"AlumnoCurso\".id_curso = \"public\".\"Cursos\".id_curso\n"
                + "INNER JOIN \"public\".\"PeriodoLectivo\" ON \"public\".\"Cursos\".id_prd_lectivo = \"public\".\"PeriodoLectivo\".id_prd_lectivo\n"
                + "INNER JOIN \"public\".\"Jornadas\" ON \"public\".\"Cursos\".id_jornada = \"public\".\"Jornadas\".id_jornada\n"
                + "INNER JOIN \"public\".\"Materias\" ON \"public\".\"Cursos\".id_materia = \"public\".\"Materias\".id_materia\n"
                + "INNER JOIN \"public\".\"Alumnos\" ON \"public\".\"AlumnoCurso\".id_alumno = \"public\".\"Alumnos\".id_alumno\n"
                + "INNER JOIN \"public\".\"Personas\" ON \"public\".\"Alumnos\".id_persona = \"public\".\"Personas\".id_persona\n"
                + "WHERE\n"
                + "\"public\".\"Cursos\".id_docente = " + idDocente + " AND\n"
                + "\"public\".\"PeriodoLectivo\".id_prd_lectivo = " + idPeriodo + " AND\n"
                + "\"public\".\"Cursos\".curso_nombre = '" + cursoNombre + "' AND\n"
                + "\"public\".\"Materias\".materia_nombre = '" + nombreMateria + "'\n"
                //+ "\"public\".\"AlumnoCurso\"almn_curso_activo IS TRUE"
                + "ORDER BY\n"
                + "\"public\".\"Personas\".persona_primer_apellido, \"public\".\"Personas\".persona_segundo_apellido ASC";

        System.out.println(SELECT);

        List<AlumnoCursoBD> lista = new ArrayList();

        try {

            PreparedStatement stmt = ResourceManager.getConnection().prepareStatement(SELECT);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AlumnoCursoBD alumnoCurso = new AlumnoCursoBD();

                    alumnoCurso.setId(rs.getInt("id_almn_curso"));
                    alumnoCurso.setAsistencia(rs.getString("almn_curso_asistencia"));
                    alumnoCurso.setEstado(rs.getString("almn_curso_estado"));
                    alumnoCurso.setNumFalta(rs.getInt("almn_curso_num_faltas"));
                    alumnoCurso.setNotaFinal(rs.getDouble("almn_curso_nota_final"));

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
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lista;

    }

    public boolean editar() {
        String UPDATE = "UPDATE \"AlumnoCurso\" \n"
                + "SET \n"
                + "almn_curso_nota_final = " + getNotaFinal() + ", \n"
                + "almn_curso_estado = '" + getEstado() + "',\n"
                + "almn_curso_asistencia = '" + getAsistencia() + "',\n"
                + "almn_curso_num_faltas = " + getNumFalta() + "\n"
                + "WHERE \n"
                + "id_almn_curso = " + getId() + ";";

        System.out.println(UPDATE);
        return ResourceManager.Statement(UPDATE) == null;

    }

}
