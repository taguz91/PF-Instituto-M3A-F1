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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public boolean insertar() {

        String INSERT = "INSERT INTO \"Accesos\" "
                + " VALUES(?,?,?)";

        Map<Integer, Object> parametros = new HashMap<>();
        parametros.put(1, getIdAccesos());
        parametros.put(2, getNombre());
        parametros.put(3, getDescripcion());
        conn = pool.getConnection();
        return pool.ejecutar(INSERT, conn, parametros) == null;
    }

    public Map<String, AccesosBD> SelectAll() {

        String SELECT = "SELECT id_acceso, acc_nombre FROM \"Accesos\" ";

        return SelectSimple(SELECT);

    }

    private Map<String, AccesosBD> SelectSimple(String QUERY) {
        Map<String, AccesosBD> map = new HashMap<>();

        conn = pool.getConnection();
        rs = pool.ejecutarQuery(QUERY, conn, null);

        try {
            while (rs.next()) {
                AccesosBD acceso = new AccesosBD();
                acceso.setIdAccesos(rs.getInt("id_acceso"));
                acceso.setNombre(rs.getString("acc_nombre"));
                map.put(acceso.getNombre(), acceso);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AccesosBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            pool.closeStmt().close(rs).close(conn);
        }

        return map;
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
