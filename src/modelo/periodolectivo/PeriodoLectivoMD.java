package modelo.periodolectivo;

import java.time.LocalDate;
import modelo.carrera.CarreraMD;

public class PeriodoLectivoMD {

    private int id;
    private String nombre, observacion;
    private boolean activo, estado;
    private LocalDate fechaInicio, fechaFin;
    private int NumSemanas;

    private int NumDias;
    //Debemos arreglar esot 
    private CarreraMD carrera;

    public PeriodoLectivoMD() {
    }

    public PeriodoLectivoMD(int id_PerioLectivo, String nombre_PerLectivo, String observacion_PerLectivo, boolean activo_PerLectivo, boolean estado_PerLectivo, LocalDate fecha_Inicio, LocalDate fecha_Fin, CarreraMD carrera) {
        this.id = id_PerioLectivo;
        this.nombre = nombre_PerLectivo;
        this.observacion = observacion_PerLectivo;
        this.activo = activo_PerLectivo;
        this.estado = estado_PerLectivo;
        this.fechaInicio = fecha_Inicio;
        this.fechaFin = fecha_Fin;
        this.carrera = carrera;
    }

    public int getId() {
        return id;
    }

    public PeriodoLectivoMD setPeriodo(int id_PerioLectivo) {
        this.id = id_PerioLectivo;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion_PerLectivo) {
        this.observacion = observacion_PerLectivo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo_PerLectivo) {
        this.activo = activo_PerLectivo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fecha_Fin) {
        this.fechaFin = fecha_Fin;
    }

    public CarreraMD getCarrera() {
        return carrera;
    }

    public void setCarrera(CarreraMD carrera) {
        this.carrera = carrera;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado_PerLectivo(boolean estado_PerLectivo) {
        this.estado = estado_PerLectivo;
    }

    public int getNumSemanas() {
        return NumSemanas;
    }

    public void setNumSemanas(int NumSemanas) {
        this.NumSemanas = NumSemanas;
    }

    public int getNumDias() {
        return NumDias;
    }

    public void setNumDias(int NumDias) {
        this.NumDias = NumDias;
    }

    

    public PeriodoLectivoMD get() {
        return this;
    }

}
