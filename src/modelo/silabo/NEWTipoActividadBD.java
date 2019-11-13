package modelo.silabo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.silabo.mbd.ITipoActividadBD;
import modelo.tipoActividad.TipoActividadMD;
import utils.CONBD;

/**
 *
 * @author gus
 */
public class NEWTipoActividadBD extends CONBD implements ITipoActividadBD {

    private static NEWTipoActividadBD TABD;

    public static NEWTipoActividadBD single() {
        if (TABD == null) {
            TABD = new NEWTipoActividadBD();
        }
        return TABD;
    }

    @Override
    public List<TipoActividadMD> getAll() {
        String sql = "SELECT id_tipo_actividad, "
                + "nombre_tipo_actividad, "
                + "nombre_subtipo_actividad "
                + "FROM public.\"TipoActividad\"";
        PreparedStatement ps = CON.getPSPOOL(sql);
        List<TipoActividadMD> TAS = new ArrayList<>();
        try {
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                TipoActividadMD ta = new TipoActividadMD();
                ta.setIdTipoActividad(res.getInt(1));
                ta.setNombreTipoActividad(res.getString(2));
                ta.setNombreSubtipoActividad(res.getString(3));

                TAS.add(ta);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "No consultamos tipos actividad. \n"
                    + e.getMessage(),
                    "Error al consultar",
                    JOptionPane.ERROR_MESSAGE
            );
        } finally {
            CON.cerrarCONPS(ps);
        }
        return TAS;
    }

}
