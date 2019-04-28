package controlador.usuario.forms;

import controlador.Libraries.Effects;
import controlador.Libraries.Validaciones;
import controlador.excepciones.BreakException;
import controlador.usuario.VtnUsuarioCTR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;
import javax.swing.JOptionPane;
import modelo.persona.PersonaBD;
import modelo.persona.PersonaMD;
import modelo.usuario.UsuarioBD;
import vista.principal.VtnPrincipal;
import vista.usuario.FrmUsuario;

/**
 *
 * @author MrRainx
 */
public abstract class AbstracForm {

    protected final VtnPrincipal desktop;
    protected FrmUsuario vista;
    protected UsuarioBD modelo;
    protected VtnUsuarioCTR vtnPadre;

    //LISTAS
    protected Map<String, PersonaMD> listaPersonas;

    public AbstracForm(VtnPrincipal desktop, VtnUsuarioCTR vtnPadre) {
        this.desktop = desktop;
        this.vtnPadre = vtnPadre;
        this.vista = new FrmUsuario();
    }

    public synchronized void Init() {

        new Thread(() -> {
            Effects.addInDesktopPane(vista, desktop.getDpnlPrincipal());
            InitEventos();
        }).start();
        activarFormulario(false);
        listaPersonas = PersonaBD.selectAll();
        cargarComoPersonas();
        activarFormulario(true);

    }

    private void InitEventos() {
        //VALIDACIONES

        vista.getTxtUsername().addKeyListener(Validaciones.validarSoloLetrasYnumeros());

        vista.getTxtPassword().addKeyListener(Validaciones.validarSoloLetrasYnumeros());

        vista.getTxtBuscarPer().addKeyListener(Validaciones.validarNumeros());

        vista.getTxtBuscarPer().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    vista.getCmbPersona().setSelectedItem(buscarPersona(vista.getTxtBuscarPer().getText()).getKey());

                }
            }
        });

        vista.getBtnBuscarPer().addActionListener(e -> vista.getCmbPersona().setSelectedItem(buscarPersona(vista.getTxtBuscarPer().getText()).getKey()));

        vista.getBtnResetear().addActionListener(e -> resetForm());

        vista.getBtnCancelar().addActionListener(e -> {
            vista.dispose();
            destruirVariables();
        });

        vista.getBtnGuardar().addActionListener(e -> guardar());

    }

    //METODOS DE APOYO
    private void cargarComoPersonas() {
        try {
            listaPersonas
                    .entrySet()
                    .stream()
                    .forEach(obj -> {
                        if (!vista.isClosed()) {
                            vista.getCmbPersona().addItem(obj.getKey());
                        } else {
                            throw new BreakException();
                        }
                    });

        } catch (BreakException e) {
            destruirVariables();
        }

    }

    private void resetForm() {
        if (!vista.getTxtUsername().getText().equals("ROOT")) {
            vista.getTxtUsername().setText("");
        }
        vista.getTxtPassword().setText("");
        vista.getTxtBuscarPer().setText("");
        vista.getCmbPersona().setSelectedIndex(0);
    }

    private void activarFormulario(boolean estado) {
        vista.getTxtUsername().setEnabled(estado);
        vista.getTxtPassword().setEnabled(estado);
        vista.getCmbPersona().setEnabled(estado);
        vista.getTxtBuscarPer().setEnabled(estado);
        vista.getBtnResetear().setEnabled(estado);
        vista.getBtnGuardar().setEnabled(estado);
    }

    private void destruirVariables() {
        modelo = null;
        vista = null;
        listaPersonas = null;
        System.gc();
    }

    protected UsuarioBD getObj() {
        modelo = new UsuarioBD();

        modelo.setUsername(vista.getTxtUsername().getText());

        modelo.setPassword(vista.getTxtPassword().getText());

        modelo.setPersona(buscarPersona(vista.getTxtBuscarPer().getText()).getValue());

        modelo.setEstado(true);

        return modelo;
    }

    private Map.Entry<String, PersonaMD> buscarPersona(String aguja) {
        return listaPersonas
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().getIdentificacion().contains(aguja))
                .findAny()
                .get();
    }

    protected boolean validarFormulario() {
        if (!vista.getTxtUsername().getText().isEmpty()) {
            if (!vista.getTxtPassword().getText().isEmpty()) {
                return true;
            } else {
                JOptionPane.showMessageDialog(vista, "INGRESE UNA CONTRASEñA".toUpperCase());
            }
        } else {
            JOptionPane.showMessageDialog(vista, "INGRESE UN NOMBRE DE USUARIO");
        }
        return false;
    }

    //EVENTOS
    public abstract void guardar();
}
