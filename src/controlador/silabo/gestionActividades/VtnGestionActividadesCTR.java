/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo.gestionActividades;

import controlador.Libraries.abstracts.AbstractVTN;
import controlador.principal.VtnPrincipalCTR;
import java.util.List;
import modelo.evaluacionSilabo.EvaluacionSilaboMD;
import modelo.silabo.NEWSilaboBD;
import modelo.silabo.SilaboMD;
import utils.CONS;
import vista.silabos.actividades.VtnActividadesGestion;

/**
 *
 * @author MrRainx
 */
public class VtnGestionActividadesCTR extends AbstractVTN<VtnActividadesGestion, EvaluacionSilaboMD> {

    private final NEWSilaboBD SILABO_CONN = NEWSilaboBD.single();
    private final List<SilaboMD> misSilabos = SILABO_CONN.getMisSilabosBy(CONS.USUARIO.getPersona().getIdentificacion());

    public VtnGestionActividadesCTR(VtnPrincipalCTR desktop) {
        super(desktop);
        vista = new VtnActividadesGestion();
    }

    @Override
    public void Init() {
        super.Init(); //To change body of generated methods, choose Tools | Templates.
        System.out.println("HOLA MUNDO");
        setTable(vista.getTbl());

    }

}
