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

    private final ImageIcon estados[] = {
        new ImageIcon(getClass().getResource("/vista/img/animacion/SPL1.png")),
        new ImageIcon(getClass().getResource("/vista/img/animacion/SPL2.png")),
        new ImageIcon(getClass().getResource("/vista/img/animacion/SPL3.png")),
        new ImageIcon(getClass().getResource("/vista/img/animacion/SPL4.png")),
        new ImageIcon(getClass().getResource("/vista/img/animacion/SPL5.png"))
    };

    private final ImageIcon icono = new ImageIcon(getClass().getResource("/vista/img/logo.png"));

    private final Splash s;

    public SplashCTR() {
        this.s = new Splash();
        s.setLocationRelativeTo(null);
        AWTUtilities.setWindowOpaque(s, false);
        s.setExtendedState(JFrame.MAXIMIZED_BOTH);
        s.setIconImage(icono.getImage());
        s.setVisible(true);
    }

    public void iniciar() {

        for (ImageIcon estado : estados) {
            s.getLblFondo().setIcon(estado);
            dormir(500);
        }

        dormir(250);
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
