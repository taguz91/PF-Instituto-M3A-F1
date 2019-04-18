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
import java.awt.event.MouseAdapter;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import modelo.ConectarDB;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import modelo.validaciones.Validar;
import vista.docente.VtnFinContratacion;
import vista.docente.VtnPeriodosDocente;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Lina
 */
public class VtnFinContratacionCTR {

    private final ConectarDB conecta;
    private PeriodoLectivoBD plBD;
    private final VtnFinContratacion frmFinContrato;
    private final VtnPrincipal vtnPrin;
    private DocenteBD dc;
    private DocenteMD docenteMD;
    private final String cedula;
    private boolean guardar = true;

    public VtnFinContratacionCTR(
            ConectarDB conecta, VtnPrincipal vtnPrin, String cedula) {
        this.frmFinContrato = new VtnFinContratacion(vtnPrin, false);
        this.dc = new DocenteBD(conecta);
        this.conecta = conecta;
        this.vtnPrin = vtnPrin;
        this.cedula = cedula;
        this.frmFinContrato.setLocationRelativeTo(vtnPrin);
        this.frmFinContrato.setVisible(true);
        this.frmFinContrato.setTitle("Fin de Contrato");

    }

    public void iniciar() {
        frmFinContrato.getLblErrorFechaFinContratacion().setVisible(false);
        frmFinContrato.getLblErrorObservacion().setVisible(false);
        frmFinContrato.getBtnGuardar().setEnabled(false);
        frmFinContrato.getBtnGuardar().addActionListener(e -> guardarFinContratacion());
        frmFinContrato.getBtnCancelar().addActionListener(e -> salir());

        frmFinContrato.getTxtObservacion().addKeyListener(new KeyAdapter() {

            public void keyReleased(KeyEvent e) {
                String Observacion = frmFinContrato.getTxtObservacion().getText();
                if (!Validar.esObservacion(Observacion)) {

                    frmFinContrato.getLblErrorObservacion().setVisible(true);

                } else {
                    frmFinContrato.getLblErrorObservacion().setVisible(false);
                }
                habilitarGuardar();
            }
        });
        
        frmFinContrato.getJdcFinContratacion().addMouseListener(new MouseAdapter(){
            public void mouseClicked(){
                if(validarFecha() == true){
                    frmFinContrato.getLblErrorFechaFinContratacion().setVisible(false);
                } else{
                    frmFinContrato.getLblErrorFechaFinContratacion().setText("El inicio de contrato no puede ser \n mayor al de finalizacion");
                    frmFinContrato.getLblErrorFechaFinContratacion().setVisible(true);
                }
                habilitarGuardar();
            }
        });
        
        docenteMD = dc.buscarDocente(cedula);
    }

    public void habilitarGuardar() {
        String observacion;
        observacion = frmFinContrato.getTxtObservacion().getText();
        Date fecha = frmFinContrato.getJdcFinContratacion().getDate();
        if (observacion.equals("") == false || fecha != null) {
            if (frmFinContrato.getLblErrorFechaFinContratacion().isVisible() == true
                    || frmFinContrato.getLblErrorObservacion().isVisible() == true) {
                frmFinContrato.getBtnGuardar().setEnabled(false);
            } else {
                frmFinContrato.getBtnGuardar().setEnabled(true);
            }
        } else {
            frmFinContrato.getBtnGuardar().setEnabled(false);
        }

    }

    private void guardarFinContratacion() {

        String Observacion, fechaFinContra;
        Date fecha;

        Observacion = frmFinContrato.getTxtObservacion().getText().trim().toUpperCase();
        fecha = frmFinContrato.getJdcFinContratacion().getDate();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        fechaFinContra = sdf.format(fecha);

//        if (docentesMD.getFechaInicioContratacion().isAfter(convertirDate(fecha)) == false
//                && docentesMD.getFechaInicioContratacion().isEqual(convertirDate(fecha)) == false) {
//            guardar = true;
//            vtnFinContratacion.getLblErrorFechaFinContratacion().setVisible(false);
//        } else {
//            guardar = false;
//            vtnFinContratacion.getLblErrorFechaFinContratacion().setText("La fecha de inicio de contrato no puede ser mayor a la de finalizacion");
//            vtnFinContratacion.getLblErrorFechaFinContratacion().setVisible(true);
//        }
        validarFecha();
        System.out.println("DIA " + fechaFinContra);

        if (guardar == true) {
//            DocenteMD docente = new DocenteMD();
//            docente.setObservacion(Observacion);
//            docente.setFechaFinContratacion(convertirDate(fecha));
            

            VtnPeriodosDocenteCTR vtnPeriodoDocenteCTR = new VtnPeriodosDocenteCTR(conecta, vtnPrin, docenteMD);
            vtnPeriodoDocenteCTR.iniciarPeriodosDocente();
            frmFinContrato.dispose();
        }

    }

    public boolean validarFecha() {
        Date fecha;
        fecha = frmFinContrato.getJdcFinContratacion().getDate();

        if (docenteMD.getFechaInicioContratacion().isAfter(convertirDate(fecha)) == false
                && docenteMD.getFechaInicioContratacion().isEqual(convertirDate(fecha)) == false) {

            
            return true;
        } else {
            
            return false;
        }

    }

    private void salir() {
        frmFinContrato.dispose();
        System.out.println("Se dio click en cancelar Fin contratacion");
    }

    public LocalDate convertirDate(Date fecha) {
        return Instant.ofEpochMilli(fecha.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
