/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo;

import modelo.silabo.SilaboBD;
import vista.principal.VtnPrincipal;
import vista.silabos.frmGestionSilabo;
import vista.silabos.frmSilabos;

/**
 *
 * @author Andres Ullauri
 */
public class ControladorSilaboU {

    private SilaboBD silabo;

    private frmGestionSilabo gestion;

    private VtnPrincipal principal;

    public ControladorSilaboU(SilaboBD silabo, VtnPrincipal principal) {
        this.silabo = silabo;
        this.principal = principal;
    }

    public void iniciarControlador() {
        gestion = new frmGestionSilabo();

        principal.getDpnlPrincipal().add(gestion);

        gestion.setTitle("Nombre de la Materia");

        gestion.show();

        gestion.setLocation((principal.getDpnlPrincipal().getSize().width - gestion.getSize().width) / 2,
                (principal.getDpnlPrincipal().getSize().height - gestion.getSize().height) / 2);

    }
}
