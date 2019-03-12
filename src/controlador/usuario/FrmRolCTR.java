package controlador.usuario;

import controlador.Libraries.Effects;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.usuario.RolBD;
import vista.principal.VtnPrincipal;
import vista.usuario.FrmRol;

/**
 *
 * @author USUARIO
 */
public class FrmRolCTR {

    private final VtnPrincipal desktop;
    private final FrmRol vista;
    private final RolBD modelo;

    private final String Funcion;

    private int Pk = 0;

    public FrmRolCTR(VtnPrincipal desktop, FrmRol vista, RolBD modelo, String Funcion) {
        this.desktop = desktop;
        this.vista = vista;
        this.modelo = modelo;
        this.Funcion = Funcion;
    }

    //INICIADORES
    public void Init() {

        InitEventos();
        
        Effects.centerFrame(vista, desktop.getDpnlPrincipal());
        
        if (Funcion.equals("Editar")) {
            vista.setTitle("Editar Un Rol");
            Pk = modelo.getId();
            vista.getTxtNombre().setText(modelo.getNombre());

        } else {
            vista.setTitle("Agregar Un Rol");
        }

        vista.show();
        desktop.getDpnlPrincipal().add(vista);

        try {
            vista.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(FrmRolCTR.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void InitEventos() {
        if (Funcion.equals("Agregar")) {
            vista.getBtnGuardar().addActionListener(e -> btnAgregarActionPerformance(e));
        } else {
            vista.getBtnGuardar().addActionListener(e -> btnEditarActionPerformance(e));
        }
        vista.getBtnCancelar().addActionListener(e -> btnCancelarActionPerformance(e));
    }

    //METODOS DE APOYO
    private boolean validacion() {

        return !vista.getTxtNombre().getText().isEmpty();
    }

    private void setModelo() {
        modelo.setNombre(vista.getTxtNombre().getText());
    }

    //EVENTOS
    private void btnCancelarActionPerformance(ActionEvent e) {
        vista.dispose();
    }

    private void btnAgregarActionPerformance(ActionEvent e) {
        if (validacion()) {

            setModelo();

            if (modelo.insertar()) {
                JOptionPane.showMessageDialog(desktop, "ROL AGREGADO CORRECTAMENTE");
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(desktop, "YA EXISTE UN ROL CON ESTE NOMBRE");
            }
        } else {
            JOptionPane.showMessageDialog(desktop, "RELLENE CORRECTAMENTE EL FORMULARIO");
        }
    }

    private void btnEditarActionPerformance(ActionEvent e) {
        if (validacion()) {

            setModelo();

            if (modelo.editar(Pk)) {

                JOptionPane.showMessageDialog(desktop, "ROL EDITADO CORRECTAMENTE");

                vista.dispose();

            } else {
                JOptionPane.showMessageDialog(desktop, "YA EXISTE UN ROL CON ESTE NOMBRE");
            }

        } else {
            JOptionPane.showMessageDialog(desktop, "RELLENE CORRECTAMENTE EL FORMULARIO");
        }
    }

}
