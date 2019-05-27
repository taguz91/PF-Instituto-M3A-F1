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
import modelo.jornada.JornadaMD;


public class JornadasDB extends JornadaMD {
    
    private ConexionBD conexion;

    public JornadasDB(ConexionBD conexion) {
        this.conexion = conexion;
    }

    public JornadasDB() {
    }

    
    public static List<JornadaMD> consultarJornadas(ConexionBD conexion){
        List<JornadaMD> lista_jornadas=new ArrayList<>();
        try {
            
            PreparedStatement st=conexion.getCon().prepareStatement("select nombre_jornada from \"Jornadas\" ");
            ResultSet rs=st.executeQuery();
            
            while(rs.next()){
                JornadaMD j=new JornadaMD();
                j.setNombre(rs.getString(1));
                lista_jornadas.add(j);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JornadasDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista_jornadas;
    }
    
}
