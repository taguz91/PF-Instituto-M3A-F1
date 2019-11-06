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
    private final RolPeriodoBD rolDoc;
    private ArrayList<PeriodoLectivoMD> periodos;
    private final PeriodoLectivoBD prd;
    private boolean editar = false;
    private int idRolPrd;

    public FrmRolPeriodoCTR(FrmRolesPeriodos frmRolPer, VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
        this.frmRolPer = frmRolPer;
        this.rolDoc = new RolPeriodoBD(ctrPrin.getConecta());
        this.prd = new PeriodoLectivoBD(ctrPrin.getConecta());
    }

    public void iniciar() {
        cargarCmbPrdLectivo();
        frmRolPer.getLbl_error_roles().setVisible(false);
        iniciarValidaciones();
        frmRolPer.getBtnGuardar().addActionListener(e -> insertarRolesPeriodos());

        ctrPrin.agregarVtn(frmRolPer);
    }

    private void cargarCmbPrdLectivo() {
        periodos = prd.cargarPrdParaCmbFrm();
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
        frmRolPer.getCmbPeriodoLectivo().setSelectedItem(rp.getPeriodo().getNombre());
    }
}
