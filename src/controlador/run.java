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
        //No borrar !!!
//        try {
//            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(run.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }

        if (!iniciaEstilo("Windows")) {
            iniciaEstilo("Nimbus"); 
        }

//        SplashCTR ctrSplash = new SplashCTR();
//        ctrSplash.iniciar();
        java.awt.EventQueue.invokeLater(() -> {
            
            LoginCTR login = new LoginCTR(new Login(), new UsuarioBD());
            login.Init();
            
        });
    }
    
    public static boolean iniciaEstilo(String estilo) {
        boolean encontrado = false;        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if (estilo.equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    encontrado = true;
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            System.out.println("No enconramos ninugn LOOK AND FIELD " + ex.getMessage());
        }
        return true;
    }
    
}
