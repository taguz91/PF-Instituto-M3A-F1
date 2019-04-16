package modelo.carrera;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.ConectarDB;
import modelo.ResourceManager;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;

/**
 *
 * @author arman
 */
public class CarreraBD extends CarreraMD {

    private final ConectarDB conecta;
    private final DocenteBD doc;

    public CarreraBD(ConectarDB conecta) {
        this.conecta = conecta;
        this.doc = new DocenteBD(conecta);
    }

    public void guardarCarrera() {
        String nsql = "INSERT INTO public.\"Carreras\"(\n"
                + "	id_docente_coordinador, carrera_nombre, \n"
                + "	carrera_codigo, carrera_fecha_inicio, carrera_modalidad)\n"
                + "	VALUES (" + getCoordinador().getIdDocente() + ", "
                + " '" + getNombre() + "', '" + getCodigo() + "', '" + getFechaInicio() + "',"
                + " '" + getModalidad() + "');";
        if (conecta.nosql(nsql) == null) {
            JOptionPane.showMessageDialog(null, "Guardamos correctamente \n" + getNombre());
        }
    }

    public void editarCarrera(int idCarrera) {
        String nsql = "UPDATE public.\"Carreras\"\n"
                + "SET id_docente_coordinador=" + getCoordinador().getIdDocente() + ", "
                + "carrera_nombre='" + getNombre() + "', \n"
                + "carrera_codigo='" + getCodigo() + "', carrera_fecha_inicio='" + getFechaInicio() + "', \n"
                + "carrera_modalidad='" + getModalidad() + "'\n"
                + "WHERE id_carrera=" + idCarrera + ";";

        if (conecta.nosql(nsql) == null) {
            JOptionPane.showMessageDialog(null, "Editamos correctamente \n" + getNombre());
        }
    }

    public void eliminarCarrera(int idCarrera) {
        String nsql = "UPDATE public.\"Carreras\"\n"
                + "SET  carrera_activo='false'\n"
                + "WHERE id_carrera=" + idCarrera + ";";
        if (conecta.nosql(nsql) == null) {
            JOptionPane.showMessageDialog(null, "Eliminamos correctamente ");
        }
    }

    public CarreraMD buscar(int idCarrera) {
        CarreraMD carrera = new CarreraMD();
        String sql = "SELECT id_carrera, id_docente_coordinador, carrera_nombre,"
                + " carrera_codigo, carrera_fecha_inicio, carrera_fecha_fin,"
                + " carrera_modalidad, carrera_activo\n"
                + "FROM public.\"Carreras\" WHERE id_carrera = '" + idCarrera + "';";

        ResultSet rs = conecta.sql(sql);

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
                + "carrera_modalidad, (\n"
                + "	SELECT persona_primer_nombre || ' ' || \n"
                + "	persona_segundo_nombre || ' ' ||\n"
                + "	persona_primer_apellido || ' ' ||\n"
                + "	persona_segundo_apellido || ' ' || "
                + "     persona_identificacion\n"
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
                + "carrera_modalidad, (\n"
                + "	SELECT persona_primer_nombre || ' ' || \n"
                + "	persona_segundo_nombre || ' ' ||\n"
                + "	persona_primer_apellido || ' ' ||\n"
                + "	persona_segundo_apellido \n"
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
        ResultSet rs = conecta.sql(sql);

        if (rs != null) {
            try {
                while (rs.next()) {
                    CarreraMD carrera = new CarreraMD();

                    carrera.setId(rs.getInt("id_carrera"));
                    DocenteMD docen = new DocenteMD();
                    String nombreC = rs.getString(7);
                    if (nombreC != null) {
                        String nombres[] = nombreC.split(" ");
                        docen.setPrimerNombre(nombres[0]);
                        docen.setSegundoNombre(nombres[1]);
                        docen.setPrimerApellido(nombres[2]);
                        docen.setSegundoApellido(nombres[3]);
                    }
                    carrera.setCoordinador(docen);

                    carrera.setNombre(rs.getString("carrera_nombre"));
                    carrera.setCodigo(rs.getString("carrera_codigo"));
                    carrera.setFechaInicio(rs.getDate("carrera_fecha_inicio").toLocalDate());
                    carrera.setModalidad(rs.getString("carrera_modalidad"));

                    carreras.add(carrera);
                }
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
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    CarreraMD carrera = new CarreraMD();
                    carrera.setId(rs.getInt("id_carrera"));
                    carrera.setNombre(rs.getString("carrera_nombre"));
                    carrera.setCodigo(rs.getString("carrera_codigo"));

                    carreras.add(carrera);
                }
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

    public static String selectCarreraWherePerdLectivo(String nombre) {

        String SELECT = "SELECT\n"
                + "\"Carreras\".carrera_nombre\n"
                + "FROM\n"
                + "\"PeriodoLectivo\"\n"
                + "INNER JOIN \"Carreras\" ON \"PeriodoLectivo\".id_carrera = \"Carreras\".id_carrera\n"
                + "WHERE\n"
                + "\"PeriodoLectivo\".prd_lectivo_nombre = '" + nombre + "'\n"
                + "AND\n"
                + "\"PeriodoLectivo\".prd_lectivo_estado = TRUE";

        String carrera = "";

        ResultSet rs = ResourceManager.Query(SELECT);

        try {
            while (rs.next()) {

                carrera = rs.getString("carrera_nombre");

            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return carrera;
    }

    public static List<CarreraMD> selectIdNombreAll() {
        String SELECT = "SELECT\n"
                + "\"public\".\"Carreras\".id_carrera,\n"
                + "\"public\".\"Carreras\".carrera_nombre\n"
                + "FROM\n"
                + "\"public\".\"Carreras\"";

        List<CarreraMD> lista = new ArrayList<>();
        ResultSet rs = ResourceManager.Query(SELECT);
        try {
            while (rs.next()) {

                CarreraMD carrera = new CarreraMD();

                carrera.setId(rs.getInt("id_carrera"));
                carrera.setNombre(rs.getString("carrera_nombre"));
                lista.add(carrera);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return lista;
    }

}
