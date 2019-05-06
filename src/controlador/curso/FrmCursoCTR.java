package controlador.curso;

import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
import java.util.ArrayList;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import modelo.ConectarDB;
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

    private final FrmCurso frmCurso;
    private final VtnCursoCTR ctrCurso;
    private final CursoBD curso;
    private final VtnPrincipalCTR ctrPrin;
    //Para saber si estamos editando  
    private boolean editando = false;
    private int idCurso = 0;

    //Para cargar los datos en la tabla  
    private final DocenteBD docen;
    private ArrayList<DocenteMD> docentes;
    //Para carar periodos lectivos 
    private final PeriodoLectivoBD prd;
    private ArrayList<PeriodoLectivoMD> periodos;
    //Para cargar materias 
    private final MateriaBD mt;
    private ArrayList<MateriaMD> materias;
    //Para cargar jornadas  
    private final JornadaBD jd;
    private ArrayList<JornadaMD> jornadas;

    public FrmCursoCTR(VtnPrincipal vtnPrin, FrmCurso frmCurso, ConectarDB conecta, VtnPrincipalCTR ctrPrin) {
        this.frmCurso = frmCurso;
        this.ctrPrin = ctrPrin;

        this.curso = new CursoBD(conecta);
        this.docen = new DocenteBD(conecta);
        this.prd = new PeriodoLectivoBD(conecta);
        this.mt = new MateriaBD(conecta);
        this.jd = new JornadaBD(conecta);
        this.ctrCurso = null;

        vtnPrin.getDpnlPrincipal().add(frmCurso);
        frmCurso.show();
    }

    public FrmCursoCTR(VtnPrincipal vtnPrin, FrmCurso frmCurso, ConectarDB conecta, VtnPrincipalCTR ctrPrin,
            VtnCursoCTR ctrCurso) {
        this.frmCurso = frmCurso;
        this.ctrPrin = ctrPrin;

        this.curso = new CursoBD(conecta);
        this.docen = new DocenteBD(conecta);
        this.prd = new PeriodoLectivoBD(conecta);
        this.mt = new MateriaBD(conecta);
        this.jd = new JornadaBD(conecta);
        this.ctrCurso = ctrCurso;

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

        //Al cerrar la ventana se le dara una accion 
        frmCurso.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                if (ctrCurso != null) {
                    ctrCurso.actualizarVtn();
                }
            }
        });
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
        periodos = prd.cargarPrdParaCmbFrm();
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
        if (guardar()) {
            actualizarCmbMaterias();
            actualizarCmbDocentes();
            frmCurso.getLblError().setVisible(false);
        } else {
            System.out.println("No se pudo guardar, posiblmente ");
        }
    }

    public void guardarYSalir() {
        if (guardar()) {
            frmCurso.dispose();
            ctrPrin.cerradoJIF();
            if (ctrCurso != null) {
                ctrCurso.actualizarVtn();
            }
        } else {
            System.out.println("No se pudo guardar.");
        }
    }

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
                CursoMD existeCurso = curso.existeDocenteMateria(materias.get(posMat - 1).getId(),
                        docentes.get(posDoc - 1).getIdDocente(), jornadas.get(posJrd - 1).getId(),
                        periodos.get(posPrd - 1).getId_PerioLectivo(), ciclo, paralelo);

                if (existeCurso != null) {
                    guardar = false;
                    frmCurso.getLblError().setText("Datos ya guardados.");
                    frmCurso.getLblError().setVisible(true);
                } else {
                    frmCurso.getLblError().setVisible(false);

                    existeCurso = curso.existeMateriaCursoJornada(materias.get(posMat - 1).getId(), ciclo,
                            jornadas.get(posJrd - 1).getId(), periodos.get(posPrd - 1).getId_PerioLectivo(),
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
                //Guardamos persona  
                curso.guardarCurso();

            } else {
                if (idCurso > 0) {
                    //Editamos curso
                    curso.editarCurso(idCurso);
                    editando = false;
                }
            }
        }
        return guardar;
    }

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

        frmCurso.getCbxPeriodoLectivo().setSelectedItem(c.getPeriodo().getNombre_PerLectivo());
        frmCurso.getCbxJornada().setSelectedIndex(j);
        frmCurso.getCbxCiclo().setSelectedItem(c.getCiclo() + "");
        frmCurso.getCbxParalelo().setSelectedItem(c.getNombre().charAt(2) + "");
        frmCurso.getCbxMateria().setSelectedItem(c.getMateria().getNombre());
        frmCurso.getCbxDocente().setSelectedItem(c.getDocente().getPrimerNombre() + " "
                + c.getDocente().getPrimerApellido());
        frmCurso.getTxtCapacidad().setText(c.getCapacidad() + "");
    }

}
