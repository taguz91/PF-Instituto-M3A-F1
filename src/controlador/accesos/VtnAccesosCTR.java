package controlador.accesos;

import controlador.Libraries.Effects;
import controlador.Libraries.Effects;
import controlador.notas.VtnActivarNotasCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.accesos.AccesosBD;
import modelo.accesos.AccesosMD;
import vista.accesos.FrmAccesosEditar;
import vista.accesos.VtnAccesos;
import vista.principal.VtnPrincipal;

public class VtnAccesosCTR {

    private VtnPrincipal desktop;
    private VtnAccesos vista;
    private AccesosBD modelo;
    private final ConectarDB conecta;

    private final VtnPrincipalCTR ctrPrin;
    private String Pk;

    //Listas
    private List<AccesosMD> listaAccesosbd;
    private boolean cargarTabla = true;

    public VtnAccesosCTR(VtnPrincipal desktop, VtnAccesos vista, ConectarDB conecta, VtnPrincipalCTR ctrPrin) {
        this.desktop = desktop;
        this.vista = vista;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
    }

    //Iniciamos Tabla
    private static DefaultTableModel tablaAccesos;

    //METODO INIT()
    public void Init() {

        tablaAccesos = (DefaultTableModel) vista.getTblAccesosDeRol().getModel();

        new Thread(() -> {
            Effects.setLoadCursor(vista);
            listaAccesosbd = AccesosBD.SelectAll();
            cargarTabla(listaAccesosbd);
            Effects.setDefaultCursor(vista);
        }).start();

        try {
            vista.show();
            desktop.getDpnlPrincipal().add(vista);
            Effects.centerFrame(vista, desktop.getDpnlPrincipal());
            vista.setSelected(true);

        } catch (PropertyVetoException ex) {
            Logger.getLogger(VtnActivarNotasCTR.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        InitEventos();

    }

    //ESCUCHADORES DE EVENTOS
    private void InitEventos() {

        vista.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                txtBuscarKeyReleased(e);
            }
        });
        
        vista.getBtnEditar().addActionListener(e-> btnEditarActonPerformance(e));

    }

    //METODOS DE APOYO
    public void cargarTabla(List<AccesosMD> lista) {
        new Thread(() -> {
            if (cargarTabla) {

                Effects.setLoadCursor(vista);

                cargarTabla = false;

                desktop.getLblEstado().setText("CARGANDO...");

                tablaAccesos.setRowCount(0);

                lista.stream().forEach(VtnAccesosCTR::agregarFila);

                vista.getLblResultados().setText(lista.size() + " Resultados");

                cargarTabla = true;

                Effects.setDefaultCursor(vista);

                Effects.setTextInLabel(desktop.getLblEstado(), "COMPLETADO", 2);
            }

        }).start();
    }

    private static void agregarFila(AccesosMD obj) {
        tablaAccesos.addRow(new Object[]{
            tablaAccesos.getDataVector().size() + 1,
            obj.getIdAccesos(),
            obj.getNombre()

        });
    }

    private void cargarTablaFilter(String aguja) {

        tablaAccesos.setRowCount(0);

        List<AccesosMD> listaTemporal = listaAccesosbd
                .stream()
                .filter(item ->
                        item.getNombre().toUpperCase().contains(aguja.toUpperCase())
                )
                .collect(Collectors.toList());

        listaTemporal.forEach(VtnAccesosCTR::agregarFila);
        vista.getLblResultados().setText(listaTemporal.size() + " Registros");
    }
    
    private void setObjFromTable(int fila){
        
        listaAccesosbd = AccesosBD.SelectAll();
        
        String acceso = (String) vista.getTblAccesosDeRol().getValueAt(fila, 2);
        
        modelo = new AccesosBD();
        
        listaAccesosbd
                .stream()
                .filter(item -> item.getNombre().equals(acceso))
                .collect(Collectors.toList())
                .forEach(obj->{
                    modelo.setNombre(obj.getNombre());
                    modelo.setDescripcion(obj.getDescripcion());
                });              
    }

    //EVENTOS
    private void txtBuscarKeyReleased(KeyEvent e) {
        cargarTablaFilter(vista.getTxtBuscar().getText());
    }
    
    private void btnEditarActonPerformance(ActionEvent e){
        
        int fila = vista.getTblAccesosDeRol().getSelectedRow();
        
        if (fila != -1) {
            setObjFromTable(fila);           
            FrmAccesosEditarCTR form = new FrmAccesosEditarCTR(desktop, new FrmAccesosEditar(), modelo, conecta);
            form.Init();
        }else{
            JOptionPane.showMessageDialog(vista, "SELECCIONE UNA FILA!!");
        }if (modelo.editar(Pk)) {
                JOptionPane.showMessageDialog(desktop, "SE HA EDITADO EL ACCESO CORRECTAMENTE");
                vista.dispose();
            }else{
                JOptionPane.showMessageDialog(desktop, "DATOS NO EDITADOS");
            }
             
    }

}
