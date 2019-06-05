package controlador.usuario.Roles.forms;

import controlador.Libraries.Effects;
import controlador.Libraries.Validaciones;
import controlador.principal.VtnPrincipalCTR;
import controlador.usuario.Roles.VtnRolCTR;
import java.awt.event.ActionEvent;
import modelo.usuario.RolBD;
import vista.usuario.FrmRol;

/**
 *
 * @author MrRainx
 */
public abstract class AbstractForm {

    protected final VtnPrincipalCTR desktop;
    protected final VtnRolCTR vtnPadre;
    protected FrmRol vista;
    protected RolBD modelo;

    public AbstractForm(VtnPrincipalCTR desktop, VtnRolCTR vtnPadre) {
        this.desktop = desktop;
        this.vtnPadre = vtnPadre;
        this.vista = new FrmRol();
    }

    //Inits
    public void Init() {
        Effects.addInDesktopPane(vista, desktop.getVtnPrin().getDpnlPrincipal());
        InitEventos();
    }

    public void InitEventos() {
        vista.getTxtNombre().addKeyListener(Validaciones.validarSoloLetrasYnumeros());
        vista.getBtnCancelar().addActionListener(e -> vista.dispose());
        vista.getBtnGuardar().addActionListener(e -> btnGuardar(e));
    }

    //Metodos de apoyo
    protected RolBD setObj() {
        modelo = new RolBD();
        modelo.setNombre(vista.getTxtNombre().getText());
        modelo.setObservaciones(vista.getTxtObservaciones().getText());
        return modelo;
    }

    protected abstract void btnGuardar(ActionEvent e);
}
