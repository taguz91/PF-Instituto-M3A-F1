package controlador.alumno;

import controlador.curso.PnlHorarioCursoCTR;
import controlador.estilo.CambioPnlCTR;
import controlador.estilo.TblRenderNumMatricula;
import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import controlador.ventanas.VtnLblToolTip;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.alumno.AlumnoCarreraBD;
import modelo.alumno.AlumnoCarreraMD;
import modelo.curso.CursoBD;
import modelo.curso.CursoMD;
import modelo.estilo.TblEstilo;
import modelo.alumno.AlumnoCursoBD;
import modelo.alumno.MallaAlumnoBD;
import modelo.alumno.MallaAlumnoMD;
import modelo.alumno.MatriculaBD;
import modelo.alumno.MatriculaMD;
import modelo.alumno.UtilEgresadoBD;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.curso.SesionClaseBD;
import modelo.curso.SesionClaseMD;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import modelo.materia.MateriaRequisitoBD;
import modelo.materia.MateriaRequisitoMD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.validaciones.CmbValidar;
import modelo.validaciones.TxtVBuscador;
import modelo.validaciones.Validar;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import vista.alumno.FrmAlumnoCurso;
import vista.curso.JDInfoHorario;
import vista.curso.PnlHorarioClase;

/**
 *
 * @author Johnny
 */
public class FrmAlumnoCursoCTR extends DCTR {

    private final FrmAlumnoCurso frmAlmCurso;
    private final AlumnoCursoBD almnCurso;
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
    //Para eliminar las materias en las que ya estoy matriculado  
    private ArrayList<MallaAlumnoMD> mallaCompleta;
    private ArrayList<MallaAlumnoMD> mallaPerdidas;
    private ArrayList<MallaAlumnoMD> mallaMatriculadas;
    private ArrayList<MallaAlumnoMD> mallaCursadas;
    private ArrayList<MallaAlumnoMD> mallaAnuladas;
    private ArrayList<MallaAlumnoMD> mallaPendientes;
    private ArrayList<MateriaRequisitoMD> requisitos;
    private ArrayList<SesionClaseMD> horarioAlmn, horario, hcurso;
    private final SesionClaseBD sesion;
    //Para revisar de que materias son requisitos y si no paso eliminarla 
    private final MateriaRequisitoBD matReq;
    //Matricula 
    private final MatriculaBD matri;
    //Numero de materia matricula:
    private int numMateria = 0;
    //Para buscar materias si una materia es nucleo estructurante
    private final MateriaBD mat;
    private ArrayList<MateriaMD> materias;
    private Boolean perdioNE, tieneTerceraMatricula;
    //Para guardar la carrera en la que se esta trabajando 
    private CarreraMD carrera;
    private final CarreraBD car;
    // Listado de las 
    private List<MallaAlumnoMD> mallaAlmnNoPagadas;
    private final UtilEgresadoBD UEBD = UtilEgresadoBD.single();

    /**
     * Constructor del sistema. Esta nos sirve para matricular un estudiante en
     * una carrera.
     *
     * @param frmAlmCurso FrmAlumnoCurso:
     * @param ctrPrin VtnPrincipalCTR: Controlador de ventana principal.
     */
    public FrmAlumnoCursoCTR(FrmAlumnoCurso frmAlmCurso,
            VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
        this.frmAlmCurso = frmAlmCurso;
        this.matri = new MatriculaBD(ctrPrin.getConecta());
        this.mat = new MateriaBD(ctrPrin.getConecta());
        this.car = new CarreraBD(ctrPrin.getConecta());
        //Inicializamos todas la clases que usaremos
        this.almnCurso = new AlumnoCursoBD(ctrPrin.getConecta());
        this.almCar = new AlumnoCarreraBD(ctrPrin.getConecta());
        this.prd = new PeriodoLectivoBD(ctrPrin.getConecta());
        this.cur = new CursoBD(ctrPrin.getConecta());
        this.mallaAlm = new MallaAlumnoBD(ctrPrin.getConecta());
        this.matReq = new MateriaRequisitoBD(ctrPrin.getConecta());
        this.sesion = new SesionClaseBD(ctrPrin.getConecta());
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
        iniciarTbls();
        iniciarAcciones();
        //Iniciamos el txtbuscador y el btn  los activamos cuando escojamos un periodo
        buscadoresEstado(false);
        iniciarBuscador();
        //Ocultamos el boton 
        frmAlmCurso.getBtnAnuladas().setVisible(false);
        inicarValidaciones();
        //Vemos los componentes 
        VtnLblToolTip.agregarTooltipsLblJI(frmAlmCurso);

        ctrPrin.agregarVtn(frmAlmCurso);
    }

