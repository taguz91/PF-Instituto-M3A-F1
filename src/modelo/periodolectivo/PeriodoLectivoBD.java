package modelo.periodolectivo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modelo.ConectarDB;
import modelo.ConnDBPool;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;

public class PeriodoLectivoBD extends PeriodoLectivoMD {

    private ConectarDB conecta;
    //Para guardar carrera en un periodo  
    private CarreraBD car;

    private CarreraMD carrera;

    private final static ConnDBPool POOL;
    private static Connection conn;
    private static ResultSet rst;

    static {
        POOL = new ConnDBPool();
    }

    public PeriodoLectivoBD() {
    }

    public PeriodoLectivoBD(ConectarDB conecta) {
        this.conecta = conecta;
        this.car = new CarreraBD(conecta);
    }

    public boolean guardarPeriodo(PeriodoLectivoMD p, CarreraMD c) {
        String nsql = "INSERT INTO public.\"PeriodoLectivo\"(\n"
                + "id_carrera, prd_lectivo_nombre, prd_lectivo_fecha_inicio, prd_lectivo_fecha_fin, prd_lectivo_observacion, prd_lectivo_activo, prd_lectivo_estado)"
                + " VALUES( " + c.getId() + ", '" + p.getNombre_PerLectivo().toUpperCase() + "   " + Meses(p.getFecha_Inicio()) + "   " + Meses(p.getFecha_Fin()) + "', '" + p.getFecha_Inicio()
                + "', '" + p.getFecha_Fin() + "', '" + p.getObservacion_PerLectivo().toUpperCase() + "', true, true);";
        PreparedStatement ps = conecta.getPS(nsql);
        if (conecta.nosql(ps) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public boolean editarPeriodo(PeriodoLectivoMD p, CarreraMD c) {
        String nsql = "UPDATE public.\"PeriodoLectivo\" SET\n"
                + " id_carrera = " + c.getId() + ", prd_lectivo_nombre = '" + p.getNombre_PerLectivo() + "',"
                + " prd_lectivo_fecha_inicio = '" + p.getFecha_Inicio() + "', prd_lectivo_fecha_fin = '" + p.getFecha_Fin()
                + "', prd_lectivo_observacion = '" + p.getObservacion_PerLectivo()
                + "' WHERE id_prd_lectivo = " + p.getId_PerioLectivo() + ";";
        PreparedStatement ps = conecta.getPS(nsql);
        if (conecta.nosql(ps) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public boolean eliminarPeriodo(PeriodoLectivoMD p) {
        String nsql = "UPDATE public.\"PeriodoLectivo\" SET\n"
                + " prd_lectivo_activo = false"
                + " WHERE id_prd_lectivo = " + p.getId_PerioLectivo() + ";";
        PreparedStatement ps = conecta.getPS(nsql);
        if (conecta.nosql(ps) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public boolean cerrarPeriodo(PeriodoLectivoMD p) {
        String nsql = "UPDATE public.\"PeriodoLectivo\" SET\n"
                + " prd_lectivo_estado = false"
                + " WHERE id_prd_lectivo = " + p.getId_PerioLectivo() + ";";
        PreparedStatement ps = conecta.getPS(nsql);
        if (conecta.nosql(ps) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public boolean abrirPeriodo(int id) {
        String nsql = "UPDATE public.\"PeriodoLectivo\" SET\n"
                + " prd_lectivo_estado = true"
                + " WHERE id_prd_lectivo = " + id + ";";
        PreparedStatement ps = conecta.getPS(nsql);
        if (conecta.nosql(ps) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public List<PeriodoLectivoMD> periodoDocente(int aguja) {
        String sql = "SELECT DISTINCT p.prd_lectivo_nombre, p.id_prd_lectivo FROM (public.\"PeriodoLectivo\" p JOIN public.\"Cursos\" c USING(id_prd_lectivo)) JOIN\n"
                + "public.\"Docentes\" d USING(id_docente)\n"
                + "WHERE d.id_docente = " + aguja + " AND p.prd_lectivo_activo = true;";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        List<PeriodoLectivoMD> lista = new ArrayList<>();
        try {
            while (rs.next()) {
                PeriodoLectivoMD p = new PeriodoLectivoMD();
                p.setNombre_PerLectivo(rs.getString("prd_lectivo_nombre"));
                lista.add(p);
            }
            rs.close();
            ps.getConnection().close();
            return lista;
        } catch (SQLException ex) {
            System.out.println("No pudimos consultar el periodo: " + ex.getMessage());
            return null;
        }
    }

    public List<CarreraMD> capturarCarrera() {
        List<CarreraMD> lista = new ArrayList();
        String sql = "SELECT id_carrera, carrera_nombre, carrera_codigo FROM public.\"Carreras\" "
                + "WHERE carrera_activo = true;";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        if (rs != null) {
            try {
                while (rs.next()) {
                    CarreraMD a = new CarreraMD();
                    a.setId(rs.getInt("id_carrera"));
                    a.setNombre(rs.getString("carrera_nombre"));
                    a.setCodigo(rs.getString("carrera_codigo"));
                    lista.add(a);
                }
                rs.close();
                ps.getConnection().close();
                return lista;
            } catch (SQLException ex) {
                System.out.println("No pude capturar una carrera: " + ex.getMessage());
                return null;
            }
        } else {
            return null;
        }

    }

    public CarreraMD capturarIdCarrera(String aguja) {
        String sql = "SELECT id_carrera"
                + " FROM public.\"Carreras\" WHERE carrera_nombre LIKE '%" + aguja + "%';";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            CarreraMD c = new CarreraMD();
            while (rs.next()) {
                c.setId(rs.getInt("id_carrera"));
            }
            rs.close();
            ps.getConnection().close();
            return c;
        } catch (SQLException ex) {
            System.out.println("No pude capturar id del periodo: " + ex.getMessage());
            return null;
        }
    }

    public CarreraMD capturarNomCarrera(int aguja) {
        String sql = "SELECT carrera_nombre"
                + " FROM public.\"Carreras\" WHERE id_carrera = " + aguja + ";";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            CarreraMD c = new CarreraMD();
            while (rs.next()) {
                c.setNombre(rs.getString("carrera_nombre"));
            }
            rs.close();
            ps.getConnection().close();
            return c;
        } catch (SQLException ex) {
            System.out.println("Capturamos el nombre de la carrera: " + ex.getMessage());
            return null;
        }
    }

    public List<PeriodoLectivoMD> llenarTabla() {
        List<PeriodoLectivoMD> lista = new ArrayList();
        String sql = "SELECT id_prd_lectivo, id_carrera, prd_lectivo_nombre, prd_lectivo_fecha_inicio, prd_lectivo_fecha_fin"
                + " FROM public.\"PeriodoLectivo\" WHERE prd_lectivo_activo = true";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            while (rs.next()) {
                PeriodoLectivoMD m = new PeriodoLectivoMD();
                carrera = new CarreraMD();
                m.setId_PerioLectivo(rs.getInt("id_prd_lectivo"));
                carrera.setId(rs.getInt("id_carrera"));
                m.setNombre_PerLectivo(rs.getString("prd_lectivo_nombre"));
                m.setFecha_Inicio(rs.getDate("prd_lectivo_fecha_inicio").toLocalDate());
                m.setFecha_Fin(rs.getDate("prd_lectivo_fecha_fin").toLocalDate());
                m.setCarrera(carrera);
                lista.add(m);
            }
            rs.close();
            ps.getConnection().close();
            return lista;
        } catch (SQLException ex) {
            System.out.println("No pudimos consultar para llenar tabla: " + ex.getMessage());
            return null;
        }
    }

    public List<PeriodoLectivoMD> capturarPeriodos(String aguja) {
        List<PeriodoLectivoMD> lista = new ArrayList();
        String sql = "SELECT id_prd_lectivo, pl.id_carrera, prd_lectivo_nombre, prd_lectivo_fecha_inicio, \n"
                + "prd_lectivo_fecha_fin, carrera_nombre, carrera_codigo, prd_lectivo_estado\n"
                + "FROM public.\"PeriodoLectivo\" pl, public.\"Carreras\" c\n"
                + "WHERE c.id_carrera = pl.id_carrera AND\n"
                + "prd_lectivo_activo = true AND(\n"
                + "	prd_lectivo_nombre ILIKE '%" + aguja + "%' OR\n"
                + "	carrera_nombre ILIKE '%" + aguja + "%' OR\n"
                + "	carrera_codigo ILIKE '%" + aguja + "%')\n"
                + "ORDER BY prd_lectivo_fecha_inicio DESC;";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            while (rs.next()) {
                PeriodoLectivoMD p = new PeriodoLectivoMD();

                p.setId_PerioLectivo(rs.getInt("id_prd_lectivo"));
                //Buscamos la carrera para guardarla en la clase
                carrera = new CarreraMD();
                carrera.setId(rs.getInt("id_carrera"));
                carrera.setCodigo(rs.getString("carrera_codigo"));
                carrera.setNombre(rs.getString("carrera_nombre"));
                p.setCarrera(carrera);

                p.setNombre_PerLectivo(rs.getString("prd_lectivo_nombre"));
                p.setFecha_Inicio(rs.getDate("prd_lectivo_fecha_inicio").toLocalDate());
                p.setFecha_Fin(rs.getDate("prd_lectivo_fecha_fin").toLocalDate());
                p.setEstado_PerLectivo(rs.getBoolean("prd_lectivo_estado"));

                lista.add(p);
            }
            ps.getConnection().close();
            rs.close();
            return lista;
        } catch (SQLException ex) {
            System.out.println("No pudimos capturar periodos: " + ex.getMessage());
            return null;
        }
    }

    public PeriodoLectivoMD capturarPerLectivo(int ID) {
        String sql = "SELECT p.id_prd_lectivo, p.id_carrera, p.prd_lectivo_nombre, p.prd_lectivo_fecha_inicio, "
                + "p.prd_lectivo_fecha_fin, p.prd_lectivo_observacion, c.carrera_nombre, p.prd_lectivo_estado"
                + " FROM public.\"PeriodoLectivo\" p JOIN public.\"Carreras\" c USING(id_carrera)"
                + " WHERE p.id_prd_lectivo = "
                + ID + " AND prd_lectivo_activo = true;";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            PeriodoLectivoMD m = new PeriodoLectivoMD();
            carrera = new CarreraMD();
            while (rs.next()) {
                m.setId_PerioLectivo(rs.getInt("id_prd_lectivo"));
                m.setNombre_PerLectivo(rs.getString("prd_lectivo_nombre"));
                m.setFecha_Inicio(rs.getDate("prd_lectivo_fecha_inicio").toLocalDate());
                m.setFecha_Fin(rs.getDate("prd_lectivo_fecha_fin").toLocalDate());
                m.setObservacion_PerLectivo(rs.getString("prd_lectivo_observacion"));
                carrera.setId(rs.getInt("id_carrera"));
                carrera.setNombre(rs.getString("carrera_nombre"));
                m.setEstado_PerLectivo(rs.getBoolean("prd_lectivo_estado"));
                m.setCarrera(carrera);
            }
            rs.close();
            ps.getConnection().close();
            return m;
        } catch (SQLException ex) {
            System.out.println("No pudimos capturar solo un periodo lectivo: " + ex.getMessage());
            return null;
        }
    }

    public ArrayList<PeriodoLectivoMD> cargarPeriodos() {
        ArrayList<PeriodoLectivoMD> lista = new ArrayList();
        String sql = "SELECT id_prd_lectivo, pl.id_carrera, prd_lectivo_nombre, prd_lectivo_fecha_inicio, \n"
                + "prd_lectivo_fecha_fin, carrera_nombre, carrera_codigo, prd_lectivo_estado\n"
                + "FROM public.\"PeriodoLectivo\" pl, public.\"Carreras\" c\n"
                + "WHERE c.id_carrera = pl.id_carrera AND\n"
                + "prd_lectivo_activo = true "
                + "AND carrera_activo = true\n"
                + "ORDER BY prd_lectivo_fecha_inicio DESC;";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            while (rs.next()) {
                PeriodoLectivoMD p = new PeriodoLectivoMD();
                p.setId_PerioLectivo(rs.getInt("id_prd_lectivo"));
                //Buscamos la carrera para guardarla en la clase
                carrera = new CarreraMD();
                carrera.setId(rs.getInt("id_carrera"));
                carrera.setCodigo(rs.getString("carrera_codigo"));
                carrera.setNombre(rs.getString("carrera_nombre"));
                p.setCarrera(carrera);
                p.setNombre_PerLectivo(rs.getString("prd_lectivo_nombre"));
                p.setFecha_Inicio(rs.getDate("prd_lectivo_fecha_inicio").toLocalDate());
                p.setFecha_Fin(rs.getDate("prd_lectivo_fecha_fin").toLocalDate());
                p.setEstado_PerLectivo(rs.getBoolean("prd_lectivo_estado"));

                lista.add(p);
            }
            rs.close();
            ps.getConnection().close();
            return lista;
        } catch (SQLException ex) {
            System.out.println("No pudimos consultar periodos");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public PeriodoLectivoMD buscarPerido(int idPeriodo) {
        PeriodoLectivoMD p = new PeriodoLectivoMD();
        String sql = "SELECT id_prd_lectivo, id_carrera, prd_lectivo_nombre,"
                + " prd_lectivo_fecha_inicio, prd_lectivo_fecha_fin "
                + " FROM public.\"PeriodoLectivo\" WHERE prd_lectivo_activo = true AND "
                + "id_prd_lectivo = " + idPeriodo + ";";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            while (rs.next()) {

                p.setId_PerioLectivo(rs.getInt("id_prd_lectivo"));
                //Buscamos la carrera para guardarla en la clase
                carrera = car.buscar(rs.getInt("id_carrera"));
                p.setCarrera(carrera);

                p.setNombre_PerLectivo(rs.getString("prd_lectivo_nombre"));
                p.setFecha_Inicio(rs.getDate("prd_lectivo_fecha_inicio").toLocalDate());
                p.setFecha_Fin(rs.getDate("prd_lectivo_fecha_fin").toLocalDate());

            }
            rs.close();
            ps.getConnection().close();
            return p;
        } catch (SQLException ex) {
            System.out.println("No pudimos consultar periodos");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Consultamos todos los peridoos para poder filtrar en una ventana.
     * Unicamente indicamos que el periodo no este eliminado.
     *
     * @return periodos ArrayList:
     */
    public ArrayList<PeriodoLectivoMD> cargarPrdParaCmbVtn() {
        String sql = "SELECT id_prd_lectivo, id_carrera, prd_lectivo_nombre\n"
                + "FROM public.\"PeriodoLectivo\"\n"
                + "WHERE prd_lectivo_activo = true \n"
                + "ORDER BY prd_lectivo_fecha_inicio DESC;";
        return consultarParaCmb(sql);
    }

    /**
     * Se consultan los periodos de la base de datos. Restringuiendo los que ya
     * fueron cerrados y los eliminados.
     *
     * @return periodos ArrayList
     */
    public ArrayList<PeriodoLectivoMD> cargarPrdParaCmbFrm() {
        String sql = "SELECT id_prd_lectivo, id_carrera, prd_lectivo_nombre\n"
                + "FROM public.\"PeriodoLectivo\"\n"
                + "WHERE prd_lectivo_activo = true  AND prd_lectivo_estado = true \n"
                + "ORDER BY prd_lectivo_fecha_inicio DESC;";
        return consultarParaCmb(sql);
    }

    /**
     * Consulta para combo, unicamente se busca: id, id_carrera, nombre del
     * periodo
     *
     * @param sql
     * @return periodos ArrayList
     */
    private ArrayList<PeriodoLectivoMD> consultarParaCmb(String sql) {
        ArrayList<PeriodoLectivoMD> prds = new ArrayList();
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            while (rs.next()) {
                PeriodoLectivoMD p = new PeriodoLectivoMD();
                p.setId_PerioLectivo(rs.getInt("id_prd_lectivo"));
                carrera = new CarreraMD();
                carrera.setId(rs.getInt("id_carrera"));
                p.setCarrera(carrera);
                p.setNombre_PerLectivo(rs.getString("prd_lectivo_nombre"));

                prds.add(p);
            }
            rs.close();
            ps.getConnection().close();
            return prds;
        } catch (SQLException ex) {
            System.out.println("No pudimos consultar periodos para combo");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public PeriodoLectivoMD capturarIdPeriodo(String nombrePer) {
        PeriodoLectivoMD p = new PeriodoLectivoMD();
        String sql = "SELECT id_prd_lectivo FROM public.\"PeriodoLectivo\" WHERE prd_lectivo_activo = true AND "
                + "prd_lectivo_nombre LIKE '" + nombrePer + "';";
        ResultSet rs = conecta.sql(sql);
        try {
            while (rs.next()) {
                p.setId_PerioLectivo(rs.getInt("id_prd_lectivo"));
            }
            rs.close();
            return p;
        } catch (SQLException ex) {
            System.out.println("No pudimos consultar periodos para combo");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public PeriodoLectivoMD buscarPeriodo(String nombrePer) {
        PeriodoLectivoMD p = new PeriodoLectivoMD();
        String sql = "SELECT id_prd_lectivo, id_carrera, prd_lectivo_nombre,"
                + " prd_lectivo_fecha_inicio, prd_lectivo_fecha_fin "
                + " FROM public.\"PeriodoLectivo\" WHERE prd_lectivo_activo = true AND "
                + "prd_lectivo_nombre LIKE '" + nombrePer + "';";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(sql);
        try {
            while (rs.next()) {

                p.setId_PerioLectivo(rs.getInt("id_prd_lectivo"));
                //Buscamos la carrera para guardarla en la clase
                carrera = car.buscar(rs.getInt("id_carrera"));
                p.setCarrera(carrera);

                p.setNombre_PerLectivo(rs.getString("prd_lectivo_nombre"));
                p.setFecha_Inicio(rs.getDate("prd_lectivo_fecha_inicio").toLocalDate());
                p.setFecha_Fin(rs.getDate("prd_lectivo_fecha_fin").toLocalDate());

            }
            rs.close();
            ps.getConnection().close();
            return p;
        } catch (SQLException ex) {
            System.out.println("No pudimos consultar periodos");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public String Meses(LocalDate fecha) {
        String nueva_Fecha = "";
        String nuevo_Mes = "";
        switch (fecha.getMonth().toString()) {
            case "JANUARY":
                nuevo_Mes = "ENERO";
                break;
            case "FEBRUARY":
                nuevo_Mes = "FEBRERO";
                break;
            case "MARCH":
                nuevo_Mes = "MARZO";
                break;
            case "APRIL":
                nuevo_Mes = "ABRIL";
                break;
            case "MAY":
                nuevo_Mes = "MAYO";
                break;
            case "JUNE":
                nuevo_Mes = "JUNIO";
                break;
            case "JULY":
                nuevo_Mes = "JULIO";
                break;
            case "AUGUST":
                nuevo_Mes = "AGOSTO";
                break;
            case "SEPTEMBER":
                nuevo_Mes = "SEPTIEMBRE";
                break;
            case "OCTOBER":
                nuevo_Mes = "OCTUBRE";
                break;
            case "NOVEMBER":
                nuevo_Mes = "NOVIEMBRE";
                break;
            case "DECEMBER":
                nuevo_Mes = "DICIEMBRE";
                break;
        }
        return nueva_Fecha = nuevo_Mes + "/" + fecha.getYear();
    }

    public String alumnosMatriculados(int ID) {
        System.out.println("ID: " + ID);
        String sql = "SELECT COUNT(*) AS numeroAlumnos FROM (public.\"Carreras\" c JOIN public.\"AlumnosCarrera\" a\n"
                + "					  USING(id_carrera)) JOIN public.\"MallaAlumno\" m USING(id_almn_carrera)\n"
                + "							WHERE c.id_carrera = " + ID + " AND m.malla_almn_estado LIKE 'M';";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        String count = "";
        try {
            while (rs.next()) {
                count = String.valueOf(rs.getInt("numeroAlumnos"));
            }
            rs.close();
            ps.getConnection().close();
            return count;
        } catch (SQLException ex) {
            System.out.println("No pudimos consultar periodos para combo");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public LocalDate buscarFechaInicioPrd(int idPrd) {
        LocalDate fi = null;
        String sql = "SELECT prd_lectivo_fecha_inicio \n"
                + "FROM public.\"PeriodoLectivo\"\n"
                + "WHERE id_prd_lectivo = " + idPrd + ";";
        ResultSet rs = conecta.sql(sql);
        PreparedStatement ps = conecta.getPS(sql);
        if (rs != null) {
            try {
                while (rs.next()) {
                    fi = rs.getDate("prd_lectivo_fecha_inicio").toLocalDate();
                }
                ps.getConnection().close();
            } catch (SQLException e) {
                System.out.println("No pudimos consultar fecha inicio del periodo: " + e.getMessage());
            }
        }
        return fi;
    }

    public static List<PeriodoLectivoMD> selectPeriodoWhere(int idDocente) {
        String SELECT = "SELECT DISTINCT\n"
                + "\"public\".\"PeriodoLectivo\".id_prd_lectivo,\n"
                + "\"public\".\"PeriodoLectivo\".prd_lectivo_nombre,\n"
                + "\"public\".\"PeriodoLectivo\".id_carrera,\n"
                + "\"public\".\"Carreras\".carrera_nombre,\n"
                + "\"public\".\"Carreras\".carrera_modalidad,\n"
                + "\"public\".\"PeriodoLectivo\".prd_lectivo_estado,\n"
                + "\"public\".\"PeriodoLectivo\".prd_lectivo_activo,\n"
                + "\"public\".\"PeriodoLectivo\".prd_lectivo_fecha_inicio,\n"
                + "\"public\".\"PeriodoLectivo\".prd_lectivo_fecha_fin\n"
                + "FROM\n"
                + "\"public\".\"PeriodoLectivo\"\n"
                + "INNER JOIN \"public\".\"Carreras\" ON \"public\".\"PeriodoLectivo\".id_carrera = \"public\".\"Carreras\".id_carrera\n"
                + "INNER JOIN \"public\".\"Cursos\" ON \"public\".\"Cursos\".id_prd_lectivo = \"public\".\"PeriodoLectivo\".id_prd_lectivo\n"
                + "INNER JOIN \"public\".\"Docentes\" ON \"public\".\"Cursos\".id_docente = \"public\".\"Docentes\".id_docente\n"
                + "WHERE\n"
                + " \"public\".\"Docentes\".id_docente = " + idDocente;

        List<PeriodoLectivoMD> lista = new ArrayList<>();

        conn = POOL.getConnection();
        rst = POOL.ejecutarQuery(SELECT, conn, null);

        try {
            while (rst.next()) {

                PeriodoLectivoMD periodo = new PeriodoLectivoMD();
                periodo.setId_PerioLectivo(rst.getInt("id_prd_lectivo"));
                periodo.setNombre_PerLectivo(rst.getString("prd_lectivo_nombre"));

                CarreraMD carrera = new CarreraMD();
                carrera.setId(rst.getInt("id_carrera"));
                carrera.setNombre(rst.getString("carrera_nombre"));
                carrera.setModalidad(rst.getString("carrera_modalidad"));
                periodo.setCarrera(carrera);

                periodo.setEstado_PerLectivo(rst.getBoolean("prd_lectivo_estado"));
                periodo.setActivo_PerLectivo(rst.getBoolean("prd_lectivo_activo"));
                periodo.setFecha_Inicio(rst.getDate("prd_lectivo_fecha_inicio").toLocalDate());
                periodo.setFecha_Fin(rst.getDate("prd_lectivo_fecha_fin").toLocalDate());

                lista.add(periodo);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            POOL.close(conn);
        }
        return lista;
    }

    public static List<PeriodoLectivoMD> selectIdNombreAll() {

        String SELECT = "SELECT id_prd_lectivo, prd_lectivo_nombre "
                + "FROM \"PeriodoLectivo\" \n"
                + "ORDER BY prd_lectivo_fecha_inicio ASC";

        System.out.println("-->" + SELECT);

        List<PeriodoLectivoMD> lista = new ArrayList<>();
        conn = POOL.getConnection();
        rst = POOL.ejecutarQuery(SELECT, conn, null);

        try {
            while (rst.next()) {
                PeriodoLectivoMD periodo = new PeriodoLectivoMD();
                periodo.setId_PerioLectivo(rst.getInt("id_prd_lectivo"));
                periodo.setNombre_PerLectivo(rst.getString("prd_lectivo_nombre"));
                lista.add(periodo);
            }
        } catch (SQLException | NullPointerException e) {
            if (e instanceof SQLException) {
                System.out.println(e.getMessage());
            }
        } finally {
            POOL.close(conn);
        }
        return lista;
    }

    public static Map<String, PeriodoLectivoMD> selectPeriodosFaltantes() {
        String SELECT = "SELECT DISTINCT\n"
                + "	p1.id_prd_lectivo,\n"
                + "	p1.id_carrera,\n"
                + "	p1.prd_lectivo_nombre,\n"
                + "	\"Carreras\".carrera_nombre,\n"
                + "	\"Carreras\".carrera_modalidad \n"
                + "FROM\n"
                + "\"PeriodoLectivo\" p1\n"
                + "	INNER JOIN \"public\".\"Carreras\" ON p1.id_carrera = \"public\".\"Carreras\".id_carrera \n"
                + "WHERE\n"
                + "	7 != (\n"
                + "	SELECT\n"
                + "		\"count\" ( * ) \n"
                + "	FROM\n"
                + "		\"public\".\"TipoDeNota\" AS t2\n"
                + "		INNER JOIN \"public\".\"PeriodoLectivo\" ON t2.id_prd_lectivo = \"public\".\"PeriodoLectivo\".id_prd_lectivo\n"
                + "		INNER JOIN \"public\".\"Carreras\" ON \"public\".\"PeriodoLectivo\".id_carrera = \"public\".\"Carreras\".id_carrera \n"
                + "	WHERE\n"
                + "		t2.id_prd_lectivo = p1.id_prd_lectivo \n"
                + "		AND ( \"Carreras\".carrera_modalidad ='PRESENCIAL' OR \"Carreras\".carrera_modalidad ='TRADICIONAL' ) \n"
                + "	) \n"
                + "	AND 11 != (\n"
                + "	SELECT\n"
                + "		\"count\" ( * ) \n"
                + "	FROM\n"
                + "		\"public\".\"TipoDeNota\" AS t2\n"
                + "		INNER JOIN \"public\".\"PeriodoLectivo\" ON t2.id_prd_lectivo = \"public\".\"PeriodoLectivo\".id_prd_lectivo\n"
                + "		INNER JOIN \"public\".\"Carreras\" ON \"public\".\"PeriodoLectivo\".id_carrera = \"public\".\"Carreras\".id_carrera \n"
                + "	WHERE\n"
                + "		t2.id_prd_lectivo = p1.id_prd_lectivo \n"
                + "	AND ( \"Carreras\".carrera_modalidad ='DUAL' OR \"Carreras\".carrera_modalidad ='DUAL FOCALIZADA' ) \n"
                + "	)\n"
                + "	\n"
                + "ORDER BY p1.prd_lectivo_nombre";

        System.out.println(SELECT);

        Map<String, PeriodoLectivoMD> map = new HashMap<>();

        //System.out.println(SELECT);
        conn = POOL.getConnection();
        rst = POOL.ejecutarQuery(SELECT, conn, null);

        try {
            while (rst.next()) {

                PeriodoLectivoMD periodo = new PeriodoLectivoMD();
                periodo.setId_PerioLectivo(rst.getInt("id_prd_lectivo"));
                periodo.setNombre_PerLectivo(rst.getString("prd_lectivo_nombre"));

                CarreraMD carrera = new CarreraMD();
                carrera.setId(rst.getInt("id_carrera"));
                carrera.setNombre(rst.getString("carrera_nombre"));
                carrera.setModalidad(rst.getString("carrera_modalidad"));
                periodo.setCarrera(carrera);

                String key = rst.getString("prd_lectivo_nombre");

                map.put(key, periodo);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            POOL.close(conn);
        }

        return map;
    }


    public Map<String, PeriodoLectivoMD> selectWhere(String nombrePeriodo) {

        String SELECT = "SELECT DISTINCT\n"
                + "	\"PeriodoLectivo\".id_prd_lectivo,\n"
                + "	\"PeriodoLectivo\".id_carrera,\n"
                + "	\"PeriodoLectivo\".prd_lectivo_nombre,\n"
                + "	\"Carreras\".carrera_nombre,\n"
                + "	\"Carreras\".carrera_modalidad \n"
                + "FROM\n"
                + "	\"PeriodoLectivo\" \n"
                + "	INNER JOIN \"public\".\"Carreras\" ON \"PeriodoLectivo\".id_carrera = \"public\".\"Carreras\".id_carrera \n"
                + "WHERE\n"
                + "	\"PeriodoLectivo\".prd_lectivo_nombre = ?";
        Map<String, PeriodoLectivoMD> map = new HashMap<>();

        Map<Integer, Object> parametros = new HashMap<>();
        parametros.put(1, nombrePeriodo);

        conn = pool.getConnection();
        rst = pool.ejecutarQuery(SELECT, conn, parametros);

        System.out.println("--->" + pool.getStmt());

        try {
            while (rst.next()) {

                PeriodoLectivoMD periodo = new PeriodoLectivoMD();
                periodo.setId_PerioLectivo(rst.getInt("id_prd_lectivo"));
                periodo.setNombre_PerLectivo(rst.getString("prd_lectivo_nombre"));

                CarreraMD carreraMap = new CarreraMD();
                carreraMap.setId(rst.getInt("id_carrera"));
                carreraMap.setNombre(rst.getString("carrera_nombre"));
                carreraMap.setModalidad(rst.getString("carrera_modalidad"));
                periodo.setCarrera(carreraMap);

                String key = rst.getString("prd_lectivo_nombre");

                map.put(key, periodo);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            pool.close(conn);
        }

        return map;
    }


    
    
    public static List<PeriodoLectivoMD> buscarNumSemanas(int idDocente, int idPrd) {
        String SELECT = "SELECT DISTINCT\n"
                + " \"public\".\"Docentes\".id_docente,\n"
                + " \"public\".\"PeriodoLectivo\".prd_lectivo_nombre,\n"
                + " (prd_lectivo_fecha_fin - prd_lectivo_fecha_inicio)/7 AS semanas\n"
                + " FROM\n"
                + " \"public\".\"Carreras\"\n"
                + " INNER JOIN \"public\".\"PeriodoLectivo\" ON \"public\".\"PeriodoLectivo\".id_carrera = \"public\".\"Carreras\".id_carrera\n"
                + " INNER JOIN \"public\".\"Cursos\" ON \"public\".\"Cursos\".id_prd_lectivo = \"public\".\"PeriodoLectivo\".id_prd_lectivo\n"
                + " INNER JOIN \"public\".\"Docentes\" ON \"public\".\"Docentes\".id_docente = \"public\".\"Cursos\".id_docente\n"
                + " INNER JOIN \"public\".\"Materias\" ON \"public\".\"Materias\".id_carrera = \"public\".\"Carreras\".id_carrera\n"
                + " INNER JOIN \"public\".\"SesionClase\" ON \"public\".\"SesionClase\".id_curso = \"public\".\"Cursos\".id_curso\n"
                + " WHERE\n"
                + " \"public\".\"Cursos\".id_docente = " + idDocente + " AND\n"
                + " \"public\".\"PeriodoLectivo\".id_prd_lectivo = " + idPrd + "";
        List<PeriodoLectivoMD> semana = new ArrayList<>();
        conn = pool.getConnection();
        rst = pool.ejecutarQuery(SELECT, conn, null);
        
        System.out.println("Query: \n"+SELECT);

        try {
            while (rst.next()) {
                PeriodoLectivoMD periodo = new PeriodoLectivoMD();
                
                periodo.setNombre_PerLectivo(rst.getString("prd_lectivo_nombre"));
                System.out.println("Semanas "+rst.getInt(3) );
                periodo.setNumSemanas(rst.getInt(3));
                semana.add(periodo);
            }
        } catch (SQLException | NullPointerException e) {
            if (e instanceof SQLException) {
                System.out.println(e.getMessage());
            }
        } finally {
            pool.close(conn);
        }
        return semana;

    }
    

}
