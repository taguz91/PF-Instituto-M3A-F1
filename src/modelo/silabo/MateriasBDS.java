/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.silabo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConnDBPool;
import modelo.materia.MateriaMD;

/**
 *
 * @author Andres Ullauri
 */
public class MateriasBDS extends MateriaMD {

    private static final ConnDBPool CON = ConnDBPool.single();

    public MateriasBDS() {
    }

    public static List<MateriaMD> consultarSilabo2(String carrera, int id_persona, String periodo_nombe) {
        PreparedStatement st = CON.prepareStatement("SELECT DISTINCT id_silabo,\n"
                + "m.materia_nombre\n"
                + "FROM \"Silabo\" AS s\n"
                + "JOIN \"Materias\" AS m ON s.id_materia=m.id_materia\n"
                + "JOIN \"PeriodoLectivo\" AS pr ON pr.id_prd_lectivo=s.id_prd_lectivo\n"
                + "JOIN \"Carreras\" AS crr ON crr.id_carrera = m.id_carrera\n"
                + "JOIN \"Cursos\" AS cr ON cr.id_materia=m.id_materia\n"
                + "JOIN \"Docentes\" AS d ON d.id_docente= cr.id_docente\n"
                + "JOIN \"Personas\" AS p ON d.id_persona=p.id_persona\n"
                + "WHERE crr.carrera_nombre=?\n"
                + "AND p.id_persona=? AND pr.prd_lectivo_nombre=? AND cr.id_prd_lectivo=s.id_prd_lectivo");

        List<MateriaMD> materias = new ArrayList<>();
        try {

            st.setString(1, carrera);
            st.setInt(2, id_persona);
            st.setString(3, periodo_nombe);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                MateriaMD tmp = new MateriaMD();
                tmp.setNombre(rs.getString(2));

                materias.add(tmp);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(st);
        }
        return materias;
    }
}
