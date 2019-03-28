package controlador.estilo;

import com.sun.awt.AWTUtilities;
import javax.swing.ImageIcon;
import vista.principal.Splash;

/**
 *
 * @author Johnny
 */
public class SplastCTR {
    //Cargamoslas imagenes para el splash

    private final ImageIcon estados[] = {
        new ImageIcon(getClass().getResource("/vista/img/animacion/SpriteC1.png")),
        new ImageIcon(getClass().getResource("/vista/img/animacion/SpriteC2.png")),
        new ImageIcon(getClass().getResource("/vista/img/animacion/SpriteC3.png")),
        new ImageIcon(getClass().getResource("/vista/img/animacion/SpriteC4.png"))
    };

    private final Splash s;

    public SplastCTR() {
        this.s = new Splash();
        s.setLocationRelativeTo(null);
        AWTUtilities.setWindowOpaque(s, false);
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
