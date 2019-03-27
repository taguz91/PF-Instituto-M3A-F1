/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.silabo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.persona.PersonaMD;
import modelo.usuario.UsuarioMD;
import modelo.pgConect;

import org.postgresql.util.Base64;

/**
 *
 * @author Andres Ullauri
 */
public class dbUsuarios extends UsuarioMD {

    pgConect conecta = new pgConect();

    public dbUsuarios() {
    }

    public dbUsuarios autenticar(String userName, String password) {

        try {
            dbUsuarios dbusuario = null;
            String sql = "SELECT id_persona\n"
                    + "FROM \"Usuarios\"\n"
                    + "WHERE usu_username='" + userName + "' AND usu_password='" + password + "'";
            ResultSet rs = conecta.query(sql);
            while (rs.next()) {
                dbusuario = new dbUsuarios();

                PersonaMD persona = new PersonaMD();
                persona.setIdPersona(rs.getInt(1));

                dbusuario.setIdPersona(persona);

            }

            rs.close();
            return dbusuario;
        } catch (SQLException ex) {
            Logger.getLogger(dbUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

}
