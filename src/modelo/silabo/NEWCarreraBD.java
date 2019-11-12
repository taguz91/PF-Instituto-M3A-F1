package modelo.silabo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.ConnDBPool;
import modelo.carrera.CarreraMD;
import modelo.silabo.mbd.ICarreraBD;

/**
 *
 * @author gus
 */
public class NEWCarreraBD implements ICarreraBD {

    private final ConnDBPool CON = ConnDBPool.single();

    private static NEWCarreraBD CBD;

    public static NEWCarreraBD single() {
        if (CBD == null) {
            CBD = new NEWCarreraBD();
        }
        return CBD;
    }
    
    @Override
    public List<CarreraMD> getByUsername(String username) {
        String sql = "SELECT DISTINCT crr.id_carrera, "
                + "crr.carrera_nombre "
                + "FROM \"Materias\" AS m "
                + "JOIN \"Cursos\" AS crs ON m.id_materia=crs.id_materia\n"
                + "JOIN \"Docentes\" AS d ON d.id_docente=crs.id_docente\n"
                + "JOIN \"Personas\" AS p ON p.id_persona=d.id_persona\n"
                + "JOIN \"Usuarios\" AS u ON u.id_persona=p.id_persona\n"
                + "JOIN \"Carreras\" AS crr ON crr.id_carrera = m.id_carrera\n"
                + "JOIN \"PeriodoLectivo\" AS pr ON pr.id_carrera=crr.id_carrera\n"
                + "WHERE usu_username=? ";
        List<CarreraMD> CS = new ArrayList<>();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setString(1, username);
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                CarreraMD c = new CarreraMD();
                c.setId(res.getInt(1));
                c.setNombre(res.getString(2));
                CS.add(c);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "No consultamos carreras por username. \n"
                    + e.getMessage(),
                    "Error al consultar",
                    JOptionPane.ERROR_MESSAGE
            );
        } finally {
            CON.cerrarCONPS(ps);
        }
        return CS;
    }

    @Override
    public String getModalidadByCurso(int idCurso) {
        String sql = "SELECT carrera_modalidad\n"
                + "FROM \"Carreras\"\n"
                + "WHERE id_carrera = (\n"
                + "  SELECT id_carrera\n"
                + "  FROM \"PeriodoLectivo\"\n"
                + "  WHERE id_prd_lectivo = (\n"
                + "    SELECT id_prd_lectivo FROM \"Cursos\"\n"
                + "    WHERE id_curso = ?\n"
                + "  )\n"
                + ");";
        String m = "";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setInt(1, idCurso);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                m = res.getString(1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "No modalidad por curso. \n"
                    + e.getMessage(),
                    "Error al consultar",
                    JOptionPane.ERROR_MESSAGE
            );
        } finally {
            CON.cerrarCONPS(ps);
        }
        return m;
    }

    @Override
    public CarreraMD getByCoordinador(String username) {
        String sql = "SELECT c.id_carrera, "
                + "c.carrera_nombre "
                + "FROM \"Carreras\" c "
                + "JOIN \"Docentes\" d ON c.id_docente_coordinador=d.id_docente\n"
                + "JOIN \"Personas\" p ON p.id_persona=d.id_persona\n"
                + "JOIN \"Usuarios\" u ON u.id_persona=p.id_persona\n"
                + "WHERE u.usu_username = ?;";
        CarreraMD c = null;
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setString(1, username);
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                c = new CarreraMD();
                c.setId(res.getInt(1));
                c.setNombre(res.getString(2));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "No obtuvimos al coordinador.\n"
                    + e.getMessage(),
                    "Error al consultar",
                    JOptionPane.ERROR_MESSAGE
            );
        } finally {
            CON.cerrarCONPS(ps);
        }
        return c;
    }

}
