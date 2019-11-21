package modelo.alumno;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.carrera.CarreraMD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.AlumnoMD;
import utils.CONBD;

/**
 *
 * @author gus
 */
public class RetiradoBD extends CONBD {

    private static RetiradoBD RBD;

    public static RetiradoBD single() {
        if (RBD == null) {
            RBD = new RetiradoBD();
        }
        return RBD;
    }

    public static final String BASEQUERY_TBL = "SELECT\n"
            + "r.id_retiro,\n"
            + "fecha_retiro,\n"
            + "ac.id_almn_carrera,\n"
            + "ac.id_carrera,\n"
            + "almn_carrera_fecha_registro,\n"
            + "persona_primer_nombre,\n"
            + "persona_segundo_nombre,\n"
            + "persona_primer_apellido,\n"
            + "persona_segundo_apellido,\n"
            + "persona_identificacion,\n"
            + "carrera_codigo, "
            + "prd_lectivo_nombre, "
            + "r.id_prd_lectivo,"
            + "r.motivo_retiro "
            + "FROM public.\"AlumnosCarrera\" ac\n"
            + "JOIN public.\"Alumnos\" a ON a.id_alumno = ac.id_alumno\n"
            + "JOIN public.\"Personas\" p ON p.id_persona = a.id_persona\n"
            + "JOIN public.\"Carreras\" c ON c.id_carrera = ac.id_carrera\n"
            + "JOIN alumno.\"Retirados\" r ON r.id_almn_carrera = ac.id_almn_carrera "
            + "JOIN public.\"PeriodoLectivo\" pl ON pl.id_prd_lectivo = r.id_prd_lectivo "
            + "WHERE carrera_activo = true\n"
            + "AND almn_carrera_activo = true ";

    public int guardar(Retirado r) {
        String sql = "INSERT INTO alumno.\"Retirados\"( "
                + "id_almn_carrera, "
                + "id_prd_lectivo, "
                + "fecha_retiro, "
                + "motivo_retiro) "
                + "VALUES (?, ?, ?, ?);";

        PreparedStatement ps = CON.getPSID(sql);
        try {
            ps.setInt(1, r.getAlmnCarrera().getId());
            ps.setInt(2, r.getPeriodo().getID());
            ps.setDate(3, Date.valueOf(LocalDate.now()));
            ps.setString(4, r.getMotivo());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al guardar retirado. \n"
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return CON.getIDGenerado(ps);
    }

    public Retirado getById(int idRetiro) {
        String sql = "SELECT\n"
                + "id_retiro,\n"
                + "id_prd_lectivo,\n"
                + "fecha_retiro,\n"
                + "motivo_retiro\n"
                + "FROM alumno.\"Retirados\" \n"
                + "WHERE id_retiro = ?;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        Retirado r = null;
        try {
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                r = new Retirado();
                r.setId(res.getInt(1));
                PeriodoLectivoMD pl = new PeriodoLectivoMD();
                pl.setID(res.getInt(2));
                r.setPeriodo(pl);
                r.setFechaRetiro(res.getDate(3).toLocalDate());
                r.setMotivo(res.getString(4));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al obtener por id. \n"
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        } finally {
            CON.cerrarCONPS(ps);
        }
        return r;
    }

    public int editar(Retirado r) {
        String sql = "UPDATE alumno.\"Retirados\" "
                + "SET id_prd_lectivo=?, "
                + "motivo_retiro=? "
                + "WHERE id_retiro=?;";
        PreparedStatement ps = CON.getPSID(sql);
        try {
            ps.setInt(1, r.getPeriodo().getID());
            ps.setString(2, r.getMotivo());
            ps.setInt(3, r.getId());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al guardar retirado. \n"
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return CON.getIDGenerado(ps);
    }

    public List<Retirado> getAllTbl() {
        return getForTbl(
                CON.getPSPOOL(BASEQUERY_TBL)
        );
    }

    public List<Retirado> getByCarrera(int idCarrera) {
        String sql = BASEQUERY_TBL
                + " AND ac.id_carrera = ?";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setInt(1, idCarrera);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al consultar retirado por id carrera. \n"
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return getForTbl(ps);
    }

    public List<Retirado> getForBusqueda(String aguja) {
        String sql = BASEQUERY_TBL
                + " AND ( persona_primer_nombre = ? "
                + " OR persona_segundo_nombre = ? "
                + " OR persona_primer_apellido = ? "
                + " OR persona_segundo_apellido = ? "
                + " OR persona_identificacion = ? "
                + ") ";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            aguja = "%" + aguja + "%";
            ps.setString(1, aguja);
            ps.setString(2, aguja);
            ps.setString(3, aguja);
            ps.setString(4, aguja);
            ps.setString(5, aguja);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al consultar retirado por id carrera. \n"
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return getForTbl(ps);
    }

    private List<Retirado> getForTbl(PreparedStatement ps) {
        List<Retirado> rs = new ArrayList<>();
        try {
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                Retirado r = new Retirado();
                r.setId(res.getInt("id_retiro"));
                r.setFechaRetiro(res.getDate("fecha_retiro").toLocalDate());

                AlumnoCarreraMD ac = new AlumnoCarreraMD();
                ac.setId(res.getInt("id_almn_carrera"));
                ac.setFechaRegistro(res.getTimestamp("almn_carrera_fecha_registro").toLocalDateTime());

                CarreraMD c = new CarreraMD();
                c.setId(res.getInt("id_carrera"));
                c.setCodigo(res.getString("carrera_codigo"));

                AlumnoMD a = new AlumnoMD();
                a.setPrimerNombre(res.getString("persona_primer_nombre"));
                a.setSegundoNombre(res.getString("persona_segundo_nombre"));
                a.setPrimerApellido(res.getString("persona_primer_apellido"));
                a.setSegundoApellido(res.getString("persona_segundo_apellido"));
                a.setIdentificacion(res.getString("persona_identificacion"));

                PeriodoLectivoMD pl = new PeriodoLectivoMD();
                pl.setNombre(res.getString("prd_lectivo_nombre"));
                pl.setID(res.getInt("id_prd_lectivo"));
                ac.setCarrera(c);
                ac.setAlumno(a);
                r.setPeriodo(pl);
                r.setAlmnCarrera(ac);
                r.setMotivo(res.getString("motivo_retiro"));

                rs.add(r);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al mapear para table los retirados\n"
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        } finally {
            CON.cerrarCONPS(ps);
        }
        return rs;
    }

}
