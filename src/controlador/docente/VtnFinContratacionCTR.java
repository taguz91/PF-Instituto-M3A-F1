/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.docente;

import controlador.persona.VtnDocenteCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import modelo.ConectarDB;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import modelo.validaciones.Validar;
import vista.docente.VtnFinContratacion;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Lina
 */
public class VtnFinContratacionCTR {

    private final ConectarDB conecta;
    private PeriodoLectivoBD plBD;
    private final VtnFinContratacion vtnFinContratacion;
    private final VtnPrincipal vtnPrin;
    private static LocalDate fechaInicio;
    private boolean guardar = false;

    public VtnFinContratacionCTR(VtnFinContratacion vtnFinContratacion,
        ConectarDB conecta, VtnPrincipal vtnPrin, LocalDate fechaInicio) {
        this.vtnFinContratacion = vtnFinContratacion;
        this.conecta = conecta;
        this.vtnPrin = vtnPrin;
        this.fechaInicio = fechaInicio;
        vtnPrin.getDpnlPrincipal().add(vtnFinContratacion);
        vtnFinContratacion.setLocationRelativeTo(vtnPrin);
        vtnFinContratacion.setVisible(true);
        vtnFinContratacion.setTitle("Fin de Contrato");

    }

    public void iniciar() {
        vtnFinContratacion.getLblErrorFechaFinContratacion().setVisible(false);
        vtnFinContratacion.getLblErrorObservacion().setVisible(false);
        vtnFinContratacion.getBtnGuardar().setEnabled(false);
        vtnFinContratacion.getBtnGuardar().addActionListener(e -> guardarFinContratacion());
        vtnFinContratacion.getBtnCancelar().addActionListener(e -> salir());

        vtnFinContratacion.getTxtObservacion().addKeyListener(new KeyAdapter() {

            public void keyReleased(KeyEvent e) {
                String Observacion = vtnFinContratacion.getTxtObservacion().getText();
                if (!Validar.esObservacion(Observacion)) {

                    vtnFinContratacion.getLblErrorObservacion().setVisible(true);

                } else {
                    vtnFinContratacion.getLblErrorObservacion().setVisible(false);
                }
                habilitarGuardar();
            }
        });
    }

    public void habilitarGuardar() {
        String observacion;
        observacion = vtnFinContratacion.getTxtObservacion().getText();
        Date fecha = vtnFinContratacion.getJdcFinContratacion().getDate();
        if (observacion.equals("") == false || fecha == null) {
            if (vtnFinContratacion.getLblErrorFechaFinContratacion().isVisible() == true
                    || vtnFinContratacion.getLblErrorObservacion().isVisible() == true) {
                vtnFinContratacion.getBtnGuardar().setEnabled(false);
            } else {
                vtnFinContratacion.getBtnGuardar().setEnabled(true);
            }
        } else {
            vtnFinContratacion.getBtnGuardar().setEnabled(false);
        }

    }

    private void guardarFinContratacion() {

        String Observacion, fechaFinContra;

        Date fecha;

        Observacion = vtnFinContratacion.getTxtObservacion().getText().trim().toUpperCase();

        fecha = vtnFinContratacion.getJdcFinContratacion().getDate();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        //Se lo pasa a un string para poder validarlo
        fechaFinContra = sdf.format(fecha);
        
        if(fechaInicio.isAfter(convertirDate(fecha)) == false && 
                fechaInicio.isEqual(convertirDate(fecha)) == false){
            guardar = true;
            vtnFinContratacion.getLblErrorFechaFinContratacion().setVisible(false);
        } else{
            guardar = false;
            vtnFinContratacion.getLblErrorFechaFinContratacion().setText("La fecha de inicio de contrato no puede ser mayor a la de finalizacion");
            vtnFinContratacion.getLblErrorFechaFinContratacion().setVisible(true);
        }

        System.out.println("DIA " + fechaFinContra);

        if(guardar == true){
            DocenteMD docente = new DocenteMD();
            docente.setObservacion(Observacion);
            docente.setFechaFinContratacion(convertirDate(fecha));
        }
        
    }

    private void salir() {
        vtnFinContratacion.dispose();
        System.out.println("Se dio click en cancelar Fin contratacion");
    }

    public LocalDate convertirDate(Date fecha) {
        return Instant.ofEpochMilli(fecha.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
