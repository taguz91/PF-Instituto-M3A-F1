package modelo.accesosDelRol;

import modelo.accesos.AccesosBD;
import modelo.usuario.RolBD;

/**
 *
 * @author MrRainx
 */
public class AccesosDelRolMD {

    private int id;
    private boolean activo;
    private RolBD rol;
    private AccesosBD acceso;

    public AccesosDelRolMD(int id, boolean activo, RolBD rol, AccesosBD acceso) {
        this.id = id;
        this.activo = activo;
        this.rol = rol;
        this.acceso = acceso;
    }

    public AccesosDelRolMD() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public RolBD getRol() {
        return rol;
    }

    public void setRol(RolBD rol) {
        this.rol = rol;
    }

    public AccesosBD getAcceso() {
        return acceso;
    }

    public void setAcceso(AccesosBD acceso) {
        this.acceso = acceso;
    }

    @Override
    public String toString() {
        return "AccesosDelRolMD{" + "id=" + id + ", activo=" + activo + ", rol=" + rol + ", acceso=" + acceso + '}';
    }

}
