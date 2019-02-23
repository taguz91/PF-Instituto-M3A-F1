package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Johnny
 */
public class LlenarAlumnosBD {

    ConectarDB conecta = new ConectarDB();

    ArrayList<String> alumnos;

    public void iniciar() {
        alumnos = buscarAlumnosEnPersona();
        for (int i = 0; i < alumnos.size(); i++) {
            System.out.println("ID " + alumnos.get(i));
            insertarAlumno(alumnos.get(i)); 
        }
        System.out.println("Numero de alumnos consultados " + alumnos.size());
    }

    public ArrayList<String> buscarAlumnosEnPersona() {
        ArrayList<String> dc = new ArrayList();
        String sql = "SELECT persona_identificacion \n"
                + "FROM public.\"Personas\"\n"
                + "WHERE id_tipo_persona = 2;";

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
            System.out.println("No se pudieron consultar alumnos. ");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void insertarAlumno(String identificacion) {
        String nosql = "INSERT INTO public.\"Alumnos\"(\n"
                + "	id_sec_economico, alumno_tipo_colegio, alumno_tipo_bachillerato,\n"
                + "	alumno_anio_graduacion, alumno_educacion_superior, alumno_titulo_superior, \n"
                + "	alumno_nivel_academico, alumno_ocupacion, alumno_trabaja,\n"
                + "	alumno_nivel_formacion_padre, alumno_nivel_formacion_madre,\n"
                + "	alumno_nombre_contacto_emergencia, alumno_parentesco_contacto,\n"
                + "	alumno_numero_contacto, id_persona)\n"
                + "     SELECT  alu_sectoreconomico, alu_tipocolegio, alu_tipobachillerato, alu_aniograduacion,\n"
                + "     alu_educacionsuperior, alu_titulosuperior, alu_nivelacademico, \n"
                + "     alu_ocupacion, alu_trabaja, \n"
                + "     alu_nivelformacionpadre, alu_nivelformacionmadre, alu_contactoemergencia, \n"
                + "     alu_parentescocontacto, alu_numerocontacto, (\n"
                + " 	SELECT id_persona \n"
                + "	FROM public.\"Personas\"\n"
                + "	WHERE persona_identificacion = '"+identificacion+"'\n"
                + ")FROM public.estudiantes\n"
                + " WHERE alu_id = '"+identificacion+"';";
        if (conecta.nosql(nosql) == null) {
            System.out.println("Se guardo un alumno correctamente");
        } else {
            System.out.println("ERROR AL GUARDAR AlUMNO");
        }
    }

}
