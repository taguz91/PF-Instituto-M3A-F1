
package modelo.validaciones;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class TxtVNumeros_2 extends KeyAdapter{
    
    private final JTextField txt;
    private String palabra;

    public TxtVNumeros_2(JTextField txt) {
        this.txt = txt;
        
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
