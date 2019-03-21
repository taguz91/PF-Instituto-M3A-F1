package controlador.carrera;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.ConexionReportes;
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
    
    private final CarreraBD car;
    ArrayList<CarreraMD> carreras;

    DefaultTableModel mdTbl;

    public VtnCarreraCTR(VtnPrincipal vtnPrin, VtnCarrera vtnCarrera, ConectarDB conecta) {
        this.vtnPrin = vtnPrin;
        this.vtnCarrera = vtnCarrera;
        this.conecta = conecta;
        this.car = new CarreraBD(conecta);
        
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
        vtnCarrera.getBtnReporteCarreras().addActionListener(e -> llamaReporte());
    }

    private void editarCarrera() {
        int fila = vtnCarrera.getTblMaterias().getSelectedRow();
        if (fila >= 0) {
            FrmCarrera frmCarrera = new FrmCarrera();
            FrmCarreraCTR ctrFrmCarrera = new FrmCarreraCTR(vtnPrin, frmCarrera, conecta);
            ctrFrmCarrera.iniciar();
            ctrFrmCarrera.editar(carreras.get(fila));
        }
    }

    private void abrirFrmCarrera() {
        FrmCarrera frmCarrera = new FrmCarrera();
        FrmCarreraCTR ctrFrmCarrera = new FrmCarreraCTR(vtnPrin, frmCarrera, conecta);
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
public void llamaReporte(){
    ConexionReportes con = new ConexionReportes();
        Connection conexion = con.getConexion();
    
    //String path = "C:\\Users\\arman\\Desktop\\githubtest1\\PF-Instituto-M3A-F1\\src\\vista\\reportes\\repCarreras.jasper";
        JasperReport jr = null;
       String path = "./src/vista/reportes/repCarreras.jasper";
        File dir = new File("./");
        System.out.println("Direccion: " + dir.getAbsolutePath());
        try {
            Map parametro = new HashMap();
            parametro.put("carreras", vtnCarrera.getTblMaterias().getSelectedRow() + 1);
            System.out.println(parametro);
            jr = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint print = JasperFillManager.fillReport(jr, parametro, conexion);
            JasperViewer view = new JasperViewer(print, false);
            view.setVisible(true);
            view.setTitle(path);

        } catch (JRException ex) {
            Logger.getLogger(VtnCarreraCTR.class.getName()).log(Level.SEVERE, null, ex);
        }
}
}

