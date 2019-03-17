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

    private static String TABLA = " \"RolesDelUsuario\" ";
    private static String ATRIBUTOS = " id_roles_usuarios, id_rol, usu_username ";
    private static String PRIMARY_KEY = " id_roles_usuarios ";

    public boolean insertar() {

        String INSERTAR = "INSERT INTO " + TABLA
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
        String QUERY = "SELECT " + ATRIBUTOS + " FROM " + TABLA;

        return SelectSimple(QUERY);
    }

    public static List<RolesDelUsuarioMD> SelectWhereUsername(String Aguja) {
        String QUERY = "SELECT " + ATRIBUTOS + " FROM " + TABLA + " WHERE usu_username = '" + Aguja + "'";
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

    public boolean eliminar(int primaryKey) {

        String ELIMINAR = "DELETE FROM " + TABLA + " WHERE " + PRIMARY_KEY + " = " + primaryKey;

        return ResourceManager.Statement(ELIMINAR) == null;

    }

    public boolean eliminarWhere(int idRol, String username) {
        String ELIMINAR = "DELETE FROM " + TABLA + ""
                + " WHERE "
                + " id_rol = " + idRol
                + " AND "
                + " usu_username = '" + username + "'"
                + "";

        return ResourceManager.Statement(ELIMINAR) == null;
    }

}
