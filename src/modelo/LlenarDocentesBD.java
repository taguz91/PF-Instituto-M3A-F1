package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Johnny
 */
public class LlenarDocentesBD {

    ConectarDB conecta = new ConectarDB("Llena docentes BD");

    ArrayList<String> docentes;

    public void iniciar() {
        docentes = buscarDocenteEnPersona();
        for (int i = 0; i < docentes.size(); i++) {
            System.out.println("ID "+docentes.get(i)); 
            insertarDocente(docentes.get(i)); 
        }
        System.out.println("Numero de docentes consultados"+docentes.size());
    }

    public ArrayList<String> buscarDocenteEnPersona() {
        ArrayList<String> dc = new ArrayList();
        String sql = "select persona_identificacion from public.\"Personas\"\n"
                + "WHERE id_tipo_persona = 1";

        ResultSet rs = conecta.sql(sql);

        try {
            if (rs != null) {
                while (rs.next()) {
                    dc.add(rs.getString("persona_identificacion"));
                }
                return dc;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("No se pudieron consultar docentes. ");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void insertarDocente(String identificacion) {
        String nosql = "INSERT INTO public.\"Docentes\"\n"
                + "(docente_codigo, docente_otro_trabajo, docente_categoria, \n"
                + "docente_fecha_contrato, docente_tipo_tiempo, \n"
                + "docente_capacitador, id_persona)\n"
                + "SELECT pro_id, pro_otrotrabajo, pro_categoria,\n"
                + "pro_fechacontrato, pro_tipotiempo, pro_capacitador, (\n"
                + "	SELECT id_persona \n"
                + "FROM public.\"Personas\"\n"
                + "WHERE persona_identificacion = '" + identificacion + "'\n"
                + ") FROM public.profesores \n"
                + "WHERE pro_id = '" + identificacion + "';";
        if (conecta.nosql(nosql) == null) {
            System.out.println("Se guardo un docente correctamente");
        } else {
            System.out.println("ERROR AL GUARDAR DOCENTE");
        }
    }

}
