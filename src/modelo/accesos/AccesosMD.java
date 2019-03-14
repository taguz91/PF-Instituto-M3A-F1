package modelo.accesos;

/**
 *
 * @author MrRainx
 */
public class AccesosMD {

    private int idAccesos;
    private String nombre;
    private String descripcion;

    public AccesosMD(int idAccesos, String nombre, String descripcion) {
        this.idAccesos = idAccesos;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public AccesosMD() {
    }

    public int getIdAccesos() {
        return idAccesos;
    }

    public void setIdAccesos(int idAccesos) {
        this.idAccesos = idAccesos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Accesos{" + "idAccesos=" + idAccesos + ", nombre=" + nombre + ", descripcion=" + descripcion + '}';
    }

}
