package modelo.alumno;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.ConectarDB;
import modelo.curso.CursoMD;
import modelo.materia.MateriaMD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.AlumnoMD;

/**
 *
 * @author Johnny
 */
public class AlumnoCursoRetiradoBD extends AlumnoCursoRetiradoMD {

    private final ConectarDB conecta;
    private String sql, nsql;

    public AlumnoCursoRetiradoBD(ConectarDB conecta) {
        this.conecta = conecta;
    }

    public void guardar() {
        nsql = "INSERT INTO public.\"AlumnoCursoRetirados\"(\n"
                + "	id_almn_curso, retiro_observacion)\n"
                + "	VALUES (" + getAlumnoCurso().getId() + ", "
                + " '" + getObservacion() + "');";
        if (conecta.nosql(nsql) == null) {
            JOptionPane.showMessageDialog(null, "Anulada la matricula de: \n"
                    + getAlumnoCurso().getCurso().getMateria().getNombre());
        }
    }

    public ArrayList<AlumnoCursoRetiradoMD> cargarRetirados() {
        sql = "SELECT id_retirado, acr.id_almn_curso, retiro_fecha, \n"
                + "retiro_observacion, ac.id_curso, materia_nombre, \n"
                + "ac.id_alumno, persona_identificacion, \n"
                + "persona_primer_nombre, persona_primer_apellido, "
                + "prd_lectivo_nombre \n"
                + "FROM public.\"AlumnoCursoRetirados\" acr, \n"
                + "public.\"AlumnoCurso\" ac, public.\"Cursos\" c, \n"
                + "public.\"Materias\" m, public.\"Alumnos\" a, \n"
                + "public.\"Personas\" p, public.\"PeriodoLectivo\" pl \n"
                + "WHERE ac.id_almn_curso = acr.id_almn_curso AND\n"
                + "c.id_curso = ac.id_curso AND \n"
                + "m.id_materia = c.id_materia AND \n"
                + "a.id_alumno = ac.id_alumno AND \n"
                + "pl.id_prd_lectivo = c.id_prd_lectivo AND \n"
                + "p.id_persona = a.id_persona AND \n"
                + "retiro_activo = true; ";
        return consultarParaTbl(sql);
    }

    public ArrayList<AlumnoCursoRetiradoMD> cargarRetiradosEliminados() {
        sql = "SELECT id_retirado, acr.id_almn_curso, retiro_fecha, \n"
                + "retiro_observacion, ac.id_curso, materia_nombre, \n"
                + "ac.id_alumno, persona_identificacion, \n"
                + "persona_primer_nombre, persona_primer_apellido, "
                + "prd_lectivo_nombre \n"
                + "FROM public.\"AlumnoCursoRetirados\" acr, \n"
                + "public.\"AlumnoCurso\" ac, public.\"Cursos\" c, \n"
                + "public.\"Materias\" m, public.\"Alumnos\" a, \n"
                + "public.\"Personas\" p, public.\"PeriodoLectivo\" pl \n"
                + "WHERE ac.id_almn_curso = acr.id_almn_curso AND\n"
                + "c.id_curso = ac.id_curso AND \n"
                + "m.id_materia = c.id_materia AND \n"
                + "a.id_alumno = ac.id_alumno AND \n"
                + "pl.id_prd_lectivo = c.id_prd_lectivo AND \n"
                + "p.id_persona = a.id_persona AND \n"
                + "retiro_activo = false; ";
        return consultarParaTbl(sql);
    }

    public ArrayList<AlumnoCursoRetiradoMD> buscarRetirados(String aguja) {
        sql = "	SELECT id_retirado, acr.id_almn_curso, retiro_fecha,\n"
                + "retiro_observacion, ac.id_curso, materia_nombre,\n"
                + "ac.id_alumno, persona_identificacion,\n"
                + "persona_primer_nombre, persona_primer_apellido, "
                + "prd_lectivo_nombre\n"
                + "FROM public.\"AlumnoCursoRetirados\" acr,\n"
                + "public.\"AlumnoCurso\" ac, public.\"Cursos\" c,\n"
                + "public.\"Materias\" m, public.\"Alumnos\" a,\n"
                + "public.\"Personas\" p, public.\"PeriodoLectivo\" pl \n"
                + "WHERE ac.id_almn_curso = acr.id_almn_curso AND\n"
                + "c.id_curso = ac.id_curso AND\n"
                + "m.id_materia = c.id_materia AND\n"
                + "a.id_alumno = ac.id_alumno AND \n"
                + "pl.id_prd_lectivo = c.id_prd_lectivo AND \n"
                + "p.id_persona = a.id_persona AND\n"
                + "retiro_activo = true AND(\n"
                + "	persona_identificacion ILIKE '%" + aguja + "%' OR\n"
                + "	persona_primer_nombre || ' ' || persona_segundo_nombre\n"
                + "	|| ' ' || persona_primer_apellido || ' ' ||persona_segundo_apellido\n"
                + "	ILIKE '%" + aguja + "%' "
                + "OR persona_primer_nombre || ' ' || persona_primer_apellido ILIKE '%" + aguja + "%');";
        return consultarParaTbl(sql);
    }

