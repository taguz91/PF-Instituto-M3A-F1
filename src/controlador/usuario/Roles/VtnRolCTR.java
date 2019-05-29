package controlador.usuario.Roles;

import controlador.Libraries.Effects;
import controlador.usuario.Roles.forms.FrmRolAdd;
import controlador.accesos.FrmAccesosAddCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.CONS;
import modelo.usuario.RolBD;
import modelo.usuario.RolMD;
import vista.usuario.VtnRol;

/**
 *
 * @author MrRainx
 */
public class VtnRolCTR {

    private final VtnPrincipalCTR desktop;

    private final VtnRol vista;

    private final RolBD modelo;

    private final RolBD permisos;

    private List<RolBD> listaRoles;

    private static DefaultTableModel tabla;

    private boolean cargarTabla = true;

    public VtnRolCTR(VtnPrincipalCTR desktop) {
        this.desktop = desktop;
        this.vista = new VtnRol();
        this.modelo = new RolBD();
        this.permisos = CONS.ROL;
    }

    public VtnRol getVista() {
        return vista;
    }

    //Inits
    public void Init() {

        Effects.addInDesktopPane(vista, desktop.getVtnPrin().getDpnlPrincipal());

        vista.setTitle("Lista de Roles");

        tabla = (DefaultTableModel) vista.getTabRoles().getModel();

        InitPermisos();
        cargarTabla();
        InitPermisos();

    }

    private void InitPermisos() {
           vista.getBtnIngresar().getAccessibleContext().setAccessibleName("Roles-Usuarios-Ingresar");
       vista.getBtnActualizar().getAccessibleContext().setAccessibleName("Roles-Usuarios-Actualizar");
       vista.getBtnEditar().getAccessibleContext().setAccessibleName("Roles-Usuarios-Editar");
       vista.getBtnVerPermisos().getAccessibleContext().setAccessibleName("Roles-Usuarios-Ver Permisos");
       vista.getBtnEliminar().getAccessibleContext().setAccessibleName("Roles-Usuarios-Eliminar");
       vista.getBtnEditarPermisos().getAccessibleContext().setAccessibleName("Roles-Usuarios-Editar Permisos");
       
        CONS.activarBtns(vista.getBtnIngresar(), vista.getBtnActualizar(),
                vista.getBtnEditar(), vista.getBtnVerPermisos(),
                vista.getBtnEliminar(), vista.getBtnEditarPermisos());
        
    }

    //Metodos de Apoyo
    private void cargarTabla() {
        if (cargarTabla) {

            //tabla.setRowCount(0);
            vista.getTxtBuscar().setEnabled(false);

            Effects.setLoadCursor(vista);

            cargarTabla = false;

            listaRoles = modelo.selectAll();

            listaRoles.stream().forEach(VtnRolCTR::agregarFila);

            vista.getLblResultados().setText(listaRoles.size() + " Resultados Obtenidos");

            cargarTabla = true;

            Effects.setDefaultCursor(vista);

            vista.getTxtBuscar().setEnabled(true);

        } else {
            JOptionPane.showMessageDialog(vista, "YA HAY UNA CARGA PENDIENTE!!");
        }

    }

    private static void agregarFila(RolMD obj) {

        tabla.addRow(new Object[]{
            tabla.getDataVector().size() + 1,
            obj.getId(),
            obj.getNombre()
        });

    }

    private void cargarTablaFilter(String Aguja) {
        if (cargarTabla) {
            JOptionPane.showMessageDialog(vista, "ESPERE QUE TERMINE LA CARGA PENDIENTE!!");
        } else {
            List<RolMD> lista = listaRoles
                    .stream()
                    .filter(item -> item.getNombre().toLowerCase().contains(Aguja.toLowerCase()))
                    .collect(Collectors.toList());

//            lista.forEach(VtnRolCTR::agregarFila);
            vista.getLblResultados().setText(lista.size() + " Resultados Obtenidos");
        }

    }

    private List<RolBD> getSeleccionados() {
        List<RolBD> lista = new ArrayList<>();
        int[] rows = vista.getTabRoles().getSelectedRows();
        IntStream.of(rows).forEach(i -> {
            int id = (Integer) vista.getTabRoles().getValueAt(i, 1);
            lista.add(
                    listaRoles.stream().filter(item -> item.getId() == id).findFirst().get()
            );
        });
        return lista;
    }

    //EVENTOS
    private void btnEditarPermisos(ActionEvent e) {
        List<RolBD> listaSeleccionados = getSeleccionados();
        if (!listaSeleccionados.isEmpty()) {
            listaSeleccionados.forEach(obj -> {
                FrmAccesosAddCTR form = new FrmAccesosAddCTR(desktop);
                form.setRol(obj);
                form.Init();
            });
        }
    }

}
