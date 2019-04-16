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
import modelo.validaciones.TxtVDireccion;
import modelo.validaciones.TxtVLetras;
import vista.docente.FrmRolesPeriodos;
import vista.docente.VtnRolesPeriodos;
import vista.principal.VtnPrincipal;

/**
 *
 * @author arman
 */
public class FrmRolPeriodoCTR {

    private final VtnPrincipal vtnPrin;
    private final FrmRolesPeriodos frmRolPer;
    private final ConectarDB conecta;
    private final VtnPrincipalCTR ctrPrin;
    private final RolDocenteBD rolDoc;
    private ArrayList<PeriodoLectivoMD> periodos;
    private final PeriodoLectivoBD prd;

    public FrmRolPeriodoCTR(VtnPrincipal vtnPrin, FrmRolesPeriodos frmRolPer, ConectarDB conecta, VtnPrincipalCTR ctrPrin) {
        this.vtnPrin = vtnPrin;
        this.frmRolPer = frmRolPer;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        this.rolDoc = new RolDocenteBD(conecta);
        this.prd = new PeriodoLectivoBD(conecta);
        vtnPrin.getDpnlPrincipal().add(frmRolPer);
        frmRolPer.show();
    }

    public FrmRolPeriodoCTR(VtnPrincipal vtnPrin, VtnRolesPeriodos vtnRolprd, ConectarDB conecta, VtnPrincipalCTR aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void iniciar() {
        cargarCmbPrdLectivo();
        frmRolPer.getLbl_error_roles().setVisible(false);
        iniciarValidaciones();
    }

    private void cargarCmbPrdLectivo() {
        periodos = prd.cargarPrdParaCmbFrm();
        if (periodos != null) {
            frmRolPer.getCmbPeriodoLectivo().removeAllItems();
            frmRolPer.getCmbPeriodoLectivo().addItem("Seleccione");
            periodos.forEach((p) -> {
                frmRolPer.getCmbPeriodoLectivo().addItem(p.getNombre_PerLectivo());
            });
        }
    }
    public void iniciarValidaciones(){
        frmRolPer.getTxtNombreRol().addKeyListener(new TxtVLetras(
                frmRolPer.getTxtNombreRol(), frmRolPer.getLbl_error_roles()));
    }
}