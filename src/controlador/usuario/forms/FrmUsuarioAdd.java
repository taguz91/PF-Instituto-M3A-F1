package controlador.usuario.forms;

import controlador.usuario.Roles.forms.FrmAsignarRolCTR;
import controlador.usuario.VtnUsuarioCTR;
import modelo.usuario.RolesDelUsuarioBD;
import vista.principal.VtnPrincipal;
import vista.usuario.FrmAsignarRoles;

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

            FrmAsignarRolCTR form = new FrmAsignarRolCTR(desktop, new FrmAsignarRoles(), new RolesDelUsuarioBD(), getObj(), "Asignar-Roles-Usuario");
            form.Init();
            vista.dispose();
        }
    }

}
