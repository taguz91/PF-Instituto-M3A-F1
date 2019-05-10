/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.notas;

import controlador.Libraries.Effects;
import controlador.Libraries.Middlewares;
import controlador.Libraries.Validaciones;
import controlador.Libraries.cellEditor.ComboBoxCellEditor;
import controlador.Libraries.cellEditor.ComboBoxCellEditor;
import controlador.Libraries.cellEditor.TextFieldCellEditor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;
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
import vista.notas.VtnNotas;
import vista.principal.VtnPrincipal;

/**
 *
 * @author MrRainx
 */
public class NotasCTR {

    private final VtnPrincipal desktop;
    private final VtnNotas vista;
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

    //JTables
    private JTable jTblTrad;
    private JTable jTblDual;

    //ACTIVACION DE HILOS
    private boolean cargarTabla = true;

    public NotasCTR(VtnPrincipal desktop, VtnNotas vista, UsuarioBD usuario, RolBD rolSeleccionado) {
        this.desktop = desktop;
        this.vista = vista;
        this.usuario = usuario;
        this.rolSeleccionado = rolSeleccionado;
    }

    // <editor-fold defaultstate="collapsed" desc="INITS">    
    public void Init() {
        tablaNotasTrad = (DefaultTableModel) vista.getTblTrad().getModel();
        tablaNotasDuales = (DefaultTableModel) vista.getTblDual().getModel();

        jTblTrad = vista.getTblTrad();
        jTblDual = vista.getTblDual();

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
        InitTablas();
        activarForm(true);
    }

    private void InitEventos() {

        vista.getCmbDocente().addActionListener(e -> cargarComboPeriodos());

        vista.getCmbPeriodoLectivo().addActionListener(e -> cargarComboCiclo());

        vista.getCmbPeriodoLectivo().addItemListener(e -> setLblCarrera());

        vista.getCmbCiclo().addActionListener(e -> cargarComboMaterias());

        vista.getBtnVerNotas().addActionListener(e -> btnVerNotas(e));
//
//        vista.getBtnImprimir().addActionListener(e -> btnImprimir(e));
//
//        vista.getBtnBuscar().addActionListener(e -> buscarDocente());
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

    }

