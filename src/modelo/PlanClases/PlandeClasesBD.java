package modelo.PlanClases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.curso.CursoMD;
import modelo.materia.MateriaMD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.DocenteMD;
import modelo.unidadSilabo.UnidadSilaboMD;
import utils.CONBD;

public class PlandeClasesBD extends CONBD {

    private static PlandeClasesBD INSTANCE = null;

    public static PlandeClasesBD single() {
        if (INSTANCE == null) {
            INSTANCE = new PlandeClasesBD();
        }

        return INSTANCE;
    }

    public PlandeClasesBD() {
    }

    public boolean insertarPlanClases(PlandeClasesMD pl) {

        String SELECT = ""
                + "INSERT INTO public.\"PlandeClases\"(\n"
                + "	 id_curso, id_unidad, observaciones,\n"
                + "	 fecha_revision, fecha_generacion, fecha_cierre,trabajo_autonomo)\n"
                + "	VALUES (?, ?, ?, ?, ?, ?,?)";

        PreparedStatement stmt = CON.prepareStatement(SELECT);
        try {

            stmt.setInt(1, pl.getCurso().getId());
            stmt.setInt(2, pl.getUnidad().getIdUnidad());
            stmt.setString(3, pl.getObservaciones());

            stmt.setDate(4, null);
            stmt.setDate(5, java.sql.Date.valueOf(LocalDate.now()));
            stmt.setDate(6, null);
            stmt.setString(7, pl.getTrabajoAutonomo());
//            st.setDate(4, java.sql.Date.valueOf(getFecha_revision()));
//            st.setDate(5, java.sql.Date.valueOf(getFecha_generacion()));
//            st.setDate(6, java.sql.Date.valueOf(getFecha_cierre()));
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Falló al guardar");
        } finally {
            CON.close(stmt);
        }
        return true;
    }

    public boolean editarFechageneracoion(int idpla, LocalDate fecha) {

        String SELECT = "UPDATE public.\"PlandeClases\"\n"
                + "	SET fecha_generacion=?\n"
                + "	WHERE id_plan_clases=?";
        PreparedStatement stmt = CON.prepareStatement(SELECT);
        try {

            stmt.setDate(1, java.sql.Date.valueOf(fecha));
            stmt.setInt(2, idpla);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Falló al guardar");
        } finally {
            CON.close(stmt);
        }
        return true;
    }

