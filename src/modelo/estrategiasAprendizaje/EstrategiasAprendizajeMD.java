/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.estrategiasAprendizaje;

import java.util.List;
import modelo.estrategiasUnidad.EstrategiasUnidad;

/**
 * 
 * @author Andres Ullauri
 */
public class EstrategiasAprendizajeMD {

    
    private Integer idEstrategia;

    private String descripcionEstrategia;
  

    private List<EstrategiasUnidad> estrategiasUnidadList;

    public EstrategiasAprendizajeMD() {
    }
    
    

    public EstrategiasAprendizajeMD( String descripcionEstrategia) {
        
        this.descripcionEstrategia = descripcionEstrategia;
       
    }

    public Integer getIdEstrategia() {
        return idEstrategia;
    }

    public void setIdEstrategia(Integer idEstrategia) {
        this.idEstrategia = idEstrategia;
    }

    public String getDescripcionEstrategia() {
        return descripcionEstrategia;
    }

    public void setDescripcionEstrategia(String descripcionEstrategia) {
        this.descripcionEstrategia = descripcionEstrategia;
    }

    public List<EstrategiasUnidad> getEstrategiasUnidadList() {
        return estrategiasUnidadList;
    }

    public void setEstrategiasUnidadList(List<EstrategiasUnidad> estrategiasUnidadList) {
        this.estrategiasUnidadList = estrategiasUnidadList;
    }
    
    
    
    
}
