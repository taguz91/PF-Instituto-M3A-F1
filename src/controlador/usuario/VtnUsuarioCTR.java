package controlador.usuario;

import controlador.usuario.forms.FrmUsuarioAdd;
import controlador.usuario.Roles.forms.FrmAsignarRolCTR;
import controlador.Libraries.Effects;
import controlador.usuario.forms.FrmUsuarioUpdt;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
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

    //Listas Para rellenar la tabla
    private static List<UsuarioMD> listaUsuarios;
    //Modelo de la tabla
    private static DefaultTableModel tablaUsuarios;

    private boolean cargar = true;

    public VtnUsuarioCTR(VtnPrincipal desktop, VtnUsuario vista, RolMD permisos) {
        this.desktop = desktop;
        this.vista = vista;
        this.permisos = permisos;
    }

    public VtnUsuario getVista() {
        return vista;
    }

    //Inits
    public synchronized void Init() {
        //Inicializamos la tabla
        Effects.addInDesktopPane(vista, desktop.getDpnlPrincipal());

        tablaUsuarios = (DefaultTableModel) vista.getTblUsuario().getModel();
        //Inicializamos las listas con las consultas
        listaUsuarios = UsuarioBD.selectAll();
        cargarTabla(listaUsuarios);
        //InitPermisos();
        InitEventos();

    }

    //METODOS DE APOYO
    private void InitEventos() {

        vista.getBtnIngresar().addActionListener(e -> new FrmUsuarioAdd(desktop, this).Init());
        vista.getBtnEliminar().addActionListener(e -> btnEliminar(e));
        vista.getBtnEditar().addActionListener(e -> btnEditar(e));
        vista.getBtnActualizar().addActionListener(e -> cargarTabla(UsuarioBD.selectAll()));
        vista.getBtnAsignarRoles().addActionListener(e -> btnAsignarRoles(e));
        vista.getBtnVerRoles().addActionListener(e -> btnVerRoles(e));
        vista.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                cargarTablaFilter(vista.getTxtBuscar().getText());
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

        if (cargar) {
            new Thread(() -> {

                tablaUsuarios.setRowCount(0);

                vista.getTxtBuscar().setEnabled(false);

                cargar = false;

                Effects.setLoadCursor(vista);

                lista.stream().forEach(VtnUsuarioCTR::agregarFila);

                vista.getLblResultados().setText(lista.size() + " Registros");

                Effects.setDefaultCursor(vista);

                vista.getTxtBuscar().setEnabled(true);

                cargar = true;
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
        listaTemporal = null;

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

    private UsuarioBD setObjFromTable(int fila) {

        String username = (String) vista.getTblUsuario().getValueAt(fila, 1);
        modelo = null;
        modelo = new UsuarioBD(listaUsuarios.stream()
                .filter(item -> item.getUsername().equals(username))
                .findAny()
                .get());

        return modelo;

    }

    //EVENTOS 
    private void btnEliminar(ActionEvent e) {

        int fila = vista.getTblUsuario().getSelectedRow();

        if (fila != -1) {

            String Username = (String) vista.getTblUsuario().getValueAt(fila, 1);

            if (Username.equals("ROOT")) {
                JOptionPane.showMessageDialog(vista, "NO SE PUEDE ELIMINAR AL USUARIO ROOT!!!");
            } else {

                int opcion = JOptionPane.showConfirmDialog(vista, "ESTA SEGURO DE BORRAR AL USUARIO\n" + Username);

                if (opcion == 0) {
                    if (modelo == null) {
                        modelo = new UsuarioBD();
                    }
                    modelo.cambiarEstado(Username, false);

                    cargarTabla(UsuarioBD.selectAll());

                } else {
                    JOptionPane.showMessageDialog(vista, "HA DECIDIDO NO BORRAR AL USUARIO!!");
                }
            }

        } else {

            JOptionPane.showMessageDialog(vista, "SELECCIONE UNA FILA!!!");
        }

    }

    private void btnEditar(ActionEvent e) {

        int fila = vista.getTblUsuario().getSelectedRow();

        if (fila != -1) {

            setObjFromTable(fila);

            FrmUsuarioUpdt form = new FrmUsuarioUpdt(desktop, this);
            form.Init();
            form.setModelo(setObjFromTable(fila));

        } else {
            JOptionPane.showMessageDialog(vista, "SELECCIONE UNA FILA!!");
        }

    }

    private void btnAsignarRoles(ActionEvent e) {

        int fila = vista.getTblUsuario().getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "SELECCIONE UNA FILA!!");
        } else {

            if (modelo.getUsername().equals("ROOT")) {
                JOptionPane.showMessageDialog(vista, "NO SE PUEDE EDITAR LOS PERMISOS DEL USUARIO ROOT!");
            } else {
                FrmAsignarRolCTR form = new FrmAsignarRolCTR(desktop, new FrmAsignarRoles(), new RolesDelUsuarioBD(), setObjFromTable(fila), "Asignar");
                form.Init();
            }

        }

    }

    private void btnVerRoles(ActionEvent e) {

        int fila = vista.getTblUsuario().getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "SELECCIONE UNA FILA!!");
        } else {

            FrmAsignarRolCTR form = new FrmAsignarRolCTR(desktop, new FrmAsignarRoles(), new RolesDelUsuarioBD(), setObjFromTable(fila), "Consultar");
            form.Init();

        }

    }

}
