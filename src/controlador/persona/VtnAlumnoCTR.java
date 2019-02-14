package controlador.persona;

import vista.persona.FrmAlumno;
import vista.persona.VtnAlumno;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnAlumnoCTR {
    
    private final VtnPrincipal vtnPrin;
    private final VtnAlumno vtnAlumno; 

    public VtnAlumnoCTR(VtnPrincipal vtnPrin, VtnAlumno vtnAlumno) {
        this.vtnPrin = vtnPrin;
        this.vtnAlumno = vtnAlumno;  
        
        vtnPrin.getDpnlPrincipal().add(vtnAlumno);   
        vtnAlumno.show(); 
    }
    
    public void iniciar(){
        vtnAlumno.getBtnIngresar().addActionListener(e -> abrirFrmAlumno()); 
    }
    
    public void abrirFrmAlumno(){
        FrmAlumno frmAlumno = new FrmAlumno(); 
        FrmAlumnoCTR ctrFrmAlumno = new FrmAlumnoCTR(vtnPrin, frmAlumno); 
        ctrFrmAlumno.iniciar();
    } 
    
}
