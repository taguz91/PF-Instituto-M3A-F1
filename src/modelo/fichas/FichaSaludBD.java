/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.fichas;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConnDBPool;
import modelo.persona.PersonaMD;

/**
 *
 * @author MrRainx
 */
public class FichaSaludBD extends FichaSaludMD {

    private final ConnDBPool pool;
    private Connection conn;
    private ResultSet rs;

    {
        pool = new ConnDBPool();
    }

    public List<FichaSaludMD> getFichas() {

        String SELECT = "SELECT\n"
                + "\"FichaSalud\".\"id\",\n"
                + "\"FichaSalud\".estado_revision,\n"
                + "\"FichaSalud\".estado_envio,\n"
                + "\"Personas\".persona_identificacion,\n"
                + "\"Personas\".persona_primer_apellido,\n"
                + "\"Personas\".persona_segundo_apellido,\n"
                + "\"Personas\".persona_primer_nombre,\n"
                + "\"Personas\".persona_segundo_nombre,\n"
                + "\"FichaSalud\".ingreso_fin_extend,\n"
                + "\"FichaSalud\".ingreso_inicio_extend\n"
                + "FROM\n"
                + "\"FichaSalud\"\n"
                + "INNER JOIN \"Personas\" ON \"FichaSalud\".persona_id = \"Personas\".id_persona";
        List<FichaSaludMD> lista = new ArrayList<>();

        try {
            conn = pool.getConnection();
            rs = pool.ejecutarQuery(SELECT, conn, null);
            while (rs.next()) {

                FichaSaludMD ficha = new FichaSaludMD();
                ficha.setId(rs.getInt("id"));
                ficha.setEstadoRevision(rs.getBoolean("estado_revision"));
                ficha.setEstadoEnvio(rs.getBoolean("estado_envio"));

                PersonaMD persona = new PersonaMD();
                persona.setIdentificacion(rs.getString("persona_identificacion"));
                persona.setPrimerApellido(rs.getString("persona_primer_apellido"));
                persona.setSegundoNombre(rs.getString("persona_segundo_apellido"));
                persona.setPrimerNombre(rs.getString("persona_primer_nombre"));
                persona.setSegundoNombre(rs.getString("persona_segundo_nombre"));
                
                ficha.setPersona(persona);

                Date fechaInicio = rs.getDate("ingreso_fin_extend");
                if (fechaInicio != null) {
                    ficha.setIngresoFinExtend(fechaInicio.toLocalDate());

                }

                Date fechaFin = rs.getDate("ingreso_inicio_extend");
                if (fechaFin != null) {
                    ficha.setIngresoInicioExtend(fechaFin.toLocalDate());
                }

                lista.add(ficha);

            }
        } catch (SQLException ex) {
            Logger.getLogger(FichaSaludBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;

    }

}
