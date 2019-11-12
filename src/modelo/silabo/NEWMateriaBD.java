package modelo.silabo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.ConnDBPool;
import modelo.materia.MateriaMD;
import modelo.silabo.mbd.IMateriaBD;

/**
 *
 * @author gus
 */
public class NEWMateriaBD implements IMateriaBD {

    private final ConnDBPool CON = ConnDBPool.single();
    
    private static NEWMateriaBD MBD; 
    
    public static NEWMateriaBD single(){
        if(MBD == null){
            MBD = new NEWMateriaBD();
        }
        return MBD;
    }

    @Override
    public List<MateriaMD> getByUsernameCarreraPeriodoSinSilabo(String username, int idPeriodo, int idCarrera) {
        String sql = "SELECT DISTINCT "
                + " m.id_materia, "
                + " m.materia_nombre, "
                + " m.materia_horas_docencia, "
                + " m.materia_horas_practicas, "
                + " m.materia_horas_auto_estudio\n"
                + "FROM \"Materias\" AS m "
                + "JOIN \"Cursos\" AS crs ON m.id_materia=crs.id_materia\n"
                + "JOIN \"Docentes\" AS d ON d.id_docente=crs.id_docente\n"
                + "JOIN \"Personas\" AS p ON p.id_persona=d.id_persona\n"
                + "JOIN \"Usuarios\" AS u ON u.id_persona=p.id_persona\n"
                + "JOIN \"Carreras\" AS crr ON crr.id_carrera = m.id_carrera\n"
                + "JOIN \"PeriodoLectivo\" AS pr ON pr.id_prd_lectivo=crs.id_prd_lectivo\n"
                + "WHERE usu_username=? "
                + "AND crr.id_carrera=? "
                + "AND crs.id_prd_lectivo=? "
                + "EXCEPT "
                + "SELECT DISTINCT \n"
                + "m.id_materia, m.materia_nombre, m.materia_horas_docencia, m.materia_horas_practicas, m.materia_horas_auto_estudio\n"
                + "FROM \"Materias\" AS m\n"
                + "JOIN \"Cursos\" AS crs ON m.id_materia=crs.id_materia\n"
                + "JOIN \"Docentes\" AS d ON d.id_docente=crs.id_docente\n"
                + "JOIN \"Personas\" AS p ON p.id_persona=d.id_persona\n"
                + "JOIN \"Usuarios\" AS u ON u.id_persona=p.id_persona\n"
                + "JOIN \"Carreras\" AS crr ON crr.id_carrera = m.id_carrera\n"
                + "JOIN \"PeriodoLectivo\" AS pr ON pr.id_prd_lectivo=crs.id_prd_lectivo\n"
                + "JOIN \"Silabo\" AS s ON s.id_materia = m.id_materia\n"
                + "WHERE usu_username=? "
                + "AND crr.id_carrera=? "
                + "AND s.id_prd_lectivo=?";

        List<MateriaMD> MS = new ArrayList<>();

        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setString(1, username);
            ps.setInt(2, idCarrera);
            ps.setInt(3, idPeriodo);
            ps.setString(4, username);
            ps.setInt(5, idCarrera);
            ps.setInt(6, idPeriodo);

            ResultSet res = ps.executeQuery();

            while (res.next()) {

                MateriaMD m = new MateriaMD();

                m.setId(res.getInt(1));
                m.setNombre(res.getString(2));
                m.setHorasDocencia(res.getInt(3));
                m.setHorasPracticas(res.getInt(4));
                m.setHorasAutoEstudio(res.getInt(5));

                MS.add(m);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al consultar materias. "
                    + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return MS;
    }

    @Override
    public List<MateriaMD> getByCarreraPersonaPeriodo(String carreraNombre, int idPersona, String periodoNombre) {
        String sql = "SELECT DISTINCT id_silabo,\n"
                + "m.materia_nombre\n"
                + "FROM \"Silabo\" AS s\n"
                + "JOIN \"Materias\" AS m ON s.id_materia=m.id_materia\n"
                + "JOIN \"PeriodoLectivo\" AS pr ON pr.id_prd_lectivo=s.id_prd_lectivo\n"
                + "JOIN \"Carreras\" AS crr ON crr.id_carrera = m.id_carrera\n"
                + "JOIN \"Cursos\" AS cr ON cr.id_materia=m.id_materia\n"
                + "JOIN \"Docentes\" AS d ON d.id_docente= cr.id_docente\n"
                + "JOIN \"Personas\" AS p ON d.id_persona=p.id_persona\n"
                + "WHERE crr.carrera_nombre=?\n"
                + "AND p.id_persona=? AND pr.prd_lectivo_nombre=? AND cr.id_prd_lectivo=s.id_prd_lectivo";
        List<MateriaMD> MS = new ArrayList<>();
        PreparedStatement ps = CON.getPSPOOL(sql);

        try {
            ps.setString(1, carreraNombre);
            ps.setInt(2, idPersona);
            ps.setString(3, periodoNombre);
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                MateriaMD m = new MateriaMD();
                m.setNombre(res.getString(2));
                MS.add(m);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al consultar materias. "
                    + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return MS;
    }

}
