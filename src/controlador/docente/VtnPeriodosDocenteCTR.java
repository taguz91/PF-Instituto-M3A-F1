/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.docente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.curso.CursoMD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import modelo.persona.PersonaMD;
import vista.docente.VtnFinContratacion;
import vista.docente.VtnPeriodosDocente;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Lina
 */
public class VtnPeriodosDocenteCTR {

    private final ConectarDB conecta;
    private final VtnPeriodosDocente vtnPeriodoDocente;
    private final VtnPrincipal vtnPrin;
    private static LocalDate fechaInicio;
    private final DocenteMD docente;
    private DefaultTableModel mdTbl;
    PeriodoLectivoBD periodoBD;
    private boolean guardar = false;

    public VtnPeriodosDocenteCTR(ConectarDB conecta, VtnPrincipal vtnPrin, DocenteMD docente) {
        this.vtnPeriodoDocente = new VtnPeriodosDocente(vtnPrin, false);
        this.conecta = conecta;
        this.vtnPrin = vtnPrin;
        this.docente = docente;

        this.vtnPeriodoDocente.setLocationRelativeTo(vtnPrin);
        this.vtnPeriodoDocente.setVisible(true);
        this.vtnPeriodoDocente.setTitle("Periodos Docente");

    }

    public void iniciarPeriodosDocente() {

        vtnPeriodoDocente.getBntAceptar().addActionListener(e -> aceptar());
        vtnPeriodoDocente.getBtnCancelar().addActionListener(e -> cancelarPeriodo());
        vtnPeriodoDocente.getCbx_Periodos().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String periodo = vtnPeriodoDocente.getCbx_Periodos().getSelectedItem().toString();
                if(periodo.equals("NINGUNO")){
                    JOptionPane.showConfirmDialog(null, "Este Docente no tiene ninguna materia asignada al no tener un Período Lectivo");
                } else {
                    llenarTabla(periodo);
                }
            }
            
        });
        
        listaPeriodos();
    }

    public void listaPeriodos() {
        periodoBD = new PeriodoLectivoBD(conecta);

        List<PeriodoLectivoMD> listaPeriodos = periodoBD.llenarTabla();
        for (int i = 0; i < listaPeriodos.size(); i++) {
            vtnPeriodoDocente.getJcbPeriodos().addItem(listaPeriodos.get(i).getNombre_PerLectivo());

        }

    }
    
    public void llenarTabla(String periodo){
        DocenteBD d = new DocenteBD(conecta);
        PeriodoLectivoBD p = new PeriodoLectivoBD(conecta);
        DefaultTableModel modelo_Tabla;
        modelo_Tabla = (DefaultTableModel) vtnPeriodoDocente.getTblMateriasCursos().getModel();
        for (int i = vtnPeriodoDocente.getTblMateriasCursos().getRowCount() - 1; i >= 0; i--) {
            modelo_Tabla.removeRow(i);
        }
        List<CursoMD> lista = d.capturarMaterias(p.buscarPeriodo(periodo).getId_PerioLectivo(), docente.getIdDocente());
        int columnas = modelo_Tabla.getColumnCount();
        for (int i = 0; i < lista.size(); i++) {
            modelo_Tabla.addRow(new Object[columnas]);
            vtnPeriodoDocente.getTblMateriasCursos().setValueAt(lista.get(i).getId_materia().getId(), i, 0);
            vtnPeriodoDocente.getTblMateriasCursos().setValueAt(lista.get(i).getCurso_nombre(), i, 1);
        }
    }

    private void aceptar() {
        vtnPeriodoDocente.getJcbPeriodos().getSelectedItem();

    }

    private void cancelarPeriodo() {
        vtnPeriodoDocente.dispose();
        System.out.println("Se dio clic en cancelar periodo docente");

    }
}
//    public void llenarTabla() {
//        DefaultTableModel modelo_Tabla;
//        modelo_Tabla = (DefaultTableModel) vtnPeriodoDocente.getTblMateriasCursos().getModel();
//        for (int i = vtnPeriodoDocente.getTblMateriasCursos().getRowCount() - 1; i >= 0; i--) {
//            modelo_Tabla.removeRow(i);
//        }
//        List<PersonaMD> lista = bdAlumno.llenarTabla();
//        int columnas = modelo_Tabla.getColumnCount();
//        for (int i = 0; i < lista.size(); i++) {
//            modelo_Tabla.addRow(new Object[columnas]);
//            vtnAlumno.getTblAlumno().setValueAt(String.valueOf(lista.get(i).getIdPersona()), i, 0);
//            vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getIdentificacion(), i, 1);
//            vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getPrimerNombre()
//                    + " " + lista.get(i).getSegundoNombre(), i, 2);
//            vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getPrimerApellido()
//                    + " " + lista.get(i).getSegundoApellido(), i, 3);
//            vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getCorreo(), i, 4);
//        }
//        vtnAlumno.getLblResultados().setText(String.valueOf(lista.size()) + " Resultados obtenidos.");
//    }
//}
