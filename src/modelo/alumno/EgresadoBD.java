package modelo.alumno;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.carrera.CarreraMD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.AlumnoMD;
import utils.CONBD;
import utils.M;

/**
 *
 * @author gus
 */
public class EgresadoBD extends CONBD {

    private static EgresadoBD EBD;

    public static EgresadoBD single() {
        if (EBD == null) {
            EBD = new EgresadoBD();
        }
        return EBD;
    }

    public static final String BASEQUERY_TBLEGRESADOS = "SELECT\n"
            + "id_egresado,\n"
            + "e.id_almn_carrera,\n"
            + "persona_primer_nombre,\n"
            + "persona_segundo_nombre,\n"
            + "persona_primer_apellido,\n"
            + "persona_segundo_apellido,\n"
            + "persona_identificacion,\n"
            + "carrera_codigo,\n"
            + "prd_lectivo_nombre,\n"
            + "fecha_egreso,\n"
            + "graduado, "
            + "trabajo_titulacion \n"
            + "FROM alumno.\"Egresados\" e\n"
            + "JOIN public.\"AlumnosCarrera\" ac\n"
            + "ON e.id_almn_carrera = ac.id_almn_carrera\n"
            + "JOIN public.\"Carreras\" c\n"
            + "ON c.id_carrera = ac.id_carrera\n"
            + "JOIN public.\"PeriodoLectivo\" pl\n"
            + "ON pl.id_prd_lectivo = e.id_prd_lectivo\n"
            + "JOIN public.\"Alumnos\" a\n"
            + "ON ac.id_alumno = a.id_alumno\n"
            + "JOIN public.\"Personas\" p\n"
            + "ON p.id_persona = a.id_persona\n"
            + "WHERE graduado = false ";

    public static final String ENQUERY_TBLEGRESADOS = " ORDER BY fecha_egreso DESC;";

    public static final String BASEQUERY_TBLGRADUADOS = "SELECT\n"
            + "id_egresado,\n"
            + "e.id_almn_carrera,\n"
            + "persona_primer_nombre,\n"
            + "persona_segundo_nombre,\n"
            + "persona_primer_apellido,\n"
            + "persona_segundo_apellido,\n"
            + "persona_identificacion,\n"
            + "carrera_codigo,\n"
            + "prd_lectivo_nombre,\n"
            + "fecha_graduacion\n"
            + "FROM alumno.\"Egresados\" e\n"
            + "JOIN public.\"AlumnosCarrera\" ac\n"
            + "ON e.id_almn_carrera = ac.id_almn_carrera\n"
            + "JOIN public.\"Carreras\" c\n"
            + "ON c.id_carrera = ac.id_carrera\n"
            + "JOIN public.\"PeriodoLectivo\" pl\n"
            + "ON pl.id_prd_lectivo = e.id_prd_lectivo\n"
            + "JOIN public.\"Alumnos\" a\n"
            + "ON ac.id_alumno = a.id_alumno\n"
            + "JOIN public.\"Personas\" p\n"
            + "ON p.id_persona = a.id_persona\n"
            + "WHERE graduado = true  ";

    public static final String ENQUERY_TBLGRADUADOS = " ORDER BY fecha_graduacion DESC;";

