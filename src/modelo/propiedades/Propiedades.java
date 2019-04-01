/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.propiedades;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author MrRainx
 */
public class Propiedades {

    private static Properties config;
    private static FileInputStream configInput;
    private static FileOutputStream configOutput;
    private final static String PATH = "configs/configuracion.properties";
    private static File archivo;

    static {
        config = new Properties();
        archivo = new File(PATH);
    }

    public static boolean verificador() {
        return archivo.exists();
    }

    public static void setPropertyValue(String propiedad, String valor) {
        
        try {
            configOutput = new FileOutputStream(archivo);
            config.setProperty(propiedad, valor);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error guardando configuración\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
