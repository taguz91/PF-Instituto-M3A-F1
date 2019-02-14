package controlador.curso;

import vista.curso.FrmCurso;
import vista.curso.VtnAlumnoCurso;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class FrmCursoCTR {
    
    private final VtnPrincipal vtnPrin;
    private final FrmCurso frmCurso; 

    public FrmCursoCTR(VtnPrincipal vtnPrin, FrmCurso frmCurso) {
        this.vtnPrin = vtnPrin; 
        this.frmCurso = frmCurso;
        
        vtnPrin.getDpnlPrincipal().add(frmCurso);  
        frmCurso.show(); 
    }
    
    public void iniciar(){
        
    }
    
    
}
