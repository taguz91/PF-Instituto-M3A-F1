/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.fichas.salud;

import controlador.Libraries.Effects;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.fichas.FichaSaludBD;
import modelo.fichas.FichaSaludMD;
import vista.fichas.salud.VtnFichaSalud;
import vista.principal.VtnPrincipal;

/**
 *
 * @author MrRainx
 */
public class VtnFsaludCTR {

    private VtnPrincipal desktop;
    private VtnFichaSalud vista;
    private DefaultTableModel table;
    private FichaSaludBD modelo;

    private List<FichaSaludMD> lista;

    public VtnFsaludCTR(VtnPrincipal desktop, VtnFichaSalud vista) {
        this.desktop = desktop;
        this.vista = vista;
        this.modelo = new FichaSaludBD();
    }

    public void Init() {
        Effects.addInDesktopPane(vista, desktop.getDpnlPrincipal());
        table = (DefaultTableModel) vista.getTbl().getModel();
        lista = modelo.getFichas();
        cargarTabla();
        InitEventos();
    }

    private void InitEventos() {

        vista.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                cargarTablaFilter(vista.getTxtBuscar().getText().toLowerCase());
            }
        });

        vista.getBtnGenerarFicha().addActionListener(e -> btnGenerarFicha(e));
    }

    private void cargarTabla() {
        table.setRowCount(0);
        lista.forEach(agregarFilas());
        setResultados();
    }

    private void cargarTablaFilter(String aguja) {
        table.setRowCount(0);
        lista.stream()
                .filter(item
                        -> item.getPersona().getInfo().toLowerCase().contains(aguja)
                || item.getPersona().getNombreCompleto().toLowerCase().contains(aguja)
                ).forEach(agregarFilas());
        setResultados();
    }

    private void setResultados() {
        vista.getLblResultados().setText(table.getDataVector().size() + " Resultados");
    }

    private Consumer<FichaSaludMD> agregarFilas() {

        return obj -> {
            table.addRow(new Object[]{
                table.getDataVector().size() + 1,
                obj.getId(),
                obj.isEstadoRevision(),
                obj.isEstadoEnvio(),
                obj.getPersona().getInfo()
            });
        };
    }

    private int getRow() {
        return vista.getTbl().getSelectedRow();
    }

    private void btnGenerarFicha(ActionEvent e) {
        int row = getRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(vista, "SELECCIONE UNA FILA DE LA TABLA!!");

        } else {

            Thread thread = new Thread(() -> {
                int idFicha = (Integer) table.getValueAt(getRow(), 1);

                Map params = new HashMap();

            });

            thread.start();

        }

    }

}
