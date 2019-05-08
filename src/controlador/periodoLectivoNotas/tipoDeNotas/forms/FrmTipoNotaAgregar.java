package controlador.periodoLectivoNotas.tipoDeNotas.forms;

import controlador.periodoLectivoNotas.tipoDeNotas.VtnTipoNotasCTR;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import modelo.tipoDeNota.TipoDeNotaBD;
import vista.periodoLectivoNotas.FrmTipoNota;
import vista.principal.VtnPrincipal;

/**
 *
 * @author MrRainx
 */
public class FrmTipoNotaAgregar extends AbstracForm {

    public FrmTipoNotaAgregar(VtnPrincipal desktop, FrmTipoNota vista, TipoDeNotaBD modelo, VtnTipoNotasCTR vtnPadre) {
        super(desktop, vista, modelo, vtnPadre);
    }

    //INITS
    public void InitAgregar() {

        Init();

        vista.setTitle("Agregar Nueva Nota");

        activarFormulario(true);

    }

    //EVENTOS
    @Override
    protected void btnGuardar(ActionEvent e) {
        if (validarFormulario()) {
            if (setObj().insertar()) {
                String MENSAJE = "SE HA AGREGADO EL NUEVO TIPO DE NOTA";
                JOptionPane.showMessageDialog(vista, MENSAJE);
                vtnPadre.cargarTabla();
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(vista, "HA OCURRIDO UN PROBLEMA");
            }
        }

    }

}
