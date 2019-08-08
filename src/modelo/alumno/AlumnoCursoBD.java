package modelo.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import modelo.ConectarDB;
import modelo.ConnDBPool;
import modelo.curso.CursoMD;
import modelo.materia.MateriaMD;
import modelo.notas.NotasBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.AlumnoMD;

/**
 *
 * @author Johnny
 */
public class AlumnoCursoBD extends AlumnoCursoMD {

    private ConectarDB conecta;
    private String nsqlMatri = "", nsqlMatriUpdate = "";

    private final ConnDBPool pool;
    private Connection conn;
    private ResultSet rst;

    {
        pool = new ConnDBPool();
    }

    public AlumnoCursoBD(ConectarDB conecta) {
        this.conecta = conecta;
    }

    public AlumnoCursoBD() {
    }

    /**
     * La sentencia que se va a enviar se le manda un string vacio.
     */
    public void borrarMatricula() {
        nsqlMatri = "";
    }

    public void agregarMatricula(int idAlmn, int idCurso, int numMatricula) {
        String nsql = "\nINSERT INTO public.\"AlumnoCurso\"(\n"
                + "id_alumno, id_curso, almn_curso_num_matricula)\n"
                + "VALUES (" + idAlmn + ", " + idCurso + ", " + numMatricula + ");";
        nsqlMatri = nsqlMatri + nsql;
    }

    public boolean guardarAlmnCurso() {
        PreparedStatement ps = conecta.getPS(nsqlMatri);
        if (conecta.nosql(ps) == null) {
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
        PreparedStatement ps = conecta.getPS(nsqlMatriUpdate);
        if (conecta.nosql(ps) == null) {
            JOptionPane.showMessageDialog(null, "Editamos correctamente la matricula.");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "No pudimos editar la matricula, revise \n"
                    + "su conexion a internet.");
            return false;
        }
    }

    public boolean editarNumMatricula(int idAlmnCurso, int numMatricula) {
        String nosql = "UPDATE public.\"AlumnoCurso\"\n"
                + "	SET almn_curso_num_matricula = ?\n"
                + "	WHERE id_almn_curso = ?;";
        PreparedStatement ps = conecta.getPS(nosql);
        try {
            ps.setInt(1, numMatricula);
            ps.setInt(2, idAlmnCurso);
        } catch (SQLException e) {
            System.out.println("No pudimos preparar le statement: " + e.getMessage());
        }
        if (conecta.nosql(ps) == null) {
            JOptionPane.showMessageDialog(null, "Editamos correctamente el numero de matricula.");
            return true;
        } else {
            return false;
        }
    }

