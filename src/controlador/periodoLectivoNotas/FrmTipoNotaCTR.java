package controlador.periodoLectivoNotas;

import controlador.Libraries.Effects;
import java.awt.event.ActionEvent;
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

    //INICIADORES
    public void Init() {

        try {
            Effects.centerFrame(vista, desktop.getDpnlPrincipal());
            desktop.getDpnlPrincipal().add(vista);
            vista.show();
            vista.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(FrmTipoNotaCTR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void InitEventos() {
        vista.getBtnGuardar().addActionListener(e -> btnGuardarActionPerformance(e));
        vista.getBtnCancelar().addActionListener(e -> btnCancelarActionPerformance(e));
    }

    //METODOS DE APOYO
    //EVENTOS
    private void btnGuardarActionPerformance(ActionEvent e) {

    }

    private void btnCancelarActionPerformance(ActionEvent e) {

    }

}
