package modelo.usuario;

/**
 *
 * @author MrRainx
 */
public class RolesDelUsuarioMD {

    private int id;
    private int idRol;
    private String username;

    public RolesDelUsuarioMD(int id, int idRol, String username) {
        this.id = id;
        this.idRol = idRol;
        this.username = username;
    }

    public RolesDelUsuarioMD() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "RolesDelUsuario{" + "id=" + id + ", idRol=" + idRol + ", username=" + username + '}';
    }

}
