/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.docente;

import modelo.periodolectivo.PeriodoLectivoMD;

/**
 *
 * @author arman
 */
public class RolPeriodoMD {
    private int id_rol;
    private String nombre_rol;
    private PeriodoLectivoMD periodo;

    public RolPeriodoMD() {
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }

    public String getNombre_rol() {
        return nombre_rol;
    }

    public void setNombre_rol(String nombre_rol) {
        this.nombre_rol = nombre_rol;
    }

    public PeriodoLectivoMD getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoLectivoMD periodo) {
        this.periodo = periodo;
    }

    @Override
    public String toString() {
        return "RolDocenteMD{" + "id_rol=" + id_rol + ", nombre_rol=" + nombre_rol + '}';
    }
    
}
