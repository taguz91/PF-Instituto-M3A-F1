package controlador.estilo;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Johnny
 */
public class AnimacionCarga extends Thread {

    //Animacion que se realizara en un lbl  
    private final JLabel lbl;
    private int pos = 0;
    private boolean cargando = true;
    private int cuenta = 0;
    //Todos los inconos que usaremos 
    ImageIcon estados[] = {
        new ImageIcon(getClass().getResource("/vista/img/animacion/LogoPosFinal.png")),
        new ImageIcon(getClass().getResource("/vista/img/animacion/LogoPos1.png")),
        new ImageIcon(getClass().getResource("/vista/img/animacion/LogoPos2.png")),
        new ImageIcon(getClass().getResource("/vista/img/animacion/LogoPos3.png"))
    };

    public AnimacionCarga(JLabel lbl) {
        this.lbl = lbl;
    }

    @Override
    public void run() {
        System.out.println("Animacion en funcionamiento 000000000");

        while (cargando) {
            //System.out.println("Animacion en funcionamiento " + cuenta);
            lbl.setIcon(estados[pos]);
            //lbl.updateUI();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("No se realizo la animacion " + e.getMessage());
            }
            pos++;
            if (pos > estados.length - 1) {
                pos = 1;
            }
        }

        //Le regresmos al icono original
        //Cuando se detiene la animacion
        //lbl.setIcon(estados[0]);
    }

    public void iniciar() {
        System.out.println("Se inicio la animacion");
        this.cargando = true;
    }

    public void detener() {
        System.out.println("Se detuvo la animacion");
        this.cargando = false;
        lbl.setIcon(estados[0]);
    }

}
