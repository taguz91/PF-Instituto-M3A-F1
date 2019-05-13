package modelo.usuario;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConnDBPool;

/**
 *
 * @author MrRainx
 */
public class RolBD extends RolMD {

    private static ConnDBPool pool;
    private static Connection conn;
    private static ResultSet rs;

    static {
        pool = new ConnDBPool();
    }

    public RolBD(int id, String nombre, String observaciones, boolean estado) {
        super(id, nombre, observaciones, estado);
    }

    public RolBD() {
    }

    private static String ATRIBUTOS = " id_rol, rol_nombre, rol_observaciones, rol_estado ";

    public boolean insertar() {

        String INSERT = "INSERT INTO \"Roles\"(rol_nombre,rol_observaciones) VALUES(?, ?)";

        Map<Integer, Object> parametros = new HashMap<>();
        parametros.put(1, getNombre());
        parametros.put(2, getObservaciones());
        conn = pool.getConnection();
        return pool.ejecutar(INSERT, conn, parametros) == null;
    }

    public static List<RolMD> selectAll() {

        String SELECT = "SELECT " + ATRIBUTOS + " FROM \"Roles\" WHERE rol_nombre != 'ROOT'";

        return SelectSimple(SELECT);
    }

    public static List<RolMD> SelectWhereNombreLike(String Aguja) {
        String SELECT = "SELECT " + ATRIBUTOS + " FROM \"Roles\" WHERE rol_nombre LIKE '%" + Aguja + "%'";
        return SelectSimple(SELECT);
    }

    public static List<RolMD> SelectWhereUSUARIOusername(String username) {
        String SELECT = "SELECT  " + ATRIBUTOS + " FROM \"Roles\" JOIN \"RolesDelUsuario\" USING(id_rol) WHERE usu_username = '" + username + "'";
        return SelectSimple(SELECT);
    }

    private static List<RolMD> SelectSimple(String Query) {
        List<RolMD> Lista = new ArrayList<>();

        try {
            conn = pool.getConnection();
            rs = pool.ejecutarQuery(Query, conn, null);
            while (rs.next()) {
                RolMD rol = new RolMD();

                rol.setId(rs.getInt("id_rol"));
                rol.setNombre(rs.getString("rol_nombre"));
                rol.setObservaciones(rs.getString("rol_observaciones"));
                rol.setEstado(rs.getBoolean("rol_estado"));

                Lista.add(rol);

            }
        } catch (SQLException ex) {
            Logger.getLogger(RolBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            pool.close(conn);
        }

        return Lista;
    }

    public boolean editar(int Pk) {

        String UPDATE = "UPDATE \"Roles\" \n"
                + "SET \n"
                + " id_rol = ?,\n"
                + " rol_nombre = ?\n"
                + " WHERE\n"
                + " id_rol = ?";

        Map<Integer, Object> parametros = new HashMap<>();
        parametros.put(1, getId());
        parametros.put(2, getNombre());
        parametros.put(3, Pk);

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

        return pool.ejecutar(DELETE, conn, parametros) == null;

    }

}
