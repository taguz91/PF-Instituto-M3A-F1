package controlador.estilo;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
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
    private InetAddress ina;
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
        iniciarSocketEscucha();
        System.out.println("Animacion en funcionamiento 1111111111");
        try {
            ina = InetAddress.getByName(Propiedades.getPropertie("ip"));
            System.out.println("EL IP ES: " + Propiedades.getPropertie("ip"));
        } catch (UnknownHostException e) {
            ina = null;
            System.out.println("No se puede hacer ping a esta direccion." + e.getMessage());
        }

        while (cargando) {
            //System.out.println("Animacion en funcionamiento " + cuenta);
            lbl.setIcon(estados[pos]);
            //lbl.updateUI();
            dormir(500);
            pos++;
            if (pos > estados.length - 1) {
                pos = 1;
            }

//            if (ina != null) {
//                try {
//                    if (ina.isReachable(2000)) {
//                        System.out.println("Se puede acceder sin problema.");
//                        System.out.println("Esta hosteado en: "+ina.getHostName());
//                        System.out.println(""+ina.getCanonicalHostName());
//                        if (bloqueo) {
//                            JOptionPane.showMessageDialog(null, "Se reestablecio la conexion a la red. "
//                                    + "\nPuede volver a usar la aplicacion...");
//                            vtnPrin.setEnabled(true);
//                            bloqueo = false;
//                        }
//                    } else {
//                        if (!bloqueo) {
//                            JOptionPane.showMessageDialog(null, "Se perdio la conexion a la red, \npor favor espere. "
//                                    + "\nSe bloqueara la aplicacion hasta que vuelva a tener conexion.");
//                            vtnPrin.setEnabled(false);
//                            bloqueo = true;
//                        }
//                    }
//                } catch (IOException e) {
//                    System.out.println("No se puede ver si puede ser accedida.");
//                }
//            }
            //Aqui estara el sockect escuchando siempre 
            escucharSocket();
        }

        //Le regresmos al icono original
        //Cuando se detiene la animacion
        //lbl.setIcon(estados[0]);
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
            System.out.println("Se inicia el sockect para escuchar mensajes.");
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

}
