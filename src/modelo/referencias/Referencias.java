
package modelo.referencias;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import modelo.referenciasSilabo.ReferenciaSilabo;

public class Referencias implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idReferencia;

    private String codigoReferencia;

    private String descripcionReferencia;
 
    private String tipoReferencia;

    private boolean existeEnBiblioteca;

    private List<ReferenciaSilabo> referenciaSilaboList;

    public Referencias() {
    }

    public Referencias(Integer idReferencia) {
        this.idReferencia = idReferencia;
    }

    public Referencias(Integer idReferencia, String codigoReferencia, String descripcionReferencia, String tipoReferencia, boolean existeEnBiblioteca) {
        this.idReferencia = idReferencia;
        this.codigoReferencia = codigoReferencia;
        this.descripcionReferencia = descripcionReferencia;
        this.tipoReferencia = tipoReferencia;
        this.existeEnBiblioteca = existeEnBiblioteca;
    }

    public Referencias(String descripcionReferencia, String tipoReferencia, boolean existeEnBiblioteca) {
        this.descripcionReferencia = descripcionReferencia;
        this.tipoReferencia = tipoReferencia;
        this.existeEnBiblioteca = existeEnBiblioteca;
    }
    
    
    
    
    

    public Integer getIdReferencia() {
        return idReferencia;
    }

    public void setIdReferencia(Integer idReferencia) {
        this.idReferencia = idReferencia;
    }

    public String getCodigoReferencia() {
        return codigoReferencia;
    }

    public void setCodigoReferencia(String codigoReferencia) {
        this.codigoReferencia = codigoReferencia;
    }

    public String getDescripcionReferencia() {
        return descripcionReferencia;
    }

    public void setDescripcionReferencia(String descripcionReferencia) {
        this.descripcionReferencia = descripcionReferencia;
    }

    public String getTipoReferencia() {
        return tipoReferencia;
    }

    public void setTipoReferencia(String tipoReferencia) {
        this.tipoReferencia = tipoReferencia;
    }

    public boolean getExisteEnBiblioteca() {
        return existeEnBiblioteca;
    }

    public void setExisteEnBiblioteca(boolean existeEnBiblioteca) {
        this.existeEnBiblioteca = existeEnBiblioteca;
    }

    @XmlTransient
    public List<ReferenciaSilabo> getReferenciaSilaboList() {
        return referenciaSilaboList;
    }

    public void setReferenciaSilaboList(List<ReferenciaSilabo> referenciaSilaboList) {
        this.referenciaSilaboList = referenciaSilaboList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReferencia != null ? idReferencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Referencias)) {
            return false;
        }
        Referencias other = (Referencias) object;
        if ((this.idReferencia == null && other.idReferencia != null) || (this.idReferencia != null && !this.idReferencia.equals(other.idReferencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Referencias[ idReferencia=" + idReferencia + " ]";
    }

}
