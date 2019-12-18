package controlador.silabo.planes_de_clases;

import controlador.Libraries.abstracts.AbstractVTN;
import controlador.principal.VtnPrincipalCTR;
import modelo.PlanClases.PlandeClasesMD;
import vista.silabos.new_planes_de_clase.VtnPlanDeClases;

/**
 *
 * @author MrRainx
 */
public class VtnPlanDeClasesCTR extends AbstractVTN<VtnPlanDeClases, PlandeClasesMD> {

    public VtnPlanDeClasesCTR(VtnPrincipalCTR desktop) {
        super(desktop);
        this.vista = new VtnPlanDeClases();
    }

    @Override
    public void Init() {
        super.Init(); //To change body of generated methods, choose Tools | Templates.
    }

}
