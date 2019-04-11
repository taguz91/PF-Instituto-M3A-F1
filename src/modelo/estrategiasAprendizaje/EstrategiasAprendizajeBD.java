/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.estrategiasAprendizaje;

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
public class EstrategiasAprendizajeBD extends EstrategiasAprendizajeMD {

    private final ConexionBD conexion;

    public EstrategiasAprendizajeBD(ConexionBD conexion) {
        this.conexion = conexion;
    }

    public EstrategiasAprendizajeBD(ConexionBD conexion, String descripcionEstrategia) {
        super(descripcionEstrategia);
        this.conexion = conexion;
    }

    public void insertar() {

        try {
            PreparedStatement st = conexion.getCon().prepareStatement("INSERT INTO public.\"EstrategiasAprendizaje\"(\n"
                    + "	 descripcion_estrategia)\n"
                    + "	VALUES (?)");

            st.setString(1, getDescripcionEstrategia());

            st.executeUpdate();
            System.out.println(st);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(EstrategiasAprendizajeBD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static List<EstrategiasAprendizajeMD> consultar(ConexionBD conexion) {

        List<EstrategiasAprendizajeMD> estrategias = new ArrayList<>();
        try {

            PreparedStatement st = conexion.getCon().prepareStatement("SELECT id_estrategia, descripcion_estrategia\n"
                    + "	FROM public.\"EstrategiasAprendizaje\" ORDER BY id_estrategia DESC");

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                EstrategiasAprendizajeMD tmp = new EstrategiasAprendizajeMD();
                tmp.setIdEstrategia(rs.getInt(1));
                tmp.setDescripcionEstrategia(rs.getString(2));
                estrategias.add(tmp);
            }

        } catch (SQLException ex) {
            Logger.getLogger(EstrategiasAprendizajeBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estrategias;
    }
}
