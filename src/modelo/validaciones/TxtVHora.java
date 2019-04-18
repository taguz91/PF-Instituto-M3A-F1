package modelo.validaciones;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Johnny
 */
public class TxtVHora extends KeyAdapter {

    private final JTextField txt;
    private final JLabel lbl;
    private String ingreso;
    private char car;

    public TxtVHora(JTextField txt) {
        this.txt = txt;
        this.lbl = null;
    }

    public TxtVHora(JTextField txt, JLabel lbl) {
        this.txt = txt;
        this.lbl = lbl;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        ingreso = txt.getText().trim();

        if (e.getKeyCode() != 10 && e.getKeyCode() != 127 && ingreso.length() > 0) {
            txt.setSize(txt.getWidth(), 20);
            txt.setPreferredSize(new Dimension(txt.getWidth(), 20));
            if (!Validar.esHora(ingreso)) {
                txt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
                if (lbl != null) {
                    lbl.setVisible(true);
                }
            } else {
                txt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
                if (lbl != null) {
                    lbl.setVisible(false);
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        car = e.getKeyChar();
        if (!Validar.esHora(car + "") && car != ':' && (car < '0' && car > '9')) {
            e.consume();
        }
    }
}
