package modelo.lugar;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.ConectarDB;

/**
 *
 * @author Johnny
 */
public class LugarBD extends LugarMD {

    private final ConectarDB conecta;

    public LugarBD(ConectarDB conecta) {
        this.conecta = conecta;
    }

    public LugarMD buscar(int idLugar) {
        LugarMD lg = new LugarMD();
        String sql = "SELECT id_lugar, lugar_codigo, lugar_nombre, lugar_nivel, "
                + "id_lugar_referencia\n FROM public.\"Lugares\" "
                + "WHERE id_lugar ='" + idLugar + "'; ";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);

        try {
            if (rs != null) {
                while (rs.next()) {
                    lg.setCodigo(rs.getString("lugar_codigo"));
                    lg.setId(rs.getInt("id_lugar"));
                    lg.setIdReferencia(rs.getInt("id_lugar_referencia"));
                    lg.setNivel(rs.getString("lugar_nivel"));
                    lg.setNombre(rs.getString("lugar_nombre"));
                }
                ps.getConnection().close();
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

    public void insertarLugar() {
        String nsql = "INSERT INTO public.\"Lugares\"(id_lugar, lugar_codigo, "
                + "lugar_nombre, lugar_nivel, id_lugar_referencia) \n"
                + "values(" + getId() + ",'" + getCodigo() + "' ,'" + getNombre() 
                + "'," + getNivel() + "," + getIdReferencia() + ");";

        PreparedStatement ps = conecta.getPS(nsql);
        if (conecta.nosql(ps) == null) {
            System.out.println("Se guardo correctamente");
        }

    }

    public boolean editarLugar(int aguja) {
        String sql = "UPDATE public.\"Lugares\" SET lugar_codigo = " + getCodigo() 
                + " WHERE id_lugar = " + aguja + ";";
        PreparedStatement ps = conecta.getPS(sql);
        if (conecta.nosql(ps) == null) {
            System.out.println("Se edito correctamente la identificacion");
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public ArrayList<LugarMD> buscarPaises() {
        ArrayList<LugarMD> lugares = new ArrayList();

        String sql = "SELECT id_lugar, lugar_codigo, lugar_nombre, lugar_nivel, "
                + "id_lugar_referencia\n FROM public.\"Lugares\" "
                + "WHERE id_lugar_referencia IS NULL ORDER BY lugar_nombre;";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);

        try {
            if (rs != null) {
                while (rs.next()) {
                    LugarMD lg = new LugarMD();
                    lg.setCodigo(rs.getString("lugar_codigo"));
                    lg.setId(rs.getInt("id_lugar"));
                    lg.setIdReferencia(rs.getInt("id_lugar_referencia"));
                    lg.setNivel(rs.getString("lugar_nivel"));
                    lg.setNombre(rs.getString("lugar_nombre"));

                    lugares.add(lg);
                }
                ps.getConnection().close();
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
                + "WHERE id_lugar_referencia = '" + idReferencia + "' "
                + "ORDER BY lugar_nombre;";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            if (rs != null) {
                while (rs.next()) {
                    LugarMD lg = new LugarMD();
                    lg.setCodigo(rs.getString("lugar_codigo"));
                    lg.setId(rs.getInt("id_lugar"));
                    lg.setIdReferencia(rs.getInt("id_lugar_referencia"));
                    lg.setNivel(rs.getString("lugar_nivel"));
                    lg.setNombre(rs.getString("lugar_nombre"));

                    lugares.add(lg);
                }
                ps.getConnection().close();
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
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);

        try {
            if (rs != null) {
                while (rs.next()) {
                    LugarMD lg = new LugarMD();
                    lg.setCodigo(rs.getString("lugar_codigo"));
                    lg.setId(rs.getInt("id_lugar"));
                    lg.setIdReferencia(rs.getInt("id_lugar_referencia"));
                    lg.setNivel(rs.getString("lugar_nivel"));
                    lg.setNombre(rs.getString("lugar_nombre"));

                    lugares.add(lg);
                }
                ps.getConnection().close();
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
