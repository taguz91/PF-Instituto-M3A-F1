package modelo.version;

import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
                JOptionPane.showMessageDialog(null, "Se guardar correctamente la version.");
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

    public boolean eliminar(int idVersion) {
        String nsql = "UPDATE public.\"Versiones\"\n"
                + "  SET version_activa = false\n"
                + "  WHERE id_version = ?;";
        PreparedStatement ps = conecta.getPS(nsql);
        try {
            ps.setInt(0, idVersion);
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
            ps.setInt(0, idVersion);
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

}
