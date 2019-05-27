/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.silabo;

import java.util.List;
import modelo.evaluacionSilabo.EvaluacionSilabo;
import modelo.materia.MateriaMD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.referenciasSilabo.ReferenciaSilabo;
import modelo.unidadSilabo.UnidadSilabo;

/**
 * 
 * @author Andres Ullauri
 */
public class SilaboMD {
    
    
    private Integer idSilabo;

    private MateriaMD idMateria;
    
    
    
    private int estadoSilabo;
    
    private PeriodoLectivoMD idPeriodoLectivo;
 
    private List<UnidadSilabo> unidadSilaboList;

    private List<ReferenciaSilabo> referenciaSilaboList;

    public SilaboMD() {
        this.idMateria = new MateriaMD();
        this.idPeriodoLectivo = new PeriodoLectivoMD();
    }

    public SilaboMD(MateriaMD idMateria, PeriodoLectivoMD idPeriodoLectivo) {
        this.idMateria = idMateria;
        this.idPeriodoLectivo = idPeriodoLectivo;
    }

    


    public Integer getIdSilabo() {
        return idSilabo;
    }

    public void setIdSilabo(Integer idSilabo) {
        this.idSilabo = idSilabo;
    }

    public MateriaMD getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(MateriaMD idMateria) {
        this.idMateria = idMateria;
    }

    public int getEstadoSilabo() {
        return estadoSilabo;
    }

    public void setEstadoSilabo(int estadoSilabo) {
        this.estadoSilabo = estadoSilabo;
    }

    public PeriodoLectivoMD getIdPeriodoLectivo() {
        return idPeriodoLectivo;
    }

    public void setIdPeriodoLectivo(PeriodoLectivoMD idPeriodoLectivo) {
        this.idPeriodoLectivo = idPeriodoLectivo;
    }

    public List<UnidadSilabo> getUnidadSilaboList() {
        return unidadSilaboList;
    }

    public void setUnidadSilaboList(List<UnidadSilabo> unidadSilaboList) {
        this.unidadSilaboList = unidadSilaboList;
    }

    public List<ReferenciaSilabo> getReferenciaSilaboList() {
        return referenciaSilaboList;
    }

    public void setReferenciaSilaboList(List<ReferenciaSilabo> referenciaSilaboList) {
        this.referenciaSilaboList = referenciaSilaboList;
    }
 

    

}
