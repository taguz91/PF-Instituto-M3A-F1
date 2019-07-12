/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo;

import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionEvent;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.JOptionPane;
import modelo.ConexionBD;
import modelo.PlanClases.PlandeClasesBD;
import modelo.PlanClases.PlandeClasesMD;
import vista.principal.VtnPrincipal;
import vista.silabos.frmEditarFechaG;

public class ControladorEditarFechaGenPlanClase {
    
    private ConexionBD conexion;
    private final VtnPrincipal principal;
    private frmEditarFechaG feditar;
    private PlandeClasesMD planMD;

    public ControladorEditarFechaGenPlanClase(ConexionBD conexion, VtnPrincipal principal, PlandeClasesMD planMD) {
        this.conexion = conexion;
        this.principal = principal;
        this.planMD = planMD;
    }
    
    public void iniciaControlador(){
        conexion.conectar();
        feditar= new frmEditarFechaG();
        feditar.setLocation((principal.getDpnlPrincipal().getSize().width - feditar.getSize().width) / 2,
                (principal.getDpnlPrincipal().getSize().height - feditar.getSize().height) / 2);
        principal.getDpnlPrincipal().add(feditar);
        feditar.show();
        feditar.setTitle("Editar Fecha Generacion Plan de clase");
        CargarFecha(planMD);
        
        feditar.getBtn_Cancelar().addActionListener((ActionEvent e) -> {
          feditar.dispose();
          principal.getMnCtPlandeClase().doClick();
        });
        feditar.getBtn_actualizar_fg().addActionListener((ActionEvent e) -> {
            if (validarNullo(feditar.getDch_fecha_g())==true) {
                
                feditar.dispose();
                actualizarFecha();
                 principal.getMnCtPlandeClase().doClick();
            } else {
                JOptionPane.showMessageDialog(null, "Campo Vacío", "Aviso", JOptionPane.ERROR_MESSAGE);
            }
        });
        
    }
    
    private void CargarFecha(PlandeClasesMD pl){
        feditar.getDch_fecha_g().setDate(Date.from(pl.getFecha_generacion().atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }
    private void actualizarFecha(){
        try {
            new PlandeClasesBD(conexion).editarFechageneracoion(planMD.getId_plan_clases(),feditar.getDch_fecha_g().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            JOptionPane.showMessageDialog(null, "Se actualizó exitosamente", "Exitoso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            System.out.println();
            JOptionPane.showMessageDialog(null, "Falló al guardar", "Aviso", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validarNullo(JDateChooser jd){
         if(jd.getDate()==null) {
            return false;
        }else{
          return true;  
        }
    }
    
    
    
}
