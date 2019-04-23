package controlador.principal;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Johnny
 */
public class ConexionesCTR extends Thread {

    private final Connection ct;
    private int segundos;
    private String msg; 

    public ConexionesCTR(Connection ct) {
        this.ct = ct;
    }

    @Override
    public void run() {
        try {
            System.out.println("Se inicia el hilo para comprobar, la conexion.");
            
            while (segundos < 130) {
                dormir(1000);
                //System.out.println("Sigue contando: "+segundos);
                //System.out.println("SEgundos: "+segundos);
                segundos++; 
            }
            System.out.println("Se cerrara la conexion. Se receteo por ultima: \n"+msg);
            ct.close();
        } catch (SQLException ex) {
            System.out.println("No se pudo cerrar la conexion."+ex.getMessage());
        }
    }

    public void iniciar(String mensaje) {
        System.out.println("Iniciamos conexion desde: "+mensaje);
        if (ct != null) {
            System.out.println("Si tenemos conexion.");
        }
        segundos = 0; 
        this.start();
    }

    public void recetear(String mensaje) {
        this.msg = mensaje;
        System.out.println("Se receteara. Segundos: "+segundos);
        segundos = 0;
        System.out.println("Se receteeo. Segundos: "+segundos);
    }
    
    public void dormir(int seg){
        try {
            sleep(seg);
        } catch (InterruptedException ex) {
            System.out.println("El hilo no se pudo dormir. "+ex.getMessage());
        }
    }
    
    public void matarHilo(){
        segundos = 2000;
    }

}
