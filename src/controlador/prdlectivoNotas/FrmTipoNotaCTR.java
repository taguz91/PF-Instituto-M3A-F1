
package controlador.prdlectivoNotas;

import java.awt.event.ActionEvent;
import vista.periodoLectivoNotas.FrmTipoNota;
import vista.principal.VtnPrincipal;

/**
 *
 * @author USUARIO
 */
public class FrmTipoNotaCTR {
    private VtnPrincipal desktop;
    private FrmTipoNota vista;
    
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
