/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Optional;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConexionBD;
import modelo.PlanClases.JornadasDB;
import modelo.PlanClases.PlandeClasesBD;
import modelo.PlanClases.PlandeClasesMD;
import modelo.carrera.CarreraMD;
import modelo.jornada.JornadaMD;
import modelo.silabo.CarrerasBDS;
import modelo.usuario.UsuarioBD;
import vista.principal.VtnPrincipal;
import vista.silabos.frmCRUDPlanClase;



/**
 *
 * @author Daniel
 */
public class ControladorCRUDPlanClase {
  private final UsuarioBD usuario;
  private ConexionBD conexion;
  private final VtnPrincipal principal;
  private frmCRUDPlanClase fCrud_plan_Clases;
  private List<CarreraMD> carreras_docente;
  private List<JornadaMD> lista_jornadas;
  private List<PlandeClasesMD> lista_plan_clases;
    public ControladorCRUDPlanClase(UsuarioBD usuario, ConexionBD conexion, VtnPrincipal principal) {
        this.usuario = usuario;
        this.conexion = conexion;
        this.principal = principal;
    }
  
  public void iniciaControlador(){
        conexion.conectar();

        fCrud_plan_Clases = new frmCRUDPlanClase();
        principal.getDpnlPrincipal().add(fCrud_plan_Clases);
        fCrud_plan_Clases.setTitle("Configuración Plan de Clases");
        fCrud_plan_Clases.show();

        fCrud_plan_Clases.setLocation((principal.getDpnlPrincipal().getSize().width - fCrud_plan_Clases.getSize().width) / 2,
                (principal.getDpnlPrincipal().getSize().height - fCrud_plan_Clases.getSize().height) / 2);
         
        fCrud_plan_Clases.getBtnNuevoPLC().addActionListener(a -> { 
            fCrud_plan_Clases.dispose();
         ControladorConfiguracion_plan_clases cp = new ControladorConfiguracion_plan_clases(usuario, principal, conexion);
        cp.iniciarControlaador();
        });
        fCrud_plan_Clases.getCmb_Carreras().addActionListener(a-> cargarPlanesDeClaseProfesor());
        fCrud_plan_Clases.getCmbJornadas().addActionListener(a-> cargarPlanesDeClaseProfesor());
        fCrud_plan_Clases.getTxtBuscarPLC().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {

                  cargarPlanesDeClaseProfesor();
            }
            });
            
        fCrud_plan_Clases.getBtnEliminarPLC().addActionListener((ActionEvent ae) -> {
            int row=fCrud_plan_Clases.getTlbTablaPLC().getSelectedRow();
            if(row!=-1){
                eliminarPlanClase();
                cargarPlanesDeClaseProfesor();
            }else{
                JOptionPane.showMessageDialog(null, "Seleccione un plan de clase", "Aviso", JOptionPane.ERROR_MESSAGE);
            }
        });
        CARGAR_COMBO_CARRERAS();
        CARGAR_JORNADAS();
        cargarPlanesDeClaseProfesor();
  } 
  
  private void cargarPlanesDeClaseProfesor(){
      try {
          DefaultTableModel modelotabla;
          modelotabla=(DefaultTableModel)fCrud_plan_Clases.getTlbTablaPLC().getModel();
          String [] parametros={fCrud_plan_Clases.getCmb_Carreras().getSelectedItem().toString(),fCrud_plan_Clases.getCmbJornadas().getSelectedItem().toString(),fCrud_plan_Clases.getTxtBuscarPLC().getText(),String.valueOf(usuario.getPersona().getIdPersona())};
          lista_plan_clases=PlandeClasesBD.consultarPlanClase(conexion, parametros);
          for (int j =  fCrud_plan_Clases.getTlbTablaPLC().getModel().getRowCount()-1; j>=0;j--) {
               modelotabla.removeRow(j);
          }
          
          for (PlandeClasesMD plc : lista_plan_clases) {
              modelotabla.addRow(new Object[]{
                  plc.getId_persona().getPrimerApellido()+" "+plc.getId_persona().getPrimerNombre(),plc.getId_materia().getNombre(),plc.getId_plan_clases(),plc.getId_unidad().getIdUnidad()
              });
          }
          
          fCrud_plan_Clases.getTlbTablaPLC().setModel(modelotabla);
      } catch (Exception e) {
      }
      
  }
  
   private void CARGAR_COMBO_CARRERAS(){
        fCrud_plan_Clases.getCmb_Carreras().removeAllItems();
        carreras_docente=CarrerasBDS.consultar(conexion, usuario.getUsername());
        
        if(carreras_docente==null){
            JOptionPane.showMessageDialog(null, "No tiene carreras asignadas");
        }else{
        carreras_docente.forEach((cmd) -> {
            fCrud_plan_Clases.getCmb_Carreras().addItem(cmd.getNombre());
        });
        }
    }
   
   private void CARGAR_JORNADAS(){
       fCrud_plan_Clases.getCmbJornadas().removeAllItems();
       lista_jornadas=JornadasDB.consultarJornadas(conexion);
       lista_jornadas.forEach((lj)-> { 
           fCrud_plan_Clases.getCmbJornadas().addItem(lj.getNombre());
       });
       
   }
   private PlandeClasesMD plan_clas_selecc(){
       int seleccion=fCrud_plan_Clases.getTlbTablaPLC().getSelectedRow();
       Optional<PlandeClasesMD> plan_clase_selec=lista_plan_clases.stream().
               filter(pl -> pl.getId_plan_clases()==Integer.parseInt(fCrud_plan_Clases.getTlbTablaPLC().getValueAt(seleccion, 2).toString())).findFirst();
       
       return plan_clase_selec.get();
   }
   
   private void eliminarPlanClase(){
       int reply = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar este silabo?", "Eliminar", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            new PlandeClasesBD(conexion).eliminarPlanClase(plan_clas_selecc());
            JOptionPane.showMessageDialog(null, "Silabo eliminado correctamente");
        }
   }
}
