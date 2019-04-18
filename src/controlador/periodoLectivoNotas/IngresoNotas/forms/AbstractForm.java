package controlador.periodoLectivoNotas.IngresoNotas.forms;

import com.toedter.calendar.JDateChooser;
import controlador.periodoLectivoNotas.IngresoNotas.VtnPeriodoIngresoNotasCTR;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import modelo.periodoIngresoNotas.PeriodoIngresoNotasBD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.tipoDeNota.TipoDeNotaBD;
import modelo.tipoDeNota.TipoDeNotaMD;
import vista.periodoLectivoNotas.FrmIngresoNotas;
import vista.principal.VtnPrincipal;

/**
 *
 * @author MrRainx
 */
public abstract class AbstractForm {

    protected VtnPrincipal desktop;
    protected FrmIngresoNotas vista;
    protected PeriodoIngresoNotasBD modelo;
    protected VtnPeriodoIngresoNotasCTR vtnPadre;

    //listas
    protected List<PeriodoLectivoMD> listaNomPeriodos;
    protected List<TipoDeNotaMD> listaNomNotas;

    public AbstractForm(VtnPrincipal desktop, FrmIngresoNotas vista, PeriodoIngresoNotasBD modelo, VtnPeriodoIngresoNotasCTR vtnPadre) {
        this.desktop = desktop;
        this.vista = vista;
        this.modelo = modelo;
        this.vtnPadre = vtnPadre;
    }

    public void Init() {

        listaNomPeriodos = PeriodoLectivoBD.SelectAll();
        listaNomNotas = TipoDeNotaBD.SelectAll();
        cargarComboNotas();
        cargarComboPeriodo();

        InitEventos();
        try {
            desktop.getDpnlPrincipal().add(vista);
            vista.setSelected(true);
            vista.show();
        } catch (PropertyVetoException ex) {
            Logger.getLogger(FrmIngresoNotasAgregar.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("prueba netbeans");
    }

    private void InitEventos() {
        vista.getBtnGuardar().addActionListener(e -> btnGuardar(e));
        vista.getBtnCancelar().addActionListener(e -> vista.dispose());
    }

    //METODOS DE APOYO
    protected boolean validarFormulario() {
        if (vista.getJdcFechaIni().getCalendar() != null) {
            if (vista.getJdcFechaFin().getCalendar() != null) {
                return true;
            } else {
                JOptionPane.showMessageDialog(vista, "RELLENE BIEN LA FECHA DE INICIO");
            }
        } else {
            JOptionPane.showMessageDialog(vista, "RELLENE BIEN LA FECHA DE FINALIZACION");
        }
        return false;
    }

    protected LocalDate conversorFechas(JDateChooser jDateChooser) {
        int anio = jDateChooser.getCalendar().get(Calendar.YEAR);
        int mes = jDateChooser.getCalendar().get(Calendar.MONTH) + 1;
        int dia = jDateChooser.getCalendar().get(Calendar.DAY_OF_MONTH);

        return LocalDate.of(anio, mes, dia);
    }

    protected void cargarComboNotas() {
        listaNomNotas
                .stream()
                .forEach(obj -> {
                    vista.getCmbTipoNota().addItem(obj.getNombre());
                });
    }

    protected void cargarComboPeriodo() {
        listaNomPeriodos
                .stream()
                .forEach(obj -> {
                    vista.getCmbPeriodoLec().addItem(obj.getNombre_PerLectivo());
                });
    }

    protected PeriodoIngresoNotasBD setObj() {
        modelo = new PeriodoIngresoNotasBD();
        modelo.setFechaInicio(conversorFechas(vista.getJdcFechaIni()));
        modelo.setFechaCierre(conversorFechas(vista.getJdcFechaFin()));

        listaNomPeriodos
                .stream()
                .filter(item -> item.getNombre_PerLectivo().equals(vista.getCmbPeriodoLec().getSelectedItem().toString()))
                .collect(Collectors.toList())
                .forEach(obj -> {
                    modelo.setPeriodoLectivo(obj);
                });

        listaNomNotas
                .stream()
                .filter(item -> item.getNombre().equals(vista.getCmbTipoNota().getSelectedItem().toString()))
                .collect(Collectors.toList())
                .forEach(obj -> {
                    modelo.setTipoNota(obj);
                });
        return modelo;
    }

    protected void setObjinTxt() {

        vista.getJdcFechaIni().setDate(java.sql.Date.valueOf(modelo.getFechaInicio()));
        vista.getJdcFechaFin().setDate(java.sql.Date.valueOf(modelo.getFechaCierre()));
        vista.getCmbPeriodoLec().setSelectedItem(modelo.getPeriodoLectivo().getNombre_PerLectivo());
        vista.getCmbTipoNota().setSelectedItem(modelo.getTipoNota().getNombre());

    }
    //EVENTOS

    protected abstract void btnGuardar(ActionEvent e);

}
