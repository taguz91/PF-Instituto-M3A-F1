
package modelo.PlanClases;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    public void insertarRecursosPlanClases(RecursosMD e){
        
        try {
            PreparedStatement st=conexion.getCon().prepareStatement("INSERT INTO public.\"RecursosPlanClases\"(\n" +
                    "	 id_plan_clases, id_recurso)\n" +
                    "	VALUES ( SELECT MAX(id_plan_clases) from public.\"PlandeClases), ?)");
            st.setInt(1, e.getId_recurso());
            st.executeUpdate();
            System.out.println(st);
        } catch (SQLException ex) {
            Logger.getLogger(RecursosPlanClasesBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
