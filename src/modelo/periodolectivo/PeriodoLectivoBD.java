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
import modelo.ConnDBPool;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.persona.DocenteMD;
import utils.CONBD;
import utils.M;

public class PeriodoLectivoBD extends CONBD {

    //Para guardar carrera en un periodo  
    private CarreraBD car;

    private CarreraMD carrera;

    private final ConnDBPool pool;
    private Connection conn;
    private ResultSet rst;
    private static PeriodoLectivoBD PLBD;

    public static PeriodoLectivoBD single() {
        if (PLBD == null) {
            PLBD = new PeriodoLectivoBD();
        }
        return PLBD;
    }

    {
        pool = new ConnDBPool();
    }

    public boolean guardarPeriodo(PeriodoLectivoMD p, CarreraMD c) {
        String nsql = "INSERT INTO public.\"PeriodoLectivo\"(\n"
                + "id_carrera, "
                + "prd_lectivo_nombre, "
                + "prd_lectivo_fecha_inicio, "
                + "prd_lectivo_fecha_fin, "
                + "prd_lectivo_observacion, "
                + "prd_lectivo_activo, "
                + "prd_lectivo_estado,"
                + "prd_lectivo_fecha_fin_clases,"
                + "prd_lectivo_coordinador )"
                + " VALUES( "
                + "" + c.getId() + ", "
                + "'" + p.getNombre().toUpperCase()
                + " " + Meses(p.getFechaInicio())
                + "  " + Meses(p.getFechaFin()) + "', "
                + "'" + p.getFechaInicio() + "', "
                + "'" + p.getFechaFin() + "', "
                + "'" + p.getObservacion().toUpperCase() + "', "
                + "true, "
                + "true, "
                + "'" + p.getFechaFinClases() + "', "
                + p.getDocente().getIdDocente()
                + ");";
        return CON.executeNoSQL(nsql);
    }

    public boolean editarPeriodo(PeriodoLectivoMD p, CarreraMD c) {
        String nsql = "UPDATE public.\"PeriodoLectivo\" SET "
                + " id_carrera = " + c.getId() + ", "
                + "prd_lectivo_nombre = '" + p.getNombre() + "',"
                + "prd_lectivo_fecha_inicio = '" + p.getFechaInicio() + "', "
                + "prd_lectivo_fecha_fin = '" + p.getFechaFin() + "', "
                + "prd_lectivo_observacion = '" + p.getObservacion() + "', "
                + "prd_lectivo_fecha_fin_clases = '" + p.getFechaFinClases() + "', "
                + "prd_lectivo_coordinador = " + p.getDocente().getIdDocente()
                + "WHERE id_prd_lectivo = " + p.getID() + ";";
        return CON.executeNoSQL(nsql);
    }

    public boolean eliminarPeriodo(PeriodoLectivoMD p) {
        String nsql = "UPDATE public.\"PeriodoLectivo\" "
                + "SET prd_lectivo_activo = false"
                + " WHERE id_prd_lectivo = " + p.getID() + ";";
        return CON.executeNoSQL(nsql);
    }

    public boolean cerrarPeriodo(PeriodoLectivoMD p) {
        String nsql = "UPDATE public.\"PeriodoLectivo\" "
                + "SET prd_lectivo_estado = false "
                + "WHERE id_prd_lectivo = " + p.getID() + ";";
        return CON.executeNoSQL(nsql);
    }

    public boolean abrirPeriodo(int id) {
        String nsql = "UPDATE public.\"PeriodoLectivo\" "
                + "SET  prd_lectivo_estado = true"
                + "WHERE id_prd_lectivo = " + id + ";";
        return CON.executeNoSQL(nsql);
    }

