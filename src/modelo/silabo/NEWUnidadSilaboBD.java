package modelo.silabo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.ConnDBPool;
import modelo.silabo.mbd.IUnidadSilaboBD;
import modelo.unidadSilabo.UnidadSilaboMD;

/**
 *
 * @author gus
 */
public class NEWUnidadSilaboBD implements IUnidadSilaboBD {

    private final ConnDBPool CON = ConnDBPool.single();

    private static NEWUnidadSilaboBD UBD;

    public static NEWUnidadSilaboBD single() {
        if (UBD == null) {
            UBD = new NEWUnidadSilaboBD();
        }
        return UBD;
    }

    @Override
    public List<UnidadSilaboMD> getBySilabo(int idSilabo) {
        String sql = "SELECT id_unidad, "
                + "numero_unidad, "
                + "objetivo_especifico_unidad, "
                + "resultados_aprendizaje_unidad, "
                + "contenidos_unidad, "
                + "fecha_inicio_unidad, "
                + "fecha_fin_unidad, "
                + "horas_docencia_unidad, "
                + "horas_practica_unidad, "
                + "horas_autonomo_unidad, "
                + "titulo_unidad "
                + "FROM public.\"UnidadSilabo\" "
                + "WHERE id_silabo=? "
                + "ORDER BY numero_unidad";
        List<UnidadSilaboMD> US = new ArrayList<>();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setInt(1, idSilabo);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                UnidadSilaboMD u = new UnidadSilaboMD();
                u.setIdUnidad(res.getInt(1));
                u.setNumeroUnidad(res.getInt(2));
                u.setObjetivoEspecificoUnidad(res.getString(3));
                u.setResultadosAprendizajeUnidad(res.getString(4));
                u.setContenidosUnidad(res.getString(5));
                if (res.getDate(6) != null) {
                    u.setFechaInicioUnidad(res.getDate(6).toLocalDate());
                }
                if (res.getDate(7) != null) {
                    u.setFechaFinUnidad(res.getDate(7).toLocalDate());
                }
                u.setHorasDocenciaUnidad(res.getInt(8));
                u.setHorasPracticaUnidad(res.getInt(9));
                u.setHorasAutonomoUnidad(res.getInt(10));
                u.setTituloUnidad(res.getString(11));

                US.add(u);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al consultar unidad con fecha. "
                    + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return US;
    }

    @Override
    public List<UnidadSilaboMD> getBySilaboParaReferencia(int idSilabo) {
        String sql = "SELECT id_unidad, "
                + "numero_unidad, "
                + "objetivo_especifico_unidad, "
                + "resultados_aprendizaje_unidad, "
                + "contenidos_unidad, "
                + "horas_docencia_unidad, "
                + "horas_practica_unidad, "
                + "horas_autonomo_unidad, "
                + "titulo_unidad "
                + "FROM public.\"UnidadSilabo\" "
                + "WHERE id_silabo=? "
                + "ORDER BY numero_unidad";
        List<UnidadSilaboMD> US = new ArrayList<>();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setInt(1, idSilabo);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                UnidadSilaboMD u = new UnidadSilaboMD();
                u.setIdUnidad(0);
                u.setNumeroUnidad(res.getInt(2));
                u.setObjetivoEspecificoUnidad(res.getString(3));
                u.setResultadosAprendizajeUnidad(res.getString(4));
                u.setContenidosUnidad(res.getString(5));
                u.setHorasDocenciaUnidad(res.getInt(6));
                u.setHorasPracticaUnidad(res.getInt(7));
                u.setHorasAutonomoUnidad(res.getInt(8));
                u.setTituloUnidad(res.getString(9));

                US.add(u);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al consultar unidad sin fecha. "
                    + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return US;
    }

    @Override
    public List<UnidadSilaboMD> getBySilaboUnidad(int idSilabo, int numUnidad) {
        String sql = "SELECT u.numero_unidad, "
                + "u.titulo_unidad, "
                + "u.fecha_inicio_unidad, "
                + "u.fecha_fin_unidad, "
                + "u.horas_docencia_unidad, "
                + "u.horas_practica_unidad, "
                + "u.objetivo_especifico_unidad, "
                + "u.resultados_aprendizaje_unidad, "
                + "u.contenidos_unidad "
                + "FROM public.\"UnidadSilabo\" u "
                + "JOIN public.\"Silabo\" s ON u.id_silabo=s.id_silabo "
                + "WHERE s.id_silabo=? "
                + "AND u.numero_unidad=?;";
        List<UnidadSilaboMD> US = new ArrayList<>();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setInt(1, idSilabo);
            ps.setInt(2, numUnidad);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                UnidadSilaboMD u = new UnidadSilaboMD();
                u.setNumeroUnidad(res.getInt(1));
                u.setTituloUnidad(res.getString(2));
                u.setFechaInicioUnidad(res.getDate(3).toLocalDate());
                u.setFechaFinUnidad(res.getDate(4).toLocalDate());
                u.setHorasDocenciaUnidad(res.getInt(5));
                u.setHorasPracticaUnidad(res.getInt(6));
                u.setObjetivoEspecificoUnidad(res.getString(7));
                u.setResultadosAprendizajeUnidad(res.getString(8));
                u.setContenidosUnidad(res.getString(9));

                US.add(u);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al consultar silabo por id y unidad. \n"
                    + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return US;
    }

