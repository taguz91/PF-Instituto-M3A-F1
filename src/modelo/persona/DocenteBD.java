package modelo.persona;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import modelo.ConectarDB;

public class DocenteBD extends DocenteMD {

    public DocenteBD() {
    }

    public DocenteBD(String codigo, String docenteTipoTiempo, String estado, int docenteCategoria, int idDocente, boolean docenteOtroTrabajo, LocalDate fechaInicioContratacion, LocalDate fechaFinContratacion, boolean docenteCapacitador) {
        super(codigo, docenteTipoTiempo, estado, docenteCategoria, idDocente, docenteOtroTrabajo, fechaInicioContratacion, fechaFinContratacion, docenteCapacitador);
    }

    ConectarDB conecta = new ConectarDB("Docente BD");
    PersonaMD p;
    //Para consultar personas  
    PersonaBD per = new PersonaBD();

    public boolean InsertarDocente() {
        //  DocenteMD doc = new DocenteMD();
        String sql = "INSERT INTO public.\"Docentes\"(\n"
                + "id_persona, docente_codigo, docente_otro_trabajo, docente_categoria, "
                + "docente_fecha_contrato, docente_fecha_fin, docente_tipo_tiempo, "
                + "docente_activo, docente_observacion, docente_capacitador)\n"
                + "VALUES (" + getIdPersona() + ", '" + this.getCodigo() + "', " + this.isDocenteOtroTrabajo() + ", " + this.getDocenteCategoria() + ""
                + ", '" + this.getFechaInicioContratacion() + "', '" + this.getFechaFinContratacion() + "','" + this.getDocenteTipoTiempo() + "', true,"
                + "" + this.getEstado() + ", " + this.isDocenteCapacitador() + ");";

        System.out.println(sql);
        if (conecta.nosql(sql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }
        public boolean ActualizarDocente(String cedula) {
        String sql = "UPDATE public.\"Docentes\" SET\n"
                + " docente_otro_trabajo = '" + this.isDocenteOtroTrabajo() + "', docente_categoria = '" + this.getDocenteCategoria()
                + "', docente_fecha_contrato = " + this.getFechaInicioContratacion() + ", docente_fecha_fin = '" + this.getFechaFinContratacion() + "', docente_tipo_tiempo= '" + this.getDocenteTipoTiempo()
                + "', docente_activo = " + "true" + ", docente_observacion = '" + this.getEstado() + "', docente_capacitador = " + this.isDocenteCapacitador()+ "'\n"
                + " WHERE docente_codigo = " + cedula + ";";
        if (conecta.nosql(sql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

        public List<PersonaMD> llenarTabla() {
        List<PersonaMD> lista = new ArrayList();
        String sql = "SELECT p.id_persona, p.persona_identificacion, "
                + " p.persona_primer_nombre, p.persona_segundo_nombre,"
                + " p.persona_primer_apellido, p.persona_segundo_apellido"
                + " FROM public.\"Personas\" p JOIN public.\"Docentes\" d USING(id_persona) WHERE d.docente_activo = true AND p.persona_activa = true;";
        //Esto estaba mal WHERE alumno_activo = 'true'
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
            System.out.println("No hay docente dentro del instituto");
            System.out.println(ex.getMessage());
            return null;
        }
    }
    //metodo para buscar una persona 
    public ArrayList<String> existeDocente(String cedula) {
        ArrayList<String> datos = new ArrayList();
        String sql = "SELECT id_docente, docente_codigo \n"
                + "FROM public.\"Docentes\" \n"
                + "WHERE docente_codigo ='" + cedula + "';";
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
        String sql = "SELECT id_persona, persona_primer_nombre FROM public.\"Personas\" "
                + "WHERE persona_identificacion = '" + cedula + "' "
                + "and id_tipo_persona = 1 ;";
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
            //Logger.getLogger(Modelo_Alumno.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ArrayList<DocenteMD> cargarDocentes() {
        ArrayList<DocenteMD> docentes = new ArrayList();
        String sql = "SELECT id_docente, id_persona, docente_codigo, "
                + "docente_otro_trabajo, docente_categoria, "
                + "docente_fecha_contrato,"
                + " docente_tipo_tiempo, docente_activo,"
                + " docente_observacion, docente_capacitador\n"
                + "FROM public.\"Docentes\" ;";
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

    public ArrayList<DocenteMD> cargarDocentesPorCarrera(int idCarrera) {
        ArrayList<DocenteMD> docentes = new ArrayList();
        String sql = "	\n"
                + "SELECT public.\"Docentes\".id_docente, id_persona, docente_codigo, docente_otro_trabajo, \n"
                + "docente_categoria, docente_fecha_contrato,\n"
                + "docente_tipo_tiempo, docente_activo, docente_observacion,\n"
                + "docente_capacitador\n"
                + "	FROM public.\"Docentes\", public.\"Materias\", public.\"DocentesMateria\"\n"
                + "	WHERE public.\"Materias\".id_carrera = " + idCarrera + " \n"
                + "	AND public.\"DocentesMateria\".id_docente = public.\"Docentes\".id_docente\n"
                + "	AND public.\"DocentesMateria\".id_materia = \"Materias\".id_materia \n"
                + "	GROUP BY \"Docentes\".id_docente, \"Materias\".id_carrera ORDER BY id_docente;";
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
                + "WHERE public.\"DocentesMateria\".id_materia = "+idMateria+" \n"
                + "AND public.\"Docentes\".id_docente = public.\"DocentesMateria\".id_docente\n"
                + "ORDER BY id_docente;";
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
    
    public DocenteMD buscarDocente(int idDocente){
        DocenteMD d = null;
        String sql = "SELECT id_docente, id_persona, docente_codigo, "
                + "docente_otro_trabajo, docente_categoria, "
                + "docente_fecha_contrato,"
                + " docente_tipo_tiempo, docente_activo,"
                + " docente_observacion, docente_capacitador\n"
                + "FROM public.\"Docentes\" "
                + "WHERE id_docente = "+idDocente+";";
        ResultSet rs = conecta.sql(sql); 
        try {
            if (rs != null) {
                while(rs.next()){
                    d = obtenerDocente(rs); 
                }
                return  d; 
            } else{
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No se pudo consultar docente "+idDocente);
            return null;
        }
                
    }

    public DocenteMD obtenerDocente(ResultSet rs) {
        DocenteMD d = new DocenteMD();
        try {
            d.setIdDocente(rs.getInt("id_docente"));
            //Buscamos todos los datos de la tabla persona de este docente 
            p = per.buscarPersona(rs.getInt("id_persona"));
            d.setPersona(p);

            d.setCodigo(rs.getString("docente_codigo"));

            if (rs.wasNull()) {
                d.setDocenteOtroTrabajo(false);
            } else {
                d.setDocenteOtroTrabajo(rs.getBoolean("docente_otro_trabajo"));
            }

            d.setDocenteCategoria(rs.getInt("docente_categoria"));

            d.setFechaInicioContratacion(rs.getDate("docente_fecha_contrato").toLocalDate());

            d.setDocenteTipoTiempo(rs.getString("docente_tipo_tiempo"));

            if (rs.wasNull()) {
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

}
