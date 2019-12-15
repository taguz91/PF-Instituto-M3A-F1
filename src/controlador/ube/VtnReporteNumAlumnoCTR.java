package controlador.ube;

import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.curso.CursoBD;
import modelo.jornada.JornadaBD;
import modelo.jornada.JornadaMD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import vista.ube.VtnReporteNumAlumno;

/**
 *
 * @author gus
 */
public class VtnReporteNumAlumnoCTR extends DCTR {

    private final VtnReporteNumAlumno vtn;
    //Todos los array que usaremos en los combos  
    private ArrayList<CarreraMD> carreras;
    private ArrayList<PeriodoLectivoMD> periodos;
    private ArrayList<Integer> ciclos;
    private ArrayList<JornadaMD> jornadas;

    public VtnReporteNumAlumnoCTR(VtnPrincipalCTR ctrPrin, VtnReporteNumAlumno vtn) {
        super(ctrPrin);
        this.vtn = vtn;
    }

    public void iniciar() {
        inicarCmbs();
        ctrPrin.agregarVtn(vtn);
        vtn.getBtnPorCarrera().addActionListener(e -> reportePorCarrera());
        vtn.getBtnPorJornada().addActionListener(e -> reporteCarreraJornada());
        vtn.getBtnPorPeriodo().addActionListener(e -> reportePeriodo());
    }

    private void inicarCmbs() {
        inicarCmbCarrera();
        iniciarCmbPeriodo();
        iniciarCmbJornada();
        iniciarCmbCiclo();
    }

    private void inicarCmbCarrera() {
        CarreraBD CBD = CarreraBD.single();
        carreras = CBD.cargarCarrerasCmb();
        iniciarCmb(vtn.getCmbCarrera());
        carreras.forEach(c -> {
            vtn.getCmbCarrera().addItem(c.getCodigo());
        });
        vtn.getCmbCarrera().addActionListener(e -> clickCarrera());
    }

    private void clickCarrera() {
        int sel = vtn.getCmbCarrera().getSelectedIndex();
        PeriodoLectivoBD PBD = PeriodoLectivoBD.single();
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
            CursoBD MBD = CursoBD.single();
            ciclos = MBD.cargarCiclosPorPeriodo(periodos.get(sel - 1).getCarrera().getId());
            llenarCmbCiclos(ciclos);
        }

    }

    private void llenarCmbCiclos(ArrayList<Integer> ciclos) {
        iniciarCmb(vtn.getCmbCiclo());
        ciclos.forEach(c -> {
            vtn.getCmbCiclo().addItem(c + "");
        });
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
        JornadaBD JBD = JornadaBD.single();
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
            vtn.getCmbPeriodo().addItem(p.getNombre());
        });
    }

    private void iniciarCmb(JComboBox cmb) {
        cmb.removeAllItems();
        cmb.addItem("Seleccione");
    }

    private void reportePorCarrera() {
        int posCarrera = vtn.getCmbCarrera().getSelectedIndex();
        if (posCarrera > 0) {
            try {
                JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/ube/UBE/UBE-principal.jasper"));
                Map parametro = new HashMap();
                parametro.put("id_carrera", carreras.get(posCarrera - 1).getId());
                CON.mostrarReporte(
                        jr,
                        parametro,
                        "Número de alumnos por carrera"
                );
            } catch (JRException ex) {
                System.out.println("Error reporte: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(vtn, "Debe seleccionar una carrera");
        }
    }

    private void reporteCarreraJornada() {
        int posCarrera = vtn.getCmbCarrera().getSelectedIndex();
        int posJornada = vtn.getCmbJornada().getSelectedIndex();

        if (posCarrera > 0 && posJornada > 0) {
            try {
                JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/ube/UBE/alumnos_por_jornada.jasper"));
                Map parametro = new HashMap();

                parametro.put("id_carrera", carreras.get(posCarrera - 1).getId());
                parametro.put("id_jornada", jornadas.get(posJornada - 1).getId());

                CON.mostrarReporte(
                        jr,
                        parametro,
                        "Número de alumnos por jornada"
                );

            } catch (JRException ex) {
                System.out.println("Error con el reporte por carrera y jornada. " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(vtn, "Debe seleccionar una carrera y una jornada.");
        }
    }

    private void reportePeriodo() {
        int posPeriodo = vtn.getCmbPeriodo().getSelectedIndex();

        if (posPeriodo > 0) {
            try {
                JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/ube/UBE/num_alumnos_periodo.jasper"));
                Map parametro = new HashMap();
                parametro.put("id_periodo", periodos.get(posPeriodo - 1).getID());
                CON.mostrarReporte(
                        jr,
                        parametro,
                        "Número de alumnos por jornada"
                );
            } catch (JRException ex) {
                System.out.println("Error con el reporte por carrera y jornada. " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(vtn, "Debe seleccionar un periodo.");
        }
    }

}
