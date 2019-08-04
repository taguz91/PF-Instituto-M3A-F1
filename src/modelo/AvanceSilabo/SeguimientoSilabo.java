/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.AvanceSilabo;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author HP
 */
public class SeguimientoSilabo {
    int id_seguimiento;
    LocalDate fecha_entrega;
    boolean esInterciclo;
    int id_curso;

    public SeguimientoSilabo() {
    }

    public SeguimientoSilabo(int id_seguimiento, LocalDate fecha_entrega, boolean esInterciclo, int id_curso) {
        this.id_seguimiento = id_seguimiento;
        this.fecha_entrega = fecha_entrega;
        this.esInterciclo = esInterciclo;
        this.id_curso = id_curso;
    }

    public int getId_seguimiento() {
        return id_seguimiento;
    }

    public void setId_seguimiento(int id_seguimiento) {
        this.id_seguimiento = id_seguimiento;
    }

    public LocalDate getFecha_entrega() {
        return fecha_entrega;
    }

    public void setFecha_entrega(LocalDate fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    public boolean isInterciclo() {
        return esInterciclo;
    }

    public void setEsInterciclo(boolean esInterciclo) {
        this.esInterciclo = esInterciclo;
    }

    public int getId_curso() {
        return id_curso;
    }

    public void setId_curso(int id_curso) {
        this.id_curso = id_curso;
    }
    
}
