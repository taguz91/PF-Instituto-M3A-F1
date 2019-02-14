package controlador.persona;

import vista.persona.FrmAlumno;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class FrmAlumnoCTR {

    private final VtnPrincipal vtnPrin;
    private final FrmAlumno frmAlumno;

    public FrmAlumnoCTR(VtnPrincipal vtnPrin, FrmAlumno frmAlumno) {
        this.vtnPrin = vtnPrin;
        this.frmAlumno = frmAlumno;

        vtnPrin.getDpnlPrincipal().add(frmAlumno);
        frmAlumno.show();
    }
    
    public void iniciar(){
        
    }
}
