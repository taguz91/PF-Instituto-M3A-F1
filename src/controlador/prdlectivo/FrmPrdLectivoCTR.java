package controlador.prdlectivo;

import vista.prdlectivo.FrmPrdLectivo;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class FrmPrdLectivoCTR {
    
    private final VtnPrincipal vtnPrin;
    private final FrmPrdLectivo frmPrdLectivo; 

    public FrmPrdLectivoCTR(VtnPrincipal vtnPrin, FrmPrdLectivo frmPrdLectivo) {
        this.vtnPrin = vtnPrin;
        this.frmPrdLectivo = frmPrdLectivo; 
        
        vtnPrin.getDpnlPrincipal().add(frmPrdLectivo);   
        frmPrdLectivo.show(); 
    }
    
    public void iniciar(){
        
    }
    
}
