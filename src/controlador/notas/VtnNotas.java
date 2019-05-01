package controlador.notas;

import controlador.Libraries.Effects;
import controlador.Libraries.Middlewares;
import controlador.Libraries.Validaciones;
import controlador.notas.ux.RowStyle;
import java.awt.event.ActionEvent;
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
public class VtnNotas {

    private VtnPrincipal desktop;
    private static VtnNotasAlumnoCurso vista;
    private UsuarioBD usuario;
    private static RolBD rolSeleccionado;

    //LISTAS
    private static Map<String, DocenteMD> listaDocentes;
    private static List<PeriodoLectivoMD> listaPeriodos;
    private static List<AlumnoCursoBD> listaNotas;
    private static List<MateriaMD> listaMaterias;
    private static List<TipoDeNotaMD> listaValidaciones;

    //TABLA
    private static DefaultTableModel tablaNotasTradicionales;
    private static DefaultTableModel tablaNotasDuales;

    //VARIABLES DE BUSQUEDA
    protected static int idDocente = -1;
    protected static int idPeriodoLectivo = -1;
    protected static int idCurso = -1;

    //ACTIVACION DE HILOS
    private boolean cargarTabla = true;

    public VtnNotas(VtnPrincipal desktop, VtnNotasAlumnoCurso vista, UsuarioBD usuario, RolBD rolSeleccionado) {
        vista.setValorMinimo(70);
        this.desktop = desktop;
        this.vista = vista;
        this.usuario = usuario;
        this.rolSeleccionado = rolSeleccionado;
    }

    // <editor-fold defaultstate="collapsed" desc="INITS">    
    public void Init() {

        vista.canEdit[1] = true;

        tablaNotasTradicionales = (DefaultTableModel) vista.getTblNotas().getModel();
        tablaNotasDuales = (DefaultTableModel) vista.getTblNotasDuales().getModel();
        if (rolSeleccionado.getNombre().toLowerCase().contains("docente")) {
            listaDocentes = DocenteBD.selectAll(usuario.getUsername());
        } else {
            listaDocentes = DocenteBD.selectAll();
        }

        new Thread(() -> {
            activarForm(false);
            cargarComboDocente();
            cargarComboPeriodos();
            setLblCarrera();
            cargarComboCiclo();
            cargarComboMaterias();
            InitEventos();

            activarForm(true);
        }).start();

        Effects.addInDesktopPane(vista, desktop.getDpnlPrincipal());

    }

    private void InitEventos() {

        vista.getCmbDocente().addActionListener(e -> cargarComboPeriodos());
        vista.getCmbPeriodoLectivo().addActionListener(e -> {
            cargarComboCiclo();
        });
        vista.getCmbPeriodoLectivo().addItemListener(e -> setLblCarrera());

        vista.getCmbCiclo().addActionListener(e -> {
            cargarComboMaterias();
        });

        //vista.getBtnImprimir().addActionListener(e -> btnImprimir(e));
        vista.getBtnVerNotas().addActionListener(e -> btnVerNotas(e));

        vista.getBtnImprimir().addActionListener(e -> btnImprimir(e));

        vista.getBtnBuscar().addActionListener(e -> btnBuscar(e));

        vista.getTxtBuscar().addKeyListener(Validaciones.validarNumeros());

        vista.getBtnBuscar().addActionListener(e -> btnBuscar(e));

        tablaNotasTradicionales.addTableModelListener(new TableModelListener() {

            boolean active = false;

            @Override
            public void tableChanged(TableModelEvent e) {
                if (!active && e.getType() == TableModelEvent.UPDATE) {

                    active = true;

                    carlcularNotas(tablaNotasTradicionales);

                    active = false;
                }

            }

        });
    }

    // </editor-fold>  
    // <editor-fold defaultstate="collapsed" desc="METODOS DE APOYO">    
    /*
        METODOS DE CARGA
     */
    private static int getIdPeriodoLectivo() {
        try {
            String periodo = vista.getCmbPeriodoLectivo().getSelectedItem().toString();

            listaPeriodos
                    .stream()
                    .filter(item -> item.getNombre_PerLectivo().equals(periodo))
                    .collect(Collectors.toList())
                    .forEach(obj -> {
                        idPeriodoLectivo = obj.getId_PerioLectivo();
                    });

        } catch (NullPointerException e) {
        }
        return idPeriodoLectivo;
    }

