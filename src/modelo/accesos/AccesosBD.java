/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.accesos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ResourceManager;

/**
 *
 * @author MrRainx
 */
public class AccesosBD extends AccesosMD {

    public AccesosBD(int idAccesos, String nombre, String descripcion) {
        super(idAccesos, nombre, descripcion);
    }

    public AccesosBD() {
    }

    public boolean insertar() {

        String INSERT = "INSERT INTO \"Accesos\" "
                + " VALUES"
                + "("
                + "" + getIdAccesos() + ","
                + "'" + getNombre() + "',"
                + "'" + getDescripcion() + "'"
                + ")"
                + "";

        return ResourceManager.Statement(INSERT) == null;
    }

    public List<AccesosMD> SelectAll() {

        String SELECT = "SELECT id_acceso, acc_nombre, acc_descripcion FROM \"Accesos\" ";

        return SelectSimple(SELECT);

    }

    public List<AccesosMD> SelectOneWhereNombreLike(String Aguja) {

        String SELECT = "SELECT id_acceso, acc_nombre, acc_descripcion FROM \"Accesos\" WHERE acc_nombre LIKE '%" + Aguja + "%'";

        return SelectSimple(SELECT);
    }

    public List<AccesosMD> SelectWhereIdAcceso(int idAcceso) {
        String SELECT = "SELECT id_acceso, acc_nombre, acc_descripcion FROM \"Accesos\" WHERE id_acceso = " + idAcceso + "";

        return SelectSimple(SELECT);
    }

    public static List<AccesosMD> SelectWhereACCESOROLidRol(int idRol) {

        String SELECT = "SELECT ACC.id_acceso, acc_nombre, acc_descripcion FROM \"Accesos\" ACC INNER JOIN \"AccesosDelRol\" ACC_ROL ON ACC.id_acceso = ACC_ROL.id_acceso WHERE ACC_ROL.id_rol = " + idRol;

        return SelectSimple(SELECT);
    }

    private static List<AccesosMD> SelectSimple(String QUERY) {
        List<AccesosMD> Lista = new ArrayList<>();

        ResultSet rs = ResourceManager.Query(QUERY);

        try {
            while (rs.next()) {
                AccesosMD acceso = new AccesosMD();
                if (rs.wasNull()) {

                }
                acceso.setIdAccesos(rs.getInt("id_acceso"));
                acceso.setNombre(rs.getString("acc_nombre"));
                acceso.setDescripcion(rs.getString("acc_descripcion"));
                Lista.add(acceso);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(AccesosBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Lista;
    }

    public static List<AccesosMD> selectWhereLIKE(int idRol, String LIKE) {
        String SELECT = "SELECT\n"
                + "\"public\".\"Accesos\".acc_nombre\n"
                + "FROM\n"
                + "\"public\".\"Accesos\"\n"
                + "INNER JOIN \"public\".\"AccesosDelRol\" ON \"public\".\"AccesosDelRol\".id_acceso = \"public\".\"Accesos\".id_acceso\n"
                + "WHERE \n"
                + "\"AccesosDelRol\".id_rol = " + idRol + "\n"
                + "AND \n"
                + "\"Accesos\".acc_nombre ILIKE '%" + LIKE + "%'";

        List<AccesosMD> Lista = new ArrayList<>();

        ResultSet rs = ResourceManager.Query(SELECT);

        try {
            while (rs.next()) {
                AccesosMD acceso = new AccesosMD();
                acceso.setNombre(rs.getString("acc_nombre"));
                Lista.add(acceso);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(AccesosBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Lista;
    }

}
