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

    public void setID(Integer id_plan_clases) {
        this.id_plan_clases = id_plan_clases;
    }

    public CursoMD getCurso() {
        return id_curso;
    }

    public void setCurso(CursoMD id_curso) {
        this.id_curso = id_curso;
    }

    public UnidadSilaboMD getUnidad() {
        return id_unidad;
    }

    public void setUnidad(UnidadSilaboMD id_unidad) {
        this.id_unidad = id_unidad;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDate getFechaRevision() {
        return fecha_revision;
    }

    public void setFechaRevision(LocalDate fecha_revision) {
        this.fecha_revision = fecha_revision;
    }

    public LocalDate getFechaGeneracion() {
        return fecha_generacion;
    }

    public void setFechaGeneracion(LocalDate fecha_generacion) {
        this.fecha_generacion = fecha_generacion;
    }

    public LocalDate getFechaCierre() {
        return fecha_cierre;
    }

    public void setFechaCierre(LocalDate fecha_cierre) {
        this.fecha_cierre = fecha_cierre;
    }

    public MateriaMD getMateria() {
        return id_materia;
    }

    public void setMateria(MateriaMD id_materia) {
        this.id_materia = id_materia;
    }

    public PersonaMD getPersona() {
        return id_persona;
    }

    public void setPersona(PersonaMD id_persona) {
        this.id_persona = id_persona;
    }

    public String getTrabajoAutonomo() {
        return trabajo_autonomo;
    }

    public void setTrabajoAutonomo(String trabajo_autonomo) {
        this.trabajo_autonomo = trabajo_autonomo;
    }

    public int getEstado() {
        return estado_plan;
    }

    public void setEstado(int estado_plan) {
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
