package controlador.silabo;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import modelo.ConexionBD;
import modelo.silabo.SilaboBD;
import modelo.silabo.SilaboMD;
import modelo.validaciones.Validar;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import vista.principal.VtnPrincipal;
import vista.silabos.frmCargando1;
import vista.silabos.VtnSilabos;

/**
 *
 * @author Andres Ullauri
 */
public class ControladorSilaboR {
    /*
    private SilaboMD silabo;

    private ConexionBD conexion;

    private VtnSilabos crud;

    private VtnPrincipal principal;

    public ControladorSilaboR(VtnSilabos crud, SilaboMD silabo, ConexionBD conexion, VtnPrincipal principal) {
        this.silabo = silabo;
        this.conexion = conexion;
        this.crud = crud;
        this.principal = principal;
    }

    public ControladorSilaboR() {
    }

    public void iniciarControlador() {
        
        crud.getBtnGenerar().addActionListener(e -> ejecutar(e));

        crud.getChbProgramaAnalitico().addActionListener((ActionEvent ae) -> {
            crud.getChbSilabo().setSelected(false);
            crud.getChxDualSemanas().setSelected(false);
        });

        crud.getChbSilabo().addActionListener((ActionEvent ae) -> {
            crud.getChbProgramaAnalitico().setSelected(false);
            crud.getChxDualSemanas().setSelected(false);
        });

        crud.getChxDualSemanas().addActionListener((ActionEvent e) -> {
            crud.getChbSilabo().setSelected(false);
            crud.getChbProgramaAnalitico().setSelected(false);
        });
         
    }



    private void existeCarpeta(File pdf, JasperPrint jasPDF) {
        File carpeta = new File("pdfs/");
        if (!carpeta.exists()) {
            if (carpeta.mkdir()) {
                JOptionPane.showMessageDialog(null, "Creamos la carpeta pdfs en la que\n"
                        + "se guardaran los documentos.");
            }
        }

        try {
            OutputStream output = new FileOutputStream(pdf);
            JasperExportManager.exportReportToPdfStream(jasPDF, output);
            FileInputStream fis = new FileInputStream(pdf);
            SilaboBD.guardarSilabo(conexion, fis, pdf, silabo);
        } catch (FileNotFoundException | JRException e) {
            JOptionPane.showMessageDialog(null, "Error guardar PDF: " + e);
        }
    }

    private void imprimirProgramaAnalitico() {
        try {
            JasperReport jr = (JasperReport) JRLoader.loadObject(getClass()
                    .getResource("/vista/silabos/reportes/silabo_duales/primera_pag.jasper"));

            Map parametro = new HashMap();
            parametro.put("parameter1", String.valueOf(silabo.getMateria().getId()));
            parametro.put("id_silabo", String.valueOf(silabo.getID()));
            JasperPrint jp = JasperFillManager.fillReport(jr, parametro, conexion.getCon());
            JasperViewer pv = new JasperViewer(jp, false);
            pv.setVisible(true);
            pv.setTitle("Silabo Duales");

            File pdf = new File(("pdfs/" + "SD-" + silabo.getMateria().getNombre() + "-" + LocalDate.now() + ".pdf"));
            existeCarpeta(pdf, jp);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }
    }

    private void imprimirProgramaAnaliticoConSemanas() {

        String semanas = JOptionPane.showInputDialog("Escriba el numero de semanas");;

        if (semanas != null) {
            int numSemanas = 0;
            if (Validar.esNumeros(semanas)) {
                numSemanas = Integer.parseInt(semanas);
                if (numSemanas >= 6) {
                    try {
                        JasperReport jr = (JasperReport) JRLoader.loadObject(getClass()
                                .getResource("/vista/silabos/"
                                        + "reportes/silabo_duales/"
                                        + "primera_pag_param_semanas.jasper")
                        );

                        Map parametro = new HashMap();
                        parametro.put("parameter1", String.valueOf(silabo.getMateria().getId()));
                        parametro.put("id_silabo", String.valueOf(silabo.getID()));
                        parametro.put("num_semanas", numSemanas);

                        JasperPrint jp = JasperFillManager.fillReport(jr, parametro, conexion.getCon());
                        JasperViewer pv = new JasperViewer(jp, false);
                        pv.setVisible(true);
                        pv.setTitle("Silabos Duales | Semanas");

                        File pdf = new File(("pdfs/" + "SD-" + silabo.getMateria().getNombre() + "-" + LocalDate.now() + ".pdf"));
                        existeCarpeta(pdf, jp);
                    } catch (JRException e) {
                        JOptionPane.showMessageDialog(null, "Error: " + e);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Debe indicar mas de seis semanas de clases por periodo ");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Solo puede ingresar numeros.");
            }
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

                boolean select = false;

                if (crud.getChbSilabo().isSelected()) {
                    imprimirSilabo();
                }

                if (crud.getChbProgramaAnalitico().isSelected()) {
                    imprimirProgramaAnalitico();
                }

                if (crud.getChxDualSemanas().isSelected()) {
                    imprimirProgramaAnaliticoConSemanas();
                }

                if (select) {
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
     */
}
