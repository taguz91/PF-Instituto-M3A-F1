/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.periodoSilabos;

import java.time.LocalDate;


/**
 *
 * @author Andres Ullauri
 */
public class PeriodoIngresoSilabosMD {

    private Integer idPeriodo;

    private LocalDate fechaInicioPeriodo;

    private LocalDate fechaFinPeriodo;

    private boolean estadoPeriodo;

    private Integer idPeriodoCarrera;

    public PeriodoIngresoSilabosMD() {
    }

    public PeriodoIngresoSilabosMD(Integer idPeriodo, LocalDate fechaInicioPeriodo, LocalDate fechaFinPeriodo, boolean estadoPeriodo, Integer idPeriodoCarrera) {
        this.idPeriodo = idPeriodo;
        this.fechaInicioPeriodo = fechaInicioPeriodo;
        this.fechaFinPeriodo = fechaFinPeriodo;
        this.estadoPeriodo = estadoPeriodo;
        this.idPeriodoCarrera = idPeriodoCarrera;
    }

    public Integer getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(Integer idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public LocalDate getFechaInicioPeriodo() {
        return fechaInicioPeriodo;
    }

    public void setFechaInicioPeriodo(LocalDate fechaInicioPeriodo) {
        this.fechaInicioPeriodo = fechaInicioPeriodo;
    }

    public LocalDate getFechaFinPeriodo() {
        return fechaFinPeriodo;
    }

    public void setFechaFinPeriodo(LocalDate fechaFinPeriodo) {
        this.fechaFinPeriodo = fechaFinPeriodo;
    }

    public boolean isEstadoPeriodo() {
        return estadoPeriodo;
    }

    public void setEstadoPeriodo(boolean estadoPeriodo) {
        this.estadoPeriodo = estadoPeriodo;
    }

    public Integer getIdPeriodoCarrera() {
        return idPeriodoCarrera;
    }

    public void setIdPeriodoCarrera(Integer idPeriodoCarrera) {
        this.idPeriodoCarrera = idPeriodoCarrera;
    }
    
   

}
