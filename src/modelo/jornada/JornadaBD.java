package modelo.jornada;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.ConectarDB;

/**
 *
 * @author Johnny
 */
public class JornadaBD extends JornadaMD {

    private final ConectarDB conecta;

    public JornadaBD(ConectarDB conecta) {
        this.conecta = conecta;
    }
    
    public ArrayList<JornadaMD> cargarJornadas() {
        ArrayList<JornadaMD> jornadas = new ArrayList();
        String sql = "SELECT id_jornada, nombre_jornada\n"
                + "	FROM public.\"Jornadas\";";

        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    JornadaMD j = obtenerJornada(rs);
                    if (j != null) {
                        jornadas.add(j);
                    }
                }
                return jornadas;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No pudimos cargar jornadas");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public JornadaMD buscarJornada(int idJornada) {
        JornadaMD j = null;
        String sql = "SELECT id_jornada, nombre_jornada\n"
                + "	FROM public.\"Jornadas\"  "
                + "WHERE id_jornada = " + idJornada + ";";

        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    j = obtenerJornada(rs);
                }
                return j;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No pudimos consultar una jornada " + idJornada);
            System.out.println(e.getMessage());
            return null;
        }
    }

    public JornadaMD obtenerJornada(ResultSet rs) {
        JornadaMD j = new JornadaMD();
        try {
            j.setId(rs.getInt("id_jornada"));
            j.setNombre(rs.getString("nombre_jornada"));

            return j;
        } catch (SQLException e) {
            System.out.println("No pudimos obtener una jornada");
            System.out.println(e.getMessage());
            return null;
        }
    }
}
