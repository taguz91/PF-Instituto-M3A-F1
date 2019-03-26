/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.tipoDeNota;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ResourceManager;

/**
 *
 * @author MrRainx
 */
public class TipoDeNotaBD extends TipoDeNotaMD {

    public TipoDeNotaBD(int idTipoNota, String nombre, double valorMinimo, double valorMaximo, LocalDate fechaCreacion, boolean estado) {
        super(idTipoNota, nombre, valorMinimo, valorMaximo, fechaCreacion, estado);
    }

    public TipoDeNotaBD() {
    }

    public TipoDeNotaBD(TipoDeNotaMD obj) {
        this.setIdTipoNota(obj.getIdTipoNota());
        this.setNombre(obj.getNombre());
        this.setValorMinimo(obj.getValorMinimo());
        this.setValorMaximo(obj.getValorMaximo());
        this.setFechaCreacion(obj.getFechaCreacion());
        this.setEstado(obj.isEstado());
    }

    private static final String TABLA = " \"TipoDeNota\" ";

    private static final String ATRIBUTOS = "\"TipoDeNota\".id_tipo_nota,\n"
            + "\"TipoDeNota\".tipo_nota_nombre,\n"
            + "\"TipoDeNota\".tipo_nota_valor_minimo,\n"
            + "\"TipoDeNota\".tipo_nota_valor_maximo,\n"
            + "\"TipoDeNota\".tipo_nota_fecha_creacion,\n"
            + "\"TipoDeNota\".tipo_nota_estado";

    private static final String PRIMARY_KEY = " \"TipoDeNota\".id_tipo_nota ";

    private static final String RESTRICCION = " \"TipoDeNota\".tipo_nota_estado  IS TRUE ";

    public boolean insertar() {
        String INSERT = "INSERT INTO " + TABLA + " \n"
                + "( tipo_nota_nombre, tipo_nota_valor_minimo, tipo_nota_valor_maximo )\n"
                + "VALUES\n"
                + "( "
                + "'" + getNombre() + "',"
                + " " + getValorMinimo() + ", "
                + " " + getValorMaximo() + ""
                + " );";

        return ResourceManager.Statement(INSERT) == null;
    }

    public List<TipoDeNotaMD> SelectAll() {

        String SELECT = "SELECT " + ATRIBUTOS + " FROM " + TABLA + " WHERE " + RESTRICCION + " ORDER BY tipo_nota_fecha_creacion DESC";

        return SelectSimple(SELECT);

    }

    public List<TipoDeNotaMD> SelectOneWhereNombre(String Aguja) {

        String SELECT = "SELECT " + ATRIBUTOS + " FROM " + TABLA + "  WHERE lower(tipo_nota_nombre) LIKE '%" + Aguja + "%' AND " + RESTRICCION + "  ORDER BY tipo_nota_fecha_creacion DESC";
        return SelectSimple(SELECT);

    }

    public static TipoDeNotaMD selectWhere(int idTipoNota) {

        String SELECT = "SELECT " + ATRIBUTOS + " FROM " + TABLA + " WHERE " + RESTRICCION;

        TipoDeNotaMD tipoNota = new TipoDeNotaMD();

        ResultSet rs = ResourceManager.Query(SELECT);

        try {

            while (rs.next()) {
                
                tipoNota.setIdTipoNota(rs.getInt("id_tipo_nota"));
                tipoNota.setNombre(rs.getString("tipo_nota_nombre"));
                tipoNota.setValorMinimo(rs.getDouble("tipo_nota_valor_minimo"));
                tipoNota.setValorMaximo(rs.getDouble("tipo_nota_valor_maximo"));
                tipoNota.setEstado(rs.getBoolean("tipo_nota_estado"));
                
            }
            
            rs.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return tipoNota;
    }

    private List<TipoDeNotaMD> SelectSimple(String QUERY) {
        List<TipoDeNotaMD> Lista = new ArrayList<>();

        ResultSet rs = ResourceManager.Query(QUERY);

        try {
            while (rs.next()) {

                TipoDeNotaMD tipoNota = new TipoDeNotaMD();

                tipoNota.setIdTipoNota(rs.getInt("id_tipo_nota"));
                tipoNota.setNombre(rs.getString("tipo_nota_nombre"));
                tipoNota.setValorMinimo(rs.getDouble("tipo_nota_valor_minimo"));
                tipoNota.setValorMaximo(rs.getDouble("tipo_nota_valor_maximo"));
                tipoNota.setFechaCreacion(rs.getDate("tipo_nota_fecha_creacion").toLocalDate());
                tipoNota.setEstado(rs.getBoolean("tipo_nota_estado"));

                Lista.add(tipoNota);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(TipoDeNotaBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Lista;
    }

    public boolean editar(int Pk) {
        String UPDATE = "UPDATE\n"
                + "	\"TipoDeNota\" \n"
                + "SET \n"
                + "	tipo_nota_nombre = '" + getNombre() + "',\n"
                + "	tipo_nota_valor_minimo = " + getValorMinimo() + ",\n"
                + "	tipo_nota_valor_maximo = " + getValorMaximo() + " \n"
                + "WHERE\n"
                + "	id_tipo_nota = " + Pk;

        return ResourceManager.Statement(UPDATE) == null;

    }

    public boolean eliminar(int Pk) {
        String DELETE = "UPDATE\n"
                + TABLA
                + "SET \n"
                + "     tipo_nota_estado = " + false + " \n"
                + "WHERE\n"
                + "	" + PRIMARY_KEY + " = " + Pk;

        return ResourceManager.Statement(DELETE) == null;
    }

    public boolean reactivar(int Pk) {
        String DELETE = "UPDATE\n"
                + TABLA
                + "SET \n"
                + "     tipo_nota_estado = " + true + "\n"
                + "WHERE\n"
                + "	" + PRIMARY_KEY + " = " + Pk;

        return ResourceManager.Statement(DELETE) == null;
    }

}
