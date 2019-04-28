/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.PlanClases;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    public void insertarTrabajoAutonomo(EvaluacionSilaboMD es){
        try {
            PreparedStatement st=conexion.getCon().prepareStatement("INSERT INTO public.\"TrabajoAutonomo\"(\n" +
                    "	id_evaluacion, id_plan_clases, autonomo_plan)\n" +
                    "	VALUES (?, SELECT MAX(id_plan_clases) from public.\"PlandeClases\", ?)");
            st.setInt(1, es.getIdEvaluacion());
            st.setString(2, getAutonomo_plan_descripcion());
            st.executeUpdate();
            System.out.println(st);
        } catch (SQLException ex) {
            Logger.getLogger(TrabajoAutonomoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
