/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.propiedades;

import controlador.Libraries.Middlewares;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
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

    private static Properties config;
    private static String PATH = "configuracion.properties";
    private static File archivo;

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
        try {
            
            return Files.lines(Paths.get("configuracion.properties"))
                    .filter(item -> item.contains("="))
                    .map(c -> c.split("="))
                    .collect(Collectors.toMap(s -> s[0], s -> s[1]));
        } catch (NoSuchFileException ex) {
            setDefault();
            return loadProperties();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }

    public static String getPropertie(String propertie) {
        return loadProperties().entrySet()
                .stream()
                .collect(Collectors.toMap(c -> c.getKey().toString(), c -> c.getValue().toString()))
                .entrySet()
                .stream()
                .filter(item -> item.getKey().equalsIgnoreCase(propertie))
                .map(c -> c.getValue())
                .findFirst()
                .orElse("");
    }
}