    public ArrayList<AlumnoCursoRetiradoMD> buscarRetiradosEliminados(String aguja) {
        sql = "	SELECT id_retirado, acr.id_almn_curso, retiro_fecha,\n"
                + "retiro_observacion, ac.id_curso, materia_nombre,\n"
                + "ac.id_alumno, persona_identificacion,\n"
                + "persona_primer_nombre, persona_primer_apellido, "
                + "prd_lectivo_nombre\n"
                + "FROM public.\"AlumnoCursoRetirados\" acr,\n"
                + "public.\"AlumnoCurso\" ac, public.\"Cursos\" c,\n"
                + "public.\"Materias\" m, public.\"Alumnos\" a,\n"
                + "public.\"Personas\" p, public.\"PeriodoLectivo\" pl \n"
                + "WHERE ac.id_almn_curso = acr.id_almn_curso AND\n"
                + "c.id_curso = ac.id_curso AND\n"
                + "m.id_materia = c.id_materia AND\n"
                + "a.id_alumno = ac.id_alumno AND \n"
                + "pl.id_prd_lectivo = c.id_prd_lectivo AND \n"
                + "p.id_persona = a.id_persona AND\n"
                + "retiro_activo = false AND(\n"
                + "	persona_identificacion ILIKE '%" + aguja + "%' OR\n"
                + "	persona_primer_nombre || ' ' || persona_segundo_nombre\n"
                + "	|| ' ' || persona_primer_apellido || ' ' ||persona_segundo_apellido\n"
                + "	ILIKE '%" + aguja + "%' "
                + "OR persona_primer_nombre || ' ' || persona_primer_apellido ILIKE '%" + aguja + "%');";
        return consultarParaTbl(sql);
    }

    public ArrayList<AlumnoCursoRetiradoMD> cargarRetiradosPorPrdEliminados(int idPrd) {
        sql = "	SELECT id_retirado, acr.id_almn_curso, retiro_fecha,\n"
                + "retiro_observacion, ac.id_curso, materia_nombre,\n"
                + "ac.id_alumno, persona_identificacion,\n"
                + "persona_primer_nombre, persona_primer_apellido, "
                + "prd_lectivo_nombre \n"
                + "FROM public.\"AlumnoCursoRetirados\" acr,\n"
                + "public.\"AlumnoCurso\" ac, public.\"Cursos\" c,\n"
                + "public.\"Materias\" m, public.\"Alumnos\" a,\n"
                + "public.\"Personas\" p, public.\"PeriodoLectivo\" pl \n"
                + "WHERE ac.id_almn_curso = acr.id_almn_curso AND\n"
                + "c.id_curso = ac.id_curso AND\n"
                + "m.id_materia = c.id_materia AND \n"
                + "a.id_alumno = ac.id_alumno AND \n"
                + "pl.id_prd_lectivo = c.id_prd_lectivo AND \n"
                + "p.id_persona = a.id_persona AND\n"
                + "retiro_activo = false AND\n"
                + "c.id_prd_lectivo = " + idPrd + ";";
        return consultarParaTbl(sql);
    }

    public ArrayList<AlumnoCursoRetiradoMD> cargarRetiradosPorPrd(int idPrd) {
        sql = "	SELECT id_retirado, acr.id_almn_curso, retiro_fecha,\n"
                + "retiro_observacion, ac.id_curso, materia_nombre,\n"
                + "ac.id_alumno, persona_identificacion,\n"
                + "persona_primer_nombre, persona_primer_apellido, "
                + "prd_lectivo_nombre \n"
                + "FROM public.\"AlumnoCursoRetirados\" acr,\n"
                + "public.\"AlumnoCurso\" ac, public.\"Cursos\" c,\n"
                + "public.\"Materias\" m, public.\"Alumnos\" a,\n"
                + "public.\"Personas\" p, public.\"PeriodoLectivo\" pl \n"
                + "WHERE ac.id_almn_curso = acr.id_almn_curso AND\n"
                + "c.id_curso = ac.id_curso AND\n"
                + "m.id_materia = c.id_materia AND \n"
                + "a.id_alumno = ac.id_alumno AND \n"
                + "pl.id_prd_lectivo = c.id_prd_lectivo AND \n"
                + "p.id_persona = a.id_persona AND\n"
                + "retiro_activo = true AND\n"
                + "c.id_prd_lectivo = " + idPrd + ";";
        return consultarParaTbl(sql);
    }

    private ArrayList<AlumnoCursoRetiradoMD> consultarParaTbl(String sql) {
        ArrayList<AlumnoCursoRetiradoMD> retirados = new ArrayList<>();
        ResultSet rs = conecta.sql(sql);
        if (rs != null) {
            try {
                while (rs.next()) {
                    AlumnoCursoRetiradoMD r = new AlumnoCursoRetiradoMD();
                    AlumnoCursoMD ac = new AlumnoCursoMD();
                    AlumnoMD a = new AlumnoMD();
                    CursoMD c = new CursoMD();
                    MateriaMD m = new MateriaMD();
                    PeriodoLectivoMD p = new PeriodoLectivoMD();

                    r.setId(rs.getInt("id_retirado"));
                    ac.setId(rs.getInt("id_almn_curso"));
                    r.setFecha(rs.getTimestamp("retiro_fecha").toLocalDateTime());
                    r.setObservacion(rs.getString("retiro_observacion"));
                    c.setId(rs.getInt("id_curso"));
                    m.setNombre(rs.getString("materia_nombre"));
                    a.setId_Alumno(rs.getInt("id_alumno"));
                    a.setIdentificacion(rs.getString("persona_identificacion"));
                    a.setPrimerNombre(rs.getString("persona_primer_nombre"));
                    a.setPrimerApellido(rs.getString("persona_primer_apellido"));
                    p.setNombre_PerLectivo(rs.getString("prd_lectivo_nombre"));

                    c.setMateria(m);
                    c.setPeriodo(p);
                    ac.setCurso(c);
                    ac.setAlumno(a);

                    r.setAlumnoCurso(ac);

                    retirados.add(r);
                }
                return retirados;
            } catch (SQLException e) {
                System.out.println("No se pudo consultar alumnos retirados." + e.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }

}
