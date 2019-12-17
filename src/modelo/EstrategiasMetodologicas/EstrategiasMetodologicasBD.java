package modelo.EstrategiasMetodologicas;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConnDBPool;

public class EstrategiasMetodologicasBD extends EstrategiasMetodologicasMD {

    private static final ConnDBPool CON = ConnDBPool.single();

    public EstrategiasMetodologicasBD() {
    }

    public boolean insertarEstrategiasMetodologicas2(EstrategiasMetodologicasMD em, int id_plan_Clase) {
        PreparedStatement st = CON.prepareStatement("INSERT INTO public.\"EstrategiasMetodologias\"(\n"
                + "	tipo_estrategias_metodologias, id_plan_de_clases, nombre_estrategia\n)"
                + "	VALUES ( ?, " + id_plan_Clase + ", ?)");

        try {

            st.setString(1, em.getTipo_estrategias_metodologicas());
            st.setString(2, em.getNombre_estrategia());
            st.executeUpdate();
            System.out.println(st);
        } catch (SQLException ex) {
            Logger.getLogger(EstrategiasMetodologicasBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(st);
        }
        return true;
    }

    public static List<EstrategiasMetodologicasMD> consultarEstrategiasMetologicas(int id_plan_clase) {
        List<EstrategiasMetodologicasMD> lista_est_meto = new ArrayList<>();
        PreparedStatement st = CON.prepareStatement(""
                + "select  em.tipo_estrategias_metodologias ,em.nombre_estrategia from \"EstrategiasMetodologias\" em join \"PlandeClases\" p\n"
                + "					 on em.id_plan_de_clases=p.id_plan_clases where em.id_plan_de_clases=?");
        try {
            st.setInt(1, id_plan_clase);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                EstrategiasMetodologicasMD em = new EstrategiasMetodologicasMD();
                em.setTipo_estrategias_metodologicas(rs.getString(1));
                em.setNombre_estrategia(rs.getString(2));
                lista_est_meto.add(em);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstrategiasMetodologicasBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(st);
        }
        return lista_est_meto;
    }

}
