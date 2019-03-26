/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.referenciasSilabo;

import java.io.Serializable;
import modelo.referencias.Referencias;
import modelo.silabo.Silabo;



/**
 * 
 * @author Andres Ullauri
 */
public class ReferenciaSilabo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idReferenciaSilabo;

    private Referencias idReferencia;

    private Silabo idSilabo;

    public ReferenciaSilabo() {
        this.idSilabo=new Silabo();
        this.idReferencia=new Referencias();
    }

    public ReferenciaSilabo(Integer idReferenciaSilabo) {
        this.idReferenciaSilabo = idReferenciaSilabo;
    }

    public ReferenciaSilabo( Referencias idReferencia, Silabo idSilabo) {
        
        this.idReferencia = idReferencia;
        this.idSilabo = idSilabo;
    }
    
    public ReferenciaSilabo( Referencias idReferencia) {
        
        this.idReferencia = idReferencia;
        
    }

    public Integer getIdReferenciaSilabo() {
        return idReferenciaSilabo;
    }

    public void setIdReferenciaSilabo(Integer idReferenciaSilabo) {
        this.idReferenciaSilabo = idReferenciaSilabo;
    }

    public Referencias getIdReferencia() {
        return idReferencia;
    }

    public void setIdReferencia(Referencias idReferencia) {
        this.idReferencia = idReferencia;
    }

    public Silabo getIdSilabo() {
        return idSilabo;
    }

    public void setIdSilabo(Silabo idSilabo) {
        this.idSilabo = idSilabo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReferenciaSilabo != null ? idReferenciaSilabo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReferenciaSilabo)) {
            return false;
        }
        ReferenciaSilabo other = (ReferenciaSilabo) object;
        if ((this.idReferenciaSilabo == null && other.idReferenciaSilabo != null) || (this.idReferenciaSilabo != null && !this.idReferenciaSilabo.equals(other.idReferenciaSilabo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.ReferenciaSilabo[ idReferenciaSilabo=" + idReferenciaSilabo + " ]";
    }

}
