package controlador.Libraries;

import javax.swing.JDesktopPane;

import javax.swing.JInternalFrame;

/**
 *
 * @author MrRainx
 */
public class Effects {

    public static void centerFrame(JInternalFrame view, JDesktopPane desktop) {

        int deskWidth = (desktop.getWidth() / 2) - (view.getWidth() / 2);
        int deskHeight = (desktop.getHeight() / 2) - (view.getHeight() / 2);

        if (desktop.getHeight() < 500) {
            deskHeight = 0;
        }

        view.setLocation(deskWidth, deskHeight);

    }

}
