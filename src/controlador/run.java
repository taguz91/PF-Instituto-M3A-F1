package controlador;

import controlador.login.LoginCTR;
import controlador.version.VtnDitoolCTR;
import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.version.DitoolBD;
import modelo.version.VersionMD;
import utils.CONS;
import vista.version.VtnDitool;

/**
 *
 * @author Johnny
 */
public class run {

    public static void main(String[] args) {

        if (!iniciaEstilo("Windows")) {
            iniciaEstilo("Nimbus");
        }
        if (CONS.M_DESARROLLO) {
            Logger.getLogger(run.class.getName()).log(
                    Level.SEVERE, 
                    null, 
                    "Iniciamos en modo desarrollo"
            );
            EventQueue.invokeLater(() -> {
                LoginCTR login = new LoginCTR();
                login.Init();
            });
        } else {
            VtnDitool vtnDitool = new VtnDitool();
            vtnDitool.setTitle("Ditool | Version instalada: ");
            DitoolBD di = new DitoolBD(CONS.getBDUser(), CONS.BD_PASS);
            VersionMD v = di.consultarUltimaVersion();
            if (v != null) {
                VtnDitoolCTR ctrVtn = new VtnDitoolCTR(v, vtnDitool);
                ctrVtn.iniciar();
            } else {
                JOptionPane.showMessageDialog(
                        vtnDitool, "Posiblemente no tengamos acceso al servidor. \n"
                        + "Ponganse en contacto con el administrador de la base de datos."
                );
                System.exit(0);
            }
        }

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
            Logger.getLogger(run.class.getName()).log(Level.SEVERE, null, "No enconramos ninugn LOOK AND FIELD " + ex.getMessage());
        }
        return encontrado;
    }

}
