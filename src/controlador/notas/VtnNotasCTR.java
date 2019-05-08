package controlador.notas;

import controlador.Libraries.Effects;
import controlador.Libraries.Middlewares;
import controlador.Libraries.Validaciones;
import controlador.notas.ux.RowStyle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import modelo.alumno.AlumnoCursoBD;
import modelo.curso.CursoBD;
import modelo.curso.CursoMD;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import modelo.notas.NotasBD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import modelo.tipoDeNota.TipoDeNotaBD;
import modelo.tipoDeNota.TipoDeNotaMD;
import modelo.usuario.RolBD;
import modelo.usuario.UsuarioBD;
import vista.notas.VtnNotasAlumnoCurso;
import vista.principal.VtnPrincipal;

/**
 *
 * @author MrRainx
 */
public class VtnNotasCTR {

    private final VtnPrincipal desktop;
    private final VtnNotasAlumnoCurso vista;
    private final UsuarioBD usuario;
    private final RolBD rolSeleccionado;

    //LISTAS
    private Map<String, DocenteMD> listaDocentes;
    private List<PeriodoLectivoMD> listaPeriodos;
    private List<AlumnoCursoBD> listaNotas;
    private List<MateriaMD> listaMaterias;
    private List<TipoDeNotaMD> listaValidaciones;

    //TABLA
    private DefaultTableModel tablaNotasTrad;
    private DefaultTableModel tablaNotasDuales;

    //ACTIVACION DE HILOS
    private boolean cargarTabla = true;

    public VtnNotasCTR(VtnPrincipal desktop, VtnNotasAlumnoCurso vista, UsuarioBD usuario, RolBD rolSeleccionado) {
        this.desktop = desktop;
        this.vista = vista;
        this.usuario = usuario;
        this.rolSeleccionado = rolSeleccionado;
    }

    // <editor-fold defaultstate="collapsed" desc="INITS">    
    public void Init() {

        vista.canEdit[1] = true;

        tablaNotasTrad = (DefaultTableModel) vista.getTblNotas().getModel();
        tablaNotasDuales = (DefaultTableModel) vista.getTblNotasDuales().getModel();
        if (rolSeleccionado.getNombre().toLowerCase().contains("docente")) {
            listaDocentes = DocenteBD.selectAll(usuario.getUsername());
        } else {
            listaDocentes = DocenteBD.selectAll();
        }

        Effects.addInDesktopPane(vista, desktop.getDpnlPrincipal());

        activarForm(false);
        cargarComboDocente();
        cargarComboPeriodos();
        setLblCarrera();
        cargarComboCiclo();
        cargarComboMaterias();
        InitEventos();

        activarForm(true);

    }

