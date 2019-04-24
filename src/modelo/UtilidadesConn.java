package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

/**
 *
 * @author MrRainx
 */
public class UtilidadesConn {

    public static boolean ejecutar(String sql, Connection conn, Map<Integer, Object> parametros) {
        boolean proceso = false;
        PreparedStatement stmt = null;
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
                if (entry.getValue() instanceof Boolean) {
                    stmt.setBoolean(posicion, (boolean) entry.getValue());
                }

            }

            proceso = !stmt.execute();

            conn.close();
            stmt.close();
            parametros = null;
            System.out.println("*******************************************");
            System.out.println("*PreparedStatement ejecutado correctamente*");
            System.out.println("*******************************************");
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
            System.out.println(ex.getMessage());
        }
    }

    public static void close(PreparedStatement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
