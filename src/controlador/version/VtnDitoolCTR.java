package controlador.version;

import controlador.login.LoginCTR;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import modelo.CONS;
import modelo.version.VersionMD;
import vista.Login;
import vista.principal.VtnPrincipal;
import vista.version.VtnDitool;

/**
 *
 * @author alumno
 */
public class VtnDitoolCTR {

    private final VersionMD version;
    private final VtnDitool vtnDitool;
    private final VtnPrincipal vtnPrincipal;
    private boolean aCorrecto = true;
    private final ImageIcon icono;
    private boolean cerrar = false;
    //Cancion al descargar el sistema.
    private Clip sonido;

    //Iconos para el sprit 
    private final ImageIcon estados[] = {
        new ImageIcon(getClass().getResource("/vista/img/animacion/SPP1.png")),
        new ImageIcon(getClass().getResource("/vista/img/animacion/SPP2.png")),
        new ImageIcon(getClass().getResource("/vista/img/animacion/SPP3.png"))
    };
    //Icono para comenzar 
    private final ImageIcon iconIni = new ImageIcon(getClass().getResource("/vista/img/animacion/SPPF.png"));

    public VtnDitoolCTR(VersionMD version, VtnDitool vtnPrin) {
        this.icono = new ImageIcon(getClass().getResource("/vista/img/update.png"));
        this.version = version;
        this.vtnDitool = vtnPrin;
        this.vtnPrincipal = null;
        this.vtnDitool.setIconImage(icono.getImage());
    }

    public VtnDitoolCTR(VersionMD version, VtnDitool vtnPrin, VtnPrincipal vtnPrincipal) {
        this.icono = new ImageIcon(getClass().getResource("/vista/img/update.png"));
        this.version = version;
        this.vtnDitool = vtnPrin;
        this.vtnPrincipal = vtnPrincipal;
        this.vtnDitool.setIconImage(icono.getImage());
    }

    public void iniciar() {
        mostrarVtn();
        //Accion del boton 
        vtnDitool.getBtnPausar().addActionListener(e -> clickPausar());
        mostrarInformacion();
        if (validarVersion()) {
            if (aCorrecto) {
                comprobarVersion();
            }
        } else {
            JOptionPane.showMessageDialog(vtnDitool, "No se an publicado versiones.");
            ejecutarPrograma();
        }

    }

