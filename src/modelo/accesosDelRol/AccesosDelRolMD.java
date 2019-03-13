/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.accesosDelRol;

/**
 *
 * @author MrRainx
 */
public class AccesosDelRolMD {

    private int idAccesoRol;
    private int idRol;
    private int idAcceso;

    public AccesosDelRolMD(int idAccesoRol, int idRol, int idAcceso) {
        this.idAccesoRol = idAccesoRol;
        this.idRol = idRol;
        this.idAcceso = idAcceso;
    }

    public AccesosDelRolMD() {
    }

    public int getIdAccesoRol() {
        return idAccesoRol;
    }

    public void setIdAccesoRol(int idAccesoRol) {
        this.idAccesoRol = idAccesoRol;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public int getIdAcceso() {
        return idAcceso;
    }

    public void setIdAcceso(int idAcceso) {
        this.idAcceso = idAcceso;
    }

    @Override
    public String toString() {
        return "AccesosDelRolMD{" + "idAccesoRol=" + idAccesoRol + ", idRol=" + idRol + ", idAcceso=" + idAcceso + '}';
    }

}
