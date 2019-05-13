package modelo.ReferenciasB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.ConectarDB;
import modelo.ConexionBD;

public class ReferenciaBD extends ReferenciasMD {

    private ConectarDB conectar;
    private ConexionBD conexion;

    public ReferenciaBD(ConectarDB conectar) {
        this.conectar = conectar;
    }

    public boolean insertarReferencia() {
        String nsql = "INSERT INTO public.\"Referencias\"(\n"
                + "codigo_referencia,descripcion_referencia,tipo_referencia,existe_en_biblioteca)\n"
                + " values ('" + getCodigo_referencia() + "','" + getDescripcion_referencia() + "','" + getTipo_referencia() + "'," + isExiste_en_biblioteca() + ");";
        if (conectar.nosql(nsql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }

    }

    public List<ReferenciasMD> consultarBibliotecaTabla(ConexionBD conexion) {

        List<ReferenciasMD> referencias = new ArrayList<>();
        try {

            PreparedStatement st = conexion.getCon().prepareStatement("SELECT id_referencia, codigo_referencia, descripcion_referencia, tipo_referencia, existe_en_biblioteca\n"
                    + "                     FROM public.\"Referencias\"\n"
                    + "                    WHERE tipo_referencia='Base'");

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                ReferenciasMD tmp = new ReferenciasMD();
                tmp.setId_referencia(rs.getInt(1));
                tmp.setCodigo_referencia(rs.getString(2));
                tmp.setDescripcion_referencia(rs.getString(3));
                tmp.setTipo_referencia(rs.getString(4));
                tmp.setExiste_en_biblioteca(rs.getBoolean(5));

                referencias.add(tmp);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReferenciaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return referencias;

    }

    public void eliminar(ConexionBD conexion, String codigo) {
        try {
            PreparedStatement st = conexion.getCon().prepareStatement("DELETE FROM \"Referencias\" where codigo_referencia=?");
            st.setString(1, codigo);

            st.executeUpdate();

            System.out.println(st);
            st.close();
            JOptionPane.showMessageDialog(null, "Datos eliminados correctamente");
        } catch (SQLException ex) {
            Logger.getLogger(ReferenciaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public  List<ReferenciasMD> consultarBiblioteca(ConexionBD conexion, String clave) {

        List<ReferenciasMD> referencias = new ArrayList<>();
        try {

            PreparedStatement st = conexion.getCon().prepareStatement("SELECT id_referencia, codigo_referencia, descripcion_referencia, tipo_referencia, existe_en_biblioteca\n"
                    + "FROM public.\"Referencias\"\n"
                    + "WHERE tipo_referencia='Base'\n"
                    + "AND descripcion_referencia ILIKE '%" + clave + "%'");

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                ReferenciasMD tmp = new ReferenciasMD();
                tmp.setId_referencia(rs.getInt(1));
                tmp.setCodigo_referencia(rs.getString(2));
                tmp.setDescripcion_referencia(rs.getString(3));
                tmp.setTipo_referencia(rs.getString(4));
                tmp.setExiste_en_biblioteca(rs.getBoolean(5));

                referencias.add(tmp);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReferenciasMD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return referencias;

    }
}
