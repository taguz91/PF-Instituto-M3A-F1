package modelo.materia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modelo.ConnDBPool;
import modelo.carrera.CarreraMD;
import modelo.curso.CursoMD;
import utils.CONBD;
import utils.M;

/**
 *
 * @author USUARIOXD
 */
public class MateriaBD extends CONBD {

    private String sqlg;

    private final ConnDBPool POOL;
    private Connection conn;
    private ResultSet rst;

    {
        POOL = new ConnDBPool();
    }

    private static MateriaBD MBD;

    public static MateriaBD single() {
        if (MBD == null) {
            MBD = new MateriaBD();
        }
        return MBD;
    }

    public boolean insertarMateria(MateriaMD m) {
        String nsql = "INSERT INTO public.\"Materias\"( "
                + "id_carrera, "
                + "id_eje, "
                + "materia_codigo, "
                + "materia_nombre, "
                + "materia_ciclo, "
                + "materia_creditos, "
                + "materia_tipo, "
                + "materia_categoria, "
                + "materia_tipo_acreditacion, "
                + "materia_horas_docencia, "
                + "materia_horas_practicas, "
                + "materia_horas_auto_estudio, "
                + "materia_horas_presencial, "
                + "materia_total_horas, "
                + "materia_activa, "
                + "materia_objetivo, "
                + "materia_descripcion, "
                + "materia_objetivo_especifico, "
                + "materia_organizacion_curricular, "
                + "materia_campo_formacion, "
                + "materia_nucleo) "
                + "VALUES( " + m.getCarrera().getId() + ", "
                + m.getEje().getId() + ", "
                + "'" + m.getCodigo() + "', "
                + "'" + m.getNombre() + "', "
                + m.getCiclo()
                + ", " + m.getCreditos() + ", "
                + "'" + m.getTipo() + "', "
                + "'" + m.getCategoria() + "', "
                + "'" + m.getTipoAcreditacion() + "', "
                + m.getHorasDocencia() + ", "
                + m.getHorasPracticas() + ", "
                + m.getHorasAutoEstudio() + ", "
                + m.getHorasPresenciales() + ", "
                + m.getTotalHoras() + ", true, "
                + "'" + m.getObjetivo() + "', "
                + "'" + m.getDescripcion() + "', "
                + "'" + m.getObjetivoespecifico() + "', "
                + "'" + m.getOrganizacioncurricular() + "', "
                + "'" + m.getMateriacampoformacion() + "', "
                + "'" + m.isMateriaNucleo() + "');";
        return CON.executeNoSQL(nsql);
    }

    public boolean editarMateria(MateriaMD m) {
        String nsql = "UPDATE public.\"Materias\" SET "
                + " id_carrera = " + m.getCarrera().getId() + ", "
                + "id_eje = " + m.getEje().getId() + ", "
                + "materia_codigo = '" + m.getCodigo() + "', "
                + "materia_nombre = '" + m.getNombre() + "', "
                + "materia_ciclo = " + m.getCiclo() + ", "
                + "materia_creditos = " + m.getCreditos() + ", "
                + "materia_tipo = '" + m.getTipo() + "', "
                + "materia_categoria = '" + m.getCategoria() + "', "
                + "materia_tipo_acreditacion = '" + m.getTipoAcreditacion() + "', "
                + "materia_horas_docencia = " + m.getHorasDocencia() + ", "
                + "materia_horas_practicas = " + m.getHorasPracticas() + ", "
                + "materia_horas_auto_estudio = " + m.getHorasAutoEstudio() + ", "
                + "materia_horas_presencial = " + m.getHorasPresenciales() + ", "
                + "materia_total_horas = " + m.getTotalHoras() + ", "
                + "materia_objetivo = '" + m.getObjetivo() + "', "
                + "materia_descripcion = '" + m.getDescripcion() + "', "
                + "materia_objetivo_especifico = '" + m.getObjetivoespecifico() + "', "
                + "materia_organizacion_curricular = '" + m.getOrganizacioncurricular() + "', "
                + "materia_campo_formacion = '" + m.getMateriacampoformacion() + "', "
                + "materia_nucleo = '" + m.isMateriaNucleo() + "' "
                + " WHERE id_materia = " + m.getId() + ";";
        return CON.executeNoSQL(nsql);
    }

