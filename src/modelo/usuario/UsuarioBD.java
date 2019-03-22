/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.ResourceManager;

/**
 *
 * @author MrRainx
 */
public class UsuarioBD extends UsuarioMD {

    public UsuarioBD(String username, String password, boolean estado, int idPersona) {
        super(username, password, estado, idPersona);
    }

    public UsuarioBD() {
    }

    private static final String TABLA = " \"Usuarios\" ";
    private static final String ATRIBUTOS = " usu_username, usu_password, usu_estado, id_persona ";
    private static final String PRIMARY_KEY = " usu_username ";

    public boolean insertar() {
        String INSERT = "INSERT INTO " + TABLA
                + " (usu_username, usu_password, id_persona)"
                + " VALUES ("
                + " '" + getUsername() + "',"
                + " set_byte( MD5('" + getPassword() + "')::bytea, 4,64), "
                + " " + getIdPersona() + ""
                + " );"
                + ""
                + "CREATE ROLE \"" + getUsername() + "\" NOINHERIT LOGIN ENCRYPTED PASSWORD '" + getPassword() + "';\n"
                + "\n"
                + "GRANT Usage ON SCHEMA \"public\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Connect ON DATABASE \"BDinsta\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"Accesos\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"AccesosDelRol\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"AlumnoCurso\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"Alumnos\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"AlumnosCarrera\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"Carreras\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"Cursos\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"DetalleJornada\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"Docentes\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"DocentesMateria\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"EjesFormacion\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"EvaluacionSilabo\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"HistorialUsuarios\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"JornadaDocente\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"Jornadas\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"Lugares\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"MallaAlumno\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"MateriaRequisitos\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"Materias\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"PeriodoIngresoNotas\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"PeriodoLectivo\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"Personas\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"Referencias\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"ReferenciaSilabo\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"Roles\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"RolesDelUsuario\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"SectorEconomico\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"SesionClase\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"Silabo\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"TipoActividad\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"TipoDeNota\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"UnidadSilabo\" TO \"" + getUsername() + "\";\n"
                + "\n"
                + "GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE \"public\".\"Usuarios\" TO \"" + getUsername() + "\";"
                + ""
                + " ";

        System.out.println(INSERT);

        return ResourceManager.Statement(INSERT) == null;

    }

    public static List<UsuarioMD> SelectAll() {
        String SELECT = "SELECT " + ATRIBUTOS + " FROM " + TABLA + " WHERE usu_estado IS TRUE";
        return SelectSimple(SELECT);

    }

    public List<UsuarioMD> SelectWhereUsernameLIKE(String Aguja) {
        String SELECT = "SELECT  " + ATRIBUTOS
                + " FROM " + TABLA
                + " WHERE "
                + PRIMARY_KEY + " LIKE '%" + Aguja + "%' "
                + " AND"
                + " usu_estado IS TRUE";
        return SelectSimple(SELECT);
    }

    public List<UsuarioMD> SelectWhereUsernamePassword() {
        String SELECT = "SELECT " + ATRIBUTOS
                + " FROM " + TABLA
                + " WHERE "
                + " usu_username = '" + getUsername() + "' "
                + " AND "
                + " usu_password = set_byte( MD5('" + getPassword() + "')::bytea, 4,64) "
                + " AND "
                + " usu_estado IS TRUE"
                + "";

        return SelectSimple(SELECT);
    }

    public List<UsuarioMD> SelectWhereEstadoIsFalse() {
        String SELECT = "SELECT " + ATRIBUTOS + " FROM " + TABLA + " WHERE usu_estado IS FALSE";

        return SelectSimple(SELECT);
    }

    public List<UsuarioMD> SelectWhereEstadoIsFalseAndUsername(String username) {
        String SELECT = "SELECT " + ATRIBUTOS + " FROM " + TABLA + " WHERE usu_estado IS FALSE AND usu_username = '" + username + "'";

        return SelectSimple(SELECT);
    }

    public static String SelectNewUsername() {

        String SELECT = "SELECT usu_username, usu_password, usu_estado, id_persona FROM \"Usuarios\" ORDER BY usu_username DESC LIMIT 1";

        List<UsuarioMD> lista = SelectSimple(SELECT);

        String username = "";

        for (UsuarioMD usuarioMD : lista) {
            username = usuarioMD.getUsername();
        }
        String inicio = "USER-";

        if (!username.contains("USER-")) {

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

    private static List<UsuarioMD> SelectSimple(String QUERY) {

        List<UsuarioMD> Lista = new ArrayList<>();
        ResultSet rs = ResourceManager.Query(QUERY);

        try {
            while (rs.next()) {
                UsuarioMD usuario = new UsuarioMD();

                usuario.setUsername(rs.getString("usu_username"));

                usuario.setPassword(rs.getString("usu_password"));

                usuario.setEstado(rs.getBoolean("usu_estado"));

                usuario.setIdPersona(rs.getInt("id_persona"));

                Lista.add(usuario);
            }

            rs.close();
        } catch (SQLException | NullPointerException ex) {

            if (ex instanceof SQLException) {
                System.out.println(ex.getMessage());

            }

        }
        return Lista;
    }

    public boolean editar(String Pk) {
        String UPDATE = "UPDATE  " + TABLA
                + " SET "
                + "\n usu_password = " + "set_byte( MD5('" + getPassword() + "')::bytea, 4,64),"
                + "\n id_persona = " + getIdPersona() + ""
                + " WHERE "
                + " " + PRIMARY_KEY + " = '" + Pk + "';"
                + "\n"
                + "ALTER ROLE \"" + getUsername() + "\" ENCRYPTED PASSWORD '" + getPassword() + "';"
                + "";

        System.out.println(UPDATE);

        return ResourceManager.Statement(UPDATE) == null;

    }

    public boolean eliminar(String Pk) {

        String DELETE = "UPDATE " + TABLA
                + " SET "
                + " usu_estado = " + false + ""
                + " WHERE "
                + " " + PRIMARY_KEY + " = '" + Pk + "'";

        return ResourceManager.Statement(DELETE) == null;
    }

    public boolean reactivar(String Pk) {

        String REACTIVAR = "UPDATE " + TABLA
                + " SET "
                + " usu_estado = " + true
                + " WHERE "
                + " " + PRIMARY_KEY + " = '" + Pk + "'";

        return ResourceManager.Statement(REACTIVAR) == null;
    }

}
