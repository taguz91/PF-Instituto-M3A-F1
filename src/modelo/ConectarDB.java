package modelo;

import controlador.principal.ConexionesCTR;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Usuario
 */
public class ConectarDB {

    private Connection ct;
    private Statement st;
    private ResultSet rs;
    private ResultSetMetaData metaData;
    private String user, pass, url;
    //Iniciamos el hilo 
    private ConexionesCTR ctrCt;
    //"Transaccion de tipo read commited
    //Se ven solo las modificaciones ya guardadas hechas por otras transacciones

    //BD En cloud
    public ConectarDB(String user, String pass) {
        try {
            //Cargamos el driver
            Class.forName("org.postgresql.Driver");
            //Nos conectamos
            url = "jdbc:postgresql://35.193.226.187:5432/BDcierre";
            this.user = user;
            this.pass = pass;

            //ct = DriverManager.getConnection(url, user, pass);
            ct = DriverManager.getConnection(url, user, pass);
            ctrCt = new ConexionesCTR(ct);
            ctrCt.iniciar("Constructor conectarBD");
            //ResourceManager.setConecct(ct);
            System.out.println("Nos conectamos. Como invitados: " + user);
        } catch (ClassNotFoundException e) {
            System.out.println("No pudimos conectarnos DB. " + e.getMessage());
        } catch (SQLException ex) {
            System.out.println("No se puede conectar.");
        }
    }

    public ConectarDB(String user, String pass, String mensaje) {
        try {
            //Cargamos el driver
            Class.forName("org.postgresql.Driver");
            //Nos conectamos
            this.user = user;
            this.pass = pass;
            conecta();
            ct = DriverManager.getConnection(url, user, pass);
            ctrCt = new ConexionesCTR(ct);
            ctrCt.iniciar("Contructor ConectarBD");
            //ct = ResourceManager.getConnection();
            //ct = DriverManager.getConnection(url, user, pass);
            System.out.println("Nos conectamos. Desde: " + mensaje);
        } catch (ClassNotFoundException e) {
            System.out.println("No pudimos conectarnos DB. " + e.getMessage());
        } catch (SQLException ex) {
            System.out.println("No nos pudimos conectar.");
        }
    }

    public PreparedStatement sqlPS(String nsql) {
        try {
            //ct = ResourceManager.getConnection();
            if (ct.isClosed()) {
                ct = DriverManager.getConnection(url, user, pass);
                ctrCt = new ConexionesCTR(ct);
                ctrCt.iniciar("sqlPS Clase ConectarBD");
            }
            PreparedStatement ps = ct.prepareStatement(nsql);

            return ps;
        } catch (SQLException e) {
            System.out.println("No se pudo preparar el statement. " + e.getMessage());
            ctrCt.matarHilo();
            return null;
        } finally {

            ctrCt.recetear("Terminando de preparar un statamente.");
//            try {
//                ct.close();
//                System.out.println("Cerramos conexion: Luego de hacer hacer un prepared statement");
//            } catch (SQLException ex) {
//                System.out.println("No se pudo cerrar la conexion");
//            }
        }
    }

    public SQLException nosql(String noSql) {
        try {
            //Variable para las transacciones
            //ct = ResourceManager.getConnection();
            if (ct.isClosed()) {
                ct = DriverManager.getConnection(url, user, pass);
                ctrCt = new ConexionesCTR(ct);
                ctrCt.iniciar("nosql Clase: ConectarBD");
            }
            st = ct.createStatement();
            //Ejecutamos la sentencia SQL
            st.execute(noSql);
            System.out.println("---------NSQL-----------");
            System.out.println("Afecto a: " + st.getUpdateCount());
            System.out.println();
            rs = st.getGeneratedKeys();
            while (rs.next()) {
                System.out.println("ID generado: " + rs.getInt(1));
            }
            System.out.println("No hay id");
            System.out.println("--------------------");
            //idGenerado = st.getGeneratedKeys().getInt(0);
            return null;
        } catch (SQLException e) {
            System.out.println("No pudimos realizar la accion " + e.getMessage());
            ctrCt.matarHilo();
            return e;
        } finally {
            try {
                //Cerramos la consulta
                st.close();
                ctrCt.recetear("Terminando de ejecutar una transaccion.");
//                //Si todo salio bienn retornamos nulo
//                ct.close();
//                if (ct.isClosed()) {
//                    System.out.println("CERRAMOS CONEXION: Despues de realizar una transaccion.");
//                }
            } catch (SQLException ex) {
                System.out.println("NO SE CERRARON LAS CONEXIONES");
            }
        }
    }

