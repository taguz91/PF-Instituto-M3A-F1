package controlador.notas;

import controlador.Libraries.Effects;
import controlador.Libraries.Effects;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.tipoDeNota.IngresoNotasBD;
import modelo.tipoDeNota.IngresoNotasMD;
import modelo.usuario.RolBD;
import vista.notas.VtnActivarNotas;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Alejandro
 */
public class VtnActivarNotasCTR {

    private final VtnPrincipal desktop;
    private static VtnActivarNotas vista;
    private IngresoNotasBD modelo;
    private final RolBD permisos;

    //TABLA
    private static DefaultTableModel tablaActivarNotas;

    //LISTA
    private List<IngresoNotasBD> listaNotasActivadas;
    private List<PeriodoLectivoMD> listaPeriodos;

    private boolean cargarTabla = true;
    private String itemCombo;

    public VtnActivarNotasCTR(VtnPrincipal desktop, VtnActivarNotas vista, IngresoNotasBD modelo, RolBD permisos) {
        this.desktop = desktop;
        this.vista = vista;
        this.modelo = modelo;
        this.permisos = permisos;
    }

    //INIT
    public void Init() {
        tablaActivarNotas = (DefaultTableModel) vista.getTblCursoTipoNotas().getModel();

        InitEventos();

        new Thread(() -> {
            Effects.setLoadCursor(vista);
            listaPeriodos = PeriodoLectivoBD.SelectAll();
            cargarComboPeriodos();
            setSelectedItemInCombo();
            Effects.setDefaultCursor(vista);
        }).start();

        try {
            vista.show();
            desktop.getDpnlPrincipal().add(vista);
            Effects.centerFrame(vista, desktop.getDpnlPrincipal());
            vista.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(VtnActivarNotasCTR.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void InitEventos() {

        tablaActivarNotas.addTableModelListener(new TableModelListener() {

            boolean active = false;

            @Override
            public void tableChanged(TableModelEvent e) {
                if (!active && e.getType() == TableModelEvent.UPDATE) {

                    active = true;
                    vista.getTblCursoTipoNotas().setModel(Validaciones(tablaActivarNotas));
                    active = false;

                }

            }

        });

        vista.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                txtBuscarOnKeyReleased(e);
            }

        });

        vista.getBtnActualizar().addActionListener(e -> btnActualizar(e));

        vista.getTblCursoTipoNotas().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (modelo.editar(modelo.getIdIngresoNotas())) {
                    refreshTabla();
                    Effects.setTextInLabel(vista.getLblDatosCorrectos(), "Datos actualizados correctamente", 2);
                }

                System.out.println(modelo);
            }
        });

        tablaActivarNotas.addTableModelListener(new TableModelListener() {
            boolean active = false;

            @Override
            public void tableChanged(TableModelEvent e) {
                if (!active && e.getType() == TableModelEvent.UPDATE) {
                    active = true;
                    tablaActivarNotas = (DefaultTableModel) Validaciones(tablaActivarNotas);
                    //vista.getTblCursoTipoNotas().setModel(tablaActivarNotas);
                    //active = false;
                }
            }
        });

        vista.getCmbPeriodoLectivo().addActionListener(e -> cmbPeriodos(e));

    }

    //METODOS DE APOYO
    private void setObj(int fila) {
        if (fila != -1) {

            modelo = new IngresoNotasBD();

            modelo.setIdIngresoNotas(Integer.parseInt(vista.getTblCursoTipoNotas().getValueAt(fila, 1).toString()));

            modelo.setNotaPrimerInterCiclo(validarValor(vista.getTblCursoTipoNotas().getValueAt(fila, 7).toString()));
            modelo.setNotaExamenInteCiclo(validarValor(vista.getTblCursoTipoNotas().getValueAt(fila, 8).toString()));
            modelo.setNotaSegundoInterCiclo(validarValor(vista.getTblCursoTipoNotas().getValueAt(fila, 9).toString()));
            modelo.setNotaExamenFinal(validarValor(vista.getTblCursoTipoNotas().getValueAt(fila, 10).toString()));
            modelo.setNotaExamenDeRecuperacion(validarValor(vista.getTblCursoTipoNotas().getValueAt(fila, 11).toString()));
        }

    }

    private void setSelectedItemInCombo() {
        try {
            itemCombo = vista.getCmbPeriodoLectivo().getSelectedItem().toString();
        } catch (NullPointerException e) {
        }
    }

    private boolean validarValor(String valor) {
        boolean valorBool = false;
        try {
            valorBool = Boolean.parseBoolean(valor);
        } catch (Exception e) {
            valorBool = false;
        }
        return valorBool;
    }

    private void cargarTabla(List<IngresoNotasBD> lista) {
        new Thread(() -> {
            if (cargarTabla) {

                Effects.setLoadCursor(vista);

                cargarTabla = false;

                desktop.getLblEstado().setText("CARGANDO...");

                tablaActivarNotas.setRowCount(0);

                lista.stream().forEach(VtnActivarNotasCTR::agregarFila);

                vista.getLblResultados().setText(lista.size() + " Resultados");

                cargarTabla = true;

                Effects.setDefaultCursor(vista);

                Effects.setTextInLabel(desktop.getLblEstado(), "COMPLETADO", 2);
            }

        }).start();

    }

    private void cargarComboPeriodos() {
        vista.getCmbPeriodoLectivo().removeAllItems();
        listaPeriodos
                .stream()
                .forEach(obj -> {
                    vista.getCmbPeriodoLectivo().addItem(obj.getNombre_PerLectivo());
                });
    }

    private static void agregarFila(IngresoNotasMD obj) {
        tablaActivarNotas.addRow(new Object[]{
            tablaActivarNotas.getDataVector().size() + 1,
            obj.getIdIngresoNotas(),
            obj.getCurso().getPeriodo().getNombre_PerLectivo(),
            obj.getCurso().getNombre(),
            obj.getCurso().getMateria().getNombre(),
            obj.getCurso().getDocente().getPrimerNombre() + " " + obj.getCurso().getDocente().getSegundoNombre(),
            obj.getCurso().getDocente().getPrimerApellido() + " " + obj.getCurso().getDocente().getSegundoApellido(),
            obj.isNotaPrimerInterCiclo(),
            obj.isNotaExamenInteCiclo(),
            obj.isNotaSegundoInterCiclo(),
            obj.isNotaExamenFinal(),
            obj.isNotaExamenDeRecuperacion()
        });
    }

    private TableModel Validaciones(TableModel datos) {
        int fila = getSelectedRow();
        int columna = vista.getTblCursoTipoNotas().getSelectedColumn();
        String valor = "";
        try {

            switch (getSelectedColum()) {
                case 7:
                    valor = vista.getTblCursoTipoNotas().getValueAt(getSelectedRow(), 7).toString();

                    IngresoNotasBD notaModificar = listaNotasActivadas.get(getSelectedRow());

                    break;
                case 8:

                    break;
                case 9:

                    break;
                case 10:

                    break;
                case 11:

                    break;
                default:
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }

        return datos;

    }

    private static int getSelectedRow() {
        return vista.getTblCursoTipoNotas().getSelectedRow();
    }

    private static int getSelectedColum() {
        return vista.getTblCursoTipoNotas().getSelectedColumn();
    }

    //EVENTOS
    private void txtBuscarOnKeyReleased(KeyEvent e) {

    }

    private void btnActualizar(ActionEvent e) {

        if (cargarTabla) {

            listaNotasActivadas = null;

            System.gc();
            setSelectedItemInCombo();
            listaNotasActivadas = IngresoNotasBD.selectAll(itemCombo);

            cargarTabla(listaNotasActivadas);
        } else {
            JOptionPane.showMessageDialog(vista, "YA HAY UNA CARGA PENDIENTE!!");
        }

    }

    private void cmbPeriodos(ActionEvent e) {
        new Thread(() -> {
            setSelectedItemInCombo();
            listaNotasActivadas = IngresoNotasBD.selectAll(itemCombo);
            cargarTabla(listaNotasActivadas);
        }).start();

    }

    private void refreshTabla() {
        System.out.println("REFRESH");
        tablaActivarNotas.setRowCount(0);
        new Thread(() -> {
            listaNotasActivadas.stream().forEach(VtnActivarNotasCTR::agregarFila);
        }).start();

    }

}
