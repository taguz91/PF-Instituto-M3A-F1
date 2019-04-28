package modelo.usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.ResourceManager;
import modelo.persona.PersonaMD;

/**
 *
 * @author MrRainx
 */
public final class UsuarioBD extends UsuarioMD {

    public UsuarioBD(String username, String password, boolean estado, PersonaMD idPersona) {
        super(username, password, estado, idPersona);
    }

    public UsuarioBD() {
    }

    public UsuarioBD(UsuarioMD obj) {
        setUsername(obj.getUsername());
        setPassword(obj.getPassword());
        setEstado(obj.isEstado());
        setPersona(obj.getPersona());
    }

    private static final String TABLA = " \"Usuarios\" ";
    private static final String PRIMARY_KEY = " usu_username ";

    public boolean insertar() {

        String INSERT = "INSERT INTO " + TABLA + "\n"
                + " (usu_username, usu_password, id_persona)\n"
                + " VALUES ("
                + " '" + getUsername() + "',\n"
                + " set_byte( MD5('" + getPassword() + "')::bytea, 4,64), \n"
                + " " + getPersona().getIdPersona() + "\n"
                + " );\n"
                + "CREATE ROLE \"" + getUsername() + "\" CREATEROLE LOGIN ENCRYPTED PASSWORD '" + getPassword() + "';\n"
                + "GRANT \"permisos\" TO \"" + getUsername() + "\";"
                + " ";

        System.out.println(INSERT);

        return ResourceManager.Statement(INSERT) == null;

    }

    public static List<UsuarioMD> SelectAll() {
        String SELECT = "SELECT\n"
                + "    \"Usuarios\".usu_username,\n"
                + "    \"Usuarios\".usu_password,\n"
                + "    \"Usuarios\".usu_estado,\n"
                + "    \"Usuarios\".id_persona,\n"
                + "    \"Personas\".persona_identificacion,\n"
                + "    \"Personas\".persona_primer_apellido,\n"
                + "    \"Personas\".persona_segundo_apellido,\n"
                + "    \"Personas\".persona_primer_nombre,\n"
                + "    \"Personas\".persona_segundo_nombre,\n"
                + "    \"Personas\".persona_foto\n"
                + "   FROM (\"Usuarios\"\n"
                + "     JOIN \"Personas\" ON ((\"Usuarios\".id_persona = \"Personas\".id_persona)))"
                + "WHERE\n"
                + "\"public\".\"Usuarios\".usu_estado IS TRUE;";

        return selectSimple(SELECT);

    }

    private static List<UsuarioMD> selectSimple(String QUERY) {
        List<UsuarioMD> lista = new ArrayList<>();

        ResultSet rs = ResourceManager.Query(QUERY);
        try {

            while (rs.next()) {
                UsuarioMD usuario = new UsuarioMD();
                usuario.setUsername(rs.getString("usu_username"));
                usuario.setEstado(rs.getBoolean("usu_estado"));
                usuario.setPassword(rs.getString("usu_password"));

                PersonaMD persona = new PersonaMD();
                persona.setIdPersona(rs.getInt("id_persona"));
                persona.setIdentificacion(rs.getString("persona_identificacion"));
                persona.setPrimerApellido(rs.getString("persona_primer_apellido"));
                persona.setSegundoApellido(rs.getString("persona_segundo_apellido"));
                persona.setPrimerNombre(rs.getString("persona_primer_nombre"));
                persona.setSegundoNombre(rs.getString("persona_segundo_nombre"));

                usuario.setPersona(persona);

                lista.add(usuario);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

        return lista;

    }

    public List<UsuarioMD> SelectWhereUsernamePassword() {
        String SELECT = "SELECT\n"
                + "    \"Usuarios\".usu_username,\n"
                + "    \"Usuarios\".usu_password,\n"
                + "    \"Usuarios\".usu_estado,\n"
                + "    \"Usuarios\".id_persona,\n"
                + "    \"Personas\".persona_identificacion,\n"
                + "    \"Personas\".persona_primer_apellido,\n"
                + "    \"Personas\".persona_segundo_apellido,\n"
                + "    \"Personas\".persona_primer_nombre,\n"
                + "    \"Personas\".persona_segundo_nombre,\n"
                + "    \"Personas\".persona_foto\n"
                + "   FROM (\"Usuarios\"\n"
                + "     JOIN \"Personas\" ON ((\"Usuarios\".id_persona = \"Personas\".id_persona)))"
                + "WHERE\n"
                + "\"public\".\"Usuarios\".usu_username = '" + getUsername() + "' AND\n"
                + "\"public\".\"Usuarios\".usu_password = set_byte( MD5( '" + getPassword() + "' ) :: bytea, 4, 64 ) AND\n"
                + "\"public\".\"Usuarios\".usu_estado IS TRUE;";
        return selectSimple(SELECT);
    }

    public List<UsuarioMD> SelectAllWhereEstadoIsFalse() {
        String SELECT = "SELECT \"Usuarios\".id_usuario,\n"
                + "    \"Usuarios\".usu_username,\n"
                + "    \"Usuarios\".usu_password,\n"
                + "    \"Usuarios\".usu_estado,\n"
                + "    \"Usuarios\".id_persona,\n"
                + "    \"Personas\".persona_identificacion,\n"
                + "    \"Personas\".persona_primer_apellido,\n"
                + "    \"Personas\".persona_segundo_apellido,\n"
                + "    \"Personas\".persona_primer_nombre,\n"
                + "    \"Personas\".persona_segundo_nombre,\n"
                + "    \"Personas\".persona_foto\n"
                + "   FROM (\"Usuarios\"\n"
                + "     JOIN \"Personas\" ON ((\"Usuarios\".id_persona = \"Personas\".id_persona)))"
                + "WHERE\n"
                + "\"public\".\"Usuarios\".usu_estado IS FALSE;";

        return selectSimple(SELECT);
    }

    public boolean editar(String Pk) {
        String UPDATE = "UPDATE  " + TABLA + "\n"
                + " SET \n"
                + "usu_password = " + "set_byte( MD5('" + getPassword() + "')::bytea, 4,64),\n"
                + "id_persona = " + getPersona().getIdPersona() + "\n"
                + " WHERE \n"
                + " " + PRIMARY_KEY + " = '" + Pk + "';\n"
                + "ALTER ROLE \"" + getUsername() + "\" ENCRYPTED PASSWORD '" + getPassword() + "';\n"
                + "";

        System.out.println(UPDATE);

        return ResourceManager.Statement(UPDATE) == null;

    }

    public boolean eliminar(String Pk) {

        String DELETE = "UPDATE " + TABLA + "\n"
                + " SET \n"
                + " usu_estado = " + false + "\n"
                + " WHERE \n"
                + " " + PRIMARY_KEY + " = '" + Pk + "';\n"
                + " ALTER ROLE \"" + Pk + "\" NOLOGIN;"
                + "";

        return ResourceManager.Statement(DELETE) == null;
    }

    public boolean reactivar(String Pk) {

        String REACTIVAR = "UPDATE " + TABLA + "\n"
                + " SET \n"
                + " usu_estado = " + true + "\n"
                + " WHERE \n"
                + " " + PRIMARY_KEY + " = '" + Pk + "';\n"
                + "ALTER ROLE \"" + Pk + "\" LOGIN;"
                + "";

        return ResourceManager.Statement(REACTIVAR) == null;
    }

}
