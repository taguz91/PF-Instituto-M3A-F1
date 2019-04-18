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
public class FrmTipoNotaEditar extends AbstracForm {

    protected Integer PK = null;

    public FrmTipoNotaEditar(VtnPrincipal desktop, FrmTipoNota vista, TipoDeNotaBD modelo, VtnTipoNotasCTR vtnPadre) {
        super(desktop, vista, modelo, vtnPadre);
    }

    //INITS
    public void InitEditar() {

        Init();
        PK = modelo.getIdTipoNota();
        vista.setTitle(StringUtils.capitalize("editar tipo de nota"));
        setObjInForm();
        activarFormulario(true);
    }

    private void setObjInForm() {
        vista.getCmbCarrera().setSelectedItem(modelo.getCarrera().getNombre());
        vista.getTxtNotaMax().setText(modelo.getValorMaximo() + "");
        vista.getTxtNotaMin().setText(modelo.getValorMinimo() + "");
        vista.getCmbTipoDeNota().setSelectedItem(modelo.getNombre());
    }

    //EVENTOS
    @Override
    protected void btnGuardar(ActionEvent e) {
        if (validarFormulario()) {
            if (setObj().editar(PK)) {
                String MENSAJE = "SE HA EDITADO ELTIPO DE NOTA";
                JOptionPane.showMessageDialog(vista, MENSAJE);
                Middlewares.setTextInLabelWithColor(vtnPadre.getVista().getLblEstado(), MENSAJE, 2, Middlewares.SUCCESS_COLOR);
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(vista, "HA OCURRIDO UN PROBLEMA");
            }
        }
    }

}
