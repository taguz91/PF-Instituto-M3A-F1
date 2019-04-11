/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.evaluacionSilabo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConexionBD;

import modelo.tipoActividad.TipoActividadMD;

import modelo.unidadSilabo.UnidadSilaboMD;

/**
 *
 * @author Andres Ullauri
 */
public class EvaluacionSilaboBD extends EvaluacionSilaboMD {

    private ConexionBD conexion;

    public EvaluacionSilaboBD(ConexionBD conexion) {
        this.conexion = conexion;
    }

    public EvaluacionSilaboBD(ConexionBD conexion, String indicador, String instrumento, double valoracion, LocalDate fechaEnvio, LocalDate fechaPresentacion, TipoActividadMD idTipoActividad, UnidadSilaboMD idUnidad) {
        super(indicador, instrumento, valoracion, fechaEnvio, fechaPresentacion, idTipoActividad, idUnidad);
        this.conexion = conexion;
    }

    public void insertar(EvaluacionSilaboMD e) {

        try {
            PreparedStatement st = conexion.getCon().prepareStatement("INSERT INTO public.\"EvaluacionSilabo\"(\n"
                    + "	 id_unidad, id_tipo_actividad, instrumento, valoracion, fecha_envio, fecha_presentacion, indicador)\n"
                    + "	VALUES ( (SELECT MAX(id_unidad) FROM \"UnidadSilabo\"), ?, ?, ?, ?, ?, ?)");

            st.setInt(1, e.getIdTipoActividad().getIdTipoActividad());
            st.setString(2, e.getInstrumento());

            st.setDouble(3, e.getValoracion());
            st.setDate(4, java.sql.Date.valueOf(e.getFechaEnvio()));
            st.setDate(5, java.sql.Date.valueOf(e.getFechaPresentacion()));
            st.setString(6, e.getIndicador());
            st.executeUpdate();
            System.out.println(st);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(EvaluacionSilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
