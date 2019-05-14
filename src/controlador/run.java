package controlador;

import controlador.login.LoginCTR;
import java.awt.EventQueue;
import javax.swing.UIManager;
import vista.Login;

/**
 *
 * @author Johnny
 */
public class run {

    public static void main(String[] args) {
        if (!iniciaEstilo("Windows")) {
            iniciaEstilo("Nimbus");
        }

//        SplashCTR ctrSplash = new SplashCTR();
//        ctrSplash.iniciar();
        EventQueue.invokeLater(() -> {

            LoginCTR login = new LoginCTR(new Login());
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
        return encontrado;
    }

}
