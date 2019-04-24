/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.propiedades.Propiedades;

/**
 *
 * @author diego
 */
public class ConnDBPool {

    private static final HikariConfig config;
    private static final HikariDataSource ds;

    static {

        config = new HikariConfig();
        config.setJdbcUrl(generarURL());

        String username = Propiedades.getUserProp("username");
        config.setUsername(username);

        String password = Propiedades.getUserProp("password");
        config.setPassword(password);

        config.setMaximumPoolSize(12);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    public ConnDBPool() {
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    /*
        Este metodo lee el archivo "configuracion.properties" de la raiz del proyecto
        y genera una URL con los valores que toma del archivo
     */
    public static String generarURL() {

        String ip = Propiedades.getPropertie("ip");
        String port = Propiedades.getPropertie("port");
        String database = Propiedades.getPropertie("database");
        return "jdbc:postgresql://" + ip + ":" + port + "/" + database;
    }

    /*
        METODOS DE CIERRE
     */
    public void close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConectionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void close(PreparedStatement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConectionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConectionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
