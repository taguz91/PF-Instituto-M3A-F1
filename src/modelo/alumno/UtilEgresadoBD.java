package modelo.alumno;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.materia.MateriaMD;
import utils.CONBD;

/**
 *
 * @author gus
 */
public class UtilEgresadoBD extends CONBD {

    private static UtilEgresadoBD UEBD;

    public static UtilEgresadoBD single() {
        if (UEBD == null) {
            UEBD = new UtilEgresadoBD();
        }
        return UEBD;
    }

    public List<MallaAlumnoMD> getMateriasNoPagadas(int idAlumnoCarrera) {
        String sql = "SELECT\n"
                + "id_malla_alumno,\n"
                + "malla_almn_num_matricula,\n"
                + "materia_nombre,\n"
                + "materia_ciclo\n"
                + "FROM public.\"MallaAlumno\" ma\n"
                + "JOIN public.\"Materias\" m\n"
                + "ON ma.id_materia = m.id_materia\n"
                + "WHERE id_almn_carrera = ? "
                + "AND malla_almn_pago_pendiente = true\n"
                + "ORDER BY materia_ciclo;";
        List<MallaAlumnoMD> mas = new ArrayList<>();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setInt(1, idAlumnoCarrera);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                MallaAlumnoMD ma = new MallaAlumnoMD();
                ma.setId(res.getInt(1));
                ma.setMallaNumMatricula(res.getInt(2));
                MateriaMD m = new MateriaMD();
                m.setNombre(res.getString(3));
                m.setCiclo(res.getInt(4));
                ma.setMateria(m);
                mas.add(ma);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al mapear las materias que "
                    + "no cancelar su deuda un alumno."
            );
        } finally {
            CON.cerrarCONPS(ps);
        }
        return mas;
    }

}
