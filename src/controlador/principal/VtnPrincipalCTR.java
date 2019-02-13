package controlador.principal;

import Vista.FrmMateria;
import vista.docente.FrmDocente;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnPrincipalCTR {

    private VtnPrincipal vtnPrin;

    public VtnPrincipalCTR(VtnPrincipal vtnPrin) {
        this.vtnPrin = vtnPrin;

        vtnPrin.setVisible(true);
    }

    public void iniciar() {
        //Para el estilo 
        vtnPrin.getMnRbtnMetal().addActionListener(e -> estiloVtn());
        vtnPrin.getMnRbtnNimbus().addActionListener(e -> estiloVtn());
        vtnPrin.getMnRbtnWindows().addActionListener(e -> estiloVtn());
        
        vtnPrin.getBtnDocente().addActionListener(e -> abrirFrmDocente());
        vtnPrin.getMnIgDocente().addActionListener(e -> abrirFrmDocente());

        vtnPrin.getBtnMateria().addActionListener(e -> abrirFrmMateria());
        vtnPrin.getMnCtMateria().addActionListener(e -> abrirFrmMateria());
    }

    public void abrirFrmDocente() {
        FrmDocente frmDocen = new FrmDocente();

        vtnPrin.getDpnlPrincipal().add(frmDocen);
        frmDocen.show();
    }

    public void abrirFrmMateria() {
        FrmMateria frmMate = new FrmMateria();

        vtnPrin.getDpnlPrincipal().add(frmMate);
        frmMate.show();
    }

    public void estiloVtn() {
        String estilo = "Windows";

        if (vtnPrin.getMnRbtnMetal().isSelected()) {
            estilo = "Metal";
        } else if (vtnPrin.getMnRbtnNimbus().isSelected()) {
            estilo = "Nimbus";
        }
        
        vtnPrin.dispose();
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if (estilo.equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VtnPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VtnPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VtnPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VtnPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        vtnPrin = new VtnPrincipal(); 
        vtnPrin.setVisible(true); 
    }

}
