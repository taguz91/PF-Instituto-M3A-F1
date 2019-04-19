package modelo;

import controlador.login.LoginCTR;
import java.io.File;
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
        System.out.println("Se inicio la conexion. En resource manager");
        if (driver == null) {
            try {

                Class jdbcDriverClass = Class.forName(JDBC_DRIVER);
                driver = (Driver) jdbcDriverClass.newInstance();
                DriverManager.registerDriver(driver);

            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
                System.out.println(e.getMessage());
            }

        }

        if (conn != null) {
            //cerrarSesion();

            //conn.close();
            resetConn();
        }

        if (conn == null || conn.isClosed()) {
            //cerrarSesion();
            resetConn();
        }

        return conn;

    }

    private static void resetConn() {
        try {
            JDBC_URL = generarURL();

            USERNAME = Propiedades.getUserProp("username");
            PASSWORD = Propiedades.getUserProp("password");

            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            System.out.println("INTENTE NUEVAMENTE");
        }
    }

    public static SQLException Statement(String Statement) {

        try {
            //System.out.println(Statement);

            conn = getConnection();

            stmt = conn.createStatement();

            stmt.execute(Statement);
            return null;

        } catch (SQLException | NullPointerException e) {
            if (e instanceof NullPointerException) {
                driver = null;
            } else {
                System.out.println(e.getMessage());
                return (SQLException) e;
            }

        }
        return null;
    }

    public static void Statements(String Statement) {
        try {
            //System.out.println(Statement);

            conn = getConnection();

            stmt = conn.createStatement();

            stmt.execute(Statement);
        } catch (SQLException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ResultSet Query(String Query) {

        try {

            //System.out.println(Query);
            conn = getConnection();

            stmt = conn.createStatement();

            rs = stmt.executeQuery(Query);

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
}
