package modelo.PlanClases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.EstrategiasMetodologicas.EstrategiasMetodologicasMD;
import modelo.carrera.CarreraMD;
import modelo.curso.CursoMD;
import modelo.materia.MateriaMD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.DocenteMD;
import static modelo.silabo.NEWUnidadSilaboBD.getEstrategiasUnidad;
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
            stmt.setInt(2, pl.getUnidad().getID());
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
        } finally {
            CON.close(stmt);
        }
        return true;
    }


    public static void eliminarPlanClase(int idPlan) {

        String DELETE = "delete from \"PlandeClases\" where id_plan_clases=" + idPlan;
        CON.ejecutar(DELETE);
    }


    public List<CursoMD> cursosSinPlanes(int idPlanClasesRef) {
        String SELECT = ""
                + "WITH mi_info AS (\n"
                + "	SELECT\n"
                + "		\"Cursos\".id_prd_lectivo,\n"
                + "		\"Cursos\".id_docente,\n"
                + "		\"Cursos\".id_materia,\n"
                + "		\"PlandeClases\".id_unidad\n"
                + "	FROM\n"
                + "		\"PlandeClases\"\n"
                + "		INNER JOIN \"Cursos\" ON \"Cursos\".id_curso = \"PlandeClases\".id_curso \n"
                + "	WHERE\n"
                + "		\"PlandeClases\".id_plan_clases = " + idPlanClasesRef + " \n"
                + "	),\n"
                + "	mis_planes AS (\n"
                + "	SELECT\n"
                + "		\"Cursos\".id_prd_lectivo,\n"
                + "		\"Cursos\".id_docente,\n"
                + "		\"Cursos\".id_materia,\n"
                + "		\"Cursos\".curso_nombre,\n"
                + "		\"Cursos\".id_curso,\n"
                + "		\"PlandeClases\".id_unidad\n"
                + "	FROM\n"
                + "		\"PlandeClases\"\n"
                + "		INNER JOIN mi_info ON mi_info.id_unidad = \"PlandeClases\".id_unidad\n"
                + "		INNER JOIN \"Cursos\" ON \"Cursos\".id_materia = mi_info.id_materia \n"
                + "		AND \"Cursos\".id_prd_lectivo = mi_info.id_prd_lectivo\n"
                + "		AND \"Cursos\".id_docente = mi_info.id_docente\n"
                + "		AND \"Cursos\".id_curso = \"PlandeClases\".id_curso\n"
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
                + "		INNER JOIN mi_info ON mi_info.id_prd_lectivo = \"Cursos\".id_prd_lectivo \n"
                + "		AND mi_info.id_materia = \"Cursos\".id_materia \n"
                + "		AND mi_info.id_docente = \"Cursos\".id_docente\n"
                + "	)\n"
                + "	SELECT\n"
                + "    mis_cursos.id_curso,\n"
                + "    mis_cursos.curso_nombre\n"
                + "	FROM\n"
                + "		mis_cursos \n"
                + "	WHERE\n"
                + "		mis_cursos.id_curso NOT IN ( SELECT id_curso FROM mis_planes )"
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

    public static void editarEstado(int id_plan, int estado) {
        System.out.println(id_plan);
        String UPDATE = "UPDATE \"PlandeClases\" \n"
                + "SET estado_plan = " + estado + " \n"
                + "WHERE\n"
                + "	id_plan_clases = " + id_plan;

        CON.ejecutar(UPDATE);

    }

    public static PlandeClasesMD getPlanBy(int id) {
        String SELECT = ""
                + "SELECT\n"
                + "	\"PlandeClases\".id_plan_clases,\n"
                + "	\"PlandeClases\".id_curso,\n"
                + "	\"PlandeClases\".id_unidad,\n"
                + "	\"PlandeClases\".observaciones,\n"
                + "	\"PlandeClases\".fecha_generacion,\n"
                + "	\"PlandeClases\".trabajo_autonomo,\n"
                + "	\"PlandeClases\".estado_plan,\n"
                + "	\"PlandeClases\".fecha_revision,\n"
                + "	\"Cursos\".curso_nombre,\n"
                + "	\"Personas\".persona_identificacion,\n"
                + "	\"Personas\".persona_primer_apellido,\n"
                + "	\"Personas\".persona_segundo_apellido,\n"
                + "	\"Personas\".persona_primer_nombre,\n"
                + "	\"Personas\".persona_segundo_nombre,\n"
                + "	\"PeriodoLectivo\".prd_lectivo_nombre,\n"
                + "	\"PeriodoLectivo\".id_prd_lectivo,\n"
                + "	\"Carreras\".carrera_nombre,\n"
                + "	\"Carreras\".id_carrera,\n"
                + "	\"Materias\".materia_codigo,\n"
                + "	\"Materias\".materia_nombre,\n"
                + "	\"Materias\".id_materia,\n"
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
                + "	\"PlandeClases\"\n"
                + "	INNER JOIN \"Cursos\" ON \"PlandeClases\".id_curso = \"Cursos\".id_curso\n"
                + "	INNER JOIN \"Docentes\" ON \"Cursos\".id_docente = \"Docentes\".id_docente\n"
                + "	INNER JOIN \"Personas\" ON \"Docentes\".id_persona = \"Personas\".id_persona\n"
                + "	INNER JOIN \"PeriodoLectivo\" ON \"Cursos\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo\n"
                + "	INNER JOIN \"Carreras\" ON \"PeriodoLectivo\".id_carrera = \"Carreras\".id_carrera\n"
                + "	INNER JOIN \"Materias\" ON \"Cursos\".id_materia = \"Materias\".id_materia\n"
                + "	INNER JOIN \"UnidadSilabo\" ON \"PlandeClases\".id_unidad = \"UnidadSilabo\".id_unidad\n"
                + "WHERE\n"
                + "\"PlandeClases\".id_plan_clases = " + id
                + "";

        System.out.println("---DOC");
        ResultSet rs = CON.ejecutarQuery(SELECT);
        PlandeClasesMD plandeClasesMD = new PlandeClasesMD();

        try {
            while (rs.next()) {
                DocenteMD docenteMD = new DocenteMD();
                //docenteMD.setIdDocente(rs.getInt("id_docente"));
                docenteMD.setIdentificacion(rs.getString("persona_identificacion"));
                docenteMD.setPrimerNombre(rs.getString("persona_primer_nombre"));
                docenteMD.setSegundoNombre(rs.getString("persona_segundo_nombre"));
                docenteMD.setPrimerApellido(rs.getString("persona_primer_apellido"));
                docenteMD.setSegundoApellido(rs.getString("persona_segundo_apellido"));

                CarreraMD carreraMD = new CarreraMD();
                carreraMD.setId(rs.getInt("id_carrera"));
                carreraMD.setNombre(rs.getString("carrera_nombre"));

                PeriodoLectivoMD periodoLectivoMD = new PeriodoLectivoMD();
                periodoLectivoMD.setID(rs.getInt("id_prd_lectivo"));
                periodoLectivoMD.setNombre(rs.getString("prd_lectivo_nombre"));
                periodoLectivoMD.setCarrera(carreraMD);

                MateriaMD materiaMD = new MateriaMD();
                materiaMD.setId(rs.getInt("id_materia"));
                materiaMD.setNombre(rs.getString("materia_nombre"));
                materiaMD.setCodigo(rs.getString("materia_codigo"));

                CursoMD cursoMD = new CursoMD();
                cursoMD.setId(rs.getInt("id_curso"));
                cursoMD.setNombre(rs.getString("curso_nombre"));
                cursoMD.setMateria(materiaMD);
                cursoMD.setDocente(docenteMD);
                cursoMD.setPeriodo(periodoLectivoMD);

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

                plandeClasesMD = new PlandeClasesMD();
                plandeClasesMD.setID(rs.getInt("id_plan_clases"));
                plandeClasesMD.setObservaciones(rs.getString("observaciones"));
                plandeClasesMD.setTrabajoAutonomo(rs.getString("trabajo_autonomo"));
                plandeClasesMD.setEstado(rs.getInt("estado_plan"));
                plandeClasesMD.setFechaGeneracion(rs.getDate("fecha_generacion").toLocalDate());

                plandeClasesMD.setRecursos(getRecursosPlan(id));
                plandeClasesMD.setEstrategias(getEstrategiasPlan(id));
                plandeClasesMD.setCurso(cursoMD);
                plandeClasesMD.setUnidad(unidadSilaboMD);

            }
        } catch (SQLException ex) {
            Logger.getLogger(PlandeClasesBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(rs);
        }

        return plandeClasesMD;

    }

    public List<PlandeClasesMD> getPlanesCoordinadorBy(String cedulaDocente, String periodo, String jornada) {
        String SELECT = ""
                + "SELECT\n"
                + "	\"Docentes\".id_docente,\n"
                + "	\"Personas\".id_persona,\n"
                + "	\"Personas\".persona_identificacion,\n"
                + "	\"Personas\".persona_primer_apellido,\n"
                + "	\"Personas\".persona_segundo_apellido,\n"
                + "	\"Personas\".persona_primer_nombre,\n"
                + "	\"Personas\".persona_segundo_nombre,\n"
                + "	\"PeriodoLectivo\".id_prd_lectivo,\n"
                + "	\"PeriodoLectivo\".prd_lectivo_nombre,\n"
                + "	\"UnidadSilabo\".id_unidad,\n"
                + "	\"UnidadSilabo\".numero_unidad,\n"
                + "	\"PlandeClases\".id_plan_clases,\n"
                + "	\"PlandeClases\".fecha_generacion,\n"
                + "	\"PlandeClases\".estado_plan,\n"
                + "	\"Cursos\".id_curso,\n"
                + "	\"Cursos\".curso_nombre,\n"
                + "	\"Materias\".id_materia,\n"
                + "	\"Materias\".materia_nombre \n"
                + "FROM\n"
                + "	\"PlandeClases\"\n"
                + "	INNER JOIN \"Cursos\" ON \"PlandeClases\".id_curso = \"Cursos\".id_curso\n"
                + "	INNER JOIN \"Docentes\" ON \"Cursos\".id_docente = \"Docentes\".id_docente\n"
                + "	INNER JOIN \"Personas\" ON \"Docentes\".id_persona = \"Personas\".id_persona\n"
                + "	INNER JOIN \"PeriodoLectivo\" ON \"Cursos\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo\n"
                + "	INNER JOIN \"Materias\" ON \"Cursos\".id_materia = \"Materias\".id_materia\n"
                + "	INNER JOIN \"UnidadSilabo\" ON \"PlandeClases\".id_unidad = \"UnidadSilabo\".id_unidad\n"
                + "	INNER JOIN \"Jornadas\" ON \"Cursos\".id_jornada = \"Jornadas\".id_jornada \n"
                + "WHERE\n"
                + "	\"PeriodoLectivo\".prd_lectivo_nombre = '" + periodo + "' \n"
                + "     AND \"Jornadas\".nombre_jornada = '" + jornada + "'\n"
                + "ORDER BY persona_primer_apellido, materia_nombre, numero_unidad"
                + "";
        List<PlandeClasesMD> lista = new ArrayList<>();

        System.out.println("---COR");
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
                plandeClasesMD.setEstado(rs.getInt("estado_plan"));
                plandeClasesMD.setCurso(curso);
                plandeClasesMD.setUnidad(unidadSilaboMD);
                plandeClasesMD.setFechaGeneracion(rs.getDate("fecha_generacion").toLocalDate());

                lista.add(plandeClasesMD);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlandeClasesBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(rs);
        }

        return lista;

    }

    public List<PlandeClasesMD> getPlanesSuperSu(String periodo, String jornada) {
        String SELECT = ""
                + "SELECT\n"
                + "	\"Docentes\".id_docente,\n"
                + "	\"Personas\".id_persona,\n"
                + "	\"Personas\".persona_identificacion,\n"
                + "	\"Personas\".persona_primer_apellido,\n"
                + "	\"Personas\".persona_segundo_apellido,\n"
                + "	\"Personas\".persona_primer_nombre,\n"
                + "	\"Personas\".persona_segundo_nombre,\n"
                + "	\"PeriodoLectivo\".id_prd_lectivo,\n"
                + "	\"PeriodoLectivo\".prd_lectivo_nombre,\n"
                + "	\"UnidadSilabo\".id_unidad,\n"
                + "	\"UnidadSilabo\".numero_unidad,\n"
                + "	\"PlandeClases\".id_plan_clases,\n"
                + "	\"PlandeClases\".fecha_generacion,\n"
                + "	\"PlandeClases\".estado_plan,\n"
                + "	\"Cursos\".id_curso,\n"
                + "	\"Cursos\".curso_nombre,\n"
                + "	\"Materias\".id_materia,\n"
                + "	\"Materias\".materia_nombre \n"
                + "FROM\n"
                + "	\"PlandeClases\"\n"
                + "	INNER JOIN \"Cursos\" ON \"PlandeClases\".id_curso = \"Cursos\".id_curso\n"
                + "	INNER JOIN \"Docentes\" ON \"Cursos\".id_docente = \"Docentes\".id_docente\n"
                + "	INNER JOIN \"Personas\" ON \"Docentes\".id_persona = \"Personas\".id_persona\n"
                + "	INNER JOIN \"PeriodoLectivo\" ON \"Cursos\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo\n"
                + "	INNER JOIN \"Materias\" ON \"Cursos\".id_materia = \"Materias\".id_materia\n"
                + "	INNER JOIN \"UnidadSilabo\" ON \"PlandeClases\".id_unidad = \"UnidadSilabo\".id_unidad\n"
                + "	INNER JOIN \"Jornadas\" ON \"Cursos\".id_jornada = \"Jornadas\".id_jornada\n"
                + "WHERE\n"
                + "	\"PeriodoLectivo\".prd_lectivo_nombre = '" + periodo + "' \n"
                + "     AND \"Jornadas\".nombre_jornada = '" + jornada + "'\n"
                + "ORDER BY persona_primer_apellido, materia_nombre, numero_unidad"
                + "";
        List<PlandeClasesMD> lista = new ArrayList<>();
        System.out.println("---SU");
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
                plandeClasesMD.setEstado(rs.getInt("estado_plan"));
                plandeClasesMD.setCurso(curso);
                plandeClasesMD.setUnidad(unidadSilaboMD);
                plandeClasesMD.setFechaGeneracion(rs.getDate("fecha_generacion").toLocalDate());

                lista.add(plandeClasesMD);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlandeClasesBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(rs);
        }

        return lista;

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
                + "	\"PlandeClases\".estado_plan,\n"
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
                + "ORDER BY persona_primer_apellido, materia_nombre, numero_unidad"
                + "";
        List<PlandeClasesMD> lista = new ArrayList<>();

        System.out.println(SELECT);

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
                plandeClasesMD.setEstado(rs.getInt("estado_plan"));
                plandeClasesMD.setCurso(curso);
                plandeClasesMD.setUnidad(unidadSilaboMD);
                plandeClasesMD.setFechaGeneracion(rs.getDate("fecha_generacion").toLocalDate());

                lista.add(plandeClasesMD);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlandeClasesBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(rs);
        }

        return lista;

    }

    public static boolean crear(PlandeClasesMD plan) {
        String INSERT = ""
                + "INSERT INTO \"PlandeClases\"\n"
                + "    (id_curso, id_unidad, observaciones, trabajo_autonomo, estado_plan) \n"
                + "VALUES(\n"
                + "    " + plan.getCurso().getId() + ",\n"
                + "    " + plan.getUnidad().getID() + ",\n"
                + "    '" + plan.getObservaciones() + "',\n"
                + "    '" + plan.getTrabajoAutonomo() + "',\n"
                + "    0\n"
                + ")RETURNING id_plan_clases"
                + "";
        ResultSet rs = CON.ejecutarQuery(INSERT);
        try {
            while (rs.next()) {
                plan.setID(rs.getInt("id_plan_clases"));
                insertarRecursos(plan.getRecursos(), plan.getID());
                insertartEstrategias(plan.getEstrategias(), plan.getID());
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlandeClasesBD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public static boolean editar(PlandeClasesMD plan) {
        String UPDATE = ""
                + "UPDATE \"PlandeClases\" \n"
                + "SET trabajo_autonomo = '',\n"
                + "observaciones = '' \n"
                + "WHERE\n"
                + "	\"PlandeClases\".id_plan_clases = " + plan.getID()
                + "";
        CON.ejecutar(UPDATE);
        eliminarRecursos(plan.getID());
        eliminarEstrategias(plan.getID());
        insertarRecursos(plan.getRecursos(), plan.getID());
        insertartEstrategias(plan.getEstrategias(), plan.getID());
        return true;
    }

    private static boolean eliminarRecursos(int idPlan) {
        String DELETE = ""
                + "DELETE FROM \"RecursosPlanClases\" WHERE \"RecursosPlanClases\".id_plan_clases = " + idPlan
                + "";

        return CON.ejecutar(DELETE) == null;
    }

    private static boolean eliminarEstrategias(int idPlan) {
        String DELETE = ""
                + "DELETE FROM \"EstrategiasMetodologias\" WHERE \"EstrategiasMetodologias\".id_plan_de_clases = " + idPlan
                + "";

        return CON.ejecutar(DELETE) == null;
    }

    private static void insertarRecursos(List<RecursosMD> recursos, int idPlan) {
        recursos.forEach(obj -> {
            CON.ejecutar(String.format(
                    "INSERT INTO \"RecursosPlanClases\"\n"
                    + "     (id_plan_clases, id_recurso) \n"
                    + "VALUES(%s, %s )",
                    idPlan,
                    obj.getId_recurso()
            ));
        });
    }

    private static void insertartEstrategias(List<EstrategiasMetodologicasMD> estrategias, int idPlan) {

        estrategias.forEach(obj -> {
            CON.ejecutar(String.format(""
                    + "INSERT INTO \"EstrategiasMetodologias\"\n"
                    + "     (nombre_estrategia,tipo_estrategias_metodologias, id_plan_de_clases) \n"
                    + "VALUES('%s', '%s', %s)",
                    obj.getNombre_estrategia(),
                    obj.getTipo_estrategias_metodologicas(),
                    idPlan
            ));
        });
    }

    private static List<RecursosMD> getRecursosPlan(int idPlan) {
        String SELECT = ""
                + "SELECT\n"
                + "	\"Recursos\".id_recurso,\n"
                + "	\"Recursos\".nombre_recursos,\n"
                + "	\"Recursos\".tipo_recurso\n"
                + "FROM\n"
                + "	\"Recursos\"\n"
                + "	INNER JOIN \"RecursosPlanClases\" ON \"RecursosPlanClases\".id_recurso = \"Recursos\".id_recurso \n"
                + "WHERE\n"
                + "	id_plan_clases = " + idPlan
                + "";
        List<RecursosMD> recursos = new ArrayList<>();

        ResultSet rs = CON.ejecutarQuery(SELECT);

        try {
            while (rs.next()) {
                RecursosMD recursosMD = new RecursosMD();
                recursosMD.setId_recurso(rs.getInt("id_recurso"));
                recursosMD.setNombre_recursos(rs.getString("nombre_recursos"));
                recursosMD.setTipo_recurso(rs.getString("tipo_recurso"));
                recursos.add(recursosMD);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlandeClasesBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(rs);
        }
        return recursos;
    }

    private static List<EstrategiasMetodologicasMD> getEstrategiasPlan(int idPlan) {
        String SELECT = ""
                + "SELECT\n"
                + "	\"EstrategiasMetodologias\".id_estrategias_metodologias,\n"
                + "	\"EstrategiasMetodologias\".tipo_estrategias_metodologias,\n"
                + "	\"EstrategiasMetodologias\".nombre_estrategia \n"
                + "FROM\n"
                + "	\"EstrategiasMetodologias\" \n"
                + "WHERE\n"
                + "	\"EstrategiasMetodologias\".id_plan_de_clases =" + idPlan
                + "";
        List<EstrategiasMetodologicasMD> estrategias = new ArrayList<>();

        ResultSet rs = CON.ejecutarQuery(SELECT);

        try {
            while (rs.next()) {
                EstrategiasMetodologicasMD metodologicasMD = new EstrategiasMetodologicasMD();
                metodologicasMD.setId_estrategias_metodologicas(rs.getInt("id_estrategias_metodologias"));
                metodologicasMD.setTipo_estrategias_metodologicas(rs.getString("tipo_estrategias_metodologias"));
                metodologicasMD.setNombre_estrategia(rs.getString("nombre_estrategia"));
                estrategias.add(metodologicasMD);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlandeClasesBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(rs);
        }
        return estrategias;
    }

}
