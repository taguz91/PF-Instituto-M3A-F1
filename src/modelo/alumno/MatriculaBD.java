package modelo.alumno;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.ConectarDB;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.AlumnoMD;

/**
 *
 * @author Johnny
 */
public class MatriculaBD extends MatriculaMD {

    private String sql = "", nsql = "";

    private final ConectarDB conecta;

    public MatriculaBD(ConectarDB conecta) {
        this.conecta = conecta;
    }

    public void ingresar() {
        nsql = "INSERT INTO public.\"Matricula\"(\n"
                + "	id_alumno, id_prd_lectivo)\n"
                + "	VALUES (" + getAlumno().getId_Alumno() + ", " + getPeriodo().getId_PerioLectivo() + ");";

        System.out.println("SE matricula: ");
        System.out.println(nsql);
        if (conecta.nosql(nsql) == null) {
            JOptionPane.showMessageDialog(null, "Matricula realizada con exito.");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo realizar la matricula, \n"
                    + "compruebe su conexion a internet.");
        }
    }

    public ArrayList<MatriculaMD> cargarMatriculas() {
        sql = "SELECT id_matricula, m.id_alumno, m.id_prd_lectivo, matricula_fecha, \n"
                + "persona_identificacion, persona_primer_nombre, persona_segundo_nombre, \n"
                + "persona_primer_apellido, persona_segundo_apellido, prd_lectivo_nombre\n"
                + "FROM public.\"Matricula\" m, public.\"PeriodoLectivo\" pl,\n"
                + "public.\"Alumnos\" a, public.\"Personas\" p\n"
                + "	WHERE pl.id_prd_lectivo = m.id_prd_lectivo AND \n"
                + "	a.id_alumno = m.id_alumno AND \n"
                + "	p.id_persona = a.id_persona; ";
        return consultarParaTbl(sql);
    }

    public ArrayList<MatriculaMD> cargarMatriculasPorPrd(int idPrd) {
        sql = "SELECT id_matricula, m.id_alumno, m.id_prd_lectivo, matricula_fecha, \n"
                + "persona_identificacion, persona_primer_nombre, persona_segundo_nombre, \n"
                + "persona_primer_apellido, persona_segundo_apellido, prd_lectivo_nombre\n"
                + "FROM public.\"Matricula\" m, public.\"PeriodoLectivo\" pl,\n"
                + "public.\"Alumnos\" a, public.\"Personas\" p\n"
                + "	WHERE pl.id_prd_lectivo = m.id_prd_lectivo AND \n"
                + "	a.id_alumno = m.id_alumno AND \n"
                + "	p.id_persona = a.id_persona AND \n"
                + "     m.id_prd_lectivo = " + idPrd + "; ";
        return consultarParaTbl(sql);
    }

    /**
     * Buscamos si el alumno ya fue matriculado
     *
     * @param idAlm
     * @param idPrd
     * @return
     */
    public MatriculaMD buscarMatriculaAlmnPrd(int idAlm, int idPrd) {
        MatriculaMD m = null;
        sql = "SELECT id_matricula, id_alumno, id_prd_lectivo, matricula_fecha \n"
                + "FROM public.\"Matricula\" \n "
                + "WHERE id_prd_lectivo = " + idPrd + " AND id_alumno = " + idAlm + ";";
        ResultSet rs = conecta.sql(sql);
        if (rs != null) {
            try {
                while (rs.next()) {
                    m = new MatriculaMD();
                    m.setId(rs.getInt("id_matricula"));
                    m.setFecha(rs.getTimestamp("matricula_fecha").toLocalDateTime());
                }
                return m;
            } catch (SQLException e) {
                System.out.println("No se pudieron consultar matriculas. " + e.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Se podran buscar por cedula, nombres completos y nombre del periodo, o
     * siglas de la carrera
     *
     * @param aguja
     * @return
     */
    public ArrayList<MatriculaMD> buscarMatriculas(String aguja) {
        sql = "SELECT id_matricula, m.id_alumno, m.id_prd_lectivo, matricula_fecha, \n"
                + "persona_identificacion, persona_primer_nombre, persona_segundo_nombre, \n"
                + "persona_primer_apellido, persona_segundo_apellido, prd_lectivo_nombre\n"
                + "FROM public.\"Matricula\" m, public.\"PeriodoLectivo\" pl,\n"
                + "public.\"Alumnos\" a, public.\"Personas\" p\n"
                + "	WHERE pl.id_prd_lectivo = m.id_prd_lectivo AND \n"
                + "	a.id_alumno = m.id_alumno AND \n"
                + "	p.id_persona = a.id_persona AND(\n"
                + "	persona_identificacion ILIKE '%" + aguja + "%' OR \n"
                + "	persona_primer_nombre || ' ' || persona_segundo_nombre || ' ' ||\n"
                + "	persona_primer_apellido ||' '|| persona_segundo_apellido ILIKE '%" + aguja + "%'\n"
                + "	OR prd_lectivo_nombre ILIKE '%" + aguja + "%'); ";
        return consultarParaTbl(sql);
    }

    private ArrayList<MatriculaMD> consultarParaTbl(String sql) {
        ArrayList<MatriculaMD> matriculas = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        if (rs != null) {
            try {
                while (rs.next()) {
                    MatriculaMD m = new MatriculaMD();
                    AlumnoMD a = new AlumnoMD();
                    PeriodoLectivoMD p = new PeriodoLectivoMD();
                    m.setId(rs.getInt("id_matricula"));
                    a.setId_Alumno(rs.getInt("id_alumno"));
                    p.setId_PerioLectivo(rs.getInt("id_prd_lectivo"));
                    m.setFecha(rs.getTimestamp("matricula_fecha").toLocalDateTime());
                    a.setIdentificacion(rs.getString("persona_identificacion"));
                    a.setPrimerNombre(rs.getString("persona_primer_nombre"));
                    a.setSegundoNombre(rs.getString("persona_segundo_nombre"));
                    a.setPrimerApellido(rs.getString("persona_primer_apellido"));
                    a.setSegundoApellido(rs.getString("persona_segundo_apellido"));
                    p.setNombre_PerLectivo(rs.getString("prd_lectivo_nombre"));
                    m.setAlumno(a);
                    m.setPeriodo(p);

                    matriculas.add(m);
                }
                return matriculas;
            } catch (SQLException e) {
                System.out.println("No se pudieron consultar matriculas. " + e.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Numero de matriculas hechas en un periodo lectivo
     *
     * @param idPrd
     * @return
     */
    public int numMaticuladosClases(int idPrd) {
        int num = 0;
        sql = "SELECT count(*) FROM public.\"AlumnoCurso\"\n"
                + "WHERE id_curso IN(\n"
                + "  SELECT id_curso\n"
                + "  FROM public.\"Cursos\"\n"
                + "  WHERE id_prd_lectivo = " + idPrd + ");";
        ResultSet rs = conecta.sql(sql);
        if (rs != null) {
            try {
                while (rs.next()) {
                    num = rs.getInt(1);
                }
                return num;
            } catch (SQLException e) {
                System.out.println("No podemos obetener el numero de matriculados en un periodo \n"
                        + e.getMessage());
                return 0;
            }
        } else {
            return 0;
        }
    }

    public int numMaticulados(int idPrd) {
        int num = 0;
        sql = "SELECT count(*) FROM public.\"Matricula\"\n"
                + "WHERE id_prd_lectivo = " + idPrd + ";";
        ResultSet rs = conecta.sql(sql);
        if (rs != null) {
            try {
                while (rs.next()) {
                    num = rs.getInt(1);
                }
                return num;
            } catch (SQLException e) {
                System.out.println("No podemos obetener el numero de matriculados en un periodo \n"
                        + e.getMessage());
                return 0;
            }
        } else {
            return 0;
        }
    }

}
