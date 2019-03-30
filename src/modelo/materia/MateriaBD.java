package modelo.materia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.ConectarDB;
import modelo.ResourceManager;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;

/**
 *
 * @author USUARIOXD
 */
public class MateriaBD extends MateriaMD {

    private final ConectarDB conecta;
    private final CarreraBD car;

    public MateriaBD(ConectarDB conecta) {
        this.conecta = conecta;
        this.car = new CarreraBD(conecta);
    }

    //para mostrar datos de la materia
    public ArrayList<MateriaMD> cargarMaterias() {
        String sql = "SELECT id_materia, materia_codigo,"
                + " materia_nombre, materia_ciclo, "
                + "materia_horas_docencia, materia_horas_practicas, "
                + "materia_horas_auto_estudio, materia_horas_presencial, "
                + "materia_total_horas\n"
                + "FROM public.\"Materias\" WHERE materia_activa = 'true';";
        return consultarMateriasParaTabla(sql);
    }

    //Cargar datos de materia por carrera
    public ArrayList<MateriaMD> cargarMateriaPorCarrera(int idcarrera) {
        String sql = "SELECT id_materia, materia_codigo,"
                + " materia_nombre, materia_ciclo, "
                + "materia_horas_docencia, materia_horas_practicas, "
                + "materia_horas_auto_estudio, materia_horas_presencial, "
                + "materia_total_horas\n"
                + "FROM public.\"Materias\" WHERE materia_activa = 'true'"
                + "AND id_carrera= " + idcarrera + ";";
        return consultarMateriasParaTabla(sql);
    }

    //Cargar datos de materia por carrera
    public ArrayList<MateriaMD> cargarMateriaPorCarreraCiclo(int idcarrera, int ciclo) {
        String sql = "SELECT id_materia, materia_codigo,"
                + " materia_nombre, materia_ciclo, "
                + "materia_horas_docencia, materia_horas_practicas, "
                + "materia_horas_auto_estudio, materia_horas_presencial, "
                + "materia_total_horas\n"
                + "FROM public.\"Materias\" WHERE materia_activa = 'true'"
                + "AND id_carrera= " + idcarrera + " "
                + "AND materia_ciclo = " + ciclo + ";";
        return consultarMateriasParaTabla(sql);
    }

