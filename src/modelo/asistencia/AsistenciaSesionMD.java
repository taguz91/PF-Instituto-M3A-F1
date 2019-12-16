package modelo.asistencia;

import java.time.LocalDate;

/**
 *
 * @author gus
 */
public class AsistenciaSesionMD {

    private LocalDate prdFechaInicio;
    private LocalDate prdFechaFin;
    private int diaInicio;
    private int diaFin;

    public LocalDate getPrdFechaInicio() {
        return prdFechaInicio;
    }

    public void setPrdFechaInicio(LocalDate prdFechaInicio) {
        this.prdFechaInicio = prdFechaInicio;
    }

    public LocalDate getPrdFechaFin() {
        return prdFechaFin;
    }

    public void setPrdFechaFin(LocalDate prdFechaFin) {
        this.prdFechaFin = prdFechaFin;
    }

    public int getDiaInicio() {
        return diaInicio;
    }

    public void setDiaInicio(int diaInicio) {
        this.diaInicio = diaInicio;
    }

    public int getDiaFin() {
        return diaFin;
    }

    public void setDiaFin(int diaFin) {
        this.diaFin = diaFin;
    }

}