    /**
     * Formateamos nuestras tablas para mostrar las informacion
     */
    private void iniciarTbls() {
        //Pasamos los modelos a las tablas 
        String[] titulo1 = {"Materias no seleccionadas", "M", "#"};
        String[] titulo2 = {"Materias seleccionadas", "C"};
        String[] tituloAlmn = {"Cédula", "Alumnos"};
        String[][] datos1 = {};
        mdMatPen = TblEstilo.modelTblSinEditar(datos1, titulo1);
        mdMatSelec = TblEstilo.modelTblSinEditar(datos1, titulo2);
        mdAlm = TblEstilo.modelTblSinEditar(datos1, tituloAlmn);
        frmAlmCurso.getTblMateriasPen().setModel(mdMatPen);
        frmAlmCurso.getTblMateriasSelec().setModel(mdMatSelec);
        frmAlmCurso.getTblAlumnos().setModel(mdAlm);
        TblEstilo.formatoTblMatricula(frmAlmCurso.getTblMateriasPen());
        TblEstilo.formatoTblMatricula(frmAlmCurso.getTblMateriasSelec());
        TblEstilo.formatoTbl(frmAlmCurso.getTblAlumnos());
        //Tamaño de la cedula del estudiante
        TblEstilo.columnaMedida(frmAlmCurso.getTblAlumnos(), 0, 100);
        //Tamaño del nombre curso 
        TblEstilo.columnaMedida(frmAlmCurso.getTblMateriasSelec(), 1, 40);
        //Tamaño de la capacidad del curso
        TblEstilo.columnaMedida(frmAlmCurso.getTblMateriasPen(), 1, 40);
        TblEstilo.columnaMedida(frmAlmCurso.getTblMateriasPen(), 2, 20);
        //Agregamos el formato de la columna para darle color  
        frmAlmCurso.getTblMateriasPen().getColumnModel().getColumn(2).
                setCellRenderer(new TblRenderNumMatricula(2));

    }

