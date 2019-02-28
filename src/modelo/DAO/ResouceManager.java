package MODELO.conectBD.DAO;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arman
 */
public class ResouceManager {
   
    
    private static final String JDBC_DRIVER   = "org.postgresql.Driver";
    
    private static final String JDBC_URL      = "jdbc:postgresql://localhost:5432/proyecto1";

    private static final String JDBC_USER     = "postgres";
    
    private static final String JDBC_PASSWORD = "2197";

    private static Driver driver = null;
    
    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;
    
public static synchronized Connection getConnection()
	throws SQLException
    {
        if (driver == null)
        {
            try
            {
                Class jdbcDriverClass = Class.forName( JDBC_DRIVER );
                driver = (Driver) jdbcDriverClass.newInstance();
                DriverManager.registerDriver( driver );
            }
            catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e)
            {
                System.out.println( "Fallo la conexion" );
                e.printStackTrace();
            }
        }

        return DriverManager.getConnection(
                JDBC_URL,
                JDBC_USER,
                JDBC_PASSWORD
        );
    }

    public static SQLException Statement(String Statement){
        
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            stmt.execute(Statement);
            stmt.close();
            return null;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return e;
        }
        
    }
    
    public static ResultSet Query(String Query){
        System.out.println(Query);
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(Query);
            conn.close();
            return rs;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        
    }
        
    public static void closeRs(){
        try {
            ResouceManager.rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ResouceManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}