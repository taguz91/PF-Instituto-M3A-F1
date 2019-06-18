package controlador.notas;

import controlador.Libraries.Effects;
import controlador.Libraries.Middlewares;
import controlador.Libraries.Validaciones;
import controlador.Libraries.cellEditor.ComboBoxCellEditor;
import controlador.Libraries.cellEditor.TextFieldCellEditor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import modelo.CONS;
import modelo.alumno.AlumnoCursoBD;
import modelo.notas.NotasBD;
import modelo.tipoDeNota.TipoDeNotaMD;
import vista.notas.VtnNotas;
import vista.principal.VtnPrincipal;

/**
 *
 * @author MrRainx
 */
public class VtnNotasCTR extends AbstractVtn {

    public VtnNotasCTR(VtnPrincipal desktop, VtnNotas vista) {
        super(desktop, vista);
    }

    // <editor-fold defaultstate="collapsed" desc="INITS">
    public void Init() {
        tablaTrad = (DefaultTableModel) vista.getTblTrad().getModel();
        tablaDuales = (DefaultTableModel) vista.getTblDual().getModel();

        jTblTrad = vista.getTblTrad();
        jTblDual = vista.getTblDual();

        if (CONS.ROL.getNombre().toLowerCase().contains("docente")) {
            listaDocentes = docenteBD.selectAll(CONS.USUARIO.getUsername());
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

        tablaTrad.addTableModelListener(new TableModelListener() {

            boolean active = false;

            @Override
            public void tableChanged(TableModelEvent e) {
                if (!active && e.getType() == TableModelEvent.UPDATE) {

                    active = true;
                    carlcularNotasTradicionales(jTblTrad);
                    active = false;
                }

            }
        });

        tablaDuales.addTableModelListener(new TableModelListener() {
            boolean active = false;

            @Override
            public void tableChanged(TableModelEvent e) {

                if (!active && e.getType() == TableModelEvent.UPDATE) {
                    active = true;
                    cacularNotasDuales(jTblDual);
                    active = false;
                }

            }
        });
        vista.getTxtBuscar().addKeyListener(Validaciones.validarNumeros());
        InitPermisos();
    }

    private void InitTablas() {
        List<String> items = new ArrayList<>();
        items.add("Asiste");
        items.add("No asiste");
        items.add("Retirado");
        items.add("Desertor");
        if (getModalidad().equalsIgnoreCase("tradicional") || getModalidad().equalsIgnoreCase("presencial")) {
            if (!getEstado()) {
                // TABLA TRADICIONALES
                jTblTrad.getColumnModel().getColumn(6).setCellEditor(new TextFieldCellEditor(false));
                jTblTrad.getColumnModel().getColumn(7).setCellEditor(new TextFieldCellEditor(false));
                jTblTrad.getColumnModel().getColumn(9).setCellEditor(new TextFieldCellEditor(false));
                jTblTrad.getColumnModel().getColumn(10).setCellEditor(new TextFieldCellEditor(false));
                jTblTrad.getColumnModel().getColumn(11).setCellEditor(new TextFieldCellEditor(false));
                jTblTrad.getColumnModel().getColumn(14).setCellEditor(new TextFieldCellEditor(false));
                jTblTrad.getColumnModel().getColumn(16).setCellEditor(new ComboBoxCellEditor(false, items));

            } else {
                jTblTrad.getColumnModel().getColumn(6).setCellEditor(new TextFieldCellEditor(true));
                jTblTrad.getColumnModel().getColumn(7).setCellEditor(new TextFieldCellEditor(false));
                jTblTrad.getColumnModel().getColumn(9).setCellEditor(new TextFieldCellEditor(false));
                jTblTrad.getColumnModel().getColumn(10).setCellEditor(new TextFieldCellEditor(false));
                jTblTrad.getColumnModel().getColumn(11).setCellEditor(new TextFieldCellEditor(false));
                jTblTrad.getColumnModel().getColumn(14).setCellEditor(new TextFieldCellEditor(false));
                jTblTrad.getColumnModel().getColumn(16).setCellEditor(new ComboBoxCellEditor(false, items));
            }

        } else {
            if (!getEstado()) {
                jTblDual.getColumnModel().getColumn(6).setCellEditor(new TextFieldCellEditor(false));
                jTblDual.getColumnModel().getColumn(7).setCellEditor(new TextFieldCellEditor(false));
                jTblDual.getColumnModel().getColumn(9).setCellEditor(new TextFieldCellEditor(false));
                jTblDual.getColumnModel().getColumn(10).setCellEditor(new TextFieldCellEditor(false));
                jTblDual.getColumnModel().getColumn(13).setCellEditor(new TextFieldCellEditor(false));
                jTblDual.getColumnModel().getColumn(15).setCellEditor(new ComboBoxCellEditor(false, items));
            } else {
                jTblDual.getColumnModel().getColumn(6).setCellEditor(new TextFieldCellEditor(true));
                jTblDual.getColumnModel().getColumn(7).setCellEditor(new TextFieldCellEditor(false));
                jTblDual.getColumnModel().getColumn(9).setCellEditor(new TextFieldCellEditor(false));
                jTblDual.getColumnModel().getColumn(10).setCellEditor(new TextFieldCellEditor(false));
                jTblDual.getColumnModel().getColumn(13).setCellEditor(new TextFieldCellEditor(false));
                jTblDual.getColumnModel().getColumn(15).setCellEditor(new ComboBoxCellEditor(false, items));
            }
        }

    }

    private void InitPermisos() {
        vista.getBtnImprimir().getAccessibleContext().setAccessibleName("Notas-Consultar-Notas-Imprimir");
        vista.getBtnVerNotas().getAccessibleContext().setAccessibleName("Notas-Consultar-Notas-Ver Notas");

        CONS.activarBtns(vista.getBtnImprimir(), vista.getBtnVerNotas());
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="VARIOS">
    private void mensajeDeError() {
        Effects.setTextInLabel(vista.getLblEstado(), "INGRESE UN NUMERO CORRECTO EJEMPLO (15.6)", Effects.ERROR_COLOR,
                2);

        Effects.ERROR_COLOR = null;

    }

    private void errorDeNota(TipoDeNotaMD rango) {
        Effects.setTextInLabel(vista.getLblEstado(),
                "EL RANGO DE LA NOTA DEBE ESTAR ENTRE: " + 0 + " Y " + rango.getValorMaximo(), Effects.ERROR_COLOR, 2);
    }

    private void refreshTabla(Consumer<AlumnoCursoBD> loader, DefaultTableModel tabla) {
        activarForm(false);
        tabla.setRowCount(0);
        listaNotas.stream().forEach(loader);
        activarForm(true);
    }

    private int getSelectedRowTrad() {
        return vista.getTblTrad().getSelectedRow();
    }

    private int getSelectedColumTrad() {
        return vista.getTblTrad().getSelectedColumn();
    }

    private int getSelectedRowDuales() {
        return vista.getTblDual().getSelectedRow();
    }

    private int getSelectedColumDuales() {
        return vista.getTblDual().getSelectedColumn();
    }

    private final BiFunction<JTable, String, Integer> getIndex = (tabla, nombre) -> {
        return tabla.getColumnModel().getColumnIndex(nombre);
    };

    private String getModalidad() {
        return listaPeriodos.stream().filter(item -> item.getId_PerioLectivo() == getIdPeriodoLectivo())
                .map(c -> c.getCarrera().getModalidad()).findFirst().orElse("");
    }

    private int getHoras() {
        return listaMaterias.stream()
                .filter(item -> item.getNombre().equals(vista.getCmbAsignatura().getSelectedItem().toString()))
                .map(c -> c.getHorasPresenciales()).findFirst().orElse(1);
    }

    private TipoDeNotaMD getRango(String nombreNota) {
        return listaValidaciones.stream().filter(item -> item.getNombre().equals(nombreNota)).findFirst().get();
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

    private void activarForm(boolean estado) {

        if (CONS.ROL.getNombre().toLowerCase().contains("docente")) {
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
        vista.getTblTrad().setEnabled(estado);
    }

    private int calcularPorcentaje(int faltas, int horas) {
        if (horas == 0) {
            horas = 1;
        }
        return (faltas * 100) / horas;
    }

    private void editarFaltas(int fila, JTable tabla, Consumer<AlumnoCursoBD> loader, Function<String, Void> editar,
            Function<Void, Void> sumar) {

        int colFaltas = getIndex.apply(tabla, "Faltas");
        int colEstado = getIndex.apply(tabla, "Estado");
        int conPorcentaje = getIndex.apply(tabla, "% Faltas");
        int colAsistencia = getIndex.apply(tabla, "Asistencia");

        String faltasText = tabla.getValueAt(fila, colFaltas).toString();

        if (faltasText.isEmpty()) {
            faltasText = "2.2";
        }

        if (Validaciones.isInt(faltasText)) {
            int faltas;
            try {
                faltas = new Integer(faltasText);
                int oldFaltas = listaNotas.get(fila).getNumFalta();
                if (faltas != oldFaltas) {
                    if (faltas <= getHoras()) {
                        int horas = getHoras();
                        int porcentaje = 0;
                        if (horas <= 0) {
                            horas = 1;
                        }

                        porcentaje = (faltas * 100) / horas;

                        tabla.setValueAt(porcentaje, fila, conPorcentaje);

                        String estado = tabla.getValueAt(fila, colEstado).toString();
                        String asistencia = tabla.getValueAt(fila, colAsistencia).toString();

                        if (!estado.equalsIgnoreCase("RETIRADO") && !asistencia.equalsIgnoreCase("RETIRADO")) {
                            if (porcentaje >= 25) {
                                tabla.setValueAt("REPROBADO", fila, colEstado);
                            } else {
                                sumar.apply(null);
                            }
                        }

                        tabla.setValueAt(faltas, fila, colFaltas);
                        editar.apply("");
                    } else {
                        Effects.setTextInLabel(vista.getLblEstado(),
                                "LAS FALTAS NO PUEDEN SER MAYORES AL NUMERO DE HORAS", Effects.ERROR_COLOR, 2);
                        refreshTabla(loader, (DefaultTableModel) tabla.getModel());
                    }
                }
            } catch (NumberFormatException e) {
                Effects.setTextInLabel(vista.getLblEstado(), "INGRESE UN NUMERO VALIDO!!", Effects.ERROR_COLOR, 2);
                refreshTabla(loader, (DefaultTableModel) tabla.getModel());
            }
        } else {
            Effects.setTextInLabel(vista.getLblEstado(), "INGRESE SOLO NUMERO ENTEROS!!", Effects.ERROR_COLOR, 2);
            refreshTabla(loader, (DefaultTableModel) tabla.getModel());
        }
    }

    private void cargarTabla(Consumer<AlumnoCursoBD> loader) {
        new Thread(() -> {
            cargarTabla = false;
            String cursoNombre = vista.getCmbCiclo().getSelectedItem().toString();
            String nombreMateria = vista.getCmbAsignatura().getSelectedItem().toString();
            listaNotas = almnCursoBD.selectWhere(cursoNombre, nombreMateria, getIdDocente(), getIdPeriodoLectivo());
            InitTablas();
            listaNotas.stream().forEach(loader);
            cargarTabla = true;
            vista.getLblResultados().setText(listaNotas.size() + " Resultados");
        }).start();
    }

    private double getValorNota(int index, String tipoNota) {
        return listaNotas.get(index).getNotas().stream()
                .filter(item -> item.getTipoDeNota().getNombre().equals(tipoNota)).findFirst()
                .map(c -> c.getNotaValor()).orElse(listaNotas.get(index).getNotaFinal());
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="CARRERAS TRADICIONALES">
    private void carlcularNotasTradicionales(JTable tabla) {

        int fila = tabla.getSelectedRow();
        int columna = tabla.getSelectedColumn();
        String valueText;
        String tipoNota;
        switch (columna) {
            case 6:
                valueText = tabla.getValueAt(fila, columna).toString();
                tipoNota = "APORTE 1";
                guardarTRAD(fila, valueText, tipoNota);
                break;
            case 7:
                valueText = tabla.getValueAt(fila, columna).toString();
                tipoNota = "EXAMEN INTERCICLO";
                guardarTRAD(fila, valueText, tipoNota);
                break;
            case 9:
                valueText = tabla.getValueAt(fila, columna).toString();
                tipoNota = "APORTE 2";
                guardarTRAD(fila, valueText, tipoNota);
                break;
            case 10:
                valueText = tabla.getValueAt(fila, columna).toString();
                tipoNota = "EXAMEN FINAL";
                guardarTRAD(fila, valueText, tipoNota);
                break;
            case 11:
                valueText = tabla.getValueAt(fila, columna).toString();
                tipoNota = "EXAMEN DE RECUPERACION";
                guardarTRAD(fila, valueText, tipoNota);
                break;
            case 14:
                editarFaltas(fila, tabla, agregarFilasTrad(), editarTrad(), sumarTrad());
                break;
            case 16:
                String asistencia = tabla.getValueAt(fila, columna).toString().toLowerCase();
                int colEstado = getIndex.apply(tabla, "Estado");

                if (asistencia.equalsIgnoreCase("retirado")) {
                    tabla.setValueAt("RETIRADO", fila, colEstado);
                    editarTrad().apply(null);
                } else if (asistencia.equalsIgnoreCase("asiste")) {
                    tabla.setValueAt("REPROBADO", fila, colEstado);
                    sumarTrad().apply(null);
                    editarTrad().apply("");
                } else {
                    tabla.setValueAt("REPROBADO", fila, colEstado);
                    editarTrad().apply("");
                }
                break;
        }

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="AGREGAR FILAS">
    private Consumer<AlumnoCursoBD> agregarFilasTrad() {
        return (obj) -> {
            tablaTrad.addRow(new Object[]{tablaTrad.getDataVector().size() + 1, obj.getAlumno().getIdentificacion(),
                obj.getAlumno().getPrimerApellido(), obj.getAlumno().getSegundoApellido(),
                obj.getAlumno().getPrimerNombre(), obj.getAlumno().getSegundoNombre(),
                obj.getNotas().stream().filter(buscar("APORTE 1")).findAny().get().getNotaValor(),
                obj.getNotas().stream().filter(buscar("EXAMEN INTERCICLO")).findAny().get().getNotaValor(),
                obj.getNotas().stream().filter(buscar("NOTA INTERCICLO")).findAny().get().getNotaValor(),
                obj.getNotas().stream().filter(buscar("APORTE 2")).findAny().get().getNotaValor(),
                obj.getNotas().stream().filter(buscar("EXAMEN FINAL")).findAny().get().getNotaValor(),
                obj.getNotas().stream().filter(buscar("EXAMEN DE RECUPERACION")).findAny().get().getNotaValor(),
                (int) Middlewares.conversor("" + obj.getNotaFinal()), obj.getEstado(), obj.getNumFalta(),
                calcularPorcentaje(obj.getNumFalta(), getHoras()), obj.getAsistencia()});
        };
    }

    private Consumer<AlumnoCursoBD> agregarFilasDuales() {
        return (obj) -> {

            tablaDuales
                    .addRow(new Object[]{tablaDuales.getDataVector().size() + 1, obj.getAlumno().getIdentificacion(),
                obj.getAlumno().getPrimerApellido(), obj.getAlumno().getSegundoApellido(),
                obj.getAlumno().getPrimerNombre(), obj.getAlumno().getSegundoNombre(),
                obj.getNotas().stream().filter(buscar("G. DE AULA 1")).findAny().get().getNotaValor(),
                obj.getNotas().stream().filter(buscar("G. DE AULA 2")).findAny().get().getNotaValor(),
                obj.getNotas().stream().filter(buscar("TOTAL GESTION")).findAny().get().getNotaValor(),
                obj.getNotas().stream().filter(buscar("EXAMEN FINAL")).findAny().get().getNotaValor(),
                obj.getNotas().stream().filter(buscar("EXAMEN DE RECUPERACION")).findAny().get()
                .getNotaValor(),
                (int) Middlewares.conversor("" + obj.getNotaFinal()), obj.getEstado(), obj.getNumFalta(),
                carcularPorcentaje(obj.getNumFalta(), getHoras()), obj.getAsistencia()});
        };
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="SUMA DE COLUMNAS">
    private void guardarTRAD(int fila, String valueText, String tipoNota) {
        if (Validaciones.isDecimal(valueText)) {
            double value = Middlewares.conversor(valueText);
            if (value != getValorNota(fila, tipoNota)) {
                TipoDeNotaMD rango = getRango(tipoNota);
                if (value > rango.getValorMaximo()) {
                    errorDeNota(rango);
                    refreshTabla(agregarFilasTrad(), tablaTrad);
                } else {
                    sumarTrad().apply(null);
                    editarTrad().apply(tipoNota);
                    refreshTabla(agregarFilasTrad(), tablaTrad);
                }
            }

        } else {
            mensajeDeError();
            refreshTabla(agregarFilasTrad(), tablaTrad);
        }
    }

    private Function<Void, Void> sumarTrad() {
        return t -> {
            int fila = getSelectedRowTrad();

            double aporte1;
            double examenInterCiclo;
            double totalInterciclo;

            double aporte2;
            double examenFinal;
            double examenRecuperacion;
            double notaFinal;

            aporte1 = Middlewares.conversor(tablaTrad.getValueAt(fila, 6).toString());
            examenInterCiclo = Middlewares.conversor(tablaTrad.getValueAt(fila, 7).toString());
            totalInterciclo = aporte1 + examenInterCiclo;
            tablaTrad.setValueAt(totalInterciclo, fila, 8);
            aporte2 = Middlewares.conversor(tablaTrad.getValueAt(fila, 9).toString());
            examenFinal = Middlewares.conversor(tablaTrad.getValueAt(fila, 10).toString());
            examenRecuperacion = Middlewares.conversor(tablaTrad.getValueAt(fila, 11).toString());

            if (examenRecuperacion != 0) {
                notaFinal = totalInterciclo + aporte2 + examenRecuperacion;
            } else {
                notaFinal = totalInterciclo + aporte2 + examenFinal;
            }
            validarAprobado(examenFinal, examenRecuperacion, Math.round(notaFinal), jTblTrad);
            tablaTrad.setValueAt(Math.round(notaFinal), fila, 12);
            return null;
        };
    }

    private void validarAprobado(double examenFinal, double examenRecuperacion, double notaFinal, JTable tabla) {
        int fila = tabla.getSelectedRow();
        int colEstado = getIndex.apply(tabla, "Estado");
        int colFaltas = getIndex.apply(tabla, "% Faltas");
        TipoDeNotaMD rango = null;
        String estado = tabla.getValueAt(fila, colEstado).toString();
        int faltas = (int) Math.round(Middlewares.conversor(tabla.getValueAt(fila, colFaltas).toString()));

        if (!estado.equalsIgnoreCase("RETIRADO") && faltas < 25) {
            if (examenRecuperacion > 0) {
                rango = getRango("EXAMEN DE RECUPERACION");
                if (examenRecuperacion < rango.getValorMinimo()) {
                    tabla.setValueAt("REPROBADO", fila, colEstado);
                } else {
                    validarNotaFinal(notaFinal, tabla, fila, colEstado);
                }
            } else {
                rango = getRango("EXAMEN FINAL");
                if (examenFinal < rango.getValorMinimo()) {
                    tabla.setValueAt("REPROBADO", fila, colEstado);
                } else {
                    validarNotaFinal(notaFinal, tabla, fila, colEstado);
                }
            }
        }

    }

    private void validarNotaFinal(double notaFinal, JTable tabla, int fila, int colEstado) {

        if (notaFinal >= getRango("NOTA FINAL").getValorMinimo()) {
            tabla.setValueAt("APROBADO", fila, colEstado);
        } else {
            tabla.setValueAt("REPROBADO", fila, colEstado);
        }
    }

    private Function<String, Void> editarTrad() {

        return tipoNota -> {
            jTblTrad.setEnabled(false);
            int fila = getSelectedRowTrad();

            int columa = getSelectedColumTrad();
            String estado = vista.getTblTrad().getValueAt(fila, 13).toString();
            String asistencia = Middlewares.capitalize(vista.getTblTrad().getValueAt(fila, 16).toString());

            AlumnoCursoBD alumno = listaNotas.get(fila);
            if (tipoNota != null) {
                List<NotasBD> notas = alumno.getNotas();

                notas.stream().filter(buscar(tipoNota)).collect(Collectors.toList())
                        .forEach(editarNota(fila, columa, tablaTrad));

                notas.stream().filter(buscar("NOTA INTERCICLO")).collect(Collectors.toList())
                        .forEach(editarNota(fila, 8, tablaTrad));

                if (asistencia.equalsIgnoreCase("retirado")) {
                    estado = "RETIRADO";
                }

                alumno.setNotaFinal(Middlewares.conversor(tablaTrad.getValueAt(fila, 12).toString()));
            }
            alumno.setAsistencia(asistencia);
            alumno.setEstado(estado);
            alumno.setNumFalta(Integer.valueOf(tablaTrad.getValueAt(fila, 14).toString()));
            alumno.editar();
            jTblTrad.setEnabled(true);
            return null;
        };

    }

    private void guardarDUAL(int fila, String valueText, String tipoNota) {
        if (Validaciones.isDecimal(valueText)) {
            double value = Middlewares.conversor(valueText);
            if (value != getValorNota(fila, tipoNota)) {
                TipoDeNotaMD rango = getRango(tipoNota);
                if (value > rango.getValorMaximo()) {
                    errorDeNota(rango);
                    refreshTabla(agregarFilasDuales(), tablaDuales);
                } else {
                    sumarDual().apply(null);
                    editarDuales().apply(tipoNota);
                    refreshTabla(agregarFilasDuales(), tablaDuales);
                }
            }

        } else {
            mensajeDeError();
            refreshTabla(agregarFilasDuales(), tablaDuales);
        }
    }

    private Function<Void, Void> sumarDual() {
        return t -> {
            int fila = getSelectedRowDuales();

            double gestion1;
            double gestion2;
            double totalGestion;

            double examenFinal;
            double examenRecuperacion;
            double notaFinal;

            gestion1 = Middlewares.conversor(jTblDual.getValueAt(fila, 6).toString());
            gestion2 = Middlewares.conversor(jTblDual.getValueAt(fila, 7).toString());

            totalGestion = gestion1 + gestion2;
            jTblDual.setValueAt(totalGestion, fila, 8);
            examenFinal = Middlewares.conversor(jTblDual.getValueAt(fila, 9).toString());
            examenRecuperacion = Middlewares.conversor(jTblDual.getValueAt(fila, 10).toString());

            if (examenRecuperacion != 0) {
                notaFinal = totalGestion + examenRecuperacion;
            } else {
                notaFinal = totalGestion + examenFinal;
            }

            validarAprobado(examenFinal, examenRecuperacion, Math.round(notaFinal), jTblDual);
            jTblDual.setValueAt(Math.round(notaFinal), fila, 11);
            return null;
        };
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="CARRERAS DUALES">
    private void cacularNotasDuales(JTable tabla) {

        int fila = tabla.getSelectedRow();
        int columna = tabla.getSelectedColumn();
        String valueText;
        String tipoNota;

        switch (columna) {
            case 6:
                valueText = tabla.getValueAt(fila, columna).toString();
                tipoNota = "G. DE AULA 1";
                guardarDUAL(fila, valueText, tipoNota);
                break;
            case 7:
                tipoNota = "G. DE AULA 2";
                valueText = tabla.getValueAt(fila, columna).toString();
                guardarDUAL(fila, valueText, tipoNota);

                break;
            case 9:
                tipoNota = "EXAMEN FINAL";
                valueText = tabla.getValueAt(fila, columna).toString();
                guardarDUAL(fila, valueText, tipoNota);

                break;
            case 10:
                tipoNota = "EXAMEN DE RECUPERACION";
                valueText = tabla.getValueAt(fila, columna).toString();
                guardarDUAL(fila, valueText, tipoNota);

                break;

            case 13:// FALTAS
                editarFaltas(fila, tabla, agregarFilasDuales(), editarDuales(), sumarDual());
                break;

            case 15:
                String asistencia = tabla.getValueAt(fila, columna).toString().toLowerCase();
                int colEstado = getIndex.apply(tabla, "Estado");
                if (asistencia.equalsIgnoreCase("retirado")) {
                    tabla.setValueAt("RETIRADO", fila, colEstado);
                    editarDuales().apply(null);
                } else if (asistencia.equalsIgnoreCase("asiste")) {
                    tabla.setValueAt("REPROBADO", fila, colEstado);
                    sumarDual().apply(null);
                    editarDuales().apply("");
                } else {
                    tabla.setValueAt("REPROBADO", fila, colEstado);
                    editarDuales().apply("");
                }
                break;
            default:
                break;
        }

    }

    private Function<String, Void> editarDuales() {

        return tipoNota -> {
            jTblDual.setEnabled(false);
            int fila = getSelectedRowDuales();
            int columna = getSelectedColumDuales();

            String estado = jTblDual.getValueAt(fila, 12).toString();
            String asistencia = jTblDual.getValueAt(fila, 15).toString();

            AlumnoCursoBD alumno = listaNotas.get(fila);

            if (tipoNota != null) {

                List<NotasBD> notas = alumno.getNotas();

                notas.stream().filter(buscar(tipoNota)).collect(Collectors.toList())
                        .forEach(editarNota(fila, columna, tablaDuales));

                notas.stream().filter(buscar("TOTAL GESTION")).collect(Collectors.toList())
                        .forEach(editarNota(fila, 8, tablaDuales));
                if (asistencia.equalsIgnoreCase("retirado")) {
                    estado = "RETIRADO";
                }
                alumno.setNotaFinal(Middlewares.conversor(jTblDual.getValueAt(fila, 11).toString()));
            }

            alumno.setEstado(estado);
            alumno.setAsistencia(asistencia);
            alumno.setNumFalta(Integer.valueOf(tablaDuales.getValueAt(fila, 13).toString()));
            alumno.editar();
            jTblDual.setEnabled(true);
            return null;
        };

    }

    private int carcularPorcentaje(int faltas, int horas) {
        if (horas == 0) {
            horas = 1;
        }
        return (faltas * 100) / horas;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="EVENTOS">
    private void btnVerNotas(ActionEvent e) {
        if (cargarTabla) {

            if (getModalidad().equalsIgnoreCase("TRADICIONAL") || getModalidad().equalsIgnoreCase("PRESENCIAL")) {
                vista.getTabPane().setSelectedIndex(0);
                jTblTrad.clearSelection();
                jTblTrad.removeAll();
                tablaTrad.setRowCount(0);
                cargarTabla(agregarFilasTrad());
            } else {
                vista.getTabPane().setSelectedIndex(1);
                jTblDual.clearSelection();
                jTblDual.removeAll();
                tablaDuales.setRowCount(0);
                cargarTabla(agregarFilasDuales());
            }
        } else {
            JOptionPane.showMessageDialog(vista, "YA HAY UNA CARGA PENDIENTE!");
        }

        vista.setTitle("NOTAS " + vista.getCmbCiclo().getSelectedItem().toString());
    }

    private void btnImprimir(ActionEvent e) {
        new Thread(() -> {

            int r = JOptionPane.showOptionDialog(vista, "Reporte de Notas por Curso\n" + "¿Elegir el tipo de Reporte?",
                    "REPORTE NOTAS", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                    new Object[]{"Alumnos con menos de 70", "Alumnos entre 70 a 80", "Alumnos entre 80 a 90",
                        "Alumnos entre 90 a 100", "Reporte Completo", "Tabla Final"},
                    "Cancelar");

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

                case 5:
                    desktop.getLblEstado().setText("CARGANDO REPORTE....");
                    reportes.generarReporteInformeFinalTabla();
                    desktop.getLblEstado().setText("COMPLETADO");
                    break;

                default:
                    break;
            }

            try {
                sleep(500);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
            desktop.getLblEstado().setText("");
            Effects.setDefaultCursor(vista);
            vista.getBtnVerNotas().setEnabled(true);
        }).start();

    }

    private void buscarDocente() {
        activarForm(false);
        vista.getCmbDocente()
                .setSelectedItem(listaDocentes.entrySet().stream()
                        .filter(entry -> entry.getValue().getIdentificacion().equals(vista.getTxtBuscar().getText()))
                        .map(c -> c.getKey()).findFirst().orElse(""));
        activarForm(true);
    }

    // </editor-fold>
}
