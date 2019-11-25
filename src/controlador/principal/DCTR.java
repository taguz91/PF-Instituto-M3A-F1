package controlador.principal;

import java.awt.Cursor;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Johnny
 */
public class DCTR {

    protected final VtnPrincipalCTR ctrPrin;
    protected boolean vtnCargada = false; 

    public DCTR(VtnPrincipalCTR ctrPrin) {
        this.ctrPrin = ctrPrin;
    }
    
    protected void abrirJD(JDialog jd) {
        jd.setLocationRelativeTo(ctrPrin.getVtnPrin());
        jd.setVisible(true);
    }
    
    protected void errorNoSeleccionoFila() {
        JOptionPane.showMessageDialog(
                null,
                "No selecciono ninguna fila."
        );
    }
    
    protected void cursorCarga(JInternalFrame jif){
        vtnCargada = false;
        Cursor c = new Cursor(3);
        jif.setCursor(c);
        ctrPrin.getVtnPrin().setCursor(c);
    }
    
    protected void cursorNormal(JInternalFrame jif){
        Cursor c = new Cursor(0);
        jif.setCursor(c);
        ctrPrin.getVtnPrin().setCursor(c);
        vtnCargada = true;
    }

}
