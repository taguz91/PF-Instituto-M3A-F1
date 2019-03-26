package controlador.periodoLectivoNotas;

import com.toedter.calendar.JDateChooser;
import controlador.Libraries.Effects;
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
 * @author USUARIO
 */
public class FrmIngresoNotasCTR {

    private VtnPrincipal desktop;
    private FrmIngresoNotas vista;
    private PeriodoIngresoNotasBD modelo;

    private VtnPeriodoIngresoNotasCTR vtnPadre;

    private List<PeriodoLectivoMD> listaNomPeriodos;
    private List<TipoDeNotaMD> listaNomNotas;

    public FrmIngresoNotasCTR(VtnPrincipal desktop, FrmIngresoNotas vista, PeriodoIngresoNotasBD modelo, VtnPeriodoIngresoNotasCTR vtnPadre) {
        this.desktop = desktop;
        this.vista = vista;
        this.modelo = modelo;
        this.vtnPadre = vtnPadre;
    }

    //INICIADORES
    public void Init() {

        listaNomPeriodos = PeriodoLectivoBD.SelectAll();
        listaNomNotas = TipoDeNotaBD.SelectAll();

        InitEventos();
        try {
            desktop.getDpnlPrincipal().add(vista);
            vista.setSelected(true);
            vista.show();
        } catch (PropertyVetoException ex) {
            Logger.getLogger(FrmIngresoNotasCTR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void InitEventos() {
        vista.getBtnGuardar().addActionListener(e -> btnGuardarActionPerformance(e));
        vista.getBtnCancelar().addActionListener(e -> btnCancelarActionPerformance(e));
    }

    //METODOS DE APOYO
    private boolean validacion() {
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

    public LocalDate conversorFechas(JDateChooser jDateChooser) {
        int anio = jDateChooser.getCalendar().get(Calendar.YEAR);
        int mes = jDateChooser.getCalendar().get(Calendar.MONTH) + 1;
        int dia = jDateChooser.getCalendar().get(Calendar.DAY_OF_MONTH);

        return LocalDate.of(anio, mes, dia);
    }

    public void cargarCombos() {
        listaNomPeriodos
                .stream()
                .forEach(obj -> {
                    vista.getCmbPeriodoLec().addItem(obj.getNombre_PerLectivo());
                });

        listaNomNotas
                .stream()
                .forEach(obj -> {
                    vista.getCmbTipoNota().addItem(obj.getNombre());
                });
    }

    private void setInforEnModelo() {
        modelo = new PeriodoIngresoNotasBD();
        modelo.setFechaInicio(conversorFechas(vista.getJdcFechaIni()));
        modelo.setFechaCierre(conversorFechas(vista.getJdcFechaFin()));

        listaNomPeriodos
                .stream()
                .filter(item -> item.getNombre_PerLectivo().equals(vista.getCmbPeriodoLec().getSelectedItem().toString()))
                .collect(Collectors.toList())
                .forEach(obj -> {
                    modelo.setIdPeriodoLectivo(obj);
                });

        listaNomNotas
                .stream()
                .filter(item -> item.getNombre().equals(vista.getCmbTipoNota().getSelectedItem().toString()))
                .collect(Collectors.toList())
                .forEach(obj -> {
                    modelo.setIdTipoNota(obj);
                });
    }

    private void agregar() {
        if (modelo.insertar()) {
            JOptionPane.showMessageDialog(vista, "SE HA AGREGADO EL PERIODO DE INGRESO DE NOTAS");
            Effects.setTextInLabel(desktop.getLblEstado(), "SE HA AGREGADO EL PERIODO DE INGRESO DE NOTAS", 2);
            vista.dispose();
        } else {
            JOptionPane.showMessageDialog(vista, "HA OCURRIDO UN ERROR");
        }
    }

    //EVENTOS
    private void btnGuardarActionPerformance(ActionEvent e) {

        if (validacion()) {
            setInforEnModelo();
            agregar();
        }

    }

    private void btnCancelarActionPerformance(ActionEvent e) {

        vista.dispose();

    }
}
