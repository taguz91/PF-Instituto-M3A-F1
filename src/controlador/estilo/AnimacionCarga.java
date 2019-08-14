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
        //iniciarSocketEscucha();
        while (cargando) {
            //System.out.println("Animacion en funcionamiento " + cuenta);
            lbl.setIcon(estados[pos]);
            //lbl.updateUI();
            dormir(500);
            pos++;
            if (pos > estados.length - 1) {
                pos = 1;
            }
            //comprobarConexion();
            //Aqui estara el sockect escuchando siempre 
            //escucharSocket();
        }
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

    public void dormir(int seg) {
        try {
            Thread.sleep(seg);
        } catch (InterruptedException e) {
            System.out.println("No se realizo la animacion " + e.getMessage());
        }
    }

}
