package controlador.Libraries;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;

/**
 *
 * @author MrRainx
 */
public class Effects {

    private static Thread thread = new Thread();

    public static void centerFrame(JInternalFrame view, JDesktopPane desktop) {

        int deskWidth = (desktop.getWidth() / 2) - (view.getWidth() / 2);
        int deskHeight = (desktop.getHeight() / 2) - (view.getHeight() / 2);

        if (desktop.getHeight() < 500) {
            deskHeight = 0;
        }

        view.setLocation(deskWidth, deskHeight);

    }

    public static void setTextInLabel(JLabel label, String text, long time) {

        thread = new Thread() {
            @Override
            public void run() {
                System.out.println("---------------------------------------->");
                label.setText(text);
            }

        };
        thread.start();

    }

}
