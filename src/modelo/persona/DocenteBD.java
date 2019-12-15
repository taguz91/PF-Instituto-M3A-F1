package modelo.persona;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import modelo.ConnDBPool;
import modelo.curso.CursoMD;
import modelo.materia.MateriaMD;
import utils.CONBD;
import utils.M;

public class DocenteBD extends CONBD {

    private PersonaMD p;

    private final ConnDBPool pool;
    private Connection conn;
    private ResultSet res;

    {
        pool = new ConnDBPool();
    }

    private static DocenteBD DBD;

    public static DocenteBD single() {
        if (DBD == null) {
            DBD = new DocenteBD();
        }
        return DBD;
    }

    public boolean InsertarDocente(DocenteMD d) {
        String nsql = "INSERT INTO public.\"Docentes\"( "
                + "id_persona, "
                + "docente_codigo, "
                + "docente_otro_trabajo, "
                + "docente_categoria, "
                + "docente_fecha_contrato, "
                + "docente_tipo_tiempo, "
                + "docente_activo, "
                + "docente_observacion, "
                + "docente_capacitador, "
                + "docente_titulo, "
                + "docente_abreviatura)\n"
                + "VALUES (" + d.getIdPersona() + ", "
                + "'" + d.getCodigo() + "', "
                + d.isDocenteOtroTrabajo() + ","
                + d.getDocenteCategoria() + ", '"
                + d.getFechaInicioContratacion() + "', '"
                + d.getDocenteTipoTiempo() + "', "
                + "true, "
                + "NULL, "
                + d.isDocenteCapacitador() + ", '"
                + d.getTituloDocente() + "', '"
                + d.getAbreviaturaDocente() + "' );";

        if (d.getFechaFinContratacion() != null) {
            nsql = "INSERT INTO public.\"Docentes\"( "
                    + "id_persona, "
                    + "docente_codigo, "
                    + "docente_otro_trabajo, "
                    + "docente_categoria, "
                    + "docente_fecha_contrato, "
                    + "docente_fecha_fin, "
                    + "docente_tipo_tiempo, "
                    + "docente_activo, "
                    + "docente_observacion, "
                    + "docente_capacitador, "
                    + "docente_titulo, "
                    + "docente_abreviatura) "
                    + "	VALUES ("
                    + d.getIdPersona() + ", '"
                    + d.getCodigo() + "', "
                    + d.isDocenteOtroTrabajo() + ","
                    + d.getDocenteCategoria() + ", '"
                    + d.getFechaInicioContratacion() + "', '"
                    + d.getFechaFinContratacion() + "', '"
                    + d.getDocenteTipoTiempo() + "', "
                    + "true, "
                    + "NULL, "
                    + d.isDocenteCapacitador() + ", '"
                    + d.getTituloDocente() + "', '"
                    + d.getAbreviaturaDocente() + "' );";
        }
        return CON.executeNoSQL(nsql);
    }

