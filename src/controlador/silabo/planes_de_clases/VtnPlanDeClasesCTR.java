package controlador.silabo.planes_de_clases;

import controlador.Libraries.abstracts.AbstractVTN;
import controlador.Libraries.cellEditor.ComboBoxCellEditor;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.JOptionPane;
import modelo.PlanClases.PlandeClasesBD;
import modelo.PlanClases.PlandeClasesMD;
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
        }

        InitEventos();
        super.Init();
    }

    private void InitEventos() {
        this.vista.getChxSuperSu().addActionListener(this::chxSuperSU);

        this.vista.getCmbPeriodos().addActionListener(this::cargarTablaAsEvent);
        this.vista.getCmbJornadas().addActionListener(this::cargarTablaAsEvent);

        boolean estado = CONS.ROL.getNombre().equalsIgnoreCase("COORDINADOR")
                || CONS.ROL.getNombre().equalsIgnoreCase("DEV")
                || CONS.USUARIO.isIsSuperUser();

        vista.getTbl()
                .getColumnModel()
                .getColumn(5)
                .setCellEditor(new ComboBoxCellEditor(
                        estado,
                        Arrays.asList("APROBADO", "PENDIENTE", "REVISAR"))
                );
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
    }

    /*
        EVENTOS
     */
    private void chxSuperSU(ActionEvent e) {
        this.periodos = getPeriodos();
        this.cargarCmbPeriodos();
    }

    private void cargarTablaAsEvent(ActionEvent e) {
        String periodo = this.vista.getCmbPeriodos().getSelectedItem().toString();
        String cedulaDocente = CONS.USUARIO.getPersona().getIdentificacion();
        String jornada = this.vista.getCmbJornadas().getSelectedItem().toString();
        setLista(CON.getPlanesBy(cedulaDocente, periodo, jornada));

        cargarTabla(cargador());

    }

}
