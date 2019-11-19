/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.referenciasSilabo;


import java.io.Serializable;
import modelo.referencias.ReferenciasMD;
import modelo.silabo.SilaboMD;

/**
 *
 * @author Andres Ullauri
 */
public class ReferenciaSilaboMD implements Serializable {

    private Integer idReferenciaSilabo = 0;

    private ReferenciasMD idReferencia;

    private SilaboMD idSilabo;

    public ReferenciaSilaboMD() {
        
        this.idReferencia=new ReferenciasMD();
        
    }

    public ReferenciaSilaboMD( ReferenciasMD idReferencia, SilaboMD idSilabo) {
        
        this.idReferencia = idReferencia;
        this.idSilabo = idSilabo;
    }

    public Integer getIdReferenciaSilabo() {
        return idReferenciaSilabo;
    }

    public void setIdReferenciaSilabo(Integer idReferenciaSilabo) {
        this.idReferenciaSilabo = idReferenciaSilabo;
    }

    public ReferenciasMD getIdReferencia() {
        return idReferencia;
    }

    public void setIdReferencia(ReferenciasMD idReferencia) {
        this.idReferencia = idReferencia;
    }

    public SilaboMD getIdSilabo() {
        return idSilabo;
    }

    public void setIdSilabo(SilaboMD idSilabo) {
        this.idSilabo = idSilabo;
    }

}
