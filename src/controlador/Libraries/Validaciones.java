package controlador.Libraries;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import javax.swing.text.JTextComponent;

/**
 *
 * @author MrRainx
 */
public class Validaciones {

    public static int NUMERO_ENTERO = 0;
    public static int NUMERO_DECIMAL = 1;
    public static int NUMERO_DECIMAL_LIMIT = 2;

    public static boolean isInt(String Number) {
        return Number.matches("^[0-9]*");
    }

    public static boolean isDecimal(String Number) {
        return Number.matches("^[0-9]*+[.]{0,1}+[0-9]*");
    }

    public static boolean isDecimalLimit(String number, int minimun, int maximun) {
        return number.matches("^[0-9]*+[.]{0,1}+[0-9]{" + minimun + "," + maximun + "}");
    }

    public static void validarJtextField(JTextComponent component, String errorMessage, JComponent previewComponent, int validationType) {
        component.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = component.getText();
                if (validationType == NUMERO_ENTERO) {

                    if (!isInt(text)) {
                        error(component, errorMessage, previewComponent);
                    }

                } else if (validationType == NUMERO_DECIMAL) {

                    if (!isDecimal(text)) {
                        error(component, errorMessage, previewComponent);
                    }

                }
            }
        });
    }

    public static void validarDecimalJtextField(JTextComponent component, String errorMessage, JComponent previewComponent, int mimun, int maximun) {
        component.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = component.getText();

                if (!isDecimalLimit(text, mimun, maximun)) {
                    error(component, errorMessage, previewComponent);
                }

            }
        });
    }

    private static void error(JTextComponent component, String errorMessage, JComponent previewComponent) {
        JOptionPane.showMessageDialog(previewComponent, errorMessage);
        component.setText("");
    }

}
