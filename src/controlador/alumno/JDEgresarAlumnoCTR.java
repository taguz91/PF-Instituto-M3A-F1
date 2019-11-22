package controlador.alumno;

import com.toedter.calendar.JDateChooser;
import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.alumno.AlumnoCarreraMD;
import modelo.alumno.Egresado;
import modelo.alumno.EgresadoBD;
import modelo.alumno.MallaAlumnoBD;
import modelo.alumno.MallaAlumnoMD;
import modelo.periodolectivo.PeriodoLectivoMD;
import vista.alumno.JDEgresarAlumno;

/**
 *
 * @author gus
 */
public class JDEgresarAlumnoCTR extends DCTR {

    private final JDEgresarAlumno FRM;
    private final EgresadoBD EBD = EgresadoBD.single();
    // Para el combo 
    private List<PeriodoLectivoMD> ps;
    private Egresado egresado = new Egresado();
    private int idAlmnCarrera = 0;
    // Para saber si estamos editando  
    private boolean editar = false;
    // Para saber que matriculas nos quedan por pagar antes de ingresarnos como egresados. 
    private final MallaAlumnoBD MABD;
    private ArrayList<MallaAlumnoMD> mallaAlumno;

    public JDEgresarAlumnoCTR(VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
        this.FRM = new JDEgresarAlumno(ctrPrin.getVtnPrin(), false);
        this.MABD = new MallaAlumnoBD(ctrPrin.getConecta());
    }

    public void ingresar(int idAlmnCarrera) {
        this.idAlmnCarrera = idAlmnCarrera;
        AlumnoCarreraMD ac = new AlumnoCarreraMD();
        ac.setId(idAlmnCarrera);
        egresado.setAlmnCarrera(ac);
        iniciarVtn();
        mallaAlumno = MABD.buscarMallaAlumnoParaEstado(idAlmnCarrera);
        String msg = "";
        msg = mallaAlumno.stream().filter((ma) -> (ma.getMallaNumMatricula() > 1))
                .map((ma) -> "Ciclo: " + ma.getMallaCiclo() + "  "
                + "# Matricula: " + ma.getMallaNumMatricula() + "  "
                + "Materia: " + ma.getMateria().getNombre() + " \n")
                .reduce(msg, String::concat);
        if (msg.length() > 0) {
            JOptionPane.showMessageDialog(
                    FRM,
                    "Matriculas que debe cancelar. \n"
                    + msg
            );
        }
    }

    public void editar(Egresado e) {
        idAlmnCarrera = e.getAlmnCarrera().getId();
        this.egresado = e;
        iniciarVtn();
        if (e.getFechaEgreso() != null) {
            FRM.getJdcFechaEgreso().setDate(
                    getDateFromLocalDate(e.getFechaEgreso())
            );
        }
        if (e.isGraduado() && e.getFechaGraduacion() != null) {
            FRM.getCbxGraduado().setSelected(true);
            FRM.getJdcFechaGraduacion().setDate(
                    getDateFromLocalDate(e.getFechaGraduacion())
            );
        }
        editar = true;
    }

    private void iniciarVtn() {
        cargarCmbPeriodo();
        FRM.getJdcFechaEgreso().setDateFormatString("dd/MM/yyyy");
        FRM.getJdcFechaGraduacion().setDateFormatString("dd/MM/yyyy");
        FRM.getBtnGuardar().addActionListener(ev -> guardar());
        abrirJD(FRM);
    }

    private void guardar() {
        if (valido()) {
            int posPeriodo = FRM.getCmbPeriodo().getSelectedIndex();
            egresado.setPeriodo(ps.get(posPeriodo));
            egresado.setFechaEgreso(getFechaJDC(FRM.getJdcFechaEgreso()));
            egresado.setGraduado(FRM.getCbxGraduado().isSelected());
            egresado.setFechaGraduacion(getFechaJDC(FRM.getJdcFechaGraduacion()));
            int idGenerado;
            String msg;
            if (editar) {
                if (egresado.isGraduado()) {
                    idGenerado = EBD.editarConGraduacion(egresado);
                } else {
                    idGenerado = EBD.editarSinGraduacion(egresado);
                }
                msg = "Editamos correctamente.";
                editar = false;
            } else {
                if (egresado.isGraduado()) {
                    idGenerado = EBD.guardarConGraduacion(egresado);
                } else {
                    idGenerado = EBD.guardarSinGraduacion(egresado);
                }
                msg = "Guardamos correctamente.";
            }

            if (idGenerado > 0) {
                JOptionPane.showMessageDialog(FRM, msg);
                FRM.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(FRM, "No pudimos realizar la accion.");
            }
        }
    }

    private boolean valido() {
        boolean valido = true;
        String msg = "";
        if (FRM.getJdcFechaEgreso().getDate() == null) {
            msg += "No indico en que fecha egreso el alumno.\n";
            valido = false;
        }

        if (FRM.getCbxGraduado().isSelected()) {
            if (FRM.getJdcFechaGraduacion().getDate() == null) {
                msg += "No indico en que fecha se graduo el alumno.\n";
                valido = false;
            }
        }

        if (!valido) {
            JOptionPane.showMessageDialog(
                    FRM,
                    msg,
                    "Error en el formulario",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        return valido;
    }

    private void cargarCmbPeriodo() {
        ps = EBD.getPeriodoByIdAlmnCarrera(idAlmnCarrera);
        FRM.getCmbPeriodo().removeAllItems();
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

    private Date getDateFromLocalDate(LocalDate fecha) {
        return Date.from(
                fecha.atStartOfDay().atZone(ZoneId.systemDefault())
                        .toInstant()
        );
    }

}
