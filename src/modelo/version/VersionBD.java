package modelo.version;

import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import utils.CONBD;
import utils.M;

/**
 *
 * @author alumno
 */
public class VersionBD extends CONBD {

    public boolean guardar(VersionMD v) {
        String nsql = "INSERT INTO public.\"Versiones\"(usu_username,\n"
                + "  version, nombre, url, notas)\n"
                + "  VALUES(?, ?, ?, ?, ?);";
        PreparedStatement ps = CON.getPSPOOL(nsql);

        try {
            ps.setString(1, v.getUsername());
            ps.setString(2, v.getVersion());
            ps.setString(3, v.getNombre());
            ps.setString(4, v.getUrl());
            ps.setString(5, v.getNotas());

            if (CON.noSQLPOOL(ps)) {
                JOptionPane.showMessageDialog(null, "Se guardo correctamente la version.");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo guardar la version.");
                return false;
            }
        } catch (SQLException e) {
            M.errorMsg("No pudimos preparar el statement: " + e.getMessage());
            return false;
        }
    }

    public boolean editar(VersionMD v) {
        String nsql = "UPDATE public.\"Versiones\" SET \n"
                + "  usu_username = ?, \n"
                + "  version = ?, \n"
                + "  nombre = ?, \n"
                + "  url = ?,\n"
                + "  notas = ?\n"
                + "  WHERE id_version = ?;";
        PreparedStatement ps = CON.getPSPOOL(nsql);

        try {
            ps.setString(1, v.getUsername());
            ps.setString(2, v.getVersion());
            ps.setString(3, v.getNombre());
            ps.setString(4, v.getUrl());
            ps.setString(5, v.getNotas());
            ps.setInt(6, v.getId());

            if (CON.noSQLPOOL(ps)) {
                JOptionPane.showMessageDialog(null, "Se edito correctamente la version.");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo editar la version.");
                return false;
            }
        } catch (SQLException e) {
            M.errorMsg("No pudimos preparar el statement: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idVersion) {
        String nsql = "UPDATE public.\"Versiones\"\n"
                + "  SET version_activa = false\n"
                + "  WHERE id_version = ?;";
        PreparedStatement ps = CON.getPSPOOL(nsql);
        try {
            ps.setInt(1, idVersion);
            if (CON.noSQLPOOL(ps)) {
                JOptionPane.showMessageDialog(null, "Se elimino correctamente la version.");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar la version.");
                return false;
            }
        } catch (HeadlessException | SQLException e) {
            M.errorMsg("No pudimos eliminar: " + e.getMessage());
            return false;
        }
    }

    public boolean activar(int idVersion) {
        String nsql = "UPDATE public.\"Versiones\"\n"
                + "  SET version_activa = true\n"
                + "  WHERE id_version = ?;";
        PreparedStatement ps = CON.getPSPOOL(nsql);
        try {
            ps.setInt(1, idVersion);
            if (CON.noSQLPOOL(ps)) {
                JOptionPane.showMessageDialog(null, "Se activo correctamente la version.");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo activar la version.");
                return false;
            }
        } catch (HeadlessException | SQLException e) {
            M.errorMsg("No pudimos eliminar: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<VersionMD> cargarVersionesActivas() {
        String sql = "SELECT id_version, usu_username,\n"
                + "version, nombre\n"
                + "FROM public.\"Versiones\"\n"
                + "WHERE version_activa = true\n"
                + "ORDER BY fecha DESC;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        return consultarParaTbl(ps);
    }

    public ArrayList<VersionMD> cargarVersionesEliminadas() {
        String sql = "SELECT id_version, usu_username,\n"
                + "version, nombre\n"
                + "FROM public.\"Versiones\"\n"
                + "WHERE version_activa = false\n"
                + "ORDER BY fecha DESC;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        return consultarParaTbl(ps);
    }

    private ArrayList<VersionMD> consultarParaTbl(PreparedStatement ps) {
        ArrayList<VersionMD> versiones = null;
        try {
            ResultSet rs = ps.executeQuery();
            versiones = new ArrayList<>();
            while (rs.next()) {
                VersionMD v = new VersionMD();
                v.setId(rs.getInt("id_version"));
                v.setNombre(rs.getString("nombre"));
                v.setVersion(rs.getString("version"));
                v.setUsername(rs.getString("usu_username"));

                versiones.add(v);
            }
        } catch (SQLException e) {
            M.errorMsg("No pudimos consultar versiones: " + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return versiones;
    }

    public VersionMD existeVersion(String nombre, String version) {
        String sql = "SELECT id_version, usu_username \n"
                + "FROM public.\"Versiones\"\n"
                + "WHERE nombre ILIKE '%" + nombre + "%' \n"
                + "AND version = ? \n"
                + "AND version_activa = true;";
        VersionMD v = null;
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setString(1, version);
            ResultSet rs = ps.executeQuery();
            v = new VersionMD();
            while (rs.next()) {
                v.setId(rs.getInt(1));
                v.setUsername(rs.getString(2));
            }
        } catch (SQLException e) {
            M.errorMsg("No pudimos consultar la version: " + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return v;
    }

    public VersionMD consultarVersion(int idVersion) {
        String sql = "SELECT id_version, usu_username,\n"
                + "version, nombre, url, notas\n"
                + "FROM public.\"Versiones\"\n"
                + "WHERE id_version = ?;";
        VersionMD v = null;
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setInt(1, idVersion);
            ResultSet rs = ps.executeQuery();
            v = new VersionMD();
            while (rs.next()) {
                v.setId(rs.getInt("id_version"));
                v.setUsername(rs.getString("usu_username"));
                v.setVersion(rs.getString("version"));
                v.setNombre(rs.getString("nombre"));
                v.setUrl(rs.getString("url"));
                v.setNotas(rs.getString("notas"));
            }
        } catch (SQLException e) {
            M.errorMsg("No pudimos preparar la consulta: " + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return v;
    }

}
