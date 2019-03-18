/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.usuario;

import controlador.Libraries.Effects;
import controlador.accesos.FrmAccesosDeRolCTR;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.accesos.AccesosBD;
import modelo.accesos.AccesosMD;
import modelo.accesosDelRol.AccesosDelRolBD;
import modelo.usuario.RolBD;
import modelo.usuario.RolMD;
import vista.accesos.FrmAccesosDeRol;
import vista.principal.VtnPrincipal;
import vista.usuario.FrmRol;
import vista.usuario.VtnRol;

/**
 *
 * @author MrRainx
 */
public class VtnRolCTR {

    private final VtnPrincipal desktop;

    private final VtnRol vista;

    private final RolBD modelo;

    private final RolBD rol;

    private List<RolMD> listaRoles;

    private static DefaultTableModel modelT;

    public VtnRolCTR(VtnPrincipal desktop, VtnRol vista, RolBD modelo, RolBD rol) {
        this.desktop = desktop;
        this.vista = vista;
        this.modelo = modelo;
        this.rol = rol;
    }

    //Inits
    public void Init() {

        Effects.centerFrame(vista, desktop.getDpnlPrincipal());

        vista.setTitle("Lista de Roles");

        modelT = (DefaultTableModel) vista.getTabRoles().getModel();

        InitPermisos();
        cargarTabla();

        vista.show();
        desktop.getDpnlPrincipal().add(vista);

        try {
            vista.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(VtnRolCTR.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

    }

    private void InitPermisos() {
        vista.getBtnBuscar().addActionListener(e -> btnBuscarActionPerformance(e));
        vista.getBtnActualizar().addActionListener(e -> btnActualizarActionPerformance(e));
        vista.getBtnCancelar().addActionListener(e -> btnCancelarActionPerformance(e));

        vista.getBtnVerPermisos().addActionListener(e -> btnVerPermisosActionPerformance(e));
        vista.getBtnEditarPermisos().addActionListener(e -> btnEditarPermisosActionPerformance(e));

        vista.getBtnBuscar().addActionListener(e -> btnBuscarActionPerformance(e));
        vista.getBtnActualizar().addActionListener(e -> btnActualizarActionPerformance(e));

        for (AccesosMD obj : AccesosBD.SelectWhereACCESOROLidRol(rol.getId())) {

            if (obj.getNombre().equals("ROLES-Agregar")) {
                vista.getBtnIngresar().setEnabled(true);
            }
            if (obj.getNombre().equals("ROLES-Editar")) {
                vista.getBtnEditar().setEnabled(true);
            }
            if (obj.getNombre().equals("ROLES-Eliminar")) {
                vista.getBtnEliminar().setEnabled(true);
            }
            if (obj.getNombre().equals("ROLES-Ver-Permisos")) {
                vista.getBtnVerPermisos().setEnabled(true);
            }
            if (obj.getNombre().equals("ROLES-Editar-Permisos")) {
                vista.getBtnEditarPermisos().setEnabled(true);
            }
        }

    }

    //Metodos de Apoyo
    private void cargarTabla() {

        listaRoles = RolBD.SelectAll();

        listaRoles.stream().forEach(VtnRolCTR::insertarFila);

        vista.getLblResultados().setText(listaRoles.size() + " Resultados Obtenidos");

    }

    private static void insertarFila(RolMD obj) {

        modelT.addRow(new Object[]{
            obj.getId(),
            obj.getNombre()
        });

    }

    private void cargarTablaFilter(String Aguja) {

        listaRoles = RolBD.SelectWhereNombreLike(Aguja);

        listaRoles.stream().forEach(VtnRolCTR::insertarFila);

        vista.getLblResultados().setText(listaRoles.size() + " Resultados Obtenidos");

    }

    private void borrarFilas() {

        int filas = modelT.getDataVector().size();

        if (filas > 0) {
            for (int i = 0; i < filas; i++) {
                modelT.removeRow(0);
            }
        }
    }

    private void getObjFromRow(int fila) {

        modelo.setId((Integer) vista.getTabRoles().getValueAt(fila, 0));
        modelo.setNombre((String) vista.getTabRoles().getValueAt(fila, 1));

    }

    //Procesadores de Eventos
    private void btnBuscarActionPerformance(ActionEvent e) {
        borrarFilas();
        cargarTablaFilter(vista.getTxtBuscar().getText());
    }

    private void btnActualizarActionPerformance(ActionEvent e) {
        borrarFilas();
        cargarTabla();
    }

    private void btnIngresarActionPerformance(ActionEvent e) {

        FrmRolCTR ingresar = new FrmRolCTR(desktop, new FrmRol(), new RolBD(), "Agregar");
        ingresar.Init();

    }

    private void btnEditarActionPerformance(ActionEvent e) {

        int fila = vista.getTabRoles().getSelectedRow();

        if (fila != -1) {

            getObjFromRow(fila);

            if (modelo.getNombre().equals("ROOT")) {

                JOptionPane.showMessageDialog(vista, "NO SE PUEDE EDITAR EL ROL: " + " 'ROOT'");

            } else {

                FrmRolCTR editar = new FrmRolCTR(desktop, new FrmRol(), modelo, "Editar");
                editar.Init();
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

                borrarFilas();
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
            FrmAccesosDeRolCTR permisos = new FrmAccesosDeRolCTR(desktop, new FrmAccesosDeRol(), new AccesosDelRolBD(), modelo, "Consultar");
            permisos.Init();

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

                FrmAccesosDeRolCTR permisos = new FrmAccesosDeRolCTR(desktop, new FrmAccesosDeRol(), new AccesosDelRolBD(), modelo, "Editar");
                permisos.Init();

            }

        } else {
            JOptionPane.showMessageDialog(desktop, "SELECCIONE UNA FILA!!");
        }
    }

}
