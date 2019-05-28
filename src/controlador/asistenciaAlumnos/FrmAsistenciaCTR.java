package controlador.asistenciaAlumnos;

import controlador.Libraries.Effects;
import controlador.Libraries.Validaciones;
import controlador.Libraries.cellEditor.ComboBoxCellEditor;
import controlador.principal.VtnPrincipalCTR;
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
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.curso.CursoBD;
import modelo.curso.CursoMD;
import modelo.curso.SesionClaseBD;
import modelo.curso.SesionClaseMD;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import modelo.tipoDeNota.TipoDeNotaMD;
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
    private List<PeriodoLectivoMD> listaPrdSemana;
    private static LocalDate IniSemana;
    private static LocalDate FinSemana;
    private static LocalDate fechaInicial;
    private static int semanas;
    private static ArrayList<String> lista_fecha = new ArrayList<>();

    private static String dia_String;
    private static int dia;
    private static int num_dias;
    private static String Fecha;

    // LISTAS
    private Map<String, DocenteMD> listaDocentes;
    private List<PeriodoLectivoMD> listaPeriodos;
    private List<AlumnoCursoBD> listaNotas;
    private List<MateriaMD> listaMaterias;
    private static List<SesionClaseMD> listaSesionClase;
    private List<CarreraMD> listaNumSemanas;
    private List<TipoDeNotaMD> listaValidaciones;
    private VtnPrincipalCTR ctrPrin;

    private PeriodoLectivoBD prd = new PeriodoLectivoBD();

    // TABLA
    private DefaultTableModel tablaTrad;

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
    private final SesionClaseBD sesionClaseBD;

    {
        periodoBD = new PeriodoLectivoBD();
        almnCursoBD = new AlumnoCursoBD();
        materiaBD = new MateriaBD();
        cursoBD = new CursoBD();
        docenteBD = new DocenteBD();
        sesionClaseBD = new SesionClaseBD();
        carreraBD = new CarreraBD();
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

        vista.getCmbPeriodoLectivoAsis().addItemListener(e -> setLblCarrera());

        vista.getCmbCicloAsis().addActionListener(e -> {
            cargarComboMaterias();
            CargarDiasClase();
        });

        vista.getBtnVerAsistencia().addActionListener(e -> btnVerAsistencia(e));
        vista.getBtnBuscarAsis().addActionListener(e -> buscarDocentes());

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

            vista.getLblHorasAsis().setText(listaMaterias.stream().filter(item -> item.getNombre().equals(materia))
                    .map(c -> c.getHorasPresenciales()).findFirst().orElse(-1) + "");
        } catch (NullPointerException e) {
            vista.getCmbAsignaturaAsis().removeAllItems();
        }
        tablaTrad.setRowCount(0);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="METODOS DE APOYO">
    /*Contruimos la tabla dependiendo de los dias que tiene clase */
    public void CargarDiasClase() {
        jTbl.setModel(tablaTrad);
        new Thread(() -> {

            String cursoNombre = vista.getCmbCicloAsis().getSelectedItem().toString();
            String nombreMateria = vista.getCmbAsignaturaAsis().getSelectedItem().toString();
            listaSesionClase = sesionClaseBD.cargarDiasClase(cursoNombre, getIdPeriodoLectivo(), getIdDocente(), nombreMateria);

            System.out.println("-----> tamaño de sesion en clase  " + listaSesionClase.size());
            for (int i = 0; i < listaSesionClase.size(); i++) {

                SesionClaseMD sesion = listaSesionClase.get(0);
                dia = sesion.getNumeroDias();

                System.out.println(" En el for Num de Dias  " + dia);

            }
            ConstruirTabla(tablaTrad, dia);

        }).start();

    }

    public static void ConstruirTabla(DefaultTableModel modelo, int dia_valor) {
        try {
            List<String> items = new ArrayList<>();
            items.add("1");
            items.add("2");
            modelo = (DefaultTableModel) vista.getTblAsistencia().getModel();
            num_dias = listaSesionClase.size();
            System.out.println("-----> Size de lista sesion clase " + num_dias);
            for (int i = 0; i < num_dias; i++) {

                modelo.addColumn(DiaDeLaSemana(dia_valor));

            }

            vista.getTblAsistencia().setModel(modelo);

            switch (num_dias) {
                case 1:
                    jTbl.getColumnModel().getColumn(6).setCellEditor(new ComboBoxCellEditor(true, items));
                    System.out.println("case 1");
                    break;
                case 2:
                    jTbl.getColumnModel().getColumn(7).setCellEditor(new ComboBoxCellEditor(true, items));
                    System.out.println("case 2");
                    break;
                case 3:
                    jTbl.getColumnModel().getColumn(8).setCellEditor(new ComboBoxCellEditor(true, items));
                    System.out.println("case 3");
                    break;
                case 4:
                    jTbl.getColumnModel().getColumn(9).setCellEditor(new ComboBoxCellEditor(true, items));
                    System.out.println("case 4");
                    break;
                case 5:
                    jTbl.getColumnModel().getColumn(10).setCellEditor(new ComboBoxCellEditor(true, items));
                    System.out.println("case 5");
                    break;
                case 6:
                    jTbl.getColumnModel().getColumn(11).setCellEditor(new ComboBoxCellEditor(true, items));
                    System.out.println("case 6");
                    break;
                default:
                    System.out.println("case DEFAULT");
                    break;
            }

        } catch (NullPointerException e) {

            System.out.println("Ocurrio un problema");
        }

    }

    /*Se valida el dia de la semana*/
    public static String DiaDeLaSemana(int diaValue) {
        System.out.println("Estamos en dia de la semana");
        switch (diaValue) {
            case 1:
                dia_String = "LUNES";
                System.out.println(dia_String);
                break;
            case 2:
                dia_String = "MARTES";
                System.out.println(dia_String);
                break;
            case 3:
                dia_String = "MIERCOLES";
                System.out.println(dia_String);
                break;
            case 4:
                dia_String = "JUEVES";
                System.out.println(dia_String);
                break;
            case 5:
                dia_String = "VIERNES";
                System.out.println(dia_String);
                break;
            case 6:
                dia_String = "SABADO";
                System.out.println(dia_String);

                break;
            default:
                dia_String = "Dia no Asignado";
                System.out.println(dia_String);
                break;

        }
        return dia_String;
    }

    private static void CalculoSemana(int NumeroDia) {
        switch (NumeroDia) {
            case 1:
                IniSemana = fechaInicial.minusDays(0);
                FinSemana = fechaInicial.plusDays(6);

                CalculoSemanaPorSemana();
                break;
            case 2:
                IniSemana = fechaInicial.minusDays(1);
                FinSemana = fechaInicial.plusDays(5);

                CalculoSemanaPorSemana();
                break;
            case 3:
                IniSemana = fechaInicial.minusDays(2);
                FinSemana = fechaInicial.plusDays(4);

                CalculoSemanaPorSemana();
                break;
            case 4:
                IniSemana = fechaInicial.minusDays(3);
                FinSemana = fechaInicial.plusDays(3);

                CalculoSemanaPorSemana();
                break;
            case 5:
                IniSemana = fechaInicial.minusDays(4);
                FinSemana = fechaInicial.plusDays(2);

                CalculoSemanaPorSemana();
                break;
            case 6:
                IniSemana = fechaInicial.minusDays(5);
                FinSemana = fechaInicial.plusDays(1);

                CalculoSemanaPorSemana();
                break;
            case 7:
                IniSemana = fechaInicial.minusDays(6);
                FinSemana = fechaInicial.plusDays(0);

                CalculoSemanaPorSemana();

                break;

        }

    }

    public static void CalculoSemanaPorSemana() {
        lista_fecha = new ArrayList<>();
        for (int i = 1; i <= semanas; i++) {

            Fecha = "Semana " + i + "    " + IniSemana.plusWeeks(i).getDayOfMonth() + " de  " + IniSemana.plusWeeks(i).getMonth() + "  -  " + FinSemana.plusWeeks(i).getDayOfMonth() + " de " + FinSemana.plusWeeks(i).getMonth();
            lista_fecha.add(Fecha);
        }
        
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
            cargarTabla = false;
            String cursoNombre = vista.getCmbCicloAsis().getSelectedItem().toString();
            String nombreMateria = vista.getCmbAsignaturaAsis().getSelectedItem().toString();
            listaNotas = almnCursoBD.selectWhere(cursoNombre, nombreMateria, getIdDocente(), getIdPeriodoLectivo());

            listaNotas.stream().forEach(obj -> {
                funcionCarga.apply(obj, tabla);
            });

            cargarTabla = true;
            vista.getLblResultados().setText(listaNotas.size() + " Resultados");
        }).start();
    }

    private void cargarComboSemanas() {

        //prd.buscarFechaInicioPrd(getIdPeriodoLectivo());
        try {
            System.out.println("-------------------------------------->  metodo carga de semanas");
            fechaInicial = periodoBD.buscarFechaInicioPrd(getIdPeriodoLectivo());
            System.out.println("------------>   "+ fechaInicial);
            vista.getCmbSemana().removeAllItems();
            listaNumSemanas = carreraBD.cargarNumdeSemanas(getIdPeriodoLectivo());
            System.out.println("------->" + listaNumSemanas.size());

            if (listaNumSemanas.size() > 0) {
                CarreraMD carrera = listaNumSemanas.get(0);
                semanas = carrera.getNumSemanas();
                System.out.println("Semanas - - ---------->" + semanas);
                CalculoSemana(fechaInicial.getDayOfWeek().getValue());
                vista.getCmbSemana().removeAllItems();
                lista_fecha.forEach(t -> vista.getCmbSemana().addItem(t));
            }

            lista_fecha.forEach(t -> System.out.println(t));
        } catch (Exception e) {
            System.out.println("%%%%%%%%%%%%%%%%%%%%");
            System.out.println("Murio: " + e.getMessage());
            System.out.println("%%%%%%%%%%%%%%%%%%%%");
        }
    }

    // Agregar Filas
    private BiFunction<AlumnoCursoBD, DefaultTableModel, Void> agregarFilasTrad() {
        return (obj, tabla) -> {

            // System.out.println(obj);
            tabla.addRow(new Object[]{tabla.getDataVector().size() + 1, obj.getAlumno().getIdentificacion(),
                obj.getAlumno().getPrimerApellido(), obj.getAlumno().getSegundoApellido(),
                obj.getAlumno().getPrimerNombre(), obj.getAlumno().getSegundoNombre(), obj.getNumFalta(),});
            return null;
        };
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="EVENTOS">

    private void btnImprimir(ActionEvent e) {
        new Thread(() -> {

            int r = JOptionPane.showOptionDialog(vista,
                    "Reporte de Asistencia de Alumnos\n" + "¿Elegir el tipo de Reporte?", "REPORTE ASISTENCIA",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                    new Object[]{"Asistencia Alumnos"}, "Cancelar");

            Effects.setLoadCursor(vista);

            // ReportesCTR reportes = new ReportesCTR(vista, getIdDocente());
            try {
                sleep(500);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
            desktop.getLblEstado().setText("");
            Effects.setDefaultCursor(vista);
            vista.getBtnVerAsistencia().setEnabled(true);
        }).start();

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
