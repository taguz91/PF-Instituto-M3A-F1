package controlador.alumno;

import modelo.ConectarDB;
import vista.alumno.VtnAlumnoCurso;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnAlumnoCursoCTR {
    
    private final VtnPrincipal vtnPrin;
    private final VtnAlumnoCurso vtnAlmnCurso;
    private final ConectarDB conecta;

    public VtnAlumnoCursoCTR(VtnPrincipal vtnPrin, VtnAlumnoCurso vtnAlmnCurso, ConectarDB conecta) {
        this.vtnPrin = vtnPrin; 
        this.vtnAlmnCurso = vtnAlmnCurso;
        this.conecta = conecta;
        
        vtnPrin.getDpnlPrincipal().add(vtnAlmnCurso);  
        vtnAlmnCurso.show(); 
    }
    
    public void iniciar(){
        
    }
    
    public void abrirFrmCurso(){
        
    }
    
    
}
