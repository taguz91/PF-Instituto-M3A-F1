package controlador.usuario.Roles.forms;

import controlador.Libraries.Effects;
import controlador.principal.VtnPrincipalCTR;
import controlador.usuario.Roles.VtnRolCTR;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import utils.CONS;

/**
 *
 * @author MrRainx
 */
public class FrmRolAdd extends AbstractForm {

    public FrmRolAdd(VtnPrincipalCTR desktop, VtnRolCTR vtnPadre) {
        super(desktop, vtnPadre);
    }

    @Override
    protected void btnGuardar(ActionEvent e) {
        if (!vista.getTxtNombre().getText().isEmpty()) {

            if (setObj().insertar()) {
                String message = "SE HA AGREGADO EL ROL: " + modelo.getNombre();
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
