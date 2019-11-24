package modelo.periodolectivo;

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
public class CMBPeriodoLectivoBD extends CONBD {
    
    private static CMBPeriodoLectivoBD PLBD; 
    
    public static CMBPeriodoLectivoBD single(){
        if (PLBD == null) {
            PLBD = new CMBPeriodoLectivoBD();
        }
        return PLBD;
    }

    public List<PeriodoLectivoMD> getForCmbSoloAbiertos() {
        List<PeriodoLectivoMD> pls = new ArrayList<>();
        String sql = "SELECT "
                + "id_prd_lectivo, "
                + "prd_lectivo_nombre "
                + "FROM public.\"PeriodoLectivo\" "
                + "WHERE prd_lectivo_activo = true  "
                + "AND prd_lectivo_estado = true "
                + "ORDER BY prd_lectivo_fecha_fin DESC;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                PeriodoLectivoMD pl = new PeriodoLectivoMD();
                pl.setID(res.getInt("id_prd_lectivo"));
                pl.setNombre(res.getString("prd_lectivo_nombre"));
                pls.add(pl);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "No consultamos los combos "
                    + "abiertos. " + e.getMessage()
            );
        } finally {
            CON.cerrarCONPS(ps);
        }
        return pls;
    }

}
