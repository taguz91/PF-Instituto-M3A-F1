package modelo.silabo.mbd;

import java.util.List;
import modelo.periodolectivo.PeriodoLectivoMD;

/**
 *
 * @author gus
 */
public interface IPeriodoLectivoBD {
    
    public List<PeriodoLectivoMD> getByCarrera(int idCarrera);
    
    public List<PeriodoLectivoMD> getByCarrera(String nombre);
    
}
