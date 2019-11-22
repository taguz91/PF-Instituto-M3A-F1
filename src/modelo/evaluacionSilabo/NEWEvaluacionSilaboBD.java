package modelo.evaluacionSilabo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.seguimientoSilabo.SeguimientoEvaluacionMD;
import modelo.tipoActividad.TipoActividadMD;
import utils.CONBD;

/**
 *
 * @author gus
 */
public class NEWEvaluacionSilaboBD extends CONBD {

    private static NEWEvaluacionSilaboBD EVBD;

    public static NEWEvaluacionSilaboBD single() {
        if (EVBD == null) {
            EVBD = new NEWEvaluacionSilaboBD();
        }
        return EVBD;
    }

    private static final String FROM_SILABO = "SELECT "
            + "id_evaluacion, "
            + "indicador, "
            + "id_tipo_actividad, "
            + "instrumento, "
            + "valoracion, "
            + "fecha_envio, "
            + "fecha_presentacion, "
            + "numero_unidad "
            + "FROM \"EvaluacionSilabo\", "
            + "\"Silabo\", "
            + "\"UnidadSilabo\" "
            + "WHERE \"EvaluacionSilabo\".id_unidad = \"UnidadSilabo\".id_unidad "
            + "AND \"UnidadSilabo\".id_silabo=\"Silabo\".id_silabo "
            + "AND \"Silabo\".id_silabo=?;";

    private static final String INSERT = "INSERT INTO public.\"EvaluacionSilabo\"( "
            + "id_unidad, "
            + "id_tipo_actividad, "
            + "instrumento, "
            + "valoracion, "
            + "fecha_envio, "
            + "fecha_presentacion, "
            + "indicador ) "
            + "	VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE = "UPDATE public.\"EvaluacionSilabo\" "
            + "SET indicador=?, "
            + "id_tipo_actividad=?, "
            + "instrumento=?, "
            + "valoracion=?, "
            + "fecha_envio=?, "
            + "fecha_presentacion=? "
            + " WHERE id_evaluacion=?;";

    public int guardar(EvaluacionSilaboMD evaluacion, int idUnidad) {
        PreparedStatement ps = CON.getPSID(INSERT);
        try {
            ps.setInt(1, idUnidad);
            ps.setInt(2, evaluacion.getIdTipoActividad().getIdTipoActividad());
            ps.setString(3, evaluacion.getInstrumento());
            ps.setDouble(4, evaluacion.getValoracion());
            ps.setDate(5, java.sql.Date.valueOf(evaluacion.getFechaEnvio()));
            ps.setDate(6, java.sql.Date.valueOf(evaluacion.getFechaPresentacion()));
            ps.setString(7, evaluacion.getIndicador());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "No guardamos la evaluacion del silabo. \n"
                    + e.getMessage(),
                    "Error al consultar",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return CON.getIDGenerado(ps);
    }

    public int editar(EvaluacionSilaboMD evaluacion) {
        PreparedStatement ps = CON.getPSID(UPDATE);
        try {
            ps.setString(1, evaluacion.getIndicador());
            ps.setInt(2, evaluacion.getIdTipoActividad().getIdTipoActividad());
            ps.setString(3, evaluacion.getInstrumento());
            ps.setDouble(4, evaluacion.getValoracion());
            ps.setDate(5, java.sql.Date.valueOf(evaluacion.getFechaEnvio()));
            ps.setDate(6, java.sql.Date.valueOf(evaluacion.getFechaPresentacion()));
            ps.setInt(7, evaluacion.getIdEvaluacion());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "No guardamos la evaluacion del silabo. \n"
                    + e.getMessage(),
                    "Error al consultar",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return CON.getIDGenerado(ps);
    }

    public boolean eliminar(int idEvaluacion) {
        String sql = "DELETE FROM public.\"EvaluacionSilabo\" "
                + "WHERE id_evaluacion=?;";
        return CON.deleteById(sql, idEvaluacion);
    }

    public List<EvaluacionSilaboMD> getBySilabo(int idSilabo) {
        List<EvaluacionSilaboMD> ES = new ArrayList<>();
        PreparedStatement ps = CON.getPSPOOL(FROM_SILABO);
        try {
            ps.setInt(1, idSilabo);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                EvaluacionSilaboMD e = new EvaluacionSilaboMD();
                e.setIdEvaluacion(res.getInt(1));
                e.setIndicador(res.getString(2));
                e.getIdTipoActividad().setIdTipoActividad(res.getInt(3));
                e.setInstrumento(res.getString(4));
                e.setValoracion(res.getDouble(5));
                if (res.getDate(6) != null) {
                    e.setFechaEnvio(res.getDate(6).toLocalDate());
                }
                if (res.getDate(7) != null) {
                    e.setFechaPresentacion(res.getDate(7).toLocalDate());
                }
                e.getIdUnidad().setNumeroUnidad(res.getInt(8));
                ES.add(e);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "No consultamos evaluaciones por id silabo. \n"
                    + e.getMessage(),
                    "Error al consultar",
                    JOptionPane.ERROR_MESSAGE
            );
        } finally {
            CON.cerrarCONPS(ps);
        }
        return ES;
    }

    public List<EvaluacionSilaboMD> getBySilaboReferencia(int idSilabo) {
        List<EvaluacionSilaboMD> ES = new ArrayList<>();
        PreparedStatement ps = CON.getPSPOOL(FROM_SILABO);
        try {
            ps.setInt(1, idSilabo);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                EvaluacionSilaboMD e = new EvaluacionSilaboMD();
                e.setIdEvaluacion(0);
                e.setIndicador(res.getString(2));
                e.getIdTipoActividad().setIdTipoActividad(res.getInt(3));
                e.setInstrumento(res.getString(4));
                e.setValoracion(res.getDouble(5));
                e.setFechaEnvio(res.getDate(6).toLocalDate());
                e.setFechaPresentacion(res.getDate(7).toLocalDate());
                e.getIdUnidad().setNumeroUnidad(res.getInt(8));
                ES.add(e);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "No consultamos evaluaciones por id silabo. \n"
                    + e.getMessage(),
                    "Error al consultar",
                    JOptionPane.ERROR_MESSAGE
            );
        } finally {
            CON.cerrarCONPS(ps);
        }
        return ES;
    }

