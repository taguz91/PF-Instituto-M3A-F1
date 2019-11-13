package modelo.silabo.mbd;

import java.util.List;
import modelo.unidadSilabo.UnidadSilaboMD;

/**
 *
 * @author gus
 */
public interface IUnidadSilaboBD {
    
    public List<UnidadSilaboMD> getBySilabo(int idSilabo);
    
    public List<UnidadSilaboMD> getBySilaboParaReferencia(int idSilabo);
    
    public List<UnidadSilaboMD> getBySilaboUnidad(
            int idSilabo,
            int numUnidad
    );
    
    public List<UnidadSilaboMD> getForPlanClaseBySilabo(
            int idSilabo
    );
    
    public int guardar(UnidadSilaboMD u, int idSilabo);
}
