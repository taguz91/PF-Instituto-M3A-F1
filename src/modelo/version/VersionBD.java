package modelo.version;

import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.ConectarDB;

/**
 *
 * @author alumno
 */
public class VersionBD extends VersionMD {

    private final ConectarDB conecta;

    public VersionBD(ConectarDB conecta) {
        this.conecta = conecta;
    }

    public boolean guardar() {
        String nsql = "INSERT INTO public.\"Versiones\"(usu_username,\n"
                + "  version, nombre, url, notas)\n"
                + "  VALUES(?, ?, ?, ?, ?);";
        PreparedStatement ps = conecta.getPS(nsql);

        try {
            ps.setString(1, getUsername());
            ps.setString(2, getVersion());
            ps.setString(3, getNombre());
            ps.setString(4, getUrl());
            ps.setString(5, getNotas());

            if (conecta.nosql(ps) == null) {
                JOptionPane.showMessageDialog(null, "Se guardo correctamente la version.");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo guardar la version.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("No pudimos preparar el statement: " + e.getMessage());
            return false;
        }
    }

    public boolean editar(int idVersion) {
        String nsql = "UPDATE public.\"Versiones\" SET \n"
                + "  usu_username = ?, \n"
                + "  version = ?, \n"
                + "  nombre = ?, \n"
                + "  url = ?,\n"
                + "  notas = ?\n"
                + "  WHERE id_version = ?;";
        PreparedStatement ps = conecta.getPS(nsql);

        try {
            ps.setString(1, getUsername());
            ps.setString(2, getVersion());
            ps.setString(3, getNombre());
            ps.setString(4, getUrl());
            ps.setString(5, getNotas());
            ps.setInt(6, idVersion);

            if (conecta.nosql(ps) == null) {
                JOptionPane.showMessageDialog(null, "Se edito correctamente la version.");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo editar la version.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("No pudimos preparar el statement: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idVersion) {
        String nsql = "UPDATE public.\"Versiones\"\n"
                + "  SET version_activa = false\n"
                + "  WHERE id_version = ?;";
        PreparedStatement ps = conecta.getPS(nsql);
        try {
            ps.setInt(1, idVersion);
            if (conecta.nosql(ps) == null) {
                JOptionPane.showMessageDialog(null, "Se elimino correctamente la version.");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar la version.");
                return false;
            }
        } catch (HeadlessException | SQLException e) {
            System.out.println("No pudimos eliminar: " + e.getMessage());
            return false;
        }
    }

    public boolean activar(int idVersion) {
        String nsql = "UPDATE public.\"Versiones\"\n"
                + "  SET version_activa = true\n"
                + "  WHERE id_version = ?;";
        PreparedStatement ps = conecta.getPS(nsql);
        try {
            ps.setInt(1, idVersion);
            if (conecta.nosql(ps) == null) {
                JOptionPane.showMessageDialog(null, "Se activo correctamente la version.");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo activar la version.");
                return false;
            }
        } catch (HeadlessException | SQLException e) {
            System.out.println("No pudimos eliminar: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<VersionMD> cargarVersionesActivas() {
        String sql = "SELECT id_version, usu_username,\n"
                + "version, nombre\n"
                + "FROM public.\"Versiones\"\n"
                + "WHERE version_activa = true\n"
                + "ORDER BY fecha DESC;";
        PreparedStatement ps = conecta.getPS(sql);
        return consultarParaTbl(ps);
    }

    public ArrayList<VersionMD> cargarVersionesEliminadas() {
        String sql = "SELECT id_version, usu_username,\n"
                + "version, nombre\n"
                + "FROM public.\"Versiones\"\n"
                + "WHERE version_activa = false\n"
                + "ORDER BY fecha DESC;";
        PreparedStatement ps = conecta.getPS(sql);
        return consultarParaTbl(ps);
    }

    private ArrayList<VersionMD> consultarParaTbl(PreparedStatement ps) {
        ArrayList<VersionMD> versiones = null;
        ResultSet rs = conecta.sql(ps);
        if (rs != null) {
            try {
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
                System.out.println("No pudimos consultar versiones: " + e.getMessage());
            }
        }
        conecta.cerrar(ps);
        return versiones;
    }

    public VersionMD existeVersion(String nombre, String version) {
        String sql = "SELECT id_version, usu_username \n"
                + "FROM public.\"Versiones\"\n"
                + "WHERE nombre ILIKE '%" + nombre + "%' \n"
                + "AND version = ? \n"
                + "AND version_activa = true;";
        VersionMD v = null;
        PreparedStatement ps = conecta.getPS(sql);
        try {
            ps.setString(1, version);
        } catch (SQLException e) {
            System.out.println("No pudimos preparar la consulta: " + e.getMessage());
        }

        ResultSet rs = conecta.sql(ps);
        if (rs != null) {
            try {
                v = new VersionMD();
                while (rs.next()) {
                    v.setId(rs.getInt(1));
                    v.setUsername(rs.getString(2));
                }
            } catch (SQLException e) {
                System.out.println("No pudimos consultar la version: " + e.getMessage());
            }
        }
        conecta.cerrar(ps);
        return v;
    }

    public VersionMD consultarVersion(int idVersion) {
        String sql = "SELECT id_version, usu_username,\n"
                + "version, nombre, url, notas\n"
                + "FROM public.\"Versiones\"\n"
                + "WHERE id_version = ?;";
        VersionMD v = null;
        PreparedStatement ps = conecta.getPS(sql);
        try {
            ps.setInt(1, idVersion);
        } catch (SQLException e) {
            System.out.println("No pudimos preparar la consulta: " + e.getMessage());
        }

        ResultSet rs = conecta.sql(ps);
        if (rs != null) {
            try {
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
                System.out.println("No pudimos consultar la version: " + e.getMessage());
            }
        }
        conecta.cerrar(ps);
        return v;
    }

}