    private void mostrarInformacion() {
        File pv = new File(CONS.V_DIR);
        Properties p = new Properties();
        if (pv.exists()) {
            if (comprobarRequisitos(p, pv)) {
                try {
                    p.load(new FileReader(pv));
                    vtnDitool.getLblAutor().setText(p.getProperty(CONS.V_AUTOR));
                    vtnDitool.getLblNombre().setText(version.getNombreSinExtension(p.getProperty(CONS.V_NOMBRE)));
                    vtnDitool.getLblVersion().setText(p.getProperty(CONS.V_VERSION));
                    //Ponemos la version del sistema en el titulo
                    vtnDitool.setTitle(vtnDitool.getTitle() + " " + p.getProperty(CONS.V_VERSION));
                } catch (IOException e) {
                    System.out.println("No pudimos leer las propiedades de version: " + e.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(vtnDitool, "Su archivo de propiedades esta corrupto,\n"
                        + "vamos a descargar todo nuevamente.");
                aCorrecto = false;
                if (validarVersion()) {
                    crearVersion();
                    cerrarTodo();
                }

            }
        }

    }

    public void mostrarVtn() {
        vtnDitool.setVisible(true);
        vtnDitool.setLocationRelativeTo(null);
        vtnDitool.getLblFondo().setIcon(iconIni);
    }

    private void iniciarSprint() {
        new Thread(() -> {
            while (!cerrar) {
                for (ImageIcon estado : estados) {
                    vtnDitool.getLblFondo().setIcon(estado);
                    dormir(500);
                }
                System.out.println("Sigue funcionando la animacion del DITOOL");
            }
        }).start();
    }

    public void comprobarVersion() {
        File pv = new File(CONS.V_DIR);
        Properties p = new Properties();
        if (pv.exists()) {
            if (comprobarRequisitos(p, pv)) {
                if (compara(p, pv)) {
                    JOptionPane.showMessageDialog(null, "Esta instalada la ultima version del sistema.");
                    if (vtnPrincipal == null) {
                        ejecutarPrograma();
                    } else {
                        habilitarPrograma();
                    }

                } else {
                    //Aqui descargamos la versio nueva
                    crearVersion();
                }
            } else {
                //Si el archivo de version no contiene todo lo necesario se descarga
                crearVersion();
            }
        } else {
            crearVersion();
        }
        cerrarTodo();
    }

    public void crearVersion() {
        Object[] opt;
        
        if(vtnPrincipal == null){
            opt = new Object[]{"Actualizar", "Abrir", "Salir"};
        }else{
            opt = new Object[]{"Actualizar", "Cancelar", "Salir"};
        }
        
        int s = JOptionPane.showOptionDialog(vtnDitool,
                "\n"
                + "¿Tiene una actualizacion pendiente del sistema?\n"
                + "Si no actualiza el sistema no tendra acceso a \n"
                + "todas las funciones del sistema.",
                "Actualizacion pendiente",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null, opt, "Actualizar");
        switch (s) {
            case 0:
                altualizarSistema();
                break;
            case 1:
                if(vtnPrincipal == null){
                    ejecutarPrograma();
                }else{
                    habilitarPrograma();
                }
                break;
            case 2:
                System.exit(0);
                break;
        }

    }

    private void altualizarSistema() {
        iniciarSprint();
        musicaDescarga();
        vtnDitool.getLblEstado().setText("Descargando...");
        File pv = new File(CONS.V_DIR);
        Properties p = new Properties();
        Descarga descarga = new Descarga(version);
        if (descarga.descargar()) {
            crearPropiedades(p, pv);
            vtnDitool.getLblEstado().setText("Descargado");
            Descomprime uzip = new Descomprime(version);
            vtnDitool.getLblEstado().setText("Instalando..");
            uzip.descomprime();
        }
    }

    private boolean compara(Properties p, File bd) {
        boolean igual = true;
        String pNombre = "";
        String pVersion = "";
        try {
            p.load(new FileReader(bd));
            pNombre = p.getProperty(CONS.V_NOMBRE);
            pVersion = p.getProperty(CONS.V_VERSION);
        } catch (IOException e) {
            System.out.println("No pudimos leer las propiedades de version: " + e.getMessage());
        }

        if (!pNombre.equals(version.getNombre())) {
            igual = false;
        }

        if (!pVersion.equals(version.getVersion())) {
            igual = false;
        }

        return igual;
    }

    private boolean crearPropiedades(Properties p, File bd) {
        boolean creado = false;
        try {
            FileOutputStream fo = new FileOutputStream(bd);
            p.setProperty(CONS.V_AUTOR, version.getUsername());
            p.setProperty(CONS.V_NOMBRE, version.getNombre());
            p.setProperty(CONS.V_VERSION, version.getVersion());
            p.setProperty(CONS.V_FECHA, version.getFecha().toString());
            p.setProperty(CONS.V_NOTAS, version.getNotas());
            p.store(fo, "Descripcion de la version del sistema: ");
            creado = true;
        } catch (FileNotFoundException e) {
            System.out.println("No podemos escribir el archivo: " + e.getMessage());
        } catch (IOException ex) {
            System.out.println("No se pudo guardar el properties: " + ex.getMessage());
        }
        return creado;
    }

    private boolean comprobarRequisitos(Properties p, File bd) {
        boolean todas = true;
        try {
            p.load(new FileReader(bd));
            if (p.getProperty(CONS.V_AUTOR) == null) {
                todas = false;
            }

            if (p.getProperty(CONS.V_FECHA) == null) {
                todas = false;
            }

            if (p.getProperty(CONS.V_NOMBRE) == null) {
                todas = false;
            }

            if (p.getProperty(CONS.V_NOTAS) == null) {
                todas = false;
            }

            if (p.getProperty(CONS.V_VERSION) == null) {
                todas = false;
            }

        } catch (IOException e) {
            System.out.println("Error al comprobar si tiene todas las constantes:" + e.getMessage());
        }
        return todas;
    }

    private void ejecutarPrograma() {
        cerrarTodo();
        EventQueue.invokeLater(() -> {

            LoginCTR login = new LoginCTR();
            login.Init();

        });
    }

    private void habilitarPrograma() {
        cerrarTodo();
        vtnPrincipal.setEnabled(true);
    }

    private void cerrarTodo() {
        cerrar = true;
        vtnDitool.dispose();
    }

    private boolean validarVersion() {
        return !(version.getFecha() == null
                || version.getId() == 0
                || version.getNombre() == null
                || version.getNotas() == null
                || version.getUrl() == null
                || version.getUsername() == null
                || version.getVersion() == null);
    }

    private void dormir(int miliSeg) {
        try {
            Thread.sleep(miliSeg);
        } catch (InterruptedException e) {
            System.out.println("No se durmio el hilo " + e.getMessage());
        }
    }

    private void musicaDescarga() {
        try {
            File a = new File("dancin8bits.wav");
            if (a.exists()) {
                sonido = AudioSystem.getClip();

                sonido.open(AudioSystem.getAudioInputStream(a));
                sonido.start();
            }

        } catch (LineUnavailableException e) {
            System.out.println("No pudimos encontrar el audio. " + e.getMessage());
        } catch (UnsupportedAudioFileException | IOException ex) {
            System.out.println("No encontramos el archivo de audio. " + ex.getMessage());
        }
    }

    private void clickPausar() {
        if (vtnDitool.getBtnPausar().getText().equals("Pausar")) {
            vtnDitool.getBtnPausar().setText("Reanudar");
            pausar();
        } else {
            vtnDitool.getBtnPausar().setText("Pausar");
            reanudar();
        }
    }

    private void pausar() {
        sonido.stop();
    }

    private void reanudar() {
        sonido.start();
    }

    private void silenciar() {
        sonido.close();
    }

}
