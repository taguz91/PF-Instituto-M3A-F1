package modelo.accesosDelRol;

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
import modelo.accesos.AccesosBD;
import modelo.usuario.RolBD;

/**
 *
 * @author MrRainx
 */
public class AccesosDelRolBD extends AccesosDelRolMD {

    private ConnDBPool pool;
    private Connection conn;

    {
        pool = new ConnDBPool();
    }

    public AccesosDelRolBD(int id, boolean activo, RolBD rol, AccesosBD acceso) {
        super(id, activo, rol, acceso);
    }

    public AccesosDelRolBD() {
    }

    public List<AccesosDelRolBD> selectWhere(int idRol) {
        String SELECT = "SELECT\n"
                + "	\"public\".\"AccesosDelRol\".id_acceso_del_rol, \n"
                + "	\"public\".\"AccesosDelRol\".acc_activo,\n"
                + "	\"public\".\"Accesos\".acc_nombre\n"
                + "FROM\n"
                + "	\"public\".\"AccesosDelRol\"\n"
                + "	INNER JOIN \"public\".\"Accesos\" ON \"public\".\"AccesosDelRol\".id_acceso = \"public\".\"Accesos\".id_acceso \n"
                + "WHERE\n"
                + "	\"AccesosDelRol\".id_rol = " + idRol;

        List<AccesosDelRolBD> lista = new ArrayList<>();

        conn = pool.getConnection();
        ResultSet rs = pool.ejecutarQuery(SELECT, conn, null);

        try {
            while (rs.next()) {

                AccesosDelRolBD accesoDelRol = new AccesosDelRolBD();
                accesoDelRol.setId(rs.getInt(1));
                accesoDelRol.setActivo(rs.getBoolean(2));

                AccesosBD acceso = new AccesosBD();
                acceso.setNombre(rs.getString(3));
                accesoDelRol.setAcceso(acceso);

                lista.add(accesoDelRol);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            pool.closeStmt().close(rs).close(conn);
        }

        return lista;

    }

    public List<String> selectWhere(int idRol, boolean estado) {
        String SELECT = "SELECT\n"
                + "	\"public\".\"Accesos\".acc_nombre \n"
                + "FROM\n"
                + "	\"public\".\"AccesosDelRol\"\n"
                + "	INNER JOIN \"public\".\"Accesos\" ON \"public\".\"AccesosDelRol\".id_acceso = \"public\".\"Accesos\".id_acceso \n"
                + "WHERE\n"
                + "	\"AccesosDelRol\".id_rol = " + idRol + " \n"
                + "	AND \"AccesosDelRol\".acc_activo IS " + estado;

        List<String> lista = new ArrayList<>();

        conn = pool.getConnection();

        ResultSet rs = pool.ejecutarQuery(SELECT, conn, null);

        try {
            while (rs.next()) {
                lista.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            pool.closeStmt().close(rs).close(conn);
        }

        return lista;
    }

    public boolean editar() {
        String UPDATE = "UPDATE \"AccesosDelRol\" \n"
                + "SET acc_activo = ? \n"
                + "WHERE\n"
                + "	\"AccesosDelRol\".id_acceso_del_rol = ?";

        Map<Integer, Object> parametros = new HashMap<>();
        parametros.put(1, isActivo());
        parametros.put(2, getId());
        conn = pool.getConnection();
        return pool.ejecutar(UPDATE, conn, parametros) == null;
    }

}
