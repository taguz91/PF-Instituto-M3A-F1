package controlador.curso;

import java.util.ArrayList;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import vista.curso.FrmCurso;
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
        //Ocusltamos los errores 
        ocultarErrores();
        //Inicializamos 
        cargarCmbPrdLectivo();
        cargarCmbDocente();
        cargarCmbMateria();

        frmCurso.getCbxPeriodoLectivo().addActionListener(e -> cargarCmbMateria());
        frmCurso.getCbxCiclo().addActionListener(e -> cargarCmbMateria());
    }

    public void ocultarErrores() {
        frmCurso.getLblError().setVisible(false);
        frmCurso.getLblErrorCapacidad().setVisible(false);
        frmCurso.getLblErrorCiclo().setVisible(false);
        frmCurso.getLblErrorDocente().setVisible(false);
        frmCurso.getLblErrorJornada().setVisible(false);
        frmCurso.getLblErrorMateria().setVisible(false);
        frmCurso.getLblErrorParalelo().setVisible(false);
        frmCurso.getLblErrorPrdLectivo().setVisible(false);
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

    private void cargarCmbMateria() {
        //Activamos el combo de materias
        frmCurso.getCbxMateria().setEnabled(true);
        int posPr = frmCurso.getCbxPeriodoLectivo().getSelectedIndex();
        int posCi = frmCurso.getCbxCiclo().getSelectedIndex();
        if (posPr > 0 && posCi == 0) {
            materias = mt.cargarMateriaPorCarrera(periodos.get(posPr - 1).getId());
            if (materias != null) {
                frmCurso.getCbxMateria().removeAllItems();
                frmCurso.getCbxMateria().addItem("Seleccione");
                materias.forEach((m) -> {
                    frmCurso.getCbxMateria().addItem(m.getNombre());
                });
            }
        } else if (posPr > 0 && posCi > 0) {
            cargarCmbMateriaPorCarreraCiclo(periodos.get(posPr - 1).getId());
        } else {
            frmCurso.getCbxMateria().removeAllItems();
            frmCurso.getCbxMateria().setEnabled(false);
        }

    }

    private void cargarCmbMateriaPorCarreraCiclo(int idCarrera) {
        int ciclo = Integer.parseInt(frmCurso.getCbxCiclo().getSelectedItem().toString());
        materias = mt.cargarMateriaPorCarreraCiclo(idCarrera, ciclo);
        if (materias != null) {
            frmCurso.getCbxMateria().removeAllItems();
            frmCurso.getCbxMateria().addItem("Seleccione");
            materias.forEach((m) -> {
                frmCurso.getCbxMateria().addItem(m.getNombre());
            });
        }
    }

    public void cargarCmbDocente() {
        docentes = docen.cargarDocentes();
        if (docentes != null) {
            frmCurso.getCbxDocente().removeAllItems();
            frmCurso.getCbxDocente().addItem("Seleccione");
            docentes.forEach((d) -> {
                frmCurso.getCbxDocente().addItem(d.getPrimerNombre() + " "
                        + d.getPrimerApellido());
            });
        }
    }
    
    public void cargarCmbJornada(){
        
    }

}
