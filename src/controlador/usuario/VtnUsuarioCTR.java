package controlador.usuario;

import controlador.Libraries.Effects;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.accesos.AccesosBD;
import modelo.accesos.AccesosMD;
import modelo.persona.PersonaBD;
import modelo.persona.PersonaMD;
import modelo.usuario.RolMD;
import modelo.usuario.RolesDelUsuarioBD;
import modelo.usuario.UsuarioBD;
import modelo.usuario.UsuarioMD;
import vista.principal.VtnPrincipal;
import vista.usuario.FrmAsignarRoles;
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
    //Modelos para trabajar
    private final RolMD permisos;
    private final ConectarDB conexion;

    //Listas Para rellenar la tabla
    private static List<UsuarioMD> listaUsuarios;
    private static List<PersonaMD> listaPersonas;

    //Modelo de la tabla
    private static DefaultTableModel tablaUsuarios;

    public VtnUsuarioCTR(VtnPrincipal desktop, VtnUsuario vista, RolMD permisos, ConectarDB conexion) {
        this.desktop = desktop;
        this.vista = vista;
        this.permisos = permisos;
        this.conexion = conexion;
    }

    //Inits
    public void Init() {
        //Inicializamos la tabla
        tablaUsuarios = (DefaultTableModel) vista.getTblUsuario().getModel();

        //Inicializamos las listas con las consultas
        listaUsuarios = UsuarioBD.SelectAll();

        cargarTabla(listaUsuarios);

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
        vista.getBtnAsignarRoles().addActionListener(e -> btnAsignarRolesActionPerformance(e));
        vista.getBtnVerRoles().addActionListener(e -> btnVerRolesActionPerformance(e));
        vista.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                txtBuscarKeyReleased(e);
            }
        });

    }

    private void InitPermisos() {

        for (AccesosMD obj : AccesosBD.SelectWhereACCESOROLidRol(permisos.getId())) {

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
            if (obj.getNombre().equals("USUARIOS-VerRoles")) {
                vista.getBtnVerRoles().setEnabled(true);
            }

        }

    }

    public void cargarTabla(List<UsuarioMD> lista) {
        listaPersonas = new ArrayList<>();
        tablaUsuarios.setRowCount(0);
        lista.stream()
                .forEach(objUser -> {
                    PersonaBD.selectWhereUsername(objUser.getUsername())
                            .stream()
                            .forEach(objPersona -> {
                                agregarFila(objUser, objPersona);
                                listaPersonas.add(objPersona);
                            });
                });

    }

    private void cargarTablaFilter(String Aguja) {
        tablaUsuarios.setRowCount(0);
//        listaUsuarios
//                .stream()
//                .filter(item -> item.getUsername().toUpperCase().contains(Aguja.toUpperCase()))
//                .collect(Collectors.toList())
//                .forEach(objUsuario -> {
//                    listaPersonas
//                            .stream()
//                            .filter(itemPersona -> itemPersona.)
//                            .forEach(objPersona -> {
//                                agregarFila(objUsuario, objPersona);
//                            });
//                });
    }

    private static void agregarFila(UsuarioMD objUsuario, PersonaMD objPersona) {

        if (objUsuario.isEstado()) {
            tablaUsuarios.addRow(new Object[]{
                objUsuario.getUsername(),
                objPersona.getIdentificacion(),
                objPersona.getPrimerNombre() + " " + objPersona.getSegundoNombre() + " " + objPersona.getPrimerApellido() + " " + objPersona.getSegundoApellido()
            });
        }

    }

    private void setObjFromTable(int fila) {

        listaUsuarios = UsuarioBD.SelectAll();

        String username = (String) vista.getTblUsuario().getValueAt(fila, 0);

        modelo = new UsuarioBD();

        listaUsuarios.stream()
                .filter(item -> item.getUsername().equals(username))
                .collect(Collectors.toList())
                .forEach(obj -> {
                    modelo.setUsername(obj.getUsername());
                    modelo.setIdPersona(obj.getIdPersona());
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

                    cargarTabla(UsuarioBD.SelectAll());

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

            FrmUsuarioCTR form = new FrmUsuarioCTR(desktop, new FrmUsuario(), modelo, "Editar", conexion);

            form.Init();

        } else {
            JOptionPane.showMessageDialog(vista, "SELECCIONE UNA FILA!!");
        }

    }

    private void btnActualizarActionPerformance(ActionEvent e) {

        cargarTabla(UsuarioBD.SelectAll());

    }

    private void btnIngresarActionPerformance(ActionEvent e) {

        FrmUsuarioCTR frm = new FrmUsuarioCTR(desktop, new FrmUsuario(), new UsuarioBD(), "Agregar", conexion);
        frm.Init();

    }

    private void btnAsignarRolesActionPerformance(ActionEvent e) {

        int fila = vista.getTblUsuario().getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "SELECCIONE UNA FILA!!");
        } else {

            setObjFromTable(fila);

            if (modelo.getUsername().equals("ROOT")) {
                JOptionPane.showMessageDialog(vista, "NO SE PUEDE EDITAR LOS PERMISOS DEL USUARIO ROOT!");
            } else {
                FrmAsignarRolCTR form = new FrmAsignarRolCTR(desktop, new FrmAsignarRoles(), new RolesDelUsuarioBD(), modelo, "Asignar");
                form.Init();
            }

        }

    }

    private void btnVerRolesActionPerformance(ActionEvent e) {

        int fila = vista.getTblUsuario().getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "SELECCIONE UNA FILA!!");
        } else {

            setObjFromTable(fila);

            FrmAsignarRolCTR form = new FrmAsignarRolCTR(desktop, new FrmAsignarRoles(), new RolesDelUsuarioBD(), modelo, "Consultar");
            form.Init();

        }

    }

    private void txtBuscarKeyReleased(KeyEvent e) {
        cargarTablaFilter(vista.getTxtBuscar().getText());
    }
}
