/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.notas;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class conector {

    
        public conector() {
    }
    public Connection getConexion(){
        Connection con=null;
        String sDriver="org.postgresql.Driver";
        String sUrl="jdbc:postgresql://localhost:5433/proyecto";
        try {
            Class.forName(sDriver).newInstance();
            con= DriverManager.getConnection(sUrl,"postgres","postgres");
            System.out.println("conexion Exitosa");
        } catch (Exception e) {
            System.err.println("Error al conectar con la base de datos");
        }
        return con;
        
    }
}


    /*private Connection conexion;
    private String error = null;

    public conector(String user, String pass, String bd, String host) {

        try {

            Class.forName("org.postgresql.Driver");

            this.conexion = DriverManager.getConnection("jdbc:mysql://" + host + ":5433/" + bd, user, pass);

        } catch (ClassNotFoundException | SQLException ex) {

            this.error = ex.getMessage();

        }

    }

    public String getError() {


        return this.error;


    }

    public Connection getConexion() {

        return this.conexion;

    }

    public void Cerrar() {
        try {

            this.conexion.close();
        } catch (SQLException ex) {

            Logger.getLogger(conector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
