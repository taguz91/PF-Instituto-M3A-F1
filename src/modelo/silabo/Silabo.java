/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.silabo;


import java.util.List;

import javax.xml.bind.annotation.XmlTransient;
import modelo.evaluacionSilabo.EvaluacionSilabo;
import modelo.materia.MateriaMD;
import modelo.referenciasSilabo.ReferenciaSilabo;
import modelo.unidadSilabo.UnidadSilabo;

/**
 * 
 * @author Andres Ullauri
 */
public class Silabo  {


    private List<UnidadSilabo> unidadSilaboList;

    private static final long serialVersionUID = 1L;



    private Integer idSilabo;


    private String estadoSilabo;
 
    private List<EvaluacionSilabo> evaluacionSilaboList;

    private List<ReferenciaSilabo> referenciaSilaboList;
 

    private MateriaMD idMateria;

    public Silabo() {
    }

    public Silabo(Integer idSilabo) {
        this.idSilabo = idSilabo;
    }

    public Silabo(Integer idSilabo, String estadoSilabo) {
        this.idSilabo = idSilabo;
        this.estadoSilabo = estadoSilabo;
    }

    public Silabo (MateriaMD  idMateria,String estadoSilabo) {
        this.estadoSilabo = estadoSilabo;
        this.idMateria = idMateria;
    }
    
    

    public Integer getIdSilabo() {
        return idSilabo;
    }

    public void setIdSilabo(Integer idSilabo) {
        this.idSilabo = idSilabo;
    }

    public String getEstadoSilabo() {
        return estadoSilabo;
    }

    public void setEstadoSilabo(String estadoSilabo) {
        this.estadoSilabo = estadoSilabo;
    }

    @XmlTransient
    public List<EvaluacionSilabo> getEvaluacionSilaboList() {
        return evaluacionSilaboList;
    }

    public void setEvaluacionSilaboList(List<EvaluacionSilabo> evaluacionSilaboList) {
        this.evaluacionSilaboList = evaluacionSilaboList;
    }

    @XmlTransient
    public List<ReferenciaSilabo> getReferenciaSilaboList() {
        return referenciaSilaboList;
    }

    public void setReferenciaSilaboList(List<ReferenciaSilabo> referenciaSilaboList) {
        this.referenciaSilaboList = referenciaSilaboList;
    }

    public MateriaMD  getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(MateriaMD idMateria) {
        this.idMateria = idMateria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSilabo != null ? idSilabo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Silabo)) {
            return false;
        }
        Silabo other = (Silabo) object;
        if ((this.idSilabo == null && other.idSilabo != null) || (this.idSilabo != null && !this.idSilabo.equals(other.idSilabo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Silabo[ idSilabo=" + idSilabo + " ]";
    }

    @XmlTransient
    public List<UnidadSilabo> getUnidadSilaboList() {
        return unidadSilaboList;
    }

    public void setUnidadSilaboList(List<UnidadSilabo> unidadSilaboList) {
        this.unidadSilaboList = unidadSilaboList;
    }

}
