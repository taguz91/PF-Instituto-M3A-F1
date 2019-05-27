/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.silabo;

import java.time.LocalDate;
import modelo.ConexionBD;
import modelo.periodoSilabos.PeriodoIngresoSilabosMD;

/**
 *
 * @author HP
 */
public class PeriodoIngresoSIlaboBD extends PeriodoIngresoSilabosMD {
         private ConexionBD conexion;


    public PeriodoIngresoSIlaboBD(ConexionBD conexion,Integer idPeriodo, LocalDate fechaInicioPeriodo, LocalDate fechaFinPeriodo, boolean estadoPeriodo, Integer idPeriodoCarrera) {
        super(idPeriodo, fechaInicioPeriodo, fechaFinPeriodo, estadoPeriodo, idPeriodoCarrera);
    }

    public PeriodoIngresoSIlaboBD(ConexionBD conexion) {
        this.conexion = conexion;
    }

    public PeriodoIngresoSIlaboBD() {
    }
    
    public void ingresar(){
        
    }
    public void editar(){
        
    }
    
    
}
