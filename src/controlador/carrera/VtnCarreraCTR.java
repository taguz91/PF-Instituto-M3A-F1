package controlador.carrera;

import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.estilo.TblEstilo;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import vista.carrera.FrmCarrera;
import vista.carrera.VtnCarrera;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnCarreraCTR {

    private final VtnPrincipal vtnPrin;
    private final VtnCarrera vtnCarrera;
    private final ConectarDB conecta;
    private final VtnPrincipalCTR ctrPrin;

    private final CarreraBD car;
    ArrayList<CarreraMD> carreras;

    DefaultTableModel mdTbl;

    public VtnCarreraCTR(VtnPrincipal vtnPrin, VtnCarrera vtnCarrera, ConectarDB conecta, VtnPrincipalCTR ctrPrin) {
        this.vtnPrin = vtnPrin;
        this.vtnCarrera = vtnCarrera;
        this.conecta = conecta;
        this.car = new CarreraBD(conecta);
        this.ctrPrin = ctrPrin;
        //Cambiamos el estado del cursos  
        vtnPrin.setCursor(new Cursor(3));
        ctrPrin.estadoCargaVtn("Carreras");

        vtnPrin.getDpnlPrincipal().add(vtnCarrera);
        vtnCarrera.show();
    }

    public void iniciar() {
        String titutlo[] = {"id", "Codigo", "Nombre", "Fecha Inicio", "Modalidad", "Coordinador"};
        String datos[][] = {};
        mdTbl = TblEstilo.modelTblSinEditar(datos, titutlo);
        vtnCarrera.getTblMaterias().setModel(mdTbl);
        TblEstilo.formatoTbl(vtnCarrera.getTblMaterias());
        TblEstilo.ocualtarID(vtnCarrera.getTblMaterias());
        TblEstilo.columnaMedida(vtnCarrera.getTblMaterias(), 1, 50);
        TblEstilo.columnaMedida(vtnCarrera.getTblMaterias(), 3, 90);
        TblEstilo.columnaMedida(vtnCarrera.getTblMaterias(), 4, 90);

        cargarCarreras();
        //Le damos accion al btn editar  
        vtnCarrera.getBtnIngresar().addActionListener(e -> abrirFrmCarrera());
        vtnCarrera.getBtnEditar().addActionListener(e -> editarCarrera());
        vtnCarrera.getBtnReporteCarreras().addActionListener(e -> llamaReporteCarreras());
        vtnCarrera.getBtnReporteAlumnoCarrera().addActionListener(e -> llamaReporteAlumnoCarrera());

        //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaVtnFin("Carreras");
    }

    private void editarCarrera() {
        int fila = vtnCarrera.getTblMaterias().getSelectedRow();
        if (fila >= 0) {
            FrmCarrera frmCarrera = new FrmCarrera();
            FrmCarreraCTR ctrFrmCarrera = new FrmCarreraCTR(vtnPrin, frmCarrera, conecta, ctrPrin);
            ctrFrmCarrera.iniciar();
            ctrFrmCarrera.editar(carreras.get(fila));
        }
    }

    private void abrirFrmCarrera() {
        FrmCarrera frmCarrera = new FrmCarrera();
        FrmCarreraCTR ctrFrmCarrera = new FrmCarreraCTR(vtnPrin, frmCarrera, conecta, ctrPrin);
        ctrFrmCarrera.iniciar();
    }

    public void cargarCarreras() {
        carreras = car.cargarCarreras();
        llenarTbl(carreras);
    }

    public void llenarTbl(ArrayList<CarreraMD> carreras) {
        mdTbl.setRowCount(0);
        if (carreras != null) {
            carreras.forEach((c) -> {
                Object valores[] = {};
                if (c.getCoordinador() == null) {
                    Object valoresSD[] = {c.getId(), c.getCodigo(), c.getNombre(),
                        c.getFechaInicio(), c.getModalidad(), "SIN COORDINADOR "};
                    mdTbl.addRow(valoresSD);
                } else {
                    System.out.println(c.getCoordinador().getPrimerApellido());
                    Object valoresCD[] = {c.getId(), c.getCodigo(), c.getNombre(),
                        c.getFechaInicio(), c.getModalidad(), c.getCoordinador().getPrimerApellido()
                        + " " + c.getCoordinador().getSegundoApellido() + " "
                        + c.getCoordinador().getPrimerNombre() + " " + c.getCoordinador().getSegundoNombre()};
                    mdTbl.addRow(valoresCD);
                }

            });
        }
    }

    public void llamaReporteCarreras() {

        JasperReport jr = null;
        String path = "./src/vista/reportes/repCarreras.jasper";
        File dir = new File("./");
        System.out.println("Direccion: " + dir.getAbsolutePath());
        try {
            Map parametro = new HashMap();
            parametro.put("carreras", vtnCarrera.getTblMaterias().getSelectedRow() + 1);
            System.out.println(parametro);
            jr = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint print = JasperFillManager.fillReport(jr, parametro, conecta.getConecction());
            JasperViewer view = new JasperViewer(print, false);
            view.setVisible(true);
            view.setTitle("Reporte de Materias por Carrera");

        } catch (JRException ex) {
            Logger.getLogger(VtnCarreraCTR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void llamaReporteAlumnoCarrera(){

        JasperReport jr = null;
        String path = "./src/vista/reportes/repAlumnosCarrera.jasper";
        File dir = new File("./");
        System.out.println("Direccion: " + dir.getAbsolutePath());
        try {
            Map parametro = new HashMap();
            parametro.put("carreras", vtnCarrera.getTblMaterias().getSelectedRow() + 1);
            System.out.println(parametro);
            jr = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint print = JasperFillManager.fillReport(jr, parametro, conecta.getConecction());
            JasperViewer view = new JasperViewer(print, false);
            view.setVisible(true);
            view.setTitle("Reporte de Materias por Carrera");

        } catch (JRException ex) {
            Logger.getLogger(VtnCarreraCTR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
