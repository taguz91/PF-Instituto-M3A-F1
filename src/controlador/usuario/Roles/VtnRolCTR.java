package controlador.usuario.Roles;

import controlador.Libraries.Effects;
import controlador.usuario.Roles.forms.FrmRolAdd;
import controlador.accesos.FrmAccesosDeRolCTR;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.CONS;
import modelo.accesosDelRol.AccesosDelRolBD;
import modelo.usuario.RolBD;
import modelo.usuario.RolMD;
import vista.accesos.FrmAccesosDeRol;
import vista.principal.VtnPrincipal;
import vista.usuario.VtnRol;

/**
 *
 * @author MrRainx
 */
public class VtnRolCTR {

    private final VtnPrincipal desktop;

    private final VtnRol vista;

    private final RolBD modelo;

    private final RolBD permisos;

    private List<RolBD> listaRoles;

    private static DefaultTableModel tabla;

    private boolean cargarTabla = true;

    public VtnRolCTR(VtnPrincipal desktop) {
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

        Effects.centerFrame(vista, desktop.getDpnlPrincipal());

        vista.setTitle("Lista de Roles");

        tabla = (DefaultTableModel) vista.getTabRoles().getModel();

        InitPermisos();
        cargarTabla();

        try {
            vista.setSelected(true);
            vista.show();
            desktop.getDpnlPrincipal().add(vista);
        } catch (PropertyVetoException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private void InitPermisos() {

        vista.getBtnActualizar().addActionListener(e -> cargarTabla());
        vista.getBtnCancelar().addActionListener(e -> btnCancelarActionPerformance(e));

        vista.getBtnVerPermisos().addActionListener(e -> btnVerPermisosActionPerformance(e));
        vista.getBtnEditarPermisos().addActionListener(e -> btnEditarPermisosActionPerformance(e));

        vista.getBtnIngresar().addActionListener(e -> new FrmRolAdd(desktop, this).Init());
        vista.getBtnEditar().addActionListener(e -> btnEditarActionPerformance(e));
        vista.getBtnEliminar().addActionListener(e -> btnEliminarActionPerformance(e));

//        for (AccesosMD obj : AccesosBD.SelectWhereACCESOROLidRol(permisos.getId())) {
//
//            if (obj.getNombre().equals("ROLES-Agregar")) {
//                vista.getBtnIngresar().setEnabled(true);
//            }
//            if (obj.getNombre().equals("ROLES-Editar")) {
//                vista.getBtnEditar().setEnabled(true);
//            }
//            if (obj.getNombre().equals("ROLES-Eliminar")) {
//                vista.getBtnEliminar().setEnabled(true);
//            }
//            if (obj.getNombre().equals("ROLES-Ver-Permisos")) {
//                vista.getBtnVerPermisos().setEnabled(true);
//            }
//            if (obj.getNombre().equals("ROLES-Editar-Permisos")) {
//                vista.getBtnEditarPermisos().setEnabled(true);
//            }
//        }
    }

    //Metodos de Apoyo
    private void cargarTabla() {
        if (cargarTabla) {
            new Thread(() -> {

                tabla.setRowCount(0);

                vista.getTxtBuscar().setEnabled(false);

                Effects.setLoadCursor(vista);

                cargarTabla = false;

                listaRoles = modelo.selectAll();

                listaRoles.stream().forEach(VtnRolCTR::agregarFila);

                vista.getLblResultados().setText(listaRoles.size() + " Resultados Obtenidos");

                cargarTabla = true;

                Effects.setDefaultCursor(vista);

                vista.getTxtBuscar().setEnabled(true);

            }).start();

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

    private void getObjFromRow(int fila) {

        modelo.setId((Integer) vista.getTabRoles().getValueAt(fila, 1));
        modelo.setNombre((String) vista.getTabRoles().getValueAt(fila, 2));

    }

    private void btnEditarActionPerformance(ActionEvent e) {

        int fila = vista.getTabRoles().getSelectedRow();

        if (fila != -1) {

            getObjFromRow(fila);

            if (modelo.getNombre().equals("ROOT")) {

                JOptionPane.showMessageDialog(vista, "NO SE PUEDE EDITAR EL ROL: " + " 'ROOT'");

            } else {

//                FrmRolAdd editar = new FrmRolAdd(desktop, new FrmRol(), modelo, "Editar");
//                editar.Init();
            }

        } else {
            JOptionPane.showMessageDialog(desktop, "SELECCIONE UNA FILA DE LA TABLA!!");
        }

    }

    private void btnEliminarActionPerformance(ActionEvent e) {

        int fila = vista.getTabRoles().getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(desktop, "SELECCIONE UNA FILA!!");
        } else {
            getObjFromRow(fila);
            int Pk = modelo.getId();

            ImageIcon icon = new ImageIcon();

            int opcion = JOptionPane.showConfirmDialog(null,
                    modelo.getNombre(),
                    "ESTA SEGURO DE ELIMINAR ESTE ROL?",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    icon
            );

            if (opcion == 0) {

                modelo.eliminar(Pk);

                JOptionPane.showMessageDialog(null, "ROL ELIMINADO");

                cargarTabla();

            } else {
                JOptionPane.showMessageDialog(null, "ROL NO ELIMINADO");
            }

        }

    }

    private void btnCancelarActionPerformance(ActionEvent e) {
        vista.dispose();
    }

    private void btnVerPermisosActionPerformance(ActionEvent e) {

        int fila = vista.getTabRoles().getSelectedRow();

        if (fila != -1) {
            getObjFromRow(fila);
            FrmAccesosDeRolCTR permisosVtn = new FrmAccesosDeRolCTR(desktop, new FrmAccesosDeRol(), new AccesosDelRolBD(), modelo, "Consultar");
            permisosVtn.Init();

        } else {
            JOptionPane.showMessageDialog(desktop, "SELECCIONE UNA FILA!!");
        }

    }

    private void btnEditarPermisosActionPerformance(ActionEvent e) {

        int fila = vista.getTabRoles().getSelectedRow();

        if (fila != -1) {
            getObjFromRow(fila);

            if (modelo.getNombre().equals("ROOT")) {
                JOptionPane.showMessageDialog(vista, "NO SE PUEDEN EDITAR LOS PERMISOS DEL ROL: " + " 'ROOT'");
            } else {

                FrmAccesosDeRolCTR permisosVtn = new FrmAccesosDeRolCTR(desktop, new FrmAccesosDeRol(), new AccesosDelRolBD(), modelo, "Editar");
                permisosVtn.Init();

            }

        } else {
            JOptionPane.showMessageDialog(desktop, "SELECCIONE UNA FILA!!");
        }
    }

}
