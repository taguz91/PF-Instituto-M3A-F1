package controlador.periodoLectivoNotas;

import controlador.Libraries.Effects;
import controlador.Libraries.Validaciones;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.tipoDeNota.TipoDeNotaBD;
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

    //INICIADORES
    public void Init() {

        InitEventos();
        try {
            Effects.centerFrame(vista, desktop.getDpnlPrincipal());
            desktop.getDpnlPrincipal().add(vista);
            vista.show();
            vista.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(FrmTipoNotaCTR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void InitEventos() {

        Validaciones.validarDoubleTxt(vista, vista.getTxtNotaMin(), 1, 100, "Ingrese Numeros entre 1 y 100 \nEn este formato (55.65 o 55)");

        vista.getBtnGuardar().addActionListener(e -> btnGuadar(e));
        vista.getBtnCancelar().addActionListener(e -> btnCancelar(e));
    }

    //METODOS DE APOYO
    private void setInfoEnModelo() {

        modelo = new TipoDeNotaBD();

        modelo.setNombre(vista.getCmbTipoNota().getSelectedItem().toString());

        modelo.setValorMaximo(Double.parseDouble(vista.getTxtNotaMax().getText()));
        modelo.setValorMinimo(Double.parseDouble(vista.getTxtNotaMin().getText()));

    }

    //EVENTOS
    private void btnGuadar(ActionEvent e) {
        setInfoEnModelo();

        System.out.println("------------>");

        if (modelo.insertar()) {

            JOptionPane.showMessageDialog(vista, "SE HA AGREGADO EL NUEVO TIPO DE NOTA");

        } else {

            JOptionPane.showMessageDialog(vista, "REVISE LA INFORMACION");

        }

    }

    private void btnCancelar(ActionEvent e) {
        vista.dispose();
    }
}
