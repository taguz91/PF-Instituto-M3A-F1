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
import modelo.alumno.AlumnoCursoBD;
import modelo.alumno.MatriculaBD;
import modelo.alumno.MatriculaMD;
import modelo.curso.CursoBD;
import modelo.curso.CursoMD;
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
    private final AlumnoBD ALBD = AlumnoBD.single();
    private final CursoBD CRBD = CursoBD.single();
    private final AlumnoCursoBD ACBD = AlumnoCursoBD.single();
    private final MatriculaBD MTBD = MatriculaBD.single();
    // Formulario 
    private final FrmAlumnoCursoEspecial FRM = new FrmAlumnoCursoEspecial();
    // Para los mensajes de confirmacion
    private String materiasMatricula = "";
    private int numMateria = 0;

    public FrmAlumnoCursoEspecialCTR(VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
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
        FRM.getBtnGuardar().addActionListener(e -> guardar());
    }

    private void iniciarCmbPeriodo() {
        FRM.getCmbPrdLectivo().removeAllItems();
        FRM.getCmbPrdLectivo().addItem("Seleccione");
        PeriodoLectivoBD PLBD = PeriodoLectivoBD.single();
        pls = PLBD.cargarPeriodoEspecial();
        pls.forEach(e -> {
            FRM.getCmbPrdLectivo().addItem(e.getNombre());
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
        if (posAlmn >= 0 && posPrd > 0) {
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

            tcs = CRBD.buscarCursosPorPeriodoAlumno(
                    pls.get(posPrd - 1).getID(),
                    als.get(posAlmn).getId_Alumno()
            );
        }
    }

    private void iniciarTbls() {
        String[] TM = {"Materia"};
        String[] TAL = {"Cedula", "Nombre"};
        mdTblMP = iniciarTbl(FRM.getTblMateriasPen(), TM);
        mdTblMS = iniciarTbl(FRM.getTblMateriasSelec(), TM);
        mdTblAlm = iniciarTbl(FRM.getTblAlumnos(), TAL);
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

    private void guardar() {
        boolean guardar = !css.isEmpty();
        int posAlm = FRM.getTblAlumnos().getSelectedRow();
        int posPrd = FRM.getCmbPrdLectivo().getSelectedIndex();

        if (posAlm < 0 && posPrd < 1) {
            guardar = false;
        }

        if (guardar) {
            // Receteamos todos 
            ACBD.borrarMatricula();
            materiasMatricula = "";
            numMateria = 1;
            css.forEach(c -> {
                ACBD.agregarMatricula(
                        als.get(posAlm).getId_Alumno(),
                        c.getId(),
                        1
                );

                materiasMatricula = materiasMatricula + numMateria + ": "
                        + "  Curso: " + c.getNombre()
                        + "  Matricula: " + c.getNumMatricula()
                        + "  Materia: " + c.getMateria().getNombre()
                        + "    \n";
                numMateria++;
            });

            int r = JOptionPane.showConfirmDialog(ctrPrin.getVtnPrin(), "Se matricula a: \n"
                    + als.get(posAlm).getNombreCorto() + "\n"
                    + "Periodo: \n" + pls.get(posPrd - 1).getNombre() + "\n"
                    + "En las siguientes materias: \n" + materiasMatricula);

            if (r == JOptionPane.YES_OPTION) {

                MatriculaMD m = MTBD.buscarMatriculaAlmnPrd(
                        als.get(posAlm).getId_Alumno(),
                        pls.get(posPrd - 1).getID()
                );

                if (m == null) {
                    MatriculaMD matricula = new MatriculaMD();
                    matricula.setAlumno(als.get(posAlm));
                    matricula.setPeriodo(pls.get(posPrd - 1));
                    matricula.setTipo("ORDINARIA");
                    MTBD.ingresar(matricula);
                }

                if (ACBD.guardarAlmnCurso()) {
                    JOptionPane.showMessageDialog(FRM, "Guadamos correctamente.");
                    recetearFrm();
                } else {
                    JOptionPane.showMessageDialog(FRM, "No pudimos guardar.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(FRM, "El formulario contiene errores.");
        }
    }

    private void recetearFrm() {
        css = new ArrayList<>();
        csp = css;
        FRM.getTxtBuscar().setText("");
        mdTblAlm.setRowCount(0);
        mdTblMP.setRowCount(0);
        mdTblMS.setRowCount(0);
        FRM.getCmbCurso().removeAllItems();
    }

}
