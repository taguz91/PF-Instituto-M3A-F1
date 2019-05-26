/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.asistenciaAlumnos;

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

    public AsistenciaMD(int id, LocalDate fechaAsistencia, int numeroFaltas, String observaciones, AlumnoCursoMD alumnoCurso) {
        this.id = id;
        this.fechaAsistencia = fechaAsistencia;
        this.numeroFaltas = numeroFaltas;
        this.observaciones = observaciones;
        this.alumnoCurso = alumnoCurso;
    }

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

    @Override
    public String toString() {
        return "asistenciaMD{" + "id_asistencia=" + id + ", fecha_asistencia=" + fechaAsistencia + ", numero_faltas=" + numeroFaltas + ", observaciones=" + observaciones + ", alumnoCurso=" + alumnoCurso + '}';
    }

}
