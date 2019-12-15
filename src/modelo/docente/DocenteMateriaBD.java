package modelo.docente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.materia.MateriaMD;
import modelo.persona.DocenteMD;
import utils.CONBD;
import utils.M;

/**
 *
 * @author Johnny
 */
public class DocenteMateriaBD extends CONBD {

    private static DocenteMateriaBD DMBD;

    public static DocenteMateriaBD single() {
        if (DMBD == null) {
            DMBD = new DocenteMateriaBD();
        }
        return DMBD;
    }

    public boolean guardar(DocenteMateriaMD dm) {
        String nsql = "INSERT INTO public.\"DocentesMateria\"(\n"
                + "id_docente, "
                + "id_materia) "
                + "VALUES (" + dm.getDocente().getIdDocente() + ", "
                + "" + dm.getMateria().getId() + ");";

        if (CON.executeNoSQL(nsql)) {
            JOptionPane.showMessageDialog(null,
                    "Asignamos correctamente \n"
                    + dm.getMateria().getNombre()
                    + " A \n" + dm.getDocente().getPrimerNombre() + " "
                    + dm.getDocente().getSegundoNombre()
                    + " " + dm.getDocente().getPrimerApellido()
                    + " " + dm.getDocente().getSegundoApellido());
            return true;
        } else {
            JOptionPane.showMessageDialog(null,
                    "No se pudo asignar  \n"
                    + dm.getMateria().getNombre()
                    + " A \n" + dm.getDocente().getPrimerNombre() + " "
                    + dm.getDocente().getSegundoNombre()
                    + " " + dm.getDocente().getPrimerApellido() + " "
                    + dm.getDocente().getSegundoApellido() + "\n"
                    + "Por favor compruebe su conexion a internet y vuelva a intentarlo.");
            return false;
        }
    }

    public void eliminar(int idDocenMat) {
        String nsql = "UPDATE public.\"DocentesMateria\""
                + " SET docente_mat_activo = false\n"
                + "WHERE id_docente_mat = " + idDocenMat + ";";
        if (CON.executeNoSQL(nsql)) {
            JOptionPane.showMessageDialog(null, "Se elimino correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar, compruebe su conexion.");
        }
    }

    public void activar(int idDocenMat) {
        String nsql = "UPDATE public.\"DocentesMateria\""
                + " SET docente_mat_activo = true\n"
                + "WHERE id_docente_mat = " + idDocenMat + ";";
        if (CON.executeNoSQL(nsql)) {
            JOptionPane.showMessageDialog(null, "Se activo correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo activar, compruebe su conexion.");
        }
    }

