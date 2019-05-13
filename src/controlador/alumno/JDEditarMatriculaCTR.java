package controlador.alumno;

import controlador.estilo.TblRenderMatricula;
import controlador.principal.DVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.alumno.AlumnoCursoBD;
import modelo.alumno.AlumnoCursoMD;
import modelo.alumno.MatriculaMD;
import modelo.curso.CursoBD;
import modelo.curso.CursoMD;
import modelo.estilo.TblEstilo;
import vista.alumno.JDEditarMatricula;

/**
 *
 * @author Johnny
 */
public class JDEditarMatriculaCTR extends DVtnCTR {

    private final JDEditarMatricula jd;
    private final MatriculaMD matricula;
    private final CursoBD cur;
    private final AlumnoCursoBD almCur;
    private String nombreCurso;
    private ArrayList<String> nomCursos;
    private ArrayList<AlumnoCursoMD> almnsCurso, cursosNuevos = null;
    private ArrayList<CursoMD> cursos;
    private DefaultTableModel mdTblA, mdTblN;
    private String nombreCursosN = "";

    public JDEditarMatriculaCTR(VtnPrincipalCTR ctrPrin,
            MatriculaMD matricula) {
        super(ctrPrin);
        this.cur = new CursoBD(ctrPrin.getConecta());
        this.almCur = new AlumnoCursoBD(ctrPrin.getConecta());
        this.cursosNuevos = new ArrayList<>();
        this.matricula = matricula;
        this.jd = new JDEditarMatricula(ctrPrin.getVtnPrin(), false);
    }

    public void iniciar() {
        iniciarTbls();
        inicarInformacion();
        inicarAcciones();
        iniciarJD();
    }

    private void clickRemover() {
        int[] selecs = jd.getTblClasesNuevas().getSelectedRows();
        posFila = jd.getTblClasesNuevas().getSelectedRow();
        if (posFila >= 0) {
            boolean mantener;
            ArrayList<AlumnoCursoMD> acx = new ArrayList<>();
            for (int i = 0; i < cursosNuevos.size(); i++) {
                mantener = true;
                for (int s : selecs) {
                    if (i == s) {
                        mantener = false;
                        break;
                    }
                }
                if (mantener) {
                    acx.add(cursosNuevos.get(i));
                }
            }
            cursosNuevos = acx;
            llenarTblMN(cursosNuevos);
        } else {
            JOptionPane.showMessageDialog(ctrPrin.getVtnPrin(), "Debe seleccionar una fila o mas filas.");
        }
    }

    private void clickGuardar() {
        if (cursosNuevos != null) {
            almCur.borrarActualizarMatricula();
            nombreCursosN = "";
            cursosNuevos.forEach(ac -> {
                if (ac.getCurso().getCapaciadActual() > 0) {
                    almCur.agregarUpdate(ac.getId(), ac.getCurso().getId());
                    nombreCursosN = nombreCursosN + ac.getCurso().getMateria().getNombre() + "   Curso: "
                            + ac.getCurso().getNombre() + "   \n";
                }
            });

            int r = JOptionPane.showConfirmDialog(ctrPrin.getVtnPrin(), "Se editara la matricula de: "
                    + matricula.getAlumno().getNombreCompleto() + "   \n" + ""
                    + "Estos son sus nuevos cursos: \n" + nombreCursosN);
            if (r == JOptionPane.YES_OPTION) {
                if (almCur.actualizarMatricula()) {
                    ctrPrin.getVtnPrin().setEnabled(true);
                    jd.dispose();
                    almCur.borrarActualizarMatricula();
                }
            }
        }
    }

    private void cambiarACurso(String curso) {
        cursos = cur.buscarCursosPorNombreYPrdLectivo(curso,
                matricula.getPeriodo().getId_PerioLectivo());
        int[] selecs = jd.getTblClasesActuales().getSelectedRows();
        for (int s : selecs) {
            for (int i = 0; i < cursos.size(); i++) {
                if (almnsCurso.get(s).getCurso().getMateria().getId() == cursos.get(i).getMateria().getId()) {
                    borrarSiExisteCurso(cursos.get(i));

                    AlumnoCursoMD ac = new AlumnoCursoMD();
                    ac.setId(almnsCurso.get(s).getId());
                    ac.setCurso(cursos.get(i));
                    cursosNuevos.add(ac);

                    llenarTblMN(cursosNuevos);
                    break;
                }
            }
        }
    }

    /**
     * Si el curso ya esta en nuestro array de nuevos cursos lo borramos
     *
     * @param curso
     */
    private void borrarSiExisteCurso(CursoMD curso) {
        for (int i = 0; i < cursosNuevos.size(); i++) {
            if (curso.getMateria().getId() == cursosNuevos.get(i).getCurso().getMateria().getId()) {
                cursosNuevos.remove(i);
                break;
            }
        }
    }

