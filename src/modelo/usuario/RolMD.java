package modelo.usuario;

/**
 *
 * @author MrRainx
 */
public class RolMD {

    private int id;
    private String nombre;
    private String observaciones;
    private boolean estado;

    public RolMD(int id, String nombre, String observaciones, boolean estado) {
        this.id = id;
        this.nombre = nombre;
        this.observaciones = observaciones;
        this.estado = estado;
    }

    public RolMD() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "RolMD{" + "id=" + id + ", nombre=" + nombre + ", observaciones=" + observaciones + ", estado=" + estado + '}';
    }

}
