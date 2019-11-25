package modelo.pagos;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import utils.CONBD;

/**
 *
 * @author gus
 */
public class PagoMateriaBD extends CONBD {

    private static final String INSERT = "INSERT INTO pago.\"PagoMateria\"(\n"
            + "id_comprobante,\n"
            + "id_malla_alumno,\n"
            + "pago_materia,\n"
            + "pago_numero_matricula)\n"
            + "VALUES (?, ?, ?, ?);";

    private static final String UPDATE = "UPDATE pago.\"PagoMateria\"\n"
            + "SET pago_materia=?,\n"
            + "pago_numero_matricula=?\n"
            + "WHERE id_pago_materia=?; ";

    public int guardar(PagoMateriaMD pm) {
        PreparedStatement ps = CON.getPSID(INSERT);
        try {
            ps.setInt(1, pm.getComprobante().getId());
            ps.setInt(2, pm.getMallaAlumno().getId());
            ps.setDouble(3, pm.getPago());
            ps.setInt(4, pm.getNumMatricula());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al guardar el pago. "
                    + e.getMessage()
            );
        }
        return CON.getIDGenerado(ps);
    }

    public int editar(PagoMateriaMD pm) {
        PreparedStatement ps = CON.getPSID(UPDATE);
        try {
            ps.setDouble(1, pm.getPago());
            ps.setInt(2, pm.getNumMatricula());
            ps.setInt(3, pm.getId());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al editar el pago. "
                    + e.getMessage()
            );
        }
        return CON.getIDGenerado(ps);
    }

}
