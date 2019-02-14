package controlador.carrera;

import vista.carrera.FrmCarrera;
import vista.carrera.VtnCarrera;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnCarreraCTR {

    private final VtnPrincipal vtnPrin;
    private final VtnCarrera vtnCarrera;

    public VtnCarreraCTR(VtnPrincipal vtnPrin, VtnCarrera vtnCarrera) {
        this.vtnPrin = vtnPrin;
        this.vtnCarrera = vtnCarrera;

        vtnPrin.getDpnlPrincipal().add(vtnCarrera);
        vtnCarrera.show();
    }

    public void iniciar() {

    }

    public void abrirFrmCarrera() {
        FrmCarrera frmCarrera = new FrmCarrera();
        FrmCarreraCTR ctrFrmCarrera = new FrmCarreraCTR(vtnPrin, frmCarrera);
        ctrFrmCarrera.iniciar();
    }

}
