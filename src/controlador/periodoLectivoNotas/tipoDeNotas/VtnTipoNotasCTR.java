package controlador.periodoLectivoNotas.tipoDeNotas;

import controlador.periodoLectivoNotas.tipoDeNotas.forms.FrmTipoNotaAgregar;
import controlador.Libraries.Effects;
import controlador.periodoLectivoNotas.tipoDeNotas.forms.FrmTipoNotaEditar;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;
import utils.CONS;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.tipoDeNota.TipoDeNotaBD;
import modelo.tipoDeNota.TipoDeNotaMD;
import vista.tipoDeNotas.FrmTipoNota;
import vista.tipoDeNotas.VtnTipoNotas;
import vista.principal.VtnPrincipal;

/**
 *
 * @author MrRainx
 */
public class VtnTipoNotasCTR {

    private final VtnPrincipal desktop;
    private VtnTipoNotas vista;
    private TipoDeNotaBD modelo;

    private List<TipoDeNotaBD> listaTiposNotas;
    private List<PeriodoLectivoMD> listaPeriodos;
    private DefaultTableModel tablaTiposNotas;

    private final PeriodoLectivoBD periodo;

    {
        periodo = new PeriodoLectivoBD();
    }

    public VtnTipoNotasCTR(VtnPrincipal desktop) {
        this.desktop = desktop;
        this.vista = new VtnTipoNotas();
        this.modelo = new TipoDeNotaBD();
    }

    public VtnTipoNotas getVista() {
        return vista;
    }

    //Inits
    public void Init() {
        Effects.addInDesktopPane(vista, desktop.getDpnlPrincipal());

        Effects.centerFrame(vista, desktop.getDpnlPrincipal());
        tablaTiposNotas = (DefaultTableModel) vista.getTblTipoNotas().getModel();

        InitEventos();
        listaTiposNotas = modelo.selectAllWhereEstadoIs(true);
        cargarTabla(listaTiposNotas);
        cargarCmbPeriodos();
        InitPermisos();
    }

    private void InitEventos() {

        vista.getBtnEditar().addActionListener(e -> btnEditar(e));

        vista.getBtnEliminar().addActionListener(e -> btnEliminar(e));

        vista.getBtnIngresar().addActionListener(e -> new FrmTipoNotaAgregar(desktop, new FrmTipoNota(), new TipoDeNotaBD(), this).Init());

        vista.getBtnActualizar().addActionListener(e -> {
            listaTiposNotas = modelo.selectAllWhereEstadoIs(true);
            cargarTabla(listaTiposNotas);
        });

        vista.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                cargarTablaFilter(vista.getTxtBuscar().getText().toUpperCase());
            }
        });

        vista.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                vista = null;
                listaTiposNotas = null;
                modelo = null;
                tablaTiposNotas = null;
                System.gc();
                System.out.println(".VTN Tipos de notas closed!");
            }
        });

        vista.getCmbPeriodos().addActionListener(e -> {
            if (vista.getCmbPeriodos().getSelectedIndex() != 0) {
                cargarTabla(listaTiposNotas
                        .stream()
                        .filter(item -> item.getPeriodoLectivo().getNombre().equalsIgnoreCase(vista.getCmbPeriodos().getSelectedItem().toString()))
                        .collect(Collectors.toList())
                );
            } else {
                cargarTabla(listaTiposNotas);
            }
        });
    }

    //METODOS DE APOYO
    public void cargarTabla(List<TipoDeNotaBD> lista) {
        tablaTiposNotas.setRowCount(0);

        lista.forEach(agregarFilas());

    }

    private void cargarCmbPeriodos() {

        listaPeriodos = periodo.selectIdNombreAll();

        vista.getCmbPeriodos().addItem("---------------------------------------------------");
        listaPeriodos
                .stream()
                .map(c -> c.getNombre())
                .forEachOrdered(vista.getCmbPeriodos()::addItem);

    }

    private void cargarTablaFilter(String Aguja) {
        tablaTiposNotas.setRowCount(0);
        listaTiposNotas.stream()
                .filter(
                        item -> item.getNombre().toUpperCase().contains(Aguja)
                        || item.getFechaCreacion().toString().toUpperCase().contains(Aguja)
                        || String.valueOf(item.getValorMaximo()).toUpperCase().contains(Aguja)
                        || String.valueOf(item.getValorMinimo()).toUpperCase().contains(Aguja)
                        || item.getPeriodoLectivo().getNombre().toUpperCase().contains(Aguja)
                )
                .collect(Collectors.toList())
                .forEach(agregarFilas());
    }

    private Consumer<TipoDeNotaMD> agregarFilas() {
        return obj -> {
            tablaTiposNotas.addRow(new Object[]{
                tablaTiposNotas.getDataVector().size() + 1,
                obj.getId(),
                obj.getNombre(),
                obj.getPeriodoLectivo().getNombre(),
                obj.getPeriodoLectivo().getCarrera().getNombre(),
                obj.getPeriodoLectivo().getCarrera().getModalidad(),
                obj.getValorMinimo(),
                obj.getValorMaximo(),
                obj.getFechaCreacion()
            });
            vista.getLblResultados().setText(tablaTiposNotas.getDataVector().size() + " Resultados Obtenidos");
        };
    }

    //PROCESADORES DE EVENTOS
    private void btnEditar(ActionEvent e) {

        int fila = vista.getTblTipoNotas().getSelectedRow();

        if (fila != -1) {

            int ID = Integer.valueOf(vista.getTblTipoNotas().getValueAt(fila, 1).toString());

            modelo = listaTiposNotas.stream()
                    .filter(item -> item.getId() == ID)
                    .findFirst().get();

            FrmTipoNotaEditar form = new FrmTipoNotaEditar(desktop, new FrmTipoNota(), modelo, this);
            form.Init();

        } else {
            JOptionPane.showMessageDialog(vista, "SELECCIONE UNA FILA");
        }

    }

    private void btnEliminar(ActionEvent e) {

    }

    private void InitPermisos() {
        vista.getBtnIngresar().getAccessibleContext().setAccessibleName("Notas-Tipos-de-Notas-Ingresar");
        vista.getBtnActualizar().getAccessibleContext().setAccessibleName("Notas-Tipos-de-Notas-Actualizar");
        vista.getBtnEditar().getAccessibleContext().setAccessibleName("Notas-Tipos-de-Notas-Editar");
        vista.getBtnEliminar().getAccessibleContext().setAccessibleName("Notas-Tipos-de-Notas-Eliminar");
        CONS.activarBtns(vista.getBtnIngresar(), vista.getBtnActualizar(),
                vista.getBtnEditar(), vista.getBtnEliminar());
    }
}
