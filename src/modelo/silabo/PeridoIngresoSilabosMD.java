/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.silabo;

import java.time.LocalDate;

/**
 *
 * @author HP
 */
public class PeridoIngresoSilabosMD {
    private int id;
    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;
    private int id_prd_lectivo;
    private String estado;

    public PeridoIngresoSilabosMD(int id, LocalDate fecha_inicio, LocalDate fecha_fin, int id_prd_lectivo, String estado) {
        this.id = id;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.id_prd_lectivo = id_prd_lectivo;
        this.estado = estado;
    }

    public PeridoIngresoSilabosMD() {
    }

    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the fecha_inicio
     */
    public LocalDate getFecha_inicio() {
        return fecha_inicio;
    }

    /**
     * @param fecha_inicio the fecha_inicio to set
     */
    public void setFecha_inicio(LocalDate fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    /**
     * @return the fecha_fin
     */
    public LocalDate getFecha_fin() {
        return fecha_fin;
    }

    /**
     * @param fecha_fin the fecha_fin to set
     */
    public void setFecha_fin(LocalDate fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    /**
     * @return the id_prd_lectivo
     */
    public int getId_prd_lectivo() {
        return id_prd_lectivo;
    }

    /**
     * @param id_prd_lectivo the id_prd_lectivo to set
     */
    public void setId_prd_lectivo(int id_prd_lectivo) {
        this.id_prd_lectivo = id_prd_lectivo;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
