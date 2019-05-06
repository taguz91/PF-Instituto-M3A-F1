
package modelo.EstrategiasMetodologicas;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConexionBD;
import modelo.PlanClases.PlandeClasesMD;
import modelo.estrategiasUnidad.EstrategiasUnidadMD;


public class EstrategiasMetodologicasBD extends EstrategiasMetodologicasMD {
     private ConexionBD conexion;

    public EstrategiasMetodologicasBD(ConexionBD conexion) {
        this.conexion = conexion;
    }

    public EstrategiasMetodologicasBD(ConexionBD conexion, PlandeClasesMD id_plan_clases, EstrategiasUnidadMD id_estrategias_unidad) {
        super(id_plan_clases, id_estrategias_unidad);
        this.conexion = conexion;
    }
    public boolean  insertarEstrategiasMetodologicas(EstrategiasMetodologicasMD em,EstrategiasUnidadMD eu){
        
         try {
             PreparedStatement st =conexion.getCon().prepareStatement("INSERT INTO public.\"EstrategiasMetodologias\"(\n" +
                     "	tipo_estrategias_metodologias, id_plan_de_clases, id_estrategias_unidad)\n" +
                     "	VALUES ( ?, (SELECT MAX(id_plan_clases) from \"PlandeClases\"), ?)");
             
             st.setString(1, em.getTipo_estrategias_metodologicas());
             st.setInt(2, eu.getIdEstrategiaUnidad());
         } catch (SQLException ex) {
             Logger.getLogger(EstrategiasMetodologicasBD.class.getName()).log(Level.SEVERE, null, ex);
         }
         return true;
    }
     
}
