package modelo.mallaalumno;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.ConectarDB;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import modelo.persona.AlumnoBD;
import modelo.persona.AlumnoMD;

/**
 *
 * @author Johnny
 */
public class MallaAlumnoBD extends MallaAlumnoMD {

    ConectarDB conecta = new ConectarDB("Malla alumno");

    MateriaBD mat = new MateriaBD();
    AlumnoBD alm = new AlumnoBD();

    public void iniciarMalla(int idMateria, int idAlumno, int ciclo) {
        //Este inser deberia cambiar
        String nsql = "INSERT INTO public.\"MallaEstudiante\"(\n"
                + "	id_malla_alumno, id_materia, id_alumno, malla_almn_ciclo)\n"
                + "	VALUES ('" + idMateria + "-" + idAlumno + "' ," + idMateria + ", " + idAlumno + ", " + ciclo + ");";

        if (conecta.nosql(nsql) == null) {
            //System.out.println("Se guarda malla de un estidiante");
        }
    }

    public ArrayList<MallaAlumnoMD> cargarMallas() {
        String sql = "SELECT id_malla_alumno, id_materia, id_alumno, malla_almn_ciclo, \n"
                + "malla_almn_num_matricula, malla_almn_nota1, malla_almn_nota2, \n"
                + "malla_almn_nota3, malla_almn_estado\n"
                + "	FROM public.\"MallaEstudiante\";";
        return consultaMallas(sql);
    }
    
    public ArrayList<MallaAlumnoMD> cargarMallasPorEstudiante(int idEstudiante) {
        String sql = "SELECT id_malla_alumno, id_materia, id_alumno, malla_almn_ciclo, \n"
                + "malla_almn_num_matricula, malla_almn_nota1, malla_almn_nota2, \n"
                + "malla_almn_nota3, malla_almn_estado\n"
                + "	FROM public.\"MallaEstudiante\" "
                + "WHERE id_alumno = "+idEstudiante+";";
        return consultaMallas(sql);
    }

    private ArrayList<MallaAlumnoMD> consultaMallas(String sql) {
        ArrayList<MallaAlumnoMD> mallas = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    MallaAlumnoMD m = obtenerMallaAlumno(rs);
                    if (m != null) {
                        mallas.add(m);
                    }
                }
                return mallas;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No se pudieron cargar mallas");
            System.out.println(e.getMessage());
            return null;
        }
    }

    private MallaAlumnoMD obtenerMallaAlumno(ResultSet rs) {
        MallaAlumnoMD mll = new MallaAlumnoMD();
        try {
            mll.setId(rs.getString("id_malla_alumno"));
            MateriaMD m = mat.buscarMateria(rs.getInt("id_materia"));
            mll.setMateria(m);
            AlumnoMD a = alm.buscarAlumno(rs.getInt("id_alumno"));
            mll.setAlumno(a);
            mll.setMallaCiclo(rs.getInt("malla_almn_ciclo"));
            mll.setMallaNumMatricula(rs.getInt("malla_almn_num_matricula"));
            mll.setNota1(rs.getDouble("malla_almn_nota1"));
            mll.setNota2(rs.getDouble("malla_almn_nota2"));
            mll.setNota2(rs.getDouble("malla_almn_nota2"));
            return mll;
        } catch (SQLException e) {
            System.out.println("No se pudo obtener la malla");
            System.out.println(e.getMessage());
            return null;
        }
    }
}
