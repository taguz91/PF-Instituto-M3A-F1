/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.persona;

import controlador.principal.DVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JOptionPane;
import modelo.persona.PersonaBD;
import modelo.persona.PersonaMD;
import modelo.validaciones.CmbValidar;
import modelo.validaciones.TxtVCedula;
import modelo.validaciones.Validar;
import vista.persona.JDEditarIdentificacion;

/**
 *
 * @author Linis
 */
public class JDEditarIdentificacionCTR extends DVtnCTR {

    private final PersonaBD personaBD;
    private PersonaMD personaMD;
    private final JDEditarIdentificacion jdEditarIdentificacion;
    private final String identificacion, nombrePersona;
    private int idPersona = 0;
    private boolean editar = false;
    private int numAccion = 2;
    private TxtVCedula valCe;

    public JDEditarIdentificacionCTR(VtnPrincipalCTR ctrPrin, String identificacion, String nombrePersona) {
        super(ctrPrin);
        this.personaBD = new PersonaBD(ctrPrin.getConecta());
        this.identificacion = identificacion;
        this.nombrePersona = nombrePersona;
        this.jdEditarIdentificacion = new JDEditarIdentificacion(ctrPrin.getVtnPrin(), false);
        this.jdEditarIdentificacion.setLocationRelativeTo(ctrPrin.getVtnPrin());
        this.jdEditarIdentificacion.setVisible(true);
        this.jdEditarIdentificacion.setTitle("Editar Identificacion");

    }

    public void iniciar() {
        //Clase para mostrar el tecto en Negrita
        Font negrita = new Font("Tahoma", Font.BOLD, 13);
        jdEditarIdentificacion.getLblNombrePersona().setFont(negrita);
        jdEditarIdentificacion.getLblNombrePersona().setText(nombrePersona);
        //Desactivamos el campo de identificacion porque debe ingresar primero el tipo de identificacion
        jdEditarIdentificacion.getTxtIdentificacion().setEnabled(false);
        personaMD = personaBD.buscarPersona(identificacion);
        //Ocultamos todos los erores del formulario 
        iniciarComponentes();
        //Cuando se realice una accion en alguno de esos combos 
        iniciarValidaciones();
        jdEditarIdentificacion.getBtnCancelar().addActionListener(e -> cancelar());
        jdEditarIdentificacion.getBtnGuardar().addActionListener(e -> guardarIdentificacion());
        //Validacion de la cedula
        valCe = new TxtVCedula(jdEditarIdentificacion.getTxtIdentificacion(), jdEditarIdentificacion.getLblErrorIdentificacion());
        jdEditarIdentificacion.getTxtIdentificacion().addKeyListener(valCe);
        jdEditarIdentificacion.getCbx_Identificacion().addActionListener(e -> tipoID());
    }

    public void iniciarComponentes() {
        jdEditarIdentificacion.getCbx_Identificacion().setToolTipText("Seleccione un Tipo de Identificación");
        jdEditarIdentificacion.getTxtIdentificacion().setToolTipText("Ingrese una Identificación válida");
        jdEditarIdentificacion.getLblErrorIdentificacion().setVisible(false);

    }

