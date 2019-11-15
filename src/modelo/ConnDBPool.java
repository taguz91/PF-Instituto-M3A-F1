package modelo;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool.PoolInitializationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import javax.swing.JOptionPane;

/**
 *
 * @author diego
 */
public class ConnDBPool {

    private static HikariConfig config;
    private static HikariDataSource ds;

    private PreparedStatement stmt;
    private ResultSet rs;

    private static ConnDBPool CONPOOL;

    public ConnDBPool() {
        if (config == null && ds == null) {
            CONPOOL = new ConnDBPool(null);
        }
    }

    public static ConnDBPool single() {
        if (CONPOOL == null) {
            CONPOOL = new ConnDBPool();
        }

        return CONPOOL;
    }

    public ConnDBPool(Object param) {
        try {

            config = new HikariConfig();
            config.setJdbcUrl(CONS.BD_URL);

            config.setUsername(CONS.getBDUser());

            config.setPassword(CONS.BD_PASS);

            config.setMaximumPoolSize(2);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            /*
                CONFIG A PROBAR
             */
            config.addDataSourceProperty("allowMultiQueries", "true");
            config.addDataSourceProperty("useServerPrepStmts", "true");
            ds = new HikariDataSource(config);
        } catch (PoolInitializationException e) {
        }
    }

    public Connection getConnection() {
        try {
//            while (!Middlewares.isConnected()) {
//                JOptionPane.showMessageDialog(null, "POR FAVOR CONECTECE A INTERNET!!!!");
//            }
            return ds.getConnection();
        } catch (SQLException e) {
            return null;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="METODOS DE MANEJO DE DATOS"> 
    public SQLException ejecutar(String sql, Connection conn, Map<Integer, Object> parametros) {
        //this.conn = conn;
        try {

            if (parametros == null) {
                stmt = conn.prepareStatement(sql);
            } else {
                stmt = prepararStatement(sql, conn, parametros);
            }

            stmt.executeUpdate();

            parametros = null;
//            System.out.println("*******************************************");
//            System.out.println("*PreparedStatement ejecutado correctamente*");
//            System.out.println("*******************************************");

//            System.out.println("---->" + stmt.toString());
            return null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            close(conn);
            return e;
        } finally {
            closeStmt().close(conn);
        }
    }

    public SQLException ejecutar(String sql, Map<Integer, Object> parametros) {
        //this.conn = conn;
        Connection conn = getConnection();
        try {

            if (parametros == null) {
                stmt = conn.prepareStatement(sql);
            } else {
                stmt = prepararStatement(sql, conn, parametros);
            }

            stmt.executeUpdate();

//            System.out.println("*******************************************");
//            System.out.println("*PreparedStatement ejecutado correctamente*");
//            System.out.println("*******************************************");
            return null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            close(conn);
            return e;
        } finally {
            closeStmt().close(conn);
        }
    }

    private PreparedStatement prepararStatement(String sql, Connection conn, Map<Integer, Object> parametros) throws SQLException {

        stmt = conn.prepareStatement(sql);

        try {
            int threads = 4;
            if (parametros.size() > 10) {
                threads = 6;
            }

            CONS.getPool(threads).submit(() -> {
                parametros.entrySet().parallelStream().forEach(new Consumer<Map.Entry<Integer, Object>>() {

                    int posicion = 1;

                    @Override
                    public void accept(Map.Entry<Integer, Object> entry) {

                        try {
                            posicion = entry.getKey();
                            if (entry.getValue() instanceof String) {

                                stmt.setString(posicion, entry.getValue().toString());

                            } else if (entry.getValue() instanceof Integer) {

                                stmt.setInt(posicion, (int) entry.getValue());

                            } else if (entry.getValue() instanceof Double) {

                                stmt.setDouble(posicion, (double) entry.getValue());

                            } else if (entry.getValue() instanceof LocalTime) {

                                stmt.setTime(posicion, java.sql.Time.valueOf((LocalTime) entry.getValue()));

                            } else if (entry.getValue() instanceof LocalDate) {

                                stmt.setDate(posicion, java.sql.Date.valueOf((LocalDate) entry.getValue()));

                            } else if (entry.getValue() instanceof Boolean) {

                                stmt.setBoolean(posicion, (boolean) entry.getValue());

                            } else if (entry.getValue() instanceof Boolean) {

                                stmt.setBoolean(posicion, (boolean) entry.getValue());

                            } else if (entry.getValue() instanceof Byte[]) {

                                stmt.setBytes(posicion, (byte[]) entry.getValue());

                            }
                        } catch (SQLException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                });
            }).get();
            CONS.THREAD_POOL.shutdown();
        } catch (InterruptedException | ExecutionException ex) {
            System.out.println(ex.getMessage());
        }
        return stmt;
    }

    public ResultSet ejecutarQuery(String sql, Connection conn, Map<Integer, Object> parametros) {
        try {
            if (parametros == null) {
                stmt = conn.prepareStatement(sql);
            } else {
                stmt = prepararStatement(sql, conn, parametros);
            }
            rs = stmt.executeQuery();

            parametros = null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return rs;
    }

    public ResultSet ejecutarQuery(String sql, Map<Integer, Object> parametros) {
        Connection conn = getConnection();
        try {
            if (parametros == null) {
                stmt = conn.prepareStatement(sql);
            } else {
                stmt = prepararStatement(sql, conn, parametros);
            }
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return rs;
    }

    public ResultSet ejecutarQuery(String sql) {
        Connection conn = getConnection();
        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return rs;
    }

    public ConnDBPool close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return this;
    }

    public ConnDBPool close(ResultSet rs) {
        try {
            rs.close();
            Connection conn = rs.getStatement().getConnection();
            rs.getStatement().close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return this;
    }

    public ConnDBPool closeStmt() {
        try {
            stmt.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return this;
    }

    public PreparedStatement getStmt() {
        return stmt;
    }

    public ResultSet getRs() {
        return rs;
    }
    // </editor-fold>  

    public void closePool() {
        if (ConnDBPool.ds != null && !ConnDBPool.ds.isClosed()) {
            ConnDBPool.ds.close();
        }
    }

    // Metodos JOHNNY
    public PreparedStatement getPSPOOL(String sql) {
        try {
            return getConnection().prepareStatement(sql);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error de conexion con el servidor. \n"
                    + e.getMessage()
            );
            return null;
        }
    }

    public void noSQLPOOL(PreparedStatement ps) {
        try {
            ps.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al ejecutar el script. \n"
                    + e.getMessage(),
                    "Error SQL",
                    JOptionPane.ERROR_MESSAGE
            );
        } finally {
            try {
                ps.getConnection().close();
                ps.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null,
                        "Error al cerrar conexion. \n"
                        + e.getMessage(),
                        "Error servidor",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    public PreparedStatement getPSID(String sql) {
        try {
            return getConnection()
                    .prepareStatement(
                            sql,
                            Statement.RETURN_GENERATED_KEYS
                    );
        } catch (SQLException e) {
            return null;
        }
    }

    public int getIDGenerado(PreparedStatement ps) {
        int id = 0;
        try {
            ResultSet res = ps.getGeneratedKeys();
            if (res.next()) {
                id = res.getInt(1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al obtener el ID generado. "
                    + e.getMessage(),
                    "Error ID",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return id;
    }

    public void cerrarCONPS(PreparedStatement ps) {
        try {
            ps.getConnection().close();
        } catch (SQLException e) {
        }
    }

}
