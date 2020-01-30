/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.PlanClases;

import java.time.LocalDate;
import java.util.List;
import modelo.curso.CursoMD;
import modelo.materia.MateriaMD;
import modelo.persona.PersonaMD;
import modelo.unidadSilabo.UnidadSilaboMD;

/**
 *
 * @author MrRainx
 */
public class PlandeClasesMD {

    private Integer id;
    private CursoMD curso;
    private UnidadSilaboMD unidad;
    private String observaciones;
    private LocalDate fechaRevicion;
    private LocalDate fechaGeneracion;
    private LocalDate fechaCierre;
    private String trabajoAutonomo;
    private int estado;

    private List<RecursosMD> recursos;

    //PARA CARGAR EN LA TABLA NECESITO ESTOS ATRIBUTOS
    private MateriaMD materia;
    private PersonaMD persona;

    public PlandeClasesMD() {
        this.curso = new CursoMD();
        this.unidad = new UnidadSilaboMD();
        this.materia = new MateriaMD();
        this.persona = new PersonaMD();
    }

    public PlandeClasesMD(CursoMD id_curso, UnidadSilaboMD id_unidad) {
        this.curso = id_curso;
        this.unidad = id_unidad;
    }

    public PlandeClasesMD(CursoMD id_curso, UnidadSilaboMD id_unidad, MateriaMD id_materia, PersonaMD id_persona) {
        this.curso = id_curso;
        this.unidad = id_unidad;
        this.materia = id_materia;
        this.persona = id_persona;
    }

    public Integer getID() {
        return id;
    }

    public void setID(Integer id_plan_clases) {
        this.id = id_plan_clases;
    }

    public CursoMD getCurso() {
        return curso;
    }

    public void setCurso(CursoMD id_curso) {
        this.curso = id_curso;
    }

    public UnidadSilaboMD getUnidad() {
        return unidad;
    }

    public void setUnidad(UnidadSilaboMD id_unidad) {
        this.unidad = id_unidad;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDate getFechaRevision() {
        return fechaRevicion;
    }

    public void setFechaRevision(LocalDate fecha_revision) {
        this.fechaRevicion = fecha_revision;
    }

    public LocalDate getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDate fecha_generacion) {
        this.fechaGeneracion = fecha_generacion;
    }

    public LocalDate getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDate fecha_cierre) {
        this.fechaCierre = fecha_cierre;
    }

    public MateriaMD getMateria() {
        return materia;
    }

    public void setMateria(MateriaMD id_materia) {
        this.materia = id_materia;
    }

    public PersonaMD getPersona() {
        return persona;
    }

    public void setPersona(PersonaMD id_persona) {
        this.persona = id_persona;
    }

    public String getTrabajoAutonomo() {
        return trabajoAutonomo;
    }

    public void setTrabajoAutonomo(String trabajo_autonomo) {
        this.trabajoAutonomo = trabajo_autonomo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado_plan) {
        this.estado = estado_plan;
    }

    public List<RecursosMD> getRecursos() {
        return recursos;
    }

    public void setRecursos(List<RecursosMD> recursos) {
        this.recursos = recursos;
    }

    public String descripcion() {
        return "<html>"
                + "Curso: "
                + "<br><center>" + curso.getNombre() + "</center><br>"
                + "Materia: "
                + "<br><center>" + materia.getNombre() + "</center>"
                + "</html>";
    }

    public String getInfoDocente() {

        return String.format("%s %s %s %s",
                this.curso.getDocente().getIdentificacion(),
                this.curso.getDocente().getPrimerNombre(),
                this.curso.getDocente().getPrimerApellido(),
                this.curso.getDocente().getSegundoApellido()
        );

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

    @Override
    public String toString() {
        return "PlandeClasesMD{" + "id=" + id + ", curso=" + curso + ", unidad=" + unidad + ", observaciones=" + observaciones + ", fechaRevicion=" + fechaRevicion + ", fechaGeneracion=" + fechaGeneracion + ", fechaCierre=" + fechaCierre + ", trabajoAutonomo=" + trabajoAutonomo + ", estado=" + estado + ", materia=" + materia + ", persona=" + persona + '}';
    }

}
