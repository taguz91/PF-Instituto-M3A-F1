package modelo;

import controlador.login.LoginCTR;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.propiedades.Propiedades;

/**
 *
 * @author MrRainx
 */
public class ResourceManager implements Serializable {

    private static boolean TRANSACCION = true;

    private static final String JDBC_DRIVER = "org.postgresql.Driver";

    private static String JDBC_URL = "";
    private static String USERNAME = "";
    private static String PASSWORD = "";
    private static Driver driver = null;

    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;

    public static synchronized Connection getConnection()
            throws SQLException {

        if (driver == null) {
            try {

                Class jdbcDriverClass = Class.forName(JDBC_DRIVER);
                driver = (Driver) jdbcDriverClass.newInstance();
                DriverManager.registerDriver(driver);

                USERNAME = LoginCTR.USERNAME;
                PASSWORD = LoginCTR.PASSWORD;

            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
                System.out.println(e.getMessage());
            }

        }

        if (conn == null || conn.isClosed()) {
            JDBC_URL = generarURL();
            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        }

        return conn;

    }

    public static SQLException Statement(String Statement) {

        try {
            INICIAR_TRANSACCION();
            //System.out.println(Statement);
            if (conn == null) {
                conn = getConnection();
            }

            stmt = conn.createStatement();

            stmt.execute(Statement);
            TERMINAR_TRANSACCION();
            return null;

        } catch (SQLException | NullPointerException e) {
            if (e instanceof NullPointerException) {
                driver = null;
            } else {
                System.out.println(e.getMessage());
                return (SQLException) e;
            }
            TERMINAR_TRANSACCION();
        }
        return null;
    }

    public static void Statements(String Statement) {
        try {
            INICIAR_TRANSACCION();
            //System.out.println(Statement);
            if (conn == null || conn.isClosed()) {
                conn = getConnection();
            }

            stmt = conn.createStatement();

            stmt.execute(Statement);
            TERMINAR_TRANSACCION();
        } catch (SQLException | NullPointerException e) {
            System.out.println(e.getMessage());
            TERMINAR_TRANSACCION();
        }
    }

    public static ResultSet Query(String Query) {

        try {
            INICIAR_TRANSACCION();
            //System.out.println(Query);
            if (conn == null || conn.isClosed()) {
                System.out.println("QUERY!!");
                conn = getConnection();
            }
            stmt = conn.createStatement();

            rs = stmt.executeQuery(Query);

            TERMINAR_TRANSACCION();
            return rs;

        } catch (SQLException | NullPointerException e) {
            if (e instanceof NullPointerException) {
                driver = null;
            } else {

                String mensaje = e.getMessage();

                if (mensaje.contains("FATAL: password authentication failed for user")
                        || mensaje.contains("FATAL: no PostgreSQL user name specified in startup packet")) {
                    driver = null;
                }

                System.out.println(e.getMessage());
            }
            TERMINAR_TRANSACCION();
            return null;
        }
    }

    public static void cerrarSesion() {
        try {
            if (conn != null) {
                conn.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
            driver = null;
            stmt = null;
            conn = null;
            rs = null;
        } catch (SQLException ex) {
            Logger.getLogger(ResourceManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String generarURL() {

        String ip = Propiedades.getPropertie("ip");
        String port = Propiedades.getPropertie("port");
        String database = Propiedades.getPropertie("database");

        return "jdbc:postgresql://" + ip + ":" + port + "/" + database;
    }

    /**
     * Para logearme como invitado
     *
     * @param cn
     */
    public static void setConecct(Connection cn) {
        ResourceManager.conn = cn;
    }

    public static void resetConn() {
//        new Thread(() -> {
//            for (;;) {
//                try {
//                    Thread.sleep(1000 * 10);
//                    if (!TRANSACCION) {
//                        cerrarSesion();
//                        try {
//                            ResourceManager.getConnection();
//                        } catch (SQLException ex) {
//                            Logger.getLogger(ResourceManager.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    }
//                    System.out.println("SE REINICIO LA CONEXION");
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(ResourceManager.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//            }
//        }).start();
    }

    public static void INICIAR_TRANSACCION() {
        //System.out.println("HA INICIADO UNA TRANSACCION");
        TRANSACCION = true;
    }

    public static void TERMINAR_TRANSACCION() {
        //System.out.println("HA TERMINADO LA TRANSACCION");
        TRANSACCION = false;
    }
}
