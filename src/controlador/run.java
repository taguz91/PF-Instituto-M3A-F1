package controlador;

import controlador.principal.VtnPrincipalCTR;
import modelo.LlenarLugaresBD;
import modelo.persona.UsuarioMD;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class run {

    public static void main(String[] args) {
        estiloWindows();
        //Con esta clase llene lugares 
        /*LlenarLugaresBD prueba = new LlenarLugaresBD(); 
        prueba.iniciar();*/
        
        UsuarioMD usuario = new UsuarioMD();
        VtnPrincipal vtn = new VtnPrincipal(); 
        VtnPrincipalCTR ctrVtnPrin = new VtnPrincipalCTR(vtn, usuario); 
        ctrVtnPrin.iniciar();
    }
    
    public static void estiloWindows(){
         try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
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
    }
}
