package controlador.principal;

import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnPrincipalCTR {
    
    private final VtnPrincipal vtnPrin; 

    public VtnPrincipalCTR(VtnPrincipal vtnPrin) {
        this.vtnPrin = vtnPrin;
        
        vtnPrin.setVisible(true); 
    }
    
    
    
    
    
}
