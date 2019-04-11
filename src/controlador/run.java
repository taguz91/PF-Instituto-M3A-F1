package controlador;

import controlador.estilo.SplashCTR;
import controlador.login.LoginCTR;
import javax.swing.UIManager;
import modelo.usuario.UsuarioBD;
import vista.Login;

/**
 *
 * @author Johnny
 */
public class run {

    public static void main(String[] args) {
        try {
//            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//                if ("Windows".equals(info.getName())) {
//                    UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
            //Inicia con el look and field por defecto en el sistema
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(run.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

//        SplashCTR ctrSplash = new SplashCTR();
//        ctrSplash.iniciar();
        java.awt.EventQueue.invokeLater(() -> {

            LoginCTR login = new LoginCTR(new Login(), new UsuarioBD());
            login.Init();

        });

    }

}
