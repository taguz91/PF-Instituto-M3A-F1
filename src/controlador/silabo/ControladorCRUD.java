/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Optional;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.ConexionBD;
import modelo.carrera.CarreraMD;
import modelo.silabo.CarrerasBDS;

import modelo.silabo.SilaboMD;
import modelo.silabo.SilaboBD;
import modelo.usuario.UsuarioBD;
import vista.principal.VtnPrincipal;

import vista.silabos.frmSilabos;

/**
 *
 * @author Andres Ullauri
 */
public class ControladorCRUD {

    private SilaboBD silabo;

    private final UsuarioBD usuario;

    private frmSilabos crud;

    //private frmGestionSilabo gestion;
    //private frmConfiguracionSilabo configuracion;
    private ConexionBD conexion;

    private final VtnPrincipal principal;

    private List<SilaboMD> silabosDocente;

    public ControladorCRUD(UsuarioBD usuario, VtnPrincipal principal, ConexionBD conexion) {
        this.usuario = usuario;
        this.principal = principal;
        this.conexion = conexion;

    }

    public void iniciarControlador() {

        conexion.conectar();

        crud = new frmSilabos();

        principal.getDpnlPrincipal().add(crud);

        crud.setTitle("Silabos");

        crud.show();

        crud.setLocation((principal.getDpnlPrincipal().getSize().width - crud.getSize().width) / 2,
                (principal.getDpnlPrincipal().getSize().height - crud.getSize().height) / 2);
        
        opcionesImpresion(false);
        // Boton NUEVO Silabo
        crud.getBtnNuevo().addActionListener((ActionEvent ae) -> {
            
            crud.dispose();

            ControladorSilaboC csc = new ControladorSilaboC(principal, usuario, conexion);

            csc.iniciarControlador();
        });

        // Boton EDITAR Silabo
        crud.getBtnEditar().addActionListener((ActionEvent ae) -> {

            crud.dispose();

            ControladorSilaboU csu = new ControladorSilaboU(seleccionarSilabo(), principal, conexion);

            csu.iniciarControlador();
        });

        // Boton ELIMINAR Silabo
        crud.getBtnEliminar().addActionListener((ActionEvent ae) -> {

            eliminarSilabo();
            cargarSilabosDocente();

        });

        crud.getBtnImprimir().addActionListener((ActionEvent ae) -> {
            
            
            //VALIDA QUE SELECCIONE UN SILABO E IMPRIMA
            int row =crud.getTblSilabos().getSelectedRow();
            
            if (row!=-1) {
                
            opcionesImpresion(true);

            ControladorSilaboR csr = new ControladorSilaboR(crud, seleccionarSilabo(), conexion, principal);

            csr.iniciarControlador();
            } else {
                JOptionPane.showMessageDialog(principal, "Seleccione un silabo");
            }

        });

        crud.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {

                cargarSilabosDocente();

            }

        });

        crud.getCmbCarrera().addActionListener(al -> cargarSilabosDocente());

        cargarComboCarreras();

        cargarSilabosDocente();

    }

    public List<CarreraMD> cargarComboCarreras() {

        List<CarreraMD> carrerasDocente = CarrerasBDS.consultar(conexion, usuario.getUsername());

        carrerasDocente.forEach((cmd) -> {
            crud.getCmbCarrera().addItem(cmd.getNombre());
        });

        return carrerasDocente;
    }

    public void cargarSilabosDocente() {
        try {

            DefaultTableModel modeloTabla;

            modeloTabla = (DefaultTableModel) crud.getTblSilabos().getModel();

            String[] parametros = {crud.getCmbCarrera().getSelectedItem().toString(), crud.getTxtBuscar().getText(), String.valueOf(usuario.getPersona().getIdPersona())};
            //

            silabosDocente = SilaboBD.consultar(conexion, parametros);

            for (int j = crud.getTblSilabos().getModel().getRowCount() - 1; j >= 0; j--) {

                modeloTabla.removeRow(j);
            }

            for (SilaboMD smd : silabosDocente) {

                String estado = null;
                if (smd.getEstadoSilabo() == 0) {
                    estado = "Por aprobar";
                }

                modeloTabla.addRow(new Object[]{
                    smd.getIdMateria().getNombre(),
                    smd.getIdPeriodoLectivo().getFecha_Inicio() + " / " + smd.getIdPeriodoLectivo().getFecha_Fin(),
                    estado,
                    smd.getIdSilabo()});

            }

            crud.getTblSilabos().setModel(modeloTabla);

        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "Usted no tiene carreras asignadas en el presente periodo", "Aviso", JOptionPane.ERROR_MESSAGE);
            crud.dispose();    
            
        }

    }

    public SilaboMD seleccionarSilabo() {

        int seleccion = crud.getTblSilabos().getSelectedRow();
        
        Optional<SilaboMD> silaboSeleccionado = silabosDocente.stream().
                filter(s -> s.getIdSilabo()==Integer.parseInt(crud.getTblSilabos().getValueAt(seleccion, 3).toString())).
                findFirst();

        return silaboSeleccionado.get();
    }

    public void opcionesImpresion(boolean estado) {

        crud.getLblSeleccionDocumento().setVisible(estado);
        crud.getChbProgramaAnalitico().setVisible(estado);
        crud.getChbSilabo().setVisible(estado);
        crud.getBtnGenerar().setVisible(estado);

    }

    public void eliminarSilabo() {

        int reply = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar este silabo?", "Eliminar", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            new SilaboBD(conexion).eliminar(seleccionarSilabo());
            JOptionPane.showMessageDialog(null, "Silabo eliminado correctamente");
        }

    }
    
    
    

}
