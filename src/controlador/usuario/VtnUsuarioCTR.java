package controlador.usuario;

import controlador.usuario.forms.FrmUsuarioAdd;
import controlador.usuario.Roles.forms.FrmAsignarRolCTR;
import controlador.Libraries.Middlewares;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import static java.lang.Thread.sleep;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.accesos.AccesosBD;
import modelo.accesos.AccesosMD;
import modelo.usuario.RolMD;
import modelo.usuario.RolesDelUsuarioBD;
import modelo.usuario.UsuarioBD;
import modelo.usuario.UsuarioMD;
import vista.principal.VtnPrincipal;
import vista.usuario.FrmAsignarRoles;
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

    //Modelo de la tabla
    private static DefaultTableModel tablaUsuarios;

    //Threads
    private Thread thread;
    private boolean cargaTabla = true;

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

        Middlewares.centerFrame(vista, desktop.getDpnlPrincipal());

        //InitPermisos();
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

        for (AccesosMD obj : AccesosBD.selectWhereLIKE(permisos.getId(), "USUARIOS")) {

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

    /*
    
        METODOS DE APOYO
    
     */
    public void cargarTabla(List<UsuarioMD> lista) {

        if (cargaTabla) {
            new Thread(() -> {
                try {
                    tablaUsuarios.setRowCount(0);

                    vista.getTxtBuscar().setEnabled(false);

                    cargaTabla = false;

                    Middlewares.setLoadCursor(vista);

                    desktop.getLblEstado().setText("CARGANDO USUARIOS");

                    lista.stream()
                            .forEach(VtnUsuarioCTR::agregarFila);
                    vista.getLblResultados().setText(lista.size() + " Registros");

                    Middlewares.setDefaultCursor(vista);

                    sleep(500);

                    cargaTabla = true;

                    Middlewares.setTextInLabel(desktop.getLblEstado(), "CARGA COMPLETA", 2);

                    vista.getTxtBuscar().setEnabled(true);

                    System.out.println("");
                } catch (InterruptedException ex) {
                    Logger.getLogger(VtnUsuarioCTR.class.getName()).log(Level.SEVERE, null, ex);
                }

            }).start();

        } else {
            JOptionPane.showMessageDialog(vista, "YA HAY UNA CARGA DE UNA TABLA PENDIENTE");
        }

    }

    private void cargarTablaFilter(String Aguja) {
        tablaUsuarios.setRowCount(0);

        List<UsuarioMD> listaTemporal = listaUsuarios
                .stream()
                .filter(
                        item -> item.getUsername().toUpperCase().contains(Aguja.toUpperCase())
                        || item.getPersona().getIdentificacion().toUpperCase().contains(Aguja.toUpperCase())
                        || item.getPersona().getPrimerApellido().toUpperCase().contains(Aguja.toUpperCase())
                        || item.getPersona().getSegundoApellido().toUpperCase().contains(Aguja.toUpperCase())
                        || item.getPersona().getPrimerNombre().toUpperCase().contains(Aguja.toUpperCase())
                        || item.getPersona().getSegundoNombre().toUpperCase().contains(Aguja.toUpperCase())
                )
                .collect(Collectors.toList());

        listaTemporal.forEach(VtnUsuarioCTR::agregarFila);
        vista.getLblResultados().setText(listaTemporal.size() + " Registros");

    }

    private static void agregarFila(UsuarioMD obj) {
        tablaUsuarios.addRow(new Object[]{
            tablaUsuarios.getDataVector().size() + 1,
            obj.getUsername(),
            obj.getPersona().getIdentificacion(),
            obj.getPersona().getPrimerApellido(),
            obj.getPersona().getSegundoApellido(),
            obj.getPersona().getPrimerNombre(),
            obj.getPersona().getSegundoNombre()

        });

    }

    private void setObjFromTable(int fila) {

        listaUsuarios = UsuarioBD.SelectAll();

        String username = (String) vista.getTblUsuario().getValueAt(fila, 1);

        modelo = new UsuarioBD();

        listaUsuarios.stream()
                .filter(item -> item.getUsername().equals(username))
                .collect(Collectors.toList())
                .forEach(obj -> {
                    modelo.setUsername(obj.getUsername());
                    modelo.setPersona(obj.getPersona());
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

            FrmUsuarioAdd form = new FrmUsuarioAdd(desktop);

            form.Init();

        } else {
            JOptionPane.showMessageDialog(vista, "SELECCIONE UNA FILA!!");
        }

    }

    private void btnActualizarActionPerformance(ActionEvent e) {

        cargarTabla(UsuarioBD.SelectAll());

    }

    private void btnIngresarActionPerformance(ActionEvent e) {

        FrmUsuarioAdd frm = new FrmUsuarioAdd(desktop);
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
