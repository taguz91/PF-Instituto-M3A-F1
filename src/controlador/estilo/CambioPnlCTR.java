package controlador.estilo;

import javax.swing.JPanel;

/**
 *
 * @author Johnny
 */
public class CambioPnlCTR {

    //Metodo para cambiar de paneles, este metodo repinta un panel con otro 
    public static void cambioPnl(JPanel pnlPadre, JPanel pnlHijo) {
        pnlPadre.removeAll();
        pnlPadre.repaint();
        pnlPadre.revalidate();

        pnlPadre.add(pnlHijo);
        pnlPadre.repaint();
        pnlPadre.revalidate();
    }
}
