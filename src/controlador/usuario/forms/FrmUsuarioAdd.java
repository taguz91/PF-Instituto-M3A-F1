package controlador.usuario.forms;

import controlador.Libraries.Middlewares;
import controlador.usuario.VtnUsuarioCTR;
import modelo.usuario.UsuarioBD;
import vista.principal.VtnPrincipal;

/**
 *
 * @author MrRainx
 */
public class FrmUsuarioAdd extends AbstracForm {

    public FrmUsuarioAdd(VtnPrincipal desktop, VtnUsuarioCTR vtnPadre) {
        super(desktop, vtnPadre);
    }

    @Override
    public void guardar() {
        if (validarFormulario()) {
            if (setObj().insertar()) {
                vtnPadre.cargarTabla(UsuarioBD.SelectAll());
                Middlewares.setTextInLabel(vtnPadre.getVista().getLblEstado(), "SE HA ", 3);
            }

        }
    }

}
