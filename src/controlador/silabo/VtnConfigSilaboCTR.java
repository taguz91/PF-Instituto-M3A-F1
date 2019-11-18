package controlador.silabo;

import controlador.Libraries.abstracts.AbstractVTN;
import controlador.principal.VtnPrincipalCTR;
import controlador.silabo.frm.FRMSilaboCTR;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Optional;
import javax.swing.JOptionPane;
import modelo.CONS;
import modelo.carrera.CarreraMD;
import modelo.materia.MateriaMD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.silabo.NEWCarreraBD;
import modelo.silabo.NEWMateriaBD;
import modelo.silabo.NEWPeriodoLectivoBD;
import modelo.silabo.NEWSilaboBD;
import modelo.silabo.SilaboMD;
import vista.silabos.VtnConfigSilabo;

/**
 *
 * @author MrRainx
 */
public class VtnConfigSilaboCTR extends AbstractVTN<VtnConfigSilabo, SilaboMD> {

    /*
        BASE DE DATOS
     */
    private final NEWCarreraBD CARRERA_BD = NEWCarreraBD.single();
    private final NEWPeriodoLectivoBD PERIODO_BD = NEWPeriodoLectivoBD.single();
    private final NEWSilaboBD SILABO_CON = NEWSilaboBD.single();

    private final List<CarreraMD> carreras = CARRERA_BD.getByUsername(CONS.USUARIO.getUsername());
    private List<MateriaMD> materias;
    private List<SilaboMD> silabosRef;
    private List<PeriodoLectivoMD> misPeriodos = PERIODO_BD.getMisPeriodosBy(CONS.USUARIO.getPersona().getIdPersona());
    private final String MENSAJE_SIN_SILABO_PENDIENTE = "NO TIENE SILABOS PENDIENTES PARA ESTA CARRERA";
    private VtnSilabosCTR vtnSilabos;

    public VtnConfigSilaboCTR(VtnPrincipalCTR desktop) {
        super(desktop);
        vista = new VtnConfigSilabo();
    }

    public VtnSilabosCTR getVtnSilabos() {
        return vtnSilabos;
    }

    public void setVtnSilabos(VtnSilabosCTR vtnSilabos) {
        this.vtnSilabos = vtnSilabos;
    }

    @Override
    public void Init() {
        super.Init();
        cargarCmbMisPeriodos();
        InitEventos();
    }

    private void InitEventos() {
        vista.getCmbPeriodos().addActionListener(this::cmbAsignatura);
        vista.getCmbAsignatura().addActionListener(this::cmbPeriodoRef);
        vista.getCmbPeriodoRef().addActionListener(this::validarPeriodoRef);
        vista.getBtnSiguiente().addActionListener(this::btnSiguiente);
        vista.getBtnCancelar().addActionListener(e -> vista.dispose());
    }

    /*
        METODOS
     */
    private int getIdPeriodo() {
        return misPeriodos.stream()
                .filter(periodos -> periodos.getNombre().equals(vista.getCmbPeriodos().getSelectedItem().toString()))
                .findFirst()
                .map(c -> c.getID())
                .orElse(0);

    }

    private int getIdMateria() throws NullPointerException {
        return getMateria()
                .map(c -> c.getId())
                .orElse(0);
    }

    private Optional<MateriaMD> getMateria() throws NullPointerException {
        return materias.stream()
                .filter(mat -> mat.getNombre().equals(vista.getCmbAsignatura().getSelectedItem().toString()))
                .findFirst();
    }

    private void cargarCmbMisPeriodos() {
        misPeriodos.stream()
                .map(c -> c.getNombre())
                .forEach(vista.getCmbPeriodos()::addItem);
    }

    private void validarPeriodoRef(ActionEvent e) {
        if (vista.getCmbPeriodoRef().getSelectedIndex() != 0) {
            vista.getSpnUnidades().setEnabled(false);
        } else {
            vista.getSpnUnidades().setEnabled(true);
        }
    }

