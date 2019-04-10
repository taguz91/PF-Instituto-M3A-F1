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
import modelo.materia.MateriaMD;
import modelo.periodolectivo.PeriodoLectivoMD;

/**
 *
 * @author Andres Ullauri
 */
public class SilaboBD extends SilaboMD {

    private ConexionBD conexion;

    public SilaboBD(ConexionBD conexion) {
        this.conexion = conexion;
    }

    public SilaboBD(ConexionBD conexion, MateriaMD idMateria, PeriodoLectivoMD idPeriodoLectivo) {
        super(idMateria, idPeriodoLectivo);
        this.conexion = conexion;
    }

    public void insertar() {

        try {
            PreparedStatement st = conexion.getCon().prepareStatement("INSERT INTO public.\"Silabo\"(\n"
                    + "	 id_materia, id_prd_lectivo)\n"
                    + "	VALUES (?, ?)");

            st.setInt(1, getIdMateria().getId());
            st.setInt(2, getIdPeriodoLectivo().getId_PerioLectivo());
            st.executeUpdate();
            System.out.println(st);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(SilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static List<SilaboMD> consultar(ConexionBD conexion, String [] clave) {

        List<SilaboMD> silabos = new ArrayList<>();
        try {

            PreparedStatement st = conexion.getCon().prepareStatement("SELECT DISTINCT id_silabo,\n"
                    + "s.id_materia, m.materia_nombre, estado_silabo,\n"
                    + "pr.id_prd_lectivo, pr.prd_lectivo_fecha_inicio, pr.prd_lectivo_fecha_fin\n"
                    + "FROM \"Silabo\" AS s\n"
                    + "JOIN \"Materias\" AS m ON s.id_materia=m.id_materia\n"
                    + "JOIN \"PeriodoLectivo\" AS pr ON pr.id_prd_lectivo=s.id_prd_lectivo\n"
                    + "JOIN \"Carreras\" AS crr ON crr.id_carrera = m.id_carrera\n"
                    + "WHERE crr.carrera_nombre=?\n"
                    + "AND m.materia_nombre ILIKE '%"+clave[1]+"%'");
            
            
            st.setString(1, clave[0]);
            
            System.out.println(st);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {

                SilaboMD tmp = new SilaboMD();
                tmp.setIdSilabo(rs.getInt(1));
                tmp.getIdMateria().setId(rs.getInt(2));
                tmp.getIdMateria().setNombre(rs.getString(3));
                tmp.setEstadoSilabo(rs.getInt(4));
                tmp.getIdPeriodoLectivo().setId_PerioLectivo(rs.getInt(5));
                tmp.getIdPeriodoLectivo().setFecha_Inicio(rs.getDate(6).toLocalDate());
                tmp.getIdPeriodoLectivo().setFecha_Fin(rs.getDate(7).toLocalDate());

                silabos.add(tmp);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return silabos;
    }

    public void eliminar() {

        try {
            PreparedStatement st = conexion.getCon().prepareStatement("DELETE FROM public.\"Silabo\"\n"
                    + "	WHERE id_silabo=?");

            st.setInt(1, getIdSilabo());
            st.executeUpdate();
            System.out.println(st);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(SilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void actualizar() {

        try {
            PreparedStatement st = conexion.getCon().prepareStatement("UPDATE public.\"Silabo\"\n"
                    + "	SET id_materia=?, estado_silabo=?, id_prd_lectivo=?\n"
                    + "	WHERE id_silabo=?");

            st.setInt(1, getIdMateria().getId());
            st.setInt(2, getEstadoSilabo());
            st.setInt(3, getIdPeriodoLectivo().getId_PerioLectivo());
            st.setInt(4, getIdSilabo());
            st.executeUpdate();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(SilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
