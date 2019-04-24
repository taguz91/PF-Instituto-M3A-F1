package controlador.Libraries;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;
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

}
