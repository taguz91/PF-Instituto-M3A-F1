/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author eduardo
 */
public class ConexionReportes {

    public ConexionReportes() {
    }
    public Connection getConexion(){
        Connection con=null;
        String sDriver="org.postgresql.Driver";
        String sUrl="jdbc:postgresql://localhost:5433/BDPFINSTITUTO";
        try {
            Class.forName(sDriver).newInstance();
            con= DriverManager.getConnection(sUrl,"postgres","Holapostgres");
            System.out.println("conexion Exitosa");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            System.err.println("Error al conectar con la base de datos");
        }
        return con;
        
    }
}
