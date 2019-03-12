package modelo.materia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.ConectarDB;
import modelo.carrera.CarreraMD;

/**
 *
 * @author USUARIO
 */
public class MateriaBD extends MateriaMD {

    private final ConectarDB conecta;

    public MateriaBD(ConectarDB conecta) {
        this.conecta = conecta;
    }
   
    //para mostrar datos de la materia
    public ArrayList<MateriaMD> cargarMaterias() {
        String sql = "SELECT id_materia, id_carrera, id_eje, materia_codigo,"
                + " materia_nombre, materia_ciclo, materia_creditos, "
                + "materia_tipo, materia_categoria, materia_tipo_acreditacion, "
                + "materia_horas_docencia, materia_horas_practicas, "
                + "materia_horas_auto_estudio, materia_horas_presencial, "
                + "materia_total_horas, materia_activa, materia_objetivo,"
                + "materia_descripcion,materia_objetivo_especifico,"
                + "materia_organizacion_curricular,materia_campo_formacion\n"
                + "FROM public.\"Materias\" WHERE materia_activa = 'true';";
        return consultarMaterias(sql);
    }

    //Cargar datos de materia por carrera
    public ArrayList<MateriaMD> cargarMateriaPorCarrera(int idcarrera) {
        String sql = "SELECT id_materia, id_carrera, id_eje, materia_codigo,"
                + " materia_nombre, materia_ciclo, materia_creditos, "
                + "materia_tipo, materia_categoria, materia_tipo_acreditacion, "
                + "materia_horas_docencia, materia_horas_practicas, "
                + "materia_horas_auto_estudio, materia_horas_presencial, "
                + "materia_total_horas, materia_activa, materia_objetivo,"
                + "materia_descripcion,"
                + "materia_objetivo_especifico,materia_organizacion_curricular,materia_campo_formacion\n"
                + "FROM public.\"Materias\" WHERE materia_activa = 'true'"
                + "AND id_carrera= " + idcarrera + ";";
        return consultarMaterias(sql);
    }
    
    //Cargar datos de materia por carrera
    public ArrayList<MateriaMD> cargarMateriaPorCarreraCiclo(int idcarrera, int ciclo) {
        String sql = "SELECT id_materia, id_carrera, id_eje, materia_codigo,"
                + " materia_nombre, materia_ciclo, materia_creditos, "
                + "materia_tipo, materia_categoria, materia_tipo_acreditacion, "
                + "materia_horas_docencia, materia_horas_practicas, "
                + "materia_horas_auto_estudio, materia_horas_presencial, "
                + "materia_total_horas, materia_activa, materia_objetivo,"
                + "materia_descripcion,"
                + "materia_objetivo_especifico,materia_organizacion_curricular,materia_campo_formacion\n"
                + "FROM public.\"Materias\" WHERE materia_activa = 'true'"
                + "AND id_carrera= " + idcarrera + " "
                + "AND materia_ciclo = "+ciclo+";";
        return consultarMaterias(sql);
    }
    
    //Cargar todos los ciclos de una carrera  
    public ArrayList<Integer> cargarCiclosCarrera(int idCarrera){
        ArrayList<Integer> ciclos = new ArrayList(); 
        String sql = "SELECT DISTINCT materia_ciclo\n" +
"	FROM public.\"Materias\" \n" +
"	WHERE id_carrera = "+idCarrera+" ORDER BY materia_ciclo; "; 
        ResultSet rs = conecta.sql(sql); 
        try {
            if (rs != null) {
                while(rs.next()){
                    ciclos.add(rs.getInt("materia_ciclo"));
                }
                return ciclos; 
            }else{
                return null; 
            }
        } catch (SQLException e) {
            System.out.println("No pudimos cargar los ciclos de una carrera");
            System.out.println(e.getMessage());
            return null;
        }
    }

