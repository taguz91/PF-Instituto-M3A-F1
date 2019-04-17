package controlador.principal;

import modelo.ConectarDB;
import vista.principal.VtnPrincipal;

/**
 * En esta clase sera la padre de CTR aqui agregaremos dependencias, 
 * ya sean ventanas, ctroladores que se necesiten en todos los formularios, o no
 * pero controlando los que no son necesarios
 * @author Johnny
 */
public class DependenciasCTR {
    public final ConectarDB conecta;
    public final VtnPrincipal vtnPrin;
    public final VtnPrincipalCTR ctrPrin;

    public DependenciasCTR(ConectarDB conecta, VtnPrincipal vtnPrin, VtnPrincipalCTR ctrPrin) {
        this.conecta = conecta;
        this.vtnPrin = vtnPrin;
        this.ctrPrin = ctrPrin;
    }
    
    
}
