package modelo;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Usuario
 */
public class ConectarDB {

    private Connection ct;
    private Statement st;
    private ResultSet rs;
    //Si se cambia el url no borrar solo comentar 
    private String url = "jdbc:postgresql://localhost:5433/BDInstitutoPF"; //BD Johnny PCPRO
    private String user = "postgres";
    private String pass = "Holapostgres";

    public ConectarDB() {
        try {
            //Cargamos el driver  
            Class.forName("org.postgresql.Driver");
            //Nos conectamos  
            ct = DriverManager.getConnection(url, user, pass);
            System.out.println("Nos conectamos.");

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("No pudimos conectarnos DB. " + e.getMessage());
        }
    }

    public PreparedStatement sqlPS(String nsql) {
        try {
            PreparedStatement ps = ct.prepareStatement(nsql);
            return ps;
        } catch (SQLException e) {
            System.out.println("No se pudo preparar el statement. " + e.getMessage());
            return null;
        }
    }

    public SQLException nosql(String noSql) {
        try {
            //Variable para las transacciones  
            st = ct.createStatement();
            //Ejecutamos la sentencia SQL  
            st.execute(noSql);
            //Cerramos la consulta  
            st.close();
            //Si todo salio bienn retornamos nulo
            return null;
        } catch (SQLException e) {
            System.out.println("No pudimos realizar la accion " + e.getMessage());
            return e;
        }
    }

    public ResultSet sql(String sql) {
        try {
            //Iniciamos la variable para las transacciones  
            st = ct.createStatement();
            //Ejecutamos la consulta
            rs = st.executeQuery(sql);
            //Si todo salio bien retornamos los resultados.
            return rs;
        } catch (SQLException e) {
            System.out.println("No pudimos realizar la consulta. " + e.getMessage());
            return null;
        }

    }
}
