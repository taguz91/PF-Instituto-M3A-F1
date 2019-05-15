package controlador.referencias;

import controlador.principal.VtnPrincipalCTR;
import javax.swing.JOptionPane;
import modelo.ConectarDB;
import modelo.ReferenciasB.ReferenciaBD;
import vista.principal.VtnPrincipal;
import vista.silabos.frmBibliografia;

public class ReferenciasCTR {

    private final frmBibliografia frmBibliografia;
    private final ReferenciaBD BDbibliografia;
    private final ConectarDB conecta;
    private final VtnPrincipal vtnPrin;
    private final VtnPrincipalCTR ctrPrin;
    private String autor, autorCorporativo, titulo, año, ciudad, editor;

    public ReferenciasCTR(ConectarDB conecta, VtnPrincipalCTR ctrPrin, VtnPrincipal vtnPrin, frmBibliografia frmBibliografia) {
        this.frmBibliografia = frmBibliografia;
        this.BDbibliografia = new ReferenciaBD(conecta);
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        this.vtnPrin = vtnPrin;
        this.vtnPrin.getDpnlPrincipal().add(frmBibliografia);
        frmBibliografia.show();
        frmBibliografia.getCbxExistenciaBiblioteca().setSelected(true);

    }

    public void iniciarControlador() {
        frmBibliografia.getBtnCancelarB().addActionListener(e -> bottonCancelar());
        frmBibliografia.getGuardarB().addActionListener(e -> bottonGuardar());

    }

    public void bottonGuardar() {
        if (frmBibliografia.getTxtAnio().getText().equals(null) || frmBibliografia.getTxtAutor().getText().equals(null)
                || frmBibliografia.getTxtCiudad().getText().equals(null) || frmBibliografia.getTxtEditor().getText().equals(null)) {
            JOptionPane.showMessageDialog(null, "Campos vacios no Permitido");
        } else {
            boolean existe;
            if (frmBibliografia.getCbxExistenciaBiblioteca().isSelected() == true) {
                existe = true;
            } else {
                existe = false;
            }
            String observaciones = frmBibliografia.getTxtObservaciones().getText();
            String tipoD = "Base";
            String contenedor = "";
            autor = frmBibliografia.getTxtAutor().getText();
            titulo = frmBibliografia.getTxtTitulo().getText();
            año = frmBibliografia.getTxtAnio().getText();
            ciudad = frmBibliografia.getTxtCiudad().getText();
            editor = frmBibliografia.getTxtEditor().getText();
            contenedor = autor + ',' + titulo + ',' + año + ',' + ciudad + ',' + editor;
            BDbibliografia.setCodigo_referencia(frmBibliografia.getTxtCodigoLibro().getText());
            BDbibliografia.setDescripcion_referencia(contenedor);
            BDbibliografia.setTipo_referencia(tipoD);
            BDbibliografia.setExiste_en_biblioteca(existe);
            BDbibliografia.setObservaciones(observaciones);
            if (BDbibliografia.insertarReferencia()) {
                JOptionPane.showMessageDialog(null, "Los Datos se guardaron Correctamente");
                frmBibliografia.getTxtAutor().setText("");
                frmBibliografia.getTxtTitulo().setText("");
                frmBibliografia.getTxtAnio().setText("");
                frmBibliografia.getTxtCiudad().setText("");
                frmBibliografia.getTxtEditor().setText("");
                frmBibliografia.getTxtObservaciones().setText("");
                frmBibliografia.getTxtCodigoLibro().setText("");

            } else {
                JOptionPane.showMessageDialog(null, "ERROR  Datos no guardados");
            }
        }
    }

    public void bottonCancelar() {
        this.frmBibliografia.dispose();

    }
}
