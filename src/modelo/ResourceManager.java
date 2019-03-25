package modelo;

import controlador.login.LoginCTR;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author MrRainx
 */
public class ResourceManager {

    private static final String JDBC_DRIVER = "org.postgresql.Driver";
<<<<<<< HEAD
    private static String JDBC_URL = "jdbc:postgresql://35.193.226.187:5432/BDinsta";//BD cloud
=======

    //private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/BDinsta";
    private static final String JDBC_URL = "jdbc:postgresql://35.193.226.187:5432/BDinsta";//BD cloud
    //private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/Proyecto_Final";//BD Andres

>>>>>>> d6708c7996af14dcbe84898671d8d2b9b082839e
    private static String USERNAME = "";
    private static String PASSWORD = "";

    private static Driver driver = null;

    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;

    public static synchronized Connection getConnection()
            throws SQLException {

        Connection conex = null;

      if (driver == null) {
            try {
                /*
                    JAVA REFLECTION
                 */
                Class jdbcDriverClass = Class.forName(JDBC_DRIVER);
                driver = (Driver) jdbcDriverClass.newInstance();
                DriverManager.registerDriver(driver);

                USERNAME = LoginCTR.USERNAME;
                PASSWORD = LoginCTR.PASSWORD;

            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
                System.out.println(e.getMessage());

            }

        }
<<<<<<< HEAD
        
        JDBC_URL = "jdbc:postgresql://localhost:5432/Pruebas";
        USERNAME = "postgres";
        PASSWORD = "PAOLA";
=======


//        JDBC_URL = "jdbc:postgresql://localhost:5433/BDPFM3AConMallaCursada";
//        USERNAME = "postgres";
//        PASSWORD = "Holapostgres";
>>>>>>> d6708c7996af14dcbe84898671d8d2b9b082839e
        conex = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

        return conex;

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
                System.out.println(e.getMessage());
            }
            return null;
        }
    }

}
