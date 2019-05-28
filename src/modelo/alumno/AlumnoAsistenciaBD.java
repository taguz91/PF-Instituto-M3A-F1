/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.alumno;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import modelo.ConnDBPool;
import modelo.asistencia.AsistenciaBD;
import modelo.curso.CursoMD;
import modelo.persona.AlumnoMD;

/**
 *
 * @author Lushito
 */
public class AlumnoAsistenciaBD extends AlumnoAsistenciaMD{
    
    private ConnDBPool pool;
    private Connection conn;
    private ResultSet rst;
    
    {
        pool = new ConnDBPool();
    }

    public AlumnoAsistenciaBD(int id, AlumnoMD alumno, CursoMD curso, String fecha, int numFaltas, List<AsistenciaBD> faltas) {
        super(id, alumno, curso, fecha, numFaltas, faltas);
    }

    public AlumnoAsistenciaBD() {
    }
    
//    public List<AlumnoAsistenciaBD> selectWhere (int idDocente, int idperiodoLectivo, String materiaNombre, String cursoNombre) {
//        String SELECT = "SELECT \"Asistencia\".id_asistencia, \"Asistencia\".fecha_asistencia, \"              Asistencia\".numero_faltas\n" +
//        "FROM \"Asistencia\"\n" +
//        "WHERE\n" +
//        "\"Asistencia\".id_almn_curso = 1";
//    
}
