package controlador.materia;

import controlador.principal.VtnPrincipalCTR;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.ConectarDB;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import modelo.materia.MateriaRequisitoBD;
import vista.materia.FrmRequisitos;
import vista.principal.VtnPrincipal;

/**
 *
 * @author gus
 */
public class VtnRequisitosCTR {

    private final ConectarDB conecta;
    private final VtnPrincipalCTR ctrPrin;

    private final VtnPrincipal vtnPrin;
    private final FrmRequisitos frmreq;
    private final MateriaBD materiabd;
    
    private final MateriaRequisitoBD materiarequisito;
    
    

    //objeto Materia
    MateriaMD materia;
    //ArrayList de Materias

    private ArrayList<MateriaMD> materias;

    public VtnRequisitosCTR(ConectarDB conecta, VtnPrincipalCTR ctrPrin, VtnPrincipal vtnPrin, FrmRequisitos frmreq, MateriaBD materiabd, MateriaMD materia) {
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        this.vtnPrin = vtnPrin;
        this.frmreq = frmreq;
        this.materiabd = materiabd;
        this.materiarequisito = new MateriaRequisitoBD(conecta);
        
        this.materia = materia;
        //agregar la ventana
        vtnPrin.getDpnlPrincipal().add(frmreq);
        frmreq.show();
        System.out.println(materiabd);
        System.out.println(materia.getNombre());
    }

    public void iniciar() {
        frmreq.getLblNombreMateria().setText(materia.getNombre());
        cargarComboMaterias();
        
        frmreq.getBtnGuardar().addActionListener(e -> guardarMateriaRequisito());
        

    }

    private void cargarComboMaterias() {
        materias = materiabd.cargarMateriaPorCarrera(materia.getCarrera().getId());
        frmreq.getCmbrequisitos().removeAllItems();
        frmreq.getCmbrequisitos().addItem("Seleccione");

        materias.forEach(m -> {
            frmreq.getCmbrequisitos().addItem(m.getNombre());

        });

    }

    
        public void guardarMateriaRequisito(){
       
        
        String tipo="";
        
        //Set de materia
        materiarequisito.setMateria(materia);
        
        
        /////////////////////////////////////////////////////////////////////
        int posicion = frmreq.getCmbrequisitos().getSelectedIndex();
        
        
        if(posicion > 0){
        
           
        materia = materias.get(posicion - 1);
       
        //set de materiarequisito
         materiarequisito.setMateriaRequisito(materia);
        }
        
        
        ////////////////////////////////////////////////////////////////////////////// 
        //Valida la opcion del radio buton para asignar P o C
        if(frmreq.getJrbCoRequisito().isSelected()){
        tipo = "C";
        materiarequisito.setTipo(tipo);        
        }else if (frmreq.getJrbPrerequisito().isSelected()){
        tipo = "P";
        
        materiarequisito.setTipo(tipo);
          
        }
        
        
        //Verifica que se haya guardado en el MateriaRequisitosBD   
        if(materiarequisito.insertarMateriaRequisito()){
        JOptionPane.showMessageDialog(null, "Datos ingresados correctamente");
        }else{
        JOptionPane.showMessageDialog(null, "Error al grabar en la tabla MateriaRequisito");
        }
        }
        
        
    
    
    
}
