package modelo.alumno;

import java.time.LocalDate;
import modelo.periodolectivo.PeriodoLectivoMD;

/**
 *
 * @author gus
 */
public class Retirado {

    private int id;
    private LocalDate fechaRetiro;
    private String motivo;
    // Modelos
    private AlumnoCarreraMD almnCarrera;
    private PeriodoLectivoMD periodo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFechaRetiro() {
        return fechaRetiro;
    }

    public void setFechaRetiro(LocalDate fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
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

}
