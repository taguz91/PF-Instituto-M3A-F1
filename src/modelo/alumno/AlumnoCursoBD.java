package modelo.alumno;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.ConectarDB;
import modelo.curso.CursoBD;
import modelo.persona.AlumnoBD;

/**
 *
 * @author Johnny
 */
public class AlumnoCursoBD extends AlumnoCursoMD {

    private final ConectarDB conecta;
    private final AlumnoBD alm;
    private final CursoBD cur;

    public AlumnoCursoBD(ConectarDB conecta) {
        this.conecta = conecta;
        this.alm = new AlumnoBD(conecta);
        this.cur = new CursoBD(conecta);
    }

    public ArrayList<AlumnoCursoMD> cargarAlumnosCursos() {
        String sql = "SELECT id_almn_curso, id_alumno, id_curso, almn_curso_nt_1_parcial,\n"
                + "almn_curso_nt_examen_interciclo, almn_curso_nt_2_parcial,\n"
                + "almn_curso_nt_examen_final, almn_curso_nt_examen_supletorio,\n"
                + "almn_curso_asistencia, almn_curso_nota_final, almn_curso_estado,\n"
                + "almn_curso_num_faltas\n"
                + "FROM public.\"AlumnoCurso\";";
        return consultarAlmnCursos(sql);
    }

    public ArrayList<AlumnoCursoMD> cargarAlumnosCursosPorAlumno(int idAlumno) {
        String sql = "SELECT id_almn_curso, id_alumno, id_curso, almn_curso_nt_1_parcial,\n"
                + "almn_curso_nt_examen_interciclo, almn_curso_nt_2_parcial,\n"
                + "almn_curso_nt_examen_final, almn_curso_nt_examen_supletorio,\n"
                + "almn_curso_asistencia, almn_curso_nota_final, almn_curso_estado,\n"
                + "almn_curso_num_faltas\n"
                + "FROM public.\"AlumnoCurso\" "
                + "WHERE id_alumno = " + idAlumno + ";";
        return consultarAlmnCursos(sql);
    }
    
    public ArrayList<AlumnoCursoMD> cargarAlumnosCursosPorCurso(int idCurso) {
        String sql = "SELECT id_almn_curso, id_alumno, id_curso, almn_curso_nt_1_parcial,\n"
                + "almn_curso_nt_examen_interciclo, almn_curso_nt_2_parcial,\n"
                + "almn_curso_nt_examen_final, almn_curso_nt_examen_supletorio,\n"
                + "almn_curso_asistencia, almn_curso_nota_final, almn_curso_estado,\n"
                + "almn_curso_num_faltas\n"
                + "FROM public.\"AlumnoCurso\" "
                + "WHERE id_curso = " + idCurso + ";";
        return consultarAlmnCursos(sql);
    }
    
    public AlumnoCursoMD buscarAlumnoCurso(int idAlmnCurso){
        AlumnoCursoMD almn = new AlumnoCursoMD(); 
        String sql = "SELECT id_almn_curso, id_alumno, id_curso, almn_curso_nt_1_parcial,\n"
                + "almn_curso_nt_examen_interciclo, almn_curso_nt_2_parcial,\n"
                + "almn_curso_nt_examen_final, almn_curso_nt_examen_supletorio,\n"
                + "almn_curso_asistencia, almn_curso_nota_final, almn_curso_estado,\n"
                + "almn_curso_num_faltas\n"
                + "FROM public.\"AlumnoCurso\" "
                + "WHERE id_almn_curso = " + idAlmnCurso + ";";
        ResultSet rs = conecta.sql(sql); 
        try {
            if (rs != null) {
                while(rs.next()){
                    almn = obtenerAlmCurso(rs);
                }
                return almn; 
            }else{
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No se pudo buscar alumno curso");
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    

    private ArrayList<AlumnoCursoMD> consultarAlmnCursos(String sql) {
        ArrayList<AlumnoCursoMD> almns = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    almns.add(obtenerAlmCurso(rs));
                }
                return almns;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No se pudieron consultar los cursos");
            return null;
        }
    }

    private AlumnoCursoMD obtenerAlmCurso(ResultSet rs) {
        try {
            AlumnoCursoMD a = new AlumnoCursoMD();
            a.setId(rs.getInt("id_almn_curso"));
            a.setAlumno(alm.buscarAlumno(rs.getInt("id_alumno")));
            a.setCurso(cur.buscarCurso(rs.getInt("id_curso")));
            a.setNota1Parcial(rs.getDouble("almn_curso_nt_1_parcial"));
            a.setNotaExamenInter(rs.getDouble("almn_curso_nt_examen_interciclo"));
            a.setNota2Parcial(rs.getDouble("almn_curso_nt_2_parcial"));
            a.setNotaExamenFinal(rs.getDouble("almn_curso_nt_examen_final"));
            a.setNotaExamenSupletorio(rs.getDouble("almn_curso_nt_examen_supletorio"));
            a.setAsistencia(rs.getString("almn_curso_asistencia"));
            a.setNotaFinal(rs.getDouble("almn_curso_nota_final"));
            a.setEstado(rs.getString("almn_curso_estado"));
            a.setNumFalta(rs.getInt("almn_curso_num_faltas"));
            return a;
        } catch (SQLException e) {
            System.out.println("No pudimos obtener alumnocurso");
            System.out.println(e.getMessage());
            return null;
        }
    }

}
