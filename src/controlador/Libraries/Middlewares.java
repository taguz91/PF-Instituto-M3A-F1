package controlador.Libraries;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import static java.lang.Thread.sleep;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import modelo.ResourceManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author MrRainx
 */
public class Middlewares {

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

    /**
     *
     * @param path direccion del reporte
     * @param QUERY Sentencia SQL con la que se generara el reporte
     * @param tituloVentana Titulo de la ventana del ReporViewer
     */
    public static void generarReporteDefault(String path, String QUERY, String tituloVentana) {
        try {

            Map parameter = new HashMap();

            parameter.put("consulta", QUERY);

            JasperReport jasper = (JasperReport) JRLoader.loadObjectFromFile(path);

            JasperPrint print = JasperFillManager.fillReport(jasper, parameter, ResourceManager.getConnection());

            JasperViewer view = new JasperViewer(print, false);

            view.setTitle(tituloVentana);

            view.setVisible(true);

        } catch (JRException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     *
     * @param path direccion del reporte
     * @param tituloVentana Titulo de la ventana del ReporViewer
     * @param parametros
     */
    public static void generarReporte(String path, String tituloVentana, Map parametros) {
        try {

            JasperReport jasper = (JasperReport) JRLoader.loadObjectFromFile(path);

            JasperPrint print = JasperFillManager.fillReport(jasper, parametros, ResourceManager.getConnection());

            JasperViewer view = new JasperViewer(print, false);

            view.setTitle(tituloVentana);

            view.setVisible(true);

        } catch (JRException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
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

    public static String getProjectPath() {
        String path = new File(".").getAbsolutePath();
        return path.substring(0, path.length() - 1);
    }

    public static void placeHolder(JLabel component, String placeholderText) {

        component.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {

                if (component.getText().equalsIgnoreCase(placeholderText)) {
                    component.setText("");
                }

            }

            @Override
            public void focusLost(FocusEvent e) {
                if (placeholderText.equalsIgnoreCase(component.getText())) {
                    //AGREGAR COLORES
                }
            }
        });

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
