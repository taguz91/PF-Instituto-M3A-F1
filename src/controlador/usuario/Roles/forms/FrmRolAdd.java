package controlador.usuario.Roles.forms;

import controlador.Libraries.Effects;
import controlador.usuario.Roles.VtnRolCTR;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import vista.principal.VtnPrincipal;

/**
 *
 * @author MrRainx
 */
public class FrmRolAdd extends AbstractForm {

    public FrmRolAdd(VtnPrincipal desktop, VtnRolCTR vtnPadre) {
        super(desktop, vtnPadre);
    }

    //INICIADORES
    @Override
    protected void btnGuardar(ActionEvent e) {
        if (!vista.getTxtNombre().getText().isEmpty()) {

            if (setObj().insertar()) {
                String message = "SE HA AGREGADO EL ROL: " + modelo.getNombre();
                Effects.setTextInLabel(vtnPadre.getVista().getLblEstado(), message, Effects.SUCCESS_COLOR, 3);
                vista.dispose();
                destruidVariables();
            }

        } else {
            JOptionPane.showMessageDialog(vista, "RELLENE EL CAMPO DE NOMBRE!!");
        }
    }
}
