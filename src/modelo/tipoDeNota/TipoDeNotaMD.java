package modelo.tipoDeNota;

import java.time.LocalDate;

/**
 *
 * @author MrRainx
 */
public class TipoDeNotaMD {

    private int idTipoNota;
    private String nombre;
    private double valorMinimo;
    private double valorMaximo;
    private LocalDate fechaCreacion;
    private boolean estado;
    private int idCarrera;

    public TipoDeNotaMD(int idTipoNota, String nombre, double valorMinimo, double valorMaximo, LocalDate fechaCreacion, boolean estado, int idCarrera) {
        this.idTipoNota = idTipoNota;
        this.nombre = nombre;
        this.valorMinimo = valorMinimo;
        this.valorMaximo = valorMaximo;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
        this.idCarrera = idCarrera;
    }

    public TipoDeNotaMD() {
    }

    public int getIdTipoNota() {
        return idTipoNota;
    }

    public void setIdTipoNota(int idTipoNota) {
        this.idTipoNota = idTipoNota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(double valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public double getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(double valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    @Override
    public String toString() {
        return "TipoDeNotaMD{" + "idTipoNota=" + idTipoNota + ", nombre=" + nombre + ", valorMinimo=" + valorMinimo + ", valorMaximo=" + valorMaximo + ", fechaCreacion=" + fechaCreacion + ", estado=" + estado + ", idCarrera=" + idCarrera + '}';
    }

}
