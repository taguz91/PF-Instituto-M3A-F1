package modelo.estrategiasAprendizaje;

import java.util.List;

/**
 *
 * @author gus
 */
public interface IEstrategiaAprendizajeBD {
    
    public int guardar(EstrategiasAprendizajeMD estrategia);
    
    public List<EstrategiasAprendizajeMD> getAll();
    
    public List<EstrategiasAprendizajeMD> getByDescripcion(String descripcion);
    
}
