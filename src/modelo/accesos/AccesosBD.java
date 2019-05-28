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

    public List<AccesosBD> SelectAll() {

        String SELECT = "SELECT id_acceso, acc_nombre FROM \"Accesos\" ";

        return SelectSimple(SELECT);

    }

    public List<AccesosBD> SelectWhereACCESOROLidRol(int idRol) {

        String SELECT = "SELECT ACC.id_acceso, acc_nombre, acc_descripcion FROM \"Accesos\" ACC INNER JOIN \"AccesosDelRol\" ACC_ROL ON ACC.id_acceso = ACC_ROL.id_acceso WHERE ACC_ROL.id_rol = " + idRol;

        return SelectSimple(SELECT);
    }

    private List<AccesosBD> SelectSimple(String QUERY) {
        List<AccesosBD> Lista = new ArrayList<>();

        conn = pool.getConnection();
        rs = pool.ejecutarQuery(QUERY, conn, null);

        try {
            while (rs.next()) {
                AccesosBD acceso = new AccesosBD();
                acceso.setIdAccesos(rs.getInt("id_acceso"));
                acceso.setNombre(rs.getString("acc_nombre"));
                Lista.add(acceso);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AccesosBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            pool.closeStmt().close(rs).close(conn);
        }

        return Lista;
    }

    public List<AccesosBD> selectWhereLIKE(int idRol, String LIKE) {
        String SELECT = "SELECT\n"
                + "\"public\".\"Accesos\".acc_nombre\n"
                + "FROM\n"
                + "\"public\".\"Accesos\"\n"
                + "INNER JOIN \"public\".\"AccesosDelRol\" ON \"public\".\"AccesosDelRol\".id_acceso = \"public\".\"Accesos\".id_acceso\n"
                + "WHERE \n"
                + "\"AccesosDelRol\".id_rol = ?\n"
                + "AND \n"
                + "\"Accesos\".acc_nombre ILIKE '%" + LIKE + "%'";

        List<AccesosBD> Lista = new ArrayList<>();

        Map<Integer, Object> parametros = new HashMap<>();
        parametros.put(1, idRol);

        conn = pool.getConnection();
        rs = pool.ejecutarQuery(SELECT, conn, parametros);

        try {
            while (rs.next()) {
                AccesosBD acceso = new AccesosBD();
                acceso.setNombre(rs.getString("acc_nombre"));
                Lista.add(acceso);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccesosBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            pool.closeStmt().close(rs).close(conn);
        }

        return Lista;
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
