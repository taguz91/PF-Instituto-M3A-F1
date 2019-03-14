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

    public UsuarioBD(String username, String password, int idPersona) {
        super(username, password, idPersona);
    }

    public UsuarioBD() {
    }

    public boolean insertar() {
        String INSERT = "INSERT INTO \"Usuarios\"   "
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
        String SELECT = "SELECT\n"
                + "	usu_username,\n"
                + "	usu_password,\n"
                + "	id_persona\n"
                + "FROM\n"
                + "	\"Usuarios\"";

        return SelectSimple(SELECT);

    }

    public List<UsuarioMD> SelectWhereUsernameLIKE(String Aguja) {
        String SELECT = "SELECT  usu_username, usu_password, id_persona"
                + " FROM \"Usuarios\" "
                + " WHERE usu_username LIKE '%" + Aguja + "%' ";
        return SelectSimple(SELECT);
    }

    public List<UsuarioMD> SelectWhereUsernamePassword() {
        String SELECT = "SELECT  usu_username, usu_password, id_persona "
                + " FROM \"Usuarios\" "
                + " WHERE "
                + " usu_username = '" + getUsername() + "' "
                + " AND "
                + " usu_password = set_byte( MD5('" + getPassword() + "')::bytea, 4,64) "
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
        String UPDATE = "UPDATE \"Usuarios\" SET "
                + " usu_username = '" + getUsername() + "', "
                + " usu_password = " + "set_byte( MD5('" + getPassword() + "')::bytea, 4,64),"
                + " id_persona = " + getIdPersona() + ""
                + " WHERE "
                + " id_usuario = '" + Pk + "'";
        return ResourceManager.Statement(UPDATE) == null;

    }

    public boolean eliminar(String Pk) {
        String DELETE = "DELETE FROM \"Usuarios\" WHERE id_usuario = " + Pk;

        return ResourceManager.Statement(DELETE) == null;
    }

}
