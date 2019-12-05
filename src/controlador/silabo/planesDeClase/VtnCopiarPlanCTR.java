/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo.planesDeClase;

import controlador.Libraries.abstracts.AbstractVTN;
import controlador.principal.VtnPrincipalCTR;
import java.util.List;
import modelo.PlanClases.PlandeClasesBD;
import modelo.PlanClases.PlandeClasesMD;
import modelo.curso.CursoMD;
import vista.silabos.planesDeClase.VtnCopiarPlan;

/**
 *
 * @author MrRainx
 */
public class VtnCopiarPlanCTR extends AbstractVTN<VtnCopiarPlan, PlandeClasesMD> {

    private final PlandeClasesBD CON = PlandeClasesBD.single();
    private List<CursoMD> cursos = null;

    public VtnCopiarPlanCTR(VtnPrincipalCTR desktop) {
        super(desktop);
        vista = new VtnCopiarPlan();
    }

    @Override
    public void Init() {
        
        cursos = CON.cursosSinPlanes(modelo.getID());
        
        super.Init();

        
    }

}
