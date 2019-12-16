package modelo.silabo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConnDBPool;
import modelo.periodolectivo.PeriodoLectivoMD;

/**
 *
 * @author Andres Ullauri
 */
public class PeriodoLectivoBDS extends PeriodoLectivoMD {

    private static final ConnDBPool CON = ConnDBPool.single();

    private static PeriodoLectivoBDS INSTANCE;

    public static PeriodoLectivoBDS single() {
        if (INSTANCE == null) {
            INSTANCE = new PeriodoLectivoBDS();
        }

        return INSTANCE;
    }

    // Pasado 
    public List<PeriodoLectivoMD> consultarPeriodos(String carrera) {
        String SELECT = "SELECT p.id_prd_lectivo, p.prd_lectivo_nombre , p.prd_lectivo_fecha_fin\n"
                + "                    FROM \"PeriodoLectivo\" AS p\n"
                + "                   JOIN \"Carreras\" AS c ON c.id_carrera=p.id_carrera\n"
                + "                    WHERE c.carrera_nombre='" + carrera + "'  AND  p.prd_lectivo_fecha_inicio>='2019-05-27' ORDER BY p.id_prd_lectivo DESC";

        System.out.println("--->" + SELECT);

        List<PeriodoLectivoMD> periodos = new ArrayList<>();
        ResultSet rs = CON.ejecutarQuery(SELECT);
        try {

            while (rs.next()) {

                PeriodoLectivoMD tmp = new PeriodoLectivoMD();

                tmp.setID(rs.getInt(1));
                tmp.setNombre(rs.getString(2));
                tmp.setFechaFin(rs.getDate(3).toLocalDate());

                periodos.add(tmp);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PeriodoLectivoBDS.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CON.close(rs);
        }
        return periodos;

    }

}
