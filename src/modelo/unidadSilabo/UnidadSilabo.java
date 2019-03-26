/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.unidadSilabo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import modelo.evaluacionSilabo.EvaluacionSilabo;
import modelo.silabo.Silabo;



/**
 * 
 * @author Andres Ullauri
 */
public class UnidadSilabo  {


    
    private LocalDate fechaInicioUnidad;
 
    

    
    private LocalDate fechaFinUnidad;

    private static final long serialVersionUID = 1L;

    
    private Integer idUnidad;

    
    private int numeroUnidad;

    
    private String objetivoEspecificoUnidad;

    
    private String resultadosAprendizajeUnidad;

    
    private String contenidosUnidad;

    
    private int horasDocenciaUnidad;

    
    private int horasPracticaUnidad;

    
    private int horasAutonomoUnidad;

    
    private String tituloUnidad;

    
    private List<EvaluacionSilabo> evaluacionSilaboList;

    
    private Silabo idSilabo;

    public UnidadSilabo() {
    }

    public UnidadSilabo(Integer idUnidad) {
        this.idUnidad = idUnidad;
    }

    public UnidadSilabo(Integer idUnidad, int numeroUnidad, String objetivoEspecificoUnidad, String resultadosAprendizajeUnidad, String contenidosUnidad, LocalDate fechaInicioUnidad, LocalDate fechaFinUnidad, int horasDocenciaUnidad, int horasPracticaUnidad, int horasAutonomoUnidad, String tituloUnidad) {
        this.idUnidad = idUnidad;
        this.numeroUnidad = numeroUnidad;
        this.objetivoEspecificoUnidad = objetivoEspecificoUnidad;
        this.resultadosAprendizajeUnidad = resultadosAprendizajeUnidad;
        this.contenidosUnidad = contenidosUnidad;
        this.fechaInicioUnidad = fechaInicioUnidad;
        this.fechaFinUnidad = fechaFinUnidad;
        this.horasDocenciaUnidad = horasDocenciaUnidad;
        this.horasPracticaUnidad = horasPracticaUnidad;
        this.horasAutonomoUnidad = horasAutonomoUnidad;
        this.tituloUnidad = tituloUnidad;
    }

    public UnidadSilabo(int idUnidad, LocalDate fechaInicioUnidad, LocalDate fechaFinUnidad) {
        this.idUnidad = idUnidad;
        this.fechaInicioUnidad = fechaInicioUnidad;
        this.fechaFinUnidad = fechaFinUnidad;
    }

    public Integer getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
    }

    public int getNumeroUnidad() {
        return numeroUnidad;
    }

    public void setNumeroUnidad(int numeroUnidad) {
        this.numeroUnidad = numeroUnidad;
    }

    public String getObjetivoEspecificoUnidad() {
        return objetivoEspecificoUnidad;
    }

    public void setObjetivoEspecificoUnidad(String objetivoEspecificoUnidad) {
        this.objetivoEspecificoUnidad = objetivoEspecificoUnidad;
    }

    public String getResultadosAprendizajeUnidad() {
        return resultadosAprendizajeUnidad;
    }

    public void setResultadosAprendizajeUnidad(String resultadosAprendizajeUnidad) {
        this.resultadosAprendizajeUnidad = resultadosAprendizajeUnidad;
    }

    public String getContenidosUnidad() {
        return contenidosUnidad;
    }

    public void setContenidosUnidad(String contenidosUnidad) {
        this.contenidosUnidad = contenidosUnidad;
    }

    public LocalDate getFechaInicioUnidad() {
        return fechaInicioUnidad;
    }

    public void setFechaInicioUnidad(LocalDate fechaInicioUnidad) {
        this.fechaInicioUnidad = fechaInicioUnidad;
    }

    public LocalDate getFechaFinUnidad() {
        return fechaFinUnidad;
    }

    public void setFechaFinUnidad(LocalDate fechaFinUnidad) {
        this.fechaFinUnidad = fechaFinUnidad;
    }

    public int getHorasDocenciaUnidad() {
        return horasDocenciaUnidad;
    }

    public void setHorasDocenciaUnidad(int horasDocenciaUnidad) {
        this.horasDocenciaUnidad = horasDocenciaUnidad;
    }

    public int getHorasPracticaUnidad() {
        return horasPracticaUnidad;
    }

    public void setHorasPracticaUnidad(int horasPracticaUnidad) {
        this.horasPracticaUnidad = horasPracticaUnidad;
    }

    public int getHorasAutonomoUnidad() {
        return horasAutonomoUnidad;
    }

    public void setHorasAutonomoUnidad(int horasAutonomoUnidad) {
        this.horasAutonomoUnidad = horasAutonomoUnidad;
    }

    public String getTituloUnidad() {
        return tituloUnidad;
    }

    public void setTituloUnidad(String tituloUnidad) {
        this.tituloUnidad = tituloUnidad;
    }

    
    public List<EvaluacionSilabo> getEvaluacionSilaboList() {
        return evaluacionSilaboList;
    }

    public void setEvaluacionSilaboList(List<EvaluacionSilabo> evaluacionSilaboList) {
        this.evaluacionSilaboList = evaluacionSilaboList;
    }

    public Silabo getIdSilabo() {
        return idSilabo;
    }

    public void setIdSilabo(Silabo idSilabo) {
        this.idSilabo = idSilabo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUnidad != null ? idUnidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UnidadSilabo)) {
            return false;
        }
        UnidadSilabo other = (UnidadSilabo) object;
        if ((this.idUnidad == null && other.idUnidad != null) || (this.idUnidad != null && !this.idUnidad.equals(other.idUnidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.UnidadSilabo[ idUnidad=" + idUnidad + " ]";
    }



}
