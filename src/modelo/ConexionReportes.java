/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author eduardo
 */
public class ConexionReportes {

    public ConexionReportes() {
    }
    public Connection getConexion(){
        Connection con=null;
        //String pass="2197"; //Contraseña Armando
        String pass="davicho"; //Contraseña David
        //String sUrl="jdbc:postgresql://localhost:5432/proyecto1"; //BD Armando
        String sUrl="jdbc:postgresql://localhost:5432/proyecto"; //BD David

        String sDriver="org.postgresql.Driver";
        try {
            Class.forName(sDriver).newInstance();
            con= DriverManager.getConnection(sUrl,"postgres",pass);
            System.out.println("conexion Exitosa");
        } catch (Exception e) {
            System.err.println("Error al conectar con la base de datos");
        }
        return con;
        
    }
}
