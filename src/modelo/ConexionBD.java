/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import utils.CONS;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

        con = conecta.getConecction("Clase conexion de andres");
        url = CONS.BD_URL;
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
                    con = conecta.getConecction("Funcion getCon de andresss");
                }
            } else {
                con = conecta.devolverConexion();
            }
        } catch (SQLException ex) {
            System.out.println("Error al comprobar la conexion");
            con = null;
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

}
