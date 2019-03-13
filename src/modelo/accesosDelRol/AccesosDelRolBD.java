package modelo.accesosDelRol;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ResourceManager;

/**
 *
 * @author MrRainx
 */
public class AccesosDelRolBD extends AccesosDelRolMD {

    public AccesosDelRolBD(int idAccesoRol, int idRol, int idAcceso) {
        super(idAccesoRol, idRol, idAcceso);
    }

    public AccesosDelRolBD() {
    }

    public boolean insertar() {

        String INSERT = "INSERT INTO \"AccesosDelRol\" "
                + "(id_rol,id_acceso)"
                + "VALUES("
                + "" + getIdRol() + ","
                + "" + getIdAcceso() + ""
                + ")"
                + "";

        return ResourceManager.Statement(INSERT) == null;
    }

    public List<AccesosDelRolMD> SelectAll() {

        String SELECT = "SELECT id_acceso_del_rol, id_rol, id_acceso  FROM \"AccesosDelRol\" ";

        return SelectSimple(SELECT);

    }

    public static List<AccesosDelRolMD> SelectWhereIdRol(int Aguja) {

        String SELECT = "SELECT id_acceso_del_rol, id_rol, id_acceso  FROM \"AccesosDelRol\" "
                + " WHERE "
                + " id_rol = " + Aguja
                + "";

        return SelectSimple(SELECT);

    }

    public List<AccesosDelRolMD> SelectWhereIdAcceso(int Aguja) {

        String SELECT = "SELECT id_acceso_del_rol, id_rol, id_acceso  FROM \"AccesosDelRol\" "
                + " WHERE "
                + " id_acceso = " + Aguja
                + "";

        System.out.println(SELECT);

        return SelectSimple(SELECT);

    }

    public List<AccesosDelRolMD> SelectWhereAccesoNombreLike(String NombreAcceso) {

        String SELECT = "SELECT\n"
                + "	id_acceso_del_rol,\n"
                + "	id_rol,\n"
                + "	PER_ROL.id_acceso \n"
                + "FROM\n"
                + "	\"AccesosDelRol\" PER_ROL\n"
                + "	INNER JOIN \"Accesos\" ACCE ON PER_ROL.id_acceso = ACCE.id_acceso \n"
                + "WHERE\n"
                + "	ACCE.acc_nombre LIKE'%Usuarios_SELECT'";

        return SelectSimple(SELECT);

    }

    public int SelectCount() {

        String SELECT = "SELECT count(*) FROM \"AccesosDelRol\"";

        int registros = 0;

        ResultSet rs = ResourceManager.Query(SELECT);

        try {
            while (rs.next()) {

                registros = rs.getInt("count");

            }
        } catch (SQLException ex) {
            Logger.getLogger(AccesosDelRolBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return registros;
    }

    public boolean editar(int Pk) {

        String UPDATE = "UPDATE \"AccesosDelRol\" \n"
                + "SET id_acceso_del_rol = " + getIdAccesoRol() + ",\n"
                + "id_rol = " + getIdRol() + ",\n"
                + "id_acceso = " + getIdAcceso() + " \n"
                + "WHERE\n"
                + "	id_acceso_del_rol = " + Pk;

        return ResourceManager.Statement(UPDATE) == null;

    }

    public boolean eliminar(int Pk) {

        String DELETE = "DELETE FROM \"AccesosDelRol\" WHERE id_acceso_del_rol = " + Pk;

        return ResourceManager.Statement(DELETE) == null;

    }

    public boolean eliminarWhere(int idRol, int idAcceso) {
        String DELETE = "DELETE \n"
                + "FROM\n"
                + "	\"AccesosDelRol\" \n"
                + "WHERE id_rol = " + idRol + " \n"
                + "	AND id_acceso = " + idAcceso;

        //System.out.println(DELETE);
        return ResourceManager.Statement(DELETE) == null;
    }

    private static List<AccesosDelRolMD> SelectSimple(String QUERY) {
        List<AccesosDelRolMD> Lista = new ArrayList<>();

        ResultSet rs = ResourceManager.Query(QUERY);

        try {

            while (rs.next()) {

                AccesosDelRolMD accesos = new AccesosDelRolMD();

                accesos.setIdAccesoRol(rs.getInt("id_acceso_del_rol"));
                accesos.setIdRol(rs.getInt("id_rol"));
                accesos.setIdAcceso(rs.getInt("id_acceso"));

                Lista.add(accesos);

            }
            rs.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Lista;
    }

}