    public DocenteMD capturarIdDocente(String identificacion, int idDocente) {
        String sql = "SELECT "
                + "id_docente, "
                + "docente_codigo "
                + "FROM public.\"Docentes\" "
                + "WHERE docente_codigo LIKE '%" + identificacion + "%'"
                + " OR id_docente = " + idDocente + ";";
        DocenteMD d = new DocenteMD();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                d.setCodigo(rs.getString("docente_codigo"));
                d.setIdDocente(rs.getInt("id_docente"));
            }
            return d;
        } catch (SQLException e) {
            M.errorMsg("No capturamos el id del docente. " + e.getMessage());
            return null;
        } finally {
            CON.cerrarCONPS(ps);
        }
    }

    public boolean reasignarAlumnoCurso(int curso_Old, int curso_New) {
        boolean exito = false;
        CallableStatement cStmt;
        try {
            conn = pool.getConnection();
            cStmt = conn.prepareCall("SELECT reasignarMaterias(?, ?);");
            cStmt.setInt(1, curso_Old);
            cStmt.setInt(2, curso_New);
            exito = pool.call(cStmt) == null;
        } catch (SQLException ex) {
            M.errorMsg("Error al reasignar alumno curso. " + ex.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                M.errorMsg("Error al cerrar la conexion. " + ex.getMessage());
            }
        }
        return exito;
    }

    public boolean reasignarNotas(int curso_Old, int curso_New) {

        boolean exito = false;
        CallableStatement cStmt;
        try {
            conn = pool.getConnection();
            cStmt = conn.prepareCall("SELECT reasignarNotas(?, ?)");
            cStmt.setInt(1, curso_Old);
            cStmt.setInt(2, curso_New);

            exito = pool.call(cStmt) == null;
        } catch (SQLException ex) {
            M.errorMsg("Error al reasignar notas. " + ex.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                M.errorMsg("Error al cerrar la conexion. " + ex.getMessage());
            }
        }
        return exito;
    }

    private ArrayList<DocenteMD> consultarDocenteTbl(String sql) {
        ArrayList<DocenteMD> pers = new ArrayList();
        DocenteMD d;
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                d = new DocenteMD();
                d.setCodigo(rs.getString("docente_codigo"));
                d.setIdDocente(rs.getInt("id_docente"));
                d.setIdPersona(rs.getInt("id_persona"));
                d.setDocenteTipoTiempo(rs.getString("docente_tipo_tiempo"));
                d.setPrimerNombre(rs.getString("persona_primer_nombre"));
                d.setSegundoNombre(rs.getString("persona_segundo_nombre"));
                d.setPrimerApellido(rs.getString("persona_primer_apellido"));
                d.setSegundoApellido(rs.getString("persona_segundo_apellido"));
                d.setCorreo(rs.getString("persona_correo"));
                d.setCelular(rs.getString("persona_celular"));
                d.setIdentificacion(rs.getString("persona_identificacion"));

                pers.add(d);
            }
        } catch (SQLException e) {
            M.errorMsg("No pudimos consultar docente para tabla. " + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return pers;
    }

    //metodo para buscar una persona 
    public ArrayList<String> existeDocente(String cedula) {
        ArrayList<String> datos = new ArrayList();
        String sql = "SELECT id_docente, docente_codigo \n"
                + "FROM public.\"Docentes\" \n"
                + "WHERE docente_codigo ='" + cedula + "';";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                datos.add(rs.getString("id_docente"));
                datos.add(rs.getString("docente_codigo"));
            }
        } catch (SQLException e) {
            M.errorMsg("no es posible realizar la consulta buscar persona" + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return datos;

    }

    //metodo para buscar una persona 
    public ArrayList<String> buscarPersonaDocente(String cedula) {
        ArrayList<String> datos = new ArrayList();
        String sql = "SELECT \"Personas\".id_persona, persona_primer_nombre \n"
                + "FROM public.\"Personas\", public.\"Docentes\"\n"
                + "WHERE persona_identificacion = '" + cedula + "'\n"
                + "and \"Personas\".id_persona = \"Docentes\".id_persona;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                datos.add(rs.getString("id_persona"));
                datos.add(rs.getString("persona_primer_nombre"));
            }
        } catch (SQLException e) {
            M.errorMsg("No es posible realizar la consulta buscar persona" + e);
        } finally {
            CON.cerrarCONPS(ps);
        }
        return datos;
    }

    public DocenteMD buscarDocenteid(int id) {
        String sql = "SELECT id_docente, "
                + "id_persona, "
                + "docente_codigo, "
                + "docente_otro_trabajo, "
                + "docente_categoria, "
                + "docente_fecha_contrato, "
                + "docente_fecha_fin, "
                + "docente_tipo_tiempo, "
                + "docente_activo, "
                + "docente_observacion, "
                + "docente_capacitador, "
                + "docente_titulo, "
                + "docente_abreviatura "
                + "FROM public.\"Docentes\" "
                + "WHERE id_docente = '" + id + "';";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            DocenteMD doc = new DocenteMD();
            while (rs.next()) {
                doc.setIdDocente(rs.getInt("id_docente"));
                doc.setIdPersona(rs.getInt("id_persona"));
                doc.setCodigo(rs.getString("docente_codigo"));
                if (rs.wasNull()) {
                    doc.setDocenteCapacitador(rs.getBoolean(null));
                } else {
                    doc.setDocenteCapacitador(rs.getBoolean("docente_capacitador"));
                }
                doc.setDocenteCategoria(rs.getInt("docente_categoria"));
                if (rs.wasNull()) {
                    doc.setDocenteOtroTrabajo(rs.getBoolean(null));
                } else {
                    doc.setDocenteOtroTrabajo(rs.getBoolean("docente_otro_trabajo"));
                }
                doc.setDocenteTipoTiempo(rs.getString("docente_tipo_tiempo"));
                // falta estado
                doc.setEstado(rs.getString(""));
                doc.setFechaInicioContratacion(rs.getDate("docente_fecha_contrato").toLocalDate());
                doc.setFechaFinContratacion(rs.getDate("docente_fecha_fin").toLocalDate());
                doc.setTituloDocente(rs.getString("docente_titulo"));
                doc.setAbreviaturaDocente(rs.getString("docente_abreviatura"));
            }
            return doc;
        } catch (SQLException ex) {
            M.errorMsg("Error al consultar docente. " + ex.getMessage());
            return null;
        } finally {
            CON.cerrarCONPS(ps);
        }
    }

    public ArrayList<DocenteMD> cargarDocentes() {
        String sql = "SELECT docente_codigo, id_docente, d.id_persona, docente_tipo_tiempo, \n"
                + "persona_primer_nombre, persona_segundo_nombre,\n"
                + "persona_primer_apellido, persona_segundo_apellido,\n"
                + "persona_celular, persona_correo, persona_identificacion\n"
                + "FROM public.\"Docentes\" d, public.\"Personas\" p \n"
                + "WHERE p.id_persona = d.id_persona AND \n"
                + "docente_activo = true;";
        return consultarDocenteTbl(sql);
    }

    public ArrayList<DocenteMD> cargarDocentesParaReasignarMaterias() {
        String sql = "SELECT docente_codigo, id_docente, d.id_persona, docente_tipo_tiempo, \n"
                + "persona_primer_nombre, persona_segundo_nombre,\n"
                + "persona_primer_apellido, persona_segundo_apellido,\n"
                + "persona_celular, persona_correo, persona_identificacion\n"
                + "FROM public.\"Docentes\" d, public.\"Personas\" p \n"
                + "WHERE p.id_persona = d.id_persona AND \n"
                + "docente_activo = true AND docente_en_funcion = true;";
        return consultarDocenteTbl(sql);
    }

    public ArrayList<DocenteMD> cargarDocentesFinContrato() {
        String sql = "SELECT docente_codigo, id_docente, d.id_persona, docente_tipo_tiempo, \n"
                + "persona_primer_nombre, persona_segundo_nombre,\n"
                + "persona_primer_apellido, persona_segundo_apellido,\n"
                + "persona_celular, persona_correo, persona_identificacion\n"
                + "FROM public.\"Docentes\" d, public.\"Personas\" p \n"
                + "WHERE p.id_persona = d.id_persona AND \n"
                + "docente_activo = true AND docente_en_funcion = false;";
        return consultarDocenteTbl(sql);
    }

    public DocenteMD capturarFecha(int ID) {
        String sql = "SELECT docente_fecha_contrato, "
                + "docente_codigo "
                + "FROM public.\"Docentes\" \n"
                + "WHERE id_persona = " + ID + ";";
        DocenteMD datos = new DocenteMD();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                datos.setFechaInicioContratacion(rs.getDate("docente_fecha_contrato").toLocalDate());
                datos.setCodigo(rs.getString("docente_codigo"));
            }
            return datos;
        } catch (SQLException e) {
            M.errorMsg("No es posible realizar la consulta buscar persona" + e);
            return null;
        } finally {
            CON.cerrarCONPS(ps);
        }
    }

    public ArrayList<DocenteMD> cargarDocentesEliminados() {
        String sql = "SELECT docente_codigo, "
                + "id_docente, "
                + "d.id_persona, "
                + "docente_tipo_tiempo, "
                + "persona_primer_nombre, "
                + "persona_segundo_nombre, "
                + "persona_primer_apellido, "
                + "persona_segundo_apellido, "
                + "persona_celular, "
                + "persona_correo, "
                + "persona_identificacion "
                + "FROM public.\"Docentes\" d, "
                + "public.\"Personas\" p "
                + "WHERE p.id_persona = d.id_persona AND "
                + "docente_activo = false;";
        return consultarDocenteTbl(sql);
    }

    public List<CursoMD> capturarMaterias(int idPeriodo, int idDocente) {
        String nsql = "SELECT m.id_materia, "
                + "c.id_curso, "
                + "m.materia_nombre, "
                + "c.curso_nombre FROM ("
                + " (public.\"Materias\" m JOIN public.\"Cursos\" c USING(id_materia)) JOIN \n"
                + " public.\"PeriodoLectivo\" p USING(id_prd_lectivo)"
                + ") JOIN public.\"Docentes\" d USING(id_docente) WHERE\n"
                + "p.id_prd_lectivo = " + idPeriodo + " "
                + "AND d.id_docente = " + idDocente + " "
                + "AND m.materia_activa = true "
                + "AND p.prd_lectivo_activo = true;";
        PreparedStatement ps = CON.getPSPOOL(nsql);
        List<CursoMD> lista = new ArrayList<>();
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CursoMD c = new CursoMD();
                MateriaMD m = new MateriaMD();
                m.setId(rs.getInt("id_materia"));
                m.setNombre(rs.getString("materia_nombre"));
                c.setMateria(m);
                c.setId(rs.getInt("id_curso"));
                c.setNombre(rs.getString("curso_nombre"));
                lista.add(c);
            }
        } catch (SQLException ex) {
            M.errorMsg("Error al consultar materias " + ex.getMessage());
            return null;
        } finally {
            CON.cerrarCONPS(ps);
        }
        return lista;
    }

    public ArrayList<DocenteMD> cargarDocentesPorCarrera(int idCarrera) {
        ArrayList<DocenteMD> docentes = new ArrayList();
        String sql = "SELECT public.\"Docentes\".id_docente, "
                + "id_persona, "
                + "docente_codigo, "
                + "docente_otro_trabajo, "
                + "docente_categoria, "
                + "docente_fecha_contrato, "
                + "docente_fecha_fin, "
                + "docente_tipo_tiempo, "
                + "docente_activo, "
                + "docente_observacion, "
                + "docente_capacitador, "
                + "docente_titulo, "
                + "docente_abreviatura "
                + "	FROM public.\"Docentes\", public.\"Materias\", public.\"DocentesMateria\"\n"
                + "	WHERE public.\"Materias\".id_carrera = " + idCarrera + " \n"
                + "	AND public.\"DocentesMateria\".id_docente = public.\"Docentes\".id_docente\n"
                + "	AND public.\"DocentesMateria\".id_materia = \"Materias\".id_materia \n"
                + "	GROUP BY \"Docentes\".id_docente, \"Materias\".id_carrera ORDER BY id_docente;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DocenteMD doc = obtenerDocente(rs);
                if (doc != null) {
                    docentes.add(doc);
                }
            }
        } catch (SQLException ex) {
            M.errorMsg("No consultamos docentes. " + ex.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return docentes;
    }

    public ArrayList<DocenteMD> cargarDocentesPorCarreraCiclo(int idCarrera, int ciclo) {
        ArrayList<DocenteMD> docentes = new ArrayList();
        String sql = "SELECT public.\"Docentes\".id_docente, id_persona, docente_codigo, docente_otro_trabajo, \n"
                + "docente_categoria, docente_fecha_contrato,\n"
                + "docente_tipo_tiempo, docente_activo, docente_observacion,\n"
                + "docente_capacitador, docente_titulo, docente_abreviatura\n"
                + "	FROM public.\"Docentes\", public.\"Materias\", public.\"DocentesMateria\"\n"
                + "	WHERE public.\"Materias\".id_carrera = " + idCarrera + " \n"
                + "	AND public.\"DocentesMateria\".id_docente = public.\"Docentes\".id_docente\n"
                + "	AND public.\"DocentesMateria\".id_materia = \"Materias\".id_materia \n"
                + "	AND public.\"Materias\".materia_ciclo = " + ciclo + " \n"
                + "	GROUP BY \"Docentes\".id_docente, \"Materias\".id_carrera ORDER BY id_docente;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DocenteMD doc = obtenerDocente(rs);
                if (doc != null) {
                    docentes.add(doc);
                }
            }
        } catch (SQLException ex) {
            M.errorMsg("No consultamos docentes. " + ex.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return docentes;
    }

    public ArrayList<DocenteMD> cargarDocentesPorMateria(int idMateria) {
        ArrayList<DocenteMD> docentes = new ArrayList();
        String sql = "SELECT d.id_docente, d.id_persona, \n"
                + "docente_abreviatura, \n"
                + "persona_primer_nombre, persona_primer_apellido \n"
                + "FROM public.\"Docentes\" d,  public.\"DocentesMateria\" dm, "
                + "public.\"Personas\"p \n"
                + "WHERE dm.id_materia = " + idMateria + " \n"
                + "AND d.id_docente = dm.id_docente \n"
                + "AND p.id_persona = d.id_persona \n"
                + "AND docente_activo = TRUE\n"
                + "ORDER BY id_docente;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DocenteMD doc = new DocenteMD();
                doc.setPrimerNombre(rs.getString("persona_primer_nombre"));
                doc.setPrimerApellido(rs.getString("persona_primer_apellido"));
                doc.setIdDocente(rs.getInt("id_docente"));
                doc.setIdPersona(rs.getInt("id_persona"));

                docentes.add(doc);
            }
        } catch (SQLException ex) {
            M.errorMsg("No consultamos docentes. " + ex.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return docentes;
    }

    public DocenteMD buscarDocente(int idDocente) {
        DocenteMD d = null;
        String sql = "SELECT id_docente, d.id_persona, docente_codigo, "
                + "docente_otro_trabajo, docente_categoria, "
                + "docente_fecha_contrato,docente_fecha_fin, "
                + " docente_tipo_tiempo, docente_activo,"
                + " docente_observacion, docente_capacitador, docente_titulo, docente_abreviatura, "
                + " docente_en_funcion, "
                + " p.persona_primer_nombre, p.persona_primer_apellido, "
                + " p.persona_segundo_nombre, p.persona_segundo_apellido, \n"
                + " p.persona_identificacion \n"
                + "FROM public.\"Docentes\" d JOIN public.\"Personas\" p USING(id_persona) "
                + "WHERE d.id_docente = " + idDocente + " AND docente_activo = true;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                d = obtenerDocente(rs);
            }
            ps.getConnection().close();
            return d;
        } catch (SQLException e) {
            M.errorMsg("No consultamos docente por di. " + e.getMessage());
            return null;
        } finally {
            CON.cerrarCONPS(ps);
        }
    }

    public DocenteMD obtenerDocente(ResultSet rs) {
        DocenteMD d = new DocenteMD();
        try {
            if (!rs.wasNull()) {
                d.setIdDocente(rs.getInt("id_docente"));
            }
            PersonaMD persona = new PersonaMD();
            persona.setPrimerNombre(rs.getString("persona_primer_nombre"));
            persona.setPrimerApellido(rs.getString("persona_primer_apellido"));
            persona.setIdentificacion(rs.getString("persona_identificacion"));
            persona.setSegundoNombre(rs.getString("persona_segundo_nombre"));
            persona.setSegundoApellido(rs.getString("persona_segundo_apellido"));
            d.setPersona(persona);
            if (!rs.wasNull()) {
                d.setCodigo(rs.getString("docente_codigo"));
            }
            if (rs.wasNull()) {
                d.setDocenteOtroTrabajo(false);
            } else {
                d.setDocenteOtroTrabajo(rs.getBoolean("docente_otro_trabajo"));
            }
            if (rs.getInt("docente_categoria") != 0) {
                d.setDocenteCategoria(rs.getInt("docente_categoria"));
            }
            if (rs.getDate("docente_fecha_contrato") != null) {
                d.setFechaInicioContratacion(rs.getDate("docente_fecha_contrato").toLocalDate());
            }

            if (rs.getDate("docente_fecha_fin") != null) {
                d.setFechaFinContratacion(rs.getDate("docente_fecha_fin").toLocalDate());
            }

            //d.setFechaFinContratacion(rs.getDate("docente_fecha_fin").toLocalDate());
            if (!rs.wasNull()) {
                d.setDocenteTipoTiempo(rs.getString("docente_tipo_tiempo"));
            } else {
                d.setDocenteTipoTiempo(rs.getString("docente_tipo_tiempo"));
                d.setFechaFinContratacion(null);
            }
            if (!rs.wasNull()) {
                d.setDocenteCapacitador(false);
            } else {
                d.setDocenteCapacitador(rs.getBoolean("docente_capacitador"));
            }

            d.setTituloDocente(rs.getString("docente_titulo"));
            d.setAbreviaturaDocente(rs.getString("docente_abreviatura"));
            d.setDocenteEnFuncion(rs.getBoolean("docente_en_funcion"));
            return d;
        } catch (SQLException e) {
            M.errorMsg("Error al mapear docente. " + e.getMessage());
            return null;
        }
    }

    //Sentencia para editar una Persona
    public boolean editarDocente(DocenteMD d) {
        String sql = "UPDATE public.\"Docentes\" SET\n"
                + "docente_otro_trabajo= " + d.isDocenteOtroTrabajo() + ", "
                + "docente_categoria=" + d.getDocenteCategoria() + ","
                + " docente_fecha_contrato= '" + d.getFechaInicioContratacion() + "',  "
                + "docente_tipo_tiempo= '" + d.getDocenteTipoTiempo() + "', "
                + " docente_activo=TRUE, docente_observacion=NULL, "
                + "docente_capacitador= " + d.isDocenteCapacitador()
                + ", docente_titulo= '" + d.getTituloDocente() + "',"
                + " docente_abreviatura= '" + d.getAbreviaturaDocente() + "'  \n"
                + "WHERE id_docente= " + d.getIdDocente() + ";";
        if (d.getFechaFinContratacion() != null) {
            sql = "UPDATE public.\"Docentes\" SET\n"
                    + "	docente_otro_trabajo= " + d.isDocenteOtroTrabajo() + ", "
                    + "docente_categoria=" + d.getDocenteCategoria() + ","
                    + " docente_fecha_contrato= '" + d.getFechaInicioContratacion() + "', "
                    + "docente_fecha_fin='" + d.getFechaFinContratacion() + "', "
                    + "docente_tipo_tiempo= '" + d.getDocenteTipoTiempo() + "', "
                    + "docente_activo=TRUE, docente_observacion=NULL, "
                    + "docente_capacitador= " + d.isDocenteCapacitador()
                    + ", docente_titulo= '" + d.getTituloDocente() + "',"
                    + " docente_abreviatura= '" + d.getAbreviaturaDocente() + "' \n"
                    + "WHERE id_docente= " + d.getIdDocente() + ";";
        }
        return CON.executeNoSQL(sql);
    }

    public boolean deshabilitarCursos(CursoMD curso) {
        String sql = "UPDATE public.\"Cursos\" SET curso_activo = false "
                + "WHERE id_docente = " + curso.getDocente().getIdDocente() + " "
                + "AND id_prd_lectivo = " + curso.getPeriodo().getID() + " "
                + "AND id_materia = " + curso.getMateria().getId()
                + "AND curso_nombre = '" + curso.getNombre() + "';";
        return CON.executeNoSQL(sql);
    }

    public ArrayList<DocenteMD> buscar(String aguja) {
        String sql = "SELECT docente_codigo, id_docente, d.id_persona, docente_tipo_tiempo, \n"
                + "persona_primer_nombre, persona_segundo_nombre,\n"
                + "persona_primer_apellido, persona_segundo_apellido,\n"
                + "persona_celular, persona_correo, persona_identificacion\n"
                + "FROM public.\"Docentes\" d, public.\"Personas\" p \n"
                + "WHERE p.id_persona = d.id_persona AND \n"
                + "docente_activo = true AND (\n"
                + "	persona_primer_nombre || ' ' || persona_segundo_nombre || ' ' ||\n"
                + "	persona_primer_apellido || ' ' || persona_segundo_apellido ILIKE '%" + aguja + "%' OR\n"
                + "	persona_identificacion ILIKE '%" + aguja + "%'\n"
                + ");";
        return consultarDocenteTbl(sql);
    }

    public ArrayList<DocenteMD> buscarReasignarMateria(String aguja) {
        String sql = "SELECT docente_codigo, id_docente, d.id_persona, docente_tipo_tiempo, \n"
                + "persona_primer_nombre, persona_segundo_nombre,\n"
                + "persona_primer_apellido, persona_segundo_apellido,\n"
                + "persona_celular, persona_correo, persona_identificacion\n"
                + "FROM public.\"Docentes\" d, public.\"Personas\" p \n"
                + "WHERE p.id_persona = d.id_persona AND \n"
                + "docente_activo = true AND docente_en_funcion = true AND (\n"
                + "	persona_primer_nombre || ' ' || persona_segundo_nombre || ' ' ||\n"
                + "	persona_primer_apellido || ' ' || persona_segundo_apellido ILIKE '%" + aguja + "%' OR\n"
                + "	persona_identificacion ILIKE '%" + aguja + "%'\n"
                + ");";
        return consultarDocenteTbl(sql);
    }

    public ArrayList<DocenteMD> buscarEliminados(String aguja) {
        String sql = "SELECT docente_codigo,id_docente, d.id_persona, docente_tipo_tiempo, \n"
                + "persona_primer_nombre, persona_segundo_nombre,\n"
                + "persona_primer_apellido, persona_segundo_apellido,\n"
                + "persona_celular, persona_correo, persona_identificacion\n"
                + "FROM public.\"Docentes\" d, public.\"Personas\" p \n"
                + "WHERE p.id_persona = d.id_persona AND \n"
                + "docente_activo = false AND (\n"
                + "	persona_primer_nombre || ' ' || persona_segundo_nombre || ' ' ||\n"
                + "	persona_primer_apellido || ' ' || persona_segundo_apellido ILIKE '%" + aguja + "%' OR\n"
                + "	persona_identificacion ILIKE '%" + aguja + "%'\n"
                + ");";
        return consultarDocenteTbl(sql);
    }

    public DocenteMD buscarDocente(String identificacion) {
        String sql = "SELECT d.id_docente, p.id_persona, d.docente_codigo, d.docente_otro_trabajo, "
                + "d.docente_categoria, d.docente_fecha_contrato, d.docente_fecha_fin, "
                + "d.docente_tipo_tiempo, d.docente_capacitador, d.docente_titulo, d.docente_abreviatura, d.docente_en_funcion,\n"
                + " p.persona_primer_nombre, p.persona_primer_apellido, p.persona_identificacion\n"
                + " FROM public.\"Docentes\" d JOIN public.\"Personas\" p USING(id_persona) "
                + "WHERE d.docente_activo = true AND d.docente_codigo = '" + identificacion + "';";
        //System.out.println(sql);
        return consultarPor(sql);
    }

    public DocenteMD buscarDocenteInactivo(String identificacion) {
        String sql = "SELECT id_docente, id_persona, docente_codigo, docente_otro_trabajo, "
                + "docente_categoria, docente_fecha_contrato, docente_fecha_fin, "
                + "docente_tipo_tiempo, docente_capacitador,docente_titulo,docente_abreviatura\n"
                + "FROM public.\"Docentes\" WHERE "
                + " docente_activo=false and docente_codigo ='" + identificacion + "'";
        return consultarPor(sql);
    }

    //Este metodo unicamente nos devolvera una persona dependiendo de la setencia sql que se envie
    private DocenteMD consultarPor(String sql) {
        DocenteMD d = null;
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                d = obtenerDocente(rs);
            }
            return d;
        } catch (SQLException e) {
            M.errorMsg("No consultamos una persona. " + e.getMessage());
            return null;
        } finally {
            CON.cerrarCONPS(ps);
        }
    }

    public boolean eliminarDocente(DocenteMD a, int aguja) {
        String nsql = "UPDATE public.\"Docentes\" SET\n"
                + " docente_activo = false, docente_observacion = '" + a.getEstado()
                + "' WHERE id_docente = " + aguja + ";";
        return CON.executeNoSQL(nsql);
    }

    public boolean activarDocente(int aguja) {
        String nsql = "UPDATE public.\"Docentes\" SET\n"
                + " docente_activo = true\n"
                + " WHERE id_docente = " + aguja + ";";
        return CON.executeNoSQL(nsql);
    }

    public boolean terminarContrato(DocenteMD docente) {
        String nsql = "UPDATE public.\"Docentes\" SET\n"
                + " docente_fecha_fin = '" + docente.getFechaFinContratacion()
                + "', docente_observacion = '" + docente.getObservacion() + "', docente_en_funcion = false\n"
                + " WHERE id_docente = " + docente.getIdDocente() + ";";
        return CON.executeNoSQL(nsql);
    }

    public HashMap<String, DocenteMD> selectAll() {

        String SELECT = "SELECT \"Docentes\".id_docente,\n"
                + "    \"Docentes\".id_persona,\n"
                + "    \"Docentes\".docente_codigo,\n"
                + "    \"Docentes\".docente_activo,\n"
                + "    \"Personas\".persona_identificacion,\n"
                + "    \"Personas\".persona_primer_apellido,\n"
                + "    \"Personas\".persona_segundo_apellido,\n"
                + "    \"Personas\".persona_primer_nombre,\n"
                + "    \"Personas\".persona_segundo_nombre\n"
                + "   FROM (\"Docentes\"\n"
                + "     JOIN \"Personas\" ON ((\"Docentes\".id_persona = \"Personas\".id_persona)))\n"
                + "ORDER BY persona_primer_apellido";

        HashMap<String, DocenteMD> lista = new HashMap<>();

        conn = pool.getConnection();
        res = pool.ejecutarQuery(SELECT, conn, null);

        try {

            while (res.next()) {

                DocenteMD docente = new DocenteMD();

                docente.setIdDocente(res.getInt("id_docente"));
                docente.setIdPersona(res.getInt("id_persona"));
                docente.setCodigo(res.getString("docente_codigo"));
                docente.setIdentificacion(res.getString("persona_identificacion"));
                docente.setPrimerApellido(res.getString("persona_primer_apellido"));
                docente.setSegundoApellido(res.getString("persona_segundo_apellido"));
                docente.setPrimerNombre(res.getString("persona_primer_nombre"));
                docente.setSegundoNombre(res.getString("persona_segundo_nombre"));

                String key = docente.getIdentificacion() + " " + docente.getPrimerNombre() + " " + docente.getSegundoNombre() + " " + docente.getPrimerApellido() + " " + docente.getSegundoApellido();

                lista.put(key, docente);

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            pool.closeStmt().close(res).close(conn);
        }
        return lista;

    }

    public HashMap<String, DocenteMD> selectAll(String username) {

        String SELECT = "SELECT\n"
                + "\"public\".\"Personas\".id_persona,\n"
                + "\"public\".\"Personas\".persona_identificacion,\n"
                + "\"public\".\"Personas\".persona_primer_apellido,\n"
                + "\"public\".\"Personas\".persona_segundo_apellido,\n"
                + "\"public\".\"Personas\".persona_primer_nombre,\n"
                + "\"public\".\"Personas\".persona_segundo_nombre,\n"
                + "\"public\".\"Docentes\".id_docente,\n"
                + "\"public\".\"Docentes\".docente_codigo,\n"
                + "\"public\".\"Docentes\".docente_activo,\n"
                + "\"public\".\"Docentes\".docente_en_funcion\n"
                + "FROM\n"
                + "\"public\".\"Personas\"\n"
                + "INNER JOIN \"public\".\"Usuarios\" ON \"public\".\"Usuarios\".id_persona = \"public\".\"Personas\".id_persona\n"
                + "INNER JOIN \"public\".\"Docentes\" ON \"public\".\"Docentes\".id_persona = \"public\".\"Personas\".id_persona\n"
                + "WHERE\n"
                + "\"public\".\"Usuarios\".usu_username = '" + username + "'"
                + "ORDER BY \"public\".\"Personas\".persona_primer_nombre ASC";

        HashMap<String, DocenteMD> lista = new HashMap<>();
        conn = pool.getConnection();
        res = pool.ejecutarQuery(SELECT, conn, null);
        try {

            while (res.next()) {

                DocenteMD docente = new DocenteMD();

                docente.setIdDocente(res.getInt("id_docente"));
                docente.setIdPersona(res.getInt("id_persona"));
                docente.setCodigo(res.getString("docente_codigo"));
                docente.setIdentificacion(res.getString("persona_identificacion"));
                docente.setPrimerApellido(res.getString("persona_primer_apellido"));
                docente.setSegundoApellido(res.getString("persona_segundo_apellido"));
                docente.setPrimerNombre(res.getString("persona_primer_nombre"));
                docente.setSegundoNombre(res.getString("persona_segundo_nombre"));

                String key = docente.getIdentificacion() + " " + docente.getPrimerNombre() + " " + docente.getSegundoNombre() + " " + docente.getPrimerApellido() + " " + docente.getSegundoApellido();

                lista.put(key, docente);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            pool.closeStmt().close(res).close(conn);
        }
        return lista;
    }

    public DocenteBD selectWhere(String cedula) {
        return new DocenteBD();
    }

}
