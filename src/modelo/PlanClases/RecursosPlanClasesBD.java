
package modelo.PlanClases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConexionBD;

public class RecursosPlanClasesBD extends RecursosPlanClasesMD{
    
    private ConexionBD conexion;

    public RecursosPlanClasesBD(ConexionBD conexion) {
        this.conexion = conexion;
    }

    public RecursosPlanClasesBD(ConexionBD conexion, PlandeClasesMD id_plan_clases, RecursosMD id_recursos) {
        super(id_plan_clases, id_recursos);
        this.conexion = conexion;
    }

    public RecursosPlanClasesBD(ConexionBD conexion, RecursosMD id_recursos) {
        super(id_recursos);
        this.conexion = conexion;
    }
    
    
    public boolean insertarRecursosPlanClases2(RecursosPlanClasesMD rP,int id_plan_clase){
        
        try {
            PreparedStatement st=conexion.getCon().prepareStatement("INSERT INTO public.\"RecursosPlanClases\"(\n" +
                    "	 id_plan_clases, id_recurso)\n" +
                    "	VALUES ( "+id_plan_clase+", ?)");
            st.setInt(1, rP.getId_recursos().getId_recurso());
            st.executeUpdate();
            System.out.println(st);
        } catch (SQLException ex) {
            Logger.getLogger(RecursosPlanClasesBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  true;
    }
    public boolean ActualizarRecursosPlanClases(RecursosPlanClasesMD rP){
        
        try {
            PreparedStatement st=conexion.getCon().prepareStatement("UPDATE public.\"RecursosPlanClases\"\n" +
"	  set id_recurso =? \n" +
"	WHERE id_plan_clases=? and id_recurso=?");
            st.setInt(1, rP.getId_recursos().getId_recurso());
            st.setInt(2, rP.getId_plan_clases().getID());
            st.setInt(3, rP.getId_recursos().getId_recurso());
            st.executeUpdate();
            System.out.println(st);
        } catch (SQLException ex) {
            Logger.getLogger(RecursosPlanClasesBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  true;
    }
    
       public  static List<RecursosPlanClasesMD>  consultarRecursos(ConexionBD conexion){
        List<RecursosPlanClasesMD> recursos=new ArrayList<>();
        
         try {

            PreparedStatement st = conexion.getCon().prepareStatement("select id_recurso,nombre_recursos,tipo_recurso from \"Recursos\" order by nombre_recursos ");

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                RecursosPlanClasesMD re=new RecursosPlanClasesMD();
                re.getId_recursos().setId_recurso(rs.getInt(1));
                re.getId_recursos().setNombre_recursos(rs.getString(2));
                re.getId_recursos().setTipo_recurso(rs.getString(3));
                
                recursos.add(re);
            }

        } catch (SQLException ex) {
            Logger.getLogger(RecursosPlanClasesBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return recursos;
    }
       public  static List<RecursosPlanClasesMD>  consultarRecursosPlanClase(ConexionBD conexion,int id_plan_clase){
        List<RecursosPlanClasesMD> recursos=new ArrayList<>();
        
         try {

            PreparedStatement st = conexion.getCon().prepareStatement("select r.id_recurso,r.nombre_recursos from \"Recursos\" r join \"RecursosPlanClases\" rp on r.id_recurso=rp.id_recurso \n" +
            "where rp.id_plan_clases=? ");
            st.setInt(1, id_plan_clase);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                RecursosPlanClasesMD re=new RecursosPlanClasesMD();
                re.getId_recursos().setId_recurso(rs.getInt(1));
                re.getId_recursos().setNombre_recursos(rs.getString(2));
                recursos.add(re);
            }

        } catch (SQLException ex) {
            Logger.getLogger(RecursosPlanClasesBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return recursos;
    }
    
}
