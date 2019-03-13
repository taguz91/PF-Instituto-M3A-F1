package modelo.usuario;

import modelo.accesos.AccesosMD;

/**
 *
 * @author MrRainx
 */
public class UsuarioMD {

    private String username;
    private String password;
    private int idPersona;
    
    

    public UsuarioMD(String username, String password, int idPersona) {
        this.username = username;
        this.password = password;
        this.idPersona = idPersona;
    }

    public UsuarioMD() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    @Override
    public String toString() {
        return "UsuarioMD{" + "username=" + username + ", password=" + password + ", idPersona=" + idPersona + '}';
    }

    public Iterable<AccesosMD> getPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
