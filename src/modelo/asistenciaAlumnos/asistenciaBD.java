/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.asistenciaAlumnos;

import java.sql.Connection;
import java.sql.ResultSet;
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
public class asistenciaBD extends asistenciaMD{
    
    private ConnDBPool pool;
    private Connection conn;
    private ResultSet rs;
    
    {
      pool = new ConnDBPool();
    }

    public asistenciaBD(int id_asistencia, int fecha_asistencia, int numero_faltas, String observaciones, AlumnoCursoMD alumnoCurso) {
        super(id_asistencia, fecha_asistencia, numero_faltas, observaciones, alumnoCurso);
    }

    public asistenciaBD() {
    }

    public List<asistenciaBD> selectWhere (AlumnoCursoMD alumnoCurso){
        String SELECT = "SELECT\n"
                + "\"public\".\"Asistencia\".id_asistencia,\n"
                + "\"public\".\"Asistencia\".fecha_asistencia,\n"
                + "\"public\".\"Asistencia\".numero_faltas,\n"
                + "\"public\".\"Asistencia\".observacion_asistencia,\n"
                + "FROM\n"
                + "\"public\".\"Asistencia\"\n"
                + "INNER JOIN \"public\".\"AlumnoCurso\" ON \"public\".\"Asistencia\".id_almn_curso = \"public\".\"AlumnoCurso\".id_almn_curso\n"
                + "WHERE\n"
                + "\"public\".\"Asistencia\".id_almn_curso = ?" ;
        
        List<asistenciaBD> listaAsistencia = new ArrayList<>();
        Map<Integer, Object> parametrosAsis = new HashMap<>();
        parametrosAsis.put(1,alumnoCurso.getId());
        
        try {
            conn = pool.getConnection();
            rs = pool.ejecutarQuery(SELECT, conn, parametrosAsis);
            System.out.println(pool.getStmt().toString());
            while(rs.next()){
               asistenciaBD asistencia = new asistenciaBD();
               asistencia.setId_asistencia(rs.getInt("id_asistencia"));
               // asistencia.setFecha_asistencia(0);
               asistencia.setNumero_faltas(rs.getInt("numero_faltas"));
               asistencia.setObservaciones(rs.getString("observacion_asistencia"));
               
               
               listaAsistencia.add(asistencia);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            pool.closeStmt().close(rs).close(conn);
        }
    
        return listaAsistencia;
    }
    
    private boolean ejecutarAsis = false;
    
    private synchronized boolean editar(){
        new Thread(() -> {
            String UPDATE = "UPDATE \"Asistencia\" \n"
                    + "SET numero_faltas = " + getNumero_faltas() + " \n"
                    + "WHERE \n"
                    + "\"public\".\"Asistencia\".id_asistencia = " +getId_asistencia();
            System.out.println(UPDATE);
            conn = pool.getConnection();
            ejecutarAsis = pool.ejecutar(UPDATE, conn, null) == null;
        }).start();
        
        return ejecutarAsis;
    }
}
