package modelo.persona;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.ConectarDB;

/**
 *
 * @author Johnny
 */
public class TipoPersonaBD extends TipoPersonaMD {

    ConectarDB conecta = new ConectarDB("Tipo persona");

    public TipoPersonaBD() {
    }

    public ArrayList<TipoPersonaMD> cargarTipoPersona() {
        ArrayList<TipoPersonaMD> tipos = new ArrayList();
        String sql = "SELECT id_tipo_persona, tipo_persona\n"
                + "FROM public.\"TipoPersona\" WHERE tipo_persona_activo = 'true';";
        ResultSet rs = conecta.sql(sql);
        try {
            if (rs != null) {

                while (rs.next()) {
                    TipoPersonaMD tip = new TipoPersonaMD();
                    tip.setId(rs.getInt("id_tipo_persona"));
                    tip.setTipo(rs.getString("tipo_persona"));
                    tipos.add(tip);
                }
                return tipos;
            } else {
                System.out.println("No se encontraron datos.");
                return null;
            }

        } catch (SQLException ex) {
            System.out.println("No se pudo consultar tipos de persona. ");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public TipoPersonaMD buscar(int idTipoPersona) {
        TipoPersonaMD tipo = new TipoPersonaMD();
        String sql = "SELECT id_tipo_persona, tipo_persona\n"
                + "FROM public.\"TipoPersona\" WHERE tipo_persona_activo = 'true' "
                + "and id_tipo_persona = '" + idTipoPersona + "';";

        ResultSet rs = conecta.sql(sql);

        try {
            if (rs != null) {
                TipoPersonaMD tip = new TipoPersonaMD();
                while (rs.next()) {
                    tip.setId(rs.getInt("id_tipo_persona"));
                    tip.setTipo(rs.getString("tipo_persona"));
                }
                return tip;
            } else {
                System.out.println("No se encontraron datos.");
                return null;
            }

        } catch (SQLException ex) {
            System.out.println("No se pudo consultar tipos de persona. ");
            System.out.println(ex.getMessage());
            return null;
        }
    }

}
