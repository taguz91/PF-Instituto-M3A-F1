package controlador.usuario;

import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.estilo.TblEstilo;
import modelo.usuario.HistorialUsuarioBD;
import modelo.usuario.HistorialUsuarioMD;
import vista.principal.VtnPrincipal;
import vista.usuario.VtnHistorialUsuarios;

/**
 *
 * @author Johnny
 */
public class VtnHistorialUserCTR {

    private final ConectarDB conecta;
    private final VtnHistorialUsuarios vtnH;
    private final VtnPrincipal vtnPrin;
    private final VtnPrincipalCTR ctrPrin;
    private final HistorialUsuarioBD his;
    private ArrayList<HistorialUsuarioMD> historial;
    //Para cargar los combos 
    private ArrayList<String> tablas;
    private ArrayList<String> acciones;
    private ArrayList<String> usuarios;
    private ArrayList<String> fechaIni;
    private ArrayList<String> fechaFin;

    //Modelo de la tabla 
    private DefaultTableModel mdTbl;
    //Para guardar las posiciones de los combos seleccionados
    private int posTbl, posAcc, posUser, posFI, posFF;

    public VtnHistorialUserCTR(ConectarDB conecta, VtnPrincipal vtnPrin, VtnPrincipalCTR ctrPrin) {
        this.conecta = conecta;
        this.vtnPrin = vtnPrin;
        this.ctrPrin = ctrPrin;
        //Cambiamos el estado del cursos  
        vtnPrin.setCursor(new Cursor(3));
        ctrPrin.estadoCargaVtn("Historial usuarios.");

        this.vtnH = new VtnHistorialUsuarios();
        vtnPrin.getDpnlPrincipal().add(vtnH);
        vtnH.show();
        this.his = new HistorialUsuarioBD(conecta);
        ctrPrin.setIconJIFrame(vtnH);
    }

    public void iniciar() {
        cargarCmbAcciones();
        cargarCmbUsuarios();
        cargarCmbTablas();
        cargarCmbFechas();
        //Le damos el modelo a la tabla  
        String[] titulo = {"Usuario", "Tabla", "Accion", "PK", "Fecha"};
        String[][] datos = {};
        mdTbl = TblEstilo.modelTblSinEditar(datos, titulo);
        vtnH.getTblHistorial().setModel(mdTbl);
        TblEstilo.formatoTbl(vtnH.getTblHistorial());
        TblEstilo.columnaMedida(vtnH.getTblHistorial(), 2, 80);
        TblEstilo.columnaMedida(vtnH.getTblHistorial(), 3, 50);
        //Buscamos el historial de hoy  
        cargarHistorialHoy();
        //Acciones de los combos 
        vtnH.getCmbUsers().addActionListener(e -> clickCmbUsers());
        vtnH.getCmbTablas().addActionListener(e -> clickCmbTbl());
        vtnH.getCmbAccion().addActionListener(e -> clickCmbAccion());
        vtnH.getCmbFechaIni().addActionListener(e -> clickFechaIni());
        vtnH.getCmbFechaFin().addActionListener(e -> clickFechaFin());
        //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaVtnFin("Historial usuarios.");
    }
    
    private void clickFechaIni(){
        posFI = vtnH.getCmbFechaIni().getSelectedIndex();
        if (posFI > 0) {
            historial = his.cargarHistorialFecha(fechaIni.get(posFI - 1)); 
            llenarTbl(historial);
            llenarCmbFechaFin();
        }else{
            cargarHistorialHoy();
        }
    }
    
    private void clickFechaFin(){
        posFF = vtnH.getCmbFechaFin().getSelectedIndex(); 
        if (posFF > 0) {
            historial = his.cargarHistorialEntresFechas(fechaIni.get(posFI - 1), 
                    fechaFin.get(posFF - 1));
            llenarTbl(historial);
        }else{
            clickFechaIni();
        }
    }
    
    private void clickCmbUsers(){
        posUser = vtnH.getCmbUsers().getSelectedIndex(); 
        if (posUser > 0) {
            historial = his.cargarHistorialUser(usuarios.get(posUser - 1)); 
            llenarTbl(historial);
        }else{
            cargarHistorialHoy();
        }
    }
    
    private void clickCmbAccion(){
        posAcc = vtnH.getCmbAccion().getSelectedIndex();
        if (posAcc > 0) {
            historial = his.cargarHistorialAccion(acciones.get(posAcc -1)); 
            llenarTbl(historial);
        }else{
            cargarHistorialHoy();
        }
    }
    
