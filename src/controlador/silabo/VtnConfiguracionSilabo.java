/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo;

import vista.principal.VtnPrincipal;
import vista.silabos.frmConfiguracionSilabo;

/**
 *
 * @author MrRainx
 */
public class VtnConfiguracionSilabo {

    private frmConfiguracionSilabo vista;
    private VtnPrincipal desktop;

    public VtnConfiguracionSilabo(VtnPrincipal desktop) {
        this.desktop = desktop;
        this.vista = new frmConfiguracionSilabo();
    }

    public void Init() {
    }

}
