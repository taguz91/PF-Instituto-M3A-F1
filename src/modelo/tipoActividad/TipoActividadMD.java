/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.tipoActividad;

import java.util.List;
import modelo.evaluacionSilabo.EvaluacionSilaboMD;


/**
 * 
 * @author Andres Ullauri
 */
public class TipoActividadMD {

    private Integer idTipoActividad;
 
    private String nombreTipoActividad;
   
    private String nombreSubtipoActividad;
    
    private List<EvaluacionSilaboMD> evaluacionSilaboList;

    public TipoActividadMD() {
    }

    public TipoActividadMD(String nombreTipoActividad, String nombreSubtipoActividad) {
       
        this.nombreTipoActividad = nombreTipoActividad;
        this.nombreSubtipoActividad = nombreSubtipoActividad;
    }

    public Integer getIdTipoActividad() {
        return idTipoActividad;
    }

    public void setIdTipoActividad(Integer idTipoActividad) {
        this.idTipoActividad = idTipoActividad;
    }

    public String getNombreTipoActividad() {
        return nombreTipoActividad;
    }

    public void setNombreTipoActividad(String nombreTipoActividad) {
        this.nombreTipoActividad = nombreTipoActividad;
    }

    public String getNombreSubtipoActividad() {
        return nombreSubtipoActividad;
    }

    public void setNombreSubtipoActividad(String nombreSubtipoActividad) {
        this.nombreSubtipoActividad = nombreSubtipoActividad;
    }

    public List<EvaluacionSilaboMD> getEvaluacionSilaboList() {
        return evaluacionSilaboList;
    }

    public void setEvaluacionSilaboList(List<EvaluacionSilaboMD> evaluacionSilaboList) {
        this.evaluacionSilaboList = evaluacionSilaboList;
    }
   
    
    
}
