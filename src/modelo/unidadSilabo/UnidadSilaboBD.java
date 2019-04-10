/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.unidadSilabo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConexionBD;
import modelo.evaluacionSilabo.EvaluacionSilabo;
import modelo.silabo.Silabo;
import modelo.silabo.SilaboMD;

/**
 *
 * @author Andres Ullauri
 */
public class UnidadSilaboBD extends UnidadSilaboMD {

    private ConexionBD conexion;

    public UnidadSilaboBD(ConexionBD conexion) {
        this.conexion = conexion;
    }

    public UnidadSilaboBD(ConexionBD conexion, int numeroUnidad, String tituloUnidad, LocalDate fechaInicioUnidad, LocalDate fechaFinUnidad, String objetivoEspecificoUnidad, String resultadosAprendizajeUnidad, String contenidosUnidad, int horasDocenciaUnidad, int horasPracticaUnidad, int horasAutonomoUnidad) {
        super(numeroUnidad, tituloUnidad, fechaInicioUnidad, fechaFinUnidad, objetivoEspecificoUnidad, resultadosAprendizajeUnidad, contenidosUnidad, horasDocenciaUnidad, horasPracticaUnidad, horasAutonomoUnidad);
        this.conexion = conexion;
    }


    

    public void insertar(UnidadSilaboMD u) {

        try {
            PreparedStatement st = conexion.getCon().prepareStatement("INSERT INTO public.\"UnidadSilabo\"(\n"
                    + "	 numero_unidad, objetivo_especifico_unidad, resultados_aprendizaje_unidad, contenidos_unidad, fecha_inicio_unidad, fecha_fin_unidad, horas_docencia_unidad, horas_practica_unidad, horas_autonomo_unidad, id_silabo, titulo_unidad)\n"
                    + "	VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, (SELECT MAX(id_silabo) FROM \"Silabo\"), ?)");

            st.setInt(1, u.getNumeroUnidad());
            st.setString(2, u.getObjetivoEspecificoUnidad());
            st.setString(3, u.getResultadosAprendizajeUnidad());
            st.setString(4, u.getContenidosUnidad());
            st.setDate(5, java.sql.Date.valueOf(u.getFechaInicioUnidad()));
            st.setDate(6, java.sql.Date.valueOf(u.getFechaFinUnidad()));
            st.setInt(7, u.getHorasDocenciaUnidad());
            st.setInt(8, u.getHorasPracticaUnidad());
            st.setInt(9, u.getHorasAutonomoUnidad());
           
            st.setString(10, u.getTituloUnidad());
            
            st.executeUpdate();
            System.out.println(st);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(UnidadSilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