    public DocenteMateriaMD existeDocenteMateria(int idDocente, int idMateria) {
        DocenteMateriaMD dm = null;
        String sql = "SELECT id_docente_mat, docente_mat_activo\n"
                + "	FROM public.\"DocentesMateria\" \n"
                + "	WHERE id_docente = " + idDocente + " AND id_materia = " + idMateria + ";";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dm = new DocenteMateriaMD();
                dm.setId(rs.getInt("id_docente_mat"));
                dm.setActivo(rs.getBoolean("docente_mat_activo"));
            }
            return dm;
        } catch (SQLException e) {
            M.errorMsg("No se pudo consultar docente materia. " + e.getMessage());
            return null;
        } finally {
            CON.cerrarCONPS(ps);
        }
    }

    public ArrayList<DocenteMateriaMD> cargarDocenteMateria() {
        String sql = "SELECT id_docente_mat, dm.id_docente, dm.id_materia, \n"
                + "persona_primer_nombre, persona_primer_apellido, persona_identificacion,\n"
                + "materia_nombre\n"
                + "FROM public.\"DocentesMateria\" dm, public.\"Docentes\" d, public.\"Personas\" p, \n"
                + "public.\"Materias\" m, public.\"Carreras\" c \n"
                + "WHERE d.id_docente = dm.id_docente AND \n"
                + "p.id_persona = d.id_persona AND \n"
                + "m.id_materia = dm.id_materia AND \n"
                + "c.id_carrera = m.id_carrera AND \n"
                + "docente_mat_activo = true AND persona_activa = true "
                + "AND docente_activo = true;";
        return consultar(sql);
    }

    public ArrayList<DocenteMateriaMD> cargarDocenteMateriaPorDocente(int idDocente) {
        String sql = "SELECT id_docente_mat, dm.id_docente, dm.id_materia, \n"
                + "persona_primer_nombre, persona_primer_apellido, persona_identificacion,\n"
                + "materia_nombre\n"
                + "FROM public.\"DocentesMateria\" dm, public.\"Docentes\" d, public.\"Personas\" p, \n"
                + "public.\"Materias\" m, public.\"Carreras\" c \n"
                + "WHERE d.id_docente = dm.id_docente AND \n"
                + "p.id_persona = d.id_persona AND \n"
                + "m.id_materia = dm.id_materia AND "
                + "c.id_carrera = m.id_carrera AND \n"
                + "docente_mat_activo = true AND \n"
                + "id_docente = " + idDocente + " AND \n"
                + "docente_activo = true AND persona_activa = true AND \n"
                + "carrera_activo = true;";
        return consultar(sql);
    }

    public ArrayList<DocenteMateriaMD> cargarDocenteMateriaPorMateria(int idMateria) {
        String sql = "SELECT id_docente_mat, dm.id_docente, dm.id_materia, \n"
                + "persona_primer_nombre, persona_primer_apellido, persona_identificacion,\n"
                + "materia_nombre\n"
                + "FROM public.\"DocentesMateria\" dm, public.\"Docentes\" d, public.\"Personas\" p, \n"
                + "public.\"Materias\" m, public.\"Carreras\" c\n"
                + "WHERE d.id_docente = dm.id_docente AND \n"
                + "p.id_persona = d.id_persona AND \n"
                + "m.id_materia = dm.id_materia AND \n"
                + "c.id_carrera = m.id_carrera AND\n"
                + "docente_mat_activo = true AND \n"
                + "dm.id_materia = " + idMateria + " AND \n"
                + "carrera_activo = true AND docente_activo = true AND \n"
                + "persona_activa = true;";
        return consultar(sql);
    }

    public ArrayList<DocenteMateriaMD> cargarDocenteMateriaPorCarrera(int idCarrera) {
        String sql = "SELECT id_docente_mat, dm.id_docente, dm.id_materia, \n"
                + "persona_primer_nombre, persona_primer_apellido, persona_identificacion,\n"
                + "materia_nombre\n"
                + "FROM public.\"DocentesMateria\" dm, public.\"Docentes\" d, public.\"Personas\" p, \n"
                + "public.\"Materias\" m\n"
                + "WHERE d.id_docente = dm.id_docente AND \n"
                + "p.id_persona = d.id_persona AND \n"
                + "m.id_materia = dm.id_materia AND\n"
                + "docente_mat_activo = true AND \n"
                + "m.id_carrera = " + idCarrera + " AND \n"
                + "docente_activo = true AND persona_activa = true;";
        return consultar(sql);
    }

    public ArrayList<DocenteMateriaMD> cargarDocenteMateriaPorCarreraYCiclo(int idCarrera, int ciclo) {
        String sql = "SELECT id_docente_mat, dm.id_docente, dm.id_materia, \n"
                + "persona_primer_nombre, persona_primer_apellido, persona_identificacion,\n"
                + "materia_nombre\n"
                + "FROM public.\"DocentesMateria\" dm, public.\"Docentes\" d, public.\"Personas\" p, \n"
                + "public.\"Materias\" m, public.\"Carreras\" c\n"
                + "WHERE d.id_docente = dm.id_docente AND \n"
                + "p.id_persona = d.id_persona AND \n"
                + "m.id_materia = dm.id_materia AND \n"
                + "c.id_carrera = m.id_carrera AND \n"
                + "docente_mat_activo = true AND \n"
                + "m.id_carrera = " + idCarrera + " AND m.materia_ciclo = " + ciclo + " "
                + "AND carrera_activo = true AND persona_activa = true AND docente_activo = true;";
        return consultar(sql);
    }

    public ArrayList<DocenteMateriaMD> buscar(String aguja) {
        String sql = "SELECT id_docente_mat, dm.id_docente, dm.id_materia, \n"
                + "persona_primer_nombre, persona_primer_apellido, persona_identificacion,\n"
                + "materia_nombre\n"
                + "FROM public.\"DocentesMateria\" dm, public.\"Docentes\" d, public.\"Personas\" p, \n"
                + "public.\"Materias\" m, public.\"Carreras\" c\n"
                + "WHERE d.id_docente = dm.id_docente AND \n"
                + "p.id_persona = d.id_persona AND \n"
                + "m.id_materia = dm.id_materia AND \n"
                + "c.id_carrera = m.id_carrera AND \n"
                + "docente_mat_activo = true AND (\n"
                + "	materia_nombre ILIKE '%" + aguja + "%' OR \n"
                + "persona_primer_nombre || ' ' || persona_primer_apellido ILIKE '%" + aguja + "%'\n"
                + "OR persona_identificacion ILIKE '%" + aguja + "%' ) \n"
                + "AND carrera_activo = true AND persona_activa = true AND docente_activo = true;";
        return consultar(sql);
    }

    private ArrayList<DocenteMateriaMD> consultar(String sql) {
        ArrayList<DocenteMateriaMD> dms = new ArrayList();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DocenteMateriaMD dm = obtenerDocenteMateriaTbl(rs);
                if (dm != null) {
                    dms.add(dm);
                }
            }
        } catch (SQLException e) {
            M.errorMsg("No se pudo consultar docente materia. " + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return dms;
    }

    private DocenteMateriaMD obtenerDocenteMateriaTbl(ResultSet rs) {
        DocenteMateriaMD dm = new DocenteMateriaMD();
        try {
            dm.setId(rs.getInt("id_docente_mat"));
            DocenteMD d = new DocenteMD();
            d.setIdDocente(rs.getInt("id_docente_mat"));
            d.setPrimerNombre(rs.getString("persona_primer_nombre"));
            d.setPrimerApellido(rs.getString("persona_primer_apellido"));
            d.setIdentificacion(rs.getString("persona_identificacion"));
            MateriaMD m = new MateriaMD();
            m.setId(rs.getInt("id_materia"));
            m.setNombre(rs.getString("materia_nombre"));
            dm.setDocente(d);
            dm.setMateria(m);
            return dm;
        } catch (SQLException e) {
            return null;
        }
    }

}
