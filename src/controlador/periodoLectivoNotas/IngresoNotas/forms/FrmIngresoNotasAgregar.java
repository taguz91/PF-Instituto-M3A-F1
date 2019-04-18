package controlador.periodoLectivoNotas.IngresoNotas.forms;

import controlador.periodoLectivoNotas.IngresoNotas.VtnPeriodoIngresoNotasCTR;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import modelo.periodoIngresoNotas.PeriodoIngresoNotasBD;
import vista.periodoLectivoNotas.FrmIngresoNotas;
import vista.principal.VtnPrincipal;

/**
 *
 * @author MrRainx
 */
public class FrmIngresoNotasAgregar extends AbstractForm {

    public FrmIngresoNotasAgregar(VtnPrincipal desktop, FrmIngresoNotas vista, PeriodoIngresoNotasBD modelo, VtnPeriodoIngresoNotasCTR vtnPadre) {
        super(desktop, vista, modelo, vtnPadre);
    }

    //INICIADORES
    public void InitEditar() {
        Init();
    }

    //EVENTOS
    @Override
    protected void btnGuardar(ActionEvent e) {
        if (validarFormulario()) {
            if (setObj().insertar()) {

                String MENSAJE = "SE HA AGREGADO EL PERIODO DE INGRESO DE NOTAS";

                JOptionPane.showMessageDialog(vista, MENSAJE);
            } else {
                JOptionPane.showMessageDialog(vista, "HA OCURRIDO UN ERROR");
            }
        }
    }

}
