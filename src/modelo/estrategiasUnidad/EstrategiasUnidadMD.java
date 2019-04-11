/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.estrategiasUnidad;


import modelo.estrategiasAprendizaje.EstrategiasAprendizajeMD;
import modelo.unidadSilabo.UnidadSilaboMD;

/**
 *
 * @author Andres Ullauri
 */
public class EstrategiasUnidadMD {

    private Integer idEstrategiaUnidad;
    private EstrategiasAprendizajeMD idEstrategia;

    private UnidadSilaboMD idUnidad;

    public EstrategiasUnidadMD() {
    }

    public EstrategiasUnidadMD( EstrategiasAprendizajeMD idEstrategia, UnidadSilaboMD idUnidad) {
      
        this.idEstrategia = idEstrategia;
        this.idUnidad = idUnidad;
    }

    public Integer getIdEstrategiaUnidad() {
        return idEstrategiaUnidad;
    }

    public void setIdEstrategiaUnidad(Integer idEstrategiaUnidad) {
        this.idEstrategiaUnidad = idEstrategiaUnidad;
    }

    public EstrategiasAprendizajeMD getIdEstrategia() {
        return idEstrategia;
    }

    public void setIdEstrategia(EstrategiasAprendizajeMD idEstrategia) {
        this.idEstrategia = idEstrategia;
    }

    public UnidadSilaboMD getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(UnidadSilaboMD idUnidad) {
        this.idUnidad = idUnidad;
    }

}
