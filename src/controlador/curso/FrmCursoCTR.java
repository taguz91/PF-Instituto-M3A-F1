package controlador.curso;

import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import java.util.ArrayList;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import modelo.curso.CursoBD;
import modelo.curso.CursoMD;
import modelo.jornada.JornadaBD;
import modelo.jornada.JornadaMD;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import modelo.validaciones.Validar;
import vista.curso.FrmCurso;

/**
 *
 * @author Johnny
 */
public class FrmCursoCTR extends DCTR {

    private final FrmCurso frmCurso;
    private final VtnCursoCTR ctrCurso;
    private final CursoBD CBD = CursoBD.single();
    //Para saber si estamos editando  
    private boolean editando = false;
    private int idCurso = 0;

    //Para cargar los datos en la tabla  
    private final DocenteBD DBD = DocenteBD.single();
    private ArrayList<DocenteMD> docentes;
    //Para carar periodos lectivos 
    private final PeriodoLectivoBD PLBD = PeriodoLectivoBD.single();
    private ArrayList<PeriodoLectivoMD> periodos;
    //Para cargar materias 
    private final MateriaBD MTBD = MateriaBD.single();
    private ArrayList<MateriaMD> materias;
    //Para cargar jornadas  
    private final JornadaBD JBD = JornadaBD.single();
    private ArrayList<JornadaMD> jornadas;
    //Private void ciclos de una carrera
    private ArrayList<Integer> ciclos;

    public FrmCursoCTR(FrmCurso frmCurso, VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
        this.frmCurso = frmCurso;
        this.ctrCurso = null;
    }

    public FrmCursoCTR(FrmCurso frmCurso, VtnPrincipalCTR ctrPrin,
            VtnCursoCTR ctrCurso) {
        super(ctrPrin);
        this.frmCurso = frmCurso;
        this.ctrCurso = ctrCurso;
    }

