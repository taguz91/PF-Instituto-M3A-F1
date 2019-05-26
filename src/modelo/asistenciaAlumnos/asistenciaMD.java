/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.asistenciaAlumnos;

import modelo.alumno.AlumnoCursoMD;

/**
 *
 * @author Yani
 */
public class asistenciaMD {
    
    private int id_asistencia;
    private int fecha_asistencia;  //No recuerdo cual era la variable para la fecha xD
    private int numero_faltas;
    private String observaciones;
    private AlumnoCursoMD alumnoCurso;

    public asistenciaMD(int id_asistencia, int fecha_asistencia, int numero_faltas, String observaciones, AlumnoCursoMD alumnoCurso) {
        this.id_asistencia = id_asistencia;
        this.fecha_asistencia = fecha_asistencia;
        this.numero_faltas = numero_faltas;
        this.observaciones = observaciones;
        this.alumnoCurso = alumnoCurso;
    }

    public asistenciaMD() {
    }

    public int getId_asistencia() {
        return id_asistencia;
    }

    public void setId_asistencia(int id_asistencia) {
        this.id_asistencia = id_asistencia;
    }

    public int getFecha_asistencia() {
        return fecha_asistencia;
    }

    public void setFecha_asistencia(int fecha_asistencia) {
        this.fecha_asistencia = fecha_asistencia;
    }

    public int getNumero_faltas() {
        return numero_faltas;
    }

    public void setNumero_faltas(int numero_faltas) {
        this.numero_faltas = numero_faltas;
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
        return "asistenciaMD{" + "id_asistencia=" + id_asistencia + ", fecha_asistencia=" + fecha_asistencia + ", numero_faltas=" + numero_faltas + ", observaciones=" + observaciones + ", alumnoCurso=" + alumnoCurso + '}';
    }

}
