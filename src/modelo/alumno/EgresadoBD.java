package modelo.alumno;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.periodolectivo.PeriodoLectivoMD;
import utils.CONBD;

/**
 *
 * @author gus
 */
public class EgresadoBD extends CONBD {

    private static EgresadoBD EBD;

    public static EgresadoBD single() {
        if (EBD == null) {
            EBD = new EgresadoBD();
        }
        return EBD;
    }

    public List<PeriodoLectivoMD> getPeriodoByIdAlmnCarrera(int idAlmnCarrera) {
        List<PeriodoLectivoMD> pls = new ArrayList<>();
        String sql = "SELECT\n"
                + "id_prd_lectivo,\n"
                + "prd_lectivo_nombre\n"
                + "FROM public.\"PeriodoLectivo\" pl\n"
                + "JOIN public.\"Matricula\" m ON pl.id_prd_lectivo = m.id_prd_lectivo\n"
                + "WHERE pl.id_carrera IN (\n"
                + "  SELECT id_carrera\n"
                + "  FROM public.\"AlumnosCarrera\"\n"
                + "  WHERE id_almn_carrera = ?\n"
                + ") AND m.id_alumno = (\n"
                + "  SELECT id_alumno\n"
                + "  FROM public.\"AlumnosCarrera\"\n"
                + "  WHERE id_almn_carrera = ?\n"
                + ") ORDER BY prd_lectivo_fecha_fin;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setInt(1, idAlmnCarrera);
            ps.setInt(2, idAlmnCarrera);
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
                    "Error al consultar ultimo periodo "
                    + "en el que se matriculo. \n"
                    + e.getMessage()
            );
        } finally {
            CON.cerrarCONPS(ps);
        }
        return pls;
    }

    public int guardarSinGraduacion(Egresado e) {
        String sql = "INSERT INTO alumno.\"Egresados\"(\n"
                + "id_almn_carrera,\n"
                + "id_prd_lectivo,\n"
                + "fecha_egreso)\n"
                + "VALUES (?, ?, ?);";
        PreparedStatement ps = CON.getPSID(sql);
        try {
            ps.setInt(1, e.getAlmnCarrera().getId());
            ps.setInt(2, e.getPeriodo().getID());
            ps.setDate(3, Date.valueOf(e.getFechaEgreso()));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al guardar sin graduacion.\n"
                    + ex.getMessage()
            );
        }
        return CON.getIDGenerado(ps);
    }

    public int editarSinGraduacion(Egresado e) {
        String sql = "UPDATE alumno.\"Egresados\" SET \n"
                + " id_prd_lectivo=?,\n"
                + " fecha_egreso=?, "
                + " graduado=? \n"
                + "  WHERE id_egresado=?;";
        PreparedStatement ps = CON.getPSID(sql);
        try {
            ps.setInt(1, e.getPeriodo().getID());
            ps.setDate(2, Date.valueOf(e.getFechaEgreso()));
            ps.setBoolean(3, e.isGraduado());
            ps.setInt(4, e.getId());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al editar sin graduacion.\n"
                    + ex.getMessage()
            );
        }
        return CON.getIDGenerado(ps);
    }

    public int guardarConGraduacion(Egresado e) {
        String sql = "INSERT INTO alumno.\"Egresados\"(\n"
                + "id_almn_carrera,\n"
                + "id_prd_lectivo,\n"
                + "fecha_egreso,\n"
                + "graduado,\n"
                + "fecha_graduacion )\n"
                + "VALUES (?, ?, ?, ?, ?);";
        PreparedStatement ps = CON.getPSID(sql);
        try {
            ps.setInt(1, e.getAlmnCarrera().getId());
            ps.setInt(2, e.getPeriodo().getID());
            ps.setDate(3, Date.valueOf(e.getFechaEgreso()));
            ps.setBoolean(4, e.isGraduado());
            ps.setDate(5, Date.valueOf(e.getFechaGraduacion()));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al editar sin graduacion.\n"
                    + ex.getMessage()
            );
        }
        return CON.getIDGenerado(ps);
    }

    public int editarConGraduacion(Egresado e) {
        String sql = "UPDATE alumno.\"Egresados\" SET\n"
                + "id_almn_carrera=?,\n"
                + "id_prd_lectivo=?,\n"
                + "fecha_egreso=?,\n"
                + "graduado=?,\n"
                + "fecha_graduacion=?\n"
                + " WHERE id_egresado=?;";
        PreparedStatement ps = CON.getPSID(sql);
        try {
            ps.setInt(1, e.getAlmnCarrera().getId());
            ps.setInt(2, e.getPeriodo().getID());
            ps.setDate(3, Date.valueOf(e.getFechaEgreso()));
            ps.setBoolean(4, e.isGraduado());
            ps.setDate(5, Date.valueOf(e.getFechaGraduacion()));
            ps.setInt(6, e.getId());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al editar sin graduacion.\n"
                    + ex.getMessage()
            );
        }
        return CON.getIDGenerado(ps);
    }

}