package controlador.usuario.forms;

import controlador.Libraries.Middlewares;
import controlador.Libraries.Validaciones;
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

    private final VtnPrincipal desktop;
    private final FrmUsuario vista;

    //LISTAS
    private Map<String, DocenteMD> listaPersonas;

    public AbstracForm(VtnPrincipal desktop) {
        this.desktop = desktop;
        this.vista = new FrmUsuario();
    }

    public void Init() {

        InitEventos();

        new Thread(() -> {
            
            Middlewares.centerFrame(vista, desktop.getDpnlPrincipal());
            try {
                vista.setSelected(true);
                desktop.getDpnlPrincipal().add(vista);
                vista.show();
            } catch (PropertyVetoException ex) {
                Logger.getLogger(FrmUsuarioAdd.class.getName()).log(Level.SEVERE, null, ex);
            }

            
        }).start();

        listaPersonas = DocenteBD.selectDocentes();
        cargarComoPersonas();
    }

    private void InitEventos() {
        //VALIDACIONES

        vista.getTxtUsername().addKeyListener(Validaciones.validarSoloLetrasYnumeros());
        vista.getTxtPassword().addKeyListener(Validaciones.validarSoloLetrasYnumeros());

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
                            System.out.println("-------");
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
}