    public List<PeriodoLectivoMD> getPeriodoByIdAlmnCarrera(int idAlmnCarrera) {
        List<PeriodoLectivoMD> pls = new ArrayList<>();
        String sql = "SELECT\n"
                + "pl.id_prd_lectivo,\n"
                + "prd_lectivo_nombre\n"
                + "FROM public.\"PeriodoLectivo\" pl\n"
                + "JOIN public.\"Matricula\" m ON pl.id_prd_lectivo = m.id_prd_lectivo\n"
                + "WHERE pl.id_carrera IN (\n"
                + "  SELECT id_carrera\n"
                + "  FROM public.\"AlumnosCarrera\"\n"
                + "  WHERE id_almn_carrera = ?\n"
                + ") AND m.id_alumno = (\n"
                + "  SELECT id_alumno\n"
                + "  FROM public.\"AlumnosCarrera\"\n"
                + "  WHERE id_almn_carrera = ?\n"
                + ") ORDER BY prd_lectivo_fecha_fin DESC;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setInt(1, idAlmnCarrera);
            ps.setInt(2, idAlmnCarrera);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                PeriodoLectivoMD p = new PeriodoLectivoMD();
                p.setID(res.getInt(1));
                p.setNombre(res.getString(2));
                pls.add(p);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al consultar ultimo periodo "
                    + "en el que se matriculo. \n"
                    + e.getMessage()
            );
        } finally {
            CON.cerrarCONPS(ps);
        }
        return pls;
    }

    public int guardarSinGraduacion(Egresado e) {
        String sql = "INSERT INTO alumno.\"Egresados\"(\n"
                + "id_almn_carrera,\n"
                + "id_prd_lectivo,\n"
                + "fecha_egreso, "
                + "trabajo_titulacion)\n"
                + "VALUES (?, ?, ?, ?);";
        PreparedStatement ps = CON.getPSID(sql);
        try {
            ps.setInt(1, e.getAlmnCarrera().getId());
            ps.setInt(2, e.getPeriodo().getID());
            ps.setDate(3, Date.valueOf(e.getFechaEgreso()));
            ps.setBoolean(4, e.isTrabajoTitulacion());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al guardar sin graduacion.\n"
                    + ex.getMessage()
            );
        }
        return CON.getIDGenerado(ps);
    }

    public int editarSinGraduacion(Egresado e) {
        String sql = "UPDATE alumno.\"Egresados\" SET \n"
                + " id_prd_lectivo=?,\n"
                + " fecha_egreso=?, "
                + " graduado=?,"
                + " trabajo_titulacion=? \n"
                + "  WHERE id_egresado=?;";
        PreparedStatement ps = CON.getPSID(sql);
        try {
            ps.setInt(1, e.getPeriodo().getID());
            ps.setDate(2, Date.valueOf(e.getFechaEgreso()));
            ps.setBoolean(3, e.isGraduado());
            ps.setBoolean(4, e.isTrabajoTitulacion());
            ps.setInt(5, e.getId());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al editar sin graduacion.\n"
                    + ex.getMessage()
            );
        }
        return CON.getIDGenerado(ps);
    }

    public int guardarConGraduacion(Egresado e) {
        String sql = "INSERT INTO alumno.\"Egresados\"(\n"
                + "id_almn_carrera,\n"
                + "id_prd_lectivo,\n"
                + "fecha_egreso,\n"
                + "graduado,\n"
                + "fecha_graduacion,"
                + "trabajo_titulacion )\n"
                + "VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = CON.getPSID(sql);
        try {
            ps.setInt(1, e.getAlmnCarrera().getId());
            ps.setInt(2, e.getPeriodo().getID());
            ps.setDate(3, Date.valueOf(e.getFechaEgreso()));
            ps.setBoolean(4, e.isGraduado());
            ps.setDate(5, Date.valueOf(e.getFechaGraduacion()));
            ps.setBoolean(6, e.isTrabajoTitulacion());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al editar sin graduacion.\n"
                    + ex.getMessage()
            );
        }
        return CON.getIDGenerado(ps);
    }

    public int editarConGraduacion(Egresado e) {
        String sql = "UPDATE alumno.\"Egresados\" SET\n"
                + "id_almn_carrera=?,\n"
                + "id_prd_lectivo=?,\n"
                + "fecha_egreso=?,\n"
                + "graduado=?,\n"
                + "fecha_graduacion=?, "
                + "trabajo_titulacion=? \n"
                + " WHERE id_egresado=?;";
        PreparedStatement ps = CON.getPSID(sql);
        try {
            ps.setInt(1, e.getAlmnCarrera().getId());
            ps.setInt(2, e.getPeriodo().getID());
            ps.setDate(3, Date.valueOf(e.getFechaEgreso()));
            ps.setBoolean(4, e.isGraduado());
            ps.setDate(5, Date.valueOf(e.getFechaGraduacion()));
            ps.setBoolean(6, e.isTrabajoTitulacion());
            ps.setInt(7, e.getId());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al editar sin graduacion.\n"
                    + ex.getMessage()
            );
        }
        return CON.getIDGenerado(ps);
    }

    public List<Egresado> getAllEgresados() {
        return getForTblEgresados(
                CON.getPSPOOL(BASEQUERY_TBLEGRESADOS + ENQUERY_TBLEGRESADOS)
        );
    }

    public List<Egresado> getAllGraduados() {
        return getForTblGraduados(
                CON.getPSPOOL(BASEQUERY_TBLGRADUADOS + ENQUERY_TBLGRADUADOS)
        );
    }

    public boolean eliminar(int idEgresado) {
        String sql = "DELETE FROM alumno.\"Egresados\" "
                + "WHERE id_egresado = ?;";
        return CON.deleteById(sql, idEgresado);
    }

    private List<Egresado> getForTblEgresados(PreparedStatement ps) {
        List<Egresado> es = new ArrayList<>();
        try {
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                Egresado e = new Egresado();
                e.setFechaEgreso(res.getDate("fecha_egreso").toLocalDate());
                e.setId(res.getInt("id_egresado"));
                e.setGraduado(res.getBoolean("graduado"));
                e.setTrabajoTitulacion(res.getBoolean("trabajo_titulacion"));

                AlumnoCarreraMD ac = new AlumnoCarreraMD();
                ac.setId(res.getInt("id_almn_carrera"));

                AlumnoMD a = new AlumnoMD();
                a.setPrimerNombre(res.getString("persona_primer_nombre"));
                a.setSegundoNombre(res.getString("persona_segundo_nombre"));
                a.setPrimerApellido(res.getString("persona_primer_apellido"));
                a.setSegundoApellido(res.getString("persona_segundo_apellido"));
                a.setIdentificacion(res.getString("persona_identificacion"));

                ac.setAlumno(a);

                CarreraMD c = new CarreraMD();
                c.setCodigo(res.getString("carrera_codigo"));

                ac.setCarrera(c);

                PeriodoLectivoMD pl = new PeriodoLectivoMD();
                pl.setNombre(res.getString("prd_lectivo_nombre"));

                e.setAlmnCarrera(ac);
                e.setPeriodo(pl);

                es.add(e);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al mapear los egresados.\n"
                    + e.getMessage()
            );
        } finally {
            CON.cerrarCONPS(ps);
        }
        return es;
    }

    private List<Egresado> getForTblGraduados(PreparedStatement ps) {
        List<Egresado> es = new ArrayList<>();
        try {
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                Egresado e = new Egresado();
                e.setFechaGraduacion(res.getDate("fecha_graduacion").toLocalDate());
                e.setId(res.getInt("id_egresado"));

                AlumnoCarreraMD ac = new AlumnoCarreraMD();
                ac.setId(res.getInt("id_almn_carrera"));

                AlumnoMD a = new AlumnoMD();
                a.setPrimerNombre(res.getString("persona_primer_nombre"));
                a.setSegundoNombre(res.getString("persona_segundo_nombre"));
                a.setPrimerApellido(res.getString("persona_primer_apellido"));
                a.setSegundoApellido(res.getString("persona_segundo_apellido"));
                a.setIdentificacion(res.getString("persona_identificacion"));

                ac.setAlumno(a);

                CarreraMD c = new CarreraMD();
                c.setCodigo(res.getString("carrera_codigo"));

                ac.setCarrera(c);

                PeriodoLectivoMD pl = new PeriodoLectivoMD();
                pl.setNombre(res.getString("prd_lectivo_nombre"));

                e.setAlmnCarrera(ac);
                e.setPeriodo(pl);

                es.add(e);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al mapear los egresados.\n"
                    + e.getMessage()
            );
        } finally {
            CON.cerrarCONPS(ps);
        }
        return es;
    }

    public List<List<String>> getReportesEgresadosExcel(String ids) {
        List<List<String>> lista = new ArrayList<>();
        String sql = "SELECT\n"
                + "'' AS \"CÓDIGO DEL IST\",\n"
                + "'ISTA' AS \"NOMBRE DEL INSTITUTO\",\n"
                + "'AZUAY' AS \"PROVINCIA\",\n"
                + "carrera_codigo AS \"CÓDIGO DE LA CARRERA\",\n"
                + "carrera_nombre AS \"CARRERA\",\n"
                + "carrera_modalidad AS \"MODALIDAD DE ESTUDIOS\",\n"
                + "'' AS \"TIPO DE IDENTIFICACIÓN\",\n"
                + "p.persona_identificacion AS \"NRO. DE IDENTIFICACIÓN\",\n"
                + "p.persona_primer_apellido || ' ' ||\n"
                + "p.persona_segundo_apellido || ' ' ||\n"
                + "p.persona_primer_nombre || ' ' ||\n"
                + "p.persona_segundo_nombre AS \"APELLIDOS Y NOMBRES\",\n"
                + "consultar_pais(p.id_lugar_natal) AS \"NACIONALIDAD\",\n"
                + "CASE WHEN trabajo_titulacion THEN 'SI' ELSE 'NO' END\n"
                + "AS \"TRABAJO DE TITULACIÓN FINALIZADO S/N\"\n"
                + "\n"
                + "FROM alumno.\"Egresados\" e\n"
                + "JOIN public.\"AlumnosCarrera\" ac\n"
                + "ON ac.id_almn_carrera = e.id_almn_carrera\n"
                + "JOIN public.\"Carreras\" c\n"
                + "ON ac.id_carrera = c.id_carrera\n"
                + "JOIN public.\"Alumnos\" a\n"
                + "ON a.id_alumno = ac.id_alumno\n"
                + "JOIN public.\"Personas\" p\n"
                + "ON p.id_persona = a.id_persona\n"
                + "WHERE id_prd_lectivo IN (" + ids + ")";
        
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                List<String> datos = new ArrayList();
                datos.add(rs.getString(1));
                datos.add(rs.getString(2));
                datos.add(rs.getString(3));
                datos.add(rs.getString(4));
                datos.add(rs.getString(5));
                datos.add(rs.getString(6));
                datos.add(rs.getString(7));
                datos.add(rs.getString(8));
                datos.add(rs.getString(9));
                datos.add(rs.getString(10));
                datos.add(rs.getString(11));
                
                lista.add(datos);
            }
        } catch (SQLException e) {
            M.errorMsg(
                    "No consultamos los alumnos egresados para el reporte"
                    + "por periodos para el reporte. " + e.getMessage()
            );
        }

        return lista;
    }

}
