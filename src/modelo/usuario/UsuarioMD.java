package modelo.usuario;

import modelo.persona.PersonaMD;

/**
 *
 * @author MrRainx
 */
public class UsuarioMD {

    private String username;
    private String password;
    private boolean estado;
    private boolean isSuperUser;
    private boolean canCreateRolPostgres;

    private PersonaMD idPersona;

    public UsuarioMD(String username, String password, boolean estado, PersonaMD idPersona) {
        this.username = username;
        this.password = password;
        this.estado = estado;
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

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public PersonaMD getPersona() {
        return idPersona;
    }

    public void setPersona(PersonaMD idPersona) {
        this.idPersona = idPersona;
    }
    
    
    

    @Override
    public String toString() {
        return "UsuarioMD{" + "username=" + username + ", password=" + password + ", estado=" + estado + ", idPersona=" + idPersona + '}';
    }

}
