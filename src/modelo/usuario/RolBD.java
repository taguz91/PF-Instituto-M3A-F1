package modelo.usuario;

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
public class RolBD extends RolMD {

    public RolBD(int id, String nombre) {
        super(id, nombre);
    }

    public RolBD() {
    }

    public boolean insertar() {

        String INSERT = "INSERT INTO \"Roles\"(rol_nombre) VALUES('" + getNombre() + "')";

        return ResourceManager.Statement(INSERT) == null;
    }

    public List<RolMD> SelectAll() {

        String SELECT = "SELECT id_rol, rol_nombre FROM \"Roles\"";

        return SelectSimple(SELECT);
    }

    public List<RolMD> SelectWhereNombreLike(String Aguja) {
        String SELECT = "SELECT id_rol, rol_nombre FROM \"Roles\" WHERE rol_nombre LIKE '%" + Aguja + "%'";
        return SelectSimple(SELECT);
    }

    public List<RolMD> SelectWhereUSUARIOusername(UsuarioMD usuario) {
        String SELECT = "SELECT ROL.id_rol, rol_nombre FROM \"Roles\" ROL INNER JOIN \"Usuarios\" USU ON ROL.id_rol = USU.id_rol  WHERE USU.usu_username = '" + usuario.getUsername() + "'";

        return SelectSimple(SELECT);

    }

    public List<RolMD> SelectWhereUSUARIOidUsuario(int idUsuario) {
        String SELECT = "SELECT\n"
                + "	ROL.id_rol,\n"
                + "	rol_nombre \n"
                + "FROM\n"
                + "	\"Roles\" ROL\n"
                + "	INNER JOIN \"Usuarios\" USU ON ROL.id_rol = USU.id_rol \n"
                + "WHERE\n"
                + "	USU.id_usuario = " + idUsuario + "";
        return SelectSimple(SELECT);
    }

    private List<RolMD> SelectSimple(String Query) {
        List<RolMD> Lista = new ArrayList<>();

        ResultSet rs = ResourceManager.Query(Query);

        try {
            while (rs.next()) {
                RolMD rol = new RolMD();

                rol.setId(rs.getInt("id_rol"));
                rol.setNombre(rs.getString("rol_nombre"));

                Lista.add(rol);

            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(RolBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Lista;
    }

    public boolean editar(int Pk) {

        String UPDATE = "UPDATE \"Roles\" \n"
                + "SET id_rol = " + getId() + ",\n"
                + "rol_nombre = '" + getNombre() + "' \n"
                + "WHERE\n"
                + "	id_rol = '" + Pk + "'";

        return ResourceManager.Statement(UPDATE) == null;

    }

    public boolean eliminar(int Pk) {

        String DELETE = "DELETE FROM \"RolesUsuarios\" WHERE id_rol = " + getId() + "";

        return ResourceManager.Statement(DELETE) == null;

    }

}
