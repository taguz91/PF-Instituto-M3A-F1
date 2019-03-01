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

    //private String url = "jdbc:postgresql://localhost:5433/BDPFInstitutoM3A"; //BD Johnny PCPRO

    //private String url = "jdbc:postgresql://localhost:5433/BDPFInstitutoM3A"; //BD Johnny PCPRO

    //private String url = "jdbc:postgresql://localhost:5432/BDPFInstitutoM3A"; //BD Johnny PCNOOB
    //private String url = "jdbc:postgresql://localhost:5432/baseCompleta"; //BD Lina
    //private String url = "jdbc:postgresql://localhost:5432/ingreso"; //BD PAO M 
    //private String url = "jdbc:postgresql://localhost:5433/BDPFInstituto"; //BD Johnny PCPRO
    //private String url = "jdbc:postgresql://localhost:5432/PFInstituto"; //BD Johnny PCNOOB
    //private String url = "jdbc:postgresql://localhost:5432/baseCompleta"; //BD Lina
    //private String url = "jdbc:postgresql://localhost:5432/PFInstituto"; //BD Johnny PCPRO
    //private String url = "jdbc:postgresql://localhost:5432/ingreso"; //BD PAO MEDINA 
    private String url = "jdbc:postgresql://localhost:5432/Proyecto"; // BD Andres Novillo
    private String user = "postgres";
    //private String pass = "Holapostgres"; //Clave Johnny

    //private String pass = "linis4413"; //Clave Lina
    private String pass = "Holapostgres"; //Clave Johnny
    //private String pass = "PAOLA"; //Clave Paola
    //private String pass = "NuEvOsErVeR1997"; // Clave Andres

    public ConectarDB() {

    }

    public ConectarDB(String mensaje) {

        try {
            //Cargamos el driver  
            Class.forName("org.postgresql.Driver");
            //Nos conectamos  
            ct = DriverManager.getConnection(url, user, pass);
            System.out.println("Nos conectamos. Desde: " + mensaje);

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
