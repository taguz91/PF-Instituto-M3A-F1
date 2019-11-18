package modelo.estrategiasAprendizaje;

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
public class NEWEstrategiaAprendizajeBD extends CONBD implements IEstrategiaAprendizajeBD {

    private static NEWEstrategiaAprendizajeBD EBD;

    public static NEWEstrategiaAprendizajeBD single() {
        if (EBD == null) {
            EBD = new NEWEstrategiaAprendizajeBD();
        }
        return EBD;
    }

    @Override
    public int guardar(EstrategiasAprendizajeMD estrategia) {
        String sql = "INSERT INTO public.\"EstrategiasAprendizaje\"( "
                + "descripcion_estrategia) "
                + "VALUES (?);";
        PreparedStatement ps = CON.getPSID(sql);
        try {
            ps.setString(1, estrategia.getDescripcionEstrategia());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al guardar una estrategia.\n"
                    + e.getMessage(),
                    "Error al guardar",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return CON.getIDGenerado(ps);
    }

    @Override
    public List<EstrategiasAprendizajeMD> getAll() {
        String sql = "SELECT DISTINCT "
                + "id_estrategia, "
                + "descripcion_estrategia "
                + "FROM public.\"EstrategiasAprendizaje\" "
                + "ORDER BY descripcion_estrategia;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        return getByPS(ps);
    }

    @Override
    public List<EstrategiasAprendizajeMD> getByDescripcion(String descripcion) {
        String sql = "SELECT "
                + "id_estrategia, "
                + "descripcion_estrategia "
                + "FROM public.\"EstrategiasAprendizaje\" "
                + "WHERE descripcion_estrategia ILIKE ? "
                + "ORDER BY descripcion_estrategia;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setString(2, "%" + descripcion + "%");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al preparar la consulta por descripcion. \n"
                    + e.getMessage(),
                    "Error al consultar",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return getByPS(ps);
    }

    private List<EstrategiasAprendizajeMD> getByPS(PreparedStatement ps) {
        List<EstrategiasAprendizajeMD> ES = new ArrayList<>();
        try {
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                EstrategiasAprendizajeMD e = new EstrategiasAprendizajeMD();
                e.setIdEstrategia(res.getInt(1));
                e.setDescripcionEstrategia(res.getString(2));

                ES.add(e);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al mapear los datos en el modelo.\n"
                    + e.getMessage(),
                    "Error al consultar",
                    JOptionPane.ERROR_MESSAGE
            );
        } finally {
            CON.cerrarCONPS(ps);
        }
        return ES;
    }

}
