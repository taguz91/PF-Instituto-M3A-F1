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
        String SELECT = "SELECT \"Asistencia\".id_asistencia, \"Asistencia\".fecha_asistencia, \"              Asistencia\".numero_faltas\n"
                + "FROM \"Asistencia\"\n"
                + "WHERE\n"
                + "\"Asistencia\".id_almn_curso = 1";

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

    public synchronized boolean editar(int id_almn_curso, String fecha, int falta) {
        new Thread(() -> {
            String UPDATE = " UPDATE public.\"Asistencia\" \n"
                    + "  SET numero_faltas = " + falta + " \n"
                    + " WHERE \n"
                    + " public.\"Asistencia\".id_almn_curso = " + id_almn_curso + " \n"
                    + " AND public.\"Asistencia\".fecha_asistencia = '" + fecha + "';";
            System.out.println(UPDATE);
            conn = pool.getConnection();
            ejecutarAsis = pool.ejecutar(UPDATE, conn, null) == null;
        }).start();

        return ejecutarAsis;
    }

    public synchronized boolean insertar(int id_alumno, String fecha, int falta) {

        String INSERT = "INSERT INTO \"Asistencia\" (id_almn_curso, fecha_asistencia, numero_faltas)\n"
                + "VALUES (" + id_alumno + ", '" + fecha + "'," + falta + ");";
        System.out.println(INSERT);
        conn = pool.getConnection();
        ejecutarAsis = pool.ejecutar(INSERT, conn, null) == null;

        return ejecutarAsis;

    }

    public int numHorasPorDia(int id_curso, int dia) {
        int d = 0;
        String SELECT = "SELECT \n"
                + "COUNT(\"SesionClase\".id_sesion)\n"
                + "FROM \"SesionClase\" \n"
                + "WHERE \"SesionClase\".id_curso = " + id_curso + "\n"
                + "AND \"SesionClase\".dia_sesion = " + dia + "\n"
                + "GROUP BY \"SesionClase\".dia_sesion; ";
        conn = pool.getConnection();
        rst = pool.ejecutarQuery(SELECT, conn, null);
        System.out.println("Numero de semanas: \n" + SELECT);
        try {
            while (rst.next()) {
                d = rst.getInt(1);
            }
        } catch (SQLException | NullPointerException e) {
            if (e instanceof SQLException) {
                System.out.println(e.getMessage());
            }
        } finally {
            pool.closeStmt();
            pool.close(rst);
            pool.close(conn);
        }
        return d;
    }

    public synchronized boolean eliminar(int id_almn_curso, String fecha) {
        new Thread(() -> {
            String DELETE = "DELETE \n"
                    + "FROM \"public\".\"Asistencia\"\n"
                    + "WHERE \"public\".\"Asistencia\".id_almn_curso = " + id_almn_curso + "\n"
                    + "AND \"public\".\"Asistencia\".fecha_asistencia = '" + fecha + "'";
            System.out.println(DELETE);
            conn = pool.getConnection();
            ejecutarAsis = pool.ejecutar(DELETE, conn, null) == null;
        }).start();

        return ejecutarAsis;
    }

    public synchronized boolean eliminarACursoDia(int idCurso, String fecha) {
        String DELETE = "DELETE FROM public.\"Asistencia\"\n"
                + "WHERE id_almn_curso IN (\n"
                + "	SELECT id_almn_curso\n"
                + "	FROM public.\"AlumnoCurso\"\n"
                + "	WHERE id_curso = " + idCurso + "\n"
                + ") AND fecha_asistencia = '" + fecha + "';";

        System.out.println("------------------------------------");
        System.out.println("D E L E T E ");
        System.out.println(DELETE);
        System.out.println("------------------------------------");

        conn = pool.getConnection();

        return pool.ejecutar(DELETE, conn, null) == null;
    }
}