    @Override
    public List<UnidadSilaboMD> getForPlanClaseBySilabo(int idSilabo) {
        String sql = "SELECT id_unidad, "
                + "numero_unidad, "
                + "titulo_unidad, "
                + "contenidos_unidad "
                + "FROM public.\"UnidadSilabo\" "
                + "WHERE id_silabo = ? "
                + "ORDER BY numero_unidad;";
        List<UnidadSilaboMD> US = new ArrayList<>();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setInt(1, idSilabo);
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                UnidadSilaboMD u = new UnidadSilaboMD();
                u.setIdUnidad(res.getInt(1));
                u.setNumeroUnidad(res.getInt(2));
                u.setTituloUnidad(res.getString(3));
                u.setContenidosUnidad(res.getString(4));

                US.add(u);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al consultar para plan de clase. "
                    + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return US;
    }

    @Override
    public int guardar(UnidadSilaboMD u, int idSilabo) {
        String sql = "INSERT INTO public.\"UnidadSilabo\"( "
                + "numero_unidad, "
                + "objetivo_especifico_unidad, "
                + "resultados_aprendizaje_unidad, "
                + "contenidos_unidad, "
                + "fecha_inicio_unidad, "
                + "fecha_fin_unidad, "
                + "horas_docencia_unidad, "
                + "horas_practica_unidad, "
                + "horas_autonomo_unidad, "
                + "id_silabo, "
                + "titulo_unidad ) VALUES ( "
                + "?, ?, "
                + "?, ?, "
                + "?, ?, "
                + "?, ?, "
                + "?, ?, "
                + "?)";
        int idGenerado = 0;
        PreparedStatement ps = CON.getPSID(sql);
        try {
            ps.setInt(1, u.getNumeroUnidad());
            ps.setString(2, u.getObjetivoEspecificoUnidad());
            ps.setString(3, u.getResultadosAprendizajeUnidad());
            ps.setString(4, u.getContenidosUnidad());
            if (u.getFechaInicioUnidad() == null) {
                ps.setDate(5, null);
            } else {
                ps.setDate(5, java.sql.Date.valueOf(u.getFechaInicioUnidad()));
            }

            if (u.getFechaFinUnidad() == null) {
                ps.setDate(6, null);
            } else {
                ps.setDate(6, java.sql.Date.valueOf(u.getFechaFinUnidad()));
            }

            ps.setDouble(7, u.getHorasDocenciaUnidad());
            ps.setDouble(8, u.getHorasPracticaUnidad());
            ps.setDouble(9, u.getHorasAutonomoUnidad());
            ps.setInt(10, idSilabo);
            ps.setString(11, u.getTituloUnidad());
            idGenerado = CON.getIDGenerado(ps);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al guardar la unidad. \n"
                    + e.getMessage(),
                    "Error unidad",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return idGenerado;
    }

    public int editar(UnidadSilaboMD u) {
        String sql = "UPDATE public.\"UnidadSilabo\" SET "
                + "numero_unidad=?, "
                + "objetivo_especifico_unidad=?, "
                + "resultados_aprendizaje_unidad=?, "
                + "contenidos_unidad=?, "
                + "fecha_inicio_unidad=?, "
                + "fecha_fin_unidad=?, "
                + "horas_docencia_unidad=?, "
                + "horas_practica_unidad=?, "
                + "horas_autonomo_unidad=?, "
                + "titulo_unidad=? "
                + "WHERE id_unidad=?;"
                + "";
        int idGenerado = 0;
        PreparedStatement ps = CON.getPSID(sql);
        try {
            ps.setInt(1, u.getNumeroUnidad());
            ps.setString(2, u.getObjetivoEspecificoUnidad());
            ps.setString(3, u.getResultadosAprendizajeUnidad());
            ps.setString(4, u.getContenidosUnidad());
            if (u.getFechaInicioUnidad() == null) {
                ps.setDate(5, null);
            } else {
                ps.setDate(5, java.sql.Date.valueOf(u.getFechaInicioUnidad()));
            }

            if (u.getFechaFinUnidad() == null) {
                ps.setDate(6, null);
            } else {
                ps.setDate(6, java.sql.Date.valueOf(u.getFechaFinUnidad()));
            }

            ps.setDouble(7, u.getHorasDocenciaUnidad());
            ps.setDouble(8, u.getHorasPracticaUnidad());
            ps.setDouble(9, u.getHorasAutonomoUnidad());
            ps.setString(10, u.getTituloUnidad());
            ps.setInt(11, u.getIdUnidad());
            idGenerado = CON.getIDGenerado(ps);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al guardar la unidad. \n"
                    + e.getMessage(),
                    "Error unidad",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return idGenerado;
    }

}
