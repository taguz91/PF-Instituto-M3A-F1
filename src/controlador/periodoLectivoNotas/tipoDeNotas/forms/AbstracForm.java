package controlador.periodoLectivoNotas.tipoDeNotas.forms;

import controlador.Libraries.Middlewares;
import controlador.Libraries.Validaciones;
import controlador.Libraries.cellEditor.TextFieldCellEditor;
import controlador.periodoLectivoNotas.tipoDeNotas.VtnTipoNotasCTR;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
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
    protected List<String> listaDuales;
    protected List<String> listaTradicionales;
    protected List<TipoDeNotaBD> listaTipos;
    protected List<String> listaNombres;
    //TABLAS
    protected DefaultTableModel tabla;

    protected PeriodoLectivoBD periodoBD;

    {
        periodoBD = new PeriodoLectivoBD();
    }

    public AbstracForm(VtnPrincipal desktop, FrmTipoNota vista, TipoDeNotaBD modelo, VtnTipoNotasCTR vtnPadre) {
        this.desktop = desktop;
        this.vista = vista;
        this.modelo = modelo;
        this.vtnPadre = vtnPadre;
    }

    //INITS
    public abstract void Init();

    protected void InitEventos() {
        vista.getBtnCancelar().addActionListener(e -> vista.dispose());

        vista.getBtnGuardar().addActionListener(e -> btnGuardar(e));

        vista.getCmbPeriodoLectivo().addItemListener(e -> {
            InitListas();
            listaNombres = modelo.selectNombreWhere(getIdPeriodo());
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

    protected void InitListas() {
        listaDuales = new ArrayList<>();
        listaDuales.add("G. DE AULA 1");
        listaDuales.add("G. DE AULA 2");
        listaDuales.add("TOTAL GESTION");
        listaDuales.add("EXAMEN FINAL");
        listaDuales.add("EXAMEN DE RECUPERACION");
        listaDuales.add("NOTA FINAL");
        listaDuales.add("PTI");
        listaDuales.add("N. TUTOR EMPRESARIAL");
        listaDuales.add("N. TUTOR ACADEMICO");
        listaDuales.add("DESEMPEÑO GENERAL EN LA INSTITUCION FORMADORA");
        listaDuales.add("SUBTOTAL FASE PRACTICA");
        listaDuales.add("NOTA FINAL TOTAL");

        listaTradicionales = new ArrayList<>();
        listaTradicionales.add("APORTE 1");
        listaTradicionales.add("EXAMEN INTERCICLO");
        listaTradicionales.add("NOTA INTERCICLO");
        listaTradicionales.add("APORTE 2");
        listaTradicionales.add("EXAMEN FINAL");
        listaTradicionales.add("EXAMEN DE RECUPERACION");
        listaTradicionales.add("NOTA FINAL");
    }

    protected void InitTablas() {
        vista.getTblTipoNota().getColumnModel().getColumn(1).setCellEditor(new TextFieldCellEditor(true));
        vista.getTblTipoNota().getColumnModel().getColumn(2).setCellEditor(new TextFieldCellEditor(true));
    }

    //METODOS DE APOYO
    protected int getRow() {
        return vista.getTblTipoNota().getSelectedRow();
    }

    protected int getColum() {
        return vista.getTblTipoNota().getSelectedColumn();
    }

    protected void cargarComboCarreras() {

        listaPeriodos.entrySet().stream().forEach(entry -> {
            vista.getCmbPeriodoLectivo().addItem(entry.getKey());
        });
    }

    protected void setlblCarrera() {

        vista.getLblNombreCarrera().setText(
                listaPeriodos.entrySet().stream()
                        .filter(item -> item.getKey().equals(vista.getCmbPeriodoLectivo().getSelectedItem().toString()))
                        .map(c -> c.getValue().getCarrera().getNombre())
                        .findFirst().orElse("")
        );

    }

    protected String getModalidad() {
        return listaPeriodos
                .entrySet()
                .stream()
                .filter(item -> item.getKey().equalsIgnoreCase(vista.getCmbPeriodoLectivo().getSelectedItem().toString()))
                .map(c -> c.getValue().getCarrera().getModalidad())
                .findFirst()
                .orElse("");
    }

    protected int getIdPeriodo() {
        return listaPeriodos
                .entrySet()
                .stream()
                .filter(item -> item.getKey().equalsIgnoreCase(vista.getCmbPeriodoLectivo().getSelectedItem().toString()))
                .map(c -> c.getValue().getId_PerioLectivo())
                .findFirst()
                .get();
    }

    protected void cargarTabla() {

        tabla.setRowCount(0);

        if (getModalidad().toLowerCase().contains("dual")) {

            listaDuales.removeAll(listaNombres);
            listaDuales.forEach(obj -> {
                tabla.addRow(new Object[]{obj, 0, 100});
            });
        } else if (getModalidad().toLowerCase().contains("tradicional") || getModalidad().toLowerCase().contains("presencial")) {
            listaTradicionales.removeAll(listaNombres);
            listaTradicionales.forEach(obj -> {
                tabla.addRow(new Object[]{obj, 0, 100});
            });

        }

    }

    protected void setObjs() {

        listaTipos = new ArrayList<>();
        if (getModalidad().equalsIgnoreCase("presencial") || getModalidad().equalsIgnoreCase("tradicional")) {
            setObjLista(listaTradicionales);
        } else {
            setObjLista(listaDuales);
        }

    }

    private void setObjLista(List<String> lista) {
        lista.stream().forEach(new Consumer<String>() {

            int index = 0;

            @Override
            public void accept(String obj) {
                TipoDeNotaBD tipo = new TipoDeNotaBD();
                tipo.setNombre(obj);

                tipo.setValorMinimo(new Double(vista.getTblTipoNota().getValueAt(index, 1).toString()));
                tipo.setValorMaximo(new Double(vista.getTblTipoNota().getValueAt(index, 2).toString()));

                PeriodoLectivoMD periodo = new PeriodoLectivoMD();
                periodo.setId_PerioLectivo(getIdPeriodo());
                tipo.setPeriodoLectivo(periodo);
                listaTipos.add(tipo);

                index++;
            }
        });
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
                    if (valor1 > valor2 || valor1 == valor2 || valor2 == 0 || valor1 < 0) {
                        JOptionPane.showMessageDialog(vista,
                                "EL VALOR MINIMO NO PUEDE SER MENOR AL MAXIMO\n"
                                + "EL VALOR MAXIMO NO PUEDE SER 0\n"
                                + "EL VALOR MINIMO Y MAXIMO NO PUEDEN SER IGUALES"
                        );
                        tabla.setValueAt(0, getRow(), 1);
                        tabla.setValueAt(100, getRow(), 2);
                    }

                } else {
                    tabla.setValueAt(100, getRow(), 2);
                }
            }

        } else {
            cargarTabla();
        }

    }

    //PROCESADORES DE EVENTOS
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
