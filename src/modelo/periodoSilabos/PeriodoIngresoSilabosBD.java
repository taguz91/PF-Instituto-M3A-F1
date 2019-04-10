/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.periodoSilabos;


import java.time.LocalDate;

import modelo.ConexionBD;


/**
 * 
 * @author Andres Ullauri
 */
public class PeriodoIngresoSilabosBD extends PeriodoIngresoSilabosMD  {
    
     private ConexionBD conexion;

    public PeriodoIngresoSilabosBD(ConexionBD conexion) {
        this.conexion = conexion;
    }

    public PeriodoIngresoSilabosBD(ConexionBD conexion, Integer idPeriodo, LocalDate fechaInicioPeriodo, LocalDate fechaFinPeriodo, boolean estadoPeriodo, Integer idPeriodoCarrera) {
        super(idPeriodo, fechaInicioPeriodo, fechaFinPeriodo, estadoPeriodo, idPeriodoCarrera);
        this.conexion = conexion;
    }
     
     

}
