/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.accesos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modelo.ConnDBPool;

/**
 *
 * @author MrRainx
 */
public class AccesosBD extends AccesosMD {

    private ConnDBPool pool;
    private Connection conn;
    private ResultSet rs;

    {
        pool = new ConnDBPool();
    }

    public AccesosBD(int idAccesos, String nombre, String descripcion) {
        super(idAccesos, nombre, descripcion);
    }

    public AccesosBD() {
    }

    public List<AccesosBD> SelectAll() {

        String SELECT = "SELECT\n"
                + "	\"public\".\"Accesos\".id_acceso,\n"
                + "	\"public\".\"Accesos\".acc_nombre,\n"
                + "	\"public\".\"Accesos\".acc_descripcion \n"
                + "FROM\n"
                + "	\"public\".\"Accesos\"";

        List<AccesosBD> lista = new ArrayList<>();

        conn = pool.getConnection();

        rs = pool.ejecutarQuery(SELECT, conn, null);

        try {
            while (rs.next()) {
                AccesosBD acceso = new AccesosBD();
                acceso.setIdAccesos(rs.getInt("id_acceso"));
                acceso.setNombre(rs.getString("acc_nombre"));
                acceso.setDescripcion(rs.getString("acc_descripcion"));
                lista.add(acceso);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            pool.closeStmt().close(rs).close(conn);
        }

        return lista;

    }

    public boolean editar(String pk) {
        String UPDATE = "UPDATE Accesos SET\n"
                + " acc_nombre = ?\n"
                + " acc_descripcion = ?\n"
                + "WHERE\n"
                + " id_acceso = ?";

        Map<Integer, Object> parametros = new HashMap<>();
        parametros.put(1, getNombre());
        parametros.put(2, getDescripcion());
        parametros.put(2, pk);
        System.out.println(UPDATE);
        conn = pool.getConnection();
        return pool.ejecutar(UPDATE, conn, parametros) == null;
    }

}