    public void borrarActualizarMatricula() {
        nsqlMatriUpdate = "";
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
        PreparedStatement ps = conecta.getPS(sql);
        ArrayList<AlumnoCursoMD> cursos = new ArrayList();
        ResultSet rs = conecta.sql(ps);
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
                ps.getConnection().close();
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
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
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
                ps.getConnection().close();
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
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
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
                ps.getConnection().close();
                return almns;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No se pudieron consultar los cursos");
            return null;
        }
    }

    public List<AlumnoCursoBD> selectWhere(String cursoNombre, String nombreMateria, int idDocente, int idPeriodo) {

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
                + "\"public\".\"Cursos\".id_docente = ? AND\n"
                + "\"public\".\"PeriodoLectivo\".id_prd_lectivo = ? AND\n"
                + "\"public\".\"Cursos\".curso_nombre = ? AND\n"
                + "\"public\".\"Materias\".materia_nombre = ? AND\n"
                + "\"public\".\"AlumnoCurso\".almn_curso_activo IS TRUE\n"
                + "ORDER BY\n"
                + "\"public\".\"Personas\".persona_primer_apellido, \"public\".\"Personas\".persona_segundo_apellido ASC";

        List<AlumnoCursoBD> lista = new ArrayList();
        Map<Integer, Object> parametros = new HashMap<>();
        parametros.put(1, idDocente);
        parametros.put(2, idPeriodo);
        parametros.put(3, cursoNombre);
        parametros.put(4, nombreMateria);

        try {
            conn = pool.getConnection();
            rst = pool.ejecutarQuery(SELECT, conn, parametros);
            NotasBD notasBD = new NotasBD();
            System.out.println(pool.getStmt().toString());
            while (rst.next()) {
                AlumnoCursoBD alumnoCurso = new AlumnoCursoBD();

                alumnoCurso.setId(rst.getInt("id_almn_curso"));
                alumnoCurso.setAsistencia(rst.getString("almn_curso_asistencia"));
                alumnoCurso.setEstado(rst.getString("almn_curso_estado"));
                alumnoCurso.setNumFalta(rst.getInt("almn_curso_num_faltas"));
                alumnoCurso.setNotaFinal(rst.getDouble("almn_curso_nota_final"));

                AlumnoMD alumno = new AlumnoMD();
                alumno.setId_Alumno(rst.getInt("id_alumno"));
                alumno.setIdentificacion(rst.getString("persona_identificacion"));
                alumno.setPrimerApellido(rst.getString("persona_primer_apellido"));
                alumno.setSegundoApellido(rst.getString("persona_segundo_apellido"));
                alumno.setPrimerNombre(rst.getString("persona_primer_nombre"));
                alumno.setSegundoNombre(rst.getString("persona_segundo_nombre"));

                //Agrego esto el Yoni  
                CursoMD c = new CursoMD();
                c.setId(rst.getInt("id_curso"));

                alumnoCurso.setCurso(c);

                alumnoCurso.setAlumno(alumno);

                List<NotasBD> notas = notasBD.selectWhere(alumnoCurso);

                if (notas.isEmpty()) {
                    System.out.println("\nSIN NOTAS-->" + alumno.getPrimerNombre());
                    System.out.println("" + alumnoCurso.getId());
                    System.out.println("");
                    System.out.println("");
                }

                alumnoCurso.setNotas(notas);

                lista.add(alumnoCurso);

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            pool.closeStmt().close(rst).close(conn);
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

        conn = pool.getConnection();

        return pool.ejecutar(UPDATE, conn, null) == null;

    }

    public List<AlumnoCursoBD> selectParaAsistencia(String cursoNombre, String nombreMateria,
            int idDocente, int idPeriodo, String fecha) {

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
                + "\"public\".\"AlumnoCurso\".id_curso, \n"
                + "(SELECT SUM(public.\"Asistencia\".numero_faltas) FROM public.\"Asistencia\"  "
                + " WHERE public.\"Asistencia\".id_almn_curso = public.\"AlumnoCurso\".id_almn_curso "
                + "AND fecha_asistencia = '" + fecha + "')"
                + "FROM\n"
                + "\"public\".\"AlumnoCurso\"\n"
                + "INNER JOIN \"public\".\"Cursos\" ON \"public\".\"AlumnoCurso\".id_curso = \"public\".\"Cursos\".id_curso\n"
                + "INNER JOIN \"public\".\"PeriodoLectivo\" ON \"public\".\"Cursos\".id_prd_lectivo = \"public\".\"PeriodoLectivo\".id_prd_lectivo\n"
                + "INNER JOIN \"public\".\"Jornadas\" ON \"public\".\"Cursos\".id_jornada = \"public\".\"Jornadas\".id_jornada\n"
                + "INNER JOIN \"public\".\"Materias\" ON \"public\".\"Cursos\".id_materia = \"public\".\"Materias\".id_materia\n"
                + "INNER JOIN \"public\".\"Alumnos\" ON \"public\".\"AlumnoCurso\".id_alumno = \"public\".\"Alumnos\".id_alumno\n"
                + "INNER JOIN \"public\".\"Personas\" ON \"public\".\"Alumnos\".id_persona = \"public\".\"Personas\".id_persona\n"
                + "WHERE\n"
                + "\"public\".\"Cursos\".id_docente = ? AND\n"
                + "\"public\".\"PeriodoLectivo\".id_prd_lectivo = ? AND\n"
                + "\"public\".\"Cursos\".curso_nombre = ? AND\n"
                + "\"public\".\"Materias\".materia_nombre = ? AND \n"
                + "\"public\".\"AlumnoCurso\".almn_curso_activo = TRUE \n"
                + "ORDER BY\n"
                + "\"public\".\"Personas\".persona_primer_apellido, \"public\".\"Personas\".persona_segundo_apellido ASC";

        System.out.println(SELECT);

        List<AlumnoCursoBD> lista = new ArrayList();
        Map<Integer, Object> parametros = new HashMap<>();
        parametros.put(1, idDocente);
        parametros.put(2, idPeriodo);
        parametros.put(3, cursoNombre);
        parametros.put(4, nombreMateria);

        try {
            conn = pool.getConnection();
            rst = pool.ejecutarQuery(SELECT, conn, parametros);
            NotasBD notasBD = new NotasBD();
            while (rst.next()) {
                AlumnoCursoBD alumnoCurso = new AlumnoCursoBD();

                alumnoCurso.setId(rst.getInt("id_almn_curso"));
                alumnoCurso.setAsistencia(rst.getString("almn_curso_asistencia"));
                alumnoCurso.setEstado(rst.getString("almn_curso_estado"));
                alumnoCurso.setNumFalta(rst.getInt("almn_curso_num_faltas"));
                alumnoCurso.setNotaFinal(rst.getDouble("almn_curso_nota_final"));

                AlumnoMD alumno = new AlumnoMD();
                alumno.setId_Alumno(rst.getInt("id_alumno"));
                alumno.setIdentificacion(rst.getString("persona_identificacion"));
                alumno.setPrimerApellido(rst.getString("persona_primer_apellido"));
                alumno.setSegundoApellido(rst.getString("persona_segundo_apellido"));
                alumno.setPrimerNombre(rst.getString("persona_primer_nombre"));
                alumno.setSegundoNombre(rst.getString("persona_segundo_nombre"));

                //Agrego esto el Yoni  
                CursoMD c = new CursoMD();
                c.setId(rst.getInt("id_curso"));

                alumnoCurso.setCurso(c);

                alumnoCurso.setAlumno(alumno);

                alumnoCurso.setFaltas(rst.getInt(14));

                List<NotasBD> notas = notasBD.selectWhere(alumnoCurso);

                alumnoCurso.setNotas(notas);

                lista.add(alumnoCurso);

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            pool.closeStmt().close(rst).close(conn);
        }
        return lista;

    }

}
