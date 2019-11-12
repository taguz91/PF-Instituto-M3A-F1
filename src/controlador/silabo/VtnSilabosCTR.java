package controlador.silabo;

import controlador.Libraries.abstracts.AbstractVTN;
import controlador.principal.VtnPrincipalCTR;
import java.util.List;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.silabo.SilaboMD;
import vista.silabos.VtnSilabos;

/**
 *
 * @author MrRainx
 */
public class VtnSilabosCTR extends AbstractVTN<VtnSilabos, SilaboMD> {

    private List<PeriodoLectivoMD> periodos;

    public VtnSilabosCTR(VtnPrincipalCTR desktop) {
        super(desktop);
    }

    @Override
    public void Init() {
        vista = new VtnSilabos();
        modelo = new SilaboMD();
        super.Init();

    }

}
