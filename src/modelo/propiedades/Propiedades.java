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
    private static FileInputStream configInput;
    private static FileOutputStream configOutput;
    private static String PATH = "configuracion.properties";
    private static File archivo;

    static {
        config = new Properties();
        PATH = Effects.getProjectPath() + PATH;
    }

    public static boolean verificador() {
        return archivo.exists();
    }

    public static void setPropertyValue(String propiedad, String valor) {

        try {
            configOutput = new FileOutputStream(PATH);
            config.setProperty(propiedad, valor);

            configOutput.close();
        } catch (IOException ex) {
            Logger.getLogger(Propiedades.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void escribirPropiedades() {
        //            configOutput = new FileOutputStream(PATH);

        config.setProperty("IP_ADRESS", "35.193.226.187");

        try {
            config.store(new FileWriter(PATH), "comentario");
        } catch (IOException ex) {
            Logger.getLogger(Propiedades.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
