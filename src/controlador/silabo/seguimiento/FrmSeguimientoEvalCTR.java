/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo.seguimiento;

import controlador.Libraries.abstracts.AbstractVTN;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.curso.CursoMD;
import modelo.evaluacionSilabo.EvaluacionSilaboMD;
import modelo.evaluacionSilabo.NEWEvaluacionSilaboBD;
import modelo.seguimientoSilabo.SeguimientoEvaluacionBD;
import modelo.seguimientoSilabo.SeguimientoEvaluacionMD;
import modelo.silabo.SilaboMD;
import modelo.unidadSilabo.UnidadSilaboMD;
import utils.CONS;
import vista.silabos.seguimiento.FrmSeguimientoEvaluacion;

/**
 *
 * @author MrRainx
 */
public class FrmSeguimientoEvalCTR extends AbstractVTN<FrmSeguimientoEvaluacion, SeguimientoEvaluacionMD> {

    private final SeguimientoEvaluacionBD CONN = SeguimientoEvaluacionBD.sigle();
    private final NEWEvaluacionSilaboBD EVAL_CONN = NEWEvaluacionSilaboBD.single();
    private EvaluacionSilaboMD evaluacion = null;

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

        this.evaluaciones = EVAL_CONN.getByUnidad(unidad.getIdUnidad(), curso.getId());

        if (this.evaluaciones.isEmpty()) {
            vista.dispose();
            JOptionPane.showMessageDialog(
                    vista,
                    "TODAVIA NO HA CREADO INFORMES PARA ESTA UNIDAD",
                    "AVISO",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }

        InitEventos();

        cargarCmbEvaluaciones();

        System.out.println("ID UNIDAD: " + unidad.getIdUnidad());
        System.out.println("ID SILABO: " + silabo.getID());
        System.out.println("ID CURSO: " + curso.getId());

    }

    private void InitEventos() {
        vista.getCmbEval().addActionListener(this::cmbEval);

        vista.getCmbFormato().addActionListener(this::cmbFormato);

        vista.getBtnSaveCont().addActionListener(this::btnSaveCont);

        vista.getBtnSaveExit().addActionListener(this::btnSaveExit);

        vista.getTxtObservacion().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                txtObservacion(e);
            }

        });

    }

    /*
        OPERACIONES
     */
    private EvaluacionSilaboMD getEvaluacion() {

        return evaluaciones.stream()
                .filter(item -> item.getSeguimientoEvaluacion().getNombreCmb().equals(vista.getCmbEval().getSelectedItem().toString()))
                .findFirst()
                .get();
    }

    private void cargarCmbEvaluaciones() {
        this.evaluaciones.stream()
                .map(c -> c.getSeguimientoEvaluacion().getNombreCmb())
                .forEach(vista.getCmbEval()::addItem);
    }


    /*
        EVENTOS
     */
    private void cmbEval(ActionEvent e) {

        evaluacion = getEvaluacion();

        vista.getTxtInfoEvaluacion().setText(evaluacion.descripcionTextArea());

        vista.getCmbFormato().setSelectedItem(
                SeguimientoEvaluacionMD.formatoToString(evaluacion.getSeguimientoEvaluacion().getFormato())
        );

        vista.getTxtObservacion().setText(
                evaluacion.getSeguimientoEvaluacion().getObservacion()
        );
    }

    private void txtObservacion(KeyEvent e) {
        evaluacion.getSeguimientoEvaluacion()
                .setObservacion(
                        vista.getTxtObservacion().getText()
                );

    }

    private void cmbFormato(ActionEvent e) {

        evaluacion.getSeguimientoEvaluacion()
                .setFormato(
                        SeguimientoEvaluacionMD.formatoToInt(vista.getCmbFormato().getSelectedItem().toString())
                );

    }

    private void btnSaveCont(ActionEvent e) {
        CONN.editar(getEvaluacion().getSeguimientoEvaluacion());
        JOptionPane.showMessageDialog(vista, "SE HA GUARDADO CORRECTAMENTE", "AVISO", JOptionPane.INFORMATION_MESSAGE);
    }

    private void btnSaveExit(ActionEvent e) {

        CONS.getPool(2).submit(() -> {

            evaluaciones
                    .parallelStream()
                    .map(c -> c.getSeguimientoEvaluacion())
                    .forEach(CONN::editar);

            JOptionPane.showMessageDialog(vista, "SE HA GUARDADO CORRECTAMENTE", "AVISO", JOptionPane.INFORMATION_MESSAGE);

        });

        CONS.THREAD_POOL.shutdown();

        vista.dispose();
    }

}
