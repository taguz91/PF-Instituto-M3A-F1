package modelo.tipoDeNota;

import controlador.Libraries.Effects;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import vista.periodoLectivoNotas.FrmTipoNota;
import vista.principal.VtnPrincipal;

/**
 *
 * @author MrRainx
 */
public class FrmTipoNotaCTR {
    
    private VtnPrincipal desktop;
    private FrmTipoNota vista;
    private TipoDeNotaBD modelo;

    public FrmTipoNotaCTR(VtnPrincipal desktop, FrmTipoNota vista, TipoDeNotaBD modelo) {
        this.desktop = desktop;
        this.vista = vista;
        this.modelo = modelo;
    }
    
    //Init
    
    public void Init(){
        
        Effects.centerFrame(vista, desktop.getDpnlPrincipal());
        vista.show();
        desktop.add(vista);
        
        try {
            vista.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(FrmTipoNotaCTR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //metodos de Apoyo
    
    //procesadores de Eventos
    
    
}
