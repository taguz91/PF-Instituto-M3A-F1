package modelo.materia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.ConectarDB;

/**
 *
 * @author Johnny
 */
public class MateriaRequisitoBD extends MateriaRequisitoMD {

    private final ConectarDB conecta;
    private final MateriaBD mat;

    public MateriaRequisitoBD(ConectarDB conecta) {
        this.conecta = conecta;
        this.mat = new MateriaBD(conecta);
    }

    public ArrayList<MateriaRequisitoMD> buscarPreRequisitos(int idMateria) {
        String sql = "SELECT id_requisito, mr.id_materia_requisito, materia_nombre\n"
                + "FROM public.\"MateriaRequisitos\" mr, public.\"Materias\" m\n"
                + "WHERE mr.id_materia = "+idMateria+" AND tipo_requisito = 'P' AND\n"
                + "m.id_materia = id_materia_requisito;";
        return consultarMaterias(sql);
    }

    public ArrayList<MateriaRequisitoMD> buscarCoRequisitos(int idMateria) {
        String sql = "SELECT id_requisito, mr.id_materia_requisito, materia_nombre\n"
                + "FROM public.\"MateriaRequisitos\" mr, public.\"Materias\" m\n"
                + "WHERE mr.id_materia = "+idMateria+" AND tipo_requisito = 'C' AND\n"
                + "m.id_materia = id_materia_requisito;";
        return consultarMaterias(sql);
    }

    private ArrayList<MateriaRequisitoMD> consultarMaterias(String sql) {
        ArrayList<MateriaRequisitoMD> requisitos = new ArrayList();
        ResultSet rs = conecta.sql(sql);
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
                return requisitos;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No pudimos consultar materias requisitos");
            return null;
        }
    }

    private ArrayList<MateriaRequisitoMD> consultarMateriasRequisito(String sql, int idMateria) {
        ArrayList<MateriaRequisitoMD> requisitos = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                MateriaMD m = mat.buscarMateria(idMateria);
                while (rs.next()) {
                    requisitos.add(obtenerRequisito(rs, m));
                }
                return requisitos;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No pudimos consultar materias requisitos");
            return null;
        }
    }

    private MateriaRequisitoMD obtenerRequisito(ResultSet rs, MateriaMD m) {
        MateriaRequisitoMD materia = new MateriaRequisitoMD();
        try {
            materia.setId(rs.getInt("id_requisito"));
            materia.setMateria(m);
            MateriaMD mr = mat.buscarMateriaPorReferencia(rs.getInt("id_materia_requisito"));
            materia.setMateriaRequisito(mr);
            materia.setTipo(rs.getString("tipo_requisito"));
            return materia;
        } catch (SQLException e) {
            System.out.println("No pudimos obtener materia");
            System.out.println(e.getMessage());
            return null;
        }
    }

}
