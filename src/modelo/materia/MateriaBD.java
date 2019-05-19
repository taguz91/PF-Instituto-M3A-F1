package modelo.materia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.ConectarDB;
import modelo.ConnDBPool;
import modelo.carrera.CarreraMD;
import modelo.curso.CursoMD;

/**
 *
 * @author USUARIOXD
 */
public class MateriaBD extends MateriaMD {

    private final ConectarDB conecta;
    private String sqlg;

    private final static ConnDBPool POOL;
    private static Connection conn;
    private static ResultSet rst;

    static {
        POOL = new ConnDBPool();
    }

    public MateriaBD(ConectarDB conecta) {
        this.conecta = conecta;
    }

    public boolean insertarMateria() {
        String nsql = "INSERT INTO public.\"Materias\"(\n"
                + "	 id_carrera, id_eje, materia_codigo, materia_nombre, materia_ciclo,"
                + " materia_creditos, materia_tipo, materia_categoria, materia_tipo_acreditacion, materia_horas_docencia, materia_horas_practicas,"
                + " materia_horas_auto_estudio, materia_horas_presencial, materia_total_horas, materia_activa, "
                + "materia_objetivo, materia_descripcion, materia_objetivo_especifico, materia_organizacion_curricular, materia_campo_formacion, materia_nucleo)\n"
                + "	VALUES ( " + getCarrera().getId() + ", " + getEje().getId() + ", '" + getCodigo() + "', '" + getNombre() + "', " + getCiclo()
                + ", " + getCreditos() + ", '" + getTipo() + "', '" + getCategoria() + "', '" + getTipoAcreditacion() + "', " + getHorasDocencia() + ", " + getHorasPracticas()
                + ", " + getHorasAutoEstudio() + ", " + getHorasPresenciales() + ", " + getTotalHoras() + ", true, '"
                + getObjetivo() + "', '" + getDescripcion() + "', '" + getObjetivoespecifico() + "', '" + getOrganizacioncurricular() + "', '" + getMateriacampoformacion() + "', '" + isMateriaNucleo() + "');";
        PreparedStatement ps = conecta.getPS(nsql);
        if (conecta.nosql(ps) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public boolean editarMateria(int aguja) {
        String nsql = "UPDATE public.\"Materias\" SET\n"
                + " id_carrera = " + getCarrera().getId() + ", id_eje = " + getEje().getId() + ", materia_codigo = '" + getCodigo()
                + "', materia_nombre = '" + getNombre() + "', materia_ciclo = " + getCiclo() + ", materia_creditos = " + getCreditos()
                + ", materia_tipo = '" + getTipo() + "', materia_categoria = '" + getCategoria() + "', materia_tipo_acreditacion = '" + getTipoAcreditacion()
                + "', materia_horas_docencia = " + getHorasDocencia() + ", materia_horas_practicas = " + getHorasPracticas() + ", materia_horas_auto_estudio = " + getHorasAutoEstudio()
                + ", materia_horas_presencial = " + getHorasPresenciales() + ", materia_total_horas = " + getTotalHoras() + ", materia_objetivo = '" + getObjetivo() + "\n"
                + "', materia_descripcion = '" + getDescripcion() + "', materia_objetivo_especifico = '" + getObjetivoespecifico() + "', materia_organizacion_curricular = '" + getOrganizacioncurricular()
                + "', materia_campo_formacion = '" + getMateriacampoformacion() + "', materia_nucleo = '" + isMateriaNucleo() + "'\n"
                + " WHERE id_materia = " + aguja + ";";
        PreparedStatement ps = conecta.getPS(nsql);
        if (conecta.nosql(ps) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public boolean elminarMateria(int aguja) {
        String nsql = "UPDATE public.\"Materias\" SET\n"
                + " materia_activa = 'false'"
                + " WHERE id_materia = " + aguja + ";";
        PreparedStatement ps = conecta.getPS(nsql);
        if (conecta.nosql(ps) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public List<CarreraMD> cargarCarreras() {
        String sql = "SELECT carrera_nombre FROM public.\"Carreras\" WHERE carrera_activo = true;";
        List<CarreraMD> lista = new ArrayList();
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            while (rs.next()) {
                CarreraMD c = new CarreraMD();
                c.setNombre(rs.getString("carrera_nombre"));
                lista.add(c);
            }
            ps.getConnection().close();
            return lista;
        } catch (SQLException ex) {
            System.out.println("No se pudieron consultar alumnos");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public List<EjeFormacionMD> cargarEjes(int aguja) {
        String sql = "SELECT eje_nombre FROM public.\"EjesFormacion\" WHERE id_carrera = " + aguja + " AND eje_estado = true;";
        List<EjeFormacionMD> lista = new ArrayList();
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            while (rs.next()) {
                EjeFormacionMD eje = new EjeFormacionMD();
                eje.setNombre(rs.getString("eje_nombre"));
                lista.add(eje);
            }
            ps.getConnection().close();
            return lista;
        } catch (SQLException ex) {
            System.out.println("No se pudieron consultar alumnos");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public CarreraMD filtrarIdCarrera(String nombre, int id) {
        String sql = "SELECT id_carrera, carrera_nombre FROM public.\"Carreras\" WHERE carrera_nombre LIKE '" + nombre
                + "' or id_carrera = " + id + " AND carrera_activo = true;";
        CarreraMD carrera = new CarreraMD();
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            while (rs.next()) {
                carrera.setId(rs.getInt("id_carrera"));
                carrera.setNombre(rs.getString("carrera_nombre"));
            }
            ps.getConnection().close();
            return carrera;
        } catch (SQLException ex) {
            System.out.println("No se pudieron consultar carreras");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public EjeFormacionMD filtrarIdEje(String nombre, int id) {
        String sql = "SELECT id_eje, eje_nombre FROM public.\"EjesFormacion\" WHERE eje_nombre LIKE '" + nombre
                + "' or id_eje = " + id + ";";
        PreparedStatement ps = conecta.getPS(sql);
        EjeFormacionMD eje = new EjeFormacionMD();
        ResultSet rs = conecta.sql(ps);
        try {
            while (rs.next()) {
                eje.setId(rs.getInt("id_eje"));
                eje.setNombre(rs.getString("eje_nombre"));
            }
            ps.getConnection().close();
            return eje;
        } catch (SQLException ex) {
            System.out.println("No se pudieron consultar ejes");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public MateriaMD capturarIDMaterias(String nombre, int carrera) {
        String sql = "SELECT id_materia FROM public.\"Materias\" WHERE materia_nombre LIKE '" + nombre + "' AND id_carrera = " + carrera + " AND materia_activa = true;";
        MateriaMD m = new MateriaMD();
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            while (rs.next()) {
                m.setId(rs.getInt("id_materia"));
            }
            ps.getConnection().close();
            return m;
        } catch (SQLException ex) {
            System.out.println("No se pudieron consultar ejes");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    //para mostrar datos de la materia
    public ArrayList<MateriaMD> cargarMaterias() {
        sqlg = "SELECT id_materia, materia_codigo,"
                + " materia_nombre, materia_ciclo, "
                + "materia_horas_docencia, materia_horas_practicas, "
                + "materia_horas_auto_estudio, materia_horas_presencial, "
                + "materia_total_horas, m.id_carrera\n"
                + "FROM public.\"Materias\" m \n"
                + "WHERE materia_activa = 'true'"
                + "ORDER BY materia_ciclo;";
        return consultarMateriasParaTabla(sqlg);
    }

    public MateriaMD buscarMateria(String materia) {
        String sql = "SELECT id_materia FROM public.\"Materias\" WHERE materia_nombre LIKE '%" + materia + "%';";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        MateriaMD m = new MateriaMD();
        try {
            if (rs != null) {
                while (rs.next()) {
                    m.setId(rs.getInt("id_materia"));
                }
                ps.getConnection().close();
                return m;
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

    //Cargar datos de materia por carrera para comprobar si es nucleo o no 
    public ArrayList<MateriaMD> cargarMateriaPorCarreraFrm(int idcarrera) {
        sqlg = "SELECT id_materia, materia_codigo,"
                + " materia_nombre, materia_ciclo, "
                + "m.id_carrera, materia_nucleo \n"
                + "FROM public.\"Materias\" m \n"
                + "WHERE materia_activa = 'true' "
                + "AND m.id_carrera= " + idcarrera + " AND materia_activa = true;";
        ArrayList<MateriaMD> lista = new ArrayList();
        PreparedStatement ps = conecta.getPS(sqlg);
        ResultSet rs = conecta.sql(ps);
        try {
            if (rs != null) {
                MateriaMD m;
                while (rs.next()) {
                    m = new MateriaMD();
                    m.setId(rs.getInt("id_materia"));
                    CarreraMD cr = new CarreraMD();
                    cr.setId(idcarrera);
                    m.setCarrera(cr);
                    m.setMateriaNucleo(rs.getBoolean("materia_nucleo"));
                    lista.add(m);
                }
                ps.getConnection().close();
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

    //Cargar datos de materia por carrera
    public ArrayList<MateriaMD> cargarMateriaPorCarrera(int idcarrera) {
        sqlg = "SELECT id_materia, materia_codigo,"
                + " materia_nombre, materia_ciclo, "
                + "materia_horas_docencia, materia_horas_practicas, "
                + "materia_horas_auto_estudio, materia_horas_presencial, "
                + "materia_total_horas, m.id_carrera\n"
                + "FROM public.\"Materias\" m, public.\"Carreras\" c \n"
                + "WHERE materia_activa = 'true' "
                + "AND m.id_carrera= " + idcarrera + " AND c.id_carrera = m.id_carrera "
                + "AND carrera_activo = true;";
        return consultarMateriasParaTabla(sqlg);
    }

    //Cargar datos de materia por carrera
    public ArrayList<MateriaMD> cargarMateriaPorCarreraCiclo(int idcarrera, int ciclo) {
        sqlg = "SELECT id_materia, materia_codigo,"
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
        return consultarMateriasParaTabla(sqlg);
    }

    //Cargar datos de materia por carrera ciclo para ver sus materias onli materias
    public ArrayList<MateriaMD> buscarMateriaPorCarreraCiclo(int idcarrera, int ciclo) {
        String sql = "SELECT id_materia, materia_codigo,"
                + " materia_nombre \n"
                + "FROM public.\"Materias\" WHERE materia_activa = 'true'"
                + "AND id_carrera= " + idcarrera + " "
                + "AND materia_ciclo = " + ciclo + ";";
        ArrayList<MateriaMD> lista = new ArrayList();
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
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
                ps.getConnection().close();
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
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            if (rs != null) {
                while (rs.next()) {
                    ciclos.add(rs.getInt("materia_ciclo"));
                }
                ps.getConnection().close();
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
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
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
                ps.getConnection().close();
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
                + "materia_objetivo_especifico,materia_organizacion_curricular,materia_campo_formacion, materia_nucleo\n"
                + "FROM public.\"Materias\" WHERE materia_activa = 'true' "
                + "AND id_materia= " + idmateria + ";";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            if (rs != null) {
                while (rs.next()) {
                    m = obtenerMateria(rs);
                }
                ps.getConnection().close();
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

    public MateriaMD buscarMateriaxCodigo(String codigo) {
        MateriaMD m = new MateriaMD();
        String sql = "SELECT id_materia, id_carrera, id_eje, materia_codigo,"
                + " materia_nombre, materia_ciclo, materia_creditos, "
                + "materia_tipo, materia_categoria, materia_tipo_acreditacion, "
                + "materia_horas_docencia, materia_horas_practicas, "
                + "materia_horas_auto_estudio, materia_horas_presencial, "
                + "materia_total_horas, materia_activa, materia_objetivo,"
                + "materia_descripcion,"
                + "materia_objetivo_especifico,materia_organizacion_curricular,materia_campo_formacion, materia_nucleo\n"
                + "FROM public.\"Materias\" WHERE materia_activa = 'true' "
                + "AND materia_codigo LIKE '" + codigo + "';";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            if (rs != null) {
                while (rs.next()) {
                    m = obtenerMateria(rs);
                }
                ps.getConnection().close();
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
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
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
        sqlg = "SELECT id_materia, materia_codigo,"
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
        return consultarMateriasParaTabla(sqlg);
    }

    private ArrayList<MateriaMD> consultarMateriasParaTabla(String sql) {
        ArrayList<MateriaMD> lista = new ArrayList();
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
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
                ps.getConnection().close();
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
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
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
                ps.getConnection().close();
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
        Integer numero;
        String palabra;
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
            numero = rs.getInt("materia_ciclo");
            if (numero == 0) {
                m.setCiclo(0);
            } else {
                m.setCiclo(numero);
            }

            numero = rs.getInt("materia_creditos");
            if (numero == 0) {
                m.setCreditos(0);
            } else {
                m.setCreditos(numero);
            }

            palabra = rs.getString("materia_tipo");
            if (palabra == null) {
                m.setTipo('A');
            } else {
                m.setTipo(palabra.charAt(0));
            }

            palabra = rs.getString("materia_categoria");
            if (palabra == null) {
                m.setCategoria("SELECCIONE");
            } else {
                m.setCategoria(palabra);
            }

            palabra = rs.getString("materia_tipo_acreditacion");
            if (palabra == null) {
                m.setTipoAcreditacion('A');
            } else {
                m.setTipoAcreditacion(rs.getString("materia_tipo_acreditacion").charAt(0));
            }

            numero = rs.getInt("materia_horas_docencia");
            if (numero == 0) {
                m.setHorasDocencia(0);
            } else {
                m.setHorasDocencia(rs.getInt("materia_horas_docencia"));
            }

            numero = rs.getInt("materia_horas_practicas");
            if (numero == 0) {
                m.setHorasPracticas(0);
            } else {
                m.setHorasPracticas(rs.getInt("materia_horas_practicas"));
            }

            numero = rs.getInt("materia_horas_presencial");
            if (numero == 0) {
                m.setHorasPresenciales(0);
            } else {
                m.setHorasPresenciales(rs.getInt("materia_horas_presencial"));
            }

            numero = rs.getInt("materia_horas_auto_estudio");
            if (numero == 0) {
                m.setHorasAutoEstudio(0);
            } else {
                m.setHorasAutoEstudio(rs.getInt("materia_horas_auto_estudio"));
            }

            numero = rs.getInt("materia_total_horas");
            if (numero == 0) {
                m.setTotalHoras(0);
            } else {
                m.setTotalHoras(rs.getInt("materia_total_horas"));
            }

            palabra = rs.getString("materia_objetivo");
            if (palabra == null) {
                m.setObjetivo(palabra);
            } else {
                m.setObjetivo(rs.getString("materia_objetivo"));
            }

            palabra = rs.getString("materia_descripcion");
            if (palabra == null) {
                m.setDescripcion(null);
            } else {
                m.setDescripcion(rs.getString("materia_descripcion"));
            }

            palabra = rs.getString("materia_objetivo_especifico");
            if (palabra == null) {
                m.setObjetivoespecifico(null);
            } else {
                m.setObjetivoespecifico(palabra);
            }

            palabra = rs.getString("materia_organizacion_curricular");
            if (palabra == null) {
                m.setOrganizacioncurricular("SELECCIONE");
            } else {
                m.setOrganizacioncurricular(palabra);
            }

            palabra = rs.getString("materia_campo_formacion");
            if (palabra == null) {
                m.setMateriacampoformacion("SELECCIONE");
            } else {
                m.setMateriacampoformacion(palabra);
            }
            m.setMateriaNucleo(rs.getBoolean("materia_nucleo"));
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
                + "\"public\".\"Materias\".id_materia,\n"
                + "\"public\".\"Materias\".materia_horas_presencial\n"
                + "FROM\n"
                + "\"public\".\"Cursos\"\n"
                + "INNER JOIN \"public\".\"Materias\" ON \"public\".\"Cursos\".id_materia = \"public\".\"Materias\".id_materia\n"
                + "WHERE\n"
                + "\"Cursos\".id_docente = " + curso.getDocente().getIdDocente() + " AND\n"
                + "\"Cursos\".id_prd_lectivo = '" + curso.getPeriodo().getId_PerioLectivo() + "' AND \n"
                + "\"Cursos\".curso_nombre = '" + curso.getNombre() + "'";

        System.out.println(SELECT);

        List<MateriaMD> lista = new ArrayList<>();

        conn = POOL.getConnection();
        rst = POOL.ejecutarQuery(SELECT, conn, null);

        try {
            while (rst.next()) {
                MateriaMD materia = new MateriaMD();
                materia.setId(rst.getInt("id_materia"));
                materia.setNombre(rst.getString("materia_nombre"));
                materia.setHorasPresenciales(rst.getInt("materia_horas_presencial"));
                lista.add(materia);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            POOL.close(conn);
        }
        return lista;

    }

    public String getSql() {
        return sqlg;
    }

}
