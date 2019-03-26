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
public class EstrategiasAprendizaje {




    private Integer idEstrategia;

    private String descripcionEstrategia;
  

    private List<EstrategiasUnidad> estrategiasUnidadList;

    public EstrategiasAprendizaje() {
    }

    public EstrategiasAprendizaje(Integer idEstrategia) {
        this.idEstrategia = idEstrategia;
    }

    public EstrategiasAprendizaje(Integer idEstrategia, String descripcionEstrategia) {
        this.idEstrategia = idEstrategia;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstrategia != null ? idEstrategia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstrategiasAprendizaje)) {
            return false;
        }
        EstrategiasAprendizaje other = (EstrategiasAprendizaje) object;
        if ((this.idEstrategia == null && other.idEstrategia != null) || (this.idEstrategia != null && !this.idEstrategia.equals(other.idEstrategia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.EstrategiasAprendizaje[ idEstrategia=" + idEstrategia + " ]";
    }

}