    private void clickCmbTbl(){
        posTbl = vtnH.getCmbTablas().getSelectedIndex();
        if (posTbl > 0) {
            historial = his.cargarHistorialTbl(tablas.get(posTbl - 1));
            llenarTbl(historial);
        }else{
            cargarHistorialHoy();
        }
    }
    
    private void cargarHistorialHoy(){
        historial = his.cargarHistorialHoy();
        llenarTbl(historial);
    }

    private void cargarCmbFechas() {
        System.out.println("Se cargaran fechas");
        fechaIni = his.cargarFechas();
        llenarCmbFechaIni(fechaIni);
    }
    
    private void llenarCmbFechaFin() {
        vtnH.getCmbFechaFin().removeAllItems();
        //Llenamos el combo desde las fechas que esta
        //Seleccionada en el combo fecha inicio
        fechaFin = new ArrayList();
        for (int i = 0; i < fechaIni.size(); i++) {
            if (fechaIni.get(i).compareTo(fechaIni.get(posFI - 1)) > 0) {
                fechaFin.add(fechaIni.get(i));
            }
        }
        //Al final removemos el ultimo porque se carga desde el seleecionado 
        fechaFin.remove(fechaIni.get(posFI - 1));
        
        if (!fechaFin.isEmpty()) {
            vtnH.getCmbFechaFin().addItem("Seleccione");
            fechaFin.forEach(f -> {
                vtnH.getCmbFechaFin().addItem(f);
            });
            vtnH.getCmbFechaFin().setSelectedIndex(0);
        }
    }

    private void llenarCmbFechaIni(ArrayList<String> fechas) {
        vtnH.getCmbFechaIni().removeAllItems();
        if (!fechas.isEmpty()) {
            vtnH.getCmbFechaIni().addItem("Seleccione");
            fechas.forEach(f -> {
                vtnH.getCmbFechaIni().addItem(f);
            });
            vtnH.getCmbFechaIni().setSelectedIndex(0);
        }
    }

    private void cargarCmbTablas() {
        tablas = his.cargarTablas();
        llenarCmbTablas(tablas);
    }

    private void llenarCmbTablas(ArrayList<String> tablas) {
        vtnH.getCmbTablas().removeAllItems();
        if (!tablas.isEmpty()) {
            vtnH.getCmbTablas().addItem("Seleccione");
            tablas.forEach(t -> {
                vtnH.getCmbTablas().addItem(t);
            });
            vtnH.getCmbTablas().setSelectedIndex(0);
        }
    }

    private void cargarCmbUsuarios() {
        usuarios = his.cargarUsuarios();
        llenarCmbUsuarios(usuarios);
    }

    private void llenarCmbUsuarios(ArrayList<String> usuarios) {
        vtnH.getCmbUsers().removeAllItems();
        if (!usuarios.isEmpty()) {
            vtnH.getCmbUsers().addItem("Seleccione");
            usuarios.forEach(u -> {
                vtnH.getCmbUsers().addItem(u);
            });
            vtnH.getCmbUsers().setSelectedIndex(0);
        }
    }

    private void cargarCmbAcciones() {
        acciones = his.cargarAcciones();
        llenarCmbAcciones(acciones);
    }

    private void llenarCmbAcciones(ArrayList<String> acciones) {
        vtnH.getCmbAccion().removeAllItems();
        if (!acciones.isEmpty()) {
            vtnH.getCmbAccion().addItem("Seleccione");
            acciones.forEach(a -> {
                vtnH.getCmbAccion().addItem(a);
                System.out.println(a);
            });
            vtnH.getCmbAccion().setSelectedIndex(0);
        }
        System.out.println("Se cargaron acciones");
    }

    private void llenarTbl(ArrayList<HistorialUsuarioMD> historial) {
        mdTbl.setRowCount(0);
        System.out.println("Historia "+historial.size());
        if (!historial.isEmpty()) {
            historial.forEach(h -> {
                Object[] v = {h.getUsername(), h.getNombreTbl(), h.getTipoAccion(),
                    h.getPkTbl(), h.getFechaAccion()};
                mdTbl.addRow(v);
            });
            vtnH.getLblResultados().setText(historial.size() + " Resultados obtenidos.");
        }
        vtnH.getLblResultados().setText(historial.size() + " Resultados obtenidos.");
    }

}
