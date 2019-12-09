package controlador.referencias;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConectarDB;
import modelo.ConexionBD;
import modelo.ReferenciasB.ReferenciaBD;
import modelo.ReferenciasB.ReferenciasMD;
import vista.principal.VtnPrincipal;
import vista.silabos.frmBibliografia;
import vista.silabos.frmEditarBiblioteca;
import vista.silabos.FrmReferencias;

public class ControladorEditar {

    private frmEditarBiblioteca vista;
    private frmBibliografia vista1;
    private ReferenciaBD modelo;
    private VtnPrincipal vtnPrin;
    private ConexionBD conexion;
    private String clave;
    private int id;

    public ControladorEditar(frmEditarBiblioteca vista, ReferenciaBD modelo, String Clave) {
        this.vista = vista;
        this.modelo = modelo;
        this.clave = clave;
        vista.setVisible(true);
    }

    public ControladorEditar(frmBibliografia vista1, ReferenciaBD modelo, VtnPrincipal vtnPrin, String clave, ConexionBD conexion) {
        this.vista1 = vista1;
        this.modelo = modelo;
        this.vtnPrin = vtnPrin;
        this.vtnPrin.getDpnlPrincipal().add(vista1);
        this.conexion = conexion;
        this.clave = clave;
        conexion.conectar();
        vista1.show();
        vista1.getLbldescripcion_referecnia().setVisible(true);
        vista1.getTxtdescripcionreferencia().setVisible(true);
        //vista1.getLbldescripcion_referecnia().setEnabled(false);
        vista1.getTxtdescripcionreferencia().setEnabled(false);

    }

    public ControladorEditar(frmEditarBiblioteca vista, ReferenciaBD modelo, VtnPrincipal vtnPrin, String clave, ConexionBD conexion) {
        this.vista = vista;
        this.modelo = modelo;
        this.vtnPrin = vtnPrin;
        this.vtnPrin.getDpnlPrincipal().add(vista);
        this.conexion = conexion;
        this.clave = clave;
        conexion.conectar();
        vista.show();
    }

    public void inicia_vista() {
        List<ReferenciasMD> r = modelo.obtenerdatos(conexion, clave);
        modelo.setId_referencia(r.get(0).getId_referencia());
        modelo.setCodigo_referencia(r.get(0).getCodigo_referencia());
        modelo.setDescripcion_referencia(r.get(0).getDescripcion_referencia());
        modelo.setTipo_referencia(r.get(0).getTipo_referencia());
        modelo.setExiste_en_biblioteca(r.get(0).isExiste_en_biblioteca());
        modelo.setObservaciones(r.get(0).getObservaciones());
        modelo.setCodigo_isbn(r.get(0).getCodigo_isbn());
        modelo.setNumero_de_paginas(r.get(0).getNumero_de_paginas());
        modelo.setCodigo_koha(r.get(0).getCodigo_koha());
        modelo.setCodigo_dewey(r.get(0).getCodigo_dewey());
        modelo.setArea_referencias(r.get(0).getArea_referencias());
        modelo.setAutor2(r.get(0).getAutor2());
        modelo.setAutor3(r.get(0).getAutor3());
        modelo.setAutor1(r.get(0).getAutor1());
        modelo.setEditor(r.get(0).getEditor());
        modelo.setTitulo(r.get(0).getTitulo());
        modelo.setAño(r.get(0).getAño());
        modelo.setCiudad(r.get(0).getCiudad());
        clave = modelo.getCodigo_referencia();
        id=modelo.getId_referencia();
        vista1.getTxtArea().setText(modelo.getArea_referencias());
        vista1.getTxtAutor2().setText(modelo.getAutor2());
        vista1.getTxtObservaciones1().setText(modelo.getObservaciones());
        vista1.getTxtAutor3().setText(modelo.getAutor3());
        vista1.getTxtCodigoDewey().setText(modelo.getCodigo_dewey());
        vista1.getTxtCodigoISBM().setText(modelo.getCodigo_isbn());
        vista1.getTxtCodigoKoha().setText(modelo.getCodigo_koha());
        vista1.getTxtCodigoLibro().setText(modelo.getCodigo_referencia());
        vista1.getTxtNumeroPaginas().setText(modelo.getNumero_de_paginas());
        vista1.getCbxExistenciaBiblioteca().setSelected(modelo.isExiste_en_biblioteca());
        vista1.getTxtTitulo().setText(modelo.getTitulo());
        vista1.getTxtAnio().setText(modelo.getAño());
        vista1.getTxtCiudad().setText(modelo.getCiudad());
        vista1.getTxtAutor().setText(modelo.getAutor1());
        vista1.getTxtEditor().setText(modelo.getEditor());
        // vista1.getLbldescripcion_referecnia().setVisible(true);
        vista1.getTxtdescripcionreferencia().setText(modelo.getDescripcion_referencia());
    }

