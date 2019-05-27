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

    

    public void insertar(EstrategiasUnidadMD e, int iu) {

        try {
            PreparedStatement st = conexion.getCon().prepareStatement("INSERT INTO public.\"EstrategiasUnidad\"(\n"
                    + "	 id_unidad, id_estrategia)\n"
                    + "	VALUES ("+iu+", ?)");

           
            st.setInt(1, e.getIdEstrategia().getIdEstrategia());
            st.executeUpdate();
            System.out.println(st);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(EstrategiasUnidadBD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static List<EstrategiasUnidadMD> cargarEstrategiasU(ConexionBD conexion, int aguja) {
        
        List<EstrategiasUnidadMD> lista = new ArrayList<>();
        try {
            
             PreparedStatement st = conexion.getCon().prepareStatement( "SELECT DISTINCT \"EstrategiasUnidad\".id_unidad,\"EstrategiasUnidad\".id_estrategia,\"EstrategiasAprendizaje\".descripcion_estrategia,\"UnidadSilabo\".numero_unidad  \n"
                    + "FROM \"EstrategiasUnidad\",\"UnidadSilabo\",\"EstrategiasAprendizaje\"\n"
                    + "WHERE \"EstrategiasUnidad\".id_unidad=\"UnidadSilabo\".id_unidad\n"
                    + "AND \"EstrategiasUnidad\".id_estrategia=\"EstrategiasAprendizaje\".id_estrategia\n"
                    + "AND id_silabo="+aguja+"");
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                EstrategiasUnidadMD eu = new EstrategiasUnidadMD();

                eu.getIdUnidad().setIdUnidad(rs.getInt(1));
                eu.getIdUnidad().setNumeroUnidad(rs.getInt(4));
                eu.getIdEstrategia().setIdEstrategia(rs.getInt(2));
                eu.getIdEstrategia().setDescripcionEstrategia(rs.getString(3));

                lista.add(eu);
            }
           
            

        } catch (SQLException ex) {
            Logger.getLogger(EstrategiasUnidadBD.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return lista;
    }
    public static List<EstrategiasUnidadMD> cargarEstrategiasPlanClae(ConexionBD conexion, int id_silabo,int numero_unidad)  {
        
        List<EstrategiasUnidadMD> lista = new ArrayList<>();
        try {
            
             PreparedStatement st = conexion.getCon().prepareStatement("SELECT distinct \"EstrategiasAprendizaje\".descripcion_estrategia\n" +
"                     FROM \"EstrategiasUnidad\",\"UnidadSilabo\",\"EstrategiasAprendizaje\"\n" +
"                     WHERE \"EstrategiasUnidad\".id_unidad=\"UnidadSilabo\".id_unidad\n" +
"                     AND \"EstrategiasUnidad\".id_estrategia=\"EstrategiasAprendizaje\".id_estrategia\n" +
"                    AND id_silabo=? AND numero_unidad=?");
             st.setInt(1, id_silabo);
            st.setInt(2, numero_unidad);
            ResultSet rs = st.executeQuery();
            System.out.println(st);
            while (rs.next()) {
                EstrategiasUnidadMD eu = new EstrategiasUnidadMD();
                eu.getIdEstrategia().setDescripcionEstrategia(rs.getString(1));
                lista.add(eu);
            }
           
            

        } catch (SQLException ex) {
            Logger.getLogger(EstrategiasUnidadBD.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return lista;
    }


}
