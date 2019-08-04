/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.AvanceSilabo;

import modelo.*;

/**
 *
 * @author HP
 */
public class AvanceUnidades {
    private String Observaciones;
    private String avancePorcentaje;
    private int id_unidad;
    private int id_avance;

    public AvanceUnidades(String Observaciones, String avancePorcentaje, int id_unidad, int id_avance) {
        this.Observaciones = Observaciones;
        this.avancePorcentaje = avancePorcentaje;
        this.id_unidad = id_unidad;
        this.id_avance = id_avance;
    }

    public AvanceUnidades() {
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String Observaciones) {
        this.Observaciones = Observaciones;
    }

    public String getAvancePorcentaje() {
        return avancePorcentaje;
    }

    public void setAvancePorcentaje(String avancePorcentaje) {
        this.avancePorcentaje = avancePorcentaje;
    }

    public int getId_unidad() {
        return id_unidad;
    }

    public void setId_unidad(int id_unidad) {
        this.id_unidad = id_unidad;
    }

    public int getId_avance() {
        return id_avance;
    }

    public void setId_avance(int id_avance) {
        this.id_avance = id_avance;
    }
    
}
