package modelo.materia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.ConectarDB;

/**
 *
 * @author USUARIO
 */
public class MateriaBD extends MateriaMD {

    ConectarDB conecta = new ConectarDB();
  
    //para mostrar datos de la materia
    public List<MateriaMD> cargarMateria() {
        try {
            List<MateriaMD> lista = new ArrayList();
            String sql = "select * from public.\"Materias\" ";
            ResultSet rs = conecta.sql(sql);

            while (rs.next()) {
                MateriaMD m = new MateriaMD();
                m.setId(rs.getInt("id_materia"));
                //Falta poner datos de carrera
                //m.setCarrera("carrera");
                m.setCodigo("materia_codigo");
                m.setNombre("materia_nombre");
                m.setCiclo(rs.getInt("materia_ciclo"));
                m.setCreditos(rs.getInt("materia_creditos"));
                m.setTipo(rs.getString("materia_tipo").charAt(0));
                m.setCategoria("materia_categoria");
                m.setEje("materia_eje");
                m.setTipoAcreditacion(rs.getString("materia_tipoacreditacion").charAt(0));
                m.setHorasTeoricas(rs.getInt("materia_horasteoricas"));
                m.setHorasPracticas(rs.getInt("materia_horasPracticas"));
                m.setHorasAutoEstudio(rs.getInt("materia_horasautoestudipo"));
                m.setTotalHoras(rs.getInt("materia_totalhoras"));
                lista.add(m);
            }

            return lista;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }

    }

    //Cargar datos de materia por carrera
    public List<MateriaMD> cargarMateriacarrera(String idcarrera) {
        try {
            List<MateriaMD> lista = new ArrayList();
            String sql = "select * from public.\"Materias\" WHERE id_carrera= " + idcarrera + ";";
            ResultSet rs = conecta.sql(sql);

            while (rs.next()) {
                MateriaMD m = new MateriaMD();
                  m.setId(rs.getInt("id_materia"));
                //Falta poner datos de carrera
                //m.setCarrera("carrera");
                m.setCodigo("materia_codigo");
                m.setNombre("materia_nombre");
                m.setCiclo(rs.getInt("materia_ciclo"));
                m.setCreditos(rs.getInt("materia_creditos"));
                m.setTipo(rs.getString("materia_tipo").charAt(0));
                m.setCategoria("materia_categoria");
                m.setEje("materia_eje");
                m.setTipoAcreditacion(rs.getString("materia_tipoacreditacion").charAt(0));
                m.setHorasTeoricas(rs.getInt("materia_horasteoricas"));
                m.setHorasPracticas(rs.getInt("materia_horasPracticas"));
                m.setHorasAutoEstudio(rs.getInt("materia_horasautoestudipo"));
                m.setTotalHoras(rs.getInt("materia_totalhoras"));
                lista.add(m);
            }

            return lista;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }

    }

    //Metodo buscar por id de la materia
    public MateriaBD buscarMateria(int idmateria) {

        MateriaBD m = new MateriaBD();

        String sql = "select * from public.\"Materias\" WHERE id_materia= " + idmateria + ";";
        ResultSet rs = conecta.sql(sql);
        try {
            while (rs.next()) {

                 m.setId(rs.getInt("id_materia"));
                //Falta poner datos de carrera
                //m.setCarrera("carrera");
                m.setCodigo("materia_codigo");
                m.setNombre("materia_nombre");
                m.setCiclo(rs.getInt("materia_ciclo"));
                m.setCreditos(rs.getInt("materia_creditos"));
                m.setTipo(rs.getString("materia_tipo").charAt(0));
                m.setCategoria("materia_categoria");
                m.setEje("materia_eje");
                m.setTipoAcreditacion(rs.getString("materia_tipoacreditacion").charAt(0));
                m.setHorasTeoricas(rs.getInt("materia_horasteoricas"));
                m.setHorasPracticas(rs.getInt("materia_horasPracticas"));
                m.setHorasAutoEstudio(rs.getInt("materia_horasautoestudipo"));
                m.setTotalHoras(rs.getInt("materia_totalhoras"));

            }
            return m;
        } catch (SQLException ex) {

            System.out.println("No pudimos cargar los Datos" + ex);
            return null;
        }

    }

    //eliminar Materia
    
    public void eliminarMateria(int codigo){
            String sql = "DELETE FROM public.\"Materias\"\n"
                + "WHERE \"id_codigo\" = '" + codigo + "'";
        
    }
    
    //Metodo buscar por aguja
    
    public List<MateriaMD> cargarMateria(String aguja) {
        try {
            List<MateriaMD> lista = new ArrayList();
            String sql = "SELECT * FROM public.\"Materias\"\n"
                    + "WHERE \"id_materia\" ILKE '%" + aguja + "%' "
                    + "WHERE \"materia_nombre\" ILKE '%" + aguja + "%'"
                    + "WHERE \"materia_codigo\" ILKE '%" + aguja + "%';";
            
            ResultSet rs = conecta.sql(sql);
            
              while (rs.next()) {
                MateriaMD m = new MateriaMD();
                  m.setId(rs.getInt("id_materia"));
                //Falta poner datos de carrera
                //m.setCarrera("carrera");
                m.setCodigo("materia_codigo");
                m.setNombre("materia_nombre");
                m.setCiclo(rs.getInt("materia_ciclo"));
                m.setCreditos(rs.getInt("materia_creditos"));
                m.setTipo(rs.getString("materia_tipo").charAt(0));
                m.setCategoria("materia_categoria");
                m.setEje("materia_eje");
                m.setTipoAcreditacion(rs.getString("materia_tipoacreditacion").charAt(0));
                m.setHorasTeoricas(rs.getInt("materia_horasteoricas"));
                m.setHorasPracticas(rs.getInt("materia_horasPracticas"));
                m.setHorasAutoEstudio(rs.getInt("materia_horasautoestudipo"));
                m.setTotalHoras(rs.getInt("materia_totalhoras"));
                lista.add(m);
            }
              rs.close();
              return lista;
            
        } catch (Exception ex) {
            System.out.println("No se cargaron los datos"+ex);
            return null;
        }

    }

 
}
