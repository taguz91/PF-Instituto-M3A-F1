package controlador.periodoLectivoNotas.tipoDeNotas.forms;

import controlador.Libraries.Effects;
import controlador.Libraries.Middlewares;
import controlador.Libraries.Validaciones;
import controlador.periodoLectivoNotas.tipoDeNotas.VtnTipoNotasCTR;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.tipoDeNota.TipoDeNotaBD;
import vista.periodoLectivoNotas.FrmTipoNota;
import vista.principal.VtnPrincipal;

/**
 *
 * @author MrRainx
 */
public abstract class AbstracForm {

    protected VtnPrincipal desktop;
    protected FrmTipoNota vista;
    protected TipoDeNotaBD modelo;
    //Ventana Padre
    protected VtnTipoNotasCTR vtnPadre;
    //listas
    protected Map<String, PeriodoLectivoMD> listaPeriodos;

    //Combo
    protected String[] carrerasTradicionales = {
        "APORTE 1",
        "NOTA INTERCICLO",
        "EXAMEN INTERCICLO",
        "APORTE 2",
        "EXAMEN FINAL",
        "EXAMEN SUPLETORIO",
        "NOTA FINAL"
    };

    protected String[] carrerasDuales = {
        "G. DE AULA 1",
        "G. DE AULA 2",
        "TOTAL GESTION",
        "EXAMEN FINAL",
        "EXAMEN DE RECUPERACION",
        "NOTA FINAL"
    };

    //TABLAS
    protected DefaultTableModel tabla;

    public AbstracForm(VtnPrincipal desktop, FrmTipoNota vista, TipoDeNotaBD modelo, VtnTipoNotasCTR vtnPadre) {
        this.desktop = desktop;
        this.vista = vista;
        this.modelo = modelo;
        this.vtnPadre = vtnPadre;
    }

    //INITS
    public void Init() {

        tabla = (DefaultTableModel) vista.getTblTipoNota().getModel();

        Effects.addInDesktopPane(vista, desktop.getDpnlPrincipal());

        listaPeriodos = PeriodoLectivoBD.selectWhereEstadoAndActivo(true, true);

        cargarComboCarreras();

        setlblCarrera();

        cargarTabla();

        InitEventos();

    }

    private void InitEventos() {
        vista.getBtnCancelar().addActionListener(e -> btnCancelar(e));

        vista.getBtnGuardar().addActionListener(e -> btnGuardar(e));

        vista.getCmbPeriodoLectivo().addActionListener(e -> {
            cargarTabla();
            setlblCarrera();
        });

        tabla.addTableModelListener(new TableModelListener() {

            boolean active = false;

            @Override
            public void tableChanged(TableModelEvent e) {
                if (!active && e.getType() == TableModelEvent.UPDATE) {

                    active = true;

                    validarNotas();

                    active = false;
                }

            }
        });

    }

    //METODOS DE APOYO
    public int getRow() {
        return vista.getTblTipoNota().getSelectedRow();
    }

    public int getColum() {
        return vista.getTblTipoNota().getSelectedColumn();
    }

    protected void cargarComboCarreras() {

        listaPeriodos.entrySet().stream().forEach(entry -> {
            vista.getCmbPeriodoLectivo().addItem(entry.getKey());
        });
    }

    private void setlblCarrera() {

        vista.getLblNombreCarrera().setText(listaPeriodos
                .entrySet()
                .stream()
                .filter(item -> item.getKey().equals(vista.getCmbPeriodoLectivo().getSelectedItem().toString()))
                .map(c -> c.getValue().getCarrera().getNombre())
                .findFirst()
                .orElse(""));

    }

    protected String getModalidad() {
        return listaPeriodos
                .entrySet()
                .stream()
                .filter(item -> item.getKey().equalsIgnoreCase(vista.getCmbPeriodoLectivo().getSelectedItem().toString()))
                .map(c -> c.getValue().getCarrera().getModalidad())
                .findAny()
                .orElse("");
    }

    protected void cargarTabla() {
        tabla.setRowCount(0);
        if (getModalidad().toLowerCase().contains("dual")) {
            Arrays.asList(carrerasDuales)
                    .stream()
                    .forEach(obj -> {
                        tabla.addRow(new Object[]{obj, 0, 100});
                    });

        } else {
            if (getModalidad().toLowerCase().contains("tradicional")) {
                Arrays.asList(carrerasTradicionales)
                        .stream()
                        .forEach(obj -> {
                            tabla.addRow(new Object[]{obj, 0, 100});

                        });

            }

        }

    }

    protected void validacion() {
        String v1 = "0";
        String v2 = "0";
        try {
            v1 = tabla.getValueAt(getRow(), 1).toString();
            v2 = tabla.getValueAt(getRow(), 2).toString();
        } catch (NullPointerException e) {
        }

        if (Validaciones.isDecimal(v1)) {
            if (!v2.isEmpty()) {
                if (Validaciones.isDecimal(v2)) {

                    double valor1 = Middlewares.conversor(v1);
                    double valor2 = Middlewares.conversor(v2);
                    if (valor1 > valor2 || valor1 == valor2 || valor2 == 0 || valor1 >= 0) {
                        JOptionPane.showMessageDialog(vista,
                                "EL VALOR MINIMO NO PUEDE SER MENOR AL MAXIMO\n"
                                + "EL VALOR MAXIMO NO PUEDE SER 0\n"
                                + "EL VALOR MINIMO Y MAXIMO NO PUEDEN SER IGUALES"
                        );
                        cargarTabla();
                    }

                }
            }

        } else {
            cargarTabla();
        }

    }

    //PROCESADORES DE EVENTOS
    private void btnCancelar(ActionEvent e) {
        vista.dispose();

    }

    protected abstract void btnGuardar(ActionEvent e);

    private void validarNotas() {

        switch (getColum()) {
            case 1:
                validacion();
                break;
            case 2:
                validacion();
                break;
            default:
                break;
        }

    }
}
