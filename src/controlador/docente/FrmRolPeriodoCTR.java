package controlador.docente;

import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.docente.RolPeriodoBD;
import modelo.docente.RolPeriodoMD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.validaciones.TxtVLetras;
import modelo.validaciones.Validar;
import vista.docente.FrmRolesPeriodos;

/**
 *
 * @author arman
 */
public class FrmRolPeriodoCTR extends DCTR {

    private final FrmRolesPeriodos frmRolPer;
    private final RolPeriodoBD RPLBD = RolPeriodoBD.single();
    private ArrayList<PeriodoLectivoMD> periodos;
    private final PeriodoLectivoBD PRBD = PeriodoLectivoBD.single();
    private boolean editar = false;
    private int idRolPrd;

    public FrmRolPeriodoCTR(FrmRolesPeriodos frmRolPer, VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
        this.frmRolPer = frmRolPer;
    }

    public void iniciar() {
        cargarCmbPrdLectivo();
        frmRolPer.getLbl_error_roles().setVisible(false);
        iniciarValidaciones();
        frmRolPer.getBtnGuardar().addActionListener(e -> insertarRolesPeriodos());

        ctrPrin.agregarVtn(frmRolPer);
    }

    private void cargarCmbPrdLectivo() {
        periodos = PRBD.cargarPrdParaCmbFrm();
        if (periodos != null) {
            frmRolPer.getCmbPeriodoLectivo().removeAllItems();
            frmRolPer.getCmbPeriodoLectivo().addItem("Seleccione");
            periodos.forEach((p) -> {
                frmRolPer.getCmbPeriodoLectivo().addItem(p.getNombre());
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

        if (guardar) {
            RolPeriodoMD rpl = new RolPeriodoMD();
            rpl.setPeriodo(periodos.get(posFila - 1));
            rpl.setNombre_rol(frmRolPer.getTxtNombreRol().getText());
            if (editar) {
                rpl.setId_rol(idRolPrd);
                RPLBD.editarRolPeriodo(rpl);
                JOptionPane.showMessageDialog(null, "Datos editados correctamente");
            } else {
                if (RPLBD.InsertarRol(rpl)) {
                    JOptionPane.showMessageDialog(null, "Datos grabados correctamente");
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
        frmRolPer.getCmbPeriodoLectivo().setSelectedItem(rp.getPeriodo().getNombre());
    }
}
