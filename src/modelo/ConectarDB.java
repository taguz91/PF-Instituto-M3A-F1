package modelo;

import controlador.principal.ConexionesCTR;
import java.awt.Cursor;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;
import javax.swing.JOptionPane;
import modelo.propiedades.Propiedades;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import vista.principal.VtnPrincipal;

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
    //Para cambiar el estado de un mause cuando se hace una consulta.
    private VtnPrincipal vtnPrin;
    //Nombre de la tabla solo para testear 
    private String tabla;
    //Pool 
    private ConnDBPool pool;

    public ConectarDB(String user, String pass, String mensaje, ConnDBPool pool) {
        try {
            //Cargamos el driver
            Class.forName("org.postgresql.Driver");
            //Nos conectamos
            this.user = user;
            this.pass = pass;
            this.vtnPrin = null;
            this.url = generarURL();
            ct = DriverManager.getConnection(url, user, pass);
            this.pool = pool;

            ctrCt = new ConexionesCTR(ct);
            ctrCt.iniciar("Contructor ConectarBD || Modo Produccion");

            //ct = DriverManager.getConnection(url, user, pass);
            System.out.println("Nos conectamos. Desde: " + mensaje);
        } catch (ClassNotFoundException e) {
            System.out.println("No pudimos conectarnos DB. " + e.getMessage());
        } catch (SQLException ex) {
            System.out.println("No nos pudimos conectar.");
        }
    }

    private String generarURL() {

        String ip = Propiedades.getPropertie("ip");
        String port = Propiedades.getPropertie("port");
        String database = Propiedades.getPropertie("database");
        return "jdbc:postgresql://" + ip + ":" + port + "/" + database;
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
        }
    }

    public SQLException nosql(String noSql) {
        try {
            cursorCarga();
            if (ct.isClosed()) {
                ct = DriverManager.getConnection(url, user, pass);
                ctrCt = new ConexionesCTR(ct);
                ctrCt.iniciar("nosql Clase: ConectarBD");
            }
            st = ct.createStatement();
            //Ejecutamos la sentencia SQL
            st.executeUpdate(noSql);
            System.out.println("Tiempo de ejecucion " + st.getQueryTimeout());
            System.out.println("---------NSQL-----------");
            System.out.println("Afecto a: " + st.getUpdateCount());
            System.out.println();
            rs = st.getGeneratedKeys();
            while (rs.next()) {
                System.out.println("ID generado: " + rs.getInt(1));
            }
            System.out.println("--------------------");
            //idGenerado = st.getGeneratedKeys().getInt(0);
            return null;
        } catch (SQLException e) {
            System.out.println("No pudimos realizar la accion " + e.getMessage());
            ctrCt.matarHilo();
            return e;
        } finally {
            try {
                cursorNormal();
                //Cerramos el statement
                st.close();
                ctrCt.recetear("Terminando de ejecutar una transaccion.");
            } catch (SQLException ex) {
                System.out.println("NO SE CERRARON LAS CONEXIONES " + ex.getMessage());
            }
        }
    }

    public ResultSet sql(String sql) {
        try {
            cursorCarga();
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
            System.out.println("--------SQL----------");
            System.out.println(ct.getSchema());
            System.out.println("Tabla en la que se consulta: " + metaData.getTableName(1));
            System.out.println("Numero de columnas devueltas: " + metaData.getColumnCount());
            System.out.println("Nombre Base de datos: " + ct.getCatalog());
            System.out.println("Concourrencia: " + rs.getConcurrency());
            System.out.println("------------------");
            tabla = metaData.getTableName(1);
            return rs;
        } catch (SQLException e) {
            System.out.println("No pudimos realizar la consulta. " + e.getMessage());
            ctrCt.matarHilo();
            return null;
        } finally {
            ctrCt.recetear("Terminando de realizar una consulta en: " + tabla);
            cursorNormal();
        }
    }

    public Connection getConecction(String mensaje) {
        try {
            System.out.println("~$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$~");
            System.out.println("OBTENEMOS CONEXION " + mensaje);
            if (ct.isClosed()) {
                System.out.println("La conexion fue cerrada no podemos retornarla. Debemos abrir una nueva");
                ctrCt = new ConexionesCTR(ct);
                ctrCt.iniciar("Get Connection Clase: ConectarBD");
                ctrCt.agregarSegundos(60);
                ct = DriverManager.getConnection(url, user, pass);
                return ct;
            } else {
                System.out.println("Esta abierta la conexion.");
                ctrCt.recetear("Se recetea al devolver la conexion.");
                ctrCt.agregarSegundos(60);
                return ct;
            }
        } catch (SQLException | NullPointerException ex) {
            System.out.println("No pudimos comprobar el estado de la conexion." + ex.getMessage());
            ctrCt.matarHilo();
            return null;
        }

    }

    public void mostrarReporte(JasperReport jr, Map parametro, String titulo) {
        new Thread(() -> {
            try {
                vtnPrin.getLblEstado().setText("Ejecutando reporte: " + titulo);
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
                ctrCt.matarHilo();
                JOptionPane.showMessageDialog(null, "Error en consulta: " + ex.getMessage());
            } catch (JRException ex) {
                JOptionPane.showMessageDialog(null, "Error en reporte: " + ex);
            } finally {
                ctrCt.recetear("Terminando de imprimir un reporte.");
            }
        }).start();
    }

    public void cerrarConexion() {
        try {
            if (!ct.isClosed()) {
                ct.close();
            }
        } catch (SQLException e) {
            System.out.println("Un error ocurrimio mientras se cerraba conexion. " + e.getMessage());
        }
    }

    private void cursorCarga() {
        if (vtnPrin != null) {
            vtnPrin.setCursor(new Cursor(3));
        }
    }

    private void cursorNormal() {
        if (vtnPrin != null) {
            vtnPrin.setCursor(new Cursor(0));
        }
    }

    public void setVtnPrin(VtnPrincipal vtnPrin) {
        this.vtnPrin = vtnPrin;
    }

    public PreparedStatement getPS(String sql) {
        try {
            return pool.getConnection().prepareStatement(sql);
        } catch (SQLException e) {
            System.out.println("No pudimos preparar el statement: " + e.getMessage());
            return null;
        }
    }

    public SQLException nosql(PreparedStatement ps) {
        try {
            int a = ps.executeUpdate();
            System.out.println("Afecto a: " + a);
            return null;
        } catch (SQLException e) {
            System.out.println("No se pudo ejecutar el nsql. " + e.getMessage());
            return e;
        } finally {
            try {
                ps.getConnection().close();
                ps.close();
            } catch (SQLException e) {
                System.out.println("No se pudo cerrar el prepared statemente: " + e.getMessage());
            }
        }
    }

    public ResultSet sql(PreparedStatement ps) {
        try {
            rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            System.out.println("No se pudo ejecutar el sql: " + e.getMessage());
            return null;
        }
    }

}
