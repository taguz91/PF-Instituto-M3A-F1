package controlador.Libraries;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.BiFunction;
import javax.swing.JTable;
import modelo.ConnDBPool;
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
public final class Middlewares {

    private static ConnDBPool pool;
    private static Connection conn;

    static {
        pool = new ConnDBPool();
    }

    /**
     *
     * @param path direccion del reporte
     * @param title Titulo de la ventana del ReporViewer
     * @param params
     */
    public static void generarReporte(URL path, String title, Map params) {
        try {

            JasperReport jasper = (JasperReport) JRLoader.loadObject(path);
            conn = pool.getConnection();
            JasperPrint print = JasperFillManager.fillReport(jasper, params, conn);

            JasperViewer view = new JasperViewer(print, false);

            view.setTitle(title);
            view.setVisible(true);

        } catch (JRException ex) {
            System.out.println(ex.getMessage());
        } finally {
            pool.close(conn);
        }
    }

    public static String getProjectPath() {
        String path = new File(".").getAbsolutePath();
        return path.substring(0, path.length() - 1);
    }

    public static double conversor(String texto) {
        if (texto.isEmpty()) {
            texto = "99999";
        }
        if (texto.equalsIgnoreCase(".") || texto.equalsIgnoreCase(",")) {
            texto = "0";
        }
        return Math.round(Double.valueOf(texto) * 10) / 10d;
    }

    public static String capitalize(String texto) {
        if (texto.length() > 1) {
            return texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase();
        } else {
            return texto.toUpperCase();
        }
    }

    public static BiFunction<JTable, String, Integer> getNombre = (tabla, nombre) -> {
        return tabla.getColumnModel().getColumnIndex(nombre);
    };

    public static String simpleDateFormat(LocalDateTime date) {
        return date.getDayOfWeek().getValue() + "-" + date.getMonth().getValue() + "-" + date.getYear();

    }

}
