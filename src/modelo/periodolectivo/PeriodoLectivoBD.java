package modelo.periodolectivo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
                + "id_carrera, prd_lectivo_nombre, prd_lectivo_fecha_inicio, prd_lectivo_fecha_fin, prd_lectivo_observacion, prd_lectivo_activo)"
                + " VALUES( " + c.getId() + ", '" + p.getNombre_PerLectivo().toUpperCase() + "   " + Meses(p.getFecha_Inicio()) + "   " + Meses(p.getFecha_Fin()) + "', '" + p.getFecha_Inicio()
                + "', '" + p.getFecha_Fin() + "', '" + p.getObservacion_PerLectivo().toUpperCase() + "', true);";
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

    public List<CarreraMD> capturarCarrera() {
        List<CarreraMD> lista = new ArrayList();
        String sql = "SELECT id_carrera, carrera_nombre FROM public.\"Carreras\";";
        ResultSet rs = conecta.sql(sql);
        try {
            while (rs.next()) {
                CarreraMD a = new CarreraMD();
                a.setId(rs.getInt("id_carrera"));
                a.setNombre(rs.getString("carrera_nombre"));
                lista.add(a);
            }
            rs.close();
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(PeriodoLectivoBD.class.getName()).log(Level.SEVERE, null, ex);
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
                + " FROM public.\"PeriodoLectivo\" WHERE prd_lectivo_activo = true;";
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
                + "prd_lectivo_fecha_fin, carrera_nombre, carrera_codigo\n"
                + "FROM public.\"PeriodoLectivo\" pl, public.\"Carreras\" c\n"
                + "WHERE c.id_carrera = pl.id_carrera AND\n"
                + "prd_lectivo_activo = true AND (\n"
                + "	prd_lectivo_nombre ILIKE '%" + aguja + "%' OR\n"
                + "	carrera_nombre ILIKE '%" + aguja + "%' OR\n"
                + "	carrera_codigo ILIKE '%" + aguja + "%')\n"
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
        String sql = "SELECT p.id_prd_lectivo, p.id_carrera, p.prd_lectivo_nombre, p.prd_lectivo_fecha_inicio, p.prd_lectivo_fecha_fin, p.prd_lectivo_observacion, c.carrera_nombre"
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
                + "prd_lectivo_fecha_fin, carrera_nombre, carrera_codigo\n"
                + "FROM public.\"PeriodoLectivo\" pl, public.\"Carreras\" c\n"
                + "WHERE c.id_carrera = pl.id_carrera AND\n"
                + "prd_lectivo_activo = true\n"
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
        return nueva_Fecha = fecha.getDayOfMonth() + "/" + nuevo_Mes + "/" + "20" + fecha.getYear();
    }

    public static List<String> selectPeriodoWhereUsername(String username) {
        String SELECT = "SELECT\n"
                + "DISTINCT \"PeriodoLectivo\".prd_lectivo_nombre\n"
                + "FROM\n"
                + "\"Usuarios\"\n"
                + "INNER JOIN \"Personas\" ON \"Usuarios\".id_persona = \"Personas\".id_persona\n"
                + "INNER JOIN \"Docentes\" ON \"Docentes\".id_persona = \"Personas\".id_persona\n"
                + "INNER JOIN \"Cursos\" ON \"Cursos\".id_docente = \"Docentes\".id_docente\n"
                + "INNER JOIN \"PeriodoLectivo\" ON \"Cursos\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo\n"
                + "WHERE\n"
                + "\"Usuarios\".usu_username = '" + username + "'"
                + "AND\n"
                + "\"PeriodoLectivo\".prd_lectivo_estado = false";
        List<String> lista = new ArrayList<>();

        ResultSet rs = ResourceManager.Query(SELECT);

        try {
            while (rs.next()) {

                String paralelo = rs.getString("prd_lectivo_nombre");

                lista.add(paralelo);

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
                + "\"PeriodoLectivo\".id_prd_lectivo,\n"
                + "\"PeriodoLectivo\".id_carrera,\n"
                + "\"PeriodoLectivo\".prd_lectivo_nombre,\n"
                + "\"PeriodoLectivo\".prd_lectivo_fecha_inicio,\n"
                + "\"PeriodoLectivo\".prd_lectivo_fecha_fin,\n"
                + "\"PeriodoLectivo\".prd_lectivo_observacion,\n"
                + "\"PeriodoLectivo\".prd_lectivo_activo,\n"
                + "\"PeriodoLectivo\".prd_lectivo_estado\n"
                + "FROM\n"
                + "\"PeriodoLectivo\"\n"
                + "WHERE \n"
                + "\"PeriodoLectivo\".id_prd_lectivo = " + idPeriodo;

        ResultSet rs = ResourceManager.Query(SELECT);

        try {

            while (rs.next()) {

                periodo.setId_PerioLectivo(rs.getInt("id_prd_lectivo"));

                CarreraMD carrera = new CarreraMD();
                carrera.setId(rs.getInt("id_carrera"));

                periodo.setCarrera(carrera);

                periodo.setNombre_PerLectivo(rs.getString("prd_lectivo_nombre"));
                periodo.setFecha_Inicio(rs.getDate("prd_lectivo_fecha_inicio").toLocalDate());
                periodo.setFecha_Fin(rs.getDate("prd_lectivo_fecha_fin").toLocalDate());
                periodo.setObservacion_PerLectivo(rs.getString("prd_lectivo_observacion"));
                periodo.setActivo_PerLectivo(rs.getBoolean("prd_lectivo_activo"));

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return periodo;
    }

}
