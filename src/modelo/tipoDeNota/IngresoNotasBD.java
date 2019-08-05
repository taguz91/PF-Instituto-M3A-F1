package modelo.tipoDeNota;

import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDate;
import modelo.ConnDBPool;
import modelo.curso.CursoBD;

/**
 *
 * @author MrRainx
 */
public class IngresoNotasBD extends IngresoNotasMD {

    private ConnDBPool pool;
    private Connection conn;
    private ResultSet rs;

    {
        pool = new ConnDBPool();
    }

    public IngresoNotasBD(int id, LocalDate fechaInicio, LocalDate fechaCierre, LocalDate fechaCierreExtendido, boolean estado, TipoDeNotaBD tipoNota, CursoBD curso) {
        super(id, fechaInicio, fechaCierre, fechaCierreExtendido, estado, tipoNota, curso);
    }

    public IngresoNotasBD() {
    }

    private boolean insertar() {
        return false;
    }

}
