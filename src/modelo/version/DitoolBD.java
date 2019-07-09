package modelo.version;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import modelo.CONS;

/**
 *
 * @author alumno
 */
public class DitoolBD {

    private Connection ct;

    public DitoolBD(String user, String pass) {
        try {
            String url = generarURL();
            //Cargamos el driver
            Class.forName("org.postgresql.Driver");
            ct = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException e) {
            System.out.println("No pudimos conectarnos DB. " + e.getMessage());
        } catch (SQLException ex) {
            System.out.println("No nos pudimos conectar. " + ex.getMessage());
        }
    }

    public PreparedStatement getPs(String nsql) {
        try {
            PreparedStatement ps = ct.prepareStatement(nsql);
            return ps;
        } catch (SQLException e) {
            System.out.println("No se pudo preparar el statement. " + e.getMessage());
            return null;
        }
    }

    public ResultSet sql(PreparedStatement ps) {
        try {
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            System.out.println("No se pudo ejecutar el sql: " + e.getMessage());
            return null;
        }
    }

    public void cerrar(PreparedStatement ps) {
        try {
            ps.getConnection().close();
        } catch (SQLException e) {
            System.out.println("No pudimos cerrar la conexion pls try again. " + e.getMessage());
        }
    }

    private String generarURL() {
        File bd = new File(CONS.BD_DIR);
        Properties p = new Properties();
        if (!bd.exists()) {
            crearPropiedades(p, bd);
        }

        if (!comprobarRequisitos(p, bd)) {
            if (bd.delete()) {
                System.out.println("No tiene todas las propiedades lo borramos.");
            }
            crearPropiedades(p, bd);
        }

        String ip = "";
        String port = "";
        String database = "";

        try {
            p.load(new FileReader(bd));
            ip = p.getProperty(CONS.BD_IP);
            port = p.getProperty(CONS.BD_PUERTO);
            database = p.getProperty(CONS.BD_DATABASE);
        } catch (IOException e) {
            System.out.println("No encontramos el señor archivo. " + e.getMessage());
        }

        return "jdbc:postgresql://" + ip + ":" + port + "/" + database;
    }

    private boolean comprobarRequisitos(Properties p, File bd) {
        boolean todas = true;
        try {
            p.load(new FileReader(bd));
            if (p.getProperty(CONS.BD_DATABASE) == null) {
                todas = false;
            }

            if (p.getProperty(CONS.BD_IP) == null) {
                todas = false;
            }

            if (p.getProperty(CONS.BD_PUERTO) == null) {
                todas = false;
            }

        } catch (IOException e) {
            System.out.println("Divertida: " + e.getMessage());
        }
        return todas;
    }

    private boolean crearPropiedades(Properties p, File bd) {
        boolean creado = false;
        try {
            FileOutputStream fo = new FileOutputStream(bd);
            p.setProperty("ip", "35.193.226.187");
            p.setProperty("database", "BDinsta");
            p.setProperty("port", "5432");
            p.store(fo, "Propiedades de la base de datos: \n"
                    + "Modifiquelos para que se pueda conectar a otra base de datos: ");
            creado = true;
        } catch (FileNotFoundException e) {
            System.out.println("No podemos escribir el archivo: " + e.getMessage());
        } catch (IOException ex) {
            System.out.println("No se pudo guardar el properties: " + ex.getMessage());
        }
        return creado;
    }

    //Consultamos la ultima version de la base de datos:
    public VersionMD consultarUltimaVersion() {
        VersionMD v = null;
        String sql = "SELECT \n"
                + "id_version, \n"
                + "usu_username, \n"
                + "version,\n"
                + "nombre, \n"
                + "url, \n"
                + "notas, \n"
                + "fecha\n"
                + "FROM public.\"Versiones\"\n"
                + "WHERE version_activa = true\n"
                + "ORDER BY id_version DESC limit 1;";
        PreparedStatement ps = getPs(sql);
        if (ps != null) {
            ResultSet rs = sql(ps);
            if (rs != null) {
                try {
                    v = new VersionMD();
                    while (rs.next()) {
                        v.setId(rs.getInt(1));
                        v.setUsername(rs.getString(2));
                        v.setVersion(rs.getString(3));
                        v.setNombre(rs.getString(4));
                        v.setUrl(rs.getString(5));
                        v.setNotas(rs.getString(6));
                        v.setFecha(rs.getTimestamp(7).toLocalDateTime());
                    }
                } catch (SQLException e) {
                    System.out.println("No se pudo consultar la version: " + e.getMessage());
                }
            }
        }
        //Cerramos la conexion que abrimos
        cerrar(ps);
        return v;
    }

}
