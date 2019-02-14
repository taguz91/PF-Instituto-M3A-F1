package controlador.materia;

import vista.materia.VtnMateria;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnMateriaCTR {
    
    private final VtnPrincipal vtnPrin;
    private final VtnMateria vtnMateria; 

    public VtnMateriaCTR(VtnPrincipal vtnPrin, VtnMateria vtnMateria) {
        this.vtnPrin = vtnPrin;
        this.vtnMateria = vtnMateria;
        
        vtnPrin.getDpnlPrincipal().add(vtnMateria);  
        vtnMateria.show(); 
    }
    
    public void iniciar(){
        
    }
    
    public void abrirFrmMateria(){
        
    }
    
    
}
