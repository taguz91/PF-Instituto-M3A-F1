package controlador.Libraries;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author MrRainx
 */
public class Effects {

    private static final Cursor LOAD_CURSOR;
    private static final Cursor DEFAULT_CURSOR;

    public static Color SUCCESS_COLOR = new Color(10, 186, 52);

    public static Color ERROR_COLOR = new Color(37, 107, 187);

    static {
        LOAD_CURSOR = new Cursor(Cursor.WAIT_CURSOR);

        DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
    }

    public static void addInDesktopPane(JInternalFrame component, JDesktopPane desktop) {
        try {
            centerFrame(component, desktop);
            desktop.add(component);
            component.setSelected(true);
            component.setVisible(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Middlewares.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void centerFrame(JInternalFrame view, JDesktopPane desktop) {

        int deskWidth = (desktop.getWidth() / 2) - (view.getWidth() / 2);
        int deskHeight = (desktop.getHeight() / 2) - (view.getHeight() / 2);

        if (desktop.getHeight() < 500) {
            deskHeight = 0;
        }
        view.setLocation(deskWidth, deskHeight);
    }

    public static void setTextInLabel(JLabel component, String message, Color color, int time) {
        if (color != null) {
            component.setForeground(color);
        }
        component.setText(message);
        setText(component, time);
    }

    public static void setText(JLabel component, int time) {
        Timer task = new Timer(time * 1000, e -> {
            component.setText("");
        });
        task.setRepeats(false);
        task.start();
    }

    public static void setLoadCursor(Container view) {
        view.setCursor(LOAD_CURSOR);
    }

    public static void setDefaultCursor(Container view) {
        view.setCursor(DEFAULT_CURSOR);
    }
}