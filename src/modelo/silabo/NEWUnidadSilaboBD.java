package modelo.silabo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.ConnDBPool;
import modelo.estrategiasAprendizaje.EstrategiasAprendizajeMD;
import modelo.estrategiasUnidad.EstrategiasUnidadMD;
import modelo.silabo.mbd.IUnidadSilaboBD;
import modelo.unidadSilabo.UnidadSilaboBD;
import modelo.unidadSilabo.UnidadSilaboMD;
import org.postgresql.util.PSQLException;

/**
 *
 * @author gus
 */
public class NEWUnidadSilaboBD implements IUnidadSilaboBD {

    private static final ConnDBPool CON = ConnDBPool.single();

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
                u.setId(res.getInt(1));
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
                u.setId(0);
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
                u.setId(res.getInt(1));
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
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al guardar la unidad. \n"
                    + e.getMessage(),
                    "Error unidad",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return CON.getIDGenerado(ps);
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
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al guardar la unidad. \n"
                    + e.getMessage(),
                    "Error unidad",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return CON.getIDGenerado(ps);
    }

    public boolean eliminar(int idUnidad) {
        String sql = "DELETE FROM public.\"UnidadSilabo\" "
                + "WHERE id_unidad = ?;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setInt(1, idUnidad);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "No eliminamos unidad con ID:"
                    + idUnidad + " \n"
                    + e.getMessage(),
                    "Error unidad",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return CON.noSQLPOOL(ps);
    }

    public static UnidadSilaboMD mapper(ResultSet rs) {
        UnidadSilaboMD unidad = new UnidadSilaboMD();

        try {

            try {
                Integer id = rs.getInt("id_unidad");
                unidad.setId(id);

            } catch (PSQLException e) {
            }

            try {
                String titulo = rs.getString("titulo_unidad");
                unidad.setTituloUnidad(titulo);

            } catch (PSQLException e) {
            }

            try {

                Integer idSilabo = rs.getInt("id_silabo");

                //TODO SilaboMD.mapper();
            } catch (PSQLException e) {
            }

            try {
                Integer numero = rs.getInt("numero_unidad");
                unidad.setNumeroUnidad(numero);

            } catch (PSQLException e) {
            }

            try {
                String objetivoEspecifico = rs.getString("objetivo_especifico_unidad");
                unidad.setObjetivoEspecificoUnidad(objetivoEspecifico);

            } catch (PSQLException e) {
            }

            try {
                String resultadosAprendizaje = rs.getString("resultados_aprendizaje_unidad");
                unidad.setResultadosAprendizajeUnidad(resultadosAprendizaje);

            } catch (PSQLException e) {
            }

            try {
                String contenidos = rs.getString("contenidos_unidad");
                unidad.setContenidosUnidad(contenidos);

            } catch (PSQLException e) {
            }

            try {
                LocalDate fechaInicio = rs.getDate("fecha_inicio_unidad").toLocalDate();
                unidad.setFechaFinUnidad(fechaInicio);

            } catch (PSQLException e) {
            }

            try {
                LocalDate fechaFin = rs.getDate("fecha_fin_unidad").toLocalDate();
                unidad.setFechaFinUnidad(fechaFin);

            } catch (PSQLException e) {
            }

            try {
                Double horasDocencia = rs.getDouble("horas_docencia_unidad");
                unidad.setHorasDocenciaUnidad(horasDocencia);

            } catch (PSQLException e) {
            }

            try {
                Double horasPractica = rs.getDouble("horas_practica_unidad");
                unidad.setHorasPracticaUnidad(horasPractica);

            } catch (PSQLException e) {
            }

            try {
                Double horasAutonomas = rs.getDouble("horas_autonomo_unidad");
                unidad.setHorasAutonomoUnidad(horasAutonomas);

            } catch (PSQLException e) {
            }

        } catch (SQLException ex) {
            Logger.getLogger(NEWUnidadSilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return unidad;

    }

    public List<UnidadSilaboMD> getSimpleBySilabo(int idSilabo) {
        String SELECT = ""
                + "SELECT\n"
                + "	\"UnidadSilabo\".id_unidad,\n"
                + "	\"UnidadSilabo\".numero_unidad,\n"
                + "	\"UnidadSilabo\".titulo_unidad \n"
                + "FROM\n"
                + "	\"UnidadSilabo\" \n"
                + "WHERE\n"
                + "	\"UnidadSilabo\".id_silabo = " + idSilabo
                + "";
        List<UnidadSilaboMD> lista = new ArrayList<>();

        ResultSet rs = CON.ejecutarQuery(SELECT);

        try {
            while (rs.next()) {
                lista.add(mapper(rs));

            }
        } catch (SQLException ex) {
            Logger.getLogger(NEWUnidadSilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(rs);
        }

        return lista;
    }

    public static List<UnidadSilaboMD> getUnidadesBy(int idPeriodo, int idMateria, int idCurso) {

        String SELECT = ""
                + "SELECT\n"
                + "	\"UnidadSilabo\".id_unidad,\n"
                + "	\"UnidadSilabo\".id_silabo,\n"
                + "	\"UnidadSilabo\".numero_unidad,\n"
                + "	\"UnidadSilabo\".objetivo_especifico_unidad,\n"
                + "	\"UnidadSilabo\".resultados_aprendizaje_unidad,\n"
                + "	\"UnidadSilabo\".contenidos_unidad,\n"
                + "	\"UnidadSilabo\".fecha_inicio_unidad,\n"
                + "	\"UnidadSilabo\".fecha_fin_unidad,\n"
                + "	\"UnidadSilabo\".horas_docencia_unidad,\n"
                + "	\"UnidadSilabo\".horas_practica_unidad,\n"
                + "	\"UnidadSilabo\".horas_autonomo_unidad,\n"
                + "	\"UnidadSilabo\".titulo_unidad \n"
                + "FROM\n"
                + "	\"UnidadSilabo\"\n"
                + "	INNER JOIN \"Silabo\" ON \"UnidadSilabo\".id_silabo = \"Silabo\".id_silabo \n"
                + "	AND \"UnidadSilabo\".id_silabo = \"Silabo\".id_silabo \n"
                + "WHERE\n"
                + "	\"Silabo\".id_prd_lectivo = " + idPeriodo + " \n"
                + "	AND \"Silabo\".id_materia = " + idMateria + " \n"
                + "	AND \"UnidadSilabo\".id_unidad NOT IN (\n"
                + "	    SELECT\n"
                + "		    \"UnidadSilabo\".id_unidad \n"
                + "	    FROM\n"
                + "		    \"PlandeClases\"\n"
                + "		    INNER JOIN \"UnidadSilabo\" ON \"PlandeClases\".id_unidad = \"UnidadSilabo\".id_unidad\n"
                + "		    INNER JOIN \"Cursos\" ON \"PlandeClases\".id_curso = \"Cursos\".id_curso \n"
                + "	    WHERE\n"
                + "		    \"Cursos\".id_curso = " + idCurso + " \n"
                + "	) \n"
                + "ORDER BY\n"
                + "	numero_unidad"
                + "";

        List<UnidadSilaboMD> unidades = new ArrayList<>();
        ResultSet rs = CON.ejecutarQuery(SELECT);
        try {
            while (rs.next()) {

                UnidadSilaboMD unidadSilaboMD = new UnidadSilaboMD();
                unidadSilaboMD.setId(rs.getInt("id_unidad"));
                unidadSilaboMD.setNumeroUnidad(rs.getInt("numero_unidad"));
                unidadSilaboMD.setObjetivoEspecificoUnidad(rs.getString("objetivo_especifico_unidad"));
                unidadSilaboMD.setResultadosAprendizajeUnidad(rs.getString("resultados_aprendizaje_unidad"));
                unidadSilaboMD.setContenidosUnidad(rs.getString("contenidos_unidad"));
                unidadSilaboMD.setFechaInicioUnidad(rs.getDate("fecha_inicio_unidad").toLocalDate());
                unidadSilaboMD.setFechaFinUnidad(rs.getDate("fecha_fin_unidad").toLocalDate());
                unidadSilaboMD.setHorasDocenciaUnidad(rs.getDouble("horas_docencia_unidad"));
                unidadSilaboMD.setHorasPracticaUnidad(rs.getDouble("horas_practica_unidad"));
                unidadSilaboMD.setHorasAutonomoUnidad(rs.getDouble("horas_autonomo_unidad"));
                unidadSilaboMD.setTituloUnidad(rs.getString("titulo_unidad"));

                unidadSilaboMD.setEstrategias(getEstrategiasUnidad(rs.getInt("id_unidad")));

                unidades.add(unidadSilaboMD);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NEWUnidadSilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(rs);
        }

        return unidades;
    }

    private static List<EstrategiasUnidadMD> getEstrategiasUnidad(int idUnidad) throws SQLException {
        String SELECT = ""
                + "SELECT\n"
                + "	\"EstrategiasAprendizaje\".descripcion_estrategia \n"
                + "FROM\n"
                + "	\"EstrategiasUnidad\"\n"
                + "	INNER JOIN \"EstrategiasAprendizaje\" ON \"EstrategiasUnidad\".id_estrategia = \"EstrategiasAprendizaje\".id_estrategia \n"
                + "WHERE\n"
                + "	\"EstrategiasUnidad\".id_unidad = " + idUnidad
                + "";

        List<EstrategiasUnidadMD> estrategias = new ArrayList<>();

        ResultSet rs = CON.ejecutarQuery(SELECT);

        while (rs.next()) {
            EstrategiasAprendizajeMD aprendizajeMD = new EstrategiasAprendizajeMD();
            aprendizajeMD.setDescripcionEstrategia(rs.getString("descripcion_estrategia"));

            EstrategiasUnidadMD estrategiasUnidadMD = new EstrategiasUnidadMD();
            estrategiasUnidadMD.setEstrategia(aprendizajeMD);

            estrategias.add(estrategiasUnidadMD);
        }

        CON.close(rs);

        return estrategias;

    }
}
