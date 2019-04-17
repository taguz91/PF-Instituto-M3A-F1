package controlador.Libraries;

import java.awt.event.KeyAdapter;
import javax.swing.JComponent;

/**
 *
 * @author MrRainx
 */
public class Validaciones {

    public static boolean isInt(String Number) {
        return Number.matches("^[0-9]");
    }

    public static boolean isDecimal(String Number) {
        return Number.matches("^[0-9]*+[.]{0,1}+[0-9]*");
    }

    public static void validardarNumeroDecimalEnTxtField(JComponent component) {
        component.addKeyListener(new KeyAdapter() {
            
        });
    }

}
