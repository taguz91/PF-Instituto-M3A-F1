package modelo.alumno;

import java.time.LocalDate;
import modelo.periodolectivo.PeriodoLectivoMD;

/**
 *
 * @author gus
 */
public class Egresado {

    private int id;
    private AlumnoCarreraMD almnCarrera;
    private PeriodoLectivoMD periodo;
    private LocalDate fechaEgreso;
    private boolean graduado;
    private LocalDate fechaGraduacion;
    private boolean trabajoTitulacion = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AlumnoCarreraMD getAlmnCarrera() {
        return almnCarrera;
    }

    public void setAlmnCarrera(AlumnoCarreraMD almnCarrera) {
        this.almnCarrera = almnCarrera;
    }

    public PeriodoLectivoMD getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoLectivoMD periodo) {
        this.periodo = periodo;
    }

    public LocalDate getFechaEgreso() {
        return fechaEgreso;
    }

    public void setFechaEgreso(LocalDate fechaEgreso) {
        this.fechaEgreso = fechaEgreso;
    }

    public boolean isGraduado() {
        return graduado;
    }

    public void setGraduado(boolean graduado) {
        this.graduado = graduado;
    }

    public LocalDate getFechaGraduacion() {
        return fechaGraduacion;
    }

    public void setFechaGraduacion(LocalDate fechaGraduacion) {
        this.fechaGraduacion = fechaGraduacion;
    }

    public boolean isTrabajoTitulacion() {
        return trabajoTitulacion;
    }

    public void setTrabajoTitulacion(boolean trabajoTitulacion) {
        this.trabajoTitulacion = trabajoTitulacion;
    }

}
