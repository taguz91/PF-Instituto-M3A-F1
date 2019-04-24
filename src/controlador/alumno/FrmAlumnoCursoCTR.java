package controlador.alumno;

import controlador.curso.PnlHorarioCursoCTR;
import controlador.estilo.CambioPnlCTR;
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
import modelo.curso.SesionClaseBD;
import modelo.curso.SesionClaseMD;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaRequisitoBD;
import modelo.materia.MateriaRequisitoMD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.validaciones.CmbValidar;
import modelo.validaciones.TxtVBuscador;
import modelo.validaciones.Validar;
import vista.alumno.FrmAlumnoCurso;
import vista.curso.JDInfoHorario;
import vista.curso.PnlHorarioClase;
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
    private String materiasMatricula = "";

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
    private ArrayList<MallaAlumnoMD> mallaAnuladas;
    private ArrayList<MateriaRequisitoMD> requisitos;
    private ArrayList<SesionClaseMD> horarioAlmn, horario;
    private SesionClaseBD sesion;
    //Para revisar de que materias son requisitos y si no paso eliminarla 
    private final MateriaRequisitoBD matReq;

    /**
     * Constructor del sistema. Esta nos sirve para matricular un estudiante en
     * una carrera.
     *
     * @param vtnPrin VtnPrincipal: Vista principal del sistema.
     * @param frmAlmCurso FrmAlumnoCurso:
     * @param conecta ConectarBD: Nos ayuda a realizar consultas en la base de
     * datos.
     * @param ctrPrin VtnPrincipalCTR: Controlador de ventana principal.
     */
    public FrmAlumnoCursoCTR(VtnPrincipal vtnPrin, FrmAlumnoCurso frmAlmCurso, ConectarDB conecta,
            VtnPrincipalCTR ctrPrin) {
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
        this.sesion = new SesionClaseBD(conecta);

        vtnPrin.getDpnlPrincipal().add(frmAlmCurso);
        frmAlmCurso.show();
    }

    /**
     * Inicia dependencias de la ventana. Carga combos. Da formato a las tablas
     * Eventos de mouse.
     */
    public void iniciar() {
        //Cargamos el combo con los periodos 
        cargarCmbPrdLectivo();
        //Ocusltamos los errores 
        ocultarErrores();
        //Pasamos los modelos a las tablas 
        String[] titulo1 = {"Materias no seleccionadas"};
        String[] titulo2 = {"Materias seleccionadas", "C"};
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

        //Tamaño de el nombre curso 
        TblEstilo.columnaMedida(frmAlmCurso.getTblMateriasSelec(), 1, 30);

        //Acciones de los combos  
        frmAlmCurso.getCmbPrdLectivo().addActionListener(e -> clickPrdLectivo());
        frmAlmCurso.getCmbCurso().addActionListener(e -> cargarMaterias());
        //Le damos una accion a los botones de clase
        frmAlmCurso.getBtnPasar1().addActionListener(e -> pasarUnaMateria());
        frmAlmCurso.getBtnPasarTodos().addActionListener(e -> pasarTodasMaterias());
        frmAlmCurso.getBtnRegresar1().addActionListener(e -> regresarUnaMateria());
        frmAlmCurso.getBtnRegresarTodos().addActionListener(e -> regresarTodasMaterias());
        frmAlmCurso.getBtnReprobadas().addActionListener(e -> mostrarInformacion("R"));
        frmAlmCurso.getBtnHorarioCurso().addActionListener(e -> clickHorario());

        //Iniciamos el txtbuscador y el btn  los activamos cuando escojamos un periodo
        buscadoresEstado(false);
        //Inciamos el buscardor 
        frmAlmCurso.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String aguja = frmAlmCurso.getTxtBuscar().getText().trim();
                if (e.getKeyCode() == 10) {
                    buscarAlumnos(aguja);
                } else if (aguja.length() == 0) {
                    //Si no tipamos mas de tres letras borramos los datos
                    mdAlm.setRowCount(0);
                    frmAlmCurso.getBtnReprobadas().setVisible(false);
                    frmAlmCurso.getBtnAnuladas().setVisible(false);
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
        frmAlmCurso.getBtnAnuladas().addActionListener(e -> mostrarInformacion("A"));
        frmAlmCurso.getBtnGuardar().addActionListener(e -> guardar());
        //Ocultamos el boton 
        frmAlmCurso.getBtnAnuladas().setVisible(false);
        inicarValidaciones();
        //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaFrmFin("Alumno por curso");
    }

    /**
     * Inicia la validaciones de la ventana.
     */
    private void inicarValidaciones() {
        frmAlmCurso.getTxtBuscar().addKeyListener(new TxtVBuscador(frmAlmCurso.getTxtBuscar(),
                frmAlmCurso.getLblErrorBuscar(), frmAlmCurso.getBtnBuscar()));

        frmAlmCurso.getCmbPrdLectivo().addActionListener(new CmbValidar(frmAlmCurso.getCmbPrdLectivo(),
                frmAlmCurso.getLblErrorPrdLectivo()));
    }

    /**
     * Evento al dar click en guardar. Comprueba que todo el formulario este
     * ingresado correctamente. Si el formulario esta ingresado correctamente se
     * inserta en la base de datos.
     */
    private void guardar() {
        boolean guardar = true;
        if (cursosSelec.isEmpty()) {
            guardar = false;
            JOptionPane.showMessageDialog(vtnPrin, "Debe seleccionar materias.");
        }

        int posAlm = frmAlmCurso.getTblAlumnos().getSelectedRow();

        if (posAlm < 0) {
            guardar = false;
        }

        int posCar = frmAlmCurso.getCmbPrdLectivo().getSelectedIndex();
        if (posCar < 1) {
            guardar = false;
        }
        
        System.out.println("Antes de borrar teniamos: "+cursosSelec.size());
        //Borro los choques de horas  
        cursosSelec.forEach(c -> {
            if (c.getCurso_nombre().charAt(0) == 'C') {
                if(cursosSelec.remove(c)){
                    System.out.println("Eliminamos: "+c.getCurso_nombre()+" Materia: "+
                            c.getId_materia().getNombre());
                }
            }
        });
        System.out.println("Despues de borrar tenemos: "+cursosSelec.size());

        if (guardar) {
            System.out.println("Ingresaremos " + cursosSelec.size() + " cursos.");
            //Se limpia la variable antes de guardar 
            almnCurso.borrarMatricula();
            materiasMatricula = "";
            cursosSelec.forEach(c -> {
                //almnCurso.ingresarAlmnCurso(alumnosCarrera.get(posAlm).getAlumno().getId_Alumno(), c.getId_curso());
                almnCurso.agregarMatricula(alumnosCarrera.get(posAlm).getAlumno().getId_Alumno(), c.getId_curso());
                materiasMatricula = materiasMatricula + c.getId_materia().getNombre() + "\n";
            });

            int r = JOptionPane.showConfirmDialog(vtnPrin, alumnosCarrera.get(posAlm).getAlumno().getNombreCorto() + "\n"
                    + "Sera matricula en estas materias: \n" + materiasMatricula);
            if (r == JOptionPane.YES_OPTION) {
                if (almnCurso.guardarAlmnCurso()) {
                    //Reiniciamos todo 
                    frmAlmCurso.getTxtBuscar().setText("");
                    frmAlmCurso.getCmbCurso().removeAllItems();
                    mdAlm.setRowCount(0);
                    mdMatPen.setRowCount(0);
                    mdMatSelec.setRowCount(0);
                    cursosSelec = new ArrayList();
                    frmAlmCurso.getBtnReprobadas().setVisible(false);
                }
                System.out.println("-----------------------------");
                System.out.println("H O R A R I O");
                horarioAlmn.forEach(h -> {
                    System.out.println("Dia: " + h.getDia() + "--" + h.getHoraIni() + " || " + h.getHoraFin());
                });
                System.out.println("-----------------------------");
            }
        } else {
            JOptionPane.showMessageDialog(vtnPrin, "El formulario contiene errores.");
        }
    }
    
    /**
     * Ocultamos los errores en los formularios.
     */
    private void ocultarErrores() {
        frmAlmCurso.getLblErrorBuscar().setVisible(false);
        frmAlmCurso.getLblErrorPrdLectivo().setVisible(false);
        frmAlmCurso.getBtnReprobadas().setVisible(false);
    }

    /**
     * Estado de los buscadores comienzan en falso. Inavilita el txt, el btn de
     * buscar y el btn de materias cursadas.
     *
     * @param estado
     */
    private void buscadoresEstado(boolean estado) {
        frmAlmCurso.getTxtBuscar().setEnabled(estado);
        frmAlmCurso.getBtnBuscar().setEnabled(estado);
        frmAlmCurso.getBtnMtCursadas().setEnabled(estado);
    }

    /**
     * Consulta en base de datos todos los periodos activos, para cargarlos en
     * el combo de periodos.
     */
    private void cargarCmbPrdLectivo() {
        periodos = prd.cargarPrdParaCmbFrm();
        if (periodos != null) {
            frmAlmCurso.getCmbPrdLectivo().removeAllItems();
            frmAlmCurso.getCmbPrdLectivo().addItem("Seleccione");
            periodos.forEach((p) -> {
                frmAlmCurso.getCmbPrdLectivo().addItem(p.getNombre_PerLectivo());
            });
        }
    }

    /**
     * Al hacer click en un periodo se activan los buscadores.
     */
    private void clickPrdLectivo() {
        int posPrd = frmAlmCurso.getCmbPrdLectivo().getSelectedIndex();
        if (posPrd > 0) {
            buscadoresEstado(true);
        } else {
            buscadoresEstado(false);
        }
    }

    /**
     * Se buscan los alumnos por cedula o nombre especificamente de una carrera,
     * la carrera se obtiene del periodo previamente seleccionado.
     *
     * @param aguja
     */
    private void buscarAlumnos(String aguja) {
        int posPrd = frmAlmCurso.getCmbPrdLectivo().getSelectedIndex();
        if (posPrd > 0 && Validar.esLetrasYNumeros(aguja)) {
            alumnosCarrera = almCar.buscarAlumnoCarrera(periodos.get(posPrd - 1).getCarrera().getId(),
                    aguja);
            llenarTblAlumnos(alumnosCarrera);
        }
    }

    /**
     * Al seleccionar un alumno se realizan muchos procesos. 1. Buscamos todas
     * las materias en las que se encuentra matriculado. 2. Si encuentra
     * materias en las que esta matriculado se muestra una ventana con
     * diferentes opciones: 2.1 Ingresar otro alumno: Borra la tabla y los
     * buscadores. 2.2 Ingresar en otro curso: Sigue todo el proceso de
     * matricula. 2.3 Ver materias: Se abre una ventana en la que listan todas
     * las materias que esta matriculado. 2.4 Cancelar: Unicamente cierra la
     * ventana. 3. Si no encuentra materias en las que esta matriculada se
     * clasifican, los combos para cargar sus materias.
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

    /**
     * En este metodo clasificamos todas las materias que se mostraran en el
     * listado donde se pueden matricular. 1. Se buscan todas las materias que
     * curso un alumno. Y se filtra el ultimo ciclo que curso el estudiante. 2.
     * Se buscan las materias que reprobo el estudiante. Y se cargan el ciclo
     * menor en el que reprobo una materia. 3. De igual manera se filtran, las
     * materias en las que esta matriculado, para borrarlas de la tabla. 4. Al
     * clasificar todo se carga el combo de ciclos.
     *
     * @param posAlmn Int: Poscion en el array del alumno seleccionado.
     * @param posPrd Int: Poscion en el array del periodo seleccionado.
     */
    private void clasificarMaterias(int posAlmn, int posPrd) {
        //Iniciamos los array de nuevo
        horarioAlmn = new ArrayList<>();
        cursosSelec = new ArrayList<>();
        mdMatSelec.setRowCount(0);
        //Se reinciia el ciclo en el que esta matriculado
        cicloCursado = 0;
        //Si no esta matriculado miramos la materias que a cursado 
        materiasAlmn = mallaAlm.buscarMateriasAlumnoPorEstado(alumnosCarrera.get(posAlmn).getId(), "C");
        if (materiasAlmn != null) {
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

        //BUscamos todas las materias en las que se anulo un alumno 
        mallaAnuladas = mallaAlm.buscarMateriasAlumnoPorEstado(alumnosCarrera.get(posAlmn).getId(), "A");
        System.out.println("Numero de materias anuladas: " + mallaAnuladas.size());

        //Buscamos todas las materias en las que ya a cursado  para borrarlas de la tabla
        //mallaCursadas = mallaAlm.buscarMateriasAlumnoPorEstado(alumnosCarrera.get(posAlmn).getId(), "C");
        mallaCursadas = materiasAlmn;
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

        if (mallaAnuladas.size() > 0) {
            frmAlmCurso.getBtnAnuladas().setVisible(true);
            mallaAnuladas.forEach(m -> {
                if (m.getMallaCiclo() < cicloReprobado) {
                    cicloReprobado = m.getMallaCiclo();
                }
            });
        } else {
            frmAlmCurso.getBtnAnuladas().setVisible(false);
        }

        if (mallaPerdidas.isEmpty() && mallaAnuladas.isEmpty()) {
            cicloReprobado++;
        }

        System.out.println("Curso hasta: " + cicloCursado + " Reprobo: " + cicloReprobado);
        cargarCmbCursos(posPrd, cicloCursado, cicloReprobado);
    }

    /**
     * Me consulta las materias por el estado que se le pase, en una ventana
     * emergente.
     *
     * @param estado String: Estado de la materia Estados: C: Cursado P:
     * Pendiente M: Matriculado A: Anulado/Retirado R: Reprobado
     */
    private void mostrarInformacion(String estado) {
        int posAlm = frmAlmCurso.getTblAlumnos().getSelectedRow();
        if (posAlm >= 0) {
            //Mostramos las materias que curso
            JDMateriasInformacionCTR jdCtr = new JDMateriasInformacionCTR(vtnPrin, alumnosCarrera.get(posAlm),
                    mallaAlm, estado, ctrPrin);
            jdCtr.iniciar();
        } else {
            JOptionPane.showMessageDialog(vtnPrin, "Primero debe seleccionar un alumno.");
        }
    }

    /**
     * Llenamos la tabla de alumnos, con el listado que se le pase.
     *
     * @param alumnos ArrayList<AlumnoCarreraMD>: Alumnos devueltos luego de
     * consultarlos en una base de datos.
     */
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

    /**
     * Buscamos los cursos de un periodo en especifico.
     *
     * @param posPrd
     * @param cicloCursado Int: Ciclo maximo que curso un estudiante.
     * @param cicloReprobado Int: Ciclo minimo en el que reprobo.
     */
    private void cargarCmbCursos(int posPrd, int cicloCursado, int cicloReprobado) {
        frmAlmCurso.getCmbCurso().removeAllItems();
        nombreCursos = cur.cargarNombreCursosPorPeriodo(periodos.get(posPrd - 1).getId_PerioLectivo(),
                cicloReprobado, cicloCursado);
        if (nombreCursos != null) {
            frmAlmCurso.getCmbCurso().addItem("Seleccione");
            nombreCursos.forEach(c -> {
                frmAlmCurso.getCmbCurso().addItem(c);
            });
        }
    }

    /**
     * Buscamos las materias que abrieron en este curso, y periodo lectivo
     * seleccionado. Estas materias se cargan en la tabla de materias
     * pendientes.
     */
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

    /**
     * Se llena la tabla con las materias,excluyendo las que ya se matriculo.
     *
     * @param cursos
     */
    private void llenarTblMatPen(ArrayList<CursoMD> cursos) {
        mdMatPen.setRowCount(0);
        if (cursos != null) {
            //Eliminamos las materias que ya selecciono  
            for (int i = 0; i < cursosSelec.size(); i++) {
                for (int j = 0; j < cursos.size(); j++) {
                    if (cursosSelec.get(i).getId_materia().getId() == cursos.get(j).getId_materia().getId()) {
                        cursos.remove(j);
                    }
                }
            }

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
                if (cursos.get(0).getCurso_ciclo() > 1) {
                    llenarTblConRequisitosPasados(cursos);
                }

            }
        }
    }

    /**
     * Comprobamos que haya cursado los requisitos, de cada curso en el que se
     * puede matricular.
     *
     * @param cursos ArrayList<CursoMD>: Cursos ya depurados, unicamente los
     * cursos que tiene materias que puede tomar.
     */
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

//        System.out.println("Numero de cursos: " + cursos.size());
//        System.out.println("Esto se va a eliminar: ");
//        for (int i : posElim) {
//            System.out.print(i + "  ");
//        }
//        System.out.println("");
//        cursos.forEach(c -> {
//            System.out.println(c.getId_materia().getNombre());
//        });
        //Eliminamos las materias que tiene pre requisitos y aun no los a pasado
        System.out.println("Numero de curso: " + posElim.length);
        for (int i = 0; i < posElim.length; i++) {
            if (posElim[i] > 0) {
                cursos.remove(posElim[i] - 1);
                posElim = posElim(posElim);
            }
        }

        //Pasamos el nuevo cursos depurado al array 
        cursosPen = cursos;
        if (cursos.size() > 0) {
            llenarTblConCoRequisitos(cursos);
        }
    }

    /**
     * Para mover la posicion del que se elimina uno menos, debido a que si se
     * remueve el array se movera uno
     *
     * @param posElim
     * @return
     */
    public int[] posElim(int[] posElim) {
        int[] pos = new int[posElim.length];
        for (int i = 0; i < posElim.length; i++) {
            pos[i] = posElim[i] - 1;
            System.out.println("Se movio: " + pos[i]);
        }
        return pos;
    }

    /**
     * Comprobamos que este
     */
    private void llenarTblConCoRequisitos(ArrayList<CursoMD> cursos) {

        MallaAlumnoMD requisito;
        int posAl = frmAlmCurso.getTblAlumnos().getSelectedRow();
        int[] posElim = new int[cursos.size()];
        boolean matricula;

        for (int i = 0; i < cursos.size(); i++) {
            requisitos = matReq.buscarCoRequisitos(cursos.get(i).getId_materia().getId());
            matricula = true;
            if (requisitos.size() > 0) {
                matricula = false;
            }

            for (int j = 0; j < requisitos.size(); j++) {
                requisito = mallaAlm.buscarMateriaEstado(alumnosCarrera.get(posAl).getId(),
                        requisitos.get(j).getMateriaRequisito().getId());
                System.out.println("Estado de la materia: " + requisito.getEstado());
                if (!requisito.getEstado().equals("C") && !requisito.getEstado().equals("R")) {
                    for (int k = 0; k < cursos.size(); k++) {
                        if (cursos.get(k).getId_materia().getNombre().
                                equals(requisitos.get(j).getMateriaRequisito().getNombre())) {
                            System.out.println(j + " Se puede matricular en esta materia.");
                            matricula = true;
                            break;
                        }
                    }
                } else {
                    matricula = true;
                }
            }
            if (!matricula) {
                posElim[i] = i + 1;
                System.out.println("Debemos eliminar: " + cursos.get(i).getId_materia().getNombre());
            }
        }

        //Eliminamos las materias que no van a matricularse en un co-requisito
        for (int i = 0; i < posElim.length; i++) {
            if (posElim[i] > 0) {
                cursos.remove(posElim[i] - 1);
                posElim = posElim(posElim);
            }
        }

        //Antes validamos que esos cursos ya no esten el materia seleccionados 
        //Si cursos no esta vacio llenamos la tabla
        if (!cursos.isEmpty()) {
            cursos.forEach(c -> {
                Object[] valores = {c.getId_materia().getNombre()};
                mdMatPen.addRow(valores);
            });
        }
    }

    /**
     * Llenamos la tabla con las materias que se selecciono. Esta tabla es usada
     * en el registro.
     *
     * @param cursosSelec ArrayList<CursoMD>: Cursos con las materias
     * seleccionadas.
     */
    private void llenarTblMatSelec(ArrayList<CursoMD> cursosSelec) {
        mdMatSelec.setRowCount(0);
        if (cursosSelec != null) {
            cursosSelec.forEach(c -> {
                Object[] valores = {c.getId_materia().getNombre(), c.getCurso_nombre()};
                mdMatSelec.addRow(valores);
            });
        }
    }

    /**
     * Al seleccionar una materia se puede pasar al panel de materias
     * seleccionadas.
     */
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
        } else {
            JOptionPane.showMessageDialog(vtnPrin, "Seleecione una materia.");
        }
    }

    /**
     * Se pasan todas las materias de la tabla pendientes a seleccionada.
     */
    private void pasarTodasMaterias() {
        //Pasamos todos las materias que nos quedan en la tabla cursos 
        //La rrellenamos en cursos seleccionados
        cursosPen.forEach(c -> {
            horario = sesion.cargarHorarioCurso(c.getId_curso());
            if (c.getCurso_nombre().charAt(0) != 'C') {
                if (chocanHoras(horario)) {
                    c.setCurso_nombre("C-" + c.getCurso_nombre());
                } else {
                    llenarHorarioAlmn(horario);
                }
            }
            cursosSelec.add(c);
        });
        //Borramos todas las materias de cursos 
        cursosPen = new ArrayList();
        llenarTblMatPen(cursosPen);
        llenarTblMatSelec(cursosSelec);
    }

    /**
     * Se regresa una materia seleccionada a la tabla de materias pendientes.
     */
    private void regresarUnaMateria() {
        int posMat = frmAlmCurso.getTblMateriasSelec().getSelectedRow();
        if (posMat >= 0) {
            //Pasamos u cursod e selecciona a la lista de no seleccinados
            cursosPen.add(cursosSelec.get(posMat));
            //Eliminamos el horario de esta materia 
            if (cursosSelec.get(posMat).getCurso_nombre().charAt(0) != 'C') {
                horario = sesion.cargarHorarioCurso(cursosSelec.get(posMat).getId_curso());
                quitarHorarioAlmn(horario);
            }
            //Eliminamos la materia que fue pasada 
            cursosSelec.remove(posMat);
            //Volvemos a llenar las tablas
            llenarTblMatPen(cursosPen);
            llenarTblMatSelec(cursosSelec);
        } else {
            JOptionPane.showMessageDialog(vtnPrin, "Seleecione una materia.");
        }
    }

    /**
     * Se regresan todas las materias, a la tabla de materias pendientes.
     */
    private void regresarTodasMaterias() {
        cursosSelec.forEach(c -> cursosPen.add(c));
        //Reiniciamos el array para borrar todos los datos
        cursosSelec = new ArrayList();
        horarioAlmn = new ArrayList<>();
        llenarTblMatPen(cursosPen);
        llenarTblMatSelec(cursosSelec);
    }

    /**
     * Mostramos el horario del curso que esta seleccionado
     */
    public void clickHorario() {
        int posPrd = frmAlmCurso.getCmbPrdLectivo().getSelectedIndex();
        int posCurso = frmAlmCurso.getCmbCurso().getSelectedIndex();
        if (posPrd > 0 && posCurso > 0) {
            JDInfoHorario jd = new JDInfoHorario(vtnPrin, false);
            PnlHorarioClase pnl = new PnlHorarioClase();
            CambioPnlCTR.cambioPnl(jd.getPnlHorario(), pnl);
            PnlHorarioCursoCTR ctr = new PnlHorarioCursoCTR(pnl,
                    frmAlmCurso.getCmbCurso().getSelectedItem().toString(),
                    periodos.get(posPrd - 1).getId_PerioLectivo(), conecta);
            ctr.iniciar();
            jd.setLocationRelativeTo(vtnPrin);
            jd.setVisible(true);
            jd.setTitle("Horario de " + frmAlmCurso.getCmbCurso().getSelectedItem().toString());
            ctrPrin.eventoJDCerrar(jd);
        } else {
            JOptionPane.showMessageDialog(vtnPrin, "Seleccione un curso primero.");
        }
    }

    private boolean choque;

    /**
     * Comprobamos si se choca el horario del alumno
     *
     * @param horario
     * @return
     */
    public boolean chocanHoras(ArrayList<SesionClaseMD> horario) {
        choque = false;
        horarioAlmn.forEach(h -> {
            horario.forEach(hc -> {
                if ((h.getDia() == hc.getDia() && h.getHoraIni() == hc.getHoraIni())
                        || (h.getDia() == hc.getDia() && h.getHoraFin() == hc.getHoraIni())) {
                    choque = true;
                }
            });
        });
        return choque;
    }

    /**
     * Llenamos el array con el horario del alumno
     *
     * @param horario
     */
    public void llenarHorarioAlmn(ArrayList<SesionClaseMD> horario) {
        horario.forEach(h -> {
            horarioAlmn.add(h);
        });
    }

    /**
     * Quitamos el horario de un alumno si excluye esa materia
     *
     * @param horario
     */
    public void quitarHorarioAlmn(ArrayList<SesionClaseMD> horario) {
        horario.forEach(h -> {
            for (int i = 0; i < horarioAlmn.size(); i++) {
                if (h.getId() == horarioAlmn.get(i).getId()) {
                    horarioAlmn.remove(i);
                    break;
                }
            }
        });
    }
}
