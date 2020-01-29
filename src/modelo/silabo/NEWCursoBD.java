package modelo.silabo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.ConnDBPool;
import modelo.curso.CursoMD;
import modelo.materia.MateriaMD;
import modelo.silabo.mbd.ICursoBD;
import utils.CONS;

/**
 *
 * @author gus
 */
public class NEWCursoBD implements ICursoBD {

    private final ConnDBPool CON = ConnDBPool.single();

    private static NEWCursoBD CBD;

    public static NEWCursoBD single() {
        if (CBD == null) {
            CBD = new NEWCursoBD();
        }
        return CBD;
    }

    @Override
    public List<CursoMD> getDocentePeriodoMateria(String nombreMateria, int idDocente, int idPeriodo) {
        String sql = "SELECT DISTINCT c.id_curso,"
                + "c.curso_nombre "
                + "FROM public.\"Cursos\" c "
                + "JOIN public.\"Docentes\" d ON c.id_docente = d.id_docente "
                + "JOIN public.\"Personas\" p ON d.id_persona = p.id_persona "
                + "JOIN public.\"Materias\" m on c.id_materia = m.id_materia\n"
                + "WHERE p.id_persona=? "
                + "AND c.id_prd_lectivo = ? "
                + "AND m.materia_nombre=?";
        PreparedStatement ps = CON.getPSPOOL(sql);
        List<CursoMD> CS = new ArrayList<>();

        try {
            ps.setInt(1, idDocente);
            ps.setInt(2, idPeriodo);
            ps.setString(3, nombreMateria);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                CursoMD cur = new CursoMD();
                cur.setId(res.getInt(1));
                cur.setNombre(res.getString(2));
                CS.add(cur);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al consultar cursos por periodo . "
                    + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return CS;
    }

    @Override
    public int getNumeroAlumnos(int idCurso) {
        int num = 0;
        String sql = "SELECT count(id_alumno) "
                + "FROM public.\"AlumnoCurso\" "
                + "WHERE id_curso = ?;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setInt(1, idCurso);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                num = res.getInt(1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al consultar numero de alumnos. "
                    + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return num;
    }

    @Override
    public List<CursoMDS> getByCurso(int idCurso) {
        String sql = "";
        List<CursoMDS> CS = new ArrayList<>();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ps.setInt(1, idCurso);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                CursoMDS c = new CursoMDS();
                c.getId_carrera().setNombre(res.getString(1));
                c.getId_materia().setNombre(res.getString(2));
                c.getId_materia().setCodigo(res.getString(3));
                c.setCurso_nombre(res.getString(4));
                c.getId_persona().setPrimerNombre(res.getString(5));
                c.getId_persona().setPrimerApellido(res.getString(6));
                CS.add(c);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al consultar curso para silabo. "
                    + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return CS;
    }

    public List<CursoMD> getFaltantesSeguimientoEval(SilaboMD silabo, int idUnidad) {

        String SELECT = ""
                + "WITH mis_cursos AS (\n"
                + "	SELECT\n"
                + "		\"Cursos\".id_curso,\n"
                + "		\"Cursos\".curso_nombre \n"
                + "	FROM\n"
                + "		\"Cursos\"\n"
                + "		INNER JOIN \"Docentes\" ON \"Docentes\".id_docente = \"Cursos\".id_docente \n"
                + "	WHERE\n"
                + "		\"Docentes\".docente_codigo = '" + CONS.USUARIO.getPersona().getIdentificacion() + "' \n"
                + "		AND \"Cursos\".id_prd_lectivo = " + silabo.getPeriodo().getID() + " \n"
                + "		AND \"Cursos\".id_materia = " + silabo.getMateria().getId() + " \n"
                + "	) \n"
                + "SELECT\n"
                + "    mis_cursos.id_curso,\n"
                + "    mis_cursos.curso_nombre\n"
                + "FROM\n"
                + "    mis_cursos \n"
                + "WHERE\n"
                + "    mis_cursos.id_curso NOT IN ( \n"
                + "        SELECT DISTINCT \n"
                + "            \"SeguimientoEvaluacion\".id_curso \n"
                + "        FROM \n"
                + "            \"SeguimientoEvaluacion\" \n"
                + "            INNER JOIN mis_cursos ON mis_cursos.id_curso = \"SeguimientoEvaluacion\".id_curso \n"
                + "            INNER JOIN \"EvaluacionSilabo\" ON \"EvaluacionSilabo\".id_evaluacion =  \"SeguimientoEvaluacion\".id_evaluacion	\n"
                + "        WHERE\n"
                + "            \"EvaluacionSilabo\".id_unidad = " + idUnidad + "\n"
                + "    )"
                + "";
        System.out.println(SELECT);
        List<CursoMD> lista = new ArrayList<>();
        ResultSet rs = CON.ejecutarQuery(SELECT);

        try {
            while (rs.next()) {
                CursoMD curso = new CursoMD();
                curso.setId(rs.getInt("id_curso"));
                curso.setNombre(rs.getString("curso_nombre"));
                lista.add(curso);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NEWCursoBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(rs);
        }

        return lista;

    }

    public List<CursoMD> getDeReferenciaSeguimientoEval(int idUnidad) {

        String SELECT = ""
                + "SELECT DISTINCT\n"
                + "	\"Cursos\".id_curso,\n"
                + "	\"Cursos\".curso_nombre \n"
                + "FROM\n"
                + "	\"SeguimientoEvaluacion\"\n"
                + "	INNER JOIN \"EvaluacionSilabo\" ON \"EvaluacionSilabo\".id_evaluacion = \"SeguimientoEvaluacion\".id_evaluacion\n"
                + "	INNER JOIN \"Cursos\" ON \"Cursos\".id_curso = \"SeguimientoEvaluacion\".id_curso \n"
                + "WHERE\n"
                + "	id_unidad = " + idUnidad
                + "";

        List<CursoMD> lista = new ArrayList<>();

        ResultSet rs = CON.ejecutarQuery(SELECT);

        try {
            while (rs.next()) {

                CursoMD curso = new CursoMD();

                curso.setId(rs.getInt("id_curso"));
                curso.setNombre(rs.getString("curso_nombre"));
                lista.add(curso);

            }
        } catch (SQLException ex) {
            Logger.getLogger(NEWCursoBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(rs);
        }

        return lista;

    }

    public List<CursoMD> getMisCursosBy(String cedulaDocente, int idPeriodo, int idMateria) {

        String SELECT = ""
                + "SELECT\n"
                + "	\"Cursos\".id_curso,\n"
                + "	\"Cursos\".curso_nombre \n"
                + "FROM\n"
                + "	\"Cursos\"\n"
                + "	INNER JOIN \"Docentes\" ON \"Cursos\".id_docente = \"Docentes\".id_docente \n"
                + "WHERE\n"
                + "	\"Docentes\".docente_codigo = '" + cedulaDocente + "' \n"
                + "	AND \"Cursos\".id_prd_lectivo = " + idPeriodo + " \n"
                + "	AND \"Cursos\".id_materia = " + idMateria
                + "";
        List<CursoMD> lista = new ArrayList<>();
        ResultSet rs = CON.ejecutarQuery(SELECT);

        try {
            while (rs.next()) {
                CursoMD curso = new CursoMD();
                curso.setId(rs.getInt("id_curso"));
                curso.setNombre(rs.getString("curso_nombre"));
                lista.add(curso);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NEWCursoBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(rs);
        }

        return lista;

    }

    public List<CursoMD> getCursosMateriasBy(String periodo, int idPersona) {
        String SELEC = ""
                + "SELECT\n"
                + "	\"Cursos\".curso_nombre,\n"
                + "	\"Cursos\".id_curso,\n"
                + "	\"Materias\".id_materia,\n"
                + "	\"Materias\".materia_nombre \n"
                + "FROM\n"
                + "	\"Cursos\"\n"
                + "	INNER JOIN \"Materias\" ON \"Cursos\".id_materia = \"Materias\".id_materia\n"
                + "	INNER JOIN \"Docentes\" ON \"Cursos\".id_docente = \"Docentes\".id_docente\n"
                + "	INNER JOIN \"PeriodoLectivo\" ON \"Cursos\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo \n"
                + "WHERE\n"
                + "	\"PeriodoLectivo\".prd_lectivo_nombre = '" + periodo + "' \n"
                + "	AND \"Docentes\".id_persona = " + idPersona + "\n"
                + "ORDER BY \"Cursos\".curso_nombre, \"Materias\".materia_nombre"
                + "";

        List<CursoMD> cursos = new ArrayList<>();

        ResultSet rs = CON.ejecutarQuery(SELEC);

        try {
            while (rs.next()) {
                MateriaMD materiaMD = new MateriaMD();
                materiaMD.setId(rs.getInt("id_materia"));
                materiaMD.setNombre(rs.getString("materia_nombre"));

                CursoMD cursoMD = new CursoMD();
                cursoMD.setId(rs.getInt("id_curso"));
                cursoMD.setNombre(rs.getString("curso_nombre"));
                cursoMD.setMateria(materiaMD);

                cursos.add(cursoMD);

            }
        } catch (SQLException ex) {
            Logger.getLogger(NEWCursoBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(rs);
        }

        return cursos;
    }

}
