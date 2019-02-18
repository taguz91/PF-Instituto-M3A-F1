package modelo.materia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.ConectarDB;

/**
 *
 * @author USUARIO
 */
public class MateriaBD extends MateriaMD {

    ConectarDB conecta = new ConectarDB();

    //para mostrar datos de la materia
    public ArrayList<MateriaMD> cargarMateria() {
        try {
            ArrayList<MateriaMD> lista = new ArrayList();
            String sql = "SELECT id_materia, id_carrera, materia_codigo, "
                    + "materia_nombre, materia_ciclo, materia_creditos,"
                    + " materia_tipo, materia_categoria, "
                    + "materia_tipo_acreditacion, materia_horas_docencia,"
                    + " materia_horas_practicas, materia_horas_auto_estudio,"
                    + " materia_total_horas, materia_activa, materia_objetivo,"
                    + " materia_descripcion\n"
                    + "	FROM public.\"Materias\";";
            ResultSet rs = conecta.sql(sql);

            if (rs != null) {

                while (rs.next()) {
                    MateriaMD m = new MateriaMD();
                    
                    m.setId(rs.getInt("id_materia"));
                    //Falta poner datos de carrera
                    //m.setCarrera("carrera");
                    m.setCodigo(rs.getString("materia_codigo"));
                    m.setNombre(rs.getString("materia_nombre"));
                    m.setCiclo(rs.getInt("materia_ciclo"));
                    m.setCreditos(rs.getInt("materia_creditos"));
                    m.setTipo(rs.getString("materia_tipo").charAt(0));
                    m.setCategoria(rs.getString("materia_categoria"));
                    m.setTipoAcreditacion(rs.getString("materia_tipo_acreditacion").charAt(0));
                    m.setHorasTeoricas(rs.getInt("materia_horas_docencia"));
                    m.setHorasPracticas(rs.getInt("materia_horas_practicas"));
                    m.setHorasAutoEstudio(rs.getInt("materia_horas_auto_estudio"));
                    m.setTotalHoras(rs.getInt("materia_total_horas"));
                    lista.add(m);
                }
                return lista;
            } else {
                return null;
            }

        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }

    }

    //Cargar datos de materia por carrera
    public ArrayList<MateriaBD> cargarMateriacarrera(String idcarrera) {
        try {
            ArrayList<MateriaBD> lista = new ArrayList();
            String sql = "select * from public.\"Materias\" WHERE id_carrera= " + idcarrera + ";";
            ResultSet rs = conecta.sql(sql);

            while (rs.next()) {
                MateriaBD m = new MateriaBD();
                m.setId(rs.getInt("id_materia"));
                //Falta poner datos de carrera
                //m.setCarrera("carrera");
                m.setCodigo("materia_codigo");
                m.setNombre("materia_nombre");
                m.setCiclo(rs.getInt("materia_ciclo"));
                m.setCreditos(rs.getInt("materia_creditos"));
                m.setTipo(rs.getString("materia_tipo").charAt(0));
                m.setCategoria("materia_categoria");
                m.setTipoAcreditacion(rs.getString("materia_tipoacreditacion").charAt(0));
                m.setHorasTeoricas(rs.getInt("materia_horas_docencia"));
                m.setHorasPracticas(rs.getInt("materia_horas_practicas"));
                m.setHorasAutoEstudio(rs.getInt("materia_horas_auto_estudipo"));
                m.setTotalHoras(rs.getInt("materia_total_horas"));

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
        try {
            String sql = "select * from public.\"Materias\" WHERE id_materia= " + idmateria + ";";
            ResultSet rs = conecta.sql(sql);
            if (rs != null) {

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
                    m.setTipoAcreditacion(rs.getString("materia_tipoacreditacion").charAt(0));
                    m.setHorasTeoricas(rs.getInt("materia_horas_docencia"));
                    m.setHorasPracticas(rs.getInt("materia_horas_practicas"));
                    m.setHorasAutoEstudio(rs.getInt("materia_horas_auto_estudipo"));
                    m.setTotalHoras(rs.getInt("materia_total_horas"));

                }
                return m;
            } else {
                return null;
            }
        } catch (SQLException ex) {

            System.out.println("No pudimos cargar los Datos" + ex);
            return null;
        }

    }

    /*
    //eliminar Materia
    public void eliminarMateria(int codigo) {
        String sql = "DELETE FROM public.\"Materias\"\n"
                + "WHERE \"id_codigo\" = '" + codigo + "'";
    }*/
    //Metodo buscar por aguja
    public ArrayList<MateriaBD> cargarMateria(String aguja) {

        ArrayList<MateriaBD> lista = new ArrayList();
        String sql = "SELECT * FROM public.\"Materias\"\n"
                + "WHERE \"id_materia\" ILKE '%" + aguja + "%' "
                + "WHERE \"materia_nombre\" ILKE '%" + aguja + "%'"
                + "WHERE \"materia_codigo\" ILKE '%" + aguja + "%';";
        try {
            ResultSet rs = conecta.sql(sql);

            if (rs != null) {

                while (rs.next()) {
                    MateriaBD m = new MateriaBD();
                    m.setId(rs.getInt("id_materia"));
                    //Falta poner datos de carrera
                    //m.setCarrera("carrera");
                    m.setCodigo("materia_codigo");
                    m.setNombre("materia_nombre");
                    m.setCiclo(rs.getInt("materia_ciclo"));
                    m.setCreditos(rs.getInt("materia_creditos"));
                    m.setTipo(rs.getString("materia_tipo").charAt(0));
                    m.setCategoria("materia_categoria");
                    m.setTipoAcreditacion(rs.getString("materia_tipoacreditacion").charAt(0));
                    m.setHorasTeoricas(rs.getInt("materia_horas_docencia"));
                    m.setHorasPracticas(rs.getInt("materia_horas_practicas"));
                    m.setHorasAutoEstudio(rs.getInt("materia_horas_auto_estudipo"));
                    m.setTotalHoras(rs.getInt("materia_total_horas"));
                    lista.add(m);
                }
                rs.close();
                return lista;
            } else {
                return null;
            }

        } catch (SQLException ex) {
            System.out.println("No se cargaron los datos" + ex);
            return null;
        }

    }

}
