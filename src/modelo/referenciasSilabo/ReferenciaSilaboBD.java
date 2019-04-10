/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.referenciasSilabo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    public void insertar(ReferenciaSilaboMD r) {

        PreparedStatement st=null;
        
        try {
            if (r.getIdReferencia().getIdReferencia() != null) {
                  st= conexion.getCon().prepareStatement("INSERT INTO public.\"ReferenciaSilabo\"(\n"
                    + "	 id_referencia, id_silabo)\n"
                    + "	VALUES ( ?, (SELECT MAX(id_silabo) FROM \"Silabo\"))");

            st.setInt(1, r.getIdReferencia().getIdReferencia());
            }else{
                  st = conexion.getCon().prepareStatement("INSERT INTO public.\"ReferenciaSilabo\"(\n"
                    + "	 id_referencia, id_silabo)\n"
                    + "	VALUES ( (SELECT MAX(id_referencia) FROM \"Referencias\"), (SELECT MAX(id_silabo) FROM \"Silabo\"))");

            }

            st.executeUpdate();
            System.out.println(st);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReferenciaSilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
