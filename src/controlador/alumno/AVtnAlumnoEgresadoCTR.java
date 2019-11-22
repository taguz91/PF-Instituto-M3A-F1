package controlador.alumno;

import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.alumno.Egresado;
import modelo.alumno.EgresadoBD;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.estilo.TblEstilo;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import vista.alumno.VtnAlumnoEgresados;

/**
 *
 * @author gus
 */
public abstract class AVtnAlumnoEgresadoCTR extends DCTR {

    protected final VtnAlumnoEgresados vtn;
    protected List<Egresado> egresados, todosEgresados;
    protected DefaultTableModel mdTbl;
    protected final EgresadoBD EBD = EgresadoBD.single();
    private final CarreraBD CBD;
    private final PeriodoLectivoBD PBD;
    private ArrayList<CarreraMD> todasCarreras;
    private ArrayList<PeriodoLectivoMD> todosPeriodos;
    protected ArrayList<CarreraMD> carreras;
    protected ArrayList<PeriodoLectivoMD> periodos;

    public AVtnAlumnoEgresadoCTR(VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
        this.vtn = new VtnAlumnoEgresados();
        this.CBD = new CarreraBD(ctrPrin.getConecta());
        this.PBD = new PeriodoLectivoBD(ctrPrin.getConecta());
    }

    protected void iniciarVtn(String[] titulo) {
        mdTbl = TblEstilo.modelTblSinEditar(titulo);
        vtn.getTblEgresados().setModel(mdTbl);
        cargarCmb();
        vtn.getCmbCarrera().addActionListener(e -> clickCarreras());
    }

    private void cargarCmb() {
        todasCarreras = CBD.cargarCarrerasCmb();
        todosPeriodos = PBD.cargarPrdParaCmbVtn();
        carreras = todasCarreras;
        periodos = todosPeriodos;
        llenarCmbCarreras(carreras);
        llenarCmbPeriodos(periodos);
    }

    private void clickCarreras() {
        int posCarrera = vtn.getCmbCarrera().getSelectedIndex();
        if (posCarrera > 0) {
            periodos = new ArrayList<>();
            todosPeriodos.forEach(p -> {
                if (p.getCarrera().getId() == carreras.get(posCarrera - 1).getId()) {
                    periodos.add(p);
                }
            });
            llenarCmbPeriodos(periodos);
        } else {
            periodos = todosPeriodos;
            llenarCmbPeriodos(periodos);
        }
    }

    private void llenarCmbCarreras(ArrayList<CarreraMD> carreras) {
        vtn.getCmbCarrera().removeAllItems();
        vtn.getCmbCarrera().addItem("Seleccione");
        carreras.forEach(c -> {
            vtn.getCmbCarrera().addItem(c.getCodigo());
        });
    }

    private void llenarCmbPeriodos(ArrayList<PeriodoLectivoMD> periodos) {
        vtn.getCmbPeriodo().removeAllItems();
        vtn.getCmbPeriodo().addItem("Seleccione");
        periodos.forEach(p -> {
            vtn.getCmbPeriodo().addItem(p.getNombre());
        });
    }

}
