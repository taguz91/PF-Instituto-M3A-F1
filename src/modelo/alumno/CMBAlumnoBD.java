package modelo.alumno;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.persona.AlumnoMD;
import utils.CONBD;

/**
 *
 * @author gus
 */
public class CMBAlumnoBD extends CONBD {

    private static CMBAlumnoBD CABD;

    public static CMBAlumnoBD single() {
        if (CABD == null) {
            CABD = new CMBAlumnoBD();
        }
        return CABD;
    }

    public List<AlumnoMD> getForBusquedaPeriodo(String aguja, int idPeriodo) {
        String sql = "SELECT\n"
                + "id_alumno,\n"
                + "persona_primer_nombre,\n"
                + "persona_segundo_nombre,\n"
                + "persona_primer_apellido,\n"
                + "persona_segundo_apellido\n"
                + "FROM public.\"Alumnos\" a\n"
                + "JOIN public.\"Personas\" p\n"
                + "ON a.id_persona = p.id_persona\n"
                + "WHERE id_alumno IN (\n"
                + "  SELECT id_alumno\n"
                + "  FROM public.\"AlumnosCarrera\"\n"
                + "  WHERE id_carrera IN (\n"
                + "    SELECT id_carrera\n"
                + "    FROM public.\"PeriodoLectivo\"\n"
                + "    WHERE id_prd_lectivo = ?\n"
                + "  )\n"
                + ") AND (\n"
                + "  persona_primer_nombre ILIKE ?\n"
                + "  OR persona_segundo_nombre ILIKE ?\n"
                + "  OR persona_primer_apellido ILIKE ?\n"
                + "  OR persona_segundo_apellido ILIKE ?\n"
                + "  OR persona_identificacion ILIKE ?\n"
                + "  OR persona_primer_nombre || ' ' ||\n"
                + "  persona_primer_apellido ILIKE ?\n"
                + ") ORDER BY persona_primer_apellido; ";
        List<AlumnoMD> as = new ArrayList<>();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            aguja = "%" + aguja + "%";
            ps.setInt(1, idPeriodo);
            ps.setString(2, aguja);
            ps.setString(3, aguja);
            ps.setString(4, aguja);
            ps.setString(5, aguja);
            ps.setString(6, aguja);
            ps.setString(7, aguja);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                AlumnoMD a = new AlumnoMD();
                a.setId_Alumno(res.getInt(1));
                a.setPrimerNombre(res.getString(2));
                a.setSegundoNombre(res.getString(3));
                a.setPrimerApellido(res.getString(4));
                a.setSegundoApellido(res.getString(5));
                as.add(a);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al consultar alumno "
                    + "por periodo para combo. \n"
                    + e.getMessage()
            );
        } finally {
            CON.cerrarCONPS(ps);
        }
        return as;
    }
}