    //Metodo para habilitar o deshabiltar el combo TipoID
    private void tipoID() {
        int pos = jdEditarIdentificacion.getCbx_Identificacion().getSelectedIndex();
        jdEditarIdentificacion.getTxtIdentificacion().setEnabled(false);
        switch (pos) {
            case 1:
                //Activamos el campo para ingresar los datos
                valCe.activarValidacion();
                jdEditarIdentificacion.getTxtIdentificacion().setEnabled(true);
                break;
            case 2:
                valCe.desactivarValidacion();
                jdEditarIdentificacion.getTxtIdentificacion().setBorder(
                        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
                jdEditarIdentificacion.getLblErrorIdentificacion().setVisible(false);
                //Activamos el campo para ingresar los datos
                jdEditarIdentificacion.getTxtIdentificacion().setEnabled(true);
                break;
            default:
                jdEditarIdentificacion.getTxtIdentificacion().setEnabled(false);
                break;
        }
    }

    private void cancelar() {
        jdEditarIdentificacion.dispose();
    }

    public void guardarIdentificacion() {
        String identifi;
        int tipoIdentifi;
        boolean guardar = true;

        identifi = jdEditarIdentificacion.getTxtIdentificacion().getText().trim().toUpperCase();

        tipoIdentifi = jdEditarIdentificacion.getCbx_Identificacion().getSelectedIndex();
        if (tipoIdentifi == 1) {
            if (!Validar.esCedula(identifi)) {
                guardar = false;
                jdEditarIdentificacion.getLblErrorIdentificacion().setVisible(true);
            } else {
                jdEditarIdentificacion.getLblErrorIdentificacion().setVisible(false);
            }
        } else {
            //Validar cuando es pasaporte 
        }

        if (guardar) {

            PersonaBD per = new PersonaBD(ctrPrin.getConecta());
            per.setIdentificacion(identifi);

            if (editar) {
                if (idPersona > 0) {
                    System.out.println("idPersona" + idPersona);
                    if (per.editarIdentificacion(idPersona)) {
                        JOptionPane.showMessageDialog(null, "Datos Editados Correctamente.");
                        iniciarComponentes();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo editar,\n"
                                + "Revise su conexion a internet. ");
                    }
                    editar = false;
                }
            } else {
                per.insertarIdentificacion();
                JOptionPane.showMessageDialog(null, "Datos guardados correctamente.");
                iniciarComponentes();
            }
            jdEditarIdentificacion.dispose();
            ctrPrin.cerradoJIF();
        } else {
            JOptionPane.showMessageDialog(null, "Existen errores en los campos\nRevise su información!!");
        }

    }

    public void editarIdentificacion(PersonaMD perEditar) {

        idPersona = perEditar.getIdPersona();
        System.out.println("id" + idPersona);
        editar = true;
        String cedula;
        cedula = perEditar.getIdentificacion();

        if (modelo.validaciones.Validar.esCedula(cedula) == true) {
            jdEditarIdentificacion.getCbx_Identificacion().setSelectedItem("CEDULA");
        } else {
            jdEditarIdentificacion.getCbx_Identificacion().setSelectedItem("PASAPORTE");
        }
        jdEditarIdentificacion.getTxtIdentificacion().setEnabled(true);
        jdEditarIdentificacion.getTxtIdentificacion().setText(perEditar.getIdentificacion());
        iniciarValidaciones();
    }

    public void habilitarBtnGuardar() {

        String TipoId, Identificacion;
        TipoId = jdEditarIdentificacion.getCbx_Identificacion().getSelectedItem().toString();
        Identificacion = jdEditarIdentificacion.getTxtIdentificacion().getText();
        if (jdEditarIdentificacion.getLblErrorIdentificacion().isVisible() == false) {
            if (TipoId.equals("SELECCIONE") == false
                    && Identificacion.equals("") == false) {
                jdEditarIdentificacion.getBtnGuardar().setEnabled(true);
            } else {
                jdEditarIdentificacion.getBtnGuardar().setEnabled(false);
            }
        } else {
            jdEditarIdentificacion.getBtnGuardar().setEnabled(false);
        }
    }

    private void iniciarValidaciones() {

        PropertyChangeListener habilitar_Guardar = (PropertyChangeEvent evt) -> {
            habilitarBtnGuardar();
        };
        if (numAccion > 1) {
            jdEditarIdentificacion.getCbx_Identificacion().addActionListener(new CmbValidar(
                    jdEditarIdentificacion.getCbx_Identificacion()));
            jdEditarIdentificacion.getCbx_Identificacion().addPropertyChangeListener(habilitar_Guardar);
        }
    }

}
