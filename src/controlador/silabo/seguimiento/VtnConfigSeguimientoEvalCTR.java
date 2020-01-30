/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo.seguimiento;

import controlador.Libraries.Effects;
import controlador.Libraries.abstracts.AbstractVTN;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.curso.CursoMD;
import modelo.seguimientoSilabo.SeguimientoEvaluacionBD;
import modelo.seguimientoSilabo.SeguimientoEvaluacionMD;
import modelo.silabo.NEWCursoBD;
import modelo.silabo.NEWSilaboBD;
import modelo.silabo.SilaboMD;
import modelo.unidadSilabo.UnidadSilaboMD;
import utils.CONS;
import vista.silabos.seguimiento.VtnConfigSeguimientoEval;

/**
 *
 * @author MrRainx
 */
public class VtnConfigSeguimientoEvalCTR extends AbstractVTN<VtnConfigSeguimientoEval, SeguimientoEvaluacionMD> {

    private final NEWSilaboBD SILABO_CONN = NEWSilaboBD.single();
    private final SeguimientoEvaluacionBD CONN = SeguimientoEvaluacionBD.sigle();
    private final NEWCursoBD CURSO_CONN = NEWCursoBD.single();

    private List<SilaboMD> misSilabos = null;

    private List<CursoMD> misCursos = null;

    private List<CursoMD> cursosRef = null;

    public VtnConfigSeguimientoEvalCTR(VtnPrincipalCTR desktop) {
        super(desktop);
        this.vista = new VtnConfigSeguimientoEval();
    }

    @Override
    public void Init() {
        super.Init(); //To change body of generated methods, choose Tools | Templates.

        InitEventos();
        misSilabos = SILABO_CONN.getMisSilabosConUnidadesBy(CONS.USUARIO.getPersona().getIdentificacion());
        cargarCmbSilabos();
    }

    private void InitEventos() {

        vista.getCmbSilabo().addItemListener(this::cmbUnidades);
        vista.getCmbUnidad().addItemListener(this::cmbCursos);
        vista.getBtnSiguiente().addActionListener(this::btnSiguiente);

    }

    /*
        OPERACIONES
     */
    private void validarCmbCursos() {
        vista.getCmbCurso().removeAllItems();
        vista.getCmbCursoRef().removeAllItems();
        if (misCursos.size() < 1) {
            vista.getCmbCurso().setEnabled(false);
            vista.getCmbCurso().addItem("UNIDAD COMPLETADA");
            vista.getBtnSiguiente().setEnabled(false);
        } else {
            vista.getCmbCurso().setEnabled(true);
            vista.getBtnSiguiente().setEnabled(true);
            cursosRef = CURSO_CONN.getDeReferenciaSeguimientoEval(getUnidad().getID());
            if (cursosRef.size() < 1) {

                vista.getCmbCursoRef().addItem("NO TIENE");
                vista.getCmbCursoRef().setEnabled(false);

            } else {
                vista.getCmbCursoRef().setEnabled(true);
                vista.getCmbCursoRef().addItem("SI TIENE CURSOS DE REFERENCIA");
                cargarCursosRef();
            }
        }
    }

    private void cargarCursosRef() {
        cursosRef.stream()
                .map(c -> c.getNombre())
                .forEach(vista.getCmbCursoRef()::addItem);
    }

    private void cargarCmbSilabos() {
        misSilabos.stream()
                .map(c -> c.nombrePeriodoMateria())
                .forEach(vista.getCmbSilabo()::addItem);
    }

    private SilaboMD getSilabo() {
        return misSilabos.stream()
                .filter(item -> item.nombrePeriodoMateria().equals(vista.getCmbSilabo().getSelectedItem().toString()))
                .findFirst()
                .orElse(null);
    }

    private UnidadSilaboMD getUnidad() throws NullPointerException {
        return getSilabo()
                .getUnidades()
                .stream()
                .filter(item -> item.getNumeroUnidad() == Integer.valueOf(vista.getCmbUnidad().getSelectedItem().toString()))
                .findFirst()
                .orElse(null);
    }

    private CursoMD getCurso() {
        return misCursos.stream()
                .filter(item -> item.getNombre().equals(vista.getCmbCurso().getSelectedItem().toString()))
                .findFirst()
                .orElse(null);
    }

    private CursoMD getCursoRef() {
        return cursosRef.stream()
                .filter(item -> item.getNombre().equals(vista.getCmbCursoRef().getSelectedItem().toString()))
                .findFirst()
                .orElse(null);
    }

    /*
        EVENTOS
     */
    private void cmbUnidades(ItemEvent e) {
        vista.getCmbUnidad().removeAllItems();
        misSilabos.stream()
                .filter(item -> item.nombrePeriodoMateria().equals(vista.getCmbSilabo().getSelectedItem().toString()))
                .flatMap(c -> c.getUnidades().stream())
                .map(c -> c.getNumeroUnidad() + "")
                .forEach(vista.getCmbUnidad()::addItem);
    }

    private void cmbCursos(ItemEvent e) {
        try {

            misCursos = CURSO_CONN.getFaltantesSeguimientoEval(
                    getSilabo(),
                    getUnidad().getID()
            );
            this.validarCmbCursos();
            misCursos.stream()
                    .map(c -> c.getNombre())
                    .forEach(vista.getCmbCurso()::addItem);
        } catch (NullPointerException ex) {
        }

    }

    private void btnSiguiente(ActionEvent e) {
        new Thread(() -> {

            Effects.setLoadCursor(vista);

            FrmSeguimientoEvalCTR form = new FrmSeguimientoEvalCTR(
                    desktop,
                    getSilabo(),
                    getUnidad(),
                    getCurso()
            );

            if (vista.getCmbCursoRef().getSelectedIndex() != 0) {
                CONN.copiarSeguimientos(getCurso().getId(), getUnidad().getID(), getCursoRef().getId());
            } else {
                CONN.crearSeguimientos(getUnidad().getID(), getCurso().getId());
            }

            try {
                Thread.sleep(2500);
            } catch (InterruptedException ex) {
                Logger.getLogger(VtnConfigSeguimientoEvalCTR.class.getName()).log(Level.SEVERE, null, ex);
            }
            Effects.setDefaultCursor(vista);
            form.Init();
            vista.dispose();

        }).start();

    }
}