    /**
     * Iniciamos todas las dependencias de esta ventana
     */
    public void iniciar() {
        //Ocusltamos los errores 
        ocultarErrores();
        //Inicializamos 
        cargarCmbPrdLectivo();
        actualizarCmbMaterias();
        actualizarCmbDocentes();
        cargarCmbJornada();

        frmCurso.getCbxPeriodoLectivo().addActionListener(e -> clickCmbPrd());
        frmCurso.getCbxCiclo().addActionListener(e -> actualizarCmbMaterias());
        frmCurso.getCbxMateria().addActionListener(e -> actualizarCmbDocentes());
        //Le damos accion a los botones  
        frmCurso.getBtnGuardar().addActionListener(e -> guardarYSalir());
        frmCurso.getBtnGuardarContinuar().addActionListener(e -> guardarSeguirIngresando());

        //Al cerrar la ventana se le dara una accion 
        frmCurso.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                if (ctrCurso != null) {
                    ctrCurso.actualizarVtn();
                }
            }
        });

        ctrPrin.agregarVtn(frmCurso);
    }

    /**
     * Ocultamos todos los errores que existen en este formulario
     */
    private void ocultarErrores() {
        frmCurso.getLblError().setVisible(false);
        frmCurso.getLblErrorCapacidad().setVisible(false);
        frmCurso.getLblErrorCiclo().setVisible(false);
        frmCurso.getLblErrorDocente().setVisible(false);
        frmCurso.getLblErrorJornada().setVisible(false);
        frmCurso.getLblErrorMateria().setVisible(false);
        frmCurso.getLblErrorParalelo().setVisible(false);
        frmCurso.getLblErrorPrdLectivo().setVisible(false);
    }

    /**
     * Cargamos el combo de periodos lectivos
     */
    private void cargarCmbPrdLectivo() {
        periodos = PLBD.cargarPrdParaCmbFrm();
        if (periodos != null) {
            frmCurso.getCbxPeriodoLectivo().removeAllItems();
            frmCurso.getCbxPeriodoLectivo().addItem("Seleccione");
            periodos.forEach((p) -> {
                frmCurso.getCbxPeriodoLectivo().addItem(p.getNombre());
            });
        }
    }

    /**
     * Al seleccionar un periodo se cargan todos los ciclos.
     */
    private void clickCmbPrd() {
        int posPr = frmCurso.getCbxPeriodoLectivo().getSelectedIndex();
        llenarCmbCiclos(posPr);
    }

    /**
     * Con este emtodo actualizamos los datos del combo materias De igual manera
     * en los del combo docente Materia consultamos todas las materias de ese
     * cilo
     */
    private void actualizarCmbMaterias() {
        //Activamos el combo de materias
        frmCurso.getCbxMateria().setEnabled(true);
        int posPr = frmCurso.getCbxPeriodoLectivo().getSelectedIndex();
        int posCi = frmCurso.getCbxCiclo().getSelectedIndex();
        if (posPr > 0 && posCi > 0) {
            int ciclo = Integer.parseInt(frmCurso.getCbxCiclo().getSelectedItem().toString());

            materias = MTBD.cargarMateriaPorCarreraCiclo(periodos.get(posPr - 1).getCarrera().getId(), ciclo);
            cargarCmbMaterias(materias);
        } else {
            frmCurso.getCbxMateria().removeAllItems();
            frmCurso.getCbxMateria().setEnabled(false);
        }

    }

    /**
     * Llenamso el combo de con los ciclos de la carrera
     *
     * @param posPrd
     */
    private void llenarCmbCiclos(int posPrd) {
        ciclos = MTBD.cargarCiclosCarrera(periodos.get(posPrd - 1).getCarrera().getId());
        frmCurso.getCbxCiclo().removeAllItems();
        if (ciclos != null) {
            frmCurso.getCbxCiclo().addItem("Seleccione");
            ciclos.forEach(c -> {
                frmCurso.getCbxCiclo().addItem(c + "");
            });
        }
    }

    /**
     * Actualizamos el combo de de docentes correspondiendo a la materia que
     * Seleccionemos
     */
    private void actualizarCmbDocentes() {
        int posMat = frmCurso.getCbxMateria().getSelectedIndex();
        frmCurso.getCbxDocente().setEnabled(true);
        if (posMat > 0) {
            docentes = DBD.cargarDocentesPorMateria(materias.get(posMat - 1).getId());
            cargarCmbDocente(docentes);
        } else {
            frmCurso.getCbxDocente().setEnabled(false);
            frmCurso.getCbxDocente().removeAllItems();
        }
    }

    /**
     * Cargamos el combo con las materias seleccionadas
     *
     * @param materias
     */
    private void cargarCmbMaterias(ArrayList<MateriaMD> materias) {
        if (materias != null) {
            frmCurso.getCbxMateria().removeAllItems();
            frmCurso.getCbxMateria().addItem("Seleccione");
            materias.forEach((m) -> {
                frmCurso.getCbxMateria().addItem(m.getNombre());
            });
        }
    }

    /**
     * Cargamos el combo de docentes con los docentes que le pasemos por
     * parametro
     *
     * @param docentes
     */
    private void cargarCmbDocente(ArrayList<DocenteMD> docentes) {
        if (docentes != null) {
            frmCurso.getCbxDocente().removeAllItems();
            frmCurso.getCbxDocente().addItem("Seleccione");
            docentes.forEach((d) -> {
                frmCurso.getCbxDocente().addItem(d.getPrimerNombre() + " "
                        + d.getPrimerApellido());
            });
        }
    }

    /**
     * Cargamos las jornadas
     */
    private void cargarCmbJornada() {
        jornadas = JBD.cargarJornadas();
        if (jornadas != null) {
            frmCurso.getCbxJornada().removeAllItems();
            frmCurso.getCbxJornada().addItem("Seleccione");
            jornadas.forEach((j) -> {
                frmCurso.getCbxJornada().addItem(j.getNombre());
            });
        }
    }

    /**
     * Guardamos todo y actualizamos el combo de materias y docentes
     */
    public void guardarSeguirIngresando() {
        if (guardar()) {
            actualizarCmbMaterias();
            actualizarCmbDocentes();
            frmCurso.getLblError().setVisible(false);
        }
    }

    /**
     * Guardamos y cerramos todo
     */
    public void guardarYSalir() {
        if (guardar()) {
            frmCurso.dispose();
            ctrPrin.cerradoJIF();
            if (ctrCurso != null) {
                ctrCurso.actualizarVtn();
            }
        }
    }

    /**
     * Al dar click en guardar validamos que todo el formulario este
     * correctamente Tambien que no exista el curso Ni la materia en ese curso
     *
     * @return
     */
    private boolean guardar() {
        //Para validar todo 
        boolean guardar = true;

        int posPrd = frmCurso.getCbxPeriodoLectivo().getSelectedIndex();
        int posJrd = frmCurso.getCbxJornada().getSelectedIndex();
        int posCic = frmCurso.getCbxCiclo().getSelectedIndex();
        int posPrl = frmCurso.getCbxParalelo().getSelectedIndex();
        int posMat = frmCurso.getCbxMateria().getSelectedIndex();
        int posDoc = frmCurso.getCbxDocente().getSelectedIndex();
        String capacidad = frmCurso.getTxtCapacidad().getText();
        int ciclo = 0;
        String paralelo = "SP";

        if (!Validar.esNumeros(capacidad)) {
            guardar = false;
            frmCurso.getLblErrorCapacidad().setVisible(true);
        } else {
            frmCurso.getLblErrorCapacidad().setVisible(false);
        }

        if (posPrd < 1 || posJrd < 1 || posCic < 1 || posPrl < 1 || posMat < 1 || posDoc < 1) {
            guardar = false;
            frmCurso.getLblError().setText("Todos los campos son obligatorios.");
            frmCurso.getLblError().setVisible(true);
        } else {
            frmCurso.getLblError().setVisible(false);
            ciclo = Integer.parseInt(frmCurso.getCbxCiclo().getSelectedItem().toString());
            paralelo = frmCurso.getCbxParalelo().getSelectedItem().toString();

            if (!editando) {
                CursoMD existeCurso = CBD.existeDocenteMateria(materias.get(posMat - 1).getId(),
                        docentes.get(posDoc - 1).getIdDocente(), jornadas.get(posJrd - 1).getId(),
                        periodos.get(posPrd - 1).getID(), ciclo, paralelo);

                if (existeCurso != null) {
                    guardar = false;
                    frmCurso.getLblError().setText("Datos ya guardados.");
                    frmCurso.getLblError().setVisible(true);
                } else {
                    frmCurso.getLblError().setVisible(false);

                    existeCurso = CBD.existeMateriaCursoJornada(materias.get(posMat - 1).getId(), ciclo,
                            jornadas.get(posJrd - 1).getId(), periodos.get(posPrd - 1).getID(),
                            paralelo);
                    if (existeCurso != null) {
                        guardar = false;
                        frmCurso.getLblError().setText("Este curso ya tiene guardado: " + materias.get(posMat - 1).getNombre() + ".");
                        frmCurso.getLblError().setVisible(true);
                    } else {
                        frmCurso.getLblError().setVisible(false);
                    }
                }

            }
        }

        if (guardar) {
            CursoMD curso = new CursoMD();
            String nombre = jornadas.get(posJrd - 1).getNombre().charAt(0) + "" + ciclo + "" + paralelo;

            curso.setCapacidad(Integer.parseInt(capacidad));
            curso.setCiclo(ciclo);
            curso.setJornada(jornadas.get(posJrd - 1));
            curso.setNombre(nombre);
            curso.setDocente(docentes.get(posDoc - 1));
            curso.setMateria(materias.get(posMat - 1));
            curso.setPeriodo(periodos.get(posPrd - 1));
            curso.setParalelo(paralelo);

            if (!editando) {
                CBD.guardarCurso(curso);
            } else {
                if (idCurso > 0) {
                    //Editamos curso
                    curso.setId(idCurso);
                    CBD.editarCurso(curso);
                    editando = false;
                }
            }
        }
        return guardar;
    }

    /**
     * Editamos el curso Cargamos todo el formulario con los datos que nos den
     * de curso desde la tabla
     *
     * @param c
     */
    public void editar(CursoMD c) {
        editando = true;
        idCurso = c.getId();
        int j = 0;
        switch (c.getNombre().charAt(0)) {
            case 'M':
                j = 1;
                break;
            case 'V':
                j = 2;
                break;
            case 'N':
                j = 3;
                break;
        }
        //Ocultamos el btn de guardar y continuar
        frmCurso.getBtnGuardarContinuar().setVisible(false);

        frmCurso.getCbxPeriodoLectivo().setSelectedItem(c.getPeriodo().getNombre());
        frmCurso.getCbxJornada().setSelectedIndex(j);
        frmCurso.getCbxCiclo().setSelectedItem(c.getCiclo() + "");
        frmCurso.getCbxParalelo().setSelectedItem(c.getNombre().charAt(2) + "");
        frmCurso.getCbxMateria().setSelectedItem(c.getMateria().getNombre());
        frmCurso.getCbxDocente().setSelectedItem(c.getDocente().getPrimerNombre() + " "
                + c.getDocente().getPrimerApellido());
        frmCurso.getTxtCapacidad().setText(c.getCapacidad() + "");
    }

}
