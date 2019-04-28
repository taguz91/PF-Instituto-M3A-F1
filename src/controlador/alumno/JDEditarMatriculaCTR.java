package controlador.alumno;

import controlador.principal.DependenciasVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.alumno.MatriculaMD;
import modelo.curso.CursoBD;
import modelo.curso.CursoMD;
import modelo.estilo.TblEstilo;
import vista.alumno.JDEditarMatricula;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class JDEditarMatriculaCTR extends DependenciasVtnCTR {

    private final JDEditarMatricula jd;
    private final MatriculaMD matricula;
    private final CursoBD cur;
    private ArrayList<CursoMD> cursosMatricula;
    private DefaultTableModel mdTblA, mdTblN;

    public JDEditarMatriculaCTR(ConectarDB conecta, VtnPrincipal vtnPrin, VtnPrincipalCTR ctrPrin,
            MatriculaMD matricula) {
        super(conecta, vtnPrin, ctrPrin);
        this.cur = new CursoBD(conecta);
        this.matricula = matricula;
        this.jd = new JDEditarMatricula(vtnPrin, false);
    }

    public void iniciar() {
        iniciarTbls();
        inicarInformacion();
        inicarAcciones();
        iniciarJD();
    }

    private void clickCambiar() {
        boolean mismoCiclo = true;
        posFila = jd.getTblClasesActuales().getSelectedRow();
        if (posFila >= 0) {
            int ciclo = cursosMatricula.get(posFila).getCiclo();
            int[] selecs = jd.getTblClasesActuales().getSelectedRows();

            for (int s : selecs) {
                if (cursosMatricula.get(s).getCiclo() != ciclo) {
                    mismoCiclo = false;
                    break;
                }
            }

            if (mismoCiclo) {

            } else {
                JOptionPane.showMessageDialog(vtnPrin, "Debe seleccionar clases del mismo ciclo.");
            }
        }

    }

    private void inicarAcciones() {
        jd.getBtnCambiar().addActionListener(e -> clickCambiar());
    }

    /**
     * Llenamos la tabla con las materias actuales
     */
    private void llenarTblMA(ArrayList<CursoMD> cursosMatricula) {
        if (cursosMatricula != null) {
            cursosMatricula.forEach(c -> {
                Object[] v = {c.getMateria().getNombre(), c.getNombre()};
                mdTblA.addRow(v);
            });
        }
    }

    private void iniciarTbls() {
        String[] t = {"Materia", "Curso"};
        String[][] datos = {};
        mdTblA = TblEstilo.modelTblSinEditar(datos, t);
        mdTblN = TblEstilo.modelTblSinEditar(datos, t);
        jd.getTblClasesActuales().setModel(mdTblA);
        jd.getTblClasesNuevas().setModel(mdTblN);
        TblEstilo.formatoTblMultipleSelec(jd.getTblClasesActuales());
        TblEstilo.formatoTblMultipleSelec(jd.getTblClasesNuevas());
        TblEstilo.columnaMedida(jd.getTblClasesActuales(), 1, 50);
        TblEstilo.columnaMedida(jd.getTblClasesNuevas(), 1, 50);

    }

    private void inicarInformacion() {
        jd.getLblAlumno().setText(matricula.getAlumno().getNombreCompleto());
        jd.getLblPeriodo().setText(matricula.getPeriodo().getNombre_PerLectivo());
        jd.getLblFecha().setText(matricula.getSoloFecha());
        cursosMatricula = cur.buscarCursosAlmPeriodo(matricula.getAlumno().getId_Alumno(),
                matricula.getPeriodo().getId_PerioLectivo());
        llenarTblMA(cursosMatricula);
    }

    private void iniciarJD() {
        jd.setVisible(true);
        jd.setLocationRelativeTo(vtnPrin);
        jd.setTitle("Editar matricula");
        ctrPrin.eventoJDCerrar(jd);
    }

}
