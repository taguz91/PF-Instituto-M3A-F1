/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo.planes_de_clases;

import controlador.Libraries.abstracts.AbstractVTN;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import modelo.PlanClases.PlandeClasesBD;
import modelo.PlanClases.PlandeClasesMD;
import modelo.curso.CursoMD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.silabo.NEWCursoBD;
import modelo.silabo.NEWPeriodoLectivoBD;
import modelo.silabo.NEWUnidadSilaboBD;
import modelo.unidadSilabo.UnidadSilaboMD;
import vista.silabos.new_planes_de_clase.FrmConfigPlan;

/**
 *
 * @author MrRainx
 */
public class FrmConfigPlanCTR extends AbstractVTN<FrmConfigPlan, PlandeClasesMD> {

    private final PlandeClasesBD CON = PlandeClasesBD.single();
    private final NEWCursoBD CURSO_CON = NEWCursoBD.single();
    private final List<PeriodoLectivoMD> periodos = NEWPeriodoLectivoBD.
            getMisPeriodosBy(
                    this.personaCONS.getIdPersona(),
                    true
            );

    private List<CursoMD> cursos;

    private List<UnidadSilaboMD> unidades;

    public FrmConfigPlanCTR(VtnPrincipalCTR desktop) {
        super(desktop);
        this.vista = new FrmConfigPlan();
    }

    @Override
    public void Init() {

        this.cargarCmbPeriodos();
        this.cargarCmbCursos();
        this.cargarCmbUnidades();
        super.Init();
        this.InitEventos();
    }

    private void InitEventos() {
        this.vista.getCmbPeriodos().addActionListener(e -> cmbPeriodos(e));
        this.vista.getCmbCursos().addActionListener(e -> cmbCursos(e));

        this.vista.getBtnSiguiente().addActionListener(this::btnSiguiente);

    }

    //FACTORIZACION
    private void cargarCmbPeriodos() {
        this.periodos.stream()
                .map(c -> c.getNombre())
                .forEach(this.vista.getCmbPeriodos()::addItem);
    }

    private void cargarCmbCursos() {
        this.vista.getCmbCursos().removeAllItems();

        this.cursos = CURSO_CON.getCursosMateriasBy(
                this.vista.getCmbPeriodos().getSelectedItem().toString(),
                this.personaCONS.getIdPersona()
        );
        this.cursos.stream()
                .map(c -> c.getCursoMateriaNombre())
                .forEach(this.vista.getCmbCursos()::addItem);
    }

    private void cargarCmbUnidades() throws NullPointerException {
        this.vista.getCmbUnidades().removeAllItems();
        this.unidades = NEWUnidadSilaboBD.getUnidadesBy(
                getPeriodo().getID(),
                getCurso().getMateria().getId(),
                getCurso().getId()
        );

        if (this.unidades.size() > 0) {
            this.vista.getBtnSiguiente().setEnabled(true);
        } else {
            this.vista.getBtnSiguiente().setEnabled(false);
        }

        this.unidades.stream()
                .map(c -> String.valueOf(c.getNumeroUnidad()))
                .forEach(this.vista.getCmbUnidades()::addItem);
    }

    private PeriodoLectivoMD getPeriodo() {
        return periodos.stream()
                .filter(item -> item.getNombre().equals(this.vista.getCmbPeriodos().getSelectedItem().toString()))
                .findFirst()
                .get();
    }

    private CursoMD getCurso() {
        return cursos.stream()
                .filter(item -> item.getCursoMateriaNombre().equals(this.vista.getCmbCursos().getSelectedItem().toString()))
                .findFirst()
                .get();
    }

    private UnidadSilaboMD getUnidad() {
        return unidades.stream()
                .filter(item -> item.getNumeroUnidad() == Integer.parseInt(this.vista.getCmbUnidades().getSelectedItem().toString()))
                .findFirst()
                .get();
    }

    //EVENTOS
    private void btnSiguiente(ActionEvent e) {

        PlandeClasesMD plandeClasesMD = new PlandeClasesMD();

        plandeClasesMD.setCurso(getCurso());
        plandeClasesMD.setUnidad(getUnidad());

        plandeClasesMD.setRecursos(new ArrayList<>());
        plandeClasesMD.setEstrategias(new ArrayList<>());

        FrmPlanDeClasesCTR form = new FrmPlanDeClasesCTR(desktop);

        form.setModelo(plandeClasesMD);
        form.setAccion("add");
        form.Init();

        this.vista.dispose();

    }

    private void cmbPeriodos(ActionEvent e) {

        if (this.periodos.size() > 0) {
            cargarCmbCursos();
        }

    }

    private void cmbCursos(ActionEvent e) {

        if (this.cursos.size() > 0) {
            try {
                cargarCmbUnidades();
            } catch (NullPointerException ex) {
            }
        }

    }
}
