package modelo.usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.ConectarDB;

/**
 *
 * @author arman
 */
public class HistorialUsuarioBD extends HistorialUsuarioMD {

    private final ConectarDB conecta;

    public HistorialUsuarioBD(ConectarDB conecta) {
        this.conecta = conecta;
    }

    public ArrayList<HistorialUsuarioMD> cargarHistorialHoy() {
        String sql = "SELECT id_historial_user, usu_username, historial_fecha, "
                + "historial_tipo_accion, historial_nombre_tabla, historial_pk_tabla\n"
                + "FROM public.\"HistorialUsuarios\"\n"
                + "WHERE date_part('year', historial_fecha) = date_part('year', now())\n"
                + "AND date_part('month', historial_fecha) = date_part('month', now())\n"
                + "AND date_part('day', historial_fecha) = date_part('day', now());													;";
        return consultarTbl(sql);
    }

    public ArrayList<HistorialUsuarioMD> cargarHistorialUser(String username) {
        String sql = "SELECT id_historial_user, usu_username, historial_fecha, "
                + "historial_tipo_accion, historial_nombre_tabla, historial_pk_tabla\n"
                + "FROM public.\"HistorialUsuarios\"\n"
                + "WHERE usu_username = '" + username + "';";
        return consultarTbl(sql);
    }

    public ArrayList<HistorialUsuarioMD> cargarHistorialTbl(String tabla) {
        String sql = "SELECT id_historial_user, usu_username, historial_fecha, "
                + "historial_tipo_accion, historial_nombre_tabla, historial_pk_tabla\n"
                + "FROM public.\"HistorialUsuarios\"\n"
                + "WHERE historial_nombre_tabla = '" + tabla + "';";
        return consultarTbl(sql);
    }

    public ArrayList<HistorialUsuarioMD> cargarHistorialAccion(String accion) {
        String sql = "SELECT id_historial_user, usu_username, historial_fecha, "
                + "historial_tipo_accion, historial_nombre_tabla, historial_pk_tabla\n"
                + "FROM public.\"HistorialUsuarios\"\n"
                + "WHERE historial_tipo_accion = '" + accion + "';";
        return consultarTbl(sql);
    }

    public ArrayList<HistorialUsuarioMD> cargarHistorialFecha(String fecha) {
        String sql = "SELECT id_historial_user, usu_username, historial_fecha, "
                + "historial_tipo_accion, historial_nombre_tabla, historial_pk_tabla\n"
                + "FROM public.\"HistorialUsuarios\"\n"
                + "WHERE historial_fecha::date = TO_DATE('" + fecha + "', 'DD/MM/YYYY');";
        return consultarTbl(sql);
    }

    public ArrayList<HistorialUsuarioMD> cargarHistorialEntresFechas(String fechaIni, String fechaFin) {
        String sql = "SELECT id_historial_user, usu_username, historial_fecha, "
                + "historial_tipo_accion, historial_nombre_tabla, historial_pk_tabla\n"
                + "FROM public.\"HistorialUsuarios\"\n"
                + "WHERE historial_fecha::date >= TO_DATE('" + fechaIni + "', 'DD/MM/YYYY') AND\n"
                + "historial_fecha::date <= TO_DATE('" + fechaFin + "', 'DD/MM/YYYY');";
        return consultarTbl(sql);
    }

    private ArrayList<HistorialUsuarioMD> consultarTbl(String sql) {
        ArrayList<HistorialUsuarioMD> historial = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        if (rs != null) {
            try {
                while (rs.next()) {
                    HistorialUsuarioMD h = new HistorialUsuarioMD();
                    h.setId(rs.getInt("id_historial_user"));
                    h.setNombreTbl(rs.getString("historial_nombre_tabla"));
                    h.setFechaAccion(rs.getTimestamp("historial_fecha").toLocalDateTime());
                    h.setPkTbl(rs.getInt("historial_pk_tabla"));
                    h.setTipoAccion(rs.getString("historial_tipo_accion"));
                    h.setUsername(rs.getString("usu_username"));

                    historial.add(h);
                }
                rs.close();
                return historial;
            } catch (SQLException e) {
                System.out.println("No se pudo consultar historial. " + e.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }

    public ArrayList<String> cargarAcciones() {
        String sql = "SELECT DISTINCT historial_tipo_accion\n"
                + "FROM public.\"HistorialUsuarios\";";
        return cargarClm(sql);
    }

    public ArrayList<String> cargarTablas() {
        String sql = "SELECT DISTINCT historial_nombre_tabla\n"
                + "FROM public.\"HistorialUsuarios\";";
        return cargarClm(sql);
    }

    public ArrayList<String> cargarUsuarios() {
        String sql = "SELECT DISTINCT usu_username\n"
                + "FROM public.\"HistorialUsuarios\";";
        return cargarClm(sql);
    }

    public ArrayList<String> cargarFechas() {
        String sql = "SELECT DISTINCT \n"
                + "date_part('day', historial_fecha) || '/' ||\n"
                + "date_part('month', historial_fecha) || '/' ||\n"
                + "date_part('year', historial_fecha)\n"
                + "FROM public.\"HistorialUsuarios\"\n"
                + "ORDER BY 1 DESC;";
        return cargarClm(sql);
    }

    private ArrayList<String> cargarClm(String sql) {
        ArrayList<String> acciones = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        if (rs != null) {
            try {
                while (rs.next()) {
                    //Nos devuelve la primera columna 
                    String a = rs.getString(1);
                    acciones.add(a);
                }
                rs.close();
                return acciones;
            } catch (SQLException e) {
                System.out.println("No se pudo consultar historial. " + e.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }

    public String buscarObservacion(int idHistorial) {
        String sql = "SELECT hitorial_observacion\n"
                + "FROM public.\"HistorialUsuarios\" \n"
                + "WHERE id_historial_user = " + idHistorial + ";";
        String a = "";
        ResultSet rs = conecta.sql(sql);
        if (rs != null) {
            try {
                while (rs.next()) {
                    a = rs.getString("hitorial_observacion");
                }
                rs.close();
                return a;
            } catch (SQLException e) {
                System.out.println("No se pudo consultar historial. " + e.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }
}
