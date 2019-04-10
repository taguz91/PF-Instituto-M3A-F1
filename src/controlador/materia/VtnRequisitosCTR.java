
package controlador.materia;

import controlador.principal.VtnPrincipalCTR;
import java.util.ArrayList;
import modelo.ConectarDB;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import vista.materia.FrmRequisitos;
import vista.principal.VtnPrincipal;

/**
 *
 * @author gus
 */
public class VtnRequisitosCTR {

   private final ConectarDB conecta;
    private final VtnPrincipalCTR ctrPrin;
    
    
    private final VtnPrincipal vtnPrin;
    private final FrmRequisitos frmreq;
    private final MateriaBD materiabd;
    
    //objeto Materia
    MateriaMD materia;
    //ArrayList de Materias
    
    private ArrayList<MateriaMD> materias;

    public VtnRequisitosCTR(ConectarDB conecta, VtnPrincipalCTR ctrPrin, VtnPrincipal vtnPrin, FrmRequisitos frmreq, MateriaBD materiabd, MateriaMD materia) {
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        this.vtnPrin = vtnPrin;
        this.frmreq = frmreq;
        this.materiabd = materiabd;
        this.materia = materia;
        //agregar la ventana
        vtnPrin.getDpnlPrincipal().add(frmreq);
        frmreq.show();
    }
    
    
    
    
    

    
    
    
   
    
    
    
    
}