    public boolean elminarMateria(int aguja) {
        String nsql = "UPDATE public.\"Materias\" SET\n"
                + " materia_activa = 'false'"
                + " WHERE id_materia = " + aguja + ";";
        return CON.executeNoSQL(nsql);
    }

    public List<CarreraMD> cargarCarreras() {
        String sql = "SELECT carrera_nombre FROM public.\"Carreras\" WHERE carrera_activo = true;";
        List<CarreraMD> lista = new ArrayList();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CarreraMD c = new CarreraMD();
                c.setNombre(rs.getString("carrera_nombre"));
                lista.add(c);
            }
        } catch (SQLException ex) {
            System.out.println("No se pudieron consultar alumnos");
            System.out.println(ex.getMessage());
            M.errorMsg("No consultamos carreras. " + ex.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return lista;
    }

    public List<EjeFormacionMD> cargarEjes(int aguja) {
        String sql = "SELECT eje_nombre "
                + "FROM public.\"EjesFormacion\" "
                + "WHERE id_carrera = " + aguja + " "
                + "AND eje_estado = true;";
        List<EjeFormacionMD> lista = new ArrayList();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                EjeFormacionMD eje = new EjeFormacionMD();
                eje.setNombre(rs.getString("eje_nombre"));
                lista.add(eje);
            }
        } catch (SQLException ex) {
            M.errorMsg("No se consultaron ejes. " + ex.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return lista;
    }

    public CarreraMD filtrarIdCarrera(String nombre, int id) {
        String sql = "SELECT id_carrera, "
                + "carrera_nombre "
                + "FROM public.\"Carreras\" "
                + "WHERE carrera_nombre LIKE '" + nombre + "' "
                + "OR id_carrera = " + id + " "
                + "AND carrera_activo = true;";
        CarreraMD carrera = new CarreraMD();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                carrera.setId(rs.getInt("id_carrera"));
                carrera.setNombre(rs.getString("carrera_nombre"));
            }
            return carrera;
        } catch (SQLException ex) {
            M.errorMsg("No se consultaron carrera. " + ex.getMessage());
            return null;
        } finally {
            CON.cerrarCONPS(ps);
        }
    }

    public EjeFormacionMD filtrarIdEje(String nombre, int id) {
        String sql = "SELECT id_eje, "
                + "eje_nombre "
                + "FROM public.\"EjesFormacion\" "
                + "WHERE eje_nombre LIKE '" + nombre + "' "
                + "OR id_eje = " + id + ";";
        PreparedStatement ps = CON.getPSPOOL(sql);
        EjeFormacionMD eje = new EjeFormacionMD();
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                eje.setId(rs.getInt("id_eje"));
                eje.setNombre(rs.getString("eje_nombre"));
            }
            return eje;
        } catch (SQLException ex) {
            M.errorMsg("No se consultaron eje por ID. " + ex.getMessage());
            return null;
        } finally {
            CON.cerrarCONPS(ps);
        }
    }

    public MateriaMD capturarIDMaterias(String nombre, int carrera) {
        String sql = "SELECT id_materia "
                + "FROM public.\"Materias\" "
                + "WHERE materia_nombre LIKE '" + nombre + "' "
                + "AND id_carrera = " + carrera + " "
                + "AND materia_activa = true;";
        MateriaMD m = new MateriaMD();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                m.setId(rs.getInt("id_materia"));
            }
            return m;
        } catch (SQLException ex) {
            M.errorMsg("No se consultaron materias. " + ex.getMessage());
            return null;
        } finally {
            CON.cerrarCONPS(ps);
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
        String sql = "SELECT id_materia "
                + "FROM public.\"Materias\" "
                + "WHERE materia_nombre LIKE '%" + materia + "%';";
        PreparedStatement ps = CON.getPSPOOL(sql);
        MateriaMD m = new MateriaMD();
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                m.setId(rs.getInt("id_materia"));
            }
            return m;
        } catch (SQLException ex) {
            M.errorMsg("No se consultaron materias para tabla. " + ex.getMessage());
            return null;
        } finally {
            CON.cerrarCONPS(ps);
        }
    }

    //Cargar datos de materia por carrera para comprobar si es nucleo o no 
    public ArrayList<MateriaMD> cargarMateriaPorCarreraFrm(int idcarrera) {
        sqlg = "SELECT id_materia, materia_codigo, "
                + "materia_nombre, materia_ciclo, "
                + "m.id_carrera, materia_nucleo "
                + "FROM public.\"Materias\" m "
                + "WHERE materia_activa = 'true' "
                + "AND m.id_carrera= " + idcarrera + " "
                + "AND materia_activa = true;";
        ArrayList<MateriaMD> lista = new ArrayList();
        PreparedStatement ps = CON.getPSPOOL(sqlg);
        try {
            ResultSet rs = ps.executeQuery();
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
        } catch (SQLException ex) {
            M.errorMsg("No se consultaron materias para formulario. " + ex.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return lista;
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
        PreparedStatement ps = CON.getPSPOOL(sql);

        try {
            ResultSet rs = ps.executeQuery();
            MateriaMD m;
            while (rs.next()) {
                m = new MateriaMD();
                m.setId(rs.getInt("id_materia"));
                m.setCodigo(rs.getString("materia_codigo"));
                m.setNombre(rs.getString("materia_nombre"));
                lista.add(m);
            }
        } catch (SQLException ex) {
            M.errorMsg("No se consultaron materias para formulario. " + ex.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return lista;
    }

    //Cargar todos los ciclos de una carrera  
    public ArrayList<Integer> cargarCiclosCarrera(int idCarrera) {
        ArrayList<Integer> ciclos = new ArrayList();
        String sql = "SELECT DISTINCT materia_ciclo "
                + "FROM public.\"Materias\"  "
                + "WHERE id_carrera = " + idCarrera + " "
                + "ORDER BY materia_ciclo; ";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ciclos.add(rs.getInt("materia_ciclo"));
            }
        } catch (SQLException ex) {
            M.errorMsg("No se consultaron ciclos por carrera. " + ex.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return ciclos;
    }

    //Buscar materia para informacion
    public MateriaMD buscarMateriaInfo(int idmateria) {
        MateriaMD m = new MateriaMD();
        String sql = "SELECT id_materia, "
                + "m.id_carrera, "
                + "materia_codigo, "
                + "materia_nombre, "
                + "carrera_nombre "
                + "FROM public.\"Materias\" m, "
                + "public.\"Carreras\" c "
                + "WHERE id_materia = " + idmateria + " "
                + "AND c.id_carrera = m.id_carrera;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
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
        } catch (SQLException ex) {
            M.errorMsg("No se consultar materia info. " + ex.getMessage());
            return null;
        } finally {
            CON.cerrarCONPS(ps);
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
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                m = obtenerMateria(rs);
            }
            return m;
        } catch (SQLException ex) {
            M.errorMsg("No se consultar materia por id. " + ex.getMessage());
            return null;
        } finally {
            CON.cerrarCONPS(ps);
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
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                m = obtenerMateria(rs);
            }
            return m;
        } catch (SQLException ex) {
            M.errorMsg("No se consultar materia por codigo. " + ex.getMessage());
            return null;
        } finally {
            CON.cerrarCONPS(ps);
        }
    }

    //Metodo buscar por id de la materia
    public MateriaMD buscarMateriaPorReferencia(int idmateria) {
        MateriaMD m = new MateriaMD();
        String sql = "SELECT id_materia, id_carrera, "
                + "materia_nombre, materia_ciclo "
                + "FROM public.\"Materias\" "
                + "WHERE materia_activa = 'true' "
                + "AND id_materia= " + idmateria + ";";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                m.setId(rs.getInt("id_materia"));
                m.setNombre(rs.getString("materia_nombre"));
                m.setCiclo(rs.getInt("materia_ciclo"));
            }
            return m;
        } catch (SQLException ex) {
            M.errorMsg("No se consultar materia por referencia. " + ex.getMessage());
            return null;
        } finally {
            CON.cerrarCONPS(ps);
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
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MateriaMD m = new MateriaMD();
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
        } catch (SQLException ex) {
            M.errorMsg("No se consultar materia para tabla. " + ex.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return lista;
    }

    public ArrayList<MateriaMD> cargarMateriasCarreraCmb(int idCarrera) {
        String sql = "SELECT id_materia, materia_codigo,"
                + " materia_nombre \n"
                + "FROM public.\"Materias\" m \n"
                + "WHERE materia_activa = 'true' "
                + "AND m.id_carrera= " + idCarrera + ";";
        ArrayList<MateriaMD> lista = new ArrayList();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            MateriaMD m;
            while (rs.next()) {
                m = new MateriaMD();
                m.setId(rs.getInt("id_materia"));
                m.setCodigo(rs.getString("materia_codigo"));
                m.setNombre(rs.getString("materia_nombre"));
                lista.add(m);
            }
        } catch (SQLException ex) {
            M.errorMsg("No se consultar materia para combo. " + ex.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return lista;
    }

    public MateriaMD obtenerMateria(ResultSet rs) {
        MateriaMD m = new MateriaMD();
        Integer numero;
        String palabra;
        try {
            m.setId(rs.getInt("id_materia"));
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

    public List<MateriaMD> selectWhere(CursoMD curso) {

        String SELECT = "SELECT\n"
                + "\"public\".\"Materias\".materia_nombre,\n"
                + "\"public\".\"Materias\".id_materia,\n"
                + "\"public\".\"Materias\".materia_horas_presencial\n"
                + "FROM\n"
                + "\"public\".\"Cursos\"\n"
                + "INNER JOIN \"public\".\"Materias\" ON \"public\".\"Cursos\".id_materia = \"public\".\"Materias\".id_materia\n"
                + "WHERE\n"
                + "\"Cursos\".id_docente = ? AND\n"
                + "\"Cursos\".id_prd_lectivo = ?AND \n"
                + "\"Cursos\".curso_nombre = ?";

        System.out.println(SELECT);

        List<MateriaMD> lista = new ArrayList<>();

        Map<Integer, Object> parametros = new HashMap<>();
        parametros.put(1, curso.getDocente().getIdDocente());
        parametros.put(2, curso.getPeriodo().getID());
        parametros.put(3, curso.getNombre());

        conn = POOL.getConnection();
        rst = POOL.ejecutarQuery(SELECT, conn, parametros);

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
            POOL.closeStmt().close(rst).close(conn);
        }
        return lista;

    }

    public String getSql() {
        return sqlg;
    }

    // Querys para pre y co requisitos 
    public ArrayList<MateriaMD> getMateriasParaCorequisito(int idMateria) {
        String sql = "SELECT id_materia, "
                + " materia_nombre \n"
                + "FROM public.\"Materias\" m \n"
                + "WHERE materia_activa = 'true' "
                + "AND m.id_carrera = ( "
                + " SELECT id_carrera FROM public.\"Materias\" "
                + " WHERE id_materia = " + idMateria
                + ") AND materia_ciclo = ("
                + " SELECT materia_ciclo FROM public.\"Materias\" "
                + " WHERE id_materia = " + idMateria
                + ")"
                + "AND materia_activa = true;";
        return getParaRequisitos(sql);
    }

    public ArrayList<MateriaMD> getMateriasParaPrequisitos(int idMateria) {
        String sql = "SELECT id_materia, "
                + " materia_nombre \n"
                + "FROM public.\"Materias\" m \n"
                + "WHERE materia_activa = 'true' "
                + "AND m.id_carrera = ( "
                + " SELECT id_carrera FROM public.\"Materias\" "
                + " WHERE id_materia = " + idMateria
                + ") AND materia_ciclo < ("
                + " SELECT materia_ciclo FROM public.\"Materias\" "
                + " WHERE id_materia = " + idMateria
                + ")"
                + "AND materia_activa = true;";
        return getParaRequisitos(sql);
    }

    private ArrayList<MateriaMD> getParaRequisitos(String sql) {
        ArrayList<MateriaMD> lista = new ArrayList();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            MateriaMD m;
            while (rs.next()) {
                m = new MateriaMD();
                m.setId(rs.getInt("id_materia"));
                m.setNombre(rs.getString("materia_nombre"));
                lista.add(m);
            }
        } catch (SQLException ex) {
            M.errorMsg("No se consultar materia para combo. " + ex.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return lista;
    }
}