    /**
     * Para llenar el nombre de los cursos de un periodo y ciclo en especifico
     *
     * @param ciclo
     */
    private void llenarCursosDisponibles(int ciclo) {
        nomCursos = cur.cargarNombreCursosPorPeriodoCiclo(matricula.getPeriodo().getId_PerioLectivo(),
                ciclo);
        ArrayList<String> nomAux = new ArrayList<>();

        nomAux.add("Seleccione");
        nomCursos.forEach(c -> {
            if (!c.equals(nombreCurso)) {
                nomAux.add(c);
            }
        });
        nomCursos = nomAux;

        Object np = JOptionPane.showInputDialog(ctrPrin.getVtnPrin(),
                "Lista de cursos disponibles", "Cursos",
                JOptionPane.QUESTION_MESSAGE, null,
                nomCursos.toArray(), "Seleccione");
        if (np != null) {
            if (np.toString().equals("Seleccione")) {
                JOptionPane.showMessageDialog(ctrPrin.getVtnPrin(), "Debe seleccionar un curso.");
                llenarCursosDisponibles(ciclo);
            } else {
                cambiarACurso(np.toString());
            }
        }
    }

    private void clickCambiar() {
        boolean mismoCiclo = true;
        posFila = jd.getTblClasesActuales().getSelectedRow();
        if (posFila >= 0) {
            int ciclo = almnsCurso.get(posFila).getCurso().getCiclo();
            nombreCurso = almnsCurso.get(posFila).getCurso().getNombre();
            int[] selecs = jd.getTblClasesActuales().getSelectedRows();

            for (int s : selecs) {
                if (almnsCurso.get(s).getCurso().getCiclo() != ciclo
                        || !almnsCurso.get(s).getCurso().getNombre().equals(nombreCurso)) {
                    mismoCiclo = false;
                    break;
                }
            }

            if (mismoCiclo) {
                llenarCursosDisponibles(ciclo);
            } else {
                JOptionPane.showMessageDialog(ctrPrin.getVtnPrin(), "Debe seleccionar clases del mismo ciclo o la misma jornada.");
            }
        } else {
            JOptionPane.showMessageDialog(ctrPrin.getVtnPrin(), "Debe seleccionar una o mas filas.");
        }

    }

    private void inicarAcciones() {
        jd.getBtnCambiar().addActionListener(e -> clickCambiar());
        jd.getBtnGuardar().addActionListener(e -> clickGuardar());
        jd.getBtnRemover().addActionListener(e -> clickRemover());
    }

    /**
     * Llenamos la tabla con las materias actuales
     */
    private void llenarTblMA(ArrayList<AlumnoCursoMD> almnsCurso) {
        if (almnsCurso != null) {
            almnsCurso.forEach(ac -> {
                Object[] v = {ac.getCurso().getMateria().getNombre(),
                    ac.getCurso().getNombre()};
                mdTblA.addRow(v);
            });
        }
    }

    /**
     * Llenamos la tabla con las materias nuevas
     */
    private void llenarTblMN(ArrayList<AlumnoCursoMD> cursosNuevos) {
        mdTblN.setRowCount(0);
        if (cursosNuevos != null) {
            cursosNuevos.forEach(ac -> {
                Object[] v = {ac.getCurso().getMateria().getNombre(),
                    ac.getCurso().getCapaciadActual(),
                    ac.getCurso().getNombre()};
                mdTblN.addRow(v);
            });
        }
    }

    private void iniciarTbls() {
        String[] t = {"Materia", "Curso"};
        String[] t2 = {"Materia", "M", "C"};
        String[][] datos = {};
        mdTblA = TblEstilo.modelTblSinEditar(datos, t);
        jd.getTblClasesActuales().setModel(mdTblA);
        TblEstilo.formatoTblMultipleSelec(jd.getTblClasesActuales());
        TblEstilo.columnaMedida(jd.getTblClasesActuales(), 1, 50);

        mdTblN = TblEstilo.modelTblSinEditar(datos, t2);
        TblEstilo.formatoTblMultipleSelec(jd.getTblClasesNuevas());
        jd.getTblClasesNuevas().setModel(mdTblN);
        TblEstilo.columnaMedida(jd.getTblClasesNuevas(), 1, 50);
        TblEstilo.columnaMedida(jd.getTblClasesNuevas(), 2, 50);

        jd.getTblClasesNuevas().getColumnModel().getColumn(1).setCellRenderer(new TblRenderMatricula(1));
        jd.getTblClasesNuevas().getColumnModel().getColumn(2).setCellRenderer(new TblRenderMatricula(2));
    }

    private void inicarInformacion() {
        jd.getLblAlumno().setText(matricula.getAlumno().getNombreCompleto());
        jd.getLblPeriodo().setText(matricula.getPeriodo().getNombre_PerLectivo());
        jd.getLblFecha().setText(matricula.getSoloFecha());
        almnsCurso = almCur.buscarCursosAlmPeriodo(matricula.getAlumno().getId_Alumno(),
                matricula.getPeriodo().getId_PerioLectivo());
        llenarTblMA(almnsCurso);
    }

    private void iniciarJD() {
        jd.setVisible(true);
        jd.setLocationRelativeTo(ctrPrin.getVtnPrin());
        jd.setTitle("Editar matricula");
        ctrPrin.eventoJDCerrar(jd);
    }

}
