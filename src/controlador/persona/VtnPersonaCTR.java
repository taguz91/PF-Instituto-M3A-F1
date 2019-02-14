package controlador.persona;

import vista.persona.VtnPersona;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnPersonaCTR {
    
    private final VtnPrincipal vtnPrin;
    private final VtnPersona vtnPersona; 

    public VtnPersonaCTR(VtnPrincipal vtnPrin, VtnPersona vtnPersona) {
        this.vtnPrin = vtnPrin;
        this.vtnPersona = vtnPersona;
        
        vtnPrin.getDpnlPrincipal().add(vtnPersona);   
        vtnPersona.show(); 
    }
    
    public void iniciar(){
        
    }
    
    public void abrirFrmPersona(){
        
    }
    
    
}
