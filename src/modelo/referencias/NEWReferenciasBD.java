package modelo.referencias;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import utils.CONBD;

/**
 *
 * @author gus
 */
public class NEWReferenciasBD extends CONBD {

    private static NEWReferenciasBD RBD;

    public static NEWReferenciasBD single() {
        if (RBD == null) {
            RBD = new NEWReferenciasBD();
        }
        return RBD;
    }

    private static final String BASE_QUERY = "SELECT id_referencia, "
            + "codigo_referencia, "
            + "descripcion_referencia, "
            + "tipo_referencia, "
            + "existe_en_biblioteca, "
            + "autor2, "
            + "autor3 "
            + "FROM public.\"Referencias\" "
            + "WHERE tipo_referencia='Base' ";

    public int editarNoBase(ReferenciasMD r) {
        String sql = "UPDATE public.\"Referencias\" "
                + "SET descripcion_referencia = ? "
                + "WHERE id_referencia = ?;";
        PreparedStatement ps = CON.getPSID(sql);
        try {
            ps.setString(1, r.getDescripcionReferencia());
            ps.setInt(2, r.getIdReferencia());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "No guardamos la bibliografia no base\n"
                    + e.getMessage(),
                    "Error al guardar referencia",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return CON.getIDGenerado(ps);
    }

    public int guardarNoBase(ReferenciasMD r) {
        String sql = "INSERT INTO public.\"Referencias\"( "
                + "codigo_referencia, "
                + "descripcion_referencia, "
                + "tipo_referencia, "
                + "existe_en_biblioteca) "
                + "VALUES ( ?, ?, ?, false);";
        PreparedStatement ps = CON.getPSID(sql);
        try {
            ps.setString(1, r.getCodigoReferencia());
            ps.setString(2, r.getDescripcionReferencia());
            ps.setString(3, r.getTipoReferencia());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "No guardamos la bibliografia no base\n"
                    + e.getMessage(),
                    "Error al guardar referencia",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return CON.getIDGenerado(ps);
    }

    public List<ReferenciasMD> getAllBiblioteca() {
        return getForTbl(
                BASE_QUERY
                + " AND existe_en_biblioteca = true "
                + "ORDER BY codigo_referencia;"
        );
    }

    public List<ReferenciasMD> getAllVirtual() {
        return getForTbl(
                BASE_QUERY
                + " AND existe_en_biblioteca = false "
                + "ORDER BY codigo_referencia;"
        );
    }

    private List<ReferenciasMD> getForTbl(String sql) {
        List<ReferenciasMD> RS = new ArrayList<>();
        PreparedStatement ps = CON.getPSPOOL(sql);
        if (ps != null) {

            try {
                ResultSet res = ps.executeQuery();
                while (res.next()) {
                    ReferenciasMD r = new ReferenciasMD();
                    r.setIdReferencia(res.getInt(1));
                    r.setCodigoReferencia(res.getString(2));
                    r.setDescripcionReferencia(res.getString(3));
                    r.setTipoReferencia(res.getString(4));
                    r.setExisteEnBiblioteca(res.getBoolean(5));
                    RS.add(r);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Error al consultar referencias "
                        + e.getMessage(),
                        "Error consulta",
                        JOptionPane.ERROR_MESSAGE
                );
            } finally {
                CON.cerrarCONPS(ps);
            }
        }

        return RS;
    }

}
