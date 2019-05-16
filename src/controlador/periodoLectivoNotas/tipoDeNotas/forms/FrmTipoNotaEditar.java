package controlador.periodoLectivoNotas.tipoDeNotas.forms;

import controlador.periodoLectivoNotas.tipoDeNotas.VtnTipoNotasCTR;
import java.awt.event.ActionEvent;
import modelo.tipoDeNota.TipoDeNotaBD;
import vista.periodoLectivoNotas.FrmTipoNota;
import vista.principal.VtnPrincipal;

/**
 *
 * @author MrRainx
 */
public class FrmTipoNotaEditar extends AbstracForm {

    protected Integer PK = null;

    public FrmTipoNotaEditar(VtnPrincipal desktop, FrmTipoNota vista, TipoDeNotaBD modelo, VtnTipoNotasCTR vtnPadre) {
        super(desktop, vista, modelo, vtnPadre);
    }
    
    

    //INITS
    public void InitEditar() {

        Init();

        PK = modelo.getIdTipoNota();

        vista.setTitle("Editar Tipo De Nota");
    }
    
    
    
    
    
    
    
    
    
    //EVENTOS
    @Override
    protected void btnGuardar(ActionEvent e) {
        
        
        
        
        
        
    }

}
