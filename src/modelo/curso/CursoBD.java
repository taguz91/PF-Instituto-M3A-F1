package modelo.curso;

import modelo.DAO.CursoDAO;
import modelo.DAO.pgConection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;

/**
 *
 * @author arman
 */
public class CursoBD extends CursoMD implements CursoDAO {
    
    MateriaBD m = new MateriaBD(); 
    PeriodoLectivoBD p= new PeriodoLectivoBD(); 
    DocenteBD d = new DocenteBD();

    private final String INSERT = "INSERT INTO public.\"Cursos\"(\n"
            + "	 id_materia, id_prd_lectivo, id_docente, curso_nombre, "
            + "curso_jornada, curso_capacidad, curso_ciclo, "
            + "curso_permiso_ingreso_nt)\n"
            + "	VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

    private final String SELECT_ALL = "SELECT id_curso, id_materia, id_prd_lectivo, id_docente, curso_nombre, curso_jornada, curso_capacidad, curso_ciclo, curso_permiso_ingreso_nt\n"
            + "	FROM public.\"Cursos\";";

    private final String UPDATE = "SELECT id_curso, id_materia, id_prd_lectivo, id_docente, curso_nombre, curso_jornada, curso_capacidad, curso_ciclo, curso_permiso_ingreso_nt\n"
            + "	FROM public.\"Cursos\";";

    private final String DELETE = "DELETE FROM public.\"Cursos\"\n"
            + "	WHERE <condition>;";
    
    private String SELECT_ONE(String aguja) {
        return "SELECT * FROM Cursos "
                + "WHERE \"curso_nombre\" LIKE '%" + aguja + "%'"
                + "OR \"curso_jornada\" LIKE '%" + aguja + "%'"
                + "OR \"id_docente\" LIKE '%" + aguja + "%'";
    }

    public CursoBD(int id_curso, MateriaMD id_materia, PeriodoLectivoMD id_prd_lectivo, DocenteMD id_docente, String curso_nombre, String curso_jornada, int curso_capacidad, int curso_ciclo, boolean permiso_ingreso_nt) {
        super(id_curso, id_materia, id_prd_lectivo, id_docente, curso_nombre, curso_jornada, curso_capacidad, curso_ciclo, permiso_ingreso_nt);
    }

    public CursoBD() {
    }

    @Override
    public void insertarCurso() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = pgConection.getConnection();
            stmt = conn.prepareStatement(INSERT);
            stmt.setInt(1, getId_materia().getId());
            stmt.setInt(2, getId_prd_lectivo().getId());
            stmt.setInt(3, getId_docente().getIdDocente());
            stmt.setString(4, getCurso_nombre());
            stmt.setString(5, getCurso_jornada());
            stmt.setInt(6, getCurso_capacidad());
            stmt.setInt(7, getCurso_ciclo());
            stmt.setBoolean(8, isPermiso_ingreso_nt());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("No se pudo ingresar carrera "+e.getMessage());
        } finally {
            pgConection.close(conn);
            pgConection.close(stmt);
        }
    }

    @Override
    public List<CursoMD> SelectAll() {
        List<CursoMD> curso = new ArrayList<>();
        CursoMD filtroCurso;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = pgConection.getConnection();
            stmt = conn.prepareStatement(SELECT_ALL);
            rs = stmt.executeQuery();
            while (rs.next()) {
                filtroCurso = new CursoMD();
                filtroCurso.setId_curso(rs.getInt(1));
                filtroCurso.setCurso_nombre(rs.getString(2));
                //Consultamos materia 
                MateriaMD mate= m.buscarMateria(rs.getInt(3)); 
                
                filtroCurso.setId_materia(mate);
                //Consultamos periodo lectivo
                PeriodoLectivoMD peri = p.capturarPerLectivo(rs.getInt(4));
                filtroCurso.setId_prd_lectivo(peri);
                // consultamos el docente  
                DocenteMD dcnt =  d.buscarDocenteid(rs.getInt(5));
                filtroCurso.setId_docente(dcnt);
                
                
                filtroCurso.setCurso_jornada(rs.getString(6));
                filtroCurso.setCurso_nombre(rs.getString(7));
                filtroCurso.setCurso_capacidad(rs.getInt(8));
                filtroCurso.setCurso_ciclo(rs.getInt(9));
                curso.add(filtroCurso);
            }
        } catch (SQLException e) {
            System.out.println("No se pudo consultar cursos "+e.getMessage());
        } finally {
            pgConection.close(conn);
            pgConection.close(stmt);
            pgConection.close(rs);
        }
        return curso;
    }

    @Override
    public void modificarCurso(String pk) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
           conn = pgConection.getConnection();
            stmt = conn.prepareStatement(UPDATE);
            stmt.setInt(1, getId_materia().getId());
            stmt.setInt(2, getId_prd_lectivo().getId());
            stmt.setInt(3, getId_docente().getIdDocente());
            stmt.setString(4, getCurso_nombre());
            stmt.setString(5, getCurso_jornada());
            stmt.setInt(6, getCurso_capacidad());
            stmt.setInt(7, getCurso_ciclo());
            stmt.setBoolean(8, isPermiso_ingreso_nt());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pgConection.close(conn);
            pgConection.close(stmt);
        }
    }

    @Override
    public void eliminarCurso(String pk) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = pgConection.getConnection();
            stmt = conn.prepareStatement(DELETE);
            stmt.setString(1, pk);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            pgConection.close(conn);
            pgConection.close(stmt);
        }
    }

    @Override
    public List<CursoMD> SelectOne(String aguja) {
        List<CursoMD> curso = new ArrayList<>();
        CursoMD filtroCurso = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = pgConection.getConnection();
            stmt = conn.prepareStatement(SELECT_ONE(aguja));
            rs = stmt.executeQuery();
            while (rs.next()) {
                filtroCurso = new CursoMD();
                filtroCurso.setId_curso(rs.getInt(1));
                filtroCurso.setCurso_nombre(rs.getString(2));
                //Consultamos materia 
                MateriaMD mate= m.buscarMateria(rs.getInt(3)); 
                
                filtroCurso.setId_materia(mate);
                //Consultamos periodo lectivo
                PeriodoLectivoMD peri = p.capturarPerLectivo(rs.getInt(4));
                filtroCurso.setId_prd_lectivo(peri);
                // consultamos el docente  
                DocenteMD dcnt =  d.buscarDocenteid(rs.getInt(5));
                filtroCurso.setId_docente(dcnt);
                
                
                filtroCurso.setCurso_jornada(rs.getString(6));
                filtroCurso.setCurso_nombre(rs.getString(7));
                filtroCurso.setCurso_capacidad(rs.getInt(8));
                filtroCurso.setCurso_ciclo(rs.getInt(9));
                curso.add(filtroCurso);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pgConection.close(conn);
            pgConection.close(stmt);
            pgConection.close(rs);
        }
        return curso;
    }
}
