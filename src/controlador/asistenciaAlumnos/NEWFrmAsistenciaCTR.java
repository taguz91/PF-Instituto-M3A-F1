package controlador.asistenciaAlumnos;

import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import java.time.LocalDate;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.asistencia.GenerarFechas;
import modelo.asistencia.NEWAsistenciaBD;
import modelo.curso.CursoMD;
import modelo.periodolectivo.PeriodoLectivoMD;
import utils.CONS;
import vista.asistenciaAlumnos.NEWFrmAsistencia;

/**
 *
 * @author gus
 */
public class NEWFrmAsistenciaCTR extends DCTR {

    private final NEWFrmAsistencia VTN = new NEWFrmAsistencia();
    // Conexion a BD
    private final NEWAsistenciaBD ABD = NEWAsistenciaBD.single();
    // Listas para combos 
    private List<PeriodoLectivoMD> pls;
    private List<CursoMD> cs;
    // Modelo de la tabla 
    private DefaultTableModel mdTbl;

    public NEWFrmAsistenciaCTR(VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
    }

    public void iniciar() {
        iniciarCMBPeriodo();
        iniciarTablas();
        ctrPrin.agregarVtn(VTN);
        iniciarAcciones();
        vtnCargada = true;
    }

    private void iniciarTablas() {
        String[] titulo = {
            "#",
            "Alumno",
            "Faltas"
        };
        mdTbl = iniciarTbl(
                VTN.getTblAlumnos(),
                titulo
        );
        VTN.getTblAlumnos().setModel(mdTbl);
    }

    private void iniciarAcciones() {
        VTN.getCmbMateria().addActionListener(e -> {
            //vtnCargada = false;
            clickCmbCurso();
        });
        VTN.getCmbPeriodo().addActionListener(e -> clickCmbPeriodo());
    }

    private void iniciarCMBPeriodo() {
        pls = ABD.getPeriodosDocente(
                CONS.USUARIO.getPersona().getIdentificacion()
        );
        VTN.getCmbPeriodo().removeAllItems();
        VTN.getCmbPeriodo().addItem("Seleccione");
        pls.forEach(p -> {
            VTN.getCmbPeriodo().addItem(p.getNombre());
        });
    }

    private void clickCmbPeriodo() {
        int posPrd = VTN.getCmbPeriodo().getSelectedIndex();
        if (posPrd > 0) {
            cs = ABD.getCursosPeriodoDocente(
                    pls.get(posPrd - 1).getID(),
                    CONS.USUARIO.getPersona().getIdentificacion()
            );
            VTN.getCmbMateria().removeAllItems();
            VTN.getCmbMateria().addItem("Seleccione");
            cs.forEach(c -> {
                VTN.getCmbMateria().addItem(
                        c.getNombre() + " | "
                        + c.getMateria().getNombre()
                );
            });
            vtnCargada = true;
        }
    }

    private void clickCmbCurso() {
        int posCurso = VTN.getCmbMateria().getSelectedIndex();
        if (posCurso > 0) {
            GenerarFechas gf = new GenerarFechas();
            List<String> fechas = gf.getFechasClaseCurso(
                    cs.get(posCurso - 1).getId()
            );
            VTN.getCmbFechas().removeAllItems();
            VTN.getCmbFechas().addItem("Sin clases");
            fechas.forEach(f -> {
                VTN.getCmbFechas().addItem(f);
            });
            LocalDate ld = LocalDate.now();
            VTN.getCmbFechas().setSelectedItem(
                    ld.getDayOfMonth() + "/"
                    + ld.getMonthValue() + "/"
                    + ld.getYear()
            );
        }
    }

}
