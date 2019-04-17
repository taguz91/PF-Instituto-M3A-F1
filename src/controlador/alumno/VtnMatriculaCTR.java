package controlador.alumno;

import controlador.principal.DependenciasCTR;
import controlador.principal.VtnPrincipalCTR;
import java.util.ArrayList;
import modelo.ConectarDB;
import modelo.alumno.MatriculaBD;
import modelo.alumno.MatriculaMD;
import vista.alumno.VtnMatricula;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnMatriculaCTR extends DependenciasCTR{
    
    private final VtnMatricula vtnMatri;
    private final MatriculaBD matr; 
    private ArrayList<MatriculaMD> matriculas;
    
    public VtnMatriculaCTR(ConectarDB conecta, VtnPrincipal vtnPrin, VtnPrincipalCTR ctrPrin, VtnMatricula vtnMatri) {
        super(conecta, vtnPrin, ctrPrin);
        this.matr = new MatriculaBD(conecta);
        this.vtnMatri = vtnMatri; 
    }
    
    
}
