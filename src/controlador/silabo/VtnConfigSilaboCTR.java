/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo;

import controlador.Libraries.abstracts.AbstractVTN;
import controlador.principal.VtnPrincipalCTR;
import java.util.List;
import modelo.carrera.CarreraMD;
import modelo.silabo.SilaboMD;
import vista.silabos.VtnConfigSilabo;

/**
 *
 * @author MrRainx
 */
public class VtnConfigSilaboCTR extends AbstractVTN<VtnConfigSilabo, SilaboMD> {

    private List<CarreraMD> carreras;

    public VtnConfigSilaboCTR(VtnPrincipalCTR desktop) {
        super(desktop);
        vista = new VtnConfigSilabo();
    }

    /*
        METODOS
     */
    private void cargarCmbCarreras() {

    }
}
