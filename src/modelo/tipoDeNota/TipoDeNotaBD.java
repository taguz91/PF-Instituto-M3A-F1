/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.tipoDeNota;

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
public class TipoDeNotaBD extends TipoDeNotaMD {

    public TipoDeNotaBD(int idTipoNota, String nombre, double valorMinimo, double valorMaximo) {
        super(idTipoNota, nombre, valorMinimo, valorMaximo);
    }

    public TipoDeNotaBD() {
    }

    private final String TABLA = "SELECT id_tipo_nota, tipo_nota_nombre, tipo_nota_valor_minimo, tipo_nota_valor_maximo FROM \"TipoDeNota\" ";

    public boolean insertar() {
        String INSERT = "INSERT INTO \"TipoDeNota\" \n"
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

        return SelectSimple(TABLA);

    }

    public List<TipoDeNotaMD> SelectOneWhereNombre(String Aguja) {

        String SELECT = TABLA + "  WHERE lower(tipo_nota_nombre) LIKE '%" + Aguja + "%'";
        return SelectSimple(SELECT);

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
        String DELETE = "DELETE FROM \"TipoDeNota\" WHERE id_tipo_nota = " + Pk;

        return ResourceManager.Statement(DELETE) == null;
    }

}
