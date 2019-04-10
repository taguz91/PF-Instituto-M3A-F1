/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.estrategiasUnidad;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConexionBD;
import modelo.estrategiasAprendizaje.EstrategiasAprendizajeMD;
import modelo.unidadSilabo.UnidadSilaboMD;

/**
 *
 * @author Andres Ullauri
 */
public class EstrategiasUnidadBD extends EstrategiasUnidadMD {

    private final ConexionBD conexion;

    public EstrategiasUnidadBD(ConexionBD conexion) {
        this.conexion = conexion;
    }

    public EstrategiasUnidadBD(ConexionBD conexion, EstrategiasAprendizajeMD idEstrategia, UnidadSilaboMD idUnidad) {
        super(idEstrategia, idUnidad);
        this.conexion = conexion;
    }

    

    public void insertar(EstrategiasUnidadMD e) {

        try {
            PreparedStatement st = conexion.getCon().prepareStatement("INSERT INTO public.\"EstrategiasUnidad\"(\n"
                    + "	 id_unidad, id_estrategia)\n"
                    + "	VALUES ((SELECT MAX(id_unidad) FROM \"UnidadSilabo\"), ?)");

           
            st.setInt(1, e.getIdEstrategia().getIdEstrategia());
            st.executeUpdate();
            System.out.println(st);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(EstrategiasUnidadBD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
