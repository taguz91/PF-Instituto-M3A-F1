
package MODELO.conectBD.DAO;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author arman
 */
public class pgConection {
    
    private static final String JDBC_DRIVER   = "org.postgresql.Driver";
    
    private static final String JDBC_URL      = "jdbc:postgresql://localhost:5432/mvcdb";

    private static final String JDBC_USER     = "postgres";
    
    private static final String JDBC_PASSWORD = "m4st3r0r";

    private static Driver driver = null;

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
            catch (ClassNotFoundException | IllegalAccessException | InstantiationException e)
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


    public static void close(Connection conn)
    {
            try {
                    if (conn != null) conn.close();
            }
            catch (SQLException sqle)
            {
                    sqle.printStackTrace();
            }
    }

    public static void close(PreparedStatement stmt)
    {
            try {
                    if (stmt != null) stmt.close();
            }
            catch (SQLException sqle)
            {
                    sqle.printStackTrace();
            }
    }

    public static void close(ResultSet rs)
    {
            try {
                    if (rs != null) rs.close();
            }
            catch (SQLException sqle)
            {
                    sqle.printStackTrace();
            }
}
}
