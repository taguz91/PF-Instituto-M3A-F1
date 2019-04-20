package controlador.alumno;

import controlador.principal.DependenciasVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import modelo.ConectarDB;
import modelo.alumno.MatriculaBD;
import modelo.alumno.MatriculaMD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.validaciones.Validar;
import vista.alumno.VtnMatricula;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnMatriculaCTR extends DependenciasVtnCTR {

    private final VtnMatricula vtnMatri;
    private final MatriculaBD matr;
    private ArrayList<MatriculaMD> matriculas;

    //Para combos
    private ArrayList<PeriodoLectivoMD> periodos;
    private final PeriodoLectivoBD prd;
    private int posPrd; 

    public VtnMatriculaCTR(ConectarDB conecta, VtnPrincipal vtnPrin, VtnPrincipalCTR ctrPrin, VtnMatricula vtnMatri) {
        super(conecta, vtnPrin, ctrPrin);
        this.matr = new MatriculaBD(conecta);
        this.vtnMatri = vtnMatri;
        this.prd = new PeriodoLectivoBD(conecta);
        //Mostramos en la ventana 
        vtnPrin.getDpnlPrincipal().add(vtnMatri);
        vtnMatri.show();
    }

    public void iniciar() {
        //Iniciamos la tabla 
        String[] t = {"Periodo", "Alumno", "Fecha", "Hora"};
        String[][] d = {};
        iniciarTbl(t, d, vtnMatri.getTblMatricula());

        llenarCmbPrd();
        cargarMatriculas();
        
        iniciarAcciones();
        formatoBuscador(vtnMatri.getTxtBuscar(), vtnMatri.getBtnBuscar());
        iniciarBuscador();
    }
    
    private void iniciarBuscador(){
        vtnMatri.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (vtnMatri.getTxtBuscar().getText().trim().length() > 2) {
                    buscar(vtnMatri.getTxtBuscar().getText().trim()); 
                }else if(vtnMatri.getTxtBuscar().getText().trim().length() == 0){
                    cargarMatriculas();
                }
            }
        });
    }
    
    private void buscar(String aguja){
        if (Validar.esLetrasYNumeros(aguja)) {
            matriculas = matr.buscarMatriculas(aguja); 
            llenarTbl(matriculas);
        }
    }
    
    private void iniciarAcciones(){
        vtnMatri.getCmbPeriodos().addActionListener(e -> clickPrd());
    }
    
    private void cargarMatriculas(){
        matriculas = matr.cargarMatriculas();
        llenarTbl(matriculas);
    }
    
    private void clickPrd(){
        posPrd = vtnMatri.getCmbPeriodos().getSelectedIndex(); 
        if (posPrd > 0) {
            matriculas = matr.cargarMatriculasPorPrd(periodos.get(posPrd - 1).getId_PerioLectivo()); 
            llenarTbl(matriculas);
        }else{
            cargarMatriculas();
        }
    }

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
    
    private void llenarTbl(ArrayList<MatriculaMD> matriculas){
        mdTbl.setRowCount(0);
        if (matriculas != null) {
            matriculas.forEach(m -> {
                Object[] v = {m.getPeriodo().getNombre_PerLectivo(), 
                m.getAlumno().getNombreCompleto(), 
                m.getSoloFecha(), m.getSoloHora()}; 
                mdTbl.addRow(v);
            });
            vtnMatri.getLblNumResultados().setText(matriculas.size()+" Resultados obtenidos.");
        }else{
            vtnMatri.getLblNumResultados().setText("0 Resultados obtenidos.");
        }
    }

}
