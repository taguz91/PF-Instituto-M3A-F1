package controlador.curso;

import java.util.ArrayList;
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
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class FrmCursoCTR {

    private final VtnPrincipal vtnPrin;
    private final FrmCurso frmCurso;
    private final CursoBD curso;
    //Para saber si estamos editando  
    private boolean editando = false;
    private int idCurso = 0;

    //Para cargar los datos en la tabla  
    DocenteBD docen = new DocenteBD();
    ArrayList<DocenteMD> docentes;
    //Para carar periodos lectivos 
    PeriodoLectivoBD prd = new PeriodoLectivoBD();
    ArrayList<PeriodoLectivoMD> periodos;
    //Para cargar materias 
    MateriaBD mt = new MateriaBD();
    ArrayList<MateriaMD> materias;
    //Para cargar jornadas  
    JornadaBD jd = new JornadaBD();
    ArrayList<JornadaMD> jornadas;

    public FrmCursoCTR(VtnPrincipal vtnPrin, FrmCurso frmCurso) {
        this.vtnPrin = vtnPrin;
        this.frmCurso = frmCurso;
        this.curso = new CursoBD();

        vtnPrin.getDpnlPrincipal().add(frmCurso);
        frmCurso.show();
    }

    public void iniciar() {
        //Ocusltamos los errores 
        ocultarErrores();
        //Inicializamos 
        cargarCmbPrdLectivo();
        actualizarCmbMaterias();
        actualizarCmbDocentes();
        cargarCmbJornada();

        frmCurso.getCbxPeriodoLectivo().addActionListener(e -> actualizarCmbMaterias());
        frmCurso.getCbxCiclo().addActionListener(e -> actualizarCmbMaterias());
        frmCurso.getCbxMateria().addActionListener(e -> actualizarCmbDocentes());
        //Le damos accion a los botones  
        frmCurso.getBtnGuardar().addActionListener(e -> guardarYSalir());
        frmCurso.getBtnGuardarContinuar().addActionListener(e -> guardarSeguirIngresando());
    }

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

    private void cargarCmbPrdLectivo() {
        periodos = prd.cargarPeriodos();
        if (periodos != null) {
            frmCurso.getCbxPeriodoLectivo().removeAllItems();
            frmCurso.getCbxPeriodoLectivo().addItem("Seleccione");
            periodos.forEach((p) -> {
                frmCurso.getCbxPeriodoLectivo().addItem(p.getNombre_PerLectivo());
            });
        }
    }

    //Con este emtodo actualizamos los datos del combo materias  
    //De igual manera en los del combo docente  
    //Materia consultamos todas las materias de ese cilo 
    private void actualizarCmbMaterias() {
        //Activamos el combo de materias
        frmCurso.getCbxMateria().setEnabled(true);
        int posPr = frmCurso.getCbxPeriodoLectivo().getSelectedIndex();
        int posCi = frmCurso.getCbxCiclo().getSelectedIndex();
        if (posPr > 0 && posCi == 0) {
            //Consultamos las materias para cargarlo co el combo materias
            materias = mt.cargarMateriaPorCarrera(periodos.get(posPr - 1).getCarrera().getId());
            cargarCmbMaterias(materias);
        } else if (posPr > 0 && posCi > 0) {
            int ciclo = Integer.parseInt(frmCurso.getCbxCiclo().getSelectedItem().toString());

            materias = mt.cargarMateriaPorCarreraCiclo(periodos.get(posPr - 1).getCarrera().getId(), ciclo);
            cargarCmbMaterias(materias);
        } else {
            frmCurso.getCbxMateria().removeAllItems();
            frmCurso.getCbxMateria().setEnabled(false);
        }

    }

    private void actualizarCmbDocentes() {
        int posMat = frmCurso.getCbxMateria().getSelectedIndex();
        frmCurso.getCbxDocente().setEnabled(true);
        if (posMat > 0) {
            docentes = docen.cargarDocentesPorMateria(materias.get(posMat - 1).getId());
            cargarCmbDocente(docentes);
        } else {
            frmCurso.getCbxDocente().setEnabled(false);
            frmCurso.getCbxDocente().removeAllItems();
        }
    }

    private void cargarCmbMaterias(ArrayList<MateriaMD> materias) {
        if (materias != null) {
            frmCurso.getCbxMateria().removeAllItems();
            frmCurso.getCbxMateria().addItem("Seleccione");
            materias.forEach((m) -> {
                frmCurso.getCbxMateria().addItem(m.getNombre());
            });
        }
    }

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

    private void cargarCmbJornada() {
        jornadas = jd.cargarJornadas();
        if (jornadas != null) {
            frmCurso.getCbxJornada().removeAllItems();
            frmCurso.getCbxJornada().addItem("Seleccione");
            jornadas.forEach((j) -> {
                frmCurso.getCbxJornada().addItem(j.getNombre());
            });
        }
    }

    public void guardarSeguirIngresando() {
        guardar();
        actualizarCmbMaterias();
        actualizarCmbDocentes();
        frmCurso.getCbxPermisoIng().setSelected(false);
    }

    public void guardarYSalir() {
        guardar();
        frmCurso.dispose();
    }

    private void guardar() {
        //Para validar todo 
        boolean guardar = true;

        int posPrd = frmCurso.getCbxPeriodoLectivo().getSelectedIndex();
        int posJrd = frmCurso.getCbxJornada().getSelectedIndex();
        int posCic = frmCurso.getCbxCiclo().getSelectedIndex();
        int posPrl = frmCurso.getCbxParalelo().getSelectedIndex();
        int posMat = frmCurso.getCbxMateria().getSelectedIndex();
        int posDoc = frmCurso.getCbxDocente().getSelectedIndex();
        String capacidad = frmCurso.getTxtCapacidad().getText();
        //Este es un check box  
        boolean permisoIng = frmCurso.getCbxPermisoIng().isSelected();
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
                CursoMD existeCurso = curso.existeDocenteMateria(materias.get(posMat - 1).getId(),
                        docentes.get(posDoc - 1).getIdDocente(), jornadas.get(posJrd - 1).getId(),
                        periodos.get(posPrd - 1).getId_PerioLectivo(), ciclo, paralelo);

                if (existeCurso != null) {
                    guardar = false;
                    frmCurso.getLblError().setText("Ya guardo estos datos.");
                    frmCurso.getLblError().setVisible(true);
                } else {
                    frmCurso.getLblError().setVisible(false);
                }

                existeCurso = curso.existeMateriaCursoJornada(materias.get(posMat - 1).getId(), ciclo,
                        jornadas.get(posJrd - 1).getId(), periodos.get(posPrd - 1).getId_PerioLectivo(),
                        paralelo);
                if (existeCurso != null) {
                    guardar = false;
                    frmCurso.getLblError().setText("Ya guardo esta materia en el curso.");
                    frmCurso.getLblError().setVisible(true);
                } else {
                    frmCurso.getLblError().setVisible(false);
                }
            }
        }

        if (guardar) {

            String nombre = jornadas.get(posJrd - 1).getNombre().charAt(0) + "" + ciclo + "" + paralelo;

            curso.setCurso_capacidad(Integer.parseInt(capacidad));
            curso.setCurso_ciclo(ciclo);
            curso.setCurso_jornada(jornadas.get(posJrd - 1));
            curso.setCurso_nombre(nombre);
            curso.setId_docente(docentes.get(posDoc - 1));
            curso.setId_materia(materias.get(posMat - 1));
            curso.setId_prd_lectivo(periodos.get(posPrd - 1));
            curso.setParalelo(paralelo);
            curso.setPermiso_ingreso_nt(permisoIng);

            if (!editando) {
                //Guardamos persona  
                curso.guardarCurso();
            } else {
                if (idCurso > 0) {
                    //Editamos curso
                    curso.editarCurso(idCurso);
                }
            }

        }
    }

    public void editar(CursoMD c) {
        editando = true;
        idCurso = c.getId_curso();

        frmCurso.getCbxPeriodoLectivo().setSelectedItem(c.getId_prd_lectivo().getNombre_PerLectivo());
        frmCurso.getCbxJornada().setSelectedItem(c.getCurso_jornada().getNombre());
        frmCurso.getCbxCiclo().setSelectedItem(c.getCurso_ciclo() + "");
        frmCurso.getCbxParalelo().setSelectedItem(c.getParalelo());
        frmCurso.getCbxMateria().setSelectedItem(c.getId_materia().getNombre());
        frmCurso.getCbxDocente().setSelectedItem(c.getId_docente().getPrimerNombre() + " "
                + c.getId_docente().getPrimerApellido());
        frmCurso.getTxtCapacidad().setText(c.getCurso_capacidad() + "");
        frmCurso.getCbxPermisoIng().setSelected(c.isPermiso_ingreso_nt());

    }

}
