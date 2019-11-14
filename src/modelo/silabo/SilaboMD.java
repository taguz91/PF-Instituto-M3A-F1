/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.silabo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import modelo.materia.MateriaMD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.referenciasSilabo.ReferenciaSilaboMD;
import modelo.unidadSilabo.UnidadSilaboMD;

/**
 *
 * @author Andres Ullauri
 */
public class SilaboMD {

    private Integer ID;

    private MateriaMD materia;

    private int estado;

    private PeriodoLectivoMD periodo;

    private LocalDate fechaGeneracion;

    private LocalDateTime fechaActualizacion;

    private List<UnidadSilaboMD> unidades;

    private List<ReferenciaSilaboMD> referencias;

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

    public List<UnidadSilaboMD> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<UnidadSilaboMD> unidades) {
        this.unidades = unidades;
    }

    public List<ReferenciaSilaboMD> getReferencias() {
        return referencias;
    }

    public void setReferencias(List<ReferenciaSilaboMD> referencias) {
        this.referencias = referencias;
    }

    public LocalDate getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDate fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    @Override
    public String toString() {
        return "SilaboMD{" + "ID=" + ID + ", materia=" + materia + ", estado=" + estado + ", periodo=" + periodo + ", unidades=" + unidades + ", referencias=" + referencias + '}';
    }

}
