/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo.seguimiento;

import controlador.Libraries.abstracts.AbstractVTN;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.ActionEvent;
import java.util.List;
import modelo.AvanceSilabo.SeguimientoSilaboMD;
import modelo.curso.CursoMD;
import modelo.evaluacionSilabo.EvaluacionSilaboMD;
import modelo.evaluacionSilabo.NEWEvaluacionSilaboBD;
import modelo.seguimientoSilabo.SeguimientoEvaluacionBD;
import modelo.silabo.SilaboMD;
import modelo.unidadSilabo.UnidadSilaboMD;
import vista.silabos.seguimiento.FrmSeguimientoEvaluacion;

/**
 *
 * @author MrRainx
 */
public class FrmSeguimientoEvalCTR extends AbstractVTN<FrmSeguimientoEvaluacion, SeguimientoSilaboMD> {

    private final SeguimientoEvaluacionBD CONN = SeguimientoEvaluacionBD.sigle();
    private final NEWEvaluacionSilaboBD EVAL_CONN = NEWEvaluacionSilaboBD.single();

    private final SilaboMD silabo;
    private final UnidadSilaboMD unidad;
    private final CursoMD curso;
    private List<EvaluacionSilaboMD> evaluaciones;

    public FrmSeguimientoEvalCTR(VtnPrincipalCTR desktop, SilaboMD silabo, UnidadSilaboMD unidad, CursoMD curso) {
        super(desktop);
        this.vista = new FrmSeguimientoEvaluacion();
        this.silabo = silabo;
        this.unidad = unidad;
        this.curso = curso;
    }

    /*
        INITS
     */
    @Override
    public void Init() {
        super.Init();

        vista.setTitle(
                silabo.nombrePeriodoMateria()
        );
        vista.getLblUnidad().setText(
                "UNIDAD: " + unidad.getNumeroUnidad() + ". " + unidad.getTituloUnidad() + " \t\n"
                + "CURSO: " + curso.getNombre()
        );

        this.evaluaciones = EVAL_CONN.getByUnidad(unidad.getIdUnidad());

        InitEventos();

        cargarCmbEvaluaciones();

        System.out.println("ID UNIDAD: " + unidad.getIdUnidad());
        System.out.println("ID SILABO: " + silabo.getID());
        System.out.println("ID CURSO: " + curso.getId());

    }

    private void InitEventos() {
        vista.getCmbEval().addActionListener(this::cmbEval);

    }

    /*
        OPERACIONES
     */
    private void cargarCmbEvaluaciones() {
        this.evaluaciones.stream()
                .map(c -> c.getInstrumento())
                .forEach(vista.getCmbEval()::addItem);
    }

    private void cmbEval(ActionEvent e) {
        EvaluacionSilaboMD evaluacion = this.evaluaciones.get(vista.getCmbEval().getSelectedIndex());

        vista.getTxtInfoEvaluacion().setText(evaluacion.descripcionTextArea());
    }

}
