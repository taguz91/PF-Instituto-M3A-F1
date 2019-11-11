/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.silabo;

import java.util.List;
import modelo.materia.MateriaMD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.referenciasSilabo.ReferenciaSilabo;
import modelo.unidadSilabo.UnidadSilabo;

/**
 *
 * @author Andres Ullauri
 */
public class SilaboMD {

    private Integer ID;

    private MateriaMD materia;

    private int estado;

    private PeriodoLectivoMD periodo;

    private List<UnidadSilabo> unidades;

    private List<ReferenciaSilabo> referencias;

    public SilaboMD() {
        this.materia = new MateriaMD();
        this.periodo = new PeriodoLectivoMD();
    }

    public SilaboMD(MateriaMD idMateria, PeriodoLectivoMD idPeriodoLectivo) {
        this.materia = idMateria;
        this.periodo = idPeriodoLectivo;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public MateriaMD getMateria() {
        return materia;
    }

    public void setMateria(MateriaMD idMateria) {
        this.materia = idMateria;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estadoSilabo) {
        this.estado = estadoSilabo;
    }

    public PeriodoLectivoMD getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoLectivoMD periodo) {
        this.periodo = periodo;
    }

    public List<UnidadSilabo> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<UnidadSilabo> unidades) {
        this.unidades = unidades;
    }

    public List<ReferenciaSilabo> getReferencias() {
        return referencias;
    }

    public void setReferencias(List<ReferenciaSilabo> referencias) {
        this.referencias = referencias;
    }

}
