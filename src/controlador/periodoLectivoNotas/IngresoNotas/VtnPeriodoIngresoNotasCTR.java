package controlador.periodoLectivoNotas.IngresoNotas;

import controlador.periodoLectivoNotas.IngresoNotas.forms.FrmIngresoNotasAgregar;
import controlador.Libraries.Middlewares;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.accesos.AccesosBD;
import modelo.accesos.AccesosMD;
import modelo.periodoIngresoNotas.PeriodoIngresoNotasBD;
import modelo.periodoIngresoNotas.PeriodoIngresoNotasMD;
import modelo.usuario.RolBD;
import vista.periodoLectivoNotas.FrmIngresoNotas;
import vista.periodoLectivoNotas.VtnPeriodoIngresoNotas;
import vista.principal.VtnPrincipal;

/**
 *
 * @author MrRainx
 */
public class VtnPeriodoIngresoNotasCTR {

    private VtnPrincipal desktop;
    private VtnPeriodoIngresoNotas vista;
    private PeriodoIngresoNotasBD modelo;
    private RolBD permisos;

    //Tabla
    private static DefaultTableModel tablaPeriodoNotas;

    //listas
    private List<PeriodoIngresoNotasMD> listaPeriodoNotas;

    //Thread
    private Thread thread = null;

    private boolean cargarTabla = true;

    public VtnPeriodoIngresoNotasCTR(VtnPrincipal desktop, VtnPeriodoIngresoNotas vista, PeriodoIngresoNotasBD modelo, RolBD permisos) {
        this.desktop = desktop;
        this.vista = vista;
        this.modelo = modelo;
        this.permisos = permisos;
    }

    //Inits
    public void Init() {

        tablaPeriodoNotas = (DefaultTableModel) vista.getTblPeriodoIngresoNotas().getModel();

        listaPeriodoNotas = PeriodoIngresoNotasBD.selectAll();
        cargarTabla(listaPeriodoNotas);

        InitEventos();
        try {

            desktop.getDpnlPrincipal().add(vista);
            vista.show();
            vista.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(VtnPeriodoIngresoNotasCTR.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void InitEventos() {

        vista.getBtnEditar().addActionListener(e -> btnEditarActionPerformance(e));
        vista.getBtnEliminar().addActionListener(e -> btnEliminarActionPerformance(e));
        vista.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                txtBuscarOnKeyReleased(e);
            }

        });
        vista.getBtnIngresar().addActionListener(e -> btnIngresar(e));
        vista.getBtnActualizar().addActionListener(e -> btnActualizarActionPerformance(e));
    }

    private void InitPermisos() {
        for (AccesosMD obj : AccesosBD.SelectWhereACCESOROLidRol(permisos.getId())) {

            //HACER LOS IFS DE LOS PERMISOS
        }
    }

    //Metodos de Apoyo
    public void cargarTabla(List<PeriodoIngresoNotasMD> lista) {
        if (cargarTabla) {
            thread = new Thread() {
                @Override
                public void run() {
                    Middlewares.setLoadCursor(vista);

                    cargarTabla = false;

                    tablaPeriodoNotas.setRowCount(0);
                    lista
                            .stream()
                            .forEach(VtnPeriodoIngresoNotasCTR::agregarFila);
                    Middlewares.setDefaultCursor(vista);

                    cargarTabla = true;

                    vista.getLblResultados().setText(lista.size() + " Resultados");

                }
            };
            thread.start();

        } else {
            JOptionPane.showMessageDialog(vista, "YA HAY UNA CARGA PENDIENTE!!");
        }

    }

    private static void agregarFila(PeriodoIngresoNotasMD obj) {
        tablaPeriodoNotas.addRow(new Object[]{
            obj.getIdPeriodoIngreso(),
            obj.getFechaInicio(),
            obj.getFechaCierre(),
            obj.getPeriodoLectivo().getNombre_PerLectivo(),
            obj.getTipoNota().getNombre()
        });
    }

    private void setObjFromTabla(int fila) {
        int idPeriodoIngreso = (Integer) vista.getTblPeriodoIngresoNotas().getValueAt(fila, 0);

        listaPeriodoNotas
                .stream()
                .filter(item -> item.getIdPeriodoIngreso() == idPeriodoIngreso)
                .collect(Collectors.toList())
                .forEach(obj -> {
                    modelo = new PeriodoIngresoNotasBD(obj);
                });

    }

    private void cargarTablaFilter(String Aguja) {
        tablaPeriodoNotas.setRowCount(0);
        List<PeriodoIngresoNotasMD> listaTemporal = listaPeriodoNotas.stream()
                .filter(item -> item.getFechaCierre().toString().toUpperCase().contains(Aguja.toUpperCase())
                || item.getFechaInicio().toString().toUpperCase().contains(Aguja.toUpperCase())
                || item.getPeriodoLectivo().getNombre_PerLectivo().toUpperCase().contains(Aguja.toUpperCase())
                || item.getTipoNota().getNombre().toUpperCase().contains(Aguja.toUpperCase())
                ).collect(Collectors.toList());

        listaTemporal.forEach(VtnPeriodoIngresoNotasCTR::agregarFila);

        vista.getLblResultados().setText(listaTemporal.size() + " Resultado");

    }

    //Procesadores de eventos
    private void btnEditarActionPerformance(ActionEvent e) {
        int fila = vista.getTblPeriodoIngresoNotas().getSelectedRow();

        if (fila != -1) {

            setObjFromTabla(fila);
//            FrmIngresoNotasAgregar form = new FrmIngresoNotasAgregar(desktop, new FrmIngresoNotas(), modelo, this, "Editar");
//            form.Init();
        } else {
            JOptionPane.showMessageDialog(vista, "SELECCIONE UNA FILA!!");
        }
    }

    private void btnEliminarActionPerformance(ActionEvent e) {
        int fila = vista.getTblPeriodoIngresoNotas().getSelectedRow();

        if (fila != -1) {
            int PK = (Integer) vista.getTblPeriodoIngresoNotas().getValueAt(fila, 0);
            int opcion = JOptionPane.showConfirmDialog(vista, "ESTA SEGURO DE BORRAR EL ELEMENTO SELECCIONADO");

            if (opcion == 0) {
                modelo.eliminar(PK);
                cargarTabla(PeriodoIngresoNotasBD.selectAll());
            }
        } else {
            JOptionPane.showMessageDialog(vista, "SELECCIONE UNA FILA!!");
        }
    }

    private void btnIngresar(ActionEvent e) {

        FrmIngresoNotasAgregar form = new FrmIngresoNotasAgregar(desktop, new FrmIngresoNotas(), modelo, this);
        form.InitAgregar();
    }

    private void btnActualizarActionPerformance(ActionEvent e) {
        cargarTabla(PeriodoIngresoNotasBD.selectAll());
    }

    private void txtBuscarOnKeyReleased(KeyEvent e) {
        cargarTablaFilter(vista.getTxtBuscar().getText());
    }
}
