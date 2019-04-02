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
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.tipoDeNota.TipoDeNotaBD;
import modelo.tipoDeNota.TipoDeNotaMD;
import modelo.usuario.RolBD;
import vista.periodoLectivoNotas.FrmTipoNota;
import vista.periodoLectivoNotas.VtnTipoNotas;
import vista.principal.VtnPrincipal;

/**
 *
 * @author MrRainx
 */
public class VtnTipoNotasCTR {

    private final VtnPrincipal desktop;
    private final VtnTipoNotas vista;
    private TipoDeNotaBD modelo;
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
        vista.setVisible(true);

        Effects.centerFrame(vista, desktop.getDpnlPrincipal());
        tablaTiposNotas = (DefaultTableModel) vista.getTblTipoNotas().getModel();

        InitEventos();
        cargarTabla();
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

    //METODOS DE APOYO
    public void cargarTabla() {
        tablaTiposNotas.setRowCount(0);
        listaTiposNotas = TipoDeNotaBD.SelectAll();

        for (TipoDeNotaMD obj : listaTiposNotas) {
            if (vista.isVisible()) {
                agregarFila(listaTiposNotas.indexOf(obj) + 1, obj);
            } else {
                listaTiposNotas = null;
                System.gc();
                break;
            }
        }

    }

    private static void agregarFila(int indice, TipoDeNotaMD obj) {

        tablaTiposNotas.addRow(new Object[]{
            indice,
            obj.getIdTipoNota(),
            obj.getNombre(),
            obj.getValorMinimo(),
            obj.getValorMaximo(),
            obj.getFechaCreacion()

        });

    }

    private void setModeloFromTabla(int fila) {

        int idTipoNota = (Integer) vista.getTblTipoNotas().getValueAt(fila, 0);

        listaTiposNotas
                .stream()
                .filter(item -> item.getIdTipoNota() == idTipoNota)
                .collect(Collectors.toList())
                .forEach(obj -> {
                    modelo = new TipoDeNotaBD(obj);
                });

    }

    //PROCESADORES DE EVENTOS
    private void btnEditarActionPerformance(ActionEvent e) {

        int fila = vista.getTblTipoNotas().getSelectedRow();

        if (fila != -1) {

            setModeloFromTabla(fila);
            FrmTipoNotaCTR form = new FrmTipoNotaCTR(desktop, new FrmTipoNota(), modelo, this, "Editar");
            form.Init();

        } else {
            JOptionPane.showMessageDialog(vista, "SELECCIONE UNA FILA");
        }

    }

    private void btnEliminarActionPerformance(ActionEvent e) {

        int fila = vista.getTblTipoNotas().getSelectedRow();
        if (fila != -1) {

            setModeloFromTabla(fila);

            int opcion = JOptionPane.showConfirmDialog(vista, "ESTA SEGURO DE ELIMINAR LA NOTA: " + modelo.getNombre());

            if (opcion == 0) {

                if (modelo.eliminar(modelo.getIdTipoNota())) {

                    JOptionPane.showMessageDialog(vista, "SE HA ELIMINADO LA NOTA: " + modelo.getNombre());

                    desktop.getLblEstado().setText("SE HA ELIMINADO LA NOTA: " + modelo.getNombre());

                    cargarTabla();

                } else {

                    JOptionPane.showMessageDialog(vista, "HA DECIDIDO NO ELIMINAR NADA");

                }

            }

        } else {
            JOptionPane.showMessageDialog(vista, "SELECCIONE UNA FILA!!!");

        }

    }

    private void btnIngresarActionPerformance(ActionEvent e) {

        FrmTipoNotaCTR form = new FrmTipoNotaCTR(desktop, new FrmTipoNota(), new TipoDeNotaBD(), this, "Agregar");
        form.Init();

    }

    private void txtBuscarOnKeyReleased(KeyEvent e) {

        String Aguja = vista.getTxtBuscar().getText().toLowerCase();
        tablaTiposNotas.setRowCount(0);
        if (Aguja.length() >= 3) {
            listaTiposNotas = modelo.SelectOneWhereNombre(Aguja);
            cargarTabla();
        } else if (Aguja.length() == 0) {
            listaTiposNotas = TipoDeNotaBD.SelectAll();
            cargarTabla();
        }

    }

    private void btnActualizarActionPerformance(ActionEvent e) {
        tablaTiposNotas.setRowCount(0);
        cargarTabla();
    }
}
