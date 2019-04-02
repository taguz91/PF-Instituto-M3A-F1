package controlador.Libraries;

import java.awt.Cursor;
import java.io.File;
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

    private static Thread thread = null;
    private static final Cursor LOAD_CURSOR;
    private static final Cursor DEFAULT_CURSOR;

    static {
        LOAD_CURSOR = new Cursor(Cursor.WAIT_CURSOR);
        DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
    }

    public static void centerFrame(JInternalFrame view, JDesktopPane desktop) {

        int deskWidth = (desktop.getWidth() / 2) - (view.getWidth() / 2);
        int deskHeight = (desktop.getHeight() / 2) - (view.getHeight() / 2);

        if (desktop.getHeight() < 500) {
            deskHeight = 0;
        }

        view.setLocation(deskWidth, deskHeight);

    }

    public static String getProjectPath() {
        String path = new File(".").getAbsolutePath();
        return path.substring(0, path.length() - 1);
    }

    public static void setTextInLabel(JLabel label, String text, long time) {
        thread = new Thread() {
            @Override
            public void run() {

                label.setText(text);

                try {
                    sleep(time * 1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Effects.class.getName()).log(Level.SEVERE, null, ex);
                }

                label.setText("");
            }
        };
        thread.start();
    }

    public static void setLoadCursor(JComponent view) {
        view.setCursor(LOAD_CURSOR);
    }

    public static void setDefaultCursor(JComponent view) {
        view.setCursor(DEFAULT_CURSOR);
    }

}
