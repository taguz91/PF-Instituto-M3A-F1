package modelo.usuario;

import java.sql.ResultSet;
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
                return historial;
            } catch (Exception e) {
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
        return cargarClm(sql, "historial_tipo_accion");
    }

    public ArrayList<String> cargarTablas() {
        String sql = "SELECT DISTINCT historial_nombre_tabla\n"
                + "FROM public.\"HistorialUsuarios\";";
        return cargarClm(sql, "historial_nombre_tabla");
    }

    private ArrayList<String> cargarClm(String sql, String clm) {
        ArrayList<String> acciones = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        if (rs != null) {
            try {
                while (rs.next()) {
                    String a = rs.getString(clm);
                }
                return acciones;
            } catch (Exception e) {
                System.out.println("No se pudo consultar historial. " + e.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }
}
