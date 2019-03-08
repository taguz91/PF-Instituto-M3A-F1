package modelo.validaciones;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Johnny
 */
public class TxtValidarLetras extends KeyAdapter {

    //El campo que validaremos
    private final JTextField txt;
    //El error que mostraremos
    private final JLabel lbl;
    private String ingreso;

    public TxtValidarLetras(JTextField txt, JLabel lbl) {
        this.txt = txt;
        this.lbl = lbl;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        ingreso = txt.getText().trim();
        //es el codigo ASCCI de caracteres especiales
        //33 al 44 
        //58 al 64
        //91 AL 96
        //123 al 128
        //155 al 159
        //166 al 180
        //184 al 197
        //200 al 209
        //217 al 223
        //238 al 255
        ingreso = txt.getText().trim();
        //El 10 corresponde a un salto de linea  
        //El 8 corresponde a borrar
        //127 es para suprimir
        if (e.getKeyCode() != 10 && e.getKeyCode() != 127 && ingreso.length() > 0) {
            if (!Validar.esLetrasYNumeros(ingreso)) {
                /*JOptionPane.showMessageDialog(null, "No se permiten ingresar caracteres especiales.",
                        "Error", JOptionPane.WARNING_MESSAGE);*/

                txt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
                lbl.setVisible(true);
            } else {
                txt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
                lbl.setVisible(false);
            }
        }
    }

}
