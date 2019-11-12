package modelo.silabo.mbd;

import java.util.List;
import modelo.carrera.CarreraMD;

/**
 *
 * @author gus
 */
public interface ICarreraBD {
    
    public List<CarreraMD> getByUsername(String username);
    
    public String getModalidadByCurso(int idCurso);
    
    public CarreraMD getByCoordinador(String username);
    
}
