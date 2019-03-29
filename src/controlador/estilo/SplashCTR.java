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
        new ImageIcon(getClass().getResource("/vista/img/animacion/SP1.png")),
        new ImageIcon(getClass().getResource("/vista/img/animacion/SP2.png")),
        new ImageIcon(getClass().getResource("/vista/img/animacion/SP3.png")),
        new ImageIcon(getClass().getResource("/vista/img/animacion/SP4.png")),
        new ImageIcon(getClass().getResource("/vista/img/animacion/SP5.png"))
    };

    private final ImageIcon icono = new ImageIcon(getClass().getResource("/vista/img/logo.png"));

    private final Splash s;

    public SplashCTR() {
        this.s = new Splash();
        s.setLocationRelativeTo(null);
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
