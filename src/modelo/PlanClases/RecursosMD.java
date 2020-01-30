/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.PlanClases;

public class RecursosMD {

    private int id_recurso;
    private String nombre_recursos;
    private String tipo_recurso;
    private boolean checked;

    public RecursosMD(int id_recurso, String nombre_recursos, String tipo_recurso) {
        this.id_recurso = id_recurso;
        this.nombre_recursos = nombre_recursos;
        this.tipo_recurso = tipo_recurso;
    }

    public RecursosMD() {
    }

    public int getId_recurso() {
        return id_recurso;
    }

    public void setId_recurso(int id_recurso) {
        this.id_recurso = id_recurso;
    }

    public String getNombre_recursos() {
        return nombre_recursos;
    }

    public void setNombre_recursos(String nombre_recursos) {
        this.nombre_recursos = nombre_recursos;
    }

    public String getTipo_recurso() {
        return tipo_recurso;
    }

    public void setTipo_recurso(String tipo_recurso) {
        this.tipo_recurso = tipo_recurso;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    
    

}
