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
import modelo.curso.CursoMD;
import modelo.evaluacionSilabo.EvaluacionSilaboMD;
import modelo.tipoActividad.TipoActividadMD;
import modelo.unidadSilabo.UnidadSilaboMD;

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

    public List<SeguimientoEvaluacionMD> getSeguimientosBy(int idCurso, int idUnidad) {
        String SELECT = ""
                + "SELECT\n"
                + "	\"SeguimientoEvaluacion\".\"id\",\n"
                + "	\"SeguimientoEvaluacion\".formato,\n"
                + "	\"SeguimientoEvaluacion\".observacion,\n"
                + "	\"SeguimientoEvaluacion\".feha_creacion,\n"
                + "	\"SeguimientoEvaluacion\".fecha_edicion,\n"
                + "	\"SeguimientoEvaluacion\".id_evaluacion,\n"
                + "	\"EvaluacionSilabo\".instrumento,\n"
                + "	\"EvaluacionSilabo\".valoracion,\n"
                + "	\"Cursos\".id_curso,\n"
                + "	\"Cursos\".curso_nombre,\n"
                + "	\"UnidadSilabo\".id_unidad,\n"
                + "	\"UnidadSilabo\".numero_unidad,\n"
                + "	\"UnidadSilabo\".titulo_unidad \n"
                + "FROM\n"
                + "	\"SeguimientoEvaluacion\"\n"
                + "	INNER JOIN \"EvaluacionSilabo\" ON \"SeguimientoEvaluacion\".id_evaluacion = \"EvaluacionSilabo\".id_evaluacion\n"
                + "	INNER JOIN \"Cursos\" ON \"SeguimientoEvaluacion\".id_curso = \"Cursos\".id_curso\n"
                + "	INNER JOIN \"Docentes\" ON \"Cursos\".id_docente = \"Docentes\".id_docente\n"
                + "	INNER JOIN \"UnidadSilabo\" ON \"EvaluacionSilabo\".id_unidad = \"UnidadSilabo\".id_unidad \n"
                + "WHERE\n"
                + "	\"Cursos\".id_curso = " + idCurso + "\n"
                + "     AND \"UnidadSilabo\".id_unidad = " + idUnidad
                + "";

        System.out.println(SELECT);

        List<SeguimientoEvaluacionMD> lista = new ArrayList<>();

        ResultSet rs = CON.ejecutarQuery(SELECT);

        try {
            while (rs.next()) {

                SeguimientoEvaluacionMD seguimiento = new SeguimientoEvaluacionMD();
                seguimiento.setID(rs.getInt("id"));
                seguimiento.setFormato(rs.getInt("formato"));
                seguimiento.setObservacion(rs.getString("observacion"));
                seguimiento.setFechaCreacion(rs.getTimestamp("feha_creacion").toLocalDateTime());

                seguimiento.setFechaEdicion(rs.getTimestamp("fecha_edicion").toLocalDateTime());

                CursoMD curso = new CursoMD();
                curso.setId(rs.getInt("id_curso"));
                curso.setNombre(rs.getString("curso_nombre"));

                seguimiento.setCurso(curso);

                EvaluacionSilaboMD evaluacion = new EvaluacionSilaboMD();

                evaluacion.setIdEvaluacion(rs.getInt("id_evaluacion"));
                evaluacion.setInstrumento(rs.getString("instrumento"));
                evaluacion.setValoracion(rs.getDouble("valoracion"));
                UnidadSilaboMD unidad = new UnidadSilaboMD();
                unidad.setIdUnidad(rs.getInt("id_unidad"));
                unidad.setNumeroUnidad(rs.getInt("numero_unidad"));
                unidad.setTituloUnidad(rs.getString("titulo_unidad"));

                TipoActividadMD tipo = new TipoActividadMD();

                evaluacion.setIdUnidad(unidad);

                seguimiento.setEvaluacion(evaluacion);

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
