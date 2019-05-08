package modelo.curso;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.ConectarDB;
import modelo.ResourceManager;
import modelo.jornada.JornadaBD;
import modelo.jornada.JornadaMD;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;

/**
 *
 * @author arman
 */
public class CursoBD extends CursoMD {

    private final MateriaBD mat;
    private final PeriodoLectivoBD prd;
    private final DocenteBD doc;
    private final JornadaBD jrd;
    private final ConectarDB conecta;

    public CursoBD(ConectarDB conecta) {
        this.conecta = conecta;
        this.mat = new MateriaBD(conecta);
        this.prd = new PeriodoLectivoBD(conecta);
        this.doc = new DocenteBD(conecta);
        this.jrd = new JornadaBD(conecta);
    }

    public void iniciarIngresoNotas() {
        String nsql = "INSERT INTO public.\"IngresoNotas\"( id_curso)\n"
                + "	VALUES ();";
        if (conecta.nosql(nsql) == null) {
            JOptionPane.showMessageDialog(null, "Se incio ingreso notas.");
        }
    }

    public void guardarCurso() {
        String nsql = "INSERT INTO public.\"Cursos\"(\n"
                + "	id_materia, id_prd_lectivo, id_docente, id_jornada, \n"
                + "	curso_nombre, curso_capacidad, curso_ciclo,\n"
                + "	curso_paralelo)\n"
                + "	VALUES (" + getMateria().getId() + ", " + getPeriodo().getId_PerioLectivo()
                + ", " + getDocente().getIdDocente() + ", " + getJornada().getId()
                + ", '" + getNombre() + "', " + getCapacidad() + ", " + getCiclo()
                + ", '" + getParalelo() + "');";
        if (conecta.nosql(nsql) == null) {
            JOptionPane.showMessageDialog(null, "Se guardo correctamente el curso " + getNombre());
        }
    }

    public void editarCurso(int idCurso) {
        String nsql = "UPDATE public.\"Cursos\"\n"
                + "	SET id_materia=" + getMateria().getId() + ", "
                + " id_prd_lectivo=" + getPeriodo().getId_PerioLectivo() + ", "
                + "id_docente= " + getDocente().getIdDocente() + ", "
                + "id_jornada=" + getJornada().getId() + ", "
                + "curso_nombre='" + getNombre() + "', curso_capacidad=" + getCapacidad() + ", "
                + "curso_ciclo=" + getCiclo() + ", "
                + "curso_paralelo= '" + getParalelo() + "'\n"
                + "	WHERE id_curso = " + idCurso + ";";
        if (conecta.nosql(nsql) == null) {
            JOptionPane.showMessageDialog(null, "Se edito correctamente el curso " + getNombre());
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo editar el curso " + getNombre());
        }
    }

