package controlador.alumno;

import com.toedter.calendar.JDateChooser;
import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.alumno.AlumnoCarreraMD;
import modelo.alumno.Retirado;
import modelo.alumno.RetiradoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.periodolectivo.RetiradoPeriodoLectivoBD;
import vista.alumno.JDRetirarAlumno;

/**
 *
 * @author gus
 */
public class JDRetirarAlumnoCTR extends DCTR {

    private AlumnoCarreraMD ac;
    private final JDRetirarAlumno FRM;
    private Retirado r;
    private final RetiradoBD RBD = RetiradoBD.single();
    private boolean editar = false;
    private final RetiradoPeriodoLectivoBD RPBD = RetiradoPeriodoLectivoBD.single();
    private List<PeriodoLectivoMD> ps;

    public JDRetirarAlumnoCTR(
            VtnPrincipalCTR ctrPrin
    ) {
        super(ctrPrin);
        this.FRM = new JDRetirarAlumno(ctrPrin.getVtnPrin(), false);
    }

    public void ingresar(AlumnoCarreraMD ac) {
        this.ac = ac;
        r = new Retirado();
        r.setAlmnCarrera(ac);
        iniciarVtn();
        FRM.getJdcFechaRetiro().setDate(
                getDateFromLocalDate(LocalDate.now())
        );
    }

    public void editar(Retirado r) {
        this.editar = true;
        this.ac = null;
        this.r = r;
        iniciarVtn();
        FRM.getJdcFechaRetiro().setDate(
                getDateFromLocalDate(r.getFechaRetiro())
        );
        FRM.getCmbPeriodo().setSelectedIndex(
                getPosPeriodoById(r.getPeriodo().getID())
        );
        FRM.getTxtMotivo().setText(r.getMotivo());
        editar = true;
    }

    public void informacion(Retirado r) {
        this.ac = null;
        this.r = r;
        iniciarVtn();
        FRM.getJdcFechaRetiro().setDate(
                getDateFromLocalDate(r.getFechaRetiro())
        );
        FRM.getTxtMotivo().setText(
                r.getMotivo()
        );
        FRM.getCmbPeriodo().setSelectedIndex(
                getPosPeriodoById(r.getPeriodo().getID())
        );
        FRM.getBtnGuardar().setVisible(false);
    }

    private int getPosPeriodoById(int idPeriodo) {
        int pos = 0;
        for (int i = 0; i < ps.size(); i++) {
            if (idPeriodo == ps.get(i).getID()) {
                pos = i + 1;
                break;
            }
        }
        return pos;
    }

    private void iniciarVtn() {
        cargarCmbPeriodo();
        FRM.getJdcFechaRetiro().setDateFormatString("dd/MM/yyyy");
        FRM.getBtnGuardar().addActionListener(e -> guardar());
        abrirJD(FRM);
    }

    private Date getDateFromLocalDate(LocalDate fecha) {
        return Date.from(
                fecha.atStartOfDay().atZone(ZoneId.systemDefault())
                        .toInstant()
        );
    }

    private void guardar() {
        int posPrd = FRM.getCmbPeriodo().getSelectedIndex();
        LocalDate fecha = getFechaJDC(FRM.getJdcFechaRetiro());
        if (posPrd > 0 && fecha != null) {
            r.setPeriodo(ps.get(posPrd - 1));
            r.setMotivo(FRM.getTxtMotivo().getText());
            r.setFechaRetiro(fecha);
            if (editar) {
                editar = false;
                if (RBD.editar(r) > 0) {
                    JOptionPane.showMessageDialog(
                            FRM,
                            "Editamos correctamente."
                    );
                }
            } else {
                if (RBD.guardar(r) > 0) {
                    JOptionPane.showMessageDialog(
                            FRM,
                            "Guardamos correctamente."
                    );
                }
            }
            FRM.setVisible(false);
        }
    }

    private void cargarCmbPeriodo() {
        if (ac != null) {
            ps = RPBD.getForAlmnCarrera(ac.getId());
        } else {
            ps = RPBD.getForAlmnCarrera(r.getAlmnCarrera().getId());
        }
        FRM.getCmbPeriodo().removeAllItems();
        FRM.getCmbPeriodo().addItem("Seleccione");
        ps.forEach(p -> {
            FRM.getCmbPeriodo().addItem(p.getNombre());
        });
    }

    private LocalDate getFechaJDC(JDateChooser jdc) {
        LocalDate fecha = null;
        if (jdc.getDate() != null) {
            fecha = jdc.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        return fecha;
    }

}
