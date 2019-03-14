package controlador.usuario;

import controlador.Libraries.Effects;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.accesos.AccesosBD;
import modelo.accesos.AccesosMD;
import modelo.usuario.RolMD;
import modelo.usuario.UsuarioBD;
import modelo.usuario.UsuarioMD;
import vista.principal.VtnPrincipal;
import vista.usuario.FrmUsuario;
import vista.usuario.VtnUsuario;

/**
 *
 * @author USUARIO
 */
public class VtnUsuarioCTR {

    private final VtnPrincipal desktop; // DONDE VOY A VISUALIZAR
    private final VtnUsuario vista; // QUE VOY A VISUALIZAR
    private UsuarioBD modelo; // CON LO QUE VOY A TRABAJAR

    private final RolMD permisos;// LOS PERMISOS DEL USUARIO

    private static List<UsuarioMD> ListaUsuarios;

    private static DefaultTableModel modelT;

    public VtnUsuarioCTR(VtnPrincipal desktop, VtnUsuario vista, UsuarioBD modelo, RolMD permisos) {
        this.desktop = desktop;
        this.vista = vista;
        this.modelo = modelo;
        this.permisos = permisos;
    }

    //Inits
    public void Init() {
        modelT = (DefaultTableModel) vista.getTblUsuario().getModel();

        ListaUsuarios = modelo.SelectAll();

        cargarTabla(ListaUsuarios);
        Effects.centerFrame(vista, desktop.getDpnlPrincipal());

        vista.setTitle("Usuarios");

        InitPermisos();
        InitEventos();

        try {
            vista.show();
            desktop.getDpnlPrincipal().add(vista);
            vista.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(VtnUsuarioCTR.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //METODOS DE APOYO
    private void InitEventos() {

        vista.getBtnIngresar().addActionListener(e -> btnIngresarActionPerformance(e));
        vista.getBtnEliminar().addActionListener(e -> btnEliminarActionPerformance(e));
        vista.getBtnEditar().addActionListener(e -> btnEditarActionPerformance(e));
        vista.getBtnActualizar().addActionListener(e -> btnActualizarActionPerformance(e));

    }

    private void InitPermisos() {

        List<AccesosMD> listaPermisos = AccesosBD.SelectWhereACCESOROLidRol(permisos.getId());

        for (AccesosMD obj : listaPermisos) {

            if (obj.getNombre().equals("USUARIOS-Agregar")) {
                vista.getBtnIngresar().setEnabled(true);
            }
            if (obj.getNombre().equals("USUARIOS-Editar")) {
                vista.getBtnEditar().setEnabled(true);
            }
            if (obj.getNombre().equals("USUARIOS-Eliminar")) {
                vista.getBtnEliminar().setEnabled(true);
            }
            if (obj.getNombre().equals("USUARIOS-AsignarRoles")) {
                vista.getBtnAsignarRoles().setEnabled(true);
            }

        }

    }

    public void cargarTabla(List<UsuarioMD> lista) {
        modelT.setRowCount(0);
        lista.stream()
                .forEach(VtnUsuarioCTR::agregarFila);

    }

    private static void agregarFila(UsuarioMD obj) {

        if (obj.isEstado()) {
            modelT.addRow(new Object[]{
                obj.getUsername()
            });
        }

    }

    private void setObjFromTable(int fila) {

        ListaUsuarios = modelo.SelectAll();

        String username = (String) vista.getTblUsuario().getValueAt(fila, 0);

        modelo = new UsuarioBD();

        ListaUsuarios.stream()
                .filter(item -> item.getUsername().equals(username))
                .collect(Collectors.toList())
                .forEach(obj -> {
                    modelo.setUsername(obj.getUsername());
                    modelo.setIdPersona(obj.getIdPersona());
                    System.out.println(obj);
                });

    }

    //EVENTOS 
    private void btnEliminarActionPerformance(ActionEvent e) {

        int fila = vista.getTblUsuario().getSelectedRow();

        if (fila != -1) {

            String Username = (String) vista.getTblUsuario().getValueAt(fila, 0);

            if (Username.equals("ROOT")) {
                JOptionPane.showMessageDialog(vista, "NO SE PUEDE ELIMINAR AL USUARIO ROOT!!!");
            } else {

                int opcion = JOptionPane.showConfirmDialog(vista, "ESTA SEGURO DE BORRAR AL USUARIO\n" + Username);

                if (opcion == 0) {

                    modelo.eliminar(Username);

                    cargarTabla(modelo.SelectAll());

                } else {
                    JOptionPane.showMessageDialog(vista, "HA DECIDIDO NO BORRAR AL USUARIO!!");
                }
            }

        } else {

            JOptionPane.showMessageDialog(vista, "SELECCIONE UNA FILA!!!");
        }

    }

    private void btnEditarActionPerformance(ActionEvent e) {

        int fila = vista.getTblUsuario().getSelectedRow();

        if (fila != -1) {

            setObjFromTable(fila);

            FrmUsuarioCTR form = new FrmUsuarioCTR(desktop, new FrmUsuario(), modelo, "Editar");

            form.Init();

        } else {
            JOptionPane.showMessageDialog(vista, "SELECCIONE UNA FILA!!");
        }

    }

    private void btnActualizarActionPerformance(ActionEvent e) {

        cargarTabla(modelo.SelectAll());

    }

    private void btnIngresarActionPerformance(ActionEvent e) {

        FrmUsuarioCTR frm = new FrmUsuarioCTR(desktop, new FrmUsuario(), new UsuarioBD(), "Agregar");
        frm.Init();

    }

}
