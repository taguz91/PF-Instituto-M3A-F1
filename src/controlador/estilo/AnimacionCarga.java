package controlador.estilo;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import modelo.propiedades.Propiedades;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class AnimacionCarga extends Thread {

    //Animacion que se realizara en un lbl  
    private final JLabel lbl;
    private final VtnPrincipal vtnPrin;
    private int pos = 0;
    private boolean cargando = true, bloqueo = false;
    //El sockect que escuchara todo  
    private Socket sc;
    private ServerSocket ssc;
    private DataInputStream mensaje;
    private String me;

    //Todos los inconos que usaremos 
    ImageIcon estados[] = {
        new ImageIcon(getClass().getResource("/vista/img/animacion/LogoPosFinal.png")),
        new ImageIcon(getClass().getResource("/vista/img/animacion/LogoPos1.png")),
        new ImageIcon(getClass().getResource("/vista/img/animacion/LogoPos2.png")),
        new ImageIcon(getClass().getResource("/vista/img/animacion/LogoPos3.png"))
    };

    public AnimacionCarga(JLabel lbl, VtnPrincipal vtnPrin) {
        this.lbl = lbl;
        this.vtnPrin = vtnPrin;
    }

    @Override
    public void run() {
        //iniciarSocketEscucha();
        while (cargando) {
            //System.out.println("Animacion en funcionamiento " + cuenta);
            lbl.setIcon(estados[pos]);
            //lbl.updateUI();
            dormir(500);
            pos++;
            if (pos > estados.length - 1) {
                pos = 1;
            }
            //comprobarConexion();
            //Aqui estara el sockect escuchando siempre 
            //escucharSocket();
        }
    }

    private void escucharSocket() {
        if (ssc != null) {
            try {
                sc = ssc.accept();
                mensaje = new DataInputStream(sc.getInputStream());
                me = mensaje.readUTF();
                JOptionPane.showMessageDialog(vtnPrin, me);
                sc.close();
            } catch (IOException e) {
                System.out.println("El socket se murio escuchando pray for it. " + e.getMessage());
            }
        }
    }

    private void iniciarSocketEscucha() {
        try {
            ssc = new ServerSocket(6000);
        } catch (IOException ex) {
            System.out.println("No se puedo iniciar el sokect de escucha. " + ex.getMessage());
        }
    }

    public void iniciar() {
        System.out.println("Se inicio la animacion");
        this.cargando = true;
    }

    public void detener() {
        System.out.println("Se detuvo la animacion");
        this.cargando = false;
        lbl.setIcon(estados[0]);
    }

    public void dormir(int seg) {
        try {
            Thread.sleep(seg);
        } catch (InterruptedException e) {
            System.out.println("No se realizo la animacion " + e.getMessage());
        }
    }

    private void comprobarConexion() {
        String ip = Propiedades.getPropertie("ip");
        String ping = "ping " + ip;

        try {
            Runtime rt = Runtime.getRuntime();
            Process pc = rt.exec(ping);

            BufferedReader br = new BufferedReader(new InputStreamReader(pc.getInputStream()));
            String linea = br.readLine();
            while (linea != null) {
                System.out.println(linea + "\n");
                linea = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("No se pudo hacer ping " + e.getMessage());
        }
    }

}
