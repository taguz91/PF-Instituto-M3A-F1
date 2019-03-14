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
        String sql = "SELECT id_requisito, id_materia, id_materia_requisito, tipo_requisito\n"
                + "	FROM public.\"MateriaRequisitos\"\n"
                + "	WHERE id_materia = " + idMateria + " and tipo_requisito = 'P';";
        return consultarMateriasRequisito(sql, idMateria);
    }
    
    public ArrayList<MateriaRequisitoMD> buscarCoRequisitos(int idMateria) {
        String sql = "SELECT id_requisito, id_materia, id_materia_requisito, tipo_requisito\n"
                + "	FROM public.\"MateriaRequisitos\"\n"
                + "	WHERE id_materia = " + idMateria + " and tipo_requisito = 'C';";
        return consultarMateriasRequisito(sql, idMateria);
    }
    
    private ArrayList<MateriaRequisitoMD> consultarMateriasRequisito(String sql, int idMateria){
        ArrayList<MateriaRequisitoMD> requisitos = new ArrayList(); 
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                MateriaMD m = mat.buscarMateria(idMateria);
                while(rs.next()){
                    requisitos.add(obtenerRequisito(rs, m));
                }
                return requisitos;
            }else{
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
            MateriaMD mr = mat.buscarMateria(rs.getInt("id_materia_requisito"));            
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
