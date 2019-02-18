package modelo.lugar;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.ConectarDB;

/**
 *
 * @author Johnny
 */
public class LugarBD extends LugarMD {

    ConectarDB conecta = new ConectarDB();

    public LugarBD() {
    }

    public LugarMD buscar(int idLugar) {
        LugarBD lg = new LugarBD();
        String sql = "SELECT id_lugar, lugar_codigo, lugar_nombre, lugar_nivel, "
                + "id_lugar_referencia\n FROM public.\"Lugares\" "
                + "WHERE id_lugar ='" + idLugar + "'; ";
        ResultSet rs = conecta.sql(sql);

        try {
            if (rs != null) {
                while (rs.next()) {
                    lg.setCodigo(rs.getString("lugar_codigo"));
                    lg.setId(rs.getInt("id_lugar"));
                    lg.setIdReferencia(rs.getInt("id_lugar_referencia"));
                    lg.setNivel(rs.getString("lugar_nivel"));
                    lg.setNombre(rs.getString("lugar_nombre"));
                }
                return lg;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No se pudo consultar un lugar");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<LugarMD> buscarPaises() {
        ArrayList<LugarMD> lugares = new ArrayList();

        String sql = "SELECT id_lugar, lugar_codigo, lugar_nombre, lugar_nivel, "
                + "id_lugar_referencia\n FROM public.\"Lugares\" "
                + "WHERE id_lugar_referencia IS NULL;";

        ResultSet rs = conecta.sql(sql);

        try {
            if (rs != null) {
                while (rs.next()) {
                    LugarBD lg = new LugarBD();
                    lg.setCodigo(rs.getString("lugar_codigo"));
                    lg.setId(rs.getInt("id_lugar"));
                    lg.setIdReferencia(rs.getInt("id_lugar_referencia"));
                    lg.setNivel(rs.getString("lugar_nivel"));
                    lg.setNombre(rs.getString("lugar_nombre"));

                    lugares.add(lg);
                }
                return lugares;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No se pudo consultar lugares");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<LugarMD> buscarPorReferencia(int idReferencia) {
        ArrayList<LugarMD> lugares = new ArrayList();

        String sql = "SELECT id_lugar, lugar_codigo, lugar_nombre, lugar_nivel, "
                + "id_lugar_referencia\n FROM public.\"Lugares\" "
                + "WHERE id_lugar_referencia = '" + idReferencia + "';";

        ResultSet rs = conecta.sql(sql);

        try {
            if (rs != null) {
                while (rs.next()) {
                    LugarBD lg = new LugarBD();
                    lg.setCodigo(rs.getString("lugar_codigo"));
                    lg.setId(rs.getInt("id_lugar"));
                    lg.setIdReferencia(rs.getInt("id_lugar_referencia"));
                    lg.setNivel(rs.getString("lugar_nivel"));
                    lg.setNombre(rs.getString("lugar_nombre"));

                    lugares.add(lg);
                }
                return lugares;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No se pudo consultar lugares");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<LugarMD> buscarPorNivel(int nivel) {
        ArrayList<LugarMD> lugares = new ArrayList();

        String sql = "SELECT id_lugar, lugar_codigo, lugar_nombre, lugar_nivel, "
                + "id_lugar_referencia\n FROM public.\"Lugares\" "
                + "WHERE lugar_nivel = '" + nivel + "';";

        ResultSet rs = conecta.sql(sql);

        try {
            if (rs != null) {
                while (rs.next()) {
                    LugarBD lg = new LugarBD();
                    lg.setCodigo(rs.getString("lugar_codigo"));
                    lg.setId(rs.getInt("id_lugar"));
                    lg.setIdReferencia(rs.getInt("id_lugar_referencia"));
                    lg.setNivel(rs.getString("lugar_nivel"));
                    lg.setNombre(rs.getString("lugar_nombre"));

                    lugares.add(lg);
                }
                return lugares;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No se pudo consultar lugares");
            System.out.println(e.getMessage());
            return null;
        }
    }
}
