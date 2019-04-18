/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.docente;

import controlador.principal.VtnPrincipalCTR;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.ConectarDB;
import modelo.docente.RolPeriodoBD;
import modelo.docente.RolPeriodoMD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.validaciones.TxtVLetras;
import modelo.validaciones.Validar;
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
    private final RolPeriodoBD rolDoc;
    private ArrayList<PeriodoLectivoMD> periodos;
    private final PeriodoLectivoBD prd;
    private boolean editar = false;
    private int idRolPrd;

    public FrmRolPeriodoCTR(VtnPrincipal vtnPrin, FrmRolesPeriodos frmRolPer, ConectarDB conecta, VtnPrincipalCTR ctrPrin) {
        this.vtnPrin = vtnPrin;
        this.frmRolPer = frmRolPer;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        this.rolDoc = new RolPeriodoBD(conecta);
        this.prd = new PeriodoLectivoBD(conecta);
        vtnPrin.getDpnlPrincipal().add(frmRolPer);
        frmRolPer.show();
    }

    public void iniciar() {
        cargarCmbPrdLectivo();
        frmRolPer.getLbl_error_roles().setVisible(false);
        iniciarValidaciones();
        frmRolPer.getBtnGuardar().addActionListener(e -> insertarRolesPeriodos());
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

    public void insertarRolesPeriodos() {
        int posFila = frmRolPer.getCmbPeriodoLectivo().getSelectedIndex();
        boolean guardar = true;
        if (posFila == 0) {
            guardar = false;
        }
        if (!Validar.esLetras(frmRolPer.getTxtNombreRol().getText().trim())) {
            guardar = false;
        }
        rolDoc.setPeriodo(periodos.get(posFila - 1));
        rolDoc.setNombre_rol(frmRolPer.getTxtNombreRol().getText());
        if (guardar) {
            if (editar) {
                rolDoc.editarRolPeriodo(idRolPrd);
                JOptionPane.showMessageDialog(null, "Datos editados correctamente");
            } else {
                if (rolDoc.InsertarRol() == true) {
                    JOptionPane.showMessageDialog(null, "Datos grabados correctamente");
                    System.out.println(rolDoc.getPeriodo());
                } else {
                    JOptionPane.showMessageDialog(null, "Error en grabar los datos");
                }
            }
            frmRolPer.dispose();
            ctrPrin.abrirVtnRolesPeriodos();
        } 
    }
    public void iniciarValidaciones() {
        frmRolPer.getTxtNombreRol().addKeyListener(new TxtVLetras(
                frmRolPer.getTxtNombreRol(), frmRolPer.getLbl_error_roles()));
    }

    public void editarRolesPeriodos(RolPeriodoMD rp) {
        idRolPrd = rp.getId_rol(); 
        editar = true;
        frmRolPer.getTxtNombreRol().setText(rp.getNombre_rol()); 
        frmRolPer.getCmbPeriodoLectivo().setSelectedItem(rp.getPeriodo().getNombre_PerLectivo());
    }
}