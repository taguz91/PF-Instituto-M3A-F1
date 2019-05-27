package controlador.usuario.Roles.forms;

import controlador.Libraries.Effects;
import controlador.Libraries.Validaciones;
import controlador.usuario.Roles.VtnRolCTR;
import java.awt.event.ActionEvent;
import modelo.usuario.RolBD;
import vista.principal.VtnPrincipal;
import vista.usuario.FrmRol;

/**
 *
 * @author MrRainx
 */
public abstract class AbstractForm {

    protected final VtnPrincipal desktop;
    protected final VtnRolCTR vtnPadre;
    protected FrmRol vista;
    protected RolBD modelo;

    public AbstractForm(VtnPrincipal desktop, VtnRolCTR vtnPadre) {
        this.desktop = desktop;
        this.vtnPadre = vtnPadre;
        this.vista = new FrmRol();
    }

    //Inits
    public synchronized void Init() {
        Effects.addInDesktopPane(vista, desktop.getDpnlPrincipal());
        InitEventos();
    }

    public void InitEventos() {
        vista.getTxtNombre().addKeyListener(Validaciones.validarSoloLetrasYnumeros());
        vista.getBtnCancelar().addActionListener(e -> {
            vista.dispose();
            destruidVariables();
        });
        vista.getBtnGuardar().addActionListener(e -> btnGuardar(e));
    }

    //Metodos de apoyo
    protected void destruidVariables() {
        vista = null;
        modelo = null;
        System.gc();
    }

    protected RolBD setObj() {
        modelo = new RolBD();
        modelo.setNombre(vista.getTxtNombre().getText());
        modelo.setObservaciones(vista.getTxtObservaciones().getText());
        return modelo;
    }

    protected abstract void btnGuardar(ActionEvent e);
}
