package controlador.materia;

import controlador.principal.DVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import modelo.materia.MateriaRequisitoBD;
import modelo.materia.MateriaRequisitoMD;
import vista.materia.FrmRequisitos;

/**
 *
 * @author Dannes
 */
public class VtnRequisitosCTR extends DVtnCTR {

    private final FrmRequisitos frmreq;
    private final MateriaBD MTBD = MateriaBD.single();

    private final MateriaRequisitoBD MTRBD = MateriaRequisitoBD.single();
    private int idRequisito = -1;
    private boolean editar = false;
    //objeto Materia
    private MateriaMD materia;
    //ArrayList de Materias

    private ArrayList<MateriaMD> materias;

    /*
    *Constructor de la clase
     */
    public VtnRequisitosCTR(VtnPrincipalCTR ctrPrin, FrmRequisitos frmreq, MateriaMD materia) {
        super(ctrPrin);
        this.frmreq = frmreq;
        this.materia = materia;
    }

    /**
     * Inicia la accion del boton,asi como asigna valores a los componentes de
     * la vista
     */
    public void iniciar() {
        frmreq.getLblNombreMateria().setText(materia.getNombre());

        frmreq.getBtnGuardar().addActionListener(e -> guardarMateriaRequisito());
        ctrPrin.agregarVtn(frmreq);

        // Acciones en la ventana  
        frmreq.getJrbCoRequisito().addActionListener(e -> cargarComboMaterias());
        frmreq.getJrbPrerequisito().addActionListener(e -> cargarComboMaterias());
    }

    /**
     * Se encargar de enlistar en un combobox las materias
     */
    private void cargarComboMaterias() {
        boolean co = frmreq.getJrbCoRequisito().isSelected();
        boolean pre = frmreq.getJrbPrerequisito().isSelected();

        if (co) {
            materias = MTBD.getMateriasParaCorequisito(materia.getId());
        }

        if (pre) {
            materias = MTBD.getMateriasParaPrequisitos(materia.getId());
        }

        //materias = materiabd.cargarMateriaPorCarrera(materia.getCarrera().getId());
        frmreq.getCmbrequisitos().removeAllItems();
        frmreq.getCmbrequisitos().addItem("Seleccione");

        materias.forEach(m -> {
            frmreq.getCmbrequisitos().addItem(m.getNombre());

        });

    }

    /*
    *Guardar toda la informacion del formulario Requisitos
    *En la tabla Materia_Requisitos
     */
    public void guardarMateriaRequisito() {

        boolean guardar = true;
        String tipo;
        int posicion;
        //Guarda en a varibale posicion el indice de la materia seleccionada
        posicion = frmreq.getCmbrequisitos().getSelectedIndex();

        //Guarda la materia requisito en su objeto
        MateriaRequisitoMD mr = new MateriaRequisitoMD();
        mr.setMateria(materia);

        if (posicion > 0) {
            materia = materias.get(posicion - 1);
            mr.setMateriaRequisito(materia);
        } else {
            guardar = false;
            JOptionPane.showMessageDialog(null, "Seleccione los datos");
        }

        //Verifica que opcion de los radio buton han sido seleccionados para guardarlo en la variable
        if (frmreq.getJrbCoRequisito().isSelected()) {
            tipo = "C";
            mr.setTipo(tipo);
        } else if (frmreq.getJrbPrerequisito().isSelected()) {
            tipo = "P";
            mr.setTipo(tipo);
        } else {
            guardar = false;
        }
        if (guardar) {
            //Verifica que se haya guardado en el MateriaRequisitosBD   
            if (editar) {
                mr.setId(idRequisito);
                MTRBD.editar(mr);
            } else {
                if (MTRBD.insertarMateriaRequisito(mr)) {
                    JOptionPane.showMessageDialog(null, "Datos guardados correctamente");
                    frmreq.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "La Informacion existe, Por favor seleccione otros datos");
                }
            }
        }
    }

    /**
     * Permite cargar los datos seleccionados en la tabla en el formulario
     * materia paa su porterior edicion
     *
     * @param mr
     */
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
