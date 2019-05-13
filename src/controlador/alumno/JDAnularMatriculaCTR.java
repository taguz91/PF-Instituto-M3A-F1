package controlador.alumno;

import controlador.principal.DVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.ConectarDB;
import modelo.alumno.AlumnoCursoBD;
import modelo.alumno.AlumnoCursoMD;
import modelo.alumno.AlumnoCursoRetiradoBD;
import modelo.alumno.MatriculaMD;
import modelo.estilo.TblEstilo;
import modelo.materia.MateriaRequisitoBD;
import modelo.materia.MateriaRequisitoMD;
import modelo.validaciones.Validar;
import vista.alumno.JDAnularMatricula;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class JDAnularMatriculaCTR extends DVtnCTR {

    private final JDAnularMatricula jd;
    private final MatriculaMD matricula;
    private final AlumnoCursoBD almCur;
    private ArrayList<AlumnoCursoMD> almnsCurso, almnsCursoAnular;
    private final AlumnoCursoRetiradoBD acrb;
    private ArrayList<MateriaRequisitoMD> corequisitos;
    private final MateriaRequisitoBD mtr;
    private String materiaAnular = "";

    public JDAnularMatriculaCTR(VtnPrincipalCTR ctrPrin,
            MatriculaMD matricula) {
        super(ctrPrin);
        this.almCur = new AlumnoCursoBD(ctrPrin.getConecta());
        this.acrb = new AlumnoCursoRetiradoBD(ctrPrin.getConecta());
        this.mtr = new MateriaRequisitoBD(ctrPrin.getConecta());
        this.matricula = matricula;
        this.jd = new JDAnularMatricula(ctrPrin.getVtnPrin(), false);
    }

    public void iniciar() {
        inicarInformacion();
        iniciarTbls();
        inicarAcciones();

        iniciarJD();
    }

    private void inicarAcciones() {
        jd.getBtnAnular().addActionListener(e -> clickAnular());
    }

    private void anunarMatricula(ArrayList<AlumnoCursoMD> almnsCursoAnular) {
        String observacion = JOptionPane.showInputDialog(
                "Ingrese la razon de porque \n"
                + matricula.getAlumno().getNombreCompleto() + " anula la matricula de: \n"
                + materiaAnular);
        if (observacion != null) {
            if (Validar.esLetras(observacion)) {

                almnsCursoAnular.forEach(ac -> {
                    acrb.setAlumnoCurso(ac);
                    acrb.setObservacion(observacion);

                    acrb.guardar();
                });

                llenarTbl();
            } else {
                JOptionPane.showMessageDialog(ctrPrin.getVtnPrin(), "Unicamente puede ingresar letras.");
                anunarMatricula(almnsCursoAnular);
            }
        }
    }

    private void clickAnular() {
        posFila = jd.getTblCursos().getSelectedRow();
        almnsCursoAnular = new ArrayList<>();
        materiaAnular = "";
        if (posFila >= 0) {
            materiaAnular = materiaAnular + almnsCurso.get(posFila).getCurso().getMateria().getNombre() + "\n";
            corequisitos = mtr.buscarDeQueEsCorequisito(almnsCurso.get(posFila).getCurso().getMateria().getId());
            if (corequisitos.size() > 0) {
                materiaAnular = materiaAnular + "Con sus corequisitos: \n";
            }

            corequisitos.forEach(c -> {
                materiaAnular = materiaAnular + c.getMateria().getNombre() + "\n";
            });

            int r = JOptionPane.showConfirmDialog(ctrPrin.getVtnPrin(), "Se anulara la matricula de: \n" + materiaAnular);
            if (r == JOptionPane.YES_OPTION) {
                almnsCurso.forEach(ac -> {
                    for (int i = 0; i < corequisitos.size(); i++) {
                        if (ac.getCurso().getMateria().getId() == corequisitos.get(i).getMateria().getId()) {
                            almnsCursoAnular.add(ac);
                            break;
                        }
                    }
                });
                almnsCursoAnular.add(almnsCurso.get(posFila));
                anunarMatricula(almnsCursoAnular);
            }
        } else {
            JOptionPane.showMessageDialog(ctrPrin.getVtnPrin(), "Seleccione una fila primero.");
        }
    }

    private void iniciarTbls() {
        String[] t = {"Materia", "Curso"};
        String[][] datos = {};
        mdTbl = TblEstilo.modelTblSinEditar(datos, t);
        jd.getTblCursos().setModel(mdTbl);
        TblEstilo.formatoTbl(jd.getTblCursos());
        TblEstilo.columnaMedida(jd.getTblCursos(), 1, 50);
        llenarTbl();
    }

    private void llenarTbl() {
        mdTbl.setRowCount(0);
        almnsCurso = almCur.buscarCursosAlmPeriodo(matricula.getAlumno().getId_Alumno(),
                matricula.getPeriodo().getId_PerioLectivo());
        almnsCurso.forEach(ac -> {
            Object[] v = {ac.getCurso().getMateria().getNombre(),
                ac.getCurso().getNombre()};
            mdTbl.addRow(v);
        });
    }

    private void inicarInformacion() {
        jd.getLblAlumno().setText(matricula.getAlumno().getNombreCompleto());
        jd.getLblPeriodo().setText(matricula.getPeriodo().getNombre_PerLectivo());
        jd.getLblFecha().setText(matricula.getSoloFecha());
    }

    private void iniciarJD() {
        jd.setVisible(true);
        jd.setLocationRelativeTo(ctrPrin.getVtnPrin());
        jd.setTitle("Anular matricula");
        ctrPrin.eventoJDCerrar(jd);
    }

}
