package controlador.pagos;

import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import vista.pagos.FrmComprobantes;

/**
 *
 * @author gus
 */
public class FRMComprobanteCTR extends DCTR {

    private final FrmComprobantes FRM = new FrmComprobantes();

    public FRMComprobanteCTR(VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
    }

    public void iniciar() {
        ctrPrin.agregarVtn(FRM);
    }

}
