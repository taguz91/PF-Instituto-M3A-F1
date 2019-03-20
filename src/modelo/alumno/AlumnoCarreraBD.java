package modelo.alumno;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.ConectarDB;
import modelo.ResourceManager;
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
                + "'" + getFechaRegistro() + "' );";
        if (conecta.nosql(nsql) == null) {
            System.out.println("Guardamos correctamente el alumno en la carrera");
            return true;
        } else {
            return false;
        }
    }

    public AlumnoCarreraMD buscarAlumnoCarrera(int idAlmCarrera) {
        AlumnoCarreraMD ac = null;
        String sql = "SELECT id_almn_carrera, id_alumno, id_carrera, almn_carrera_fecha_registro\n"
                + "	FROM public.\"AlumnosCarrera\" WHERE almn_carrera_activo = 'true' "
                + "AND id_almn_carrera = " + idAlmCarrera + ";";
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    ac = obtenerAlumnoCarrera(rs);
                }
                return ac;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No pudimos consultar un alumno");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<AlumnoCarreraMD> cargarAlumnoCarrera() {
        String sql = "SELECT id_almn_carrera, id_alumno, id_carrera, almn_carrera_fecha_registro\n"
                + "	FROM public.\"AlumnosCarrera\" WHERE almn_carrera_activo = 'true';";
        return consultarAlumnoCarrera(sql);
    }

    public ArrayList<AlumnoCarreraMD> cargarAlumnoCarreraPorCarrera(int idCarrera) {
        String sql = "SELECT id_almn_carrera, id_alumno, id_carrera, almn_carrera_fecha_registro\n"
                + "	FROM public.\"AlumnosCarrera\" WHERE almn_carrera_activo = 'true' "
                + "AND id_carrera = " + idCarrera + ";";
        return consultarAlumnoCarreraPorCarrera(sql, idCarrera);
    }

    public ArrayList<AlumnoCarreraMD> buscarAlumnoCarrera(int idCarrera, String aguja) {
        String sql = "SELECT id_almn_carrera, \"AlumnosCarrera\".id_alumno, id_carrera, almn_carrera_fecha_registro\n"
                + "FROM public.\"AlumnosCarrera\", public.\"Alumnos\", public.\"Personas\" \n"
                + "WHERE \"Alumnos\".id_alumno = \"AlumnosCarrera\".id_alumno \n"
                + "AND \"Personas\".id_persona = \"Alumnos\".id_persona \n"
                + "AND almn_carrera_activo = 'true' AND id_carrera = " + idCarrera + " \n"
                + "AND (persona_identificacion ILIKE '%" + aguja + "%' OR\n"
                + "	 persona_primer_apellido ILIKE '%" + aguja + "%' OR\n"
                + "	 persona_segundo_apellido ILIKE '%" + aguja + "%' OR\n"
                + "	 persona_primer_nombre ILIKE '%" + aguja + "%' OR \n"
                + "	 persona_segundo_nombre ILIKE '%" + aguja + "%');";
        return consultarAlumnoCarreraPorCarrera(sql, idCarrera);
    }

    private ArrayList<AlumnoCarreraMD> consultarAlumnoCarrera(String sql) {
        ArrayList<AlumnoCarreraMD> alms = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    AlumnoCarreraMD ac = obtenerAlumnoCarrera(rs);
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

    private ArrayList<AlumnoCarreraMD> consultarAlumnoCarreraPorCarrera(String sql, int idCarrera) {
        ArrayList<AlumnoCarreraMD> alms = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                CarreraMD c = car.buscar(idCarrera);
                while (rs.next()) {
                    AlumnoCarreraMD ac = obtenerAlumnoCarreraPorCarrera(rs, c);
                    if (ac != null) {
                        alms.add(ac);
                    }
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

    private AlumnoCarreraMD obtenerAlumnoCarrera(ResultSet rs) {
        AlumnoCarreraMD ac = new AlumnoCarreraMD();
        try {
            ac.setId(rs.getInt("id_almn_carrera"));
            AlumnoMD a = alm.buscarAlumno(rs.getInt("id_alumno"));
            ac.setAlumno(a);
            CarreraMD c = car.buscar(rs.getInt("id_carrera"));
            ac.setCarrera(c);
            ac.setFechaRegistro(rs.getTimestamp("almn_carrera_fecha_registro").toLocalDateTime());
            return ac;
        } catch (SQLException e) {
            System.out.println("No pudimos obtener un alumno");
            return null;
        }
    }

    private AlumnoCarreraMD obtenerAlumnoCarreraPorCarrera(ResultSet rs, CarreraMD c) {
        AlumnoCarreraMD ac = new AlumnoCarreraMD();
        try {
            ac.setId(rs.getInt("id_almn_carrera"));
            AlumnoMD a = alm.buscarAlumno(rs.getInt("id_alumno"));
            ac.setAlumno(a);
            ac.setCarrera(c);
            ac.setFechaRegistro(rs.getTimestamp("almn_carrera_fecha_registro").toLocalDateTime());
            return ac;
        } catch (SQLException e) {
            System.out.println("No pudimos obtener un alumno");
            return null;
        }
    }
}