    public List<PeriodoLectivoMD> llenarPeriodosxCarreras(int idCarrera) {
        String sql = "SELECT "
                + " p.id_prd_lectivo, "
                + "c.id_carrera, "
                + "p.prd_lectivo_nombre, "
                + "p.prd_lectivo_fecha_inicio, "
                + "p.prd_lectivo_fecha_fin, "
                + "p.prd_lectivo_estado, "
                + "c.carrera_nombre,"
                + "persona_primer_nombre,"
                + "persona_primer_apellido "
                + "FROM public.\"PeriodoLectivo\" p "
                + "JOIN public.\"Carreras\" c USING(id_carrera) "
                + "JOIN public.\"Docentes\" d ON d.id_docente = p.prd_lectivo_coordinador "
                + "JOIN public.\"Personas\" pr ON pr.id_persona = d.id_persona "
                + "WHERE c.id_carrera = " + idCarrera + ";";
        PreparedStatement ps = CON.getPSPOOL(sql);
        List<PeriodoLectivoMD> lista = new ArrayList<>();
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PeriodoLectivoMD p = new PeriodoLectivoMD();
                p.setID(rs.getInt("id_prd_lectivo"));
                carrera = new CarreraMD();
                carrera.setId(rs.getInt("id_carrera"));
                carrera.setNombre(rs.getString("carrera_nombre"));
                p.setCarrera(carrera);
                p.setNombre(rs.getString("prd_lectivo_nombre"));
                p.setFechaInicio(rs.getDate("prd_lectivo_fecha_inicio").toLocalDate());
                p.setFechaFin(rs.getDate("prd_lectivo_fecha_fin").toLocalDate());
                p.setEstado(rs.getBoolean("prd_lectivo_estado"));

                DocenteMD d = new DocenteMD();
                d.setPrimerNombre(rs.getString("persona_primer_nombre"));
                d.setPrimerApellido(rs.getString("persona_primer_apellido"));
                p.setDocente(d);

                lista.add(p);
            }
        } catch (SQLException ex) {
            M.errorMsg("No pudimos consultar el periodo: " + ex.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return lista;
    }

    public List<PeriodoLectivoMD> periodoDocente(int aguja) {
        String sql = "SELECT "
                + "DISTINCT "
                + "p.prd_lectivo_nombre, "
                + "p.id_prd_lectivo "
                + "FROM ( public.\"PeriodoLectivo\" p JOIN public.\"Cursos\" c USING(id_prd_lectivo) ) "
                + "JOIN public.\"Docentes\" d USING(id_docente) "
                + "WHERE d.id_docente = " + aguja + " "
                + "AND p.prd_lectivo_activo = true;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        List<PeriodoLectivoMD> lista = new ArrayList<>();
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PeriodoLectivoMD p = new PeriodoLectivoMD();
                p.setNombre(rs.getString("prd_lectivo_nombre"));
                lista.add(p);
            }
        } catch (SQLException ex) {
            M.errorMsg("No pudimos consultar el periodo: " + ex.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return lista;
    }

    public List<CarreraMD> capturarCarrera() {
        List<CarreraMD> lista = new ArrayList();
        String sql = "SELECT id_carrera, carrera_nombre, carrera_codigo FROM public.\"Carreras\" "
                + "WHERE carrera_activo = true;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CarreraMD a = new CarreraMD();
                a.setId(rs.getInt("id_carrera"));
                a.setNombre(rs.getString("carrera_nombre"));
                a.setCodigo(rs.getString("carrera_codigo"));
                lista.add(a);
            }
        } catch (SQLException ex) {
            M.errorMsg("No pudimos consultar el carrera: " + ex.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return lista;

    }

    public CarreraMD capturarIdCarrera(String aguja) {
        String sql = "SELECT id_carrera "
                + "FROM public.\"Carreras\" "
                + "WHERE carrera_nombre LIKE '%" + aguja + "%';";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            CarreraMD c = new CarreraMD();
            while (rs.next()) {
                c.setId(rs.getInt("id_carrera"));
            }
            return c;
        } catch (SQLException ex) {
            M.errorMsg("No pudimos consultar el id de la carrera: " + ex.getMessage());
            return null;
        } finally {
            CON.cerrarCONPS(ps);
        }
    }

    public CarreraMD capturarNomCarrera(int aguja) {
        String sql = "SELECT carrera_nombre"
                + " FROM public.\"Carreras\" WHERE id_carrera = " + aguja + ";";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            CarreraMD c = new CarreraMD();
            while (rs.next()) {
                c.setNombre(rs.getString("carrera_nombre"));
            }
            return c;
        } catch (SQLException ex) {
            M.errorMsg("No pudimos consultar el nombre de la carrera: " + ex.getMessage());
            return null;
        } finally {
            CON.cerrarCONPS(ps);
        }
    }

    public List<PeriodoLectivoMD> llenarTabla() {
        List<PeriodoLectivoMD> lista = new ArrayList();
        String sql = "SELECT id_prd_lectivo, "
                + "id_carrera, "
                + "prd_lectivo_nombre, "
                + "prd_lectivo_fecha_inicio, "
                + "prd_lectivo_fecha_fin "
                + "FROM public.\"PeriodoLectivo\" "
                + "WHERE prd_lectivo_activo = true";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PeriodoLectivoMD m = new PeriodoLectivoMD();
                carrera = new CarreraMD();
                m.setID(rs.getInt("id_prd_lectivo"));
                carrera.setId(rs.getInt("id_carrera"));
                m.setNombre(rs.getString("prd_lectivo_nombre"));
                m.setFechaInicio(rs.getDate("prd_lectivo_fecha_inicio").toLocalDate());
                m.setFechaFin(rs.getDate("prd_lectivo_fecha_fin").toLocalDate());
                m.setCarrera(carrera);
                lista.add(m);
            }
        } catch (SQLException ex) {
            M.errorMsg("No pudimos consultar para llenar la tabla: " + ex.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return lista;
    }

    public List<PeriodoLectivoMD> capturarPeriodos(String aguja) {
        List<PeriodoLectivoMD> lista = new ArrayList();
        String sql = "SELECT "
                + "id_prd_lectivo, "
                + "pl.id_carrera, "
                + "prd_lectivo_nombre, "
                + "prd_lectivo_fecha_inicio, \n"
                + "prd_lectivo_fecha_fin, "
                + "carrera_nombre, "
                + "carrera_codigo, "
                + "prd_lectivo_estado, "
                + "p.persona_primer_nombre, "
                + "p.persona_primer_apellido "
                + "FROM public.\"PeriodoLectivo\" pl, "
                + "public.\"Carreras\" c, "
                + "public.\"Docentes\" d, "
                + "public.\"Personas\" p "
                + "WHERE c.id_carrera = pl.id_carrera "
                + "AND d.id_docente = pl.prd_lectivo_coordinador "
                + "AND p.id_persona = d.id_persona "
                + "AND prd_lectivo_activo = true AND( "
                + "	prd_lectivo_nombre ILIKE '%" + aguja + "%' OR\n"
                + "	carrera_nombre ILIKE '%" + aguja + "%' OR\n"
                + "	carrera_codigo ILIKE '%" + aguja + "%')\n"
                + "ORDER BY prd_lectivo_fecha_inicio DESC;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PeriodoLectivoMD p = new PeriodoLectivoMD();
                p.setID(rs.getInt("id_prd_lectivo"));
                carrera = new CarreraMD();
                carrera.setId(rs.getInt("id_carrera"));
                carrera.setCodigo(rs.getString("carrera_codigo"));
                carrera.setNombre(rs.getString("carrera_nombre"));
                p.setCarrera(carrera);

                p.setNombre(rs.getString("prd_lectivo_nombre"));
                p.setFechaInicio(rs.getDate("prd_lectivo_fecha_inicio").toLocalDate());
                p.setFechaFin(rs.getDate("prd_lectivo_fecha_fin").toLocalDate());
                p.setEstado(rs.getBoolean("prd_lectivo_estado"));

                DocenteMD d = new DocenteMD();
                d.setPrimerNombre(rs.getString("persona_primer_nombre"));
                d.setPrimerApellido(rs.getString("persona_primer_apellido"));
                p.setDocente(d);

                lista.add(p);
            }
        } catch (SQLException ex) {
            M.errorMsg("No pudimos consultar para llenar la tabla: " + ex.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return lista;
    }

    public PeriodoLectivoMD capturarPerLectivo(int ID) {
        String sql = "SELECT "
                + "p.id_prd_lectivo, "
                + "p.id_carrera, "
                + "p.prd_lectivo_nombre, "
                + "p.prd_lectivo_fecha_inicio, "
                + "p.prd_lectivo_fecha_fin, "
                + "p.prd_lectivo_observacion, "
                + "c.carrera_nombre, "
                + "p.prd_lectivo_estado,"
                + "p.prd_lectivo_fecha_fin_clases, "
                + "d.id_docente, "
                + "pr.persona_identificacion "
                + "FROM public.\"PeriodoLectivo\" p "
                + "JOIN public.\"Carreras\" c USING(id_carrera) "
                + "JOIN public.\"Docentes\" d ON p.prd_lectivo_coordinador = d.id_docente "
                + "JOIN public.\"Personas\" pr ON pr.id_persona = d.id_persona "
                + "WHERE p.id_prd_lectivo = "
                + ID + " AND prd_lectivo_activo = true;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            PeriodoLectivoMD m = new PeriodoLectivoMD();
            carrera = new CarreraMD();
            while (rs.next()) {
                m.setID(rs.getInt("id_prd_lectivo"));
                m.setNombre(rs.getString("prd_lectivo_nombre"));
                m.setFechaInicio(rs.getDate("prd_lectivo_fecha_inicio").toLocalDate());
                m.setFechaFin(rs.getDate("prd_lectivo_fecha_fin").toLocalDate());
                m.setObservacion(rs.getString("prd_lectivo_observacion"));
                carrera.setId(rs.getInt("id_carrera"));
                carrera.setNombre(rs.getString("carrera_nombre"));
                m.setEstado(rs.getBoolean("prd_lectivo_estado"));
                m.setCarrera(carrera);
                if (rs.getDate("prd_lectivo_fecha_fin_clases") != null) {
                    m.setFechaFinClases(rs.getDate("prd_lectivo_fecha_fin_clases").toLocalDate());
                }
                DocenteMD d = new DocenteMD();
                d.setIdDocente(rs.getInt("id_docente"));
                d.setIdentificacion(rs.getString("persona_identificacion"));

                m.setDocente(d);
            }
            return m;
        } catch (SQLException ex) {
            M.errorMsg("No pudimos consultar para llenar la tabla: " + ex.getMessage());
            return null;
        } finally {
            CON.cerrarCONPS(ps);
        }
    }

    public ArrayList<PeriodoLectivoMD> cargarPeriodos() {
        ArrayList<PeriodoLectivoMD> lista = new ArrayList();
        String sql = "SELECT id_prd_lectivo, "
                + "pl.id_carrera, "
                + "prd_lectivo_nombre, "
                + "prd_lectivo_fecha_inicio, "
                + "prd_lectivo_fecha_fin, "
                + "carrera_nombre, "
                + "carrera_codigo, "
                + "prd_lectivo_estado,"
                + "p.persona_primer_nombre, "
                + "p.persona_primer_apellido "
                + "FROM public.\"PeriodoLectivo\" pl, "
                + "public.\"Carreras\" c, "
                + "public.\"Docentes\" d, "
                + "public.\"Personas\" p "
                + "WHERE c.id_carrera = pl.id_carrera "
                + "AND d.id_docente = pl.prd_lectivo_coordinador "
                + "AND p.id_persona = d.id_persona "
                + "AND prd_lectivo_activo = true "
                + "AND carrera_activo = true "
                + "ORDER BY prd_lectivo_fecha_inicio DESC;";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PeriodoLectivoMD p = new PeriodoLectivoMD();
                p.setID(rs.getInt("id_prd_lectivo"));
                carrera = new CarreraMD();
                carrera.setId(rs.getInt("id_carrera"));
                carrera.setCodigo(rs.getString("carrera_codigo"));
                carrera.setNombre(rs.getString("carrera_nombre"));
                p.setCarrera(carrera);
                p.setNombre(rs.getString("prd_lectivo_nombre"));
                p.setFechaInicio(rs.getDate("prd_lectivo_fecha_inicio").toLocalDate());
                p.setFechaFin(rs.getDate("prd_lectivo_fecha_fin").toLocalDate());
                p.setEstado(rs.getBoolean("prd_lectivo_estado"));

                DocenteMD d = new DocenteMD();
                d.setPrimerNombre(rs.getString("persona_primer_nombre"));
                d.setPrimerApellido(rs.getString("persona_primer_apellido"));
                p.setDocente(d);

                lista.add(p);
            }
        } catch (SQLException ex) {
            M.errorMsg("No pudimos consultar para llenar la tabla: " + ex.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return lista;
    }

    public PeriodoLectivoMD buscarPerido(int idPeriodo) {
        PeriodoLectivoMD p = new PeriodoLectivoMD();
        String sql = "SELECT id_prd_lectivo, id_carrera, prd_lectivo_nombre,"
                + " prd_lectivo_fecha_inicio, prd_lectivo_fecha_fin "
                + " FROM public.\"PeriodoLectivo\" WHERE prd_lectivo_activo = true AND "
                + "id_prd_lectivo = " + idPeriodo + ";";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                p.setID(rs.getInt("id_prd_lectivo"));
                //Buscamos la carrera para guardarla en la clase
                carrera = car.buscar(rs.getInt("id_carrera"));
                p.setCarrera(carrera);

                p.setNombre(rs.getString("prd_lectivo_nombre"));
                p.setFechaInicio(rs.getDate("prd_lectivo_fecha_inicio").toLocalDate());
                p.setFechaFin(rs.getDate("prd_lectivo_fecha_fin").toLocalDate());
            }
            return p;
        } catch (SQLException ex) {
            M.errorMsg("No pudimos consultar para llenar la tabla: " + ex.getMessage());
            return null;
        } finally {
            CON.cerrarCONPS(ps);
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

    public List<PeriodoLectivoMD> cargarPeriodoEspecial() {
        String sql = "SELECT "
                + "id_prd_lectivo, "
                + "pl.id_carrera, "
                + "prd_lectivo_nombre "
                + "FROM public.\"PeriodoLectivo\" pl "
                + "JOIN public.\"Carreras\" c "
                + "ON c.id_carrera = pl.id_carrera "
                + "WHERE "
                + "prd_lectivo_activo = true "
                + "AND prd_lectivo_estado = true "
                + "AND carrera_modalidad = 'ESPECIAL' "
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
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PeriodoLectivoMD p = new PeriodoLectivoMD();
                p.setID(rs.getInt("id_prd_lectivo"));
                carrera = new CarreraMD();
                carrera.setId(rs.getInt("id_carrera"));
                p.setCarrera(carrera);
                p.setNombre(rs.getString("prd_lectivo_nombre"));

                prds.add(p);
            }
        } catch (SQLException ex) {
            M.errorMsg("No pudimos consultar para combo: " + ex.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return prds;
    }

    public PeriodoLectivoMD capturarIdPeriodo(String nombrePer) {
        PeriodoLectivoMD p = new PeriodoLectivoMD();
        String sql = "SELECT id_prd_lectivo FROM public.\"PeriodoLectivo\" WHERE prd_lectivo_activo = true AND "
                + "prd_lectivo_nombre LIKE '" + nombrePer + "';";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                p.setID(rs.getInt("id_prd_lectivo"));
            }
            return p;
        } catch (SQLException ex) {
            M.errorMsg("No pudimos consultar para combo: " + ex.getMessage());
            return null;
        } finally {
            CON.cerrarCONPS(ps);
        }
    }

    public PeriodoLectivoMD buscarPeriodo(String nombrePer) {
        PeriodoLectivoMD p = new PeriodoLectivoMD();
        String sql = "SELECT id_prd_lectivo, "
                + "id_carrera, "
                + "prd_lectivo_nombre, "
                + "prd_lectivo_fecha_inicio, "
                + "prd_lectivo_fecha_fin "
                + "FROM public.\"PeriodoLectivo\" "
                + "WHERE prd_lectivo_activo = true AND "
                + "prd_lectivo_nombre LIKE '" + nombrePer + "';";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                p.setID(rs.getInt("id_prd_lectivo"));
                carrera = car.buscar(rs.getInt("id_carrera"));
                p.setCarrera(carrera);
                p.setNombre(rs.getString("prd_lectivo_nombre"));
                p.setFechaInicio(rs.getDate("prd_lectivo_fecha_inicio").toLocalDate());
                p.setFechaFin(rs.getDate("prd_lectivo_fecha_fin").toLocalDate());
            }
            return p;
        } catch (SQLException ex) {
            M.errorMsg("No pudimos consultar para combo: " + ex.getMessage());
            return null;
        } finally {
            CON.cerrarCONPS(ps);
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
        String sql = "SELECT COUNT(*) AS numeroAlumnos FROM ( "
                + " public.\"Carreras\" c "
                + " JOIN public.\"AlumnosCarrera\" a "
                + " USING(id_carrera)"
                + ") JOIN public.\"MallaAlumno\" m USING(id_almn_carrera) "
                + "WHERE c.id_carrera = " + ID + " AND m.malla_almn_estado LIKE 'M';";
        PreparedStatement ps = CON.getPSPOOL(sql);
        String count = "";
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = String.valueOf(rs.getInt("numeroAlumnos"));
            }
        } catch (SQLException ex) {
            M.errorMsg("No pudimos consultar numero de alumnos: " + ex.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return count;
    }

    public LocalDate buscarFechaInicioPrd(int idPrd) {
        LocalDate fi = null;
        String sql = "SELECT prd_lectivo_fecha_inicio \n"
                + "FROM public.\"PeriodoLectivo\"\n"
                + "WHERE id_prd_lectivo = " + idPrd + ";";
        conn = pool.getConnection();
        rst = pool.ejecutarQuery(sql, conn, null);
        try {
            while (rst.next()) {
                fi = rst.getDate("prd_lectivo_fecha_inicio").toLocalDate();
            }
            return fi;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            pool.close(conn);
        }
        return fi;
    }

    public List<PeriodoLectivoMD> selectPeriodoWhere(int idDocente) {
        String SELECT = "SELECT DISTINCT\n"
                + "\"PeriodoLectivo\".id_prd_lectivo,\n"
                + "\"PeriodoLectivo\".prd_lectivo_nombre,\n"
                + "\"PeriodoLectivo\".id_carrera,\n"
                + "\"Carreras\".carrera_nombre,\n"
                + "\"Carreras\".carrera_modalidad,\n"
                + "\"PeriodoLectivo\".prd_lectivo_estado,\n"
                + "\"PeriodoLectivo\".prd_lectivo_activo,\n"
                + "\"PeriodoLectivo\".prd_lectivo_fecha_inicio,\n"
                + "\"PeriodoLectivo\".prd_lectivo_fecha_fin\n"
                + "FROM\n"
                + "\"PeriodoLectivo\"\n"
                + "INNER JOIN \"Carreras\" ON \"PeriodoLectivo\".id_carrera = \"Carreras\".id_carrera\n"
                + "INNER JOIN \"Cursos\" ON \"Cursos\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo\n"
                + "INNER JOIN \"Docentes\" ON \"Cursos\".id_docente = \"Docentes\".id_docente\n"
                + "WHERE\n"
                + " \"Docentes\".id_docente = ? \n"
                + " AND prd_lectivo_activo = true \n"
                + " AND prd_lectivo_estado = true \n"
                + "ORDER BY \"PeriodoLectivo\".prd_lectivo_fecha_inicio DESC";

        List<PeriodoLectivoMD> lista = new ArrayList<>();
        Map<Integer, Object> parametros = new HashMap<>();
        parametros.put(1, idDocente);
        conn = pool.getConnection();
        rst = pool.ejecutarQuery(SELECT, conn, parametros);

        try {
            while (rst.next()) {

                PeriodoLectivoMD periodo = new PeriodoLectivoMD();
                periodo.setID(rst.getInt("id_prd_lectivo"));
                periodo.setNombre(rst.getString("prd_lectivo_nombre"));

                CarreraMD c = new CarreraMD();
                c.setId(rst.getInt("id_carrera"));
                c.setNombre(rst.getString("carrera_nombre"));
                c.setModalidad(rst.getString("carrera_modalidad"));
                periodo.setCarrera(c);

                periodo.setEstado(rst.getBoolean("prd_lectivo_estado"));
                periodo.setActivo(rst.getBoolean("prd_lectivo_activo"));
                periodo.setFechaInicio(rst.getDate("prd_lectivo_fecha_inicio").toLocalDate());
                periodo.setFechaFin(rst.getDate("prd_lectivo_fecha_fin").toLocalDate());

                lista.add(periodo);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            pool.closeStmt().close(rst).close(conn);
        }
        return lista;
    }

    public List<PeriodoLectivoMD> selectPeriodoWhere(String cedulaDocente) {
        String SELECT = "SELECT DISTINCT\n"
                + "\"PeriodoLectivo\".id_prd_lectivo,\n"
                + "\"PeriodoLectivo\".prd_lectivo_nombre,\n"
                + "\"PeriodoLectivo\".id_carrera,\n"
                + "\"Carreras\".carrera_nombre,\n"
                + "\"Carreras\".carrera_modalidad,\n"
                + "\"PeriodoLectivo\".prd_lectivo_estado,\n"
                + "\"PeriodoLectivo\".prd_lectivo_activo,\n"
                + "\"PeriodoLectivo\".prd_lectivo_fecha_inicio,\n"
                + "\"PeriodoLectivo\".prd_lectivo_fecha_fin\n"
                + "FROM\n"
                + "\"PeriodoLectivo\"\n"
                + "INNER JOIN \"Carreras\" ON \"PeriodoLectivo\".id_carrera = \"Carreras\".id_carrera\n"
                + "INNER JOIN \"Cursos\" ON \"Cursos\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo\n"
                + "INNER JOIN \"Docentes\" ON \"Cursos\".id_docente = \"Docentes\".id_docente\n"
                + "WHERE\n"
                + " \"Docentes\".docente_codigo = ? "
                + " AND prd_lectivo_activo = true "
                + " AND prd_lectivo_estado = true \n"
                + "ORDER BY prd_lectivo_fecha_inicio DESC";

        List<PeriodoLectivoMD> lista = new ArrayList<>();
        Map<Integer, Object> parametros = new HashMap<>();
        parametros.put(1, cedulaDocente);
        conn = pool.getConnection();
        rst = pool.ejecutarQuery(SELECT, conn, parametros);

        try {
            while (rst.next()) {

                PeriodoLectivoMD periodo = new PeriodoLectivoMD();
                periodo.setID(rst.getInt("id_prd_lectivo"));
                periodo.setNombre(rst.getString("prd_lectivo_nombre"));

                CarreraMD c = new CarreraMD();
                c.setId(rst.getInt("id_carrera"));
                c.setNombre(rst.getString("carrera_nombre"));
                c.setModalidad(rst.getString("carrera_modalidad"));
                periodo.setCarrera(c);

                periodo.setEstado(rst.getBoolean("prd_lectivo_estado"));
                periodo.setActivo(rst.getBoolean("prd_lectivo_activo"));
                periodo.setFechaInicio(rst.getDate("prd_lectivo_fecha_inicio").toLocalDate());
                periodo.setFechaFin(rst.getDate("prd_lectivo_fecha_fin").toLocalDate());

                lista.add(periodo);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            pool.closeStmt().close(rst).close(conn);
        }
        return lista;
    }

    public List<PeriodoLectivoMD> selectIdNombreAll() {

        String SELECT = "SELECT id_prd_lectivo, prd_lectivo_nombre "
                + "FROM \"PeriodoLectivo\" \n"
                + "ORDER BY prd_lectivo_fecha_inicio DESC";

        List<PeriodoLectivoMD> lista = new ArrayList<>();
        conn = pool.getConnection();
        rst = pool.ejecutarQuery(SELECT, conn, null);

        try {
            while (rst.next()) {
                PeriodoLectivoMD periodo = new PeriodoLectivoMD();
                periodo.setID(rst.getInt("id_prd_lectivo"));
                periodo.setNombre(rst.getString("prd_lectivo_nombre"));
                lista.add(periodo);
            }
        } catch (SQLException | NullPointerException e) {
            if (e instanceof SQLException) {
                System.out.println(e.getMessage());
            }
        } finally {
            pool.closeStmt().close(rst).close(conn);
        }
        return lista;
    }

    public Map<String, PeriodoLectivoMD> selectPeriodosFaltantes() {
        String SELECT = "SELECT DISTINCT\n"
                + "	p1.id_prd_lectivo,\n"
                + "	p1.id_carrera,\n"
                + "	p1.prd_lectivo_nombre,\n"
                + "	\"Carreras\".carrera_nombre,\n"
                + "	\"Carreras\".carrera_modalidad \n"
                + "FROM\n"
                + "\"PeriodoLectivo\" p1\n"
                + "	INNER JOIN \"Carreras\" ON p1.id_carrera = \"Carreras\".id_carrera \n"
                + "WHERE\n"
                + "	7 != (\n"
                + "	SELECT\n"
                + "		\"count\" ( * ) \n"
                + "	FROM\n"
                + "		\"TipoDeNota\" AS t2\n"
                + "		INNER JOIN \"PeriodoLectivo\" ON t2.id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo\n"
                + "		INNER JOIN \"Carreras\" ON \"PeriodoLectivo\".id_carrera = \"Carreras\".id_carrera \n"
                + "	WHERE\n"
                + "		t2.id_prd_lectivo = p1.id_prd_lectivo \n"
                + "		AND ( \"Carreras\".carrera_modalidad ='PRESENCIAL' OR \"Carreras\".carrera_modalidad ='TRADICIONAL' ) \n"
                + "	) \n"
                + "	AND 12 != (\n"
                + "	SELECT\n"
                + "		\"count\" ( * ) \n"
                + "	FROM\n"
                + "		\"TipoDeNota\" AS t2\n"
                + "		INNER JOIN \"PeriodoLectivo\" ON t2.id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo\n"
                + "		INNER JOIN \"Carreras\" ON \"PeriodoLectivo\".id_carrera = \"Carreras\".id_carrera \n"
                + "	WHERE\n"
                + "		t2.id_prd_lectivo = p1.id_prd_lectivo \n"
                + "	AND ( \"Carreras\".carrera_modalidad ='DUAL' OR \"Carreras\".carrera_modalidad ='DUAL FOCALIZADA' ) \n"
                + "	)\n"
                + "	\n"
                + "ORDER BY p1.prd_lectivo_fecha_inicio DESC";

        System.out.println(SELECT);

        Map<String, PeriodoLectivoMD> map = new HashMap<>();

        //System.out.println(SELECT);
        conn = pool.getConnection();
        rst = pool.ejecutarQuery(SELECT, conn, null);

        try {
            while (rst.next()) {

                PeriodoLectivoMD periodo = new PeriodoLectivoMD();
                periodo.setID(rst.getInt("id_prd_lectivo"));
                periodo.setNombre(rst.getString("prd_lectivo_nombre"));

                CarreraMD c = new CarreraMD();
                c.setId(rst.getInt("id_carrera"));
                c.setNombre(rst.getString("carrera_nombre"));
                c.setModalidad(rst.getString("carrera_modalidad"));
                periodo.setCarrera(c);

                String key = rst.getString("prd_lectivo_nombre");

                map.put(key, periodo);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            pool.closeStmt().close(rst).close(conn);
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
                + "	INNER JOIN \"Carreras\" ON \"PeriodoLectivo\".id_carrera = \"Carreras\".id_carrera \n"
                + "WHERE\n"
                + "	\"PeriodoLectivo\".prd_lectivo_nombre = ?";
        Map<String, PeriodoLectivoMD> map = new HashMap<>();

        Map<Integer, Object> parametros = new HashMap<>();
        parametros.put(1, nombrePeriodo);

        conn = pool.getConnection();
        rst = pool.ejecutarQuery(SELECT, conn, parametros);

        try {
            while (rst.next()) {

                PeriodoLectivoMD periodo = new PeriodoLectivoMD();
                periodo.setID(rst.getInt("id_prd_lectivo"));
                periodo.setNombre(rst.getString("prd_lectivo_nombre"));

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
            pool.closeStmt().close(rst).close(conn);
        }

        return map;
    }

    public List<PeriodoLectivoMD> buscarNumSemanas(int idDocente, int idPrd) {
        String SELECT = "SELECT DISTINCT\n"
                + " \"Docentes\".id_docente,\n"
                + " \"PeriodoLectivo\".prd_lectivo_nombre,\n"
                + " (prd_lectivo_fecha_fin - prd_lectivo_fecha_inicio)/7 AS semanas\n"
                + " FROM\n"
                + " \"Carreras\"\n"
                + " INNER JOIN \"PeriodoLectivo\" ON \"PeriodoLectivo\".id_carrera = \"Carreras\".id_carrera\n"
                + " INNER JOIN \"Cursos\" ON \"Cursos\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo\n"
                + " INNER JOIN \"Docentes\" ON \"Docentes\".id_docente = \"Cursos\".id_docente\n"
                + " INNER JOIN \"Materias\" ON \"Materias\".id_carrera = \"Carreras\".id_carrera\n"
                + " INNER JOIN \"SesionClase\" ON \"SesionClase\".id_curso = \"Cursos\".id_curso\n"
                + " WHERE\n"
                + " \"Cursos\".id_docente = " + idDocente + " AND\n"
                + " \"PeriodoLectivo\".id_prd_lectivo = " + idPrd + "";
        List<PeriodoLectivoMD> semana = new ArrayList<>();
        conn = pool.getConnection();
        rst = pool.ejecutarQuery(SELECT, conn, null);

        System.out.println("Query: \n" + SELECT);

        try {
            while (rst.next()) {
                PeriodoLectivoMD periodo = new PeriodoLectivoMD();

                periodo.setNombre(rst.getString("prd_lectivo_nombre"));
                System.out.println("Semanas " + rst.getInt(3));
                periodo.setNumSemanas(rst.getInt(3));
                semana.add(periodo);
            }
        } catch (SQLException | NullPointerException e) {
            if (e instanceof SQLException) {
                System.out.println(e.getMessage());
            }
        } finally {
            pool.closeStmt();
            pool.close(rst);
            pool.close(conn);
        }
        return semana;

    }

}
