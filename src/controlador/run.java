package controlador;

import controlador.login.LoginCTR;
import modelo.usuario.UsuarioBD;
import vista.Login;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class run {

    public static void main(String[] args) {
        estiloWindows();


        
        LoginCTR login = new LoginCTR(new Login(), new UsuarioBD());
        login.Init();
        
        

    }

    public static void estiloWindows() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VtnPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

}
