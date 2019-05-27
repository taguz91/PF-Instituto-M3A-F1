/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.ConexionBD;
import modelo.silabo.SilaboBD;

import modelo.silabo.SilaboMD;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import vista.principal.VtnPrincipal;
import vista.silabos.frmCargando1;
import vista.silabos.frmSilabos;

/**
 *
 * @author Andres Ullauri
 */
public class ControladorSilaboR {

    private SilaboMD silabo;

    private ConexionBD conexion;

    private frmSilabos crud;

    private VtnPrincipal principal;

    public ControladorSilaboR(frmSilabos crud, SilaboMD silabo, ConexionBD conexion, VtnPrincipal principal) {
        this.silabo = silabo;
        this.conexion = conexion;
        this.crud = crud;
        this.principal = principal;
    }

    public ControladorSilaboR() {
    }

    public void iniciarControlador() {

        crud.getBtnGenerar().addActionListener(e -> ejecutar(e));

        crud.getChbProgramaAnalitico().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                crud.getChbSilabo().setSelected(false);
            }

        });

        crud.getChbSilabo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                crud.getChbProgramaAnalitico().setSelected(false);
            }

        });

    }

    public void imprimirSilabo() {
        try {

            System.out.println("Imprimiendo.......");
            JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/silabos/reportes/silabo2/primera_pag.jasper"));
            Map parametro = new HashMap();
            String par = "47";

            parametro.put("parameter1", String.valueOf(silabo.getIdMateria().getId()));
            parametro.put("id_silabo", String.valueOf(silabo.getIdSilabo()));
            JasperPrint jp = JasperFillManager.fillReport(jr, parametro, conexion.getCon());
            JasperViewer pv = new JasperViewer(jp, false);
            pv.setVisible(true);
            pv.setTitle("Sílabo");

            existeCarpeta();

            //EXPORTACION A PDF
            File f = new File(("pdfs/" + "SA-" + silabo.getIdMateria().getNombre() + "-" + LocalDate.now() + ".pdf"));
            OutputStream output = new FileOutputStream(f);
            JasperExportManager.exportReportToPdfStream(jp, output);
            //byte[] d=JasperExportManager.exportReportToPdf(jp);
            FileInputStream fis = new FileInputStream(f);
            SilaboBD.guardarSilabo(conexion, fis, f, silabo);
            System.out.println("Se guardo pdf");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "error " + e);
        }

    }

    private void existeCarpeta() {
        File carpeta = new File("pdfs/");
        if (!carpeta.exists()) {
            if (carpeta.mkdir()) {
                JOptionPane.showMessageDialog(null, "Creamos la carpeta pdfs en la que\n"
                        + "se guardaran los documentos.");
            }
        }
    }

    public void imprimirProgramaAnalitico() {
        try {

            System.out.println("Imprimiendo.......");
           //JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/silabos/reportes/silabo2/formato2/primerapag.jasper"));
              JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/silabos/reportes/silabo_duales/primera_pag.jasper"));

           Map parametro = new HashMap();
            String par = "47";

            parametro.put("parameter1", String.valueOf(silabo.getIdMateria().getId()));
            parametro.put("id_silabo", String.valueOf(silabo.getIdSilabo()));
            JasperPrint jp = JasperFillManager.fillReport(jr, parametro, conexion.getCon());
            JasperViewer pv = new JasperViewer(jp, false);
            pv.setVisible(true);
            pv.setTitle("Silabo Duales");

            //EXPORTACION A PDF
            File f = new File(("../PF-Instituto-M3A-F1/pdfs/" + "SA-" + silabo.getIdMateria().getNombre() + "-" + LocalDate.now() + ".pdf"));
            OutputStream output = new FileOutputStream(f);
            JasperExportManager.exportReportToPdfStream(jp, output);
            //byte[] d=JasperExportManager.exportReportToPdf(jp);
            FileInputStream fis = new FileInputStream(f);
            SilaboBD.guardarSilabo(conexion, fis, f, silabo);
            System.out.println("Se guardo pdf");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, " error " + e);
        }
    }
      public void imprimirProgramaAnalitico1() {
        try {

            System.out.println("Imprimiendo.......");
           JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/silabos/reportes/silabo2/formato2/primerapag.jasper"));
              //JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/silabos/reportes/silabo_duales/primera_pag.jasper"));

           Map parametro = new HashMap();
            String par = "47";

            parametro.put("parameter1", String.valueOf(silabo.getIdMateria().getId()));
            parametro.put("id_silabo", String.valueOf(silabo.getIdSilabo()));
            JasperPrint jp = JasperFillManager.fillReport(jr, parametro, conexion.getCon());
            JasperViewer pv = new JasperViewer(jp, false);
            pv.setVisible(true);
            pv.setTitle("Programa Analítico");

            existeCarpeta();
            //EXPORTACION A PDF
            File fl = new File(("pdfs/" + "PA-" + silabo.getIdMateria().getNombre() + "-" + LocalDate.now() + ".pdf"));
            OutputStream output = new FileOutputStream(fl);
            JasperExportManager.exportReportToPdfStream(jp, output);
            //byte[] d=JasperExportManager.exportReportToPdf(jp);
            FileInputStream fis1 = new FileInputStream(fl);
            SilaboBD.guardarAnalitico(conexion, fis1, fl, silabo);
            System.out.println("Se guardo pdf");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, " error " + e);
        }
    }

    private boolean accion = true;

    private void ejecutar(ActionEvent e) {
        if (accion) {
            new Thread(() -> {
                accion = false;

                principal.setEnabled(false);

                frmCargando1 frmCargando1 = new frmCargando1();

                frmCargando1.setVisible(true);

                if (crud.getChbSilabo().isSelected()) {
                    imprimirSilabo();

                } else if (crud.getChbProgramaAnalitico().isSelected()) {
                    imprimirProgramaAnalitico();

                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar el documento antes de imprimir");
                }

                accion = true;

                principal.setEnabled(true);

                frmCargando1.dispose();

                crud.dispose();

                principal.getBtnConsultarSilabo().doClick();

            }).start();

        }

    }
}
