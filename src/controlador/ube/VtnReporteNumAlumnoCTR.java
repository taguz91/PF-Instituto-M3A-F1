package controlador.ube;

import controlador.principal.VtnPrincipalCTR;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.jornada.JornadaBD;
import modelo.jornada.JornadaMD;
import modelo.materia.MateriaBD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import vista.ube.VtnReporteNumAlumno;

/**
 *
 * @author gus
 */
public class VtnReporteNumAlumnoCTR {

    private final VtnPrincipalCTR ctrPrin;
    private final VtnReporteNumAlumno vtn;
    //Todos los array que usaremos en los combos  
    private ArrayList<CarreraMD> carreras;
    private ArrayList<PeriodoLectivoMD> periodos;
    private ArrayList<Integer> ciclos;
    private ArrayList<JornadaMD> jornadas;

    public VtnReporteNumAlumnoCTR(VtnPrincipalCTR ctrPrin, VtnReporteNumAlumno vtn) {
        this.ctrPrin = ctrPrin;
        this.vtn = vtn;
    }

    public void iniciar() {
        inicarCmbs();
        ctrPrin.agregarVtn(vtn);
        vtn.getBtnRepCarreraPrd().addActionListener(e -> imprimirPrimer());
        vtn.getBtnJornadaCiclo().addActionListener(e -> imprimirSeg());

    }

    private void inicarCmbs() {
        inicarCmbCarrera();
        iniciarCmbPeriodo();
        iniciarCmbJornada();
        iniciarCmbCiclo();
    }

    private void inicarCmbCarrera() {
        CarreraBD CBD = new CarreraBD(ctrPrin.getConecta());
        carreras = CBD.cargarCarrerasCmb();
        iniciarCmb(vtn.getCmbCarrera());
        carreras.forEach(c -> {
            vtn.getCmbCarrera().addItem(c.getCodigo());
        });
        vtn.getCmbCarrera().addActionListener(e -> clickCarrera());
    }

    private void clickCarrera() {
        int sel = vtn.getCmbCarrera().getSelectedIndex();
        PeriodoLectivoBD PBD = new PeriodoLectivoBD(ctrPrin.getConecta());
        if (sel > 0) {
            periodos = (ArrayList<PeriodoLectivoMD>) PBD.llenarPeriodosxCarreras(
                    carreras.get(sel - 1).getId()
            );
            llenarCmbPeriodo(periodos);
        }
    }

    private void clickPeriodo() {
        int sel = vtn.getCmbPeriodo().getSelectedIndex();
        if (sel > 0) {
            MateriaBD MBD = new MateriaBD(ctrPrin.getConecta());
            ciclos = MBD.cargarCiclosCarrera(periodos.get(sel - 1).getCarrera().getId());
            llenarCmbCiclos(ciclos);
        }

    }

    private void llenarCmbCiclos(ArrayList<Integer> ciclos) {
        iniciarCmb(vtn.getCmbCiclo());
        if (ciclos != null) {
            ciclos.forEach(c -> {
                vtn.getCmbCiclo().addItem(c + "");
            });
        }
    }

    private void iniciarCmbPeriodo() {
        iniciarCmb(vtn.getCmbPeriodo());
        vtn.getCmbCiclo().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickPeriodo();
            }
        });
    }

    private void iniciarCmbCiclo() {
        iniciarCmb(vtn.getCmbCiclo());
    }

    private void iniciarCmbJornada() {
        JornadaBD JBD = new JornadaBD(ctrPrin.getConecta());
        jornadas = JBD.cargarJornadas();
        if (jornadas != null) {
            iniciarCmb(vtn.getCmbJornada());
            jornadas.forEach((j) -> {
                vtn.getCmbJornada().addItem(j.getNombre());
            });
        }
    }

    private void llenarCmbPeriodo(ArrayList<PeriodoLectivoMD> periodos) {
        iniciarCmb(vtn.getCmbPeriodo());
        periodos.forEach(p -> {
            vtn.getCmbPeriodo().addItem(p.getNombre_PerLectivo());
        });
    }

    private void iniciarCmb(JComboBox cmb) {
        cmb.removeAllItems();
        cmb.addItem("Seleccione");
    }

    private void imprimirPrimer() {
//        int seleccion = fCrud_plan_Clases.getTlbTablaPLC().getSelectedRow();
//        if (seleccion >= 0) {
        try {
            JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/ube/UBE/UBE-principal.jasper"));
            Map parametro = new HashMap();

            parametro.put("id_carrera", String.valueOf("2"));

            JasperPrint jp = JasperFillManager.fillReport(jr, parametro, ctrPrin.getConecta().devolverConexion());
            JasperViewer pv = new JasperViewer(jp, false);
            pv.setVisible(true);
            pv.setTitle("Número de alumnos por carrera");
            //principal.add(pv);
        } catch (JRException ex) {
            Logger.getLogger(VtnReporteNumAlumnoCTR.class.getName()).log(Level.SEVERE, null, ex);
        }

//        } else {
//            JOptionPane.showMessageDialog(null, "DEBE SELECIONAR EL DOCUMENTO PARA IMPRIMIR");
//        }
    }

    private void imprimirSeg() {
//        int seleccion = fCrud_plan_Clases.getTlbTablaPLC().getSelectedRow();
//        if (seleccion >= 0) {
        try {
            JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/ube/UBE/ube2/alumnos_por_jornada.jasper"));
            Map parametro = new HashMap();

            parametro.put("id_carrera", String.valueOf("2"));
            parametro.put("id_jornada", String.valueOf("2"));

            JasperPrint jp = JasperFillManager.fillReport(jr, parametro, ctrPrin.getConecta().devolverConexion());
            JasperViewer pv = new JasperViewer(jp, false);
            pv.setVisible(true);
            pv.setTitle("Número de alumnos por jornada");
            //principal.add(pv);
        } catch (JRException ex) {
            Logger.getLogger(VtnReporteNumAlumnoCTR.class.getName()).log(Level.SEVERE, null, ex);
        }

//        } else {
//            JOptionPane.showMessageDialog(null, "DEBE SELECIONAR EL DOCUMENTO PARA IMPRIMIR");
//        }
    }
}
