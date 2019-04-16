package controlador.usuario;

import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.estilo.TblEstilo;
import modelo.usuario.HistorialUsuarioBD;
import modelo.usuario.HistorialUsuarioMD;
import modelo.validaciones.Validar;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
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
    //Para el buscador
    private String b;
    //Sentencias sql que se ejecuto la ultima vez 
    private String sql;

    /**
     * Iniciamos las clases de base de datos.
     *
     * @param conecta
     * @param vtnPrin
     * @param ctrPrin
     */
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

    /**
     * Iniciamos todas las dependencias. Tabla, buscadores y cargamos combos
     */
    public void iniciar() {
        cargarCmbAcciones();
        cargarCmbUsuarios();
        cargarCmbTablas();
        cargarCmbFechas();
        //Le damos el modelo a la tabla  
        String[] titulo = {"Usuario", "Tabla", "Acción", "PK", "Fecha"};
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

        //Iniciamos el buscados 
        vtnH.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                b = vtnH.getTxtBuscar().getText().trim();
                if (b.length() > 2) {
                    buscar(b);
                } else if (b.length() == 0) {
                    mdTbl.setRowCount(0);
                }
            }
        });
        vtnH.getBtnBuscar().addActionListener(e -> buscar(vtnH.getTxtBuscar().getText().trim()));
        //Accion para el reporte  
        vtnH.getBtnReporte().addActionListener(e -> llamaReporteHistorialUser());
        //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaVtnFin("Historial usuarios.");
    }

    /**
     * Buscamos en la base de datos unicamente se aceptan numeros y letras. El
     * buscador tiene una combinacion de nombre accion y cargan todos las
     * acciones que realizo esa persona y en que tablas.
     *
     * @param b String
     */
    private void buscar(String b) {
        if (Validar.esLetrasYNumeros(b)) {
            historial = his.buscar(b);
            llenarTbl(historial);
        } else {
            mdTbl.setRowCount(0);
        }
    }

    /**
     * Al dar click cargamos el historial de la fecha iniciada, y cargamos el
     * combo de fecha fin con fehcas posteriores a la elegida.
     */
    private void clickFechaIni() {
        posFI = vtnH.getCmbFechaIni().getSelectedIndex();
        if (posFI > 0) {
            llenarCmbFechaFin();
        } else {
            vtnH.getCmbFechaFin().removeAllItems();
        }
        cmbCombinados();
    }

    /**
     * Se carga el historial, entre fechas, al seleccionar una fecha fin.
     */
    private void clickFechaFin() {
        posFF = vtnH.getCmbFechaFin().getSelectedIndex();
        cmbCombinados();
    }

    /**
     * Cargan las acciones de un usuario.
     */
    private void clickCmbUsers() {
        posUser = vtnH.getCmbUsers().getSelectedIndex();
        cmbCombinados();
    }

    /**
     * Cargamos el combo por acciones.
     */
    private void clickCmbAccion() {
        posAcc = vtnH.getCmbAccion().getSelectedIndex();
        cmbCombinados();
    }

    private void clickCmbTbl() {
        posTbl = vtnH.getCmbTablas().getSelectedIndex();
        cmbCombinados();
    }

    private void cargarHistorialHoy() {
        historial = his.cargarHistorialHoy();
        llenarTbl(historial);
        sql = his.getSql();
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
            });
            vtnH.getCmbAccion().setSelectedIndex(0);
        }
    }

    private void cmbCombinados() {
        //Iniciamos el array
        historial = new ArrayList();

        if (posUser > 0 && posFI > 0 && posFF > 0
                && posTbl > 0 && posAcc > 0) {
            //Consultamos una combinacion completa
            historial = his.cargarHistorialUserTablaAccionEntreFechas(usuarios.get(posUser - 1),
                    tablas.get(posTbl - 1), acciones.get(posAcc - 1), fechaIni.get(posFI - 1),
                    fechaFin.get(posFF - 1));
        } else if (posUser > 0 && posFI > 0
                && posAcc > 0 && posTbl > 0
                && posFF == 0) {
            //Consultamos por usuario tabla accion y fecha
            historial = his.cargarHistorialUserTablaAccionPorFecha(usuarios.get(posUser - 1),
                    tablas.get(posTbl - 1), acciones.get(posAcc - 1), fechaIni.get(posFI - 1));
        } else if (posUser > 0 && posAcc > 0
                && posTbl > 0 && posFI == 0
                && posFF == 0) {
            //Consultamos por usuario tabla y accion
            historial = his.cargarHistorialUserTablaAccion(usuarios.get(posUser - 1),
                    tablas.get(posTbl - 1), acciones.get(posAcc - 1));
        } else if (posUser > 0 && posTbl > 0
                && posAcc == 0 && posFI == 0
                && posFF == 0) {
            //Consultamos por usuario por tabla
            historial = his.cargarHistorialUserTabla(usuarios.get(posUser - 1), tablas.get(posTbl - 1));
        } else if (posUser > 0 && posTbl == 0
                && posAcc == 0 && posFI == 0
                && posFF == 0) {
            //Consultamos por usuario
            historial = his.cargarHistorialUser(usuarios.get(posUser - 1));
        } else if (posUser > 0 && posTbl == 0
                && posAcc > 0 && posFI == 0
                && posFF == 0) {
            //Consultamos por usuario y accion
            historial = his.cargarHistorialUserAccion(usuarios.get(posUser - 1), acciones.get(posAcc - 1));
        } else if (posUser > 0 && posTbl == 0
                && posAcc == 0 && posFI > 0
                && posFF == 0) {
            //Consultamos por usuario y fecha 
            historial = his.cargarHistorialUserPorFecha(usuarios.get(posUser - 1), fechaIni.get(posFI - 1));
        } else if (posUser > 0 && posTbl == 0
                && posAcc == 0 && posFI > 0
                && posFF > 0) {
            //Consultamos por usuario y fecha de inicio y fin 
            historial = his.cargarHistorialUserEntreFechas(usuarios.get(posUser - 1),
                    fechaIni.get(posFI - 1), fechaFin.get(posFF - 1));
        } else if (posUser > 0 && posTbl > 0
                && posAcc == 0 && posFI > 0
                && posFF == 0) {
            //Consultamos por usuario tabla y fecha inicio
            historial = his.cargarHistorialUserTablaPorFecha(usuarios.get(posUser - 1),
                    tablas.get(posTbl - 1), fechaIni.get(posFI - 1));
        } else if (posUser > 0 && posTbl > 0
                && posAcc == 0 && posFI > 0
                && posFF > 0) {
            //Consultamos por usuario tabla entre fechas
            historial = his.cargarHistorialUserTablaEntreFechas(usuarios.get(posUser - 1),
                    tablas.get(posTbl - 1), fechaIni.get(posFI - 1), fechaFin.get(posFF - 1));
        } else if (posUser > 0 && posTbl == 0
                && posAcc > 0 && posFI == 0
                && posFF == 0) {
            //Consultamos  por usuario y accion
            historial = his.cargarHistorialUserAccion(usuarios.get(posUser - 1),
                    acciones.get(posAcc - 1));
        } else if (posUser > 0 && posTbl == 0
                && posAcc > 0 && posFI > 0
                && posFF == 0) {
            //Consultamos por usuario accion y fecha inicio
            historial = his.cargarHistorialUserAccionPorFecha(usuarios.get(posUser - 1),
                    acciones.get(posAcc - 1), fechaIni.get(posFI - 1));
        } else if (posUser > 0 && posTbl == 0
                && posAcc > 0 && posFI > 0
                && posFF > 0) {
            //Consultamos por usuario accion entre fechas
            historial = his.cargarHistorialUserAccionEntreFechas(usuarios.get(posUser - 1),
                    acciones.get(posAcc - 1), fechaIni.get(posFI - 1), fechaFin.get(posFF - 1));
        } else if (posUser == 0 && posTbl > 0
                && posAcc == 0 && posFI == 0
                && posFF == 0) {
            //Consultamos por tabla 
            historial = his.cargarHistorialAccion(acciones.get(posAcc - 1));
        } else if (posUser == 0 && posTbl > 0
                && posAcc > 0 && posFI == 0
                && posFF == 0) {
            //Consultamos por tabla y accion
            historial = his.cargarHistorialTblAccion(tablas.get(posTbl - 1), acciones.get(posAcc - 1));
        } else if (posUser == 0 && posTbl > 0
                && posAcc > 0 && posFI > 0
                && posFF == 0) {
            //Consultamos por tabla accion y fecha
            historial = his.cargarHistorialTblAccionPoFecha(tablas.get(posTbl - 1),
                    acciones.get(posAcc - 1), fechaIni.get(posFI - 1));
        } else if (posUser == 0 && posTbl > 0
                && posAcc > 0 && posFI > 0
                && posFF > 0) {
            //Consultamos por tabla accion y entre fechas
            historial = his.cargarHistorialTblAccionEntreFecha(tablas.get(posTbl - 1),
                    acciones.get(posAcc - 1), fechaIni.get(posFI - 1), fechaFin.get(posFF - 1));
        } else if (posUser == 0 && posTbl == 0
                && posAcc > 0 && posFI == 0
                && posFF == 0) {
            //Consultamos por accion
            historial = his.cargarHistorialAccion(acciones.get(posAcc - 1));
        } else if (posUser == 0 && posTbl == 0
                && posAcc > 0 && posFI > 0
                && posFF == 0) {
            //Consultamos por accion y fecha
            historial = his.cargarHistorialAccionPorFecha(acciones.get(posAcc - 1), fechaIni.get(posFI - 1));
        } else if (posUser == 0 && posTbl == 0
                && posAcc > 0 && posFI > 0
                && posFF > 0) {
            //Consultamos por accion  y entre fechas
            historial = his.cargarHistorialAccionEntreFechas(acciones.get(posAcc - 1),
                    fechaIni.get(posFI - 1), fechaFin.get(posFF - 1));
        } else if (posUser == 0 && posTbl == 0
                && posAcc == 0 && posFI > 0
                && posFF == 0) {
            //Consultamos historial por fecha
            historial = his.cargarHistorialFecha(fechaIni.get(posFI - 1));
        } else if (posUser == 0 && posTbl == 0
                && posAcc == 0 && posFI > 0
                && posFF > 0) {
            //Consultamos historial entre fechas
            historial = his.cargarHistorialEntreFechas(fechaIni.get(posFI - 1), fechaFin.get(posFF - 1));
        } else {
            cargarHistorialHoy();
        }
        //Obtenemos la sentencia sql que se ejecuto
        sql = his.getSql();
        llenarTbl(historial);
    }

    /**
     * Llenamos la tabla
     *
     * @param historial
     */
    private void llenarTbl(ArrayList<HistorialUsuarioMD> historial) {
        mdTbl.setRowCount(0);
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

    /**
     * El reporte sera llamado con la ultima query ejecutada.
     */
    private void llamaReporteHistorialUser() {
        JasperReport jr;
        String path = "./src/vista/reportes/repHistorialUser.jasper";
        try {
            System.out.println("Cargando reporte");
            vtnH.setCursor(new Cursor(3));
            Map parametro = new HashMap();
            System.out.println(sql);
            parametro.put("consulta", sql);
            //System.out.println(parametro);
            jr = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint print = JasperFillManager.fillReport(jr, parametro, conecta.getConecction());
            JasperViewer view = new JasperViewer(print, false);
            view.setVisible(true);
            view.setTitle("Reporte de Historial Usuario");
            System.out.println("Reporte cargado");
            vtnH.setCursor(new Cursor(0));

        } catch (JRException ex) {
            System.out.println("No se pudo realizar el reporte.");
        }
    }

}
