
package controlador.curso;

import java.awt.event.ActionEvent;
import vista.curso.FrmAlumnoCurso;
import vista.principal.VtnPrincipal;

/**
 *
 * @author USUARIO
 */
public class FrmAlumnoCursoCTR {
    private final VtnPrincipal vtnPrin;
    private final FrmAlumnoCurso vista; 

    public FrmAlumnoCursoCTR(VtnPrincipal vtnPrin, FrmAlumnoCurso vtnAlmnCurso) {
        this.vtnPrin = vtnPrin;
        this.vista = vtnAlmnCurso;
    }
    
    //INICIADORES
    
     public void Init(){
        vtnPrin.getDpnlPrincipal().add(vista);  
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
