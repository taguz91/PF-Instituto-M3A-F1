package controlador.referencias;

import java.util.List;
import modelo.ConexionBD;
import modelo.ReferenciasB.ReferenciaBD;
import modelo.ReferenciasB.ReferenciasMD;
import vista.principal.VtnPrincipal;
import vista.silabos.frmBibliografia;
import vista.silabos.frmEditarBiblioteca;

public class ControladorEditar {

    private frmEditarBiblioteca vista;
    private frmBibliografia vista1;
    private final ReferenciaBD RBD = ReferenciaBD.single();
    private VtnPrincipal vtnPrin;
    private ConexionBD conexion;
    private String clave;
    private int id;

    public ControladorEditar(frmEditarBiblioteca vista, String Clave) {
        this.vista = vista;
        this.clave = Clave;
        vista.setVisible(true);
    }

    public ControladorEditar(frmBibliografia vista1, VtnPrincipal vtnPrin, String clave, ConexionBD conexion) {
        this.vista1 = vista1;
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

    public ControladorEditar(frmEditarBiblioteca vista, VtnPrincipal vtnPrin, String clave, ConexionBD conexion) {
        this.vista = vista;
        this.vtnPrin = vtnPrin;
        this.vtnPrin.getDpnlPrincipal().add(vista);
        this.conexion = conexion;
        this.clave = clave;
        conexion.conectar();
        vista.show();
    }

    public void inicia_vista() {
        ReferenciasMD referencia = new ReferenciasMD();
        List<ReferenciasMD> r = RBD.obtenerdatos(conexion, clave);
        referencia.setId_referencia(r.get(0).getId_referencia());
        referencia.setCodigo_referencia(r.get(0).getCodigo_referencia());
        referencia.setDescripcion_referencia(r.get(0).getDescripcion_referencia());
        referencia.setTipo_referencia(r.get(0).getTipo_referencia());
        referencia.setExiste_en_biblioteca(r.get(0).isExiste_en_biblioteca());
        referencia.setObservaciones(r.get(0).getObservaciones());
        referencia.setCodigo_isbn(r.get(0).getCodigo_isbn());
        referencia.setNumero_de_paginas(r.get(0).getNumero_de_paginas());
        referencia.setCodigo_koha(r.get(0).getCodigo_koha());
        referencia.setCodigo_dewey(r.get(0).getCodigo_dewey());
        referencia.setArea_referencias(r.get(0).getArea_referencias());
        referencia.setAutor2(r.get(0).getAutor2());
        referencia.setAutor3(r.get(0).getAutor3());
        referencia.setAutor1(r.get(0).getAutor1());
        referencia.setEditor(r.get(0).getEditor());
        referencia.setTitulo(r.get(0).getTitulo());
        referencia.setAño(r.get(0).getAño());
        referencia.setCiudad(r.get(0).getCiudad());
        clave = referencia.getCodigo_referencia();
        id = referencia.getId_referencia();
        vista1.getTxtArea().setText(referencia.getArea_referencias());
        vista1.getTxtAutor2().setText(referencia.getAutor2());
        vista1.getTxtObservaciones1().setText(referencia.getObservaciones());
        vista1.getTxtAutor3().setText(referencia.getAutor3());
        vista1.getTxtCodigoDewey().setText(referencia.getCodigo_dewey());
        vista1.getTxtCodigoISBM().setText(referencia.getCodigo_isbn());
        vista1.getTxtCodigoKoha().setText(referencia.getCodigo_koha());
        vista1.getTxtCodigoLibro().setText(referencia.getCodigo_referencia());
        vista1.getTxtNumeroPaginas().setText(referencia.getNumero_de_paginas());
        vista1.getCbxExistenciaBiblioteca().setSelected(referencia.isExiste_en_biblioteca());
        vista1.getTxtTitulo().setText(referencia.getTitulo());
        vista1.getTxtAnio().setText(referencia.getAño());
        vista1.getTxtCiudad().setText(referencia.getCiudad());
        vista1.getTxtAutor().setText(referencia.getAutor1());
        vista1.getTxtEditor().setText(referencia.getEditor());
        // vista1.getLbldescripcion_referecnia().setVisible(true);
        vista1.getTxtdescripcionreferencia().setText(referencia.getDescripcion_referencia());
    }

    public void actualizar() {
        ReferenciasMD referencia = new ReferenciasMD();
        referencia.setId_referencia(id);

        referencia.setArea_referencias(vista1.getTxtArea().getText());
        referencia.setAutor2(vista1.getTxtAutor2().getText());
        referencia.setObservaciones(vista1.getTxtObservaciones1().getText());
        referencia.setAutor3(vista1.getTxtAutor3().getText());
        referencia.setCodigo_isbn(vista1.getTxtCodigoISBM().getText());
        referencia.setCodigo_dewey(vista1.getTxtCodigoDewey().getText());
        referencia.setCodigo_koha(vista1.getTxtCodigoKoha().getText());
        referencia.setCodigo_referencia(vista1.getTxtCodigoLibro().getText());
        referencia.setNumero_de_paginas(vista1.getTxtNumeroPaginas().getText());
        referencia.setAutor1(vista1.getTxtAutor().getText());
        referencia.setCiudad(vista1.getTxtCiudad().getText());
        referencia.setAño(vista1.getTxtAnio().getText());
        referencia.setEditor(vista1.getTxtEditor().getText());
        referencia.setTitulo(vista1.getTxtTitulo().getText());
        String autor = vista1.getTxtAutor().getText();
        String autor2 = vista1.getTxtAutor2().getText();
        String autor3 = vista1.getTxtAutor3().getText();

        String titulo = vista1.getTxtTitulo().getText();
        String año = vista1.getTxtAnio().getText();
        String ciudad = vista1.getTxtCiudad().getText();
        String editor = vista1.getTxtEditor().getText();
        String contenedor = "";
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
        referencia.setDescripcion_referencia(contenedor);
        boolean existe;
        if (vista1.getCbxExistenciaBiblioteca().isSelected()) {
            existe = true;
        } else {
            existe = false;
        }
        referencia.setExiste_en_biblioteca(existe);
        RBD.actualizar(referencia);
        vista1.dispose();
    }

    public void bottonCancelar() {
        this.vista1.dispose();

    }

    public void init() {
        vista1.getGuardarB().setText("Actualizar");
        vista1.getGuardarB().addActionListener(e -> actualizar());
        vista1.getBtnCancelarB().addActionListener(e -> bottonCancelar());
    }

}
