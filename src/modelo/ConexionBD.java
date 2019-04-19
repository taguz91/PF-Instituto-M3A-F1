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

/**
 *
 * @author Andres Ullauri
 */
public class ConexionBD {

    private final String url = "jdbc:postgresql://localhost:5432/baseFinal";

    private final String usuario = "postgres";

    private final String contrasena = "qwerty79";

    private Connection con = null;

    private Statement stm = null;

    private ResultSet rs = null;

    public ConexionBD() {
    }

    public void conectar() {

        try {
            con = ResourceManager.getConnection();

            System.out.println("Establecida la conexión con la base de datos");
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }

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
//        try {
//            if (!con.isClosed()) {
//                con.close();
//                System.out.println("Se cerro la conexion en ConexionBD ");
//            } 
//            
//            if(con.isClosed()) {
//                System.out.println("Se abrira conexion en ConexionBD referenciando a resource manager ");
//                con = ResourceManager.getConnection();
//            }
//        } catch (SQLException ex) {
//            System.out.println("Error al comprobar la conexion");
//            System.out.println(ex.getMessage());
//        }

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
