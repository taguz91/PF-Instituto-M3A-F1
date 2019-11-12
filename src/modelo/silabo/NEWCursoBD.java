package modelo.silabo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.ConnDBPool;
import modelo.curso.CursoMD;
import modelo.silabo.mbd.ICursoBD;

/**
 *
 * @author gus
 */
public class NEWCursoBD implements ICursoBD {

    private final ConnDBPool CON = ConnDBPool.single();

    private static NEWCursoBD CBD;

    public static NEWCursoBD single() {
        if (CBD == null) {
            CBD = new NEWCursoBD();
        }
        return CBD;
    }

    @Override
    public List<CursoMD> getDocentePeriodoMateria(String nombreMateria, int idDocente, int idPeriodo) {
        String sql = "SELECT DISTINCT c.id_curso,"
                + "c.curso_nombre "
                + "FROM public.\"Cursos\" c "
                + "JOIN public.\"Docentes\" d ON c.id_docente = d.id_docente "
                + "JOIN public.\"Personas\" p ON d.id_persona = p.id_persona "
                + "JOIN public.\"Materias\" m on c.id_materia = m.id_materia\n"
                + "WHERE p.id_persona=? "
                + "AND c.id_prd_lectivo = ? "
                + "AND m.materia_nombre=?";
        PreparedStatement ps = CON.getPSPOOL(sql);
        List<CursoMD> CS = new ArrayList<>();

        try {
            ps.setInt(1, idDocente);
            ps.setInt(2, idPeriodo);
            ps.setString(3, nombreMateria);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                CursoMD cur = new CursoMD();
                cur.setId(res.getInt(1));
                cur.setNombre(res.getString(2));
                CS.add(cur);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al consultar cursos por periodo . "
                    + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return CS;
    }

    @Override
    public int getNumeroAlumnos(int idCurso) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CursoMD> getByCurso(int idCurso) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
