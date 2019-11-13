package modelo.silabo.mbd;

import java.util.List;
import modelo.referenciasSilabo.ReferenciaSilaboMD;

/**
 *
 * @author gus
 */
public interface IReferenciaSilaboBD {
    
    public int guardar(
            int idSilabo, 
            int idReferencia
    );
    
    public List<ReferenciaSilaboMD> getBySilabo(int idSilabo);    
    
}
