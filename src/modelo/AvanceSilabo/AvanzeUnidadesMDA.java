/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.AvanceSilabo;

import modelo.unidadSilabo.UnidadSilaboMD;

/**
 *
 * @author Skull
 */
public class AvanzeUnidadesMDA {
    private int id_unidadSeguimiento;
    private UnidadSilaboMD unidad;
    private SeguimientoSilaboMD seguimiento;
    private int portecentaje;
    private String observaciones;

    public AvanzeUnidadesMDA()
    {
        this.unidad=new UnidadSilaboMD();
        this.seguimiento=new SeguimientoSilaboMD();
    }

    public AvanzeUnidadesMDA(UnidadSilaboMD unidad) {
        this.unidad = unidad;
        this.seguimiento=new SeguimientoSilaboMD();
    }

    public int getId_unidadSeguimiento() {
        return id_unidadSeguimiento;
    }

    public void setId_unidadSeguimiento(int id_unidadSeguimiento) {
        this.id_unidadSeguimiento = id_unidadSeguimiento;
    }

    public UnidadSilaboMD getUnidad() {
        return unidad;
    }

    public void setUnidad(UnidadSilaboMD unidad) {
        this.unidad = unidad;
    }

    public SeguimientoSilaboMD getSeguimiento() {
        return seguimiento;
    }

    public void setSeguimiento(SeguimientoSilaboMD seguimiento) {
        this.seguimiento = seguimiento;
    }

    public int getPortecentaje() {
        return portecentaje;
    }

    public void setPortecentaje(int portecentaje) {
        this.portecentaje = portecentaje;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    
    
}
