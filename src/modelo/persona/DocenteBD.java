package modelo.persona;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import modelo.ConectarDB;
import modelo.ResourceManager;

public class DocenteBD extends DocenteMD {

    private final ConectarDB conecta;
    private PersonaMD p;
    //Para consultar personas  
    private final PersonaBD per;

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
        System.out.println(nsql);
        if (getFechaFinContratacion() != null) {
            nsql = "INSERT INTO public.\"Docentes\"(\n"
                    + "	 id_persona, docente_codigo, docente_otro_trabajo, "
                    + "docente_categoria, docente_fecha_contrato, "
                    + "docente_fecha_fin, docente_tipo_tiempo, docente_activo, docente_observacion, docente_capacitador, docente_titulo, docente_abreviatura)\n"
                    + "	VALUES (" + this.getIdPersona() + ", '" + this.getCodigo() + "', " + this.isDocenteOtroTrabajo() + ","
                    + " " + this.getDocenteCategoria() + ", '" + this.getFechaInicioContratacion() + "', "
                    + "'" + this.getFechaFinContratacion() + "', '" + this.getDocenteTipoTiempo() + "', true, NULL, " + this.isDocenteCapacitador()
                    + ", '" + this.getTituloDocente() + "', '" + this.getAbreviaturaDocente() + "' " + ");";
            System.out.println(nsql);
        }

        if (conecta.nosql(nsql) == null) {
            System.out.println("Se guardo correctamente");
        }

    }
    
    public void guardarFinContrato(int aguja){
        String sql = "UPDATE public.\"Docentes\" SET\n"
                + " id_sec_economico = "
                + " WHERE id_persona = " + aguja + ";";
    }
    
