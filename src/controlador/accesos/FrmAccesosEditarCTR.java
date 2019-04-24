
package controlador.accesos;

import controlador.Libraries.Middlewares;
import controlador.notas.VtnActivarNotasCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConectarDB;
import modelo.accesos.AccesosBD;
import modelo.accesos.AccesosMD;
import vista.accesos.FrmAccesosEditar;
import vista.accesos.VtnAccesos;
import vista.principal.VtnPrincipal;

public class FrmAccesosEditarCTR {
    
    private VtnPrincipal desktop;
    private FrmAccesosEditar vista;
    private AccesosBD modelo;
    private ConectarDB conecta;


    //Listas
    private List<AccesosMD> listaAccesosbd;

    public FrmAccesosEditarCTR(VtnPrincipal desktop, FrmAccesosEditar vista, AccesosBD modelo, ConectarDB conecta) {
        this.desktop = desktop;
        this.vista = vista;
        this.modelo = modelo;
        this.conecta = conecta;
    }

    public FrmAccesosEditarCTR() {
       
    }   
    
    //Init
    
    public void Init(){
        
        vista.show();
        desktop.getDpnlPrincipal().add(vista);
        Middlewares.centerFrame(vista, desktop.getDpnlPrincipal());
        try {
            vista.setSelected(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(FrmAccesosDeRolCTR.class.getName()).log(Level.SEVERE, null, e);
        }
        InitEventos();
        
    }
    
    
    //InitEventos
    
    private void InitEventos(){
        
        vista.getBtnCancelar().addActionListener(e-> btnCancelarActionPerformance(e));
        vista.getBtnGuardar().addActionListener(e-> btnGuardarActionPerformance(e));
        
    }
    
    //Metodos de apoyo
    
    
    
    //Eventos
    
    private void btnGuardarActionPerformance(ActionEvent e){
        
    }
    
    private void btnCancelarActionPerformance(ActionEvent e){
        vista.dispose();
    }
    
    
}
