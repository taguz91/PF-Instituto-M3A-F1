package modelo.PlanClases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConexionBD;
import modelo.ConnDBPool;
import modelo.curso.CursoMD;
import modelo.materia.MateriaMD;
import modelo.persona.PersonaMD;
import modelo.unidadSilabo.UnidadSilaboMD;

public class PlandeClasesBD extends PlandeClasesMD {

    private final ConnDBPool CON = ConnDBPool.single();

    private static PlandeClasesBD INSTANCE = null;

    public static PlandeClasesBD single() {
        if (INSTANCE == null) {
            INSTANCE = new PlandeClasesBD();
        }

        return INSTANCE;
    }

    private ConexionBD conexion;

    public PlandeClasesBD() {
    }

    public PlandeClasesBD(ConexionBD conexion) {
        this.conexion = conexion;
    }

    public PlandeClasesBD(ConexionBD conexion, CursoMD id_curso, UnidadSilaboMD id_unidad) {
        super(id_curso, id_unidad);
        this.conexion = conexion;
    }

    public PlandeClasesBD(ConexionBD conexion, CursoMD id_curso, UnidadSilaboMD id_unidad, MateriaMD id_materia, PersonaMD id_persona) {
        super(id_curso, id_unidad, id_materia, id_persona);
        this.conexion = conexion;
    }

