package controlador.accesos;

import controlador.Libraries.Effects;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.ActionEvent;
import java.util.List;
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
    private DefaultTableModel tblDisponibles;

    private List<AccesosBD> permisos;

    public FrmAccesosAddCTR(VtnPrincipalCTR destop) {
        this.desktop = destop;
        this.vista = new FrmAccesosDeRol();
        this.modelo = new AccesosBD();
    }

    public void setRol(RolBD rol) {
        this.rol = rol;
    }

    //Inits
    public void Init() {

        Effects.addInDesktopPane(vista, desktop.getVtnPrin().getDpnlPrincipal());
        tblDados = (DefaultTableModel) vista.getTabPermDados().getModel();
        tblDisponibles = (DefaultTableModel) vista.getTabPermisos().getModel();
        permisos = modelo.SelectAll();
        InitEventos();
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
        permisos.stream()
                .sorted((item1, item2) -> item1.getNombre().compareTo(item2.getNombre()))
                .map(c -> c.getNombre())
                .forEach(obj -> {
                    tabla.addRow(new Object[]{
                        obj
                    });
                });
    }

    private void mover(List<AccesosBD> listDisp, List<AccesosBD> listDad, DefaultTableModel tblDisp, DefaultTableModel tblDad) {
    }

    //Eventos
    private void btnReset(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void btnDarTodos(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void btnDarUno(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void btnQuitarTodos(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void btnQuitarUno(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void btnGuardar(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
