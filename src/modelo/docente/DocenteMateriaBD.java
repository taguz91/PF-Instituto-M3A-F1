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
        String sql = "SELECT id_docente_mat, id_docente, id_materia, docente_mat_activo\n"
                + "FROM public.\"DocentesMateria\" WHERE docente_mat_activo = true";
        return consultar(sql);
    }
    
    public ArrayList<DocenteMateriaMD> cargarDocenteMateriaPorDocente(int idDocente) {
        String sql = "SELECT id_docente_mat, id_docente, id_materia, docente_mat_activo\n"
                + "FROM public.\"DocentesMateria\" WHERE docente_mat_activo = true "
                + "AND id_docente = " + idDocente + ";";
        return cosultarPorDocente(sql, idDocente);
    }
    
    public ArrayList<DocenteMateriaMD> cargarDocenteMateriaPorMateria(int idMateria) {
        String sql = "SELECT id_docente_mat, id_docente, id_materia, docente_mat_activo\n"
                + "FROM public.\"DocentesMateria\" WHERE docente_mat_activo = true "
                + "AND id_materia = " + idMateria + ";";
        return cosultarPorMateria(sql, idMateria);
    }
    
    public ArrayList<DocenteMateriaMD> cargarDocenteMateriaPorCarrera(int idCarrera) {
        String sql = "SELECT id_docente_mat, id_docente, m.id_materia, docente_mat_activo\n"
                + "	FROM public.\"DocentesMateria\" dm, public.\"Materias\" m\n"
                + "	WHERE dm.id_materia = m.id_materia AND\n"
                + "	m.id_carrera = " + idCarrera + ";";
        return consultar(sql);
    }
    
    public ArrayList<DocenteMateriaMD> cargarDocenteMateriaPorCarreraYCiclo(int idCarrera, int ciclo) {
        String sql = "SELECT id_docente_mat, id_docente, m.id_materia, docente_mat_activo\n"
                + "	FROM public.\"DocentesMateria\" dm, public.\"Materias\" m\n"
                + "	WHERE dm.id_materia = m.id_materia AND\n"
                + "	m.id_carrera = " + idCarrera + " AND m.materia_ciclo = "+ciclo+";";
        return consultar(sql);
    }
    
    private ArrayList<DocenteMateriaMD> consultar(String sql) {
        ArrayList<DocenteMateriaMD> dms = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        if (rs != null) {
            try {
                while (rs.next()) {
                    DocenteMateriaMD dm = obtenerDocenteMateria(rs, null, null);
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
    
    private ArrayList<DocenteMateriaMD> cosultarPorDocente(String sql, int idDocente) {
        ArrayList<DocenteMateriaMD> dms = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        if (rs != null) {
            try {
                DocenteMD d = doc.buscarDocente(idDocente);
                while (rs.next()) {
                    DocenteMateriaMD dm = obtenerDocenteMateria(rs, d, null);
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
    
    private ArrayList<DocenteMateriaMD> cosultarPorMateria(String sql, int idMateria) {
        ArrayList<DocenteMateriaMD> dms = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        if (rs != null) {
            try {
                MateriaMD m = mat.buscarMateriaPorReferencia(idMateria);
                while (rs.next()) {
                    DocenteMateriaMD dm = obtenerDocenteMateria(rs, null, m);
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
    
}
