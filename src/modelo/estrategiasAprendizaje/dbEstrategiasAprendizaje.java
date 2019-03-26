/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.estrategiasAprendizaje;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.estrategiasAprendizaje.EstrategiasAprendizaje;
import modelo.pgConect;

/**
 *
 * @author Andres Ullauri
 */
public class dbEstrategiasAprendizaje extends EstrategiasAprendizaje {
    
    pgConect conecta = new pgConect();
    
    public dbEstrategiasAprendizaje() {
    }
    
    public dbEstrategiasAprendizaje(Integer idEstrategia, String descripcionEstrategia) {
        super(idEstrategia, descripcionEstrategia);
    }
    
    public List<EstrategiasAprendizaje> mostrarEstrategias() {
        
        try {
            List<EstrategiasAprendizaje> lista = new ArrayList<>();
            String sql = "SELECT * FROM \"EstrategiasAprendizaje\" ORDER BY id_estrategia DESC";
            
            ResultSet rs = conecta.query(sql);
            
            while (rs.next()) {
                EstrategiasAprendizaje est = new EstrategiasAprendizaje();
                
                est.setIdEstrategia(Integer.parseInt(rs.getString("id_estrategia")));
                est.setDescripcionEstrategia(rs.getString("descripcion_estrategia"));
                lista.add(est);
            }
            rs.close();
            return lista;
            
        } catch (SQLException ex) {
            Logger.getLogger(dbEstrategiasAprendizaje.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public boolean insertar() {
        
        String nSql = "INSERT INTO public.\"EstrategiasAprendizaje\"(\n"
                + "	 descripcion_estrategia)\n"
                + "	VALUES ( '" + this.getDescripcionEstrategia() + "')";
        
        if (conecta.noQuery(nSql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }
    
    public EstrategiasAprendizaje retornaEstrategia(String aguja) {
        
        try {
            EstrategiasAprendizaje estrategia = null;
            String sql = "SELECT *FROM \"EstrategiasAprendizaje\" WHERE descripcion_estrategia='" + aguja + "'";
            ResultSet rs = conecta.query(sql);
            while (rs.next()) {
                estrategia = new EstrategiasAprendizaje();
                
                estrategia.setIdEstrategia(Integer.parseInt(rs.getString("id_estrategia")));
                estrategia.setDescripcionEstrategia(rs.getString("descripcion_estrategia"));
            }
            
            rs.close();
            return estrategia;
        } catch (SQLException ex) {
            Logger.getLogger(dbEstrategiasAprendizaje.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
