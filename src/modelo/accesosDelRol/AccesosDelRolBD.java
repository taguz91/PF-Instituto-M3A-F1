package modelo.accesosDelRol;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import modelo.ConnDBPool;

/**
 *
 * @author MrRainx
 */
public class AccesosDelRolBD extends AccesosDelRolMD {

    private ConnDBPool pool;
    private Connection conn;
    private ResultSet rs;

    public AccesosDelRolBD(int idAccesoRol, int idRol, int idAcceso) {
        super(idAccesoRol, idRol, idAcceso);
    }

    public AccesosDelRolBD() {
    }

    public boolean insertar() {

        String INSERT = "INSERT INTO \"AccesosDelRol\" "
                + "(id_rol,id_acceso)\n"
                + "VALUES(?,?)";

        Map<Integer, Object> parametros = new HashMap<>();
        parametros.put(1, getIdRol());
        parametros.put(2, getIdAcceso());

        conn = pool.getConnection();
        return pool.ejecutar(INSERT, conn, parametros) == null;
    }

    public boolean eliminarWhere(int idRol, int idAcceso) {
        String DELETE = "DELETE FROM \"AccesosDelRol\" \n"
                + "WHERE \n"
                + " id_rol = ? AND id_acceso = ? ";

        Map<Integer, Object> parametros = new HashMap<>();
        parametros.put(1, idRol);
        parametros.put(2, idAcceso);
        conn = pool.getConnection();
        return pool.ejecutar(DELETE, conn, parametros) == null;
    }

}
