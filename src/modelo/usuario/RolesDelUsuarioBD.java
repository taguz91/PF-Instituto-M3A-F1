package modelo.usuario;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modelo.ConnDBPool;

/**
 *
 * @author MrRainx
 */
public class RolesDelUsuarioBD extends RolesDelUsuarioMD {

    private static ConnDBPool pool;
    private static Connection conn;
    private static ResultSet rs;

    static {
        pool = new ConnDBPool();
    }

    public RolesDelUsuarioBD(int id, int idRol, String username) {
        super(id, idRol, username);
    }

    public RolesDelUsuarioBD() {
    }

    public boolean insertar() {

        String INSERTAR = "INSERT INTO \"RolesDelUsuario\" ( id_rol, usu_username )\n"
                + "VALUES\n"
                + "	( ?, ? );";

        Map<Integer, Object> parametros = new HashMap<>();
        parametros.put(1, getIdRol());
        parametros.put(2, getUsername());
        conn = pool.getConnection();
        return pool.ejecutar(INSERTAR, conn, parametros) == null;
    }

    public static List<RolesDelUsuarioMD> SelectAll() {
        String QUERY = "SELECT\n"
                + "	\"public\".\"RolesDelUsuario\".id_roles_usuarios,\n"
                + "	\"public\".\"RolesDelUsuario\".id_rol,\n"
                + "	\"public\".\"RolesDelUsuario\".usu_username \n"
                + "FROM\n"
                + "	\"public\".\"RolesDelUsuario\"";

        return SelectSimple(QUERY, null);
    }

    public static List<RolesDelUsuarioMD> SelectWhereUsername(String Aguja) {
        String QUERY = "SELECT\n"
                + "	\"public\".\"RolesDelUsuario\".id_roles_usuarios,\n"
                + "	\"public\".\"RolesDelUsuario\".id_rol,\n"
                + "	\"public\".\"RolesDelUsuario\".usu_username \n"
                + "FROM\n"
                + "	\"public\".\"RolesDelUsuario\"\n"
                + " WHERE usu_username = '?'";
        Map<Integer, Object> parametros = new HashMap<>();
        parametros.put(1, Aguja);

        return SelectSimple(QUERY, parametros);
    }

    private static List<RolesDelUsuarioMD> SelectSimple(String QUERY, Map<Integer, Object> parametros) {
        List<RolesDelUsuarioMD> lista = new ArrayList<>();
        conn = pool.getConnection();
        rs = pool.ejecutarQuery(QUERY, conn, parametros);
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
        } finally {
            pool.close(conn);
        }

        return lista;
    }

    public boolean eliminarWhere(int idRol, String username) {
        String ELIMINAR = "DELETE \n"
                + "FROM\n"
                + "	\"RolesDelUsuario\" \n"
                + "WHERE\n"
                + "	id_rol = ? \n"
                + "	AND usu_username = '?'";
        Map<Integer, Object> parametros = new HashMap<>();
        parametros.put(1, idRol);
        parametros.put(2, username);

        conn = pool.getConnection();
        return pool.ejecutar(ELIMINAR, conn, parametros) == null;
    }

}
