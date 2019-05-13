package modelo.curso;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.ConectarDB;
import modelo.materia.MateriaMD;
import modelo.persona.DocenteMD;

/**
 *
 * @author Johnny
 */
public class SesionClaseBD extends SesionClaseMD {

    private final ConectarDB conecta;
    private String sql, nsql;

    public SesionClaseBD(ConectarDB conecta) {
        this.conecta = conecta;
    }

    public void ingresar() {
        nsql = "INSERT INTO public.\"SesionClase\"(\n"
                + "	id_curso, dia_sesion, hora_inicio_sesion, hora_fin_sesion)\n"
                + "	VALUES (" + getCurso().getId() + ", " + getDia() + ", "
                + "'" + getHoraIni() + "', '" + getHoraFin() + "');";
        if (conecta.nosql(nsql) == null) {
            JOptionPane.showMessageDialog(null, "Se guardo correctamente el horario.");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo guardar el horario correctamente.");
        }
    }

    public String obtenerInsert() {
        nsql = "INSERT INTO public.\"SesionClase\"(\n"
                + "	id_curso, dia_sesion, hora_inicio_sesion, hora_fin_sesion)\n"
                + "	VALUES (" + getCurso().getId() + ", " + getDia() + ", "
                + "'" + getHoraIni() + "', '" + getHoraFin() + "');";
        return nsql;
    }

    public void ingresarHorarios(String nsql) {
        if (conecta.nosql(nsql) == null) {
            JOptionPane.showMessageDialog(null, "Se guardo correctamente el horario.");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo guardar el horario correctamente.");
        }
    }

    public void editar(int idSesion) {
        nsql = "UPDATE public.\"SesionClase\"\n"
                + "	SET  dia_sesion=" + getDia() + ", "
                + " hora_inicio_sesion='" + getHoraIni() + "', hora_fin_sesion='" + getHoraFin() + "'\n"
                + "	WHERE id_sesion=" + idSesion + ";";
        if (conecta.nosql(nsql) == null) {
            JOptionPane.showMessageDialog(null, "Se edito correctamente el horario.");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo editar correctamente el horario.");
        }
    }

    public void eliminar(int idSesion) {
        nsql = "DELETE FROM public.\"SesionClase\"\n"
                + "	WHERE id_sesion= " + idSesion + ";";
        if (conecta.nosql(nsql) == null) {
            JOptionPane.showMessageDialog(null, "Se elimino correctamente el horario.");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar correctamente el horario.");
        }
    }

