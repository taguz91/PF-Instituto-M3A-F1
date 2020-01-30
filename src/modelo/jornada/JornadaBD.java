package modelo.jornada;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.CONBD;
import utils.M;

/**
 *
 * @author Johnny
 */
public class JornadaBD extends CONBD {

    private static JornadaBD JBD;

    public static JornadaBD single() {
        if (JBD == null) {
            JBD = new JornadaBD();
        }
        return JBD;
    }

    public static List<JornadaMD> cargarJornadas() {
        ArrayList<JornadaMD> jornadas = new ArrayList();
        String sql = "SELECT id_jornada, nombre_jornada\n"
                + "	FROM public.\"Jornadas\";";
        PreparedStatement ps = CON.prepareStatement(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                JornadaMD j = obtenerJornada(rs);
                if (j != null) {
                    jornadas.add(j);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(JornadaBD.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            CON.close(ps);
        }
        return jornadas;
    }

    public JornadaMD buscarJornada(int idJornada) {
        JornadaMD j = null;
        String sql = "SELECT id_jornada, nombre_jornada\n"
                + "	FROM public.\"Jornadas\"  "
                + "WHERE id_jornada = " + idJornada + ";";

        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                j = obtenerJornada(rs);
            }
            return j;
        } catch (SQLException e) {
            M.errorMsg("No pudimos consultar jornadas. " + e.getMessage());
            return null;
        } finally {
            CON.cerrarCONPS(ps);
        }
    }

    private static JornadaMD obtenerJornada(ResultSet rs) {
        JornadaMD j = new JornadaMD();
        try {
            j.setId(rs.getInt("id_jornada"));
            j.setNombre(rs.getString("nombre_jornada"));

            return j;
        } catch (SQLException e) {
            M.errorMsg("No pudimos consultar jornadas. " + e.getMessage());
            return null;
        }
    }

}
