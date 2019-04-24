/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MrRainx
 */
public class ConnectionUtils {

    public static boolean ejecutar(String sql, PreparedStatement stmt, Connection conn, Map<Integer, Object> parametros) {
        boolean proceso = false;

        try {
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

            }
            System.out.println("PreparedStatement creado correctamente");

            proceso = stmt.execute();

            conn.close();
            stmt.close();
            parametros = null;
            return proceso;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return proceso;
        }
    }

    public static void close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConectionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void close(PreparedStatement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConectionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConectionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
