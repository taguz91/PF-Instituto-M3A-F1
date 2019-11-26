package modelo.pagos;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import utils.CONBD;
import utils.CONS;

/**
 *
 * @author MrRainx
 */
public class ComprobantePagoBD extends CONBD {

    private static ComprobantePagoBD CPBD;

    public static ComprobantePagoBD single() {
        if (CPBD == null) {
            CPBD = new ComprobantePagoBD();
        }
        return CPBD;
    }

    public static final String INSERT = "INSERT INTO pago.\"ComprobantePago\"(\n"
            + "id_prd_lectivo,\n"
            + "id_alumno,\n"
            + "comprobante,\n"
            + "comprobante_total,\n"
            + "comprobante_codigo,\n"
            + "comprobante_fecha_pago,\n"
            + "comprobante_observaciones,\n"
            + "comprobante_usuario_ingreso\n"
            + ") VALUES (\n"
            + "  ?, ?, ?, ?,\n"
            + "  ?, ?, ?, ?);";

    public static final String UPDATE = "UPDATE pago.\"ComprobantePago\"\n"
            + "SET id_prd_lectivo=?,\n"
            + "comprobante_total=?,\n"
            + "comprobante_codigo=?,\n"
            + "comprobante_fecha_pago=?,\n"
            + "comprobante_observaciones=?,\n"
            + "comprobante_usuario_ingreso=?\n"
            + "WHERE id_comprobante_pago=?;";

    public int editar(ComprobantePagoMD cp) {
        PreparedStatement ps = CON.getPSID(UPDATE);
        try {
            ps.setInt(1, cp.getPeriodo().getID());
            ps.setDouble(2, cp.getTotal());
            ps.setString(3, cp.getCodigo());
            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(5, cp.getObservaciones());
            ps.setString(6, CONS.USUARIO.getUsername());
            ps.setInt(7, cp.getId());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al editar el comprobante de pago.\n"
                    + e.getMessage()
            );
        }
        return CON.getIDGenerado(ps);
    }

    public boolean editarFoto(ComprobantePagoMD cp) {
        String sql = "UPDATE pago.\"ComprobantePago\"\n"
                + "SET comprobante=?\n"
                + "WHERE id_comprobante_pago=?;";
        PreparedStatement ps = CON.getPSID(sql);
        try {
            ps.setBinaryStream(1, cp.getFile(), cp.getLongBytes());
            ps.setInt(2, cp.getId());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Editar la foto de pago.\n"
                    + e.getMessage()
            );
        }
        return CON.getIDGenerado(ps) > 0;
    }

    public int guardar(ComprobantePagoMD cp) {
        PreparedStatement ps = CON.getPSID(INSERT);
        try {
            ps.setInt(1, cp.getPeriodo().getID());
            ps.setInt(2, cp.getAlumno().getId_Alumno());
            ps.setBinaryStream(3, cp.getFile(), cp.getLongBytes());
            ps.setDouble(4, cp.getTotal());
            ps.setString(5, cp.getCodigo());
            ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(7, cp.getObservaciones());
            ps.setString(8, CONS.USUARIO.getUsername());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al guardar el comprobante de pago.\n"
                    + e.getMessage()
            );
        }
        return CON.getIDGenerado(ps);
    }

    public ComprobantePagoMD getByAlumnoPeriodo(int idAlumno, int idPeriodo) {
        String sql = "SELECT\n"
                + "id_comprobante_pago,\n"
                + "comprobante,\n"
                + "comprobante_total,\n"
                + "comprobante_codigo,\n"
                + "comprobante_fecha_pago,\n"
                + "comprobante_observaciones\n"
                + "FROM pago.\"ComprobantePago\"\n"
                + "WHERE id_alumno = ? \n"
                + "AND id_prd_lectivo = ?;";
        InputStream is;
        ComprobantePagoMD cp = new ComprobantePagoMD();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setInt(1, idAlumno);
            ps.setInt(2, idPeriodo);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                cp.setId(res.getInt(1));
                // Cargamos el comprobante  
                is = res.getBinaryStream(2);
                if (is != null) {
                    try {
                        BufferedImage bi = ImageIO.read(is);
                        ImageIcon foto = new ImageIcon(bi);
                        Image img = foto.getImage();
                        cp.setComprobante(img);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Error al pasar la foto: "
                                + ex.getMessage()
                        );
                    }
                }
                cp.setTotal(res.getDouble(3));
                cp.setCodigo(res.getString(4));
                cp.setFechaPago(res.getTimestamp(5).toLocalDateTime());
                cp.setObservaciones(res.getString(6));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al consultar por alumno y periodo."
                    + e.getMessage()
            );
        } finally {
            CON.cerrarCONPS(ps);
        }
        return cp;
    }

}
