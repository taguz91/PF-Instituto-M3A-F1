
package modelo.PlanClases;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConexionBD;
import modelo.curso.CursoMD;
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
    
    public void insertarPlanClases(){
        
        try {
            PreparedStatement st=conexion.getCon().prepareStatement(""
                    + "INSERT INTO public.\"PlandeClases\"(\n" +
"	 id_curso, id_unidad, observaciones,\n" +
"	 ,fecha_revision, fecha_generacion, fecha_cierre)\n" +
"	VALUES (?, ?, ?, ?, ?, ?, ?)");
            st.setInt(1, getId_curso().getId());
            st.setInt(2, getId_unidad().getIdUnidad());
            st.setString(3, getObservaciones());
//            st.setDate(4, java.sql.Date.valueOf(getFecha_revision()));
//            st.setDate(5, java.sql.Date.valueOf(getFecha_generacion()));
//            st.setDate(6, java.sql.Date.valueOf(getFecha_cierre()));
              st.executeUpdate();
              System.out.println(st);
              st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PlandeClasesBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
   
}
