package controlador.asistenciaAlumnos;

import controlador.Libraries.Effects;
import controlador.Libraries.Validaciones;
import controlador.vistaReportes.ReporteEstadoAlumCTR;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.lang.Thread.sleep;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.CONS;
import modelo.alumno.AlumnoCursoBD;
import modelo.asistencia.AsistenciaBD;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.curso.CursoBD;
import modelo.curso.CursoMD;
import modelo.curso.SesionClaseBD;
import modelo.curso.SesionClaseMD;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import modelo.notas.calendario.CalendarioBD;
import modelo.notas.calendario.CalendarioMD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import modelo.usuario.RolBD;
import modelo.usuario.UsuarioBD;
import vista.asistenciaAlumnos.FrmAsistencia;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Lushito
 */
public class FrmAsistenciaCTR {

    private final VtnPrincipal desktop;
    private static FrmAsistencia vista;
    private final UsuarioBD usuario;
    private final RolBD rolSeleccionado;

    // CALCULOS SEMANAS
    private static int semanas;
    private static ArrayList<Integer> listadias = new ArrayList<>();
    private static int num_horas;
    private static String dia_String;
    private static int dia;

    // LISTAS
    private Map<String, DocenteMD> listaDocentes;
    private List<PeriodoLectivoMD> listaPeriodos;
    private List<AlumnoCursoBD> listaNotas;
    private List<AsistenciaBD> listaHoras;
    private List<MateriaMD> listaMaterias;
    private static List<SesionClaseMD> listaSesionClase;
    private List<CarreraMD> listaNumSemanas;
    private List<CalendarioMD> listaSemanasActivas;

    // TABLA
    private static DefaultTableModel tablaTrad;

    // JTables
    private static JTable jTbl;

    // ACTIVACION DE HILOS
    private boolean cargarTabla = true;

    private final PeriodoLectivoBD periodoBD;
    private final AlumnoCursoBD almnCursoBD;
    private final CursoBD cursoBD;
    private final MateriaBD materiaBD;
    private final DocenteBD docenteBD;
    private final CarreraBD carreraBD;
    private final CalendarioBD calendarioBD;
    private final SesionClaseBD sesionClaseBD;
    private final AsistenciaBD asistenciaBD;

    {
        periodoBD = new PeriodoLectivoBD();
        almnCursoBD = new AlumnoCursoBD();
        materiaBD = new MateriaBD();
        cursoBD = new CursoBD();
        docenteBD = new DocenteBD();
        sesionClaseBD = new SesionClaseBD();
        carreraBD = new CarreraBD();
        calendarioBD = new CalendarioBD();
        asistenciaBD = new AsistenciaBD();
    }

    public FrmAsistenciaCTR(VtnPrincipal desktop, FrmAsistencia vista, UsuarioBD usuario, RolBD rolSeleccionado) {
        this.desktop = desktop;
        this.vista = vista;
        this.usuario = usuario;
        this.rolSeleccionado = rolSeleccionado;
    }

    // <editor-fold defaultstate="collapsed" desc="INITS">
    public void Init() {
        tablaTrad = (DefaultTableModel) vista.getTblAsistencia().getModel();

        jTbl = vista.getTblAsistencia();

        if (rolSeleccionado.getNombre().toLowerCase().contains("docente")) {
            listaDocentes = docenteBD.selectAll(usuario.getUsername());
        } else {
            listaDocentes = docenteBD.selectAll();
        }

        Effects.addInDesktopPane(vista, desktop.getDpnlPrincipal());

        activarForm(false);

        cargarComboDocente();
        cargarComboPeriodos();
        setLblCarrera();
        cargarComboCiclo();
        cargarComboMaterias();
        InitEventos();
        InitTablas();
        activarForm(true);
        cargarComboSemanas();
        InitPermisos();
    }

