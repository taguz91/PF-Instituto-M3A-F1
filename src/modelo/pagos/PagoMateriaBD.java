package modelo.pagos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.alumno.MallaAlumnoMD;
import modelo.materia.MateriaMD;
import utils.CONBD;

/**
 *
 * @author gus
 */
public class PagoMateriaBD extends CONBD {
    
    private static PagoMateriaBD PMBD;
    
    public static PagoMateriaBD single(){
        if (PMBD == null) {
            PMBD = new PagoMateriaBD();
        }
        return PMBD;
    }

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

    public List<PagoMateriaMD> getByComprobante(int idComprobante) {
        String sql = "SELECT\n"
                + "id_pago_materia,\n"
                + "pm.id_malla_alumno,\n"
                + "pago_materia,\n"
                + "pago_numero_matricula,\n"
                + "materia_nombre\n"
                + "FROM pago.\"PagoMateria\" pm\n"
                + "JOIN public.\"MallaAlumno\" ma\n"
                + "ON ma.id_malla_alumno = pm.id_malla_alumno\n"
                + "JOIN public.\"Materias\" m\n"
                + "ON m.id_materia = ma.id_materia\n"
                + "WHERE id_comprobante = ?;";
        List<PagoMateriaMD> pms = new ArrayList<>();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setInt(1, idComprobante);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                PagoMateriaMD pm = new PagoMateriaMD();
                pm.setId(res.getInt(1));
                MallaAlumnoMD ma = new MallaAlumnoMD();
                ma.setId(res.getInt(2));
                pm.setPago(res.getDouble(3));
                pm.setNumMatricula(res.getInt(4));
                MateriaMD m = new MateriaMD();
                m.setNombre(res.getString(5));
                ma.setMateria(m);
                pm.setMallaAlumno(ma);
                
                pms.add(pm);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error consultar pagos de materias "
                    + "por comprobante. \n"
                    + e.getMessage()
            );
        } finally {
            CON.cerrarCONPS(ps);
        }
        return pms;
    }

}
