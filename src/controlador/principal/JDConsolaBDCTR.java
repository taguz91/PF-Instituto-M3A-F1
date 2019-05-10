package controlador.principal;

import Postgres.Informacion;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import modelo.ConectarDB;
import modelo.propiedades.Propiedades;
import vista.principal.JDConsolaBD;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class JDConsolaBDCTR {

    private final JDConsolaBD jd;
    private final VtnPrincipalCTR ctrPrin;
    private int cont = 0;
    private Socket sc;
    private final String ip = Propiedades.getPropertie("ip");
    private DataOutputStream mensaje;
    private Informacion info;
    private ArrayList<String> ips;

    public JDConsolaBDCTR(VtnPrincipal vtnPrin, ConectarDB conecta, VtnPrincipalCTR ctrPrin) {
        this.jd = new JDConsolaBD(vtnPrin, false);
        jd.setLocationRelativeTo(vtnPrin);
        jd.setVisible(true);
        this.info = new Informacion(conecta);
        this.ctrPrin = ctrPrin;
    }

    public void iniciar() {
        //Para que salta la linea autimaticamente
        jd.getTxtArea().setLineWrap(true);
        jd.getTxtArea().setWrapStyleWord(true);

        jd.getBtnEjecutar().addActionListener(e -> ejecutar());

        ctrPrin.eventoJDCerrar(jd);
    }

    private void ejecutar() {
        try {
            ips = info.obtenetIpsConectadosBD(Propiedades.getPropertie("database"));
            ips.forEach(ip -> {
                System.out.println("Ip: "+ip);
            });
            sc = new Socket("127.0.0.1", 6000);
            mensaje = new DataOutputStream(sc.getOutputStream());
            String txt = jd.getTxtArea().getText().trim();
            cont++;
            mensaje.writeUTF("Mensaje numero " + cont + ": \n" + txt + "\n");
            mensaje.close();
            sc.close();
        } catch (IOException ex) {
            System.out.println("No pude conectar: " + ex);
        }
    }

}
