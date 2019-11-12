/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.docente;

import controlador.principal.DVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.alumno.AlumnoCursoMD;
import modelo.curso.CursoBD;
import modelo.curso.CursoMD;
import modelo.estilo.TblEstilo;
import modelo.materia.MateriaBD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import modelo.validaciones.TxtVBuscador;
import modelo.validaciones.Validar;
import vista.docente.JDReasignarMaterias;

/**
 *
 * @author ista
 */
public class JDReasignarMateriasCTR extends DVtnCTR {

    private PeriodoLectivoBD periodoBD;
    private final DocenteBD dc;
    private CursoMD cursoMD;
    private DocenteMD docenteMD;
    private final String materia;
    private final String curso;
    private final int periodo;
    private final int docente;
    private final JDReasignarMaterias frmReasignarMateria;
    private ArrayList<DocenteMD> docentesMD;
    //int materia = frmFinContrato.getTblMateriasCursos();

    public JDReasignarMateriasCTR(VtnPrincipalCTR ctrPrin, String materia, String curso, int periodo, int docente) {
        super(ctrPrin);
        this.dc = new DocenteBD(ctrPrin.getConecta());
        this.materia = materia;
        this.curso = curso;
        this.periodo = periodo;
        this.docente = docente;
        this.frmReasignarMateria = new JDReasignarMaterias(ctrPrin.getVtnPrin(), false);
        this.frmReasignarMateria.setLocationRelativeTo(ctrPrin.getVtnPrin());
        this.frmReasignarMateria.setVisible(true);
        this.frmReasignarMateria.setTitle("Reasignar Materias");

    }

    public void iniciar() {
        frmReasignarMateria.getLblMateria().setText(materia);
        frmReasignarMateria.getBtnCancelar().addActionListener(e -> salir());
        frmReasignarMateria.getBtnReasignarMateria().addActionListener(e -> reasignarMaterias());
        String[] titulo = {"Cedula", "Nombres Completos", "Celular", "Correo", "Tipo Contrato"};
        String[][] datos = {};
        mdTbl = TblEstilo.modelTblSinEditar(datos, titulo);
        frmReasignarMateria.getTblDocentesDisponibles().setModel(mdTbl);
        TblEstilo.formatoTbl(frmReasignarMateria.getTblDocentesDisponibles());
        TblEstilo.columnaMedida(frmReasignarMateria.getTblDocentesDisponibles(), 0, 85);
        TblEstilo.columnaMedida(frmReasignarMateria.getTblDocentesDisponibles(), 1, 250);
        TblEstilo.columnaMedida(frmReasignarMateria.getTblDocentesDisponibles(), 2, 90);
        TblEstilo.columnaMedida(frmReasignarMateria.getTblDocentesDisponibles(), 3, 230);
        TblEstilo.columnaMedida(frmReasignarMateria.getTblDocentesDisponibles(), 4, 125);
        cargarDocentes();
        frmReasignarMateria.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String b = frmReasignarMateria.getTxtBuscar().getText().toUpperCase().trim();
                if (e.getKeyCode() == 10) {
                    buscaIncremental(b);
                } else if (b.length() == 0) {
                    cargarDocentes();
                }
            }
        });
    }

    private void salir() {
        frmReasignarMateria.dispose();
    }

    private void reasignarMaterias() {
        posFila = frmReasignarMateria.getTblDocentesDisponibles().getSelectedRow();
        if (posFila >= 0) {
            CursoBD bdCurso = new CursoBD(ctrPrin.getConecta());
            DocenteMD d = new DocenteMD();
            MateriaBD bdMateria = new MateriaBD(ctrPrin.getConecta());
            cursoMD = bdCurso.atraparCurso(bdMateria.buscarMateria(materia).getId(), this.periodo, docente, curso);
            d.setIdDocente(dc.buscarDocente(frmReasignarMateria.getTblDocentesDisponibles().getValueAt(posFila, 0).toString()).getIdDocente());
            cursoMD.setDocente(d);
            System.out.println("docente " + d.getIdDocente());
            if(bdCurso.nuevoCurso(cursoMD) == true){
                int curso_New = bdCurso.atraparCurso(bdMateria.buscarMateria(materia).getId(), this.periodo, d.getIdDocente(), curso).getId();
                if(dc.reasignarAlumnoCurso(cursoMD.getId(), curso_New)){
                    if(dc.reasignarNotas(cursoMD.getId(), curso_New)){
                        JOptionPane.showMessageDialog(null, "Se reasignó con éxito las materias y notas al docente seleccionado");
                        frmReasignarMateria.dispose();
                    } else{
                        JOptionPane.showMessageDialog(null, "No se pudo reasignar las notas de esas materias al nuevo docente seleccionado");  
                    }
                } else{
                    JOptionPane.showMessageDialog(null, "No se pudo reasignar las materias al docente seleccionado");
                }
            } else{
                JOptionPane.showMessageDialog(null, "No se pudo finalizar el contrato de este docente");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila ");
        }
    }

    public void buscaIncremental(String aguja) {
        if (Validar.esLetrasYNumeros(aguja)) {
            docentesMD = dc.buscarReasignarMateria(aguja);
            llenarTabla(docentesMD);
        }
    }

    public void cargarTabla(String periodo) {

        DocenteBD d = new DocenteBD(ctrPrin.getConecta());
        PeriodoLectivoBD p = new PeriodoLectivoBD(ctrPrin.getConecta());
        DefaultTableModel modelo_Tabla;
        modelo_Tabla = (DefaultTableModel) frmReasignarMateria.getTblDocentesDisponibles().getModel();
        for (int i = frmReasignarMateria.getTblDocentesDisponibles().getRowCount() - 1; i >= 0; i--) {
            modelo_Tabla.removeRow(i);
        }
        List<CursoMD> lista = d.capturarMaterias(p.buscarPeriodo(periodo).getID(), docenteMD.getIdDocente());
        int columnas = modelo_Tabla.getColumnCount();
        for (int i = 0; i < lista.size(); i++) {
            modelo_Tabla.addRow(new Object[columnas]);
            frmReasignarMateria.getTblDocentesDisponibles().setValueAt(lista.get(i).getDocente().getNombreCompleto(), i, 0);
        }
        frmReasignarMateria.getLblResultados().setText(lista.size() + "resultados obtenidos.");

    }

    private void cargarDocentes() {
        docentesMD = dc.cargarDocentesParaReasignarMaterias();
        llenarTabla(docentesMD);
    }

    public void llenarTabla(ArrayList<DocenteMD> docentesM) {
        mdTbl.setRowCount(0);
        if (docentesM != null) {
            docentesM.forEach(dc -> {
                Object[] valores = {dc.getCodigo(), dc.getPrimerApellido() + " "
                    + dc.getSegundoApellido() + " " + dc.getPrimerNombre()
                    + " " + dc.getSegundoNombre(),
                    dc.getCelular(), dc.getCorreo(), dc.getDocenteTipoTiempo()};
                mdTbl.addRow(valores);
            });
        }
        
    }

}
