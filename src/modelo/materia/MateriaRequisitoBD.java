package modelo.materia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import utils.CONBD;
import utils.M;

/**
 *
 * @author Johnny
 */
public class MateriaRequisitoBD extends CONBD {

    private static MateriaRequisitoBD MRBD;

    public static MateriaRequisitoBD single() {
        if (MRBD == null) {
            MRBD = new MateriaRequisitoBD();
        }
        return MRBD;
    }

    public boolean insertarMateriaRequisito(MateriaRequisitoMD mr) {
        String nsql = "INSERT INTO public.\"MateriaRequisitos\"( "
                + " id_materia, "
                + "id_materia_requisito, "
                + "tipo_requisito)\n"
                + " VALUES (" + mr.getMateria().getId() + ","
                + mr.getMateriaRequisito().getId() + ", "
                + "'" + mr.getTipo() + "');";
        return CON.executeNoSQL(nsql);
    }

    public void eliminar(int idRequisito) {
        String nsql = "DELETE FROM public.\"MateriaRequisitos\" \n"
                + "WHERE id_requisito= " + idRequisito + "";
        if (CON.executeNoSQL(nsql)) {
            JOptionPane.showMessageDialog(null, "Se elimino correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar.");
        }
    }

    public void editar(MateriaRequisitoMD mr) {
        String nsql = "UPDATE public.\"MateriaRequisitos\" \n"
                + "SET id_materia = " + mr.getMateria().getId() + ", "
                + "id_materia_requisito = " + mr.getMateriaRequisito().getId() + ", "
                + "tipo_requisito= '" + mr.getTipo() + "' "
                + "WHERE id_requisito = " + mr.getId() + " ";
        if (CON.executeNoSQL(nsql)) {
            JOptionPane.showMessageDialog(null, "Se edito correctamente. " + mr.getMateria().getNombre());
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo editar " + mr.getMateria().getNombre());
        }
    }

    public ArrayList<MateriaRequisitoMD> buscarPreRequisitosDe(int idMateria) {
        String sql = "SELECT id_requisito, mr.id_materia_requisito, materia_nombre\n"
                + "FROM public.\"MateriaRequisitos\" mr, public.\"Materias\" m\n"
                + "WHERE mr.id_materia = " + idMateria + " AND tipo_requisito = 'P' AND\n"
                + "m.id_materia = id_materia_requisito;";
        return consultarMaterias(sql);
    }

    /**
     * Buscamos los co-requisitos de una materia
     *
     * @param idMateria
     * @return
     */
    public ArrayList<MateriaRequisitoMD> buscarCoRequisitosDe(int idMateria) {
        String sql = "SELECT id_requisito, mr.id_materia_requisito, materia_nombre\n"
                + "FROM public.\"MateriaRequisitos\" mr, public.\"Materias\" m\n"
                + "WHERE mr.id_materia = " + idMateria + " AND tipo_requisito = 'C' AND\n"
                + "m.id_materia = id_materia_requisito;";
        return consultarMaterias(sql);
    }

    /**
     * Buscamos todas las materias que son necesaria que este este co requisito
     *
     * @param idMateria
     * @return
     */
    public ArrayList<MateriaRequisitoMD> buscarDeQueEsCorequisito(int idMateria) {
        String sql = "SELECT id_requisito, mr.id_materia_requisito, mr.id_materia, materia_nombre\n"
                + "FROM public.\"MateriaRequisitos\" mr, public.\"Materias\" m\n"
                + "WHERE mr.id_materia_requisito = " + idMateria + " AND tipo_requisito = 'C' AND\n"
                + "m.id_materia = mr.id_materia;";
        ArrayList<MateriaRequisitoMD> requisitos = new ArrayList();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MateriaRequisitoMD mtr = new MateriaRequisitoMD();
                mtr.setId(rs.getInt("id_requisito"));
                MateriaMD mt = new MateriaMD();
                mt.setId(rs.getInt("id_materia"));
                mt.setNombre(rs.getString("materia_nombre"));
                mtr.setMateria(mt);

                requisitos.add(mtr);
            }
        } catch (SQLException e) {
            M.errorMsg("No pudimos consultar materias requisitos " + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return requisitos;
    }

    public ArrayList<MateriaRequisitoMD> buscarRequisitosPorCarrera(int idCarrera) {
        String sql = "SELECT id_requisito, mr.id_materia, id_materia_requisito, "
                + "materia_nombre, tipo_requisito "
                + "FROM public.\"MateriaRequisitos\" mr, "
                + "public.\"Materias\" m "
                + "WHERE mr.id_materia_requisito = m.id_materia AND "
                + "m.id_carrera = " + idCarrera + " ORDER BY id_materia;";
        ArrayList<MateriaRequisitoMD> requisitos = new ArrayList();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MateriaRequisitoMD mtr = new MateriaRequisitoMD();
                mtr.setId(rs.getInt("id_requisito"));
                MateriaMD m = new MateriaMD();
                m.setId(rs.getInt("id_materia"));
                mtr.setMateria(m);
                MateriaMD mr = new MateriaMD();
                mr.setId(rs.getInt("id_materia_requisito"));
                mr.setNombre(rs.getString("materia_nombre"));
                mtr.setMateriaRequisito(mr);
                mtr.setTipo(rs.getString("tipo_requisito"));

                requisitos.add(mtr);
            }
        } catch (SQLException e) {
            M.errorMsg("No pudimos consultar materias requisitos " + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return requisitos;
    }

    private ArrayList<MateriaRequisitoMD> consultarMaterias(String sql) {
        ArrayList<MateriaRequisitoMD> requisitos = new ArrayList();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MateriaRequisitoMD mtr = new MateriaRequisitoMD();
                mtr.setId(rs.getInt("id_requisito"));
                MateriaMD mt = new MateriaMD();
                mt.setId(rs.getInt("id_materia_requisito"));
                mt.setNombre(rs.getString("materia_nombre"));
                mtr.setMateriaRequisito(mt);

                requisitos.add(mtr);
            }
        } catch (SQLException e) {
            M.errorMsg("No pudimos consultar materias requisitos " + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return requisitos;
    }

}
