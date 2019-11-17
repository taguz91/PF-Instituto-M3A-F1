package modelo.silabo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.estrategiasUnidad.EstrategiasUnidadMD;
import modelo.silabo.mbd.IEstrategiaUnidadSilaboBD;
import utils.CONBD;

/**
 *
 * @author gus
 */
public class NEWEstrategiaUnidadBD extends CONBD implements IEstrategiaUnidadSilaboBD {

    private static NEWEstrategiaUnidadBD EUBD;

    public static NEWEstrategiaUnidadBD single() {
        if (EUBD == null) {
            EUBD = new NEWEstrategiaUnidadBD();
        }
        return EUBD;
    }

    @Override
    public int guardar(EstrategiasUnidadMD e, int idUnidad) {
        String sql = "INSERT INTO public.\"EstrategiasUnidad\"("
                + " id_unidad, "
                + "id_estrategia) "
                + "VALUES (?, ?);";
        PreparedStatement ps = CON.getPSID(sql);
        int idGenerado = 0;
        try {
            ps.setInt(1, idUnidad);
            ps.setInt(2, e.getEstrategia().getIdEstrategia());
            idGenerado = CON.getIDGenerado(ps);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al guardar la estrategia unidad. \n"
                    + ex.getMessage(),
                    "Error estragia unidad",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return idGenerado;
    }

    public int editar(EstrategiasUnidadMD e) {
        String sql = "UPDATE public.\"EstrategiasUnidad\" "
                + "SET id_unidad=?, "
                + "id_estrategia=? "
                + "WHERE id_estrategia_unidad=?;";
        PreparedStatement ps = CON.getPSID(sql);
        int idGenerado = 0;
        try {
            ps.setInt(1, e.getUnidad().getIdUnidad());
            ps.setInt(2, e.getEstrategia().getIdEstrategia());
            ps.setInt(3, e.getIdEstrategiaUnidad());
            idGenerado = CON.getIDGenerado(ps);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al guardar la estrategia unidad. \n"
                    + ex.getMessage(),
                    "Error estragia unidad",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return idGenerado;
    }

    @Override
    public List<EstrategiasUnidadMD> getBySilabo(int idSilabo) {
        String sql = "SELECT "
                + "eu.id_unidad, "
                + "eu.id_estrategia, "
                + "ea.descripcion_estrategia, "
                + "us.numero_unidad  "
                + "FROM public.\"EstrategiasUnidad\" eu, "
                + "public.\"UnidadSilabo\" us, "
                + "public.\"EstrategiasAprendizaje\" ea "
                + "WHERE eu.id_unidad = us.id_unidad "
                + "AND eu.id_estrategia = ea.id_estrategia "
                + "AND id_silabo=?;";
        List<EstrategiasUnidadMD> EUS = new ArrayList<>();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setInt(1, idSilabo);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                EstrategiasUnidadMD eu = new EstrategiasUnidadMD();

                eu.getUnidad().setIdUnidad(res.getInt(1));
                eu.getUnidad().setNumeroUnidad(res.getInt(4));
                eu.getEstrategia().setIdEstrategia(res.getInt(2));
                eu.getEstrategia().setDescripcionEstrategia(res.getString(3));

                EUS.add(eu);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "No consultamos estrategias por id silabo. \n"
                    + e.getMessage(),
                    "Error al consultar",
                    JOptionPane.ERROR_MESSAGE
            );
        } finally {
            CON.cerrarCONPS(ps);
        }
        return EUS;
    }

    @Override
    public List<EstrategiasUnidadMD> getBySilaboUnidad(int idSilabo, int numUnidad) {
        String sql = "SELECT "
                + "DISTINCT ea.descripcion_estrategia "
                + "FROM public.\"EstrategiasUnidad\" eu, "
                + "public.\"UnidadSilabo\" us, "
                + "public.\"EstrategiasAprendizaje\" ea "
                + "WHERE eu.id_unidad = us.id_unidad "
                + "AND eu.id_estrategia = ea.id_estrategia "
                + "AND id_silabo=? AND numero_unidad=?";
        List<EstrategiasUnidadMD> EUS = new ArrayList<>();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setInt(1, idSilabo);
            ps.setInt(2, numUnidad);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                EstrategiasUnidadMD eu = new EstrategiasUnidadMD();
                eu.getEstrategia().setDescripcionEstrategia(res.getString(1));

                EUS.add(eu);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "No consultamos estrategias por id silabo y unidad. \n"
                    + e.getMessage(),
                    "Error al consultar",
                    JOptionPane.ERROR_MESSAGE
            );
        } finally {
            CON.cerrarCONPS(ps);
        }
        return EUS;
    }

}
