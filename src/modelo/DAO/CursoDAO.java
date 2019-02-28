
package modelo.DAO;

import modelo.curso.CursoMD;
import java.util.List;

/**
 *
 * @author arman
 */
public interface CursoDAO {
       public void insertarCurso();
    
    public List<CursoMD>SelectAll();
    
    public List<CursoMD>SelectOne(String aguja);
    
    public void modificarCurso(String pk);
    
    public void eliminarCurso(String pk);  
}
