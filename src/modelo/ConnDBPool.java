package modelo;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool.PoolInitializationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import modelo.propiedades.Propiedades;

/**
 *
 * @author diego
 */
public class ConnDBPool {

    private static HikariConfig config;
    private static HikariDataSource ds;

    private PreparedStatement stmt;
    private ResultSet rs;

    public ConnDBPool() {
    }

    public ConnDBPool(String username, String password) {
        try {

            config = new HikariConfig();
            config.setJdbcUrl(generarURL());

            config.setUsername(username);

            config.setPassword(password);

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
    private String generarURL() {

        String ip = Propiedades.getPropertie("ip");
        String port = Propiedades.getPropertie("port");
        String database = Propiedades.getPropertie("database");
        return "jdbc:postgresql://" + ip + ":" + port + "/" + database;
    }

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

            System.out.println("---->" + stmt.toString());
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
}
