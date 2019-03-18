/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.periodoLectivoNotas;

import controlador.Libraries.Effects;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.tipoDeNota.TipoDeNotaBD;
import modelo.tipoDeNota.TipoDeNotaMD;
import modelo.usuario.RolBD;
import vista.periodoLectivoNotas.VtnTipoNotas;
import vista.principal.VtnPrincipal;

/**
 *
 * @author MrRainx
 */
public class VtnTipoNotasCTR {

    private final VtnPrincipal desktop;
    private final VtnTipoNotas vista;
    private final TipoDeNotaBD modelo;
    private final RolBD permisos;

    private static List<TipoDeNotaMD> listaTiposNotas;

    private static DefaultTableModel tablaTiposNotas;

    public VtnTipoNotasCTR(VtnPrincipal desktop, VtnTipoNotas vista, TipoDeNotaBD modelo, RolBD permisos) {
        this.desktop = desktop;
        this.vista = vista;
        this.modelo = modelo;
        this.permisos = permisos;
    }

    //Inits
    public void Init() {

        Effects.centerFrame(vista, desktop.getDpnlPrincipal());
        tablaTiposNotas = (DefaultTableModel) vista.getTblTipoNotas().getModel();

        listaTiposNotas = modelo.SelectAll();

        InitEventos();
        cargarTabla();
        vista.show();
        desktop.getDpnlPrincipal().add(vista);
        try {
            vista.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(VtnTipoNotasCTR.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void InitEventos() {

        vista.getBtnEditar().addActionListener(e -> btnEditarActionPerformance(e));
        vista.getBtnEliminar().addActionListener(e -> btnEliminarActionPerformance(e));
        vista.getBtnIngresar().addActionListener(e -> btnIngresarActionPerformance(e));
        vista.getBtnActualizar().addActionListener(e -> btnActualizarActionPerformance(e));

        vista.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                txtBuscarOnKeyReleased(e);
            }
        });

    }

    //Metodos de Apoyo
    private void cargarTabla() {
        tablaTiposNotas.setRowCount(0);
        listaTiposNotas
                .stream()
                .forEach(VtnTipoNotasCTR::agregarFila);

    }

    private static void agregarFila(TipoDeNotaMD obj) {

        tablaTiposNotas.addRow(new Object[]{
            obj.getNombre(),
            obj.getValorMinimo(),
            obj.getValorMaximo(),
            obj.getFechaCreacion()
                
        });

    }

    //Procesadores de eventos
    private void btnEditarActionPerformance(ActionEvent e) {

    }

    private void btnEliminarActionPerformance(ActionEvent e) {

        int fila = vista.getTblTipoNotas().getSelectedRow();
        if (fila != -1) {

        } else {
            JOptionPane.showMessageDialog(vista, "SELECCIONE UNA FILA!!!");

        }

    }

    private void btnIngresarActionPerformance(ActionEvent e) {

    }

    private void txtBuscarOnKeyReleased(KeyEvent e) {

        String Aguja = vista.getTxtBuscar().getText().toLowerCase();
        tablaTiposNotas.setRowCount(0);
        if (Aguja.length() >= 3) {
            listaTiposNotas = modelo.SelectOneWhereNombre(Aguja);
            cargarTabla();
        } else if (Aguja.length() == 0) {
            listaTiposNotas = modelo.SelectAll();
            cargarTabla();
        }

    }

    private void btnActualizarActionPerformance(ActionEvent e) {
        tablaTiposNotas.setRowCount(0);
        cargarTabla();
    }
}
