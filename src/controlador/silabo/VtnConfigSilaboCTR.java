/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo;

import controlador.Libraries.abstracts.AbstractVTN;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.List;
import modelo.CONS;
import modelo.carrera.CarreraMD;
import modelo.materia.MateriaMD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.silabo.NEWCarreraBD;
import modelo.silabo.SilaboMD;
import vista.silabos.VtnConfigSilabo;

/**
 *
 * @author MrRainx
 */
public class VtnConfigSilaboCTR extends AbstractVTN<VtnConfigSilabo, SilaboMD> {

    private final List<CarreraMD> carreras = NEWCarreraBD.single().getByUsername(CONS.USUARIO.getUsername());
    private List<MateriaMD> materias;
    private List<PeriodoLectivoMD> periodos;

    public VtnConfigSilaboCTR(VtnPrincipalCTR desktop) {
        super(desktop);
        vista = new VtnConfigSilabo();
    }

    @Override
    public void Init() {
        super.Init(); //To change body of generated methods, choose Tools | Templates.
        cargarCmbCarreras();
        InitEventos();
    }

    private void InitEventos() {
        vista.getCmbCarrera().addItemListener(this::cmbCarrera);
        vista.getCmbAsignatura().addItemListener(this::cmbAsignatura);
        vista.getCmbPeriodoRef().addItemListener(this::cmbPeriodo);
        vista.getBtnSiguiente().addActionListener(this::btnSiguiente);
        vista.getBtnCancelar().addActionListener(this::btnCancelar);
    }

    /*
        METODOS
     */
    private void cargarCmbCarreras() {
        carreras.stream()
                .map(c -> c.getNombre())
                .forEach(vista.getCmbCarrera()::addItem);
    }

    private void cargarCmMaterias() {

    }

    private void cargarCmbPeriodosRef() {

    }

    /*
        EVENTOS
     */
    private void cmbCarrera(ItemEvent e) {

    }

    private void cmbAsignatura(ItemEvent e) {

    }

    private void cmbPeriodo(ItemEvent e) {

    }

    private void btnSiguiente(ActionEvent e) {

    }

    private void btnCancelar(ActionEvent e) {
        vista.dispose();
        VtnSilabosCTR vtn = new VtnSilabosCTR(desktop);
        vtn.Init();
    }
}