    private static int getSelectedRow() {
        return vista.getTblNotas().getSelectedRow();
    }

    private static int getSelectedColum() {
        return vista.getTblNotas().getSelectedColumn();
    }

    private static void activarForm(boolean estado) {

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

    private void carlcularNotas(TableModel datos) {

        try {
            String nombreNota = "";
            switch (getSelectedColum()) {
                case 6:
                    nombreNota = "APORTE 1";

                    String aporte1 = datos.getValueAt(getSelectedRow(), getSelectedColum()).toString();

                    guardarBD(aporte1, nombreNota, datos);

                    break;
                case 7:
                    nombreNota = "EXAMEN INTERCICLO";

                    String examenInterCiclo = datos.getValueAt(getSelectedRow(), getSelectedColum()).toString();

                    guardarBD(examenInterCiclo, nombreNota, datos);
                    break;
                case 9:
                    nombreNota = "APORTE 2";

                    String aporte2 = datos.getValueAt(getSelectedRow(), getSelectedColum()).toString();

                    guardarBD(aporte2, nombreNota, datos);
                    break;
                case 10:
                    nombreNota = "EXAMEN FINAL";

                    String examenFinal = datos.getValueAt(getSelectedRow(), getSelectedColum()).toString();

                    guardarBD(examenFinal, nombreNota, datos);
                    break;
                case 11:
                    nombreNota = "EXAMEN SUPLETORIO";

                    String examenSupletorio = datos.getValueAt(getSelectedRow(), getSelectedColum()).toString();

                    guardarBD(examenSupletorio, nombreNota, datos);
                    break;

                case 14:
                    String materia = vista.getCmbAsignatura().getSelectedItem().toString();
                    String value = tablaNotasTradicionales.getValueAt(getSelectedRow(), 14).toString();

                    if (value.isEmpty()) {
                        value = "0";
                    }

                    if (Validaciones.isInt(value)) {
                        int faltas = (int) Middlewares.conversor(value);
                        setFaltas(materia, faltas);
                        editar();
                    } else {
                        JOptionPane.showMessageDialog(vista, "INGRESE SOLO NUMEROS ENTEROS!!");
                    }

                    refreshTabla(agregarFilasTradicionales());
                    break;
                case 16:
                    String asistencia = vista.getTblNotas().getValueAt(getSelectedRow(), 16).toString().toLowerCase();

                    List<String> palabrasValidas = new ArrayList();
                    if (asistencia.isEmpty()) {
                        asistencia = "";
                    }
                    palabrasValidas.add("RETIRADO");
                    palabrasValidas.add("ASISTE");
                    palabrasValidas.add("DESERTOR");
                    palabrasValidas.add("NO ASISTE");

                    if (Validaciones.validarPalabras(palabrasValidas, asistencia)) {
                        if (asistencia.contains("retirado")) {
                            vista.getTblNotas().setValueAt("RETIRADO", getSelectedRow(), 13);
                        } else if (asistencia.contains("desertor") || asistencia.contains("no asiste")) {
                            vista.getTblNotas().setValueAt("REPROBADO", getSelectedRow(), 13);
                        }
                        sumarColumnasTradicionales();
                        editar();
                    }
                    refreshTabla(agregarFilasTradicionales());

                    break;
                default:
                    break;
            }

        } catch (NumberFormatException e) {

            System.out.println(e.getMessage());
        }
    }

    private static void setFaltas(String materia, int faltas) {
        listaMaterias
                .stream()
                .filter(item -> item.getNombre().equals(materia))
                .collect(Collectors.toList())
                .forEach(setPorcentaje(faltas));

    }

    private static void guardarBD(String nota, String nombreNota, TableModel datos) {
        if (Validaciones.isDecimal(nota)) {
            double value = Middlewares.conversor(nota);

            TipoDeNotaMD rango = getRango(nombreNota);
            if (!rango.getNombre().equalsIgnoreCase("EXAMEN FINAL")) {
                if (value >= 0 && value <= rango.getValorMaximo()) {
                    datos.setValueAt(Middlewares.conversor(nota), getSelectedRow(), getSelectedColum());
                    sumarColumnasTradicionales();
                    editar();
                    refreshTabla(agregarFilasTradicionales());

                } else {
                    errorDeNota(rango);
                }
            } else {
                if (value >= 0 && value <= rango.getValorMaximo()) {

                    if (value >= rango.getValorMinimo() && value <= rango.getValorMaximo()) {
                        datos.setValueAt(Middlewares.conversor(nota), getSelectedRow(), getSelectedColum());
                        sumarColumnasTradicionales();
                        editar();
                        refreshTabla(agregarFilasTradicionales());
                    } else {
                        datos.setValueAt(Middlewares.conversor(nota), getSelectedRow(), getSelectedColum());
                        datos.setValueAt("REPROBADO", getSelectedRow(), 13);
                        sumarColumnasTradicionales();
                        editar();
                        refreshTabla(agregarFilasTradicionales());
                    }
                } else {
                    errorDeNota(rango);
                }
            }
        } else {
            mensajeDeError();

        }
    }

    private static void errorDeNota(TipoDeNotaMD rango) {
        JOptionPane.showMessageDialog(vista, "EL RANGO DE LA NOTA DEBE ESTAR ENTRE: " + 0 + " Y " + rango.getValorMaximo());
        refreshTabla(agregarFilasTradicionales());
    }

    private static void sumarColumnasTradicionales() {
        int fila = getSelectedRow();

        double aporte1 = 0;
        double examenInterCiclo = 0;
        double totalInterciclo = 0;

        double aporte2 = 0;
        double examenFinal = 0;
        double examenSupletorio = 0;

        double notaFinal = 0;

        aporte1 = Middlewares.conversor(tablaNotasTradicionales.getValueAt(fila, 6).toString());
        examenInterCiclo = Middlewares.conversor(tablaNotasTradicionales.getValueAt(fila, 7).toString());
        totalInterciclo = aporte1 + examenInterCiclo;
        tablaNotasTradicionales.setValueAt(totalInterciclo, fila, 8);

        aporte2 = Middlewares.conversor(tablaNotasTradicionales.getValueAt(fila, 9).toString());
        examenFinal = Middlewares.conversor(tablaNotasTradicionales.getValueAt(fila, 10).toString());
        examenSupletorio = Middlewares.conversor(tablaNotasTradicionales.getValueAt(fila, 11).toString());

        if (examenSupletorio != 0) {
            notaFinal = totalInterciclo + aporte2 + examenSupletorio;
        } else {
            notaFinal = totalInterciclo + aporte2 + examenFinal;
        }

        tablaNotasTradicionales.setValueAt(Math.round(notaFinal), fila, 12);

    }

    private static Consumer<MateriaMD> setPorcentaje(int faltas) {
        return obj -> {

            int horasMateria = obj.getHorasPresenciales();
            int porcentaje = 1;
            if (horasMateria != 0) {
                porcentaje = (faltas * obj.getHorasPresenciales()) / 100;
            }
            vista.getTblNotas().setValueAt(porcentaje, getSelectedRow(), 15);
            String estado = vista.getTblNotas().getValueAt(getSelectedRow(), 13).toString();
            String asistencia = vista.getTblNotas().getValueAt(getSelectedRow(), 16).toString();
            if (!estado.equalsIgnoreCase("RETIRADO") && asistencia.equalsIgnoreCase("RETIRADO")) {
                if (porcentaje >= 25) {
                    vista.getTblNotas().setValueAt("REPROBADO", getSelectedRow(), 13);
                } else {
                    vista.getTblNotas().setValueAt("APROBADO", getSelectedRow(), 13);
                }
            }
        };
    }

    private static void mensajeDeError() {
        JOptionPane.showMessageDialog(vista, "INGRESE UN NUMERO CORRECTO "
                + "\n       EJEMPLO (15.6)");
        refreshTabla(agregarFilasTradicionales());
    }

    private void cargarComboDocente() {
        listaDocentes.entrySet().forEach((entry) -> {
            String key = entry.getKey();
            DocenteMD value = entry.getValue();

            vista.getCmbDocente().addItem(key);

        });
        tablaNotasTradicionales.setRowCount(0);
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
        tablaNotasTradicionales.setRowCount(0);
    }

    private static void setLblCarrera() {
        listaPeriodos
                .stream()
                .filter(item -> item.getId_PerioLectivo() == getIdPeriodoLectivo())
                .collect(Collectors.toList())
                .forEach(obj -> {
                    vista.getLblCarrera().setText(obj.getCarrera().getNombre());
                });

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
        tablaNotasTradicionales.setRowCount(0);
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
        tablaNotasTradicionales.setRowCount(0);
    }

    private static void refreshTabla(Function<AlumnoCursoBD, Void> funcion) {
        System.out.println("REFRESH");
        activarForm(false);
        tablaNotasTradicionales.setRowCount(0);
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

    /*
        VARIOS
     */
    private static void editar() {
        vista.getTblNotas().setEnabled(false);
        int fila = getSelectedRow();

        AlumnoCursoBD alumno = listaNotas.get(fila);

        alumno.setEstado(vista.getTblNotas().getValueAt(fila, 13).toString());
        alumno.setNumFalta(Integer.valueOf(vista.getTblNotas().getValueAt(fila, 14).toString().toUpperCase()));
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
                .forEach(editarNota(6));
        alumno.getNotas().stream()
                .filter(buscar("EXAMEN INTERCICLO"))
                .collect(Collectors.toList())
                .forEach(editarNota(7));
        alumno.getNotas().stream()
                .filter(buscar("NOTA INTERCICLO"))
                .collect(Collectors.toList())
                .forEach(editarNota(8));
        alumno.getNotas().stream()
                .filter(buscar("APORTE 2"))
                .collect(Collectors.toList())
                .forEach(editarNota(9));
        alumno.getNotas().stream()
                .filter(buscar("EXAMEN FINAL"))
                .collect(Collectors.toList())
                .forEach(editarNota(10));
        alumno.getNotas().stream()
                .filter(buscar("EXAMEN SUPLETORIO"))
                .collect(Collectors.toList())
                .forEach(editarNota(11));

        alumno.setNotaFinal(Middlewares.conversor(vista.getTblNotas().getValueAt(fila, 12).toString()));

        alumno.editar();
        vista.getTblNotas().setEnabled(true);
    }

    private static TipoDeNotaMD getRango(String nombreNota) {

        List<TipoDeNotaMD> listaTemporal = listaValidaciones
                .stream()
                .filter(item -> item.getNombre().equals(nombreNota))
                .collect(Collectors.toList());

        return listaTemporal.get(0);

    }

    private static Predicate<NotasBD> buscar(String busqueda) {
        return item -> item.getTipoDeNota().getNombre().equals(busqueda);
    }

    private static Consumer<NotasBD> editarNota(int columna) {
        return obj -> {
            String text = vista.getTblNotas().getValueAt(getSelectedRow(), columna).toString();
            obj.setNotaValor(Middlewares.conversor(text));
            obj.editar();
        };
    }

    private static int getIdDocente() {
        return listaDocentes
                .entrySet()
                .stream()
                .filter((entry) -> (entry.getKey().equals(vista.getCmbDocente().getSelectedItem().toString())))
                .findAny()
                .get()
                .getValue()
                .getIdDocente();

    }

    private void cargarTabla(Function<AlumnoCursoBD, Void> funcion, DefaultTableModel tabla, JTable Jtable, int fila) {
        new Thread(() -> {

            if (cargarTabla) {
                RowStyle row = new RowStyle(fila);

                Jtable.setDefaultRenderer(Object.class, row);

                cargarTabla = false;
                tabla.setRowCount(0);
                vista.getTblNotas().setEnabled(false);
                Effects.setLoadCursor(vista);

                String cursoNombre = vista.getCmbCiclo().getSelectedItem().toString();
                String nombreMateria = vista.getCmbAsignatura().getSelectedItem().toString();
                String nombrePeriodo = vista.getCmbPeriodoLectivo().getSelectedItem().toString();

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
            }

        }).start();

    }

    private static Consumer<MateriaMD> setPorcentajeVetor(Vector<Object> row, double faltas, AlumnoCursoBD alumno) {
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

                double examenFinal = (double) row.get(10);

                validarAprobado(alumno, examenFinal, porcentaje, row);
            }

            row.add(14, (int) faltas);

            row.add(15, porcentaje);
        };
    }

