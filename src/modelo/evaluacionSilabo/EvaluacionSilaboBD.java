/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.evaluacionSilabo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    public EvaluacionSilaboBD(ConexionBD conexion, Integer idEvaluacion, String indicador, String instrumento, double valoracion, LocalDate fechaEnvio, LocalDate fechaPresentacion, TipoActividadMD idTipoActividad, UnidadSilaboMD idUnidad) {
        super(idEvaluacion, indicador, instrumento, valoracion, fechaEnvio, fechaPresentacion, idTipoActividad, idUnidad);
        this.conexion = conexion;
    }

    public void insertar(EvaluacionSilaboMD e, int iu) {

        try {
            PreparedStatement st = conexion.getCon().prepareStatement("INSERT INTO public.\"EvaluacionSilabo\"(\n"
                    + "	 id_unidad, id_tipo_actividad, instrumento, valoracion, fecha_envio, fecha_presentacion, indicador)\n"
                    + "	VALUES ( "+iu+", ?, ?, ?, ?, ?, ?)");

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
    
    // PASADO 
    public static List<EvaluacionSilaboMD> recuperarEvaluaciones(ConexionBD conexion, int aguja) {
        List<EvaluacionSilaboMD> evaluaciones = new ArrayList<>();
        try {

            PreparedStatement st = conexion.getCon().prepareStatement("SELECT id_evaluacion,indicador,id_tipo_actividad,instrumento,valoracion,fecha_envio,fecha_presentacion,numero_unidad\n"
                    + "FROM \"EvaluacionSilabo\",\"Silabo\",\"UnidadSilabo\"\n"
                    + "WHERE \"EvaluacionSilabo\".id_unidad=\"UnidadSilabo\".id_unidad\n"
                    + "AND \"UnidadSilabo\".id_silabo=\"Silabo\".id_silabo\n"
                    + "AND \"Silabo\".id_silabo=" + aguja + "");
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                EvaluacionSilaboMD e = new EvaluacionSilaboMD();
                e.setIdEvaluacion(rs.getInt(1));
                e.setIndicador(rs.getString(2));
                e.getIdTipoActividad().setIdTipoActividad(rs.getInt(3));
                e.setInstrumento(rs.getString(4));
                e.setValoracion(rs.getDouble(5));
                e.setFechaEnvio(rs.getDate(6).toLocalDate());
                e.setFechaPresentacion(rs.getDate(7).toLocalDate());
                e.getIdUnidad().setNumeroUnidad(rs.getInt(8));
                //e.getIdUnidad().;

                evaluaciones.add(e);
            }

        } catch (SQLException ex) {
            Logger.getLogger(EvaluacionSilaboBD.class.getName()).log(Level.SEVERE, null, ex);

        }
        return evaluaciones;
    }

    public static List<EvaluacionSilaboMD> recuperarEvaluacionesUnidadSilabo(ConexionBD conexion, int silabo, int numero_unidad) {
        List<EvaluacionSilaboMD> evaluaciones = new ArrayList<>();
        try {

            PreparedStatement st = conexion.getCon().prepareStatement("select id_evaluacion,instrumento\n"
                    + "                    FROM \"EvaluacionSilabo\",\"Silabo\",\"UnidadSilabo\"\n"
                    + "                    WHERE \"EvaluacionSilabo\".id_unidad=\"UnidadSilabo\".id_unidad\n"
                    + "                    AND \"UnidadSilabo\".id_silabo=\"Silabo\".id_silabo\n"
                    + "                    AND \"Silabo\".id_silabo=? AND \"UnidadSilabo\".numero_unidad=?");
            st.setInt(1, silabo);
            st.setInt(2, numero_unidad);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                EvaluacionSilaboMD e = new EvaluacionSilaboMD();
                e.setIdEvaluacion(rs.getInt(1));
                e.setInstrumento(rs.getString(2));

                evaluaciones.add(e);
            }

        } catch (SQLException ex) {
            Logger.getLogger(EvaluacionSilaboBD.class.getName()).log(Level.SEVERE, null, ex);

        }
        return evaluaciones;
    }

    public static List<EvaluacionSilaboMD> recuperar_id_Unidad_plan_de_clase(ConexionBD conexion, int id_unidad) {
        List<EvaluacionSilaboMD> evaluaciones = new ArrayList<>();
        try {

            PreparedStatement st = conexion.getCon().prepareStatement("select es.id_evaluacion from \"EvaluacionSilabo\" es \n"
                    + "join \"TipoActividad\" t on es.id_tipo_actividad=t.id_tipo_actividad where es.id_tipo_actividad=4 AND es.id_unidad=?");
            st.setInt(1, id_unidad);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                EvaluacionSilaboMD e = new EvaluacionSilaboMD();
                e.setIdEvaluacion(rs.getInt(1));

                evaluaciones.add(e);
            }

        } catch (SQLException ex) {
            Logger.getLogger(EvaluacionSilaboBD.class.getName()).log(Level.SEVERE, null, ex);

        }
        return evaluaciones;
    }

}
