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
import modelo.persona.PersonaMD;
import modelo.referenciasSilabo.ReferenciaSilaboMD;
import modelo.unidadSilabo.UnidadSilaboMD;

/**
 *
 * @author MrRainx
 */
public class SilaboMD {

    public static int PENDIENTE = 0;
    public static int APROBADO = 1;
    public static int REVISAR = 2;

    private Integer ID = 0;

    private MateriaMD materia;

    private int estado;

    private PeriodoLectivoMD periodo;

    private LocalDate fechaGeneracion;

    private LocalDateTime fechaActualizacion;

    private List<UnidadSilaboMD> unidades;

    private List<ReferenciaSilaboMD> referencias;

    private boolean editando;

    private PersonaMD editadoPor;

    private LocalDateTime ultimaEdicion;

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

    public static String getEstadoStr(int estado) {
        switch (estado) {
            case 0:
                return "PENDIENTE";
            case 1:
                return "APROBADO";
            case 2:
                return "REVISAR";
        }
        return null;
    }

    public static Integer getEstadoInt(String estado) {
        switch (estado) {
            case "PENDIENTE":
                return 0;
            case "APROBADO":
                return 1;
            case "REVISAR":
                return 2;
        }
        return null;
    }

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

    public PersonaMD getEditadoPor() {
        return editadoPor;
    }

    public void setEditadoPor(PersonaMD editadoPor) {
        this.editadoPor = editadoPor;
    }

    public LocalDateTime getUltimaEdicion() {
        return ultimaEdicion;
    }

    public void setUltimaEdicion(LocalDateTime ultimaEdicion) {
        this.ultimaEdicion = ultimaEdicion;
    }

    @Override
    public String toString() {
        return "SilaboMD{" + "ID=" + ID + ", materia=" + materia + ", estado=" + estado + ", periodo=" + periodo + ", fechaGeneracion=" + fechaGeneracion + ", fechaActualizacion=" + fechaActualizacion + ", unidades=" + unidades + ", referencias=" + referencias + ", editando=" + editando + ", editadoPor=" + editadoPor + ", ultimaEdicion=" + ultimaEdicion + '}';
    }

}
