package modelo.periodolectivo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConectarDB;
import modelo.ResourceManager;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.persona.AlumnoBD;

public class PeriodoLectivoBD extends PeriodoLectivoMD {

    private final ConectarDB conecta;
    //Para guardar carrera en un periodo  
    private final CarreraBD car;

    private CarreraMD carrera;

    public PeriodoLectivoBD(ConectarDB conecta) {
        this.conecta = conecta;
        this.car = new CarreraBD(conecta);
    }

    public boolean guardarPeriodo(PeriodoLectivoMD p, CarreraMD c) {
        String nsql = "INSERT INTO public.\"PeriodoLectivo\"(\n"
                + "id_carrera, prd_lectivo_nombre, prd_lectivo_fecha_inicio, prd_lectivo_fecha_fin, prd_lectivo_observacion, prd_lectivo_activo, prd_lectivo_estado)"
                + " VALUES( " + c.getId() + ", '" + p.getNombre_PerLectivo().toUpperCase() + "   " + Meses(p.getFecha_Inicio()) + "   " + Meses(p.getFecha_Fin()) + "', '" + p.getFecha_Inicio()
                + "', '" + p.getFecha_Fin() + "', '" + p.getObservacion_PerLectivo().toUpperCase() + "', true, true);";
        if (conecta.nosql(nsql) == null) {
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
        if (conecta.nosql(nsql) == null) {
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

        if (conecta.nosql(nsql) == null) {
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
        if (conecta.nosql(nsql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public boolean abrirPeriodo(int id) {
        String sql = "UPDATE public.\"PeriodoLectivo\" SET\n"
                + " prd_lectivo_estado = true"
                + " WHERE id_prd_lectivo = " + id + ";";
        if (conecta.nosql(sql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    public List<PeriodoLectivoMD> periodoDocente(String aguja) {
        String sql = "SELECT DISTINCT p.prd_lectivo_nombre FROM (public.\"PeriodoLectivo\" p JOIN public.\"Cursos\" c USING(id_prd_lectivo)) JOIN\n"
                + "public.\"Docentes\" d USING(id_docente)\n"
                + "WHERE d.docente_codigo LIKE '" + aguja + "';";
        ResultSet rs = conecta.sql(sql);
        List<PeriodoLectivoMD> lista = new ArrayList<>();
        try {
            while (rs.next()) {
                PeriodoLectivoMD p = new PeriodoLectivoMD();
                p.setNombre_PerLectivo(rs.getString("prd_lectivo_nombre"));
                lista.add(p);
            }
            rs.close();
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(PeriodoLectivoBD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<CarreraMD> capturarCarrera() {
        List<CarreraMD> lista = new ArrayList();
        String sql = "SELECT id_carrera, carrera_nombre, carrera_codigo FROM public.\"Carreras\" "
                + "WHERE carrera_activo = true;";
        ResultSet rs = conecta.sql(sql);
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
                return lista;
            } catch (SQLException ex) {
                Logger.getLogger(PeriodoLectivoBD.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } else {
            return null;
        }

    }

    public CarreraMD capturarIdCarrera(String aguja) {
        String sql = "SELECT id_carrera"
                + " FROM public.\"Carreras\" WHERE carrera_nombre LIKE '%" + aguja + "%';";
        ResultSet rs = conecta.sql(sql);
        try {
            CarreraMD c = new CarreraMD();
            while (rs.next()) {
                c.setId(rs.getInt("id_carrera"));
            }
            rs.close();
            return c;
        } catch (SQLException ex) {
            Logger.getLogger(PeriodoLectivoBD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public CarreraMD capturarNomCarrera(int aguja) {
        String sql = "SELECT carrera_nombre"
                + " FROM public.\"Carreras\" WHERE id_carrera = " + aguja + ";";
        ResultSet rs = conecta.sql(sql);
        try {
            CarreraMD c = new CarreraMD();
            while (rs.next()) {
                c.setNombre(rs.getString("carrera_nombre"));
            }
            rs.close();
            return c;
        } catch (SQLException ex) {
            Logger.getLogger(PeriodoLectivoBD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<PeriodoLectivoMD> llenarTabla() {
        List<PeriodoLectivoMD> lista = new ArrayList();
        String sql = "SELECT id_prd_lectivo, id_carrera, prd_lectivo_nombre, prd_lectivo_fecha_inicio, prd_lectivo_fecha_fin"
                + " FROM public.\"PeriodoLectivo\" WHERE prd_lectivo_activo = true";
        ResultSet rs = conecta.sql(sql);
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
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoBD.class.getName()).log(Level.SEVERE, null, ex);
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
        //System.out.println(sql);
        ResultSet rs = conecta.sql(sql);
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
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoBD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public PeriodoLectivoMD capturarPerLectivo(int ID) {
        String sql = "SELECT p.id_prd_lectivo, p.id_carrera, p.prd_lectivo_nombre, p.prd_lectivo_fecha_inicio, "
                + "p.prd_lectivo_fecha_fin, p.prd_lectivo_observacion, c.carrera_nombre, p.prd_lectivo_estado"
                + " FROM public.\"PeriodoLectivo\" p JOIN public.\"Carreras\" c USING(id_carrera)"
                + " WHERE p.id_prd_lectivo = "
                + ID + " AND prd_lectivo_activo = true;";
        ResultSet rs = conecta.sql(sql);
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
            return m;
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoBD.class.getName()).log(Level.SEVERE, null, ex);
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
        ResultSet rs = conecta.sql(sql);
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
        ResultSet rs = conecta.sql(sql);
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
            return prds;
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
        ResultSet rs = conecta.sql(sql);
        String count = "";
        try {
            while (rs.next()) {
                count = String.valueOf(rs.getInt("numeroAlumnos"));
            }
            rs.close();
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
        if (rs != null) {
            try {
                while (rs.next()) {
                    fi = rs.getDate("prd_lectivo_fecha_inicio").toLocalDate();
                }
            } catch (SQLException e) {
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

        ResultSet rs = ResourceManager.Query(SELECT);

        try {
            while (rs.next()) {

                PeriodoLectivoMD periodo = new PeriodoLectivoMD();
                periodo.setId_PerioLectivo(rs.getInt("id_prd_lectivo"));
                periodo.setNombre_PerLectivo(rs.getString("prd_lectivo_nombre"));

                CarreraMD carrera = new CarreraMD();
                carrera.setId(rs.getInt("id_carrera"));
                carrera.setNombre(rs.getString("carrera_nombre"));
                carrera.setModalidad(rs.getString("carrera_modalidad"));
                periodo.setCarrera(carrera);

                periodo.setEstado_PerLectivo(rs.getBoolean("prd_lectivo_estado"));
                periodo.setActivo_PerLectivo(rs.getBoolean("prd_lectivo_activo"));
                periodo.setFecha_Inicio(rs.getDate("prd_lectivo_fecha_inicio").toLocalDate());
                periodo.setFecha_Fin(rs.getDate("prd_lectivo_fecha_fin").toLocalDate());

                lista.add(periodo);

            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lista;
    }

    public static PeriodoLectivoMD selectWhere(int idPeriodo) {
        PeriodoLectivoMD periodo = new PeriodoLectivoMD();

        String SELECT = "SELECT\n"
                + "\"public\".\"PeriodoLectivo\".id_prd_lectivo,\n"
                + "\"public\".\"PeriodoLectivo\".id_carrera,\n"
                + "\"public\".\"PeriodoLectivo\".prd_lectivo_nombre,\n"
                + "\"public\".\"PeriodoLectivo\".prd_lectivo_fecha_inicio,\n"
                + "\"public\".\"PeriodoLectivo\".prd_lectivo_fecha_fin,\n"
                + "\"public\".\"PeriodoLectivo\".prd_lectivo_observacion,\n"
                + "\"public\".\"PeriodoLectivo\".prd_lectivo_activo,\n"
                + "\"public\".\"PeriodoLectivo\".prd_lectivo_estado\n"
                + "FROM\n"
                + "\"public\".\"PeriodoLectivo\"\n"
                + "WHERE\n"
                + "\"public\".\"PeriodoLectivo\".id_prd_lectivo = " + idPeriodo;

        ResultSet rs = ResourceManager.Query(SELECT);

        try {
            while (rs.next()) {

                periodo.setId_PerioLectivo(rs.getInt("id_prd_lectivo"));
                periodo.setCarrera(null);
                periodo.setNombre_PerLectivo(rs.getString("prd_lectivo_nombre"));
                periodo.setFecha_Inicio(rs.getDate("prd_lectivo_fecha_inicio").toLocalDate());
                periodo.setFecha_Fin(rs.getDate("prd_lectivo_fecha_fin").toLocalDate());
                periodo.setActivo_PerLectivo(rs.getBoolean("prd_lectivo_activo"));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return periodo;
    }

    public static List<PeriodoLectivoMD> SelectAll() {

        String SELECT = "SELECT id_prd_lectivo, prd_lectivo_nombre "
                + "FROM \"PeriodoLectivo\" ";
        List<PeriodoLectivoMD> lista = new ArrayList<>();
        ResultSet rs = ResourceManager.Query(SELECT);

        try {
            while (rs.next()) {
                PeriodoLectivoMD periodo = new PeriodoLectivoMD();
                periodo.setId_PerioLectivo(rs.getInt("id_prd_lectivo"));
                periodo.setNombre_PerLectivo(rs.getString("prd_lectivo_nombre"));
                lista.add(periodo);
            }
            rs.close();
        } catch (SQLException | NullPointerException e) {
            if (e instanceof SQLException) {
                System.out.println(e.getMessage());
            }

        }
        return lista;
    }

    public static Map<String, PeriodoLectivoMD> selectWhereEstadoAndActivo(boolean estado, boolean activo) {
        String SELECT = "SELECT\n"
                + "	\"public\".\"PeriodoLectivo\".id_prd_lectivo,\n"
                + "	\"public\".\"PeriodoLectivo\".id_carrera,\n"
                + "	\"public\".\"PeriodoLectivo\".prd_lectivo_nombre,\n"
                + "	\"public\".\"PeriodoLectivo\".prd_lectivo_estado,\n"
                + "	\"public\".\"PeriodoLectivo\".prd_lectivo_activo,\n"
                + "	\"public\".\"Carreras\".carrera_nombre, \n"
                + "	\"public\".\"Carreras\".carrera_modalidad \n"
                + "FROM\n"
                + "	\"public\".\"PeriodoLectivo\"\n"
                + "	INNER JOIN \"public\".\"Carreras\" ON \"public\".\"PeriodoLectivo\".id_carrera = \"public\".\"Carreras\".id_carrera \n"
                + "WHERE\n"
                + "	\"PeriodoLectivo\".prd_lectivo_estado IS " + estado + " \n"
                + "	AND \"PeriodoLectivo\".prd_lectivo_activo IS " + activo;

        Map<String, PeriodoLectivoMD> map = new HashMap<>();

        ResultSet rs = ResourceManager.Query(SELECT);

        try {
            while (rs.next()) {

                PeriodoLectivoMD periodo = new PeriodoLectivoMD();
                periodo.setId_PerioLectivo(rs.getInt("id_prd_lectivo"));
                periodo.setNombre_PerLectivo(rs.getString("prd_lectivo_nombre"));
                periodo.setEstado_PerLectivo(rs.getBoolean("prd_lectivo_estado"));
                periodo.setActivo_PerLectivo(rs.getBoolean("prd_lectivo_activo"));

                CarreraMD carrera = new CarreraMD();
                carrera.setId(rs.getInt("id_carrera"));
                carrera.setNombre(rs.getString("carrera_nombre"));
                carrera.setModalidad(rs.getString("carrera_modalidad"));
                periodo.setCarrera(carrera);

                String key = rs.getString("prd_lectivo_nombre");

                map.put(key, periodo);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return map;
    }

}
