/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.docente;

import controlador.principal.VtnPrincipalCTR;
import java.util.ArrayList;
import modelo.ConectarDB;
import modelo.docente.RolDocenteBD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import vista.docente.VtnRolesPeriodos;
import vista.principal.VtnPrincipal;

/**
 *
 * @author arman
 */
public class VtnRolPeriodosCTR {
    
    private final VtnPrincipal vtnPrin;
    private final VtnRolesPeriodos vtnRolPe;
    private final ConectarDB conecta;
    private final VtnPrincipalCTR ctrPrin;
    private final RolDocenteBD rolDoc;
// para combo de periodo
    private ArrayList<PeriodoLectivoMD> periodos;
    private final PeriodoLectivoBD prd;

    public VtnRolPeriodosCTR(VtnPrincipal vtnPrin, VtnRolesPeriodos vtnRolPe, ConectarDB conecta, VtnPrincipalCTR ctrPrin) {
        this.vtnPrin = vtnPrin;
        this.vtnRolPe = vtnRolPe;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        this.rolDoc = new RolDocenteBD(conecta);
        this.prd = new PeriodoLectivoBD(conecta);
        vtnPrin.getDpnlPrincipal().add(vtnRolPe);
        vtnRolPe.show();
    }
public void iniciar(){
    vtnRolPe.getBtnIngresar().addActionListener(e->abrirFRM());
}    
private void abrirFRM(){
    ctrPrin.abrirFrmRolesPeriodos();
    vtnRolPe.dispose();
}
       
}
