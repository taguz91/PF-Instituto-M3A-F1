/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.usuario;

import controlador.Libraries.Effects;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import modelo.ConectarDB;
import modelo.persona.PersonaBD;
import modelo.persona.PersonaMD;
import modelo.usuario.UsuarioBD;
import vista.principal.VtnPrincipal;
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
    private List<PersonaMD> listaPersonas;

    private String Pk;

    public FrmUsuarioCTR(VtnPrincipal desktop, FrmUsuario vista, UsuarioBD modelo, String Funcion) {
        this.desktop = desktop;
        this.vista = vista;
        this.modelo = modelo;
        this.Funcion = Funcion;
    }

    //INICIADORES
    public void Init() {

        InitEventos();

        PersonaBD persona = new PersonaBD(new ConectarDB("PersonaBD"));

        listaPersonas = persona.cargarPersonas();

        cargarComoPersonas();

        if (Funcion.equals("Agregar")) {
            vista.setTitle("Agregar Un Nuevo Usuario");
        } else {
            vista.setTitle("Editar Un Usuario");
            Pk = modelo.getUsername();
            InitEditar();
        }

        Effects.centerFrame(vista, desktop.getDpnlPrincipal());

        try {
            vista.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(FrmUsuarioCTR.class.getName()).log(Level.SEVERE, null, ex);
        }

        desktop.getDpnlPrincipal().add(vista);
        vista.show();

    }

    private void InitEditar() {

        vista.getTxtUsername().setText(modelo.getUsername());

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

    }

    //METODOS DE APOYO
    private void cargarComoPersonas() {

        listaPersonas
                .stream()
                .forEach(obj -> {
                    vista.getCmbPersona().addItem(obj.getIdentificacion() + " " + obj.getPrimerNombre() + " " + obj.getSegundoNombre() + " " + obj.getPrimerApellido() + " " + obj.getSegundoApellido());

                });

    }

    private void buscarPersona() {

        String buscar = vista.getTxtBuscarPer().getText();

//        int posInicial = persona.indexOf(" ");
//
//        String identificacion = persona.substring(0, posInicial);
        listaPersonas
                .stream()
                .filter(item -> item.getIdentificacion().contains(buscar))
                .collect(Collectors.toList())
                .forEach(obj -> {

                    vista.getCmbPersona().setSelectedItem(obj.getIdentificacion() + " " + obj.getPrimerNombre() + " " + obj.getSegundoNombre() + " " + obj.getPrimerApellido() + " " + obj.getSegundoApellido());

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

        List<PersonaMD> listaTemporal = listaPersonas
                .stream()
                .filter(item -> item.getIdentificacion().contains(identificacion))
                .collect(Collectors.toList());

        if (listaTemporal.isEmpty()) {

        } else {
            listaTemporal.forEach(obj -> {

                modelo.setIdPersona(obj.getIdPersona());
            });
        }

    }

    //EVENTOS
    private void btnAgregarActionPerformance(ActionEvent e) {

        if (validacion()) {

            setModelo();

            if (modelo.insertar()) {
                JOptionPane.showMessageDialog(desktop, "SE HA AGREGADO AL USUARIO CORRECTAMENTE");

                VtnUsuarioCTR.cargarTabla(new UsuarioBD().SelectAll());

                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(desktop, "YA HAY UN USUARIO O PERSONA \n"
                        + "CON ESE NOMBRE DE USUARIO");
            }
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

}
