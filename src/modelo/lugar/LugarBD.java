package modelo.lugar;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import utils.CONBD;
import utils.M;

/**
 *
 * @author Johnny
 */
public class LugarBD extends CONBD {

    private static LugarBD LBD;

    public static LugarBD single() {
        if (LBD == null) {
            LBD = new LugarBD();
        }
        return LBD;
    }

    public LugarMD buscar(int idLugar) {
        LugarMD lg = new LugarMD();
        String sql = "SELECT id_lugar, lugar_codigo, lugar_nombre, lugar_nivel, "
                + "id_lugar_referencia\n FROM public.\"Lugares\" "
                + "WHERE id_lugar ='" + idLugar + "'; ";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lg.setCodigo(rs.getString("lugar_codigo"));
                lg.setId(rs.getInt("id_lugar"));
                lg.setIdReferencia(rs.getInt("id_lugar_referencia"));
                lg.setNivel(rs.getString("lugar_nivel"));
                lg.setNombre(rs.getString("lugar_nombre"));
            }
            return lg;
        } catch (SQLException e) {
            M.errorMsg("No consultamos lugar. " + e.getMessage());
            return null;
        } finally {
            CON.cerrarCONPS(ps);
        }
    }

    public void insertarLugar(LugarMD lg) {
        String nsql = "INSERT INTO public.\"Lugares\"("
                + "id_lugar, "
                + "lugar_codigo, "
                + "lugar_nombre, "
                + "lugar_nivel, "
                + "id_lugar_referencia) \n"
                + "values(" + lg.getId() + ",'"
                + lg.getCodigo() + "' ,'"
                + lg.getNombre() + "',"
                + lg.getNivel() + ","
                + lg.getIdReferencia() + ");";
        CON.executeNoSQL(nsql);
    }

    public boolean editarLugar(LugarMD lg, int aguja) {
        String sql = "UPDATE public.\"Lugares\" "
                + "SET lugar_codigo = " + lg.getCodigo()
                + " WHERE id_lugar = " + aguja + ";";
        return CON.executeNoSQL(sql);
    }

    public ArrayList<LugarMD> buscarPaises() {
        ArrayList<LugarMD> lugares = new ArrayList();

        String sql = "SELECT id_lugar, lugar_codigo, lugar_nombre, lugar_nivel, "
                + "id_lugar_referencia\n FROM public.\"Lugares\" "
                + "WHERE id_lugar_referencia IS NULL ORDER BY lugar_nombre;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LugarMD lg = new LugarMD();
                lg.setCodigo(rs.getString("lugar_codigo"));
                lg.setId(rs.getInt("id_lugar"));
                lg.setIdReferencia(rs.getInt("id_lugar_referencia"));
                lg.setNivel(rs.getString("lugar_nivel"));
                lg.setNombre(rs.getString("lugar_nombre"));

                lugares.add(lg);
            }
        } catch (SQLException e) {
            M.errorMsg("No consultamos paises. " + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return lugares;
    }

    public ArrayList<LugarMD> buscarPorReferencia(int idReferencia) {
        ArrayList<LugarMD> lugares = new ArrayList();
        String sql = "SELECT id_lugar, lugar_codigo, lugar_nombre, lugar_nivel, "
                + "id_lugar_referencia\n FROM public.\"Lugares\" "
                + "WHERE id_lugar_referencia = '" + idReferencia + "' "
                + "ORDER BY lugar_nombre;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LugarMD lg = new LugarMD();
                lg.setCodigo(rs.getString("lugar_codigo"));
                lg.setId(rs.getInt("id_lugar"));
                lg.setIdReferencia(rs.getInt("id_lugar_referencia"));
                lg.setNivel(rs.getString("lugar_nivel"));
                lg.setNombre(rs.getString("lugar_nombre"));

                lugares.add(lg);
            }
        } catch (SQLException e) {
            M.errorMsg("No consultamos por referencia. " + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return lugares;
    }

    public ArrayList<LugarMD> buscarPorNivel(int nivel) {
        ArrayList<LugarMD> lugares = new ArrayList();

        String sql = "SELECT id_lugar, lugar_codigo, lugar_nombre, lugar_nivel, "
                + "id_lugar_referencia\n FROM public.\"Lugares\" "
                + "WHERE lugar_nivel = '" + nivel + "';";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LugarMD lg = new LugarMD();
                lg.setCodigo(rs.getString("lugar_codigo"));
                lg.setId(rs.getInt("id_lugar"));
                lg.setIdReferencia(rs.getInt("id_lugar_referencia"));
                lg.setNivel(rs.getString("lugar_nivel"));
                lg.setNombre(rs.getString("lugar_nombre"));

                lugares.add(lg);
            }
        } catch (SQLException e) {
            M.errorMsg("No consultamos por referencia. " + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return lugares;
    }

}
