package modelo.carrera;

import java.time.LocalDate;
import modelo.persona.DocenteMD;

/**
 *
 * @author arman
 */
public class CarreraMD {

    private int id;
    private String codigo;
    private String nombre; 
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String modalidad;
    private DocenteMD coordinador;
    
    public CarreraMD() {
    }

    public CarreraMD(int id, String codigo, String nombre, LocalDate fechaInicio, LocalDate fechaFin, String modalidad, DocenteMD coordinador) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.modalidad = modalidad;
        this.coordinador = coordinador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public DocenteMD getCoordinador() {
        return coordinador;
    }

    public void setCoordinador(DocenteMD coordinador) {
        this.coordinador = coordinador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Carrera{" + "id=" + id + ", codigo=" + codigo + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", modalidad=" + modalidad + ", coordinador=" + coordinador + '}';
    }

}
