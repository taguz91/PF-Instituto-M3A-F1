/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.propiedades;

import controlador.Libraries.Effects;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.effect.Effect;
import javax.swing.JOptionPane;

/**
 *
 * @author MrRainx
 */
public class Propiedades {

    private static Properties config;
    private static String PATH = "configuracion.properties";
    private static File archivo;

    static {
        config = new Properties();
        PATH = Effects.getProjectPath() + PATH;
        archivo = new File(PATH);
    }

    public static void setDefault() {

        config.setProperty("IP_ADRESS", "jdbc:postgresql://35.193.226.187:5432/BDinsta");

        try {
            config.store(new FileWriter(archivo), "comentario");
        } catch (IOException ex) {
            Logger.getLogger(Propiedades.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String loadIP() {
        if (archivo.exists()) {
            try {
                config.load(new FileReader(archivo));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Propiedades.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Propiedades.class.getName()).log(Level.SEVERE, null, ex);
            }
            String IP = config.getProperty("IP_ADRESS");

            if (IP == null) {
                setDefault();
                loadIP();
            } else {
                return IP;
            }

        } else {
            setDefault();
            loadIP();
        }

        return null;
    }

}
