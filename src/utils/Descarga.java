package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.JOptionPane;

/**
 *
 * @author gus
 */
public class Descarga {

    public static void excel(
            String nombreArchivo,
            String url,
            String error
    ) {
        
        new Thread(() -> {
            File carpeta = new File("excel/");
            if (!carpeta.exists()) {
                if (carpeta.mkdir()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Creamos la carpeta excel "
                            + "en la que guardamos las "
                            + "descargas de reportes."
                    );
                }
            }
            
            File fil = new File("excel/" + nombreArchivo + ".xlsx");
            try {
                URLConnection conn = new URL(
                        CONS.URL_API + url
                ).openConnection();
                conn.connect();
                try (InputStream i = conn.getInputStream();
                        OutputStream o = new FileOutputStream(fil)) {
                    int b = 0;
                    while (b != -1) {
                        b = i.read();
                        if (b != -1) {
                            o.write(b);
                        }
                    }
                    o.close();
                    JOptionPane.showMessageDialog(
                            null,
                            "Guardamos el excel, puede encontrarlo en "
                            + "la carpeta excel " + nombreArchivo
                    );
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                        null,
                        "No pudimos conectarnos para "
                        + "realizar la descarga. \n"
                        + error
                        + e.getMessage());
            }
        }).start();
    }

    public static void excelWithPost(
            String nombreArchivo,
            String url,
            String JSON,
            String error
    ) {
        new Thread(() -> {
            File carpeta = new File("excel/");
            if (!carpeta.exists()) {
                if (carpeta.mkdir()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Creamos la carpeta excel "
                            + "en la que guardamos las "
                            + "descargas de reportes."
                    );
                }
            }

            File fil = new File(nombreArchivo + ".xlsx");
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(
                        CONS.URL_API + url
                ).openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-type", "application/json");
                conn.setRequestProperty("Accept", "*/*");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                OutputStream wr = conn.getOutputStream();
                byte[] input = JSON.getBytes("utf-8");
                wr.write(input, 0, input.length);
                conn.connect();
                try (InputStream i = conn.getInputStream();
                        OutputStream o = new FileOutputStream(fil)) {
                    int b = 0;
                    while (b != -1) {
                        b = i.read();
                        if (b != -1) {
                            o.write(b);
                        }
                    }
                    o.close();
                    wr.close();
                    JOptionPane.showMessageDialog(
                            null,
                            "Guardamos el excel, puede encontrarlo en "
                            + "la carpeta excel " + nombreArchivo
                    );
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                        null,
                        "No pudimos conectarnos para "
                        + "realizar la descarga. \n"
                        + error
                        + e.getMessage());
            }
        }).start();
    }

}
