package controlador.principal;

import javax.swing.JDialog;
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
        ctrPrin.eventoJDCerrar(jd);
    }
    
    protected void errorNoSeleccionoFila() {
        JOptionPane.showMessageDialog(
                null,
                "No selecciono ninguna fila."
        );
    }

}
