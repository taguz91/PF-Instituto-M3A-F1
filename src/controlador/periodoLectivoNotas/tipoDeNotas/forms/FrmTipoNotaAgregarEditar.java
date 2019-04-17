package controlador.periodoLectivoNotas.tipoDeNotas.forms;

import controlador.periodoLectivoNotas.tipoDeNotas.VtnTipoNotasCTR;
import java.awt.event.ActionEvent;
import modelo.tipoDeNota.TipoDeNotaBD;
import org.springframework.util.StringUtils;
import vista.periodoLectivoNotas.FrmTipoNota;
import vista.principal.VtnPrincipal;

/**
 *
 * @author MrRainx
 */
public class FrmTipoNotaAgregarEditar extends AbstracForm {

    public FrmTipoNotaAgregarEditar(VtnPrincipal desktop, FrmTipoNota vista, TipoDeNotaBD modelo, VtnTipoNotasCTR vtnPadre, String Funcion) {
        super(desktop, vista, modelo, vtnPadre, Funcion);
    }

    //INITS
    public void InitEditar() {
        vista.setTitle(StringUtils.capitalize("editar tipo de nota"));
        if (COMPLETED) {
            activarFormulario(true);
        }
    }
    
    private void setObjInForm(){
        
    }
    
    //EVENTOS
    @Override
    protected void btnGuardar(ActionEvent e) {

    }

}
