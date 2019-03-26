/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.estrategiasUnidad;




import modelo.estrategiasAprendizaje.EstrategiasAprendizaje;
import modelo.unidadSilabo.UnidadSilabo;

/**
 * 
 * @author Andres Ullauri
 */
public class EstrategiasUnidad  {


    private Integer idEstrategiaUnidad;
     private EstrategiasAprendizaje idEstrategia;

    private UnidadSilabo idUnidad;

    public EstrategiasUnidad() {
        this.idEstrategia = new EstrategiasAprendizaje();
        this.idUnidad = new UnidadSilabo();
    }

    public EstrategiasUnidad(EstrategiasAprendizaje idEstrategia, UnidadSilabo idUnidad) {
        this.idEstrategia = idEstrategia;
        this.idUnidad = idUnidad;
    }
    
   
    public EstrategiasUnidad(Integer idEstrategiaUnidad) {
        this.idEstrategiaUnidad = idEstrategiaUnidad;
    }

    

    

    public Integer getIdEstrategiaUnidad() {
        return idEstrategiaUnidad;
    }

    public void setIdEstrategiaUnidad(Integer idEstrategiaUnidad) {
        this.idEstrategiaUnidad = idEstrategiaUnidad;
    }

    public EstrategiasAprendizaje getIdEstrategia() {
        return idEstrategia;
    }

    public void setIdEstrategia(EstrategiasAprendizaje idEstrategia) {
        this.idEstrategia = idEstrategia;
    }

    public UnidadSilabo getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(UnidadSilabo idUnidad) {
        this.idUnidad = idUnidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstrategiaUnidad != null ? idEstrategiaUnidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstrategiasUnidad)) {
            return false;
        }
        EstrategiasUnidad other = (EstrategiasUnidad) object;
        if ((this.idEstrategiaUnidad == null && other.idEstrategiaUnidad != null) || (this.idEstrategiaUnidad != null && !this.idEstrategiaUnidad.equals(other.idEstrategiaUnidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.EstrategiasUnidad[ idEstrategiaUnidad=" + idEstrategiaUnidad + " ]";
    }

}