    private static void validarAprobado(AlumnoCursoBD alumno, double notaValidar, int porcentaje, List<Object> row) {
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

    // </editor-fold>  
    // <editor-fold defaultstate="collapsed" desc="CARRERAS DUALES">
    private static Function<AlumnoCursoBD, Void> agregarFilasTradicionales() {

        return (obj) -> {
            Vector<Object> row = new Vector<>();

            row.add(0, tablaNotasTradicionales.getDataVector().size() + 1);
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

            tablaNotasTradicionales.addRow(row);
            return null;
        };
    }

    private static Function<AlumnoCursoBD, Void> agregarFilasDuales() {
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
            row.add(11, obj.getNotas().stream().filter(buscar("NOTA FINAL CICLO")).findAny().get().getNotaValor());
            row.add(12, obj.getNotas().stream().filter(buscar("D. G. I. F.")).findAny().get().getNotaValor());
            row.add(13, obj.getNotas().stream().filter(buscar("C. T. E.")).findAny().get().getNotaValor());
            row.add(14, obj.getNotas().stream().filter(buscar("C. T. A.")).findAny().get().getNotaValor());
            row.add(15, obj.getNotas().stream().filter(buscar("S. T. F. P.")).findAny().get().getNotaValor());
            //row.add(11, obj.getNotas().stream().filter(buscar("NOTA FINAL")).findAny().get().getNotaValor());

            row.add(16, obj.getNotaFinal());
            row.add(17, obj.getEstado());
            row.add(18, obj.getNumFalta());

            row.add(19, obj.getAsistencia());

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
                    .findAny()
                    .get().getCarrera().getModalidad();

            if (modalidad.equalsIgnoreCase("TRADICIONAL")) {
                System.out.println("TRADICIONAL");
                vista.getTabPane().setSelectedIndex(0);
                cargarTabla(agregarFilasTradicionales(), tablaNotasTradicionales, vista.getTblNotas(), 13);
            } else if (modalidad.equalsIgnoreCase("DUAL")) {
                vista.getTabPane().setSelectedIndex(1);

                System.out.println("DUAL");

                cargarTabla(agregarFilasDuales(), tablaNotasDuales, vista.getTblNotasDuales(), 17);
            } else if (modalidad.equalsIgnoreCase("DUAL FOCALIZADA")) {
                vista.getTabPane().setSelectedIndex(1);

                System.out.println("DUAL FOCALIZADA");

                cargarTabla(agregarFilasDuales(), tablaNotasDuales, vista.getTblNotasDuales(), 17);

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

            ReportesCTR reportes = new ReportesCTR(vista, idDocente);

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
                sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(VtnNotas.class.getName()).log(Level.SEVERE, null, ex);
            }
            desktop.getLblEstado().setText("");
            Effects.setDefaultCursor(vista);
            vista.getBtnVerNotas().setEnabled(true);
        }).start();

    }

    private void btnBuscar(ActionEvent e) {
        activarForm(false);

        vista.getCmbDocente()
                .setSelectedItem(listaDocentes
                        .entrySet()
                        .stream()
                        .filter(entry -> entry.getValue().getIdentificacion().equals(vista.getTxtBuscar().getText()))
                        .findFirst()
                        .get()
                        .getKey()
                );

        activarForm(true);
    }
    // </editor-fold>  

}
