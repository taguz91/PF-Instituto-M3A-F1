package controlador.alumno;

import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
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
    private final VtnPrincipalCTR ctrPrin;

    public VtnAlumnoCursoCTR(VtnPrincipal vtnPrin, VtnAlumnoCurso vtnAlmnCurso, ConectarDB conecta, VtnPrincipalCTR ctrPrin) {
        this.vtnPrin = vtnPrin; 
        this.vtnAlmnCurso = vtnAlmnCurso;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        
        //Cambiamos el estado del cursos  
        vtnPrin.setCursor(new Cursor(3));
        ctrPrin.estadoCargaVtn("Alumnos por curso");
        
        vtnPrin.getDpnlPrincipal().add(vtnAlmnCurso);  
        vtnAlmnCurso.show(); 
    }
    
    public void iniciar(){
        //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaVtnFin("Alumnos por curso");
    }
    
    public void abrirFrmCurso(){
        
    }
    
    
}
