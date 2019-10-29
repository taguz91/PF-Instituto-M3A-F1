package modelo;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import modelo.accesosDelRol.AccesosDelRolBD;
import modelo.usuario.RolBD;
import modelo.usuario.UsuarioBD;

/**
 *
 * @author JOHNNY, MrRainx
 */
public class CONS {

    //Modo del proyecto 
    public static final boolean M_DESARROLLO = false;
    //Nombre de las propiedadesde la base de matos
    public static final String BD_DATABASE = "database", BD_IP = "ip", BD_PUERTO = "port";
    //Direccion de las propiedades de base de datos
    public static final String BD_DIR = "configuracion.properties";
    //Nombre de las propiedades la version
    public static final String V_AUTOR = "Autor", V_NOMBRE = "Nombre", V_VERSION = "Version",
            V_NOTAS = "Notas", V_FECHA = "Fecha";
    //Direccion de las propiedades de version  
    public static final String V_DIR = "version.properties";

    public static Color ERROR_COLOR = new Color(159, 53, 39);
    public static Color SUCCESS_COLOR = new Color(10, 186, 52);

    public static Border ERR_BORDER = new LineBorder(ERROR_COLOR, 1);
    public static Border DEFAULT_BORDER = UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border");

    /*
        Base de datos
     */
    public static String BD_PASS = "APP_DESK_ISTA_$2019";
    public static String BD_URL = "jdbc:postgresql://35.192.7.211:5432/BDinsta";
    //public static String BD_URL = "jdbc:postgresql://35.192.7.211:5432/BDnotas";
    public static final String DB_IP = "35.192.7.211:5432";

    public static String getBDUser() {
        if (ROL != null) {
            if (ROL.getNombre().equalsIgnoreCase("coordinador")) {
                return ROL.getNombre().toUpperCase();
            } else if (ROL.getNombre().equalsIgnoreCase("docente")) {
                return ROL.getNombre().toUpperCase();
            }            
        }

        return "APP_ESCRITORIO";

    }

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

    public static ForkJoinPool THREAD_POOL;

    public static ForkJoinPool getPool(Integer threads) {
        if (threads == null) {
            if (THREAD_POOL == null) {
                THREAD_POOL = new ForkJoinPool(4);
            } else {
                if (THREAD_POOL.isShutdown()) {
                }
            }
        } else {
            THREAD_POOL = new ForkJoinPool(threads);
        }
        return THREAD_POOL;
    }

    private static ImageIcon ICONO;

    public static ImageIcon getICONO() {
        if (ICONO == null) {
            ICONO = new ImageIcon(ClassLoader.getSystemResource("vista/img/logo.png"));
        }
        return ICONO;
    }

    public static Image getImage() {
        if (ICONO == null) {
            ICONO = getICONO();
        }
        return ICONO.getImage();
    }

    public static List<String> permisos;

    public static List<String> getPermisos() {
        if (permisos == null) {
            refreshPermisos();
        }
        return permisos;
    }

    public static void refreshPermisos() {
        permisos = new AccesosDelRolBD().selectWhere(CONS.ROL.getId(), true);
    }

    public static void activarBtns(JComponent... components) {
        if (!ROL.getNombre().equalsIgnoreCase("ROOT") && !ROL.getNombre().equalsIgnoreCase("DEV")
                && !ROL.getNombre().equalsIgnoreCase("COORDINADOR")) {
            Arrays.stream(components)
                    .forEach(obj -> {
                        obj.setEnabled(CONS.getPermisos().contains(obj.getAccessibleContext().getAccessibleName()));
                    });
        }
    }

    public static String getDia(int dia) {
        String d;
        switch (dia) {
            case 1:
                d = "Lunes";
                break;
            case 2:
                d = "Martes";
                break;
            case 3:
                d = "Miercoles";
                break;
            case 4:
                d = "Jueves";
                break;
            case 5:
                d = "Viernes";
                break;
            case 6:
                d = "Sabado";
                break;
            case 7:
                d = "Domingo";
                break;
            default:
                d = "";
                break;
        }
        return d;

    }

    public static int getDia(String dia) {
        int d;
        dia = dia.toUpperCase();
        switch (dia) {
            case "LUNES":
                d = 1;
                break;
            case "MARTES":
                d = 2;
                break;
            case "MIERCOLES":
                d = 3;
                break;
            case "JUEVES":
                d = 4;
                break;
            case "VIERNES":
                d = 5;
                break;
            case "SABADO":
                d = 6;
                break;
            case "DOMINGO":
                d = 7;
                break;
            default:
                d = 0;
                break;
        }
        return d;

    }

}
