package modelo.docente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.ConectarDB;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;

/**
 *
 * @author Johnny
 */
public class DocenteMateriaBD extends DocenteMateriaMD {

    private final ConectarDB conecta;
    private final DocenteBD doc;
    private final MateriaBD mat;

    public DocenteMateriaBD(ConectarDB conecta) {
        this.conecta = conecta;
        this.doc = new DocenteBD(conecta);
        this.mat = new MateriaBD(conecta);
    }

    public void guardar() {
        String nsql = "INSERT INTO public.\"DocentesMateria\"(\n"
                + "	id_docente, id_materia)\n"
                + "	VALUES (" + getDocente().getIdDocente() + ", " + getMateria().getId() + ");";

        if (conecta.nosql(nsql) == null) {
            JOptionPane.showMessageDialog(null, "Asignamos correctamente \n" + getMateria().getNombre()
                    + " A \n" + getDocente().getPrimerNombre() + " " + getDocente().getSegundoNombre()
                    + " " + getDocente().getPrimerApellido() + " " + getDocente().getSegundoApellido());
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
                + "c.id_carrera = m.id_carrera\n"
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
                + "c.id_carrera = m.id_carrera \n"
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
                + "	materia_nombre ILIKE '%"+aguja+"%' OR \n"
                + "persona_primer_nombre || ' ' || persona_primer_apellido ILIKE '%"+aguja+"%'\n"
                + "OR persona_identificacion ILIKE '%"+aguja+"%' ) \n"
                + "AND carrera_activo = true AND persona_activa = true AND docente_activo = true;";
        return consultar(sql);
    }

    private ArrayList<DocenteMateriaMD> consultar(String sql) {
        ArrayList<DocenteMateriaMD> dms = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        if (rs != null) {
            try {
                while (rs.next()) {
                    DocenteMateriaMD dm = obtenerDocenteMateriaTbl(rs);
                    if (dm != null) {
                        dms.add(dm);
                    }
                }
                return dms;
            } catch (SQLException e) {
                System.out.println("No se pudo consultar docentes");
                System.out.println(e.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }

    private DocenteMateriaMD obtenerDocenteMateria(ResultSet rs, DocenteMD d, MateriaMD m) {
        DocenteMateriaMD dm = new DocenteMateriaMD();
        try {
            dm.setId(rs.getInt("id_docente_mat"));
            if (d == null) {
                d = doc.buscarDocenteParaReferencia(rs.getInt("id_docente"));
            }
            dm.setDocente(d);
            if (m == null) {
                m = mat.buscarMateriaPorReferencia(rs.getInt("id_materia"));
            }
            dm.setMateria(m);
            return dm;
        } catch (SQLException e) {
            return null;
        }
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