    //Metodo buscar por id de la materia
    public MateriaMD buscarMateria(int idmateria) {

        MateriaMD m = new MateriaMD();

        String sql = "SELECT id_materia, id_carrera, id_eje, materia_codigo,"
                + " materia_nombre, materia_ciclo, materia_creditos, "
                + "materia_tipo, materia_categoria, materia_tipo_acreditacion, "
                + "materia_horas_docencia, materia_horas_practicas, "
                + "materia_horas_auto_estudio, materia_horas_presencial, "
                + "materia_total_horas, materia_activa, materia_objetivo,"
                + "materia_descripcion,"
                + "materia_objetivo_especifico,materia_organizacion_curricular,materia_campo_formacion\n"
                + "FROM public.\"Materias\" WHERE materia_activa = 'true' "
                + "AND id_materia= " + idmateria + ";";
        ResultSet rs = conecta.sql(sql);

        try {
            if (rs != null) {

                while (rs.next()) {
                    m = obtenerMateria(rs);
                }
                return m;
            } else {
                System.out.println("No se pudo consultar carreras");
                return null;
            }

        } catch (SQLException ex) {
            System.out.println("No se pudo consultar carreras");
            System.out.println(ex.getMessage());
            return null;
        }

    }

    //Metodo buscar por aguja
    public ArrayList<MateriaMD> cargarMaterias(String aguja) {
        String sql = "SELECT * FROM public.\"Materias\"\n"
                + "WHERE \"materia_nombre\" ILIKE '%" + aguja + "%' "
                + "OR \"materia_codigo\" ILIKE '%" + aguja + "%';";
        return consultarMaterias(sql);
    }
    
    private ArrayList<MateriaMD> consultarMaterias(String sql){
        ArrayList<MateriaMD> lista = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    lista.add(obtenerMateria(rs));
                }
                return lista;
            } else {
                System.out.println("No se pudo consultar carreras");
                return null;
            }

        } catch (SQLException ex) {
            System.out.println("No se pudo consultar carreras");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public MateriaMD obtenerMateria(ResultSet rs) {
        MateriaMD m = new MateriaMD();

        try {
            m.setId(rs.getInt("id_materia"));
            //Aqui cargamos la carrera el id de la carrera
            //Deberiamos buscar carrera
            CarreraMD carrera = new CarreraMD();
            carrera.setId(rs.getInt("id_carrera"));
            m.setCarrera(carrera);

            EjeFormacionMD eje = new EjeFormacionMD();
            eje.setId(rs.getInt("id_eje"));
            m.setEje(eje);

            m.setCodigo(rs.getString("materia_codigo"));
            m.setNombre(rs.getString("materia_nombre"));
            m.setCiclo(rs.getInt("materia_ciclo"));

            if (rs.wasNull()) {
                m.setCreditos(0);
            } else {
                m.setCreditos(rs.getInt("materia_creditos"));
            }

            m.setTipo(rs.getString("materia_tipo").charAt(0));
            if (rs.wasNull()) {
                m.setCategoria(null);
            } else {
                m.setCategoria(rs.getString("materia_categoria"));
            }

            m.setTipoAcreditacion(rs.getString("materia_tipo_acreditacion").charAt(0));
            m.setHorasDocencia(rs.getInt("materia_horas_docencia"));
            m.setHorasPracticas(rs.getInt("materia_horas_practicas"));
            m.setHorasPresenciales(rs.getInt("materia_horas_presencial"));
            m.setHorasAutoEstudio(rs.getInt("materia_horas_auto_estudio"));
            m.setTotalHoras(rs.getInt("materia_total_horas"));
            m.setObjetivo(rs.getString("materia_objetivo"));
            m.setDescripcion(rs.getString("materia_descripcion"));

            if (rs.wasNull()) {
                m.setObjetivoespecifico(null);
            } else {
                m.setObjetivoespecifico("materia_objetivo_especifico");
            }

            if (rs.wasNull()) {
                m.setOrganizacioncurricular(null);
            } else {
                m.setOrganizacioncurricular("materia_organizacion_curricular");
            }

            if (rs.wasNull()) {
                m.setMateriacampoformacion(null);
            } else {
                m.setMateriacampoformacion("materia_campo_formacion");
            }
            return m;
        } catch (SQLException e) {
            System.out.println("No se pudo obtener la materia");
            System.out.println(e.getMessage());
            return null;
        }
    }

}
