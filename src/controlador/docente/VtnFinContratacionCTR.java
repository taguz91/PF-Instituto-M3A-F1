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
    private final VtnFinContratacion vtnFinContratacion;
    private final VtnPrincipal vtnPrin;
    private DocenteBD docente;
    private DocenteMD docentesMD;
    private final String cedula;
    private boolean guardar = true;

    public VtnFinContratacionCTR(
            ConectarDB conecta, VtnPrincipal vtnPrin, String cedula) {
        this.vtnFinContratacion = new VtnFinContratacion(vtnPrin, false);
        this.conecta = conecta;
        this.vtnPrin = vtnPrin;
        this.cedula = cedula;
        this.vtnFinContratacion.setLocationRelativeTo(vtnPrin);
        this.vtnFinContratacion.setVisible(true);
        this.vtnFinContratacion.setTitle("Fin de Contrato");

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
        
        vtnFinContratacion.getJdcFinContratacion().addMouseListener(new MouseAdapter(){
            public void mouseClicked(){
                if(validarFecha() == true){
                    vtnFinContratacion.getLblErrorFechaFinContratacion().setVisible(false);
                } else{
                    vtnFinContratacion.getLblErrorFechaFinContratacion().setText("El inicio de contrato no puede ser \n mayor al de finalizacion");
                    vtnFinContratacion.getLblErrorFechaFinContratacion().setVisible(true);
                }
                habilitarGuardar();
            }
        });
        docente = new DocenteBD(conecta);
        docentesMD = docente.buscarDocente(cedula);
    }

    public void habilitarGuardar() {
        String observacion;
        observacion = vtnFinContratacion.getTxtObservacion().getText();
        Date fecha = vtnFinContratacion.getJdcFinContratacion().getDate();
        if (observacion.equals("") == false || fecha != null) {
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
            

            VtnPeriodosDocenteCTR vtnPeriodoDocenteCTR = new VtnPeriodosDocenteCTR(conecta, vtnPrin, docentesMD);
            vtnPeriodoDocenteCTR.iniciarPeriodosDocente();
            vtnFinContratacion.dispose();
        }

    }

    public boolean validarFecha() {
        Date fecha;
        fecha = vtnFinContratacion.getJdcFinContratacion().getDate();

        if (docentesMD.getFechaInicioContratacion().isAfter(convertirDate(fecha)) == false
                && docentesMD.getFechaInicioContratacion().isEqual(convertirDate(fecha)) == false) {

            
            return true;
        } else {
            
            return false;
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
