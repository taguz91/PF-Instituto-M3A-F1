package modelo.evaluacionSilabo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
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

}
