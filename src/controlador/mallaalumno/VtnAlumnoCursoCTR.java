package controlador.mallaalumno;

import vista.curso.VtnAlumnoCurso;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnAlumnoCursoCTR {
    
    private final VtnPrincipal vtnPrin;
    private final VtnAlumnoCurso vtnAlmnCurso; 

    public VtnAlumnoCursoCTR(VtnPrincipal vtnPrin, VtnAlumnoCurso vtnAlmnCurso) {
        this.vtnPrin = vtnPrin; 
        this.vtnAlmnCurso = vtnAlmnCurso;
        
        vtnPrin.getDpnlPrincipal().add(vtnAlmnCurso);  
        vtnAlmnCurso.show(); 
    }
    
    public void iniciar(){
        
    }
    
    public void abrirFrmCurso(){
        
    }
    
    
}
