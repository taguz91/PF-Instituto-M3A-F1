/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.PlanClases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConexionBD;
import modelo.evaluacionSilabo.EvaluacionSilaboMD;

/**
 *
 * @author ANDRES BERMEO
 */
public class TrabajoAutonomoBD extends TrabajoAutonomoMD{
    
    private ConexionBD conexion;

    public TrabajoAutonomoBD(ConexionBD conexion) {
        this.conexion = conexion;
    }

    public TrabajoAutonomoBD(ConexionBD conexion, EvaluacionSilaboMD id_evaluacion, PlandeClasesMD id_plan_clases) {
        super(id_evaluacion, id_plan_clases);
        this.conexion = conexion;
    }
    public boolean insertarTrabajoAutonomo(EvaluacionSilaboMD es,TrabajoAutonomoMD ta){
        try {
            PreparedStatement st=conexion.getCon().prepareStatement("INSERT INTO public.\"TrabajoAutonomo\"(\n" +
                    "	id_evaluacion, id_plan_clases, autonomo_plan)\n" +
                    "	VALUES (?, SELECT MAX(id_plan_clases) from public.\"PlandeClases\", ?)");
            st.setInt(1, es.getIdEvaluacion());
            st.setString(2, ta.getAutonomo_plan_descripcion());
            st.executeUpdate();
            System.out.println(st);
        } catch (SQLException ex) {
            Logger.getLogger(TrabajoAutonomoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    public boolean insertarTrabajoAutonomo1(TrabajoAutonomoMD ta){
        try {
            PreparedStatement st=conexion.getCon().prepareStatement("INSERT INTO public.\"TrabajoAutonomo\"(\n" +
                    "	id_evaluacion, id_plan_clases, autonomo_plan)\n" +
                    "	VALUES (?, (SELECT MAX(id_plan_clases) from public.\"PlandeClases\"), ?)");
            st.setInt(1, ta.getId_evaluacion().getIdEvaluacion());
            st.setString(2, ta.getAutonomo_plan_descripcion());
            st.executeUpdate();
            System.out.println(st);
        } catch (SQLException ex) {
            Logger.getLogger(TrabajoAutonomoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    
    public static List<TrabajoAutonomoMD> consultarTrabajoAutonomo(ConexionBD conexion, int id_plan_clase){
        List<TrabajoAutonomoMD> lista_tra_aut=new ArrayList<>();
        try {
            PreparedStatement st=conexion.getCon().prepareStatement("select autonomo_plan from \"TrabajoAutonomo\" where id_plan_clases=?");
            st.setInt(1, id_plan_clase);
            ResultSet rs=st.executeQuery();
            while (rs.next()) {
                TrabajoAutonomoMD ta= new TrabajoAutonomoMD();
                ta.setAutonomo_plan_descripcion(rs.getString(1));
                lista_tra_aut.add(ta);
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TrabajoAutonomoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista_tra_aut;
    }
    
    
}
