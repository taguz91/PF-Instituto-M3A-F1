
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
    private ResultSet rst;

    {
        pool = new ConnDBPool();
    }

    public AsistenciaBD(int id, LocalDate fechaAsistencia, int numeroFaltas, String observaciones, AlumnoCursoMD alumnoCurso) {
        super(id, fechaAsistencia, numeroFaltas, observaciones, alumnoCurso);
    }

    public AsistenciaBD() {
    }

    public List<AsistenciaBD> selectWhere(AlumnoCursoMD alumnoCurso) {
        String SELECT = "SELECT\n" +
"	\"Alumnos\".id_alumno,\n" +
"	p_alu.persona_identificacion,\n" +
"	p_alu.persona_primer_apellido || ' ' || p_alu.persona_segundo_apellido AS \"apellidos_al\",\n" +
"	p_alu.persona_primer_nombre || ' ' || p_alu.persona_segundo_nombre AS \"nombres_al\",\n" +
"	\"Materias\".materia_nombre,\n" +
"	\"Cursos\".curso_nombre,\n" +
"	\"Carreras\".carrera_nombre,\n" +
"	\"PeriodoLectivo\".prd_lectivo_nombre,\n" +
"	 \"Docentes\".docente_abreviatura ||' ' ||\"Personas\".persona_primer_apellido || ' ' || \"Personas\".persona_segundo_apellido ||' '|| \"Personas\".persona_primer_nombre || ' ' || \"Personas\".persona_segundo_nombre\n" +
"	 AS \"PROFESOR\",\n" +
"	\"Asistencia\".numero_faltas,\n" +
"	date_part('dow', \"CalendarioPeriodo\".clnd_prd_fecha_fin)\n" +
"\n" +
"FROM\n" +
"	\"AlumnoCurso\"\n" +
"	INNER JOIN \"Cursos\" ON \"AlumnoCurso\".id_curso = \"Cursos\".id_curso\n" +
"	INNER JOIN \"Jornadas\" ON \"Cursos\".id_jornada = \"Jornadas\".id_jornada\n" +
"	INNER JOIN \"Materias\" ON \"Cursos\".id_materia = \"Materias\".id_materia\n" +
"	INNER JOIN \"PeriodoLectivo\" ON \"Cursos\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo\n" +
"	INNER JOIN \"Alumnos\" ON \"AlumnoCurso\".id_alumno = \"Alumnos\".id_alumno\n" +
"	INNER JOIN \"Personas\" p_alu ON \"Alumnos\".id_persona = p_alu.id_persona\n" +
"	INNER JOIN \"Docentes\" ON \"public\".\"Cursos\".id_docente = \"public\".\"Docentes\".id_docente\n" +
"	INNER JOIN \"Carreras\" ON \"public\".\"Carreras\".id_carrera = \"public\".\"Materias\".id_carrera\n" +
"	INNER JOIN \"Personas\" ON \"public\".\"Personas\".id_persona = \"public\".\"Docentes\".id_persona\n" +
"	INNER JOIN \"Docentes\" doc_coor ON doc_coor.id_docente = \"public\".\"Carreras\".id_docente_coordinador\n" +
"  INNER JOIN \"Personas\" per_coor ON per_coor.id_persona = doc_coor.id_persona\n" +
"	INNER JOIN \"Asistencia\" ON \"Asistencia\".id_almn_curso = \"AlumnoCurso\".id_almn_curso\n" +
"	INNER JOIN \"CalendarioPeriodo\" ON \"CalendarioPeriodo\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo\n" +
"WHERE\n" +
"	 \"Cursos\".id_docente = 49 \n" +
"	 AND \"PeriodoLectivo\".id_prd_lectivo = (SELECT \"PeriodoLectivo\".id_prd_lectivo FROM \"PeriodoLectivo\" WHERE \"PeriodoLectivo\".prd_lectivo_nombre = 'TDS 11/2018 - 4/2019')\n" +
"AND \"Cursos\".curso_nombre= 'M2A'\n" +
"AND \"public\".\"Materias\".materia_nombre = 'ALGEBRA Y TRIGONOMETRÍA'\n" +
"\n" +
"	ORDER BY p_alu.persona_primer_apellido || ' ' || p_alu.persona_segundo_apellido";

        List<AsistenciaBD> listaAsistencia = new ArrayList<>();
        Map<Integer, Object> parametrosAsis = new HashMap<>();
        parametrosAsis.put(1, alumnoCurso.getId());

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
                    + "SET numero_faltas = " + getNumeroFaltas() + " \n"
                    + "WHERE \n"
                    + "\"public\".\"Asistencia\".id_asistencia = " + getId();
            System.out.println(UPDATE);
            conn = pool.getConnection();
            ejecutarAsis = pool.ejecutar(UPDATE, conn, null) == null;
        }).start();

        return ejecutarAsis;
    }
    
    public synchronized boolean insertar(int id_alumno, String fecha, int falta ){
        new Thread (()->{
            String INSERT = "INSERT INTO \"Asistencia\" (id_almn_curso, fecha_asistencia, numero_faltas)\n" +
            "VALUES ("+id_alumno+", '"+fecha+"',"+falta+");";
            System.out.println(INSERT);
            conn = pool.getConnection();
            ejecutarAsis = pool.ejecutar(INSERT, conn, null) == null;
            
        }).start();
        return ejecutarAsis;
        
    }
}
