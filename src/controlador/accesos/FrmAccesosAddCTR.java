package controlador.accesos;

import controlador.Libraries.Effects;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import javax.swing.table.DefaultTableModel;
import modelo.accesos.AccesosBD;
import modelo.usuario.RolBD;
import vista.accesos.FrmAccesosDeRol;

/**
 *
 * @author MrRainx
 */
public class FrmAccesosAddCTR {

    private final VtnPrincipalCTR desktop;
    private final FrmAccesosDeRol vista;
    private final AccesosBD modelo;
    private RolBD rol;

    private DefaultTableModel tblDados;
    private DefaultTableModel tblDisp;

    private List<AccesosBD> listaDisp;
    private final List<AccesosBD> listaDad;
    private final List<String> mover;

    public FrmAccesosAddCTR(VtnPrincipalCTR destop) {
        this.desktop = destop;
        this.vista = new FrmAccesosDeRol();
        this.modelo = new AccesosBD();
        this.listaDad = new ArrayList<>();
        this.mover = new ArrayList<>();
    }

    public void setRol(RolBD rol) {
        this.rol = rol;
    }

    //Inits
    public void Init() {

        Effects.addInDesktopPane(vista, desktop.getVtnPrin().getDpnlPrincipal());
        tblDados = (DefaultTableModel) vista.getTabPermDados().getModel();
        tblDisp = (DefaultTableModel) vista.getTabPermisos().getModel();
        cargarTabla(listaDisp = modelo.SelectAll(), tblDisp);
        vista.getLblRolSeleccionado().setText(rol.getNombre());
        InitEventos();
        validarBtns();
    }

    private void InitEventos() {
        vista.getBtnCancelar().addActionListener(e -> vista.dispose());
        vista.getBtnReset().addActionListener(e -> btnReset(e));
        vista.getBtnDarTodos().addActionListener(e -> btnDarTodos(e));
        vista.getBtnDarUno().addActionListener(e -> btnDarUno(e));
        vista.getBtnQuitarTodos().addActionListener(e -> btnQuitarTodos(e));
        vista.getBtnQuitarUno().addActionListener(e -> btnQuitarUno(e));
        vista.getBtnGuardar().addActionListener(e -> btnGuardar(e));
    }

    //Metodos de apoyo
    private void cargarTabla(List<AccesosBD> permisos, DefaultTableModel tabla) {
        tabla.setRowCount(0);
        permisos.stream()
                .sorted((item1, item2) -> item1.getNombre().compareTo(item2.getNombre()))
                .map(c -> c.getNombre())
                .forEach(obj -> {
                    tabla.addRow(new Object[]{
                        obj
                    });
                });
    }

    private void moverTodos(List<AccesosBD> listFrom, List<AccesosBD> listTo, DefaultTableModel tblFrom, DefaultTableModel tblTo) {
        listFrom.forEach(listTo::add);
        listFrom.removeAll(listTo);
        cargarTabla(listTo, tblTo);
        tblFrom.setRowCount(0);
        validarBtns();
    }

    private void moverUno(List<AccesosBD> listFrom, List<AccesosBD> listTo, DefaultTableModel tblFrom, DefaultTableModel tblTo, List<String> movers) {

        movers.forEach(objs -> {
            listTo.add(
                    listFrom.stream().filter(item -> item.getNombre().equals(objs)).findFirst().get()
            );
        });
        listFrom.removeAll(listTo);
        cargarTabla(listFrom, tblFrom);
        cargarTabla(listTo, tblTo);
        validarBtns();

    }

    private void validarBtns() {
        if (listaDad.isEmpty()) {
            vista.getBtnQuitarTodos().setEnabled(false);
            vista.getBtnQuitarUno().setEnabled(false);
            vista.getBtnReset().setEnabled(false);
        } else {
            vista.getBtnReset().setEnabled(true);
            vista.getBtnQuitarTodos().setEnabled(true);
            vista.getBtnQuitarUno().setEnabled(true);
        }
        if (listaDisp.isEmpty()) {
            vista.getBtnDarTodos().setEnabled(false);
            vista.getBtnDarUno().setEnabled(false);
        } else {
            vista.getBtnDarTodos().setEnabled(true);
            vista.getBtnDarUno().setEnabled(true);
        }
    }

    //Eventos
    private void btnReset(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void btnDarTodos(ActionEvent e) {
        moverTodos(listaDisp, listaDad, tblDisp, tblDados);
    }

    private void btnDarUno(ActionEvent e) {
        int[] rows = vista.getTabPermisos().getSelectedRows();

        if (rows.length != 0) {
            IntStream.of(rows).forEach(i -> {
                mover.add(vista.getTabPermisos().getValueAt(i, 0).toString());
            });
            moverUno(listaDisp, listaDad, tblDisp, tblDados, mover);
        } else if (listaDisp.size() > 0) {
            mover.add(vista.getTabPermisos().getValueAt(0, 0).toString());
            moverUno(listaDisp, listaDad, tblDisp, tblDados, mover);
        }
        mover.clear();
    }

    private void btnQuitarTodos(ActionEvent e) {
        moverTodos(listaDad, listaDisp, tblDados, tblDisp);
    }

    private void btnQuitarUno(ActionEvent e) {
        int[] rows = vista.getTabPermDados().getSelectedRows();

        if (rows.length != 0) {
            IntStream.of(rows).forEach(i -> {
                mover.add(vista.getTabPermDados().getValueAt(i, 0).toString());
            });
            moverUno(listaDad, listaDisp, tblDados, tblDisp, mover);
        } else if (listaDad.size() > 0) {
            mover.add(vista.getTabPermDados().getValueAt(0, 0).toString());
            moverUno(listaDad, listaDisp, tblDados, tblDisp, mover);
        }
        mover.clear();
    }

    private void btnGuardar(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
