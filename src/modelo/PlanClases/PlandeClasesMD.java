/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.PlanClases;

import java.time.LocalDate;
import modelo.curso.CursoMD;
import modelo.materia.MateriaMD;
import modelo.persona.PersonaMD;
import modelo.unidadSilabo.UnidadSilaboMD;

/**
 *
 * @author ANDRES BERMEO
 */
public class PlandeClasesMD {

    private Integer id_plan_clases;
    private CursoMD id_curso;
    private UnidadSilaboMD id_unidad;
    private String observaciones;
    private LocalDate fecha_revision;
    private LocalDate fecha_generacion;
    private LocalDate fecha_cierre;
    private String trabajo_autonomo;
    private int estado_plan;

    //PARA CARGAR EN LA TABLA NECESITO ESTOS ATRIBUTOS
    private MateriaMD id_materia;
    private PersonaMD id_persona;

    public PlandeClasesMD() {
        this.id_curso = new CursoMD();
        this.id_unidad = new UnidadSilaboMD();
        this.id_materia = new MateriaMD();
        this.id_persona = new PersonaMD();
    }

    public PlandeClasesMD(CursoMD id_curso, UnidadSilaboMD id_unidad) {
        this.id_curso = id_curso;
        this.id_unidad = id_unidad;
    }

    public PlandeClasesMD(CursoMD id_curso, UnidadSilaboMD id_unidad, MateriaMD id_materia, PersonaMD id_persona) {
        this.id_curso = id_curso;
        this.id_unidad = id_unidad;
        this.id_materia = id_materia;
        this.id_persona = id_persona;
    }

    public Integer getID() {
        return id_plan_clases;
    }

    public void setId_plan_clases(Integer id_plan_clases) {
        this.id_plan_clases = id_plan_clases;
    }

    public CursoMD getId_curso() {
        return id_curso;
    }

    public void setId_curso(CursoMD id_curso) {
        this.id_curso = id_curso;
    }

    public UnidadSilaboMD getId_unidad() {
        return id_unidad;
    }

    public void setId_unidad(UnidadSilaboMD id_unidad) {
        this.id_unidad = id_unidad;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDate getFecha_revision() {
        return fecha_revision;
    }

    public void setFecha_revision(LocalDate fecha_revision) {
        this.fecha_revision = fecha_revision;
    }

    public LocalDate getFecha_generacion() {
        return fecha_generacion;
    }

    public void setFecha_generacion(LocalDate fecha_generacion) {
        this.fecha_generacion = fecha_generacion;
    }

    public LocalDate getFecha_cierre() {
        return fecha_cierre;
    }

    public void setFecha_cierre(LocalDate fecha_cierre) {
        this.fecha_cierre = fecha_cierre;
    }

    public MateriaMD getId_materia() {
        return id_materia;
    }

    public void setId_materia(MateriaMD id_materia) {
        this.id_materia = id_materia;
    }

    public PersonaMD getId_persona() {
        return id_persona;
    }

    public void setId_persona(PersonaMD id_persona) {
        this.id_persona = id_persona;
    }

    public String getTrabajo_autonomo() {
        return trabajo_autonomo;
    }

    public void setTrabajo_autonomo(String trabajo_autonomo) {
        this.trabajo_autonomo = trabajo_autonomo;
    }

    public int getEstado_plan() {
        return estado_plan;
    }

    public void setEstado_plan(int estado_plan) {
        this.estado_plan = estado_plan;
    }

    public String descripcion() {
        return "<html>"
                + "Curso: "
                + "<br><center>" + id_curso.getNombre() + "</center><br>"
                + "Materia: "
                + "<br><center>" + id_materia.getNombre() + "</center>"
                + "</html>";
    }

}
