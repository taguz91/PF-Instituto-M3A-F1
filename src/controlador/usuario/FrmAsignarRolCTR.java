package controlador.usuario;

import controlador.Libraries.Effects;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
        InitEventos();
        InitFuncion();

        rolesDisponibles = RolBD.SelectAll();
        rolesDados = new ArrayList<>();

        tablaDisponibles = (DefaultTableModel) vista.getTabRolesDisp().getModel();
        tablaDados = (DefaultTableModel) vista.getTabRolesDad().getModel();

        cargarTabla(rolesDisponibles, tablaDisponibles);

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
        vista.getBtnGuardar().addActionListener(e -> btnDarPermisos(e));
        vista.getBtnCancelar().addActionListener(e -> btnCancelar(e));
        vista.getBtnReset().addActionListener(e -> btnReset(e));

    }

    private void InitFuncion() {

        if (Funcion.equals("Asignar")) {

            vista.getBtnDarTodos().setEnabled(true);
            vista.getBtnDarUno().setEnabled(true);
            vista.getBtnQuitarTodos().setEnabled(true);
            vista.getBtnQuitarUno().setEnabled(true);
            vista.getBtnReset().setEnabled(true);
            vista.getBtnGuardar().setEnabled(true);

        }

    }

    /*
        METODOS DE APOYO
     */
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
                    agregarFila(obj, tablaDados);
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

    /*
        PROCESADORES DE EVENTOS
     */
    private void btnDarTodos(ActionEvent e) {

        moverTodos(rolesDados, rolesDisponibles, tablaDados, tablaDisponibles);

    }

    private void btnQuitarTodos(ActionEvent e) {
        moverTodos(rolesDisponibles, rolesDados, tablaDisponibles, tablaDados);
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

            String permiso = (String) vista.getTabRolesDisp().getValueAt(0, 0);

            moverUno(permiso, rolesDados, rolesDisponibles, tablaDados, tablaDisponibles, false);
        }
    }

    private void btnQuitarUno(ActionEvent e) {

        int filas[] = vista.getTabRolesDisp().getSelectedRows();

        if (filas.length > 0) {

            for (int g : filas) {

                String rol = (String) vista.getTabRolesDad().getValueAt(g, 0);

                moverUno(rol, rolesDisponibles, rolesDados, tablaDisponibles, tablaDados, true);

            }

            cargarTabla(rolesDados, tablaDados);
            cargarTabla(rolesDisponibles, tablaDisponibles);

        } else if (filas.length == 0 && tablaDisponibles.getDataVector().size() > 0) {

            String permiso = (String) vista.getTabRolesDad().getValueAt(0, 0);

            moverUno(permiso, rolesDisponibles, rolesDados, tablaDisponibles, tablaDados, false);
        }
    }

    private void btnDarPermisos(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void btnCancelar(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void btnReset(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
