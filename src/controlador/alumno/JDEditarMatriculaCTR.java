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
import modelo.validaciones.Validar;
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
    private int numMat;

    /**
     * Iniciamos el editar matricula. Se tiene acceso a este formulario
     * unicamente 30 dias despues de que inicia clases
     *
     * @param ctrPrin
     * @param matricula
     */
    public JDEditarMatriculaCTR(VtnPrincipalCTR ctrPrin,
            MatriculaMD matricula) {
        super(ctrPrin);
        this.cur = new CursoBD(ctrPrin.getConecta());
        this.almCur = new AlumnoCursoBD(ctrPrin.getConecta());
        this.cursosNuevos = new ArrayList<>();
        this.matricula = matricula;
        this.jd = new JDEditarMatricula(ctrPrin.getVtnPrin(), false);
    }

    /**
     * Iniciamos todas las dependencias de esta ventana.
     */
    public void iniciar() {
        iniciarTbls();
        inicarInformacion();
        inicarAcciones();
        iniciarJD();
    }

    /**
     * Se pueden remover las materias en los cursos que tendra nuevamente.
     */
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

    /**
     * Se guardan los nuevos cursos que tiene el alumno indicando el nombre del
     * curso y la materia Se pide la confirmacion antes de guardar los cambios.
     */
    private void clickGuardar() {
        if (cursosNuevos != null) {
            almCur.borrarActualizarMatricula();
            nombreCursosN = "";
            numMat = 1;
            cursosNuevos.forEach(ac -> {
                if (ac.getCurso().getCapaciadActual() > 0) {
                    //Agregamos el update en todo el sql
                    almCur.agregarUpdate(ac.getId(), ac.getCurso().getId());

                    nombreCursosN = nombreCursosN + numMat + ": " + ac.getCurso().getMateria().getNombre() + "   Curso: "
                            + ac.getCurso().getNombre() + "   \n";
                    numMat++;
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

    /**
     * Cambiamos al nuevo curso. Buscamos en la base de datos todas las materias
     * de este curso, comparando si la materia seleccionada existe en el nuevo
     * curso, si existe la agregamos al update. Si fue agregado posteriormente
     * se elimna el curso, para ingresar el nuevo. Y todos los nuevos cursos los
     * agregamos a cursos nuevos, para llenar la tabla de nuevos cursos.
     *
     * @param curso
     */
    private void cambiarACurso(String curso) {
        cursos = cur.buscarCursosPorNombreYPrdLectivo(curso,
                matricula.getPeriodo().getId());
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
     * Si el curso ya esta en nuestro array de nuevos cursos lo borramos, para
     * agregar el nuevo.
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
     * Para llenar el nombre de los cursos de un periodo y ciclo en especifico,
     * unicamente llenamos los disponibles excluyendo el curso seleccionado
     *
     * @param ciclo
     */
    private void llenarCursosDisponibles(int ciclo) {
        nomCursos = cur.cargarNombreCursosPorPeriodoCiclo(matricula.getPeriodo().getId(),
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

    /**
     * Al dar click en cambiar curso Seleccionamos el primer curso y obtenemos
     * el ciclo para poder validar que todos los seleccionados esten en el mismo
     * curso, si todos son del mismo ciclo mostramos el combo para que
     * seleccione un curso.
     */
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

    /**
     * Iniciamos las acciones de nuestro formulario
     */
    private void inicarAcciones() {
        jd.getBtnCambiar().addActionListener(e -> clickCambiar());
        jd.getBtnGuardar().addActionListener(e -> clickGuardar());
        jd.getBtnRemover().addActionListener(e -> clickRemover());
        jd.getBtnEditarNumMatricula().addActionListener(e -> editarNumMatricula());
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

    /**
     * Iniciamos las tablas con los titulos que le corresponde
     */
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

    /**
     * Llenamos toda la informacion correspondiente a esta matricula.
     */
    private void inicarInformacion() {
        jd.getLblAlumno().setText(matricula.getAlumno().getNombreCompleto());
        jd.getLblPeriodo().setText(matricula.getPeriodo().getNombre());
        jd.getLblFecha().setText(matricula.getSoloFecha());
        almnsCurso = almCur.buscarCursosAlmPeriodo(matricula.getAlumno().getId_Alumno(),
                matricula.getPeriodo().getId());
        llenarTblMA(almnsCurso);
    }

    /**
     * Iniciamos el JD para mostrarlo en la ventana
     */
    private void iniciarJD() {
        jd.setVisible(true);
        jd.setLocationRelativeTo(ctrPrin.getVtnPrin());
        jd.setTitle("Editar matricula");
        ctrPrin.eventoJDCerrar(jd);
    }

    private void editarNumMatricula() {
        posFila = jd.getTblClasesActuales().getSelectedRow();
        if (posFila >= 0) {
            int id = almnsCurso.get(posFila).getId();
            Object e = JOptionPane.showInputDialog(jd, "Modificara el número de matricula de: \n"
                    + almnsCurso.get(posFila).getCurso().getMateria().getNombre());
            if (Validar.esNumeros(e.toString())) {
                int num = Integer.parseInt(e.toString());
                if (num > 0 && num < 4) {
                    almCur.editarNumMatricula(id, num);
                } else {
                    JOptionPane.showMessageDialog(jd, "El numero de matricula no puede ser \n"
                            + "menor a 1 ni mayor a 3.");
                    editarNumMatricula();
                }
            } else {
                JOptionPane.showMessageDialog(jd, "Solo debe ingresar numeros.");
            }
        }
    }

}
