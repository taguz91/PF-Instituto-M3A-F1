/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.PlanClases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConexionBD;
import modelo.jornada.JornadaMD;
import utils.CONBD;

public class JornadasDB extends CONBD {

    private static JornadasDB INSTANCE;

    public static List<JornadaMD> consultarJornadas(ConexionBD conexion) {
        String SELECT = "select nombre_jornada from \"Jornadas\" ";

        List<JornadaMD> lista_jornadas = new ArrayList<>();

        ResultSet rs = CON.ejecutarQuery(SELECT);
        try {

            while (rs.next()) {
                JornadaMD j = new JornadaMD();
                j.setNombre(rs.getString(1));
                lista_jornadas.add(j);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JornadasDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(rs);
        }
        return lista_jornadas;
    }

}
