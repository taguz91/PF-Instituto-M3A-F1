package modelo.usuario;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import modelo.ConnDBPool;

/**
 *
 * @author MrRainx
 */
public class RolesDelUsuarioBD extends RolesDelUsuarioMD {

    private ConnDBPool pool;
    private Connection conn;
    private ResultSet rs;

    {
        pool = new ConnDBPool();
    }

    public RolesDelUsuarioBD(int id, int idRol, String username) {
        super(id, idRol, username);
    }

    public RolesDelUsuarioBD() {
    }

    public boolean insertar() {

        String INSERTAR = "INSERT INTO \"RolesDelUsuario\" ( id_rol, usu_username )\n"
                + "VALUES ( ?, ? );";

        Map<Integer, Object> parametros = new HashMap<>();
        parametros.put(1, getIdRol());
        parametros.put(2, getUsername());
        conn = pool.getConnection();
        return pool.ejecutar(INSERTAR, conn, parametros) == null;
    }

    public boolean eliminarWhere(int idRol, String username) {
        String ELIMINAR = "DELETE \n"
                + "FROM\n"
                + "	\"RolesDelUsuario\" \n"
                + "WHERE\n"
                + "	id_rol = ? \n"
                + "	AND usu_username = ?";
        Map<Integer, Object> parametros = new HashMap<>();
        parametros.put(1, idRol);
        parametros.put(2, username);

        conn = pool.getConnection();
        return pool.ejecutar(ELIMINAR, conn, parametros) == null;
    }

}
