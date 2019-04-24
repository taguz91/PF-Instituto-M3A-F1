package modelo.usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modelo.ConnDBPool;
import modelo.ConnectionUtils;
import modelo.ResourceManager;
import modelo.persona.PersonaMD;

/**
 *
 * @author MrRainx
 */
public class UsuarioBD extends UsuarioMD {

    private static ConnDBPool pool = null;
    private static Connection conn = null;
    private static PreparedStatement stmt = null;
    private static ResultSet rs = null;

    private static final String TABLA = " \"Usuarios\" ";
    private static final String PRIMARY_KEY = " usu_username ";

    static {
        pool = new ConnDBPool();
        try {
            conn = pool.getConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ConnectionUtils.close(conn);
        }
    }

    public UsuarioBD(String username, String password, boolean estado, PersonaMD idPersona) {
        super(username, password, estado, idPersona);
    }

    public UsuarioBD() {
    }

    public boolean insertar() {

        String INSERT = ""
                + "INSERT INTO " + TABLA + "\n"
                + " (usu_username, usu_password, id_persona)\n"
                + " VALUES (?, set_byte( MD5( ? ) :: bytea, 4, 64 ), ? );\n"
                + "CREATE ROLE \"" + getUsername() + "\" LOGIN ENCRYPTED PASSWORD '" + getPassword() + "';\n"
                + "GRANT \"permisos\" TO \"" + getUsername() + "\";"
                + " ";

        Map<Integer, Object> parametros = new HashMap<>();

        parametros.put(1, getUsername());
        parametros.put(2, getPassword());
        parametros.put(3, getPersona().getIdPersona());
        System.out.println();

        try {
            return ConnectionUtils.insert(INSERT, stmt, pool.getConnection(), parametros);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    public static List<UsuarioMD> selectAll() {
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
                + "     JOIN \"Personas\" ON ((\"Usuarios\".id_persona = \"Personas\".id_persona)))\n"
                + "WHERE\n"
                + "\"public\".\"Usuarios\".usu_estado IS TRUE;";

        return selectSimple(SELECT);

    }

    private static List<UsuarioMD> selectSimple(String QUERY) {
        List<UsuarioMD> lista = new ArrayList<>();
        try {
            conn = pool.getConnection();
            stmt = conn.prepareStatement(QUERY);
            System.out.println(stmt);
            rs = stmt.executeQuery();

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
        } finally {
            ConnectionUtils.close(conn);
            ConnectionUtils.close(stmt);
            ConnectionUtils.close(rs);
        }
        return lista;
    }

    public UsuarioBD selectWhereUsernamePassword() {
        String SELECT = "SELECT\n"
                + "\"public\".\"Usuarios\".id_persona,\n"
                + "\"public\".\"Personas\".persona_identificacion,\n"
                + "\"public\".\"Personas\".persona_primer_apellido,\n"
                + "\"public\".\"Personas\".persona_segundo_apellido,\n"
                + "\"public\".\"Personas\".persona_primer_nombre,\n"
                + "\"public\".\"Personas\".persona_segundo_nombre\n"
                + "FROM\n"
                + "\"public\".\"Usuarios\"\n"
                + "INNER JOIN \"public\".\"Personas\" ON \"public\".\"Usuarios\".id_persona = \"public\".\"Personas\".id_persona\n"
                + "WHERE\n"
                + "\"public\".\"Usuarios\".usu_username = ? AND\n"
                + "\"public\".\"Usuarios\".usu_password = set_byte( MD5( ? ) :: bytea, 4, 64 ) AND\n"
                + "\"public\".\"Usuarios\".usu_estado IS TRUE;";
        UsuarioBD usuario = null;
        try {
            conn = pool.getConnection();

            stmt = conn.prepareStatement(SELECT);

            stmt.setString(1, getUsername());
            stmt.setString(2, getPassword());

            rs = stmt.executeQuery();

            while (rs.next()) {
                usuario = new UsuarioBD();
                usuario.setUsername(getUsername());

                PersonaMD persona = new PersonaMD();

                persona.setIdPersona(rs.getInt("id_persona"));
                persona.setIdentificacion(rs.getString("persona_identificacion"));
                persona.setPrimerApellido(rs.getString("persona_primer_apellido"));
                persona.setSegundoApellido(rs.getString("persona_segundo_apellido"));
                persona.setPrimerNombre(rs.getString("persona_primer_nombre"));
                persona.setSegundoNombre(rs.getString("persona_segundo_nombre"));

                usuario.setPersona(persona);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ConnectionUtils.close(rs);
            ConnectionUtils.close(conn);
            ConnectionUtils.close(stmt);
        }

        return usuario;
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

    public static String SelectNewUsername() {

        String SELECT = "SELECT\n"
                + "    \"Usuarios\".usu_username\n"
                + "   FROM " + TABLA + "\n"
                + "WHERE\n"
                + "\"public\".\"Usuarios\".usu_username LIKE '%USER%'\n"
                + "ORDER BY\n"
                + "\"public\".\"Usuarios\".usu_username";

        ResultSet rs = ResourceManager.Query(SELECT);
        List<UsuarioMD> lista = new ArrayList<>();

        try {
            while (rs.next()) {
                UsuarioMD usuario = new UsuarioMD();
                usuario.setUsername(rs.getString("usu_username"));
                lista.add(usuario);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String username = "";

        for (UsuarioMD usuarioMD : lista) {
            username = usuarioMD.getUsername();
        }
        String inicio = "USER-";

        if (!username.contains("USER")) {

            username = inicio + "0001";

        } else {
            username = username.substring(5);

            int numero = Integer.parseInt(username);
            numero++;

            if (numero < 10) {
                username = inicio + "000" + numero;
            } else if (numero < 99 && numero > 10) {
                username = inicio + "00" + numero;
            } else if (numero >= 99 & numero < 999) {
                username = inicio + "0" + numero;
            } else {
                username = inicio + numero;
            }

        }

        return username;
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
