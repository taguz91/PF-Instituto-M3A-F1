package controlador.asistenciaAlumnos;

import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import modelo.asistencia.AsistenciaMD;
import modelo.asistencia.FechasClase;
import modelo.asistencia.GenerarFechas;
import modelo.asistencia.NEWAsistenciaBD;
import modelo.curso.CursoMD;
import modelo.estilo.TblEstilo;
import modelo.periodolectivo.PeriodoLectivoMD;
import utils.CONS;
import utils.M;
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
    private List<FechasClase> fechas, fechasSelec;
    // Lista para tabla 
    private List<AsistenciaMD> as;
    // Modelo de la tabla 
    private DefaultTableModel mdTbl;
    // Para validar las faltas  
    private int maxFaltas = 0;

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
            "id",
            "#",
            "Alumno",
            "Faltas"
        };
        mdTbl = iniciarTblConEditar(
                VTN.getTblAlumnos(),
                titulo
        );
        TblEstilo.ocualtarID(VTN.getTblAlumnos());
        TblEstilo.columnaMedida(VTN.getTblAlumnos(), 1, 30);
        TblEstilo.columnaMedida(VTN.getTblAlumnos(), 3, 50);
        VTN.getTblAlumnos().setRowHeight(25);
        VTN.getTblAlumnos().setModel(mdTbl);
    }

    private void iniciarAcciones() {
        VTN.getCmbMateria().addActionListener(e -> {
            clickCmbCurso();
        });
        VTN.getCmbPeriodo().addActionListener(e -> clickCmbPeriodo());
        VTN.getBtnCargarLista().addActionListener(e -> cargarLista());
        VTN.getBtnGuardar().addActionListener(e -> guardar());
        iniciarAccionesTbl();
    }

    private void iniciarAccionesTbl() {
        mdTbl.addTableModelListener(new TableModelListener() {
            boolean active = false;

            @Override
            public void tableChanged(TableModelEvent e) {
                if (!active && e.getType() == TableModelEvent.UPDATE) {
                    active = true;
                    actualizarFalta();
                    active = false;
                }
            }
        });
    }

    private void actualizarFalta() {
        int colum = VTN.getTblAlumnos().getSelectedColumn();
        int row = VTN.getTblAlumnos().getSelectedRow();
        String estado = VTN.getTblAlumnos().getValueAt(row, colum).toString();

        if (estado.matches("[0-9]")) {
            int faltas = Integer.parseInt(estado);
            if (faltas > maxFaltas) {
                JOptionPane.showMessageDialog(VTN, "Hoy solo tenemos " + maxFaltas + " horas de clase.");
                VTN.getTblAlumnos().setValueAt(maxFaltas, row, colum);
            } else {
                VTN.getTblAlumnos().setValueAt(faltas, row, colum);
            }
        } else {
            VTN.getTblAlumnos().setValueAt(0, row, colum);
        }
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
            fechasSelec = fechas;
            VTN.getCmbFechas().removeAllItems();
            VTN.getCmbFechas().addItem("");
            LocalDate ld = LocalDate.now();
            String fechaActual = ld.getDayOfMonth() + "/"
                    + ld.getMonthValue() + "/"
                    + ld.getYear();
            fechas.forEach(f -> {
                VTN.getCmbFechas().addItem(f.getFecha());
                if (f.getFecha().equals(fechaActual)) {
                    VTN.getCmbFechas().setSelectedItem(
                            fechaActual
                    );
                }
            });
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
        fechasSelec = new ArrayList<>();
        fechas.forEach(f -> {
            if (f.getFecha().contains(aguja)) {
                VTN.getCmbFechas().addItem(f.getFecha());
                fechasSelec.add(f);
            }
        });
    }

    private void cargarLista() {
        String fecha = VTN.getCmbFechas().getSelectedItem().toString();
        int posCurso = VTN.getCmbMateria().getSelectedIndex();
        int posFecha = VTN.getCmbFechas().getSelectedIndex();
        if (!fecha.equals("") && posCurso > 0 && posFecha > 0) {
            as = ABD.getAlumnosCursoFicha(
                    cs.get(posCurso - 1).getId(),
                    fecha
            );
            maxFaltas = fechasSelec.get(posFecha - 1).getHoras();
            VTN.getLblInfo().setText(maxFaltas + " numero de horas clase.");
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

    private int numAlum;

    private void llenarTbl(List<AsistenciaMD> as) {
        mdTbl.setRowCount(0);
        numAlum = 0;
        as.forEach(a -> {
            numAlum++;
            Object[] r = {
                a.getId(),
                numAlum,
                a.getAlumnoCurso().getAlumno().getApellidosNombres(),
                a.getNumeroFaltas()
            };
            mdTbl.addRow(r);
        });
    }

    private void guardar() {
        String sql = "";
        for (int i = 0; i < VTN.getTblAlumnos().getRowCount(); i++) {
            sql += ABD.getSqlActualizar(
                    Integer.parseInt(VTN.getTblAlumnos().getValueAt(i, 0).toString()),
                    Integer.parseInt(VTN.getTblAlumnos().getValueAt(i, 3).toString())
            );
        }
        System.out.println("Sql hecho: \n" + sql);
        if (ABD.actualizarFaltas(sql)) {
            JOptionPane.showMessageDialog(VTN, "Guardamos correctamente las faltas.");
        } else {
            M.errorMsg("Error al guardar las faltas.");
        }
    }

}
