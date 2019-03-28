package modelo.usuario;

import java.time.LocalDateTime;

/**
 *
 * @author arman
 */
public class HistorialUsuarioMD {

    private int id; 
    private String username; 
    private LocalDateTime fechaAccion;
    private String tipoAccion; 
    private String nombreTbl; 
    private int pkTbl; 
    private String observacion; 
    
    public HistorialUsuarioMD() {
    }

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

    public LocalDateTime getFechaAccion() {
        return fechaAccion;
    }

    public void setFechaAccion(LocalDateTime fechaAccion) {
        this.fechaAccion = fechaAccion;
    }

    public String getTipoAccion() {
        return tipoAccion;
    }

    public void setTipoAccion(String tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

    public String getNombreTbl() {
        return nombreTbl;
    }

    public void setNombreTbl(String nombreTbl) {
        this.nombreTbl = nombreTbl;
    }

    public int getPkTbl() {
        return pkTbl;
    }

    public void setPkTbl(int pkTbl) {
        this.pkTbl = pkTbl;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    
    
}