    /**
     * Iniciamos las acciones en los diferentes componentes
     */
    private void iniciarAcciones() {
        //Accion en la tabla alumno 
        frmAlmCurso.getTblAlumnos().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickTblAlumnos();
            }
        });

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
        //Buscador
        frmAlmCurso.getBtnBuscar().addActionListener(e -> {
            buscarAlumnos(frmAlmCurso.getTxtBuscar().getText().trim());
        });

        frmAlmCurso.getBtnMtCursadas().addActionListener(e -> mostrarInformacion("C"));
        frmAlmCurso.getBtnAnuladas().addActionListener(e -> mostrarInformacion("A"));
        frmAlmCurso.getBtnPendientes().addActionListener(e -> mostrarInformacion("P"));
        frmAlmCurso.getBtnHorarioAlmn().addActionListener(e -> horarioAlmn());
        frmAlmCurso.getBtnGuardar().addActionListener(e -> guardar());

        // Acciones de choques de horas
        frmAlmCurso.getBtnChoques().addActionListener(e -> mostrarChoque());

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
     * Inicio mi buscador
     */
    private void iniciarBuscador() {
        //Inciamos el buscardor 
        frmAlmCurso.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String aguja = frmAlmCurso.getTxtBuscar().getText().trim();
                if (e.getKeyCode() == 10) {
                    buscarAlumnos(aguja);
                } else if (aguja.length() == 0) {
                    //Si no tipamos mas de tres letras borramos los datos
                    //Iniciamos los array de nuevo
                    limpiarFrm();
                }
            }
        });
    }

    /**
     * Evento al dar click en guardar. Comprueba que todo el formulario este
     * ingresado correctamente. Si el formulario esta ingresado correctamente se
     * inserta en la base de datos.
     */
    private void guardar() {
        boolean guardar = true;
        String error = "El formulario contiene errores.";
        if (cursosSelec.isEmpty()) {
            guardar = false;
            JOptionPane.showMessageDialog(ctrPrin.getVtnPrin(), "Debe seleccionar materias.");
        }

        int posAlm = frmAlmCurso.getTblAlumnos().getSelectedRow();

        if (posAlm < 0) {
            guardar = false;
        }

        int posPrd = frmAlmCurso.getCmbPrdLectivo().getSelectedIndex();
        if (posPrd < 1) {
            guardar = false;
        }

        int posTipo = frmAlmCurso.getCmbTipoMatricula().getSelectedIndex();
        if (posTipo < 1) {
            guardar = false;
        }

        if (tieneTerceraMatricula) {
            guardar = validarTercerasMatriculas();
            if (!guardar) {
                error = "Debe matricularse en su tercera matricula.";
            }
        }

        //Se borra todas las materias que tienen un choque de horas
        borrarChoques(cursosSelec);

        //Se valida que se haya inscrito en todos los co-requitos establecidos 
        if (guardar) {
            guardar = validarCoRequisitos();
        }

        if (guardar) {
            //Se limpia la variable antes de guardar 
            almnCurso.borrarMatricula();
            materiasMatricula = "";
            numMateria = 1;
            cursosSelec.forEach(c -> {
                //almnCurso.ingresarAlmnCurso(alumnosCarrera.get(posAlm).getAlumno().getId_Alumno(), c.getId_curso());
                almnCurso.agregarMatricula(
                        alumnosCarrera.get(posAlm).getAlumno().getId_Alumno(),
                        c.getId(),
                        c.getNumMatricula()
                );
                materiasMatricula = materiasMatricula + numMateria + ": "
                        + "  Curso: " + c.getNombre()
                        + "  Matricula: " + c.getNumMatricula()
                        + "  Materia: " + c.getMateria().getNombre()
                        + "    \n";
                numMateria++;
            });

            int r = JOptionPane.showConfirmDialog(ctrPrin.getVtnPrin(), "Se matricula a: \n"
                    + alumnosCarrera.get(posAlm).getAlumno().getNombreCorto() + "\n"
                    + "Periodo: \n" + periodos.get(posPrd - 1).getNombre() + "\n"
                    + "En las siguientes materias: \n" + materiasMatricula);
            if (r == JOptionPane.YES_OPTION) {

                //Se ingresa matricula
                MatriculaMD m = matri.buscarMatriculaAlmnPrd(
                        alumnosCarrera.get(posAlm).getAlumno().getId_Alumno(),
                        periodos.get(posPrd - 1).getID()
                );

                if (m == null) {
                    matri.setAlumno(alumnosCarrera.get(posAlm).getAlumno());
                    matri.setPeriodo(periodos.get(posPrd - 1));
                    matri.setTipo(frmAlmCurso.getCmbTipoMatricula().getSelectedItem().toString());
                    matri.ingresar();
                }

                if (almnCurso.guardarAlmnCurso()) {
                    //Reiniciamos todo 
                    limpiarFrm();
                    int c = JOptionPane.showConfirmDialog(ctrPrin.getVtnPrin(), "Desea imprimir la matricula.");
                    if (c == JOptionPane.YES_OPTION) {
                        //Imprimimos el reporte de matricula
                        llamaReporteMatricula(alumnosCarrera.get(posAlm).getAlumno().getIdentificacion(),
                                periodos.get(posPrd - 1).getID());
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(ctrPrin.getVtnPrin(), error);
        }
    }

    /**
     * Limpiamos todo el formulario
     */
    private void limpiarFrm() {
        frmAlmCurso.getTxtBuscar().setText("");
        frmAlmCurso.getCmbCurso().removeAllItems();
        mdAlm.setRowCount(0);
        mdMatPen.setRowCount(0);
        mdMatSelec.setRowCount(0);
        cursosSelec = new ArrayList();
        horarioAlmn = new ArrayList<>();
        frmAlmCurso.getBtnReprobadas().setVisible(false);
        frmAlmCurso.getBtnAnuladas().setVisible(false);
        frmAlmCurso.getBtnPendientes().setEnabled(false);
        frmAlmCurso.getBtnMtCursadas().setEnabled(false);
    }

    /**
     * Eliminamos los que estan en choque de horas
     */
    private ArrayList<CursoMD> borrarChoques(ArrayList<CursoMD> cursos) {
        int[] posElim = new int[cursos.size()];
        for (int i = 0; i < cursos.size(); i++) {
            //System.out.println("Nombre curso: " + cursos.get(i).getNombre());
            if (cursos.get(i).getNombre().charAt(0) == 'C') {
                posElim[i] = i + 1;
            }
        }

        for (int i = 0; i < posElim.length; i++) {
            if (posElim[i] > 0) {
                cursos.remove(posElim[i] - 1);
                posElim = posElim(posElim);
            }
        }
        return cursos;
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
        frmAlmCurso.getBtnPendientes().setEnabled(estado);
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
                frmAlmCurso.getCmbPrdLectivo().addItem(p.getNombre());
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
            frmAlmCurso.getLblNumMatriculas().setText(
                    matri.numMaticulados(periodos.get(posPrd - 1).getID()) + "");
            frmAlmCurso.getLblNumMatriculasClases().setText(
                    matri.numMaticuladosClases(periodos.get(posPrd - 1).getID()) + "");
            //Cargamos informacion de la carrera que usaremos.
            carrera = car.buscarPorId(periodos.get(posPrd - 1).getCarrera().getId());
            mostrarInfoCarrera(carrera);
            limpiarFrm();
        } else {
            buscadoresEstado(false);
        }
    }

    /**
     * Mostramos la informacion de la carrera en la que se realiza la matricula.
     */
    private void mostrarInfoCarrera(CarreraMD carrera) {
        if (carrera != null) {
            frmAlmCurso.setTitle("Matricula | " + carrera.getNombre() + " - " + carrera.getModalidad());
        } else {
            frmAlmCurso.setTitle("Matricula ");
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
            alumnosCarrera = almCar.buscarAlumnoCarreraParaFrm(
                    periodos.get(posPrd - 1).getCarrera().getId(),
                    aguja
            );
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
            //Limpiamos los array que estamos utilizando 
            mdMatPen.setRowCount(0);
            mdMatSelec.setRowCount(0);
            cursosSelec = new ArrayList();
            horarioAlmn = new ArrayList<>();
            tieneTerceraMatricula = false;

            frmAlmCurso.getBtnPendientes().setEnabled(true);
            frmAlmCurso.getBtnMtCursadas().setEnabled(true);
            //Buscamos la malla completa
            mallaCompleta = mallaAlm.buscarMallaAlumnoParaEstado(alumnosCarrera.get(posAl).getId());
            //Vemos si el alumno esta matriculado en una materia
            mallaMatriculadas = filtrarMalla(mallaCompleta, "M");
            // Consultamos las materias que tiene pendiente un pago  
            String msg = "";
            mallaAlmnNoPagadas = UEBD.getMateriasNoPagadas(
                    alumnosCarrera.get(posAl).getId()
            );
            msg = mallaAlmnNoPagadas.stream().map((ma)
                    -> "Ciclo: " + ma.getMateria().getCiclo() + "  "
                    + "# Matricula: " + ma.getMallaNumMatricula() + "  "
                    + "Materia: " + ma.getMateria().getNombre() + " \n")
                    .reduce(msg, String::concat);

            if (msg.length() > 0) {
                msg = "Matricula que tiene pendiente su pago:\n" + msg;
            }

            String matriculasPagar = matri.getMatriculasAPagar(alumnosCarrera.get(posAl).getId());
            if (matriculasPagar.length() > 0) {
                msg += "\nMatriculas a pagar: \n" + matriculasPagar;
            }

            // Aqui validamos si tiene pagos pendientes no le dejamos matricular 
            if (msg.length() > 0) {
                JOptionPane.showMessageDialog(
                        frmAlmCurso,
                        msg
                );
            }

            if (!mallaMatriculadas.isEmpty()) {
                //Borramos los cursos que posiblemente carguemos antes
                frmAlmCurso.getCmbCurso().removeAllItems();
                int s = JOptionPane.showOptionDialog(ctrPrin.getVtnPrin(),
                        "Alumno matriculado en " + mallaMatriculadas.get(0).getMallaCiclo() + " ciclo. \n"
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
                        clasificarMateriasAlmn(posPrd);
                        break;
                    case 2:
                        mostrarInformacion("M");
                        break;
                    default:
                        break;
                }

            } else {
                clasificarMateriasAlmn(posPrd);
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
    private void clasificarMateriasAlmn(int posPrd) {
        //Para comprobar si perdio nucleo estruncturante
        perdioNE = false;
        //Mensajes de estado 
        ctrPrin.getVtnPrin().getLblEstado().setText("Clasificando cursos... ");
        //Se reinciia el ciclo en el que esta matriculado
        cicloCursado = 0;

        //Si no esta matriculado miramos la materias que a cursado 
        mallaCursadas = filtrarMalla(mallaCompleta, "C");
        if (mallaCursadas != null) {
            for (int i = 0; i < mallaCursadas.size(); i++) {
                if (mallaCursadas.get(i).getMallaCiclo() > cicloCursado) {
                    cicloCursado = mallaCursadas.get(i).getMallaCiclo();
                }
            }
        }

        //Se leasigna el mismo valor si es que no tiene un ciclo reprobado
        cicloReprobado = cicloCursado;
        //Aumentamos uno para que pueda coger materias superiores que no tengan prerequisitos
        cicloCursado++;
        //Esto lo usamos para saber desde que ciclo cargar el combo de cursos
        mallaPerdidas = filtrarMalla(mallaCompleta, "R");

        //Buscamos las materias en las que ya esta matriculado para borrarlas de la tabla
        mallaMatriculadas = filtrarMalla(mallaCompleta, "M");

        //BUscamos todas las materias en las que anulo el alumno 
        mallaAnuladas = filtrarMalla(mallaCompleta, "A");

        //Buscamos las pendientes 
        mallaPendientes = filtrarMalla(mallaCompleta, "P");

        //Cargamos las materias de esta carrera 
        materias = mat.cargarMateriaPorCarreraFrm(carrera.getId());

        //Si no perdio ninguna se le suba al ciclo en que perdio uno
        if (cicloCursado == cicloReprobado) {
            cicloReprobado++;
        }

        if (mallaPerdidas.size() > 0) {
            frmAlmCurso.getBtnReprobadas().setVisible(true);
            //Si reprobo una materia se busca el ciclo menor en el que reprobo
            mallaPerdidas.forEach(m -> {
                if (carrera.getModalidad().equals("DUAL")) {
                    System.out.println("Estamos en una carrera dual. " + m.getMateria().getNombre());
                    boolean p = perdioNucleoEstruncturante(m.getMateria().getId());
                    if (!perdioNE) {
                        perdioNE = p;
                    }
                }

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

        if (mallaPendientes.size() > 0) {
            frmAlmCurso.getBtnPendientes().setVisible(true);
            mallaPendientes.forEach(m -> {
                if (m.getMallaCiclo() < cicloReprobado) {
                    cicloReprobado = m.getMallaCiclo();
                }
            });
        } else {
            frmAlmCurso.getBtnPendientes().setVisible(false);
        }

        //Hacemos que seleccione unicamente donde perdio la dual
        if (perdioNE) {
            System.out.println("Ciclo en el que curso antes: " + cicloCursado + " /// Reprobo: " + cicloReprobado);
            //cicloCursado  1;
            System.out.println("Ciclo en el que curso: " + cicloCursado + " /// Reprobo: " + cicloReprobado);
        }
        //Buscamos las terceras matriculas 
        ArrayList<MallaAlumnoMD> tm = filtrarTercerasMatriculas(mallaPerdidas);
        if (tm.size() > 0) {
            tieneTerceraMatricula = true;
            int s = JOptionPane.showOptionDialog(ctrPrin.getVtnPrin(),
                    "El alumno tiene terceras matriculas \n"
                    + "¿Ver materias en las que debe realizar tercera matricula?", "Alumno con tercera matricula",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{"Permitir matricula",
                        "Ver materias", "Cancelar"}, "Ver materias");
            switch (s) {
                case 0:
                    tm.forEach(m -> {
                        if (m.getMallaCiclo() < cicloCursado) {
                            cicloCursado = m.getMallaCiclo();
                        }
                    });
                    //Le restamos un curso ya que al consultar le sumamos uno porque debe ser
                    //el siguiente que ya curso
                    cicloCursado -= 1;
                    cargarCmbCursos(posPrd, cicloCursado, cicloReprobado);
                    break;
                case 1:
                    mostrarTercerasMatriculas();
                    break;
            }
        } else {
            cargarCmbCursos(posPrd, cicloCursado, cicloReprobado);
        }

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
            JDMateriasInformacionCTR jdCtr = new JDMateriasInformacionCTR(alumnosCarrera.get(posAlm),
                    mallaAlm, estado, ctrPrin);
            jdCtr.iniciar();
            jdCtr.cargarMateriasEstado();
        } else {
            JOptionPane.showMessageDialog(ctrPrin.getVtnPrin(), "Primero debe seleccionar un alumno.");
        }
    }

    private void mostrarTercerasMatriculas() {
        int posAlm = frmAlmCurso.getTblAlumnos().getSelectedRow();
        if (posAlm >= 0) {
            //Mostramos las materias que curso
            JDMateriasInformacionCTR jdCtr = new JDMateriasInformacionCTR(alumnosCarrera.get(posAlm),
                    mallaAlm, "R", ctrPrin);
            jdCtr.iniciar();
            jdCtr.cargarTercerasMatriculas();
        } else {
            JOptionPane.showMessageDialog(ctrPrin.getVtnPrin(), "Primero debe seleccionar un alumno.");
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
        nombreCursos = cur.cargarNombreCursosPorPeriodo(periodos.get(posPrd - 1).getID(),
                cicloReprobado, cicloCursado);
        if (nombreCursos != null) {
            frmAlmCurso.getCmbCurso().addItem("Seleccione");
            nombreCursos.forEach(c -> {
                frmAlmCurso.getCmbCurso().addItem(c);
            });
            ctrPrin.getVtnPrin().getLblEstado().setText("Cursos clasificados correctamente.");
        } else {
            ctrPrin.getVtnPrin().getLblEstado().setText("No se pudieron clasificar los cursos, por favor vuelva a seleccionar un alumno.");
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
        int posAlm = frmAlmCurso.getTblAlumnos().getSelectedRow();
        //Borramos los datos existentes

        if (posPrd > 0 && posCurso > 0 && posAlm >= 0) {
            cursosPen = cur.buscarCursosPorNombreYPrdLectivo(
                    frmAlmCurso.getCmbCurso().getSelectedItem().toString(),
                    periodos.get(posPrd - 1).getID(),
                    alumnosCarrera.get(posAlm).getAlumno().getId_Alumno()
            );
            //Cargamos todos los requisitos de este ciclo en esta carrera
            requisitos = matReq.buscarRequisitosPorCarrera(periodos.get(posPrd - 1).getCarrera().getId());
            //Cargamos el horario de este curso 
            hcurso = sesion.cargarHorarioCurso(
                    frmAlmCurso.getCmbCurso().getSelectedItem().toString(),
                    periodos.get(posPrd - 1).getID()
            );
            clasificarMateriasPendientes(cursosPen);
        } else {
            mdMatPen.setRowCount(0);
        }
    }

    /**
     * Se clasifican las materias,excluyendo las que ya se matriculo. Tambien
     * excluimos las materias ya seleccionadas. Tambien se descarta las materias
     * cursadas
     *
     * @param cursos
     */
    private void clasificarMateriasPendientes(ArrayList<CursoMD> cursos) {
        mdMatPen.setRowCount(0);
        if (cursos != null) {
            //Eliminamos las materias que ya selecciono  
            for (int i = 0; i < cursosSelec.size(); i++) {
                for (int j = 0; j < cursos.size(); j++) {
                    if (cursosSelec.get(i).getMateria().getId() == cursos.get(j).getMateria().getId()) {
                        cursos.remove(j);
                        break;
                    }
                }
            }

            //Eliminamos la materias en las que ya esta matriculado
            for (int i = 0; i < mallaMatriculadas.size(); i++) {
                for (int j = 0; j < cursos.size(); j++) {
                    //Si es la misma materia la eliminamos de cursos
                    if (mallaMatriculadas.get(i).getMateria().getId() == cursos.get(j).getMateria().getId()) {
                        cursos.remove(j);
                        break;
                    }
                }
            }

            //Si no perdio el nucleo estructurante se eliminan las que ya curso
            if (!perdioNE) {
                //Eliminamos las materias que ya curso 
                for (int i = 0; i < mallaCursadas.size(); i++) {
                    for (int j = 0; j < cursos.size(); j++) {
                        //Si es la misma materia la eliminamos de cursos
                        if (mallaCursadas.get(i).getMateria().getId() == cursos.get(j).getMateria().getId()) {
                            cursos.remove(j);
                            break;
                        }
                    }
                }
            } else {
                //Eliminamos las materias que ya curso  de nucleo estructurante
                for (int i = 0; i < mallaCursadas.size(); i++) {
                    for (int j = 0; j < cursos.size(); j++) {
                        //Si es la misma materia la eliminamos de cursos
                        if (mallaCursadas.get(i).getMateria().getId() == cursos.get(j).getMateria().getId()
                                && !perdioNucleoEstruncturante(mallaCursadas.get(i).getMateria().getId())) {
                            cursos.remove(j);
                            break;
                        }
                    }
                }
            }

            //Comprabamos que el curso en el que se quiere no estan vacios 
            if (!cursos.isEmpty()) {
                //Revisamos el ciclo que es 
                if (cursos.get(0).getCiclo() > 1) {
                    llenarTblConRequisitosPasados(cursos);
                } else {
                    llenarTblMateriasPendientes(cursos);
                }
            }
        }
    }

    /**
     * Comprobamos si perdio una materia de nucleo estructurante
     *
     * @param idMateria
     * @return
     */
    private boolean perdioNucleoEstruncturante(int idMateria) {
        boolean perdio = false;
        if (materias != null) {
            for (int i = 0; i < materias.size(); i++) {
                //&& materias.get(i).isMateriaNucleo()
                if (materias.get(i).getId() == idMateria && materias.get(i).isMateriaNucleo()) {
                    perdio = true;
                    break;
                }
            }
        }
        return perdio;
    }

    /**
     * Comprobamos que haya cursado los requisitos, de cada curso en el que se
     * puede matricular.
     *
     * @param cursos ArrayList<CursoMD>: Cursos ya depurados, unicamente los
     * cursos que tiene materias que puede tomar.
     */
    private void llenarTblConRequisitosPasados(ArrayList<CursoMD> cursos) {
        ArrayList<MateriaRequisitoMD> requisitosFiltrados;
        //MallaAlumnoMD requisito;
        //Si se quiere matricular en un ciclo inferior o igual a 3 debemos revisar si
        //Paso los requisitos
        //P significa Pre-requisito
        int[] posElim = new int[cursos.size()];
        for (int i = 0; i < cursos.size(); i++) {
            requisitosFiltrados = filtrarRequisitos(cursos.get(i).getMateria().getId(), "P");
            for (int j = 0; j < requisitosFiltrados.size(); j++) {
                estadoMateria = estadoMateriaEnMalla(requisitosFiltrados.get(j).getMateriaRequisito().getId());
                if (estadoMateria != null) {
                    if (!estadoMateria.equals("C")) {
                        posElim[i] = i + 1;
                    }
                }
            }
        }

        //Eliminamos las materias que tiene pre requisitos y aun no los a pasado
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
        }
        return pos;
    }

    /**
     * Comprobamos que este matriculandose en co requisitos tambien
     */
    private void llenarTblConCoRequisitos(ArrayList<CursoMD> cursos) {

        //MallaAlumnoMD requisito;
        ArrayList<MateriaRequisitoMD> requisitosFiltrados;
        int[] posElim = new int[cursos.size()];
        boolean matricula;

        for (int i = 0; i < cursos.size(); i++) {
            requisitosFiltrados = filtrarRequisitos(cursos.get(i).getMateria().getId(), "C");
            matricula = true;
            if (requisitosFiltrados.size() > 0) {
                matricula = false;
            }

            for (int j = 0; j < requisitosFiltrados.size(); j++) {
                estadoMateria = estadoMateriaEnMalla(requisitosFiltrados.get(j).getMateriaRequisito().getId());

                if (!estadoMateria.equals("C")
                        && !estadoMateria.equals("R")
                        && !estadoMateria.equals("M")) {
                    for (int k = 0; k < cursos.size(); k++) {
                        if (cursos.get(k).getMateria().getNombre().
                                equals(requisitosFiltrados.get(j).getMateriaRequisito().getNombre())) {
                            matricula = true;
                            break;
                        }
                    }
                    //Tambien se comprueba con los cursos ya seleccionados
                    for (int k = 0; k < cursosSelec.size(); k++) {
                        if (cursosSelec.get(k).getMateria().getNombre().
                                equals(requisitosFiltrados.get(j).getMateriaRequisito().getNombre())) {
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
            }
        }

        //Eliminamos las materias que no van a matricularse en un co-requisito
        for (int i = 0; i < posElim.length; i++) {
            if (posElim[i] > 0) {
                cursos.remove(posElim[i] - 1);
                posElim = posElim(posElim);
            }
        }

        llenarTblMateriasPendientes(cursos);
    }

    /**
     * Validamos los co-requisitos antes de guardar la matricula Aqui unicamente
     * vemos los cursos en los que se esta matriculando, los seleccionados
     */
    private boolean validarCoRequisitos() {

        ArrayList<MateriaRequisitoMD> requisitosFiltrados;
        boolean matricula = true;
        String mensaje = "";

        for (int i = 0; i < cursosSelec.size(); i++) {
            requisitosFiltrados = filtrarRequisitos(cursosSelec.get(i).getMateria().getId(), "C");
            matricula = true;
            if (requisitosFiltrados.size() > 0) {
                matricula = false;
            }

            for (int j = 0; j < requisitosFiltrados.size(); j++) {
                estadoMateria = estadoMateriaEnMalla(requisitosFiltrados.get(j).getMateriaRequisito().getId());
                if (!estadoMateria.equals("C")
                        && !estadoMateria.equals("M")) {
                    //Tambien se comprueba con los cursos ya seleccionados
                    for (int k = 0; k < cursosSelec.size(); k++) {
                        if (cursosSelec.get(k).getMateria().getNombre().
                                equals(requisitosFiltrados.get(j).getMateriaRequisito().getNombre())) {
                            matricula = true;
                            break;
                        } else {
                            mensaje = mensaje + cursosSelec.get(k).getMateria().getNombre() + " tiene como co-requisito: \n"
                                    + requisitosFiltrados.get(j).getMateriaRequisito().getNombre() + "\n";
                        }
                    }
                } else {
                    matricula = true;
                }
            }
        }

        if (!matricula) {
            mensaje = mensaje + "\nDebe matricularse en las materias con su co-requisito correspondiente.";
            JOptionPane.showMessageDialog(null, mensaje);
            return false;
        } else {
            return true;
        }
    }

    /**
     * *
     * Validamos que se matricule en terceras matriculas si es que tiene
     *
     * @return
     */
    public boolean validarTercerasMatriculas() {
        boolean guardar = false;
        for (int i = 0; i < cursosSelec.size(); i++) {
            if (cursosSelec.get(i).getNumMatricula() == 3) {
                guardar = true;
                break;
            }
        }
        return guardar;
    }

    /**
     * Llenar materias pendientes, unicamente llenamos en la tabla.
     *
     * @param cursos
     */
    public void llenarTblMateriasPendientes(ArrayList<CursoMD> cursos) {
        // Antes validamos que esos cursos ya no esten el materia seleccionados 
        // Si cursos no esta vacio llenamos la tabla
        // Pasamos el nuevo cursos depurado al array 
        cursosPen = cursos;
        mdMatPen.setRowCount(0);
        if (!cursos.isEmpty()) {
            cursos.forEach(c -> {
                c.setNumMatricula(buscarNumeroMatricula(c.getMateria().getId()));
                Object[] valores = {c.getMateria().getNombre(), c.getCapaciadActual(),
                    c.getNumMatricula()};
                //Le pasamos el numero de matricula

                mdMatPen.addRow(valores);
            });
        }
    }

    /**
     * Llenamos la tabla con las materias que se selecciono. Esta tabla es usada
     * en el registro. Unicamente una tabla
     *
     * @param cursosSelec ArrayList<CursoMD>: Cursos con las materias
     * seleccionadas.
     */
    private void llenarTblMatSelec(ArrayList<CursoMD> cursosSelec) {
        mdMatSelec.setRowCount(0);
        if (cursosSelec != null) {
            cursosSelec.forEach(c -> {
                Object[] valores = {c.getMateria().getNombre(), c.getNombre()};
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
            if (cursosPen.get(posMat).getCapaciadActual() > 0) {
                //Buscamos el horario de este curso 
                //horario = sesion.cargarHorarioCurso(cursosPen.get(posMat));
                horario = buscarHorarioCurso(cursosPen.get(posMat));
                if (cursosPen.get(posMat).getNombre().charAt(0) != 'C') {
                    if (chocanHoras(horario)) {
                        cursosPen.get(posMat).setNombre("C-" + cursosPen.get(posMat).getNombre());
                    } else {
                        llenarHorarioAlmn(horario);
                    }
                }
                //Agregamos en la lista
                cursosSelec.add(cursosPen.get(posMat));
                //Quitamos de la lista que los almacena
                cursosPen.remove(posMat);
                //Rellenamos las dos tablas con los item seleccionados
                llenarTblMateriasPendientes(cursosPen);
                llenarTblMatSelec(cursosSelec);
            } else {
                JOptionPane.showMessageDialog(ctrPrin.getVtnPrin(), "No puede matricular en este curso, debido \n"
                        + "a que no se disponen de mas cupos.");
            }

        } else {
            JOptionPane.showMessageDialog(ctrPrin.getVtnPrin(), "Seleecione una materia.");
        }
    }

    /**
     * Se pasan todas las materias de la tabla pendientes a seleccionada.
     */
    private void pasarTodasMaterias() {
        //Auxiliar para gaurdar el curso que ya no tiene capacidad
        ArrayList<CursoMD> csc = new ArrayList<>();
        //Pasamos todos las materias que nos quedan en la tabla cursos 
        //La rrellenamos en cursos seleccionados
        cursosPen.forEach(c -> {
            if (c.getCapaciadActual() > 0) {
                //horario = sesion.cargarHorarioCurso(c);
                horario = buscarHorarioCurso(c);
                if (c.getNombre().charAt(0) != 'C') {
                    if (chocanHoras(horario)) {
                        c.setNombre("C-" + c.getNombre());
                    } else {
                        llenarHorarioAlmn(horario);
                    }
                }
                cursosSelec.add(c);
            } else {
                csc.add(c);
            }
        });
        //Borramos todas las materias de cursos 
        cursosPen = csc;
        llenarTblMateriasPendientes(cursosPen);
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
            if (cursosSelec.get(posMat).getNombre().charAt(0) != 'C') {
                //horario = sesion.cargarHorarioCurso(cursosSelec.get(posMat));
                horario = buscarHorarioCurso(cursosSelec.get(posMat));
                quitarHorarioAlmn(horario);
            }
            //cursosSelec.get(posMat).setNombre(cursosSelec.get(posMat).getNombre().replace("C-", ""));
            //Eliminamos la materia que fue pasada 
            cursosSelec.remove(posMat);
            //Volvemos a llenar las tablas
            llenarTblMateriasPendientes(cursosPen);
            llenarTblMatSelec(cursosSelec);
        } else {
            JOptionPane.showMessageDialog(ctrPrin.getVtnPrin(), "Seleecione una materia.");
        }
    }

    /**
     * Se regresan todas las materias, a la tabla de materias pendientes.
     */
    private void regresarTodasMaterias() {
        //Reiniciamos el array para borrar todos los datos
        cursosSelec = new ArrayList();
        horarioAlmn = new ArrayList<>();
        //llenarTblMateriasPendientes(cursosPen);
        llenarTblMatSelec(cursosSelec);
        cargarMaterias();
        // Borramos los choques en el string porque ya nos devolvimos 
        choques = "";
    }

    /**
     * Mostramos el horario del curso que esta seleccionado
     */
    public void clickHorario() {
        int posPrd = frmAlmCurso.getCmbPrdLectivo().getSelectedIndex();
        int posCurso = frmAlmCurso.getCmbCurso().getSelectedIndex();
        if (posPrd > 0 && posCurso > 0) {
            JDInfoHorario jd = new JDInfoHorario(ctrPrin.getVtnPrin(), false);
            PnlHorarioClase pnl = new PnlHorarioClase();
            CambioPnlCTR.cambioPnl(jd.getPnlHorario(), pnl);
            PnlHorarioCursoCTR ctr = new PnlHorarioCursoCTR(pnl,
                    frmAlmCurso.getCmbCurso().getSelectedItem().toString(),
                    periodos.get(posPrd - 1).getID(), ctrPrin.getConecta());
            ctr.iniciar();
            jd.setLocationRelativeTo(ctrPrin.getVtnPrin());
            jd.setVisible(true);
            jd.setTitle("Horario de " + frmAlmCurso.getCmbCurso().getSelectedItem().toString());
            ctrPrin.eventoJDCerrar(jd);
        } else {
            JOptionPane.showMessageDialog(ctrPrin.getVtnPrin(), "Seleccione un curso primero.");
        }
    }

    private boolean choque;
    private String choques = "";

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
                        || (h.getDia() == hc.getDia() && h.getHoraFin() == hc.getHoraFin())) {
                    choque = true;

                    // Mostramos los choques 
                    choques = "Dia que choca: " + hc.getDia() + "\n"
                            + "Choca hora de: " + hc.getHoraIni() + " || " + hc.getHoraFin() + " \n \n";

                    // System.out.println("Dia que choca: " + hc.getDia());
                    // System.out.println("Choca hora de: " + hc.getHoraIni() + " || " + hc.getHoraFin());
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

    /**
     * Imprimimos el horario de del alumno
     */
    public void horarioAlmn() {
        PnlHorarioClase pnl = new PnlHorarioClase();
        JDInfoHorario jd = new JDInfoHorario(ctrPrin.getVtnPrin(), false);
        CambioPnlCTR.cambioPnl(jd.getPnlHorario(), pnl);
        PnlHorarioAlmnCTR ctr = new PnlHorarioAlmnCTR(horarioAlmn, pnl);
        jd.setTitle("Horario Alumno ");
        ctr.iniciar();
        jd.setVisible(true);
        ctrPrin.eventoJDCerrar(jd);
    }

    /**
     * Llamamos al reporte que se gera al matriculas un alumno
     */
    private void llamaReporteMatricula(String cedula, int idPrd) {
        try {
            JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/reportes/repImpresionMatricula.jasper"));
            Map parametro = new HashMap();
            parametro.put("cedula", cedula);
            parametro.put("idPeriodo", idPrd);
            ctrPrin.getConecta().mostrarReporte(jr, parametro, "Reporte de Matricula");
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "error" + ex);
        }
    }

    /**
     * Esta funcion me devolvera las mallas estado de un alumno se le pasa como
     * parametro el estado, y nos devuelve todas con ese estado
     */
    private ArrayList<MallaAlumnoMD> filtrarMalla(ArrayList<MallaAlumnoMD> mallaCompleta, String estado) {
        ArrayList<MallaAlumnoMD> mf = new ArrayList<>();
        mallaCompleta.forEach(m -> {
            if (m.getEstado().equals(estado)) {
                mf.add(m);
            }
        });
        return mf;
    }

    /**
     * Buscamos las materias en las que el alumno tendra tercera matricula.
     *
     * @param mallaPerdidas
     * @return
     */
    private ArrayList<MallaAlumnoMD> filtrarTercerasMatriculas(ArrayList<MallaAlumnoMD> mallaPerdidas) {
        ArrayList<MallaAlumnoMD> mf = new ArrayList<>();
        mallaPerdidas.forEach(m -> {
            if (m.getMallaNumMatricula() == 2) {
                mf.add(m);
            }
        });
        return mf;
    }

    private String estadoMateria = "";

    /**
     * Buscamos el estado de una materia en la malla completa de un estudiante
     */
    private String estadoMateriaEnMalla(int idMateria) {
        estadoMateria = null;
        mallaCompleta.forEach(m -> {
            if (m.getMateria().getId() == idMateria) {
                estadoMateria = m.getEstado();
            }
        });
        return estadoMateria;
    }

    /**
     * Buscamos el requisito de esta materia filtrando por tipo, los tipos de
     * requisito son Co-requisito = C Pre-requisito = P
     */
    private ArrayList<MateriaRequisitoMD> filtrarRequisitos(int idMateria, String tipo) {
        ArrayList<MateriaRequisitoMD> filtrados = new ArrayList<>();
        requisitos.forEach(m -> {
            if (m.getMateria().getId() == idMateria && m.getTipo().equals(tipo)) {
                filtrados.add(m);
            }
        });
        return filtrados;
    }

    /**
     * Buscamos el numero de matricula de una materia Si es primera nos
     * devolvera 1 Si es segunda nos devolvera 2 Si es tercera nos devolvera 3
     */
    private int buscarNumeroMatricula(int idMateria) {
        int num = -1;
        for (int i = 0; i < mallaCompleta.size(); i++) {
            if (mallaCompleta.get(i).getMateria().getId() == idMateria) {
                num = mallaCompleta.get(i).getMallaNumMatricula();
                break;
            }
        }
        num++;
        return num;
    }

    /**
     * Buscamos el horario del curso en el horario general.
     *
     * @param idCurso
     * @return
     */
    private ArrayList<SesionClaseMD> buscarHorarioCurso(CursoMD curso) {
        ArrayList<SesionClaseMD> hc = new ArrayList<>();
        if (hcurso != null) {
            hcurso.forEach(h -> {
                if (h.getCurso().getId() == curso.getId()) {
                    h.setCurso(curso);
                    hc.add(h);
                }
            });
        }
        return hc;
    }

    private void mostrarChoque() {
        JOptionPane.showMessageDialog(frmAlmCurso, "Choches: \n" + choques);
    }

}
