package modelo.silabo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConexionBD;
import modelo.ConnDBPool;
import modelo.carrera.CarreraMD;

/**
 *
 * @author Andres Ullauri
 */
public class CarrerasBDS extends CarreraMD {

    private final static ConnDBPool CONN = ConnDBPool.single();

    public CarrerasBDS(ConexionBD conexion) {
        //this.conexion = conexion;
    }

    public CarrerasBDS() {
    }

    // Pasado 
    public static List<CarreraMD> consultar(String clave) {
        String SELECT = "SELECT DISTINCT crr.id_carrera, crr.carrera_nombre\n"
                + "FROM \"Materias\" AS m\n"
                + "JOIN \"Cursos\" AS crs ON m.id_materia=crs.id_materia\n"
                + "JOIN \"Docentes\" AS d ON d.id_docente=crs.id_docente\n"
                + "JOIN \"Personas\" AS p ON p.id_persona=d.id_persona\n"
                + "JOIN \"Usuarios\" AS u ON u.id_persona=p.id_persona\n"
                + "JOIN \"Carreras\" AS crr ON crr.id_carrera = m.id_carrera\n"
                + "JOIN \"PeriodoLectivo\" AS pr ON pr.id_carrera=crr.id_carrera\n"
                + "WHERE usu_username= '" + clave + "'";
        List<CarreraMD> carreras = new ArrayList<>();

        ResultSet rs = CONN.ejecutarQuery(SELECT);

        try {

            while (rs.next()) {

                CarreraMD tmp = new CarreraMD();

                tmp.setId(rs.getInt(1));
                tmp.setNombre(rs.getString(2));

                carreras.add(tmp);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CarrerasBDS.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CONN.close(rs);
        }

        return carreras;

    }

    // Pasado
    public CarreraMD retornaModalidad(int id_curso) {
        CarreraMD carrera = null;

        String SELECT = "select carrera_modalidad from \"Carreras\" where id_carrera=(select id_carrera from \"PeriodoLectivo\"  where id_prd_lectivo=(select id_prd_lectivo from \"Cursos\" where id_curso=" + id_curso + "))";
        ResultSet rs = CONN.ejecutarQuery(SELECT);
        try {

            while (rs.next()) {
                carrera = new CarreraMD();
                carrera.setModalidad(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CarrerasBDS.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CONN.close(rs);
        }
        return carrera;
    }

    // Pasado
    public CarreraMD retornaCarreraCoordinador(String username) {
        CarreraMD carrera = null;
        String SELECT = "SELECT c.id_carrera, c.carrera_nombre FROM \"Carreras\" c \n"
                + "JOIN \"Docentes\" d ON c.id_docente_coordinador=d.id_docente\n"
                + "JOIN \"Personas\" p ON p.id_persona=d.id_persona\n"
                + "JOIN \"Usuarios\" u ON u.id_persona=p.id_persona\n"
                + "WHERE u.usu_username= '" + username + "' ";

        ResultSet rs = CONN.ejecutarQuery(SELECT);

        try {
            while (rs.next()) {
                carrera = new CarreraMD();

                carrera.setId(rs.getInt(1));
                carrera.setNombre(rs.getString(2));
            }

        } catch (SQLException ex) {
            Logger.getLogger(CarrerasBDS.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CONN.close(rs);
        }

        return carrera;
    }

}
