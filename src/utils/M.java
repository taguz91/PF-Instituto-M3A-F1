package utils;

import javax.swing.JOptionPane;

/**
 *
 * @author gus
 */
public class M {

    public static void errorMsg(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Error: ", JOptionPane.ERROR_MESSAGE);
    }

}