    private void InitEventos() {

        vista.getCmbDocenteAsis().addActionListener(e -> cargarComboPeriodos());
        vista.getCmbPeriodoLectivoAsis().addActionListener(e -> {
            cargarComboCiclo();
            cargarComboSemanas();
            CargarDiasClase();
        });

        vista.getBtnGuardar().addActionListener(e -> GuardarFaltas());
        vista.getCmbSemana().addActionListener(e -> {
            CargarDiasClase();
        });
        vista.getCmbPeriodoLectivoAsis().addItemListener(e -> setLblCarrera());

        vista.getCmbCicloAsis().addActionListener(e -> {
            cargarComboMaterias();
            CargarDiasClase();
        });

        vista.getBtnVerAsistencia().addActionListener(e -> btnVerAsistencia(e));
        vista.getBtnBuscarAsis().addActionListener(e -> buscarDocentes());
        vista.getBtnImprimir().addActionListener(e -> btnImprimir(e));

        vista.getTxtBuscarAsis().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    String texto = vista.getTxtBuscarAsis().getText();
                    if (texto.length() >= 10) {
                        buscarDocentes();
                    }
                }
            }
        });

        vista.getTxtBuscarAsis().addKeyListener(Validaciones.validarNumeros());

    }

    private void InitTablas() {

        CargarDiasClase();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="ENCABEZADO">
    private void cargarComboDocente() {
        listaDocentes.entrySet().stream().map(c -> c.getKey()).forEach(vista.getCmbDocenteAsis()::addItem);
        tablaTrad.setRowCount(0);
    }

    private void cargarComboPeriodos() {
        vista.getCmbPeriodoLectivoAsis().removeAllItems();
        vista.getLblCarreraAsistencia().setText("");

        listaPeriodos = periodoBD.selectPeriodoWhere(getIdDocente());
        listaPeriodos.stream().map(c -> c.getNombre_PerLectivo()).forEach(vista.getCmbPeriodoLectivoAsis()::addItem);
        tablaTrad.setRowCount(0);

    }

    private void setLblCarrera() {

        vista.getLblCarreraAsistencia()
                .setText(listaPeriodos.stream().filter(item -> item.getId_PerioLectivo() == getIdPeriodoLectivo())
                        .map(c -> c.getCarrera().getNombre()).findFirst().orElse(""));
    }

    private void cargarComboCiclo() {
        try {
            vista.getCmbCicloAsis().removeAllItems();

            cursoBD.selectCicloWhere(getIdDocente(), getIdPeriodoLectivo()).forEach(vista.getCmbCicloAsis()::addItem);
        } catch (NullPointerException e) {
        }
        tablaTrad.setRowCount(0);
    }

    private void cargarComboMaterias() {
        try {
            vista.getCmbAsignaturaAsis().removeAllItems();

            CursoMD curso = new CursoMD();
            DocenteMD docente = new DocenteMD();
            docente.setIdDocente(getIdDocente());
            curso.setDocente(docente);
            PeriodoLectivoMD periodo = new PeriodoLectivoMD();
            periodo.setId_PerioLectivo(getIdPeriodoLectivo());
            curso.setPeriodo(periodo);
            curso.setNombre(vista.getCmbCicloAsis().getSelectedItem().toString());

            listaMaterias = materiaBD.selectWhere(curso);

            listaMaterias.stream().map(c -> c.getNombre()).forEach(vista.getCmbAsignaturaAsis()::addItem);

            String materia = vista.getCmbAsignaturaAsis().getSelectedItem().toString();

        } catch (NullPointerException e) {
            vista.getCmbAsignaturaAsis().removeAllItems();
        }
        tablaTrad.setRowCount(0);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="METODOS DE APOYO">
    /*Contruimos la tabla dependiendo de los dias que tiene clase */
    public void CargarDiasClase() {
        try {

            String inicioSemana = vista.getCmbSemana().getSelectedItem().toString();
            String[] diaArray = inicioSemana.split(" / ", 2);

            LocalDate ini = LocalDate.parse(diaArray[0]);

            String cursoNombre = vista.getCmbCicloAsis().getSelectedItem().toString();
            String nombreMateria = vista.getCmbAsignaturaAsis().getSelectedItem().toString();
            listaSesionClase = sesionClaseBD.cargarDiasClase(cursoNombre, getIdPeriodoLectivo(), getIdDocente(), nombreMateria);
            System.out.println("------> " + cursoNombre);
            System.out.println("------> " + nombreMateria);
            System.out.println("------> " + getIdPeriodoLectivo());
            System.out.println("------> " + getIdDocente());

            vista.getCmbDiaClase().removeAllItems();
            for (int i = 0; i < listaSesionClase.size(); i++) {

                SesionClaseMD sesion = listaSesionClase.get(i);
                dia = sesion.getNumeroDias();

                switch (dia) {
                    case 1:
                        vista.getCmbDiaClase().addItem("LUNES | " + ini);
                        System.out.println("dia de la semana en la que tiene clases:  " + ini);

                        break;
                    case 2:
                        vista.getCmbDiaClase().addItem("MARTES | " + ini.plusDays(1));
                        System.out.println("dia de la semana en la que tiene clases:  " + ini.plusDays(1));
                        break;
                    case 3:
                        vista.getCmbDiaClase().addItem("MIERCOLES | " + ini.plusDays(2));
                        System.out.println("dia de la semana en la que tiene clases:  " + ini.plusDays(2));
                        break;
                    case 4:
                        vista.getCmbDiaClase().addItem("JUEVES | " + ini.plusDays(3));
                        System.out.println("dia de la semana en la que tiene clases:  " + ini.plusDays(3));
                        break;
                    case 5:
                        vista.getCmbDiaClase().addItem("VIERNES | " + ini.plusDays(4));
                        System.out.println("dia de la semana en la que tiene clases:  " + ini.plusDays(4));
                        break;
                    case 6:
                        vista.getCmbDiaClase().addItem("SÁBADO | " + ini.plusDays(5));
                        System.out.println("dia de la semana en la que tiene clases:  " + ini.plusDays(5));
                        break;
                    default:
                        dia_String = "Dia no Asignado";
                        System.out.println(dia_String);
                        break;

                }

                listadias.stream().forEach(a -> System.out.println("dia obtenido " + a));

            }

            System.out.println("Numero de columnas ---->" + jTbl.getColumnCount());
        } catch (Exception e) {
            System.out.println("------->  Error Cargar Dias Clase " + e.getMessage());
        }

    }

    /*Se valida el dia de la semana*/
    public static void DiaDeLaSemana() {
        for (int i = 0; i < jTbl.getRowCount(); i++) {
            jTbl.setValueAt(0, i, 6);
        }

    }

    public boolean Validar() {
        boolean correcto = true;
        int d = CONS.getDia(vista.getCmbDiaClase().getSelectedItem().toString().split(" | ")[0]);
        System.out.println("dia obtenido de parte de cons " + d);
        int numero = asistenciaBD.numHorasPorDia(listaNotas.get(0).getCurso().getId(), d);
        System.out.println("######################### " + numero);
        for (int i = 0; i < jTbl.getRowCount(); i++) {

            int faltas = Integer.parseInt(jTbl.getValueAt(i, 6).toString());
            if (faltas > numero) {
                correcto = false;
                break;
            }
        }
        return correcto;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="VARIOS">
    private int getIdDocente() {
        return listaDocentes.entrySet().stream()
                .filter((entry) -> (entry.getKey().equals(vista.getCmbDocenteAsis().getSelectedItem().toString())))
                .map(c -> c.getValue().getIdDocente()).findFirst().get();
    }

    private int getIdPeriodoLectivo() {
        try {
            String periodo = vista.getCmbPeriodoLectivoAsis().getSelectedItem().toString();
            return listaPeriodos.stream().filter(item -> item.getNombre_PerLectivo().equals(periodo))
                    .map(c -> c.getId_PerioLectivo()).findAny().orElse(-1);
        } catch (NullPointerException e) {
        }
        return -1;
    }

    private void validarCombos() {

        if (vista.getCmbAsignaturaAsis().getItemCount() > 0) {
            vista.getBtnVerAsistencia().setEnabled(true);
        } else {
            vista.getBtnVerAsistencia().setEnabled(false);
        }
    }

    private int getHoras() {
        return listaMaterias.stream()
                .filter(item -> item.getNombre().equals(vista.getCmbAsignaturaAsis().getSelectedItem().toString()))
                .map(c -> c.getHorasPresenciales()).findFirst().orElse(1);
    }

    private int getSelectedRowTrad() {
        return vista.getTblAsistencia().getSelectedRow();
    }

    private int getSelectedColumTrad() {
        return vista.getTblAsistencia().getSelectedColumn();
    }

    private void activarForm(boolean estado) {

        if (rolSeleccionado.getNombre().toLowerCase().contains("docente")) {
            vista.getTxtBuscarAsis().setVisible(false);
            vista.getBtnBuscarAsis().setVisible(false);
            vista.getCmbDocenteAsis().setEnabled(false);
        } else {
            vista.getTxtBuscarAsis().setEnabled(estado);
            vista.getBtnBuscarAsis().setEnabled(estado);
            vista.getCmbDocenteAsis().setEnabled(estado);
        }

        vista.getCmbPeriodoLectivoAsis().setEnabled(estado);
        vista.getCmbCicloAsis().setEnabled(estado);
        vista.getCmbAsignaturaAsis().setEnabled(estado);
        vista.getTblAsistencia().setEnabled(estado);
    }

    private void cargarTabla(DefaultTableModel tabla, BiFunction<AlumnoCursoBD, DefaultTableModel, Void> funcionCarga) {
        new Thread(() -> {
            int d = CONS.getDia(vista.getCmbDiaClase().getSelectedItem().toString().split(" | ")[0]);

            cargarTabla = false;
            String cursoNombre = vista.getCmbCicloAsis().getSelectedItem().toString();
            String nombreMateria = vista.getCmbAsignaturaAsis().getSelectedItem().toString();
            String fecha = vista.getCmbDiaClase().getSelectedItem().toString().split(" | ")[2];

            //listaAsistencia = asistenciaBD.selectWhere(almnCursoBD);
            listaNotas = almnCursoBD.selectParaAsistencia(cursoNombre, nombreMateria,
                    getIdDocente(), getIdPeriodoLectivo(), fecha);

            listaNotas.forEach(la -> {
                Object[] v = {tabla.getRowCount() + 1,
                    la.getAlumno().getIdentificacion(),
                    la.getAlumno().getPrimerApellido(),
                    la.getAlumno().getSegundoApellido(),
                    la.getAlumno().getPrimerNombre(),
                    la.getAlumno().getSegundoNombre(),
                    la.getFaltas()};
                tabla.addRow(v);
            });

            cargarTabla = true;
            vista.getLblResultados().setText(listaNotas.size() + " Resultados");
            //DiaDeLaSemana();
        }).start();
    }

    private void cargarComboSemanas() {

        try {
            System.out.println("-------------------------------------->  metodo carga de semanas");
            listaSemanasActivas = calendarioBD.cargarSemanas(getIdPeriodoLectivo());
            vista.getCmbSemana().removeAllItems();
            listaNumSemanas = carreraBD.cargarNumdeSemanas(getIdPeriodoLectivo());

            if (listaNumSemanas.size() > 0 && listaSemanasActivas.size() > 0) {
                CarreraMD carrera = listaNumSemanas.get(0);
                CalendarioMD calen = listaSemanasActivas.get(0);

                calen.getSemanasActivas();
                semanas = carrera.getNumSemanas();
                System.out.println("Semanas - - ---------->" + semanas);
                listaSemanasActivas.forEach(t -> vista.getCmbSemana().addItem(t.getSemanasActivas()));
            }

        } catch (Exception e) {
            System.out.println("%%%%%%%%%%%%%%%%%%%%");
            System.out.println("Murio: " + e.getMessage());
            System.out.println("%%%%%%%%%%%%%%%%%%%%");
        }
    }

    // Agregar Filas 
    // agregar a la tabla asistencia
//    private Consumer<AlumnoCursoBD> agregarFilasTrad() {
//        return (obj) -> {
//
//            // System.out.println(obj);
//            tablaTrad.addRow(new Object[]{tablaTrad.getDataVector().size() + 1, obj.getAlumno().getIdentificacion(),
//                obj.getAlumno().getPrimerApellido(), obj.getAlumno().getSegundoApellido(),
//                obj.getAlumno().getPrimerNombre(), obj.getAlumno().getSegundoNombre()});
//        };
//    }
    private BiFunction<AlumnoCursoBD, DefaultTableModel, Void> agregarFilasTrad() {
        return (obj, tabla) -> {

            // System.out.println(obj);
            tabla.addRow(new Object[]{tabla.getDataVector().size() + 1, obj.getAlumno().getIdentificacion(),
                obj.getAlumno().getPrimerApellido(), obj.getAlumno().getSegundoApellido(),
                obj.getAlumno().getPrimerNombre(), obj.getAlumno().getSegundoNombre()});
            return null;
        };
    }

    private void GuardarFaltas() {
        String fecha = vista.getCmbDiaClase().getSelectedItem().
                toString().split(" | ")[2];

        if (Validar()) {
            //Eliminamos todos los datos anteriores. 
            asistenciaBD.eliminarACursoDia(obtenerIDCurso(), fecha);
            //Luego insertamos todo
            for (int i = 0; i < jTbl.getRowCount(); i++) {
                int faltas = Integer.parseInt(jTbl.getValueAt(i, 6).toString());

                asistenciaBD.insertar(listaNotas.get(i).getId(),
                        vista.getCmbDiaClase().getSelectedItem().
                                toString().split(" | ")[2], faltas);
                desktop.getLblEstado().setText("Los datos se han guardado exitosamente");
            }
        } else {
            JOptionPane.showMessageDialog(vista, "Los datos no se han guardado \n"
                    + " Verifique el numero de Faltas insertado"
                    + "\n");
        }
    }

    /**
     * Obtenemos el id del curso al que pertenecen todos los alumnos
     *
     * @return
     */
    private int obtenerIDCurso() {
        int id = 0;
        if (listaNotas != null) {
            for (int i = 0; i < listaNotas.size(); i++) {
                id = listaNotas.get(i).getCurso().getId();
            }
        }
        return id;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="EVENTOS">
    private void btnImprimir(ActionEvent e) {
        Effects.setLoadCursor(vista);

        reporteAsistenciaCTR reportes = new reporteAsistenciaCTR(vista, getIdPeriodoLectivo());

        reportes.generarReporteAsistencia();
        Effects.setDefaultCursor(vista);

    }

    private void btnVerAsistencia(ActionEvent e) {
        if (cargarTabla) {
            // // String modalidad = listaPeriodos.stream()
            // .filter(item -> item.getId_PerioLectivo() == getIdPeriodoLectivo())
            // .map(c -> c.getCarrera().getModalidad()).findFirst().orElse("");

            jTbl.removeAll();
            tablaTrad.setRowCount(0);
            cargarTabla(tablaTrad, agregarFilasTrad());

        } else {
            JOptionPane.showMessageDialog(vista, "YA HAY UNA CARGA PENDIENTE!");
        }

        vista.setTitle("Asistencia Alumnos " + vista.getCmbCicloAsis().getSelectedItem().toString());
    }

    private void buscarDocentes() {
        activarForm(false);
        vista.getCmbDocenteAsis().setSelectedItem(listaDocentes.entrySet().stream()
                .filter(entry -> entry.getValue().getIdentificacion().equals(vista.getTxtBuscarAsis().getText()))
                .map(c -> c.getKey()).findFirst().orElse(""));
        activarForm(true);
    }

    /*
     * public void CargarCombo(JTable tabla, TableColumn columna) { JComboBox c =
     * new JComboBox(); c.addItem("1"); c.addItem("2"); c.addItem("3");
     * c.addItem("4");
     * 
     * columna.setCellEditor(new DefaultCellEditor(c)); DefaultTableCellRenderer r =
     * new DefaultTableCellRenderer(); r.setToolTipText("Seleccionar");
     * columna.setCellRenderer(r); }
     */
    // </editor-fold>
    private void InitPermisos() {
        vista.getBtnImprimir().getAccessibleContext().setAccessibleName("Asistencia-Imprimir");
        vista.getBtnVerAsistencia().getAccessibleContext().setAccessibleName("Asistencia-Ver Asistencia");

        CONS.activarBtns(vista.getBtnImprimir(), vista.getBtnVerAsistencia());
    }

}
