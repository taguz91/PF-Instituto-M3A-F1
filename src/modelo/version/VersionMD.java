package modelo.version;

import java.time.LocalDateTime;

/**
 *
 * @author alumno
 */
public class VersionMD {

    private int id;
    private String username;
    private String version;
    private String url;
    private String nombre;
    private String notas;
    private LocalDateTime fecha;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getNombreSinExtension() {
        return nombre.substring(0, nombre.lastIndexOf("."));
    }

    public String getNombreSinExtension(String nombre) {
        return nombre.substring(0, nombre.lastIndexOf("."));
    }
}
