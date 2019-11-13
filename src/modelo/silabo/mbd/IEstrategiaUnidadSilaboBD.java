package modelo.silabo.mbd;

import java.util.List;
import modelo.estrategiasUnidad.EstrategiasUnidadMD;

/**
 *
 * @author gus
 */
public interface IEstrategiaUnidadSilaboBD {
    
    public int guardar(
            EstrategiasUnidadMD e, 
            int idUnidad
    );
    
    public List<EstrategiasUnidadMD> getBySilabo(int idSilabo);
    
    public List<EstrategiasUnidadMD> getBySilaboUnidad(
            int idSilabo,
            int numUnidad
    );
    
    
}
