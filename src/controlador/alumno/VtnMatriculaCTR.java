package controlador.alumno;

import controlador.principal.DependenciasVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import java.util.ArrayList;
import modelo.ConectarDB;
import modelo.alumno.MatriculaBD;
import modelo.alumno.MatriculaMD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
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
    }
    
    public void cargarMatriculas(){
        matriculas = matr.cargarMatriculas();
        llenarTbl(matriculas);
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
        if (matriculas != null) {
            matriculas.forEach(m -> {
                Object[] v = {m.getPeriodo().getNombre_PerLectivo(), 
                m.getAlumno().getNombreCompleto(), 
                m.getSoloFecha(), m.getSoloFecha()}; 
                mdTbl.addRow(v);
            });
        }
    }

}
