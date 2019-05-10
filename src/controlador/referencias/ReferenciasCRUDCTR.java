
package controlador.referencias;


import controlador.principal.VtnPrincipalCTR;
import modelo.ConectarDB;
import modelo.ReferenciasB.ReferenciaBD;
import vista.principal.VtnPrincipal;

import vista.silabos.frmBibliografia;
import vista.silabos.frmCRUDBibliografia;



public class ReferenciasCRUDCTR {
    private final frmCRUDBibliografia frmCRUDBibliografiaC;
    private final ReferenciaBD BDbibliotecaC;
    private final ConectarDB conecta;
     private final VtnPrincipal vtnPrin;
    private final VtnPrincipalCTR ctrPrin;
    public ReferenciasCRUDCTR(ConectarDB conecta,VtnPrincipalCTR ctrPrin,VtnPrincipal vtnPrin,frmCRUDBibliografia frmCRUDBibliografiaC) {
        this.frmCRUDBibliografiaC = frmCRUDBibliografiaC;
        this.BDbibliotecaC =  new ReferenciaBD(conecta);
        this.conecta=conecta;
        this.ctrPrin=ctrPrin;
        this.vtnPrin=vtnPrin;
        this.
        vtnPrin.getDpnlPrincipal().add(frmCRUDBibliografiaC);
        frmCRUDBibliografiaC.show();
    }
        public void iniciarControlador(
        ){
            frmCRUDBibliografiaC.getBtnNuevoCB().addActionListener(e->AbrirFormularioRefe());
            
    }
        public void AbrirFormularioRefe (){
            frmBibliografia frmBibliografiaC= new frmBibliografia ();
            ReferenciasCTR  ReferenciasCTRC = new ReferenciasCTR(conecta,ctrPrin,vtnPrin, frmBibliografiaC);
            ReferenciasCTRC.iniciarControlador();
        } 
}
