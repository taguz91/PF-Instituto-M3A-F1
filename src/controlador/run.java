package controlador;

import controlador.login.LoginCTR;
import java.awt.EventQueue;
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
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            System.out.println(ex.getMessage());
        }

//        SplashCTR ctrSplash = new SplashCTR();
//        ctrSplash.iniciar();
        EventQueue.invokeLater(() -> {

            LoginCTR login = new LoginCTR(new Login());
            login.Init();

        });

    }

}
