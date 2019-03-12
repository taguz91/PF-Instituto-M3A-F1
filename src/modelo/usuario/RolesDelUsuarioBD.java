/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.usuario;

import java.util.ArrayList;
import java.util.List;
import modelo.ResourceManager;

/**
 *
 * @author MrRainx
 */
public class RolesDelUsuarioBD extends RolesDelUsuarioMD {

    public RolesDelUsuarioBD(int id, int idRol, String username) {
        super(id, idRol, username);
    }

    public RolesDelUsuarioBD() {
    }

    public boolean insertar() {

        String INSERTAR = "INSERT INTO \"RolesDelUsuario\""
                + " (id_rol, usu_username)"
                + " VALUES"
                + "("
                + " " + getIdRol()
                + ", '" + getUsername() + "'"
                + ")"
                + "";

        return ResourceManager.Statement(INSERTAR) == null;
    }

    public static List<RolesDelUsuarioMD> SelectSimple(String QUERY) {
        List<RolesDelUsuarioMD> lista = new ArrayList<>();

        return lista;
    }

}
