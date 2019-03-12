/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.periodoLectivoNotas;

import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import vista.periodoLectivoNotas.VtnPeriodoIngresoNotas;
import vista.principal.VtnPrincipal;

/**
 *
 * @author USUARIO
 */
public class VtnPeriodoIngresoNotasCTR {
    
    private VtnPrincipal desktop;
    private VtnPeriodoIngresoNotas vista;
    
    
    private DefaultTableModel modelT;
    
    //Inits
    
    public void Init(){
        desktop.getDpnlPrincipal().add(vista);
        vista.show();
        modelT = (DefaultTableModel) vista.getTblPeriodoIngresoNotas().getModel();
        
    }
    
    private void InitEventos(){
        
        vista.getBtnEditar().addActionListener( e -> btnEditarActionPerformance(e));
        vista.getBtnEliminar().addActionListener(e ->btnEliminarActionPerformance(e) );
        vista.getBtnBuscar().addActionListener(e -> btnBuscarActionPerformance(e));
        vista.getBtnIngresar().addActionListener(e ->btnIngresarActionPerformance(e) );
    }
    
    
    //Metodos de Apoyo

    
    
    //Procesadores de eventos
    
    private void btnEditarActionPerformance(ActionEvent e){
        
    }
    
    private void btnEliminarActionPerformance(ActionEvent e){
    
    }
    
    private void btnBuscarActionPerformance(ActionEvent e){
    
    }
    
    private void btnIngresarActionPerformance(ActionEvent e){
    
    } 
    
}
