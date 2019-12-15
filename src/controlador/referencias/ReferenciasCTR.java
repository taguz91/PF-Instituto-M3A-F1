package controlador.referencias;

import controlador.principal.VtnPrincipalCTR;
import javax.swing.JOptionPane;
import modelo.ReferenciasB.ReferenciaBD;
import modelo.referencias.ReferenciasMD;
import vista.principal.VtnPrincipal;
import vista.silabos.frmBibliografia;

public class ReferenciasCTR {

    private final frmBibliografia frmBibliografia;
    private final ReferenciaBD RFBD = ReferenciaBD.single();
    private final VtnPrincipal vtnPrin;
    private final VtnPrincipalCTR ctrPrin;
    private String autor, autor2, autor3, autorCorporativo, titulo, año, ciudad, editor;

    public ReferenciasCTR(VtnPrincipalCTR ctrPrin, VtnPrincipal vtnPrin, frmBibliografia frmBibliografia) {
        this.frmBibliografia = frmBibliografia;
        this.ctrPrin = ctrPrin;
        this.vtnPrin = vtnPrin;
        this.vtnPrin.getDpnlPrincipal().add(frmBibliografia);
        frmBibliografia.show();
        frmBibliografia.getCbxExistenciaBiblioteca().setSelected(true);
        frmBibliografia.getTxtCodigoKoha().setText("S/N");
        frmBibliografia.getLbldescripcion_referecnia().setVisible(false);
        frmBibliografia.getTxtdescripcionreferencia().setVisible(false);

    }

    public void iniciarControlador() {
        frmBibliografia.getBtnCancelarB().addActionListener(e -> bottonCancelar());
        frmBibliografia.getGuardarB().addActionListener(e -> bottonGuardar());

    }

    public void bottonGuardar() {
        if (frmBibliografia.getTxtAnio().getText() == null || frmBibliografia.getTxtAutor2().getText() == null || frmBibliografia.getTxtAutor3().getText() == null
                || frmBibliografia.getTxtAutor().getText() == null
                || frmBibliografia.getTxtCiudad().getText() == null || frmBibliografia.getTxtEditor().getText().equals(null)) {
            JOptionPane.showMessageDialog(null, "Campos vacios no Permitido");
        } else {
            boolean existe;
            if (frmBibliografia.getCbxExistenciaBiblioteca().isSelected() == true) {
                existe = true;
            } else {
                existe = false;
            }
            String observaciones = frmBibliografia.getTxtObservaciones1().getText();
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
                contenedor = autor + " . " + año + " . " + titulo + " . " + editor + " , " + ciudad;
            } else if (frmBibliografia.getTxtAutor().getText().length() > 0 && frmBibliografia.getTxtAutor2().getText().length() > 0
                    && frmBibliografia.getTxtAutor3().getText().length() == 0) {
                contenedor = autor + " & " + autor2 + " . " + año + " . " + titulo + " . " + editor + " , " + ciudad;
            } else if (frmBibliografia.getTxtAutor().getText().length() > 0 || frmBibliografia.getTxtAutor2().getText().length() == 0
                    || frmBibliografia.getTxtAutor3().getText().length() > 0) {
                contenedor = autor + " & " + autor2 + " & " + autor3 + " . " + año + " . " + titulo + " , " + editor + " . " + ciudad;
            }

            modelo.ReferenciasB.ReferenciasMD re = new modelo.ReferenciasB.ReferenciasMD();

            re.setCodigo_referencia(frmBibliografia.getTxtCodigoLibro().getText());
            re.setAutor2(autor2);
            re.setAutor3(autor3);
            re.setDescripcion_referencia(contenedor);
            re.setTipo_referencia(tipoD);
            re.setExiste_en_biblioteca(existe);
            re.setObservaciones(observaciones);
            re.setCodigo_isbn(frmBibliografia.getTxtCodigoISBM().getText());
            re.setNumero_de_paginas(frmBibliografia.getTxtNumeroPaginas().getText());
            re.setCodigo_koha(frmBibliografia.getTxtCodigoKoha().getText());
            re.setCodigo_dewey(frmBibliografia.getTxtCodigoDewey().getText());
            re.setArea_referencias(frmBibliografia.getTxtArea().getText());
            re.setAutor1(autor);
            re.setTitulo(titulo);
            re.setEditor(editor);
            re.setAño(año);
            re.setCiudad(ciudad);

            if (RFBD.insertarReferencia(re)) {
                JOptionPane.showMessageDialog(null, "Los Datos se guardaron Correctamente");
                frmBibliografia.getTxtAutor().setText("");
                frmBibliografia.getTxtAutor2().setText("");
                frmBibliografia.getTxtAutor3().setText("");
                frmBibliografia.getTxtTitulo().setText("");
                frmBibliografia.getTxtAnio().setText("");
                frmBibliografia.getTxtCiudad().setText("");
                frmBibliografia.getTxtEditor().setText("");
                frmBibliografia.getTxtObservaciones1().setText("");
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
