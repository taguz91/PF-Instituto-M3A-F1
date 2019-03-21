package controlador.alumno;

import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.alumno.AlumnoCarreraBD;
import modelo.alumno.AlumnoCarreraMD;
import modelo.curso.CursoBD;
import modelo.curso.CursoMD;
import modelo.estilo.TblEstilo;
import modelo.alumno.AlumnoCursoBD;
import modelo.alumno.MallaAlumnoBD;
import modelo.alumno.MallaAlumnoMD;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.validaciones.CmbValidar;
import modelo.validaciones.TxtVBuscador;
import vista.alumno.FrmAlumnoCurso;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class FrmAlumnoCursoCTR {

    private final VtnPrincipal vtnPrin;
    private final FrmAlumnoCurso frmAlmCurso;
    private final AlumnoCursoBD almnCurso;
    private final ConectarDB conecta;
    private final MateriaBD mat;
    private final VtnPrincipalCTR ctrPrin;

    //Modelos para las tablas que seleecionan el curso 
    DefaultTableModel mdMatPen, mdMatSelec, mdAlm;

    //Para cargar los combos de periodo 
    private final PeriodoLectivoBD prd;
    private ArrayList<PeriodoLectivoMD> periodos;
    //Cargar los combos de alumnos  
    private final AlumnoCarreraBD almCar;
    private ArrayList<AlumnoCarreraMD> alumnos;
    //Cargar los cursos  
    private final CursoBD cur;
    private ArrayList<CursoMD> cursosPen;
    private ArrayList<String> nombreCursos;
    //Para guardarlas materias que registraremos en base de datos  
    private ArrayList<CursoMD> cursosSelec = new ArrayList();
    //Guardaremos la malla que tiene pasada un alumno  
    MallaAlumnoBD mallaAlm;
    private ArrayList<MallaAlumnoMD> materiasAlmn;

    public FrmAlumnoCursoCTR(VtnPrincipal vtnPrin, FrmAlumnoCurso frmAlmCurso, ConectarDB conecta, VtnPrincipalCTR ctrPrin) {
        this.vtnPrin = vtnPrin;
        this.frmAlmCurso = frmAlmCurso;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        //Cambiamos el estado del cursos  
        vtnPrin.setCursor(new Cursor(3));
        ctrPrin.estadoCargaFrm("Alumno por curso");
        //Inicializamos todas la clases que usaremos
        this.almnCurso = new AlumnoCursoBD(conecta);
        this.almCar = new AlumnoCarreraBD(conecta);
        this.prd = new PeriodoLectivoBD(conecta);
        this.cur = new CursoBD(conecta);
        this.mallaAlm = new MallaAlumnoBD(conecta);
        this.mat = new MateriaBD(conecta);

        vtnPrin.getDpnlPrincipal().add(frmAlmCurso);
        frmAlmCurso.show();
    }

    public void iniciar() {
        //Cargamos el combo con los periodos 
        cargarCmbPrdLectivo();
        //Ocusltamos los errores 
        ocultarErrores();
        //Pasamos los modelos a las tablas 
        String[] titulo1 = {"Materias no seleccionadas"};
        String[] titulo2 = {"Materias seleccionadas"};
        String[] tituloAlmn = {"Cedula", "Alumnos"};
        String[][] datos1 = {};
        String[][] datos2 = {};
        String[][] datos3 = {};
        mdMatPen = TblEstilo.modelTblSinEditar(datos1, titulo1);
        mdMatSelec = TblEstilo.modelTblSinEditar(datos2, titulo2);
        mdAlm = TblEstilo.modelTblSinEditar(datos3, tituloAlmn);
        frmAlmCurso.getTblMateriasPen().setModel(mdMatPen);
        frmAlmCurso.getTblMateriasSelec().setModel(mdMatSelec);
        frmAlmCurso.getTblAlumnos().setModel(mdAlm);
        TblEstilo.formatoTbl(frmAlmCurso.getTblMateriasPen());
        TblEstilo.formatoTbl(frmAlmCurso.getTblMateriasSelec());
        TblEstilo.formatoTbl(frmAlmCurso.getTblAlumnos());

        //Acciones de los combos  
        frmAlmCurso.getCmbPrdLectivo().addActionListener(e -> clickPrdLectivo());
        frmAlmCurso.getCmbCurso().addActionListener(e -> cargarMaterias());
        //Le damos una accion a los botones de clase
        frmAlmCurso.getBtnPasar1().addActionListener(e -> pasarUnaMateria());
        frmAlmCurso.getBtnPasarTodos().addActionListener(e -> pasarTodasMaterias());
        frmAlmCurso.getBtnRegresar1().addActionListener(e -> regresarUnaMateria());
        frmAlmCurso.getBtnRegresarTodos().addActionListener(e -> regresarTodasMaterias());

        //Iniciamos el txtbuscador y el btn  los activamos cuando escojamos un periodo
        buscadoresEstado(false);
        //Inciamos el buscardor 
        frmAlmCurso.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String aguja = frmAlmCurso.getTxtBuscar().getText().trim();
                if (aguja.length() > 2) {
                    buscarAlumnos(aguja);
                } else {
                    //Si no tipamos mas de tres letras borramos los datos
                    mdAlm.setRowCount(0);
                }
            }
        });
        frmAlmCurso.getBtnBuscar().addActionListener(e -> {
            buscarAlumnos(frmAlmCurso.getTxtBuscar().getText().trim());
        });

        frmAlmCurso.getTxtBuscar().addKeyListener(new TxtVBuscador(frmAlmCurso.getTxtBuscar(),
                frmAlmCurso.getLblErrorBuscar()));

        frmAlmCurso.getCmbPrdLectivo().addActionListener(new CmbValidar(frmAlmCurso.getCmbPrdLectivo(),
                frmAlmCurso.getLblErrorPrdLectivo()));

        frmAlmCurso.getTblAlumnos().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickTblAlumnos();
            }
        });

        frmAlmCurso.getBtnMtCursadas().addActionListener(e -> mostrarInformacion("C"));
        frmAlmCurso.getBtnGuardar().addActionListener(e -> guardar());
        //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaFrmFin("Alumno por curso");
    }

    private void guardar() {
        boolean guardar = true;
        if (cursosSelec.isEmpty()) {
            guardar = false;
        }

        int posAlm = frmAlmCurso.getTblAlumnos().getSelectedRow();

        if (posAlm < 0) {
            guardar = false;
        }

        int posCar = frmAlmCurso.getCmbPrdLectivo().getSelectedIndex();
        if (posCar < 1) {
            guardar = false;
        }

        if (guardar) {
            System.out.println("Ingresaremos " + cursosSelec.size() + " cursos.");

            cursosSelec.forEach(c -> {
                //Guardamos el alumno en su curso 
                almnCurso.ingresarAlmnCurso(alumnos.get(posAlm).getId(), c.getId_curso());
                //Actualizamos el numero de matricula
                mallaAlm.actualizarNumMatricula(alumnos.get(posAlm).getAlumno().getId_Alumno(),
                        periodos.get(posCar - 1).getCarrera().getId(), c.getId_materia().getId());

                mallaAlm.actualizarEstadoMallaAlmn(alumnos.get(posAlm).getAlumno().getId_Alumno(),
                        periodos.get(posCar - 1).getCarrera().getId(), c.getId_materia().getId());
            });
            JOptionPane.showMessageDialog(null, "Se guardo el alumno en el curso, correctamente");
        }
    }

    private void ocultarErrores() {
        frmAlmCurso.getLblErrorBuscar().setVisible(false);
        frmAlmCurso.getLblErrorPrdLectivo().setVisible(false);
    }

    private void buscadoresEstado(boolean estado) {
        frmAlmCurso.getTxtBuscar().setEnabled(estado);
        frmAlmCurso.getBtnBuscar().setEnabled(estado);
        frmAlmCurso.getBtnMtCursadas().setEnabled(estado);
    }

    private void cargarCmbPrdLectivo() {
        periodos = prd.cargarPeriodos();
        if (periodos != null) {
            frmAlmCurso.getCmbPrdLectivo().removeAllItems();
            frmAlmCurso.getCmbPrdLectivo().addItem("Seleccione");
            periodos.forEach((p) -> {
                frmAlmCurso.getCmbPrdLectivo().addItem(p.getNombre_PerLectivo());
            });
        }
    }

    private void clickPrdLectivo() {
        int posPrd = frmAlmCurso.getCmbPrdLectivo().getSelectedIndex();
        if (posPrd > 0) {
            buscadoresEstado(true);
        } else {
            buscadoresEstado(false);
        }
    }

    private void buscarAlumnos(String aguja) {
        int posPrd = frmAlmCurso.getCmbPrdLectivo().getSelectedIndex();
        if (posPrd > 0) {
            alumnos = almCar.buscarAlumnoCarrera(periodos.get(posPrd - 1).getCarrera().getId(),
                    aguja);
            llenarTblAlumnos(alumnos);
        }
    }

    /*
    *Esto se ejecutara al seleccionar un alumno 
     */
    private void clickTblAlumnos() {
        int posAl = frmAlmCurso.getTblAlumnos().getSelectedRow();
        if (posAl >= 0) {
            //Vemos si el alumno esta matriculado en una materia
            materiasAlmn = mallaAlm.cargarMallaAlumnoPorEstado(alumnos.get(posAl).getId(), "M");
            if (!materiasAlmn.isEmpty()) {
                //Borramos los cursos que posiblemente carguemos antes
                frmAlmCurso.getCmbCurso().removeAllItems();
                int s = JOptionPane.showOptionDialog(vtnPrin,
                        "Alumno matriculado en " + materiasAlmn.get(posAl).getMallaCiclo() + " ciclo. \n"
                        + "¿Ver materias en las que se encuentra matriculado?", "Alumno matriculado",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new Object[]{"Ingresar otro alumno", "Ver materias", "Cancelar"}, "Ver materias");
                if (s == 0) {
                    frmAlmCurso.getTxtBuscar().setText("");
                    mdAlm.setRowCount(0);
                } else if (s == 1) {
                    mostrarInformacion("M");
                }

            } else {
                //Si no esta matriculado miramos la materias que a cursado 
                materiasAlmn = mallaAlm.cargarMallaAlumnoPorEstado(alumnos.get(posAl).getId(), "C");
                if (mallaAlm != null) {
                    int ciclo = 0;
                    for (int i = 0; i < materiasAlmn.size(); i++) {
                        if (materiasAlmn.get(i).getMallaCiclo() > ciclo) {
                            ciclo = materiasAlmn.get(i).getMallaCiclo();
                        }
                    }
                    int posPrd = frmAlmCurso.getCmbPrdLectivo().getSelectedIndex();
                    clasificarMaterias(ciclo, posAl, posPrd);
                }
            }
        }
    }

    private void clasificarMaterias(int ciclo, int posAlmn, int posPrd) {
        ArrayList<MallaAlumnoMD> malla = mallaAlm.cargarMallaAlumnoPorEstadoYCiclo(
                alumnos.get(posAlmn).getId(), "C", ciclo);  
        ArrayList<MateriaMD> materias = mat.cargarMateriaPorCarreraCiclo(
                periodos.get(posPrd - 1).getCarrera().getId(), ciclo); 
        if (malla.size() == materias.size()) {
            //JOptionPane.showMessageDialog(null, "Paso todas las materias.");
            cargarCmbCursos(posPrd, ciclo);
        }
        
        ArrayList<MallaAlumnoMD> mallaPerdidas = mallaAlm.cargarMallaAlumnoPorEstadoYCiclo(
                alumnos.get(posAlmn).getId(), "R", ciclo);  
        System.out.println("Reprobo "+mallaPerdidas.size());
    }

    private void mostrarInformacion(String estado) {
        int posAlm = frmAlmCurso.getTblAlumnos().getSelectedRow();
        if (posAlm >= 0) {
            //Mostramos las materias que curso
            JDMateriasCursadasCTR jdCtr = new JDMateriasCursadasCTR(vtnPrin, alumnos.get(posAlm), mallaAlm, estado);
            jdCtr.iniciar();
        } else {
            JOptionPane.showMessageDialog(vtnPrin, "Primero debe seleccionar un alumno.");
        }
    }

    private void llenarTblAlumnos(ArrayList<AlumnoCarreraMD> alumnos) {
        mdAlm.setRowCount(0);
        if (alumnos != null) {
            alumnos.forEach(a -> {
                Object[] valores = {a.getAlumno().getIdentificacion(),
                    a.getAlumno().getPrimerApellido() + " "
                    + a.getAlumno().getSegundoApellido() + " "
                    + a.getAlumno().getPrimerNombre() + " "
                    + a.getAlumno().getSegundoNombre()};
                mdAlm.addRow(valores);
            });
        }
    }

    private void cargarCmbCursos(int posPrd, int ciclo) {
        frmAlmCurso.getCmbCurso().removeAllItems();
        nombreCursos = cur.cargarNombreCursosPorPeriodo(periodos.get(posPrd - 1).getId_PerioLectivo(), ciclo);
        if (nombreCursos != null) {
            frmAlmCurso.getCmbCurso().addItem("Seleccione");
            nombreCursos.forEach(c -> {
                frmAlmCurso.getCmbCurso().addItem(c);
            });
        }
    }

    private void cargarMaterias() {
        int posPrd = frmAlmCurso.getCmbPrdLectivo().getSelectedIndex();
        int posCurso = frmAlmCurso.getCmbCurso().getSelectedIndex();
        //Borramos los datos existentes

        if (posPrd > 0 && posCurso > 0) {
            cursosPen = cur.cargarCursosPorNombreYPrdLectivo(
                    frmAlmCurso.getCmbCurso().getSelectedItem().toString(),
                    periodos.get(posPrd - 1).getId_PerioLectivo());
            llenarTblMatPen(cursosPen);
        }
    }

    private void llenarTblMatPen(ArrayList<CursoMD> cursos) {
        mdMatPen.setRowCount(0);
        if (cursos != null) {
            cursos.forEach(c -> {
                Object[] valores = {c.getId_materia().getNombre()};
                mdMatPen.addRow(valores);
            });
        }
    }

    private void llenarTblMatSelec(ArrayList<CursoMD> cursosSelec) {
        mdMatSelec.setRowCount(0);
        if (cursosSelec != null) {
            cursosSelec.forEach(c -> {
                Object[] valores = {c.getId_materia().getNombre()};
                mdMatSelec.addRow(valores);
            });
        }
    }

    private void pasarUnaMateria() {
        int posMat = frmAlmCurso.getTblMateriasPen().getSelectedRow();
        if (posMat >= 0) {
            //Agregamos en la lista
            cursosSelec.add(cursosPen.get(posMat));
            //Quitamos de la lista que los almacena 
            cursosPen.remove(posMat);
            //Rellenamos las dos tablas con los item seleccionados
            llenarTblMatPen(cursosPen);
            llenarTblMatSelec(cursosSelec);
        }
    }

    private void pasarTodasMaterias() {
        //Pasamos todos las materias que nos quedan en la tabla cursos 
        //La rrellenamos en cursos seleccionados
        cursosPen.forEach(c -> cursosSelec.add(c));
        //Borramos todas las materias de cursos 
        cursosPen = new ArrayList();
        llenarTblMatPen(cursosPen);
        llenarTblMatSelec(cursosSelec);
    }

    private void regresarUnaMateria() {
        int posMat = frmAlmCurso.getTblMateriasSelec().getSelectedRow();
        if (posMat >= 0) {
            //Pasamos u cursod e selecciona a la lista de no seleccinados
            cursosPen.add(cursosSelec.get(posMat));
            //Eliminamos la materia que fue pasada 
            cursosSelec.remove(posMat);
            //Volvemos a llenar las tablas
            llenarTblMatPen(cursosPen);
            llenarTblMatSelec(cursosSelec);
        }
    }

    private void regresarTodasMaterias() {
        cursosSelec.forEach(c -> cursosPen.add(c));
        //Reiniciamos el array para borrar todos los datos
        cursosSelec = new ArrayList();
        llenarTblMatPen(cursosPen);
        llenarTblMatSelec(cursosSelec);
    }

}
