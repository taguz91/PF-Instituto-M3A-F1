/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.seguimientoSilabo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConnDBPool;
import modelo.evaluacionSilabo.EvaluacionSilaboMD;

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

    public boolean copiarSeguimientos(int idCurso, int idUnidad, int idCursoRef) {

        String CALL = ""
                + "CALL \"copiar_seguimientos_eval\" ( " + idCurso + "," + idUnidad + " , " + idCursoRef + ");"
                + "";

        return CON.ejecutar(CALL) != null;

    }

    public boolean crearSeguimientos(int idUnidad, int idCurso) {

        String CALL = ""
                + "CALL \"crear_seguimienntos_evaluaciones\"(" + idUnidad + " ," + idCurso + ")"
                + "";

        System.out.println(CALL);

        return CON.ejecutar(CALL) != null;

    }

    public List<SeguimientoEvaluacionMD> getSeguimientos(int idUnidad, int idCurso, List<EvaluacionSilaboMD> evaluaciones) {
        String SELECT = ""
                + "SELECT\n"
                + "     \"SeguimientoEvaluacion\".\"id\",\n"
                + "     \"SeguimientoEvaluacion\".id_evaluacion,\n"
                + "     \"SeguimientoEvaluacion\".formato,\n"
                + "     \"SeguimientoEvaluacion\".observacion"
                + "FROM\n"
                + "	\"SeguimientoEvaluacion\"\n"
                + "	INNER JOIN \"EvaluacionSilabo\" ON \"EvaluacionSilabo\".id_evaluacion = \"SeguimientoEvaluacion\".id_evaluacion \n"
                + "WHERE\n"
                + "	\"EvaluacionSilabo\".id_unidad = " + idUnidad + " \n"
                + "	AND \"SeguimientoEvaluacion\".id_curso = " + idCurso
                + "";

        List<SeguimientoEvaluacionMD> lista = new ArrayList<>();

        ResultSet rs = CON.ejecutarQuery(SELECT);

        try {
            while (rs.next()) {

                SeguimientoEvaluacionMD seguimiento = new SeguimientoEvaluacionMD();
                seguimiento.setID(rs.getInt("id"));

                int idEvaluacion = rs.getInt("id_evaluacion");
                seguimiento.setEvaluacion(
                        evaluaciones.stream()
                                .filter(item -> item.getIdEvaluacion() == idEvaluacion)
                                .findFirst()
                                .get()
                );

                seguimiento.setFormato(rs.getInt("formato"));
                seguimiento.setObservacion(rs.getString("observacion"));
                lista.add(seguimiento);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SeguimientoEvaluacionBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(rs);
        }

        return lista;
    }

    public boolean editar(SeguimientoEvaluacionMD seguimiento) {
        String UPDATE = ""
                + "UPDATE \"SeguimientoEvaluacion\" \n"
                + "SET \n"
                + "     \"formato\" = " + seguimiento.getFormato() + ",\n"
                + "     \"observacion\" = '" + seguimiento.getObservacion() + "', \n"
                + "     \"fecha_edicion\" = '" + java.sql.Timestamp.valueOf(LocalDateTime.now()) + "'"
                + "WHERE\n"
                + "	\"id\" = " + seguimiento.getID()
                + "";

        return CON.ejecutar(UPDATE) == null;
    }

}
