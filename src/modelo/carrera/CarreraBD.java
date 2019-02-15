package modelo.carrera;

/**
 *
 * @author arman
 */
public class CarreraBD extends CarreraMD {

    private final String INSERT = "INSERT INTO public.\"Carreras\"(\n"
            + "	id_carrera, carrera_codigo, carrera_descripcion, carrera_fecha_inicio, carrera_fecha_fin, carrera_modalidad, carrera_activo)\n"
            + "	VALUES (?, ?, ?, ?, ?, ?, ?)";

    private final String SELECT_ALL = "SELECT * "
            + " FROM Carreras ";

    private final String UPDATE = "UPDATE public.\"Carreras\"\n"
            + "	SET id_carrera=?, carrera_codigo=?, carrera_descripcion=?, carrera_fecha_inicio=?, carrera_fecha_fin=?, carrera_modalidad=?, carrera_activo=?\n"
            + "	WHERE id_carrera=?";

    private final String DELETE = "DELETE FROM Carreras WHERE id_carrera=?";

    private String SELECT_ONE(String aguja) {
        return "SELECT * FROM Carreras "
                + "WHERE \"id_carrera\" LIKE '%" + aguja + "%'"
                + "OR \"carrera_codigo\" LIKE '%" + aguja + "%'"
                + "OR \"carrera_descripcion\" LIKE '%" + aguja + "%'"
                + "OR \"carrera_modalidad\" LIKE '%" + aguja + "%'";
    }
}