    private boolean migrarEvaluaciones() {
        String mensaje = "DESEA MIGRAR LAS EVALUCACIONES DEL SILABO ANTERIOR?";
        int resuesta = JOptionPane.showConfirmDialog(vista, mensaje, "IMPORTANTE!!!", 0);
        return resuesta == JOptionPane.YES_OPTION;
    }

    /*
        EVENTOS
     */
    private void cmbAsignatura(ActionEvent e) {
        vista.getCmbAsignatura().removeAllItems();
        materias = NEWMateriaBD
                .single()
                .getMateriasSinSilabo(CONS.USUARIO.getPersona().getIdentificacion(), getIdPeriodo());
        materias.stream()
                .map(c -> c.getNombre())
                .forEach(vista.getCmbAsignatura()::addItem);

        if (materias.size() > 0) {
            vista.getCmbAsignatura().setEnabled(true);
            vista.getBtnSiguiente().setEnabled(true);
            vista.getSpnUnidades().setEnabled(true);
        } else {
            vista.getCmbAsignatura().setEnabled(false);
            vista.getCmbAsignatura().addItem(MENSAJE_SIN_SILABO_PENDIENTE);
            vista.getBtnSiguiente().setEnabled(false);
            vista.getSpnUnidades().setEnabled(false);
            vista.getCmbPeriodoRef().addItem(MENSAJE_SIN_SILABO_PENDIENTE);
            vista.getCmbPeriodoRef().setEnabled(false);
        }

    }

    private void cmbPeriodoRef(ActionEvent e) {
        try {
            vista.getCmbPeriodoRef().removeAllItems();
            silabosRef = NEWSilaboBD
                    .single()
                    .getSilaboRef(getIdPeriodo(), getIdMateria());

            if (silabosRef.size() > 0) {
                vista.getCmbPeriodoRef().setEnabled(true);

                vista.getCmbPeriodoRef().addItem("SI TIENE PERIODOS DE REFERENCIA PARA COPIAR EL SILABO");
                silabosRef.stream()
                        .map(c -> c.getPeriodo().getNombre())
                        .forEach(vista.getCmbPeriodoRef()::addItem);

            } else {
                vista.getCmbPeriodoRef().setEnabled(false);
                vista.getCmbPeriodoRef().addItem("NO TIENE PERIODOS DE REFERENCIA PARA COPIAR");
            }

        } catch (NullPointerException ex) {
            vista.getCmbPeriodoRef().addItem(MENSAJE_SIN_SILABO_PENDIENTE);
            vista.getCmbPeriodoRef().setEnabled(false);
        }

    }

    private void btnSiguiente(ActionEvent e) {
        int indexCmbRef = vista.getCmbPeriodoRef().getSelectedIndex();
        // Cursor de carga 
        desktop.getVtnPrin().setCursor(new Cursor(3));

        if (indexCmbRef > 0) {

            MateriaMD materia = materias.stream()
                    .filter(item -> item.getNombre().equals(vista.getCmbAsignatura().getSelectedItem().toString()))
                    .findFirst()
                    .get();

            PeriodoLectivoMD periodo = silabosRef.stream()
                    .filter(item -> item.getPeriodo().getNombre().equalsIgnoreCase(vista.getCmbPeriodoRef().getSelectedItem().toString()))
                    .map(c -> c.getPeriodo())
                    .findFirst()
                    .get();

            modelo = new SilaboMD();

            modelo = SILABO_CON.getSilaboBy(materia, periodo);

            FRMSilaboCTR ctr = new FRMSilaboCTR(desktop, modelo);

            ctr.referenciado(migrarEvaluaciones());

        } else {

            modelo = new SilaboMD();

            modelo.setPeriodo(PERIODO_BD.getUltimoPeriodoBy(
                    getIdPeriodo()
            ));

            modelo.setMateria(getMateria().get());

            FRMSilaboCTR ctr = new FRMSilaboCTR(desktop, modelo);

            ctr.nuevo(
                    Integer.parseInt(vista.getSpnUnidades().getValue().toString())
            );
        }

        vista.dispose();
        vtnSilabos.getVista().dispose();
    }
}
