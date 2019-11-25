package modelo.pagos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.alumno.MallaAlumnoMD;
import modelo.materia.MateriaMD;
import utils.CONBD;

/**
 *
 * @author gus
 */
public class UtilComprobanteBD extends CONBD {

    private static UtilComprobanteBD UCBD;

    public static UtilComprobanteBD single() {
        if (UCBD == null) {
            UCBD = new UtilComprobanteBD();
        }
        return UCBD;
    }

    public List<MallaAlumnoMD> getByAlumno(int idAlumno) {
        String sql = "SELECT\n"
                + "id_malla_alumno,\n"
                + "malla_almn_num_matricula,\n"
                + "materia_nombre,\n"
                + "materia_codigo\n"
                + "FROM public.\"MallaAlumno\" ma\n"
                + "JOIN public.\"Materias\" m\n"
                + "ON ma.id_materia = m.id_materia\n"
                + "WHERE id_almn_carrera IN (\n"
                + "  SELECT id_almn_carrera\n"
                + "  FROM public.\"AlumnosCarrera\"\n"
                + "  WHERE id_alumno = ?\n"
                + ") AND ( malla_almn_num_matricula > 1\n"
                + "OR malla_almn_estado = 'R' )\n"
                + "ORDER BY materia_ciclo;";
        List<MallaAlumnoMD> ms = new ArrayList<>();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setInt(1, idAlumno);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                MallaAlumnoMD ma = new MallaAlumnoMD();
                ma.setId(res.getInt(1));
                ma.setMallaNumMatricula(res.getInt(2));
                MateriaMD m = new MateriaMD();
                m.setNombre(res.getString(3));
                m.setCodigo(res.getString(4));
                ma.setMateria(m);
                // Agregamos a la lista 
                ms.add(ma);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al mapear materia con pago pendiente.\n"
                    + e.getMessage()
            );
        } finally {
            CON.cerrarCONPS(ps);
        }
        return ms;
    }

}
