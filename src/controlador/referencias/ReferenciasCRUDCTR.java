package controlador.referencias;

import controlador.Libraries.Middlewares;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConexionBD;
import modelo.ReferenciasB.ReferenciaBD;
import modelo.ReferenciasB.ReferenciasMD;
import modelo.periodolectivo.PeriodoLectivoMD;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import vista.principal.VtnPrincipal;

import vista.silabos.frmBibliografia;
import vista.silabos.frmCRUDBibliografia;

public class ReferenciasCRUDCTR {

    private final frmCRUDBibliografia frmCRUDBibliografiaC;
    private final ReferenciaBD BDbibliotecaC = ReferenciaBD.single();
    private final VtnPrincipal vtnPrin;
    private final VtnPrincipalCTR ctrPrin;
    //private ReferenciasMD modelo;

    public ReferenciasCRUDCTR(VtnPrincipalCTR ctrPrin, VtnPrincipal vtnPrin, frmCRUDBibliografia frmCRUDBibliografiaC) {
        this.frmCRUDBibliografiaC = frmCRUDBibliografiaC;
        this.ctrPrin = ctrPrin;
        this.vtnPrin = vtnPrin;

        vtnPrin.getDpnlPrincipal().add(frmCRUDBibliografiaC);
        frmCRUDBibliografiaC.show();
        cargar_combo();
    }

