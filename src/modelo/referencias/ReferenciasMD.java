/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.referencias;

import java.util.List;
import modelo.referenciasSilabo.ReferenciaSilabo;

/**
 * 
 * @author Andres Ullauri
 */
public class ReferenciasMD {
    
    private Integer idReferencia;

    private String codigoReferencia;

    private String descripcionReferencia;
 
    private String tipoReferencia;

    private boolean existeEnBiblioteca;

    private List<ReferenciaSilabo> referenciaSilaboList;

    public ReferenciasMD() {
    }

    public ReferenciasMD( String codigoReferencia, String descripcionReferencia, String tipoReferencia) {
        
        this.codigoReferencia = codigoReferencia;
        this.descripcionReferencia = descripcionReferencia;
        this.tipoReferencia = tipoReferencia;
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

    public boolean isExisteEnBiblioteca() {
        return existeEnBiblioteca;
    }

    public void setExisteEnBiblioteca(boolean existeEnBiblioteca) {
        this.existeEnBiblioteca = existeEnBiblioteca;
    }

    public List<ReferenciaSilabo> getReferenciaSilaboList() {
        return referenciaSilaboList;
    }

    public void setReferenciaSilaboList(List<ReferenciaSilabo> referenciaSilaboList) {
        this.referenciaSilaboList = referenciaSilaboList;
    }
    
    
}
