package modelo.persona;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    public DocenteBD(ConectarDB conecta, PersonaBD per) {
        this.conecta = conecta;
        this.per = per;
    }

    public DocenteBD(ConectarDB conecta, PersonaBD per, String codigo, String docenteTipoTiempo, String estado, int docenteCategoria, int idDocente, boolean docenteOtroTrabajo, LocalDate fechaInicioContratacion, LocalDate fechaFinContratacion, boolean docenteCapacitador) {
        super(codigo, docenteTipoTiempo, estado, docenteCategoria, idDocente, docenteOtroTrabajo, fechaInicioContratacion, fechaFinContratacion, docenteCapacitador);
        this.conecta = conecta;
        this.per = per;
    }

    public void InsertarDocente() {
        // DocenteMD doc = new DocenteMD();
        String nsql = "INSERT INTO public.\"Docentes\"(\n"
                + "	 id_persona, docente_codigo, docente_otro_trabajo, "
                + "docente_categoria, docente_fecha_contrato, "
                + "docente_tipo_tiempo, docente_activo, docente_observacion, docente_capacitador)\n"
                + "	VALUES (" + this.getIdPersona() + ", '" + this.getCodigo() + "', " + this.isDocenteOtroTrabajo() + ","
                + " " + this.getDocenteCategoria() + ", '" + this.getFechaInicioContratacion() + "', '"
                + this.getDocenteTipoTiempo() + "', true, NULL, " + this.isDocenteCapacitador() + ");";
        if (getFechaFinContratacion() != null) {
            nsql = "INSERT INTO public.\"Docentes\"(\n"
                    + "	 id_persona, docente_codigo, docente_otro_trabajo, "
                    + "docente_categoria, docente_fecha_contrato, "
                    + "docente_fecha_fin, docente_tipo_tiempo, docente_activo, docente_observacion, docente_capacitador)\n"
                    + "	VALUES (" + this.getIdPersona() + ", '" + this.getCodigo() + "', " + this.isDocenteOtroTrabajo() + ","
                    + " " + this.getDocenteCategoria() + ", '" + this.getFechaInicioContratacion() + "', "
                    + "'" + this.getFechaFinContratacion() + "', '" + this.getDocenteTipoTiempo() + "', true, NULL, " + this.isDocenteCapacitador() + ");";

        }

        if (conecta.nosql(nsql) == null) {
            System.out.println("Se guardo correctamente");
        }

    }

    public List<DocenteMD> llenarTabla() {
        List<DocenteMD> lista = new ArrayList();
        String sql = "SELECT p.id_persona, p.persona_identificacion, "
                + " p.persona_primer_nombre, p.persona_segundo_nombre,"
                + " p.persona_primer_apellido, p.persona_segundo_apellido, d.docente_fecha_contrato, d.docente_fecha_fin,docente_tipo_tiempo "
                + " FROM public.\"Personas\" p JOIN public.\"Docentes\" d USING(id_persona) "
                + "WHERE d.docente_activo = true AND p.persona_activa = true;";
        //Esto estaba mal WHERE alumno_activo = 'true'
        //System.out.println(sql);
        ResultSet rs = conecta.sql(sql);
        try {
            while (rs.next()) {
                DocenteMD m = new DocenteMD();
                m.setIdPersona(rs.getInt("id_persona"));
                m.setIdentificacion(rs.getString("persona_identificacion"));
                m.setPrimerNombre(rs.getString("persona_primer_nombre"));
                m.setSegundoNombre(rs.getString("persona_segundo_nombre"));
                m.setPrimerApellido(rs.getString("persona_primer_apellido"));
                m.setSegundoApellido(rs.getString("persona_segundo_apellido"));

                lista.add(m);
            }
            rs.close();
            return lista;
        } catch (SQLException ex) {
            System.out.println("No hay docente dentro del instituto");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    private ArrayList<DocenteMD> consultarDocentes(String sql) {
        ArrayList<DocenteMD> pers = new ArrayList();
        DocenteMD p;
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    p = obtenerDocente(rs);
                    if (p != null) {
                        pers.add(p);
                    }
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

    public ArrayList<String> buscarPersona(String cedula) {
        ArrayList<String> datosPersona = new ArrayList();
        String sql = "SELECT id_persona, persona_primer_nombre FROM public.\"Personas\" "
                + "WHERE persona_identificacion = '" + cedula + "';";
        System.out.println(sql + cedula);
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    datosPersona.add(rs.getString("id_persona"));
                    datosPersona.add(rs.getString("persona_primer_nombre"));
                }
                rs.close();
                return datosPersona;
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
                + " docente_observacion, docente_capacitador\n"
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
            }
            rs.close();
            return doc;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    public ArrayList<DocenteMD> cargarDocentes() {
        ArrayList<DocenteMD> docentes = new ArrayList();
        String sql = "SELECT id_docente, d.id_persona, docente_codigo, docente_otro_trabajo,\n"
                + "	docente_categoria, docente_fecha_contrato, docente_fecha_fin, docente_tipo_tiempo, docente_capacitador,\n"
                + "	persona_primer_apellido\n"
                + "FROM public.\"Docentes\" d, public.\"Personas\" p  WHERE docente_activo = 'true' AND p.id_persona = d.id_persona\n"
                + "order by persona_primer_apellido;";
        /* String sql = "SELECT id_docente, id_persona, docente_codigo, docente_otro_trabajo, "
                + "docente_categoria, docente_fecha_contrato, docente_fecha_fin, "
                + "docente_tipo_tiempo, docente_capacitador\n"
                + "FROM public.\"Docentes\"  WHERE docente_activo = 'true' ;";*/
        System.out.println(sql);
        return consultarDocentes(sql);
    }

    public ArrayList<DocenteMD> cargarDocentesPorCarrera(int idCarrera) {
        ArrayList<DocenteMD> docentes = new ArrayList();
        String sql = "	\n"
                + "SELECT public.\"Docentes\".id_docente, id_persona, docente_codigo, docente_otro_trabajo, \n"
                + "docente_categoria, docente_fecha_contrato,docente_fecha_fin, \n"
                + "docente_tipo_tiempo, docente_activo, docente_observacion,\n"
                + "docente_capacitador\n"
                + "	FROM public.\"Docentes\", public.\"Materias\", public.\"DocentesMateria\"\n"
                + "	WHERE public.\"Materias\".id_carrera = " + idCarrera + " \n"
                + "	AND public.\"DocentesMateria\".id_docente = public.\"Docentes\".id_docente\n"
                + "	AND public.\"DocentesMateria\".id_materia = \"Materias\".id_materia \n"
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

    public ArrayList<DocenteMD> cargarDocentesPorCarreraCiclo(int idCarrera, int ciclo) {
        ArrayList<DocenteMD> docentes = new ArrayList();
        String sql = "SELECT public.\"Docentes\".id_docente, id_persona, docente_codigo, docente_otro_trabajo, \n"
                + "docente_categoria, docente_fecha_contrato,\n"
                + "docente_tipo_tiempo, docente_activo, docente_observacion,\n"
                + "docente_capacitador\n"
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
                + "docente_capacitador \n"
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
                + " docente_observacion, docente_capacitador\n"
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
                + "docente_capacitador= " + this.isDocenteCapacitador() + "\n"
                + "WHERE id_docente= " + aguja + ";";
        if (getFechaFinContratacion() != null) {
            sql = "UPDATE public.\"Docentes\" SET\n"
                    + "	docente_otro_trabajo= " + this.isDocenteOtroTrabajo() + ", docente_categoria=" + this.getDocenteCategoria() + ","
                    + " docente_fecha_contrato= '" + this.getFechaInicioContratacion() + "', docente_fecha_fin='" + this.getFechaFinContratacion() + "', "
                    + "docente_tipo_tiempo= '" + this.getDocenteTipoTiempo() + "', docente_activo=TRUE, docente_observacion=NULL, "
                    + "docente_capacitador= " + this.isDocenteCapacitador() + "\n"
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
        /* String sql = "SELECT id_persona,id_docente, d.docente_codigo,\n"
                + "       p.persona_primer_apellido, p.persona_segundo_apellido, \n"
                + "       p.persona_primer_nombre, p.persona_segundo_nombre, p.persona_activa, d.docente_fecha_contrato,"
                + "       d.docente_tipo_tiempo\n"
                + "       FROM public.\"Personas\" p inner join public.\"Docentes\" d using(id_persona)\n"
                + "       WHERE d.docente_activo = true AND (\n"
                + "       d.docente_codigo ILIKE '%" + aguja + "%'  or "
                + "p.persona_primer_apellido ILIKE '%" + aguja + "%'  OR  "
                + "p.persona_segundo_apellido ILIKE '%" + aguja + "%'  OR "
                + "p.persona_primer_nombre ILIKE '%" + aguja + "%'  OR "
                + "p.persona_segundo_nombre ILIKE '%" + aguja + "%' );";*/
        String sql = "SELECT id_docente, d.id_persona, docente_codigo, "
                + "docente_otro_trabajo, docente_categoria, "
                + "docente_fecha_contrato,docente_fecha_fin, "
                + " docente_tipo_tiempo, docente_activo,"
                + " docente_observacion, docente_capacitador \n"
                + "       FROM public.\"Personas\" p inner join public.\"Docentes\" d using(id_persona)\n"
                + "       WHERE d.docente_activo = true AND (\n"
                + "       d.docente_codigo ILIKE '%" + aguja + "%'  or "
                + "p.persona_primer_apellido ILIKE '%" + aguja + "%'  OR  "
                + "p.persona_segundo_apellido ILIKE '%" + aguja + "%'  OR "
                + "p.persona_primer_nombre ILIKE '%" + aguja + "%'  OR "
                + "p.persona_segundo_nombre ILIKE '%" + aguja + "%' );";
        //System.out.println(sql);

        return consultarDocentes(sql);
    }

    private ArrayList<DocenteMD> consultarDocente(String sql) {
        ArrayList<DocenteMD> almns = new ArrayList();
        DocenteMD p;
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    DocenteMD al = new DocenteMD();
                    al.setIdDocente(rs.getInt("id_docente"));
                    PersonaMD pe = per.buscarPersonaParaReferencia(rs.getInt("id_persona"));
                    al.setPersona(pe);

                    almns.add(al);
                }
                rs.close();
                return almns;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No pudimos consultar personas");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public DocenteMD buscarPersona(int aguja) {
        String sql = "SELECT p.id_persona, p.persona_identificacion, p.persona_primer_apellido,\n"
                + "p.persona_segundo_apellido, p.persona_primer_nombre, p.persona_segundo_nombre,\n"
                + "a.docente_otro_trabajo, a.docente_categoria, a.docente_fecha_contrato,\n"
                + "a.docente_fecha_fin,a.docente_tipo_tiempo,a.docente_capacitador\n"
                + "FROM public.\"Personas\" p JOIN public.\"Docentes\" a USING(id_persona)\n"
                + "WHERE a.id_persona = " + aguja + " AND a.docente_activo = true  ;";
        System.out.println(sql);
        ResultSet rs = conecta.sql(sql);
        try {
            DocenteMD a = new DocenteMD();
            while (rs.next()) {
                a.setIdPersona(rs.getInt("id_persona"));
                a.setIdentificacion(rs.getString("persona_identificacion"));
                a.setPrimerApellido(rs.getString("persona_primer_apellido"));
                a.setSegundoApellido(rs.getString("persona_segundo_apellido"));
                a.setPrimerNombre(rs.getString("persona_primer_nombre"));
                a.setSegundoNombre(rs.getString("persona_segundo_nombre"));
                a.setDocenteOtroTrabajo(rs.getBoolean("docente_otro_trabajo"));
                a.setDocenteCategoria(rs.getInt("docente_categoria"));
                a.setFechaInicioContratacion(rs.getDate("docente_fecha_contrato").toLocalDate());
                System.out.println(rs.getDate("docente_fecha_fin").toLocalDate().toString());
//                if(rs.wasNull()){
//                   a.setFechaFinContratacion(null);
//                } else{
//                    System.out.println("Entro sin null la fecha fin");
//                    a.setFechaFinContratacion(rs.getDate("docente_fecha_fin").toLocalDate());
//                }
                a.setDocenteTipoTiempo(rs.getString("docente_tipo_tiempo"));
                a.setDocenteCapacitador(rs.getBoolean("docente_capacitador"));
            }
            rs.close();
            return a;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    public DocenteMD buscarDocente(String identificacion) {
        String sql = "SELECT id_docente, id_persona, docente_codigo, docente_otro_trabajo, "
                + "docente_categoria, docente_fecha_contrato, docente_fecha_fin, "
                + "docente_tipo_tiempo, docente_capacitador\n"
                + "FROM public.\"Docentes\" WHERE "
                + " docente_activo=true and docente_codigo ='" + identificacion + "'";
        System.out.println(sql);
        return consultarPor(sql);
    }

    public DocenteMD buscarDocenteInactivo(String identificacion) {
        String sql = "SELECT id_docente, id_persona, docente_codigo, docente_otro_trabajo, "
                + "docente_categoria, docente_fecha_contrato, docente_fecha_fin, "
                + "docente_tipo_tiempo, docente_capacitador\n"
                + "FROM public.\"Docentes\" WHERE "
                + " docente_activo=false and docente_codigo ='" + identificacion + "'";
        System.out.println(sql);
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

    public List<PersonaMD> filtrarPersona(String aguja) {
        List<PersonaMD> lista = new ArrayList();
        String sql = "SELECT id_persona, persona_identificacion, persona_primer_nombre, persona_segundo_nombre, persona_primer_apellido, persona_segundo_apellido"
                + " FROM public.\"Personas\" WHERE persona_identificacion LIKE '%" + aguja + "%' AND persona_activa = true";
        ResultSet rs = conecta.sql(sql);
        try {
            while (rs.next()) {
                PersonaMD m = new PersonaMD();
                m.setIdPersona(rs.getInt("id_persona"));
                m.setIdentificacion(rs.getString("persona_identificacion"));
                m.setPrimerNombre(rs.getString("persona_primer_nombre"));
                m.setSegundoNombre(rs.getString("persona_segundo_nombre"));
                m.setPrimerApellido(rs.getString("persona_primer_apellido"));
                m.setSegundoApellido(rs.getString("persona_segundo_apellido"));
                lista.add(m);
            }
            rs.close();
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    public boolean eliminarDocente(DocenteMD a, int aguja) {
        String nsql = "UPDATE public.\"Docentes\" SET\n"
                + " docente_activo = false, docente_observacion = '" + a.getEstado()
                + "' WHERE id_persona = " + aguja + ";";
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
                + " WHERE id_persona = " + aguja + ";";
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

}
