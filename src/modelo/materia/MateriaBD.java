package modelo.materia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.ConectarDB;
import modelo.ResourceManager;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.curso.CursoMD;
import modelo.persona.PersonaMD;

/**
 *
 * @author USUARIOXD
 */
public class MateriaBD extends MateriaMD {

    private final ConectarDB conecta;
    private final CarreraBD car;
    private String sql;

    public MateriaBD(ConectarDB conecta) {
        this.conecta = conecta;
        this.car = new CarreraBD(conecta);
    }

    public boolean insertarMateria() {
        String sql = "INSERT INTO public.\"Materias\"(\n"
                + "	 id_materia, id_carrera, id_eje, materia_codigo, materia_nombre, materia_ciclo,"
                + " materia_creditos, materia_tipo, materia_categoria, materia_tipo_acreditacion, materia_horas_docencia, materia_horas_practicas,"
                + " materia_horas_auto_estudio, materia_horas_presencial, materia_total_horas, materia_activa, "
                + "materia_objetivo, materia_descripcion, materia_objetivo_especifico, materia_organizacion_curricular, materia_campo_formacion, materia_nucleo)\n"
                + "	VALUES ( " + getId() + ", " + getCarrera().getId() + ", " + getEje().getId() + ", '" + getCodigo() + "', '" + getNombre() + "', " + getCiclo()
                + ", " + getCreditos() + ", '" + getTipo() + "', '" + getCategoria() + "', '" + getTipoAcreditacion() + "', " + getHorasDocencia() + ", " + getHorasPracticas()
                + ", " + getHorasAutoEstudio() + ", " + getHorasPresenciales() + ", " + getTotalHoras() + ", true, '"
                + getObjetivo() + "', '" + getDescripcion() + "', '" + getObjetivoespecifico() + "', '" + getOrganizacioncurricular() + "', '" + getMateriacampoformacion() + "', " + isMateriaNucleo() + ");";
        if (conecta.nosql(sql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public boolean editarMateria(int aguja) {
        String sql = "UPDATE public.\"Materias\" SET\n"
                + " id_carrera = " + getCarrera().getId() + ", id_eje = " + getEje().getId() + ", materia_codigo = '" + getCodigo()
                + "', materia_nombre = '" + getNombre() + "', materia_ciclo = " + getCiclo() + ", materia_creditos = " + getCreditos()
                + ", materia_tipo = '" + getTipo() + "', materia_categoria = '" + getCategoria() + "', materia_tipo_acreditacion = '" + getTipoAcreditacion()
                + "', materia_horas_docencia = " + getHorasDocencia() + ", materia_horas_practicas = " + getHorasPracticas() + ", materia_horas_auto_estudio = " + getHorasAutoEstudio()
                + ", materia_horas_presencial = " + getHorasPresenciales() + ", materia_total_horas = " + getTotalHoras() + ", materia_objetivo = '" + getObjetivo() + "\n"
                + "', materia_descripcion = '" + getDescripcion() + "', materia_objetivo_especifico = '" + getObjetivoespecifico() + "', materia_organizacion_curricular = '" + getOrganizacioncurricular()
                + "', materia_campo_formacion = '" + getMateriacampoformacion() + "', materia_nucleo = '" + isMateriaNucleo() + "'\n"
                + " WHERE id_materia = " + aguja + ";";
        if (conecta.nosql(sql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public boolean elminarMateria(int aguja) {
        String sql = "UPDATE public.\"Materias\" SET\n"
                + " materia_activa = false"
                + " WHERE id_materia = " + aguja + ";";
        if (conecta.nosql(sql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public List<CarreraMD> cargarCarreras() {
        String sql = "SELECT carrera_nombre FROM public.\"Carreras\" WHERE carrera_activo = true;";
        List<CarreraMD> lista = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        try {
            while (rs.next()) {
                CarreraMD c = new CarreraMD();
                c.setNombre(rs.getString("carrera_nombre"));
                lista.add(c);
            }
            rs.close();
            return lista;
        } catch (SQLException ex) {
            System.out.println("No se pudieron consultar alumnos");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public List<EjeFormacionMD> cargarEjes(int aguja) {
        String sql = "SELECT eje_nombre FROM public.\"EjesFormacion\" WHERE id_carrera = " + aguja + ";";
        List<EjeFormacionMD> lista = new ArrayList();
        ResultSet rs = conecta.sql(sql);
        try {
            while (rs.next()) {
                EjeFormacionMD eje = new EjeFormacionMD();
                eje.setNombre(rs.getString("eje_nombre"));
                lista.add(eje);
            }
            rs.close();
            return lista;
        } catch (SQLException ex) {
            System.out.println("No se pudieron consultar alumnos");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public CarreraMD filtrarIdCarrera(String nombre) {
        String sql = "SELECT id_carrera FROM public.\"Carreras\" WHERE carrera_nombre LIKE '" + nombre + "';";
        CarreraMD carrera = new CarreraMD();
        ResultSet rs = conecta.sql(sql);
        try {
            while (rs.next()) {
                carrera.setId(rs.getInt("id_carrera"));
            }
            rs.close();
            return carrera;
        } catch (SQLException ex) {
            System.out.println("No se pudieron consultar alumnos");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    //para mostrar datos de la materia
    public ArrayList<MateriaMD> cargarMaterias() {
         sql = "SELECT id_materia, materia_codigo,"
                + " materia_nombre, materia_ciclo, "
                + "materia_horas_docencia, materia_horas_practicas, "
                + "materia_horas_auto_estudio, materia_horas_presencial, "
                + "materia_total_horas, m.id_carrera\n"
                + "FROM public.\"Materias\" m, public.\"Carreras\" c \n"
                + "WHERE materia_activa = 'true' AND c.id_carrera = m.id_carrera "
                + "AND carrera_activo = true;";
        return consultarMateriasParaTabla(sql);
    }

    //Cargar datos de materia por carrera
    public ArrayList<MateriaMD> cargarMateriaPorCarrera(int idcarrera) {
        sql = "SELECT id_materia, materia_codigo,"
                + " materia_nombre, materia_ciclo, "
                + "materia_horas_docencia, materia_horas_practicas, "
                + "materia_horas_auto_estudio, materia_horas_presencial, "
                + "materia_total_horas, m.id_carrera\n"
                + "FROM public.\"Materias\" m, public.\"Carreras\" c \n"
                + "WHERE materia_activa = 'true' "
                + "AND m.id_carrera= " + idcarrera + " AND c.id_carrera = m.id_carrera "
                + "AND carrera_activo = true;";
        return consultarMateriasParaTabla(sql);
    }

    //Cargar datos de materia por carrera
    public ArrayList<MateriaMD> cargarMateriaPorCarreraCiclo(int idcarrera, int ciclo) {
        sql = "SELECT id_materia, materia_codigo,"
                + " materia_nombre, materia_ciclo, "
                + "materia_horas_docencia, materia_horas_practicas, "
                + "materia_horas_auto_estudio, materia_horas_presencial, "
                + "materia_total_horas, m.id_carrera\n"
                + "FROM public.\"Materias\" m, public.\"Carreras\" c \n"
                + "WHERE materia_activa = 'true' "
                + "AND m.id_carrera= " + idcarrera + " "
                + "AND materia_ciclo = " + ciclo + " \n"
                + "AND c.id_carrera = m.id_carrera "
                + "AND carrera_activo = true;";
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
                + "WHERE id_materia = " + idmateria + " AND\n"
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
        sql = "SELECT id_materia, materia_codigo,"
                + " materia_nombre, materia_ciclo, "
                + "materia_horas_docencia, materia_horas_practicas, "
                + "materia_horas_auto_estudio, materia_horas_presencial, "
                + "materia_total_horas, m.id_carrera\n"
                + "FROM public.\"Materias\" m, public.\"Carreras\" c\n"
                + "WHERE materia_activa = 'true' AND ("
                + "materia_codigo ILIKE '%" + aguja + "%' "
                + "OR materia_nombre ILIKE '%" + aguja + "%') \n"
                + "AND c.id_carrera = m.id_carrera "
                + "AND carrera_activo = true;";
        return consultarMateriasParaTabla(sql);
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
                    CarreraMD cr = new CarreraMD();
                    cr.setId(rs.getInt("id_carrera"));
                    m.setCarrera(cr);
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

    public ArrayList<MateriaMD> cargarMateriasCarreraCmb(int idCarrera) {
        String sql = "SELECT id_materia, materia_codigo,"
                + " materia_nombre \n"
                + "FROM public.\"Materias\" m \n"
                + "WHERE materia_activa = 'true' "
                + "AND m.id_carrera= " + idCarrera + ";";
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

    public static List<MateriaMD> selectWhere(CursoMD curso) {

        String SELECT = "SELECT\n"
                + "\"public\".\"Materias\".materia_nombre,\n"
                + "\"public\".\"Materias\".id_materia\n"
                + "FROM\n"
                + "\"public\".\"Cursos\"\n"
                + "INNER JOIN \"public\".\"Materias\" ON \"public\".\"Cursos\".id_materia = \"public\".\"Materias\".id_materia\n"
                + "WHERE\n"
                + "\"Cursos\".id_docente = " + curso.getId_docente().getIdDocente() + " AND\n"
                + "\"Cursos\".id_prd_lectivo = '" + curso.getId_prd_lectivo().getId_PerioLectivo() + "' AND \n"
                + "\"Cursos\".curso_nombre = '" + curso.getCurso_nombre() + "'";

        System.out.println(SELECT);

        List<MateriaMD> lista = new ArrayList<>();

        ResultSet rs = ResourceManager.Query(SELECT);

        try {
            while (rs.next()) {
                MateriaMD materia = new MateriaMD();
                materia.setId(rs.getInt("id_materia"));
                materia.setNombre(rs.getString("materia_nombre"));
                lista.add(materia);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lista;

    }

    public String getSql() {
        return sql;
    }

}
