package modelo.silabo;

import java.io.File;
import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.ConnDBPool;
import modelo.silabo.mbd.ISilaboBD;

/**
 *
 * @author gus
 */
public class NEWSilaboBD implements ISilaboBD {

    private final ConnDBPool CON = ConnDBPool.single();

    private static final String INSERT = "INSERT INTO "
            + "public.\"Silabo\"("
            + " id_materia,"
            + " id_prd_lectivo,"
            + " fecha_silabo "
            + ") VALUES (?, ?, now());";

    private static final String FROM_SILABO = ""
            + "FROM \"Silabo\" AS s\n"
            + "JOIN \"Materias\" AS m ON s.id_materia=m.id_materia\n"
            + "JOIN \"PeriodoLectivo\" AS pr ON pr.id_prd_lectivo=s.id_prd_lectivo\n"
            + "JOIN \"Carreras\" AS crr ON crr.id_carrera = m.id_carrera\n"
            + "JOIN \"Cursos\" AS cr ON cr.id_materia=m.id_materia\n"
            + "JOIN \"Docentes\" AS d ON d.id_docente= cr.id_docente\n"
            + "JOIN \"Personas\" AS p ON d.id_persona=p.id_persona  "
            + "WHERE ";

    @Override
    public int nuevoSilabo(SilaboMD s) {
        PreparedStatement ps = CON.getPSID(INSERT);
        try {
            ps.setInt(1, s.getMateria().getId());
            ps.setInt(2, s.getPeriodo().getId());
            return CON.getIDGenerado(ps);
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public List<SilaboMD> getByCarreraPersonaPeriodo(
            String nombreCarrera,
            int idPersona,
            String nombrePeriodo
    ) {
        List<SilaboMD> SS = new ArrayList<>();
        String sql = "SELECT DISTINCT id_silabo,\n"
                + "s.id_materia, m.materia_nombre "
                + FROM_SILABO
                + " crr.carrera_nombre = ? "
                + " AND p.id_persona = ? "
                + " AND prd_lectivo_nombre = ? ;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setString(1, nombreCarrera);
            ps.setInt(2, idPersona);
            ps.setString(3, nombrePeriodo);

            ResultSet res = ps.executeQuery();

            while (res.next()) {
                SilaboMD s = new SilaboMD();
                s.setID(res.getInt(1));
                s.getMateria().setId(res.getInt(2));
                s.getMateria().setNombre(res.getString(3));

                SS.add(s);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al consultar. "
                    + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return SS;
    }

    @Override
    public List<SilaboMD> getByCarreraPersona(
            String nombreCarrera,
            int idPersona
    ) {

        List<SilaboMD> SS = new ArrayList<>();

        String sql = "SELECT DISTINCT id_silabo, "
                + "s.id_materia, "
                + "m.materia_nombre, "
                + "m.materia_horas_docencia, "
                + "m.materia_horas_practicas, "
                + "m.materia_horas_auto_estudio, "
                + "estado_silabo, "
                + "pr.id_prd_lectivo, "
                + "pr.prd_lectivo_fecha_inicio, "
                + "pr.prd_lectivo_fecha_fin "
                + FROM_SILABO
                + "WHERE crr.carrera_nombre = ? "
                + "AND p.id_persona = ?;";
        PreparedStatement ps = CON.getPSPOOL(sql);

        try {
            ps.setString(1, nombreCarrera);
            ps.setInt(2, idPersona);

            ResultSet res = ps.executeQuery();

            while (res.next()) {

                SilaboMD s = new SilaboMD();
                s.setID(res.getInt(1));
                s.getMateria().setId(res.getInt(2));
                s.getMateria().setNombre(res.getString(3));
                s.getMateria().setHorasDocencia(res.getInt(4));
                s.getMateria().setHorasPracticas(res.getInt(5));
                s.getMateria().setHorasAutoEstudio(res.getInt(6));
                s.setEstado(res.getInt(7));
                s.getPeriodo().setPeriodo(res.getInt(8));
                s.getPeriodo().setFechaInicio(res.getDate(9).toLocalDate());
                s.getPeriodo().setFechaFin(res.getDate(10).toLocalDate());

                SS.add(s);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al consultar. "
                    + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return SS;
    }

    @Override
    public List<SilaboMD> getAnterioresByMateriaPersona(
            int idPersona,
            int idMateria
    ) {
        List<SilaboMD> SS = new ArrayList<>();
        String sql = "SELECT DISTINCT id_silabo, "
                + "s.id_materia, "
                + "m.materia_nombre, "
                + "m.materia_horas_docencia, "
                + "m.materia_horas_practicas, "
                + "m.materia_horas_auto_estudio, "
                + "estado_silabo, "
                + "pr.id_prd_lectivo, "
                + "pr.prd_lectivo_nombre "
                + FROM_SILABO
                + "WHERE m.id_materia = ? "
                + "AND p.id_persona = ?;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setInt(1, idMateria);
            ps.setInt(2, idPersona);
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                SilaboMD s = new SilaboMD();
                s.setID(res.getInt(1));
                s.getMateria().setId(res.getInt(2));
                s.getMateria().setNombre(res.getString(3));
                s.getMateria().setHorasDocencia(res.getInt(4));
                s.getMateria().setHorasPracticas(res.getInt(5));
                s.getMateria().setHorasAutoEstudio(res.getInt(6));
                s.setEstado(res.getInt(7));
                s.getPeriodo().setPeriodo(res.getInt(8));
                s.getPeriodo().setNombre(res.getString(9));

                SS.add(s);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al consultar. "
                    + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return SS;
    }

    @Override
    public void setEstado(int idSilabo, int estado) {
        String sql = "UPDATE public.\"Silabo\" "
                + " SET estado_silabo = ?"
                + " WHERE id_silabo = ?;";

        PreparedStatement ps = CON.getPSPOOL(sql);

        try {
            ps.setInt(1, estado);
            ps.setInt(2, idSilabo);
            CON.noSQLPOOL(ps);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al cambiar el estado del silabo. "
                    + e.getMessage());
        }
    }

    @Override
    public SilaboMD getSilaboById(int idSilabo) {
        SilaboMD s = null;
        String sql = "SELECT id_silabo, "
                + "id_materia, "
                + "id_prd_lectivo, "
                + "estado_silabo "
                + "FROM public.\"Silabo\"\n"
                + "WHERE id_silabo = ?;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        return s;
    }

    @Override
    public void guardarPDFAnalitico(int idSilabo, FileInputStream fis, File f) {
        String sql = "UPDATE public.\"Silabo\" "
                + " SET documento_analitico = ? "
                + " WHERE id_silabo = ?;";
        guardarPDF(sql, idSilabo, fis, f);
    }

    @Override
    public void guardarPDFSilabo(
            int idSilabo,
            FileInputStream fis,
            File f
    ) {
        String sql = "UPDATE public.\"Silabo\" "
                + " SET documento_silabo = ? "
                + " WHERE id_silabo = ?;";
        guardarPDF(sql, idSilabo, fis, f);

    }

    private void guardarPDF(
            String sql,
            int idSilabo,
            FileInputStream fis,
            File f
    ) {

        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setBinaryStream(1, fis, (int) f.length());
            ps.setInt(2, idSilabo);
            CON.noSQLPOOL(ps);
        } catch (SQLException e) {
        }
    }
    
    

}
