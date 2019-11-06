package controlador.referencias;

import controlador.principal.VtnPrincipalCTR;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
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
//import modelo.referencias.ReferenciasMD;
import vista.principal.VtnPrincipal;

import vista.silabos.frmBibliografia;
import vista.silabos.frmCRUDBibliografia;
import vista.silabos.frmEditarBiblioteca;

public class ReferenciasCRUDCTR {

    private final frmCRUDBibliografia frmCRUDBibliografiaC;
    private final ReferenciaBD BDbibliotecaC;
    private final ConectarDB conecta;
    private final VtnPrincipal vtnPrin;
    private final VtnPrincipalCTR ctrPrin;
    private ConexionBD conexion;
    //private ReferenciasMD modelo;

    public ReferenciasCRUDCTR(ConexionBD conexion, ConectarDB conecta, VtnPrincipalCTR ctrPrin, VtnPrincipal vtnPrin, frmCRUDBibliografia frmCRUDBibliografiaC) {
        this.frmCRUDBibliografiaC = frmCRUDBibliografiaC;
        this.BDbibliotecaC = new ReferenciaBD(conecta);
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        this.vtnPrin = vtnPrin;
        this.conexion
                = conexion;
        vtnPrin.getDpnlPrincipal().add(frmCRUDBibliografiaC);
        frmCRUDBibliografiaC.show();
        cargar_combo();

    }

    public void iniciarControlador() {
        conexion.conectar();
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
        frmCRUDBibliografiaC.getBtnmostrar().addActionListener(e->cargar());
        frmCRUDBibliografiaC.getBtnimprimir().addActionListener(e->imprimir_reporte());

    }

    public void cargartabla() {
        List<ReferenciasMD> referencias = BDbibliotecaC.consultarBibliotecaTabla(conexion);
        DefaultTableModel modeloTabla;

        modeloTabla = (DefaultTableModel) frmCRUDBibliografiaC.getTblTablaCB().getModel();
        for (ReferenciasMD rmd : referencias) {

            modeloTabla.addRow(new Object[]{
                rmd.getCodigo_referencia(),
                rmd.getDescripcion_referencia()
            });

        }

    }
    String nombre="";
    PeriodoLectivoMD mo;
    public void cargar(){
         //cargartablaperiodo();/*
        if (frmCRUDBibliografiaC.getCmblibros().getSelectedItem().toString().equalsIgnoreCase("Seleccionar...")) {
           // cargartabla1(); 
            JOptionPane.showMessageDialog(null,"Es necesario que seleccione un período lectivo para mostrar los libros utilizados en la carrera");
                        

           // cargartablaperiodo();
        } else if(frmCRUDBibliografiaC.getCmblibros().getSelectedIndex()>0){
         nombre= frmCRUDBibliografiaC.getCmblibros().getSelectedItem().toString();
        mo=BDbibliotecaC.retornaPRDlectivo(conexion, nombre);
           List<ReferenciasMD> referencias = BDbibliotecaC.consultarBporperiodo(conexion,mo.getId());
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
    public void imprimir_reporte(){
         if (frmCRUDBibliografiaC.getCmblibros().getSelectedItem().toString().equalsIgnoreCase("Seleccionar...")) {
           // cargartabla1(); 
            JOptionPane.showMessageDialog(null,"Es necesario que seleccione un período lectivo para mostrar los libros utilizados en la carrera");
                        

           // cargartablaperiodo();
        } else{
              try {
                System.out.println(mo.getId());
                System.out.println("Imprimiendo.......");
                JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/silabos/reportes/biblioteca/referencias_x_carrera.jasper"));
                Map parametro = new HashMap();
             
                
                parametro.put("id_prd_lectivo", String.valueOf(mo.getId()));
                
                JasperPrint jp = JasperFillManager.fillReport(jr, parametro, conexion.getCon());
                JasperViewer pv = new JasperViewer(jp, false);
                pv.setVisible(true);
                pv.setTitle("Reporte de libros");
                frmCRUDBibliografiaC.getCmblibros().setSelectedIndex(0);
                cargartabla1();
                //principal.add(pv);
                
            } catch (JRException ex) {
                Logger.getLogger(ReferenciasCRUDCTR.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
    }

    public void cargartabla1() {
        List<ReferenciasMD> referencias = BDbibliotecaC.consultarBibliotecaTabla(conexion);
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
        ReferenciasCTR ReferenciasCTRC = new ReferenciasCTR(conecta, ctrPrin, vtnPrin, frmBibliografiaC);
        ReferenciasCTRC.iniciarControlador();
    }

    public void eliminar() {
        int select = frmCRUDBibliografiaC.getTblTablaCB().getSelectedRow();
        if (select >= 0) {
            String codigo = frmCRUDBibliografiaC.getTblTablaCB().getValueAt(select, 0).toString();
            System.out.println(" " + codigo);
            BDbibliotecaC.eliminar(conexion, codigo);
            cargartabla1();
        } else {
            JOptionPane.showMessageDialog(null, "Selecione una fila");
        }

    }

    public void editar() {
        /*frmEditarBiblioteca vista= new  frmEditarBiblioteca();
               ReferenciaBD modelo =new ReferenciaBD()  ;
                int select=frmCRUDBibliografiaC.getTblTablaCB().getSelectedRow();
                if(select>=0){
                String codigo=frmCRUDBibliografiaC.getTblTablaCB().getValueAt(select, 0).toString();
                
              
               ControladorEditar editar=new ControladorEditar(vista,modelo,vtnPrin,codigo,conexion);
               editar.inicia_vista();
                editar.init();
                }*/
        frmBibliografia vista = new frmBibliografia();
        ReferenciaBD modelo = new ReferenciaBD();
        int select = frmCRUDBibliografiaC.getTblTablaCB().getSelectedRow();
        if (select >= 0) {
            String codigo = frmCRUDBibliografiaC.getTblTablaCB().getValueAt(select, 0).toString();

            ControladorEditar editar = new ControladorEditar(vista, modelo, vtnPrin, codigo, conexion);
            editar.inicia_vista();
            editar.init();
        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONE LA REFERENCIA A EDITAR");
        }

    }

    public void busqueda(String aguja) {

        List<ReferenciasMD> referencias = BDbibliotecaC.consultarBiblioteca(conexion, aguja);
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

        List<PeriodoLectivoMD> lista = BDbibliotecaC.mostrarDatos1(conexion);
        frmCRUDBibliografiaC.getCmblibros().removeAllItems();
        frmCRUDBibliografiaC.getCmblibros().addItem("Seleccionar...");
        for (int i = 0; i < lista.size(); i++) {
            frmCRUDBibliografiaC.getCmblibros().addItem(lista.get(i).getNombre());
        }
    }

    public void cargartablaperiodo() {
        List<ReferenciasMD> referencias = BDbibliotecaC.consultarBporperiodo(conexion,21);
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