    private void InitTablas() {

        tablaNotasTrad.addTableModelListener(new TableModelListener() {

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

        tablaNotasDuales.addTableModelListener(new TableModelListener() {
            boolean active = false;

            @Override
            public void tableChanged(TableModelEvent e) {

                if (!active && e.getType() == TableModelEvent.UPDATE) {
                    active = true;
                    //cacularNotasDuales(tablaNotasDuales);
                    active = false;
                }

            }
        });
        //TABLA TRADICIONALES
        jTblTrad.getColumnModel().getColumn(6).setCellEditor(new TextFieldCellEditor(true));
        jTblTrad.getColumnModel().getColumn(7).setCellEditor(new TextFieldCellEditor(true));
        jTblTrad.getColumnModel().getColumn(9).setCellEditor(new TextFieldCellEditor(true));
        jTblTrad.getColumnModel().getColumn(10).setCellEditor(new TextFieldCellEditor(true));
        jTblTrad.getColumnModel().getColumn(11).setCellEditor(new TextFieldCellEditor(true));
        jTblTrad.getColumnModel().getColumn(14).setCellEditor(new TextFieldCellEditor(true));

        List<String> items = new ArrayList<>();
        items.add("Asiste");
        items.add("No asiste");
        items.add("Retirado");
        items.add("Desertor");
        jTblTrad.getColumnModel().getColumn(16).setCellEditor(new ComboBoxCellEditor(true, items));

        //TABLA DUALES
        jTblDual.getColumnModel().getColumn(6).setCellEditor(new TextFieldCellEditor(true));
        jTblDual.getColumnModel().getColumn(7).setCellEditor(new TextFieldCellEditor(true));
        jTblDual.getColumnModel().getColumn(9).setCellEditor(new TextFieldCellEditor(true));
        jTblDual.getColumnModel().getColumn(10).setCellEditor(new TextFieldCellEditor(true));
        jTblDual.getColumnModel().getColumn(13).setCellEditor(new TextFieldCellEditor(true));
        jTblDual.getColumnModel().getColumn(15).setCellEditor(new ComboBoxCellEditor(true, items));
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
                .map(c -> c.getValue().getIdDocente()).findAny().get();
    }

    private void mensajeDeError() {
        JOptionPane.showMessageDialog(vista, "INGRESE UN NUMERO CORRECTO "
                + "\n       EJEMPLO (15.6)");

    }

    private void errorDeNota(TipoDeNotaMD rango) {
        JOptionPane.showMessageDialog(vista, "EL RANGO DE LA NOTA DEBE ESTAR ENTRE: " + 0 + " Y " + rango.getValorMaximo());
    }

    private void refreshTabla(BiFunction<AlumnoCursoBD, DefaultTableModel, Void> funcion, DefaultTableModel tabla) {
        activarForm(false);
        tabla.setRowCount(0);
        listaNotas.stream().forEach(obj -> {
            funcion.apply(obj, tabla);
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

    public static BiFunction<JTable, String, Integer> getIndex = (tabla, nombre) -> {
        return tabla.getColumnModel().getColumnIndex(nombre);
    };

    private int getHoras() {
        return listaMaterias.stream()
                .filter(item -> item.getNombre().equals(vista.getCmbAsignatura().getSelectedItem().toString()))
                .map(c -> c.getHorasPresenciales()).findFirst().orElse(1);
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
        vista.getTblTrad().setEnabled(estado);
    }

    private int calcularPorcentaje(int faltas, int horas) {
        if (horas == 0) {
            horas = 1;
        }
        return (faltas * 100) / horas;
    }

    private void editarFaltas(int fila, JTable tabla, BiFunction<AlumnoCursoBD, DefaultTableModel, Void> agregarFilas,
            Function<String, Void> editar, Function<Void, Void> sumar) {

        int colFaltas = getIndex.apply(tabla, "Faltas");
        int colEstado = getIndex.apply(tabla, "Estado");
        int conPorcentaje = getIndex.apply(tabla, "% Faltas");
        int colAsistencia = getIndex.apply(tabla, "Asistencia");

        String faltasText = tabla.getValueAt(fila, colFaltas).toString();

        if (faltasText.isEmpty()) {
            faltasText = "2.2";
        }

        if (Validaciones.isInt(faltasText)) {
            int faltas = new Integer(faltasText);
            int oldFaltas = listaNotas.get(fila).getNumFalta();
            if (faltas != oldFaltas) {
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
            }
        } else {
            JOptionPane.showMessageDialog(vista, "INGRESE SOLO NUMERO ENTEROS!!!");
            refreshTabla(agregarFilas, (DefaultTableModel) tabla.getModel());
        }
    }

    private void cargarTabla(DefaultTableModel tabla, BiFunction<AlumnoCursoBD, DefaultTableModel, Void> funcionCarga) {
        new Thread(() -> {

            cargarTabla = false;
            String cursoNombre = vista.getCmbCiclo().getSelectedItem().toString();
            String nombreMateria = vista.getCmbAsignatura().getSelectedItem().toString();

            listaNotas = AlumnoCursoBD.selectWhere(cursoNombre, nombreMateria, getIdDocente(), getIdPeriodoLectivo());

            listaNotas.stream().forEach(obj -> {
                funcionCarga.apply(obj, tabla);
            });
            cargarTabla = true;

        }).start();
    }

    private double getValorNota(int index, String tipoNota) {
        return listaNotas.get(index).getNotas()
                .stream()
                .filter(item -> item.getTipoDeNota().getNombre().equals(tipoNota))
                .findFirst()
                .map(c -> c.getNotaValor())
                .orElse(listaNotas.get(index).getNotaFinal());
    }

    // </editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="CARRERAS TRADICIONALES"> 
    private BiFunction<AlumnoCursoBD, DefaultTableModel, Void> agregarFilasTrad() {
        return (obj, tabla) -> {
            tabla.addRow(new Object[]{
                tabla.getDataVector().size() + 1,
                obj.getAlumno().getIdentificacion(),
                obj.getAlumno().getPrimerApellido(),
                obj.getAlumno().getSegundoApellido(),
                obj.getAlumno().getPrimerNombre(),
                obj.getAlumno().getSegundoNombre(),
                obj.getNotas().stream().filter(buscar("APORTE 1")).findAny().get().getNotaValor(),
                obj.getNotas().stream().filter(buscar("EXAMEN INTERCICLO")).findAny().get().getNotaValor(),
                obj.getNotas().stream().filter(buscar("NOTA INTERCICLO")).findAny().get().getNotaValor(),
                obj.getNotas().stream().filter(buscar("APORTE 2")).findAny().get().getNotaValor(),
                obj.getNotas().stream().filter(buscar("EXAMEN FINAL")).findAny().get().getNotaValor(),
                obj.getNotas().stream().filter(buscar("EXAMEN DE RECUPERACION")).findAny().get().getNotaValor(),
                (int) Middlewares.conversor("" + obj.getNotaFinal()),
                obj.getEstado(),
                obj.getNumFalta(),
                calcularPorcentaje(obj.getNumFalta(), getHoras()),
                obj.getAsistencia()
            });
            return null;
        };
    }

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

                editarFaltas(fila, tabla, agregarFilasTrad(), editarTrad(), sumarTrad);

                break;
            case 16:
                String asistencia = tabla.getValueAt(fila, columna).toString();
                int colEstado = getIndex.apply(tabla, "Estado");
                switch (asistencia.toLowerCase()) {
                    case "retirado":
                        tabla.setValueAt("RETIRADO", fila, colEstado);
                        editarTrad().apply(null);
                        break;
                    default:
                        tabla.setValueAt("REPROBADO", fila, colEstado);
                        sumarTrad.apply(null);
                        editarTrad().apply("");
                        break;
                }
                break;
        }

    }

    // <editor-fold defaultstate="collapsed" desc="SUMA DE COLUMNAS"> 
    private void guardarTRAD(int fila, String valueText, String tipoNota) {
        if (Validaciones.isDecimal(valueText)) {
            double value = Middlewares.conversor(valueText);
            if (value != getValorNota(fila, tipoNota)) {
                TipoDeNotaMD rango = getRango(tipoNota);
                if (value > rango.getValorMaximo()) {
                    errorDeNota(rango);
                    refreshTabla(agregarFilasTrad(), tablaNotasTrad);
                } else {
                    sumarTrad.apply(null);
                    editarTrad().apply(tipoNota);
                    refreshTabla(agregarFilasTrad(), tablaNotasTrad);
                }
            }

        } else {
            mensajeDeError();
            refreshTabla(agregarFilasTrad(), tablaNotasTrad);
        }
    }

    private Function<Void, Void> sumarTrad = t -> {
        int fila = getSelectedRowTrad();

        double aporte1;
        double examenInterCiclo;
        double totalInterciclo;

        double aporte2;
        double examenFinal;
        double examenRecuperacion;
        double notaFinal;

        aporte1 = Middlewares.conversor(tablaNotasTrad.getValueAt(fila, 6).toString());
        examenInterCiclo = Middlewares.conversor(tablaNotasTrad.getValueAt(fila, 7).toString());
        totalInterciclo = aporte1 + examenInterCiclo;
        tablaNotasTrad.setValueAt(totalInterciclo, fila, 8);
        aporte2 = Middlewares.conversor(tablaNotasTrad.getValueAt(fila, 9).toString());
        examenFinal = Middlewares.conversor(tablaNotasTrad.getValueAt(fila, 10).toString());
        examenRecuperacion = Middlewares.conversor(tablaNotasTrad.getValueAt(fila, 11).toString());

        if (examenRecuperacion != 0) {
            notaFinal = totalInterciclo + aporte2 + examenRecuperacion;
        } else {
            notaFinal = totalInterciclo + aporte2 + examenFinal;
        }
        validarAprobado(examenFinal, examenRecuperacion, Math.round(notaFinal), jTblTrad);
        tablaNotasTrad.setValueAt(Math.round(notaFinal), fila, 12);
        return null;
    };

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
                        .forEach(editarNota(fila, columa, tablaNotasTrad));

                notas.stream().filter(buscar("NOTA INTERCICLO")).collect(Collectors.toList())
                        .forEach(editarNota(fila, 8, tablaNotasTrad));

                if (asistencia.equalsIgnoreCase("retirado")) {
                    estado = "RETIRADO";
                }

                alumno.setNotaFinal(Middlewares.conversor(vista.getTblTrad().getValueAt(fila, 12).toString()));
            }
            alumno.setAsistencia(asistencia);
            alumno.setEstado(estado);
            alumno.setNumFalta(Integer.valueOf(tablaNotasTrad.getValueAt(fila, 14).toString()));
            alumno.editar();
            jTblTrad.setEnabled(true);
            return null;
        };

    }

    // </editor-fold>  
    // </editor-fold>  
    // <editor-fold defaultstate="collapsed" desc="EVENTOS"> 
    private void btnVerNotas(ActionEvent e) {
        if (cargarTabla) {
            jTblTrad.clearSelection();
            jTblTrad.removeAll();
            tablaNotasTrad.setRowCount(0);
            cargarTabla(tablaNotasTrad, agregarFilasTrad());
        } else {
            JOptionPane.showMessageDialog(vista, "YA HAY UNA CARGA PENDIENTE!");
        }

        vista.setTitle("NOTAS " + vista.getCmbCiclo().getSelectedItem().toString());
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