    public void iniciarControlador() {
        frmCRUDBibliografiaC.getBtnNuevoCB().addActionListener(e -> AbrirFormularioRefe());

        cargartabla();

        frmCRUDBibliografiaC.getBtnEliminarCB().addActionListener(e -> eliminar());
        KeyListener kl = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //To change body of generated methods, choose Tools | Templates.
                busqueda(frmCRUDBibliografiaC.getTxtBuscarCB().getText());
            }

        };
        frmCRUDBibliografiaC.getTxtBuscarCB().addKeyListener(kl);
        frmCRUDBibliografiaC.getBtnEditarCB().addActionListener(e -> editar());
        frmCRUDBibliografiaC.getBtnmostrar().addActionListener(e -> cargar());
        frmCRUDBibliografiaC.getBtnimprimir().addActionListener(e -> imprimir_reporte());

    }

    public void cargartabla() {
        List<ReferenciasMD> referencias = BDbibliotecaC.consultarBibliotecaTabla();
        DefaultTableModel modeloTabla;

        modeloTabla = (DefaultTableModel) frmCRUDBibliografiaC.getTblTablaCB().getModel();
        for (ReferenciasMD rmd : referencias) {

            modeloTabla.addRow(new Object[]{
                rmd.getCodigo_referencia(),
                rmd.getDescripcion_referencia()
            });

        }

    }
    String nombre = "";
    PeriodoLectivoMD mo;

    public void cargar() {
        //cargartablaperiodo();/*
        if (frmCRUDBibliografiaC.getCmblibros().getSelectedItem().toString().equalsIgnoreCase("Seleccionar...")) {
            // cargartabla1(); 
            JOptionPane.showMessageDialog(null, "Es necesario que seleccione un período lectivo para mostrar los libros utilizados en la carrera");
        } else if (frmCRUDBibliografiaC.getCmblibros().getSelectedIndex() > 0) {
            nombre = frmCRUDBibliografiaC.getCmblibros().getSelectedItem().toString();
            mo = BDbibliotecaC.retornaPRDlectivo(nombre);
            List<ReferenciasMD> referencias = BDbibliotecaC.getBasePorPeriodo(mo.getID());
            System.out.println("entro");
            DefaultTableModel modeloTabla;

            modeloTabla = (DefaultTableModel) frmCRUDBibliografiaC.getTblTablaCB().getModel();
            for (int j = frmCRUDBibliografiaC.getTblTablaCB().getRowCount() - 1; j >= 0; j--) {
                modeloTabla.removeRow(j);
            }
            for (ReferenciasMD rmd : referencias) {

                modeloTabla.addRow(new Object[]{
                    rmd.getCodigo_referencia(),
                    rmd.getDescripcion_referencia()
                });

            }

        }
    }

    public void imprimir_reporte() {
        if (frmCRUDBibliografiaC.getCmblibros().getSelectedItem().toString().equalsIgnoreCase("Seleccionar...")) {
            // cargartabla1(); 
            JOptionPane.showMessageDialog(null, "Es necesario que seleccione un período lectivo para mostrar los libros utilizados en la carrera");

            // cargartablaperiodo();
        } else {
            Map parametro = new HashMap();
            parametro.put("id_prd_lectivo", String.valueOf(mo.getID()));
            frmCRUDBibliografiaC.getCmblibros().setSelectedIndex(0);
            cargartabla1();
            Middlewares.generarReporte(
                    getClass().getResource("/vista/silabos/reportes/biblioteca/referencias_x_carrera.jasper"),
                    "Reporte de libros",
                    parametro
            );
        }
    }

    public void cargartabla1() {
        List<ReferenciasMD> referencias = BDbibliotecaC.consultarBibliotecaTabla();
        DefaultTableModel modeloTabla;

        modeloTabla = (DefaultTableModel) frmCRUDBibliografiaC.getTblTablaCB().getModel();
        for (int j = frmCRUDBibliografiaC.getTblTablaCB().getRowCount() - 1; j >= 0; j--) {
            modeloTabla.removeRow(j);
        }
        for (ReferenciasMD rmd : referencias) {

            modeloTabla.addRow(new Object[]{
                rmd.getCodigo_referencia(),
                rmd.getDescripcion_referencia()
            });

        }

    }

    public void AbrirFormularioRefe() {
        frmBibliografia frmBibliografiaC = new frmBibliografia();
        ReferenciasCTR ReferenciasCTRC = new ReferenciasCTR(ctrPrin, vtnPrin, frmBibliografiaC);
        ReferenciasCTRC.iniciarControlador();
    }

    public void eliminar() {
        int select = frmCRUDBibliografiaC.getTblTablaCB().getSelectedRow();
        if (select >= 0) {
            String codigo = frmCRUDBibliografiaC.getTblTablaCB().getValueAt(select, 0).toString();
            System.out.println(" " + codigo);
            BDbibliotecaC.eliminar(codigo);
            cargartabla1();
        } else {
            JOptionPane.showMessageDialog(null, "Selecione una fila");
        }

    }

    public void editar() {
        frmBibliografia vista = new frmBibliografia();
        int select = frmCRUDBibliografiaC.getTblTablaCB().getSelectedRow();
        if (select >= 0) {
            String codigo = frmCRUDBibliografiaC.getTblTablaCB().getValueAt(select, 0).toString();
            ControladorEditar editar = new ControladorEditar(vista, vtnPrin, codigo);
            editar.inicia_vista();
            editar.init();
        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONE LA REFERENCIA A EDITAR");
        }

    }

    public void busqueda(String aguja) {

        List<ReferenciasMD> referencias = BDbibliotecaC.consultarBiblioteca(aguja);
        DefaultTableModel modeloTabla;

        modeloTabla = (DefaultTableModel) frmCRUDBibliografiaC.getTblTablaCB().getModel();

        for (int j = frmCRUDBibliografiaC.getTblTablaCB().getRowCount() - 1; j >= 0; j--) {
            modeloTabla.removeRow(j);
        }
        for (ReferenciasMD rmd : referencias) {

            modeloTabla.addRow(new Object[]{
                rmd.getCodigo_referencia(),
                rmd.getDescripcion_referencia()
            });

        }

    }

    public void cargar_combo() {

        List<PeriodoLectivoMD> lista = BDbibliotecaC.mostrarDatos1();
        frmCRUDBibliografiaC.getCmblibros().removeAllItems();
        frmCRUDBibliografiaC.getCmblibros().addItem("Seleccionar...");
        for (int i = 0; i < lista.size(); i++) {
            frmCRUDBibliografiaC.getCmblibros().addItem(lista.get(i).getNombre());
        }
    }

    public void cargartablaperiodo() {
        List<ReferenciasMD> referencias = BDbibliotecaC.getBasePorPeriodo(21);
        System.out.println("entro");
        DefaultTableModel modeloTabla;

        modeloTabla = (DefaultTableModel) frmCRUDBibliografiaC.getTblTablaCB().getModel();
        for (int j = frmCRUDBibliografiaC.getTblTablaCB().getRowCount() - 1; j >= 0; j--) {
            modeloTabla.removeRow(j);
        }
        for (ReferenciasMD rmd : referencias) {

            modeloTabla.addRow(new Object[]{
                rmd.getCodigo_referencia(),
                rmd.getDescripcion_referencia()
            });

        }

    }

}
