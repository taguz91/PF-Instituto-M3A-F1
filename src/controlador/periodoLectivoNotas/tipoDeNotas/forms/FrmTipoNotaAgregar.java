package controlador.periodoLectivoNotas.tipoDeNotas.forms;

import controlador.Libraries.Middlewares;
import controlador.periodoLectivoNotas.tipoDeNotas.VtnTipoNotasCTR;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import modelo.tipoDeNota.TipoDeNotaBD;
import org.springframework.util.StringUtils;
import vista.periodoLectivoNotas.FrmTipoNota;
import vista.principal.VtnPrincipal;

/**
 *
 * @author MrRainx
 */
public class FrmTipoNotaAgregar extends AbstracForm {
    
    public FrmTipoNotaAgregar(VtnPrincipal desktop, FrmTipoNota vista, TipoDeNotaBD modelo, VtnTipoNotasCTR vtnPadre, String Funcion) {
        super(desktop, vista, modelo, vtnPadre, Funcion);
    }

    //INITS
    public void InitAgregar() {
        vista.setTitle(StringUtils.capitalize("agregar nueva nota"));
    }

    //EVENTOS
    @Override
    protected void btnGuardar(ActionEvent e) {
        if (validarFormulario()) {
            if (setObj().insertar()) {
                String MENSAJE = "SE HA AGREGADO EL NUEVO TIPO DE NOTA";
                JOptionPane.showMessageDialog(vista, MENSAJE);
                Middlewares.setTextInLabelWithColor(vtnPadre.getVista().getLblEstado(), MENSAJE, 2, Middlewares.SUCCESS_COLOR);
            } else {
                JOptionPane.showMessageDialog(vista, "HA OCURRIDO UN PROBLEMA");
            }
        }
        
    }
    
}
