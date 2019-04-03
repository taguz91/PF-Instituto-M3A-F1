package modelo.usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.ResourceManager;
import modelo.persona.PersonaBD;
import modelo.persona.PersonaMD;

/**
 *
 * @author MrRainx
 */
public class UsuarioBD extends UsuarioMD {

    public UsuarioBD(String username, String password, boolean estado, PersonaMD idPersona) {
        super(username, password, estado, idPersona);
    }

    public UsuarioBD() {
    }

    private static final String TABLA = " \"Usuarios\" ";
    private static final String PRIMARY_KEY = " usu_username ";

    private static void refreshView() {

        ResourceManager.Statement("REFRESH MATERIALIZED VIEW \"Usuarios_Persona\" \n");

    }

    public boolean insertar() {

        String INSERT = "INSERT INTO " + TABLA
                + " (usu_username, usu_password, id_persona)"
                + " VALUES ("
                + " '" + getUsername() + "',"
                + " set_byte( MD5('" + getPassword() + "')::bytea, 4,64), "
                + " " + getPersona().getIdPersona() + ""
                + " );"
                + "CREATE ROLE \"" + getUsername() + "\" CREATEROLE LOGIN ENCRYPTED PASSWORD '" + getPassword() + "';\n"
                + "\n"
                + "GRANT \"permisos\" TO \"" + getUsername() + "\";"
                + " ";

        System.out.println(INSERT);

        return ResourceManager.Statement(INSERT) == null;

    }

    public static List<UsuarioMD> SelectAll() {
        String SELECT = "SELECT\n"
                + "\"public\".\"Usuarios_Persona\".usu_username,\n"
                + "\"public\".\"Usuarios_Persona\".usu_estado,\n"
                + "\"public\".\"Usuarios_Persona\".id_persona,\n"
                + "\"public\".\"Usuarios_Persona\".persona_foto,\n"
                + "\"public\".\"Usuarios_Persona\".persona_identificacion,\n"
                + "\"public\".\"Usuarios_Persona\".persona_primer_apellido,\n"
                + "\"public\".\"Usuarios_Persona\".persona_segundo_apellido,\n"
                + "\"public\".\"Usuarios_Persona\".persona_primer_nombre,\n"
                + "\"public\".\"Usuarios_Persona\".persona_segundo_nombre,\n"
                + "\"public\".\"Usuarios_Persona\".usu_password\n"
                + "FROM\n"
                + "\"public\".\"Usuarios_Persona\"\n"
                + "WHERE \n"
                + "\"public\".\"Usuarios_Persona\".usu_estado = TRUE";

        return selectFromView(SELECT);

    }

    private static List<UsuarioMD> selectFromView(String QUERY) {
        refreshView();
        List<UsuarioMD> lista = new ArrayList<>();

        //System.out.println(QUERY);
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
                + "\"public\".\"Usuarios_Persona\".usu_username,\n"
                + "\"public\".\"Usuarios_Persona\".usu_estado,\n"
                + "\"public\".\"Usuarios_Persona\".id_persona,\n"
                + "\"public\".\"Usuarios_Persona\".persona_foto,\n"
                + "\"public\".\"Usuarios_Persona\".persona_identificacion,\n"
                + "\"public\".\"Usuarios_Persona\".persona_primer_apellido,\n"
                + "\"public\".\"Usuarios_Persona\".persona_segundo_apellido,\n"
                + "\"public\".\"Usuarios_Persona\".persona_primer_nombre,\n"
                + "\"public\".\"Usuarios_Persona\".persona_segundo_nombre,\n"
                + "\"public\".\"Usuarios_Persona\".usu_password\n"
                + "FROM\n"
                + "\"public\".\"Usuarios_Persona\"\n"
                + "WHERE\n"
                + "\"public\".\"Usuarios_Persona\".usu_username = '" + getUsername() + "' AND\n"
                + "\"public\".\"Usuarios_Persona\".usu_password = set_byte( MD5( '" + getPassword() + "' ) :: bytea, 4, 64 ) AND\n"
                + "\"public\".\"Usuarios_Persona\".usu_estado IS TRUE;";

        return selectFromView(SELECT);
    }

    public List<UsuarioMD> SelectAllWhereEstadoIsFalse() {
        String SELECT = "SELECT\n"
                + "\"public\".\"Usuarios_Persona\".usu_username,\n"
                + "\"public\".\"Usuarios_Persona\".usu_estado,\n"
                + "\"public\".\"Usuarios_Persona\".id_persona,\n"
                + "\"public\".\"Usuarios_Persona\".persona_foto,\n"
                + "\"public\".\"Usuarios_Persona\".persona_identificacion,\n"
                + "\"public\".\"Usuarios_Persona\".persona_primer_apellido,\n"
                + "\"public\".\"Usuarios_Persona\".persona_segundo_apellido,\n"
                + "\"public\".\"Usuarios_Persona\".persona_primer_nombre,\n"
                + "\"public\".\"Usuarios_Persona\".persona_segundo_nombre,\n"
                + "\"public\".\"Usuarios_Persona\".usu_password\n"
                + "FROM\n"
                + "\"public\".\"Usuarios_Persona\"\n"
                + "WHERE\n"
                + "\"public\".\"Usuarios_Persona\".usu_estado IS FALSE;";

        return selectFromView(SELECT);
    }

    public static String SelectNewUsername() {

        String SELECT = "SELECT\n"
                + "\"public\".\"Usuarios_Persona\".usu_username,\n"
                + "\"public\".\"Usuarios_Persona\".usu_estado,\n"
                + "\"public\".\"Usuarios_Persona\".id_persona,\n"
                + "\"public\".\"Usuarios_Persona\".persona_foto,\n"
                + "\"public\".\"Usuarios_Persona\".persona_identificacion,\n"
                + "\"public\".\"Usuarios_Persona\".persona_primer_apellido,\n"
                + "\"public\".\"Usuarios_Persona\".persona_segundo_apellido,\n"
                + "\"public\".\"Usuarios_Persona\".persona_primer_nombre,\n"
                + "\"public\".\"Usuarios_Persona\".persona_segundo_nombre,\n"
                + "\"public\".\"Usuarios_Persona\".usu_password\n"
                + "FROM\n"
                + "\"public\".\"Usuarios_Persona\"\n"
                + "WHERE\n"
                + "\"public\".\"Usuarios_Persona\".usu_username LIKE '%USER%'\n"
                + "ORDER BY\n"
                + "\"public\".\"Usuarios_Persona\".usu_username DESC";

        List<UsuarioMD> lista = selectFromView(SELECT);

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
        String UPDATE = "UPDATE  " + TABLA
                + " SET "
                + "\n usu_password = " + "set_byte( MD5('" + getPassword() + "')::bytea, 4,64),"
                + "\n id_persona = " + getPersona() + ""
                + " WHERE "
                + " " + PRIMARY_KEY + " = '" + Pk + "';"
                + "\n"
                + "ALTER ROLE \"" + getUsername() + "\" ENCRYPTED PASSWORD '" + getPassword() + "';"
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
