package modelo;

import controlador.login.LoginCTR;
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
public class ResourceManager {

    private static final String JDBC_DRIVER = "org.postgresql.Driver";

    private static String JDBC_URL = "";
    private static String USERNAME = "ROOT";
    private static String PASSWORD = "ROOT";
    private static Driver driver = null;

    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;
    private static Connection conex = null;

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

        if (conex == null) {
            System.out.println("CONEXION");
            JDBC_URL = generarURL();
            conex = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        }

        return conex;

    }

    public static SQLException Statement(String Statement) {

        try {

            //System.out.println(Statement);
            if (conn == null) {
                conn = getConnection();
            }

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
            if (conn == null) {
                conn = getConnection();
            }

            stmt = conn.createStatement();

            stmt.execute(Statement);

        } catch (SQLException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ResultSet Query(String Query) {

        try {

            //System.out.println(Query);
            if (conn == null || conn.isClosed()) {
                conn = getConnection();
            }
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
            conn.close();
            conex.close();
            stmt.close();
            rs.close();

            driver = null;
            stmt = null;
            conn = null;
            conex = null;
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
     * @param cn 
     */
    public static void setConecct(Connection cn){
        ResourceManager.conn = cn;
        ResourceManager.conex = cn;
    }
}
