
package modelo.EstrategiasMetodologicas;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
    public boolean  insertarEstrategiasMetodologicas(EstrategiasMetodologicasMD em){
        
         try {
             PreparedStatement st =conexion.getCon().prepareStatement("INSERT INTO public.\"EstrategiasMetodologias\"(\n" +
                     "	tipo_estrategias_metodologias, id_plan_de_clases, id_estrategias_unidad)\n" +
                     "	VALUES ( ?, (SELECT MAX(id_plan_clases) from \"PlandeClases\"), ?)");
             
             st.setString(1, em.getTipo_estrategias_metodologicas());
             st.setInt(2, em.getId_estrategias_unidad().getIdEstrategiaUnidad());
             st.executeUpdate();
             System.out.println(st);
         } catch (SQLException ex) {
             Logger.getLogger(EstrategiasMetodologicasBD.class.getName()).log(Level.SEVERE, null, ex);
         }
         return true;
    }
    public boolean  insertarEstrategiasMetodologicas2(EstrategiasMetodologicasMD em,int id_plan_Clase){
        
         try {
             PreparedStatement st =conexion.getCon().prepareStatement("INSERT INTO public.\"EstrategiasMetodologias\"(\n" +
                     "	tipo_estrategias_metodologias, id_plan_de_clases, nombre_estrategia\n" +
                     "	VALUES ( ?, "+id_plan_Clase+", ?)");
             
             st.setString(1, em.getTipo_estrategias_metodologicas());
             st.setString(2, em.getNombre_estrategia());
             st.executeUpdate();
             System.out.println(st);
         } catch (SQLException ex) {
             Logger.getLogger(EstrategiasMetodologicasBD.class.getName()).log(Level.SEVERE, null, ex);
         }
         return true;
    }
    
    public static List<EstrategiasMetodologicasMD> consultarEstrategiasMetologicas(ConexionBD conexion,int id_plan_clase){
        List<EstrategiasMetodologicasMD> lista_est_meto=new ArrayList<>();
         try {
             PreparedStatement st =conexion.getCon().prepareStatement(""
                     + "select  em.tipo_estrategias_metodologias ,em.nombre_estrategia from \"EstrategiasMetodologias\" em join \"PlandeClases\" p\n" +
"					 on em.id_plan_de_clases=p.id_plan_clases where em.id_plan_de_clases=?");
             st.setInt(1, id_plan_clase);
             ResultSet rs=st.executeQuery();
             while(rs.next()){
                 EstrategiasMetodologicasMD em=new EstrategiasMetodologicasMD();
                 em.setTipo_estrategias_metodologicas(rs.getString(1));
                 em.setNombre_estrategia(rs.getString(2));
                 lista_est_meto.add(em);
             }
         } catch (SQLException ex) {
             Logger.getLogger(EstrategiasMetodologicasBD.class.getName()).log(Level.SEVERE, null, ex);
         }
        return lista_est_meto;
    }
     
}
