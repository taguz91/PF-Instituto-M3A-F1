package Postgres;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.ConectarDB;

/**
 *
 * @author Johnny
 */
public class Informacion {

    private final ConectarDB conecta;

    public Informacion(ConectarDB conecta) {
        this.conecta = conecta;
    }

    public ArrayList<String> obtenetIpsConectadosBD(String nombreBD) {
        ArrayList<String> ips = new ArrayList<>();
        String sql = "SELECT client_addr FROM pg_stat_activity\n"
                + "WHERE datname = '" + nombreBD + "'; ";
        System.out.println(sql);
        ResultSet rs = conecta.sql(sql);
        if (rs != null) {
            try {
                while (rs.next()) {
                    System.out.println(rs.getString("client_addr"));
                    String ip = rs.getString(1);
                    ips.add(ip);
                }
                return ips;
            } catch (SQLException e) {
                System.out.println("No se pudo consultar ips " + e.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }

    public int obtenerNumeroConectadosBD(String nombreBD) {
        int num = 0;
        String sql = "SELECT client_addr FROM pg_stat_activity\n"
                + "WHERE datname = '" + nombreBD + "'; ";
        ResultSet rs = conecta.sql(sql);
        if (rs != null) {
            try {
                while (rs.next()) {
                    num = rs.getInt(1);
                }
                return num;
            } catch (SQLException e) {
                System.out.println("No se pudo consultar ips " + e.getMessage());
                return 0;
            }
        } else {
            return 0;
        }
    }
}
