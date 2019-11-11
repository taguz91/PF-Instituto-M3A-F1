package controlador.silabo;

import modelo.ConexionBD;
import modelo.silabo.SilaboMD;
import vista.principal.VtnPrincipal;
import vista.silabos.frmGestionSilabo;

/**
 *
 * @author MrRainx, Johnny
 */
public abstract class AbstractSilaboCTR {

    protected SilaboMD modelo;
    protected frmGestionSilabo vista;
    protected VtnPrincipal vtnPrincipal;
    protected ConexionBD conexion;

    public AbstractSilaboCTR(SilaboMD modelo, VtnPrincipal vtnPrincipal, ConexionBD conexion) {
        if (modelo != null) {
            this.modelo = modelo;
        } else {
            this.modelo = modelo;
        }
        this.vista = new frmGestionSilabo();
        this.vtnPrincipal = vtnPrincipal;
        this.conexion = conexion;
    }

}
