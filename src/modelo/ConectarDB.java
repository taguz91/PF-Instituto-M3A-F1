package modelo;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 *
 * @author Usuario
 */
public class ConectarDB {

    private Connection ct;
    private Statement st;
    private ResultSet rs;
    private int nTransaccion;
    private ResultSetMetaData metaData;

    //BD En cloud
    public ConectarDB(String user, String pass) {
        try {
            //Cargamos el driver
            Class.forName("org.postgresql.Driver");
            //Nos conectamos
            String url = "jdbc:postgresql://35.193.226.187:5432/BDcierre";

            ct = DriverManager.getConnection(url, user, pass);
            //ct = DriverManager.getConnection(url, user, pass);
            ResourceManager.setConecct(ct);
            System.out.println("Nos conectamos. Como invitados: " + user);
            nivelTransaccion();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("No pudimos conectarnos DB. " + e.getMessage());
        }
    }

    public ConectarDB(String user, String pass, String mensaje) {
        try {
            //Cargamos el driver
            Class.forName("org.postgresql.Driver");
            //Nos conectamos

            ct = ResourceManager.getConnection();
            //ct = DriverManager.getConnection(url, user, pass);
            System.out.println("Nos conectamos. Desde: " + mensaje);
            nivelTransaccion();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("No pudimos conectarnos DB. " + e.getMessage());
        }
    }

    public ConectarDB(String mensaje) {
        try {
            //Cargamos el driver
            Class.forName("org.postgresql.Driver");
            //Nos conectamos

            ct = ResourceManager.getConnection();
            //ct = DriverManager.getConnection(url, user, pass);
            System.out.println("Nos conectamos. Desde: " + mensaje);
            nivelTransaccion();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("No pudimos conectarnos DB. " + e.getMessage());
        }
    }

    public PreparedStatement sqlPS(String nsql) {
        try {
            //ct = ResourceManager.getConnection();
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
            //ct = ResourceManager.getConnection();
            st = ct.createStatement();
            //Ejecutamos la sentencia SQL
            st.execute(noSql);
            System.out.println("---------NSQL-----------");
            System.out.println("Afecto a: "+st.getUpdateCount());
            rs = st.getGeneratedKeys();
            while (rs.next()) {
                System.out.println("ID generado: " + rs.getInt(1));
            }
            System.out.println("No hay id");
            System.out.println("--------------------");
            //idGenerado = st.getGeneratedKeys().getInt(0);
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
            // ct = ResourceManager.getConnection();
            st = ct.createStatement();
            //Ejecutamos la consulta
            rs = st.executeQuery(sql);
            metaData = rs.getMetaData();
            System.out.println("--------SQL----------");
            //System.out.println(ct.getSchema());
            System.out.println(metaData.getTableName(1));
            System.out.println("Numero de columnas devueltas: " + metaData.getColumnCount());
            //System.out.println("Cantidad de tiempo de espera: "+ct.getNetworkTimeout());
            System.out.println("Nombre Base de datos: " + ct.getCatalog());
            System.out.println();
            System.out.println("------------------");
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

    private void nivelTransaccion() {
        try {
            nTransaccion = ct.getTransactionIsolation();
            System.out.println(nTransaccion);
            if (Connection.TRANSACTION_NONE == nTransaccion) {
                System.out.println("No soprota transacciones");
            }
            switch (nTransaccion) {
                case Connection.TRANSACTION_NONE:
                    System.out.println("No soporta transacciones");
                    break;
                case Connection.TRANSACTION_READ_COMMITTED:
                    System.out.println("Transaccion de tipo read commited\n"
                            + "Se ven solo las modificaciones ya guardadas hechas por otras transacciones.");
                    break;
                case Connection.TRANSACTION_READ_UNCOMMITTED:
                    System.out.println("Lectura terminada.");
                    break;
                case Connection.TRANSACTION_REPEATABLE_READ:
                    System.out.println("Soporta transacciones que pueda abseder a la base");
                    break;
                case Connection.TRANSACTION_SERIALIZABLE:
                    System.out.println("Soporta transacciones serializables. La mas alta.");
                    break;
                default:
                    System.out.println("No soporta ningun nivel de transaccion");
                    break;
            }

        } catch (SQLException ex) {
            System.out.println("No podemos obtener el nivel de transacciones que soporta.");
        }
    }
}