    public boolean insertarPlanClases(PlandeClasesMD pl) {

        try {
            PreparedStatement st = conexion.getCon().prepareStatement(""
                    + "INSERT INTO public.\"PlandeClases\"(\n"
                    + "	 id_curso, id_unidad, observaciones,\n"
                    + "	 fecha_revision, fecha_generacion, fecha_cierre,trabajo_autonomo)\n"
                    + "	VALUES (?, ?, ?, ?, ?, ?,?)");
            st.setInt(1, pl.getId_curso().getId());
            st.setInt(2, pl.getId_unidad().getIdUnidad());
            st.setString(3, pl.getObservaciones());
            System.out.println(pl.getObservaciones() + "----------------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            st.setDate(4, null);
            st.setDate(5, java.sql.Date.valueOf(LocalDate.now()));
            st.setDate(6, null);
            st.setString(7, pl.getTrabajo_autonomo());
//            st.setDate(4, java.sql.Date.valueOf(getFecha_revision()));
//            st.setDate(5, java.sql.Date.valueOf(getFecha_generacion()));
//            st.setDate(6, java.sql.Date.valueOf(getFecha_cierre()));
            st.executeUpdate();
            System.out.println(st);
            st.close();
        } catch (SQLException ex) {
            System.out.println("Falló al guardar");
        }
        return true;
    }

    public boolean editarFechageneracoion(int idpla, LocalDate fecha) {

        try {
            PreparedStatement st = conexion.getCon().prepareStatement("UPDATE public.\"PlandeClases\"\n"
                    + "	SET fecha_generacion=?\n"
                    + "	WHERE id_plan_clases=?");
            st.setDate(1, java.sql.Date.valueOf(fecha));
            st.setInt(2, idpla);
            st.executeUpdate();
            System.out.println(st);
            st.close();
        } catch (SQLException ex) {
            System.out.println("Falló al guardar");
        }
        return true;
    }

    public static List<PlandeClasesMD> consultarPlanClase(ConexionBD conexion, String[] parametros) {
        List<PlandeClasesMD> lista_plan = new ArrayList<>();

        try {
            PreparedStatement st = conexion.getCon().prepareStatement("SELECT DISTINCT pla.id_plan_clases,us.numero_unidad,p.persona_primer_apellido,p.persona_primer_nombre,m.materia_nombre, cr.curso_nombre,pla.estado_plan,pla.fecha_generacion\n"
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
                    + "                    WHERE crr.carrera_nombre=?\n"
                    + "                    AND p.id_persona=? AND jo.nombre_jornada=? AND cr.id_prd_lectivo=? AND m.materia_nombre ILIKE '%" + parametros[2] + "%'");
            st.setString(1, parametros[0]);
            st.setInt(2, Integer.parseInt(parametros[3]));
            st.setString(3, parametros[1]);
            st.setInt(4, Integer.parseInt(parametros[4]));
            ResultSet rs = st.executeQuery();
            System.out.println(st + "---------------------------------------------------------------------------->>>>>>>>>>>>>><");
            while (rs.next()) {
                PlandeClasesMD pl = new PlandeClasesMD();
                pl.setId_plan_clases(rs.getInt(1));
                pl.getId_unidad().setIdUnidad(rs.getInt(2));
                pl.getId_persona().setPrimerApellido(rs.getString(3));
                pl.getId_persona().setPrimerNombre(rs.getString(4));
                pl.getId_materia().setNombre(rs.getString(5));
                pl.getId_curso().setNombre(rs.getString(6));
                pl.setEstado_plan(rs.getInt(7));
                pl.setFecha_generacion(rs.getDate(8).toLocalDate());
                lista_plan.add(pl);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlandeClasesBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista_plan;
    }

    public static List<PlandeClasesMD> consultarPlanClaseCoordinador(ConexionBD conexion, String[] parametros) {
        List<PlandeClasesMD> lista_plan = new ArrayList<>();

        try {
            PreparedStatement st = conexion.getCon().prepareStatement("SELECT DISTINCT pla.id_plan_clases,us.numero_unidad,p.persona_primer_apellido,p.persona_primer_nombre,m.materia_nombre, cr.curso_nombre,pla.estado_plan,pla.fecha_generacion\n"
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
                    + "                    WHERE crr.carrera_nombre=?\n"
                    + "                    AND jo.nombre_jornada=? AND cr.id_prd_lectivo=? AND m.materia_nombre ILIKE '%" + parametros[2] + "%'");
            st.setString(1, parametros[0]);
            st.setString(2, parametros[1]);
            st.setInt(3, Integer.parseInt(parametros[3]));
            ResultSet rs = st.executeQuery();
            System.out.println(st + "---------------------------------------------------------------------------->>>>>>>>>>>>>><");
            while (rs.next()) {
                PlandeClasesMD pl = new PlandeClasesMD();
                pl.setId_plan_clases(rs.getInt(1));
                pl.getId_unidad().setIdUnidad(rs.getInt(2));
                pl.getId_persona().setPrimerApellido(rs.getString(3));
                pl.getId_persona().setPrimerNombre(rs.getString(4));
                pl.getId_materia().setNombre(rs.getString(5));
                pl.getId_curso().setNombre(rs.getString(6));
                pl.setEstado_plan(rs.getInt(7));
                pl.setFecha_generacion(rs.getDate(8).toLocalDate());
                lista_plan.add(pl);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlandeClasesBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista_plan;
    }

    public static List<PlandeClasesMD> consultarPlanClaseExistente(ConexionBD conexion, String[] parametros) {
        List<PlandeClasesMD> lista_plan = new ArrayList<>();

        try {
            PreparedStatement st = conexion.getCon().prepareStatement("SELECT DISTINCT pla.id_plan_clases,us.id_unidad, cr.id_curso\n"
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
                    + "                    WHERE crr.carrera_nombre=?\n"
                    + "                    AND p.id_persona=? AND cr.id_prd_lectivo=? ");
            st.setString(1, parametros[0]);
            st.setInt(2, Integer.parseInt(parametros[1]));
            st.setInt(3, Integer.parseInt(parametros[2]));
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                PlandeClasesMD pl = new PlandeClasesMD();
                pl.setId_plan_clases(rs.getInt(1));
                pl.getId_unidad().setIdUnidad(rs.getInt(2));
                pl.getId_curso().setId(rs.getInt(3));
                lista_plan.add(pl);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlandeClasesBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista_plan;
    }

    public void eliminarPlanClase(PlandeClasesMD pl) {
        try {
            PreparedStatement st = conexion.getCon().prepareStatement("delete from \"PlandeClases\" where id_plan_clases=?");
            st.setInt(1, pl.getId_plan_clases());
            st.executeUpdate();
            System.out.println(st);
            st.close();
        } catch (SQLException ex) {
            System.out.println("Falló al eliminar antes de guardar");
        }

    }

    public static List<PlandeClasesMD> consultarPlanClaseObservacion(ConexionBD conexion, int plan_clase) {
        List<PlandeClasesMD> lista_plan = new ArrayList<>();
        try {
            PreparedStatement st = conexion.getCon().prepareStatement("select observaciones,trabajo_autonomo from \"PlandeClases\" where id_plan_clases=?");
            st.setInt(1, plan_clase);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                PlandeClasesMD pc = new PlandeClasesMD();
                pc.setObservaciones(rs.getString(1));
                pc.setTrabajo_autonomo(rs.getString(2));
                lista_plan.add(pc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlandeClasesBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista_plan;
    }

    public static PlandeClasesMD consultarUltimoPlanClase(ConexionBD conexion, int id_curso, int id_unidad) {
        PlandeClasesMD planClase = null;
        try {
            PreparedStatement st = conexion.getCon().prepareStatement("select id_plan_clases "
                    + "from \"PlandeClases\" where id_curso=? AND id_unidad=?");
            st.setInt(1, id_curso);
            st.setInt(2, id_unidad);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                planClase = new PlandeClasesMD();
                planClase.setId_plan_clases(rs.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlandeClasesBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return planClase;
    }

    public static PlandeClasesMD consultarIDCURSO_ID_UNIDAD(ConexionBD conexion, int id_plan_de_clase) {
        PlandeClasesMD planClase = null;
        try {
            PreparedStatement st = conexion.getCon().prepareStatement("select distinct id_plan_clases,"
                    + " id_curso,id_unidad ,fecha_generacion from \"PlandeClases\" where id_plan_clases=?");
            st.setInt(1, id_plan_de_clase);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                planClase = new PlandeClasesMD();
                planClase.setId_plan_clases(rs.getInt(1));
                planClase.getId_curso().setId(rs.getInt(2));
                planClase.getId_unidad().setIdUnidad(rs.getInt(3));
                planClase.setFecha_generacion(rs.getDate(4).toLocalDate());
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlandeClasesBD.class.getName()).log(Level.SEVERE, null, ex);
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
                curso.setNombre(rs.getString("cursos_nombre"));

                lista.add(curso);

            }
        } catch (SQLException ex) {
            Logger.getLogger(PlandeClasesBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(rs);
        }

        return lista;

    }

    public void aprobarPlanClase(int id_plan, int estado) {
        try {
            PreparedStatement st = conexion.getCon().prepareStatement("UPDATE public .\"PlandeClases\"\n"
                    + "SET estado_plan=?"
                    + "where id_plan_clases=?");
            st.setInt(1, estado);
            st.setInt(2, id_plan);
            st.executeUpdate();
            System.out.println(st);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PlandeClasesBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
