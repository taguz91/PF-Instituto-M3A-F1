/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.docente;

import controlador.principal.DVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import modelo.curso.CursoMD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import vista.docente.JDReasignarMaterias;

/**
 *
 * @author ista
 */
public class JDReasignarMateriasCTR extends DVtnCTR {

    private PeriodoLectivoBD periodoBD;
    private final DocenteBD dc;
    private DocenteMD docenteMD;
    private final String nombre;
    private final JDReasignarMaterias frmReasignarMateria;
    //int materia = frmFinContrato.getTblMateriasCursos();

    public JDReasignarMateriasCTR(VtnPrincipalCTR ctrPrin, String nombre) {
        super(ctrPrin);
        this.dc = new DocenteBD(ctrPrin.getConecta());
        this.nombre = nombre;
        this.frmReasignarMateria = new JDReasignarMaterias(ctrPrin.getVtnPrin(), false);
        this.frmReasignarMateria.setLocationRelativeTo(ctrPrin.getVtnPrin());
        this.frmReasignarMateria.setVisible(true);
        this.frmReasignarMateria.setTitle("Reasignar Materias");

    }

    public void iniciar() {
        frmReasignarMateria.getLblMateria().setText(nombre);
        frmReasignarMateria.getBtnCancelar().addActionListener(e -> salir());
        frmReasignarMateria.getBtnReasignarMateria().addActionListener(e -> reasignarMaterias());
    }

    private void salir() {
        frmReasignarMateria.dispose();
    }

    private void reasignarMaterias() {

        
    }
    
    public void cargarTabla(String periodo){
        
        DocenteBD d = new DocenteBD(ctrPrin.getConecta());
        PeriodoLectivoBD p = new PeriodoLectivoBD(ctrPrin.getConecta());
        DefaultTableModel modelo_Tabla;
        modelo_Tabla = (DefaultTableModel) frmReasignarMateria.getTblDocentesDisponibles().getModel();
        for(int i = frmReasignarMateria.getTblDocentesDisponibles().getRowCount() - 1; i>=0; i-- ){
            modelo_Tabla.removeRow(i);
        }
        List<CursoMD> lista = d.capturarMaterias(p.buscarPeriodo(periodo).getId_PerioLectivo(), docenteMD.getIdDocente());
        int columnas = modelo_Tabla.getColumnCount();
        for (int i = 0; i < lista.size(); i++) {
            modelo_Tabla.addRow(new Object[columnas]);
            frmReasignarMateria.getTblDocentesDisponibles().setValueAt(lista.get(i).getDocente().getNombreCompleto(), i, 0);
        }
        
    }
    

}
