package modelo.silabo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConnDBPool;

/**
 *
 * @author Andres Ullauri
 */
public class SilaboBD extends SilaboMD {

    private static ConnDBPool CON = ConnDBPool.single();

    // Pasada
    public static List<SilaboMD> consultarSilabo1(String[] clave) {

        List<SilaboMD> silabos = new ArrayList<>();

        PreparedStatement st = CON.prepareStatement("SELECT DISTINCT id_silabo,\n"
                + "s.id_materia, m.materia_nombre\n"
                + "FROM \"Silabo\" AS s\n"
                + "JOIN \"Materias\" AS m ON s.id_materia=m.id_materia\n"
                + "JOIN \"PeriodoLectivo\" AS pr ON pr.id_prd_lectivo=s.id_prd_lectivo\n"
                + "JOIN \"Carreras\" AS crr ON crr.id_carrera = m.id_carrera\n"
                + "JOIN \"Cursos\" AS cr ON cr.id_materia=m.id_materia\n"
                + "JOIN \"Docentes\" AS d ON d.id_docente= cr.id_docente\n"
                + "JOIN \"Personas\" AS p ON d.id_persona=p.id_persona\n"
                + "WHERE crr.carrera_nombre=?\n"
                + "AND p.id_persona=? AND prd_lectivo_nombre=?");

        try {

            st.setString(1, clave[0]);
            st.setInt(2, Integer.parseInt(clave[1]));
            st.setString(3, clave[2]);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                SilaboMD tmp = new SilaboMD();
                tmp.setID(rs.getInt(1));
                tmp.getMateria().setId(rs.getInt(2));
                tmp.getMateria().setNombre(rs.getString(3));

                silabos.add(tmp);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(st);
        }
        return silabos;
    }

}
