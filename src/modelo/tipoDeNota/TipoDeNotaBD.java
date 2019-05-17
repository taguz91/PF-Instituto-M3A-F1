/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.tipoDeNota;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConnDBPool;
import modelo.carrera.CarreraMD;
import modelo.periodolectivo.PeriodoLectivoMD;

/**
 *
 * @author MrRainx
 */
public class TipoDeNotaBD extends TipoDeNotaMD {

    private static ConnDBPool pool;
    private static Connection conn;
    private static ResultSet rs;

    static {
        pool = new ConnDBPool();
    }

    public TipoDeNotaBD(int idTipoNota, String nombre, double valorMinimo, double valorMaximo, LocalDate fechaCreacion, boolean estado, PeriodoLectivoMD periodoLectivo) {
        super(idTipoNota, nombre, valorMinimo, valorMaximo, fechaCreacion, estado, periodoLectivo);
    }

    public TipoDeNotaBD() {
    }

    public TipoDeNotaBD(TipoDeNotaMD obj) {
        setIdTipoNota(obj.getIdTipoNota());
        setNombre(obj.getNombre());
        setValorMinimo(obj.getValorMinimo());
        setValorMaximo(obj.getValorMaximo());
        setFechaCreacion(obj.getFechaCreacion());
        setEstado(obj.isEstado());
        setPeriodoLectivo(obj.getPeriodoLectivo());
    }

    public boolean insertar() {
        String INSERT = "INSERT INTO  \"TipoDeNota\"  \n"
                + "( tipo_nota_nombre, tipo_nota_valor_minimo, tipo_nota_valor_maximo, id_prd_lectivo )\n"
                + "VALUES (?,?,?,?)";
        conn = pool.getConnection();
        Map<Integer, Object> parametros = new HashMap<>();
        parametros.put(1, getNombre());
        parametros.put(2, getValorMinimo());
        parametros.put(3, getValorMaximo());
        parametros.put(4, getPeriodoLectivo().getId_PerioLectivo());

        return pool.ejecutar(INSERT, conn, parametros) == null;
    }

    public static List<TipoDeNotaBD> selectAllWhereEstadoIs(boolean estado) {

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

    private static List<TipoDeNotaBD> SelectSimple(String QUERY) {
        List<TipoDeNotaBD> Lista = new ArrayList<>();

        conn = pool.getConnection();

        rs = pool.ejecutarQuery(QUERY, conn, null);

        try {
            while (rs.next()) {

                TipoDeNotaBD tipoNota = new TipoDeNotaBD();

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
        } catch (SQLException ex) {
            Logger.getLogger(TipoDeNotaBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            pool.close(conn);
        }

        return Lista;
    }

    public static List<TipoDeNotaBD> selectWhere(int idPeriodo) {
        String SELECT = "SELECT\n"
                + "\"public\".\"TipoDeNota\".id_tipo_nota,\n"
                + "\"public\".\"TipoDeNota\".tipo_nota_nombre,\n"
                + "\"public\".\"TipoDeNota\".tipo_nota_valor_minimo,\n"
                + "\"public\".\"TipoDeNota\".tipo_nota_valor_maximo\n"
                + "FROM\n"
                + "\"public\".\"TipoDeNota\"\n"
                + "WHERE \n"
                + "\"public\".\"TipoDeNota\".id_prd_lectivo = " + idPeriodo + "";

        List<TipoDeNotaBD> lista = new ArrayList<>();

        conn = pool.getConnection();
        rs = pool.ejecutarQuery(SELECT, conn, null);

        try {
            while (rs.next()) {
                TipoDeNotaBD tipo = new TipoDeNotaBD();
                tipo.setIdTipoNota(rs.getInt("id_tipo_nota"));
                tipo.setNombre(rs.getString("tipo_nota_nombre"));
                tipo.setValorMinimo(rs.getDouble("tipo_nota_valor_minimo"));
                tipo.setValorMaximo(rs.getDouble("tipo_nota_valor_maximo"));

                lista.add(tipo);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            pool.close(conn);
        }

        return lista;
    }

    public static List<String> selectNombreWhere(int idPeriodo) {

        String SELECT = "SELECT\n"
                + "\"public\".\"TipoDeNota\".tipo_nota_nombre\n"
                + "FROM\n"
                + "\"public\".\"TipoDeNota\"\n"
                + "WHERE\n"
                + "id_prd_lectivo = ?";
        List<String> lista = new ArrayList<>();

        Map<Integer, Object> parametros = new HashMap<>();

        parametros.put(1, idPeriodo);

        conn = pool.getConnection();
        rs = pool.ejecutarQuery(SELECT, conn, parametros);
        try {
            while (rs.next()) {
                lista.add(rs.getString("tipo_nota_nombre"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            pool.close(conn);
        }

        return lista;
    }

    public boolean editar(int Pk) {
        String UPDATE = "UPDATE \"TipoDeNota\" \n"
                + "SET \n"
                + "	tipo_nota_nombre = '" + getNombre() + "',\n"
                + "	tipo_nota_valor_minimo = " + getValorMinimo() + ",\n"
                + "	tipo_nota_valor_maximo = " + getValorMaximo() + ",\n"
                + "	id_prd_lectivo = " + getPeriodoLectivo().getId_PerioLectivo() + "\n"
                + "WHERE\n"
                + "	id_tipo_nota = " + Pk;

        conn = pool.getConnection();

        return pool.ejecutar(UPDATE, conn, null) == null;

    }
}
