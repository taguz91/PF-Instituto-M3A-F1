package modelo.materia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.ConectarDB;

/**
 *
 * @author Johnny
 */
public class MateriaRequisitoBD extends MateriaRequisitoMD {

    private final ConectarDB conecta;

    public MateriaRequisitoBD(ConectarDB conecta) {
        this.conecta = conecta;
    }

    public boolean insertarMateriaRequisito() {

        String nsql = "INSERT INTO public.\"MateriaRequisitos\"(\n"
                + " id_materia, id_materia_requisito, tipo_requisito)\n"
                + " VALUES (" + getMateria().getId() + "," + getMateriaRequisito().getId() + ", '" + getTipo() + "');";
        PreparedStatement ps = conecta.getPS(nsql);
        return conecta.nosql(ps) == null;
    }

    public void eliminar(int idRequisito) {
        String nsql = "DELETE FROM public.\"MateriaRequisitos\" \n"
                + "WHERE id_requisito= " + idRequisito + "";
        PreparedStatement ps = conecta.getPS(nsql);
        if (conecta.nosql(ps) == null) {
            JOptionPane.showMessageDialog(null, "Se elimino correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar.");
        }
    }

    public void editar(int idRequisito) {
        String nsql = "UPDATE public.\"MateriaRequisitos\" \n"
                + "SET id_materia = " + getMateria().getId() + ", id_materia_requisito = " + getMateriaRequisito().getId() + ",\n"
                + " tipo_requisito= '" + getTipo() + "'\n"
                + "WHERE id_requisito = " + idRequisito + " ";
        PreparedStatement ps = conecta.getPS(nsql);
        if (conecta.nosql(ps) == null) {
            JOptionPane.showMessageDialog(null, "Se edito correctamente. " + getMateria().getNombre());
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo editar " + getMateria().getNombre());
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
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            if (rs != null) {
                while (rs.next()) {
                    MateriaRequisitoMD mtr = new MateriaRequisitoMD();
                    mtr.setId(rs.getInt("id_requisito"));
                    MateriaMD mt = new MateriaMD();
                    mt.setId(rs.getInt("id_materia"));
                    mt.setNombre(rs.getString("materia_nombre"));
                    mtr.setMateria(mt);

                    requisitos.add(mtr);
                }
                ps.getConnection().close();
                return requisitos;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No pudimos consultar materias requisitos " + e.getMessage());
            return null;
        }
    }

    public ArrayList<MateriaRequisitoMD> buscarRequisitosPorCarrera(int idCarrera) {
        String sql = "SELECT id_requisito, mr.id_materia, id_materia_requisito, \n"
                + "materia_nombre, tipo_requisito \n"
                + "FROM public.\"MateriaRequisitos\" mr, public.\"Materias\" m \n"
                + "WHERE mr.id_materia_requisito = m.id_materia AND\n"
                + "m.id_carrera = " + idCarrera + " ORDER BY id_materia;";
        ArrayList<MateriaRequisitoMD> requisitos = new ArrayList();
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            if (rs != null) {
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
                ps.getConnection().close();
                return requisitos;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No pudimos consultar materias requisitos" + e.getMessage());
            return null;
        }
    }

    private ArrayList<MateriaRequisitoMD> consultarMaterias(String sql) {
        ArrayList<MateriaRequisitoMD> requisitos = new ArrayList();
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            if (rs != null) {
                while (rs.next()) {
                    MateriaRequisitoMD mtr = new MateriaRequisitoMD();
                    mtr.setId(rs.getInt("id_requisito"));
                    MateriaMD mt = new MateriaMD();
                    mt.setId(rs.getInt("id_materia_requisito"));
                    mt.setNombre(rs.getString("materia_nombre"));
                    mtr.setMateriaRequisito(mt);

                    requisitos.add(mtr);
                }
                ps.getConnection().close();
                return requisitos;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No pudimos consultar materias requisitos");
            return null;
        }
    }

}
