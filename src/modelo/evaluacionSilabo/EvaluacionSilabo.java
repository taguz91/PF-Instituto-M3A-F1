/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.evaluacionSilabo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import modelo.tipoActividad.TipoActividad;
import modelo.unidadSilabo.UnidadSilabo;



/**
 * 
 * @author Andres Ullauri
 */
public class EvaluacionSilabo  {


    private String indicador;


    private Integer idEvaluacion;
    

    private String instrumento;

    private double valoracion;


    private LocalDate fechaEnvio;

    private LocalDate fechaPresentacion;

    private TipoActividad idTipoActividad;

    private UnidadSilabo idUnidad;

    public EvaluacionSilabo() {
        this.idUnidad=new UnidadSilabo();
        this.idTipoActividad=new TipoActividad();
    }

    public EvaluacionSilabo(UnidadSilabo idUnidad) {
        this.idUnidad = idUnidad;
    }

   

    public EvaluacionSilabo( Integer idEvaluacion,UnidadSilabo idUnidad,String indicador, TipoActividad idTipoActividad, String instrumento, double valoracion, LocalDate fechaEnvio, LocalDate fechaPresentacion) {
        
        this.idEvaluacion = idEvaluacion;
        this.indicador = indicador;
        this.instrumento = instrumento;
        this.valoracion = valoracion;
        this.fechaEnvio = fechaEnvio;
        this.fechaPresentacion = fechaPresentacion;
        this.idTipoActividad = idTipoActividad;
        this.idUnidad = idUnidad;
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

    public TipoActividad getIdTipoActividad() {
        return idTipoActividad;
    }

    public void setIdTipoActividad(TipoActividad idTipoActividad) {
        this.idTipoActividad = idTipoActividad;
    }

    public UnidadSilabo getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(UnidadSilabo idUnidad) {
        this.idUnidad = idUnidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEvaluacion != null ? idEvaluacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluacionSilabo)) {
            return false;
        }
        EvaluacionSilabo other = (EvaluacionSilabo) object;
        if ((this.idEvaluacion == null && other.idEvaluacion != null) || (this.idEvaluacion != null && !this.idEvaluacion.equals(other.idEvaluacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.EvaluacionSilabo[ idEvaluacion=" + idEvaluacion + " ]";
    }

    public String getIndicador() {
        return indicador;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }

}
