package modelo.version;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.CONS;

/**
 *
 * @author alumno
 */
public class DitoolBD {

    private Connection ct;

    public DitoolBD(String user, String pass) {
        try {
            //Cargamos el driver
            Class.forName("org.postgresql.Driver");
            ct = DriverManager.getConnection(CONS.BD_URL, CONS.getBDUser(), CONS.BD_PASS);
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