    public void actualizar() {
        try {
            modelo.setId_referencia(id);
           
            modelo.setArea_referencias(vista1.getTxtArea().getText());
            modelo.setAutor2(vista1.getTxtAutor2().getText());
            modelo.setObservaciones(vista1.getTxtObservaciones1().getText());
            modelo.setAutor3(vista1.getTxtAutor3().getText());
            modelo.setCodigo_isbn(vista1.getTxtCodigoISBM().getText());
            modelo.setCodigo_dewey(vista1.getTxtCodigoDewey().getText());
            modelo.setCodigo_koha(vista1.getTxtCodigoKoha().getText());
            modelo.setCodigo_referencia(vista1.getTxtCodigoLibro().getText());
            modelo.setNumero_de_paginas(vista1.getTxtNumeroPaginas().getText());
            modelo.setAutor1(vista1.getTxtAutor().getText());
            modelo.setCiudad(vista1.getTxtCiudad().getText());
            modelo.setAño(vista1.getTxtAnio().getText());
            modelo.setEditor(vista1.getTxtEditor().getText());
            modelo.setTitulo(vista1.getTxtTitulo().getText());
            String autor = vista1.getTxtAutor().getText();
            String autor2 =vista1.getTxtAutor2().getText();
            String autor3 =vista1.getTxtAutor3().getText();

            String titulo = vista1.getTxtTitulo().getText();
            String año = vista1.getTxtAnio().getText();
            String ciudad = vista1.getTxtCiudad().getText();
            String editor = vista1.getTxtEditor().getText();
            String contenedor="";
            if (vista1.getTxtAutor().getText().length() > 0 && vista1.getTxtAutor2().getText().length() == 0
                    && vista1.getTxtAutor3().getText().length() == 0) {
                contenedor = autor + " . " + año + " . " + titulo + " . " + editor + " , " + ciudad;
            } else if (vista1.getTxtAutor().getText().length() > 0 && vista1.getTxtAutor2().getText().length() > 0
                    && vista1.getTxtAutor3().getText().length() == 0) {
                contenedor = autor + " & " + autor2 + " . " + año + " . " + titulo + " . " + editor + " , " + ciudad;
            } else if (vista1.getTxtAutor().getText().length() > 0 || vista1.getTxtAutor2().getText().length() == 0
                    || vista1.getTxtAutor3().getText().length() > 0) {
                contenedor = autor + " & " + autor2 + " & " + autor3 + " . " + año + " . " + titulo + " , " + editor + " . " + ciudad;
            }
             modelo.setDescripcion_referencia(contenedor);
             boolean existe;
            if (vista1.getCbxExistenciaBiblioteca().isSelected() == true) {
                existe = true;
            } else {
                existe = false;
            }
            modelo.setExiste_en_biblioteca(existe);
            modelo.actualizar(conexion, id);
            vista1.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(ControladorEditar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public void bottonCancelar() {
        this.vista1.dispose();

    }

    public void init() {
        vista1.getGuardarB().setText("Actualizar");
        vista1.getGuardarB().addActionListener(e -> actualizar());
        vista1.getBtnCancelarB().addActionListener(e->bottonCancelar());
    }

}
