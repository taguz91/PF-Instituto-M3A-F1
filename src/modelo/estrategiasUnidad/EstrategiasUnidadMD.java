package modelo.estrategiasUnidad;


import java.io.Serializable;
import java.util.UUID;
import modelo.estrategiasAprendizaje.EstrategiasAprendizajeMD;
import modelo.unidadSilabo.UnidadSilaboMD;

/**
 *
 * @author Andres Ullauri
 */
public class EstrategiasUnidadMD  implements Serializable{
    
    private final int idLocal = UUID.randomUUID().hashCode();

    private Integer idEstrategiaUnidad;
    private EstrategiasAprendizajeMD estrategia;

    private UnidadSilaboMD unidad;

    public EstrategiasUnidadMD() {
         this.estrategia = new EstrategiasAprendizajeMD();
        this.unidad = new UnidadSilaboMD();
    }

    public EstrategiasUnidadMD( EstrategiasAprendizajeMD idEstrategia, UnidadSilaboMD idUnidad) {
      
        this.estrategia = idEstrategia;
        this.unidad = idUnidad;
    }

    public Integer getIdEstrategiaUnidad() {
        return idEstrategiaUnidad;
    }

    public void setIdEstrategiaUnidad(Integer idEstrategiaUnidad) {
        this.idEstrategiaUnidad = idEstrategiaUnidad;
    }

    public EstrategiasAprendizajeMD getEstrategia() {
        return estrategia;
    }

    public void setEstrategia(EstrategiasAprendizajeMD idEstrategia) {
        this.estrategia = idEstrategia;
    }

    public UnidadSilaboMD getUnidad() {
        return unidad;
    }

    public void setUnidad(UnidadSilaboMD idUnidad) {
        this.unidad = idUnidad;
    }

    public int getIdLocal() {
        return idLocal;
    }

}
