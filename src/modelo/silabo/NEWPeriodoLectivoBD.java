package modelo.silabo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.ConnDBPool;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.silabo.mbd.IPeriodoLectivoBD;

/**
 *
 * @author gus
 */
public class NEWPeriodoLectivoBD implements IPeriodoLectivoBD {

    private final ConnDBPool CON = ConnDBPool.single();
    private static NEWPeriodoLectivoBD PBD;

    public static NEWPeriodoLectivoBD single() {
        if (PBD == null) {
            PBD = new NEWPeriodoLectivoBD();
        }
        return PBD;
    }

    @Override
    public List<PeriodoLectivoMD> getByCarrera(int idCarrera) {
        String sql = "SELECT p.id_prd_lectivo, "
                + "p.prd_lectivo_nombre, "
                + "p.prd_lectivo_fecha_inicio, "
                + "p.prd_lectivo_fecha_fin "
                + "FROM \"PeriodoLectivo\" AS p\n"
                + "JOIN \"Carreras\" AS c ON c.id_carrera = p.id_carrera "
                + "WHERE c.id_carrera=?  "
                + "AND  p.prd_lectivo_fecha_inicio >= '2018-11-12' "
                + "ORDER BY p.id_prd_lectivo DESC";
        List<PeriodoLectivoMD> PS = new ArrayList<>();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setInt(1, idCarrera);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                PeriodoLectivoMD p = new PeriodoLectivoMD();
                p.setID(res.getInt(1));
                p.setNombre(res.getString(2));
                p.setFechaInicio(res.getDate(3).toLocalDate());
                p.setFechaFin(res.getDate(4).toLocalDate());
                PS.add(p);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al consultar periodo lectivo por ID carrera. \n"
                    + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return PS;
    }

    @Override
    public List<PeriodoLectivoMD> getByCarrera(String nombre) {
        String sql = "";
        List<PeriodoLectivoMD> PS = new ArrayList<>();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setString(1, nombre);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                PeriodoLectivoMD p = new PeriodoLectivoMD();
                p.setID(res.getInt(1));
                p.setNombre(res.getString(2));
                p.setFechaFin(res.getDate(3).toLocalDate());
                PS.add(p);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al consultar periodo lectivo por carrera. \n"
                    + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return PS;
    }

}
