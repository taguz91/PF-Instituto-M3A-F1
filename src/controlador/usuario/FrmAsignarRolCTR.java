package controlador.usuario;

import controlador.Libraries.Effects;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.usuario.RolBD;
import modelo.usuario.RolMD;
import modelo.usuario.RolesDelUsuarioBD;
import modelo.usuario.UsuarioBD;
import vista.principal.VtnPrincipal;
import vista.usuario.FrmAsignarRoles;

/**
 *
 * @author MrRainx
 */
public class FrmAsignarRolCTR {

    private VtnPrincipal desktop;

    private FrmAsignarRoles vista;

    private RolesDelUsuarioBD modelo;

    private UsuarioBD usuario;

    private String Funcion;

    //Listas
    private List<RolMD> rolesDisponibles;
    private List<RolMD> rolesDados;
    private List<RolesDelUsuarioBD> listaBorrar;
    //tablas
    private DefaultTableModel tablaDisponibles;
    private DefaultTableModel tablaDados;

    public FrmAsignarRolCTR(VtnPrincipal desktop, FrmAsignarRoles vista, RolesDelUsuarioBD modelo, UsuarioBD usuario, String Funcion) {
        this.desktop = desktop;
        this.vista = vista;
        this.modelo = modelo;
        this.usuario = usuario;
        this.Funcion = Funcion;
    }

