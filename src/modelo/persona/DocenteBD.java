package modelo.persona;

import java.awt.Image;
import java.awt.image.BufferedImage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import modelo.ConectarDB;
import modelo.persona.PersonaMD;

public class DocenteBD extends DocenteMD {

    public DocenteBD() {
    }

    public DocenteBD(String codigo, String docenteTipoTiempo, String estado, int docenteCategoria, int idDocente, boolean docenteOtroTrabajo, LocalDate fechaInicioContratacion, LocalDate fechaFinContratacion, boolean docenteCapacitador) {
        super(codigo, docenteTipoTiempo, estado, docenteCategoria, idDocente, docenteOtroTrabajo, fechaInicioContratacion, fechaFinContratacion, docenteCapacitador);
    }

    ConectarDB conecta = new ConectarDB();
    PersonaMD p = new PersonaMD();

    public void guardarDocente() {
        DocenteMD doc = new DocenteMD();
        String sql = "INSERT INTO public.\"Docentes\"(\n"
                + "id_persona, docente_codigo, docente_otro_trabajo, docente_categoria, "
                + "docente_fecha_contrato, docente_fecha_fin, docente_tipo_tiempo, "
                + "docente_activo, docente_observacion, docente_capacitador)\n"
                + "VALUES (?, '"+this.getCodigo()+"', '"+this.isDocenteOtroTrabajo()+"', "+this.getDocenteCategoria()+""
                + ", '"+this.getFechaInicioContratacion()+"', '"+this.getFechaFinContratacion()+"', "
                + "?, ?, ?, ?, ?);";
    }

    public DocenteMD buscarDocente(String identificacion) {
        String sql = "SELECT persona_identificacion FROM public.\"Personas\" "
                + "WHERE persona_identificacion = '" + identificacion + "';";
        ResultSet rs = conecta.sql(sql);
        try {
            DocenteMD doc = new DocenteMD();
            while (rs.next()) {
                doc.setIdDocente(rs.getInt("id_docente"));
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
}
