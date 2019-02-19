package modelo.materia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.ConectarDB;
import modelo.carrera.CarreraMD;

/**
 *
 * @author Johnny
 */
public class EjeFormacionBD extends EjeFormacionMD {

    ConectarDB conecta = new ConectarDB();

    public EjeFormacionBD() {
    }

    public EjeFormacionMD buscar(int idEje) {
        EjeFormacionMD eje = new EjeFormacionMD();
        String sql = "SELECT id_eje, id_carrera, eje_codigo, eje_nombre\n"
                + "FROM public.\"EjesFormacion\" WHERE id_eje = '" + idEje + "';";
        ResultSet rs = conecta.sql(sql);

        try {
            if (rs != null) {
                while (rs.next()) {
                    CarreraMD carrera = new CarreraMD();
                    carrera.setId(rs.getInt("id_carrera"));
                    eje.setCarrera(carrera);
                    eje.setCodigo(rs.getString("eje_codigo"));
                    eje.setId(rs.getInt("id_eje"));
                    eje.setNombre(rs.getString("eje_nombre"));
                }
                return eje;
            } else {
                System.out.println("No se consulto un eje de formacion");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No se pudo realizar la consulta de eje formacion");
            return null;
        }
    }

    public ArrayList<EjeFormacionMD> cargarEjesFormacion() {
        ArrayList<EjeFormacionMD> ejes = new ArrayList();
        String sql = "SELECT id_eje, id_carrera, eje_codigo, eje_nombre\n"
                + "FROM public.\"EjesFormacion\";";
        ResultSet rs = conecta.sql(sql);

        try {
            if (rs != null) {
                while (rs.next()) {
                    EjeFormacionMD eje = new EjeFormacionMD();
                    CarreraMD carrera = new CarreraMD();
                    carrera.setId(rs.getInt("id_carrera"));
                    eje.setCarrera(carrera);
                    eje.setCodigo(rs.getString("eje_codigo"));
                    eje.setId(rs.getInt("id_eje"));
                    eje.setNombre(rs.getString("eje_nombre"));
                    //Agregamos a nuestro array
                    ejes.add(eje);
                }
                return ejes;
            } else {
                System.out.println("No se consulto un eje de formacion");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No se pudo realizar la consulta de eje formacion");
            return null;
        }
    }

    public ArrayList<EjeFormacionMD> cargarEjesPorCarrera(int idCarrera) {
        ArrayList<EjeFormacionMD> ejes = new ArrayList();
        String sql = "SELECT id_eje, id_carrera, eje_codigo, eje_nombre\n"
                + "FROM public.\"EjesFormacion\" WHERE id_carrera = '" + idCarrera + "';";
        ResultSet rs = conecta.sql(sql);
        //Ya que solo busca por una carrera la carrera solo se crea una vez
        CarreraMD carrera = new CarreraMD();
        carrera.setId(idCarrera);
        try {
            if (rs != null) {
                while (rs.next()) {
                    EjeFormacionMD eje = new EjeFormacionMD();

                    eje.setCarrera(carrera);
                    eje.setCodigo(rs.getString("eje_codigo"));
                    eje.setId(rs.getInt("id_eje"));
                    eje.setNombre(rs.getString("eje_nombre"));
                    //Agregamos a nuestro array
                    ejes.add(eje);
                }
                return ejes;
            } else {
                System.out.println("No se consulto un eje de formacion");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No se pudo realizar la consulta de eje formacion");
            return null;
        }
    }
}
