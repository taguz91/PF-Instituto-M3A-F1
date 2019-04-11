/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.accesos;

import controlador.Libraries.Middlewares;
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
import modelo.accesos.AccesosBD;
import modelo.accesos.AccesosMD;
import modelo.accesosDelRol.AccesosDelRolBD;
import modelo.accesosDelRol.AccesosDelRolMD;
import modelo.usuario.RolBD;
import vista.accesos.FrmAccesosDeRol;
import vista.principal.VtnPrincipal;

/**
 *
 * @author MrRainx
 */
public class FrmAccesosDeRolCTR {

    private final VtnPrincipal desktop;
    private final FrmAccesosDeRol vista;
    private final AccesosDelRolBD modelo;
    private final RolBD rol;

    private final String Funcion;

    private List<AccesosMD> listaPermisos;
    private List<AccesosMD> listaPermDados;
    private List<AccesosDelRolMD> listaBorrar;

    private DefaultTableModel tablaPermisos;
    private DefaultTableModel tablaPermDados;

    public FrmAccesosDeRolCTR(VtnPrincipal desktop, FrmAccesosDeRol vista, AccesosDelRolBD modelo, RolBD rol, String Funcion) {
        this.desktop = desktop;
        this.vista = vista;
        this.modelo = modelo;
        this.rol = rol;
        this.Funcion = Funcion;
    }

