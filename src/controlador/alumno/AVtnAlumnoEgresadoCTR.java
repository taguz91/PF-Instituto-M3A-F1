package controlador.alumno;

import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
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
    private final CarreraBD CBD = CarreraBD.single();
    private final PeriodoLectivoBD PBD = PeriodoLectivoBD.single();
    private ArrayList<CarreraMD> todasCarreras;
    private ArrayList<PeriodoLectivoMD> todosPeriodos;
    protected ArrayList<CarreraMD> carreras;
    protected ArrayList<PeriodoLectivoMD> periodos;
    private final JDEgresarAlumnoCTR ECTR;
    private IAlumnoEgresadoVTNCTR VTNCTR;    

    public AVtnAlumnoEgresadoCTR(
            VtnPrincipalCTR ctrPrin
    ) {
        super(ctrPrin);
        this.vtn = new VtnAlumnoEgresados();
        this.ECTR = new JDEgresarAlumnoCTR(ctrPrin);
    }

    protected void iniciarAcciones() {
        vtn.getBtnEliminar().addActionListener(e -> clickEliminar());
        vtn.getBtnEditar().addActionListener(e -> clickEditar());
        vtn.getBtnNotasAlumno().setVisible(false);
        vtn.getBtnNotasPeriodo().setVisible(false);
    }

    private void clickEliminar() {
        int posFila = vtn.getTblEgresados().getSelectedRow();
        if (posFila >= 0) {
            int r = JOptionPane.showConfirmDialog(
                    vtn,
                    "Esta seguro de eliminar el egreso de: \n"
                    + egresados.get(posFila).getAlmnCarrera()
                            .getAlumno().getNombreCompleto()
            );
            if (r == JOptionPane.YES_OPTION) {
                if (EBD.eliminar(egresados.get(posFila).getId())) {
                    JOptionPane.showMessageDialog(
                            vtn,
                            "Eliminamos correctamente."
                    );
                    mdTbl.removeRow(posFila);
                } else {
                    JOptionPane.showMessageDialog(
                            vtn,
                            "No se elimino el registro."
                    );
                }

            }
        } else {
            errorNoSeleccionoFila();
        }
    }

    private void clickEditar() {
        int posFila = vtn.getTblEgresados().getSelectedRow();
        if (posFila >= 0) {
            ECTR.editar(egresados.get(posFila));
        } else {
            errorNoSeleccionoFila();
        }
    }

    protected void iniciarVtn(String[] titulo, IAlumnoEgresadoVTNCTR VTNCTR) {
        this.VTNCTR = VTNCTR;
        mdTbl = TblEstilo.modelTblSinEditar(titulo);
        vtn.getTblEgresados().setModel(mdTbl);
        cargarCmb();
        vtn.getCmbCarrera().addActionListener(e -> clickCarreras());
        vtn.getCmbPeriodo().addActionListener(e -> clickPeriodo());
    }

    private void cargarCmb() {
        todasCarreras = CBD.cargarCarrerasCmb();
        todosPeriodos = PBD.cargarPrdParaCmbVtn();
        carreras = todasCarreras;
        periodos = todosPeriodos;
        llenarCmbCarreras(carreras);
        llenarCmbPeriodos(periodos);
    }

    private void clickPeriodo() {
        int posPeriodo = vtn.getCmbPeriodo().getSelectedIndex();
        if (posPeriodo > 0 && vtnCargada) {
            egresados = new ArrayList<>();
            todosEgresados.forEach(e -> {
                if (e.getPeriodo().getNombre()
                        .equals(vtn.getCmbPeriodo().getSelectedItem().toString())) {
                    egresados.add(e);
                }
            });
            VTNCTR.llenarTbl(egresados);
        } else {
            clickCarreras();
        }
    }

    private void clickCarreras() {
        int posCarrera = vtn.getCmbCarrera().getSelectedIndex();
        if (posCarrera > 0 && vtnCargada) {
            periodos = new ArrayList<>();
            todosPeriodos.forEach(p -> {
                if (p.getCarrera().getId() == carreras.get(posCarrera - 1).getId()) {
                    periodos.add(p);
                }
            });
            llenarCmbPeriodos(periodos);
            egresados = new ArrayList<>();
            todosEgresados.forEach(e -> {
                if (e.getAlmnCarrera().getCarrera().getCodigo()
                        .equals(vtn.getCmbCarrera().getSelectedItem().toString())) {
                    egresados.add(e);
                }
            });
            VTNCTR.llenarTbl(egresados);
        } else {
            periodos = todosPeriodos;
            llenarCmbPeriodos(periodos);
            egresados = todosEgresados;
            VTNCTR.llenarTbl(egresados);
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
