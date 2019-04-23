package controlador.accesos;

import controlador.Libraries.Middlewares;
import controlador.notas.VtnActivarNotasCTR;
import controlador.principal.VtnPrincipalCTR;
import java.beans.PropertyVetoException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.accesos.AccesosBD;
import modelo.accesos.AccesosMD;
import vista.accesos.VtnAccesos;
import vista.principal.VtnPrincipal;

public class VtnAccesosCTR {

    private VtnPrincipal desktop;
    private VtnAccesos vista;
    private AccesosBD modelo;
    private final ConectarDB conecta;
    
    private final VtnPrincipalCTR ctrPrin;

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
            Middlewares.setLoadCursor(vista);
            listaAccesosbd = AccesosBD.SelectAll();
            cargarTabla(listaAccesosbd);
            Middlewares.setDefaultCursor(vista);
        }).start();

        try {
            vista.show();
            desktop.getDpnlPrincipal().add(vista);
            Middlewares.centerFrame(vista, desktop.getDpnlPrincipal());
            vista.setSelected(true);
            
        } catch (PropertyVetoException ex) {
            Logger.getLogger(VtnActivarNotasCTR.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    //ESCUCHADORES DE EVENTOS
    
    
    
    //METODOS DE APOYO
    
    public void cargarTabla(List <AccesosMD> lista){
        new Thread(() -> {
            if (cargarTabla) {

                Middlewares.setLoadCursor(vista);

                cargarTabla = false;

                desktop.getLblEstado().setText("CARGANDO...");

                tablaAccesos.setRowCount(0);

                lista.stream().forEach(VtnAccesosCTR::agregarFila);
                
                vista.getLblResultados().setText(lista.size() + " Resultados");

                cargarTabla = true;

                Middlewares.setDefaultCursor(vista);

               Middlewares.setTextInLabel(desktop.getLblEstado(), "COMPLETADO", 2);
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
    
    
    
    //EVENTOS
}