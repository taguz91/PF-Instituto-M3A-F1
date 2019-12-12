package controlador.usuario;

import controlador.Libraries.Effects;
import controlador.Libraries.ImgLib;
import controlador.principal.VtnPrincipalCTR;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import utils.CONS;
import modelo.usuario.UsuarioBD;
import modelo.usuario.UsuarioMD;
import vista.usuario.VtnPerfilUsuario;

public class VtnPerfilUsuarioCTR {

    private VtnPerfilUsuario vista;
    private final UsuarioBD CONN = UsuarioBD.single();
    private UsuarioMD modelo;
    private VtnPrincipalCTR desktop;

    public VtnPerfilUsuarioCTR(VtnPrincipalCTR desktop) {
        this.vista = new VtnPerfilUsuario();
        this.modelo = CONS.USUARIO;
        this.desktop = desktop;
    }

    public VtnPerfilUsuarioCTR() {
    }

    /*
        INIT
     */
    public void Init() {

        Effects.addInDesktopPane(vista, desktop.getVtnPrin().getDpnlPrincipal());

        vista.getLblUsername().setText(modelo.getUsername());
        vista.getLblNombres().setText(modelo.getPersona().getPrimerNombre() + "  " + modelo.getPersona().getSegundoNombre());
        vista.getLblApellidos().setText(modelo.getPersona().getPrimerApellido() + "  " + modelo.getPersona().getSegundoApellido());

        vista.getLblRol().setText(CONS.ROL.getNombre());

        ImgLib.setImageInLabel(CONS.USUARIO.getPersona().getFoto(), vista.getLblFoto());

        InitEventos();

    }

    private void InitEventos() {

        vista.getBtnCancelar().addActionListener(e -> vista.dispose());
        vista.getBtnGuardar().addActionListener(e -> btnGuardarActionPerformance(e));
        vista.getBtnCambiarContrasena().addActionListener(e -> btnCambiarContrasenaActionPerformance(e));
        vista.getBtnPermisos().addActionListener(e -> CONS.refreshPermisos());

    }

    /*
        EVENTOS
     */
    private void btnCambiarContrasenaActionPerformance(ActionEvent e) {
        vista.getTxtContrasena().setEnabled(true);
        vista.getTxtContrasena().setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
    }

    private void btnGuardarActionPerformance(ActionEvent e) {

        String password;
        password = vista.getTxtContrasena().getText();
        modelo.setPassword(password);

        if (vista.getTxtContrasena().getText().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Campo Vacio...");
        } else {

            if (password.length() < 4) {
                JOptionPane.showMessageDialog(vista, "La contraseña debe ser mayor o igual a 4 caracteres");
            } else {

                if (CONN.editar(modelo, modelo.getUsername())) {
                    JOptionPane.showMessageDialog(vista, "Contraseña Editada correctamente");
                    vista.getTxtContrasena().setText("");
                    vista.getTxtContrasena().setBorder(BorderFactory.createLineBorder(Color.GRAY));
                    vista.getTxtContrasena().setEnabled(false);
                }

            }

        }

    }

}
