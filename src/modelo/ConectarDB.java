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
    
    //Base de datos en la nube ... si no quiere conectarse a la nube comente esto
    private String url = "jdbc:postgresql://35.193.226.187:5432/BDinsta";
    private String user = "ROOT";
    private String pass = "ROOT";

    //private String url = "jdbc:postgresql://localhost:5433/BDPFM3AConCursados"; //BD Johnny PCPRO
    //private String url = "jdbc:postgresql://localhost:5433/BDPFM3A"; //BD Johnny PCPRO
    //private String url = "jdbc:postgresql://localhost:5432/BDPFM3A"; //BD Johnny PCNOOB
    // private String url = "jdbc:postgresql://localhost:5433/BDPFInstitutoM3A"; //BD Johnny PCPRO
    //private String url = "jdbc:postgresql://localhost:5432/proyecto1"; //BD ARMANDO
    //private String url = "jdbc:postgresql://190.11.21.244:5432/BDinsta"; //BD Diego
    //private String url = "jdbc:postgresql://192.168.1.12:5432/Proyecto-Academico-Insta"; //BD Diego

    //Base de datos en la nube ... si no quiere conectarse a la nube comente esto 
    //jdbc:postgresql://35.193.226.187:5432/BDinsta
//    private String url = "jdbc:postgresql://35.193.226.187:5432/BDinsta";
//    private String user = "ROOT";
//    private String pass = "ROOT";

    
    //private String url = "jdbc:postgresql://localhost:5433/BDPFM3AConMallaCursada"; //BD David
    //private String url = "jdbc:postgresql://localhost:5432/proyecto"; //BD David

    // private String url = "jdbc:postgresql://localhost:5432/PFcompleta"; //BD Lina

   // private String url = "jdbc:postgresql://localhost:5432/BDTESIS"; //BD Johnny PCNOOB

    //private String url = "jdbc:postgresql://localhost:5432/ingreso"; //BD PAO MEDINA
    //private String url = "jdbc:postgresql://localhost:5432/Proyecto"; // BD Andres Novillo
    //private String url = "jdbc:postgresql://192.168.1.12:5432/Proyecto-Academico-Insta"; //BD Diego ServerLaptop

    //private String url = "jdbc:postgresql://localhost:5432/BDPFCompletaPruebas"; //BD Lina
    //private String url = "jdbc:postgresql://localhost:5432/ingreso"; //BD PAO M
    //private String url = "jdbc:postgresql://localhost:5432/Pruebas"; //BD PAO MEDINA

    //private String url = "jdbc:postgresql://localhost:5432/BDPFCompletaPruebas"; //BD Lina
    

    //private String user = "ROOT";

    //private String user = "ROOT";
    //private String user = "postgres";
    //private String url = "jdbc:postgresql://localhost:5432/Proyecto"; // BD Andres Novillo
    //  private String url = "jdbc:postgresql://localhost:5432/Pruebas"; //BD PAO MEDINA
    //private String user = "ROOT";//USUARIO DIEGO

  
    //private String user = "postgres";


    //private String pass = "ROOT"; //Clave Diego
    //private String pass = "2197";// CLAVE ARMANDO
    //private String pass = "Holapostgres"; //Clave Johnny
    //private String pass = "postgres"; //Clave cesar
    //private String pass = "ROOT"; //Clave Diego
    //private String pass = "1234"; //Clave Alejandro
    //private String pass = ""; //Clave Diego
    //private String pass = "PAOLA"; //Clave Paola
    //private String pass = "NuEvOsErVeR1997"; // Clave Andres

    //private String pass = "linis4413"; //Clave Lina
    public ConectarDB(String mensaje) {
    }
    //private String pass = "davicho"; // Clave David
    //private String pass = "linis4413"; //Clave Lina*/
    //Base de datos en la nube ... si no quiere conectarse a la nube comente esto
    //private String url = "jdbc:postgresql://localhost:5432/BDinsta";
    //private String url = "jdbc:postgresql://35.193.226.187:5432/BDinsta";//BD cloud
//    private String user = "ROOT";
//    private String pass = "ROOT";

    public ConectarDB(String user, String pass, String mensaje) {

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

    public Connection getConecction() {
        return ct;
    }
}
