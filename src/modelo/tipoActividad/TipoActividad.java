/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.tipoActividad;


import java.util.List;


import javax.xml.bind.annotation.XmlTransient;
import modelo.evaluacionSilabo.EvaluacionSilabo;

/**
 * 
 * @author Andres Ullauri
 */
public class TipoActividad  {

    private static final long serialVersionUID = 1L;
    
 
    private Integer idTipoActividad;
 
    private String nombreTipoActividad;
   
    private String nombreSubtipoActividad;
    
    private List<EvaluacionSilabo> evaluacionSilaboList;

    public TipoActividad() {
    }

    public TipoActividad(Integer idTipoActividad) {
        this.idTipoActividad = idTipoActividad;
    }

    public TipoActividad(Integer idTipoActividad, String nombreTipoActividad, String nombreSubtipoActividad) {
        this.idTipoActividad = idTipoActividad;
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

    @XmlTransient
    public List<EvaluacionSilabo> getEvaluacionSilaboList() {
        return evaluacionSilaboList;
    }

    public void setEvaluacionSilaboList(List<EvaluacionSilabo> evaluacionSilaboList) {
        this.evaluacionSilaboList = evaluacionSilaboList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoActividad != null ? idTipoActividad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoActividad)) {
            return false;
        }
        TipoActividad other = (TipoActividad) object;
        if ((this.idTipoActividad == null && other.idTipoActividad != null) || (this.idTipoActividad != null && !this.idTipoActividad.equals(other.idTipoActividad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TipoActividad[ idTipoActividad=" + idTipoActividad + " ]";
    }

}
