package controlador.periodoLectivoNotas;

import controlador.Libraries.Effects;
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
    //Ventana Padre
    private VtnTipoNotasCTR vtnPadre;
    //(Agregar o Editar)
    private String Funcion;

    private Integer PK = null;

    public FrmTipoNotaCTR(VtnPrincipal desktop, FrmTipoNota vista, TipoDeNotaBD modelo, VtnTipoNotasCTR vtnPadre, String Funcion) {
        this.desktop = desktop;
        this.vista = vista;
        this.modelo = modelo;
        this.vtnPadre = vtnPadre;
        this.Funcion = Funcion;
    }

    //INICIADORES
    public void Init() {

        if (Funcion.equals("Editar")) {
            vista.setTitle("Editar");
            setInfoEnTxts();
            PK = modelo.getIdTipoNota();
        } else {
            vista.setTitle("Agregar");
        }

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
        vista.getTxtNotaMax().addKeyListener(new KeyAdapter() {

        });

        vista.getBtnGuardar().addActionListener(e -> btnGuadar(e));
        vista.getBtnCancelar().addActionListener(e -> btnCancelar(e));

    }

    //METODOS DE APOYO
    private void setInfoEnTxts() {

        System.out.println("---->" + modelo.getNombre());

        vista.getCmbTipoNota().setSelectedItem((Object) modelo.getNombre());
        vista.getTxtNotaMax().setText(modelo.getValorMaximo() + "");
        vista.getTxtNotaMin().setText(modelo.getValorMinimo() + "");
    }

    private void setInfoEnModelo() {

        modelo = new TipoDeNotaBD();

        modelo.setNombre(vista.getCmbTipoNota().getSelectedItem().toString());

        modelo.setValorMaximo(Double.parseDouble(vista.getTxtNotaMax().getText()));
        modelo.setValorMinimo(Double.parseDouble(vista.getTxtNotaMin().getText()));

    }

    private void agregar() {
        if (modelo.insertar()) {
            Effects.setTextInLabel(desktop.getLblEstado(), "SE HA AGREGADO EL NUEVO TIPO DE NOTA", 3);

            JOptionPane.showMessageDialog(vista, "SE HA AGREGADO EL NUEVO TIPO DE NOTA");

            vtnPadre.cargarTabla();

            vista.dispose();

        } else {

            JOptionPane.showMessageDialog(vista, "REVISE LA INFORMACION");

        }
    }

    private void editar() {
        if (modelo.editar(PK)) {
            vtnPadre.cargarTabla();
            JOptionPane.showMessageDialog(vista, "SE HA EDITADO CORRECTAMENTE");
            vista.dispose();
        } else {
            JOptionPane.showMessageDialog(vista, "REVISE LA INFORMACION");
        }
    }

    //EVENTOS
    private void btnGuadar(ActionEvent e) {

        setInfoEnModelo();

        if (Funcion.equals("Agregar")) {
            agregar();
        } else {
            editar();
        }

    }

    private void btnCancelar(ActionEvent e) {
        vista.dispose();
    }
}
