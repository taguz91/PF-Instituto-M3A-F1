package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Johnny
 */
public class LlenarLugaresBD {

    //Para que esto funcione debe existir la tabla city en la base de datos 
    ConectarDB conecta = new ConectarDB();

    ArrayList<String> lugares;
    ArrayList<String> distritos;

    public void iniciar() {
        lugares = cargarPaises();

        if (lugares != null) {
            for (int i = 0; i < lugares.size(); i++) {
                System.out.println(lugares.get(i));
                insertarDistritosDePais(lugares.get(i));
                System.out.println("");
            }
            System.out.println("Numero de paises " + lugares.size());
        }

        //Solo para consultar a ver si funciona
        distritos = cargarDistritosDePais(lugares.get(1));
        if (distritos != null) {
            System.out.println("Distritos de " + lugares.get(1));
            for (int i = 0; i < distritos.size(); i++) {
                System.out.println(distritos.get(i));
            }
        }
    }

    public ArrayList<String> cargarPaises() {
        ArrayList<String> paises = new ArrayList();
        String sql = "SELECT * FROM public.\"Lugares\" "
                + "WHERE id_lugar_referencia IS NULL AND lugar_nombre <> 'ECUADOR';";

        ResultSet rs = conecta.sql(sql);

        try {
            if (rs != null) {
                while (rs.next()) {
                    paises.add(rs.getString("lugar_nombre"));
                }
                rs.close();
                return paises;
            } else {
                System.out.println("Error al cargar paises.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar paises.");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<String> cargarDistritosDePais(String nombrePais) {
        ArrayList<String> districs = new ArrayList();

        String sql = "SELECT DISTINCT \"district\", 2 ,(\n"
                + "	SELECT id_lugar FROM public.\"Lugares\" \n"
                + "	WHERE lugar_nombre = '" + nombrePais + "'\n"
                + ") FROM public.city\n"
                + "WHERE \"country\" ILIKE '" + nombrePais + "';";

        ResultSet rs = conecta.sql(sql);

        try {
            if (rs != null) {
                while (rs.next()) {
                    districs.add(rs.getString("district"));
                }
                return districs;
            } else {
                System.out.println("No pudimos consultar distritos de " + nombrePais);
                return null;
            }
        } catch (Exception e) {
            System.out.println("No pudimos consultar distritos de " + nombrePais);
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void insertarDistritosDePais(String nombrePais) {
        String nosql = "INSERT INTO public.\"Lugares\"(\n"
                + "	lugar_nombre, lugar_nivel, id_lugar_referencia)\n"
                + "    SELECT DISTINCT \"district\", 2 ,(\n"
                + "	SELECT id_lugar FROM public.\"Lugares\" \n"
                + "	WHERE lugar_nombre = '" + nombrePais + "' "
                + "     AND lugar_codigo IS NULL\n"
                + ") FROM public.city\n"
                + "WHERE \"country\" ILIKE '" + nombrePais + "';";

        if (conecta.nosql(nosql) == null) {
            System.out.println("Se guarda bien los distritos de un pais");
        } else {
            System.out.println("ERROR AL INSERTAR");
        }
    }

}
