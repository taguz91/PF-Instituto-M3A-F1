
package modelo.asistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modelo.ConnDBPool;
import modelo.alumno.AlumnoCursoMD;

/**
 *
 * @author Yani
 */
public class AsistenciaBD extends AsistenciaMD {

    private ConnDBPool pool;
    private Connection conn;
    private ResultSet rst;

    {
        pool = new ConnDBPool();
    }

    public AsistenciaBD(int id, LocalDate fechaAsistencia, int numeroFaltas, String observaciones, AlumnoCursoMD alumnoCurso) {
        super(id, fechaAsistencia, numeroFaltas, observaciones, alumnoCurso);
    }

    public AsistenciaBD() {
    }

    public List<AsistenciaBD> selectWhere(int idDocente, int idperiodoLectivo, String materiaNombre, String cursoNombre) {
        String SELECT = "SELECT \"Asistencia\".id_asistencia, \"Asistencia\".fecha_asistencia, \"              Asistencia\".numero_faltas\n" +
        "FROM \"Asistencia\"\n" +
        "WHERE\n" +
        "\"Asistencia\".id_almn_curso = 1";

        List<AsistenciaBD> listaAsistencia = new ArrayList<>();
        Map<Integer, Object> parametrosAsis = new HashMap<>();
        parametrosAsis.put(1, idDocente);
        parametrosAsis.put(2, idperiodoLectivo);
        parametrosAsis.put(3, cursoNombre);
        parametrosAsis.put(4, materiaNombre);
        parametrosAsis.put(5, getFechaAsistencia());

        try {
            conn = pool.getConnection();
            rst = pool.ejecutarQuery(SELECT, conn, parametrosAsis);
            System.out.println(pool.getStmt().toString());
            while (rst.next()) {
                AsistenciaBD asistencia = new AsistenciaBD();

                asistencia.setId(rst.getInt(1));
                asistencia.setFechaAsistencia(rst.getDate(2).toLocalDate());
                asistencia.setNumeroFaltas(rst.getInt(3));
                asistencia.setObservaciones(rst.getString(4));

                listaAsistencia.add(asistencia);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            pool.closeStmt().close(rst).close(conn);
        }

        return listaAsistencia;
    }
    

    private boolean ejecutarAsis = false;

    public synchronized boolean editar() {
        new Thread(() -> {
            String UPDATE = "UPDATE \"Asistencia\" \n"
                    + "SET id_almn_curso = " + getAlumnoCurso() + ", \n"
                    + "fecha_asistencia = "+getFechaAsistencia()+ ", \n"
                    + "numero_faltas = "+getNumeroFaltas()+", \n"
                    + "WHERE \n"
                    + "\"public\".\"Asistencia\".id_almn_curso = " + getId()+" \n" 
                    + "\"Asistencia\".fecha_asistencia = "+getFechaAsistencia();
                    
            System.out.println(UPDATE);
            conn = pool.getConnection();
            ejecutarAsis = pool.ejecutar(UPDATE, conn, null) == null;
        }).start();

        return ejecutarAsis;
    }
}
