
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
import modelo.curso.CursoMD;
import modelo.materia.MateriaMD;
import modelo.persona.PersonaMD;
import modelo.unidadSilabo.UnidadSilaboMD;


public class PlandeClasesBD extends PlandeClasesMD {
    
    private ConexionBD conexion;

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
    
    
 
    
    public boolean insertarPlanClases(PlandeClasesMD pl){
        
        try {
            PreparedStatement st=conexion.getCon().prepareStatement(""
                    + "INSERT INTO public.\"PlandeClases\"(\n" +
"	 id_curso, id_unidad, observaciones,\n" +
"	 fecha_revision, fecha_generacion, fecha_cierre,trabajo_autonomo)\n" +
"	VALUES (?, ?, ?, ?, ?, ?,?)");
            st.setInt(1, pl.getId_curso().getId());
            st.setInt(2, pl.getId_unidad().getIdUnidad());
            st.setString(3, pl.getObservaciones());
            System.out.println(pl.getObservaciones()+"----------------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
            Logger.getLogger(PlandeClasesBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
   
   
   public static  List<PlandeClasesMD> consultarPlanClase(ConexionBD conexion,String [] parametros){
       List<PlandeClasesMD> lista_plan=new ArrayList<>();
        
        try {
            PreparedStatement st=conexion.getCon().prepareStatement("SELECT DISTINCT pla.id_plan_clases,us.numero_unidad,p.persona_primer_apellido,p.persona_primer_nombre,m.materia_nombre, cr.curso_nombre\n" +
"       FROM \"Silabo\" AS s\n" +
"       JOIN \"Materias\" AS m ON s.id_materia=m.id_materia\n" +
"       JOIN \"PeriodoLectivo\" AS pr ON pr.id_prd_lectivo=s.id_prd_lectivo\n" +
"                    JOIN \"Carreras\" AS crr ON crr.id_carrera = m.id_carrera\n" +
"                    JOIN \"Cursos\" AS cr ON cr.id_materia=m.id_materia\n" +
"                    JOIN \"Docentes\" AS d ON d.id_docente= cr.id_docente\n" +
"                    JOIN \"Personas\" AS p ON d.id_persona=p.id_persona \n" +
"					JOIN \"PlandeClases\" AS pla on  cr.id_curso=pla.id_Curso \n" +
"					JOIN \"UnidadSilabo\" AS us on pla.id_unidad=us.id_unidad\n" +
"					JOIN \"Jornadas\" AS jo on cr.id_jornada=jo.id_jornada\n" +
"                    WHERE crr.carrera_nombre=?\n" +
"                    AND p.id_persona=? AND jo.nombre_jornada=? AND cr.id_prd_lectivo=? AND m.materia_nombre ILIKE '%"+parametros[2]+"%'");
            st.setString(1, parametros[0]);
            st.setInt(2, Integer.parseInt(parametros[3]));
            st.setString(3, parametros[1]);
            st.setInt(4, Integer.parseInt(parametros[4]));
            ResultSet rs=st.executeQuery();
            System.out.println(st+"---------------------------------------------------------------------------->>>>>>>>>>>>>><");
            while(rs.next()){
                PlandeClasesMD pl=new PlandeClasesMD();
                pl.setId_plan_clases(rs.getInt(1));
                pl.getId_unidad().setIdUnidad(rs.getInt(2));
                pl.getId_persona().setPrimerApellido(rs.getString(3));
                pl.getId_persona().setPrimerNombre(rs.getString(4));
                pl.getId_materia().setNombre(rs.getString(5));
                pl.getId_curso().setNombre(rs.getString(6));
                lista_plan.add(pl);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlandeClasesBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista_plan;
   }
   public static  List<PlandeClasesMD> consultarPlanClaseExistente(ConexionBD conexion,String [] parametros){
       List<PlandeClasesMD> lista_plan=new ArrayList<>();
        
        try {
            PreparedStatement st=conexion.getCon().prepareStatement("SELECT DISTINCT pla.id_plan_clases,us.id_unidad, cr.id_curso\n" +
"       FROM \"Silabo\" AS s\n" +
"       JOIN \"Materias\" AS m ON s.id_materia=m.id_materia\n" +
"       JOIN \"PeriodoLectivo\" AS pr ON pr.id_prd_lectivo=s.id_prd_lectivo\n" +
"                    JOIN \"Carreras\" AS crr ON crr.id_carrera = m.id_carrera\n" +
"                    JOIN \"Cursos\" AS cr ON cr.id_materia=m.id_materia\n" +
"                    JOIN \"Docentes\" AS d ON d.id_docente= cr.id_docente\n" +
"                    JOIN \"Personas\" AS p ON d.id_persona=p.id_persona \n" +
"					JOIN \"PlandeClases\" AS pla on  cr.id_curso=pla.id_Curso \n" +
"					JOIN \"UnidadSilabo\" AS us on pla.id_unidad=us.id_unidad\n" +
"					JOIN \"Jornadas\" AS jo on cr.id_jornada=jo.id_jornada\n" +
"                    WHERE crr.carrera_nombre=?\n" +
"                    AND p.id_persona=? AND cr.id_prd_lectivo=? ");
            st.setString(1, parametros[0]);
            st.setInt(2, Integer.parseInt(parametros[1]));
            st.setInt(3, Integer.parseInt(parametros[2]));
            ResultSet rs=st.executeQuery();
            while(rs.next()){
                PlandeClasesMD pl=new PlandeClasesMD();
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
   
   public void eliminarPlanClase(PlandeClasesMD  pl){
        try {
            PreparedStatement st=conexion.getCon().prepareStatement("delete from \"PlandeClases\" where id_plan_clases=?");
            st.setInt(1, pl.getId_plan_clases());
            st.executeUpdate();
            System.out.println(st);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PlandeClasesBD.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   }
   public static List<PlandeClasesMD> consultarPlanClaseObservacion(ConexionBD conexion,int plan_clase){
         List<PlandeClasesMD> lista_plan=new ArrayList<>();
        try {
            PreparedStatement st=conexion.getCon().prepareStatement("select observaciones,trabajo_autonomo from \"PlandeClases\" where id_plan_clases=?");
            st.setInt(1, plan_clase);
            ResultSet rs=st.executeQuery();
            while(rs.next()){
                PlandeClasesMD pc=new PlandeClasesMD();
                pc.setObservaciones(rs.getString(1));
                pc.setTrabajo_autonomo(rs.getString(2));
                lista_plan.add(pc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlandeClasesBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista_plan;
   }
   
   public static PlandeClasesMD consultarUltimoPlanClase(ConexionBD conexion, int id_curso,int id_unidad){
        PlandeClasesMD planClase =null;
        try {
            PreparedStatement st = conexion.getCon().prepareStatement("select id_plan_clases "
                    + "from \"PlandeClases\" where id_curso=? AND id_unidad=?");
            st.setInt(1, id_curso);
            st.setInt(2, id_unidad);
            ResultSet rs=st.executeQuery();
            while(rs.next()){
                planClase=new PlandeClasesMD();
                planClase.setId_plan_clases(rs.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlandeClasesBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return planClase;
   }
   
   
}
