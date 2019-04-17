package modelo.alumno;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.ConectarDB;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.persona.AlumnoBD;
import modelo.persona.AlumnoMD;

/**
 *
 * @author Johnny
 */
public class AlumnoCarreraBD extends AlumnoCarreraMD {

    private final ConectarDB conecta;
    //Para consulta alumno 
    private final AlumnoBD alm;
    //Para consultar carreras  
    private final CarreraBD car;

    public AlumnoCarreraBD(ConectarDB conecta) {
        this.conecta = conecta;
        //Inicializamos la clases que usaremos
        this.alm = new AlumnoBD(conecta);
        this.car = new CarreraBD(conecta);
    }

    public boolean guardar() {
        String nsql = "INSERT INTO public.\"AlumnosCarrera\"(\n"
                + "	id_alumno, id_carrera, almn_carrera_fecha_registro)\n"
                + "	VALUES (" + getAlumno().getId_Alumno() + ", " + getCarrera().getId() + ", "
                + " now() );";
        if (conecta.nosql(nsql) == null) {
            System.out.println("Guardamos correctamente el alumno en la carrera");

            return true;
        } else {
            return false;
        }
    }

    public ArrayList<AlumnoCarreraMD> cargarAlumnoCarrera() {
        String sql = "SELECT id_almn_carrera, ac.id_alumno, ac.id_carrera, almn_carrera_fecha_registro, \n"
                + "persona_primer_nombre, persona_segundo_nombre, persona_primer_apellido, persona_segundo_apellido,\n"
                + "persona_identificacion, carrera_codigo\n"
                + "FROM public.\"AlumnosCarrera\" ac, public.\"Alumnos\" a, public.\"Personas\" p,\n"
                + "public.\"Carreras\" c\n"
                + "WHERE  a.id_alumno = ac.id_alumno AND \n"
                + "p.id_persona = a.id_persona AND \n"
                + "c.id_carrera = ac.id_carrera AND carrera_activo = true \n"
                + "AND almn_carrera_activo = true;";
        return consultarAlumnoCarrera(sql);
    }

    public ArrayList<AlumnoCarreraMD> cargarAlumnoCarreraEliminados() {
        String sql = "SELECT id_almn_carrera, ac.id_alumno, ac.id_carrera, almn_carrera_fecha_registro, \n"
                + "persona_primer_nombre, persona_segundo_nombre, persona_primer_apellido, persona_segundo_apellido,\n"
                + "persona_identificacion, carrera_codigo\n"
                + "FROM public.\"AlumnosCarrera\" ac, public.\"Alumnos\" a, public.\"Personas\" p,\n"
                + "public.\"Carreras\" c\n"
                + "WHERE  a.id_alumno = ac.id_alumno AND \n"
                + "p.id_persona = a.id_persona AND \n"
                + "c.id_carrera = ac.id_carrera AND almn_carrera_activo = false;";
        return consultarAlumnoCarrera(sql);
    }

    /**
     * Consultamos todos los alumnos filtrandolos por una carrera.
     *
     * @param idCarrera
     * @return
     */
    public ArrayList<AlumnoCarreraMD> cargarAlumnoCarreraPorCarrera(int idCarrera) {
        String sql = "SELECT id_almn_carrera, ac.id_alumno, ac.id_carrera, almn_carrera_fecha_registro, \n"
                + "persona_primer_nombre, persona_segundo_nombre, persona_primer_apellido, persona_segundo_apellido,\n"
                + "persona_identificacion, carrera_codigo\n"
                + "FROM public.\"AlumnosCarrera\" ac, public.\"Alumnos\" a, public.\"Personas\" p,\n"
                + "public.\"Carreras\" c\n"
                + "WHERE  a.id_alumno = ac.id_alumno AND \n"
                + "p.id_persona = a.id_persona AND \n"
                + "c.id_carrera = ac.id_carrera AND \n"
                + "ac.id_carrera = " + idCarrera + ";";
        return consultarAlumnoCarrera(sql);
    }

    public ArrayList<AlumnoCarreraMD> buscar(String aguja) {
        String sql = "SELECT id_almn_carrera, ac.id_alumno, ac.id_carrera, almn_carrera_fecha_registro, \n"
                + "persona_primer_nombre, persona_segundo_nombre, persona_primer_apellido, persona_segundo_apellido,\n"
                + "persona_identificacion, carrera_codigo\n"
                + "FROM public.\"AlumnosCarrera\" ac, public.\"Alumnos\" a, public.\"Personas\" p,\n"
                + "public.\"Carreras\" c\n"
                + "WHERE  a.id_alumno = ac.id_alumno AND \n"
                + "p.id_persona = a.id_persona AND \n"
                + "c.id_carrera = ac.id_carrera AND (\n"
                + "	carrera_codigo ILIKE '%" + aguja + "%' OR \n"
                + "	persona_primer_nombre || ' ' || persona_segundo_nombre || ' ' ||\n"
                + "	persona_primer_apellido || ' ' || persona_segundo_apellido\n"
                + "	ILIKE '%" + aguja + "%'\n"
                + "	OR persona_identificacion ILIKE '%" + aguja + "%'\n"
                + ") AND persona_activa = true AND carrera_activo = true;";
        return consultarAlumnoCarrera(sql);
    }