    public void eliminarCurso(int idCurso) {
        String nsql = "UPDATE public.\"Cursos\" "
                + "SET curso_activo = false "
                + "WHERE id_curso = " + idCurso + ";";
        if (conecta.nosql(nsql) == null) {
            JOptionPane.showMessageDialog(null, "Se elimino correctamente. ");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar correctamente, \n"
                    + "compruebe su conexion a internet.");
        }
    }

    public void activarCurso(int idCurso) {
        String nsql = "UPDATE public.\"Cursos\" "
                + "SET curso_activo = true "
                + "WHERE id_curso = " + idCurso + ";";
        if (conecta.nosql(nsql) == null) {
            JOptionPane.showMessageDialog(null, "Se activo correctamente. ");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo activar,"
                    + "compruebe su conexion a internet.");
        }
    }

    public int numAlumnos(int idCurso) {
        int num = 0;
        String sql = "SELECT count(id_curso) "
                + "FROM public.\"AlumnoCurso\" "
                + "WHERE id_curso =" + idCurso + ";";
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    num = rs.getInt(1);
                }
                return num;
            } else {
                return 0;
            }
        } catch (SQLException e) {
            System.out.println("No pudimos consultar cursos. ");
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public ArrayList<CursoMD> cargarCursos() {
        String sql = "SELECT id_curso, materia_nombre, \n"
                + "persona_primer_nombre, persona_primer_apellido, \n"
                + "curso_nombre, curso_capacidad, curso_ciclo, \n"
                + "c.id_prd_lectivo, c.id_materia, c.id_docente, \n"
                + "prd_lectivo_nombre \n"
                + "FROM public.\"Cursos\" c, public.\"Materias\" m, \n"
                + "public.\"Docentes\" d, public.\"Personas\" p, \n"
                + "public.\"PeriodoLectivo\" pl \n"
                + "WHERE m.id_materia = c.id_materia AND \n"
                + "d.id_docente = c.id_docente AND \n"
                + "p.id_persona = d.id_persona AND persona_activa = true AND\n"
                + "docente_activo = true AND \n"
                + "pl.id_prd_lectivo = c.id_prd_lectivo AND \n"
                + "prd_lectivo_activo = true AND curso_activo = true;";
        return consultarCursos(sql);
    }

    /**
     * Cargamos los cursos eliminados
     *
     * @return
     */
    public ArrayList<CursoMD> cargarCursosEliminados() {
        String sql = "SELECT id_curso, materia_nombre, \n"
                + "persona_primer_nombre, persona_primer_apellido, \n"
                + "curso_nombre, curso_capacidad, curso_ciclo, \n"
                + "c.id_prd_lectivo, c.id_materia, c.id_docente, \n"
                + "prd_lectivo_nombre \n"
                + "FROM public.\"Cursos\" c, public.\"Materias\" m, \n"
                + "public.\"Docentes\" d, public.\"Personas\" p, \n"
                + "public.\"PeriodoLectivo\" pl \n"
                + "WHERE m.id_materia = c.id_materia AND \n"
                + "d.id_docente = c.id_docente AND \n"
                + "p.id_persona = d.id_persona AND persona_activa = true AND\n"
                + "docente_activo = true AND \n"
                + "pl.id_prd_lectivo = c.id_prd_lectivo AND \n"
                + "prd_lectivo_activo = true AND curso_activo = false;";
        return consultarCursos(sql);
    }

    public ArrayList<CursoMD> cargarCursosPorPeriodo(int idPrdLectivo) {
        String sql = "SELECT id_curso, materia_nombre, \n"
                + "persona_primer_nombre, persona_primer_apellido, \n"
                + "curso_nombre, curso_capacidad, curso_ciclo, \n"
                + "c.id_prd_lectivo, c.id_materia, c.id_docente, prd_lectivo_nombre\n"
                + "FROM public.\"Cursos\" c, public.\"Materias\" m, \n"
                + "public.\"Docentes\" d, public.\"Personas\" p, \n"
                + "public.\"PeriodoLectivo\" pl \n"
                + "WHERE m.id_materia = c.id_materia AND \n"
                + "d.id_docente = c.id_docente AND \n"
                + "p.id_persona = d.id_persona \n"
                + "AND C.id_prd_lectivo = " + idPrdLectivo + " AND \n"
                + "pl.id_prd_lectivo = c.id_prd_lectivo AND \n"
                + "prd_lectivo_activo = true "
                + "AND curso_activo = true;";
        return consultarCursos(sql);
    }

    public ArrayList<CursoMD> cargarCursosPorNombre(String nombre) {
        String sql = "SELECT id_curso, materia_nombre, \n"
                + "persona_primer_nombre, persona_primer_apellido, \n"
                + "curso_nombre, curso_capacidad, curso_ciclo, \n"
                + "c.id_prd_lectivo, c.id_materia, c.id_docente, \n"
                + "prd_lectivo_nombre \n"
                + "FROM public.\"Cursos\" c, public.\"Materias\" m, \n"
                + "public.\"Docentes\" d, public.\"Personas\" p, \n"
                + "public.\"PeriodoLectivo\" pl \n"
                + "WHERE m.id_materia = c.id_materia AND \n"
                + "d.id_docente = c.id_docente AND \n"
                + "p.id_persona = d.id_persona \n"
                + "AND curso_nombre = '" + nombre + "' AND \n"
                + " pl.id_prd_lectivo = c.id_prd_lectivo AND "
                + "curso_activo = true;";
        return consultarCursos(sql);
    }

    public ArrayList<CursoMD> cargarCursosPorNombreYPrdLectivo(String nombre, int idPrdLectivo) {
        String sql = "SELECT id_curso, materia_nombre, \n"
                + "persona_primer_nombre, persona_primer_apellido, \n"
                + "curso_nombre, curso_capacidad, curso_ciclo, \n"
                + "c.id_prd_lectivo, c.id_materia, c.id_docente, \n"
                + "prd_lectivo_nombre \n"
                + "FROM public.\"Cursos\" c, public.\"Materias\" m, \n"
                + "public.\"Docentes\" d, public.\"Personas\" p, \n"
                + "public.\"PeriodoLectivo\" pl \n"
                + "WHERE m.id_materia = c.id_materia AND \n"
                + "d.id_docente = c.id_docente AND \n"
                + "p.id_persona = d.id_persona \n"
                + "AND curso_nombre = '" + nombre + "' AND \n"
                + "c.id_prd_lectivo = " + idPrdLectivo + " AND \n"
                + "pl.id_prd_lectivo = c.id_prd_lectivo AND \n"
                + "prd_lectivo_activo = true "
                + "AND curso_activo = true;";
        return consultarCursos(sql);
    }

    public ArrayList<CursoMD> buscarCursos(String aguja) {
        String sql = "SELECT id_curso, materia_nombre, \n"
                + "persona_primer_nombre, persona_primer_apellido, \n"
                + "curso_nombre, curso_capacidad, curso_ciclo, \n"
                + "c.id_prd_lectivo, c.id_materia, c.id_docente, \n"
                + "prd_lectivo_nombre \n"
                + "FROM public.\"Cursos\" c, public.\"Materias\" m, \n"
                + "public.\"Docentes\" d, public.\"Personas\" p, \n"
                + "public.\"PeriodoLectivo\" pl \n"
                + "WHERE m.id_materia = c.id_materia AND \n"
                + "d.id_docente = c.id_docente AND \n"
                + "p.id_persona = d.id_persona AND \n"
                + "pl.id_prd_lectivo = c.id_prd_lectivo AND\n"
                + "(materia_nombre ILIKE '%" + aguja + "%' OR \n"
                + "persona_primer_nombre || ' ' || persona_segundo_nombre || ' ' || "
                + "persona_primer_apellido || ' ' || persona_segundo_apellido ILIKE '%" + aguja + "%'\n"
                + "OR persona_identificacion ILIKE '%" + aguja + "%') AND \n"
                + "persona_activa = true AND docente_activo = true "
                + "AND curso_activo = true;";
        return consultarCursos(sql);
    }
    
    /**
     * Para consultar los cursos para un formulario
     * @param nombre
     * @param idPrdLectivo
     * @return 
     */
    public ArrayList<CursoMD> buscarCursosPorNombreYPrdLectivo(String nombre, int idPrdLectivo) {
        String sql = "SELECT id_curso, c.id_materia, materia_nombre, "
                + "curso_capacidad, curso_ciclo, "
                + "( SELECT count(*)\n"
                + "  FROM public.\"AlumnoCurso\"\n"
                + "  WHERE id_curso = c.id_curso) \n"
                + "FROM public.\"Cursos\" c, public.\"Materias\" m\n"
                + "WHERE curso_nombre = '" + nombre + "' AND\n"
                + "m.id_materia = c.id_materia AND\n"
                + "id_prd_lectivo = " + idPrdLectivo + " AND curso_activo = true;";
        ArrayList<CursoMD> cursos = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    CursoMD c = new CursoMD();
                    c.setId(rs.getInt("id_curso"));
                    MateriaMD m = new MateriaMD();
                    m.setId(rs.getInt("id_materia"));
                    m.setNombre(rs.getString("materia_nombre"));
                    c.setMateria(m);
                    c.setCapacidad(rs.getInt("curso_capacidad"));
                    c.setCiclo(rs.getInt("curso_ciclo"));
                    c.setNombre(nombre);
                    c.setNumMatriculados(rs.getInt(6));

                    cursos.add(c);
                }
                return cursos;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No pudimos consultar cursos por periodo lectivo");
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Consultamos todas las materias que a tomado un estudiante en un curso
     * especifico
     *
     * @param cedula
     * @param nomCurso
     * @return
     */
    public ArrayList<CursoMD> buscarCursosPorAlumno(String cedula, String nomCurso) {
        String sql = "SELECT id_curso, c.id_materia, c.id_docente, materia_nombre,\n"
                + "persona_primer_nombre, persona_segundo_nombre, persona_primer_apellido, \n"
                + "persona_segundo_apellido\n"
                + "FROM public.\"Cursos\" c, public.\"Materias\" m, public.\"Docentes\" d, \n"
                + "public.\"Personas\" p\n"
                + "WHERE curso_nombre = '" + nomCurso + "' AND \n"
                + "id_curso IN (\n"
                + "	SELECT id_curso \n"
                + "	FROM public.\"AlumnoCurso\" ac, public.\"Alumnos\" a,\n"
                + "	public.\"Personas\" p \n"
                + "	WHERE persona_identificacion = '" + cedula + "' AND\n"
                + "	a.id_persona = p.id_persona AND \n"
                + "	ac.id_alumno = a.id_alumno) AND\n"
                + "m.id_materia = c.id_materia AND\n"
                + "d.id_docente = c.id_docente AND\n"
                + "p.id_persona = d.id_persona AND curso_activo = true ;";
        ArrayList<CursoMD> cursos = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    CursoMD c = new CursoMD();
                    c.setId(rs.getInt("id_curso"));
                    MateriaMD m = new MateriaMD();
                    m.setId(rs.getInt("id_materia"));
                    m.setNombre(rs.getString("materia_nombre"));
                    c.setMateria(m);
                    DocenteMD dc = new DocenteMD();
                    dc.setIdDocente(rs.getInt("id_docente"));
                    dc.setPrimerNombre(rs.getString("persona_primer_nombre"));
                    dc.setSegundoNombre(rs.getString("persona_segundo_nombre"));
                    dc.setPrimerApellido(rs.getString("persona_primer_apellido"));
                    dc.setSegundoApellido(rs.getString("persona_segundo_apellido"));
                    c.setDocente(dc);

                    cursos.add(c);
                }
                return cursos;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No pudimos consultar cursos por alumno. ");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<String> cargarNombreCursos() {
        String sql = "SELECT DISTINCT curso_nombre\n"
                + "FROM public.\"Cursos\" \n"
                + "WHERE curso_activo = true \n"
                + "ORDER BY curso_nombre ;";
        return consultarNombreCursos(sql);
    }

    public ArrayList<String> cargarNombreCursosPorPeriodo(int idPrdLectivo) {
        String sql = "SELECT DISTINCT curso_nombre\n"
                + "FROM public.\"Cursos\" "
                + "WHERE id_prd_lectivo = " + idPrdLectivo + " AND  curso_activo = true \n"
                + "ORDER BY curso_nombre;";
        return consultarNombreCursos(sql);
    }
    
    /**
     * Cargamos los el nombre de los cursos por periodo
     * @param idPrdLectivo
     * @param ciclo
     * @return 
     */
    public ArrayList<String> cargarNombreCursosPorPeriodoCiclo(int idPrdLectivo, int ciclo) {
        String sql = "SELECT DISTINCT curso_nombre\n"
                + "FROM public.\"Cursos\" "
                + "WHERE id_prd_lectivo = " + idPrdLectivo + " "
                + "AND curso_ciclo = "+ciclo+ " AND curso_activo = true\n"
                + "ORDER BY curso_nombre;";
        return consultarNombreCursos(sql);
    }
    
    /**
     * Cargamos el nombre de cursos por periodo, 
     * restringiendo por ciclos.
     * @param idPrdLectivo
     * @param cicloReprobado
     * @param cicloCursado
     * @return 
     */
    public ArrayList<String> cargarNombreCursosPorPeriodo(int idPrdLectivo, int cicloReprobado, int cicloCursado) {
        String sql = "SELECT DISTINCT curso_nombre, curso_ciclo\n"
                + "FROM public.\"Cursos\" "
                + "WHERE id_prd_lectivo = " + idPrdLectivo + " "
                + "AND curso_ciclo >= " + cicloReprobado + " AND curso_ciclo <= " + (cicloCursado + 1) + " "
                + "AND curso_activo = true "
                + "ORDER BY curso_ciclo;";
        return consultarNombreCursos(sql);
    }

    public CursoMD buscarCurso(int idCurso) {
        String sql = "SELECT id_curso, id_materia, id_prd_lectivo, id_docente, id_jornada, \n"
                + "curso_nombre, curso_capacidad, curso_ciclo, curso_paralelo\n"
                + "	FROM public.\"Cursos\" WHERE \"Cursos\".id_curso = " + idCurso + "  "
                + "AND curso_activo = true ;";
        return consultarCurso(sql);
    }

    public CursoMD existeMateriaCursoJornada(int idMateria, int ciclo, int idJornada, int idPrdLectivo, String paralelo) {
        String sql = "SELECT id_curso, id_materia, id_prd_lectivo, id_docente, id_jornada, \n"
                + "curso_nombre, curso_capacidad, curso_ciclo, curso_paralelo\n"
                + "	FROM public.\"Cursos\"  WHERE id_materia = " + idMateria + " AND  "
                + "curso_ciclo = " + ciclo + " AND  id_jornada = " + idJornada + " AND "
                + "id_prd_lectivo = " + idPrdLectivo + " AND curso_paralelo = '" + paralelo + "';";
        return consultarCurso(sql);
    }

    public CursoMD existeDocenteMateria(int idMateria, int idDocente, int idJornada, int idPrdLectivo, int ciclo, String paralelo) {
        String sql = "SELECT id_curso, id_materia, id_prd_lectivo, id_docente, id_jornada, \n"
                + "curso_nombre, curso_capacidad, curso_ciclo, curso_paralelo\n"
                + "FROM public.\"Cursos\" WHERE id_materia = " + idMateria + " AND  "
                + "id_docente = " + idDocente + " AND  id_jornada = " + idJornada + " AND "
                + "id_prd_lectivo = " + idPrdLectivo + " AND curso_ciclo = " + ciclo + " "
                + "AND curso_paralelo = '" + paralelo + "' ;";
        return consultarCurso(sql);
    }

    private ArrayList<CursoMD> consultarCursos(String sql) {
        ArrayList<CursoMD> cursos = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    CursoMD c = obtenerCursoParaTbl(rs);
                    if (c != null) {
                        cursos.add(c);
                    }
                }
                return cursos;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No pudimos consultar cursos. ");
            System.out.println(e.getMessage());
            return null;
        }
    }

    private ArrayList<String> consultarNombreCursos(String sql) {
        ArrayList<String> nombres = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    String nombre = rs.getString("curso_nombre");
                    nombres.add(nombre);
                }
                return nombres;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No se pudieron cargar los nombres de todos los cursos");
            System.out.println(e.getMessage());
            return null;
        }
    }

    private CursoMD consultarCurso(String sql) {
        CursoMD c = null;
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    c = new CursoMD();
                    c.setId(rs.getInt("id_curso"));
                    MateriaMD m = new MateriaMD();
                    m.setId(rs.getInt("id_materia"));
                    c.setMateria(m);
                    PeriodoLectivoMD p = new PeriodoLectivoMD();
                    p.setId_PerioLectivo(rs.getInt("id_prd_lectivo"));

                    c.setPeriodo(p);
                    DocenteMD d = new DocenteMD();
                    d.setIdDocente(rs.getInt("id_docente"));
                    c.setDocente(d);
                    JornadaMD j = new JornadaMD();
                    j.setId(rs.getInt("id_jornada"));
                    c.setJornada(j);
                    c.setNombre(rs.getString("curso_nombre"));
                    c.setCapacidad(rs.getInt("curso_capacidad"));
                    c.setCiclo(rs.getInt("curso_ciclo"));
                    c.setParalelo(rs.getString("curso_paralelo"));
                }
                return c;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No se pudo consultar curso curso ");
            System.out.println(e.getMessage());
            return null;
        }
    }

    private CursoMD obtenerCursoParaTbl(ResultSet rs) {
        CursoMD c = new CursoMD();
        try {
            c.setId(rs.getInt("id_curso"));
            MateriaMD m = new MateriaMD();
            m.setId(rs.getInt("id_materia"));
            m.setNombre(rs.getString("materia_nombre"));
            c.setMateria(m);
            PeriodoLectivoMD p = new PeriodoLectivoMD();
            p.setId_PerioLectivo(rs.getInt("id_prd_lectivo"));
            p.setNombre_PerLectivo(rs.getString("prd_lectivo_nombre"));
            c.setPeriodo(p);
            DocenteMD d = new DocenteMD();
            d.setIdDocente(rs.getInt("id_docente"));
            d.setPrimerNombre(rs.getString("persona_primer_nombre"));
            d.setPrimerApellido(rs.getString("persona_primer_apellido"));
            c.setDocente(d);
            c.setNombre(rs.getString("curso_nombre"));
            c.setCapacidad(rs.getInt("curso_capacidad"));
            c.setCiclo(rs.getInt("curso_ciclo"));
            return c;
        } catch (SQLException e) {
            System.out.println("No pudimos obtener curso");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static List<String> selectParaleloWhere(CursoMD curso) {

        String SELECT = "SELECT DISTINCT\n"
                + "\"public\".\"Cursos\".curso_nombre\n"
                + "FROM\n"
                + "\"public\".\"Cursos\"\n"
                + "INNER JOIN \"public\".\"PeriodoLectivo\" ON \"public\".\"Cursos\".id_prd_lectivo = \"public\".\"PeriodoLectivo\".id_prd_lectivo\n"
                + "WHERE\n"
                + "\"public\".\"Cursos\".id_docente = " + curso.getDocente() + " AND\n"
                + "\"public\".\"Cursos\".curso_ciclo = " + curso.getCiclo() + " AND\n"
                + "\"public\".\"Cursos\".id_prd_lectivo = " + curso.getPeriodo().getId_PerioLectivo() + "";

        System.out.println(SELECT);

        List<String> lista = new ArrayList<>();

        ResultSet rs = ResourceManager.Query(SELECT);

        try {
            while (rs.next()) {

                String paralelo = rs.getString("curso_nombre");

                lista.add(paralelo);

            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lista;
    }

    public static List<String> selectCicloWhere(int idDocente, int idPeriodoLectivo) {

        String SELECT = "SELECT DISTINCT\n"
                + "\"public\".\"Cursos\".curso_nombre\n"
                + "FROM\n"
                + "\"public\".\"Cursos\"\n"
                + "WHERE\n"
                + "\"public\".\"Cursos\".id_docente = " + idDocente + "\n"
                + "AND\n"
                + "\"public\".\"Cursos\".id_prd_lectivo = " + idPeriodoLectivo;

        List<String> lista = new ArrayList<>();

        ResultSet rs = ResourceManager.Query(SELECT);

        try {
            while (rs.next()) {

                lista.add(rs.getString("curso_nombre"));

            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lista;
    }

    public static int selectIdCursoWhere(String paralelo, int ciclo, String nombreJornada, int idDocente, int idPeriodoLectivo) {
        String SELECT = "SELECT\n"
                + "\"public\".\"Cursos\".id_curso\n"
                + "FROM\n"
                + "\"public\".\"Cursos\"\n"
                + "INNER JOIN \"public\".\"Materias\" ON \"public\".\"Cursos\".id_materia = \"public\".\"Materias\".id_materia\n"
                + "INNER JOIN \"public\".\"Jornadas\" ON \"public\".\"Cursos\".id_jornada = \"public\".\"Jornadas\".id_jornada\n"
                + "WHERE\n"
                + "\"public\".\"Cursos\".id_docente = " + idDocente + " AND\n"
                + "\"public\".\"Cursos\".id_prd_lectivo = '" + idPeriodoLectivo + "' AND\n"
                + "\"public\".\"Cursos\".curso_ciclo = " + ciclo + " AND\n"
                + "\"public\".\"Cursos\".curso_paralelo = '" + paralelo + "' AND\n"
                + "\"public\".\"Jornadas\".nombre_jornada = '" + nombreJornada + "'";

        ResultSet rs = ResourceManager.Query(SELECT);

        int idCurso = -1;

        try {
            while (rs.next()) {
                idCurso = rs.getInt("id_curso");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return idCurso;
    }

}