    public List<EvaluacionSilaboMD> getByUnidad(int idUnidad, int idCurso) {
        String SELECT = ""
                + "SELECT\n"
                + "	\"EvaluacionSilabo\".id_evaluacion,\n"
                + "	\"EvaluacionSilabo\".indicador,\n"
                + "	\"EvaluacionSilabo\".instrumento,\n"
                + "	\"EvaluacionSilabo\".valoracion,\n"
                + "	\"EvaluacionSilabo\".fecha_envio,\n"
                + "	\"EvaluacionSilabo\".fecha_presentacion,\n"
                + "	\"TipoActividad\".nombre_tipo_actividad,\n"
                + "	\"TipoActividad\".nombre_subtipo_actividad,\n"
                + "	\"EvaluacionSilabo\".id_tipo_actividad,\n"
                + "     \"SeguimientoEvaluacion\".\"id\", \n"
                + "	\"SeguimientoEvaluacion\".formato,\n"
                + "	\"SeguimientoEvaluacion\".observacion \n"
                + "FROM\n"
                + "	\"EvaluacionSilabo\"\n"
                + "	INNER JOIN \"TipoActividad\" ON \"EvaluacionSilabo\".id_tipo_actividad = \"TipoActividad\".id_tipo_actividad\n"
                + "	INNER JOIN \"SeguimientoEvaluacion\" ON \"SeguimientoEvaluacion\".id_evaluacion = \"EvaluacionSilabo\".id_evaluacion \n"
                + "WHERE\n"
                + "	\"EvaluacionSilabo\".id_unidad = " + idUnidad + "\n"
                + "	AND \"SeguimientoEvaluacion\".id_curso = " + idCurso
                + "";

        System.out.println(SELECT);

        List<EvaluacionSilaboMD> lista = new ArrayList<>();

        ResultSet rs = CON.ejecutarQuery(SELECT);

        try {
            while (rs.next()) {
                EvaluacionSilaboMD evaluacion = new EvaluacionSilaboMD();

                evaluacion.setIdEvaluacion(rs.getInt("id_evaluacion"));
                evaluacion.setIndicador(rs.getString("indicador"));
                evaluacion.setInstrumento(rs.getString("instrumento"));
                evaluacion.setValoracion(rs.getDouble("valoracion"));
                evaluacion.setFechaEnvio(rs.getDate("fecha_envio").toLocalDate());
                evaluacion.setFechaPresentacion(rs.getDate("fecha_presentacion").toLocalDate());

                TipoActividadMD tipoActividad = new TipoActividadMD();

                tipoActividad.setIdTipoActividad(rs.getInt("id_tipo_actividad"));
                tipoActividad.setNombreTipoActividad(rs.getString("nombre_tipo_actividad"));
                tipoActividad.setNombreSubtipoActividad(rs.getString("nombre_subtipo_actividad"));

                evaluacion.setIdTipoActividad(tipoActividad);

                SeguimientoEvaluacionMD seguimiento = new SeguimientoEvaluacionMD();

                seguimiento.setID(rs.getInt("id"));
                seguimiento.setFormato(rs.getInt("formato"));
                seguimiento.setObservacion(rs.getString("observacion"));

                evaluacion.setSeguimientoEvaluacion(seguimiento);
                seguimiento.setEvaluacion(evaluacion);

                lista.add(evaluacion);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NEWEvaluacionSilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(rs);
        }

        return lista;

    }

}
