package controlador.periodoLectivoNotas.tipoDeNotas;

import controlador.periodoLectivoNotas.tipoDeNotas.forms.FrmTipoNotaAgregar;
import controlador.Libraries.Effects;
import controlador.periodoLectivoNotas.tipoDeNotas.forms.FrmTipoNotaEditar;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;
import modelo.tipoDeNota.TipoDeNotaBD;
import modelo.tipoDeNota.TipoDeNotaMD;
import modelo.usuario.RolBD;
import vista.periodoLectivoNotas.FrmTipoNota;
import vista.periodoLectivoNotas.VtnTipoNotas;
import vista.principal.VtnPrincipal;

/**
 *
 * @author MrRainx
 */
public class VtnTipoNotasCTR {

    private final VtnPrincipal desktop;
    private VtnTipoNotas vista;
    private TipoDeNotaBD modelo;
    private final RolBD permisos;

    private List<TipoDeNotaMD> listaTiposNotas;

    private DefaultTableModel tablaTiposNotas;

    public VtnTipoNotasCTR(VtnPrincipal desktop, VtnTipoNotas vista, TipoDeNotaBD modelo, RolBD permisos) {
        this.desktop = desktop;
        this.vista = vista;
        this.modelo = modelo;
        this.permisos = permisos;
    }

    public VtnTipoNotas getVista() {
        return vista;
    }

    //Inits
    public void Init() {
        vista.setVisible(true);

        Effects.centerFrame(vista, desktop.getDpnlPrincipal());
        tablaTiposNotas = (DefaultTableModel) vista.getTblTipoNotas().getModel();

        InitEventos();
        cargarTabla();
        desktop.getDpnlPrincipal().add(vista);
        try {
            vista.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(VtnTipoNotasCTR.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void InitEventos() {

        vista.getBtnEditar().addActionListener(e -> btnEditar(e));
        vista.getBtnEliminar().addActionListener(e -> btnEliminar(e));
        vista.getBtnIngresar().addActionListener(e -> btnIngresar(e));
        vista.getBtnActualizar().addActionListener(e -> btnActualizarActionPerformance(e));

        vista.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                txtBuscarOnKeyReleased(e);
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
                System.out.println(".internalFrameClosed()");
            }
        });

//        vista.getTblTipoNotas().getTableHeader().addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                oderBy(e);
//            }
//        });
    }

    //METODOS DE APOYO
    public void cargarTabla() {
        tablaTiposNotas.setRowCount(0);
        listaTiposNotas = TipoDeNotaBD.selectAllWhereEstadoIs(true);

        listaTiposNotas.forEach(agregarFilas);

    }

    private void cargarTablaFilter(String Aguja) {
        tablaTiposNotas.setRowCount(0);
        listaTiposNotas.stream()
                .filter(item -> item.getNombre().toUpperCase().contains(Aguja)
                || item.getFechaCreacion().toString().toUpperCase().contains(Aguja)
                || String.valueOf(item.getValorMaximo()).toUpperCase().contains(Aguja)
                || String.valueOf(item.getValorMinimo()).toUpperCase().contains(Aguja)
                || item.getPeriodoLectivo().getNombre_PerLectivo().toUpperCase().contains(Aguja)
                )
                .collect(Collectors.toList()).forEach(agregarFilas);
    }

    private final Consumer<TipoDeNotaMD> agregarFilas = obj -> {
        tablaTiposNotas.addRow(new Object[]{
            tablaTiposNotas.getDataVector().size() + 1,
            obj.getIdTipoNota(),
            obj.getNombre(),
            obj.getPeriodoLectivo().getNombre_PerLectivo(),
            obj.getPeriodoLectivo().getCarrera().getNombre(),
            obj.getPeriodoLectivo().getCarrera().getModalidad(),
            obj.getValorMinimo(),
            obj.getValorMaximo(),
            obj.getFechaCreacion()
        });
        vista.getLblResultados().setText(tablaTiposNotas.getDataVector().size() + " Resultados Obtenidos");
    };

    private void setModel(int fila) {
        int idTipoNota = (Integer) vista.getTblTipoNotas().getValueAt(fila, 1);
        modelo = new TipoDeNotaBD(listaTiposNotas
                .stream()
                .filter(item -> item.getIdTipoNota() == idTipoNota)
                .findFirst()
                .get()
        );
    }

    //PROCESADORES DE EVENTOS
    private void btnEditar(ActionEvent e) {

        int fila = vista.getTblTipoNotas().getSelectedRow();

        if (fila != -1) {

            setModel(fila);
            FrmTipoNotaEditar form = new FrmTipoNotaEditar(desktop, new FrmTipoNota(), modelo, this);
            form.InitEditar();

        } else {
            JOptionPane.showMessageDialog(vista, "SELECCIONE UNA FILA");
        }

    }

    private void btnEliminar(ActionEvent e) {

        int fila = vista.getTblTipoNotas().getSelectedRow();
        if (fila != -1) {

            setModel(fila);

            int opcion = JOptionPane.showConfirmDialog(vista, "ESTA SEGURO DE ELIMINAR LA NOTA: " + modelo.getNombre());

            if (opcion == 0) {

                if (modelo.eliminar(modelo.getIdTipoNota())) {

                    JOptionPane.showMessageDialog(vista, "SE HA ELIMINADO LA NOTA: " + modelo.getNombre());

                    desktop.getLblEstado().setText("SE HA ELIMINADO LA NOTA: " + modelo.getNombre());

                    cargarTabla();

                } else {

                    JOptionPane.showMessageDialog(vista, "HA DECIDIDO NO ELIMINAR NADA");

                }

            }

        } else {
            JOptionPane.showMessageDialog(vista, "SELECCIONE UNA FILA!!!");

        }

    }

    private void btnIngresar(ActionEvent e) {

        FrmTipoNotaAgregar form = new FrmTipoNotaAgregar(desktop, new FrmTipoNota(), new TipoDeNotaBD(), this);
        form.InitAgregar();

    }

    private void txtBuscarOnKeyReleased(KeyEvent e) {
        cargarTablaFilter(vista.getTxtBuscar().getText().toUpperCase());
    }

    private void btnActualizarActionPerformance(ActionEvent e) {
        cargarTabla();
    }

//    private void oderBy(MouseEvent e) {
//
//        tablaTiposNotas.setRowCount(0);
//
//        listaTiposNotas
//                .stream()
//                .sorted((item, item2) -> item.getPeriodoLectivo().getCarrera().getNombre().compareToIgnoreCase(item2.getPeriodoLectivo().getCarrera().getNombre()))
//                .forEach(VtnTipoNotasCTR::agregarFila);
//    }
}