    //Inits
    public void Init() {

        tablaPermisos = (DefaultTableModel) vista.getTabPermisos().getModel();
        tablaPermDados = (DefaultTableModel) vista.getTabPermDados().getModel();

        Middlewares.centerFrame(vista, desktop.getDpnlPrincipal());

        InitFuncion();

        InitEventos();

        validacion();

        vista.show();
        desktop.getDpnlPrincipal().add(vista);
        try {
            vista.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(FrmAccesosDeRolCTR.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void InitEventos() {

        if (Funcion.equals("Editar")) {

            vista.getBtnDarTodos().setEnabled(true);
            vista.getBtnQuitarTodos().setEnabled(true);
            vista.getBtnDarUno().setEnabled(true);
            vista.getBtnQuitarUno().setEnabled(true);

            listaBorrar = new ArrayList<>();

            listaPermDados.forEach(obj -> {
                listaBorrar.add(new AccesosDelRolMD(0, rol.getId(), obj.getIdAccesos()));
            });

        }
        vista.getBtnGuardar().addActionListener(e -> btnGuardar(e));

        vista.getBtnDarTodos().addActionListener(e -> btnDarTodosActionPerformance(e));
        vista.getBtnQuitarTodos().addActionListener(e -> btnQuitarTodosActionPerformance(e));

        vista.getBtnDarUno().addActionListener(e -> btnDarUnoActionPerformnce(e));
        vista.getBtnQuitarUno().addActionListener(e -> btnQuitarUnoActionPerformance(e));

        vista.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                txtBuscarOnKeyReleased(e);
            }
        });

        vista.getTxtBuscarDados().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                txtBuscarOnKeyReleased(e);
            }
        });

        vista.getBtnCancelar().addActionListener(e -> btnCancelarOnActionPerformance(e));

    }

    private void InitFuncion() {

        AccesosBD permisos = new AccesosBD();
        listaPermisos = permisos.SelectAll();

        if (Funcion.equals("Consultar")) {
            setListasFuncion();
        } else if (Funcion.equals("Editar")) {
            setListasFuncion();
        }
        cargarTabla(listaPermisos, tablaPermisos);
    }

    //Metodos de Apoyo
    private void setListasFuncion() {
        vista.getLblRolSeleccionado().setText(rol.getNombre());

        listaPermDados = AccesosBD.SelectWhereACCESOROLidRol(rol.getId());

        listaPermDados.forEach((permisoDado) -> {
            listaPermisos
                    .stream()
                    .filter(item -> item.getIdAccesos() == permisoDado.getIdAccesos())
                    .collect(Collectors.toList())
                    .forEach(listaPermisos::remove);
        });

        cargarTabla(listaPermDados, tablaPermDados);
    }

    private void cargarTabla(List<AccesosMD> lista, DefaultTableModel tabla) {
        tabla.setRowCount(0);
        lista.stream()
                .sorted((item1, item2) -> item1.getNombre().compareTo(item2.getNombre()))
                .collect(Collectors.toList())
                .forEach(obj -> {
                    agregarFila(obj, tabla);
                });

    }

    private void cargarTablaFilter(List<AccesosMD> lista, DefaultTableModel tabla, String Aguja) {

        tabla.setRowCount(0);

        lista.stream()
                .filter(item -> item.getNombre().contains(Aguja))
                .sorted((item1, item2) -> item1.getNombre().compareTo(item2.getNombre()))
                .collect(Collectors.toList())
                .forEach(obj -> {
                    agregarFila(obj, tabla);
                });
    }

    private void agregarFila(AccesosMD obj, DefaultTableModel tabla) {

        tabla.addRow(new Object[]{
            obj.getNombre()
        });

    }

    private void moverTodos(List<AccesosMD> listaAgregar, List<AccesosMD> listaQuitar, DefaultTableModel tablaAgregar, DefaultTableModel tablaQuitar) {
        List<AccesosMD> listaTemporal = new ArrayList<>(listaQuitar);

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

    private void moverUno(String permiso, List<AccesosMD> listaAgregar, List<AccesosMD> listaQuitar, DefaultTableModel tablaAgregar, DefaultTableModel tablaQuitar, boolean isArray) {
        List<AccesosMD> listaTemporal = new ArrayList<>(listaQuitar);

        listaTemporal.stream()
                .filter(item -> item.getNombre().equals(permiso))
                .collect(Collectors.toList())
                .forEach(obj -> {
                    agregarFila(obj, tablaPermDados);
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

        if (listaPermDados.size() > 0) {
            vista.getBtnGuardar().setEnabled(true);
        } else {
            vista.getBtnGuardar().setEnabled(false);
        }

    }

    //Procesadores de Eventos
    private void btnDarTodosActionPerformance(ActionEvent e) {
        moverTodos(listaPermDados, listaPermisos, tablaPermDados, tablaPermisos);
        validacion();
    }

    private void btnQuitarTodosActionPerformance(ActionEvent e) {
        moverTodos(listaPermisos, listaPermDados, tablaPermisos, tablaPermDados);
        validacion();
    }

    private void btnDarUnoActionPerformnce(ActionEvent e) {

        int filas[] = vista.getTabPermisos().getSelectedRows();

        if (filas.length > 0) {

            for (int g : filas) {

                String permiso = (String) vista.getTabPermisos().getValueAt(g, 0);

                moverUno(permiso, listaPermDados, listaPermisos, tablaPermDados, tablaPermisos, true);

            }

            cargarTabla(listaPermisos, tablaPermisos);
            cargarTabla(listaPermDados, tablaPermDados);

        } else if (filas.length == 0 && tablaPermisos.getDataVector().size() > 0) {

            String permiso = (String) vista.getTabPermisos().getValueAt(0, 0);

            moverUno(permiso, listaPermDados, listaPermisos, tablaPermDados, tablaPermisos, false);
        }
        validacion();

    }

    private void btnQuitarUnoActionPerformance(ActionEvent e) {

        int filas[] = vista.getTabPermDados().getSelectedRows();

        if (filas.length > 0) {
            for (int g : filas) {

                String permiso = (String) vista.getTabPermDados().getValueAt(g, 0);

                moverUno(permiso, listaPermisos, listaPermDados, tablaPermisos, tablaPermDados, true);

            }

            cargarTabla(listaPermisos, tablaPermisos);
            cargarTabla(listaPermDados, tablaPermDados);

        } else if (filas.length == 0 && tablaPermDados.getDataVector().size() > 0) {

            String permiso = (String) vista.getTabPermDados().getValueAt(0, 0);

            moverUno(permiso, listaPermisos, listaPermDados, tablaPermisos, tablaPermDados, false);
        }
        validacion();
    }

    private void txtBuscarOnKeyReleased(KeyEvent e) {

        if (e.getSource().equals(vista.getTxtBuscar())) {

            String permiso = vista.getTxtBuscar().getText();

            if (permiso.length() >= 3) {

                cargarTablaFilter(listaPermisos, tablaPermisos, permiso);

            } else if (permiso.length() == 0) {

                cargarTabla(listaPermisos, tablaPermisos);
            }
        } else {

            String permiso = vista.getTxtBuscarDados().getText();

            if (permiso.length() >= 3) {

                cargarTablaFilter(listaPermDados, tablaPermDados, permiso);

            } else if (permiso.length() == 0) {

                cargarTabla(listaPermDados, tablaPermDados);

            }
        }

    }

    private void btnCancelarOnActionPerformance(ActionEvent e) {
        vista.dispose();
    }

    private void btnGuardar(ActionEvent e) {

        listaBorrar.stream().forEach(obj -> {
            modelo.eliminarWhere(rol.getId(), obj.getIdAcceso());
        });

        listaPermDados
                .stream()
                .forEach(obj -> {
                    modelo.setIdRol(rol.getId());
                    modelo.setIdAcceso(obj.getIdAccesos());
                    modelo.insertar();
                });

        vista.dispose();
        JOptionPane.showMessageDialog(vista, "SE HAN EDITADO LOS PERMISOS DEL ROL: " + rol.getNombre().toUpperCase());
    }

}