    public static List<PlandeClasesMD> consultarPlanClase(String[] parametros) {
        List<PlandeClasesMD> lista_plan = new ArrayList<>();
        String SELECT = "SELECT DISTINCT pla.id_plan_clases,us.numero_unidad,p.persona_primer_apellido,p.persona_primer_nombre,m.materia_nombre, cr.curso_nombre,pla.estado_plan,pla.fecha_generacion\n"
                + "       FROM \"Silabo\" AS s\n"
                + "       JOIN \"Materias\" AS m ON s.id_materia=m.id_materia\n"
                + "       JOIN \"PeriodoLectivo\" AS pr ON pr.id_prd_lectivo=s.id_prd_lectivo\n"
                + "                    JOIN \"Carreras\" AS crr ON crr.id_carrera = m.id_carrera\n"
                + "                    JOIN \"Cursos\" AS cr ON cr.id_materia=m.id_materia\n"
                + "                    JOIN \"Docentes\" AS d ON d.id_docente= cr.id_docente\n"
                + "                    JOIN \"Personas\" AS p ON d.id_persona=p.id_persona \n"
                + "					JOIN \"PlandeClases\" AS pla on  cr.id_curso=pla.id_Curso \n"
                + "					JOIN \"UnidadSilabo\" AS us on pla.id_unidad=us.id_unidad\n"
                + "					JOIN \"Jornadas\" AS jo on cr.id_jornada=jo.id_jornada\n"
                + "                    WHERE crr.carrera_nombre='" + parametros[0] + "'\n"
                + "                    AND p.id_persona=" + parametros[3] + "\n"
                + " AND jo.nombre_jornada='" + parametros[1] + "' \n"
                + " AND cr.id_prd_lectivo=" + parametros[4] + " \n"
                + " AND m.materia_nombre ILIKE '%" + parametros[2] + "%'\n"
                + "ORDER BY p.persona_primer_apellido, cr.curso_nombre";

        ResultSet rs = CON.ejecutarQuery(SELECT);

        try {

            while (rs.next()) {
                PlandeClasesMD pl = new PlandeClasesMD();
                pl.setID(rs.getInt(1));
                pl.getUnidad().setId(rs.getInt(2));
                pl.getPersona().setPrimerApellido(rs.getString(3));
                pl.getPersona().setPrimerNombre(rs.getString(4));
                pl.getMateria().setNombre(rs.getString(5));
                pl.getCurso().setNombre(rs.getString(6));
                pl.setEstado(rs.getInt(7));
                pl.setFechaGeneracion(rs.getDate(8).toLocalDate());
                lista_plan.add(pl);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlandeClasesBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(rs);
        }
        return lista_plan;
    }

    public static List<PlandeClasesMD> consultarPlanClaseCoordinador(String[] parametros) {
        List<PlandeClasesMD> lista_plan = new ArrayList<>();
        String SELECT = "SELECT DISTINCT pla.id_plan_clases,us.numero_unidad,p.persona_primer_apellido,p.persona_primer_nombre,m.materia_nombre, cr.curso_nombre,pla.estado_plan,pla.fecha_generacion\n"
                + "       FROM \"Silabo\" AS s\n"
                + "       JOIN \"Materias\" AS m ON s.id_materia=m.id_materia\n"
                + "       JOIN \"PeriodoLectivo\" AS pr ON pr.id_prd_lectivo=s.id_prd_lectivo\n"
                + "                    JOIN \"Carreras\" AS crr ON crr.id_carrera = m.id_carrera\n"
                + "                    JOIN \"Cursos\" AS cr ON cr.id_materia=m.id_materia\n"
                + "                    JOIN \"Docentes\" AS d ON d.id_docente= cr.id_docente\n"
                + "                    JOIN \"Personas\" AS p ON d.id_persona=p.id_persona \n"
                + "					JOIN \"PlandeClases\" AS pla on  cr.id_curso=pla.id_Curso \n"
                + "					JOIN \"UnidadSilabo\" AS us on pla.id_unidad=us.id_unidad\n"
                + "					JOIN \"Jornadas\" AS jo on cr.id_jornada=jo.id_jornada\n"
                + "                    WHERE crr.carrera_nombre='" + parametros[0] + "'\n"
                + "                    AND jo.nombre_jornada='" + parametros[1] + "' \n"
                + "AND cr.id_prd_lectivo=" + parametros[3] + " \n"
                + "AND m.materia_nombre ILIKE '%" + parametros[2] + "%'\n"
                + "ORDER BY p.persona_primer_apellido, cr.curso_nombre";

        ResultSet rs = CON.ejecutarQuery(SELECT);

        try {

            while (rs.next()) {
                PlandeClasesMD pl = new PlandeClasesMD();
                pl.setID(rs.getInt(1));
                pl.getUnidad().setId(rs.getInt(2));
                pl.getPersona().setPrimerApellido(rs.getString(3));
                pl.getPersona().setPrimerNombre(rs.getString(4));
                pl.getMateria().setNombre(rs.getString(5));
                pl.getCurso().setNombre(rs.getString(6));
                pl.setEstado(rs.getInt(7));
                pl.setFechaGeneracion(rs.getDate(8).toLocalDate());
                lista_plan.add(pl);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlandeClasesBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(rs);
        }
        return lista_plan;
    }

    public static List<PlandeClasesMD> consultarPlanClaseExistente(String[] parametros) {
        List<PlandeClasesMD> lista_plan = new ArrayList<>();

        String SELECT = "SELECT DISTINCT pla.id_plan_clases,us.id_unidad, cr.id_curso\n"
                + "       FROM \"Silabo\" AS s\n"
                + "       JOIN \"Materias\" AS m ON s.id_materia=m.id_materia\n"
                + "       JOIN \"PeriodoLectivo\" AS pr ON pr.id_prd_lectivo=s.id_prd_lectivo\n"
                + "                    JOIN \"Carreras\" AS crr ON crr.id_carrera = m.id_carrera\n"
                + "                    JOIN \"Cursos\" AS cr ON cr.id_materia=m.id_materia\n"
                + "                    JOIN \"Docentes\" AS d ON d.id_docente= cr.id_docente\n"
                + "                    JOIN \"Personas\" AS p ON d.id_persona=p.id_persona \n"
                + "					JOIN \"PlandeClases\" AS pla on  cr.id_curso=pla.id_Curso \n"
                + "					JOIN \"UnidadSilabo\" AS us on pla.id_unidad=us.id_unidad\n"
                + "					JOIN \"Jornadas\" AS jo on cr.id_jornada=jo.id_jornada\n"
                + "                    WHERE crr.carrera_nombre= '" + parametros[0] + "'\n"
                + "                    AND p.id_persona=" + parametros[1] + " AND cr.id_prd_lectivo=" + parametros[2] + " ";

        ResultSet rs = CON.ejecutarQuery(SELECT);
        try {
            while (rs.next()) {
                PlandeClasesMD pl = new PlandeClasesMD();
                pl.setID(rs.getInt(1));
                pl.getUnidad().setId(rs.getInt(2));
                pl.getCurso().setId(rs.getInt(3));
                lista_plan.add(pl);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlandeClasesBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(rs);
        }
        return lista_plan;
    }

    public void eliminarPlanClase(PlandeClasesMD pl) {

        String DELETE = "delete from \"PlandeClases\" where id_plan_clases=" + pl.getID();
        PreparedStatement stmt = CON.prepareStatement(DELETE);
        try {
            stmt.setInt(1, pl.getID());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Falló al eliminar antes de guardar");
        } finally {
            CON.close(stmt);
        }

    }

    public static List<PlandeClasesMD> consultarPlanClaseObservacion(int plan_clase) {
        List<PlandeClasesMD> lista_plan = new ArrayList<>();

        String SELECT = "select observaciones,trabajo_autonomo from \"PlandeClases\" where id_plan_clases=?";

        PreparedStatement stmt = CON.prepareStatement(SELECT);
        try {

            stmt.setInt(1, plan_clase);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PlandeClasesMD pc = new PlandeClasesMD();
                pc.setObservaciones(rs.getString(1));
                pc.setTrabajoAutonomo(rs.getString(2));
                lista_plan.add(pc);

            }
        } catch (SQLException ex) {
            Logger.getLogger(PlandeClasesBD.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(stmt);
        }
        return lista_plan;
    }

    public static PlandeClasesMD consultarUltimoPlanClase(int id_curso, int id_unidad) {
        PlandeClasesMD planClase = null;
        String SELECT = "select id_plan_clases "
                + "from \"PlandeClases\" where id_curso=? AND id_unidad=?";
        PreparedStatement st = CON.prepareStatement(SELECT);

        try {
            st.setInt(1, id_curso);
            st.setInt(2, id_unidad);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                planClase = new PlandeClasesMD();
                planClase.setID(rs.getInt(1));

            }
        } catch (SQLException ex) {
            Logger.getLogger(PlandeClasesBD.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(st);
        }
        return planClase;
    }

    public static PlandeClasesMD consultarIDCURSO_ID_UNIDAD(int id_plan_de_clase) {
        PlandeClasesMD planClase = null;

        String SELECT = "select distinct id_plan_clases,"
                + " id_curso,id_unidad ,fecha_generacion from \"PlandeClases\" where id_plan_clases=?";

        PreparedStatement st = CON.prepareStatement(SELECT);

        System.out.println(st.toString());

        try {
            st.setInt(1, id_plan_de_clase);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                planClase = new PlandeClasesMD();
                planClase.setID(rs.getInt(1));
                planClase.getCurso().setId(rs.getInt(2));
                planClase.getUnidad().setId(rs.getInt(3));
                planClase.setFechaGeneracion(rs.getDate(4).toLocalDate());

            }
        } catch (SQLException ex) {
            Logger.getLogger(PlandeClasesBD.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(st);
        }
        return planClase;
    }

    public List<CursoMD> cursosSinPlanes(int idPlanClasesRef) {
        String SELECT = ""
                + "WITH mi_plan AS (\n"
                + "	SELECT\n"
                + "		\"Cursos\".id_prd_lectivo,\n"
                + "		\"Cursos\".id_docente,\n"
                + "		\"Cursos\".id_materia \n"
                + "	FROM\n"
                + "		\"PlandeClases\"\n"
                + "		INNER JOIN \"Cursos\" ON \"Cursos\".id_curso = \"PlandeClases\".id_curso \n"
                + "	WHERE\n"
                + "		\"PlandeClases\".id_plan_clases = " + idPlanClasesRef + " \n"
                + "	),\n"
                + "	mis_cursos AS (\n"
                + "	SELECT\n"
                + "		\"Cursos\".id_prd_lectivo,\n"
                + "		\"Cursos\".id_docente,\n"
                + "		\"Cursos\".id_materia,\n"
                + "		\"Cursos\".curso_nombre,\n"
                + "		\"Cursos\".id_curso \n"
                + "	FROM\n"
                + "		\"Cursos\"\n"
                + "		INNER JOIN mi_plan ON mi_plan.id_prd_lectivo = \"Cursos\".id_prd_lectivo \n"
                + "		AND mi_plan.id_materia = \"Cursos\".id_materia \n"
                + "		AND mi_plan.id_docente = \"Cursos\".id_docente \n"
                + "	),\n"
                + "	mis_planes AS ( \n"
                + "     SELECT \n"
                + "             mis_cursos.* \n"
                + "     FROM \n"
                + "             \"PlandeClases\" \n"
                + "             INNER JOIN mis_cursos ON mis_cursos.id_curso = \"PlandeClases\".id_curso \n"
                + "     ) \n"
                + "SELECT\n"
                + "    mis_cursos.id_curso,\n"
                + "    mis_cursos.curso_nombre\n"
                + "FROM\n"
                + "	mis_cursos \n"
                + "WHERE\n"
                + "	mis_cursos.id_curso NOT IN ( SELECT id_curso FROM mis_planes )"
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
            Logger.getLogger(PlandeClasesBD.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(rs);
        }

        return lista;

    }

    public boolean copiarPlabes(int idPlanRef, int idCursoCopiar) {

        String CALL = ""
                + "CALL \"copiar_plan_de_clases\"(" + idPlanRef + " ," + idCursoCopiar + ")"
                + "";
        return CON.ejecutar(CALL) != null;
    }

    public void aprobarPlanClase(int id_plan, int estado) {

        String UPDATE = "UPDATE public .\"PlandeClases\"\n"
                + "SET estado_plan=?"
                + "where id_plan_clases=?";

        PreparedStatement st = CON.prepareStatement(UPDATE);

        try {
            st.setInt(1, estado);
            st.setInt(2, id_plan);
            st.executeUpdate();
            st.close();

        } catch (SQLException ex) {
            Logger.getLogger(PlandeClasesBD.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(st);
        }
    }

    public List<PlandeClasesMD> getPlanesBy(String cedulaDocente, String periodo, String jornada) {
        String SELECT = ""
                + "SELECT\n"
                + "     \"Docentes\".id_docente,\n"
                + "     \"Personas\".id_persona,\n"
                + "     \"Personas\".persona_identificacion,\n"
                + "     \"Personas\".persona_primer_apellido,\n"
                + "     \"Personas\".persona_segundo_apellido,\n"
                + "     \"Personas\".persona_primer_nombre,\n"
                + "     \"Personas\".persona_segundo_nombre,\n"
                + "     \"PeriodoLectivo\".id_prd_lectivo,\n"
                + "     \"PeriodoLectivo\".prd_lectivo_nombre,\n"
                + "     \"UnidadSilabo\".id_unidad,\n"
                + "     \"UnidadSilabo\".numero_unidad,\n"
                + "     \"PlandeClases\".id_plan_clases,\n"
                + "     \"PlandeClases\".fecha_generacion,\n"
                + "     \"Cursos\".id_curso,\n"
                + "     \"Cursos\".curso_nombre,\n"
                + "     \"Materias\".id_materia,\n"
                + "     \"Materias\".materia_nombre\n"
                + "FROM\n"
                + "	\"PlandeClases\"\n"
                + "	INNER JOIN \"Cursos\" ON \"PlandeClases\".id_curso = \"Cursos\".id_curso\n"
                + "	INNER JOIN \"Docentes\" ON \"Cursos\".id_docente = \"Docentes\".id_docente\n"
                + "	INNER JOIN \"Personas\" ON \"Docentes\".id_persona = \"Personas\".id_persona\n"
                + "	INNER JOIN \"PeriodoLectivo\" ON \"Cursos\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo\n"
                + "	INNER JOIN \"Materias\" ON \"Cursos\".id_materia = \"Materias\".id_materia\n"
                + "	INNER JOIN \"UnidadSilabo\" ON \"PlandeClases\".id_unidad = \"UnidadSilabo\".id_unidad \n"
                + "     INNER JOIN \"Jornadas\" ON \"Cursos\".id_jornada = \"Jornadas\".id_jornada\n"
                + "WHERE\n"
                + "	\"PeriodoLectivo\".prd_lectivo_nombre = '" + periodo + "' \n"
                + "	AND \"Docentes\".docente_codigo = '" + cedulaDocente + "' \n"
                + "     AND \"Jornadas\".nombre_jornada = '" + jornada + "'\n"
                + "";
        List<PlandeClasesMD> lista = new ArrayList<>();

        ResultSet rs = CON.ejecutarQuery(SELECT);

        try {
            while (rs.next()) {
                DocenteMD docente = new DocenteMD();
                docente.setIdDocente(rs.getInt("id_docente"));
                docente.setIdentificacion(rs.getString("persona_identificacion"));
                docente.setPrimerNombre(rs.getString("persona_primer_nombre"));
                docente.setSegundoNombre(rs.getString("persona_segundo_nombre"));
                docente.setPrimerApellido(rs.getString("persona_primer_apellido"));
                docente.setSegundoApellido(rs.getString("persona_segundo_apellido"));

                PeriodoLectivoMD periodoLectivo = new PeriodoLectivoMD();
                periodoLectivo.setID(rs.getInt("id_prd_lectivo"));
                periodoLectivo.setNombre(rs.getString("prd_lectivo_nombre"));

                MateriaMD materiaMD = new MateriaMD();
                materiaMD.setId(rs.getInt("id_materia"));
                materiaMD.setNombre(rs.getString("materia_nombre"));

                CursoMD curso = new CursoMD();
                curso.setId(rs.getInt("id_curso"));
                curso.setPeriodo(periodoLectivo);
                curso.setDocente(docente);
                curso.setMateria(materiaMD);
                curso.setNombre(rs.getString("curso_nombre"));

                UnidadSilaboMD unidadSilaboMD = new UnidadSilaboMD();
                unidadSilaboMD.setId(rs.getInt("id_unidad"));
                unidadSilaboMD.setNumeroUnidad(rs.getInt("numero_unidad"));

                PlandeClasesMD plandeClasesMD = new PlandeClasesMD();
                plandeClasesMD.setID(rs.getInt("id_plan_clases"));
                plandeClasesMD.setCurso(curso);
                plandeClasesMD.setUnidad(unidadSilaboMD);
                plandeClasesMD.setFechaCierre(rs.getDate("fecha_generacion").toLocalDate());

                lista.add(plandeClasesMD);
                System.out.println(plandeClasesMD);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlandeClasesBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(rs);
        }

        return lista;

    }

}
