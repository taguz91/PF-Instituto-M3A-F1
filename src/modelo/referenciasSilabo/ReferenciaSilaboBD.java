/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.referenciasSilabo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConexionBD;

import modelo.referencias.ReferenciasMD;

import modelo.silabo.SilaboMD;

/**
 *
 * @author Andres Ullauri
 */
public class ReferenciaSilaboBD extends ReferenciaSilaboMD {

    private final ConexionBD conexion;

    public ReferenciaSilaboBD(ConexionBD conexion) {
        this.conexion = conexion;
    }

    public ReferenciaSilaboBD(ConexionBD conexion, ReferenciasMD idReferencia, SilaboMD idSilabo) {
        super(idReferencia, idSilabo);
        this.conexion = conexion;
    }

    public void insertar(ReferenciaSilaboMD r, int is, int ir) {

        PreparedStatement st=null;
        
        try {
            if (r.getIdReferencia().getIdReferencia() != null) {
                  st= conexion.getCon().prepareStatement("INSERT INTO public.\"ReferenciaSilabo\"(\n"
                    + "	 id_referencia, id_silabo)\n"
                    + "	VALUES ( ?,"+ is+")");

            st.setInt(1, r.getIdReferencia().getIdReferencia());
            }else{
                  st = conexion.getCon().prepareStatement("INSERT INTO public.\"ReferenciaSilabo\"(\n"
                    + "	 id_referencia, id_silabo)\n"
                    + "	VALUES ( (SELECT MAX(id_referencia) FROM \"Referencias\"),"+is+")");

            }

            st.executeUpdate();
            System.out.println(st);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReferenciaSilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static List<ReferenciaSilaboMD> recuperarReferencias(ConexionBD conexion,int aguja) {

        List<ReferenciaSilaboMD> referencias = new ArrayList<>();
        try {
            
             PreparedStatement st = conexion.getCon().prepareStatement( "SELECT \"ReferenciaSilabo\".id_referencia, \"Referencias\".tipo_referencia, \"Referencias\".descripcion_referencia, \"Referencias\".existe_en_biblioteca FROM \"ReferenciaSilabo\",\"Referencias\"\n"
                    + "WHERE id_silabo="+aguja+"\n"
                    + "AND \"Referencias\".id_referencia=\"ReferenciaSilabo\".id_referencia");
            ResultSet res = st.executeQuery();
            while (res.next()) {
                ReferenciaSilaboMD r = new ReferenciaSilaboMD();
                r.getIdReferencia().setIdReferencia(res.getInt(1));
                r.getIdReferencia().setTipoReferencia(res.getString(2));
                r.getIdReferencia().setDescripcionReferencia(res.getString(3));
                r.getIdReferencia().setExisteEnBiblioteca(res.getBoolean(4));

                //e.getIdUnidad().;

                referencias.add(r);
            }
            
            

        } catch (SQLException ex) {
            Logger.getLogger(ReferenciaSilaboBD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return referencias;
    }

}
