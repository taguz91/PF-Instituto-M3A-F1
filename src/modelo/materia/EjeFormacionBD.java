package modelo.materia;

import java.sql.PreparedStatement;
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

    private final ConectarDB conecta;

    public EjeFormacionBD(ConectarDB conecta) {
        this.conecta = conecta;
    }


    public EjeFormacionMD buscar(int idEje) {
        EjeFormacionMD eje = new EjeFormacionMD();
        String sql = "SELECT id_eje, id_carrera, eje_codigo, eje_nombre\n"
                + "FROM public.\"EjesFormacion\" WHERE id_eje = '" + idEje + "';";
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
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
                ps.getConnection().close();
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
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
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
                ps.getConnection().close();
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
        PreparedStatement ps = conecta.getPS(sql);
        ResultSet rs = conecta.sql(ps);
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
                ps.getConnection().close();
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
