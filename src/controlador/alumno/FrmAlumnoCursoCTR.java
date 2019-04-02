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
import modelo.materia.MateriaRequisitoBD;
import modelo.materia.MateriaRequisitoMD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.validaciones.CmbValidar;
import modelo.validaciones.TxtVBuscador;
import modelo.validaciones.Validar;
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
    //Esta variable la usaremos para saber cual es el ultimo ciclo que paso 
    //o cual es el ultimo ciclo en el que reprobo una materi para cargar los cursos
    private int cicloCursado = 0;
    private int cicloReprobado = 0;

    //Modelos para las tablas que seleecionan el curso 
    DefaultTableModel mdMatPen, mdMatSelec, mdAlm;

    //Para cargar los combos de periodo 
    private final PeriodoLectivoBD prd;
    private ArrayList<PeriodoLectivoMD> periodos;
    //Cargar los combos de alumnos  
    private final AlumnoCarreraBD almCar;
    private ArrayList<AlumnoCarreraMD> alumnosCarrera;
    //Cargar los cursos  
    private final CursoBD cur;
    private ArrayList<CursoMD> cursosPen;
    private ArrayList<String> nombreCursos;
    //Para guardarlas materias que registraremos en base de datos  
    private ArrayList<CursoMD> cursosSelec = new ArrayList();
    //Guardaremos la malla que tiene pasada un alumno  
    private final MallaAlumnoBD mallaAlm;
    private ArrayList<MallaAlumnoMD> materiasAlmn;
    //Para eliminar las materias en las que ya estoy matriculado  
    private ArrayList<MallaAlumnoMD> mallaPerdidas;
    private ArrayList<MallaAlumnoMD> mallaMatriculadas;
    private ArrayList<MallaAlumnoMD> mallaCursadas;
    private ArrayList<MateriaRequisitoMD> requisitos;
    //Para revisar de que materias son requisitos y si no paso eliminarla 
    private final MateriaRequisitoBD matReq;

    public FrmAlumnoCursoCTR(VtnPrincipal vtnPrin, FrmAlumnoCurso frmAlmCurso, ConectarDB conecta, VtnPrincipalCTR ctrPrin) {
        this.vtnPrin = vtnPrin;
        this.frmAlmCurso = frmAlmCurso;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        //Cambiamos el estado del cursos  
        vtnPrin.setCursor(new Cursor(3));
        ctrPrin.estadoCargaFrm("Alumno por curso");
        ctrPrin.setIconJIFrame(frmAlmCurso);
        //Inicializamos todas la clases que usaremos
        this.almnCurso = new AlumnoCursoBD(conecta);
        this.almCar = new AlumnoCarreraBD(conecta);
        this.prd = new PeriodoLectivoBD(conecta);
        this.cur = new CursoBD(conecta);
        this.mallaAlm = new MallaAlumnoBD(conecta);
        this.mat = new MateriaBD(conecta);
        this.matReq = new MateriaRequisitoBD(conecta);

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
        String[] tituloAlmn = {"Cédula", "Alumnos"};
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
        frmAlmCurso.getBtnReprobadas().addActionListener(e -> clickMateriasReprobadas());

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
                    frmAlmCurso.getBtnReprobadas().setVisible(false);
                }
            }
        });
        frmAlmCurso.getBtnBuscar().addActionListener(e -> {
            buscarAlumnos(frmAlmCurso.getTxtBuscar().getText().trim());
        });

        frmAlmCurso.getTblAlumnos().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickTblAlumnos();
            }
        });

        frmAlmCurso.getBtnMtCursadas().addActionListener(e -> mostrarInformacion("C"));
        frmAlmCurso.getBtnGuardar().addActionListener(e -> guardar());
        
        inicarValidaciones();
        //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaFrmFin("Alumno por curso");
    }

    private void inicarValidaciones() {
        frmAlmCurso.getTxtBuscar().addKeyListener(new TxtVBuscador(frmAlmCurso.getTxtBuscar(),
                frmAlmCurso.getLblErrorBuscar(), frmAlmCurso.getBtnBuscar()));

        frmAlmCurso.getCmbPrdLectivo().addActionListener(new CmbValidar(frmAlmCurso.getCmbPrdLectivo(),
                frmAlmCurso.getLblErrorPrdLectivo()));
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
                almnCurso.ingresarAlmnCurso(alumnosCarrera.get(posAlm).getAlumno().getId_Alumno(), c.getId_curso());
                //Actualizamos el numero de matricula
                mallaAlm.actualizarNumMatricula(alumnosCarrera.get(posAlm).getAlumno().getId_Alumno(),
                        periodos.get(posCar - 1).getCarrera().getId(), c.getId_materia().getId());

                mallaAlm.actualizarEstadoMallaAlmn(alumnosCarrera.get(posAlm).getAlumno().getId_Alumno(),
                        periodos.get(posCar - 1).getCarrera().getId(), c.getId_materia().getId());
            });
            JOptionPane.showMessageDialog(null, "Se guardó el alumno en el curso, correctamente");
            //Reiniciamos todo 
            frmAlmCurso.getTxtBuscar().setText("");
            frmAlmCurso.getCmbCurso().removeAllItems();
            mdAlm.setRowCount(0);
            mdMatPen.setRowCount(0);
            mdMatSelec.setRowCount(0);
            cursosSelec = new ArrayList();
            frmAlmCurso.getBtnReprobadas().setVisible(false);
        }
    }

    private void ocultarErrores() {
        frmAlmCurso.getLblErrorBuscar().setVisible(false);
        frmAlmCurso.getLblErrorPrdLectivo().setVisible(false);
        frmAlmCurso.getBtnReprobadas().setVisible(false);
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
        if (posPrd > 0 && Validar.esLetrasYNumeros(aguja)) {
            alumnosCarrera = almCar.buscarAlumnoCarrera(periodos.get(posPrd - 1).getCarrera().getId(),
                    aguja);
            llenarTblAlumnos(alumnosCarrera);
        }
    }

    /*
    *Esto se ejecutara al seleccionar un alumno 
     */
    private void clickTblAlumnos() {
        int posAl = frmAlmCurso.getTblAlumnos().getSelectedRow();
        int posPrd = frmAlmCurso.getCmbPrdLectivo().getSelectedIndex();
        if (posAl >= 0) {
            //Vemos si el alumno esta matriculado en una materia
            materiasAlmn = mallaAlm.buscarMateriasAlumnoPorEstado(alumnosCarrera.get(posAl).getId(), "M");
            if (!materiasAlmn.isEmpty()) {
                //Borramos los cursos que posiblemente carguemos antes
                frmAlmCurso.getCmbCurso().removeAllItems();
                int s = JOptionPane.showOptionDialog(vtnPrin,
                        "Alumno matriculado en " + materiasAlmn.get(0).getMallaCiclo() + " ciclo. \n"
                        + "¿Ver materias en las que se encuentra matriculado?", "Alumno matriculado",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new Object[]{"Ingresar otro alumno", "Ingresar en otro curso",
                            "Ver materias", "Cancelar"}, "Ver materias");
                switch (s) {
                    case 0:
                        frmAlmCurso.getTxtBuscar().setText("");
                        mdAlm.setRowCount(0);
                        break;
                    case 1:
                        clasificarMaterias(posAl, posPrd);
                        break;
                    case 2:
                        mostrarInformacion("M");
                        break;
                    default:
                        break;
                }

            } else {
                clasificarMaterias(posAl, posPrd);
            }
        }
    }

    private void clasificarMaterias(int posAlmn, int posPrd) {
        //Se reinciia el ciclo en el que esta matriculado
        cicloCursado = 0;
        //Si no esta matriculado miramos la materias que a cursado 
        materiasAlmn = mallaAlm.buscarMateriasAlumnoPorEstado(alumnosCarrera.get(posAlmn).getId(), "C");
        if (mallaAlm != null) {
            for (int i = 0; i < materiasAlmn.size(); i++) {
                if (materiasAlmn.get(i).getMallaCiclo() > cicloCursado) {
                    cicloCursado = materiasAlmn.get(i).getMallaCiclo();
                }
            }
        }
        //Se leasigna el mismo valor si es que no tiene un ciclo reprobado
        cicloReprobado = cicloCursado;
        //Esto lo usamos para saber desde que ciclo cargar el combo de cursos
        mallaPerdidas = mallaAlm.buscarMateriasAlumnoPorEstado(alumnosCarrera.get(posAlmn).getId(), "R");
        System.out.println("Materias que reprobo " + mallaPerdidas.size());

        //Buscamos las materias en las que ya esta matriculado para borrarlas de la tabla
        mallaMatriculadas = mallaAlm.buscarMateriasAlumnoPorEstado(alumnosCarrera.get(posAlmn).getId(), "M");
        System.out.println("Se encuentra matriculado en " + mallaMatriculadas.size());

        //Buscamos todas las materias en las que ya a cursado  para borrarlas de la tabla
        mallaCursadas = mallaAlm.buscarMateriasAlumnoPorEstado(alumnosCarrera.get(posAlmn).getId(), "C");
        System.out.println("Ah cursado " + mallaCursadas.size());

        if (mallaPerdidas.size() > 0) {
            frmAlmCurso.getBtnReprobadas().setVisible(true);
            //Si reprobo una materia se busca el ciclo menor en el que reprobo
            mallaPerdidas.forEach(m -> {
                if (m.getMallaCiclo() < cicloReprobado) {
                    cicloReprobado = m.getMallaCiclo();
                }
            });
        } else {
            frmAlmCurso.getBtnReprobadas().setVisible(false);
        }
        cargarCmbCursos(posPrd, cicloCursado, cicloReprobado);
    }

    /*Me consulta las materias por el estado que se le pase 
      en una ventana emergente
     */
    private void mostrarInformacion(String estado) {
        int posAlm = frmAlmCurso.getTblAlumnos().getSelectedRow();
        if (posAlm >= 0) {
            //Mostramos las materias que curso
            JDMateriasCursadasCTR jdCtr = new JDMateriasCursadasCTR(vtnPrin, alumnosCarrera.get(posAlm), mallaAlm, estado);
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

    private void cargarCmbCursos(int posPrd, int cicloCursado, int cicloReprobado) {
        frmAlmCurso.getCmbCurso().removeAllItems();
        nombreCursos = cur.cargarNombreCursosPorPeriodo(periodos.get(posPrd - 1).getId_PerioLectivo(), cicloReprobado,
                cicloCursado);
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
            cursosPen = cur.buscarCursosPorNombreYPrdLectivo(
                    frmAlmCurso.getCmbCurso().getSelectedItem().toString(),
                    periodos.get(posPrd - 1).getId_PerioLectivo());
            llenarTblMatPen(cursosPen);
        } else {
            mdMatPen.setRowCount(0);
        }
    }

    private void llenarTblMatPen(ArrayList<CursoMD> cursos) {
        mdMatPen.setRowCount(0);
        if (cursos != null) {
            //Eliminamos la materias en las que ya esta matriculado
            for (int i = 0; i < mallaMatriculadas.size(); i++) {
                for (int j = 0; j < cursos.size(); j++) {
                    //Si es la misma materia la eliminamos de cursos
                    if (mallaMatriculadas.get(i).getMateria().getId() == cursos.get(j).getId_materia().getId()) {
                        //System.out.println("Esta matriculado en: " + cursos.get(j).getId_materia().getNombre());
                        cursos.remove(j);
                    }
                }
            }
            //Eliminamos las materias que ya curso 
            for (int i = 0; i < mallaCursadas.size(); i++) {
                for (int j = 0; j < cursos.size(); j++) {
                    //Si es la misma materia la eliminamos de cursos
                    if (mallaCursadas.get(i).getMateria().getId() == cursos.get(j).getId_materia().getId()) {
                        //System.out.println("Ya curso: " + cursos.get(j).getId_materia().getNombre());
                        cursos.remove(j);
                    }
                }
            }
            //Comprabamos que el curso en el que se quiere no estan vacios 
            if (!cursos.isEmpty()) {
                //Revisamos el ciclo que es 
                if (cursos.get(0).getCurso_ciclo() > 3) {
                    cursos.forEach(c -> {
                        Object[] valores = {c.getId_materia().getNombre()};
                        mdMatPen.addRow(valores);
                        //Agregamos la lista de cursos depurada 
                        cursosPen = cursos;
                    });
                } else {
                    llenarTblConRequisitosPasados(cursos);
                }

            }
        }
    }

    private void llenarTblConRequisitosPasados(ArrayList<CursoMD> cursos) {
        MallaAlumnoMD requisito;
        //Si se quiere matricular en un ciclo inferior o igual a 3 debemos revisar si
        //Paso los requisitos
        int[] posElim = new int[cursos.size()];
        int posAl = frmAlmCurso.getTblAlumnos().getSelectedRow();
        for (int i = 0; i < cursos.size(); i++) {
            requisitos = matReq.buscarPreRequisitos(cursos.get(i).getId_materia().getId());
            for (int j = 0; j < requisitos.size(); j++) {
                requisito = mallaAlm.buscarMateriaEstado(alumnosCarrera.get(posAl).getId(),
                        requisitos.get(j).getMateriaRequisito().getId());
                if (requisito.getEstado() != null) {
                    if (requisito.getEstado().equals("R")) {
                        posElim[i] = i + 1;
                    }
                }
            }
        }
        //Eliminamos las materias que tiene pre requisitos y aun no los a pasado
        for (int i = 0; i < posElim.length; i++) {
            if (posElim[i] != 0) {
                cursos.remove(posElim[i] - 1);
            }
        }
        //Pasamos el nuevo cursos depurado al array 
        cursosPen = cursos;
        //Si cursos no esta vacio llenamos la tabla
        if (!cursos.isEmpty()) {
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

    private void clickMateriasReprobadas() {
        mostrarInformacion("R");
    }

}
