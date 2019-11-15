package modelo.silabo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.CONS;
import modelo.ConnDBPool;
import modelo.carrera.CarreraMD;
import modelo.materia.MateriaMD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.silabo.mbd.ISilaboBD;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author gus
 */
public class NEWSilaboBD implements ISilaboBD {

    private final ConnDBPool CON = ConnDBPool.single();

    private static NEWSilaboBD SBD;

    public static NEWSilaboBD single() {
        if (SBD == null) {
            SBD = new NEWSilaboBD();
        }
        return SBD;
    }

    private static final String INSERT = "INSERT INTO "
            + "public.\"Silabo\"("
            + " id_materia,"
            + " id_prd_lectivo,"
            + " fecha_silabo "
            + ") VALUES (?, ?, now());";

    private static final String FROM_SILABO = "SELECT DISTINCT id_silabo, "
            + "s.id_materia, "
            + "m.materia_nombre, "
            + "m.materia_horas_docencia, "
            + "m.materia_horas_practicas, "
            + "m.materia_horas_auto_estudio, "
            + "estado_silabo, "
            + "pr.id_prd_lectivo, "
            + "pr.prd_lectivo_fecha_inicio, "
            + "pr.prd_lectivo_fecha_fin, "
            + "pr.prd_lectivo_nombre "
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
            ps.setInt(2, s.getPeriodo().getID());
            return CON.getIDGenerado(ps);
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public SilaboMD getByCarreraPersonaPeriodo(
            String nombreCarrera,
            int idPersona,
            String nombrePeriodo
    ) {
        SilaboMD s = null;
        String sql = FROM_SILABO
                + " crr.carrera_nombre = ? "
                + " AND p.id_persona = ? "
                + " AND prd_lectivo_nombre = ? ;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setString(1, nombreCarrera);
            ps.setInt(2, idPersona);
            ps.setString(3, nombrePeriodo);

            ResultSet res = ps.executeQuery();
            s = getOneForFrm(res);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al consultar un unico silabo. "
                    + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return s;
    }

