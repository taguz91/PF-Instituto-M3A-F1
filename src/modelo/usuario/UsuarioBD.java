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
public class UsuarioBD extends UsuarioMD {

    public UsuarioBD(String username, String password, boolean estado, int idPersona) {
        super(username, password, estado, idPersona);
    }

    public UsuarioBD() {
    }

    private final String TABLA = " \"Usuarios\" ";
    private final String ATRIBUTOS = " usu_username, usu_password, usu_estado, id_persona ";
    private final String PRIMARY_KEY = " usu_username ";

    public boolean insertar() {
        String INSERT = "INSERT INTO " + TABLA
                + " (usu_username, usu_password, id_persona)"
                + " VALUES ("
                + " '" + getUsername() + "',"
                + " set_byte( MD5('" + getPassword() + "')::bytea, 4,64), "
                + " " + getIdPersona() + ""
                + " )"
                + " ";

        try {
            return ResourceManager.Statement(INSERT) == null;
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<UsuarioMD> SelectAll() {
        String SELECT = "SELECT " + ATRIBUTOS + " FROM " + TABLA + " WHERE usu_estado = true";

        return SelectSimple(SELECT);

    }

    public List<UsuarioMD> SelectWhereUsernameLIKE(String Aguja) {
        String SELECT = "SELECT  " + ATRIBUTOS
                + " FROM " + TABLA
                + " WHERE "
                + PRIMARY_KEY + " LIKE '%" + Aguja + "%' "
                + " AND"
                + " usu_estado = true";
        return SelectSimple(SELECT);
    }

    public List<UsuarioMD> SelectWhereUsernamePassword() {
        String SELECT = "SELECT " + ATRIBUTOS
                + " FROM " + TABLA
                + " WHERE "
                + " usu_username = '" + getUsername() + "' "
                + " AND "
                + " usu_password = set_byte( MD5('" + getPassword() + "')::bytea, 4,64) "
                + " AND "
                + " usu_estado = true"
                + "";

        return SelectSimple(SELECT);
    }

    private List<UsuarioMD> SelectSimple(String QUERY) {

        List<UsuarioMD> Lista = new ArrayList<>();
        ResultSet rs = ResourceManager.Query(QUERY);

        try {
            while (rs.next()) {
                UsuarioMD usuario = new UsuarioMD();

                usuario.setUsername(rs.getString("usu_username"));

                usuario.setPassword(rs.getString("usu_password"));

                usuario.setEstado(rs.getBoolean("usu_estado"));

                usuario.setIdPersona(rs.getInt("id_persona"));

                Lista.add(usuario);
            }

            rs.close();
        } catch (SQLException | NullPointerException ex) {

            if (ex instanceof SQLException) {
                System.out.println(ex.getMessage());

            }

        }
        return Lista;
    }

    public boolean editar(String Pk) {
        String UPDATE = "UPDATE  " + TABLA
                + " SET "
                + " usu_username = '" + getUsername() + "', "
                + " usu_password = " + "set_byte( MD5('" + getPassword() + "')::bytea, 4,64),"
                + " id_persona = " + getIdPersona() + ""
                + " WHERE "
                + " " + PRIMARY_KEY + " = '" + Pk + "'";
        return ResourceManager.Statement(UPDATE) == null;

    }

    public boolean eliminar(String Pk) {

        String DELETE = "UPDATE " + TABLA
                + " SET "
                + " usu_estado = " + false + ""
                + " WHERE "
                + " " + PRIMARY_KEY + " = '" + Pk + "'";

        return ResourceManager.Statement(DELETE) == null;
    }

}
