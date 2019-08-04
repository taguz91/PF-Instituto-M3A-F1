/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.AvanceSilabo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConexionBD;

/**
 *
 * @author HP
 */
public class AvanceUnidadesDB extends AvanceUnidades{
 private ConexionBD conexion;

    public AvanceUnidadesDB(ConexionBD conexion) {
        this.conexion = conexion;
    }
 
    public AvanceUnidadesDB() {
    }

    public AvanceUnidadesDB(String Observaciones, String avancePorcentaje, int id_unidad, int id_avance) {
        super(Observaciones, avancePorcentaje, id_unidad, id_avance);
    }
    public void insertaUnidadesAvance(AvanceUnidades uni){
        String sql="INSERT INTO public.\"Unidad_Seguimiento\"(\n" +
"   id_unidad, id_seguimientosilabo, cumplimiento_porcentaje, observaciones)\n" +
"	VALUES (?, ?, ?, ?);";
    
    try {
            PreparedStatement st = conexion.getCon().prepareStatement(sql);
          

           st.setInt(1, uni.getId_unidad());
           st.setInt(2,uni.getId_avance() );
           st.setDouble(3,Double.valueOf(uni.getAvancePorcentaje()));
           st.setString(4, uni.getObservaciones());
            st.executeUpdate();
            System.out.println(st);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(AvanceUnidadesDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
