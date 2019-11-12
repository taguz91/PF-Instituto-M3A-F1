package controlador;

import controlador.login.LoginCTR;
import controlador.version.VtnDitoolCTR;
import java.awt.EventQueue;
import javax.swing.JOptionPane;
import modelo.CONS;
import modelo.version.DitoolBD;
import modelo.version.VersionMD;
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
        System.out.println("Modo desarrollo: "+CONS.M_DESARROLLO);
        if (CONS.M_DESARROLLO) {
            System.out.println("Iniciamos en modo desarrollo");
            EventQueue.invokeLater(() -> {
                LoginCTR login = new LoginCTR();
                login.Init();
            });
        } else {
            VtnDitool vtnDitool = new VtnDitool();
            vtnDitool.setTitle("Ditool | Version instalada: ");
                DitoolBD di = new DitoolBD("tsds", "TDSoftware158");
            VersionMD v = di.consultarUltimaVersion();
            if (v != null) {
                VtnDitoolCTR ctrVtn = new VtnDitoolCTR(v, vtnDitool);
                ctrVtn.iniciar();
            } else {
                JOptionPane.showMessageDialog(vtnDitool, "Posiblemente no tengamos acceso al servidor. \n"
                        + "Ponganse en contacto con el administrador de la base de datos.");
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
            System.out.println("No enconramos ninugn LOOK AND FIELD " + ex.getMessage());
        }
        return encontrado;
    }

}
