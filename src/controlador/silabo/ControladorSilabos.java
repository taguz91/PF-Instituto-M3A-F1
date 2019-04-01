/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import modelo.silabo.dbSilabo;
import modelo.usuario.UsuarioBD;

import vista.silabos.frmConfiguracionSilabo;

import vista.principal.VtnPrincipal;
import vista.silabos.frmCargando;
import vista.silabos.frmCargando1;
import vista.silabos.frmSilabos;

/**
 *
 * @author Andres Ullauri
 */
public class ControladorSilabos {

    private VtnPrincipal principal;

    private UsuarioBD usuario;

    private frmCargando1 carga;

    public ControladorSilabos(UsuarioBD usuario, VtnPrincipal principal) {
        this.principal = principal;
        this.usuario = usuario;
        this.carga = new frmCargando1();

    }

    public void iniciarControlador() {

        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                ControladorSilaboCRUD cs = new ControladorSilaboCRUD(new dbSilabo(), usuario, new frmConfiguracionSilabo());

                principal.getDpnlPrincipal().add(cs.getSetup());

                cs.getSetup().setTitle("Silabo");
                cs.getSetup().show();

                Dimension desktopSize = principal.getDpnlPrincipal().getSize();
                Dimension jInternalFrameSize = cs.getSetup().getSize();
                cs.getSetup().setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                        (desktopSize.height - jInternalFrameSize.height) / 2);

                ActionListener a2 = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae2) {

                        principal.getDpnlPrincipal().add(cs.getGestion());
                        cs.getGestion().show();

                        Dimension desktopSize = principal.getDpnlPrincipal().getSize();
                        Dimension jInternalFrameSize = cs.getGestion().getSize();
                        cs.getGestion().setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                                (desktopSize.height - jInternalFrameSize.height) / 2);
                        //cs.getGestion().setLocation(0, 0);
                        cs.getSetup().setVisible(false);
                        cs.getGestion().toFront();

                        ActionListener a3 = new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent ae2) {

                                principal.getDpnlPrincipal().remove(cs.getBibliografia());

                                principal.getDpnlPrincipal().add(cs.getBibliografia());

                                cs.getBibliografia().show();

                                Dimension desktopSize = principal.getDpnlPrincipal().getSize();
                                Dimension jInternalFrameSize = cs.getBibliografia().getSize();
                                cs.getBibliografia().setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                                        (desktopSize.height - jInternalFrameSize.height) / 2);
                                //cs.getGestion().setLocation(0, 0);
                                cs.getGestion().setVisible(false);
                                cs.getBibliografia().toFront();
                            }

                        };

                        cs.getGestion().getBtnSiguiente().addActionListener(a3);

                    }

                };

                cs.getSetup().getBtnSiguiente().addActionListener(a2);

                cs.getGestion().getBtnCancelar().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        int reply = JOptionPane.showConfirmDialog(null, "Esta seguro que desea cancelar el proceso?", "Cancelar", JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                            cs.getGestion().dispose();
                        }
                    }

                });

                cs.getBibliografia().getBtnCancelar().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        int reply = JOptionPane.showConfirmDialog(null, "Esta seguro que desea cancelar el proceso?", "Cancelar", JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                            cs.getBibliografia().dispose();
                            cs.getGestion().dispose();
                        }
                    }

                });

                //cs.getBibliografia().getBtnCancelar().addActionListener(amx3 -> principal.getMntConsultarSilabo().doClick());
                //cs.getGestion().getBtnAtras().addActionListener(a -> cs.getGestion().dispose());
                //cs.getSetup().getBtnCancelar().addActionListener(a -> cs.getSetup().dispose());
                //cs.getSetup().getBtnReporte().addActionListener(x->principal.getDkpSilabo().add(ControladorSilaboCRUD.fReporte()));
            }

        };
        principal.getMnIgSilabo().addActionListener(al);
        principal.getBtnIngresarSilabo().addActionListener(al);

        ActionListener am = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                ControladorSilaboCRUD cs = new ControladorSilaboCRUD(new dbSilabo(), usuario, new frmSilabos());

                principal.getDpnlPrincipal().add(cs.getSilabos());

                cs.getSilabos().setTitle("Silabos");
                cs.getSilabos().show();

                Dimension desktopSize = principal.getDpnlPrincipal().getSize();
                Dimension jInternalFrameSize = cs.getSilabos().getSize();
                cs.getSilabos().setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                        (desktopSize.height - jInternalFrameSize.height) / 2);
                //cs.getGestion().setLocation(0, 0);

                ActionListener am2 = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae2) {

                        principal.getDpnlPrincipal().remove(cs.getBibliografia());

                        principal.getDpnlPrincipal().add(cs.getGestion());

                        cs.getGestion().show();

                        //cs.getGestion().getBtnSiguiente().setEnabled(false);
                        Dimension desktopSize = principal.getDpnlPrincipal().getSize();
                        Dimension jInternalFrameSize = cs.getGestion().getSize();
                        cs.getGestion().setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                                (desktopSize.height - jInternalFrameSize.height) / 2);
                        //cs.getGestion().setLocation(0, 0);

                        cs.getSetup().setVisible(false);
                        cs.getGestion().toFront();

                        ActionListener a3 = new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent ae2) {

                                principal.getDpnlPrincipal().remove(cs.getBibliografia());

                                principal.getDpnlPrincipal().add(cs.getBibliografia());

                                cs.getBibliografia().show();

                                Dimension desktopSize = principal.getDpnlPrincipal().getSize();
                                Dimension jInternalFrameSize = cs.getBibliografia().getSize();
                                cs.getBibliografia().setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                                        (desktopSize.height - jInternalFrameSize.height) / 2);
                                //cs.getGestion().setLocation(0, 0);
                                cs.getGestion().setVisible(false);
                                cs.getBibliografia().toFront();
                            }

                        };
                        cs.getGestion().getBtnSiguiente().addActionListener(a3);

                    }

                };

                cs.getSilabos().getBtnEditar().addActionListener(am2);

                cs.getSilabos().getBtnEditar().addActionListener(amx1 -> cs.getSilabos().dispose());

                cs.getSilabos().getBtnEditar().addActionListener(amx3 -> principal.getMnCtSilabos().doClick());

                cs.getSilabos().getBtnNuevo().addActionListener(amx1 -> cs.getSilabos().dispose());

                cs.getSilabos().getBtnNuevo().addActionListener(amx2 -> principal.getMnIgSilabo().doClick());

                cs.getGestion().getBtnCancelar().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        int reply = JOptionPane.showConfirmDialog(null, "Esta que desea cancelar el proceso?", "Cancelar", JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                            cs.getGestion().dispose();
                        }
                    }

                });

                cs.getBibliografia().getBtnCancelar().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        int reply = JOptionPane.showConfirmDialog(null, "Esta que desea cancelar el proceso?", "Cancelar", JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                            cs.getBibliografia().dispose();
                            cs.getGestion().dispose();
                        }
                    }

                });
            }

        };

        principal.getMnCtSilabos().addActionListener(am);
        principal.getMnCtSilabos().addActionListener(msg->principal.getLblEstado().setText("Cargando la información de Silabos..."));
        principal.getMnIgSilabo().addActionListener(msg->principal.getLblEstado().setText("Configurando información de Silabo..."));
        principal.getBtnConsultarSilabo().addActionListener(msg->principal.getLblEstado().setText("Cargando la información de Silabos... "));
        principal.getBtnIngresarSilabo().addActionListener(msg->principal.getLblEstado().setText("Configurando información de Silabo..."));
        principal.getBtnConsultarSilabo().addActionListener(am);


        
    }

}
