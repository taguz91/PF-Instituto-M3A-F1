package modelo;

import java.io.File;
import java.util.concurrent.ForkJoinPool;
import modelo.usuario.RolBD;
import modelo.usuario.UsuarioBD;

/**
 *
 * @author alumno
 */
public class CONS {

    //Modo del proyecto 
    public static final boolean M_DESARROLLO = true;
    //Nombre de las propiedadesde la base de matos
    public static final String BD_DATABASE = "database", BD_IP = "ip", BD_PUERTO = "port";
    //Direccion de las propiedades de base de datos
    public static final String BD_DIR = "configuracion.properties";
    //Nombre de las propiedades la version
    public static final String V_AUTOR = "Autor", V_NOMBRE = "Nombre", V_VERSION = "Version",
            V_NOTAS = "Notas", V_FECHA = "Fecha";
    //Direccion de las propiedades de version  
    public static final String V_DIR = "version.properties";

    //Obtenemos la direccion 
    public static String getDir() {
        File dir = new File("./");
        return dir.getAbsolutePath();
    }

    public static UsuarioBD USUARIO;

    public static void setUsuario(UsuarioBD USUARIO) {
        CONS.USUARIO = USUARIO;
    }

    public static RolBD ROL;

    public static void setRol(RolBD ROL) {
        CONS.ROL = ROL;
    }

    private static ForkJoinPool threadPool;

    public static ForkJoinPool getPool(Integer threads) {
        if (threads == null) {
            if (threadPool == null) {
                threadPool = new ForkJoinPool(4);
            }
        } else {
            threadPool = new ForkJoinPool(threads);
        }
        return threadPool;
    }

}
