package controlador.usuario;

import controlador.Libraries.Effects;
import controlador.accesos.FrmAccesosDeRolCTR;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import modelo.CONS;
import modelo.usuario.UsuarioBD;
import vista.principal.VtnPrincipal;
import vista.usuario.VtnPerfilUsuario;

public class VtnPerfilUsuarioCTR {

    private VtnPerfilUsuario vista;
    private UsuarioBD modelo;
    private VtnPrincipal desktop;

    public VtnPerfilUsuarioCTR(VtnPrincipal desktop) {
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
        vista.show();
        desktop.getDpnlPrincipal().add(vista);
        Effects.centerFrame(vista, desktop.getDpnlPrincipal());
        System.out.println(modelo.getUsername());
        try {
            vista.setSelected(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(FrmAccesosDeRolCTR.class.getName()).log(Level.SEVERE, null, e);
        }

        vista.getLblUsername().setText(modelo.getUsername());
        vista.getLblNombres().setText(modelo.getPersona().getPrimerNombre() + "  " + modelo.getPersona().getSegundoNombre());
        vista.getLblApellidos().setText(modelo.getPersona().getPrimerApellido() + "  " + modelo.getPersona().getSegundoApellido());

        InitEventos();
    }

    private void InitEventos() {

        vista.getBtnCancelar().addActionListener(e -> btnCancelarActionPerformance(e));
        vista.getBtnGuardar().addActionListener(e -> btnGuardarActionPerformance(e));
        vista.getBtnCambiarContrasena().addActionListener(e -> btnCambiarContrasenaActionPerformance(e));

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
            JOptionPane.showMessageDialog(desktop, "Campo Vacio...");
        } else {

            if (password.length() < 4) {
                JOptionPane.showMessageDialog(desktop, "La contraseña debe ser mayor o igual a 4 caracteres");
            } else {

                if (modelo.editar(modelo.getUsername())) {
                    JOptionPane.showMessageDialog(desktop, "Contraseña Editada correctamente");
                    vista.getTxtContrasena().setText("");
                    vista.getTxtContrasena().setBorder(BorderFactory.createLineBorder(Color.GRAY));
                    vista.getTxtContrasena().setEnabled(false);
                }

            }

        }

    }

    private void btnCancelarActionPerformance(ActionEvent e) {
        vista.dispose();
    }

}
