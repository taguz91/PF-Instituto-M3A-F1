package controlador.carrera;

import vista.carrera.FrmCarrera;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class FrmCarreraCTR {
    
    private final VtnPrincipal vtnPrin;
    private final FrmCarrera frmCarrera; 

    public FrmCarreraCTR(VtnPrincipal vtnPrin, FrmCarrera frmCarrera) {
        this.vtnPrin = vtnPrin; 
        this.frmCarrera = frmCarrera;
        
        vtnPrin.getDpnlPrincipal().add(frmCarrera); 
        frmCarrera.show(); 
    }
    
    public void iniciar(){
        
    }  
    
}
