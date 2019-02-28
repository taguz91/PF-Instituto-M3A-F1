package controlador.curso;

import java.util.ArrayList;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import vista.curso.FrmCurso;
import vista.curso.VtnAlumnoCurso;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class FrmCursoCTR {

    private final VtnPrincipal vtnPrin;
    private final FrmCurso frmCurso;

    //Para cargar los datos en la tabla  
    DocenteBD docen = new DocenteBD();
    ArrayList<DocenteMD> docentes;
    //Para carar periodos lectivos 
    PeriodoLectivoBD prd = new PeriodoLectivoBD();
    ArrayList<PeriodoLectivoMD> periodos;
    //Para cargar materias 
    MateriaBD mt = new MateriaBD();
    ArrayList<MateriaMD> materias;
    

    public FrmCursoCTR(VtnPrincipal vtnPrin, FrmCurso frmCurso) {
        this.vtnPrin = vtnPrin;
        this.frmCurso = frmCurso;

        vtnPrin.getDpnlPrincipal().add(frmCurso);
        frmCurso.show();
    }

    public void iniciar() {
        //Inicializamos 
        cargarCmbPrdLectivo();
        
        frmCurso.getCbxPeriodoLectivo().addActionListener(e -> cargarCmbMateria()); 
    }

    public void cargarCmbPrdLectivo() {
        periodos = (ArrayList<PeriodoLectivoMD>) prd.capturarCarrera();
        if (periodos != null) {
            frmCurso.getCbxPeriodoLectivo().removeAllItems();
            frmCurso.getCbxPeriodoLectivo().addItem("Seleccione");
            periodos.forEach((p) -> {
                frmCurso.getCbxPeriodoLectivo().addItem(p.getNombre());
            });
        }
    }

    public void cargarCmbMateria() {
        int posPr = frmCurso.getCbxPeriodoLectivo().getSelectedIndex();
        if (posPr > 0) {
            materias = mt.cargarMateriaPorCarrera(periodos.get(posPr - 1).getId()); 
            if (materias != null) {
                frmCurso.getCbxMateria().removeAllItems(); 
                frmCurso.getCbxMateria().addItem("Seleccione"); 
                materias.forEach((m) -> {
                    frmCurso.getCbxMateria().addItem(m.getNombre()); 
                });
            }
        }
        
    }
    public void cargarCmbDocente(){
    // docentes = docen.
    
    }

}
