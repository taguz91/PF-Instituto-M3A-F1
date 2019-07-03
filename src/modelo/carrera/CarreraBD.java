package modelo.carrera;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.ConectarDB;
import modelo.ConnDBPool;
import modelo.curso.SesionClaseMD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;

/**
 *
 * @author arman
 */
public class CarreraBD extends CarreraMD {

    private final ConectarDB conecta;
    private final DocenteBD doc;
    private ConnDBPool pool;
    private ResultSet rst;
    private Connection conn;

    public CarreraBD(ConectarDB conecta) {
        this.conecta = conecta;
        this.doc = new DocenteBD(conecta);
    }

    public CarreraBD() {
        this.conecta = null;
        this.doc = null;
    }

    {
        pool = new ConnDBPool();
    }

    public boolean guardarCarrera() {
        String nsql = "INSERT INTO public.\"Carreras\"(\n"
                + "	id_docente_coordinador, carrera_nombre, \n"
                + "	carrera_codigo, carrera_fecha_inicio, carrera_modalidad, "
                + "     carrera_semanas)\n"
                + "	VALUES (" + getCoordinador().getIdDocente() + ", "
                + " '" + getNombre() + "', '" + getCodigo() + "', '" + getFechaInicio() + "',"
                + " '" + getModalidad() + "', " + getNumSemanas() + ");";
        PreparedStatement ps = conecta.getPS(nsql);
        if (conecta.nosql(ps) == null) {
            JOptionPane.showMessageDialog(null, "Guardamos correctamente \n" + getNombre());
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo guardar \n" + getNombre() + "\n"
                    + "Revise su conexion a internet.");
            return false;
        }
    }

    public boolean editarCarrera(int idCarrera) {
        String nsql = "UPDATE public.\"Carreras\"\n"
                + "SET id_docente_coordinador=" + getCoordinador().getIdDocente() + ", "
                + "carrera_nombre='" + getNombre() + "', \n"
                + "carrera_codigo='" + getCodigo() + "', carrera_fecha_inicio='" + getFechaInicio() + "', \n"
                + "carrera_modalidad='" + getModalidad() + "', carrera_semanas = " + getNumSemanas() + "\n"
                + "WHERE id_carrera=" + idCarrera + ";";
        PreparedStatement ps = conecta.getPS(nsql);
        if (conecta.nosql(ps) == null) {
            JOptionPane.showMessageDialog(null, "Editamos correctamente \n" + getNombre());
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo editar \n" + getNombre() + "\n"
                    + "Revise su conexion a internet.");
            return false;
        }
    }

    public void eliminarCarrera(int idCarrera) {
        String nsql = "UPDATE public.\"Carreras\"\n"
                + "SET  carrera_activo='false'\n"
                + "WHERE id_carrera=" + idCarrera + ";";
        PreparedStatement ps = conecta.getPS(nsql);
        if (conecta.nosql(ps) == null) {
            JOptionPane.showMessageDialog(null, "Eliminamos correctamente ");
        }
    }

    public CarreraMD buscar(int idCarrera) {
        CarreraMD carrera = new CarreraMD();
        String sql = "SELECT id_carrera, id_docente_coordinador, carrera_nombre,"
                + " carrera_codigo, carrera_fecha_inicio, carrera_fecha_fin,"
                + " carrera_modalidad, carrera_activo\n"
                + "FROM public.\"Carreras\" WHERE id_carrera = '" + idCarrera + "';";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);

        try {
            if (rs != null) {
                while (rs.next()) {
                    carrera.setId(rs.getInt("id_carrera"));
                    DocenteMD docen = null;
                    if (!rs.wasNull()) {
                        docen = doc.buscarDocente(rs.getInt("id_docente_coordinador"));
                    }
                    carrera.setCoordinador(docen);
                    carrera.setNombre(rs.getString("carrera_nombre"));

                    carrera.setCodigo(rs.getString("carrera_codigo"));
                    carrera.setFechaInicio(rs.getDate("carrera_fecha_inicio").toLocalDate());

                    if (rs.wasNull()) {
                        carrera.setFechaFin(rs.getDate("carrera_fecha_fin").toLocalDate());
                    } else {
                        carrera.setFechaFin(null);
                    }

                    carrera.setModalidad(rs.getString("carrera_modalidad"));
                }
                ps.getConnection().close();
                return carrera;
            } else {
                System.out.println("No se pudo consultar una carreras");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No se pudo consultar carreras");
            System.out.println(e.getMessage());
            return null;
        }
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
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);

        try {
            if (rs != null) {
                while (rs.next()) {
                    carrera = new CarreraMD();
                    carrera.setId(rs.getInt("id_carrera"));
                    carrera.setNombre(rs.getString("carrera_nombre"));
                    carrera.setCodigo(rs.getString("carrera_codigo"));
                    carrera.setFechaInicio(rs.getDate("carrera_fecha_inicio").toLocalDate());
                    carrera.setModalidad(rs.getString("carrera_modalidad"));
                }
                ps.getConnection().close();
                return carrera;
            } else {
                System.out.println("No se pudo consultar una carreras");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No se pudo consultar carreras");
            System.out.println(e.getMessage());
            return null;
        }
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
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);

        if (rs != null) {
            try {
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
                ps.getConnection().close();
                return carreras;
            } catch (SQLException e) {
                System.out.println("No se pudo consultar carreras");
                System.out.println(e.getMessage());
                return null;
            }
        } else {
            System.out.println("No se pudo consultar una carreras");
            return null;
        }

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
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
        try {
            if (rs != null) {
                while (rs.next()) {
                    CarreraMD carrera = new CarreraMD();
                    carrera.setId(rs.getInt("id_carrera"));
                    carrera.setNombre(rs.getString("carrera_nombre"));
                    carrera.setCodigo(rs.getString("carrera_codigo"));

                    carreras.add(carrera);
                }
                ps.getConnection().close();
                return carreras;
            } else {
                System.out.println("No se pudo consultar una carreras");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No se pudo consultar carreras");
            System.out.println(e.getMessage());
            return null;
        }
    }

    /*Obtener num de semanas de las carreras*/
    public ArrayList<CarreraMD> cargarNumdeSemanas(int id_prd) {
        String sql = "SELECT\n"
                + "\"public\".\"Carreras\".carrera_semanas\n"
                + "FROM\n"
                + "\"public\".\"Carreras\"\n"
                + "INNER JOIN \"public\".\"PeriodoLectivo\" ON \"public\".\"PeriodoLectivo\".id_carrera = \"public\".\"Carreras\".id_carrera\n"
                + "WHERE \"PeriodoLectivo\".id_prd_lectivo = " + id_prd + "";
        ArrayList<CarreraMD> semanas = new ArrayList<>();
        conn = pool.getConnection();
        rst = pool.ejecutarQuery(sql, conn, null);
        try {
            while (rst.next()) {
                CarreraMD carrera = new CarreraMD();
                carrera.setNumSemanas(rst.getInt(1));
                System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$");
                System.out.println(rst.getInt(1));
                System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$");
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
}
