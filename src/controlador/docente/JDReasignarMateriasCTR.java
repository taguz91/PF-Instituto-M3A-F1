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
import javax.swing.table.DefaultTableModel;
import modelo.curso.CursoMD;
import modelo.estilo.TblEstilo;
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
    private final DocenteBD docenteBD;
    private DocenteMD docenteMD;
    private final String nombre;
    private final JDReasignarMaterias frmReasignarMateria;
    private ArrayList<DocenteMD> docentes;
    private DefaultTableModel mdTbl;
    //int materia = frmFinContrato.getTblMateriasCursos();

    public JDReasignarMateriasCTR(VtnPrincipalCTR ctrPrin, String nombre) {
        super(ctrPrin);
        this.docenteBD = new DocenteBD(ctrPrin.getConecta());
        this.nombre = nombre;
        this.frmReasignarMateria = new JDReasignarMaterias(ctrPrin.getVtnPrin(), false);
        this.frmReasignarMateria.setLocationRelativeTo(ctrPrin.getVtnPrin());
        this.frmReasignarMateria.setVisible(true);
        this.frmReasignarMateria.setTitle("Reasignar Materias");

    }

    public void iniciar() {
        frmReasignarMateria.getLblMateria().setText(nombre);
        String[] titulo = {"Cedula", "Nombres Completos"};
        String[][] datos = {};
        mdTbl = TblEstilo.modelTblSinEditar(datos, titulo);
        frmReasignarMateria.getTblDocentesDisponibles().setModel(mdTbl);
        TblEstilo.formatoTbl(frmReasignarMateria.getTblDocentesDisponibles());
        cargarDocentes();
        //Buscador 
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
        //Clcik buscar 
        frmReasignarMateria.getTxtBuscar().addKeyListener(new TxtVBuscador(frmReasignarMateria.getTxtBuscar(),
                frmReasignarMateria.getBtnBuscar()));
        frmReasignarMateria.getBtnCancelar().addActionListener(e -> salir());
        frmReasignarMateria.getBtnReasignarMateria().addActionListener(e -> reasignarMaterias());
    }

    private void salir() {
        frmReasignarMateria.dispose();
    }

    private void reasignarMaterias() {

    }

    private void cargarDocentes() {
        docentes = docenteBD.cargarDocentesParaReasignarMaterias();
        llenarTblDocentes(docentes);
    }

    private void buscaIncremental(String aguja) {
        if (Validar.esLetrasYNumeros(aguja)) {
            docentes = docenteBD.buscar(aguja);
            llenarTblDocentes(docentes);
        }
    }

    private void llenarTblDocentes(ArrayList<DocenteMD> docentes) {
        mdTbl.setRowCount(0);
        if (docentes != null) {
            docentes.forEach(dc -> {
                Object[] valores = {dc.getCodigo(), dc.getPrimerApellido() + " "
                    + dc.getSegundoApellido() + " " + dc.getPrimerNombre()
                    + " " + dc.getSegundoNombre()};
                mdTbl.addRow(valores);
            });
            frmReasignarMateria.getLblResultados().setText(String.valueOf(docentes.size()) + " Resultados obtenidos.");
        } else {
            frmReasignarMateria.getLblResultados().setText("0 Resultados obtenidos.");
        }
    }

}
