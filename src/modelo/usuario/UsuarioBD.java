package modelo.usuario;

import controlador.Libraries.ImgLib;
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
import modelo.persona.PersonaMD;
import modelo.silabo.NEWSilaboBD;

/**
 *
 * @author MrRainx
 */
public final class UsuarioBD extends UsuarioMD {

    private final ConnDBPool pool;
    private Connection conn;
    private ResultSet rs;

    {
        pool = new ConnDBPool();
    }

    public UsuarioBD() {
    }

    public UsuarioBD(UsuarioMD obj) {
        setUsername(obj.getUsername());
        setPassword(obj.getPassword());
        setEstado(obj.isEstado());
        setPersona(obj.getPersona());
        setIsSuperUser(obj.isIsSuperUser());
    }

    public Map<String, Object> insertar() {
        Map context = new HashMap();
        Map<Integer, Object> parametros = new HashMap<>();

        String INSERT = ""
                + "INSERT INTO \"Usuarios\" \n"
                + " (usu_username, usu_password, id_persona, usus_is_superuser)\n"
                + " VALUES (?, set_byte( MD5( ? ) :: bytea, 4, 64 ), ?, ? );\n";

        parametros.put(1, getUsername());
        parametros.put(2, getPassword());
        parametros.put(3, getPersona().getIdPersona());
        parametros.put(4, isIsSuperUser());

        SQLException error = pool.ejecutar(INSERT, parametros);

        context.put("value", (error == null));
        context.put("error", error);

        return context;

    }

    public List<UsuarioMD> selectAll() {
        String SELECT = "SELECT\n"
                + "    \"Usuarios\".usu_username,\n"
                + "    \"Usuarios\".usu_password,\n"
                + "    \"Usuarios\".usu_estado,\n"
                + "    \"Usuarios\".id_persona,\n"
                + "    \"Usuarios\".usu_is_superuser,\n"
                + "    \"Personas\".persona_identificacion,\n"
                + "    \"Personas\".persona_primer_apellido,\n"
                + "    \"Personas\".persona_segundo_apellido,\n"
                + "    \"Personas\".persona_primer_nombre,\n"
                + "    \"Personas\".persona_segundo_nombre\n"
                + "   FROM (\"Usuarios\"\n"
                + "     JOIN \"Personas\" ON ((\"Usuarios\".id_persona = \"Personas\".id_persona)))\n"
                + "WHERE\n"
                + "\"Usuarios\".usu_estado IS TRUE \n"
                + "ORDER BY usu_username";

        return selectSimple(SELECT, null);

    }

    private List<UsuarioMD> selectSimple(String QUERY, Map<Integer, Object> parametros) {
        List<UsuarioMD> lista = new ArrayList<>();
        try {
            conn = pool.getConnection();
            rs = pool.ejecutarQuery(QUERY, conn, parametros);

            while (rs.next()) {
                UsuarioMD usuario = new UsuarioMD();
                usuario.setUsername(rs.getString("usu_username"));
                usuario.setEstado(rs.getBoolean("usu_estado"));
                usuario.setPassword(rs.getString("usu_password"));

                usuario.setIsSuperUser(rs.getBoolean("usu_is_superuser"));

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
            Logger.getLogger(UsuarioBD.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            pool.close(conn);
        }
        return lista;
    }

    public UsuarioBD selectWhereUsernamePassword() {
        String SELECT = "SELECT\n"
                + "\"Usuarios\".id_persona,\n"
                + "\"Personas\".persona_identificacion,\n"
                + "\"Personas\".persona_primer_apellido,\n"
                + "\"Personas\".persona_segundo_apellido,\n"
                + "\"Personas\".persona_primer_nombre,\n"
                + "\"Personas\".persona_segundo_nombre,\n"
                + "\"Personas\".persona_foto,\n"
                + "\"Usuarios\".usu_is_superuser\n"
                + "FROM\n"
                + "\"Usuarios\"\n"
                + "INNER JOIN \"Personas\" ON \"Usuarios\".id_persona = \"Personas\".id_persona\n"
                + "WHERE\n"
                + "\"Usuarios\".usu_username = ? AND\n"
                + "\"Usuarios\".usu_password = set_byte( MD5( ? ) :: bytea, 4, 64 ) AND\n"
                + "\"Usuarios\".usu_estado IS TRUE;";
        UsuarioBD usuario = null;
        try {

            Map<Integer, Object> parametros = new HashMap<>();

            parametros.put(1, getUsername());
            parametros.put(2, getPassword());
            conn = pool.getConnection();
            rs = pool.ejecutarQuery(SELECT, conn, parametros);

            while (rs.next()) {

                usuario = new UsuarioBD();
                usuario.setUsername(getUsername());
                usuario.setIsSuperUser(rs.getBoolean("usu_is_superuser"));

                PersonaMD persona = new PersonaMD();

                persona.setIdPersona(rs.getInt("id_persona"));
                persona.setIdentificacion(rs.getString("persona_identificacion"));
                persona.setPrimerApellido(rs.getString("persona_primer_apellido"));
                persona.setSegundoApellido(rs.getString("persona_segundo_apellido"));
                persona.setPrimerNombre(rs.getString("persona_primer_nombre"));
                persona.setSegundoNombre(rs.getString("persona_segundo_nombre"));

                persona.setFoto(ImgLib.byteToImage(rs.getBytes("persona_foto")));

                usuario.setPersona(persona);

            }
        } catch (SQLException e) {
            Logger.getLogger(NEWSilaboBD.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            pool.closeStmt().close(rs).close(conn);
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
                + "\"Usuarios\".usu_estado IS FALSE;";

        return selectSimple(SELECT, null);
    }

    public boolean editar(String Pk) {
        String UPDATE = "UPDATE  \"Usuarios\" \n"
                + " SET \n"
                + "usu_username = ? ,\n"
                + "usu_password = set_byte( MD5( ? )::bytea, 4,64),\n"
                + "id_persona = ? ,\n"
                + "usu_is_superuser = ? \n"
                + " WHERE \n"
                + " usu_username = ?;\n";

        Map<Integer, Object> parametros = new HashMap<>();

        parametros.put(1, getUsername());
        parametros.put(2, getPassword());
        parametros.put(3, getPersona().getIdPersona());
        parametros.put(4, isIsSuperUser());
        parametros.put(5, Pk);

        System.out.println("---->" + isIsSuperUser());
        return pool.ejecutar(UPDATE, parametros) == null;

    }

    public boolean cambiarEstado(String Pk, boolean estado) {

        String DELETE = "UPDATE \"Usuarios\" \n"
                + " SET \n"
                + " usu_estado = ?\n"
                + " WHERE \n"
                + " usu_username = ? ;\n"
                + "";

        Map<Integer, Object> parametros = new HashMap<>();
        parametros.put(1, estado);
        parametros.put(2, Pk);
        return pool.ejecutar(DELETE, parametros) == null;
    }

}
