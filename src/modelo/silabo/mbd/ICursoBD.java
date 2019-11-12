package modelo.silabo.mbd;

import java.util.List;
import modelo.curso.CursoMD;

/**
 *
 * @author gus
 */
public interface ICursoBD {
    
    public List<CursoMD> getDocentePeriodoMateria(
            String nombreMateria,
            int idDocente,
            int idPeriodo
    );
    
    public int getNumeroAlumnos(int idCurso);
    
    public List<CursoMD> getByCurso(
            int idCurso
    );
    
}
