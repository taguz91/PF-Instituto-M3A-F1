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
public class RolBD extends RolMD {

    private ConnDBPool pool;
    private Connection conn;
    private ResultSet rs;

    {
        pool = new ConnDBPool();
    }

    public RolBD(int id, String nombre, String observaciones, boolean estado) {
        super(id, nombre, observaciones, estado);
    }

    public RolBD() {
    }

    public boolean insertar() {

        String INSERT = "INSERT INTO \"Roles\" ( rol_nombre, rol_observaciones )\n"
                + "VALUES\n"
                + "	( ?, ? );";

        Map<Integer, Object> parametros = new HashMap<>();
        parametros.put(1, getNombre());
        parametros.put(2, getObservaciones());
        conn = pool.getConnection();
        return pool.ejecutar(INSERT, conn, parametros) == null;
    }

    public List<RolBD> selectAll() {

        String SELECT = "SELECT\n"
                + "	\"public\".\"Roles\".id_rol,\n"
                + "	\"public\".\"Roles\".rol_nombre,\n"
                + "	\"public\".\"Roles\".rol_observaciones,\n"
                + "	\"public\".\"Roles\".rol_estado \n"
                + "FROM\n"
                + "	\"public\".\"Roles\" \n"
                + "WHERE\n"
                + "	rol_nombre NOT IN ( 'ROOT', 'DEV' );";

        return SelectSimple(SELECT);
    }

    public List<RolBD> SelectWhereUSUARIOusername(String username) {
        String SELECT = "SELECT\n"
                + "\"public\".\"Roles\".id_rol,\n"
                + "\"public\".\"Roles\".rol_nombre,\n"
                + "\"public\".\"Roles\".rol_observaciones,\n"
                + "\"public\".\"Roles\".rol_estado\n"
                + "FROM\n"
                + "\"public\".\"Roles\"\n"
                + "INNER JOIN \"public\".\"RolesDelUsuario\" ON \"public\".\"RolesDelUsuario\".id_rol = \"public\".\"Roles\".id_rol\n"
                + "WHERE\n"
                + "\"public\".\"RolesDelUsuario\".usu_username = '" + username + "';";
        return SelectSimple(SELECT);
    }

    private List<RolBD> SelectSimple(String Query) {
        List<RolBD> Lista = new ArrayList<>();

        try {
            conn = pool.getConnection();
            rs = pool.ejecutarQuery(Query, conn, null);

            while (rs.next()) {
                RolBD rol = new RolBD();

                rol.setId(rs.getInt("id_rol"));
                rol.setNombre(rs.getString("rol_nombre"));
                rol.setObservaciones(rs.getString("rol_observaciones"));
                rol.setEstado(rs.getBoolean("rol_estado"));

                Lista.add(rol);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            pool.closeStmt().close(rs).close(conn);
        }

        return Lista;
    }

    public boolean editar(int Pk) {

        String UPDATE = "UPDATE \"Roles\" \n"
                + "SET \n"
                + " rol_nombre = ?,\n"
                + " rol_observaciones = ?\n"
                + " WHERE\n"
                + " id_rol = ?";

        Map<Integer, Object> parametros = new HashMap<>();
        parametros.put(1, getNombre());
        parametros.put(2, getObservaciones());
        parametros.put(3, Pk);
        conn = pool.getConnection();
        return pool.ejecutar(UPDATE, conn, parametros) == null;

    }

    public boolean eliminar(int Pk) {

        String DELETE = "UPDATE \"Roles\" "
                + "SET "
                + " rol_estado = ?\n"
                + "WHERE "
                + " id_rol = ?";

        Map<Integer, Object> parametros = new HashMap<>();
        parametros.put(1, false);
        parametros.put(2, Pk);
        conn = pool.getConnection();
        return pool.ejecutar(DELETE, conn, parametros) == null;

    }

}