    public SesionClaseMD buscarSesion(int idSesion) {
        SesionClaseMD s = new SesionClaseMD();
        sql = "SELECT id_sesion, id_curso, dia_sesion, hora_inicio_sesion, hora_fin_sesion \n"
                + "	FROM public.\"SesionClase\" \n"
                + "	WHERE id_sesion = " + idSesion + " ;";
        ResultSet rs = conecta.sql(sql);
        if (rs != null) {
            try {
                while (rs.next()) {
                    CursoMD c = new CursoMD();
                    s.setId(rs.getInt("id_sesion"));
                    c.setId(rs.getInt("id_curso"));
                    s.setCurso(c);
                    s.setDia(rs.getInt("dia_sesion"));
                    s.setHoraFin(rs.getTime("hora_fin_sesion").toLocalTime());
                    s.setHoraIni(rs.getTime("hora_inicio_sesion").toLocalTime());
                }
                return s;
            } catch (SQLException e) {
                System.out.println("No se pudo consultar sesion " + e.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Cargamos el horario del curso por calse de ese dia
     *
     * @param idCurso
     * @param dia
     * @return
     */
    public ArrayList<SesionClaseMD> cargarHorarioCursoPorDia(int idCurso, int dia) {
        sql = "SELECT id_sesion, dia_sesion, hora_inicio_sesion, hora_fin_sesion \n"
                + "	FROM public.\"SesionClase\" \n"
                + "	WHERE id_curso = " + idCurso + " \n"
                + "	AND dia_sesion = " + dia + " ;";
        ArrayList<SesionClaseMD> sesiones = new ArrayList<>();
        ResultSet rs = conecta.sql(sql);
        //System.out.println(sql);
        if (rs != null) {
            try {
                while (rs.next()) {
                    SesionClaseMD s = new SesionClaseMD();
                    s.setDia(rs.getInt("dia_sesion"));
                    s.setHoraFin(rs.getTime("hora_fin_sesion").toLocalTime());
                    s.setHoraIni(rs.getTime("hora_inicio_sesion").toLocalTime());
                    s.setId(rs.getInt("id_sesion"));

                    sesiones.add(s);
                }
                return sesiones;
            } catch (SQLException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Cargamos el horario de un curso.
     *
     * @param curso
     * @return
     */
    public ArrayList<SesionClaseMD> cargarHorarioCurso(CursoMD curso) {
        sql = "SELECT id_sesion, dia_sesion, hora_inicio_sesion, hora_fin_sesion \n"
                + "	FROM public.\"SesionClase\" \n"
                + "	WHERE id_curso = " + curso.getId() + ";";
        ArrayList<SesionClaseMD> sesiones = new ArrayList<>();
        ResultSet rs = conecta.sql(sql);
        //System.out.println(sql);
        if (rs != null) {
            try {
                while (rs.next()) {
                    SesionClaseMD s = new SesionClaseMD();
                    s.setCurso(curso);
                    s.setDia(rs.getInt("dia_sesion"));
                    s.setHoraFin(rs.getTime("hora_fin_sesion").toLocalTime());
                    s.setHoraIni(rs.getTime("hora_inicio_sesion").toLocalTime());
                    s.setId(rs.getInt("id_sesion"));

                    sesiones.add(s);
                }
                return sesiones;
            } catch (SQLException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Cargamos todo el horario del curso por dia.
     *
     * @param nombreCurso
     * @param dia
     * @param idPrdLectivo
     * @return
     */
    public ArrayList<SesionClaseMD> cargarHorarioCursoPorDia(String nombreCurso, int dia, int idPrdLectivo) {
        sql = "SELECT id_sesion, sc.id_curso, \n"
                + "hora_inicio_sesion, hora_fin_sesion, \n"
                + "materia_nombre, materia_codigo, \n"
                + "persona_primer_nombre, persona_primer_apellido, \n"
                + "docente_abreviatura\n"
                + "FROM public.\"SesionClase\" sc, public.\"Cursos\" c,\n"
                + "public.\"Materias\" m, public.\"Docentes\" d, \n"
                + "public.\"Personas\" p\n"
                + "WHERE sc.id_curso IN (\n"
                + "	SELECT id_curso\n"
                + "	FROM public.\"Cursos\"\n"
                + "	WHERE id_prd_lectivo = " + idPrdLectivo + "  AND \n"
                + "	curso_nombre = '" + nombreCurso + "'\n"
                + ") AND dia_sesion = " + dia + " AND\n"
                + "c.id_curso = sc.id_curso AND \n"
                + "d.id_docente = c.id_docente AND\n"
                + "p.id_persona = d.id_persona AND \n"
                + "m.id_materia = c.id_materia\n"
                + "ORDER BY hora_inicio_sesion";
        ArrayList<SesionClaseMD> sesiones = new ArrayList<>();
        ResultSet rs = conecta.sql(sql);
        //System.out.println(sql);
        if (rs != null) {
            try {
                while (rs.next()) {
                    SesionClaseMD s = new SesionClaseMD();
                    s.setId(rs.getInt("id_sesion"));
                    s.setHoraFin(rs.getTime("hora_fin_sesion").toLocalTime());
                    s.setHoraIni(rs.getTime("hora_inicio_sesion").toLocalTime());
                    CursoMD c = new CursoMD();
                    c.setId(rs.getInt("id_curso"));
                    MateriaMD m = new MateriaMD();
                    m.setNombre(rs.getString("materia_nombre"));
                    m.setCodigo(rs.getString("materia_codigo"));
                    DocenteMD d = new DocenteMD();
                    d.setPrimerNombre(rs.getString("persona_primer_nombre"));
                    d.setPrimerApellido(rs.getString("persona_primer_apellido"));

                    c.setDocente(d);
                    c.setMateria(m);
                    s.setCurso(c);

                    sesiones.add(s);
                }
                return sesiones;
            } catch (SQLException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public ArrayList<SesionClaseMD> cargarHorarioCurso(String nombreCurso, int idPrdLectivo) {
        sql = "SELECT id_sesion, dia_sesion, hora_inicio_sesion, hora_fin_sesion,"
                + " id_curso \n"
                + "	FROM public.\"SesionClase\" \n"
                + "	WHERE id_curso IN (\n"
                + "	SELECT id_curso\n"
                + "	FROM public.\"Cursos\"\n"
                + "	WHERE id_prd_lectivo = " + idPrdLectivo + "  AND \n"
                + "	curso_nombre = '" + nombreCurso + "');";

        ArrayList<SesionClaseMD> sesiones = new ArrayList<>();
        ResultSet rs = conecta.sql(sql);
        System.out.println(sql);
        if (rs != null) {
            try {
                while (rs.next()) {
                    SesionClaseMD s = new SesionClaseMD();
                    CursoMD c = new CursoMD();
                    c.setId(rs.getInt("id_curso"));
                    s.setCurso(c);
                    s.setDia(rs.getInt("dia_sesion"));
                    s.setHoraFin(rs.getTime("hora_fin_sesion").toLocalTime());
                    s.setHoraIni(rs.getTime("hora_inicio_sesion").toLocalTime());
                    s.setId(rs.getInt("id_sesion"));

                    sesiones.add(s);
                }
                return sesiones;
            } catch (SQLException e) {
                return null;
            }
        } else {
            return null;
        }
    }

}
