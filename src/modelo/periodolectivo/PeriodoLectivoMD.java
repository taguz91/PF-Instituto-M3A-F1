package modelo.periodolectivo;

import java.time.LocalDate;
import java.util.function.Consumer;
import modelo.carrera.CarreraMD;
import modelo.persona.DocenteMD;

public class PeriodoLectivoMD {

    private int id;
    private String nombre, observacion;
    private boolean activo, estado;
    private LocalDate fechaInicio, fechaFin, fechaFinClases;
    private int NumSemanas;
    private DocenteMD docente;
    

    private int NumDias;
    //Debemos arreglar esot 
    private CarreraMD carrera;

    public int getID() {
        return id;
    }

    public PeriodoLectivoMD setID(int id) {
        this.id = id;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public PeriodoLectivoMD setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public String getObservacion() {
        return observacion;
    }

    public PeriodoLectivoMD setObservacion(String observacion) {
        this.observacion = observacion;
        return this;
    }

    public boolean isActivo() {
        return activo;
    }

    public PeriodoLectivoMD setActivo(boolean activo) {
        this.activo = activo;
        return this;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public PeriodoLectivoMD setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
        return this;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public PeriodoLectivoMD setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
        return this;
    }

    public CarreraMD getCarrera() {
        return carrera;
    }

    public PeriodoLectivoMD setCarrera(CarreraMD carrera) {
        this.carrera = carrera;
        return this;
    }

    public boolean isEstado() {
        return estado;
    }

    public PeriodoLectivoMD setEstado(boolean estado) {
        this.estado = estado;
        return this;
    }

    public int getNumSemanas() {
        return NumSemanas;
    }

    public PeriodoLectivoMD setNumSemanas(int NumSemanas) {
        this.NumSemanas = NumSemanas;
        return this;
    }

    public int getNumDias() {
        return NumDias;
    }

    public PeriodoLectivoMD setNumDias(int NumDias) {
        this.NumDias = NumDias;
        return this;
    }

    public PeriodoLectivoMD periodoBuilder(Consumer<PeriodoLectivoMD> builder) {
        builder.accept(this);
        return this;
    }

    public PeriodoLectivoMD set(Consumer<PeriodoLectivoMD> consumer) {
        consumer.accept(this);
        return this;
    }

    public LocalDate getFechaFinClases() {
        return fechaFinClases;
    }

    public void setFechaFinClases(LocalDate fechaFinClases) {
        this.fechaFinClases = fechaFinClases;
    }

    public DocenteMD getDocente() {
        return docente;
    }

    public void setDocente(DocenteMD docente) {
        this.docente = docente;
    }

}
