package modelo.materia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.carrera.CarreraMD;
import utils.CONBD;
import utils.M;

/**
 *
 * @author Johnny
 */
public class EjeFormacionBD extends CONBD {

    public EjeFormacionMD buscar(int idEje) {
        EjeFormacionMD eje = new EjeFormacionMD();
        String sql = "SELECT id_eje, id_carrera, eje_codigo, eje_nombre\n"
                + "FROM public.\"EjesFormacion\" WHERE id_eje = '" + idEje + "';";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CarreraMD carrera = new CarreraMD();
                carrera.setId(rs.getInt("id_carrera"));
                eje.setCarrera(carrera);
                eje.setCodigo(rs.getString("eje_codigo"));
                eje.setId(rs.getInt("id_eje"));
                eje.setNombre(rs.getString("eje_nombre"));
            }
            return eje;
        } catch (SQLException e) {
            M.errorMsg("No se pudo buscar eje formacion. " + e.getMessage());
            return null;
        } finally {
            CON.cerrarCONPS(ps);
        }
    }

    public ArrayList<EjeFormacionMD> cargarEjesFormacion() {
        ArrayList<EjeFormacionMD> ejes = new ArrayList();
        String sql = "SELECT id_eje, id_carrera, eje_codigo, eje_nombre\n"
                + "FROM public.\"EjesFormacion\";";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                EjeFormacionMD eje = new EjeFormacionMD();
                CarreraMD carrera = new CarreraMD();
                carrera.setId(rs.getInt("id_carrera"));
                eje.setCarrera(carrera);
                eje.setCodigo(rs.getString("eje_codigo"));
                eje.setId(rs.getInt("id_eje"));
                eje.setNombre(rs.getString("eje_nombre"));

                ejes.add(eje);
            }
        } catch (SQLException e) {
            M.errorMsg("No se pudo buscar eje formacion. " + e.getMessage());
            return null;
        } finally {
            CON.cerrarCONPS(ps);
        }
        return ejes;
    }

    public ArrayList<EjeFormacionMD> cargarEjesPorCarrera(int idCarrera) {
        ArrayList<EjeFormacionMD> ejes = new ArrayList();
        String sql = "SELECT id_eje, id_carrera, eje_codigo, eje_nombre\n"
                + "FROM public.\"EjesFormacion\" WHERE id_carrera = '" + idCarrera + "';";
        PreparedStatement ps = CON.getPSPOOL(sql);
        CarreraMD carrera = new CarreraMD();
        carrera.setId(idCarrera);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                EjeFormacionMD eje = new EjeFormacionMD();
                eje.setCarrera(carrera);
                eje.setCodigo(rs.getString("eje_codigo"));
                eje.setId(rs.getInt("id_eje"));
                eje.setNombre(rs.getString("eje_nombre"));

                ejes.add(eje);
            }
        } catch (SQLException e) {
            M.errorMsg("No se pudo buscar eje formacion. " + e.getMessage());
            return null;
        } finally {
            CON.cerrarCONPS(ps);
        }
        return ejes;
    }

}
