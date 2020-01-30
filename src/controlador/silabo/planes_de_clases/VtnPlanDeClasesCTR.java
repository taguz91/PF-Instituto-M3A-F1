package controlador.silabo.planes_de_clases;

import controlador.Libraries.Middlewares;
import controlador.Libraries.abstracts.AbstractVTN;
import controlador.Libraries.cellEditor.ComboBoxCellEditor;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import modelo.PlanClases.PlandeClasesBD;
import modelo.PlanClases.PlandeClasesMD;
import modelo.curso.CursoMD;
import modelo.jornada.JornadaBD;
import modelo.jornada.JornadaMD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.silabo.NEWPeriodoLectivoBD;
import utils.CONS;
import vista.silabos.new_planes_de_clase.VtnPlanDeClases;

/**
 *
 * @author MrRainx
 */
public class VtnPlanDeClasesCTR extends AbstractVTN<VtnPlanDeClases, PlandeClasesMD> {

    private List<PeriodoLectivoMD> periodos;
    private List<JornadaMD> jornadas;
    private final Integer idPersona = CONS.USUARIO.getPersona().getIdPersona();
    private final PlandeClasesBD CON = PlandeClasesBD.single();

    public VtnPlanDeClasesCTR(VtnPrincipalCTR desktop) {
        super(desktop);
        this.vista = new VtnPlanDeClases();
    }

    @Override
    public void Init() {

        this.vista.getChxSuperSu().setVisible(CONS.USUARIO.isIsSuperUser());
        setTable(vista.getTbl());
        this.periodos = getPeriodos();

        if (this.periodos != null) {
            this.jornadas = JornadaBD.cargarJornadas();
            cargarCmbPeriodos();
            cargarJornadas();
            cargarTabla(cargador());
        }

        this.vista.getBtnEliminar().setVisible(CONS.ROL.getNombre().equalsIgnoreCase("COORDINADOR"));
        this.vista.getBtnEditarFecha().setVisible(CONS.ROL.getNombre().equalsIgnoreCase("COORDINADOR"));

        InitEventos();
        super.Init();
    }

    private void InitEventos() {
        this.vista.getChxSuperSu().addActionListener(this::chxSuperSU);

        this.vista.getCmbPeriodos().addActionListener(this::cargarTablaAsEvent);
        this.vista.getCmbJornadas().addActionListener(this::cargarTablaAsEvent);
        this.vista.getBtnNuevo().addActionListener(this::btnNuevo);
        this.vista.getBtnEditar().addActionListener(e -> btnEditar(e));
        this.vista.getBtnEliminar().addActionListener(e -> btnEliminar(e));
        this.vista.getBtnCopiar().addActionListener(e -> btnCopiar(e));
        this.vista.getBtnEditarFecha().addActionListener(e -> btnEditarFecha(e));
        this.vista.getBtnImprimir().addActionListener(e -> btnImprimir(e));

        boolean estado = CONS.ROL.getNombre().equalsIgnoreCase("COORDINADOR")
                || CONS.ROL.getNombre().equalsIgnoreCase("DEV")
                || CONS.USUARIO.isIsSuperUser();

        vista.getTbl()
                .getColumnModel()
                .getColumn(6)
                .setCellEditor(new ComboBoxCellEditor(
                        estado,
                        Arrays.asList("APROBADO", "PENDIENTE", "REVISAR"))
                );
        tableM.addTableModelListener(new TableModelListener() {
            boolean active = false;

            @Override
            public void tableChanged(TableModelEvent e) {

                if (!active && e.getType() == TableModelEvent.UPDATE) {
                    active = true;
                    cmbTblEstado();
                    active = false;
                }

            }

        });
    }

    /*
        FACTORIZACION
     */
    private List<PeriodoLectivoMD> getPeriodos() {

        if (vista.getChxSuperSu().isSelected()) {
            return NEWPeriodoLectivoBD.selectAllDEV();
        } else if (CONS.is("DOCENTE")) {
            return NEWPeriodoLectivoBD.getMisPeriodosBy(idPersona);
        } else if (CONS.is("COORDINADOR")) {
            return NEWPeriodoLectivoBD.getPeriodosCoordinador(idPersona);
        } else if (CONS.isSuperSU()) {
            return NEWPeriodoLectivoBD.selectAllDEV();
        }

        return null;
    }

    private void cargarCmbPeriodos() {

        this.vista.getCmbPeriodos().removeAllItems();

        if (this.periodos != null) {
            this.periodos
                    .stream()
                    .map(c -> c.getNombre())
                    .forEach(this.vista.getCmbPeriodos()::addItem);
        } else {
            JOptionPane.showMessageDialog(vista, "NO TIENE PERIODOS LECTIVOS ASIGNADOS");
        }

    }

    private void cargarJornadas() {
        this.jornadas.stream()
                .map(c -> c.getNombre())
                .forEachOrdered(this.vista.getCmbJornadas()::addItem);
    }

