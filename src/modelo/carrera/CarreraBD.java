package modelo.carrera;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import utils.CONS;
import modelo.ConnDBPool;
import modelo.persona.DocenteMD;
import utils.CONBD;
import utils.M;

/**
 *
 * @author arman
 */
public class CarreraBD extends CONBD {

    private final ConnDBPool pool;
    private ResultSet rst;
    private Connection conn;

    private static CarreraBD CBD;

    public static CarreraBD single() {
        if (CBD == null) {
            CBD = new CarreraBD();
        }
        return CBD;
    }

    {
        pool = new ConnDBPool();
    }

    public boolean guardarCarrera(CarreraMD cr) {
        String nsql = "INSERT INTO public.\"Carreras\"(\n"
                + "id_docente_coordinador, "
                + "carrera_nombre, "
                + "carrera_codigo, "
                + "carrera_fecha_inicio, "
                + "carrera_modalidad, "
                + "carrera_semanas)\n"
                + "VALUES ("
                + cr.getCoordinador().getIdDocente() + ", "
                + " '" + cr.getNombre() + "', "
                + "'" + cr.getCodigo() + "', "
                + "'" + cr.getFechaInicio() + "',"
                + "'" + cr.getModalidad() + "', "
                + "" + cr.getNumSemanas() + ");";
        if (CON.executeNoSQL(nsql)) {
            JOptionPane.showMessageDialog(null, "Guardamos correctamente \n" + cr.getNombre());
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo guardar \n" + cr.getNombre() + "\n"
                    + "Revise su conexion a internet.");
            return false;
        }
    }

    public boolean editarCarrera(CarreraMD cr) {
        String nsql = "UPDATE public.\"Carreras\" "
                + "SET id_docente_coordinador=" + cr.getCoordinador().getIdDocente() + ", "
                + "carrera_nombre='" + cr.getNombre() + "', "
                + "carrera_codigo='" + cr.getCodigo() + "', "
                + "carrera_fecha_inicio='" + cr.getFechaInicio() + "', "
                + "carrera_modalidad='" + cr.getModalidad() + "', "
                + "carrera_semanas = " + cr.getNumSemanas() + " "
                + "WHERE id_carrera=" + cr.getId() + ";";
        if (CON.executeNoSQL(nsql)) {
            JOptionPane.showMessageDialog(null, "Editamos correctamente \n" + cr.getNombre());
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo editar \n" + cr.getNombre() + "\n"
                    + "Revise su conexion a internet.");
            return false;
        }
    }

    public void eliminarCarrera(int idCarrera) {
        String nsql = "UPDATE public.\"Carreras\"\n"
                + "SET  carrera_activo='false'\n"
                + "WHERE id_carrera=" + idCarrera + ";";
        if (CON.executeNoSQL(nsql)) {
            JOptionPane.showMessageDialog(null, "Eliminamos correctamente ");
        }
    }

    public CarreraMD buscar(int idCarrera) {
        CarreraMD carrera = new CarreraMD();
        String sql = "SELECT id_carrera, "
                + "id_docente_coordinador, "
                + "carrera_nombre, "
                + "carrera_codigo, "
                + "carrera_fecha_inicio, "
                + "carrera_fecha_fin, "
                + "carrera_modalidad, (\n"
                + "	SELECT persona_primer_nombre || '%' || \n"
                + "	persona_segundo_nombre || '%' ||\n"
                + "	persona_primer_apellido || '%' ||\n"
                + "	persona_segundo_apellido || '%' || "
                + "     persona_identificacion \n"
                + "    FROM public.\"Docentes\" d, public.\"Personas\" p \n"
                + "    WHERE d.id_docente = id_docente_coordinador AND\n"
                + "    p.id_persona = d.id_persona) AS coordinador\n"
                + "FROM public.\"Carreras\" "
                + "WHERE id_carrera = '" + idCarrera + "';";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                carrera.setId(rs.getInt("id_carrera"));
                DocenteMD docen = null;
                if (rs.getInt(2) != 0) {
                    String nombreC = rs.getString(8);
                    docen = new DocenteMD();
                    docen.setIdDocente(rs.getInt(2));
                    if (nombreC != null) {
                        String nombres[] = nombreC.split("%");
                        docen.setPrimerNombre(nombres[0]);
                        docen.setSegundoNombre(nombres[1]);
                        docen.setPrimerApellido(nombres[2]);
                        docen.setSegundoApellido(nombres[3]);
                        docen.setIdentificacion(nombres[4]);
                    }
                }
                carrera.setCoordinador(docen);
                carrera.setNombre(rs.getString("carrera_nombre"));

                carrera.setCodigo(rs.getString("carrera_codigo"));
                carrera.setFechaInicio(rs.getDate("carrera_fecha_inicio").toLocalDate());

                if (rs.getDate("carrera_fecha_fin") != null) {
                    carrera.setFechaFin(rs.getDate("carrera_fecha_fin").toLocalDate());
                } else {
                    carrera.setFechaFin(null);
                }
                carrera.setModalidad(rs.getString("carrera_modalidad"));
            }
        } catch (SQLException e) {
            M.errorMsg("No se pudo consultar carreras. " + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return carrera;
    }

    /**
     * Buscamos la carrera por id
     *
     * @param idCarrera
     * @return
     */
    public CarreraMD buscarPorId(int idCarrera) {
        CarreraMD carrera = null;
        String sql = "SELECT id_carrera, carrera_nombre,"
                + " carrera_codigo, carrera_fecha_inicio,"
                + " carrera_modalidad \n"
                + "FROM public.\"Carreras\" WHERE id_carrera = '" + idCarrera + "';";
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                carrera = new CarreraMD();
                carrera.setId(rs.getInt("id_carrera"));
                carrera.setNombre(rs.getString("carrera_nombre"));
                carrera.setCodigo(rs.getString("carrera_codigo"));
                carrera.setFechaInicio(rs.getDate("carrera_fecha_inicio").toLocalDate());
                carrera.setModalidad(rs.getString("carrera_modalidad"));
            }
        } catch (SQLException e) {
            M.errorMsg("No se pudo consultar carreras. " + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return carrera;
    }

    /**
     * Consultamos todas la carreras activas.
     *
     * @return
     */
    public ArrayList<CarreraMD> cargarCarreras() {
        String sql = "SELECT id_carrera, id_docente_coordinador, carrera_nombre,\n"
                + "carrera_codigo, carrera_fecha_inicio,\n"
                + "carrera_modalidad, carrera_semanas, (\n"
                + "	SELECT persona_primer_nombre || '%' || \n"
                + "	persona_segundo_nombre || '%' ||\n"
                + "	persona_primer_apellido || '%' ||\n"
                + "	persona_segundo_apellido || '%' || "
                + "     persona_identificacion \n"
                + "    FROM public.\"Docentes\" d, public.\"Personas\" p \n"
                + "    WHERE d.id_docente = id_docente_coordinador AND\n"
                + "    p.id_persona = d.id_persona) AS coordinador\n"
                + "FROM public.\"Carreras\" c\n"
                + "WHERE carrera_activo = TRUE\n"
                + "ORDER BY carrera_fecha_inicio;";
        return consultarCarrerasTbl(sql);
    }

    /**
     * Buscamos las carreras, por nombre o codigo.
     *
     * @param aguja
     * @return
     */
    public ArrayList<CarreraMD> buscarCarrera(String aguja) {
        String sql = "SELECT id_carrera, id_docente_coordinador, carrera_nombre,\n"
                + "carrera_codigo, carrera_fecha_inicio,\n"
                + "carrera_modalidad, carrera_semanas, (\n"
                + "	SELECT persona_primer_nombre || '%' || \n"
                + "	persona_segundo_nombre || '%' ||\n"
                + "	persona_primer_apellido || '%' ||\n"
                + "	persona_segundo_apellido || '%' || "
                + "     persona_identificacion \n"
                + "    FROM public.\"Docentes\" d, public.\"Personas\" p \n"
                + "    WHERE d.id_docente = id_docente_coordinador AND\n"
                + "    p.id_persona = d.id_persona) AS coordinador\n"
                + "FROM public.\"Carreras\" c\n"
                + "WHERE carrera_activo = TRUE AND (\n"
                + "	carrera_codigo ILIKE '%" + aguja + "%' OR\n"
                + "	carrera_nombre ILIKE '%" + aguja + "%'\n"
                + ")ORDER BY carrera_fecha_inicio;";
        return consultarCarrerasTbl(sql);
    }

    /**
     * Consulatamos carrera para tabla
     *
     * @param sql
     * @return
     */
    private ArrayList<CarreraMD> consultarCarrerasTbl(String sql) {
        ArrayList<CarreraMD> carreras = new ArrayList();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CarreraMD carrera = new CarreraMD();

                carrera.setId(rs.getInt("id_carrera"));
                DocenteMD docen = new DocenteMD();
                String nombreC = rs.getString(8);
                if (nombreC != null) {
                    String nombres[] = nombreC.split("%");
                    docen.setPrimerNombre(nombres[0]);
                    docen.setSegundoNombre(nombres[1]);
                    docen.setPrimerApellido(nombres[2]);
                    docen.setSegundoApellido(nombres[3]);
                    docen.setIdentificacion(nombres[4]);
                }
                carrera.setCoordinador(docen);
                carrera.setNombre(rs.getString("carrera_nombre"));
                carrera.setCodigo(rs.getString("carrera_codigo"));
                carrera.setFechaInicio(rs.getDate("carrera_fecha_inicio").toLocalDate());
                carrera.setModalidad(rs.getString("carrera_modalidad"));
                carrera.setNumSemanas(rs.getInt("carrera_semanas"));

                carreras.add(carrera);
            }
        } catch (SQLException e) {
            M.errorMsg("No se pudo consultar carreras. " + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return carreras;
    }

    /**
     * Consultamos los nombres de las carreras activas para un combo.
     *
     * @return
     */
    public ArrayList<CarreraMD> cargarCarrerasCmb() {
        ArrayList<CarreraMD> carreras = new ArrayList();
        String sql = "SELECT id_carrera, carrera_nombre,\n"
                + "carrera_codigo \n"
                + "FROM public.\"Carreras\" \n"
                + "WHERE carrera_activo = TRUE\n"
                + "ORDER BY carrera_fecha_inicio DESC;";
        PreparedStatement ps = CON.getPSPOOL(sql);

        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CarreraMD carrera = new CarreraMD();
                carrera.setId(rs.getInt("id_carrera"));
                carrera.setNombre(rs.getString("carrera_nombre"));
                carrera.setCodigo(rs.getString("carrera_codigo"));

                carreras.add(carrera);
            }
        } catch (SQLException e) {
            M.errorMsg("No se pudo consultar carreras. " + e.getMessage());
        } finally {
            CON.cerrarCONPS(ps);
        }
        return carreras;
    }

    /*Obtener num de semanas de las carreras*/
    public ArrayList<CarreraMD> cargarNumdeSemanas(int id_prd) {
        String sql = "SELECT\n"
                + "\"Carreras\".carrera_semanas\n"
                + "FROM\n"
                + "\"Carreras\"\n"
                + "INNER JOIN \"PeriodoLectivo\" ON \"PeriodoLectivo\".id_carrera = \"Carreras\".id_carrera\n"
                + "WHERE \"PeriodoLectivo\".id_prd_lectivo = " + id_prd + "";
        ArrayList<CarreraMD> semanas = new ArrayList<>();
        conn = pool.getConnection();
        rst = pool.ejecutarQuery(sql, conn, null);
        try {
            while (rst.next()) {
                CarreraMD carrera = new CarreraMD();
                carrera.setNumSemanas(rst.getInt(1));
                semanas.add(carrera);
            }

            return semanas;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            pool.close(conn);
        }
        return semanas;
    }

    public String selectWhere(int idPeriodo) {

        String SELECT = "SELECT\n"
                + "	carrera_nombre \n"
                + "FROM\n"
                + "	\"Carreras\"\n"
                + "	INNER JOIN \"PeriodoLectivo\" ON \"Carreras\".id_carrera = \"PeriodoLectivo\".id_carrera\n"
                + "WHERE\n"
                + "\"PeriodoLectivo\".id_prd_lectivo = " + idPeriodo;

        String carrera = "";
        conn = pool.getConnection();
        rst = pool.ejecutarQuery(SELECT, conn, null);

        try {
            while (rst.next()) {

                carrera = rst.getString(1);

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            pool.closeStmt().close(rst).close(conn);
        }

        return carrera;
    }

    public static List<CarreraMD> findBy(String cedulaDocente) {
        String SELECT = ""
                + "SELECT DISTINCT\n"
                + "	\"Carreras\".id_carrera,\n"
                + "	\"Carreras\".carrera_nombre \n"
                + "FROM\n"
                + "	\"Cursos\"\n"
                + "	INNER JOIN \"Docentes\" ON \"Cursos\".id_docente = \"Docentes\".id_docente\n"
                + "	INNER JOIN \"PeriodoLectivo\" ON \"Cursos\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo\n"
                + "	INNER JOIN \"Carreras\" ON \"PeriodoLectivo\".id_carrera = \"Carreras\".id_carrera \n"
                + "WHERE\n"
                + "	\"Docentes\".docente_codigo = '" + CONS.USUARIO.getPersona().getIdentificacion() + "'"
                + "";

        List<CarreraMD> carreras = new ArrayList<>();

        ConnDBPool pool = new ConnDBPool();
        Connection conn = pool.getConnection();
        ResultSet rs = pool.ejecutarQuery(SELECT, conn, null);

        try {
            while (rs.next()) {
                CarreraMD carrera = new CarreraMD();
                carrera.setId(rs.getInt("id_carrera"));
                carrera.setNombre(rs.getString("carrera_nombre"));
                carreras.add(carrera);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CarreraBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            pool.closeStmt().close(rs).close(conn);
        }

        return carreras;

    }
}
