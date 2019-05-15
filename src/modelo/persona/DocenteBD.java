package modelo.persona;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import modelo.ConectarDB;
import modelo.ConnDBPool;
import modelo.curso.CursoMD;
import modelo.materia.MateriaMD;

public class DocenteBD extends DocenteMD {

    private final ConectarDB conecta;
    private PersonaMD p;
    //Para consultar personas  
    private final PersonaBD per;

    private static final ConnDBPool pool;
    private static Connection conn;
    private static ResultSet res;

    static {
        pool = new ConnDBPool();
    }

    public DocenteBD(ConectarDB conecta) {
        this.conecta = conecta;
        this.per = new PersonaBD(conecta);
    }

    /* public DocenteBD(ConectarDB conecta, PersonaBD per, String codigo, String docenteTipoTiempo, String estado, int docenteCategoria, int idDocente, boolean docenteOtroTrabajo, LocalDate fechaInicioContratacion, LocalDate fechaFinContratacion, boolean docenteCapacitador, String tituloDocente,String abreviaturaDocente) {
        super(codigo, docenteTipoTiempo, estado, docenteCategoria, idDocente, docenteOtroTrabajo, fechaInicioContratacion, fechaFinContratacion, docenteCapacitador, tituloDocente,abreviaturaDocente);
        this.conecta = conecta;
        this.per = per;
    }*/
    public void InsertarDocente() {
        // DocenteMD doc = new DocenteMD();
        String nsql = "INSERT INTO public.\"Docentes\"(\n"
                + "	 id_persona, docente_codigo, docente_otro_trabajo, "
                + "docente_categoria, docente_fecha_contrato, "
                + "docente_tipo_tiempo, docente_activo, docente_observacion, docente_capacitador, docente_titulo, docente_abreviatura)\n"
                + "	VALUES (" + this.getIdPersona() + ", '" + this.getCodigo() + "', " + this.isDocenteOtroTrabajo() + ","
                + " " + this.getDocenteCategoria() + ", '" + this.getFechaInicioContratacion() + "', '"
                + this.getDocenteTipoTiempo() + "', true, NULL, " + this.isDocenteCapacitador() + ", '" + this.getTituloDocente() + "', '" + this.getAbreviaturaDocente() + "' " + ");";

        if (getFechaFinContratacion() != null) {
            nsql = "INSERT INTO public.\"Docentes\"(\n"
                    + "	 id_persona, docente_codigo, docente_otro_trabajo, "
                    + "docente_categoria, docente_fecha_contrato, "
                    + "docente_fecha_fin, docente_tipo_tiempo, docente_activo, docente_observacion, docente_capacitador, docente_titulo, docente_abreviatura)\n"
                    + "	VALUES (" + this.getIdPersona() + ", '" + this.getCodigo() + "', " + this.isDocenteOtroTrabajo() + ","
                    + " " + this.getDocenteCategoria() + ", '" + this.getFechaInicioContratacion() + "', "
                    + "'" + this.getFechaFinContratacion() + "', '" + this.getDocenteTipoTiempo() + "', true, NULL, " + this.isDocenteCapacitador()
                    + ", '" + this.getTituloDocente() + "', '" + this.getAbreviaturaDocente() + "' " + ");";
        }

        PreparedStatement ps = conecta.getPS(nsql);

        if (conecta.nosql(ps) == null) {
            System.out.println("Se guardo correctamente");
        }

    }

    private ArrayList<DocenteMD> consultarDocenteTbl(String sql) {
        ArrayList<DocenteMD> pers = new ArrayList();
        DocenteMD d;
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            if (rs != null) {
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
                rs.close();
                ps.getConnection().close();
                return pers;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No pudimos consultar personas");
            System.out.println(e.getMessage());
            return null;
        }
    }

    //metodo para buscar una persona 
    public ArrayList<String> existeDocente(String cedula) {
        ArrayList<String> datos = new ArrayList();
        String sql = "SELECT id_docente, docente_codigo \n"
                + "FROM public.\"Docentes\" \n"
                + "WHERE docente_codigo ='" + cedula + "';";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            if (rs != null) {
                while (rs.next()) {
                    datos.add(rs.getString("id_docente"));
                    datos.add(rs.getString("docente_codigo"));
                }
                rs.close();
                ps.getConnection().close();
                return datos;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("no es posible realizar la consulta buscar persona" + e);
            return null;
        }

    }