//    public void 

    private ArrayList<DocenteMD> consultarDocenteTbl(String sql) {
        ArrayList<DocenteMD> pers = new ArrayList();
        DocenteMD d;
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    d = new DocenteMD();
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
        System.out.println(sql);
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    datos.add(rs.getString("id_docente"));
                    datos.add(rs.getString("docente_codigo"));
                }
                rs.close();
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
        System.out.println(sql);
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    datos.add(rs.getString("id_persona"));
                    datos.add(rs.getString("persona_primer_nombre"));
                }
                rs.close();
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
        System.out.println(sql);
        ResultSet rs = conecta.sql(sql);
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
            return doc;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    public ArrayList<DocenteMD> cargarDocentes() {
        String sql = "SELECT id_docente, d.id_persona, docente_tipo_tiempo, \n"
                + "persona_primer_nombre, persona_segundo_nombre,\n"
                + "persona_primer_apellido, persona_segundo_apellido,\n"
                + "persona_celular, persona_correo, persona_identificacion\n"
                + "FROM public.\"Docentes\" d, public.\"Personas\" p \n"
                + "WHERE p.id_persona = d.id_persona AND \n"
                + "docente_activo = true;";
        return consultarDocenteTbl(sql);
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
        //System.out.println(sql);
        ResultSet rs = conecta.sql(sql);
        try {
            while (rs.next()) {
                DocenteMD doc = obtenerDocente(rs);
                if (doc != null) {
                    docentes.add(doc);
                }
            }
            rs.close();
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
        System.out.println(sql);
        ResultSet rs = conecta.sql(sql);
        try {
            while (rs.next()) {
                DocenteMD doc = obtenerDocente(rs);
                if (doc != null) {
                    docentes.add(doc);
                }
            }
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
        String sql = "SELECT public.\"Docentes\".id_docente, id_persona, docente_codigo, docente_otro_trabajo, \n"
                + "docente_categoria, docente_fecha_contrato,\n"
                + "docente_tipo_tiempo, docente_activo, docente_observacion,\n"
                + "docente_capacitador , docente_titulo, docente_abreviatura\n"
                + "FROM public.\"Docentes\",  public.\"DocentesMateria\"\n"
                + "WHERE public.\"DocentesMateria\".id_materia = " + idMateria + " \n"
                + "AND public.\"Docentes\".id_docente = public.\"DocentesMateria\".id_docente\n"
                + "ORDER BY id_docente;";
        System.out.println(sql);
        ResultSet rs = conecta.sql(sql);
        try {
            while (rs.next()) {
                DocenteMD doc = obtenerDocente(rs);
                if (doc != null) {
                    docentes.add(doc);
                }
            }
            rs.close();
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
        //System.out.println(sql);
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    d = obtenerDocente(rs);
                }
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
        //System.out.println(sql);
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    d.setIdDocente(rs.getInt("id_docente"));
                    //Buscamos todos los datos de la tabla persona de este docente 
                    p = per.buscarPersonaParaReferencia(rs.getInt("id_persona"));
                    d.setPersona(p);
                    d.setCodigo(rs.getString("docente_codigo"));
                }
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

        System.out.println(sql);

        if (conecta.nosql(sql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public ArrayList<DocenteMD> buscar(String aguja) {
        String sql = "SELECT id_docente, d.id_persona, docente_tipo_tiempo, \n"
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
        //System.out.println(sql);
        return consultarPor(sql);
    }

    //Este metodo unicamente nos devolvera una persona dependiendo de la setencia sql que se envie
    private DocenteMD consultarPor(String sql) {
        DocenteMD d = null;
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    d = obtenerDocente(rs);
                }
                rs.close();
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
        System.out.println(nsql);
        if (conecta.nosql(nsql) == null) {
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
        System.out.println(nsql);
        if (conecta.nosql(nsql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public static Integer selectIdDocenteWhereUsername(String username) {

        String SELECT = "SELECT\n"
                + "\"Docentes\".id_docente\n"
                + "FROM\n"
                + "\"Docentes\"\n"
                + "INNER JOIN \"Personas\" ON \"Docentes\".id_persona = \"Personas\".id_persona\n"
                + "INNER JOIN \"Usuarios\" ON \"Usuarios\".id_persona = \"Personas\".id_persona\n"
                + "WHERE\n"
                + "\"Usuarios\".usu_username = '" + username + "'";

        ResultSet rs = ResourceManager.Query(SELECT);

        Integer idDocente = null;

        try {
            while (rs.next()) {
                idDocente = rs.getInt("id_docente");
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DocenteBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return idDocente;
    }

    public static HashMap<String, DocenteMD> selectAll() {

        String SELECT = "SELECT\n"
                + "\"public\".\"ViewDocentes\".id_docente,\n"
                + "\"public\".\"ViewDocentes\".id_persona,\n"
                + "\"public\".\"ViewDocentes\".docente_codigo,\n"
                + "\"public\".\"ViewDocentes\".docente_activo,\n"
                + "\"public\".\"ViewDocentes\".persona_identificacion,\n"
                + "\"public\".\"ViewDocentes\".persona_primer_apellido,\n"
                + "\"public\".\"ViewDocentes\".persona_segundo_apellido,\n"
                + "\"public\".\"ViewDocentes\".persona_primer_nombre,\n"
                + "\"public\".\"ViewDocentes\".persona_segundo_nombre\n"
                + "FROM\n"
                + "\"public\".\"ViewDocentes\"\n"
                + "ORDER BY persona_primer_apellido";

        HashMap<String, DocenteMD> lista = new HashMap<>();

        ResultSet rs = ResourceManager.Query(SELECT);

        try {

            while (rs.next()) {

                DocenteMD docente = new DocenteMD();

                docente.setIdDocente(rs.getInt("id_docente"));
                docente.setIdPersona(rs.getInt("id_persona"));
                docente.setCodigo(rs.getString("docente_codigo"));
                docente.setIdentificacion(rs.getString("persona_identificacion"));
                docente.setPrimerApellido(rs.getString("persona_primer_apellido"));
                docente.setSegundoApellido(rs.getString("persona_segundo_apellido"));
                docente.setPrimerNombre(rs.getString("persona_primer_nombre"));
                docente.setSegundoNombre(rs.getString("persona_segundo_nombre"));

                String key = docente.getIdentificacion() + " " + docente.getPrimerNombre() + " " + docente.getSegundoNombre() + " " + docente.getPrimerApellido() + " " + docente.getSegundoApellido();

                lista.put(key, docente);

            }
            rs.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lista;

    }

    public static Map<String, DocenteMD> selectDocentes() {
        String SELECT = "SELECT\n"
                + "\"public\".\"Personas\".persona_identificacion,\n"
                + "\"public\".\"Personas\".persona_primer_apellido,\n"
                + "\"public\".\"Personas\".persona_segundo_apellido,\n"
                + "\"public\".\"Personas\".persona_primer_nombre,\n"
                + "\"public\".\"Personas\".persona_segundo_nombre,\n"
                + "\"public\".\"Personas\".id_persona,\n"
                + "\"public\".\"Docentes\".id_docente,\n"
                + "\"public\".\"Docentes\".docente_codigo\n"
                + "FROM\n"
                + "\"public\".\"Docentes\"\n"
                + "INNER JOIN \"public\".\"Personas\" ON \"public\".\"Docentes\".id_persona = \"public\".\"Personas\".id_persona\n"
                + "WHERE \n"
                + "docente_activo IS TRUE";

        Map<String, DocenteMD> lista = new HashMap<>();

        ResultSet rs = ResourceManager.Query(SELECT);

        try {
            while (rs.next()) {

                DocenteMD docente = new DocenteMD();
                docente.setIdDocente(rs.getInt("id_docente"));
                docente.setIdentificacion(rs.getString("persona_identificacion"));
                docente.setPrimerApellido(rs.getString("persona_primer_apellido"));
                docente.setSegundoApellido(rs.getString("persona_segundo_apellido"));
                docente.setPrimerNombre(rs.getString("persona_primer_nombre"));
                docente.setSegundoNombre(rs.getString("persona_segundo_nombre"));

                docente.setIdPersona(rs.getInt("id_persona"));

                docente.setCodigo(rs.getString("docente_codigo"));

                String key = docente.getIdentificacion() + " " + docente.getPrimerApellido() + " " + docente.getSegundoApellido() + " " + docente.getPrimerNombre() + " " + docente.getSegundoNombre();

                lista.put(key, docente);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return lista;
    }

}
