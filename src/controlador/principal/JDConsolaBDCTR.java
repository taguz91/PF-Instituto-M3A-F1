package controlador.principal;

import Postgres.Informacion;
import controlador.version.FrmVersionCTR;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import modelo.propiedades.Propiedades;
import vista.principal.JDConsolaBD;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class JDConsolaBDCTR extends DVtnCTR {

    private final JDConsolaBD jd;
    private int cont = 0;
    private Socket sc;
    private final String ipservidor = Propiedades.getPropertie("ip");
    private DataOutputStream mensaje;
    private final Informacion info;
    private ArrayList<String> ips;

    public JDConsolaBDCTR(VtnPrincipal vtnPrin, VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
        this.jd = new JDConsolaBD(vtnPrin, false);
        jd.setLocationRelativeTo(vtnPrin);
        jd.setVisible(true);
        this.info = new Informacion(ctrPrin.getConecta());
    }

    public void iniciar() {
        //Para que salta la linea autimaticamente
        jd.getTxtArea().setLineWrap(true);
        jd.getTxtArea().setWrapStyleWord(true);

        jd.getBtnEjecutar().addActionListener(e -> ejecutar());

        ctrPrin.eventoJDCerrar(jd);
        //Formato de la tabla
        formatoTbl();
        //Iniciar el formulario 
        jd.getBtnIngresarVersion().addActionListener(e -> ingresar());
    }

    private void formatoTbl() {
        String[] t = {"Nombre", "Version"};
        String[][] d = {};
        iniciarTbl(t, d, jd.getTblVersiones());
    }

    private void ejecutar() {
        try {
            ips = info.obtenetIpsConectadosBD(Propiedades.getPropertie("database"));
            ips.forEach(ip -> {
                System.out.println("Ip: " + ip);
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

    private void ingresar() {
        FrmVersionCTR ctr = new FrmVersionCTR(ctrPrin);
        ctr.iniciar();
    }

}
