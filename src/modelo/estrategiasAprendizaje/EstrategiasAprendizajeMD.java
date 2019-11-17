/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.estrategiasAprendizaje;

import java.util.List;
import java.util.UUID;
import modelo.estrategiasUnidad.EstrategiasUnidadMD;

/**
 *
 * @author Andres Ullauri
 */
public class EstrategiasAprendizajeMD {
    
    private final int idLocal = UUID.randomUUID().hashCode();

    private Integer idEstrategia;

    private String descripcionEstrategia;

    private List<EstrategiasUnidadMD> estrategiasUnidadList;

    public EstrategiasAprendizajeMD() {
    }

    public EstrategiasAprendizajeMD(String descripcionEstrategia) {

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

    public List<EstrategiasUnidadMD> getEstrategiasUnidadList() {
        return estrategiasUnidadList;
    }

    public void setEstrategiasUnidadList(List<EstrategiasUnidadMD> estrategiasUnidadList) {
        this.estrategiasUnidadList = estrategiasUnidadList;
    }

    public int getIdLocal() {
        return idLocal;
    }

}