    private Consumer<PlandeClasesMD> cargador() {
        try {

            String periodo = this.vista.getCmbPeriodos().getSelectedItem().toString();
            String cedulaDocente = CONS.USUARIO.getPersona().getIdentificacion();
            String jornada = this.vista.getCmbJornadas().getSelectedItem().toString();

            boolean isSuperUserChecked = this.vista.getChxSuperSu().isSelected();
            if (isSuperUserChecked) {

            } else {
            }

            if (vista.getChxSuperSu().isSelected()) {
                setLista(CON.getPlanesSuperSu(periodo, jornada));
            } else if (CONS.is("DOCENTE")) {
                setLista(CON.getPlanesBy(cedulaDocente, periodo, jornada));
            } else if (CONS.is("COORDINADOR")) {
                setLista(CON.getPlanesCoordinadorBy(cedulaDocente, periodo, jornada));
            } else if (CONS.isSuperSU()) {
                setLista(CON.getPlanesSuperSu(periodo, jornada));
            }

            return obj -> {
                tableM.addRow(new Object[]{
                    obj.getID(),
                    tableM.getRowCount() + 1,
                    obj.getInfoDocente(),
                    obj.getCurso().getMateria().getNombre(),
                    obj.getCurso().getNombre(),
                    obj.getUnidad().getNumeroUnidad(),
                    PlandeClasesMD.getEstadoStr(obj.getEstado()),
                    obj.getFechaGeneracion()
                });
            };
        } catch (NullPointerException e) {
        }
        return obj -> {
        };
    }

    /*
        EVENTOS
     */
    private void chxSuperSU(ActionEvent e) {
        this.periodos = getPeriodos();
        this.cargarCmbPeriodos();
    }

    private void cargarTablaAsEvent(ActionEvent e) {
        cargarTabla(cargador());

    }

    private void btnNuevo(ActionEvent e) {
        FrmConfigPlanCTR form = new FrmConfigPlanCTR(desktop);
        form.Init();
    }

    private void btnEditar(ActionEvent e) {

        int row = getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA PRIMERO!!");
        } else {
            int idPlan = Integer.valueOf(getTableM().getValueAt(row, 0).toString());
            PlandeClasesMD plandeClasesMD = PlandeClasesBD.getPlanBy(idPlan);
            FrmPlanDeClasesCTR form = new FrmPlanDeClasesCTR(desktop);
            form.setAccion("edit");
            form.setModelo(plandeClasesMD);
            form.Init();
        }
    }

    private void btnEliminar(ActionEvent e) {

        int row = getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA PRIMERO!!");
        } else {
            int idPlan = Integer.valueOf(getTableM().getValueAt(row, 0).toString());
            PlandeClasesBD.eliminarPlanClase(idPlan);
            cargarTabla(cargador());
        }

    }

    private void btnCopiar(ActionEvent e) {

int row = vista.getTbl().getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    vista,
                    "SELECCIONE UN PLAN DE CLASES PARA COPIAR",
                    "AVISO!!",
                    JOptionPane.OK_OPTION
            );
        } else {

            int idPlan = (Integer) vista.getTbl().getValueAt(row, 0);


            PlandeClasesMD plan = lista.stream()
                    .filter(item -> item.getID().equals(idPlan))
                    .findFirst()
                    .get();
            List<CursoMD> cursos = CON.cursosSinPlanes(plan.getID());

            if (cursos.isEmpty()) {
                JOptionPane.showMessageDialog(
                        vista,
                        "NO TIENE CURSOS PENDIENTES",
                        "AVISO!!",
                        JOptionPane.OK_OPTION
                );
            } else {
                VtnCopiarPlanCTR vtn = new VtnCopiarPlanCTR(desktop);
                vtn.setModelo(plan);
                vtn.setCursos(cursos);
                vtn.Init();
            }

        }

    }

    private void btnEditarFecha(ActionEvent e) {

        int row = getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA PRIMERO!!");
        } else {
            int idPlan = Integer.valueOf(getTableM().getValueAt(row, 0).toString());
            PlandeClasesMD plan = this.lista.stream()
                    .filter(item -> item.getID() == idPlan)
                    .findFirst()
                    .get();
            VtnEditarFechaPlanCTR vtn = new VtnEditarFechaPlanCTR(desktop, plan);
            vtn.Init();
        }

    }

    private void btnImprimir(ActionEvent e) {

        int row = vista.getTbl().getSelectedRow();

        if (row >= 0) {
            Map parametro = new HashMap();
            int idPlan = Integer.valueOf(getTableM().getValueAt(row, 0).toString());
            PlandeClasesMD plan = this.lista.stream()
                    .filter(item -> item.getID() == idPlan)
                    .findFirst()
                    .get();
            parametro.put("id_unidad", String.valueOf(plan.getUnidad().getID()));
            parametro.put("id_curso", String.valueOf(plan.getCurso().getId()));
            parametro.put("id_plan_clase", String.valueOf(idPlan));

            Middlewares.generarReporte(
                    getClass().getResource("/vista/silabos/reportes/plan_de_clase/planClasePagPrincipal.jasper"),
                    "PLAN DE CLASES",
                    parametro);

        } else {
            JOptionPane.showMessageDialog(null, "DEBE SELECIONAR EL DOCUMENTO PARA IMPRIMIR");
        }

    }

    private void cmbTblEstado() {
        int colum = table.getSelectedColumn();
        int row = getSelectedRow();
        int idPlan = Integer.valueOf(getTableM().getValueAt(row, 0).toString());
        PlandeClasesMD plan = this.lista.stream()
                .filter(item -> item.getID() == idPlan)
                .findFirst()
                .get();

        if (plan != null) {
            String estado = table.getValueAt(row, colum).toString();

            if (plan.getEstado() != PlandeClasesMD.getEstadoInt(estado)) {
                plan.setEstado(PlandeClasesMD.getEstadoInt(estado));
                cargarTabla(cargador());
                PlandeClasesBD.editarEstado(idPlan, PlandeClasesMD.getEstadoInt(estado));

            }
        }
    }

}
