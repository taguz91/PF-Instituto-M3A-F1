package modelo.silabo.mbd;

import java.util.List;
import modelo.materia.MateriaMD;

/**
 *
 * @author gus
 */
public interface IMateriaBD {
    
    
    public List<MateriaMD> getByUsernameCarreraPeriodoSinSilabo(
            String username, 
            int idPeriodo,
            int idCarrera
    );
    
    public List<MateriaMD> getByCarreraPersonaPeriodo(
            String carreraNombre, 
            int idPersona,
            String periodoNombre
    );
}
