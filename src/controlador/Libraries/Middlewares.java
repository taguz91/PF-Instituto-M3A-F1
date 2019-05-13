package controlador.Libraries;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import javax.swing.JOptionPane;
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
     * @param QUERY Sentencia SQL con la que se generara el reporte
     * @param tituloVentana Titulo de la ventana del ReporViewer
     */
    public static void generarReporteDefault(String path, String QUERY, String tituloVentana) {
        try {

            Map parameter = new HashMap();

            parameter.put("consulta", QUERY);

            JasperReport jasper = (JasperReport) JRLoader.loadObjectFromFile(path);

            conn = pool.getConnection();
            JasperPrint print = JasperFillManager.fillReport(jasper, parameter, conn);

            JasperViewer view = new JasperViewer(print, false);

            view.setTitle(tituloVentana);

            view.setVisible(true);

        } catch (JRException ex) {
            System.out.println(ex.getMessage());
        } finally {
            pool.close(conn);
        }
    }

    /**
     *
     * @param path direccion del reporte
     * @param tituloVentana Titulo de la ventana del ReporViewer
     * @param parametros
     */
    static JasperReport prueba;

    public static void generarReporte(URL path, String tituloVentana, Map parametros) {
        try {
//            (JasperReport) JRLoader.loadObject();

            JasperReport jasper = (JasperReport) JRLoader.loadObject(path);

            System.out.println("PATH ---------->" + path);
            conn = pool.getConnection();
            JasperPrint print = JasperFillManager.fillReport(jasper, parametros, conn);

            JasperViewer view = new JasperViewer(print, false);

            view.setTitle(tituloVentana);

            view.setVisible(true);

        } catch (JRException ex) {

            JOptionPane.showMessageDialog(null, ex.getMessage());
            JOptionPane.showMessageDialog(null, "PATH\n" + path);
            JOptionPane.showMessageDialog(null, "PATH PROYECTO" + getProjectPath());
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

    public static void destruirVariables(Object... variables) {
        Arrays.asList(variables).forEach(obj -> {
            obj = null;
        });
        System.gc();
        System.out.println("======================");
        System.out.println("*VARAIBLES DESTRUIDAS*");
        System.out.println("======================");
    }

}
