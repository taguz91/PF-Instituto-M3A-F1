package controlador.silabo;

import controlador.Libraries.abstracts.AbstractVTN;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.List;
import java.util.function.Consumer;
import modelo.CONS;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.silabo.SilaboBD;
import modelo.silabo.SilaboMD;
import vista.silabos.VtnSilabos;

/**
 *
 * @author MrRainx
 */
public class VtnSilabosCTR extends AbstractVTN<VtnSilabos, SilaboMD> {

    private List<CarreraMD> carreras;

    public VtnSilabosCTR(VtnPrincipalCTR desktop) {
        super(desktop);
        vista = new VtnSilabos();

        modelo = new SilaboMD();
    }

    @Override
    public void Init() {

        InitEventos();
        setTable(vista.getTbl());
        cargarCmbCarreras();
        super.Init();
    }

    private void InitEventos() {
        vista.getBtnNuevo().addActionListener(this::btnNuevo);
        vista.getBtnEditar().addActionListener(this::btnEditar);
        vista.getBtnEliminar().addActionListener(this::btnEliminar);
        vista.getBtnGenerar().addActionListener(this::btnGenerar);
        vista.getBtnImprimir().addActionListener(this::btnImprimir);
        vista.getCmbCarrera().addItemListener(this::cmbCarrera);
    }

    /*
        METODOS
     */
    private void cargarCmbCarreras() {
        carreras = CarreraBD.findBy(
                CONS.USUARIO.getPersona().getIdentificacion()
        );

        carreras.stream()
                .map(c -> c.getNombre())
                .forEach(vista.getCmbCarrera()::addItem);
    }

    private Consumer<SilaboMD> cargador() {
        return obj -> {
            tableM.addRow(new Object[]{
                obj.getID(),
                tableM.getRowCount() + 1,
                obj.getMateria().getNombre(),
                obj.getPeriodo().getNombre(),
                obj.getEstado()
            });
        };
    }

    /*
        EVENTOS
     */
    private void btnImprimir(ActionEvent e) {
        vista.getBgImprimir().setVisible(true);
    }

    private void btnNuevo(ActionEvent e) {
        VtnConfigSilaboCTR vtn = new VtnConfigSilaboCTR(desktop);
        vtn.Init();
    }

    private void btnEditar(ActionEvent e) {

    }

    private void btnEliminar(ActionEvent e) {

    }

    private void btnGenerar(ActionEvent e) {

    }

    private void cmbCarrera(ItemEvent e) {

        CarreraMD carrera = carreras.get(vista.getCmbCarrera().getSelectedIndex());

        lista = SilaboBD.findBy(
                user.getPersona().getIdentificacion(), carrera.getId()
        );

        cargarTabla(cargador());

    }

}
