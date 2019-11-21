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
public class RetiradoPeriodoLectivoBD extends CONBD {

    private static RetiradoPeriodoLectivoBD PBD;

    public static RetiradoPeriodoLectivoBD single() {
        if (PBD == null) {
            PBD = new RetiradoPeriodoLectivoBD();
        }
        return PBD;
    }

    public List<PeriodoLectivoMD> getForAlmnCarrera(int idAlmnCarrera) {
        List<PeriodoLectivoMD> pls = new ArrayList<>();
        String sql = "SELECT\n"
                + "id_prd_lectivo,\n"
                + "prd_lectivo_nombre\n"
                + "FROM public.\"PeriodoLectivo\" pl\n"
                + "WHERE pl.id_carrera IN (\n"
                + "  SELECT id_carrera\n"
                + "  FROM public.\"AlumnosCarrera\"\n"
                + "  WHERE id_almn_carrera = ?\n"
                + ") ORDER BY prd_lectivo_fecha_fin DESC;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setInt(1, idAlmnCarrera);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                PeriodoLectivoMD p = new PeriodoLectivoMD();
                p.setID(res.getInt(1));
                p.setNombre(res.getString(2));
                pls.add(p);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al consultar periodo"
                    + e.getMessage()
            );
        } finally {
            CON.cerrarCONPS(ps);
        }
        return pls;
    }

}
