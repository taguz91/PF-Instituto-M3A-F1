package modelo.silabo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.ConnDBPool;

/**
 *
 * @author gus
 */
public class NEWSilaboBD {

    private final ConnDBPool CON = ConnDBPool.single();

    private static final String INSERT = "INSERT INTO "
            + "public.\"Silabo\"("
            + " id_materia,"
            + " id_prd_lectivo,"
            + " fecha_silabo "
            + ") VALUES (?, ?, now());";

    private static final String FROM_SILABO = "FROM \"Silabo\" AS s\n"
            + "JOIN \"Materias\" AS m ON s.id_materia=m.id_materia\n"
            + "JOIN \"PeriodoLectivo\" AS pr ON pr.id_prd_lectivo=s.id_prd_lectivo\n"
            + "JOIN \"Carreras\" AS crr ON crr.id_carrera = m.id_carrera\n"
            + "JOIN \"Cursos\" AS cr ON cr.id_materia=m.id_materia\n"
            + "JOIN \"Docentes\" AS d ON d.id_docente= cr.id_docente\n"
            + "JOIN \"Personas\" AS p ON d.id_persona=p.id_persona  "
            + "WHERE ";

    public int nuevoSilabo(SilaboMD s) {
        PreparedStatement ps = CON.getPSID(INSERT);
        try {
            ps.setInt(1, s.getMateria().getId());
            ps.setInt(2, s.getPeriodo().getId());
            return CON.getIDGenerado(ps);
        } catch (SQLException e) {
            return 0;
        }
    }

    public List<SilaboMD> getByCarreraPersonaPeriodo(
            String nombreCarrera,
            int idPersona,
            String nombrePeriodo
    ) {
        List<SilaboMD> SS = new ArrayList<>();
        String sql = "SELECT DISTINCT id_silabo,\n"
                + "s.id_materia, m.materia_nombre "
                + FROM_SILABO
                + " crr.carrera_nombre = ? "
                + "AND p.id_persona = ? "
                + " AND prd_lectivo_nombre = ? ;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setString(1, nombreCarrera);
            ps.setInt(2, idPersona);
            ps.setString(3, nombrePeriodo);

            ResultSet res = ps.executeQuery();

            while (res.next()) {
                SilaboMD s = new SilaboMD();
                s.setID(res.getInt(1));
                s.getMateria().setId(res.getInt(2));
                s.getMateria().setNombre(res.getString(3));

                SS.add(s);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al consultar. "
                    + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return SS;
    }

}
