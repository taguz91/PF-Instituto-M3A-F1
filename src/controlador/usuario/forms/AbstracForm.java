package controlador.usuario.forms;

import controlador.Libraries.Middlewares;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import vista.principal.VtnPrincipal;
import vista.usuario.FrmUsuario;

/**
 *
 * @author MrRainx
 */
public abstract class AbstracForm {

    private VtnPrincipal desktop;
    private FrmUsuario vista;

    //LISTAS
    private Map<String, DocenteMD> listaPersonas;

    public AbstracForm() {
    }

    public void setDesktop(VtnPrincipal desktop) {
        this.desktop = desktop;
    }

    public void setVista(FrmUsuario vista) {
        this.vista = vista;
    }

    public void Init() {

        InitEventos();

        new Thread(() -> {
            listaPersonas = DocenteBD.selectDocentes();
            cargarComoPersonas();

            Middlewares.centerFrame(vista, desktop.getDpnlPrincipal());
            try {
                vista.setSelected(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(FrmUsuarioCTR.class.getName()).log(Level.SEVERE, null, ex);
            }

            desktop.getDpnlPrincipal().add(vista);
            vista.show();
        }).start();

    }

    private void InitEventos() {
        //VALIDACIONES

        vista.getTxtUsername().addKeyListener();

    }

    //METODOS DE APOYO
    private void cargarComoPersonas() {
        try {
            listaPersonas
                    .entrySet()
                    .stream()
                    .forEach(obj -> {
                        if (vista.isVisible()) {
                            vista.getCmbPersona().addItem(obj.getKey());
                        } else {
                            listaPersonas = null;
                        }
                    });
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("VENTANA CERRADA.. CARGA DE PERSONAS CANCELADA");
        }

    }

    private void resetForm() {
        vista.getTxtUsername().setText("");
        vista.getTxtPassword().setText("");
        vista.getTxtBuscarPer().setText("");
        vista.getCmbPersona().setSelectedIndex(0);
    }

    //EVENTOS
    private KeyAdapter validarCaracteres() {
        return new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                
            }
        };
    }

}
