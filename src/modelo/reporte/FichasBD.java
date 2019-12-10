package modelo.reporte;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import utils.CONBD;

/**
 *
 * @author gus
 */
public class FichasBD extends CONBD {
    
    
    private static FichasBD FBD; 
     
    public static FichasBD single(){
        if (FBD == null) {
            FBD = new FichasBD();
        }
        return FBD;
    }
    
    

    public List<List<String>> getFichasSinResponder() {
        String sql = "SELECT\n"
                + "persona_identificacion AS \"IDENTIFICACIÓN\",\n"
                + "persona_primer_apellido || ' ' ||\n"
                + "persona_segundo_apellido || ' ' ||\n"
                + "persona_primer_nombre || ' ' ||\n"
                + "persona_segundo_nombre AS \"ALUMNO\",\n"
                + "persona_correo AS \"CORREO\",\n"
                + "prd_lectivo_nombre AS \"PERIODO\",\n"
                + "persona_ficha_fecha_ingreso AS \"FECHA INGRESO\",\n"
                + "persona_ficha_fecha_modificacion AS \"FECHA ULTIMA MODIFICACION\"\n"
                + "\n"
                + "FROM public.\"PersonaFicha\" pf\n"
                + "JOIN public.\"Personas\" p\n"
                + "ON p.id_persona = pf.id_persona\n"
                + "JOIN public.\"PermisoIngresoFichas\" pif\n"
                + "ON pif.id_permiso_ingreso_ficha = pf.id_permiso_ingreso_ficha\n"
                + "JOIN public.\"PeriodoLectivo\" pl\n"
                + "ON pl.id_prd_lectivo = pif.id_prd_lectivo\n"
                + "WHERE persona_ficha_finalizada = false\n"
                + "AND pl.id_prd_lectivo >= 30\n"
                + "\n"
                + "UNION\n"
                + "\n"
                + "SELECT\n"
                + "persona_identificacion AS \"IDENTIFICACIÓN\",\n"
                + "persona_primer_apellido || ' ' ||\n"
                + "persona_segundo_apellido || ' ' ||\n"
                + "persona_primer_nombre || ' ' ||\n"
                + "persona_segundo_nombre AS \"ALUMNO\",\n"
                + "persona_correo AS \"CORREO\",\n"
                + "prd_lectivo_nombre AS \"PERIODO\",\n"
                + "NULL AS \"FECHA INGRESO\",\n"
                + "NULL AS \"FECHA ULTIMA MODIFICACION\"\n"
                + "\n"
                + "\n"
                + "FROM public.\"Matricula\" m\n"
                + "JOIN public.\"Alumnos\" a\n"
                + "ON a.id_alumno = m.id_alumno\n"
                + "JOIN public.\"Personas\" p\n"
                + "ON p.id_persona = a.id_persona\n"
                + "JOIN public.\"PeriodoLectivo\" pl\n"
                + "ON pl.id_prd_lectivo = m.id_prd_lectivo\n"
                + "WHERE m.id_prd_lectivo >= 30\n"
                + "AND p.id_persona NOT IN (\n"
                + "  SELECT id_persona\n"
                + "  FROM public.\"PersonaFicha\" pf\n"
                + "  JOIN public.\"PermisoIngresoFichas\" pi\n"
                + "  ON pf.id_permiso_ingreso_ficha = pi.id_permiso_ingreso_ficha\n"
                + "  WHERE pi.id_prd_lectivo >= 30\n"
                + ")\n"
                + "\n"
                + "ORDER BY \"PERIODO\";";
        List<List<String>> fichas = new ArrayList<>();
        PreparedStatement ps = CON.getPSPOOL(sql);
        try {
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                List<String> cols = new ArrayList<>();

                cols.add(res.getString(1));
                cols.add(res.getString(2));
                cols.add(res.getString(3));
                cols.add(res.getString(4));
                if (res.getTimestamp(5) != null) {
                    cols.add(res.getTimestamp(5).toString());
                }
                if (res.getTimestamp(6) != null) {
                    cols.add(res.getTimestamp(6).toString());
                }

                fichas.add(cols);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "No consultamos fichas sin responder. \n"
                    + e.getMessage(),
                    "Error al consultar",
                    JOptionPane.ERROR_MESSAGE
            );
        } finally {
            CON.cerrarCONPS(ps);
        }

        return fichas;
    }

}
