package controlador.alumno;

import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.curso.CursoBD;
import modelo.curso.CursoMD;
import modelo.estilo.TblEstilo;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.AlumnoBD;
import modelo.persona.AlumnoMD;
import modelo.validaciones.Validar;
import vista.alumno.FrmAlumnoCursoEspecial;

/**
 *
 * @author gus
 */
public class FrmAlumnoCursoEspecialCTR extends DCTR {

    private DefaultTableModel mdTblMP, mdTblMS, mdTblAlm;
    // Listado 
    private List<PeriodoLectivoMD> pls;
    private ArrayList<AlumnoMD> als;
    private ArrayList<String> ncs;
    private ArrayList<CursoMD> tcs, csp, css;
    // BD 
    private final AlumnoBD ALBD;
    private final CursoBD CRBD;
    // Formulario 
    private final FrmAlumnoCursoEspecial FRM = new FrmAlumnoCursoEspecial();

    public FrmAlumnoCursoEspecialCTR(VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
        ALBD = new AlumnoBD(ctrPrin.getConecta());
        CRBD = new CursoBD(ctrPrin.getConecta());
        css = new ArrayList<>();
    }

    public void iniciar() {
        iniciarCmbPeriodo();
        iniciarTbls();
        iniciarBuscador();
        iniciarCmbCurso();
        iniciarAcciones();

        ctrPrin.agregarVtn(FRM);
        vtnCargada = true;
    }

    private void iniciarAcciones() {
        FRM.getBtnPasar1().addActionListener(e -> pasarUno());
        FRM.getBtnPasarTodos().addActionListener(e -> pasarTodos());
        FRM.getBtnRegresar1().addActionListener(e -> regresarUno());
        FRM.getBtnRegresarTodos().addActionListener(e -> regresarTodos());
    }

    private void iniciarCmbPeriodo() {
        FRM.getCmbPrdLectivo().removeAllItems();
        FRM.getCmbPrdLectivo().addItem("Seleccione");
        PeriodoLectivoBD PLBD = new PeriodoLectivoBD(ctrPrin.getConecta());
        pls = PLBD.cargarPeriodoEspecial();
        pls.forEach(e -> {
            FRM.getCmbPrdLectivo().addItem(e.getNombre());
        });
        // Evento al seleccionar un periodo
        FRM.getCmbPrdLectivo().addActionListener(e -> {
            if (vtnCargada) {
                int posPrd = FRM.getCmbPrdLectivo().getSelectedIndex();
                tcs = CRBD.cargarCursosPorPeriodo(
                        pls.get(posPrd - 1).getID()
                );
            }
        });
    }

    private void iniciarCmbCurso() {
        FRM.getCmbCurso().addActionListener(e -> {
            if (vtnCargada) {
                cargarMaterias();
            }
        });
    }

    private void cargarMaterias() {
        int posCurso = FRM.getCmbCurso().getSelectedIndex();
        if (posCurso > 0) {
            String nombre = FRM.getCmbCurso().getSelectedItem().toString();
            csp = new ArrayList<>();
            tcs.forEach(c -> {
                if (c.getNombre().equals(nombre)) {
                    csp.add(c);
                }
            });

            mdTblMP.setRowCount(0);
            csp.forEach(c -> {
                Object[] r = {c.getMateria().getNombre(), "0.0"};
                mdTblMP.addRow(r);
            });
        }
    }

    private Function<String, Void> buscarAlumno() {
        return t -> {
            if (Validar.esLetrasYNumeros(t)) {
                buscarAlumno(t);
            }
            return null;
        };
    }

    private void buscarAlumno(String aguja) {
        als = ALBD.buscarAlumnos(aguja);
        mdTblAlm.setRowCount(0);
        als.forEach(a -> {
            Object[] r = {
                a.getIdentificacion(),
                a.getApellidosNombres()
            };
            mdTblAlm.addRow(r);
        });
    }

    private void iniciarBuscador() {
        listenerTxtBuscar(
                FRM.getTxtBuscar(),
                FRM.getBtnBuscar(),
                buscarAlumno()
        );
    }

    private void clickAlumno() {
        int posAlmn = FRM.getTblAlumnos().getSelectedRow();
        int posPrd = FRM.getCmbPrdLectivo().getSelectedIndex();
        if (posAlmn >= 0) {
            ncs = CRBD.cargarNombreCursosPorPeriodo(
                    pls.get(posPrd - 1).getID(),
                    0,
                    10
            );
            vtnCargada = false;
            FRM.getCmbCurso().removeAllItems();
            FRM.getCmbCurso().addItem("Seleccione");
            ncs.forEach(c -> {
                FRM.getCmbCurso().addItem(c);
            });
            vtnCargada = true;
        }
    }

    private void iniciarTbls() {
        String[] TMP = {"Materia", "Nota"};
        String[] TMS = {"Materia"};
        String[] TAL = {"Cedula", "Nombre"};
        mdTblMP = iniciarTbl(FRM.getTblMateriasPen(), TMP);
        mdTblMS = iniciarTbl(FRM.getTblMateriasSelec(), TMS);
        mdTblAlm = iniciarTbl(FRM.getTblAlumnos(), TAL);
        // Medidas de la columna  de nota 
        TblEstilo.columnaMedida(FRM.getTblMateriasPen(), 1, 60);
        // Evento click en tabla alumnos  
        FRM.getTblAlumnos().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickAlumno();
            }
        });
    }

    private void regresarTodos() {
        css = new ArrayList<>();
        mdTblMS.setRowCount(0);
    }

    private void regresarUno() {
        int pos = FRM.getTblMateriasSelec().getSelectedRow();
        if (pos >= 0) {
            css.remove(pos);
            mdTblMS.removeRow(pos);
        } else {
            JOptionPane.showMessageDialog(
                    FRM,
                    "Debe seleccionar una materia."
            );
        }
    }

    private void pasarUno() {
        int pos = FRM.getTblMateriasPen().getSelectedRow();
        if (pos >= 0) {
            css.add(csp.get(pos));
            csp.remove(pos);
            mdTblMP.removeRow(pos);
            llenarTblMS(css);
        } else {
            JOptionPane.showMessageDialog(
                    FRM,
                    "Debe seleccionar una materia."
            );
        }
    }

    private void pasarTodos() {
        if (!csp.isEmpty()) {
            mdTblMP.setRowCount(0);
            css = csp;
            llenarTblMS(css);
        } else {
            JOptionPane.showMessageDialog(
                    FRM,
                    "No hay materias que pasar."
            );
        }
    }

    private void llenarTblMS(ArrayList<CursoMD> cs) {
        mdTblMS.setRowCount(0);
        cs.forEach(c -> {
            Object[] r = {c.getMateria().getNombre()};
            mdTblMS.addRow(r);
        });
    }

}