    //Cargar datos de materia por carrera ciclo para ver sus materias onli materias
    public ArrayList<MateriaMD> buscarMateriaPorCarreraCiclo(int idcarrera, int ciclo) {
        String sql = "SELECT id_materia, materia_codigo,"
                + " materia_nombre \n"
                + "FROM public.\"Materias\" WHERE materia_activa = 'true'"
                + "AND id_carrera= " + idcarrera + " "
                + "AND materia_ciclo = " + ciclo + ";";
        ArrayList<MateriaMD> lista = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                MateriaMD m;
                while (rs.next()) {
                    m = new MateriaMD();
                    m.setId(rs.getInt("id_materia"));
                    m.setCodigo(rs.getString("materia_codigo"));
                    m.setNombre(rs.getString("materia_nombre"));
                    lista.add(m);
                }
                return lista;
            } else {
                System.out.println("No se pudo buscar materias para referencia");
                return null;
            }

        } catch (SQLException ex) {
            System.out.println("No se pudo buscar materias para referencia");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    //Cargar todos los ciclos de una carrera  
    public ArrayList<Integer> cargarCiclosCarrera(int idCarrera) {
        ArrayList<Integer> ciclos = new ArrayList();
        String sql = "SELECT DISTINCT materia_ciclo\n"
                + "	FROM public.\"Materias\" \n"
                + "	WHERE id_carrera = " + idCarrera + " ORDER BY materia_ciclo; ";
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    ciclos.add(rs.getInt("materia_ciclo"));
                }
                return ciclos;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No pudimos cargar los ciclos de una carrera");
            System.out.println(e.getMessage());
            return null;
        }
    }

    //Buscar materia para informacion
    public MateriaMD buscarMateriaInfo(int idmateria) {
        MateriaMD m = new MateriaMD();
        String sql = "SELECT id_materia, m.id_carrera, materia_codigo, \n"
                + "materia_nombre, carrera_nombre\n"
                + "FROM public.\"Materias\" m, public.\"Carreras\" c\n"
                + "WHERE id_materia = "+idmateria+" AND\n"
                + "c.id_carrera = m.id_carrera;";
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    m.setId(rs.getInt("id_materia"));
                    m.setCodigo(rs.getString("materia_codigo"));
                    m.setNombre(rs.getString("materia_nombre"));
                    CarreraMD cr = new CarreraMD(); 
                    cr.setId(rs.getInt("id_carrera"));
                    cr.setNombre(rs.getString("carrera_nombre"));
                    m.setCarrera(cr);
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

    //Metodo buscar por id de la materia
    public MateriaMD buscarMateriaPorReferencia(int idmateria) {
        MateriaMD m = new MateriaMD();
        String sql = "SELECT id_materia, id_carrera, "
                + " materia_nombre, materia_ciclo\n"
                + "FROM public.\"Materias\" WHERE materia_activa = 'true' "
                + "AND id_materia= " + idmateria + ";";
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {

                    m.setId(rs.getInt("id_materia"));
                    m.setNombre(rs.getString("materia_nombre"));
                    m.setCiclo(rs.getInt("materia_ciclo"));
                }
                return m;
            } else {
                System.out.println("No se pudo consultar materia para referencia");
                return null;
            }
        } catch (SQLException ex) {
            System.out.println("No se pudo consultar materia para referencia");
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

    private ArrayList<MateriaMD> consultarMaterias(String sql) {
        ArrayList<MateriaMD> lista = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    lista.add(obtenerMateria(rs));
                }
                return lista;
            } else {
                System.out.println("No se pudo consultar materias");
                return null;
            }

        } catch (SQLException ex) {
            System.out.println("No se pudo consultar materias");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    private ArrayList<MateriaMD> consultarMateriasParaTabla(String sql) {
        ArrayList<MateriaMD> lista = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                MateriaMD m;
                while (rs.next()) {
                    m = new MateriaMD();
                    m.setId(rs.getInt("id_materia"));
                    m.setCodigo(rs.getString("materia_codigo"));
                    m.setNombre(rs.getString("materia_nombre"));
                    m.setCiclo(rs.getInt("materia_ciclo"));
                    m.setHorasDocencia(rs.getInt("materia_horas_docencia"));
                    m.setHorasPracticas(rs.getInt("materia_horas_practicas"));
                    m.setHorasPresenciales(rs.getInt("materia_horas_presencial"));
                    m.setHorasAutoEstudio(rs.getInt("materia_horas_auto_estudio"));
                    m.setTotalHoras(rs.getInt("materia_total_horas"));
                    lista.add(m);
                }
                return lista;
            } else {
                System.out.println("No se pudo consultar materias para tabla");
                return null;
            }

        } catch (SQLException ex) {
            System.out.println("No se pudo consultar materias para tabla");
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
            CarreraMD carrera = car.buscarParaReferencia(rs.getInt("id_carrera"));
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

    public static List<String> selectWhere(int idDocente, int ciclo, String paralelo, String jornada) {

        String SELECT = "SELECT\n"
                + "\"Materias\".materia_nombre\n"
                + "FROM\n"
                + "\"Cursos\"\n"
                + "INNER JOIN \"Jornadas\" ON \"Cursos\".id_jornada = \"Jornadas\".id_jornada\n"
                + "INNER JOIN \"Materias\" ON \"Cursos\".id_materia = \"Materias\".id_materia\n"
                + "INNER JOIN \"PeriodoLectivo\" ON \"Cursos\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo\n"
                + "WHERE\n"
                + "\"PeriodoLectivo\".prd_lectivo_estado = FALSE AND\n"
                + "\"Cursos\".id_docente = " + idDocente + " AND\n"
                + "\"Cursos\".curso_ciclo = " + ciclo + " AND \n"
                + "\"Cursos\".curso_paralelo = '" + paralelo + "' AND \n"
                + "\"Jornadas\".nombre_jornada = '" + jornada + "'";

        List<String> lista = new ArrayList<>();

        ResultSet rs = ResourceManager.Query(SELECT);

        try {
            while (rs.next()) {

                lista.add(rs.getString("materia_nombre"));

            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lista;

    }

}
