/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.tipoActividad;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.tipoActividad.TipoActividad;
import modelo.pgConect;

/**
 *
 * @author Andres Ullauri
 */
public class dbTipoActividad extends TipoActividad {

    pgConect conecta = new pgConect();

    public dbTipoActividad() {
    }

    public dbTipoActividad(Integer idTipoActividad, String nombreTipoActividad, String nombreSubtipoActividad) {
        super(idTipoActividad, nombreTipoActividad, nombreSubtipoActividad);
    }
    
    

    public TipoActividad retornaTipo(String []aguja) {

        try {
            TipoActividad tipo = null;
            String sql = "SELECT id_tipo_actividad FROM \"TipoActividad\" \n"
                    + "WHERE nombre_tipo_actividad='"+aguja[0]+"'\n"
                    + "AND nombre_subtipo_actividad='"+aguja[1]+"'";
             ResultSet rs = conecta.query(sql);
            while (rs.next()) {
                tipo = new TipoActividad();
                tipo.setIdTipoActividad(Integer.parseInt(rs.getString("id_tipo_actividad")));
                
            }

            rs.close();
            return tipo;
        } catch (SQLException ex) {
            Logger.getLogger(dbTipoActividad.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
