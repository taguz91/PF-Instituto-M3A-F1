/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.silabo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConexionBD;
import modelo.carrera.CarreraMD;
import modelo.persona.DocenteMD;

/**
 *
 * @author Andres Ullauri
 */
public class CarrerasBDS extends CarreraMD {

    private ConexionBD conexion;

    public CarrerasBDS(ConexionBD conexion) {
        this.conexion = conexion;
    }

    public static List<CarreraMD> consultar(ConexionBD conexion, String clave) {

        List<CarreraMD> carreras = new ArrayList<>();
        try {

            PreparedStatement st = conexion.getCon().prepareStatement("SELECT DISTINCT crr.id_carrera, crr.carrera_nombre\n"
                    + "FROM \"Materias\" AS m\n"
                    + "JOIN \"Cursos\" AS crs ON m.id_materia=crs.id_materia\n"
                    + "JOIN \"Docentes\" AS d ON d.id_docente=crs.id_docente\n"
                    + "JOIN \"Personas\" AS p ON p.id_persona=d.id_persona\n"
                    + "JOIN \"Usuarios\" AS u ON u.id_persona=p.id_persona\n"
                    + "JOIN \"Carreras\" AS crr ON crr.id_carrera = m.id_carrera\n"
                    + "JOIN \"PeriodoLectivo\" AS pr ON pr.id_carrera=crr.id_carrera\n"
                    + "WHERE usu_username=? ");

            st.setString(1, clave);
            System.out.println(st);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                CarreraMD tmp = new CarreraMD();

                tmp.setId(rs.getInt(1));
                tmp.setNombre(rs.getString(2));

                carreras.add(tmp);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CarrerasBDS.class.getName()).log(Level.SEVERE, null, ex);
        }

        return carreras;

    }

    public CarreraMD retornaCarreraCoordinador(String username) {
        CarreraMD carrera = null;
        try {

            PreparedStatement st = conexion.getCon().prepareStatement("SELECT c.id_carrera, c.carrera_nombre FROM \"Carreras\" c \n"
                    + "JOIN \"Docentes\" d ON c.id_docente_coordinador=d.id_docente\n"
                    + "JOIN \"Personas\" p ON p.id_persona=d.id_persona\n"
                    + "JOIN \"Usuarios\" u ON u.id_persona=p.id_persona\n"
                    + "WHERE u.usu_username=?");
            
            st.setString(1, username);
            System.out.println(st);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                carrera = new CarreraMD();
                
                carrera.setId(rs.getInt(1));
                carrera.setNombre(rs.getString(2));
            }

        } catch (SQLException ex) {
            Logger.getLogger(CarrerasBDS.class.getName()).log(Level.SEVERE, null, ex);

        }

        return carrera;
    }

}
