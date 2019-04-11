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
import modelo.ConexionBD;
import modelo.carrera.CarreraMD;
import modelo.materia.EjeFormacionMD;
import modelo.materia.MateriaMD;

/**
 *
 * @author Andres Ullauri
 */
public class MateriasBDS extends MateriaMD {

    private ConexionBD conexion;

    public MateriasBDS(ConexionBD conexion) {
        this.conexion = conexion;
    }

    public MateriasBDS(ConexionBD conexion, int id, CarreraMD carrera, EjeFormacionMD eje, String codigo, String nombre, int ciclo, int creditos, char tipo, String categoria, char tipoAcreditacion, int horasDocencia, int horasPracticas, int horasAutoEstudio, int horasPresenciales, int totalHoras, String objetivo, String descripcion, String objetivoespecifico, String organizacioncurricular, String materiacampoformacion) {
        super(id, carrera, eje, codigo, nombre, ciclo, creditos, tipo, categoria, tipoAcreditacion, horasDocencia, horasPracticas, horasAutoEstudio, horasPresenciales, totalHoras, objetivo, descripcion, objetivoespecifico, organizacioncurricular, materiacampoformacion);
        this.conexion = conexion;
    }

    public static List<MateriaMD> consultar(ConexionBD conexion, String[] clave) {

        List<MateriaMD> materias = new ArrayList<>();
        try {

            PreparedStatement st = conexion.getCon().prepareStatement("SELECT  DISTINCT m.id_materia, m.materia_nombre, m.materia_horas_practicas, m.materia_horas_auto_estudio, m.materia_horas_presencial \n"
                    + "FROM \"Materias\" AS m\n"
                    + "JOIN \"Cursos\" AS crs ON m.id_materia=crs.id_materia\n"
                    + "JOIN \"Docentes\" AS d ON d.id_docente=crs.id_docente\n"
                    + "JOIN \"Personas\" AS p ON p.id_persona=d.id_persona\n"
                    + "JOIN \"Usuarios\" AS u ON u.id_persona=p.id_persona\n"
                    + "JOIN \"Carreras\" AS crr ON crr.id_carrera = m.id_carrera\n"
                    + "JOIN \"PeriodoLectivo\" AS pr ON pr.id_carrera=crr.id_carrera\n"
                    + "WHERE usu_username=? AND crr.id_carrera=?\n"
                    + "AND pr.prd_lectivo_fecha_fin > current_date\n"
                    + "EXCEPT\n"
                    + "SELECT  DISTINCT m.id_materia, m.materia_nombre, m.materia_horas_practicas, m.materia_horas_auto_estudio, m.materia_horas_presencial \n"
                    + "FROM \"Materias\" AS m\n"
                    + "JOIN \"Cursos\" AS crs ON m.id_materia=crs.id_materia\n"
                    + "JOIN \"Docentes\" AS d ON d.id_docente=crs.id_docente\n"
                    + "JOIN \"Personas\" AS p ON p.id_persona=d.id_persona\n"
                    + "JOIN \"Usuarios\" AS u ON u.id_persona=p.id_persona\n"
                    + "JOIN \"Carreras\" AS crr ON crr.id_carrera = m.id_carrera\n"
                    + "JOIN \"Silabo\" AS s ON s.id_materia=m.id_materia\n"
                    + "JOIN \"PeriodoLectivo\" AS pr ON pr.id_carrera=crr.id_carrera\n"
                    + "WHERE usu_username=? AND crr.id_carrera=?\n"
                    + "AND pr.prd_lectivo_fecha_fin > current_date");

            st.setString(1, clave[0]);
            st.setInt(2, Integer.parseInt(clave[1]));
            st.setString(3, clave[0]);
            st.setInt(4, Integer.parseInt(clave[1]));

            ResultSet rs = st.executeQuery();
            System.out.println(st);
            while (rs.next()) {

                MateriaMD tmp = new MateriaMD();

                tmp.setId(rs.getInt(1));
                tmp.setNombre(rs.getString(2));
                tmp.setHorasDocencia(rs.getInt(3));
                tmp.setHorasPracticas(rs.getInt(4));
                tmp.setHorasAutoEstudio(rs.getInt(5));

                materias.add(tmp);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MateriasBDS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return materias;

    }

}
