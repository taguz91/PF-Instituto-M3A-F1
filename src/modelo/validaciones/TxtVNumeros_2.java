
package modelo.validaciones;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class TxtVNumeros_2 extends KeyAdapter{
    
    private final JTextField txt;
    private final JLabel lbl;
    private String palabra;

    public TxtVNumeros_2(JTextField txt) {
        this.txt = txt;
        this.lbl = null;
    }

    public TxtVNumeros_2(JTextField txt, JLabel lbl) {
        this.txt = txt;
        this.lbl = lbl;
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        palabra = txt.getText().trim();

        if (e.getKeyCode() != 10 && e.getKeyCode() != 127 && palabra.length() > 0) {
            txt.setSize(txt.getWidth(), 20);
            txt.setPreferredSize(new Dimension(txt.getWidth(), 20));
            if (!Validar.esNumeros(palabra)) {
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
        palabra = txt.getText().trim();
        char car = e.getKeyChar();
        if (!Validar.esNumeros(car + "")) {
            e.consume();
        }
        if (palabra != null) {
            if (palabra.length() >= 3) {
                e.consume();
            }
        }
    }
    
}