    /*
        Inits
     */
    public void Init() {
        //SE INICIALIZA LISTAS Y TABLAS
        rolesDisponibles = RolBD.SelectAll();
        rolesDados = new ArrayList<>();

        tablaDisponibles = (DefaultTableModel) vista.getTabRolesDisp().getModel();
        tablaDados = (DefaultTableModel) vista.getTabRolesDad().getModel();

        InitEventos();
        InitFuncion();

        vista.getLblUsuario().setText(usuario.getUsername());

        try {
            Effects.centerFrame(vista, desktop.getDpnlPrincipal());
            desktop.getDpnlPrincipal().add(vista);
            vista.show();
            vista.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(FrmAsignarRolCTR.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void InitEventos() {

        vista.getBtnDarTodos().addActionListener(e -> btnDarTodos(e));
        vista.getBtnQuitarTodos().addActionListener(e -> btnQuitarTodos(e));
        vista.getBtnDarUno().addActionListener(e -> btnDarDarUno(e));
        vista.getBtnQuitarUno().addActionListener(e -> btnQuitarUno(e));
        vista.getBtnGuardar().addActionListener(e -> btnGuardar(e));
        vista.getBtnCancelar().addActionListener(e -> btnCancelar(e));
        vista.getBtnReset().addActionListener(e -> btnReset(e));

    }

    private void InitFuncion() {

        switch (Funcion) {
            case "Asignar":
                activarBtns(true);

                rolesDados = RolBD.SelectWhereUSUARIOusername(usuario.getUsername());
                validacion();

                for (RolMD objDado : rolesDados) {

                    for (RolMD objDisponible : rolesDisponibles) {
                        if (objDado.getId() == objDisponible.getId()) {

                            System.out.println("--------------");

                            rolesDisponibles.remove(objDisponible);
                        }
                    }

                }

                System.out.println("--->" + rolesDisponibles.size());

                cargarTabla(rolesDados, tablaDados);
                cargarTabla(rolesDisponibles, tablaDisponibles);
                break;
            case "Asignar-Roles-Usuario":
                cargarTabla(rolesDisponibles, tablaDisponibles);
                activarBtns(true);
                break;
            case "Consultar":
                activarBtns(false);

                break;
            default:
                break;
        }

    }

    /*
        METODOS DE APOYO
     */
    private void activarBtns(boolean estado) {
        vista.getBtnDarTodos().setEnabled(estado);
        vista.getBtnDarUno().setEnabled(estado);
        vista.getBtnQuitarTodos().setEnabled(estado);
        vista.getBtnQuitarUno().setEnabled(estado);
        vista.getBtnReset().setEnabled(estado);
    }

    private void cargarTabla(List<RolMD> lista, DefaultTableModel tabla) {
        tabla.setRowCount(0);
        lista.stream()
                .forEach(obj -> {
                    agregarFila(obj, tabla);
                });

    }

    private void agregarFila(RolMD obj, DefaultTableModel tabla) {
        tabla.addRow(new Object[]{
            obj.getNombre()
        });
    }

    private void moverTodos(List<RolMD> listaAgregar, List<RolMD> listaQuitar, DefaultTableModel tablaAgregar, DefaultTableModel tablaQuitar) {
        List<RolMD> listaTemporal = new ArrayList<>(listaQuitar);

        listaTemporal.stream().forEach(obj -> {
            listaAgregar.add(obj);
            listaQuitar.remove(obj);
        });

        listaTemporal = null;
        tablaQuitar.setRowCount(0);
        cargarTabla(listaAgregar, tablaAgregar);

        vista.getTxtBuscar().setText("");
        vista.getTxtBuscarDados().setText("");

    }

    private void moverUno(String permiso, List<RolMD> listaAgregar, List<RolMD> listaQuitar, DefaultTableModel tablaAgregar, DefaultTableModel tablaQuitar, boolean isArray) {

        List<RolMD> listaTemporal = new ArrayList<>(listaQuitar);

        listaTemporal.stream()
                .filter(item -> item.getNombre().equals(permiso))
                .collect(Collectors.toList())
                .forEach(obj -> {
                    agregarFila(obj, tablaAgregar);
                    listaAgregar.add(obj);
                    listaQuitar.remove(obj);
                });

        listaTemporal = null;

        if (!isArray) {

            cargarTabla(listaAgregar, tablaAgregar);
            cargarTabla(listaQuitar, tablaQuitar);
        }
        vista.getTxtBuscar().setText("");
        vista.getTxtBuscarDados().setText("");
    }

    private void validacion() {
        if (rolesDados.size() > 0) {
            vista.getBtnGuardar().setEnabled(true);
        } else {
            vista.getBtnGuardar().setEnabled(false);
        }
    }

    private void guardarUsuario() {

        usuario.insertar();

        JOptionPane.showMessageDialog(vista, "SE HA GUARDADO AL USUARIO" + usuario.getUsername().toUpperCase());
    }

    private void EditarRoles() {
        listaBorrar = new ArrayList<>();
        rolesDisponibles.stream()
                .forEach(obj -> {
                    listaBorrar.add(new RolesDelUsuarioBD(0, obj.getId(), usuario.getUsername()));
                });

        listaBorrar.stream().forEach(obj -> {
            modelo.eliminarWhere(modelo.getId(), usuario.getUsername());
        });

        rolesDados
                .stream()
                .forEach(obj -> {
                    modelo.setUsername(usuario.getUsername());
                    modelo.setIdRol(obj.getId());
                    modelo.insertar();
                });

        vista.dispose();
    }

    /*
        PROCESADORES DE EVENTOS
     */
    private void btnDarTodos(ActionEvent e) {

        moverTodos(rolesDados, rolesDisponibles, tablaDados, tablaDisponibles);
        validacion();
    }

    private void btnQuitarTodos(ActionEvent e) {
        moverTodos(rolesDisponibles, rolesDados, tablaDisponibles, tablaDados);
        validacion();
    }

    private void btnDarDarUno(ActionEvent e) {

        int filas[] = vista.getTabRolesDisp().getSelectedRows();

        if (filas.length > 0) {

            for (int g : filas) {

                String rol = (String) vista.getTabRolesDisp().getValueAt(g, 0);

                moverUno(rol, rolesDados, rolesDisponibles, tablaDados, tablaDisponibles, true);

            }

            cargarTabla(rolesDados, tablaDados);
            cargarTabla(rolesDisponibles, tablaDisponibles);

        } else if (filas.length == 0 && tablaDisponibles.getDataVector().size() > 0) {

            String rol = (String) vista.getTabRolesDisp().getValueAt(0, 0);

            moverUno(rol, rolesDados, rolesDisponibles, tablaDados, tablaDisponibles, false);
        }
        validacion();
    }

    private void btnQuitarUno(ActionEvent e) {

        int filas[] = vista.getTabRolesDad().getSelectedRows();

        if (filas.length > 0) {

            for (int g : filas) {

                String rol = (String) vista.getTabRolesDad().getValueAt(g, 0);

                moverUno(rol, rolesDisponibles, rolesDados, tablaDisponibles, tablaDados, true);

            }

            cargarTabla(rolesDados, tablaDados);
            cargarTabla(rolesDisponibles, tablaDisponibles);

        } else if (filas.length == 0 && tablaDados.getDataVector().size() > 0) {

            String rol = (String) vista.getTabRolesDad().getValueAt(0, 0);

            moverUno(rol, rolesDisponibles, rolesDados, tablaDisponibles, tablaDados, false);
        }
        validacion();
    }

    private void btnGuardar(ActionEvent e) {
        if (Funcion.equals("Asignar-Roles-Usuario")) {
            guardarUsuario();
            EditarRoles();
        } else {
        }

    }

    private void btnCancelar(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void btnReset(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