    public ResultSet sql(String sql) {
        try {
            //Iniciamos la variable para las transacciones
            //ct = ResourceManager.getConnection();
            if (ct.isClosed()) {
                ct = DriverManager.getConnection(url, user, pass);
                ctrCt = new ConexionesCTR(ct);
                ctrCt.iniciar("SQL desde conectarBD");
            }

            st = ct.createStatement();
            //Ejecutamos la consulta
            rs = st.executeQuery(sql);
            metaData = rs.getMetaData();
//            System.out.println("--------SQL----------");
//            System.out.println(ct.getSchema());
//            System.out.println("Tabla en la que se consulta: " + metaData.getTableName(1));
//            System.out.println("Numero de columnas devueltas: " + metaData.getColumnCount());
//            System.out.println("Nombre Base de datos: " + ct.getCatalog());
//            System.out.println();
//            System.out.println("------------------");
            return rs;
        } catch (SQLException e) {
            System.out.println("No pudimos realizar la consulta. " + e.getMessage());
            ctrCt.matarHilo();
            return null;
        } finally {
            ctrCt.recetear("Terminando de realizar una consulta.");
//            try {
//                ct.close();
//                System.out.println("CERRAMOS CONEXION: Despues de hacer una consulta");
//            } catch (SQLException ex) {
//                System.out.println("No se pudo cerrar la conexion.");
//            }
        }
    }

    public Connection getConecction() {
        try {
            if (ct.isClosed()) {
                System.out.println("La conexion fue cerrada no podemos retornarla. Debemos abrir una nueva");
                ctrCt = new ConexionesCTR(ct);
                ctrCt.iniciar("Get Connection Clase: ConectarBD");
                ct = DriverManager.getConnection(url, user, pass);

                return ct;
            } else {
                System.out.println("Esta abierta la conexion.");
                ctrCt.recetear("SE recea al devolver la conexion.");

                return ct;
            }
        } catch (SQLException ex) {
            System.out.println("No pudimos comprobar el estado de la conexion." + ex.getMessage());
            ctrCt.matarHilo();
            return null;
        }

    }

    private void conecta() {
        this.url = ResourceManager.generarURL();
    }

    public void mostrarReporte(JasperReport jr, Map parametro, String titulo) {
        try {
            if (ct.isClosed()) {
                ct = DriverManager.getConnection(url, user, pass);
                ctrCt = new ConexionesCTR(ct);
                ctrCt.iniciar("Mostrar reporte desde ConectarBD");
            }
            JasperPrint print = JasperFillManager.fillReport(jr, parametro, ct);
            JasperViewer view = new JasperViewer(print, false);
            view.setVisible(true);
            view.setTitle(titulo);
        } catch (SQLException ex) {
            System.out.println("No se puede imprimir el reporte. " + ex.getMessage());
            ctrCt.matarHilo();
            //mostrarReporte(jr, parametro, titulo); 
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "Error en reporte" + ex);
        } finally {
            ctrCt.recetear("Terminando de imprimir un reporte.");
//            try {
//                ct.close();
//                System.out.println("CERRAMOS CONEXION: Despues de imprimir un reporte.");
//            } catch (SQLException ex) {
//                System.out.println("No se pudo cerrar la conexion. Al imprimir un reporte.");
//            }
        }
    }
}
