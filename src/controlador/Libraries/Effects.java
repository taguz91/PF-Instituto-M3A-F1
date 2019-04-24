package controlador.Libraries;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

/**
 *
 * @author MrRainx
 */
public class Effects {

    public static void colorHover(JComponent button, Color colorEnter, Color colorExit) {

        button.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(colorEnter);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(colorExit);
            }

        });

    }

    public static void letterHover(JComponent button, Color colorEnter, Color colorExit) {
        button.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(new Color(255, 255, 255));
                button.setFont(new java.awt.Font("Arial", 1, 16));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(new Color(204, 204, 204));
                button.setFont(new java.awt.Font("Arial", 1, 14));
            }

        });

    }

    public static void moveableFrame(JFrame frame) {

        MouseAdapter mouseEvent = new MouseAdapter() {

            private Point mouseDownComCoords = null;

            @Override
            public void mouseReleased(MouseEvent e) {
                mouseDownComCoords = null;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                mouseDownComCoords = e.getPoint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                frame.setLocation(currCoords.x - mouseDownComCoords.x, currCoords.y - mouseDownComCoords.y);
            }

        };

        frame.addMouseListener(mouseEvent);
        frame.addMouseMotionListener(mouseEvent);

    }

    public static void exit(JComponent button) {

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }

        });
    }

    public static void minimize(JComponent button, JFrame frame) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.setExtendedState(JFrame.ICONIFIED);
            }

        });
    }

    public static void disponse(JComponent button, JFrame disFrame) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                disFrame.dispose();
            }

        });
    }

    public static void btnHover(JComponent button, JLabel target, Color enterColor, Color exitColor) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                target.setBackground(enterColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                target.setBackground(exitColor);
            }
        });
    }

    public static void setTextInLabel(JLabel component, String text, int time) {

        new Thread(() -> {
            try {
                component.setText(text);
                sleep(time * 1000);
                component.setText("");
            } catch (InterruptedException ex) {
                Logger.getLogger(Middlewares.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();

    }

    public static void centerFrame(JInternalFrame view, JDesktopPane desktop) {

        int deskWidth = (desktop.getWidth() / 2) - (view.getWidth() / 2);
        int deskHeight = (desktop.getHeight() / 2) - (view.getHeight() / 2);

        if (desktop.getHeight() < 500) {
            deskHeight = 0;
        }
        view.setLocation(deskWidth, deskHeight);
    }

    public static void setTextInLabelWithColor(JLabel component, String text, int time, Color color) {
        new Thread(() -> {
            try {
                component.setForeground(color);
                component.setText(text);
                sleep(time * 1000);
                component.setText("");
            } catch (InterruptedException ex) {
                Logger.getLogger(Middlewares.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }

    public static void addInDesktopPane(JInternalFrame component, JDesktopPane desktop) {
        try {
            desktop.add(component);
            component.setSelected(true);
            component.setVisible(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Middlewares.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private static final Cursor LOAD_CURSOR;
    private static final Cursor DEFAULT_CURSOR;

    public static Color SUCCESS_COLOR = new Color(12, 166, 15);

    public static Color ERROR_COLOR = new Color(204, 0, 0);

    static {
        LOAD_CURSOR = new Cursor(Cursor.WAIT_CURSOR);
        DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
    }

    public static void setLoadCursor(JComponent view) {
        view.setCursor(LOAD_CURSOR);
    }

    public static void setDefaultCursor(JComponent view) {
        view.setCursor(DEFAULT_CURSOR);
    }

    public static void setLoadCursorInWindow(Container view) {
        view.setCursor(LOAD_CURSOR);
    }

    public static void setDefaultCursorInWindow(Container view) {
        view.setCursor(DEFAULT_CURSOR);
    }

    public static void bugProcessor(Exception e, JComponent view) {
        if (e instanceof NullPointerException) {
        } else {
            System.out.println(e.getMessage());
            setDefaultCursor(view);
        }
    }

    public static void bugProcessor(JComponent view) {
        setDefaultCursor(view);
    }
}
