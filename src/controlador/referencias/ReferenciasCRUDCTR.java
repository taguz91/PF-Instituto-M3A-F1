
package controlador.referencias;


import controlador.principal.VtnPrincipalCTR;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.ConexionBD;
import modelo.ReferenciasB.ReferenciaBD;
import modelo.ReferenciasB.ReferenciasMD;
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
     private  ConexionBD conexion;
     //private ReferenciasMD modelo;
    public ReferenciasCRUDCTR(ConexionBD conexion,ConectarDB conecta,VtnPrincipalCTR ctrPrin,VtnPrincipal vtnPrin,frmCRUDBibliografia frmCRUDBibliografiaC) {
        this.frmCRUDBibliografiaC = frmCRUDBibliografiaC;
        this.BDbibliotecaC =  new ReferenciaBD(conecta);
        this.conecta=conecta;
        this.ctrPrin=ctrPrin;
        this.vtnPrin=vtnPrin;
        this.conexion
                =conexion;
        vtnPrin.getDpnlPrincipal().add(frmCRUDBibliografiaC);
        frmCRUDBibliografiaC.show();
    }
        public void iniciarControlador(
        ){
            conexion.conectar();
            frmCRUDBibliografiaC.getBtnNuevoCB().addActionListener(e->AbrirFormularioRefe());
            cargartabla();
             frmCRUDBibliografiaC.getBtnEliminarCB().addActionListener(e->eliminar());
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
                frmCRUDBibliografiaC.getBtnEditarCB().addActionListener(e->editar());
            
    }
        public void cargartabla(){
             List<ReferenciasMD> referencias=BDbibliotecaC.consultarBibliotecaTabla(conexion);
              DefaultTableModel modeloTabla;

        modeloTabla=(DefaultTableModel)frmCRUDBibliografiaC.getTblTablaCB().getModel();
       for (ReferenciasMD rmd : referencias) {

            modeloTabla.addRow(new Object[]{
                rmd.getCodigo_referencia(),
                rmd.getDescripcion_referencia()
            });   

        }
          
        }
        public void cargartabla1(){
             List<ReferenciasMD> referencias=BDbibliotecaC.consultarBibliotecaTabla(conexion);
              DefaultTableModel modeloTabla;

        modeloTabla=(DefaultTableModel)frmCRUDBibliografiaC.getTblTablaCB().getModel();
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
        
        
        
        public void AbrirFormularioRefe (){
            frmBibliografia frmBibliografiaC= new frmBibliografia ();
            ReferenciasCTR  ReferenciasCTRC = new ReferenciasCTR(conecta,ctrPrin,vtnPrin, frmBibliografiaC);
            ReferenciasCTRC.iniciarControlador();
        }
        public void eliminar(){
            int select=frmCRUDBibliografiaC.getTblTablaCB().getSelectedRow();
            if(select>=0){
                String codigo=frmCRUDBibliografiaC.getTblTablaCB().getValueAt(select, 0).toString();
            System.out.println(" "+codigo);
            BDbibliotecaC.eliminar(conexion, codigo); 
            cargartabla1();
            }else{
                JOptionPane.showMessageDialog(null, "Selecione una fila");
            }
           
            
            
        }
        public void editar(){
               /*frmEditarBiblioteca vista= new  frmEditarBiblioteca();
               ReferenciaBD modelo =new ReferenciaBD()  ;
                int select=frmCRUDBibliografiaC.getTblTablaCB().getSelectedRow();
                if(select>=0){
                String codigo=frmCRUDBibliografiaC.getTblTablaCB().getValueAt(select, 0).toString();
                
              
               ControladorEditar editar=new ControladorEditar(vista,modelo,vtnPrin,codigo,conexion);
               editar.inicia_vista();
                editar.init();
                }*/
               frmBibliografia vista= new  frmBibliografia();
               ReferenciaBD modelo =new ReferenciaBD()  ;
                int select=frmCRUDBibliografiaC.getTblTablaCB().getSelectedRow();
                if(select>=0){
                String codigo=frmCRUDBibliografiaC.getTblTablaCB().getValueAt(select, 0).toString();
                
              
               ControladorEditar editar=new ControladorEditar(vista,modelo,vtnPrin,codigo,conexion);
               editar.inicia_vista();
                editar.init();
                }
            
        }
        public void busqueda(String aguja){
           
         List<ReferenciasMD> referencias=BDbibliotecaC.consultarBiblioteca(conexion,aguja);
              DefaultTableModel modeloTabla;

        modeloTabla=(DefaultTableModel)frmCRUDBibliografiaC.getTblTablaCB().getModel();
       

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
