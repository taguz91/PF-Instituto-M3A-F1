package controlador.accesos;

import controlador.Libraries.Effects;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import modelo.CONS;
import modelo.accesosDelRol.AccesosDelRolBD;
import modelo.usuario.RolBD;
import vista.accesos.FrmAccesosDeRol;

/**
 *
 * @author MrRainx
 */
public class FrmAccesosAddCTR {

    private final VtnPrincipalCTR desktop;
    private final FrmAccesosDeRol vista;
    private final AccesosDelRolBD modelo;
    private RolBD rol;

    private List<AccesosDelRolBD> listaPermisos;
    private DefaultTableModel tblPermisos;

    public FrmAccesosAddCTR(VtnPrincipalCTR destop) {
        this.desktop = destop;
        this.vista = new FrmAccesosDeRol();
        this.modelo = new AccesosDelRolBD();
    }

    public void setRol(RolBD rol) {
        this.rol = rol;
    }

    //Inits
    public void Init() {

        Effects.addInDesktopPane(vista, desktop.getVtnPrin().getDpnlPrincipal());
        tblPermisos = (DefaultTableModel) vista.getTabPermisos().getModel();
        listaPermisos = modelo.selectWhere(rol.getId());
        cargarTabla();
        vista.getLblRolSeleccionado().setText(rol.getNombre());
        InitEventos();
    }

    private void InitEventos() {

        vista.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                cargarTablaFilter(vista.getTxtBuscar().getText().toLowerCase());
            }
        });

        tblPermisos.addTableModelListener(new TableModelListener() {
            boolean active = false;

            @Override
            public void tableChanged(TableModelEvent e) {
                if (!active && e.getType() == TableModelEvent.UPDATE) {
                    active = true;
                    activarPermisos();
                    active = false;
                }

            }
        });

        vista.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                cargarTablaFilter(vista.getTxtBuscar().getText());
            }
        });

    }

    //Metodos de apoyo
    private int getRow() {
        return vista.getTabPermisos().getSelectedRow();
    }

    private void cargarTabla() {
        tblPermisos.setRowCount(0);
        listaPermisos.stream().sorted(sorter()).forEach(agregarFilas());
    }

    private Consumer<AccesosDelRolBD> agregarFilas() {
        return obj -> {
            tblPermisos.addRow(new Object[]{
                tblPermisos.getDataVector().size() + 1,
                obj.getAcceso().getNombre(),
                obj.isActivo(),
                obj.getId()
            });
            vista.getLblResultados().setText(tblPermisos.getDataVector().size() + " Resultados");
        };
    }

    private Comparator<AccesosDelRolBD> sorter() {
        return (item1, item2) -> item1.getAcceso().getNombre().compareTo(item2.getAcceso().getNombre());
    }

    private void cargarTablaFilter(String aguja) {
        tblPermisos.setRowCount(0);
        listaPermisos
                .stream()
                .filter(item -> item.getAcceso().getNombre().toLowerCase().contains(aguja.toLowerCase()))
                .sorted(sorter())
                .forEach(agregarFilas());
    }

    //Eventos
    private void activarPermisos() {
        new Thread(() -> {
            int id = (Integer) tblPermisos.getValueAt(getRow(), 3);
            boolean activo = (Boolean) tblPermisos.getValueAt(getRow(), 2);
            AccesosDelRolBD acceso = listaPermisos.stream()
                    .filter(item -> item.getId() == id).findFirst().get();

            acceso.setActivo(activo);
            if (acceso.editar()) {
                if (activo) {
                    vista.getLblEstado().setText("SE HA DADO EL PERMISO: " + acceso.getAcceso().getNombre());
                    vista.getLblEstado().setForeground(CONS.SUCCESS_COLOR);
                } else {

                    vista.getLblEstado().setText("SE HA QUITADO EL PERMISO: " + acceso.getAcceso().getNombre());
                    vista.getLblEstado().setForeground(CONS.ERROR_COLOR);
                }
            }
        }).start();

    }

}
