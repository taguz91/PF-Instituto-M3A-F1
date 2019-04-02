package modelo;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.propiedades.Propiedades;

/**
 *
 * @author Usuario
 */
public class ConectarDB {

    private Connection ct;
    private Statement st;
    private ResultSet rs;

    //BD En cloud
    private String url = "";

    public ConectarDB(String user, String pass, String mensaje) {
        try {
            //Cargamos el driver
            Class.forName("org.postgresql.Driver");
            //Nos conectamos
            url = Propiedades.loadIP();
            //Base de datos que entrara en prueba el dia de mañana no modificar nada 
            //url = "jdbc:postgresql://35.193.226.187:5432/BDpruebas";
            //url = "jdbc:postgresql://localhost:5432/BDinsta";
            //url = "jdbc:postgresql://35.193.226.187:5432/BDinsta";
            //url = "jdbc:postgresql://localhost:5432/baseNueva";
            //url = "jdbc:postgresql://LocalHost:5432/BD_Final";
            //url = "jdbc:postgresql://35.193.226.187:5432/BDinsta";
            //url = "jdbc:postgresql://localhost:5432/BDinsta";
            //url = "jdbc:postgresql://localhost:5432/Personas_Lleno";
            //url = "jdbc:postgresql://localhost:5432/BDPFConAlumnosCurso";
            //url = "jdbc:postgresql://localhost:5432/Proyecto_Final";//BD Andres
            ct = ResourceManager.getConnection();
            //ct = DriverManager.getConnection(url, user, pass);
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
//            if (st == null) {
                st = ct.createStatement();
//            }
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
