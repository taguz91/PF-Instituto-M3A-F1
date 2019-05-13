
package controlador.usuario;

import controlador.Libraries.Effects;
import controlador.accesos.FrmAccesosDeRolCTR;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.usuario.UsuarioBD;
import vista.principal.VtnPrincipal;
import vista.usuario.VtnPerfilUsuario;

public class VtnPerfilUsuarioCTR {
    
    private VtnPerfilUsuario vista;
    private UsuarioBD modelo;
    private VtnPrincipal desktop;

    public VtnPerfilUsuarioCTR(VtnPerfilUsuario vista, UsuarioBD modelo, VtnPrincipal desktop) {
        this.vista = vista;
        this.modelo = modelo;
        this.desktop = desktop;
    }

    public VtnPerfilUsuarioCTR() {
    }
    
    /*
        INIT
    */
    private void Init(){
        vista.show();
        desktop.getDpnlPrincipal().add(vista);
        Effects.centerFrame(vista, desktop.getDpnlPrincipal());
        try {
            vista.setSelected(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(FrmAccesosDeRolCTR.class.getName()).log(Level.SEVERE, null, e);
        }
        
        InitEventos();
    }
    
    private void InitEventos(){
        
        vista.getBtnCancelar().addActionListener(e-> btnCancelarActionPerformance(e));
        vista.getBtnGuardar().addActionListener(e-> btnGuardarActionPerformance(e));
        
    }
    
    /*
        METODOS DE APOYO
    */
    
    
    
    
    /*
        EVENTOS
    */
    
    
    private void btnGuardarActionPerformance(ActionEvent e){
        
    }
    
    private void btnCancelarActionPerformance(ActionEvent e){
        vista.dispose();
    }
    
}
