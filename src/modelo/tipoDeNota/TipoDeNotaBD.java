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
import modelo.carrera.CarreraMD;
import modelo.periodolectivo.PeriodoLectivoMD;

/**
 *
 * @author MrRainx
 */
public class TipoDeNotaBD extends TipoDeNotaMD {

    public TipoDeNotaBD(int idTipoNota, String nombre, double valorMinimo, double valorMaximo, LocalDate fechaCreacion, boolean estado, PeriodoLectivoMD periodoLectivo) {
        super(idTipoNota, nombre, valorMinimo, valorMaximo, fechaCreacion, estado, periodoLectivo);
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
        this.setPeriodoLectivo(obj.getPeriodoLectivo());
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
                + "( tipo_nota_nombre, tipo_nota_valor_minimo, tipo_nota_valor_maximo, id_prd_lectivo )\n"
                + "VALUES\n"
                + "( \n"
                + "'" + getNombre() + "',\n"
                + " " + getValorMinimo() + ",\n"
                + " " + getValorMaximo() + ",\n"
                + "" + getPeriodoLectivo().getId_PerioLectivo() + "\n"
                + " );";

        return ResourceManager.Statement(INSERT) == null;
    }

    public static List<TipoDeNotaMD> selectAllWhereEstadoIs(boolean estado) {

        String SELECT = "SELECT\n"
                + "\"public\".\"TipoDeNota\".id_tipo_nota,\n"
                + "\"public\".\"TipoDeNota\".tipo_nota_nombre,\n"
                + "\"public\".\"TipoDeNota\".tipo_nota_valor_minimo,\n"
                + "\"public\".\"TipoDeNota\".tipo_nota_valor_maximo,\n"
                + "\"public\".\"TipoDeNota\".tipo_nota_fecha_creacion,\n"
                + "\"public\".\"TipoDeNota\".id_prd_lectivo,\n"
                + "\"public\".\"PeriodoLectivo\".id_carrera,\n"
                + "\"public\".\"Carreras\".carrera_nombre,\n"
                + "\"public\".\"Carreras\".carrera_modalidad,\n"
                + "\"public\".\"PeriodoLectivo\".prd_lectivo_nombre\n"
                + "FROM\n"
                + "\"public\".\"TipoDeNota\"\n"
                + "INNER JOIN \"public\".\"PeriodoLectivo\" ON \"public\".\"TipoDeNota\".id_prd_lectivo = \"public\".\"PeriodoLectivo\".id_prd_lectivo\n"
                + "INNER JOIN \"public\".\"Carreras\" ON \"public\".\"PeriodoLectivo\".id_carrera = \"public\".\"Carreras\".id_carrera\n"
                + "WHERE\n"
                + "\"TipoDeNota\".tipo_nota_estado IS " + estado;
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

    private static List<TipoDeNotaMD> SelectSimple(String QUERY) {
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

                PeriodoLectivoMD periodo = new PeriodoLectivoMD();
                periodo.setId_PerioLectivo(rs.getInt("id_prd_lectivo"));
                periodo.setNombre_PerLectivo(rs.getString("prd_lectivo_nombre"));

                CarreraMD carrera = new CarreraMD();
                carrera.setId(rs.getInt("id_carrera"));
                carrera.setNombre(rs.getString("carrera_nombre"));
                carrera.setModalidad(rs.getString("carrera_modalidad"));
                periodo.setCarrera(carrera);

                tipoNota.setPeriodoLectivo(periodo);

                Lista.add(tipoNota);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(TipoDeNotaBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Lista;
    }

    public static List<TipoDeNotaMD> selectValidaciones(int idPeriodo) {
        String SELECT = "SELECT\n"
                + "\"public\".\"TipoDeNota\".id_tipo_nota,\n"
                + "\"public\".\"TipoDeNota\".tipo_nota_nombre,\n"
                + "\"public\".\"TipoDeNota\".tipo_nota_valor_minimo,\n"
                + "\"public\".\"TipoDeNota\".tipo_nota_valor_maximo\n"
                + "FROM\n"
                + "\"public\".\"TipoDeNota\"\n"
                + "INNER JOIN \"public\".\"PeriodoLectivo\" ON \"public\".\"TipoDeNota\".id_prd_lectivo = \"public\".\"PeriodoLectivo\".id_prd_lectivo\n"
                + "WHERE \n"
                + "\"public\".\"PeriodoLectivo\".id_prd_lectivo = " + idPeriodo + "";

        List<TipoDeNotaMD> lista = new ArrayList<>();

        System.out.println(SELECT);
        ResultSet rs = ResourceManager.Query(SELECT);

        try {
            while (rs.next()) {
                TipoDeNotaMD tipo = new TipoDeNotaMD();
                tipo.setIdTipoNota(rs.getInt("id_tipo_nota"));
                tipo.setNombre(rs.getString("tipo_nota_nombre"));
                tipo.setValorMinimo(rs.getDouble("tipo_nota_valor_minimo"));
                tipo.setValorMaximo(rs.getDouble("tipo_nota_valor_maximo"));

                lista.add(tipo);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return lista;
    }

    public boolean editar(int Pk) {
        String UPDATE = "UPDATE\n"
                + TABLA + "\n"
                + "SET \n"
                + "	tipo_nota_nombre = '" + getNombre() + "',\n"
                + "	tipo_nota_valor_minimo = " + getValorMinimo() + ",\n"
                + "	tipo_nota_valor_maximo = " + getValorMaximo() + ",\n"
                + "	id_prd_lectivo = " + getPeriodoLectivo().getId_PerioLectivo() + "\n"
                + "WHERE\n"
                + "	id_tipo_nota = " + Pk;

        return ResourceManager.Statement(UPDATE) == null;

    }

    public boolean eliminar(int Pk) {
        String DELETE = "UPDATE\n"
                + TABLA + "\n"
                + "SET \n"
                + "     tipo_nota_estado = " + false + " \n"
                + "WHERE\n"
                + "	" + PRIMARY_KEY + " = " + Pk;

        return ResourceManager.Statement(DELETE) == null;
    }

    public boolean reactivar(int Pk) {
        String DELETE = "UPDATE\n"
                + TABLA + "\n"
                + "SET \n"
                + "     tipo_nota_estado = " + true + "\n"
                + "WHERE\n"
                + "	" + PRIMARY_KEY + " = " + Pk;

        return ResourceManager.Statement(DELETE) == null;
    }

}
