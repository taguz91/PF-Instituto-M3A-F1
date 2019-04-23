package modelo.alumno;

import java.time.LocalDateTime;

/**
 *
 * @author Johnny
 */
public class AlumnoCursoRetiradoMD {
    
    private int id; 
    private AlumnoCursoMD alumnoCurso; 
    private LocalDateTime fecha; 
    private String observacion; 
    private boolean activo; 

    public AlumnoCursoRetiradoMD() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AlumnoCursoMD getAlumnoCurso() {
        return alumnoCurso;
    }

    public void setAlumnoCurso(AlumnoCursoMD alumnoCurso) {
        this.alumnoCurso = alumnoCurso;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
}
