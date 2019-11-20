package controlador.usuario.Roles.forms;

import controlador.Libraries.Effects;
import controlador.principal.VtnPrincipalCTR;
import controlador.usuario.Roles.VtnRolCTR;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import utils.CONS;
import modelo.usuario.RolBD;

/**
 *
 * @author MrRainx
 */
public class FrmRolEdit extends AbstractForm {

    private int pk;

    public FrmRolEdit(VtnPrincipalCTR desktop, VtnRolCTR vtnPadre) {
        super(desktop, vtnPadre);
    }

    public void setModelo(RolBD modelo) {
        vista.setTitle("Editar Rol");
        this.modelo = modelo;
        this.pk = modelo.getId();
        vista.getTxtNombre().setText(modelo.getNombre());
        vista.getTxtObservaciones().setText(modelo.getObservaciones());
    }

    @Override
    protected void btnGuardar(ActionEvent e) {

        if (!vista.getTxtNombre().getText().isEmpty()) {
            if (setObj().editar(pk)) {
                String message = "SE HA EDITADO EL ROL: " + modelo.getNombre();
                Effects.setTextInLabel(vtnPadre.getVista().getLblEstado(), message, CONS.SUCCESS_COLOR, 3);
                vtnPadre.cargarTabla();
                vista.dispose();
            } else {
                Effects.setTextInLabel(vtnPadre.getVista().getLblEstado(), "HA OCURRIDO UN PROBLEMA", CONS.ERROR_COLOR, 3);
            }

        } else {
            JOptionPane.showMessageDialog(vista, "RELLENE EL CAMPO DE NOMBRE!!");
        }

    }

}
