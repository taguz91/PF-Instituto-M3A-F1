package controlador.periodoLectivoNotas;

import com.toedter.calendar.JDateChooser;
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
    private List<PeriodoLectivoMD> listaNomPeriodos;
    private List<TipoDeNotaMD> listaNomNotas;

    public FrmIngresoNotasCTR(VtnPrincipal desktop, FrmIngresoNotas vista, PeriodoIngresoNotasBD modelo) {
        this.desktop = desktop;
        this.vista = vista;
        this.modelo = modelo;
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
    public void Llenos() {
        if (vista.getJdcFechaIni().getCalendar() != null) {
            if (vista.getJdcFechaFin().getCalendar() != null) {
            } else {
                JOptionPane.showMessageDialog(null, "Escoja una fecha");
            }
        }
    }

    public LocalDate conversorFechas(JDateChooser jDateChooser) {
        int anio = jDateChooser.getCalendar().get(Calendar.YEAR);
        int mes = jDateChooser.getCalendar().get(Calendar.MONTH);
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
                .forEach(obj ->{
                    vista.getCmbTipoNota().addItem(obj.getNombre());
                });
    }

    //EVENTOS
    private void btnGuardarActionPerformance(ActionEvent e) {
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
                .filter(item-> item.getNombre().equals(vista.getCmbTipoNota().getSelectedItem().toString()))
                .collect(Collectors.toList())
                .forEach(obj ->{
                    modelo.setIdTipoNota(obj);
                });
    }

    private void btnCancelarActionPerformance(ActionEvent e) {

        vista.dispose();

    }
}
