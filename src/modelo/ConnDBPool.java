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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
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

        //String username = Propiedades.getUserProp("username");
        config.setUsername("ROOT");

        //String password = Propiedades.getUserProp("password");
        config.setPassword("RUTH");

        config.setMaximumPoolSize(5);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    public ConnDBPool() {
    }

    public Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            return null;
        }
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

    public boolean ejecutar(String sql, Connection conn, Map<Integer, Object> parametros) {

        PreparedStatement stmt = null;

        try {

            if (parametros == null) {
                stmt = conn.prepareStatement(sql);
            } else {
                stmt = prepararStatement(sql, conn, parametros);
            }

            stmt.executeUpdate();

            parametros = null;
            System.out.println("*******************************************");
            System.out.println("*PreparedStatement ejecutado correctamente*");
            System.out.println("*******************************************");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            close(conn);
        }
    }

    private PreparedStatement prepararStatement(String sql, Connection conn, Map<Integer, Object> parametros) throws SQLException {
        PreparedStatement stmt = null;

        stmt = conn.prepareStatement(sql);

        for (Map.Entry<Integer, Object> entry : parametros.entrySet()) {
            int posicion = entry.getKey();
            if (entry.getValue() instanceof Integer) {
                stmt.setInt(posicion, (int) entry.getValue());
            }

            if (entry.getValue() instanceof String) {
                stmt.setString(posicion, entry.getValue().toString());
            }

            if (entry.getValue() instanceof Double) {
                stmt.setDouble(posicion, (double) entry.getValue());
            }

            if (entry.getValue() instanceof LocalTime) {
                stmt.setTime(posicion, java.sql.Time.valueOf((LocalTime) entry.getValue()));
            }

            if (entry.getValue() instanceof LocalDate) {
                stmt.setDate(posicion, java.sql.Date.valueOf((LocalDate) entry.getValue()));
            }
            if (entry.getValue() instanceof Boolean) {
                stmt.setBoolean(posicion, (boolean) entry.getValue());
            }

        }

        return stmt;
    }

    public ResultSet ejecutarQuery(String sql, Connection conn, Map<Integer, Object> parametros) {
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {
            if (parametros == null) {
                stmt = conn.prepareStatement(sql);
            } else {
                stmt = prepararStatement(sql, conn, parametros);
            }

            System.out.println("------------->");

            rs = stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            close(stmt);
        }

        return rs;
    }

    public void close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void close(PreparedStatement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
