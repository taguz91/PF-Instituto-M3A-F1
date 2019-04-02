package modelo.tipoDeNota;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.ResourceManager;
import modelo.curso.CursoMD;

/**
 *
 * @author MrRainx
 */
public class IngresoNotasBD extends IngresoNotasMD {

    public IngresoNotasBD(int idIngresoNotas, boolean notaPrimerInterCiclo, boolean notaExamenInteCiclo, boolean notaSegundoInterCicli, boolean examenFinal, boolean examenDeRecuperacion, CursoMD curso) {
        super(idIngresoNotas, notaPrimerInterCiclo, notaExamenInteCiclo, notaSegundoInterCicli, examenFinal, examenDeRecuperacion, curso);
    }

    public IngresoNotasBD() {
    }

    public boolean insertar() {
        String INSERTAR = "";

        return ResourceManager.Query(INSERTAR) == null;

    }

//    private List<IngresoNotasMD> selectAll() {
//
//        String SELECT = "";
//
//
//    }
//    
//    private List<IngresoNotasMD> selectFromView(String QUERY){
//        
//    }
}
