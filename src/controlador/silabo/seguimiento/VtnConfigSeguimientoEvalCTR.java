/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo.seguimiento;

import controlador.Libraries.abstracts.AbstractVTN;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.ItemEvent;
import java.util.List;
import modelo.seguimientoSilabo.SeguimientoEvaluacionMD;
import modelo.silabo.NEWSilaboBD;
import modelo.silabo.SilaboMD;
import utils.CONS;
import vista.silabos.seguimiento.VtnConfigSeguimientoEval;

/**
 *
 * @author MrRainx
 */
public class VtnConfigSeguimientoEvalCTR extends AbstractVTN<VtnConfigSeguimientoEval, SeguimientoEvaluacionMD> {

    private final NEWSilaboBD SILABO_CONN = NEWSilaboBD.single();

    private List<SilaboMD> misSilabos = null;

    public VtnConfigSeguimientoEvalCTR(VtnPrincipalCTR desktop) {
        super(desktop);
        this.vista = new VtnConfigSeguimientoEval();
    }

    @Override
    public void Init() {
        super.Init(); //To change body of generated methods, choose Tools | Templates.

        misSilabos = SILABO_CONN.getMisSilabosConUnidadesBy(CONS.USUARIO.getPersona().getIdentificacion());
        cargarCmbSilabos();
        InitEventos();
    }

    private void InitEventos() {

        vista.getCmbSilabo().addItemListener(this::cmbUnidades);

    }

    /*
        OPERACIONES
     */
    private void validar() {
    }

    private void cargarCmbSilabos() {
        misSilabos.stream()
                .map(c -> c.nombrePeriodoMateria())
                .forEach(vista.getCmbSilabo()::addItem);
    }


    /*
        EVENTOS
     */
    private void cmbUnidades(ItemEvent e) {
        vista.getCmbUnidad().removeAllItems();
        vista.getCmbUnidad().addItem("Unidades");

        misSilabos.stream()
                .filter(item -> item.nombrePeriodoMateria().equals(vista.getCmbSilabo().getSelectedItem().toString()))
                .flatMap(c -> c.getUnidades().stream())
                .map(c -> c.getNumeroUnidad() + "")
                .forEach(vista.getCmbUnidad()::addItem);
    }
}
