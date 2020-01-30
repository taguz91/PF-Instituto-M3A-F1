package utils;

import modelo.ConnDBPool;

/**
 *
 * @author gus
 */
public abstract class CONBD {

    protected static final ConnDBPool CON = ConnDBPool.single();
    
    protected void guardarHistoria(int id, String tipo, String tabla) {
        String nsql = " INSERT INTO public.\"HistorialUsuarios\"(\n"
                + "usu_username, "
                + "historial_fecha, "
                + "historial_tipo_accion, "
                + "historial_nombre_tabla, "
                + "historial_pk_tabla)\n"
                + " VALUES ("
                + "'" + CONS.USUARIO.getUsername() + "', "
                + "now(), "
                + "'" + tipo + "', "
                + "'" + tabla + "', "
                + id
                + ");";
        CON.executeNoSQL(nsql);
    }

}
