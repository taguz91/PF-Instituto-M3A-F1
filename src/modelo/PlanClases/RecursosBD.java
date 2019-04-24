/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.PlanClases;

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
 * @author ANDRES BERMEO
 */
public class RecursosBD extends RecursosMD {
      private ConexionBD conexion;

    public RecursosBD(ConexionBD conexion) {
        this.conexion = conexion;
    }

    public RecursosBD(ConexionBD conexion, int id_recurso, String nombre_recursos, String tipo_recurso) {
        super(id_recurso, nombre_recursos, tipo_recurso);
        this.conexion = conexion;
    }

    
    
    public  static List<RecursosMD>  consultarRecursos(ConexionBD conexion,String nombre_recurso){
        List<RecursosMD> recursos=new ArrayList<>();
        
         try {

            PreparedStatement st = conexion.getCon().prepareStatement("select id_recurso,nombre_recursos,tipo_recurso from \"Recursos\" where nombre_recursos ILIKE '%" +nombre_recurso + "%' ");

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                RecursosMD re=new RecursosMD();
                re.setId_recurso(rs.getInt(1));
                re.setNombre_recursos(rs.getString(2));
                re.setTipo_recurso(rs.getString(3));
                
                recursos.add(re);
            }

        } catch (SQLException ex) {
            Logger.getLogger(RecursosBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return recursos;
    }
    
}
