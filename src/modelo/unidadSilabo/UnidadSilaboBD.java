package modelo.unidadSilabo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConexionBD;

/**
 *
 * @author Andres Ullauri
 */
public class UnidadSilaboBD extends UnidadSilaboMD {

    private ConexionBD conexion;

    public UnidadSilaboBD(ConexionBD conexion) {
        this.conexion = conexion;
    }

    public void insertar(UnidadSilaboMD u, int is) {

        try {
            PreparedStatement ps = conexion.getCon().prepareStatement("INSERT INTO public.\"UnidadSilabo\"(\n"
                    + "	 numero_unidad, objetivo_especifico_unidad, resultados_aprendizaje_unidad, contenidos_unidad, fecha_inicio_unidad, fecha_fin_unidad, horas_docencia_unidad, horas_practica_unidad, horas_autonomo_unidad, id_silabo, titulo_unidad)\n"
                    + "	VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?," + is + ", ?)");

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

            ps.setInt(7, u.getHorasDocenciaUnidad());
            ps.setInt(8, u.getHorasPracticaUnidad());
            ps.setInt(9, u.getHorasAutonomoUnidad());

            ps.setString(10, u.getTituloUnidad());

            ps.executeUpdate();
            System.out.println(ps);
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(UnidadSilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static List<UnidadSilaboMD> consultar(ConexionBD conexion, int clave, int tipo) {
        List<UnidadSilaboMD> unidades = new ArrayList<>();

        try {
            PreparedStatement st = conexion.getCon().prepareStatement("SELECT id_unidad, numero_unidad, objetivo_especifico_unidad, resultados_aprendizaje_unidad, contenidos_unidad, fecha_inicio_unidad, fecha_fin_unidad, horas_docencia_unidad, horas_practica_unidad, horas_autonomo_unidad, titulo_unidad\n"
                    + "FROM public.\"UnidadSilabo\"\n"
                    + "WHERE id_silabo=? ORDER BY numero_unidad");
            st.setInt(1, clave);
            System.out.println(st);
            ResultSet res = st.executeQuery();

            while (res.next()) {

                UnidadSilaboMD u = new UnidadSilaboMD();
                u.setIdUnidad(res.getInt(1));
                u.setNumeroUnidad(res.getInt(2));
                u.setObjetivoEspecificoUnidad(res.getString(3));
                u.setResultadosAprendizajeUnidad(res.getString(4));
                u.setContenidosUnidad(res.getString(5));
                if (res.getDate(6) != null && tipo == 1) {
                    u.setFechaInicioUnidad(res.getDate(6).toLocalDate());
                }

                if (res.getDate(7) != null && tipo == 1) {
                    u.setFechaFinUnidad(res.getDate(7).toLocalDate());
                }

                u.setHorasDocenciaUnidad(res.getInt(8));
                System.out.println(u.getHorasDocenciaUnidad());
                u.setHorasPracticaUnidad(res.getInt(9));
                System.out.println(u.getHorasPracticaUnidad());
                u.setHorasAutonomoUnidad(res.getInt(10));
                u.setTituloUnidad(res.getString(11));

                unidades.add(u);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UnidadSilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return unidades;
    }

    public static List<UnidadSilaboMD> consultarUnidadesPlanClase(ConexionBD conexion, int clave) {

        List<UnidadSilaboMD> unidades = new ArrayList<>();

        try {

            PreparedStatement st = conexion.getCon().prepareStatement("SELECT id_unidad, numero_unidad,titulo_unidad,contenidos_unidad\n"
                    + "FROM public.\"UnidadSilabo\"\n"
                    + "WHERE id_silabo=? ORDER BY numero_unidad");

            st.setInt(1, clave);

            System.out.println(st);
            ResultSet res = st.executeQuery();

            while (res.next()) {

                UnidadSilaboMD u = new UnidadSilaboMD();
                u.setIdUnidad(res.getInt(1));
                u.setNumeroUnidad(res.getInt(2));
                u.setTituloUnidad(res.getString(3));
                u.setContenidosUnidad(res.getString(4));
                unidades.add(u);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UnidadSilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return unidades;
    }

    public static List<UnidadSilaboMD> consultarSilaboUnidades(ConexionBD conexion, int silabo, int unidad) {

        List<UnidadSilaboMD> unidades = new ArrayList<>();

        try {

            PreparedStatement st = conexion.getCon().prepareStatement("select u.numero_unidad,u.titulo_unidad,u.fecha_inicio_unidad,u.fecha_fin_unidad,u.horas_docencia_unidad,u.horas_practica_unidad,\n"
                    + "u.objetivo_especifico_unidad,u.resultados_aprendizaje_unidad,u.contenidos_unidad\n"
                    + "from \"UnidadSilabo\" u join \"Silabo\" s on u.id_silabo=s.id_silabo where s.id_silabo=? and u.numero_unidad=?");

            st.setInt(1, silabo);
            st.setInt(2, unidad);

            System.out.println(st);
            ResultSet res = st.executeQuery();

            while (res.next()) {

                UnidadSilaboMD tmp = new UnidadSilaboMD();
                tmp.setNumeroUnidad(res.getInt(1));
                tmp.setTituloUnidad(res.getString(2));
                tmp.setFechaInicioUnidad(res.getDate(3).toLocalDate());
                tmp.setFechaFinUnidad(res.getDate(4).toLocalDate());
                tmp.setHorasDocenciaUnidad(res.getInt(5));
                tmp.setHorasPracticaUnidad(res.getInt(6));
                tmp.setObjetivoEspecificoUnidad(res.getString(7));
                tmp.setResultadosAprendizajeUnidad(res.getString(8));
                tmp.setContenidosUnidad(res.getString(9));
                unidades.add(tmp);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UnidadSilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return unidades;
    }

    public static UnidadSilaboMD consultarUltima(ConexionBD conexion, int is, int iu) {
        UnidadSilaboMD unidad = null;
        try {

            PreparedStatement st = conexion.getCon().prepareStatement("SELECT id_unidad FROM \"UnidadSilabo\" WHERE id_silabo=? AND numero_unidad=?");
            st.setInt(1, is);
            st.setInt(2, iu);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                unidad = new UnidadSilaboMD();

                unidad.setIdUnidad(rs.getInt(1));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UnidadSilaboBD.class.getName()).log(Level.SEVERE, null, ex);

        }

        return unidad;
    }

}
