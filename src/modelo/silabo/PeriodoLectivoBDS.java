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
import modelo.periodolectivo.PeriodoLectivoMD;

/**
 *
 * @author Andres Ullauri
 */
public class PeriodoLectivoBDS extends PeriodoLectivoMD {

    private ConexionBD conexion;

    public PeriodoLectivoBDS(ConexionBD conexion) {
        this.conexion = conexion;
    }

    public PeriodoLectivoBDS(ConexionBD conexion, int id_PerioLectivo, String nombre_PerLectivo, String observacion_PerLectivo, boolean activo_PerLectivo, boolean estado_PerLectivo, LocalDate fecha_Inicio, LocalDate fecha_Fin, CarreraMD carrera) {
        super(id_PerioLectivo, nombre_PerLectivo, observacion_PerLectivo, activo_PerLectivo, estado_PerLectivo, fecha_Inicio, fecha_Fin, carrera);
        this.conexion = conexion;
    }

    public static List<PeriodoLectivoMD> consultar(ConexionBD conexion, int clave) {

        List<PeriodoLectivoMD> periodos = new ArrayList<>();
        try {

            PreparedStatement st = conexion.getCon().prepareStatement("SELECT p.id_prd_lectivo, p.prd_lectivo_nombre, p.prd_lectivo_fecha_inicio, p.prd_lectivo_fecha_fin\n"
                    + "FROM \"PeriodoLectivo\" AS p\n"
                    + "JOIN \"Carreras\" AS c ON c.id_carrera=p.id_carrera\n"
                    + "WHERE c.id_carrera=?  AND  p.prd_lectivo_fecha_inicio>='2018-11-12'");

            st.setInt(1, clave);

            ResultSet rs = st.executeQuery();

            System.out.println(st);
            while (rs.next()) {

                PeriodoLectivoMD tmp = new PeriodoLectivoMD();

                tmp.setId_PerioLectivo(rs.getInt(1));
                tmp.setNombre_PerLectivo(rs.getString(2));
                tmp.setFecha_Inicio(rs.getDate(3).toLocalDate());
                tmp.setFecha_Fin(rs.getDate(4).toLocalDate());

                periodos.add(tmp);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PeriodoLectivoBDS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return periodos;

    }

}
