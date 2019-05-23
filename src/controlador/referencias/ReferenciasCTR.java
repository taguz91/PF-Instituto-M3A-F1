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
    private String autor, autor2, autor3, autorCorporativo, titulo, año, ciudad, editor;

    public ReferenciasCTR(ConectarDB conecta, VtnPrincipalCTR ctrPrin, VtnPrincipal vtnPrin, frmBibliografia frmBibliografia) {
        this.frmBibliografia = frmBibliografia;
        this.BDbibliografia = new ReferenciaBD(conecta);
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        this.vtnPrin = vtnPrin;
        this.vtnPrin.getDpnlPrincipal().add(frmBibliografia);
        frmBibliografia.show();
        frmBibliografia.getCbxExistenciaBiblioteca().setSelected(true);
        frmBibliografia.getTxtCodigoKoha().setEnabled(false);
        frmBibliografia.getTxtCodigoKoha().setText("S/N");

    }

    public void iniciarControlador() {
        frmBibliografia.getBtnCancelarB().addActionListener(e -> bottonCancelar());
        frmBibliografia.getGuardarB().addActionListener(e -> bottonGuardar());

    }

    public void bottonGuardar() {
        if (frmBibliografia.getTxtAnio().getText().equals(null) || frmBibliografia.getTxtAutor2().getText().equals(null) || frmBibliografia.getTxtAutor3().getText().equals(null)
                || frmBibliografia.getTxtAutor().getText().equals(null)
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
            autor2 = frmBibliografia.getTxtAutor2().getText();
            autor3 = frmBibliografia.getTxtAutor3().getText();

            titulo = frmBibliografia.getTxtTitulo().getText();
            año = frmBibliografia.getTxtAnio().getText();
            ciudad = frmBibliografia.getTxtCiudad().getText();
            editor = frmBibliografia.getTxtEditor().getText();
            if (frmBibliografia.getTxtAutor().getText().length() > 0 && frmBibliografia.getTxtAutor2().getText().length() == 0
                    && frmBibliografia.getTxtAutor3().getText().length() == 0) {
                contenedor = autor + '.' + año + '.' + titulo + '.' + editor + '.' + ciudad;
            } else if (frmBibliografia.getTxtAutor().getText().length() > 0 && frmBibliografia.getTxtAutor2().getText().length() > 0
                    && frmBibliografia.getTxtAutor3().getText().length() == 0) {
                contenedor = autor + '&' + autor2 + '.' + año + '.' + titulo + '.' + editor + '.' + ciudad;
            } else if (frmBibliografia.getTxtAutor().getText().length() > 0 || frmBibliografia.getTxtAutor2().getText().length() == 0
                    || frmBibliografia.getTxtAutor3().getText().length() > 0) {
                contenedor = autor + '&' + autor2 + '&' + autor3 + '.' + año + '.' + titulo + '.' + editor + '.' + ciudad;
            }

            BDbibliografia.setCodigo_referencia(frmBibliografia.getTxtCodigoLibro().getText());
            BDbibliografia.setAutor2(autor2);
            BDbibliografia.setAutor3(autor3);
            BDbibliografia.setDescripcion_referencia(contenedor);
            BDbibliografia.setTipo_referencia(tipoD);
            BDbibliografia.setExiste_en_biblioteca(existe);
            BDbibliografia.setObservaciones(observaciones);
            BDbibliografia.setCodigo_isbn(frmBibliografia.getTxtCodigoISBM().getText());
            BDbibliografia.setNumero_de_paginas(frmBibliografia.getTxtNumeroPaginas().getText());
            BDbibliografia.setCodigo_koha(frmBibliografia.getTxtCodigoKoha().getText());
            BDbibliografia.setCodigo_dewey(frmBibliografia.getTxtCodigoDewey().getText());
            BDbibliografia.setArea_referencias(frmBibliografia.getTxtArea().getText());

            if (BDbibliografia.insertarReferencia()) {
                JOptionPane.showMessageDialog(null, "Los Datos se guardaron Correctamente");
                frmBibliografia.getTxtAutor().setText("");
                frmBibliografia.getTxtAutor2().setText("");
                frmBibliografia.getTxtAutor3().setText("");
                frmBibliografia.getTxtTitulo().setText("");
                frmBibliografia.getTxtAnio().setText("");
                frmBibliografia.getTxtCiudad().setText("");
                frmBibliografia.getTxtEditor().setText("");
                frmBibliografia.getTxtObservaciones().setText("");
                frmBibliografia.getTxtCodigoLibro().setText("");
                frmBibliografia.getTxtCodigoISBM().setText("");
                frmBibliografia.getTxtNumeroPaginas().setText("");
                frmBibliografia.getTxtCodigoKoha().setText("S/N");
                frmBibliografia.getTxtCodigoDewey().setText("");
                frmBibliografia.getTxtArea().setText("");
                frmBibliografia.getTxtAutor().setEnabled(true);
                frmBibliografia.getTxtCodigoKoha().setEnabled(false);

            } else {
                JOptionPane.showMessageDialog(null, "ERROR  Datos no guardados");
            }
        }
    }

    public void bottonCancelar() {
        this.frmBibliografia.dispose();

    }
}
