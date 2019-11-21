package controlador.alumno;

import com.toedter.calendar.JDateChooser;
import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.alumno.AlumnoCarreraMD;
import modelo.alumno.Egresado;
import modelo.alumno.EgresadoBD;
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
    private Egresado e = new Egresado();
    private int idAlmnCarrera = 0;
    // Para saber si estamos editando  
    private boolean editar = false;

    public JDEgresarAlumnoCTR(VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
        this.FRM = new JDEgresarAlumno(ctrPrin.getVtnPrin(), false);
    }

    public void ingresar(int idAlmnCarrera) {
        this.idAlmnCarrera = idAlmnCarrera;
        AlumnoCarreraMD ac = new AlumnoCarreraMD();
        ac.setId(idAlmnCarrera);
        e.setAlmnCarrera(ac);
        iniciarVtn();
    }

    public void editar(Egresado e) {
        idAlmnCarrera = e.getAlmnCarrera().getId();
        this.e = e;
        iniciarVtn();
    }

    private void iniciarVtn() {
        cargarCmbPeriodo();
        FRM.getJdcFechaEgreso().setDateFormatString("dd/MM/yyyy");
        FRM.getJdcFechaGraduacion().setDateFormatString("dd/MM/yyyy");
        FRM.getBtnGuardar().addActionListener(e -> guardar());
        abrirJD(FRM);
    }

    private void guardar() {
        if (valido()) {
            int posPeriodo = FRM.getCmbPeriodo().getSelectedIndex();
            e.setPeriodo(ps.get(posPeriodo));
            e.setFechaEgreso(getFechaJDC(FRM.getJdcFechaEgreso()));
            e.setGraduado(FRM.getCbxGraduado().isSelected());
            e.setFechaGraduacion(getFechaJDC(FRM.getJdcFechaGraduacion()));
            int idGenerado;
            String msg;
            if (editar) {
                if (e.isGraduado()) {
                    idGenerado = EBD.editarConGraduacion(e);
                } else {
                    idGenerado = EBD.editarSinGraduacion(e);
                }
                msg = "Editamos correctamente.";
                editar = false;
            } else {
                if (e.isGraduado()) {
                    idGenerado = EBD.guardarConGraduacion(e);
                } else {
                    idGenerado = EBD.guardarSinGraduacion(e);
                }
                msg = "Guardamos correctamente.";
            }

            if (idGenerado > 0) {
                JOptionPane.showMessageDialog(FRM, msg);
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

}