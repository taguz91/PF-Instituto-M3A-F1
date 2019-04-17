package controlador.materia;

import controlador.principal.VtnPrincipalCTR;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.ConectarDB;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import modelo.materia.MateriaRequisitoBD;
import modelo.materia.MateriaRequisitoMD;
import vista.materia.FrmRequisitos;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Dannes
 */
public class VtnRequisitosCTR {

    private final ConectarDB conecta;
    private final VtnPrincipalCTR ctrPrin;

    private final VtnPrincipal vtnPrin;
    private final FrmRequisitos frmreq;
    private final MateriaBD materiabd;

    private final MateriaRequisitoBD materiarequisito;
    private int idRequisito = -1;
    private boolean editar = false;
    private boolean habilitaBoton = false;
    //objeto Materia
    private MateriaMD materia;
    //ArrayList de Materias

    private ArrayList<MateriaMD> materias;

    public VtnRequisitosCTR(ConectarDB conecta, VtnPrincipalCTR ctrPrin, VtnPrincipal vtnPrin, FrmRequisitos frmreq,
            MateriaBD materiabd, MateriaMD materia) {
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
        
    }

    public void iniciar() {
        frmreq.getBtnGuardar().setEnabled(habilitaBoton);
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

    public void guardarMateriaRequisito() {
        
        boolean guardar = true;
        String tipo="";
        int posicion;
        posicion = frmreq.getCmbrequisitos().getSelectedIndex();

        materiarequisito.setMateria(materia);
        
        if (posicion > 0) {
            materia = materias.get(posicion - 1);
            materiarequisito.setMateriaRequisito(materia);
        } else {
            guardar = false;
        }
        
        if (frmreq.getJrbCoRequisito().isSelected()) {
            tipo = "C";
            materiarequisito.setTipo(tipo);
            habilitaBoton = true;
        } else if (frmreq.getJrbPrerequisito().isSelected()) {
            tipo = "P";
           materiarequisito.setTipo(tipo);
           frmreq.getBtnGuardar().setEnabled(true);
           habilitaBoton = true;
           
        } else {
            guardar = false;
        }
        if (guardar) {
            //Verifica que se haya guardado en el MateriaRequisitosBD   
            if (editar) {
                materiarequisito.editar(idRequisito);
            } else {
                if (materiarequisito.insertarMateriaRequisito()) {
                    JOptionPane.showMessageDialog(null, "Datos guardados correctamente");
                } else {
                    JOptionPane.showMessageDialog(null, "No se puede guardar los Datos");
                }
            }

        }
        
        
    }

    public void editar(MateriaRequisitoMD mr) {
        editar = true;
        idRequisito = mr.getId();
        if (mr.getTipo().equalsIgnoreCase("C")) {
            frmreq.getJrbCoRequisito().setSelected(true);
        } else {
            frmreq.getJrbPrerequisito().setSelected(true);
        }

        frmreq.getCmbrequisitos().setSelectedItem(mr.getMateria().getNombre());
    }

}
