
package controlador.periodoLectivoNotas;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import vista.periodoLectivoNotas.FrmIngresoNotas;
import vista.principal.VtnPrincipal;

/**
 *
 * @author USUARIO
 */
public class FrmIngresoNotasCTR {
    private VtnPrincipal desktop;
    private FrmIngresoNotas vista;
    
    
    //INICIADORES
    
    public void Init(){
        desktop.getDpnlPrincipal().add(vista);   
        vista.show(); 
    }
    
    public void InitEventos(){
        vista.getBtnGuardar().addActionListener(e->btnGuardarActionPerformance(e));
        vista.getBtnCancelar().addActionListener(e->btnCancelarActionPerformance(e));
    }
    
    //METODOS DE APOYO
    
    
    //EVENTOS
    
    private void btnGuardarActionPerformance(ActionEvent e){
         
    }
    
    private void btnCancelarActionPerformance(ActionEvent e){
         
    }
}
