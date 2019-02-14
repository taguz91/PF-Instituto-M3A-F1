package controlador.prdlectivo;

import vista.prdlectivo.VtnPrdLectivo;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnPrdLectivoCTR {
    
    private final VtnPrincipal vtnPrin;
    private final VtnPrdLectivo vtnPrdLectivo; 

    public VtnPrdLectivoCTR(VtnPrincipal vtnPrin, VtnPrdLectivo vtnPrdLectivo) {
        this.vtnPrin = vtnPrin;
        this.vtnPrdLectivo = vtnPrdLectivo;
        
        vtnPrin.getDpnlPrincipal().add(vtnPrdLectivo);   
        vtnPrdLectivo.show(); 
    }
    
    public void iniciar(){
        
    }
    
    public void abrirFrmPrdLectivo(){
        
    }
    
    
}
