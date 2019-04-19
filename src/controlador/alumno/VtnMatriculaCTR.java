package controlador.alumno;

import controlador.principal.DependenciasCTR;
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
public class VtnMatriculaCTR extends DependenciasCTR{
    
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
    }
    
    public void iniciar(){
        llenarCmbPrd();
    }
    
    private void llenarCmbPrd(){
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
