package controlador.persona;

import vista.persona.FrmDocente;
import vista.persona.VtnDocente;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnDocenteCTR {
    
    private final VtnPrincipal vtnPrin;
    private final VtnDocente vtnDocente; 

    public VtnDocenteCTR(VtnPrincipal vtnPrin, VtnDocente vtnDocente) {
        this.vtnPrin = vtnPrin;
        this.vtnDocente = vtnDocente; 
        
        vtnPrin.getDpnlPrincipal().add(vtnDocente);   
        vtnDocente.show(); 
    }
    
    public void iniciar(){
        vtnDocente.getBtnIngresar().addActionListener(e -> abrirFrmDocente()); 
    }
    
    public void abrirFrmDocente() {
        FrmDocente frmDocente  = new FrmDocente(); 
        FrmDocenteCTR ctrFrmDocente = new FrmDocenteCTR(vtnPrin, frmDocente);
        ctrFrmDocente.iniciar();
    }
    
    
}
