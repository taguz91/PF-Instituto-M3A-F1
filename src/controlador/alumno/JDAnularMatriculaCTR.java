package controlador.alumno;

import controlador.principal.DVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.alumno.AlumnoCursoBD;
import modelo.alumno.AlumnoCursoMD;
import modelo.alumno.AlumnoCursoRetiradoBD;
import modelo.alumno.MatriculaMD;
import modelo.estilo.TblEstilo;
import modelo.materia.MateriaRequisitoBD;
import modelo.materia.MateriaRequisitoMD;
import modelo.validaciones.Validar;
import vista.alumno.JDAnularMatricula;

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

    /**
     * Este formulario nos ayuda a anular matriculas para ingresar 
     * no debio pasar mas de 30 dias desde el inicio de periodo
     * @param ctrPrin
     * @param matricula
     */
    public JDAnularMatriculaCTR(VtnPrincipalCTR ctrPrin,
            MatriculaMD matricula) {
        super(ctrPrin);
        this.almCur = new AlumnoCursoBD(ctrPrin.getConecta());
        this.acrb = new AlumnoCursoRetiradoBD(ctrPrin.getConecta());
        this.mtr = new MateriaRequisitoBD(ctrPrin.getConecta());
        this.matricula = matricula;
        this.jd = new JDAnularMatricula(ctrPrin.getVtnPrin(), false);
    }
    
    /**
     * Iniciamos todas las dependencias
     */
    public void iniciar() {
        inicarInformacion();
        iniciarTbls();
        inicarAcciones();

        iniciarJD();
    }
    
    /**
     * Iniciamos todas las acciones que 
     * existan en la ventana.
     */
    private void inicarAcciones() {
        jd.getBtnAnular().addActionListener(e -> clickAnular());
    }
    
    /**
     * Anulamos la matriculas, primero mostramos 
     * las materias en las que anulara su matricula 
     * y pedimos que ingrese la razon de su anulacion
     * @param almnsCursoAnular 
     */
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
    
    /**
     * Al hacer click en eliminar, se obtiene la materia 
     * y se busca todos los co requisitos que tiene esta materia  
     * para tambien anular su matricula.
     * AL final se le pide confirmacion de si quiere anular 
     * su matricula.
     */
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
    
    /**
     * Iniciamos las tablas para que no puedan ser editables
     */
    private void iniciarTbls() {
        String[] t = {"Materia", "Curso"};
        String[][] datos = {};
        mdTbl = TblEstilo.modelTblSinEditar(datos, t);
        jd.getTblCursos().setModel(mdTbl);
        TblEstilo.formatoTbl(jd.getTblCursos());
        TblEstilo.columnaMedida(jd.getTblCursos(), 1, 50);
        llenarTbl();
    }
    
    /**
     * Llenamos la tabla con todas las materias en las que se matricula este ciclo.
     */
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
    
    /**
     * Cargamos toda la informacion referente a este estudiante.
     */
    private void inicarInformacion() {
        jd.getLblAlumno().setText(matricula.getAlumno().getNombreCompleto());
        jd.getLblPeriodo().setText(matricula.getPeriodo().getNombre_PerLectivo());
        jd.getLblFecha().setText(matricula.getSoloFecha());
    }
    
    /**
     * Iniciamos el para mostrarlo por ventana.
     */
    private void iniciarJD() {
        jd.setVisible(true);
        jd.setLocationRelativeTo(ctrPrin.getVtnPrin());
        jd.setTitle("Anular matricula");
        ctrPrin.eventoJDCerrar(jd);
    }

}