    @Override
    public List<SilaboMD> getByCarreraPersona(
            String nombreCarrera,
            int idPersona
    ) {

        List<SilaboMD> SS = new ArrayList<>();

        String sql = FROM_SILABO
                + "WHERE crr.carrera_nombre = ? "
                + "AND p.id_persona = ?;";
        PreparedStatement ps = CON.getPSPOOL(sql);

        try {
            ps.setString(1, nombreCarrera);
            ps.setInt(2, idPersona);

            ResultSet res = ps.executeQuery();
            SS = getForFrm(res);
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
        String sql = FROM_SILABO
                + "WHERE m.id_materia = ? "
                + "AND p.id_persona = ?;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setInt(1, idMateria);
            ps.setInt(2, idPersona);
            ResultSet res = ps.executeQuery();

            SS = getForFrm(res);
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
        String sql = FROM_SILABO
                + " s.id_silabo = ? ;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setInt(1, idSilabo);
            ResultSet res = ps.executeQuery();
            s = getOneForFrm(res);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al consultar para editar "
                    + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
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

    private List<SilaboMD> getForFrm(ResultSet res) {
        List<SilaboMD> SS = new ArrayList<>();
        if (res != null) {
            try {
                while (res.next()) {
                    SilaboMD s = new SilaboMD();
                    s.setID(res.getInt(1));
                    s.getMateria().setId(res.getInt(2));
                    s.getMateria().setNombre(res.getString(3));
                    s.getMateria().setHorasDocencia(res.getInt(4));
                    s.getMateria().setHorasPracticas(res.getInt(5));
                    s.getMateria().setHorasAutoEstudio(res.getInt(6));
                    s.setEstado(res.getInt(7));
                    s.getPeriodo().setID(res.getInt(8));
                    s.getPeriodo().setFechaInicio(res.getDate(9).toLocalDate());
                    s.getPeriodo().setFechaFin(res.getDate(10).toLocalDate());
                    s.getPeriodo().setNombre(res.getString(11));

                    SS.add(s);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null,
                        "Error al mapear la respuesta de silabo. "
                        + e.getMessage());
            }
        }
        return SS;
    }

    private SilaboMD getOneForFrm(ResultSet res) {
        SilaboMD s = null;
        if (res != null) {
            try {
                while (res.next()) {
                    s = new SilaboMD();
                    s.setID(res.getInt(1));
                    s.getMateria().setId(res.getInt(2));
                    s.getMateria().setNombre(res.getString(3));
                    s.getMateria().setHorasDocencia(res.getInt(4));
                    s.getMateria().setHorasPracticas(res.getInt(5));
                    s.getMateria().setHorasAutoEstudio(res.getInt(6));
                    s.setEstado(res.getInt(7));
                    s.getPeriodo().setID(res.getInt(8));
                    s.getPeriodo().setFechaInicio(res.getDate(9).toLocalDate());
                    s.getPeriodo().setFechaFin(res.getDate(10).toLocalDate());
                    s.getPeriodo().setNombre(res.getString(11));
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null,
                        "Error al obtener un silabo. "
                        + e.getMessage());
            }
        }
        return s;
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

    public List<SilaboMD> getSilaboRef(int idCarrera, int idMateria) {
        String SELECT = ""
                + "SELECT\n"
                + "	\"Silabo\".id_silabo,\n"
                + "	\"PeriodoLectivo\".id_prd_lectivo,\n"
                + "	\"PeriodoLectivo\".prd_lectivo_nombre \n"
                + "FROM\n"
                + "	\"Silabo\"\n"
                + "	INNER JOIN \"PeriodoLectivo\" ON \"Silabo\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo \n"
                + "WHERE\n"
                + "	\"PeriodoLectivo\".id_carrera = " + idCarrera + " \n"
                + "	AND \"Silabo\".id_materia = " + idMateria + " \n"
                + "	AND \"PeriodoLectivo\".id_prd_lectivo <> ( SELECT \"PeriodoLectivo\".id_prd_lectivo FROM \"PeriodoLectivo\" WHERE \"PeriodoLectivo\".id_carrera = 2 ORDER BY \"PeriodoLectivo\".prd_lectivo_fecha_inicio DESC LIMIT 1 ) \n"
                + "ORDER BY\n"
                + "	\"PeriodoLectivo\".prd_lectivo_fecha_inicio DESC \n"
                + "	LIMIT 1"
                + "";

        List<SilaboMD> silabosRef = new ArrayList<>();

        ResultSet rs = CON.ejecutarQuery(SELECT, null);
        try {
            while (rs.next()) {
                SilaboMD silabo = new SilaboMD();
                silabo.setID(rs.getInt("id_silabo"));
                PeriodoLectivoMD periodo = new PeriodoLectivoMD()
                        .setID(rs.getInt("id_prd_lectivo"))
                        .setNombre(rs.getString("prd_lectivo_nombre"));

                silabo.setPeriodo(periodo);

                silabosRef.add(silabo);

            }
        } catch (SQLException ex) {
            Logger.getLogger(NEWSilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(rs);
        }
        return silabosRef;

    }

    private void existeCarpeta(File pdf, JasperPrint jasPDF, SilaboMD silabo) {
        File carpeta = new File("pdfs/");
        if (!carpeta.exists()) {
            if (carpeta.mkdir()) {
                JOptionPane.showMessageDialog(null, "Creamos la carpeta pdfs en la que\n"
                        + "se guardaran los documentos.");
            }
        }

        try {
            OutputStream output = new FileOutputStream(pdf);
            JasperExportManager.exportReportToPdfStream(jasPDF, output);
            FileInputStream fis = new FileInputStream(pdf);
            guardarPDFSilabo(silabo.getID(), fis, pdf);
        } catch (FileNotFoundException | JRException e) {
            JOptionPane.showMessageDialog(null, "Error guardar PDF: " + e);
        }
    }

    public void imprimirProgramaAnalitico(SilaboMD silabo) {
        Connection conn = CON.getConnection();
        try {
            JasperReport jr = (JasperReport) JRLoader.loadObject(getClass()
                    .getResource("/vista/silabos/reportes/silabo_duales/primera_pag.jasper"));

            Map parametro = new HashMap();
            parametro.put("parameter1", String.valueOf(silabo.getMateria().getId()));
            parametro.put("id_silabo", String.valueOf(silabo.getID()));
            JasperPrint jp = JasperFillManager.fillReport(jr, parametro, conn);
            JasperViewer pv = new JasperViewer(jp, false);
            pv.setVisible(true);
            pv.setTitle("Silabo Duales");

            File pdf = new File(("pdfs/" + "SD-" + silabo.getMateria().getNombre() + "-" + LocalDate.now() + ".pdf"));
            existeCarpeta(pdf, jp, silabo);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        } finally {
            CON.close(conn);
        }
    }

    public void imprimirProgramaAnaliticoConSemanas(SilaboMD silabo, int semanas) {
        Connection conn = CON.getConnection();

        try {
            JasperReport jr = (JasperReport) JRLoader.loadObject(getClass()
                    .getResource("/vista/silabos/"
                            + "reportes/silabo_duales/"
                            + "primera_pag_param_semanas.jasper")
            );

            Map parametro = new HashMap();
            parametro.put("parameter1", String.valueOf(silabo.getMateria().getId()));
            parametro.put("id_silabo", String.valueOf(silabo.getID()));
            parametro.put("num_semanas", semanas);

            JasperPrint jp = JasperFillManager.fillReport(jr, parametro, conn);
            JasperViewer pv = new JasperViewer(jp, false);
            pv.setVisible(true);
            pv.setTitle("Silabos Duales | Semanas");

            File pdf = new File(("pdfs/" + "SD-" + silabo.getMateria().getNombre() + "-" + LocalDate.now() + ".pdf"));
            existeCarpeta(pdf, jp, silabo);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        } finally {
            CON.close(conn);
        }

    }

    public void imprimirSilabo(SilaboMD silabo) {
        Connection conn = CON.getConnection();
        try {
            JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/silabos/reportes/silabo2/primera_pag.jasper"));
            Map parametro = new HashMap();

            parametro.put("parameter1", String.valueOf(silabo.getMateria().getId()));
            parametro.put("id_silabo", String.valueOf(silabo.getID()));
            JasperPrint jp = JasperFillManager.fillReport(jr, parametro, conn);
            JasperViewer pv = new JasperViewer(jp, false);
            pv.setVisible(true);
            pv.setTitle("Sílabo");

            //EXPORTACION A PDF
            File pdf = new File(("pdfs/" + "ST-" + silabo.getMateria().getNombre() + "-" + LocalDate.now() + ".pdf"));
            existeCarpeta(pdf, jp, silabo);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, "Error reporte: " + e);
        } finally {
            CON.close(conn);
        }

    }

    public boolean eliminar(SilaboMD silabo) {

        String DELETE = "DELETE FROM public.\"Silabo\"\n"
                + "	WHERE id_silabo=" + silabo.getID();

        return CON.ejecutar(DELETE, null) == null;

    }

    public List<SilaboMD> findBy(String cedulaDocente, int idCarrera) {
        String SELECT = ""
                + "WITH mis_periodos_materias AS (\n"
                + "    SELECT DISTINCT\n"
                + "        \"Cursos\".id_prd_lectivo,\n"
                + "        \"Cursos\".id_materia \n"
                + "    FROM\n"
                + "        \"Cursos\"\n"
                + "        INNER JOIN \"Docentes\" ON \"Cursos\".id_docente = \"Docentes\".id_docente\n"
                + "        INNER JOIN \"PeriodoLectivo\" ON \"Cursos\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo \n"
                + "    WHERE\n"
                + "        \"Docentes\".docente_codigo = '" + cedulaDocente + "' \n"
                + "        AND \"PeriodoLectivo\".id_carrera = " + idCarrera + " \n"
                + ")\n"
                + "SELECT\n"
                + "	\"PeriodoLectivo\".prd_lectivo_nombre,\n"
                + "	\"PeriodoLectivo\".id_prd_lectivo,\n"
                + "	\"Materias\".id_materia,\n"
                + "	\"Materias\".materia_nombre,\n"
                + "	\"Silabo\".fecha_silabo,\n"
                + "	\"Silabo\".estado_silabo,\n"
                + "	\"Silabo\".id_silabo,\n"
                + "	\"Carreras\".carrera_modalidad \n"
                + "FROM\n"
                + "	\"Silabo\"\n"
                + "	INNER JOIN \"PeriodoLectivo\" ON \"Silabo\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo\n"
                + "	INNER JOIN \"Materias\" ON \"Silabo\".id_materia = \"Materias\".id_materia\n"
                + "	INNER JOIN \"Carreras\" ON \"PeriodoLectivo\".id_carrera = \"Carreras\".id_carrera\n"
                + "	INNER JOIN mis_periodos_materias ON \"PeriodoLectivo\".id_prd_lectivo = mis_periodos_materias.id_prd_lectivo \n"
                + "	    AND \"Materias\".id_materia = mis_periodos_materias.id_materia \n"
                + "ORDER BY\n"
                + "	\"PeriodoLectivo\".id_prd_lectivo DESC,\n"
                + "    \"Materias\".materia_nombre ASC"
                + "";

        System.out.println(SELECT);

        List<SilaboMD> silabos = new ArrayList<>();

        ResultSet rs = CON.ejecutarQuery(SELECT, null);

        try {
            while (rs.next()) {
                SilaboMD silabo = new SilaboMD();

                silabo.setID(rs.getInt("id_silabo"));
                silabo.setEstado(rs.getInt("estado_silabo"));

                CarreraMD carrera = new CarreraMD();
                carrera.setModalidad(rs.getString("carrera_modalidad"));

                silabo.setPeriodo(
                        new PeriodoLectivoMD()
                                .setID(rs.getInt("id_prd_lectivo"))
                                .setNombre(rs.getString("prd_lectivo_nombre"))
                                .setCarrera(carrera)
                );

                silabo.setMateria(
                        new MateriaMD()
                                .setId(rs.getInt("id_materia"))
                                .setNombre(rs.getString("materia_nombre"))
                );
                try {
                    silabo.setFechaGeneracion(
                            rs.getDate("fecha_silabo").toLocalDate()
                    );

                } catch (NullPointerException e) {
                    System.out.println(
                            "NOT TIENE FECHA DE GENERACION: "
                            + silabo.getID()
                            + " "
                            + silabo.getPeriodo().getNombre()
                            + " "
                            + silabo.getMateria().getNombre()
                    );
                }

                silabos.add(silabo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(rs);
        }

        return silabos;

    }

    public String getInformacion(SilaboMD silabo) {

        String SELECT = ""
                + "SELECT \n"
                + "    DISTINCT ON ( \"Personas\".persona_identificacion ) \"Cursos\".id_docente,\n"
                + "	(   \n"
                + "        \"Personas\".persona_identificacion || '  |  ' || \n"
                + "	    \"Personas\".persona_primer_apellido || ' ' ||\n"
                + "	    \"Personas\".persona_primer_nombre\n"
                + "    ) as docente,\n"
                + "	(\n"
                + "	    SELECT \n"
                + "            string_agg ( \"Cursos\".curso_nombre, ', ' ) \n"
                + "        FROM \n"
                + "            \"Cursos\" \n"
                + "	    WHERE\n"
                + "		    \"Cursos\".id_prd_lectivo = " + silabo.getPeriodo().getID() + " \n"
                + "            AND \"Cursos\".id_materia = " + silabo.getMateria().getId() + " \n"
                + "            AND \"Docentes\".id_docente = id_docente \n"
                + "	) as cursos\n"
                + "FROM\n"
                + "	\"Cursos\"\n"
                + "	INNER JOIN \"Docentes\" ON \"Cursos\".id_docente = \"Docentes\".id_docente\n"
                + "	INNER JOIN \"Personas\" ON \"Docentes\".id_persona = \"Personas\".id_persona \n"
                + "WHERE\n"
                + "	\"Cursos\".id_prd_lectivo = " + silabo.getPeriodo().getID() + " \n"
                + "	AND \"Cursos\".id_materia = " + silabo.getMateria().getId()
                + "";

        String informacion = ""
                + "         Periodo Lectivo:\n"
                + "             " + silabo.getPeriodo().getNombre() + "\n\n"
                + "         Materia:\n"
                + "             " + silabo.getMateria().getNombre() + "\n\n"
                + "         Docentes:\n";

        try {
            ResultSet rs = CON.ejecutarQuery(SELECT);
            while (rs.next()) {
                informacion += "             " + rs.getString("docente") + "\n"
                        + "             Cursos: " + rs.getString("cursos") + "\n\n";
            }
        } catch (SQLException ex) {
            Logger.getLogger(NEWSilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return informacion;
    }

    public boolean ediantoSilabo(int idSilabo, boolean estado) {

        String UPDATE = ""
                + "UPDATE \"Silabo\" \n"
                + "SET editando = " + estado + ",\n"
                + "editado_por = '" + CONS.USUARIO.getPersona().getIdPersona() + "' \n"
                + "WHERE\n"
                + "	id_silabo = " + idSilabo + ";"
                + "";

        return CON.ejecutar(UPDATE) == null;
    }

    public SilaboMD getDisponibilidad(int idSilabo) {

        String SELECT = ""
                + "SELECT\n"
                + "	\"public\".\"Silabo\".editando,\n"
                + "	\"public\".\"Personas\".persona_identificacion,\n"
                + "	\"public\".\"Personas\".persona_primer_apellido,\n"
                + "	\"public\".\"Personas\".persona_primer_nombre \n"
                + "FROM\n"
                + "	\"public\".\"Silabo\"\n"
                + "	INNER JOIN \"public\".\"Personas\" ON \"public\".\"Personas\".id_persona = \"public\".\"Silabo\".editado_por \n"
                + "WHERE\n"
                + "	\"public\".\"Silabo\".id_silabo =" + idSilabo
                + "";

        ResultSet rs = CON.ejecutarQuery(SELECT);
        SilaboMD silabo = null;
        try {
            while (rs.next()) {
                silabo = new SilaboMD();

                silabo.setID(rs.getInt("id_silabo"));
                silabo.setEditando(rs.getBoolean("editando"));
                silabo.setEditando(rs.getBoolean("editado_por"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(NEWSilaboBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
