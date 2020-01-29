/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo.planes_de_clases;

import controlador.Libraries.abstracts.AbstractVTN;
import controlador.principal.VtnPrincipalCTR;
import java.util.List;
import modelo.PlanClases.PlandeClasesBD;
import modelo.PlanClases.PlandeClasesMD;
import modelo.curso.CursoMD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.silabo.NEWCursoBD;
import modelo.silabo.NEWPeriodoLectivoBD;
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

    public FrmConfigPlanCTR(VtnPrincipalCTR desktop) {
        super(desktop);
        this.vista = new FrmConfigPlan();
    }

    @Override
    public void Init() {

        this.cargarCmbPeriodos();
        this.cargarCmbCursos();
        super.Init();
        this.InitEventos();
    }

    private void InitEventos() {
        this.vista.getCmbPeriodos().addActionListener(e -> cargarCmbCursos());
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

    //EVENTOS
}
