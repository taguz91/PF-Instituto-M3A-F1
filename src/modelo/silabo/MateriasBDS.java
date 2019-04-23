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

    public MateriasBDS() {
    }

    public MateriasBDS(int id, CarreraMD carrera, EjeFormacionMD eje, String codigo, String nombre, int ciclo, int creditos, char tipo, String categoria, char tipoAcreditacion, int horasDocencia, int horasPracticas, int horasAutoEstudio, int horasPresenciales, int totalHoras, String objetivo, String descripcion, String objetivoespecifico, String organizacioncurricular, String materiacampoformacion, boolean materiaNucleo, boolean materiaActiva) {
        super(id, carrera, eje, codigo, nombre, ciclo, creditos, tipo, categoria, tipoAcreditacion, horasDocencia, horasPracticas, horasAutoEstudio, horasPresenciales, totalHoras, objetivo, descripcion, objetivoespecifico, organizacioncurricular, materiacampoformacion, materiaNucleo, materiaActiva);
    }

    public static List<MateriaMD> consultar(ConexionBD conexion, String[] clave) {

        List<MateriaMD> materias = new ArrayList<>();
        try {

            PreparedStatement st = conexion.getCon().prepareStatement("SELECT  DISTINCT m.id_materia, m.materia_nombre, m.materia_horas_docencia, m.materia_horas_practicas, m.materia_horas_auto_estudio \n"
                    + "FROM \"Materias\" AS m\n"
                    + "JOIN \"Cursos\" AS crs ON m.id_materia=crs.id_materia\n"
                    + "JOIN \"Docentes\" AS d ON d.id_docente=crs.id_docente\n"
                    + "JOIN \"Personas\" AS p ON p.id_persona=d.id_persona\n"
                    + "JOIN \"Usuarios\" AS u ON u.id_persona=p.id_persona\n"
                    + "JOIN \"Carreras\" AS crr ON crr.id_carrera = m.id_carrera\n"
                    + "JOIN \"PeriodoLectivo\" AS pr ON pr.id_carrera=crr.id_carrera\n"
                    + "WHERE usu_username=? AND crr.id_carrera=?\n"
                    + "AND crs.id_prd_lectivo=? "
                    + "EXCEPT\n"
                    + "SELECT  DISTINCT m.id_materia, m.materia_nombre, m.materia_horas_docencia, m.materia_horas_practicas, m.materia_horas_auto_estudio \n"
                    + "FROM \"Materias\" AS m\n"
                    + "JOIN \"Cursos\" AS crs ON m.id_materia=crs.id_materia\n"
                    + "JOIN \"Docentes\" AS d ON d.id_docente=crs.id_docente\n"
                    + "JOIN \"Personas\" AS p ON p.id_persona=d.id_persona\n"
                    + "JOIN \"Usuarios\" AS u ON u.id_persona=p.id_persona\n"
                    + "JOIN \"Carreras\" AS crr ON crr.id_carrera = m.id_carrera\n"
                    + "JOIN \"Silabo\" AS s ON s.id_materia=m.id_materia\n"
                    + "JOIN \"PeriodoLectivo\" AS pr ON pr.id_carrera=crr.id_carrera\n"
                    + "WHERE usu_username=? AND crr.id_carrera=?\n"
                    + "AND s.id_prd_lectivo=?");

            st.setString(1, clave[0]);
            st.setInt(2, Integer.parseInt(clave[1]));
            st.setInt(3, Integer.parseInt(clave[2]));
            st.setString(4, clave[0]);
            st.setInt(5, Integer.parseInt(clave[1]));
            st.setInt(6, Integer.parseInt(clave[2]));

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
    public static List<MateriaMD> consultarSilabo2(ConexionBD conexion, String carrera,int id_persona ) {

        List<MateriaMD> materias = new ArrayList<>();
        try {

            PreparedStatement st = conexion.getCon().prepareStatement("SELECT DISTINCT id_silabo,\n"
                    + "m.materia_nombre\n"
                    + "FROM \"Silabo\" AS s\n"
                    + "JOIN \"Materias\" AS m ON s.id_materia=m.id_materia\n"
                    + "JOIN \"PeriodoLectivo\" AS pr ON pr.id_prd_lectivo=s.id_prd_lectivo\n"
                    + "JOIN \"Carreras\" AS crr ON crr.id_carrera = m.id_carrera\n"
                    + "JOIN \"Cursos\" AS cr ON cr.id_materia=m.id_materia\n"
                    + "JOIN \"Docentes\" AS d ON d.id_docente= cr.id_docente\n"
                    + "JOIN \"Personas\" AS p ON d.id_persona=p.id_persona\n"
                    + "WHERE crr.carrera_nombre=?\n"
                    + "AND p.id_persona=?");

            st.setString(1, carrera);
            st.setInt(2, id_persona);

            System.out.println(st);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
               MateriaMD tmp=new MateriaMD();
               tmp.setNombre(rs.getString(2));

                materias.add(tmp);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return materias;
    }
}