    //metodo para buscar una persona 
    public ArrayList<String> buscarPersonaDocente(String cedula) {
        ArrayList<String> datos = new ArrayList();
        String sql = "SELECT \"Personas\".id_persona, persona_primer_nombre \n"
                + "FROM public.\"Personas\", public.\"Docentes\"\n"
                + "WHERE persona_identificacion = '" + cedula + "'\n"
                + "and \"Personas\".id_persona = \"Docentes\".id_persona;";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            if (rs != null) {
                while (rs.next()) {
                    datos.add(rs.getString("id_persona"));
                    datos.add(rs.getString("persona_primer_nombre"));
                }
                rs.close();
                ps.getConnection().close();
                return datos;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("no es posible realizar la consulta buscar persona" + e);
            return null;
        }

    }

    public DocenteMD buscarDocenteid(int id) {
        String sql = "SELECT id_docente, id_persona, docente_codigo, "
                + "docente_otro_trabajo, docente_categoria, "
                + "docente_fecha_contrato, docente_fecha_fin,"
                + " docente_tipo_tiempo, docente_activo,"
                + " docente_observacion, docente_capacitador, docente_titulo, docente_abreviatura \n"
                + "FROM public.\"Docentes\" "
                + "WHERE id_docente = '" + id + "';";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            DocenteMD doc = new DocenteMD();
            while (rs.next()) {
                doc.setIdDocente(rs.getInt("id_docente"));
                //Cargamos el codigo de persona  
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
            rs.close();
            ps.getConnection().close();
            return doc;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
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

    public DocenteMD capturarFecha(int ID) {
        String sql = "SELECT docente_fecha_contrato, docente_codigo FROM public.\"Docentes\" \n"
                + "WHERE id_persona = " + ID + ";";
        DocenteMD datos = new DocenteMD();
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            if (rs != null) {
                while (rs.next()) {
                    datos.setFechaInicioContratacion(rs.getDate("docente_fecha_contrato").toLocalDate());
                    datos.setCodigo(rs.getString("docente_codigo"));
                }
                rs.close();
                ps.getConnection().close();
                return datos;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("no es posible realizar la consulta buscar persona" + e);
            return null;
        }

    }

    public ArrayList<DocenteMD> cargarDocentesEliminados() {
        String sql = "SELECT docente_codigo, id_docente, d.id_persona, docente_tipo_tiempo, \n"
                + "persona_primer_nombre, persona_segundo_nombre,\n"
                + "persona_primer_apellido, persona_segundo_apellido,\n"
                + "persona_celular, persona_correo, persona_identificacion\n"
                + "FROM public.\"Docentes\" d, public.\"Personas\" p \n"
                + "WHERE p.id_persona = d.id_persona AND \n"
                + "docente_activo = false;";
        return consultarDocenteTbl(sql);
    }

    public List<CursoMD> capturarMaterias(int idPeriodo, int idDocente) {
        String nsql = "SELECT m.id_materia, c.id_curso, m.materia_nombre, c.curso_nombre FROM ((public.\"Materias\" m JOIN public.\"Cursos\" c USING(id_materia)) JOIN \n"
                + "public.\"PeriodoLectivo\" p USING(id_prd_lectivo)) JOIN public.\"Docentes\" d USING(id_docente) WHERE\n"
                + "p.id_prd_lectivo = " + idPeriodo + " AND d.id_docente = " + idDocente + " AND m.materia_activa = true AND p.prd_lectivo_activo = true;";
        PreparedStatement ps = conecta.getPS(nsql);
        ResultSet rs = conecta.sql(ps);
        List<CursoMD> lista = new ArrayList<>();

        try {
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
            rs.close();
            ps.getConnection().close();
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoBD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ArrayList<DocenteMD> cargarDocentesPorCarrera(int idCarrera) {
        ArrayList<DocenteMD> docentes = new ArrayList();
        String sql = "	\n"
                + "SELECT public.\"Docentes\".id_docente, id_persona, docente_codigo, docente_otro_trabajo, \n"
                + "docente_categoria, docente_fecha_contrato,docente_fecha_fin, \n"
                + "docente_tipo_tiempo, docente_activo, docente_observacion,\n"
                + "docente_capacitador, docente_titulo, docente_abreviatura\n"
                + "	FROM public.\"Docentes\", public.\"Materias\", public.\"DocentesMateria\"\n"
                + "	WHERE public.\"Materias\".id_carrera = " + idCarrera + " \n"
                + "	AND public.\"DocentesMateria\".id_docente = public.\"Docentes\".id_docente\n"
                + "	AND public.\"DocentesMateria\".id_materia = \"Materias\".id_materia \n"
                + "	GROUP BY \"Docentes\".id_docente, \"Materias\".id_carrera ORDER BY id_docente;";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            while (rs.next()) {
                DocenteMD doc = obtenerDocente(rs);
                if (doc != null) {
                    docentes.add(doc);
                }
            }
            rs.close();
            ps.getConnection().close();
            return docentes;
        } catch (SQLException ex) {
            System.out.println("No se pudo consultar docentes");
            System.out.println(ex.getMessage());
            return null;
        }
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
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            while (rs.next()) {
                DocenteMD doc = obtenerDocente(rs);
                if (doc != null) {
                    docentes.add(doc);
                }
            }
            ps.getConnection().close();
            rs.close();
            return docentes;
        } catch (SQLException ex) {
            System.out.println("No se pudo consultar docentes");
            System.out.println(ex.getMessage());
            return null;
        }
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
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            while (rs.next()) {
                DocenteMD doc = new DocenteMD();
                doc.setPrimerNombre(rs.getString("persona_primer_nombre"));
                doc.setPrimerApellido(rs.getString("persona_primer_apellido"));
                doc.setIdDocente(rs.getInt("id_docente"));
                doc.setIdPersona(rs.getInt("id_persona"));

                docentes.add(doc);
            }
            rs.close();
            ps.getConnection().close();
            return docentes;
        } catch (SQLException ex) {
            System.out.println("No se pudo consultar docentes");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public DocenteMD buscarDocente(int idDocente) {
        DocenteMD d = null;
        String sql = "SELECT id_docente, id_persona, docente_codigo, "
                + "docente_otro_trabajo, docente_categoria, "
                + "docente_fecha_contrato,docente_fecha_fin, "
                + " docente_tipo_tiempo, docente_activo,"
                + " docente_observacion, docente_capacitador, docente_titulo, docente_abreviatura\n"
                + "FROM public.\"Docentes\" "
                + "WHERE id_docente = " + idDocente + " and docente_activo =true;";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            if (rs != null) {
                while (rs.next()) {
                    d = obtenerDocente(rs);
                }
                ps.getConnection().close();
                return d;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No se pudo consultar docente " + idDocente);
            return null;
        }
    }

    public DocenteMD buscarDocenteParaReferencia(int idDocente) {
        DocenteMD d = new DocenteMD();
        String sql = "SELECT id_docente, id_persona, docente_codigo \n"
                + "FROM public.\"Docentes\" "
                + "WHERE id_docente = " + idDocente + " and docente_activo =true;";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            if (rs != null) {
                while (rs.next()) {
                    d.setIdDocente(rs.getInt("id_docente"));
                    //Buscamos todos los datos de la tabla persona de este docente 
                    p = per.buscarPersonaParaReferencia(rs.getInt("id_persona"));
                    d.setPersona(p);
                    d.setCodigo(rs.getString("docente_codigo"));
                }
                ps.getConnection().close();
                return d;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No se pudo consultar docente " + idDocente);
            return null;
        }
    }

    public DocenteMD obtenerDocente(ResultSet rs) {
        DocenteMD d = new DocenteMD();
        try {
            if (!rs.wasNull()) {
                d.setIdDocente(rs.getInt("id_docente"));
            }
            //Buscamos todos los datos de la tabla persona de este docente 
            p = per.buscarPersonaParaReferencia(rs.getInt("id_persona"));
            d.setPersona(p);
            //  System.out.println(d.getPrimerApellido());
            // System.out.println(d.getPrimerNombre());
            if (!rs.wasNull()) {
                d.setCodigo(rs.getString("docente_codigo"));
            }
            if (rs.wasNull()) {
                d.setDocenteOtroTrabajo(false);
            } else {
                d.setDocenteOtroTrabajo(rs.getBoolean("docente_otro_trabajo"));
            }
            if (!rs.wasNull()) {
                d.setDocenteCategoria(rs.getInt("docente_categoria"));
            }
            if (!rs.wasNull()) {
                d.setFechaInicioContratacion(rs.getDate("docente_fecha_contrato").toLocalDate());
            }
            try {
                d.setFechaFinContratacion(rs.getDate("docente_fecha_fin").toLocalDate());
                System.out.println("Si tiene fecha fin");
            } catch (Exception e) {
                System.out.println("No tiene fecha fin");
                System.out.println(e);
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
            return d;
        } catch (SQLException e) {
            System.out.println("No pudimos obtener docente");
            System.out.println(e.getMessage());
            return null;
        }
    }

    //Sentencia para editar una Persona
    public boolean editarDocente(int aguja) {
        String sql = "UPDATE public.\"Docentes\" SET\n"
                + "	docente_otro_trabajo= " + this.isDocenteOtroTrabajo() + ", docente_categoria=" + this.getDocenteCategoria() + ","
                + " docente_fecha_contrato= '" + this.getFechaInicioContratacion() + "',  "
                + "docente_tipo_tiempo= '" + this.getDocenteTipoTiempo() + "', docente_activo=TRUE, docente_observacion=NULL, "
                + "docente_capacitador= " + this.isDocenteCapacitador()
                + ", docente_titulo= '" + this.getTituloDocente() + "',"
                + " docente_abreviatura= '" + this.getAbreviaturaDocente() + "'  \n"
                + "WHERE id_docente= " + aguja + ";";
        if (getFechaFinContratacion() != null) {
            sql = "UPDATE public.\"Docentes\" SET\n"
                    + "	docente_otro_trabajo= " + this.isDocenteOtroTrabajo() + ", docente_categoria=" + this.getDocenteCategoria() + ","
                    + " docente_fecha_contrato= '" + this.getFechaInicioContratacion() + "', docente_fecha_fin='" + this.getFechaFinContratacion() + "', "
                    + "docente_tipo_tiempo= '" + this.getDocenteTipoTiempo() + "', docente_activo=TRUE, docente_observacion=NULL, "
                    + "docente_capacitador= " + this.isDocenteCapacitador()
                    + ", docente_titulo= '" + this.getTituloDocente() + "',"
                    + " docente_abreviatura= '" + this.getAbreviaturaDocente() + "' \n"
                    + "WHERE id_docente= " + aguja + ";";
        }

        PreparedStatement ps = conecta.getPS(sql);

        if (conecta.nosql(ps) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public boolean deshabilitarCursos(CursoMD curso) {
        String sql = "UPDATE public.\"Cursos\" SET curso_activo = false "
                + "WHERE id_docente = " + curso.getDocente().getIdDocente() + " AND id_prd_lectivo = " + curso.getPeriodo().getId_PerioLectivo()
                + " AND id_materia = " + curso.getMateria().getId() + " AND curso_nombre = '" + curso.getNombre() + "';";
        PreparedStatement ps = conecta.getPS(sql); 
        if (conecta.nosql(ps) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
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
        String sql = "SELECT id_docente, id_persona, docente_codigo, docente_otro_trabajo, "
                + "docente_categoria, docente_fecha_contrato, docente_fecha_fin, "
                + "docente_tipo_tiempo, docente_capacitador,docente_titulo,docente_abreviatura\n"
                + "FROM public.\"Docentes\" WHERE "
                + " docente_activo=true and docente_codigo ='" + identificacion + "'";
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
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            if (rs != null) {
                while (rs.next()) {
                    d = obtenerDocente(rs);
                }
                rs.close();
                ps.getConnection().close();
                return d;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No se pudo consultar una persona");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean eliminarDocente(DocenteMD a, int aguja) {
        String nsql = "UPDATE public.\"Docentes\" SET\n"
                + " docente_activo = false, docente_observacion = '" + a.getEstado()
                + "' WHERE id_docente = " + aguja + ";";
        PreparedStatement ps = conecta.getPS(nsql);
        if (conecta.nosql(ps) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public boolean activarDocente(int aguja) {
        String nsql = "UPDATE public.\"Docentes\" SET\n"
                + " docente_activo = true\n"
                + " WHERE id_docente = " + aguja + ";";
        PreparedStatement ps = conecta.getPS(nsql);
        if (conecta.nosql(ps) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public boolean terminarContrato(DocenteMD docente) {
        String nsql = "UPDATE public.\"Docentes\" SET\n"
                + " docente_fecha_fin = '" + docente.getFechaFinContratacion()
                + "', docente_observacion = '" + docente.getObservacion() + "', docente_en_funcion = false\n"
                + " WHERE id_docente = " + docente.getIdDocente() + ";";
        PreparedStatement ps = conecta.getPS(nsql);
        if (conecta.nosql(ps) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public static HashMap<String, DocenteMD> selectAll() {

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
            pool.close(conn);
        }
        return lista;

    }

    public static HashMap<String, DocenteMD> selectAll(String username) {

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
            pool.close(conn);
        }
        return lista;

    }
}
