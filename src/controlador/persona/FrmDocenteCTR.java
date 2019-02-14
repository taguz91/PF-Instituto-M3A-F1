package controlador.persona;

import vista.persona.FrmDocente;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class FrmDocenteCTR {

    private final VtnPrincipal vtnPrin;
    private final FrmDocente frmDocente;

    public FrmDocenteCTR(VtnPrincipal vtnPrin, FrmDocente frmDocente) {
        this.vtnPrin = vtnPrin; 
        this.frmDocente = frmDocente;

        vtnPrin.getDpnlPrincipal().add(frmDocente);
        frmDocente.show();
    }
    
    public void iniciar(){
        
    }
}
