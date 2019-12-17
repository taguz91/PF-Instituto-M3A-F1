package controlador.asistenciaAlumnos;

import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;
import javax.swing.JOptionPane;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;
import modelo.asistencia.AsistenciaMD;
import modelo.asistencia.GenerarFechas;
import modelo.asistencia.NEWAsistenciaBD;
import modelo.curso.CursoMD;
import modelo.periodolectivo.PeriodoLectivoMD;
import utils.CONS;
import vista.asistenciaAlumnos.NEWFrmAsistencia;

/**
 *
 * @author gus
 */
public class NEWFrmAsistenciaCTR extends DCTR {

    private final NEWFrmAsistencia VTN = new NEWFrmAsistencia();
    // Conexion a BD
    private final NEWAsistenciaBD ABD = NEWAsistenciaBD.single();
    // Listas para combos 
    private List<PeriodoLectivoMD> pls;
    private List<CursoMD> cs;
    private List<String> fechas;
    // Lista para tabla 
    private List<AsistenciaMD> as;
    // Modelo de la tabla 
    private DefaultTableModel mdTbl;

    public NEWFrmAsistenciaCTR(VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
    }

    public void iniciar() {
        iniciarCMBPeriodo();
        iniciarTablas();
        iniciarBuscarCmbFechas();
        ctrPrin.agregarVtn(VTN);
        iniciarAcciones();
        vtnCargada = true;
    }

    private void iniciarTablas() {
        String[] titulo = {
            "#",
            "Alumno",
            "Faltas"
        };
        mdTbl = iniciarTbl(
                VTN.getTblAlumnos(),
                titulo
        );
        VTN.getTblAlumnos().setModel(mdTbl);
    }

    private void iniciarAcciones() {
        VTN.getCmbMateria().addActionListener(e -> {
            //vtnCargada = false;
            clickCmbCurso();
        });
        VTN.getCmbPeriodo().addActionListener(e -> clickCmbPeriodo());
        VTN.getBtnCargarLista().addActionListener(e -> cargarLista());
    }

    private void iniciarCMBPeriodo() {
        pls = ABD.getPeriodosDocente(
                CONS.USUARIO.getPersona().getIdentificacion()
        );
        VTN.getCmbPeriodo().removeAllItems();
        VTN.getCmbPeriodo().addItem("Seleccione");
        pls.forEach(p -> {
            VTN.getCmbPeriodo().addItem(p.getNombre());
        });
    }

    private void clickCmbPeriodo() {
        int posPrd = VTN.getCmbPeriodo().getSelectedIndex();
        if (posPrd > 0) {
            cs = ABD.getCursosPeriodoDocente(
                    pls.get(posPrd - 1).getID(),
                    CONS.USUARIO.getPersona().getIdentificacion()
            );
            VTN.getCmbMateria().removeAllItems();
            VTN.getCmbMateria().addItem("Seleccione");
            cs.forEach(c -> {
                VTN.getCmbMateria().addItem(
                        c.getNombre() + " | "
                        + c.getMateria().getNombre()
                );
            });
            vtnCargada = true;
        }
    }

    private void clickCmbCurso() {
        int posCurso = VTN.getCmbMateria().getSelectedIndex();
        if (posCurso > 0) {
            GenerarFechas gf = new GenerarFechas();
            fechas = gf.getFechasClaseCurso(
                    cs.get(posCurso - 1).getId()
            );
            VTN.getCmbFechas().removeAllItems();
            VTN.getCmbFechas().addItem("Sin clases");
            fechas.forEach(f -> {
                VTN.getCmbFechas().addItem(f);
            });
            LocalDate ld = LocalDate.now();
            VTN.getCmbFechas().setSelectedItem(
                    ld.getDayOfMonth() + "/"
                    + ld.getMonthValue() + "/"
                    + ld.getYear()
            );
        }
    }

    private void iniciarBuscarCmbFechas() {
        listenerCmbBuscador(VTN.getCmbFechas(), buscarFun());
    }

    private Function<String, Void> buscarFun() {
        return t -> {
            buscarCmbFechas(t);
            return null;
        };
    }

    private void buscarCmbFechas(String aguja) {
        VTN.getCmbFechas().removeAllItems();
        VTN.getCmbFechas().addItem(aguja);
        fechas.forEach(f -> {
            if (f.contains(aguja)) {
                VTN.getCmbFechas().addItem(f);
            }
        });
    }

    private void cargarLista() {
        String fecha = VTN.getCmbFechas().getSelectedItem().toString();
        int posCurso = VTN.getCmbMateria().getSelectedIndex();
        if (!fecha.equals("")) {
            as = ABD.getAlumnosCursoFicha(
                    cs.get(posCurso - 1).getId(),
                    fecha
            );
            if (as.size() > 0) {
                llenarTbl(as);
            } else {
                ABD.iniciarAsistenciaCursoFecha(
                        cs.get(posCurso - 1).getId(),
                        fecha
                );
                cargarLista();
            }
        } else {
            JOptionPane.showMessageDialog(
                    VTN,
                    "Debe seleccionar un curso y la fecha."
            );
        }
    }
    
    private int numAlum = 1;

    private void llenarTbl(List<AsistenciaMD> as) {
        SpinnerDateModel spinnerModel2 = new SpinnerDateModel();
        mdTbl.setRowCount(0);
        numAlum = 1;
        as.forEach(a -> {
            numAlum++;
            Object[] r = {
                numAlum,
                a.getAlumnoCurso().getAlumno().getApellidosNombres(),
                a.getNumeroFaltas()
            //spinnerModel2
            };
            mdTbl.addRow(r);
        });
    }

}
