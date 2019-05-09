/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.notas;

import controlador.Libraries.Effects;
import controlador.Libraries.Middlewares;
import controlador.Libraries.Validaciones;
import controlador.Libraries.cellEditor.TextFieldCellEditor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import modelo.alumno.AlumnoCursoBD;
import modelo.curso.CursoBD;
import modelo.curso.CursoMD;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
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
        InitTablas();
        activarForm(true);
    }

    private void InitEventos() {

        vista.getCmbDocente().addActionListener(e -> cargarComboPeriodos());

        vista.getCmbPeriodoLectivo().addActionListener(e -> cargarComboCiclo());

        vista.getCmbPeriodoLectivo().addItemListener(e -> setLblCarrera());

        vista.getCmbCiclo().addActionListener(e -> cargarComboMaterias());

//        vista.getBtnVerNotas().addActionListener(e -> btnVerNotas(e));
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
                        //buscarDocente();
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

                    //carlcularNotasTradicionales(tablaNotasTrad);
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
    }

    private void InitTablas() {
        vista.getTblNotas().getColumnModel().getColumn(6).setCellEditor(new TextFieldCellEditor(true));

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

            porcentaje = (faltas * 100) / horas;

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
                estado = "RETIRADO";
                tabla.setValueAt(estado, fila, colRespuesta);
            } else if (asistencia.contains("desertor") || asistencia.contains("no asiste")) {
                estado = "REPROBADO";
                tabla.setValueAt(estado, fila, colRespuesta);
            }
            asistencia = Middlewares.capitalize(asistencia);
            tabla.setValueAt(asistencia, fila, columna);
            AlumnoCursoBD alumno = listaNotas.get(fila);
            alumno.setEstado(estado);
            alumno.setAsistencia(asistencia);
            alumno.editar();
        } else {

            refreshTabla(funcion, tabla);

        }
    }

    // </editor-fold>  
}
