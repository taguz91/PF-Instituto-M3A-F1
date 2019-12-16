package modelo.asistencia;

import java.time.LocalDate;
import modelo.alumno.AlumnoCursoMD;

/**
 *
 * @author Yani
 */
public class AsistenciaMD {

    private int id;
    private LocalDate fechaAsistencia;
    private int numeroFaltas;
    private String observaciones;
    private AlumnoCursoMD alumnoCurso;
    private int num_horas;

    public AsistenciaMD() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFechaAsistencia() {
        return fechaAsistencia;
    }

    public void setFechaAsistencia(LocalDate fechaAsistencia) {
        this.fechaAsistencia = fechaAsistencia;
    }

    public int getNumeroFaltas() {
        return numeroFaltas;
    }

    public void setNumeroFaltas(int numeroFaltas) {
        this.numeroFaltas = numeroFaltas;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public AlumnoCursoMD getAlumnoCurso() {
        return alumnoCurso;
    }

    public void setAlumnoCurso(AlumnoCursoMD alumnoCurso) {
        this.alumnoCurso = alumnoCurso;
    }

    public int getNum_horas() {
        return num_horas;
    }

    public void setNum_horas(int num_horas) {
        this.num_horas = num_horas;
    }

    @Override
    public String toString() {
        return "asistenciaMD{" + "id_asistencia=" + id + ", fecha_asistencia=" + fechaAsistencia + ", numero_faltas=" + numeroFaltas + ", observaciones=" + observaciones + ", alumnoCurso=" + alumnoCurso + '}';
    }

}
