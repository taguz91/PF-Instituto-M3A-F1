package controlador.estilo;

import com.sun.awt.AWTUtilities;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import vista.principal.Splash;

/**
 *
 * @author Johnny
 */
public class SplashCTR {
    //Cargamoslas imagenes para el splash

    private final ImageIcon estados[] = {
        new ImageIcon(getClass().getResource("/vista/img/animacion/SpriteC1.png")),
        new ImageIcon(getClass().getResource("/vista/img/animacion/SpriteC2.png")),
        new ImageIcon(getClass().getResource("/vista/img/animacion/SpriteC3.png")),
        new ImageIcon(getClass().getResource("/vista/img/animacion/SpriteC4.png"))
    };
//    private final ImageIcon estados[] = {
//        new ImageIcon(getClass().getResource("/vista/img/animacion/SplasP1.png")),
//        new ImageIcon(getClass().getResource("/vista/img/animacion/SplasP9.png"))
//    };
//    private final ImageIcon estados[] = {
//        new ImageIcon(getClass().getResource("/vista/img/animacion/SplasPP1.png")),
//        new ImageIcon(getClass().getResource("/vista/img/animacion/SplasPP2.png")),
//        new ImageIcon(getClass().getResource("/vista/img/animacion/SplasPP3.png")),
//        new ImageIcon(getClass().getResource("/vista/img/animacion/SplasPP4.png"))
//    };
    private final ImageIcon icono = new ImageIcon(getClass().getResource("/vista/img/logo.png"));

    private final Splash s;

    public SplashCTR() {
        this.s = new Splash();
        //s.setLocationRelativeTo(null);
        AWTUtilities.setWindowOpaque(s, false);
        //s.setExtendedState(JFrame.MAXIMIZED_BOTH);
        s.setLocationRelativeTo(null);
        s.setIconImage(icono.getImage());
        s.setVisible(true);
    }

    public void iniciar() {
        for (ImageIcon estado : estados) {
            s.getLblFondo().setIcon(estado);
            dormir(500);
        }
        //Veremos la ancura de la pantalla
//        int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
//        //Se le quita al lbl lo opaco para mirar como va el trayecto  
//        //s.getLblFondo().setOpaque(true);
//        int x = 400;
//        s.getLblFondo().setBounds(x, 0, 100, s.getHeight());
//        //dormir(10);
//        s.getLblFondo().setIcon(estados[0]);
//        while (s.getLblFondo().getX() < ancho - 400) {
//            x = x + 3;
//            s.getLblFondo().setBounds(x, 0, 100, s.getHeight());
//            dormir(5);
//        }
//        s.getLblFondo().setBounds(0, 0, s.getWidth(), s.getHeight());
//        s.getLblFondo().setIcon(estados[1]);
//        dormir(700);
//        s.getLblFondo().setIcon(estados[2]);
//        dormir(400);
        s.dispose();
    }

    private void dormir(int miliSeg) {
        try {
            Thread.sleep(miliSeg);
        } catch (InterruptedException e) {
            System.out.println("No se durmio el hilo " + e.getMessage());
        }
    }

}
