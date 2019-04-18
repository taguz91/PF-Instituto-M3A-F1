/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.propiedades;

import controlador.Libraries.Middlewares;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author MrRainx
 */
public class Propiedades {

    private static final Properties config;
    private static String PATH = "configuracion.properties";
    private static final File archivo;

    static {
        config = new Properties();
        PATH = Middlewares.getProjectPath() + PATH;
        archivo = new File(PATH);
    }

    private static void setDefault() {

        config.setProperty("ip", "35.193.226.187");
        config.setProperty("port", "5432");
        config.setProperty("database", "BDinsta");

        try {
            config.store(new FileWriter(archivo), "comentario");
        } catch (IOException ex) {
            Logger.getLogger(Propiedades.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Map<Object, Object> loadProperties() {
        if (archivo.exists()) {
            try {
                config.load(new FileReader(archivo));
                if (config.size() != 3) {
                    setDefault();
                    config.load(new FileReader(archivo));
                }
            } catch (FileNotFoundException ex) {
                setDefault();
                Logger.getLogger(Propiedades.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                setDefault();
                Logger.getLogger(Propiedades.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            setDefault();
            Map<Object, Object> properties = loadProperties();
            return properties;
        }

        return config;
    }

    public static String getPropertie(String propertie) {
        String value = "";

        Map<Object, Object> map = loadProperties().entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(propertie.toLowerCase()))
                .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));

        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            value = (String) entry.getValue();
        }

        return value;
    }

    public static String getUserProp(String propertie) {
        String value = "";

        Properties properties = new Properties();

        try {
            properties.load(new FileReader("user.properties"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Propiedades.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Propiedades.class.getName()).log(Level.SEVERE, null, ex);
        }

        Map<Object, Object> map = properties.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(propertie.toLowerCase()))
                .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));

        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            value = (String) entry.getValue();
        }

        return value;
    }

    public static void generateUserProperties(Map<Object, Object> properties) {
        Properties file = new Properties();

        properties
                .entrySet()
                .stream()
                .forEach(entry -> {
                    file.setProperty(entry.getKey().toString(), entry.getValue().toString());
                });

        try {
            file.store(new FileWriter("user.properties"), "PROPIEDADES DEL USUARIO");
        } catch (IOException ex) {
            Logger.getLogger(Propiedades.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
