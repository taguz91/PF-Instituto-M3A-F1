/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo.seguimiento;

import controlador.Libraries.abstracts.AbstractVTN;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.List;
import java.util.function.Consumer;
import modelo.evaluacionSilabo.EvaluacionSilaboMD;
import modelo.seguimientoSilabo.SeguimientoEvaluacionMD;
import modelo.silabo.NEWSilaboBD;
import modelo.silabo.SilaboMD;
import utils.CONS;
import vista.silabos.seguimiento.VtnSeguimientoEvaluacion;

/**
 *
 * @author MrRainx
 */
public class VtnSeguimientoEvaluacionCTR extends AbstractVTN<VtnSeguimientoEvaluacion, EvaluacionSilaboMD> {

    private final NEWSilaboBD SILABO_CONN = NEWSilaboBD.single();
    private List<SilaboMD> misSilabos = null;

    public VtnSeguimientoEvaluacionCTR(VtnPrincipalCTR desktop) {
        super(desktop);
        vista = new VtnSeguimientoEvaluacion();
    }

    @Override
    public void Init() {

        // Gestion de Actividades de la Unidad
        super.Init(); //To change body of generated methods, choose Tools | Templates.
        System.out.println("HOLA MUNDO");
        setTable(vista.getTbl());
        new Thread(() -> {
            misSilabos = SILABO_CONN.getMisSilabosConUnidadesBy(CONS.USUARIO.getPersona().getIdentificacion());
            cargarCmbSilabos();
        }).start();

        InitEventos();

    }

    private void InitEventos() {

        vista.getCmbSilabo().addItemListener(this::cmbUnidades);
        vista.getBtnAgregar().addActionListener(this::btnNuevo);
    }

    /*
            OPERACIONES
     */
    public Consumer<SeguimientoEvaluacionMD> cargador() {

        return obj -> {

        };
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

    private void btnNuevo(ActionEvent e) {
        VtnConfigSeguimientoEvalCTR vtn = new VtnConfigSeguimientoEvalCTR(desktop);
        vtn.Init();

    }
}
