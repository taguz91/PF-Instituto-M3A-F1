/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    public static List<RolesDelUsuarioMD> SelectAll() {
        String QUERY = "SELECT id_roles_usuarios, id_rol, usu_username FROM \"RolesDelUsuario\"";

        return SelectSimple(QUERY);
    }

    public static List<RolesDelUsuarioMD> SelectWhereUsername(String Aguja) {
        String QUERY = "SELECT id_roles_usuarios, id_rol, usu_username FROM \"RolesDelUsuario\" WHERE usu_username = '" + Aguja + "'";
        return SelectSimple(QUERY);
    }

    private static List<RolesDelUsuarioMD> SelectSimple(String QUERY) {
        List<RolesDelUsuarioMD> lista = new ArrayList<>();

        ResultSet rs = ResourceManager.Query(QUERY);

        try {

            while (rs.next()) {
                RolesDelUsuarioMD roles = new RolesDelUsuarioMD();

                roles.setId(rs.getInt("id_roles_usuarios"));
                roles.setIdRol(rs.getInt("id_rol"));
                roles.setUsername(rs.getString("usu_username"));

                lista.add(roles);
            }

        } catch (SQLException e) {

            System.out.println(e);

        }

        return lista;
    }

}
