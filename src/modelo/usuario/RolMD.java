package modelo.usuario;

import java.util.List;
import modelo.accesos.AccesosBD;
import modelo.accesos.AccesosMD;

/**
 *
 * @author MrRainx
 */
public class RolMD {

    private int id;
    private String nombre;

    public RolMD(int idRol, String nombre) {
        this.id = idRol;
        this.nombre = nombre;
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

    @Override
    public String toString() {
        return "RolUsuario{" + "idRol=" + id + ", nombre=" + nombre + '}';
    }

}
