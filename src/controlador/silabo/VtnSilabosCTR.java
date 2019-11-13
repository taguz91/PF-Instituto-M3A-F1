package controlador.silabo;

import controlador.Libraries.abstracts.AbstractVTN;
import controlador.Libraries.cellEditor.ComboBoxCellEditor;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.JOptionPane;
import javax.swing.event.CaretEvent;
import modelo.CONS;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.silabo.NEWSilaboBD;
import modelo.silabo.SilaboBD;
import modelo.silabo.SilaboMD;
import modelo.validaciones.Validar;
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
        vista.getCmbCarrera().addItemListener(this::cmbCarrera);
        vista.getBtnImprimir().addActionListener(this::btnImprimir);
        vista.getTxtBuscar().addCaretListener(this::txtBuscar);

        List<String> estados = new ArrayList<>();

        estados.add("APROBADO");
        estados.add("PENDIENTE");
        estados.add("REVISAR");

        vista.getTbl()
                .getColumnModel()
                .getColumn(5)
                .setCellEditor(new ComboBoxCellEditor(true, estados));
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
                obj.getEstado(),
                ""
            });
        };
    }

    private SilaboMD getSilaboSeleccionadoTbl() {
        int row = getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(vista, "DEBE SELECCIONAR UN SILABO PRIMERO!!", "Aviso", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        int id = Integer.valueOf(table.getValueAt(row, 0).toString());
        return lista.stream()
                .filter(item -> item.getID() == id)
                .findFirst()
                .get();
    }

    /*
        EVENTOS
     */
    private void btnImprimir(ActionEvent e) {

        int row = vista.getTbl().getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(vista, "DEBE SELECCIONAR UN SILABO PRIMERO!!", "Aviso", JOptionPane.ERROR_MESSAGE);
        } else {

            Object[] opciones = new Object[]{
                "Silabo Dual",
                "Silabo Tradicional",
                "Silabo Dual /semanas"
            };

            int opcion = JOptionPane.showOptionDialog(
                    vista,
                    "GENERAR SILABO",
                    "GENERACION DE SILABO",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    opciones,
                    "Cancelar"
            );
            SilaboMD silabo = getSilaboSeleccionadoTbl();
            switch (opcion) {

                case 0:
                    NEWSilaboBD.single().imprimirProgramaAnalitico(silabo);
                    break;

                case 1:
                    NEWSilaboBD.single().imprimirSilabo(silabo);

                    break;

                case 2:
                    String semanas = JOptionPane.showInputDialog("Escriba el numero de semanas");
                    if (semanas != null) {

                        if (Validar.esNumeros(semanas)) {
                            int numSemanas = Integer.parseInt(semanas);
                            if (numSemanas >= 6) {

                                NEWSilaboBD.single().imprimirProgramaAnaliticoConSemanas(silabo, numSemanas);

                            } else {
                                JOptionPane.showMessageDialog(null, "Debe indicar mas de seis semanas de clases por periodo ");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Solo puede ingresar numeros.");
                        }
                        break;

                    }
            }
        }
    }

    private void btnNuevo(ActionEvent e) {
        VtnConfigSilaboCTR vtn = new VtnConfigSilaboCTR(desktop);
        vtn.Init();
        vista.dispose();
    }

    private void btnEditar(ActionEvent e) {

    }

    private void btnEliminar(ActionEvent e) {

    }

    private void cmbCarrera(ItemEvent e) {

        CarreraMD carrera = carreras.get(vista.getCmbCarrera().getSelectedIndex());

        lista = SilaboBD.findBy(
                user.getPersona().getIdentificacion(), carrera.getId()
        );
        cargarTabla(cargador());
    }

    private void txtBuscar(CaretEvent e) {
        tableM.setRowCount(0);
        String buscar = vista.getTxtBuscar().getText().toLowerCase();
        lista.stream()
                .filter(item -> item.getPeriodo().getNombre().toLowerCase().contains(buscar)
                || item.getMateria().getNombre().toLowerCase().contains(buscar)
                ).forEach(cargador());
    }

}
