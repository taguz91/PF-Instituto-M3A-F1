package controlador.usuario;

import controlador.usuario.Roles.forms.FrmAsignarRolCTR;
import controlador.Libraries.Middlewares;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import modelo.ConectarDB;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import modelo.usuario.RolesDelUsuarioBD;
import modelo.usuario.UsuarioBD;
import vista.principal.VtnPrincipal;
import vista.usuario.FrmAsignarRoles;
import vista.usuario.FrmUsuario;

/**
 *
 * @author USUARIO
 */
public class FrmUsuarioCTR {

    private final VtnPrincipal desktop;
    private final FrmUsuario vista;
    private final UsuarioBD modelo;
    private final String Funcion;


    /*
        Listas
     */
    private Map<String, DocenteMD> listaPersonas;

    private String Pk;

    //
    private String USER;

    public FrmUsuarioCTR(VtnPrincipal desktop, FrmUsuario vista, UsuarioBD modelo, String Funcion, ConectarDB conexion) {
        this.desktop = desktop;
        this.vista = vista;
        this.modelo = modelo;
        this.Funcion = Funcion;
    }

    //INICIADORES
    public void Init() {

        InitEventos();

        new Thread(() -> {
            listaPersonas = DocenteBD.selectDocentes();
            cargarComoPersonas();
            if (Funcion.equals("Agregar")) {
                vista.setTitle("Agregar Un Nuevo Usuario");
                USER = UsuarioBD.SelectNewUsername();
                vista.getTxtUsername().setText(USER);
            } else {
                vista.setTitle("Editar Un Usuario");

                vista.getBtnGuardar().setText("Guardar");

                Pk = modelo.getUsername();
                InitEditar();
            }

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

    private void InitEditar() {

        vista.getTxtUsername().setText(modelo.getUsername());

        listaPersonas
                .entrySet()
                .stream()
                .filter(item -> item.getValue().getIdPersona() == modelo.getPersona().getIdPersona())
                .collect(Collectors.toList())
                .forEach(obj -> {
                    
                    vista.getCmbPersona().setSelectedItem(obj.getKey());

                });

        if (vista.getTxtUsername().getText().equals("ROOT")) {
            vista.getTxtUsername().setEnabled(false);
            vista.getBtnResetear().setEnabled(false);
        }

    }

    private void InitEventos() {

        vista.getBtnBuscarPer().addActionListener(e -> btnBuscarActionPerformance(e));

        if (Funcion.equals("Agregar")) {
            vista.getBtnGuardar().addActionListener(e -> btnAgregarActionPerformance(e));
        } else {
            vista.getBtnGuardar().addActionListener(e -> btnEditarActionPerformance(e));
        }

        vista.getBtnCancelar().addActionListener(e -> btnCancelarActionPerformance(e));

        vista.getBtnResetear().addActionListener(e -> btnResetarActionPerformance(e));

        vista.getBtnBuscarPer().addActionListener(e -> btnBuscarAtionPerformance(e));

        vista.getTxtBuscarPer().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                txtBuscarPerOnKeyReleased(e);
            }
        });

        vista.getTxtUsername().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                txtUsernameOnKeyTyped(e);
            }
        });

        vista.getChxDefinido().addActionListener(e -> chxDefinido(e));

    }

    //METODOS DE APOYO
    private void cargarComoPersonas() {
        listaPersonas
                .entrySet()
                .stream()
                .forEach(obj -> {
                    vista.getCmbPersona().addItem(obj.getKey());
                });

    }

    private void buscarPersona() {

        String buscar = vista.getTxtBuscarPer().getText();
        listaPersonas
                .entrySet()
                .stream()
                .filter(item -> item.getKey().contains(buscar))
                .collect(Collectors.toList())
                .forEach(obj -> {
                    vista.getCmbPersona().setSelectedItem(obj.getKey());
                });

    }

    private boolean validacion() {

        if (!vista.getTxtUsername().getText().isEmpty()) {
            if (!vista.getTxtPassword().getText().isEmpty()) {
                if (vista.getCmbPersona().getSelectedIndex() != 0) {
                    return true;
                }

            }
        }

        return false;
    }

    private void setModelo() {
        modelo.setUsername(vista.getTxtUsername().getText());
        modelo.setPassword(vista.getTxtPassword().getText());

        String persona = (String) vista.getCmbPersona().getSelectedItem();

        int posInicial = persona.indexOf(" ");

        String identificacion = persona.substring(0, posInicial);

        Map<String, DocenteMD> listaTemporal = listaPersonas
                .entrySet()
                .stream()
                .filter(item -> item.getKey().contains(identificacion))
                .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));

        if (listaTemporal.isEmpty()) {

        } else {
            listaTemporal
                    .entrySet()
                    .stream()
                    .forEach(obj -> {

                        modelo.setPersona(obj.getValue());

                    });
        }

    }

    //EVENTOS
    private void btnAgregarActionPerformance(ActionEvent e) {

        if (validacion()) {

            setModelo();

            FrmAsignarRolCTR formAsignar = new FrmAsignarRolCTR(desktop, new FrmAsignarRoles(), new RolesDelUsuarioBD(), modelo, "Asignar-Roles-Usuario");
            formAsignar.Init();
            vista.dispose();

        } else {
            JOptionPane.showMessageDialog(desktop, "RELLENE CORRECTAMENTE EL FORMULARIO");
        }

    }

    private void btnCancelarActionPerformance(ActionEvent e) {
        vista.dispose();
    }

    private void btnBuscarActionPerformance(ActionEvent e) {

    }

    private void btnEditarActionPerformance(ActionEvent e) {

        if (validacion()) {

            setModelo();

            if (modelo.editar(Pk)) {
                JOptionPane.showMessageDialog(desktop, "SE HA EDITADO AL USUARIO CORRECTAMENTE");
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(desktop, "YA HAY UN USUARIO O PERSONA \n"
                        + "CON ESE NOMBRE DE USUARIO");
            }

        } else {
            JOptionPane.showMessageDialog(desktop, "RELLENE CORRECTAMENTE EL FORMULARIO");
        }
    }

    private void btnResetarActionPerformance(ActionEvent e) {

        vista.getTxtUsername().setText("");
        vista.getTxtPassword().setText("");
        vista.getTxtBuscarPer().setText("");
        vista.getCmbPersona().setSelectedIndex(0);

    }

    private void btnBuscarAtionPerformance(ActionEvent e) {

        buscarPersona();

    }

    private void txtBuscarPerOnKeyReleased(KeyEvent e) {

        if (e.getKeyCode() == 10) {
            buscarPersona();
        }

    }

    private void txtUsernameOnKeyTyped(KeyEvent e) {

        if (vista.getTxtUsername().getText().length() < 10) {

            char caracter = e.getKeyChar();
            if (Character.isLowerCase(caracter)) {
                String texto = ("" + caracter).toUpperCase();
                caracter = texto.charAt(0);
                e.setKeyChar(caracter);
            }
        } else {
            char caracter = e.getKeyChar();
            if (Character.isLetter(caracter)) {
                vista.getToolkit().beep();
                e.consume();
            }
        }

    }

    private void chxDefinido(ActionEvent e) {

        boolean seleccion = vista.getChxDefinido().isSelected();

        if (seleccion) {
            vista.getTxtUsername().setText("");
            vista.getTxtUsername().setEditable(true);
        } else {
            vista.getTxtUsername().setEditable(false);
            vista.getTxtUsername().setText(USER);
        }

    }
}
