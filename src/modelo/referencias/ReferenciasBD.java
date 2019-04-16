/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.referencias;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConexionBD;

/**
 *
 * @author Andres Ullauri
 */
public class ReferenciasBD extends ReferenciasMD {

    private final ConexionBD conexion;

    public ReferenciasBD(ConexionBD conexion) {
        this.conexion = conexion;
    }

    public ReferenciasBD(ConexionBD conexion, String codigoReferencia, String descripcionReferencia, String tipoReferencia) {
        super(codigoReferencia, descripcionReferencia, tipoReferencia);
        this.conexion = conexion;
    }

    public static List<ReferenciasMD> consultarBiblioteca(ConexionBD conexion, String clave) {

        List<ReferenciasMD> referencias = new ArrayList<>();
        try {

            PreparedStatement st = conexion.getCon().prepareStatement("SELECT id_referencia, codigo_referencia, descripcion_referencia, tipo_referencia, existe_en_biblioteca\n"
                    + "FROM public.\"Referencias\"\n"
                    + "WHERE tipo_referencia='Base'\n"
                    + "AND descripcion_referencia ILIKE '%" + clave + "%'");

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                ReferenciasMD tmp = new ReferenciasMD();
                tmp.setIdReferencia(rs.getInt(1));
                tmp.setCodigoReferencia(rs.getString(2));
                tmp.setDescripcionReferencia(rs.getString(3));
                tmp.setTipoReferencia(rs.getString(4));
                tmp.setExisteEnBiblioteca(rs.getBoolean(5));

                referencias.add(tmp);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReferenciasBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return referencias;

    }

    public void insertar(ReferenciasMD r) {

        try {
            PreparedStatement st = conexion.getCon().prepareStatement("INSERT INTO public.\"Referencias\"(\n"
                    + "	 codigo_referencia, descripcion_referencia, tipo_referencia, existe_en_biblioteca)\n"
                    + "	VALUES ( ?, ?, ?, false)");

            st.setString(1, r.getCodigoReferencia());
            st.setString(2, r.getDescripcionReferencia());
            st.setString(3, r.getTipoReferencia());

            st.executeUpdate();
            System.out.println(st);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReferenciasBD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
