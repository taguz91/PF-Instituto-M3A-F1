/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.asistenciaAlumnos;

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
    private ResultSet rs;

    {
        pool = new ConnDBPool();
    }

    public AsistenciaBD(int id, LocalDate fechaAsistencia, int numeroFaltas, String observaciones, AlumnoCursoMD alumnoCurso) {
        super(id, fechaAsistencia, numeroFaltas, observaciones, alumnoCurso);
    }

    public AsistenciaBD() {
    }

    public List<AsistenciaBD> selectWhere(AlumnoCursoMD alumnoCurso) {
        String SELECT = "SELECT\n"
                + "\"public\".\"Asistencia\".id_asistencia,\n"
                + "\"public\".\"Asistencia\".fecha_asistencia,\n"
                + "\"public\".\"Asistencia\".numero_faltas,\n"
                + "\"public\".\"Asistencia\".observacion_asistencia,\n"
                + "FROM\n"
                + "\"public\".\"Asistencia\"\n"
                + "INNER JOIN \"public\".\"AlumnoCurso\" ON \"public\".\"Asistencia\".id_almn_curso = \"public\".\"AlumnoCurso\".id_almn_curso\n"
                + "WHERE\n"
                + "\"public\".\"Asistencia\".id_almn_curso = ?";

        List<AsistenciaBD> listaAsistencia = new ArrayList<>();
        Map<Integer, Object> parametrosAsis = new HashMap<>();
        parametrosAsis.put(1, alumnoCurso.getId());

        try {
            conn = pool.getConnection();
            rs = pool.ejecutarQuery(SELECT, conn, parametrosAsis);
            System.out.println(pool.getStmt().toString());
            while (rs.next()) {
                AsistenciaBD asistencia = new AsistenciaBD();

                asistencia.setId(rs.getInt(1));
                asistencia.setFechaAsistencia(rs.getDate(2).toLocalDate());
                asistencia.setNumeroFaltas(rs.getInt(3));
                asistencia.setObservaciones(rs.getString(4));

                listaAsistencia.add(asistencia);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            pool.closeStmt().close(rs).close(conn);
        }

        return listaAsistencia;
    }

    private boolean ejecutarAsis = false;

    public synchronized boolean editar() {
        new Thread(() -> {
            String UPDATE = "UPDATE \"Asistencia\" \n"
                    + "SET numero_faltas = " + getNumeroFaltas() + " \n"
                    + "WHERE \n"
                    + "\"public\".\"Asistencia\".id_asistencia = " + getId();
            System.out.println(UPDATE);
            conn = pool.getConnection();
            ejecutarAsis = pool.ejecutar(UPDATE, conn, null) == null;
        }).start();

        return ejecutarAsis;
    }
}
