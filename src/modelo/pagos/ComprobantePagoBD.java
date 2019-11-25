package modelo.pagos;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.swing.JOptionPane;
import utils.CONBD;
import utils.CONS;

/**
 *
 * @author MrRainx
 */
public class ComprobantePagoBD extends CONBD {

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
            ps.setTimestamp(4, Timestamp.valueOf(cp.getFechaPago()));
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
            if (cp.getLongBytes() != 0 && cp.getFile() != null) {
                ps.setBinaryStream(3, cp.getFile(), cp.getLongBytes());
            }
            ps.setDouble(4, cp.getTotal());
            ps.setString(5, cp.getCodigo());
            ps.setTimestamp(6, Timestamp.valueOf(cp.getFechaPago()));
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

}