    public ArrayList<AlumnoCarreraMD> buscarAlumnoCarrera(int idCarrera, String aguja) {
        String sql = "SELECT id_almn_carrera, ac.id_carrera, ac.id_alumno, a.id_persona, \n"
                + "persona_primer_nombre, persona_segundo_nombre,\n"
                + "persona_primer_apellido, persona_segundo_apellido,\n"
                + "persona_celular, persona_correo, persona_identificacion\n"
                + "FROM public.\"AlumnosCarrera\" ac, public.\"Alumnos\" a, public.\"Personas\" p, \n"
                + "public.\"Carreras\" c\n"
                + "WHERE a.id_alumno = ac.id_alumno AND\n"
                + "p.id_persona = a.id_persona AND\n"
                + "ac.id_carrera = " + idCarrera + " AND "
                + "c.id_carrera = ac.id_carrera AND (\n"
                + "	persona_primer_nombre || ' ' || persona_segundo_nombre || ' ' ||\n"
                + "	persona_primer_apellido || ' ' || persona_segundo_apellido ILIKE '%" + aguja + "%' OR\n"
                + "	persona_identificacion ILIKE '%" + aguja + "%') "
                + "AND persona_activa = true AND carrera_activo = true;";
        return consultarAlumnoCarreraTbl(sql);
    }

    private ArrayList<AlumnoCarreraMD> consultarAlumnoCarrera(String sql) {
        ArrayList<AlumnoCarreraMD> alms = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    AlumnoCarreraMD ac = obtenerAlumnoCarreraTbl(rs);
                    if (ac != null) {
                        alms.add(ac);
                    }
                }
                return alms;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No pudimos consultar alumnos");
            System.out.println(e.getMessage());
            return null;
        }
    }

    private ArrayList<AlumnoCarreraMD> consultarAlumnoCarreraTbl(String sql) {
        ArrayList<AlumnoCarreraMD> alms = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {

                while (rs.next()) {
                    AlumnoCarreraMD ac = new AlumnoCarreraMD();
                    ac.setId(rs.getInt("id_almn_carrera"));
                    AlumnoMD al = new AlumnoMD();
                    CarreraMD c = new CarreraMD();
                    c.setId(rs.getInt("id_carrera"));
                    al.setId_Alumno(rs.getInt("id_alumno"));
                    al.setIdPersona(rs.getInt("id_persona"));
                    al.setPrimerNombre(rs.getString("persona_primer_nombre"));
                    al.setSegundoNombre(rs.getString("persona_segundo_nombre"));
                    al.setPrimerApellido(rs.getString("persona_primer_apellido"));
                    al.setSegundoApellido(rs.getString("persona_segundo_apellido"));
                    al.setIdentificacion(rs.getString("persona_identificacion"));

                    ac.setCarrera(c);
                    ac.setAlumno(al);

                    alms.add(ac);
                }
                rs.close();
                return alms;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No pudimos consultar alumnos");
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Obtenemos el alumno consultado para cargarlo en una arraylist
     *
     * @param rs
     * @return
     */
    private AlumnoCarreraMD obtenerAlumnoCarreraTbl(ResultSet rs) {
        AlumnoCarreraMD ac = new AlumnoCarreraMD();
        try {
            ac.setId(rs.getInt("id_almn_carrera"));
            AlumnoMD a = new AlumnoMD();
            a.setId_Alumno(rs.getInt("id_alumno"));
            a.setPrimerApellido(rs.getString("persona_primer_apellido"));
            a.setPrimerNombre(rs.getString("persona_primer_nombre"));
            a.setSegundoApellido(rs.getString("persona_segundo_apellido"));
            a.setSegundoNombre(rs.getString("persona_segundo_nombre"));
            a.setIdentificacion(rs.getString("persona_identificacion"));
            ac.setAlumno(a);
            CarreraMD c = new CarreraMD();
            c.setId(rs.getInt("id_carrera"));
            c.setCodigo(rs.getString("carrera_codigo"));
            ac.setCarrera(c);
            ac.setFechaRegistro(rs.getTimestamp("almn_carrera_fecha_registro").toLocalDateTime());
            return ac;
        } catch (SQLException e) {
            System.out.println("No pudimos obtener un alumno");
            return null;
        }
    }

}
