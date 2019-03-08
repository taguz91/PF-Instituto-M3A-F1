package modelo.validaciones;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Johnny
 */
public class TxtVTelefono extends KeyAdapter {

    private final JTextField txt;
    private final JLabel lbl;
    private String ingreso;

    public TxtVTelefono(JTextField txt, JLabel lbl) {
        this.txt = txt;
        this.lbl = lbl;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        ingreso = txt.getText().trim();

        if (e.getKeyCode() != 10 && e.getKeyCode() != 127 && ingreso.length() > 0) {
            if (!Validar.esTelefono(ingreso)) {
                txt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
                lbl.setVisible(true);
            } else {
                txt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
                lbl.setVisible(false);
            }
        }
    }
}
