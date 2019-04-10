/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.tipoActividad;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConexionBD;

/**
 *
 * @author Andres Ullauri
 */
public class TipoActividadBD extends TipoActividadMD {

    private ConexionBD conexion;

    public TipoActividadBD(ConexionBD conexion) {
        this.conexion = conexion;
    }

    public TipoActividadBD(ConexionBD conexion, String nombreTipoActividad, String nombreSubtipoActividad) {
        super(nombreTipoActividad, nombreSubtipoActividad);
        this.conexion = conexion;
    }

    public static List<TipoActividadMD> consultar(ConexionBD conexion) {

        List<TipoActividadMD> actividades = new ArrayList<>();
        try {

            PreparedStatement st = conexion.getCon().prepareStatement("SELECT id_tipo_actividad, nombre_tipo_actividad, nombre_subtipo_actividad\n"
                    + "	FROM public.\"TipoActividad\"");

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                TipoActividadMD tmp = new TipoActividadMD();
                tmp.setIdTipoActividad(rs.getInt(1));
                tmp.setNombreTipoActividad(rs.getString(2));
                tmp.setNombreSubtipoActividad(rs.getString(3));

                actividades.add(tmp);
            }

        } catch (SQLException ex) {
            Logger.getLogger(TipoActividadBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return actividades;
    }

}
