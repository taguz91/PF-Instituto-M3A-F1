/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.estrategiasUnidad;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConnDBPool;

/**
 *
 * @author Andres Ullauri
 */
public class EstrategiasUnidadBD extends EstrategiasUnidadMD {

    private static final ConnDBPool CON = ConnDBPool.single();

    public void insertar(EstrategiasUnidadMD e, int iu) {
        PreparedStatement st = CON.prepareStatement("INSERT INTO public.\"EstrategiasUnidad\"(\n"
                + "	 id_unidad, id_estrategia)\n"
                + "	VALUES (" + iu + ", ?)");

        try {

            st.setInt(1, e.getEstrategia().getIdEstrategia());
            st.executeUpdate();
            System.out.println(st);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(EstrategiasUnidadBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(st);
        }

    }

    public static List<EstrategiasUnidadMD> cargarEstrategiasPlanClae(int id_silabo, int numero_unidad) {

        List<EstrategiasUnidadMD> lista = new ArrayList<>();
        PreparedStatement st = CON.prepareStatement("SELECT distinct \"EstrategiasAprendizaje\".descripcion_estrategia\n"
                + "                     FROM \"EstrategiasUnidad\",\"UnidadSilabo\",\"EstrategiasAprendizaje\"\n"
                + "                     WHERE \"EstrategiasUnidad\".id_unidad=\"UnidadSilabo\".id_unidad\n"
                + "                     AND \"EstrategiasUnidad\".id_estrategia=\"EstrategiasAprendizaje\".id_estrategia\n"
                + "                    AND id_silabo=? AND numero_unidad=?");
        try {

            st.setInt(1, id_silabo);
            st.setInt(2, numero_unidad);
            ResultSet res = st.executeQuery();
            System.out.println(st);
            while (res.next()) {
                EstrategiasUnidadMD eu = new EstrategiasUnidadMD();
                eu.getEstrategia().setDescripcionEstrategia(res.getString(1));
                lista.add(eu);
            }

        } catch (SQLException ex) {
            Logger.getLogger(EstrategiasUnidadBD.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            CON.close(st);
        }

        return lista;
    }

}
