/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.unidadSilabo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import modelo.evaluacionSilabo.EvaluacionSilaboMD;

import modelo.silabo.SilaboMD;

/**
 *
 * @author Andres Ullauri
 */
public class UnidadSilaboMD implements Serializable {

    private Integer idUnidad;

    private int numeroUnidad;

    private String tituloUnidad;

    private LocalDate fechaInicioUnidad;

    private LocalDate fechaFinUnidad;

    private String objetivoEspecificoUnidad;

    private String resultadosAprendizajeUnidad;

    private String contenidosUnidad;

    private double horasDocenciaUnidad;

    private double horasPracticaUnidad;

    private double horasAutonomoUnidad;

    private List<EvaluacionSilaboMD> evaluacionSilaboList;

    private SilaboMD idSilabo;
    
    private boolean bandera;

    public UnidadSilaboMD() {
        this.idSilabo=new SilaboMD();
        this.bandera=false;
    }

    public UnidadSilaboMD( int numeroUnidad, String tituloUnidad, LocalDate fechaInicioUnidad, LocalDate fechaFinUnidad, String objetivoEspecificoUnidad, String resultadosAprendizajeUnidad, String contenidosUnidad, int horasDocenciaUnidad, int horasPracticaUnidad, int horasAutonomoUnidad) {
        
        this.numeroUnidad = numeroUnidad;
        this.tituloUnidad = tituloUnidad;
        this.fechaInicioUnidad = fechaInicioUnidad;
        this.fechaFinUnidad = fechaFinUnidad;
        this.objetivoEspecificoUnidad = objetivoEspecificoUnidad;
        this.resultadosAprendizajeUnidad = resultadosAprendizajeUnidad;
        this.contenidosUnidad = contenidosUnidad;
        this.horasDocenciaUnidad = horasDocenciaUnidad;
        this.horasPracticaUnidad = horasPracticaUnidad;
        this.horasAutonomoUnidad = horasAutonomoUnidad;
        this.bandera=false;
    }

    public boolean isBandera() {
        return bandera;
    }

    public void setBandera(boolean bandera) {
        this.bandera = bandera;
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

    public String getTituloUnidad() {
        return tituloUnidad;
    }

    public void setTituloUnidad(String tituloUnidad) {
        this.tituloUnidad = tituloUnidad;
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
    
    public void setHorasDocenciaUnidad(int horasDocenciaUnidad) {
        this.horasDocenciaUnidad = horasDocenciaUnidad;
    }

    public double getHorasDocenciaUnidad() {
        return horasDocenciaUnidad;
    }

    public void setHorasDocenciaUnidad(double horasDocenciaUnidad) {
        this.horasDocenciaUnidad = horasDocenciaUnidad;
    }

    public double getHorasPracticaUnidad() {
        return horasPracticaUnidad;
    }

    public void setHorasPracticaUnidad(double horasPracticaUnidad) {
        this.horasPracticaUnidad = horasPracticaUnidad;
    }

    public double getHorasAutonomoUnidad() {
        return horasAutonomoUnidad;
    }

    public void setHorasAutonomoUnidad(double horasAutonomoUnidad) {
        this.horasAutonomoUnidad = horasAutonomoUnidad;
    }


    public void setHorasPracticaUnidad(int horasPracticaUnidad) {
        this.horasPracticaUnidad = horasPracticaUnidad;
    }


    public void setHorasAutonomoUnidad(int horasAutonomoUnidad) {
        this.horasAutonomoUnidad = horasAutonomoUnidad;
    }

    public List<EvaluacionSilaboMD> getEvaluacionSilaboList() {
        return evaluacionSilaboList;
    }

    public void setEvaluacionSilaboList(List<EvaluacionSilaboMD> evaluacionSilaboList) {
        this.evaluacionSilaboList = evaluacionSilaboList;
    }

    public SilaboMD getIdSilabo() {
        return idSilabo;
    }

    public void setIdSilabo(SilaboMD idSilabo) {
        this.idSilabo = idSilabo;
    }

    
}
