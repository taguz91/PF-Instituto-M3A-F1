/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.seguimientoSilabo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConnDBPool;

/**
 *
 * @author MrRainx
 */
public class SeguimientoEvaluacionBD {

    private final ConnDBPool CON = ConnDBPool.single();

    private static SeguimientoEvaluacionBD THIS;

    private SeguimientoEvaluacionBD() {
    }

    public static SeguimientoEvaluacionBD sigle() {
        if (THIS == null) {
            THIS = new SeguimientoEvaluacionBD();
        }
        return THIS;
    }

    public SeguimientoEvaluacionMD insert(SeguimientoEvaluacionMD object) {
        String INSERT = ""
                + "INSERT INTO \"SeguimientoEvaluacion\" ( \"id_curso\", \"id_evaluacion\", \"formato\", \"observacion\" )\n"
                + "VALUES\n"
                + "	( "
                + "         " + object.getCurso().getId() + ",  \n"
                + "         " + object.getEvaluacion().getIdEvaluacion() + ",  \n"
                + "         " + object.getFormato() + ",  \n"
                + "         '" + object.getObservacion() + "'   \n"
                + "     )       \n"
                + " RETURNING \"id\";"
                + "";

        ResultSet rs = CON.ejecutarQuery(INSERT);

        try {
            while (rs.next()) {
                object.setID(rs.getInt("id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SeguimientoEvaluacionBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(rs);
        }

        return object;
    }

    public boolean crearSeguimientos(int idUnidad, int idCurso) {

        String CALL = ""
                + "CALL \"crear_seguimienntos_evaluaciones\"(" + idUnidad + " ," + idCurso + ")"
                + "";
        
        return CON.ejecutar(CALL) != null;

    }

}
