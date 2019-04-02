/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author @AndresSebastian
 */
public class pgConect {

    Connection con;
    Statement st;
    ResultSet rs;


    //String cadConexion = "jdbc:postgresql://35.193.226.187:5432/BDinsta";
    String cadConexion = "jdbc:postgresql://localhost:5432/baseNueva";

    String pgUser = "postgres";
    String pgContra = "qwerty79";





//    //String cadConexion = "jdbc:postgresql://35.193.226.187:5432/BDinsta";
//    String cadConexion = "jdbc:postgresql://localhost:5432/baseNueva";
//    
//    String pgUser = "postgres";
//    String pgContra = "qwerty79";
    //String cadConexion = "jdbc:postgresql://35.193.226.187:5432/BDinsta";
    /*String cadConexion = "jdbc:postgresql://localhost:5432/baseNueva";
    String pgUser = "postgres";
    String pgContra = "qwerty";*/


    public pgConect() {

        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Se Cargo Driver.");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(pgConect.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            con = DriverManager.getConnection(cadConexion, pgUser, pgContra);
            System.out.println("Se Conecto DB.");
        } catch (SQLException ex) {
            Logger.getLogger(pgConect.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public SQLException noQuery(String nSql) {
        System.out.println(nSql);
        try {
            st = con.createStatement();

            st.execute(nSql);

            st.close();

            return null;
        } catch (SQLException ex) {
            Logger.getLogger(pgConect.class.getName()).log(Level.SEVERE, null, ex);
            return ex;
        }

    }

    public ResultSet query(String sql) {

        System.out.println(sql);
        try {
            st = con.createStatement();

            rs = st.executeQuery(sql);

            //st.close();
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(pgConect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Connection getCon() {
        return con;
    }

}