    private void InitEventos() {

        vista.getCmbDocente().addActionListener(e -> cargarComboPeriodos());

        vista.getCmbPeriodoLectivo().addActionListener(e -> cargarComboCiclo());

        vista.getCmbPeriodoLectivo().addItemListener(e -> setLblCarrera());

        vista.getCmbCiclo().addActionListener(e -> cargarComboMaterias());

        vista.getBtnVerNotas().addActionListener(e -> btnVerNotas(e));

        vista.getBtnImprimir().addActionListener(e -> btnImprimir(e));

        vista.getBtnBuscar().addActionListener(e -> buscarDocente());

        vista.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    String texto = vista.getTxtBuscar().getText();
                    if (texto.length() >= 10) {
                        buscarDocente();
                    }
                }
            }
        });

        vista.getTxtBuscar().addKeyListener(Validaciones.validarNumeros());

        tablaNotasTrad.addTableModelListener(new TableModelListener() {

            boolean active = false;

            @Override
            public void tableChanged(TableModelEvent e) {
                if (!active && e.getType() == TableModelEvent.UPDATE) {

                    active = true;

                    carlcularNotasTradicionales(tablaNotasTrad);

                    active = false;
                }

            }

        });

        tablaNotasDuales.addTableModelListener(new TableModelListener() {
            boolean active = false;

            @Override
            public void tableChanged(TableModelEvent e) {

                if (!active && e.getType() == TableModelEvent.UPDATE) {
                    active = true;
                    cacularNotasDuales(tablaNotasDuales);
                    active = false;
                }

            }
        });
    }

    // </editor-fold>  
    // <editor-fold defaultstate="collapsed" desc="ENCABEZADO">    
    private void cargarComboDocente() {
        listaDocentes.entrySet().forEach((entry) -> {
            String key = entry.getKey();
            DocenteMD value = entry.getValue();

            vista.getCmbDocente().addItem(key);

        });
        tablaNotasTrad.setRowCount(0);
        tablaNotasDuales.setRowCount(0);
    }

    private void cargarComboPeriodos() {
        vista.getCmbPeriodoLectivo().removeAllItems();
        vista.getLblCarrera().setText("");

        listaPeriodos = PeriodoLectivoBD.selectPeriodoWhere(getIdDocente());
        listaPeriodos
                .stream()
                .forEach(obj -> {
                    vista.getCmbPeriodoLectivo().addItem(obj.getNombre_PerLectivo());
                });
        tablaNotasTrad.setRowCount(0);
        tablaNotasDuales.setRowCount(0);
    }

    private void setLblCarrera() {

        vista.getLblCarrera().setText(listaPeriodos
                .stream()
                .filter(item -> item.getId_PerioLectivo() == getIdPeriodoLectivo())
                .map(c -> c.getCarrera().getNombre())
                .findFirst()
                .orElse("")
        );

    }

    private void cargarComboCiclo() {
        try {
            vista.getCmbCiclo().removeAllItems();

            CursoBD.selectCicloWhere(getIdDocente(), getIdPeriodoLectivo())
                    .stream()
                    .forEach(obj -> {
                        vista.getCmbCiclo().addItem(obj + "");
                    });
        } catch (NullPointerException e) {
        }
        tablaNotasTrad.setRowCount(0);
        tablaNotasDuales.setRowCount(0);
    }

    private void cargarComboMaterias() {
        try {
            vista.getCmbAsignatura().removeAllItems();

            CursoMD curso = new CursoMD();
            DocenteMD docente = new DocenteMD();
            docente.setIdDocente(getIdDocente());
            curso.setDocente(docente);
            PeriodoLectivoMD periodo = new PeriodoLectivoMD();
            periodo.setId_PerioLectivo(getIdPeriodoLectivo());
            curso.setPeriodo(periodo);
            curso.setNombre(vista.getCmbCiclo().getSelectedItem().toString());

            listaMaterias = MateriaBD.selectWhere(curso);

            listaMaterias.stream()
                    .forEach(obj -> {
                        vista.getCmbAsignatura().addItem(obj.getNombre());
                        vista.getLblHoras().setText("" + obj.getHorasPresenciales());
                    });

            listaValidaciones = TipoDeNotaBD.selectValidaciones(getIdPeriodoLectivo());

            validarCombos();
        } catch (NullPointerException e) {
            vista.getCmbAsignatura().removeAllItems();
        }
        tablaNotasTrad.setRowCount(0);
        tablaNotasDuales.setRowCount(0);
    }

    // </editor-fold>  
    // <editor-fold defaultstate="collapsed" desc="VARIOS">
    private int getIdDocente() {
        return listaDocentes.entrySet().stream()
                .filter((entry) -> (entry.getKey().equals(vista.getCmbDocente().getSelectedItem().toString())))
                .map(c -> c.getValue().getIdDocente())
                .findAny()
                .get();

    }

    private void mensajeDeError() {
        JOptionPane.showMessageDialog(vista, "INGRESE UN NUMERO CORRECTO "
                + "\n       EJEMPLO (15.6)");

    }

    private void refreshTabla(Function<AlumnoCursoBD, Void> funcion, DefaultTableModel tabla) {
        System.out.println("REFRESH");
        activarForm(false);
        tabla.setRowCount(0);
        listaNotas.stream().forEach(obj -> {
            funcion.apply(obj);
        });
        activarForm(true);
    }

    private void validarCombos() {

        if (vista.getCmbAsignatura().getItemCount() > 0) {
            vista.getBtnVerNotas().setEnabled(true);
        } else {
            vista.getBtnVerNotas().setEnabled(false);
        }
    }

    private int getIdPeriodoLectivo() {
        try {
            String periodo = vista.getCmbPeriodoLectivo().getSelectedItem().toString();
            return listaPeriodos
                    .stream()
                    .filter(item -> item.getNombre_PerLectivo().equals(periodo))
                    .map(c -> c.getId_PerioLectivo())
                    .findAny()
                    .orElse(-1);
        } catch (NullPointerException e) {
        }
        return -1;
    }

    private int getSelectedRowTrad() {
        return vista.getTblNotas().getSelectedRow();
    }

    private int getSelectedColumTrad() {
        return vista.getTblNotas().getSelectedColumn();
    }

    private int getSelectedRowDuales() {
        return vista.getTblNotasDuales().getSelectedRow();
    }

    private int getSelectedColumDuales() {
        return vista.getTblNotasDuales().getSelectedColumn();
    }

    private int getHoras() {
        return listaMaterias
                .stream()
                .filter(item -> item.getNombre().equals(vista.getCmbAsignatura().getSelectedItem().toString()))
                .map(c -> c.getHorasPresenciales())
                .findFirst()
                .orElse(1);
    }

    private void activarForm(boolean estado) {

        if (rolSeleccionado.getNombre().toLowerCase().contains("docente")) {
            vista.getTxtBuscar().setVisible(false);
            vista.getBtnBuscar().setVisible(false);
            vista.getCmbDocente().setEnabled(false);
        } else {
            vista.getTxtBuscar().setEnabled(estado);
            vista.getBtnBuscar().setEnabled(estado);
            vista.getCmbDocente().setEnabled(estado);
        }

        vista.getCmbPeriodoLectivo().setEnabled(estado);
        vista.getCmbCiclo().setEnabled(estado);
        vista.getCmbAsignatura().setEnabled(estado);
        vista.getTblNotas().setEnabled(estado);
    }

    private void editarFaltas(int fila, int columna, DefaultTableModel tabla, int colEstado, int conPorcentaje, int colAsistencia) {

        String faltasText = tabla.getValueAt(fila, columna).toString();
        if (Validaciones.isInt(faltasText)) {
            int faltas = new Integer(faltasText);

            int horas = getHoras();

            int porcentaje = 0;

            if (horas <= 0) {
                horas = 1;
            }

            porcentaje = (faltas * horas) / 100;

            tabla.setValueAt(porcentaje, fila, conPorcentaje);

            String estado = tabla.getValueAt(fila, colEstado).toString();
            String asistencia = tabla.getValueAt(fila, colAsistencia).toString();

            if (!estado.equalsIgnoreCase("RETIRADO") && asistencia.equalsIgnoreCase("RETIRADO")) {
                if (porcentaje >= 25) {
                    vista.getTblNotas().setValueAt("REPROBADO", fila, colEstado);
                } else {
                    vista.getTblNotas().setValueAt("APROBADO", fila, colEstado);
                }
            }

            AlumnoCursoBD alumno = listaNotas.get(fila);
            tabla.setValueAt(faltas, fila, columna);

            alumno.setEstado(estado);
            alumno.setNumFalta(faltas);
            alumno.editar();
        } else {
            JOptionPane.showMessageDialog(vista, "INGRESE SOLO NUMERO ENTEROS!!!");
        }
    }

    // </editor-fold>  
    // <editor-fold defaultstate="collapsed" desc="CARRERAS TRADICIONALES">    
    private void carlcularNotasTradicionales(TableModel datos) {

        int fila = getSelectedRowTrad();
        int colum = getSelectedColumTrad();

        try {
            String nombreNota = "";
            switch (colum) {
                case 6:
                    nombreNota = "APORTE 1";

                    String aporte1 = datos.getValueAt(getSelectedRowTrad(), getSelectedColumTrad()).toString();

                    guardarBDTrad(aporte1, nombreNota);

                    break;
                case 7:
                    nombreNota = "EXAMEN INTERCICLO";

                    String examenInterCiclo = datos.getValueAt(getSelectedRowTrad(), getSelectedColumTrad()).toString();

                    guardarBDTrad(examenInterCiclo, nombreNota);
                    break;
                case 9:
                    nombreNota = "APORTE 2";

                    String aporte2 = datos.getValueAt(getSelectedRowTrad(), getSelectedColumTrad()).toString();

                    guardarBDTrad(aporte2, nombreNota);
                    break;
                case 10:
                    nombreNota = "EXAMEN FINAL";

                    String examenFinal = datos.getValueAt(getSelectedRowTrad(), getSelectedColumTrad()).toString();

                    guardarBDTrad(examenFinal, nombreNota);
                    break;
                case 11:
                    nombreNota = "EXAMEN SUPLETORIO";

                    String examenSupletorio = datos.getValueAt(getSelectedRowTrad(), getSelectedColumTrad()).toString();

                    guardarBDTrad(examenSupletorio, nombreNota);
                    break;

                case 14:
                    String materia = vista.getCmbAsignatura().getSelectedItem().toString();
                    String value = tablaNotasTrad.getValueAt(getSelectedRowTrad(), 14).toString();

                    if (value.isEmpty()) {
                        value = "0";
                    }

                    if (Validaciones.isInt(value)) {
                        int faltas = (int) Middlewares.conversor(value);
                        listaMaterias
                                .stream()
                                .filter(item -> item.getNombre().equals(materia))
                                .collect(Collectors.toList())
                                .forEach(setPorcentaje(faltas));
                        editarTrad();
                    } else {
                        JOptionPane.showMessageDialog(vista, "INGRESE SOLO NUMEROS ENTEROS!!");
                    }

                    refreshTabla(agregarFilasTradicionales(), tablaNotasTrad);
                    break;
                case 16:
                    editarAsistencia(fila, colum, tablaNotasTrad, 12, agregarFilasTradicionales());
                    break;
                default:
                    break;
            }

        } catch (NumberFormatException e) {

            System.out.println(e.getMessage());
        }
    }

    private void guardarBDTrad(String nota, String nombreNota) {

        if (Validaciones.isDecimal(nota)) {
            double value = Middlewares.conversor(nota);

            TipoDeNotaMD rango = getRango(nombreNota);
            if (!rango.getNombre().equalsIgnoreCase("EXAMEN FINAL")) {
                if (value >= 0 && value <= rango.getValorMaximo()) {
                    tablaNotasTrad.setValueAt(Middlewares.conversor(nota), getSelectedRowTrad(), getSelectedColumTrad());
                    sumarColumnasTrad();
                    editarTrad();
                    refreshTabla(agregarFilasTradicionales(), tablaNotasTrad);

                } else {
                    errorDeNota(rango);
                }
            } else {
                if (value >= 0 && value <= rango.getValorMaximo()) {

                    if (value >= rango.getValorMinimo() && value <= rango.getValorMaximo()) {
                        tablaNotasTrad.setValueAt(Middlewares.conversor(nota), getSelectedRowTrad(), getSelectedColumTrad());
                        sumarColumnasTrad();
                        editarTrad();
                        refreshTabla(agregarFilasTradicionales(), tablaNotasTrad);
                    } else {
                        tablaNotasTrad.setValueAt(Middlewares.conversor(nota), getSelectedRowTrad(), getSelectedColumTrad());
                        tablaNotasTrad.setValueAt("REPROBADO", getSelectedRowTrad(), 13);
                        sumarColumnasTrad();
                        editarTrad();
                        refreshTabla(agregarFilasTradicionales(), tablaNotasTrad);
                    }
                } else {
                    errorDeNota(rango);
                }
            }
        } else {
            mensajeDeError();
            refreshTabla(agregarFilasTradicionales(), tablaNotasTrad);

        }
    }

    private void errorDeNota(TipoDeNotaMD rango) {
        JOptionPane.showMessageDialog(vista, "EL RANGO DE LA NOTA DEBE ESTAR ENTRE: " + 0 + " Y " + rango.getValorMaximo());
        refreshTabla(agregarFilasTradicionales(), tablaNotasTrad);
    }

    private void sumarColumnasTrad() {
        int fila = getSelectedRowTrad();

        double aporte1;
        double examenInterCiclo;
        double totalInterciclo;

        double aporte2;
        double examenFinal;
        double examenSupletorio;

        double notaFinal;

        aporte1 = Middlewares.conversor(tablaNotasTrad.getValueAt(fila, 6).toString());
        examenInterCiclo = Middlewares.conversor(tablaNotasTrad.getValueAt(fila, 7).toString());
        totalInterciclo = aporte1 + examenInterCiclo;
        tablaNotasTrad.setValueAt(totalInterciclo, fila, 8);

        aporte2 = Middlewares.conversor(tablaNotasTrad.getValueAt(fila, 9).toString());
        examenFinal = Middlewares.conversor(tablaNotasTrad.getValueAt(fila, 10).toString());
        examenSupletorio = Middlewares.conversor(tablaNotasTrad.getValueAt(fila, 11).toString());

        if (examenSupletorio != 0) {
            notaFinal = totalInterciclo + aporte2 + examenSupletorio;
        } else {
            notaFinal = totalInterciclo + aporte2 + examenFinal;
        }
        tablaNotasTrad.setValueAt(Math.round(notaFinal), fila, 12);

    }

    private Consumer<MateriaMD> setPorcentaje(int faltas) {
        return obj -> {

            int horasMateria = obj.getHorasPresenciales();
            int porcentaje = 1;
            if (horasMateria != 0) {
                porcentaje = (faltas * obj.getHorasPresenciales()) / 100;
            }
            vista.getTblNotas().setValueAt(porcentaje, getSelectedRowTrad(), 15);
            String estado = vista.getTblNotas().getValueAt(getSelectedRowTrad(), 13).toString();
            String asistencia = vista.getTblNotas().getValueAt(getSelectedRowTrad(), 16).toString();
            if (!estado.equalsIgnoreCase("RETIRADO") && asistencia.equalsIgnoreCase("RETIRADO")) {
                if (porcentaje >= 25) {
                    vista.getTblNotas().setValueAt("REPROBADO", getSelectedRowTrad(), 13);
                } else {
                    vista.getTblNotas().setValueAt("APROBADO", getSelectedRowTrad(), 13);
                }
            }
        };
    }

    private void editarTrad() {
        vista.getTblNotas().setEnabled(false);
        int fila = getSelectedRowTrad();

        AlumnoCursoBD alumno = listaNotas.get(fila);

        alumno.setEstado(vista.getTblNotas().getValueAt(fila, 13).toString());

        alumno.setNumFalta(Integer.valueOf(tablaNotasTrad.getValueAt(fila, 14).toString()));

        String asistencia = vista.getTblNotas().getValueAt(fila, 16).toString().toLowerCase();

        if (asistencia.contains("desertor")) {
            alumno.setAsistencia("Desertor");
        }
        if (asistencia.contains("asiste")) {
            alumno.setAsistencia("Asiste");
        }
        if (asistencia.contains("retirado")) {
            alumno.setAsistencia("Retirado");
        }
        if (asistencia.contains("no asiste")) {
            alumno.setAsistencia("No Asiste");
        }

        alumno.getNotas().stream()
                .filter(buscar("APORTE 1"))
                .collect(Collectors.toList())
                .forEach(editarNota(fila, 6, tablaNotasTrad));
        alumno.getNotas().stream()
                .filter(buscar("EXAMEN INTERCICLO"))
                .collect(Collectors.toList())
                .forEach(editarNota(fila, 7, tablaNotasTrad));
        alumno.getNotas().stream()
                .filter(buscar("NOTA INTERCICLO"))
                .collect(Collectors.toList())
                .forEach(editarNota(fila, 8, tablaNotasTrad));
        alumno.getNotas().stream()
                .filter(buscar("APORTE 2"))
                .collect(Collectors.toList())
                .forEach(editarNota(fila, 9, tablaNotasTrad));
        alumno.getNotas().stream()
                .filter(buscar("EXAMEN FINAL"))
                .collect(Collectors.toList())
                .forEach(editarNota(fila, 10, tablaNotasTrad));
        alumno.getNotas().stream()
                .filter(buscar("EXAMEN SUPLETORIO"))
                .collect(Collectors.toList())
                .forEach(editarNota(fila, 11, tablaNotasTrad));

        alumno.setNotaFinal(Middlewares.conversor(vista.getTblNotas().getValueAt(fila, 12).toString()));

        alumno.editar();
        vista.getTblNotas().setEnabled(true);
    }

    private TipoDeNotaMD getRango(String nombreNota) {
        return listaValidaciones
                .stream()
                .filter(item -> item.getNombre().equals(nombreNota))
                .findFirst()
                .get();
    }

    private Predicate<NotasBD> buscar(String busqueda) {
        return item -> item.getTipoDeNota().getNombre().equals(busqueda);
    }

    private Consumer<NotasBD> editarNota(int fila, int columna, TableModel tabla) {
        return obj -> {
            String text = tabla.getValueAt(fila, columna).toString();
            obj.setNotaValor(Middlewares.conversor(text));
            obj.editar();
        };
    }

    private void cargarTabla(Function<AlumnoCursoBD, Void> funcion, DefaultTableModel tabla, JTable Jtable, int fila) {
        new Thread(() -> {
            RowStyle row = new RowStyle(fila);

            Jtable.setDefaultRenderer(Object.class, row);

            cargarTabla = false;
            tabla.setRowCount(0);
            vista.getTblNotas().setEnabled(false);
            Effects.setLoadCursor(vista);

            String cursoNombre = vista.getCmbCiclo().getSelectedItem().toString();
            String nombreMateria = vista.getCmbAsignatura().getSelectedItem().toString();

            activarForm(false);

            listaNotas = null;

            listaNotas = AlumnoCursoBD.selectWhere(cursoNombre, nombreMateria, getIdDocente(), getIdPeriodoLectivo());

            listaNotas.stream().forEach(obj -> {
                funcion.apply(obj);
            });

            activarForm(true);

            vista.getLblResultados().setText(listaNotas.size() + " Resultados");

            Effects.setDefaultCursor(vista);

            cargarTabla = true;
            vista.getTblNotas().setEnabled(true);
            validarCombos();
        }).start();

    }

    private Consumer<MateriaMD> setPorcentajeVetor(Vector<Object> row, int faltas, AlumnoCursoBD alumno) {
        return obj -> {

            double horaPresenciales = obj.getHorasPresenciales();

            int porcentaje = 0;

            if (horaPresenciales != 0) {

                porcentaje = (int) Math.ceil(((faltas * 100) / horaPresenciales));
            }

            double notaSupletorio = (Double) row.get(11);

            if (notaSupletorio != 0) {
                validarAprobado(alumno, notaSupletorio, porcentaje, row);
            } else {
                validarAprobado(alumno, (double) row.get(10), porcentaje, row);
            }
            row.add(14, faltas);
            row.add(15, porcentaje);
        };
    }

    private void validarAprobado(AlumnoCursoBD alumno, double notaValidar, int porcentaje, List<Object> row) {
        if (!alumno.getEstado().equalsIgnoreCase("RETIRADO") || !alumno.getAsistencia().equalsIgnoreCase("RETIRADO")) {
            int notaFinal = (Integer) row.get(12);
            TipoDeNotaMD rango = getRango("EXAMEN SUPLETORIO");
            if (notaValidar >= rango.getValorMinimo()) {

                if (porcentaje >= 25 || alumno.getNotaFinal() < rango.getValorMinimo() || notaFinal < getRango("NOTA FINAL").getValorMinimo()) {
                    row.add(13, "REPROBADO");
                } else {
                    row.add(13, "APROBADO");
                }
            } else {
                row.add(13, "REPROBADO");
            }
        } else {
            row.add(13, "RETIRADO");
        }
    }

    private Function<AlumnoCursoBD, Void> agregarFilasTradicionales() {

        return (obj) -> {
            Vector<Object> row = new Vector<>();

            row.add(0, tablaNotasTrad.getDataVector().size() + 1);
            row.add(1, obj.getAlumno().getIdentificacion());
            row.add(2, obj.getAlumno().getPrimerApellido());
            row.add(3, obj.getAlumno().getSegundoApellido());
            row.add(4, obj.getAlumno().getPrimerNombre());
            row.add(5, obj.getAlumno().getSegundoNombre());

            System.out.println(obj.getId() + "<------------------");

            row.add(6, obj.getNotas().stream().filter(buscar("APORTE 1")).findAny().get().getNotaValor());
            row.add(7, obj.getNotas().stream().filter(buscar("EXAMEN INTERCICLO")).findAny().get().getNotaValor());
            row.add(8, obj.getNotas().stream().filter(buscar("NOTA INTERCICLO")).findAny().get().getNotaValor());
            row.add(9, obj.getNotas().stream().filter(buscar("APORTE 2")).findAny().get().getNotaValor());
            row.add(10, obj.getNotas().stream().filter(buscar("EXAMEN FINAL")).findAny().get().getNotaValor());
            row.add(11, obj.getNotas().stream().filter(buscar("EXAMEN SUPLETORIO")).findAny().get().getNotaValor());

            int notaFinal = (int) Middlewares.conversor("" + obj.getNotaFinal());

            row.add(12, notaFinal);

            int faltas = obj.getNumFalta();

            String materia = vista.getCmbAsignatura().getSelectedItem().toString();

            listaMaterias.stream().filter(item -> item.getNombre().equals(materia))
                    .forEach(setPorcentajeVetor(row, faltas, obj));

            row.add(16, obj.getAsistencia());

            tablaNotasTrad.addRow(row);
            return null;
        };
    }

    // </editor-fold>  
    // <editor-fold defaultstate="collapsed" desc="CARRERAS DUALES">
    private void sumarColumnas(DefaultTableModel tabla, int filaSelecionada, int columa1, int columa2, int columaRespuesta, String forma) {

        double valor1 = Middlewares.conversor(tabla.getValueAt(filaSelecionada, columa1).toString());
        double valor2 = Middlewares.conversor(tabla.getValueAt(filaSelecionada, columa2).toString());

        double suma = valor1 + valor2;

        if (forma.equalsIgnoreCase("INT")) {
            tabla.setValueAt(Math.round(suma), filaSelecionada, columaRespuesta);
        } else if (forma.equalsIgnoreCase("DOUBLE")) {
            tabla.setValueAt(Middlewares.conversor(suma + ""), filaSelecionada, columaRespuesta);
        }
    }

    private void cacularNotasDuales(DefaultTableModel tablaNotasDuales) {
        try {

            String nombreNota = "";

            int fila = getSelectedRowDuales();
            int columna = getSelectedColumDuales();

            switch (columna) {
                case 6:
                    nombreNota = "G. DE AULA 1";
                    String Gaula1 = tablaNotasDuales.getValueAt(getSelectedRowDuales(), getSelectedColumDuales()).toString();
                    guardarDBDuales(Gaula1, nombreNota, columna);
                    break;
                case 7:
                    nombreNota = "G. DE AULA 2";
                    String Gaula2 = tablaNotasDuales.getValueAt(getSelectedRowDuales(), getSelectedColumDuales()).toString();
                    guardarDBDuales(Gaula2, nombreNota, columna);

                    break;
                case 9:
                    nombreNota = "EXAMEN FINAL";
                    String examenFinal = tablaNotasDuales.getValueAt(getSelectedRowDuales(), getSelectedColumDuales()).toString();
                    guardarDBDuales(examenFinal, nombreNota, columna);

                    break;
                case 10:
                    nombreNota = "EXAMEN DE RECUPERACION";
                    String examenRecuperacion = tablaNotasDuales.getValueAt(getSelectedRowDuales(), getSelectedColumDuales()).toString();
                    guardarDBDuales(examenRecuperacion, nombreNota, columna);

                    break;

                case 13://FALTAS
                    editarFaltas(fila, columna, tablaNotasDuales, 12, 14, 15);
                    break;

                case 15:
                    editarAsistencia(fila, columna, tablaNotasDuales, 12, agregarFilasDuales());
                    break;
                default:
                    break;
            }
        } catch (NumberFormatException e) {
        }
    }

    private void guardarDBDuales(String nota, String tipoNota, int columna) {
        int fila = getSelectedRowDuales();
        if (Validaciones.isDecimal(nota)) {

            sumarColumnas(tablaNotasDuales, fila, 6, 7, 8, "DOUBLE");
            double examenRecuperacion = Double.valueOf(tablaNotasDuales.getValueAt(fila, 10).toString());
            if (examenRecuperacion != 0) {
                sumarColumnas(tablaNotasDuales, fila, 8, 10, 11, "INT");
            } else {
                sumarColumnas(tablaNotasDuales, fila, 8, 9, 11, "INT");
            }
            editarDuales(fila, columna, tipoNota);
            refreshTabla(agregarFilasDuales(), tablaNotasDuales);
        } else {
            mensajeDeError();
            refreshTabla(agregarFilasDuales(), tablaNotasDuales);
        }
    }

    private void editarDuales(int fila, int columna, String tipoNota) {
        AlumnoCursoBD alumno = listaNotas.get(fila);

        alumno.getNotas()
                .stream()
                .filter(buscar(tipoNota))
                .collect(Collectors.toList())
                .forEach(editarNota(fila, columna, tablaNotasDuales));
        alumno.getNotas()
                .stream()
                .filter(buscar("TOTAL GESTION"))
                .collect(Collectors.toList())
                .forEach(editarNota(fila, 8, tablaNotasDuales));

        alumno.setNotaFinal(Middlewares.conversor(tablaNotasDuales.getValueAt(fila, 11).toString()));
        alumno.setEstado(tablaNotasDuales.getValueAt(fila, 12).toString());
        alumno.editar();

    }

    private void editarFaltas(int fila, int columna, int faltas, TableModel tabla) {
        AlumnoCursoBD alumno = listaNotas.get(fila);
        tabla.setValueAt(faltas, fila, columna);
        alumno.setNumFalta(faltas);
        alumno.editar();

    }

    private void editarAsistencia(int fila, int columna, DefaultTableModel tabla, int colRespuesta, Function<AlumnoCursoBD, Void> funcion) {
        String asistencia = tabla.getValueAt(fila, columna).toString().toLowerCase();

        List<String> palabrasValidas = new ArrayList();
        if (asistencia.isEmpty()) {
            asistencia = "";
        }
        palabrasValidas.add("RETIRADO");
        palabrasValidas.add("ASISTE");
        palabrasValidas.add("DESERTOR");
        palabrasValidas.add("NO ASISTE");
        String estado = tabla.getValueAt(fila, colRespuesta).toString();

        if (Validaciones.validarPalabras(palabrasValidas, asistencia)) {
            if (asistencia.contains("retirado")) {
                tabla.setValueAt("RETIRADO", fila, colRespuesta);
                estado = "RETIRADO";
            } else if (asistencia.contains("desertor") || asistencia.contains("no asiste")) {
                tabla.setValueAt("REPROBADO", fila, colRespuesta);
                estado = "REPROBADO";
            }

            AlumnoCursoBD alumno = listaNotas.get(fila);
            alumno.setEstado(estado);
            alumno.setAsistencia(asistencia.toUpperCase());
            alumno.editar();
        } else {

            refreshTabla(funcion, tabla);

        }
    }

    private Function<AlumnoCursoBD, Void> agregarFilasDuales() {
        return obj -> {
            Vector<Object> row = new Vector<>();

            row.add(0, tablaNotasDuales.getDataVector().size() + 1);
            row.add(1, obj.getAlumno().getIdentificacion());
            row.add(2, obj.getAlumno().getPrimerApellido());
            row.add(3, obj.getAlumno().getSegundoApellido());
            row.add(4, obj.getAlumno().getPrimerNombre());
            row.add(5, obj.getAlumno().getSegundoNombre());

            row.add(6, obj.getNotas().stream().filter(buscar("G. DE AULA 1")).findAny().get().getNotaValor());
            row.add(7, obj.getNotas().stream().filter(buscar("G. DE AULA 2")).findAny().get().getNotaValor());
            row.add(8, obj.getNotas().stream().filter(buscar("TOTAL GESTION")).findAny().get().getNotaValor());
            row.add(9, obj.getNotas().stream().filter(buscar("EXAMEN FINAL")).findAny().get().getNotaValor());
            row.add(10, obj.getNotas().stream().filter(buscar("EXAMEN DE RECUPERACION")).findAny().get().getNotaValor());

            row.add(11, obj.getNotaFinal());

            row.add(12, obj.getEstado());
            row.add(13, obj.getNumFalta());
            row.add(14, 0);
            row.add(15, obj.getAsistencia());

            tablaNotasDuales.addRow(row);

            return null;
        };
    }

    // </editor-fold>  
    // <editor-fold defaultstate="collapsed" desc="EVENTOS"> 
    private void btnVerNotas(ActionEvent e) {
        if (cargarTabla) {
            String modalidad = listaPeriodos
                    .stream()
                    .filter(item -> item.getId_PerioLectivo() == getIdPeriodoLectivo())
                    .map(c -> c.getCarrera().getModalidad())
                    .findAny()
                    .orElse("CARRERA SIN MODALIDAD");

            if (modalidad.equalsIgnoreCase("TRADICIONAL")) {
                System.out.println("TRADICIONAL");
                vista.getTabPane().setSelectedIndex(0);
                cargarTabla(agregarFilasTradicionales(), tablaNotasTrad, vista.getTblNotas(), 13);
            } else if (modalidad.equalsIgnoreCase("DUAL") || modalidad.equalsIgnoreCase("DUAL FOCALIZADA")) {
                vista.getTabPane().setSelectedIndex(1);
                cargarTabla(agregarFilasDuales(), tablaNotasDuales, vista.getTblNotasDuales(), 12);
            }
        } else {
            JOptionPane.showMessageDialog(vista, "YA HAY UNA CARGA PENDIENTE!");
        }

    }

    private void btnImprimir(ActionEvent e) {
        new Thread(() -> {

            int r = JOptionPane.showOptionDialog(vista,
                    "Reporte de Notas por Curso\n"
                    + "¿Elegir el tipo de Reporte?", "REPORTE NOTAS",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{"Alumnos con menos de 70", "Alumnos entre 70 a 80",
                        "Alumnos entre 80 a 90", "Alumnos entre 90 a 100", "Reporte Completo"}, "Cancelar");

            Effects.setLoadCursor(vista);

            ReportesCTR reportes = new ReportesCTR(vista, getIdDocente());

            switch (r) {
                case 0:

                    desktop.getLblEstado().setText("CARGANDO REPORTE....");
                    reportes.generarReporteMenos70();
                    desktop.getLblEstado().setText("COMPLETADO");

                    break;

                case 1:

                    desktop.getLblEstado().setText("CARGANDO REPORTE....");
                    reportes.generarReporteEntre70_80();
                    desktop.getLblEstado().setText("COMPLETADO");

                    break;

                case 2:

                    desktop.getLblEstado().setText("CARGANDO REPORTE....");
                    reportes.generarReporteEntre80_90();
                    desktop.getLblEstado().setText("COMPLETADO");

                    break;

                case 3:

                    desktop.getLblEstado().setText("CARGANDO REPORTE....");
                    reportes.generarReporteEntre90_100();
                    desktop.getLblEstado().setText("COMPLETADO");

                    break;

                case 4:
                    desktop.getLblEstado().setText("CARGANDO REPORTE....");
                    reportes.generarReporteCompleto();
                    desktop.getLblEstado().setText("COMPLETADO");
                    break;

                default:
                    break;
            }

            try {
                sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(VtnNotasCTR.class.getName()).log(Level.SEVERE, null, ex);
            }
            desktop.getLblEstado().setText("");
            Effects.setDefaultCursor(vista);
            vista.getBtnVerNotas().setEnabled(true);
        }).start();

    }

    private void buscarDocente() {
        activarForm(false);
        vista.getCmbDocente().setSelectedItem(listaDocentes
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().getIdentificacion().equals(vista.getTxtBuscar().getText()))
                .map(c -> c.getKey())
                .findFirst()
                .orElse("")
        );
        activarForm(true);
    }
    // </editor-fold>  

}
