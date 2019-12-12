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

    private PersonaMD persona;

    public UsuarioMD() {
        isSuperUser = false;
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
        return persona;
    }

    public void setPersona(PersonaMD persona) {
        this.persona = persona;
    }

    public boolean isIsSuperUser() {
        return isSuperUser;
    }

    public void setIsSuperUser(boolean isSuperUser) {
        this.isSuperUser = isSuperUser;
    }

    @Override
    public String toString() {
        return "UsuarioMD{" + "username=" + username + ", password=" + password + ", estado=" + estado + ", Persona=" + persona + '}';
    }

}
