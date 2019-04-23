/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.propiedades.Propiedades;

/**
 *
 * @author Andres Ullauri
 */
public class ConexionBD {

    private String url = "jdbc:postgresql://localhost:5432/baseFinal";

    private String usuario = "postgres";

    private String contrasena = "qwerty79";
    private final ConectarDB conecta; 

    private Connection con = null;

    private Statement stm = null;

    private ResultSet rs = null;

    public ConexionBD(ConectarDB conecta) {
        this.conecta = conecta; 
    }

    public void conectar() {

        con = conecta.getConecction();
        url = generarURL();
        System.out.println("Establecida la conexión con la base de datos");

    }

    public void desconectar() throws SQLException {

        if (con != null) {
            con.close();
        }

        if (stm != null) {
            stm.close();
        }

        if (rs != null) {
            rs.close();
        }
    }

    public Connection getCon() {
        // Comprobar conexion
        try {
            if (con != null) {
                if (con.isClosed()) {
                    System.out.println("Se abrira conexion en ConexionBD referenciando a resource manager ");
                    con = conecta.getConecction();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al comprobar la conexion");
            System.out.println(ex.getMessage());
        }

        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public Statement getStm() {
        return stm;
    }

    public void setStm(Statement stm) {
        this.stm = stm;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public static String generarURL() {

        String ip = Propiedades.getPropertie("ip");
        String port = Propiedades.getPropertie("port");
        String database = Propiedades.getPropertie("database");

        return "jdbc:postgresql://" + ip + ":" + port + "/" + database;
    }

}
