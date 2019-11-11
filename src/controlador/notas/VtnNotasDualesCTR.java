/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.notas;

import controlador.Libraries.abstracts.AbstractVTN;
import controlador.principal.VtnPrincipalCTR;
import modelo.notas.NotasMD;
import vista.notas.VtnNotasDuales;

/**
 *
 * @author MrRainx
 */
public class VtnNotasDualesCTR extends AbstractVTN<VtnNotasDuales, NotasMD> {

    public VtnNotasDualesCTR(VtnPrincipalCTR desktop) {
        super(desktop);
    }

    public void Init() {
    }

}
