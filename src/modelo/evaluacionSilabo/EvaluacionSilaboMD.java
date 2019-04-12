/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.evaluacionSilabo;

import java.time.LocalDate;

import modelo.tipoActividad.TipoActividadMD;
import modelo.unidadSilabo.UnidadSilaboMD;

/**
 * 
 * @author Andres Ullauri
 */
public class EvaluacionSilaboMD {

    private String indicador;


    private Integer idEvaluacion;
    

    private String instrumento;

    private double valoracion;


    private LocalDate fechaEnvio;

    private LocalDate fechaPresentacion;

    private TipoActividadMD idTipoActividad;

    private UnidadSilaboMD idUnidad;

    public EvaluacionSilaboMD() {
        
       
        this.idTipoActividad = new TipoActividadMD();
        this.idUnidad = new UnidadSilaboMD();
    }
    
    
    

    public EvaluacionSilaboMD(String indicador, String instrumento, double valoracion, LocalDate fechaEnvio, LocalDate fechaPresentacion, TipoActividadMD idTipoActividad, UnidadSilaboMD idUnidad) {
        this.indicador = indicador;
        this.instrumento = instrumento;
        this.valoracion = valoracion;
        this.fechaEnvio = fechaEnvio;
        this.fechaPresentacion = fechaPresentacion;
        this.idTipoActividad = idTipoActividad;
        this.idUnidad = idUnidad;
    }

    public String getIndicador() {
        return indicador;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }

    public Integer getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(Integer idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public String getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(String instrumento) {
        this.instrumento = instrumento;
    }

    public double getValoracion() {
        return valoracion;
    }

    public void setValoracion(double valoracion) {
        this.valoracion = valoracion;
    }

    public LocalDate getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDate fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public LocalDate getFechaPresentacion() {
        return fechaPresentacion;
    }

    public void setFechaPresentacion(LocalDate fechaPresentacion) {
        this.fechaPresentacion = fechaPresentacion;
    }

    public TipoActividadMD getIdTipoActividad() {
        return idTipoActividad;
    }

    public void setIdTipoActividad(TipoActividadMD idTipoActividad) {
        this.idTipoActividad = idTipoActividad;
    }

    public UnidadSilaboMD getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(UnidadSilaboMD idUnidad) {
        this.idUnidad = idUnidad;
    }
    
    
    
    
    
}
