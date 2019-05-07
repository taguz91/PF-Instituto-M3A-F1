package controlador.periodoLectivoNotas.tipoDeNotas.forms;

import controlador.Libraries.Effects;
import controlador.periodoLectivoNotas.tipoDeNotas.VtnTipoNotasCTR;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Map;
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

        cargarTabla();

        InitEventos();
    }

    private void InitEventos() {
        vista.getBtnCancelar().addActionListener(e -> btnCancelar(e));

        vista.getBtnGuardar().addActionListener(e -> btnGuardar(e));

        vista.getCmbPeriodoLectivo().addActionListener(e -> cargarTabla());
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
                        tabla.addRow(new Object[]{obj});
                    });

        } else {
            if (getModalidad().toLowerCase().contains("tradicional")) {
                Arrays.asList(carrerasTradicionales)
                        .stream()
                        .forEach(obj -> {
                            tabla.addRow(new Object[]{obj});

                        });

            }

        }

    }
    
    protected void validacion (){
        String v1 = tabla.getValueAt(getRow(), getColum()).toString();
        tabla.setValueAt(0, getRow(), 1);//Minimo
        String v2 = tabla.getValueAt(getRow(), getColum()).toString();
        tabla.setValueAt(0, getRow(), 2);//Maximo
        

    }

    //PROCESADORES DE EVENTOS
    private void btnCancelar(ActionEvent e) {
        vista.dispose();
        

    }

    protected abstract void btnGuardar(ActionEvent e);

}
