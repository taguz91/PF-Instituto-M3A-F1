package modelo.usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConnDBPool;
import modelo.ResourceManager;

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

        System.out.println(INSERT);

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

        String UPDATE = "UPDATE \"Roles\" "
                + " SET "
                + " id_rol = " + getId()
                + ",rol_nombre = '" + getNombre() + "'"
                + " WHERE"
                + " id_rol = '" + Pk + "'";

        return ResourceManager.Statement(UPDATE) == null;

    }

    public boolean eliminar(int Pk) {

        String DELETE = "UPDATE \"Roles\" "
                + " SET "
                + " rol_estado = " + false
                + " WHERE "
                + " id_rol = " + Pk
                + "";

        return ResourceManager.Statement(DELETE) == null;

    }

    public boolean reactivar(int Pk) {

        String REACTIVAR = "UPDATE \"Roles\" "
                + " SET "
                + " rol_estado = " + false
                + " WHERE "
                + " id_rol = " + Pk
                + "";

        return ResourceManager.Statement(REACTIVAR) == null;

    }
}
