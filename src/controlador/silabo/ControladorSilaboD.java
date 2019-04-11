/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo;

import javax.swing.JOptionPane;
import modelo.silabo.SilaboBD;

/**
 *
 * @author Andres Ullauri
 */
public class ControladorSilaboD {

    private SilaboBD silabo;

    public ControladorSilaboD(SilaboBD silabo) {
        this.silabo = silabo;
    }

    public void iniciarControlador() {
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar este silabo?", "Eliminar", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Silabo eliminado correctamente");

        }
    }
}
