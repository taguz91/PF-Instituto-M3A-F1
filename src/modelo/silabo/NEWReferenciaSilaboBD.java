package modelo.silabo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.referenciasSilabo.ReferenciaSilaboMD;
import modelo.silabo.mbd.IReferenciaSilaboBD;
import utils.CONBD;

/**
 *
 * @author gus
 */
public class NEWReferenciaSilaboBD extends CONBD implements IReferenciaSilaboBD {

    private static NEWReferenciaSilaboBD RSBD;

    public static NEWReferenciaSilaboBD single() {
        if (RSBD == null) {
            RSBD = new NEWReferenciaSilaboBD();
        }
        return RSBD;
    }

    @Override
    public int guardar(int idSilabo, int idReferencia) {
        String sql = "INSERT INTO "
                + "public.\"ReferenciaSilabo\"( "
                + "id_referencia, "
                + "id_silabo ) VALUES ( ?, ?)";
        PreparedStatement ps = CON.getPSID(sql);
        int idGenerado = 0;
        try {
            ps.setInt(1, idReferencia);
            ps.setInt(2, idSilabo);
            idGenerado = CON.getIDGenerado(ps);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al guardar referencia silabo. \n"
                    + e.getMessage(),
                    "Error estragia unidad",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return idGenerado;
    }

    @Override
    public List<ReferenciaSilaboMD> getBySilabo(int idSilabo) {
        String sql = "SELECT rs.id_referencia, "
                + "r.tipo_referencia, "
                + "r.descripcion_referencia, "
                + "r.existe_en_biblioteca "
                + "FROM public.\"ReferenciaSilabo\" rs, "
                + "public.\"Referencias\" r "
                + "WHERE id_silabo=? "
                + "AND r.id_referencia = rs.id_referencia";
        List<ReferenciaSilaboMD> RS = new ArrayList<>();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setInt(1, idSilabo);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                ReferenciaSilaboMD r = new ReferenciaSilaboMD();
                r.getIdReferencia().setIdReferencia(res.getInt(1));
                r.getIdReferencia().setTipoReferencia(res.getString(2));
                r.getIdReferencia().setDescripcionReferencia(res.getString(3));
                r.getIdReferencia().setExisteEnBiblioteca(res.getBoolean(4));

                RS.add(r);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "No consultamos referencias por id silabo. \n"
                    + e.getMessage(),
                    "Error al consultar",
                    JOptionPane.ERROR_MESSAGE
            );
        } finally {
            CON.cerrarCONPS(ps);
        }
        return RS;
    }

}
