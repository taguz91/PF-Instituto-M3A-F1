package modelo.carrera;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.ConectarDB;
import modelo.persona.DocenteMD;

/**
 *
 * @author arman
 */
public class CarreraBD extends CarreraMD {

    public CarreraBD() {
    }

    ConectarDB conecta = new ConectarDB("Carrera");

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
                    carrera.setCodigo(rs.getString("carrera_codigo"));
                    DocenteMD docen = new DocenteMD();
                    if (rs.wasNull()) {
                        docen.setIdDocente(0);
                    }else{
                        docen.setIdDocente(rs.getInt("id_docente_coordinador"));
                    } 
                    carrera.setCoordinador(docen);
                    if (rs.wasNull()) {
                        carrera.setFechaFin(null);
                    } else {
                        carrera.setFechaFin(rs.getDate("carrera_fecha_fin").toLocalDate());
                    }
                    carrera.setFechaInicio(rs.getDate("carrera_fecha_inicio").toLocalDate());
                    carrera.setId(rs.getInt("id_carrera"));
                    carrera.setModalidad(rs.getString("carrera_modalidad"));
                    carrera.setNombre(rs.getString("carrera_nombre"));
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

    public ArrayList<CarreraMD> cargarCarreras() {
        ArrayList<CarreraMD> carreras = new ArrayList();
        String sql = "SELECT id_carrera, id_docente_coordinador, carrera_nombre,"
                + " carrera_codigo, carrera_fecha_inicio, carrera_fecha_fin,"
                + " carrera_modalidad, carrera_activo\n"
                + "FROM public.\"Carreras\";";

        ResultSet rs = conecta.sql(sql);

        try {
            if (rs != null) {
                while (rs.next()) {
                    CarreraMD carrera = new CarreraMD();
                    carrera.setCodigo(rs.getString("carrera_codigo"));
                    DocenteMD docen = new DocenteMD();
                    if (rs.wasNull()) {
                        docen.setIdDocente(0);
                    }else{
                        docen.setIdDocente(rs.getInt("id_docente_coordinador"));
                    } 
                    carrera.setCoordinador(docen);
                    if (rs.wasNull()) {
                        carrera.setFechaFin(null);
                    } else {
                        carrera.setFechaFin(rs.getDate("carrera_fecha_fin").toLocalDate());
                    }
                    carrera.setFechaInicio(rs.getDate("carrera_fecha_inicio").toLocalDate());
                    carrera.setId(rs.getInt("id_carrera"));
                    carrera.setModalidad(rs.getString("carrera_modalidad"));
                    carrera.setNombre(rs.getString("carrera_nombre"));

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
}
