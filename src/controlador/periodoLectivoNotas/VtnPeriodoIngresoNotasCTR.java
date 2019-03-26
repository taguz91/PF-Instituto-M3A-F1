/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.periodoLectivoNotas;

import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import modelo.accesos.AccesosBD;
import modelo.accesos.AccesosMD;
import modelo.periodoIngresoNotas.PeriodoIngresoNotasBD;
import modelo.periodoIngresoNotas.PeriodoIngresoNotasMD;
import modelo.usuario.RolBD;
import vista.periodoLectivoNotas.VtnPeriodoIngresoNotas;
import vista.principal.VtnPrincipal;

/**
 *
 * @author MrRainx
 */
public class VtnPeriodoIngresoNotasCTR {

    private VtnPrincipal desktop;
    private VtnPeriodoIngresoNotas vista;
    private PeriodoIngresoNotasBD modelo;
    private RolBD permisos;

    //Tabla
    private DefaultTableModel tablaPeriodoNotas;

    //listas
    private List<PeriodoIngresoNotasMD> listaPeriodoNotas;

    public VtnPeriodoIngresoNotasCTR(VtnPrincipal desktop, VtnPeriodoIngresoNotas vista, PeriodoIngresoNotasBD modelo, RolBD permisos, DefaultTableModel tablaPeriodoNotas) {
        this.desktop = desktop;
        this.vista = vista;
        this.modelo = modelo;
        this.permisos = permisos;
        this.tablaPeriodoNotas = tablaPeriodoNotas;
    }

    //Inits
    public void Init() {

        tablaPeriodoNotas = (DefaultTableModel) vista.getTblPeriodoIngresoNotas().getModel();

        InitEventos();
        try {

            desktop.getDpnlPrincipal().add(vista);
            vista.show();
            vista.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(VtnPeriodoIngresoNotasCTR.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void InitEventos() {

        vista.getBtnEditar().addActionListener(e -> btnEditarActionPerformance(e));
        vista.getBtnEliminar().addActionListener(e -> btnEliminarActionPerformance(e));
        vista.getBtnBuscar().addActionListener(e -> btnBuscarActionPerformance(e));
        vista.getBtnIngresar().addActionListener(e -> btnIngresarActionPerformance(e));
    }

    private void InitPermisos() {
        for (AccesosMD obj : AccesosBD.SelectWhereACCESOROLidRol(permisos.getId())) {

            //HACER LOS IFS DE LOS PERMISOS
        }
    }

    //Metodos de Apoyo
    public void cargarTabla() {

    }

    //Procesadores de eventos
    private void btnEditarActionPerformance(ActionEvent e) {

    }

    private void btnEliminarActionPerformance(ActionEvent e) {

    }

    private void btnBuscarActionPerformance(ActionEvent e) {

    }

    private void btnIngresarActionPerformance(ActionEvent e) {

    }

}
