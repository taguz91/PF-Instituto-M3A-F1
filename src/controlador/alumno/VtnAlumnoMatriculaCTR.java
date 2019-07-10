/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.alumno;

import controlador.principal.DVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import modelo.alumno.AlumnoMatriculaBD;
import modelo.alumno.AlumnoMatriculaMD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.validaciones.Validar;
import vista.alumno.VtnAlumnoMatricula;

/**
 *
 * @author gus
 */
public class VtnAlumnoMatriculaCTR extends DVtnCTR {
    
    private final VtnAlumnoMatricula vtnMatri; 
    private final AlumnoMatriculaBD almMatri; 
    private ArrayList<AlumnoMatriculaMD> almnMatricula;
    
    //Para combos
    private ArrayList<PeriodoLectivoMD> periodos;
    private final PeriodoLectivoBD prd;
    private int posPrd;
    
    public VtnAlumnoMatriculaCTR(VtnPrincipalCTR ctrPrin, VtnAlumnoMatricula vtnMatri) {
        super(ctrPrin);
        this.almMatri = new AlumnoMatriculaBD(ctrPrin.getConecta());
        this.vtnMatri = vtnMatri;
        this.prd = new PeriodoLectivoBD(ctrPrin.getConecta());
    }
    
    public void iniciar(){
        String[] t = {
            "Periodo", "Cedula", 
            "Nombres", "Apellidos", 
            "Correo", "Celular",
            "Telefono", "Carrera",
            "Cursos"
        }; 
        
        String[][] d = {}; 
        
        iniciarTbl(t, d, vtnMatri.getTblMatricula());
        
        llenarCmbPrd();
        formatoBuscador(vtnMatri.getTxtBuscar(), vtnMatri.getBtnBuscar());
        iniciarBuscador();
        
        vtnMatri.getCmbPeriodos().addActionListener(e -> clickPrd());
        
        cargarAlumnosMatriculas();
        
        ctrPrin.agregarVtn(vtnMatri);
    }
    
    private void iniciarBuscador() {
        vtnMatri.getBtnBuscar().addActionListener(e -> buscar(vtnMatri.getTxtBuscar().getText().trim()));
        
        vtnMatri.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    buscar(vtnMatri.getTxtBuscar().getText().trim());
                } else if (vtnMatri.getTxtBuscar().getText().length() == 0) {
                    cargarAlumnosMatriculas();
                }
            }
        });
    }
    
    private void buscar(String aguja) {
        if (Validar.esLetrasYNumeros(aguja)) {
            almnMatricula = almMatri.buscarPor(aguja);
            llenarTbl(almnMatricula);
        }
    }
    
    private  void cargarAlumnosMatriculas(){
        new Thread(() -> {
            almnMatricula = almMatri.getTodos();
            llenarTbl(almnMatricula);
        }).start();    
    }
    
    private void llenarTbl(ArrayList<AlumnoMatriculaMD> alumnosMatriculas) {
        mdTbl.setRowCount(0);
        if (alumnosMatriculas != null) {
            alumnosMatriculas.forEach(am -> {
                Object[] v = {
                    am.getPeriodo().getNombre_PerLectivo(),
                    am.getAlumno().getIdentificacion(),
                    am.getAlumno().getSoloNombres(),
                    am.getAlumno().getSoloApellidos(), 
                    am.getAlumno().getCorreo(),
                    am.getAlumno().getCelular(),
                    am.getAlumno().getTelefono(),
                    am.getPeriodo().getCarrera().getCodigo(),
                    am.getCursos()
                    };
                mdTbl.addRow(v);
            });
            vtnMatri.getLblNumResultados().setText(alumnosMatriculas.size() + " Resultados obtenidos.");
        } else {
            vtnMatri.getLblNumResultados().setText("0 Resultados obtenidos.");
        }
    }
    
    private void clickPrd() {
        posPrd = vtnMatri.getCmbPeriodos().getSelectedIndex();
        if (posPrd > 0) {
            almnMatricula = almMatri.getPorPeriodo(periodos.get(posPrd - 1).getId_PerioLectivo());
            llenarTbl(almnMatricula);
        } else {
            cargarAlumnosMatriculas();
        }
    }
    
    /**
     * Llenamos el combo del periodo lectivo
     */
    private void llenarCmbPrd() {
        periodos = prd.cargarPrdParaCmbVtn();
        vtnMatri.getCmbPeriodos().removeAllItems();
        if (periodos != null) {
            vtnMatri.getCmbPeriodos().addItem("Seleccione");
            periodos.forEach(p -> {
                vtnMatri.getCmbPeriodos().addItem(p.getNombre_PerLectivo());
            });
        }
    }
    
}
