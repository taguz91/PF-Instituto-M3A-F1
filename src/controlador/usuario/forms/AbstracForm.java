package controlador.usuario.forms;

import controlador.Libraries.Effects;
import controlador.Libraries.Validaciones;
import controlador.excepciones.BreakException;
import controlador.usuario.VtnUsuarioCTR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import modelo.CONS;
import modelo.persona.PersonaBD;
import modelo.persona.PersonaMD;
import modelo.usuario.UsuarioBD;
import modelo.usuario.UsuarioMD;
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
    private List<UsuarioMD> usuarios;

    //LISTAS
    protected Map<String, PersonaMD> listaPersonas;

    private final PersonaBD personaBD;

    public AbstracForm(VtnPrincipal desktop, VtnUsuarioCTR vtnPadre) {
        this.desktop = desktop;
        this.vtnPadre = vtnPadre;
        this.vista = new FrmUsuario();
        this.personaBD = new PersonaBD();
    }

    public synchronized void Init() {

        Effects.addInDesktopPane(vista, desktop.getDpnlPrincipal());
        InitEventos();
        activarFormulario(false);
        listaPersonas = personaBD.selectAll();
        cargarComoPersonas();
        activarFormulario(true);

    }

    public List<UsuarioMD> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioMD> usuarios) {
        this.usuarios = usuarios;
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

        vista.getTxtBuscarPer().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String cedula = vista.getTxtBuscarPer().getText();

                vista.getTxtUsername().setText(cedula);

            }

        });

        vista.getTxtUsername().addCaretListener(e -> {
            String username = vista.getTxtUsername().getText();
/*
            if (isCreated(username)) {
                vista.getTxtUsername().setBorder(CONS.ERR_BORDER);
                vista.getLblError().setVisible(true);
                vista.getBtnGuardar().setEnabled(false);
            } else {
                vista.getTxtUsername().setBorder(CONS.DEFAULT_BORDER);
                vista.getLblError().setVisible(false);
                vista.getBtnGuardar().setEnabled(true);
            }
           */     

        });

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

    protected void destruirVariables() {
        modelo = null;
        vista = null;
        listaPersonas = null;
        System.gc();
    }

    protected UsuarioBD getObj() {
        modelo = new UsuarioBD();

        modelo.setUsername(vista.getTxtUsername().getText());

        modelo.setPassword(vista.getTxtPassword().getText());

        modelo.setPersona(listaPersonas
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().contains(vista.getCmbPersona().getSelectedItem().toString()))
                .findAny()
                .get()
                .getValue()
        );

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

    private boolean isCreated(String username) {
        return usuarios.stream().map(c -> c.getUsername()).anyMatch(item -> item.equals(username));
    }

    //EVENTOS
    public abstract void guardar();

}
