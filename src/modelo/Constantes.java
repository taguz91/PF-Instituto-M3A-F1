package modelo;

import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author alumno
 */
public class Constantes {

    //Direccion de las propiedades de base de datos
    public static final String BD_DIR = "configuracion.properties";
    //Direccion de las propiedades de version  
    public static final String V_DIR = "version.properties";

    //Obtenemos la direccion 
    public static String getDir() {
        File dir = new File("./");
        return dir.getAbsolutePath();
    }

    //Para ejecutar jars 
    public static void ejecutarJAR(String nombre) {
        //Corremos el programa desde consola
        try {
            String cmd = nombre + ".jar";
            File jar = new File(cmd);
            System.out.println(cmd);
            String cmda = "java -jar " + jar.getAbsolutePath();
            System.out.println(cmda);
            Runtime.getRuntime().exec(cmda);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Permisos denegados \n"
                    + "ejecute el programa manualmente.");
        }

    }

}
