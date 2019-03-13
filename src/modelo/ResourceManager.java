package modelo;

import controlador.login.LoginCTR;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MrRainx
 */
public class ResourceManager {

    private static final String JDBC_DRIVER = "org.postgresql.Driver";

    private static String USERNAME = "";
    private static String PASSWORD = "";

    private static Driver driver = null;

    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;

    private static synchronized Connection getConnection()
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

                /*
                    ITERA LAS LISTAS DE LAS Urls 
                 */
                USERNAME = LoginCTR.USERNAME;
                PASSWORD = LoginCTR.PASSWORD;

            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
                System.out.println(e.getMessage());

            }

        }

        for (String url : urls()) {
            try {
                conex = DriverManager.getConnection(url, USERNAME, PASSWORD);
                break;
            } catch (SQLException e) {
                System.out.println("");
            }
        }

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

    private static List<String> urls() {
        List<String> listaUrls = new ArrayList<>();

        listaUrls.add("jdbc:postgresql://localhost:5432/Proyecto-Academico-Insta");//DIEGO LAPTOP
        listaUrls.add("jdbc:postgresql://192.168.1.12:5432/Proyecto-Academico-Insta");//DIEGO LAPTOP
        listaUrls.add("jdbc:postgresql://localhost:5433/BDPFInstitutoM3A");//JHONNY
        listaUrls.add("jdbc:postgresql://localhost:5432/BDPFMallaEstudiante");//LINA
        listaUrls.add("jdbc:postgresql://localhost:5432/ingreso");//PAO MEDINA
        listaUrls.add("jdbc:postgresql://localhost:5432/Proyecto");//ANDRES NOVILLO
        listaUrls.add("jdbc:postgresql://localhost:5432/Pruebas");//PAO MEDINA

        return listaUrls;
    }

}
